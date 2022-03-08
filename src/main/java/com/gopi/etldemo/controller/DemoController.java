/**
 * Copyright © 2018–2020 by MachEye Inc. All rights reserved.
 * NOTICE: All information contained herein is the property of MachEye Inc.
 * No part of this publication may be reproduced, stored in a retrieval system, 
 * or transmitted, in any form or by any means, electronic, mechanical, 
 * photocopying, or otherwise, without the prior written consent of 
 * the publisher. Any software referred to herein is furnished under 
 * license and may only be used or copied in accordance with the terms 
 * of such license.
 * This publication and the information herein is furnished AS IS, and 
 * is subject to change without notice. MachEye Inc. assumes no responsibility 
 * or liability for any errors or inaccuracies, makes no warranty of any kind 
 * (express, implied or statutory) with respect to this publication, 
 * and expressly disclaims any and all warranties of merchantability, 
 * fitness for particular purposes and noninfringement of third party rights.
 * 
 **/
package com.gopi.etldemo.controller;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gopi.etldemo.model.DbConfig;
import com.gopi.etldemo.model.ExtractConfig;
import com.gopi.etldemo.model.Hospital;
import com.gopi.etldemo.model.TimelyCare;
import com.gopi.etldemo.service.HospitalService;
import com.gopi.etldemo.service.TimelyCareService;
import com.gopi.etldemo.transformations.TransFormulaImple;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Gopikrishna Putti
 * Mar 5, 2022
 *
 */

@RestController
@RequestMapping("/rest/etl/api")
@Slf4j
public class DemoController {


	private static final Logger logger = LoggerFactory.getLogger(DemoController.class);
	static ObjectMapper mapper = new ObjectMapper();

