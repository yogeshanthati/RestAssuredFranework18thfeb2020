package com.qa.restassured.test;

import java.util.concurrent.TimeUnit;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.qa.restassured.base.TestBase;
import com.qa.restassured.utilities.RestUtils;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;

public class Tc003_RestAssured_PUT_Test extends TestBase {

	String empName = RestUtils.Name();
	String job=RestUtils.Job();

	@SuppressWarnings("unchecked")
	@BeforeClass
	void UpdateEmployee() throws InterruptedException {
		RestAssured.baseURI = prop.getProperty("URL");
		httpRequest = RestAssured.given();
		JSONObject requestParams = new JSONObject();
		requestParams.put("name", empName);
		requestParams.put("job", job);
		httpRequest.header("Content-Type", "application/json");
		httpRequest.body(requestParams.toJSONString());
		httpResponse = httpRequest.request(Method.PUT, prop.getProperty("putServiceURL"));
		Thread.sleep(5000);
	}

	@Test(priority = 0)
	void checkResposeBody() {
		String responseBody = httpResponse.getBody().asString();
		JsonPath JsonpathEvaluator=httpResponse.jsonPath();
		String updatedAt=JsonpathEvaluator.get("updatedAt");
		System.out.println("data created at "+updatedAt);	
		System.out.println(responseBody);
		Assert.assertEquals(responseBody.contains(empName), true);
		Assert.assertEquals(responseBody.contains(job), true);
	}

	@Test(priority = 1)
	public void checkStatusCode() {
		int statusCode = httpResponse.statusCode();
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
	    Assert.assertEquals(headerContentType,"application/json; charset=utf-8");
		String contentEncoding = httpResponse.header("Content-Encoding");
		System.out.println(contentEncoding);
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
		String serverType = httpResponse.header("Server");
		Assert.assertEquals(serverType, "cloudflare");
	}

}
