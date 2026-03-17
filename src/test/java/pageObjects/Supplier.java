package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class Supplier extends BasePage {

	public Supplier(WebDriver driver) {
		super(driver);

	}

	/*
	 * ========================================================================= [
	 * LOCATORS ]
	 * =========================================================================
	 */

	@FindBy(xpath = "//input[@formcontrolname='supplierName']")
	protected WebElement supplier_name;

	@FindBy(xpath = "//input[@formcontrolname='address']")
	protected WebElement supplier_address;

	@FindBy(xpath = "//input[@formcontrolname='state']")
	protected WebElement supplier_state;

	@FindBy(xpath = "//input[@formcontrolname='city']")
	protected WebElement supplier_city;

	@FindBy(xpath = "//input[@formcontrolname='pincode']")
	protected WebElement supplier_pincode;

	@FindBy(xpath = "//ng-multiselect-dropdown[@formcontrolname='regionId']//div/span")
	protected WebElement supplier_region_drpdwn;

	/*
	 * ========================================================================= [
	 * BASIC ACTIONS ]
	 * =========================================================================
	 */

	public void supplierName(String name) {
		if (name != null && !name.trim().isEmpty()) {
			supplier_name.clear();
			waitAndSendKeys(supplier_name, name);
		}
	}

	public void supplierAddress(String address) {
		if (address != null && !address.trim().isEmpty()) {
			supplier_address.clear();
			waitAndSendKeys(supplier_address, address);
		}
	}

	public void supplierState(String state) {
		if (state != null && !state.trim().isEmpty()) {
			supplier_state.clear();
			waitAndSendKeys(supplier_state, state);
		}
	}

	public void supplierCity(String city) {
		if (city != null && !city.trim().isEmpty()) {
			supplier_city.clear();
			waitAndSendKeys(supplier_city, city);
		}
	}

	public void supplierPincode(String pincode) {
		if (pincode != null && !pincode.trim().isEmpty()) {
			supplier_pincode.clear();
			waitAndSendKeys(supplier_pincode, pincode);
		}
	}

	public void selSupplierRegion(String region) {
		waitForElementandClick(supplier_region_drpdwn);
		WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//ng-multiselect-dropdown[@formcontrolname='regionId']//li[normalize-space()='" + region
						+ "']")));
		option.click();
	}

}
