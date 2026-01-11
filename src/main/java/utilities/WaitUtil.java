package utilities;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitUtil {
    public static void waitForVisible(WebDriver driver, WebElement element, int timeout) {
        new WebDriverWait(driver, Duration.ofSeconds(timeout))
                .until(ExpectedConditions.visibilityOf(element));
    }

    public static void waitForClickable(WebDriver driver, WebElement element, int timeout) {
        new WebDriverWait(driver, Duration.ofSeconds(timeout))
                .until(ExpectedConditions.elementToBeClickable(element));
    }

    public static void waitForPageLoad(WebDriver driver, int timeout) {
        new WebDriverWait(driver, Duration.ofSeconds(timeout)).until(
            webDriver -> ((JavascriptExecutor) webDriver)
                    .executeScript("return document.readyState").equals("complete"));
    }
}

    
    
