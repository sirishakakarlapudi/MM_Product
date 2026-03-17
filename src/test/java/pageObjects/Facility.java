package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import utilities.ScreenshotUtil;

public class Facility extends BasePage {

	public Facility(WebDriver driver) {
		super(driver);
	}

	/*
	 * =========================================================================
	 * [ LOCATORS ]
	 * =========================================================================
	 */

	@FindBy(xpath = "//input[@formcontrolname='name']")
	protected WebElement facility_name;

	@FindBy(xpath = "//ng-multiselect-dropdown[@formcontrolname='facilityTypeId']//span[@class='dropdown-multiselect__caret']")
	protected WebElement facility_type_drpdwn;

	@FindBy(xpath = "//ng-multiselect-dropdown[@formcontrolname='productStoreId']//span[@class='dropdown-multiselect__caret']")
	protected WebElement department_drpdwn;

	@FindBy(xpath = "//ng-multiselect-dropdown[@formcontrolname='parentId']//span[@class='dropdown-multiselect__caret']")
	protected WebElement parent_facility_drpdwn;

	@FindBy(xpath = "//input[@formcontrolname='storageCondition']")
	protected WebElement storage_condition;

	/*
	 * =========================================================================
	 * [ BASIC ACTIONS ]
	 * =========================================================================
	 */

	public void facilityName(String name) {
		if (name != null && !name.trim().isEmpty()) {
			facility_name.clear();
			waitAndSendKeys(facility_name, name);
		}
	}

	public void clickFacilityName() throws Throwable {
		ScreenshotUtil.capture();
		facility_name.click();
	}

	public void storageCondition(String condition) {
		if (condition != null && !condition.trim().isEmpty()) {
			storage_condition.clear();
			waitAndSendKeys(storage_condition, condition);
		}
	}

	public void clickStorageCondition() throws Throwable {
		ScreenshotUtil.capture();
		storage_condition.click();
	}

	/*
	 * =========================================================================
	 * [ BUSINESS ACTIONS ]
	 * =========================================================================
	 */

	public void selFacilityType(String facilitytype) {
		waitForElementandClick(facility_type_drpdwn);
		WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//div[normalize-space()='" + facilitytype + "']")));
		option.click();
	}

	public void selDepartment(String department) {
		waitForElementandClick(department_drpdwn);
		WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//div[normalize-space()='" + department + "']")));
		option.click();
	}

	public void selParentFacility(String parentfacility) {
		waitForElementandClick(parent_facility_drpdwn);
		WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//div[normalize-space()='" + parentfacility + "']")));
		option.click();
		log.info("Selected Parent Facility: {}", parentfacility);
	}

	/*
	 * =========================================================================
	 * [ GETTERS / HELPERS ]
	 * =========================================================================
	 */

	public WebElement getFacilityNameField() {
		return facility_name;
	}

	public WebElement getStorageConditionField() {
		return storage_condition;
	}

}