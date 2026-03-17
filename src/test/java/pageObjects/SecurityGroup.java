package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.WheelInput;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import utilities.ScreenshotUtil;

public class SecurityGroup extends BasePage {
	public SecurityGroup(WebDriver driver) {
		super(driver);
	}

	/*
	 * =========================================================================
	 * [ LOCATORS ]
	 * =========================================================================
	 */

	@FindBy(xpath = "//input[@formcontrolname='name']")
	WebElement security_group_name;

	@FindBy(xpath = "//input[@formcontrolname='description']")
	WebElement security_group_desc;

	@FindBy(xpath = "//span[@class='dropdown-multiselect__caret']")
	WebElement clk_module;

	@FindBy(xpath = "//input[@formcontrolname='securityGroupName']")
	WebElement securitygroup_filter;

	@FindBy(xpath = "//div[contains(@class,'selected-card')]")
	WebElement selectedPrivilegesContainer;
	/*
	 * =========================================================================
	 * [ BUSINESS ACTIONS ]
	 * =========================================================================
	 */

	public void securityGroupName(String sgname) {
		security_group_name.clear();
		waitAndSendKeys(security_group_name, sgname);
	}

	public void clickSecurityGroupName() throws Throwable {
		ScreenshotUtil.capture();
		security_group_name.click();
	}

	public void securityGroupDesc(String sgdesc) {
		security_group_desc.clear();
		waitAndSendKeys(security_group_desc, sgdesc);
	}

	public void selModule(String selmodule) {
		waitForElementandClick(clk_module);
		WebElement selModuleXpath = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//ul[@class='item2']/li[normalize-space()='" + selmodule + "']")));
		selModuleXpath.click();
	}

	public void selPrivileges(String userprivileges) {
		String[] sgArray = userprivileges.split(",");

		for (String privilegename : sgArray) {
			privilegename = privilegename.trim(); // in case of extra spaces
			WebElement privilegeXpath = wait.until(ExpectedConditions
					.elementToBeClickable(By.xpath("//label[normalize-space()='" + privilegename + "']")));
			System.out.println("Privilege to select: " + privilegename);

			((JavascriptExecutor) driver).executeScript(
					"arguments[0].scrollIntoView({behavior: 'auto', block: 'center', inline: 'nearest'});",
					privilegeXpath);

			((JavascriptExecutor) driver).executeScript("arguments[0].click();", privilegeXpath);
		}
	}

	public void clickSecurityGroupNameFilter(String sgname) {
		waitAndSendKeys(securitygroup_filter, sgname);
	}
	
	public void scrollAndCaptureSelectedPrivileges() throws Exception {

	    JavascriptExecutor js = (JavascriptExecutor) driver;

	    int scrollStep = 200;
	    int screenshotCount = 1;

	    while (true) {

	        long currentScrollTop = ((Number) js.executeScript(
	                "return arguments[0].scrollTop;",
	                selectedPrivilegesContainer)).longValue();

	        ScreenshotUtil.capture("SelectedPrivileges_" + screenshotCount);
	        screenshotCount++;

	        js.executeScript(
	                "arguments[0].scrollTop = arguments[0].scrollTop + arguments[1];",
	                selectedPrivilegesContainer,
	                scrollStep);

	        Thread.sleep(700);

	        long newScrollTop = ((Number) js.executeScript(
	                "return arguments[0].scrollTop;",
	                selectedPrivilegesContainer)).longValue();

	        if (newScrollTop == currentScrollTop) {
	            break;
	        }
	    }

	    // Force exact bottom scroll
	    js.executeScript(
	            "arguments[0].scrollTop = arguments[0].scrollHeight;",
	            selectedPrivilegesContainer);

	    Thread.sleep(700);
	    ScreenshotUtil.capture("SelectedPrivileges_" + screenshotCount);
	}
}
