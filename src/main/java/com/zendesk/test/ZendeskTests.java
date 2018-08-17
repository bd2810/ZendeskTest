package com.zendesk.test;

import static io.restassured.RestAssured.given;

import java.io.File;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ZendeskTests {

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
	 * Below test will create a new ticket using a JSON file and verifies it returns Http status code 201
	 */
	
	@Test
	public void createTicket() throws Exception {

		File jsonFile = new File("./src/main/resources/newTicket.json");

		given().contentType("application/json").body(jsonFile).when().post("/tickets.json").then().statusCode(201);

	}
	
	/*
	 * Below test will list all tickets using Http Get method and verifies it returns Http status code 200
	 */

	@Test
	public void listTickets() {

		given().when().get("/tickets.json").then().statusCode(200);
		Response string = given().when().get("/tickets.json");
		System.out.println(string.asString());
	}
	
	/*
	 * Below test will first create a new ticket and will than delete the same ticket and verifies it
	 * returns Http status code 204 after deletion
	 */

	@Test
	public void deleteTicket() {

		// First create the ticket which we want to delete and store it's location in a string
		
		File jsonFile = new File("./src/main/resources/deleteTicket.json");
		String deleteTicket = given().contentType("application/json").body(jsonFile).when().post("/tickets.json").then()
				.statusCode(201).extract().header("Location");

		// Now delete the ticket created above
		given().when().delete(deleteTicket).then().statusCode(204);

	}
	
	/*
	 * Below test will add a comment to existing ticket and verifies it returns Http status code 200
	 */
	
	@Test
	public void addCommentToTicket() throws Exception {

		given().contentType("application/json").body("{\"request\": {\"comment\": {\"body\": \"Thanks for the information!\"}}}").when()
		.put("https://bhavik.zendesk.com/api/v2/requests/34.json").then().statusCode(200);
	}


}
