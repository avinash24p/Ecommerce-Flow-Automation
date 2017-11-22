
package com.puma.TestCase;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.puma.TestBase.Log;
import com.puma.TestBase.TestBaseSetup;
import com.puma.TestBase.TestContext;
import com.puma.TestBase.TestFailureCapture;
import com.puma.pageObjects.HomePage;

@Listeners(TestContext.class)
public class testAddProduct extends TestBaseSetup{
	private WebDriver driver;
	private HomePage homePge;
		
	@BeforeClass
	private void BeforeClass(){
		driver = getDriver();	
		homePge = PageFactory.initElements(driver, HomePage.class);	
		new Log();
	}

	
	
	
	@Test(testName = "Test Cart page")
	public void testAddToCart() throws InterruptedException{
		
		Assert.assertEquals(homePge.getTitle(), "Buy Sports T-Shirts, Tracks, Running Shoes and Accessories Online - in.puma.com");
		
		Map<String,String> map = new HashMap<String, String>();
		map=homePge.addProductToCart();
		
		if(map.size()==0){
			throw new SkipException("Skipping this exception");
		}
		
		Assert.assertTrue(homePge.verifyProduct(map), "Product details verified");
		
	}
	
	
	
	
	@AfterMethod
	public void tearDown(ITestResult result) {

		if (ITestResult.FAILURE == result.getStatus()) {
			TestFailureCapture.takeScreenShot(result.getTestName(), driver);
		}
	}
	
}
