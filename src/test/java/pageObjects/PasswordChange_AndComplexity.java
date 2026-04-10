package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PasswordChange_AndComplexity extends BasePage {
	
	public PasswordChange_AndComplexity(WebDriver driver) {
		super(driver);
	}

	/*
	 * ========================================================================= [
	 * LOCATORS ]
	 * =========================================================================
	 */
	
	@FindBy(xpath = "//input[@formcontrolname='currentPassword']")
	protected WebElement current_password;
	
	@FindBy(xpath = "//input[@formcontrolname='newPassword']")
	protected WebElement new_password;
	
	@FindBy(xpath = "//input[@formcontrolname='confirmPassword']")
	protected WebElement confirm_password;
	
	@FindBy(xpath = "//button[@type='submit']")
	protected WebElement btn_save;
	
	
	
	
	/*
	 * ========================================================================= [
	 * BASIC ACTIONS ]
	 * =========================================================================
	 */

	public void currentPassword(String name) {
		if (name != null && !name.trim().isEmpty()) {
			current_password.clear();
			waitAndSendKeys(current_password, name);
		}

	}
	
	
	public void newPassword(String name) {
		if (name != null && !name.trim().isEmpty()) {
			new_password.clear();
			waitAndSendKeys(new_password, name);
		}

	}
	
	
	public void confirmPassword(String name) {
		if (name != null && !name.trim().isEmpty()) {
			confirm_password.clear();
			waitAndSendKeys(confirm_password, name);
		}

	}
	
	
	public void clickSave() {
		waitForElementandClick(btn_save);
	}
	
	

}
