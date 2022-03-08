
package com.gopi.etldemo.model;

/**
 * @author Gopikrishna Putti
 * Mar 6, 2022
 *
 */

public class ExtractConfig {
	
	public String table_name;
	public String data_location;
	public String configfile_location;
	public String transformations;
	public boolean ignoreErrors = false;
	
	public String getTable() {
		return table_name;
	}
	public String getDataloc() {
		return data_location;		
	}
	public String getConfig() {
		return configfile_location;
	}
	
	public String getTransformations( ) {
		return transformations;
	}
	
	public String toString() {
		return table_name + "; " + configfile_location + "; " + data_location + "; " + transformations;
	}

}
