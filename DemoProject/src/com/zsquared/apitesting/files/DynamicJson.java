package com.zsquared.apitesting.files;

import static io.restassured.RestAssured.given;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class DynamicJson {
	
	@Test(dataProvider="BookData")
	public void addBook(String isbn, String aisle) {
		RestAssured.useRelaxedHTTPSValidation();
		RestAssured.baseURI="https://rahulshettyacademy.com";
		String response = given().log().all().header("Content-Type","application/json").
		body(Payload.addBook(isbn,aisle)).
		when() 
		.post("/Library/Addbook.php") 
		.then().log().all().assertThat().statusCode(200) 
		.extract().response().asString();
		JsonPath js = ReUsableMethods.rawToJson(response);
		String id = js.get("ID");
		System.out.println(id);
	
	}
	
	@Test(dataProvider="BookData")
	public void deleteBook(String isbn, String aisle) {
		RestAssured.useRelaxedHTTPSValidation();
		RestAssured.baseURI="https://rahulshettyacademy.com";
		String response = given().log().all().header("Content-Type","application/json").
		body(Payload.deleteBook(isbn,aisle)).
		when() 
		.delete("/Library/DeleteBook.php") 
		.then().log().all().assertThat().statusCode(200) 
		.extract().response().asString();
		JsonPath js = ReUsableMethods.rawToJson(response);
		String id = js.get("ID");
		System.out.println("Deleting"+id);
	
	}
	
	@DataProvider(name="BookData")
	public Object[][] getData() {
		return new Object[][] {{"bbcde","2234"},{"bcdef","2235"},{"bdefg","2236"}};
	}

}
