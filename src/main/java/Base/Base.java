package Base;

import java.time.Duration;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Base {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public void setUp() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();

        // Page load strategy — EAGER
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);

        // ⭐ SSL accept — সবচেয়ে গুরুত্বপূর্ণ
        options.setAcceptInsecureCerts(true);

        // Basic options
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");

        // SSL/Certificate errors ignore
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("--ignore-ssl-errors");
        options.addArguments("--allow-insecure-localhost");
        options.addArguments("--disable-web-security");
        options.addArguments("--allow-running-insecure-content");

        // ⭐ Renderer timeout সমাধানের জন্য
        options.addArguments("--disable-features=NetworkService");
        options.addArguments("--disable-background-networking");
        options.addArguments("--disable-sync");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-infobars");
        options.addArguments("--no-first-run");
        options.addArguments("--no-default-browser-check");
        options.addArguments("--disable-default-apps");
        options.addArguments("--disable-component-update");
        options.setPageLoadStrategy(PageLoadStrategy.NONE);

        
        // Headless mode
        String headless = System.getProperty("headless", "false");
        if (headless.equals("true")) {
            options.addArguments("--headless=new");
            System.out.println("Running in HEADLESS mode");
        } else {
            System.out.println("Running in NORMAL mode");
        }

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        // ⭐ Timeout সেট করুন
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(30));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    public void tearDown() {
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception e) {
                System.out.println("Error closing browser: " + e.getMessage());
            }
            System.out.println("Browser closed");
        }
    }
}