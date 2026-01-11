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
	
	
	@FindBy(xpath="//input[@formcontrolname='supplierName']")
	WebElement txt_suppliername;
	
	public void supplierName(String suppliername) {
		txt_suppliername.clear();
		waitAndSendKeys(txt_suppliername, suppliername);
	}
	
	
	
	@FindBy(xpath="//input[@formcontrolname='address']")
	WebElement txt_address;
	
	public void supplierAddress(String supplieraddress) {
		txt_address.clear();
		waitAndSendKeys(txt_address, supplieraddress);
	}
	
	
	@FindBy(xpath="//input[@formcontrolname='state']")
	WebElement txt_state;

	public void supplierState(String supplierstate) {
		txt_state.clear();
		waitAndSendKeys(txt_state, supplierstate);
	}
	
	
	
	@FindBy(xpath="//input[@formcontrolname='city']")
	WebElement txt_city;
	
	public void supplierCity(String suppliercity) {
		txt_city.clear();
		waitAndSendKeys(txt_city, suppliercity);
	}
	
	
	
	@FindBy(xpath="//input[@formcontrolname='pincode']")
	WebElement txt_pincode;
	
	public void supplierPincode(String supplierpincode) {
		txt_pincode.clear();
		waitAndSendKeys(txt_pincode, supplierpincode);
	}
	
	
	
	@FindBy(xpath="//ng-multiselect-dropdown[@formcontrolname='regionId']")
	WebElement dropdwn_region;
	
	public void selSupplierRegion(String supplierregion) {
	    waitForElementandClick(dropdwn_region);
	    WebElement supplierregionXpath=  
	        wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("//div[normalize-space()='"+supplierregion+"']")));
	    supplierregionXpath.click();
	}
	

}
