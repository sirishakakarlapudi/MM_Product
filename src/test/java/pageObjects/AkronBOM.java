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

	/*
	 * ========================================================================= [
	 * LOCATORS ]
	 * =========================================================================
	 */

	@FindBy(xpath = "//ng-multiselect-dropdown[@formcontrolname='routeCodeId']")
	protected WebElement drpdwn_productcode;

	@FindBy(xpath = ("//input[@formcontrolname='location']"))
	protected WebElement txt_location;

	@FindBy(xpath = "//input[@formcontrolname='batchSize']")
	protected WebElement txt_batchsize;

	@FindBy(xpath = "//ng-multiselect-dropdown[@formcontrolname='batchSizeUOM']")
	protected WebElement drpdwn_batchsizeuom;

	@FindBy(xpath = ("//input[@formcontrolname='yieldMinRange']"))
	protected WebElement txt_yieldminrange;
	
	@FindBy(xpath = ("//input[@formcontrolname='yieldMaxRange']"))
	protected WebElement txt_yieldmaxrange;

	@FindBy(xpath = ("//input[@formcontrolname='yieldTarget']"))
	protected WebElement txt_yieldtarget;

	@FindBy(xpath = "//p-checkbox[@inputid='isBomCheckBox1']")
	protected WebElement standardbom_yes;

	@FindBy(xpath = "//p-checkbox[@inputid='isBomCheckBox2']")
	protected WebElement standardbom_no;

	// Grid Locators
	@FindBy(xpath = "//span[@class='dropdown-multiselect__caret']")
	protected List<WebElement> drpdwn_materialcode;

	@FindBy(xpath = ("//input[@formcontrolname='requiredQuantity']"))
	protected List<WebElement> txt_standardquantity;
	@FindBy(xpath = ("//input[@formcontrolname='requiredQuantityTolerance']"))
	protected List<WebElement> txt_tolerance;

	@FindBy(xpath = "//input[@formcontrolname='requiredQuantityTolerance']")
	protected List<WebElement> akronbom_tolerance_txt;

	/*
	 * ========================================================================= [
	 * BASIC ACTIONS ]
	 * =========================================================================
	 */

	
	
	
	
	public void selProductCode(String productcode) {
        waitForElementandClick(drpdwn_productcode);
        WebElement productCodeXpath = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//ng-multiselect-dropdown[@formcontrolname='routeCodeId']//li[normalize-space()='"
                        + productcode + "']")));
        productCodeXpath.click();
    }
	
	
	 public void location(String location) {
		 if (location != null && !location.trim().isEmpty()) 
	        txt_location.clear();
	        waitAndSendKeys(txt_location, location);
	    }
	

	 public void batchSize(String batchSize) {
		 if (batchSize != null && !batchSize.trim().isEmpty()) 
	        txt_batchsize.clear();
	        waitAndSendKeys(txt_batchsize, batchSize);
	    }

	
	 public void selBatchSizeUOM(String batchsizeuom) {
	        waitForElementandClick(drpdwn_batchsizeuom);
	        WebElement batchSizeUOMXpath = wait.until(ExpectedConditions.elementToBeClickable(
	                By.xpath("//ng-multiselect-dropdown[@formcontrolname='batchSizeUOM']//li[normalize-space()='"
	                        + batchsizeuom + "']")));
	        batchSizeUOMXpath.click();
	    }
	 
	 
	 public void yieldMinRange(String yieldminrange) {
		 if (yieldminrange != null && !yieldminrange.trim().isEmpty()) 
	        txt_yieldminrange.clear();
	        waitAndSendKeys(txt_yieldminrange, yieldminrange);
	    }

	 public void yieldMaxRange(String yieldmaxrange) {
	        txt_yieldmaxrange.clear();
	        waitAndSendKeys(txt_yieldmaxrange, yieldmaxrange);
	    }

	 
	 public void yieldTarget(String yieldtarget) {
	        txt_yieldtarget.clear();
	        waitAndSendKeys(txt_yieldtarget, yieldtarget);
	    }

	 public void checkStandardBom(String standardbom) {
	        if (standardbom.equalsIgnoreCase("Yes")) {
	            waitForElementandClick(standardbom_yes);
	        } else {
	            waitForElementandClick(standardbom_no);
	        }
	    }

	// Grid Actions
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
	        
	        
	 public void standardQuantity(String standardquantity) {
	        enterValueInLatestElement(txt_standardquantity, standardquantity);
	    }

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
