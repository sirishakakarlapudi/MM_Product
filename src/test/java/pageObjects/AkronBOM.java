package pageObjects;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class AkronBOM extends BasePage {
    public AkronBOM(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//ng-multiselect-dropdown[@formcontrolname='routeCodeId']")
    WebElement drpdwn_productcode;

    public void selProductCode(String productcode) {
        waitForElementandClick(drpdwn_productcode);
        WebElement productCodeXpath = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//ng-multiselect-dropdown[@formcontrolname='routeCodeId']//li[normalize-space()='"
                        + productcode + "']")));
        productCodeXpath.click();
    }

    @FindBy(xpath = ("//input[@formcontrolname='location']"))
    WebElement txt_location;

    public void location(String location) {
        txt_location.clear();
        waitAndSendKeys(txt_location, location);
    }

    @FindBy(xpath = ("//input[@formcontrolname='batchSize']"))
    WebElement txt_batchsize;

    public void batchSize(String batchSize) {
        txt_batchsize.clear();
        waitAndSendKeys(txt_batchsize, batchSize);
    }

    @FindBy(xpath = "//ng-multiselect-dropdown[@formcontrolname='batchSizeUOM']")
    WebElement drpdwn_batchsizeuom;

    public void selBatchSizeUOM(String batchsizeuom) {
        waitForElementandClick(drpdwn_batchsizeuom);
        WebElement batchSizeUOMXpath = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//ng-multiselect-dropdown[@formcontrolname='batchSizeUOM']//li[normalize-space()='"
                        + batchsizeuom + "']")));
        batchSizeUOMXpath.click();
    }

    @FindBy(xpath = ("//input[@formcontrolname='yieldMinRange']"))
    WebElement txt_yieldminrange;

    public void yieldMinRange(String yieldminrange) {
        txt_yieldminrange.clear();
        waitAndSendKeys(txt_yieldminrange, yieldminrange);
    }

    @FindBy(xpath = ("//input[@formcontrolname='yieldMaxRange']"))
    WebElement txt_yieldmaxrange;

    public void yieldMaxRange(String yieldmaxrange) {
        txt_yieldmaxrange.clear();
        waitAndSendKeys(txt_yieldmaxrange, yieldmaxrange);
    }

    @FindBy(xpath = ("//input[@formcontrolname='yieldTarget']"))
    WebElement txt_yieldtarget;

    public void yieldTarget(String yieldtarget) {
        txt_yieldtarget.clear();
        waitAndSendKeys(txt_yieldtarget, yieldtarget);
    }

    @FindBy(xpath = "//p-checkbox[@inputid='isBomCheckBox1']")
    WebElement standardbom_yes;

    @FindBy(xpath = "//p-checkbox[@inputid='isBomCheckBox2']")
    WebElement standardbom_no;

    public void checkStandardBom(String standardbom) {
        if (standardbom.equalsIgnoreCase("Yes")) {
            waitForElementandClick(standardbom_yes);
        } else {
            waitForElementandClick(standardbom_no);
        }
    }

    @FindBy(xpath = "//span[@class='dropdown-multiselect__caret']")
    List<WebElement> drpdwn_materialcode;

    public void selMaterialCode(String materialcode) {
        clickLatestElement(drpdwn_materialcode);

        // Find all matching options and click the VISIBLE one
        By optionLocator = By.xpath("//li[normalize-space()='" + materialcode + "']");
        wait.until(ExpectedConditions.presenceOfElementLocated(optionLocator));

        List<WebElement> options = driver.findElements(optionLocator);
        boolean isClicked = false;

        for (WebElement option : options) {
            if (option.isDisplayed()) {
                // Scroll into view just in case
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", option);
                option.click();
                isClicked = true;
                break;
            }
        }

        if (!isClicked) {
            throw new RuntimeException("Option '" + materialcode + "' found in DOM but none were visible.");
        }
    }

    @FindBy(xpath = ("//input[@formcontrolname='requiredQuantity']"))
    List<WebElement> txt_standardquantity;

    public void standardQuantity(String standardquantity) {
        enterValueInLatestElement(txt_standardquantity, standardquantity);
    }

    @FindBy(xpath = ("//input[@formcontrolname='requiredQuantityTolerance']"))
    List<WebElement> txt_tolerance;

    public void tolerance(String tolerance) {
        enterValueInLatestElement(txt_tolerance, tolerance);
    }

    public void enterRow() throws Exception {
        Robot r = new Robot();
        clickLatestElement(txt_tolerance);
        r.keyPress(KeyEvent.VK_ENTER);
        r.keyRelease(KeyEvent.VK_ENTER);
        Thread.sleep(1000); // Wait for row creation
    }

}
