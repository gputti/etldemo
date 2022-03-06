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
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;


/**
 * @author Gopikrishna Putti
 * Mar 5, 2022
 *
 */

@Entity(name = "HOSPITAL")
@Table(name = "HOSPITAL", schema="anthem")
@Data
public class Hospital implements Serializable {
	

	private static final long serialVersionUID = 2561327726202126090L;
	
	
	@Id
	private Long Facility_ID  ;
	private String Facility_Name ;  
	private String Address ; 
	private String City  ;
	private String State ;
	private String ZIP_Code ;
	private String County_Name  ;
	private String Phone_Number ;
	private String Hospital_Type  ;
	private String Hospital_Ownership  ;
	private String Emergency_Services ;
	private String Meets_criteria_for_promoting_interoperability_of_EHRs ;  
	private int Hospital_overall_rating  ;
	private int Hospital_overall_rating_footnote ; 
	private int MORT_Group_Measure_Count  ;
	private int Count_of_Facility_MORT_Measures ;
	private int Count_of_MORT_Measures_Better  ;
	private int Count_of_MORT_Measures_No_Different ; 
	private int Count_of_MORT_Measures_Worse ; 
	private int MORT_Group_Footnote ;
	private int Safety_Group_Measure_Count ;
	private int Count_of_Facility_Safety_Measures ;
	private int Count_of_Safety_Measures_Better  ;
	private int Count_of_Safety_Measures_No_Different ; 
	private int Count_of_Safety_Measures_Worse ; 
	private int Safety_Group_Footnote ;
	private int READM_Group_Measure_Count  ;
	private int Count_of_Facility_READM_Measures ;
	private int Count_of_READM_Measures_Better ;
	private int Count_of_READM_Measures_No_Different ; 
	private int Count_of_READM_Measures_Worse ; 
	private int READM_Group_Footnote ;
	private int Pt_Exp_Group_Measure_Count ;
	private int Count_of_Facility_Pt_Exp_Measures ; 
	private int Pt_Exp_Group_Footnote  ;
	private int TE_Group_Measure_Count  ;
	private int Count_of_Facility_TE_Measures; 
	private int TE_Group_Footnote ;
	

	
	
	public static Hospital build(Map<String, DbConfig> configmap , Map<String, Object> valueMap ) {
		
		Hospital h1 = new Hospital();
		
		if( valueMap.get("Facility_ID") != null  ) {
			h1.Facility_ID = Long.parseLong((String)valueMap.get("Facility_ID"));
		}
		
		if( valueMap.get("Facility_Name") != null  ) {
			h1.Facility_ID = Long.parseLong((String)valueMap.get("Facility_ID"));
		}
		
		
		return new Hospital();
	}
	

}
