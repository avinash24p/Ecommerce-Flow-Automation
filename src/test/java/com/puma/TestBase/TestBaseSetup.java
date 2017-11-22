package com.puma.TestBase;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import com.jayway.jsonpath.JsonPath;

public abstract class TestBaseSetup {

	private WebDriver driver;
	public static String mystring []= new String [10000];


	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver() throws IOException {



		List<String> list = new ArrayList<String>();
		list = jsonSimplePath();
		String appURL = list.get(0);
		String browserType = list.get(1);

		switch (browserType) {
		case "chrome":
			driver = initChromeDriver(appURL);
			break;
		case "firefox":
			driver = initFirefoxDriver(appURL);
			break;
		default:
			System.out.println("browser : " + browserType + " is invalid, Launching Firefox as browser of choice..");
			driver = initFirefoxDriver(appURL);
		}
	}



	private static WebDriver initChromeDriver(String appURL) {
		System.out.println("Launching google chrome with new profile..");
		System.setProperty("webdriver.chrome.driver", "Repository/chromedriver");
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.navigate().to(appURL);
		return driver;
	}

	private static WebDriver initFirefoxDriver(String appURL) {
		System.out.println("Launching Firefox browser..");
		WebDriver driver;
		System.setProperty("webdriver.gecko.driver", "Repository"+File.separator+"geckodriver");
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
		driver.navigate().to(appURL);
		return driver;
	}



	public List<String> jsonSimplePath() throws IOException {

		String env = "";

		env = "stage";

		System.out.println("\n======================================");
		System.out.println("\n\n\n\n\n");

		List<String> results = new ArrayList<String>();
		String sPath = new java.io.File(".").getCanonicalPath();
		System.out.println("Path: " + sPath);
		File jsonFile = new File(sPath + File.separator + "config.json");

		System.out.println("Reading Environment variables from json file");

		System.setProperty("ElementLocatorTimeOut", (String) JsonPath.read(jsonFile, "$.selenium.ElementLocatorTimeOut"));

		results.add((String) JsonPath.read(jsonFile, "$.environments." + env + ".domain"));
		results.add((String) JsonPath.read(jsonFile, "$.environments." + env + ".browser"));

		return results;

	}

	@BeforeClass
	public void initializeTestBaseSetup() {
		try {
			setDriver();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@AfterMethod
	public void tearDown(ITestResult result) {

		if (ITestResult.FAILURE == result.getStatus()) {
			TestFailureCapture.takeScreenShot(result.getName(), driver);
		}

	}

	@AfterClass
	public void tearDown() {
		driver.quit();
	}
}