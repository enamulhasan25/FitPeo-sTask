package org.fitpeo;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

public class RunTask extends BrowserFactory {

    @Test
    public void navigateToFitPeoHomePage() throws Exception {
        WebDriver driver = getDriver();
        Thread.sleep(10000);
    }
}