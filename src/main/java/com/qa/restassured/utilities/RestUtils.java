package com.qa.restassured.utilities;

import org.apache.commons.lang3.RandomStringUtils;

public class RestUtils {

	
	public static String Name() {
		String generatedString = RandomStringUtils.randomAlphabetic(1);
		return ("John"+generatedString);
	}
	public static String Job() {
		String generatedString = RandomStringUtils.randomAlphabetic(1);
		return ("John"+generatedString);
	}
	
}
