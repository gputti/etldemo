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

	//_#ACTUALNUMBER_# | {"op" : "multi", "params" : [ "Score", "Sample"]}  
	@Column(name = "ActualNumber")
	public Integer getActualNumber() {
		if ( Score == null || Sample == null ) {
			return null;
		}
		return (int)(Score * Sample);
	}

}
