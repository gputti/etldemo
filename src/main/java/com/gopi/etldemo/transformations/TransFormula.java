
package com.gopi.etldemo.transformations;

import java.util.List;
import java.util.Map;

/**
 * @author Gopikrishna Putti
 * Mar 7, 2022
 *
 */

public interface TransFormula {

	
	public String getOperation();
	
	public List<String> getParams();
	
	public Object getValue(Map<String, Object> values);
	
}
