package com.puma.pageObjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;




public class HomePage {
	 WebDriver driver;

	@FindBy(how = How.XPATH, using = "//*[@id='men-subnav']/div[1]/div[1]/div[2]/ul[1]/li[2]/a[1]")
	public WebElement link_Running;
	
	
	@FindBy(how = How.XPATH, using = "//div[@id='header-nav']/ul[1]/li[1]/a[1]")
	public WebElement link_Men;
	
	
	@FindBy(how = How.XPATH, using = "id('mp-pusher')/div[1]/div[4]/div[2]/div[2]/div[2]/div[1]/ul[1]/li[2]/a[1]")
	public WebElement link_shoes2;
	
	@FindBy(how = How.XPATH, using = "//div[@class='add-to-cart-buttons']/button/span/span")
	public WebElement buttonCart;
	
	@FindBy(how = How.XPATH, using = "//select[@id='attribute180']")
	public WebElement selectDrop;
	
	@FindBy(how = How.XPATH, using = "//div[@class='product-name']/span")
	public WebElement productName;
	
	
	
	@FindBy(how = How.XPATH, using = "//span[@class='regular-price']")
	public WebElement price;
	
	public HomePage(WebDriver driver) {
		this.driver = driver;
	}
	public String getTitle(){
		return driver.getTitle();
		
	}
	
	public Map<String,String> addProductToCart() throws InterruptedException{
		
		Map<String,String> map = new HashMap<String, String>();
		WebDriverWait wait = new WebDriverWait(driver, 20);
		
		Actions action = new Actions(driver);
		action.moveToElement(link_Men).pause(3000).moveToElement(link_Running).pause(3000).click().build().perform();

		WebDriverWait wait2 = new WebDriverWait(driver, 20);
		link_shoes2.click();
		
		
		ArrayList<String> tabs2 = new ArrayList<String> (driver.getWindowHandles());
	    driver.switchTo().window(tabs2.get(1));
	 //   driver.close();
	    
	    
	    
	    driver.findElement(By.xpath("id('size_label')/div[1]/div[2]/span[1]")).click();
		
		
		List <WebElement> elementCount = driver.findElements(By.xpath("//li[starts-with(@class,'option')]"));
		int iSize = elementCount.size();
		boolean flag=false;
		Map<String,Boolean> mp = new HashMap<String,Boolean>();

		Iterator<WebElement> i = elementCount.iterator();
		while(i.hasNext()) {
			
		    WebElement row = i.next();
		    System.out.println(row.getText());
		    if(!row.getText().matches("[0-9]+")){
		    	System.out.println("true");
		    	continue;
		    }
		    
		    
		    if(mp.containsKey(row.getText())){
		    	continue;
		    }
		    mp.put(row.getText(), true);
		    row.click();
	
		    Thread.sleep(5000);
		

			if(buttonCart.getText().equalsIgnoreCase("Out of stock")){
				System.out.println("Out of stock");
				flag=false;
			}else if(buttonCart.getText().equalsIgnoreCase("Add to Cart")){
				flag=true;
				//map.put("size", sValue);
				break;
			}
			driver.findElement(By.xpath("id('size_label')/div[1]/div[2]/span[1]")).click();
			
		}
		
		if(flag){
			String id = price.getAttribute("id");
			WebElement pr = driver.findElement(By.xpath("//span[@id='"+id+"']/span[1]"));
			
			System.out.println(productName.getText());
			System.out.println(pr.getText());
			map.put("pname", productName.getText());
			map.put("price", pr.getText());
			buttonCart.click();
		}else{
			System.out.println("No stock for any size");
			
		}
		return map;
		
	}
	
	
 public boolean verifyProduct(Map<String,String> map){
		 
	
		 String prdname = driver.findElement(By.xpath("//td[@class='product-cart-image']/a")).getAttribute("title");
		 String pri=driver.findElement(By.xpath("//td[@class='product-cart-total last']//span[@class='price']")).getText();
		 System.out.println(prdname);
		 System.out.println(pri);
		 if(map.get("pname").equalsIgnoreCase(prdname) && map.get("price").equalsIgnoreCase(pri)){
			 System.out.println("true");
			 return true;
		 }
		
		 
		 return false;
	 }
	

}
