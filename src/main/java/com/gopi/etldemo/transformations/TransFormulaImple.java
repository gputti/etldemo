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
package com.gopi.etldemo.transformations;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * @author Gopikrishna Putti
 * Mar 7, 2022
 * sample json { "OP": "MULTIPLICATION" , "PARAMS" : ["Score", "Sample" ]} 
 */

@JsonPropertyOrder({
	"OP",
	"PARAMS"    
})
public class TransFormulaImple implements TransFormula {



	@JsonProperty("OP")
	public String operation;

	@JsonProperty("PARAMS")
	public List<String> params = new ArrayList<String>( );



	public TransFormulaImple() {

	}



	@Override
	public String getOperation() {
		return operation;
	}



	@Override
	public List<String> getParams() {
		return params;
	}



	@Override
	public Object getValue(Map<String, Object> values) {

		if ( operation.equalsIgnoreCase("MULTIPLICATION")) {

			int d = 1;

			for(String param : params ) {
				if ( values.get(param) != null ) {
					d = d * (Integer)values.get(param);

				} else {
					return null;
				}			
			}
			return d;
			
		} else if ( operation.equalsIgnoreCase("SUM") ) {
			// implement this
		}
		
		return null;
		
		
		
	}
	
}
