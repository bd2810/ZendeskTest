package com.zendesk.test;

import static io.restassured.RestAssured.given;

import java.io.File;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.zendesk.pageObjects.Tickets;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
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
	 * Below test will create a new ticket using a JSON file
	 */
	@Test
	public void createTicket() throws Exception {

		File jsonFile = new File("./src/main/resources/newTicket.json");

		given().contentType("application/json").body(jsonFile).when().post("/tickets.json").then().statusCode(201);

	}
	
	@Test
	public void addComment() throws Exception {

		// First create a ticket we want to add a comment to
		File jsonFile = new File("./src/main/resources/commentTicket.json");
//		Response response = given().contentType("application/json").body(jsonFile).when().post("/tickets.json");
//		String responseBody = response.getBody().asString();
//		JsonPath jsonPath = new JsonPath(responseBody);
//		int user_id = jsonPath.getInt("id");
		
//		System.out.println(user_id);
		String ticketID = given().contentType("application/json").body(jsonFile).when().post("/tickets.json").then().statusCode(201)
				.extract().path("id");
		
		System.out.println(ticketID);
		
		given().contentType("application/json").body("{\"request\": {\"comment\": {\"body\": \"Adding a new comment!\"}}}").when()
		.put("https://bhavik.zendesk.com/api/v2/requests/"+ticketID+".json").then().statusCode(200);
	}
	
	@Test
	public void addCommentToTicker() throws Exception {

		given().contentType("application/json").body("{\"request\": {\"comment\": {\"body\": \"Thanks for the information!\"}}}").when()
		.put("https://bhavik.zendesk.com/api/v2/requests/1.json").then().statusCode(200);
	}


	

	/*
	 * Below test will create a new ticket by creating a new ticket object and setting
	 * ticket parameters
	 */
	//@Test
	public void createTicketSecond() throws Exception {

		Tickets myTicket = new Tickets();
		myTicket.setSubject("Creating new ticket using object model in Zendesk!");
		myTicket.setType("task");
		myTicket.setComment("The issue is very critical");
		myTicket.setExternalId("1001");
		myTicket.setPriority("urgent");

		given().contentType(ContentType.JSON).body(myTicket).when().post("/tickets.json").then().statusCode(201);

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
