package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class Customer extends BasePage {

	public Customer(WebDriver driver) {
		super(driver);
	}

	/*
	 * ========================================================================= [
	 * LOCATORS ]
	 * =========================================================================
	 */

	@FindBy(xpath = "//input[@formcontrolname='customerName']")
	protected WebElement customer_name;

	@FindBy(xpath = "//input[@formcontrolname='city']")
	protected WebElement customer_city;

	@FindBy(xpath = "//input[@formcontrolname='customerCode']")
	protected WebElement customer_code;

	@FindBy(xpath = "//ng-multiselect-dropdown[@formcontrolname='regionId']//div/span")
	protected WebElement storage_location_drpdwn;

	/*
	 * ========================================================================= [
	 * BASIC ACTIONS ]
	 * =========================================================================
	 */

	public void customerName(String name) {
		if (name != null && !name.trim().isEmpty()) {
			customer_name.clear();
			waitAndSendKeys(customer_name, name);
		}

	}

	public void customerCity(String city) {
		if (city != null && !city.trim().isEmpty()) {
			customer_city.clear();
			waitAndSendKeys(customer_city, city);
		}
	}

	public void customerCode(String code) {
		if (code != null && !code.trim().isEmpty()) {
			customer_code.clear();
			waitAndSendKeys(customer_code, code);
		}
	}

	/*
	 * ========================================================================= [
	 * BUSINESS ACTIONS ]
	 * =========================================================================
	 */
	
	
	public void selMarketRegion(String region) {
		waitForElementandClick(storage_location_drpdwn);
		WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//ng-multiselect-dropdown[@formcontrolname='regionId']//li[normalize-space()='"
						+ region + "']")));
		option.click();
	}

}
