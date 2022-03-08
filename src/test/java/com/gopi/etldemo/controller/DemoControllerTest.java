
package com.gopi.etldemo.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
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

	@Test
	public void checkWrongInput() throws ClientProtocolException, IOException {


		HttpUriRequest request = new HttpPost( "http://127.0.0.1:8010/rest/etl/api/etl" );
		HttpResponse httpResponse = HttpClientBuilder.create().build().execute( request );
		assertEquals( httpResponse.getStatusLine().getStatusCode(), HttpStatus.SC_BAD_REQUEST );
	}


	
	public static void manualtesting1() throws Exception  {
		
		String json = "{\"table_name\" : \"hospital\",\"data_location\" : \"/Users/gopi/Downloads/Hospital_General_Information.csv\",\"configfile_location\" "
				+ ": \"/Users/gopi/Downloads/hospital.config\", \"transformations\" : \"\", \"ignoreErrors\" : \"False\" }";

		DemoController controller = new DemoController( );
		controller.readAndLoadData(json, null);
	}
	

	public static void manualtesting2() throws Exception  {

		String json = "{ \"table_name\" : \"timelycare\",	\"data_location\" : \"/Users/gopi/Downloads/Timely_and_Effective_Care-Hospital.csv\","
				+ " \"configfile_location\" : \"/Users/gopi/Downloads/timelycare.config\", \"transformations\" "
				+ ": \"/Users/gopi/Downloads/timelycare_trans.config\", \"ignoreErrors\" : \"True\" }";

		DemoController controller = new DemoController( );
		controller.readAndLoadData(json, null);
	}
	
	public static void main(String [] args ) throws Exception  {
		manualtesting1();
	}
	
	
}
