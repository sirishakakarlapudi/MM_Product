package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {
	
	
	public LoginPage(WebDriver driver)
	{
		super(driver);
	}
	
	
	@FindBy(xpath="//div[@class='selected-product']")
	WebElement dropdwn_allApps;
	
	public void click_allAppsdropdown() {
		dropdwn_allApps.click();
		
	}
	
	public void click_allAppsdropdown(String screenshotname) throws Throwable {
		dropdwn_allApps.click();
		
	}
	
	@FindBy(xpath="//div[normalize-space()='MM']")
	WebElement dropdwn_MM;
	public void click_dropdownMM() {
		dropdwn_MM.click();
	}
	
	
	public void click_dropdownMM(String screenshotname)  {
		dropdwn_MM.click();
	}
	
	@FindBy(xpath="//button[normalize-space()='Yes']")
	WebElement confirmation_yes;
	public void click_confirmYes() {
		confirmation_yes.click();
	}
	
	
	public void click_confirmYes(String screenshotname)  {
		
		confirmation_yes.click();
	}
	@FindBy(xpath="//li[normalize-space()='Profile']/i")
	WebElement profile;
	public void profile() {
		profile.click();
	}
	public void profile(String screenshotname) {
	
		profile.click();
	}
	@FindBy(xpath="//li[normalize-space()='Logout']")
	WebElement logout;
	public void logout() {
		Actions actions = new Actions(driver);
		actions.moveToElement(logout).perform();
		
		logout.click();
	}
	public void logout(String screenshotname) {
		Actions actions = new Actions(driver);
		actions.moveToElement(logout).perform();
		logout.click();
	}
	


	
}