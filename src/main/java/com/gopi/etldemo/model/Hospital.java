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
	

	public static final long serialVersionUID = 2561327726202126090L;
	
	
	@Id
	public Long Facility_ID  ;
	public String Facility_Name ;  
	public String Address ; 
	public String City  ;
	public String State ;
	public String ZIP_Code ;
	public String County_Name  ;
	public String Phone_Number ;
	public String Hospital_Type  ;
	public String Hospital_Ownership  ;
	public String Emergency_Services ;
	public String Meets_criteria_for_promoting_interoperability_of_EHRs ;  
	public Integer Hospital_overall_rating  ;
	public Integer Hospital_overall_rating_footnote ; 
	public Integer MORT_Group_Measure_Count  ;
	public Integer Count_of_Facility_MORT_Measures ;
	public Integer Count_of_MORT_Measures_Better  ;
	public Integer Count_of_MORT_Measures_No_Different ; 
	public Integer Count_of_MORT_Measures_Worse ; 
	public Integer MORT_Group_Footnote ;
	public Integer Safety_Group_Measure_Count ;
	public Integer Count_of_Facility_Safety_Measures ;
	public Integer Count_of_Safety_Measures_Better  ;
	public Integer Count_of_Safety_Measures_No_Different ; 
	public Integer Count_of_Safety_Measures_Worse ; 
	public Integer Safety_Group_Footnote ;
	public Integer READM_Group_Measure_Count  ;
	public Integer Count_of_Facility_READM_Measures ;
	public Integer Count_of_READM_Measures_Better ;
	public Integer Count_of_READM_Measures_No_Different ; 
	public Integer Count_of_READM_Measures_Worse ; 
	public Integer READM_Group_Footnote ;
	public Integer Pt_Exp_Group_Measure_Count ;
	public Integer Count_of_Facility_Pt_Exp_Measures ; 
	public Integer Pt_Exp_Group_Footnote  ;
	public Integer TE_Group_Measure_Count  ;
	public Integer Count_of_Facility_TE_Measures; 
	public Integer TE_Group_Footnote ;
	

	
	
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
