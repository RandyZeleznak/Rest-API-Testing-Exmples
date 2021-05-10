package com.zsquared.apitesting;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.Assert;

import com.zsquared.apitesting.files.Payload;
import com.zsquared.apitesting.files.ReUsableMethods;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Basics {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RestAssured.useRelaxedHTTPSValidation();
		RestAssured.baseURI="https://rahulshettyacademy.com";
		String resp = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
		.body(Payload.addPlace()).when().post("maps/api/place/add/json")
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
