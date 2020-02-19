package com.qa.restassured.test;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.qa.restassured.base.TestBase;
import io.restassured.RestAssured;
import io.restassured.http.Method;

public class Tc004_RestAssured_DELETE_Test extends TestBase {

	@BeforeClass
	public void deleteEmployee() throws InterruptedException {
		RestAssured.baseURI = prop.getProperty("SingleURL");
		httpRequest = RestAssured.given();
		httpResponse = httpRequest.request(Method.DELETE,prop.getProperty("deleteServiceURL")); // Pass ID to delete record
		Thread.sleep(3000);
	}

	@Test(priority = 0)
	void checkResposeBody() {
		String httpResponseBody = httpResponse.getBody().asString();
		System.out.println(httpResponseBody);
		Assert.assertEquals(httpResponseBody,"");

	}

	@Test(priority = 1)
	void checkStatusCode() {
		int statusCode = httpResponse.getStatusCode();
		// Gettng status code
		System.out.println(statusCode);
		Assert.assertEquals(statusCode, 204);
	}

	@Test(priority = 2)
	void checkstatusLine() {
		String statusLine = httpResponse.getStatusLine(); // Gettng status Line
		System.out.println(statusLine);
		Assert.assertEquals(statusLine, "HTTP/1.1 204 No Content");

	}

	@Test(priority = 3)
	void checkContentType() {
		String  contentType = httpResponse.header("Content-Length");
		System.out.println(contentType);
		Assert.assertEquals(contentType, "0");
	}

	@Test(priority = 4)
	void checkserverType() {
		String serverType = httpResponse.header("Server");
		Assert.assertEquals(serverType, "cloudflare");
	}

}
