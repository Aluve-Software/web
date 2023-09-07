package tech.aluvesoftware;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;

public class TestContext {
    private WebDriver driver;

    /*
    *@param implicitWait Wait time in seconds
     */
    public TestContext(int implicitWait) {
        driver = getDriver();
        driver.manage().timeouts().implicitlyWait((Duration.ofSeconds(implicitWait)));
    }


    private WebDriver createDriver(){
        switch (System.getProperty("browser").toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--remote-allow-origins=*");
                //the sandbox removes unnecessary privileges from the processes that don't need them in Chrome, for security purposes. Disabling the sandbox makes your PC more vulnerable to exploits via webpages, so Google don't recommend it.
                options.addArguments("--no-sandbox");
                //"--disable-dev-shm-usage" Only added when CI system environment variable is set or when inside a docker instance. The /dev/shm partition is too small in certain VM environments, causing Chrome to fail or crash.
                options.addArguments("--disable-dev-shm-usage");
                if(!System.getProperty("os.name").contains("Windows")){
                    options.addArguments("--headless");
                }
                driver = new ChromeDriver(options);
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;
            default:
                break;
        }
        driver.manage().window().maximize();
        return driver;
    }

    public WebDriver getDriver() {
        if (driver == null) driver = createDriver();
        return driver;
    }

}
