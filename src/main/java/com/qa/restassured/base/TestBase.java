package com.qa.restassured.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Properties;

import org.testng.annotations.BeforeClass;

import com.qa.restassured.utilities.TestParameters;

public class TestBase extends TestParameters{
	
	@BeforeClass
	public static void setUP() throws MalformedURLException{
		try {
			prop = new Properties();
			FileInputStream ip = new FileInputStream("src/main/resources/testconfig.properties");
			prop.load(ip);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		

    }
}
