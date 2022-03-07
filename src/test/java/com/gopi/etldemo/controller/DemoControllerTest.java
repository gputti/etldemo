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

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


/**
 * @author Gopikrishna Putti
 * Mar 7, 2022
 *
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoControllerTest {


	@Test
	public void checkSatusOfApi() throws ClientProtocolException, IOException {


		HttpUriRequest request = new HttpGet( "http://127.0.0.1:8010/rest/etl/api/status" );
		HttpResponse httpResponse = HttpClientBuilder.create().build().execute( request );
		assertEquals( httpResponse.getStatusLine().getStatusCode(), HttpStatus.SC_OK );
	}


	
	public static void manualtesting1() {
		
		String json = "{\"table_name\" : \"hospital\",\"data_location\" : \"/Users/gopi/Downloads/Hospital_General_Information.csv\",\"configfile_location\" : \"/Users/gopi/Downloads/hospital.config\"}";

		DemoController controller = new DemoController( );
		controller.readData(json, null);
	}
	

	public static void manualtesting2() {

		String json = "{ \"table_name\" : \"timelycare\",	\"data_location\" : \"/Users/gopi/Downloads/Timely_and_Effective_Care-Hospital.csv\", \"configfile_location\" : \"/Users/gopi/Downloads/timelycare.config\" }";

		DemoController controller = new DemoController( );
		controller.readData(json, null);
	}
	
	public static void main(String [] args ) throws Exception  {
		manualtesting2();
	}
	
	
}
