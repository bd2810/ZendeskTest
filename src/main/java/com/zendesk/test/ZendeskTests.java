package com.zendesk.test;

import static io.restassured.RestAssured.given;

import java.io.File;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ZendeskTests {

	private static final String token = "0Jh8yKwpNBJPQjyAEDkB3vWPPjDNe8IUFzisWTOP";
	public static String ticketID = null;

	@BeforeClass
	public static void setup() {

		RestAssured.basePath = "/api/v2";
		RestAssured.baseURI = "https://bhavik.zendesk.com";
		RestAssured.authentication = RestAssured.basic("bhavik.doshi@gmail.com/token", token);
	}

	@Test
	public void createTicket() throws Exception {

		File jsonFile = new File("./src/main/resources/newTicket.json");

		given().contentType("application/json").body(jsonFile).when().post("/tickets.json").then().statusCode(201);

	}

	@Test
	public void listTickets() {

		given().when().get("/tickets.json").then().statusCode(200);
		Response string = given().when().get("/tickets.json");
		System.out.println(string.asString());
	}

	@Test
	public void deleteTicket() {

		// First create the ticket which we want to delete and store it's
		// location in a string
		File jsonFile = new File("./src/main/resources/deleteTicket.json");
		String deleteTicket = given().contentType("application/json").body(jsonFile).when().post("/tickets.json").then()
				.statusCode(201).extract().header("Location");

		// Now delete the ticket created above
		given().when().delete(deleteTicket).then().statusCode(204);

	}

}
