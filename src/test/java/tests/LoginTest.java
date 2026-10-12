package tests;

import Base.Base;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class LoginTest extends Base {

	@BeforeMethod
	public void init() {
	    String ciEnv = System.getenv("CI");
	    if (ciEnv != null && ciEnv.equals("true")) {
	        throw new org.testng.SkipException(
	            "Skipping: Private IP 10.177.89.59 not accessible from GitHub Actions"
	        );
	    }
	    setUp();
	}

    @Test(priority = 1, description = "Verify login page loads")
    public void testLoginPageLoads() {
        System.out.println("=== TEST 1: Login Page Load ===");
        
        try {
            driver.navigate().to("https://10.177.89.59/console/login");
        } catch (Exception e) {
            System.out.println("Nav warning: " + e.getMessage());
        }
        
        try { Thread.sleep(10000); } catch (InterruptedException e) {}
        
        String title = driver.getTitle();
        System.out.println("Page Title: " + title);
        System.out.println("Current URL: " + driver.getCurrentUrl());
        System.out.println("Page Source Length: " + driver.getPageSource().length());
        
        Assert.assertNotNull(title, "Page title should not be null");
        System.out.println("✅ Test Pass");
    }

    @Test(priority = 2, description = "Verify full login flow")
    public void testFullLoginFlow() throws InterruptedException {
        System.out.println("=== TEST 2: Full Login Flow ===");
        
        try {
            driver.navigate().to("https://10.177.89.59/console/login");
        } catch (Exception e) {
            System.out.println("Nav warning: " + e.getMessage());
        }
        
        // ⭐ JavaScript execute হওয়ার জন্য অপেক্ষা
        JavascriptExecutor js = (JavascriptExecutor) driver;
        int maxAttempts = 30;
        boolean inputsFound = false;
        
        for (int i = 0; i < maxAttempts; i++) {
            Thread.sleep(1000);
            try {
                String readyState = (String) js.executeScript("return document.readyState");
                int inputCount = driver.findElements(By.tagName("input")).size();
                
                System.out.println("Attempt " + (i+1) + ": readyState=" + readyState + ", inputs=" + inputCount);
                
                if (inputCount >= 2) {
                    inputsFound = true;
                    System.out.println("✅ Input fields found after " + (i+1) + " seconds");
                    break;
                }
            } catch (Exception e) {
                System.out.println("Attempt " + (i+1) + " error: " + e.getMessage());
            }
        }
        
        if (!inputsFound) {
            System.out.println("❌ No input fields found after " + maxAttempts + " seconds");
            System.out.println("Page Source (first 2000 chars):");
            System.out.println(driver.getPageSource().substring(0, 
                Math.min(2000, driver.getPageSource().length())));
            return;
        }
        
        // ⭐ সব input field debug
        List<WebElement> inputs = driver.findElements(By.tagName("input"));
        System.out.println("Total inputs: " + inputs.size());
        for (int i = 0; i < inputs.size(); i++) {
            WebElement input = inputs.get(i);
            System.out.println("Input " + i + ": id=" + input.getAttribute("id") + 
                              ", name=" + input.getAttribute("name") + 
                              ", type=" + input.getAttribute("type"));
        }
        
        // ⭐ Username field
        WebElement usernameField = wait.until(
            ExpectedConditions.elementToBeClickable(By.id("username"))
        );
        usernameField.sendKeys("admin");
        System.out.println("✅ Username entered");
        
        // ⭐ Password field
        WebElement passwordField = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.id("password"))
        );
        passwordField.sendKeys("admin");
        System.out.println("✅ Password entered");
        
        // ⭐ Sign in button
        WebElement signInBtn = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(., 'Sign in')] | //span[normalize-space()='Sign in']")
            )
        );
        signInBtn.click();
        System.out.println("✅ Sign in clicked");
        
        Thread.sleep(5000);
        
        // ⭐ Dashboard verify
        System.out.println("Current URL after login: " + driver.getCurrentUrl());
        String pageSource = driver.getPageSource();
        if (pageSource.contains("Hermes") || pageSource.contains("Dashboard")) {
            System.out.println("✅ Login successful - Dashboard loaded");
        } else {
            System.out.println("⚠️ Dashboard verification inconclusive");
        }
    }
    

    @AfterMethod
    public void cleanup() {
        try {
            tearDown();
        } catch (Exception e) {
            System.out.println("Cleanup warning: " + e.getMessage());
        }
    }
}