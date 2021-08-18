package com.zsquared.apitesting;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;

import com.zsquared.apitesting.files.ReUsableMethods;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class JsonFromFile {

	public static void main(String[] args) throws IOException {
		
		// content of file to String -> content of file can convert into Byte-> Byte data to string
		
		RestAssured.useRelaxedHTTPSValidation();
		RestAssured.baseURI="https://rahulshettyacademy.com";
		String resp = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
		.body(new String(Files.readAllBytes(Paths.get("C:\\Users\\Randy\\udemy\\RESTAPITesting\\DemoProject\\src\\addPlace.json")))).when().post("maps/api/place/add/json")
				.then().assertThat().statusCode(200).body("scope", equalTo("APP"))
				.header("server", "Apache/2.4.18 (Ubuntu)").extract().response().asString();
		// log().all().
		
		System.out.println(resp);
		
		// for parsing json
		JsonPath js= new JsonPath(resp);

		String placeId = js.getString("place_id");
		System.out.println(placeId);
		
		String newAddress = "123 ABC Street";
		
		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
		.body("{\r\n" + 
				"  \"place_id\":\""+placeId+"\",\r\n" + 
				"  \"address\": \""+newAddress+"\",\r\n" + 
				"  \"key\":\"qaclick123\"\r\n" + 
				"}")
		.when().put("maps/api/place/update/json")
		.then().assertThat().log().all().statusCode(200);
		// .then().assertThat().log().all().statusCode(200).body("msg",equalTo("Address successfully updated"));
		
		
		String getAddress = given().log().all().queryParam("key", "qaclick123")
		.queryParam("place_id", placeId)
		.when().get("maps/api/place/get/json")
		.then().assertThat().log().all().statusCode(200).extract().response().asString();
		
		JsonPath js1 = ReUsableMethods.rawToJson(getAddress);
		String actualAddress = js1.getString("address");
		
		System.out.println("Actual Adress " +actualAddress);
		
		Assert.assertEquals(actualAddress, newAddress);
		
		
	}
	
		

}
