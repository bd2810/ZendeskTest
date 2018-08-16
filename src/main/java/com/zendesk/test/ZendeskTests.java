package com.zendesk.test;

import org.testng.annotations.BeforeClass;
import io.restassured.RestAssured;


public class ZendeskTests {
	
	@BeforeClass
	public static void setup() {
		
		String port = System.getProperty("server.port");
		
		if (port == null) {
			
			RestAssured.port = Integer.valueOf(443);
			
		} else {
			
			RestAssured.port = Integer.valueOf(port);
		}

		String basePath = System.getProperty("server.base");
		
		if (basePath == null) {
			
			basePath = "/api/v2";
		}
		
		RestAssured.basePath = basePath;

		String baseHost = System.getProperty("server.host");
		
		if (baseHost == null) {
			
			baseHost = "https://bhavik.zendesk.com";
		}
		
		RestAssured.baseURI = baseHost;

	}	


}
