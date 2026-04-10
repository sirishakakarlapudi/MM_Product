package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class Equipment extends BasePage {

	public Equipment(WebDriver driver) {
		super(driver);
	}

	/*
	 * ========================================================================= [
	 * LOCATORS ]
	 * =========================================================================
	 */

	@FindBy(xpath = "//input[@formcontrolname='name']")
	protected WebElement equipment_name;

	@FindBy(xpath = "//input[@formcontrolname='balanceId']")
	protected WebElement equipment_id;

	@FindBy(xpath = "//p-autocomplete[@formcontrolname='equipmentTypeId']//chevrondownicon")
	protected WebElement equipment_type_drpdwn;

	@FindBy(xpath = "//input[@formcontrolname='maxCapacityRange']")
	protected WebElement equipment_capacity_in_kg;

	@FindBy(xpath = "//input[@formcontrolname='minOperationalRange']")
	protected WebElement equipment_operational_range_min;

	@FindBy(xpath = "//input[@formcontrolname='maxOperationalRange']")
	protected WebElement equipment_operational_range_max;

	@FindBy(xpath = "//ng-multiselect-dropdown[@formcontrolname='department']//div/span")
	protected WebElement equipment_department_drpdwn;

	@FindBy(xpath = "//ng-multiselect-dropdown[@formcontrolname='facilityId']//div/span")
	protected WebElement equipment_facility_drpdwn;

	@FindBy(xpath = "//ng-multiselect-dropdown[@formcontrolname='childFacility']//div/span")
	protected WebElement equipment_weighing_balance_facility_drpdwn;

	/*
	 * ========================================================================= [
	 * BASIC ACTIONS ]
	 * =========================================================================
	 */

	public void equipmentName(String equipmentname) {
		if (equipmentname != null && !equipmentname.trim().isEmpty()) {
			equipment_name.clear();
			waitAndSendKeys(equipment_name, equipmentname);
		}
	}

	public void equipmentId(String equipmentid) {
		if (equipmentid != null && !equipmentid.trim().isEmpty()) {
			equipment_id.clear();
			waitAndSendKeys(equipment_id, equipmentid);
		}
	}

	public void selEquipmentType(String euipmenttype) {
		if (euipmenttype != null && !euipmenttype.trim().isEmpty()) {
			waitForElementandClick(equipment_type_drpdwn);
			WebElement equipmentTypeXpath = wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//li[normalize-space()='" + euipmenttype + "']")));
			equipmentTypeXpath.click();
		}
	}

	public void capacityInKg(String capacity) {
		if (capacity != null && !capacity.trim().isEmpty()) {
			equipment_capacity_in_kg.clear();
			waitAndSendKeys(equipment_capacity_in_kg, capacity);
		}
	}

	public void operationalRangeMin(String operationalrangemin) {
		if (operationalrangemin != null && !operationalrangemin.trim().isEmpty()) {
			equipment_operational_range_min.clear();
			waitAndSendKeys(equipment_operational_range_min, operationalrangemin);
		}
	}

	public void operationalRangeMax(String operationalrangemax) {
		if (operationalrangemax != null && !operationalrangemax.trim().isEmpty()) {
			equipment_operational_range_max.clear();
			waitAndSendKeys(equipment_operational_range_max, operationalrangemax);
		}
	}

	public void selDepartment(String department) {
		if (department != null && !department.trim().isEmpty()) {
			waitForElementandClick(equipment_department_drpdwn);
			WebElement departmentXpath = wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//ng-multiselect-dropdown[@formcontrolname='department']//li[normalize-space()='"
							+ department + "']")));
			departmentXpath.click();
		}
	}

	public void selFacility(String facility) {
		if (facility != null && !facility.trim().isEmpty()) {
			waitForElementandClick(equipment_facility_drpdwn);
			WebElement facilityXpath = wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//ng-multiselect-dropdown[@formcontrolname='facilityId']//li[normalize-space()='"
							+ facility + "']")));
			facilityXpath.click();
		}
	}

	public void selWeighingBalanceFacility(String weighingbalancefacility) {
		if (weighingbalancefacility != null && !weighingbalancefacility.trim().isEmpty()) {
			waitForElementandClick(equipment_weighing_balance_facility_drpdwn);
			WebElement weighingBalanceFacilityXpath = wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//ng-multiselect-dropdown[@formcontrolname='childFacility']//li[normalize-space()='"
							+ weighingbalancefacility + "']")));
			weighingBalanceFacilityXpath.click();
		}
	}

}
