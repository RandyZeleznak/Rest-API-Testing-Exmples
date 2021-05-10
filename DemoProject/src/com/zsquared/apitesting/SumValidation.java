package com.zsquared.apitesting;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.zsquared.apitesting.files.Payload;

import io.restassured.path.json.JsonPath;

public class SumValidation {
	@Test
	public void sumOfCourses() {
		
		int sum = 0;
		JsonPath js = new JsonPath(Payload.coursePrice());

		int count = js.getInt("courses.size()");
		
		for(int a = 0; a < count; a++) {
			int price = js.get("courses["+a+"].price");
			int copies = js.get("courses["+a+"].copies");
			int amount = price * copies;
			System.out.println("Amount = " +amount);
			sum = sum + amount;
		}
		System.out.println("Sum = "+sum);
		int purchaseAmount = js.getInt("dashboard.purchaseAmount");
		Assert.assertEquals(sum,  purchaseAmount);
		
	}

}
