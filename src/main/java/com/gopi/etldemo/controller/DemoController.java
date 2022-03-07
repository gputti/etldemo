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
import com.gopi.etldemo.service.HospitalService;
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

	private String getTime() {
		return new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(System.currentTimeMillis()) ; 
	}


	@GetMapping("/")
	public String test ( HttpServletResponse response) {

		String currtime = getTime();
		logger.info("ETL demo service running. time {}", currtime );
		log.debug("test ");
		return "ETL demo service running. Current time : " + currtime ;
	}       

	@PostMapping("/extract")
	@Transactional
	public ResponseEntity<String> readData(@RequestBody String json, HttpServletResponse response) {
		logger.info("read hospital data: {}", getTime() );
		logger.info("json received: {}", json );
		ExtractConfig obj = extractInputDetails(json);

		List<Hospital> result = new ArrayList<Hospital>();
		
		if ( obj.getTable().equalsIgnoreCase("hospital")) {
			List<Hospital> list = getHospitalObjects(obj);
			
			for(Hospital h : list) {
				result.add(hospitalService.saveHospital(h));
			}
			

			// persist all objects.


			// store the failed records to a particular place 


			// prepare return message & send,
		} else if (obj.getTable().equalsIgnoreCase("timelycare_score") ) {

			List<Hospital> list = getTimelycareScoreObjects(obj);

			// persist all objects.


			// store the failed records to a particular place 


			// prepare return message & send,



		}

		return new ResponseEntity<String>( "svaed successfully. result.size() " + result.size() , HttpStatus.OK);

	}


	private List<Hospital> getTimelycareScoreObjects(ExtractConfig obj) {
		// TODO Auto-generated method stub
		return null;
	}



	private List<Hospital> getHospitalObjects(ExtractConfig obj) {


		List<Hospital> output = new ArrayList<Hospital>( );
		List<String> erroroutput = new ArrayList<String>( );

		// read config
		List<String> colnames = new ArrayList<String>( );
		Map<String, DbConfig> configmap = new LinkedHashMap<String, DbConfig>( );		
		Path path2 = Paths.get(obj.getConfig());		
		try(  Stream<String> lines = Files.lines(path2)) {

			lines.forEach(x ->  { 
				if ( x != null && !x.trim().isEmpty()) {
					DbConfig dc = buildConfigObj(x);
					colnames.add(dc.name);
					configmap.put(dc.name, dc);					
				}

			});			
		} catch (IOException e) {
			logger.error("Error reading peoplename.txt " +e.getMessage());
		}




		// read data
		List<String> data = new ArrayList<String>( );		
		Path path = Paths.get(obj.getDataloc());
		Map<String, Integer> cntr = new HashMap<String, Integer>();
		cntr.put("counter", 0);

		try (CSVReader reader = new CSVReader(new FileReader(obj.getDataloc()))) {
			String[] lineInArray;
			while ((lineInArray = reader.readNext()) != null) {
				cntr.put("counter", cntr.get("counter") + 1  );				 
				System.out.println(">>>> arrays: " + lineInArray.length );
				Object o1 = buildObjectFromRawData(colnames, configmap,  lineInArray);
				if ( o1 instanceof Hospital) {
					output.add((Hospital)o1);
				} else {
					erroroutput.add((String)o1);						
				}
			}

		} catch (IOException | CsvValidationException ex) {
			logger.error("Error reading peoplename.txt " +ex.getMessage());
		}


		// no CSV reader
		//		try(  Stream<String> lines = Files.lines(path)) {
		//			lines.forEach(dataline -> {
		//				cntr.put("counter", cntr.get("counter") + 1  );
		//				data.add(dataline);
		//				Object o1 = buildObjectFromRawData(colnames, configmap,  dataline);
		//				if ( o1 instanceof Hospital) {
		//					output.add((Hospital)o1);
		//				} else {
		//					erroroutput.add((String)o1);						
		//				}
		//			});
		//			
		//		} catch (IOException e) {
		//			logger.error("Error reading peoplename.txt " +e.getMessage());
		//		}


		// go thru each data row and build object.


		return output;
	}

	private Object buildObjectFromRawData(List<String> colnames, Map<String, DbConfig> configmap, String[] lineInArray) {
		String dataline = null;
		//		String [] parts = dataline.split(",");
		String [] parts = lineInArray;
		String err = "";
		if ( parts.length != colnames.size() ) {
			err = "Data exception: no of values mismatch. expected: " +  colnames.size() + ", actual: " + parts.length; 
			err = err + "; Data: " + dataline;
			return err;
		}
		boolean failed = false;
		// now build hospital  object.
		// map all fields to
		Map<String, String> valmap = new HashMap<String, String>( );
		for(int ik =0 ; ik < parts.length; ik++ ) {
			String colval = parts[ik];
			String colname = colnames.get(ik);
			String type = configmap.get(colname).type;



			if( type.equals("SMALLINT") || type.equals("INTEGER") ) {
				if( colval == null || colval.isEmpty() ) {
					colval = null;
				} else if ( colval.equalsIgnoreCase("Not Available") ) {
					colval = null;
				} else {
					try {
						Integer.parseInt(colval);
					} catch (Exception e) {
						err = "error parsing " + colname + ", input is: " + colval;
						failed = true;
					}
				}
			} else if ( type.trim().toUpperCase().startsWith("VARCHAR")) {
				String len = type.substring(type.indexOf("(") + 1, type.indexOf(")"));
//				logger.debug("len : {} " , len);
			}
			if ( failed )
				return err;

			// data is clean and we can build object now			
			valmap.put(colname, colval);			
		}
		Hospital h1 = mapper.convertValue(valmap, Hospital.class);

		return h1;

	}


	private DbConfig buildConfigObj(String x) {

		String parts[] = x.split("\\|");
		logger.trace(">> x {}", x);
		DbConfig config = new DbConfig(parts[0].trim(), parts[1].trim());
		return config;
	}


	private ExtractConfig extractInputDetails(String json) {
		// extract all values. 
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

	public static void main(String[] args) {

		ExtractConfig config = new ExtractConfig( );
		config.configfile_location = "/Users/gopi/Downloads/hospital.config";
		config.data_location = "/Users/gopi/Downloads/Hospital_General_Information.csv";
		config.table_name = "hostpital";

		String json = "{\"table_name\" : \"hospital\",\"data_location\" : \"/Users/gopi/Downloads/Hospital_General_Information.csv\",\"configfile_location\" : \"/Users/gopi/Downloads/hospital.config\"}";

		DemoController controller = new DemoController( );
		controller.readData(json, null);

	}


}
