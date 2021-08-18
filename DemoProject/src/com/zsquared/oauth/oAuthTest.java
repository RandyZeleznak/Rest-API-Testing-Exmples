package com.zsquared.oauth;

import static io.restassured.RestAssured.given;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class oAuthTest {
	
	public static void main(String [] args) throws InterruptedException {
		
		RestAssured.useRelaxedHTTPSValidation();
		
	/*	System.setProperty("webdriver.chrome.driver", "C:\\Chromedriver\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("https://accounts.google.com/o/oauth2/v2/auth&%20%20client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&%20%20response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php");
		driver.findElement(By.cssSelector("input['type=email']")).sendKeys("srinath19830");
		driver.findElement(By.cssSelector("input['type=email']")).sendKeys(Keys.ENTER);
		Thread.sleep(3000);
		driver.findElement(By.cssSelector("input['type=password']")).sendKeys("password");
		driver.findElement(By.cssSelector("input['type=password']")).sendKeys(Keys.ENTER);
		Thread.sleep(4000);*/
		
		String url ="https://rahulshettyacademy.com/getCourse.php?state=verifyfjdss&code=4%2FvAHBQUZU6o4WJ719NrGBzSELBFVBI9XbxvOtYpmYpeV47bFVExkaxWaF_XR14PHtTZf7ILSEeamywJKwo_BYs9M&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&session_state=0c32992f0d47e93d273922018ade42d1072b9d1f..a35c&prompt=none#";
	//	String url = driver.getCurrentUrl();
		String partialUrl = url.split("code=")[1];
		String codeUrl = partialUrl.split("&scope=")[0];
		System.out.println(codeUrl);
		
		
		
		String accessTokenResponse = given().urlEncodingEnabled(false)
			.queryParams("code","")
			.queryParams("client_id","692183193103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
			.queryParams("client_secret","erZOWM9g3UtwNRj340YYaK_W")
			.queryParams("redirect_uri","https://rahulshettyacademy.com/getCourse.php")
			.queryParams("grant_type","authorization_code")
			 .queryParams("state", "verifyfjdss")

             .queryParams("session_state", "ff4a89d1f7011eb34eef8cf02ce4353316d9744b..7eb8")

          // .queryParam("scope", "email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email")

            

             .queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
			.when().log().all()
			.post("https://googleapis.com/oauth2/v4/token").asString();
		
		JsonPath js = new JsonPath(accessTokenResponse);
		String accessToken = js.get("access_token");
		
		
		String response = given().queryParam("access_token", accessToken)
		.when().log().all()
		.get("https://rahulshettyacademy.com/getCourse.php").asString();
		
		System.out.println(response);
	}

}
