package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class Facility extends BasePage {
	public Facility(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//input[@formcontrolname='name']")
	WebElement txt_facilityname;

	public void facilityName(String facilityname) {
		txt_facilityname.clear();
		waitAndSendKeys(txt_facilityname, facilityname);
	}

	@FindBy(xpath = "//ng-multiselect-dropdown[@formcontrolname='facilityTypeId']//span[@class='dropdown-multiselect__caret']")
	WebElement dropdwn_facilitytype;

	public void selFacilityType(String facilitytype) {
		waitForElementandClick(dropdwn_facilitytype);
		WebElement facilityXpath = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//div[normalize-space()='" + facilitytype + "']")));
		facilityXpath.click();
	}

	@FindBy(xpath = "//ng-multiselect-dropdown[@formcontrolname='productStoreId']//span[@class='dropdown-multiselect__caret']")
	WebElement dropdwn_department;

	public void selDepartment(String department) {
		waitForElementandClick(dropdwn_department);
		WebElement departmentXpath = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//div[normalize-space()='" + department + "']")));
		
		departmentXpath.click();

	}

	@FindBy(xpath = "//ng-multiselect-dropdown[@formcontrolname='parentId']//span[@class='dropdown-multiselect__caret']")
	WebElement dropdwn_parentfacility;

	public void selParentFacility(String parentfacility) {
		waitForElementandClick(dropdwn_parentfacility);
		WebElement parentFacilityXpath = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//div[normalize-space()='" + parentfacility + "']")));
		parentFacilityXpath.click();
		System.out.println("Parent Facility from Excel: " + parentfacility);
	}

	@FindBy(xpath = "//input[@formcontrolname='storageCondition']")
	WebElement txt_storagecondition;

	public void storageCondition(String storagecondition) {
		txt_storagecondition.clear();
		waitAndSendKeys(txt_storagecondition, storagecondition);

	}

}