
package com.gopi.etldemo.model;

/**
 * @author Gopikrishna Putti
 * Mar 6, 2022
 *
 */

public class DbConfig {
	
	public String name;
	public String type;
	public String format;
	public String transformjson;	
	
	public DbConfig(String name, String type ) {
		this(name, type, null);
	}
	
	public DbConfig(String name, String type , String format) {
		this(name, type, format, null);
	}
	
	public DbConfig(String name, String type , String format, String transformjson ) {
		this.name = name;
		this.type = type;
		this.format = format;
		this.transformjson = transformjson;
	}



}
