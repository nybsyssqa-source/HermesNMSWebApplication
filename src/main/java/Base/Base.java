package Base;

import java.time.Duration;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Base {

	public static void main(String[] args) throws InterruptedException {

		ChromeOptions options = new ChromeOptions();
		WebDriverManager.chromedriver().setup();
		// GitHub Actions-এ headless mode
if (System.getProperty("headless") != null) {
    options.addArguments("--headless=new");
    options.addArguments("--no-sandbox");
    options.addArguments("--disable-dev-shm-usage");
    options.addArguments("--disable-gpu");
    options.addArguments("--window-size=1920,1080");
}

WebDriver driver = new ChromeDriver(options);

        ChromeDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://10.177.89.59/console/login");
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
       // ============ Details Button ============
        WebElement detailsBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='details-button']")));
        detailsBtn.click();

        // ============ Proceed Link ============
        WebElement proceedLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='proceed-link']")));
        proceedLink.click();
        
        Thread.sleep(2000);
        
        
        WebElement usernameField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='username']")));
        usernameField.sendKeys("admin");
        
       
        
       WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='password']")));

       // তারপর sendKeys চালান
       passwordField.sendKeys("admin");
        	
       driver.findElement(By.xpath("//span[normalize-space()='Sign in']")).click();
       
       WebElement dashboardElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='root']/div/div/header/div[1]/span[2]/strong")));

           String dashboard = dashboardElement.getText().trim();
           System.out.println("Dashboard Text: " + dashboard);
           System.out.println("✅ Test Pass");
           
        // ============ Main Menu Click ============
           WebElement planningMenu = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'Planning')]")));
           planningMenu.click();

           // ============ Sub Menu Click ============
           WebElement subMenuItem = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'Planning')]")));
           subMenuItem.click();

           System.out.println("✅ Planning menu navigated");
           
           // ১. Device Model ইনপুট ফিল্ডে ক্লিক করুন
           WebElement modelInput = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//label[text()='Device model']/following::input[1]")));
           modelInput.click();
           
           // ৩. ড্রপডাউন থেকে সঠিক অপশনে ক্লিক করুন
           WebElement option = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'NL 2301')]")));
           option.click();

           System.out.println("✅ Device Model selected: NL 2301 · 4G");
           
           
           
           //driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/div/main/div/div[2]/div/div[1]/div/div/span/span[2]"));


	}

}
