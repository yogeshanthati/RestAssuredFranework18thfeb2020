package com.qa.restassured.test;

import java.util.concurrent.TimeUnit;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.qa.restassured.base.TestBase;
import com.qa.restassured.utilities.*;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;

public class Tc002_RestAssured_POST_Test extends TestBase {
	String empName = RestUtils.Name();
	String job=RestUtils.Job();

	@SuppressWarnings("unchecked")
	@BeforeClass
	void createEmployee() throws InterruptedException {
		RestAssured.baseURI = prop.getProperty("URL");
		httpRequest = RestAssured.given();
		JSONObject requestParams = new JSONObject();
		requestParams.put("name", empName);
		requestParams.put("job", job);
		httpRequest.header("Content-Type", "application/json");
		httpRequest.body(requestParams.toJSONString());
		httpResponse = httpRequest.request(Method.POST, prop.getProperty("postServiceURL"));
		Thread.sleep(5000);
	}

	@Test(priority = 0)
	void checkResposeBody() {
		String responseBody = httpResponse.getBody().asString();
		System.out.println(responseBody);
		JsonPath JsonpathEvaluator=httpResponse.jsonPath();
		//createdAt ,id
		String createdAt=JsonpathEvaluator.get("createdAt");
		String id=JsonpathEvaluator.get("id");
		System.out.println("data created at "+createdAt);
		System.out.println("ID is "+id);
		Assert.assertEquals(responseBody.contains(empName), true);
		Assert.assertEquals(responseBody.contains(job), true);
	}

	@Test(priority = 1)
	public void checkStatusCode() {
		int statusCode = httpResponse.statusCode();
		System.out.println(statusCode);
		Assert.assertEquals(statusCode, 201);
	}

	@Test(priority = 2)
	public void checkStatusLine() {
		String statusLine = httpResponse.getStatusLine();
		System.out.println(statusLine);
		Assert.assertEquals(statusLine, "HTTP/1.1 201 Created");
	}

	@Test(priority = 3)
	public void checkHeaders() {
		// content type
		String headerContentType = httpResponse.getHeader("Content-Type");
	    Assert.assertEquals(headerContentType,"application/json; charset=utf-8");
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