	static {
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL,true);
	}

	@Autowired
	private HospitalService hospitalService; 
	
	@Autowired
	private TimelyCareService timelyCareService;

	private String getTime() {
		return new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(System.currentTimeMillis()) ; 
	}


	@GetMapping("/status")
	public ResponseEntity<String> test ( HttpServletResponse response) {

		String currtime = getTime();
		logger.info("ETL demo service running. time {}", currtime );
		return new ResponseEntity<String>( "ETL demo service running. Current time : " + currtime , HttpStatus.OK);
	}       

	@PostMapping("/etl")
	@Transactional
	public ResponseEntity<String> readAndLoadData(@RequestBody String json, HttpServletResponse response) {

		logger.info("json received: {}", json );
		ExtractConfig obj = extractInputDetails(json);
		if ( obj == null ) {
			return new ResponseEntity<String>( "wrong input. " , HttpStatus.BAD_REQUEST);
		}

		List<Object> result = new ArrayList<Object>();
		
		if ( obj.getTable().equalsIgnoreCase("hospital")) {
			List<Hospital> list = buildEntityObjects(obj);
			for(Hospital h : list) {
				result.add(hospitalService.save(h));
			}

		} else if (obj.getTable().equalsIgnoreCase("timelycare") ) {

			List<TimelyCare> list = buildEntityObjects(obj);
			
			for(TimelyCare tc : list) {
				result.add(timelyCareService.save(tc));
			}

		}

		return new ResponseEntity<String>( "svaed successfully. result.size() " + result.size() , HttpStatus.OK);
	}


	protected <T> List<T> buildEntityObjects(ExtractConfig obj) {
		
		
		List<T> output = new ArrayList<T>( );
		List<String> erroroutput = new ArrayList<String>( );
		// read config
		Map<String, DbConfig> configmap = getDbConfigFromConfig(obj, false);
		Map<String, DbConfig> trxConfigmap = getDbConfigFromConfig(obj, true);
		// read data
		Map<String, Integer> cntr = new HashMap<String, Integer>();
		cntr.put("counter", 0);

		try (CSVReader reader = new CSVReader(new FileReader(obj.getDataloc()))) {
			String[] lineInArray;
			while ((lineInArray = reader.readNext()) != null) {
				if ( lineInArray.length > 0 ) {
					cntr.put("counter", cntr.get("counter") + 1  );
					Object o1 = buildObjectFromRawData(obj.table_name, configmap, trxConfigmap, lineInArray);
					if ( o1 instanceof String) {
						erroroutput.add((String)o1);
					} else {
						output.add((T)o1);
					}					
				}
			}

		} catch (IOException | CsvValidationException ex) {
			logger.error("Error reading peoplename.txt " +ex.getMessage());
		}
		return output;
	}

	// build Entity object from raw data 
	private Object buildObjectFromRawData(String entityName, Map<String, DbConfig> configmap, 
			Map<String, DbConfig> trxConfigmap, String[] lineInArray) {
		

		int numOfRawDataCol = getColumnCountFromRawData(configmap);
		
		String [] colnames = getColumnNames(configmap);
		
		String dataline = null;
		String [] parts = lineInArray;
		int noofparts = parts.length;
		String err = "";
		if ( noofparts != numOfRawDataCol ) {
			err = "Data exception: no of values mismatch. expected: " +  colnames.length + ", actual: " + parts.length; 
			err = err + "; Data: " + dataline;
			return err;
		}
		
		// now build entity object. 
		boolean failed = false;
		Map<String, Object> valmap = new HashMap<String, Object>( );
		for(int ik =0 ; ik < parts.length; ik++ ) {
			
			String colval = parts[ik];
			String colname = colnames[ik];
			String type = configmap.get(colname).type.trim().toUpperCase();
			String format = configmap.get(colname).format == null ? null : configmap.get(colname).format.trim();

			if( type.equals("SMALLINT") || type.equals("INTEGER") ) {
				
				if( colval == null || colval.isEmpty() ) {
					colval = null;
					valmap.put(colname, colval ); 
				} else if ( colval.equalsIgnoreCase("Not Available") ) {
					colval = null;
				} else {
					try {
						Integer.parseInt(colval);
						valmap.put(colname, Integer.parseInt(colval));
					} catch (Exception e) {
						err = err + "error parsing " + colname + ", input is: " + colval;
						failed = true;
					}
				}
			} else if ( type.startsWith("VARCHAR")) {
				String len = type.substring(type.indexOf("(") + 1, type.indexOf(")"));
				int len1 = Integer.parseInt(len);
				if ( colval != null ) {
					if ( len1 < colval.length() ) {
						logger.warn("table col length: {}, data len: {}", len1, colval.length() );
						err = err + " col size is small than data size";
						failed = true;
					} else {
						valmap.put(colname, colval );
					}
				} else {
					valmap.put(colname, colval);
				}
				
			} else if ( type.equals("DATE") || type.equals("TIMESTAMP") ) {				
				if ( colval != null ) {
					java.util.Date  d1 = getValidDate(colval.trim(), format);
					if ( d1 != null ) {
						valmap.put(colname, d1 );
					} else {
						logger.warn("date value is not valid. {}, expected format: {}", colval, format );
						err = err + "date value is not valid. " + colval + ", expected format: " + format;
						failed = true;						
					}

				} else {
					valmap.put(colname, colval );
				}
				
			}
			if ( failed )
				return err;
		}
		
		
		// now check for any transformations. 
		if ( trxConfigmap != null && !trxConfigmap.isEmpty() ) {
			for(String cname : trxConfigmap.keySet() ) {
				String trx = trxConfigmap.get(cname).transformjson;
				TransFormulaImple tfm =  getFormulaObject(trx);
				if ( tfm == null ) {
					err = err + "failed to do transformation. " + cname ;
					failed = true;
				} else {
					Object v1 = tfm.getValue(valmap);
					valmap.put(cname, v1 );
				}
			}
		}
		if ( failed ) 	return err;
		
		Class klazz = getClassObject(entityName);
		Object o1 = mapper.convertValue(valmap, klazz);

		return o1;

	}


	private TransFormulaImple getFormulaObject(String trx) {

		try {
			return mapper.readValue(trx, TransFormulaImple.class);
		}catch (Exception e) {
			return null;
		}

	}


	private Map<String, DbConfig> getDbConfigFromConfig(ExtractConfig obj, boolean trxConfig ){
		
		// user linkedhashmap so order of insertion is maintained.
		Map<String, DbConfig> configmap = new LinkedHashMap<String, DbConfig>( );
		String filepath = obj.getConfig();
		if ( trxConfig ) {
			filepath = obj.getTransformations();
		}
		
		if ( filepath == null || filepath.isEmpty() ) {
			// nothing to do. take u-turn 
			return null;
		}
		Path path2 = Paths.get(filepath);
		
		try(  Stream<String> lines = Files.lines(path2)) {

			lines.forEach(x ->  { 
				if ( x != null && !x.trim().isEmpty()) {
					DbConfig dc = buildConfigObj(x);
					configmap.put(dc.name, dc);					
				}

			});			
		} catch (IOException e) {
			logger.error("Error reading peoplename.txt " +e.getMessage());
		}
		return configmap;
	}


	private String[] getColumnNames(Map<String, DbConfig> configmap) {

		String [] cols = new String[configmap.size()];
		int ctr = 0;
		for(String key : configmap.keySet()) {
			cols[ctr++] = key;
		}
		return cols;
	}
	
	// date format validation.
	private java.util.Date getValidDate(String datestr, String pattern) {
		if (pattern == null ) {
			pattern = "MM/dd/yyyy";
		}		
		try {
			SimpleDateFormat dateformat = new SimpleDateFormat(pattern	);
			logger.info(">>> '{}'", datestr);
			return dateformat.parse(datestr);
        } catch (java.text.ParseException e) {
        	logger.error("date parsing error ", e);
            return null;
        }		
	}


	private int getColumnCountFromRawData(Map<String, DbConfig> configmap) {
		int colCountFromRawData = 0;
		for(String key : configmap.keySet() ) {
			DbConfig config = configmap.get(key);
			if( config.transformjson == null ) {
				colCountFromRawData++;
			}
		}
		return colCountFromRawData;
	}


	// builds dbconfig object. extend this for future requirements.
	private DbConfig buildConfigObj(String x) {

		String parts[] = x.split("\\|");
		logger.trace(">> x {}", x);
		DbConfig config = null;
		if ( parts.length == 2 ) {
			config = new DbConfig(parts[0].trim(), parts[1].trim());
		} else if ( parts.length == 3 ) {
			config = new DbConfig(parts[0].trim(), parts[1].trim(), parts[2].trim() );
		} else if ( parts.length == 4 ) {
			config = new DbConfig(parts[0].trim(), parts[1].trim(), parts[2].trim(), parts[3].trim() );			
		}
		return config;
	}


	// parser input 
	private ExtractConfig extractInputDetails(String json) {
 
		ExtractConfig obj = null;
		try {
			obj = mapper.readValue(json, ExtractConfig.class);
			logger.info("extracting details: {}", obj );			
		} catch (Exception ex) {
			logger.error("error parsing the input json" , ex);
			return null;
		}
		return obj;
	}
	
	/**
	 * maps the entity name to objecl
	 * @param entityName
	 * @return
	 */
	private Class getClassObject(String entityName) {
		if ( entityName.equalsIgnoreCase("hospital")) {
			return Hospital.class;
		} else if ( entityName.equalsIgnoreCase("timelycare")) {
			return TimelyCare.class;
		}
		return null;
	}



}
