package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class Equipment extends BasePage {

	public Equipment(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	

	@FindBy(xpath="//input[@formcontrolname='name']")
	WebElement txt_equipmentname;
	
	
	public void equipmentName(String equipmentname) {
		txt_equipmentname.clear();
		waitAndSendKeys(txt_equipmentname, equipmentname);
	}
	
	@FindBy(xpath="//input[@formcontrolname='balanceId']")
	WebElement txt_equipmentid;
	
	
	public void equipmentId(String equipmentid) {
		txt_equipmentid.clear();
		waitAndSendKeys(txt_equipmentid, equipmentid);
	}
	
	
	@FindBy (xpath= "//ng-multiselect-dropdown[@formcontrolname='equipmentTypeId']//div/span")
	WebElement dropdwn_equipmenttype;
	public void selEquipmentType(String euipmenttype) {
		waitForElementandClick(dropdwn_equipmenttype);
		WebElement equipmentTypeXpath = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//ng-multiselect-dropdown[@formcontrolname='equipmentTypeId']//li[normalize-space()='"
						+ euipmenttype + "']")));
		equipmentTypeXpath.click();
	}
	
	
	@FindBy(xpath="//input[@formcontrolname='maxCapacityRange']")
	WebElement txt_capacityinkg;
	
	
	public void capacityInKg(String capacity) {
		txt_capacityinkg.clear();
		waitAndSendKeys(txt_capacityinkg, capacity);
	}
	
	
	@FindBy(xpath="//input[@formcontrolname='minOperationalRange']")
	WebElement txt_operationalrangemin;
	
	
	public void operationalRangeMin(String operationalrangemin) {
		txt_operationalrangemin.clear();
		waitAndSendKeys(txt_operationalrangemin, operationalrangemin);
	}
	
	@FindBy(xpath="//input[@formcontrolname='maxOperationalRange']")
	WebElement txt_operationalrangemax;
	
	
	public void operationalRangeMax(String operationalrangemax) {
		txt_operationalrangemax.clear();
		waitAndSendKeys(txt_operationalrangemax, operationalrangemax);
	}
	
	
	@FindBy (xpath= "//ng-multiselect-dropdown[@formcontrolname='department']//div/span")
	WebElement dropdwn_department;
	public void selDepartment(String department) {
		waitForElementandClick(dropdwn_department);
		WebElement departmentXpath = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//ng-multiselect-dropdown[@formcontrolname='department']//li[normalize-space()='"
						+ department + "']")));
		departmentXpath.click();
	}
	
	
	@FindBy (xpath= "//ng-multiselect-dropdown[@formcontrolname='facilityId']//div/span")
	WebElement dropdwn_facility;
	public void selFacility(String facility) {
		waitForElementandClick(dropdwn_facility);
		WebElement facilityXpath = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//ng-multiselect-dropdown[@formcontrolname='facilityId']//li[normalize-space()='"
						+ facility + "']")));
		facilityXpath.click();
	}
	
	@FindBy (xpath= "//ng-multiselect-dropdown[@formcontrolname='childFacility']//div/span")
	WebElement dropdwn_weighingbalancefacility;
	public void selWeighingBalanceFacility(String weighingbalancefacility) {
		waitForElementandClick(dropdwn_weighingbalancefacility);
		WebElement weighingBalanceFacilityXpath = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//ng-multiselect-dropdown[@formcontrolname='childFacility']//li[normalize-space()='"
						+ weighingbalancefacility + "']")));
		weighingBalanceFacilityXpath.click();
	}
	
	
}
