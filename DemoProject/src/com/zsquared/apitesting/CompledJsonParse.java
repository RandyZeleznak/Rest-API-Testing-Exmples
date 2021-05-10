package com.zsquared.apitesting;


import com.zsquared.apitesting.files.Payload;

import io.restassured.path.json.JsonPath;

public class CompledJsonParse {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		JsonPath js = new JsonPath(Payload.coursePrice());

		int count = js.getInt("courses.size()");
		System.out.println("Count : " +count);
		int totalAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println("Total Amount : " +totalAmount);
		
		String titleFirst = js.get("courses[0].title");
		System.out.println("Title FIrst Course : " +titleFirst);
		
		// Print ALL courses and there respective prices
		
		for(int a = 0; a < count; a++) {
			String title = js.get("courses["+a+"].title");
			System.out.println("Title : "+title);
			System.out.println("Price = " +js.get("courses["+a+"].price").toString());
		}
		
		System.out.println("Print number of copies sold of RPA course");
		
		for(int a = 0; a < count; a++) {
			
			String courseTitle = js.get("courses["+a+"].title");
			if(courseTitle.equalsIgnoreCase("RPA")) {
				int copies = js.getInt("courses["+a+"].copies");
				System.out.println("Number of Copies : "+copies);
				break;
			}
			
			
			
		}
		
		
		
		
	}

}
