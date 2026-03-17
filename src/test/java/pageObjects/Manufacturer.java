package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class Manufacturer extends BasePage {

	public Manufacturer(WebDriver driver) {
		super(driver);
	}

	/*
	 * ========================================================================= [
	 * LOCATORS ]
	 * =========================================================================
	 */

	@FindBy(xpath = "//input[@formcontrolname='supplierName']")
	protected WebElement manufacturer_name;

	@FindBy(xpath = "//input[@formcontrolname='address']")
	protected WebElement manufacturer_address;

	@FindBy(xpath = "//input[@formcontrolname='state']")
	protected WebElement manufacturer_state;

	@FindBy(xpath = "//input[@formcontrolname='city']")
	protected WebElement manufacturer_city;

	@FindBy(xpath = "//input[@formcontrolname='pincode']")
	protected WebElement manufacturer_pincode;

	@FindBy(xpath = "//ng-multiselect-dropdown[@formcontrolname='regionId']//div/span")
	protected WebElement manufacturer_region_drpdwn;

	/*
	 * ========================================================================= [
	 * BASIC ACTIONS ]
	 * =========================================================================
	 */

	public void manufacturerName(String name) {
		if (name != null && !name.trim().isEmpty()) {
			manufacturer_name.clear();
			waitAndSendKeys(manufacturer_name, name);
		}
	}

	public void manufacturerAddress(String address) {
		if (address != null && !address.trim().isEmpty()) {
			manufacturer_address.clear();
			waitAndSendKeys(manufacturer_address, address);
		}
	}

	public void manufacturerState(String state) {
		if (state != null && !state.trim().isEmpty()) {
			manufacturer_state.clear();
			waitAndSendKeys(manufacturer_state, state);
		}
	}

	public void manufacturerCity(String city) {
		if (city != null && !city.trim().isEmpty()) {
			manufacturer_city.clear();
			waitAndSendKeys(manufacturer_city, city);
		}
	}

	public void manufacturerPincode(String pincode) {
		if (pincode != null && !pincode.trim().isEmpty()) {
			manufacturer_pincode.clear();
			waitAndSendKeys(manufacturer_pincode, pincode);
		}
	}

	public void selManufacturerRegion(String region) {
		waitForElementandClick(manufacturer_region_drpdwn);
		WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//ng-multiselect-dropdown[@formcontrolname='regionId']//li[normalize-space()='" + region
						+ "']")));
		option.click();
	}

}
