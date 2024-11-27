package org.fitpeo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static java.lang.System.out;

public class RunTask {

    // for accessing under the main method making as static
    public static void adjustingSlider(WebDriver driver, WebElement slider, WebElement valueDisplay, int targetValue) throws InterruptedException {
        int currentValue = Integer.parseInt(valueDisplay.getAttribute("value"));
        if (currentValue == targetValue) {
            System.out.println("Slider is already at the target value: " + targetValue);
        }
    }

    public static void selectingCPTCode(WebDriver driver, String cptCodeName) {
        try {
            String xpath = String.format("//div/p[contains(text(),'%s')]//following-sibling::label/span/input", cptCodeName);
            WebElement checkbox = driver.findElement(By.xpath(xpath));
            if (!checkbox.isSelected()) {
                checkbox.click();
                System.out.println(cptCodeName + " selected.");
            } else {
                System.out.println(cptCodeName + " was already selected.");
            }
        } catch (Exception e) {
            System.out.println("Failed to select CPT code: " + cptCodeName);
        }
    }

    public static void main(String[] args) throws Exception {

        WebDriver driver;

        // Navigating to FitPeo's Homepage
        WebDriverManager.chromedriver().setup();
        ChromeOptions opt = new ChromeOptions();
        opt.addArguments("--start-maximized");
        driver = new ChromeDriver(opt);
        Thread.sleep(5000);
        driver.navigate().to("https://fitpeo.com");
        out.println("Successfully Landed to the home page of FitPeo");


        //explicit wait
        //WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        try {
            //Navigating to Revenue calculator Page
            if (driver.findElement(By.xpath("//button[contains(@class,'MuiIconButton')]")).isDisplayed()) {
                driver.findElement(By.xpath("//span[contains(text(),'Revenue Calculator')]")).click();
            } else {
                driver.findElement(By.xpath("//div[contains(text(),'Revenue Calculator')]")).click();
            }
            System.out.println("Navigated to the Revenue Calculator Page");
            Thread.sleep(10000);


            //Scrolling down to the slider
            WebElement sliderAtRevenuePage = driver.findElement(By.xpath("//input[@type='range']"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", sliderAtRevenuePage);
            if (sliderAtRevenuePage.isDisplayed()) {
                System.out.println("Slider is visible.");
            } else {
                System.out.println("Slider is not visible.");
            }

            // Adjusting the slider
            WebElement sliderValueField = driver.findElement(By.xpath("//input[@type='range']"));
            adjustingSlider(driver, sliderAtRevenuePage, sliderValueField, 820);

            // Updating the text field again with the value of 560
            sliderValueField.clear();
            sliderValueField.sendKeys("560");
            System.out.println("Updated the text field to 560 value latest.");

            int updatedValue = Integer.parseInt(sliderValueField.getAttribute("value"));
            if (updatedValue == 560) {
                System.out.println("Slider value updated to 560 inside input text field.");
            } else {
                System.out.println("Failed to update slider value to input text field.");
            }


            //selecting CPT grids
            selectingCPTCode(driver, "CPT-99453");
            selectingCPTCode(driver, "CPT-99457");
            selectingCPTCode(driver, "CPT-99474");
            selectingCPTCode(driver, "CPT-G2065");
            Thread.sleep(10000);

            System.out.println("Selected all required CPT codes.");

            //Validating the Total Recurring Reimbursement for all Patients Per Month:
            String expectedHeader = "Total Recurring Reimbursement for all Patients Per Month:\n$110700";
            WebElement headerElement = driver.findElement(By.xpath("(//p[@class='MuiTypography-root MuiTypography-body1 inter css-1bl0tdj'])[4]"));
            String actualHeader = headerElement.getText();

            if (expectedHeader.equals(actualHeader)) {
                System.out.println("Total Recurring Reimbursement validated successfully: $110700.");
            } else {
                System.out.println("Validation failed. Expected: $110700, but found anything: " + actualHeader);
            }
        } catch (Exception e) {
            System.out.println("Something went wrong in the revenue calculator page: " + e.getMessage());
        } finally {
            driver.quit();
            System.out.println("Test execution completed.");
        }
    }
}