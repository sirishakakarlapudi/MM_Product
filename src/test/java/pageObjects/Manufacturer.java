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
	
	
	@FindBy(xpath="//input[@formcontrolname='supplierName']")
	WebElement txt_manufacturename;
	
	public void manufacturerName(String manufacturename) {
		txt_manufacturename.clear();
		waitAndSendKeys(txt_manufacturename, manufacturename);
	}
	
	
	
	@FindBy(xpath="//input[@formcontrolname='address']")
	WebElement txt_address;
	
	public void manufactureAddress(String manufactureaddress) {
		txt_address.clear();
		waitAndSendKeys(txt_address, manufactureaddress);
	}
	
	
	@FindBy(xpath="//input[@formcontrolname='state']")
	WebElement txt_state;

	public void manufactureState(String manufacturestate) {
		txt_state.clear();
		waitAndSendKeys(txt_state, manufacturestate);
	}
	
	
	
	@FindBy(xpath="//input[@formcontrolname='city']")
	WebElement txt_city;
	
	public void manufactureCity(String manufacturecity) {
		txt_city.clear();
		waitAndSendKeys(txt_city, manufacturecity);
	}
	
	
	
	@FindBy(xpath="//input[@formcontrolname='pincode']")
	WebElement txt_pincode;
	
	public void manufacturePincode(String manufacturepincode) {
		txt_pincode.clear();
		waitAndSendKeys(txt_pincode, manufacturepincode);
	}
	
	
	
	@FindBy(xpath="//ng-multiselect-dropdown[@formcontrolname='regionId']")
	WebElement dropdwn_region;
	
	public void selManufactureRegion(String manufactureregion) {
	    waitForElementandClick(dropdwn_region);
	    WebElement manufactureregionXpath=  
	        wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("//div[normalize-space()='"+manufactureregion+"']")));
	    manufactureregionXpath.click();
	}
	

}
