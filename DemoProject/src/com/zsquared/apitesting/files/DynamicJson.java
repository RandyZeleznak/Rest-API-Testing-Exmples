package com.zsquared.apitesting.files;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class DynamicJson {
	
	@Test
	public void addBook() {
		RestAssured.useRelaxedHTTPSValidation();
		RestAssured.baseURI="https://rahulshettyacademy.com";
		String response = given().log().all().header("Content-Type","application/json").
		body(Payload.addBook()).
		when() 
		.post("/Library/Addbook.php") 
		.then().log().all().assertThat().statusCode(200) 
		.extract().response().asString();
		JsonPath js = ReUsableMethods.rawToJson(response);
		String id = js.get("ID");
		System.out.println(id);
	
	}

}
