package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ProductCustomer extends BasePage {

	public ProductCustomer(WebDriver driver) {
		super(driver);
	}
	
	
	/*
	 * ========================================================================= [
	 * LOCATORS ]
	 * =========================================================================
	 */
	
	@FindBy(xpath = "//ng-multiselect-dropdown[@formcontrolname='productId']//div/span")
	protected WebElement product_drpdwn;
	
	
	@FindBy(xpath = "//ng-multiselect-dropdown[@formcontrolname='regionId']//div/span")
	protected WebElement marketregion_drpdwn;
	
	
	@FindBy(xpath = "//ng-multiselect-dropdown[@formcontrolname='customerId']//div/span")
	protected WebElement customerid_drpdwn;
	
	/*
	 * ========================================================================= [
	 * BUSINESS ACTIONS ]
	 * =========================================================================
	 */
	public void selProduct(String product) {
		waitForElementandClick(product_drpdwn);
		WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//ng-multiselect-dropdown[@formcontrolname='productId']//li[normalize-space()='"
						+ product + "']")));
		option.click();
	}
	
	
	
	public void selMarketRegion(String region) {
		waitForElementandClick(marketregion_drpdwn);
		WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//ng-multiselect-dropdown[@formcontrolname='regionId']//li[normalize-space()='"
						+ region + "']")));
		option.click();
	}
	
	public void selCustomer(String customer) {
		waitForElementandClick(customerid_drpdwn);
		WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//ng-multiselect-dropdown[@formcontrolname='customerId']//li[normalize-space()='"
						+ customer + "']")));
		option.click();
	}
	
	public void selMultipleCustomers(String customersProp) {
		waitForElementandClick(customerid_drpdwn);
		String[] customers = customersProp.split(",");
		for (String cust : customers) {
			WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//ng-multiselect-dropdown[@formcontrolname='customerId']//li[normalize-space()='"
							+ cust.trim() + "']")));
			option.click();
		}
	}

	
}
