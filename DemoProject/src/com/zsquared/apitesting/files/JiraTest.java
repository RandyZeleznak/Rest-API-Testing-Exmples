package com.zsquared.apitesting.files;

import static io.restassured.RestAssured.given;

import org.testng.Assert;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

public class JiraTest {

	public static void main(String[] args) {

		RestAssured.baseURI="http://localhost:8080";

		SessionFilter session = new SessionFilter();
		
		String response = given().header("Content-Type", "application/json").body("{\"username\": \"RandyZeleznak\", \"password\": \"Patti0913\"}")
				.log().all().filter(session).when().post("/rest/auth/1/session").then().log().all().extract().response().asString();
		System.out.println(response);
		
		String givenMessage = "This is my test Jira message";
		
		String addCommentResponse = given().pathParam("id", "10202").log().all().header("Content-Type", "application/json").body("{\r\n" + 
				"    \"body\": \""+givenMessage+"\",\r\n" + 
				"    \"visibility\": {\r\n" + 
				"        \"type\": \"role\",\r\n" + 
				"        \"value\": \"Administrators\"\r\n" + 
				"    }\r\n" + 
				"}").filter(session).when().post("/rest/api/2/issue/{id}/comment").then().log().all().assertThat().statusCode(201).extract().response().asString();
		JsonPath jsc = new JsonPath(addCommentResponse);
		String commentId = jsc.getString("id");
		
		
		String issueDetails = given().filter(session).pathParam("id", "10202")
				.queryParam("fields", "comment")
				.log().all().when().get("/rest/api/2/issue/{id}").then().extract().response().asString();
		
		System.out.println(issueDetails);
		
		
		JsonPath js = new JsonPath(issueDetails);
		int commentsCount = js.getInt("fields.comment.comments.size()");
		for (int i = 0; i < commentsCount; i++) {
			String commentIdIssue = js.get("fields.comment.comments["+i+"].id").toString();
			if(commentIdIssue.equalsIgnoreCase(commentId)) {
			String message = js.get("fields.comment.comments["+i+"].body").toString();
			System.out.println(message);
			Assert.assertEquals(message, givenMessage);
			}
		}
		

	}

}
