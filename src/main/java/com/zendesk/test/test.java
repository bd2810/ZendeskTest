package com.zendesk.test;

import static io.restassured.RestAssured.given;


import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.zendesk.pageObjects.Tickets;

import io.restassured.RestAssured;

public class test {
	
	private static final String token = "0Jh8yKwpNBJPQjyAEDkB3vWPPjDNe8IUFzisWTOP";

	/*
	 * Before class method which sets base path, base URI, and authentication credentials
	 * which is common to all tests
	 */
	@BeforeClass
	public static void setup() {

		RestAssured.basePath = "/api/v2";
		RestAssured.baseURI = "https://bhavik.zendesk.com";
		RestAssured.authentication = RestAssured.basic("bhavik.doshi@gmail.com/token", token);
	}


	/*
	 * Below test will create a new ticket by creating a new ticket object and setting
	 * ticket parameters
	 */
	@Test
	public void createTicketSecond() throws Exception {

		Tickets myTicket = new Tickets();
		myTicket.setSubject("Creating new ticket using object model in Zendesk!");
		myTicket.setType("task");
		myTicket.setComment("The issue is very critical");
		myTicket.setExternalId("1001");
		myTicket.setPriority("urgent");

		given().contentType("application/json").body(myTicket).when().post("/tickets.json").then().statusCode(201);

	}


}
