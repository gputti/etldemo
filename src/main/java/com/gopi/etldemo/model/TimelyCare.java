
package com.gopi.etldemo.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * @author Gopikrishna Putti
 * Mar 6, 2022
 *
 */

@Entity(name = "TIMELYCARE")
@Table(name = "TIMELYCARE", schema="anthem")
@Data
public class TimelyCare implements Serializable {
	
	
	private static final long serialVersionUID = 4030080734920005843L;

	
    @Id
    @Column(name = "ID", unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    
	public Long Facility_ID;
	public String Facility_Name;
	public String Address;
	public String City;
	public String State;
	public String ZIP_Code;
	public String County_Name;
	public String Phone_Number;
	public String Condition;
	public String Measure_ID;
	public String Measure_Name;
	public Integer Score;
	public Integer Sample;
	public String Footnote;
	public Date Start_Date;
	public Date End_Date;
	public Integer actualNumber;


}
