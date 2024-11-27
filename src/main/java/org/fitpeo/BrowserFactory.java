package org.fitpeo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import static java.lang.System.out;

public class BrowserFactory {
    public WebDriver driver;

    public WebDriver getDriver() {
        return driver;  // This method returns the initialized WebDriver
    }

    @BeforeMethod
    public void browsersSetUp() {
        String browserName = ConfigReader.getProperty("browserName");
        switch (browserName.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions opt = new ChromeOptions();
                opt.addArguments("--start-maximized");
                driver = new ChromeDriver(opt);
                driver.get(ConfigReader.getProperty("FitPeoHomepage"));
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions opt1 = new EdgeOptions();
                opt1.addArguments("--start-maximized");
                driver = new EdgeDriver(opt1);
                driver.navigate().to(ConfigReader.getProperty("browserName"));
                driver.get(ConfigReader.getProperty("FitPeoHomepage"));
                break;
            default:
                out.println(browserName + " is not a valid browser");
        }
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}