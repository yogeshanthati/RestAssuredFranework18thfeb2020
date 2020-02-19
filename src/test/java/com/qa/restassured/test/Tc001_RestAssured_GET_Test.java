package com.qa.restassured.test;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.restassured.base.TestBase;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;

public class Tc001_RestAssured_GET_Test extends TestBase {

	@BeforeClass
	public void SetUpGET() {
		RestAssured.baseURI = prop.getProperty("URL");
		httpRequest = RestAssured.given();
		httpResponse = httpRequest.request(Method.GET, prop.getProperty("GetServiceURL"));

	}

	@Test(priority = 0)
	public void getAllEmployeeRecords() {
		
		// Get json path and evaluate single value response
		JsonPath jsonpathEvaluator = httpResponse.jsonPath();
		
		//fetching single value
		String statusresponse = jsonpathEvaluator.getString("total_pages");
		Assert.assertEquals(statusresponse, "2");
		
		//fetching array using index
		System.out.println(jsonpathEvaluator.get("data[0].last_name"));
		
		//another way of fetching array  value
		HashMap<String,String> hm=jsonpathEvaluator.get("data[0]");
		if(hm.containsKey("last_name")) {
			System.out.println(hm.get("last_name"));
			}
		String responseString = httpResponse.getBody().asString();
   		System.out.println(responseString);
		Assert.assertTrue(responseString != null);
		
	}

	@Test(priority = 1)
	public void checkStatusCode() {
		int statusCode = httpResponse.getStatusCode();
		System.out.println(statusCode);
		Assert.assertEquals(statusCode, 200);
	}

	@Test(priority = 2)
	public void checkStatusLine() {
		String statusLine = httpResponse.getStatusLine();
		System.out.println(statusLine);
		Assert.assertEquals(statusLine, "HTTP/1.1 200 OK");
	}

	@Test(priority = 3)
	public void checkHeaders() {
		// content type
		String headerContentType = httpResponse.getHeader("Content-Type");
		System.out.println(headerContentType);
		Assert.assertEquals(headerContentType, "application/json; charset=utf-8");
	
		// content encoding
		String contentEncoding = httpResponse.getHeader("Content-Encoding");
		Assert.assertEquals(contentEncoding, "gzip");

	}

	@Test(priority = 4)
	public void checkResponseTime() {

		long responseTime = httpResponse.getTimeIn(TimeUnit.MILLISECONDS);
		System.out.println("Response time.............> " + responseTime + "milliSeconds");
	}

	@Test(priority = 5)
	void checkCookies() {

		String cookie = httpResponse.getCookie("__cfduid");
		System.out.println(cookie);

	}

	@Test(priority = 6)
	void checkserverType() {
		String serverType = httpResponse.getHeader("Server");
		Assert.assertEquals(serverType, "cloudflare");
	}

}
