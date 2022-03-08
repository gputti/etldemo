
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
