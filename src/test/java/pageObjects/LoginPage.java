package pageObjects;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {

	public LoginPage(WebDriver driver) {
		super(driver);
	}

	/*
	 * =========================================================================
	 * [ LOCATORS ]
	 * =========================================================================
	 */

	@FindBy(xpath = "//div[@class='selected-product']")
	protected WebElement dropdwn_allApps;

	@FindBy(xpath = "//ul[@class='dropdown-product-list ng-star-inserted']//div[normalize-space()='MASTER']")
	protected WebElement dropdwn_Master;

	@FindBy(xpath = "//ul[@class='dropdown-product-list ng-star-inserted']//div[normalize-space()='MM']")
	protected WebElement dropdwn_MM;

	@FindBy(xpath = "//button[normalize-space()='Yes']")
	protected WebElement confirmation_yes;

	@FindBy(xpath = "//button[normalize-space()='No']")
	protected WebElement confirmation_no;

	@FindBy(xpath = "//div[contains(@class,'modal-content')] | //div[contains(@id,'modal')]")
	protected WebElement confirmation_dialog;

	@FindBy(xpath = "//li[normalize-space()='Profile']/i")
	protected WebElement profile_icon;
	@FindBy(xpath = "//li[normalize-space()='Profile']")
	protected WebElement profile;

	@FindBy(xpath = "//li[normalize-space()='Logout']")
	protected WebElement logout;

	@FindBy(xpath = "//div[@class='selected-product']//span[1]")
	protected WebElement selectedAppTitle;

	@FindBy(xpath = "//div[@class='selected-product']//img")
	protected WebElement selectedAppIcon;

	@FindBy(xpath = "//ul[contains(@class,'dropdown-product-list')]//li[contains(@class,'dropdown-product-item')]")
	protected List<WebElement> dropdownAppItems;

	@FindBy(tagName = "img")
	protected List<WebElement> allImages;
	
	@FindBy(xpath = "//div[@class='profile-info']")
	protected WebElement accountIcon;

	@FindBy(xpath = "//button[@class='profile-btn']//i")
	protected WebElement accountDropdown;

	@FindBy(xpath = "//button[@class='profile-btn']//i//span[normalize-space()='Profile']")
	protected WebElement profileMenuItem;

	@FindBy(id = "userName")
	protected WebElement profile_username;

	@FindBy(id = "employeeName")
	protected WebElement profile_employeeName;

	@FindBy(id = "employeeId")
	protected WebElement profile_employeeId;

	@FindBy(id = "department")
	protected WebElement profile_department;

	@FindBy(id = "userRole")
	protected WebElement profile_userRole;

	@FindBy(xpath = "//textarea[@formcontrolname='privilages']")
	protected WebElement profile_privileges;

	@FindBy(id = "email")
	protected WebElement profile_email;

	@FindBy(id = "mobileNumber")
	protected WebElement profile_mobileNumber;

	@FindBy(id = "currentPassword")
	protected WebElement profile_currentPassword;

	@FindBy(id = "newPassword")
	protected WebElement profile_newPassword;

	@FindBy(id = "confirmNewPassword")
	protected WebElement profile_confirmNewPassword;

	@FindBy(xpath = "//a")
	protected List<WebElement> allLinks;

	/*
	 * =========================================================================
	 * [ BASIC ACTIONS ]
	 * =========================================================================
	 */

	public void click_allAppsdropdown() {
	
		dropdwn_allApps.click();
	}

	public void click_dropdownMaster() {
		dropdwn_Master.click();
	}

	public void click_dropdownMM() {
		
		waitForElementandClick(dropdwn_MM);
	}

	public void click_confirmYes() {
		
		waitForElementandClick(confirmation_yes);
	}

	public void click_confirmNo() {
		waitForElementandClick(confirmation_no);
	}

	public void profile() {
	
		waitForElementandClick(profile);
	}

	
	public void click_profileDropdown() {
		waitForElementandClick(accountDropdown);
	}

	/*
	 * =========================================================================
	 * [ BUSINESS ACTIONS ]
	 * =========================================================================
	 */

	public void logout() {
		Actions actions = new Actions(driver);
		actions.moveToElement(logout).perform();
		waitForElementandClick(logout);
	}

	public void navigateToProfile() {
		waitForElementandClick(accountDropdown);
		waitForElementandClick(profileMenuItem);
		waitForLoading();
	}

	public void clickAppByTitle(String title) {
		jsClick(wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
				"//ul[contains(@class,'dropdown-product-list')]//div[contains(@class,'product-title')][translate(normalize-space(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz')='"
						+ title.toLowerCase() + "']"))));
	}

	/*
	 * =========================================================================
	 * [ GETTERS / HELPERS ]
	 * =========================================================================
	 */

	public boolean isConfirmationPopupDisplayed() {
		try {
			return confirmation_yes.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public List<String> getDisplayedLinks() {
		return allLinks.stream().filter(WebElement::isDisplayed).map(e -> e.getText().trim()).filter(t -> !t.isEmpty())
				.collect(Collectors.toList());
	}

	public List<String> getDisplayedImages() {
		return allImages.stream().filter(WebElement::isDisplayed).map(e -> e.getAttribute("src"))
				.filter(s -> s != null && !s.isEmpty()).map(s -> s.substring(s.lastIndexOf('/') + 1))
				.collect(Collectors.toList());
	}

	public String getSelectedAppTitle() {
		return selectedAppTitle.getText().trim();
	}

	public String getSelectedAppIconSrc() {
		String src = selectedAppIcon.getAttribute("src");
		return (src != null) ? src.substring(src.lastIndexOf('/') + 1) : "";
	}

	public static class AppDropdownDetails {
		public String title;
		public String description;
		public String iconSrc;

		public AppDropdownDetails(String title, String description, String iconSrc) {
			this.title = title;
			this.description = description;
			this.iconSrc = iconSrc;
		}

		@Override
		public String toString() {
			return String.format("[Title: %s, Desc: %s, Icon: %s]", title, description, iconSrc);
		}
	}

	public List<AppDropdownDetails> getAppDropdownDetails() {
		List<AppDropdownDetails> list = new ArrayList<>();
		try {
			waitForElementToVisible(driver.findElement(By.xpath("//ul[contains(@class,'dropdown-product-list')]")));

			for (WebElement item : dropdownAppItems) {
				String title = item.findElement(By.xpath(".//div[contains(@class,'product-title')]")).getText().trim();
				String desc = item.findElement(By.xpath(".//div[contains(@class,'product-description')]")).getText()
						.trim();
				String src = item.findElement(By.xpath(".//img")).getAttribute("src");
				String iconSrc = (src != null) ? src.substring(src.lastIndexOf('/') + 1) : "";
				list.add(new AppDropdownDetails(title, desc, iconSrc));
			}
		} catch (Exception e) {
			log.error("Dropdown capture failed: " + e.getMessage());
		}
		return list;
	}

	public String getProfileFieldPlaceholder(String fieldId) {
		return driver.findElement(By.id(fieldId)).getAttribute("placeholder");
	}

	public String getProfileFieldValue(String fieldId) {
		return driver.findElement(By.id(fieldId)).getAttribute("value");
	}

	public String getPrivilegesValue() {
		return profile_privileges.getAttribute("value");
	}

	public List<String> getAllAppsDisplayed() {
		return driver.findElements(By.xpath("//div[contains(@class,'card')]//h5")).stream()
				.map(e -> e.getText().trim()).filter(t -> !t.isEmpty()).collect(Collectors.toList());
	}

	public WebElement getCardByTitle(String title) {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//h5[normalize-space()='" + title + "']/ancestor::div[contains(@class,'card')]")));
	}

	public WebElement getIcon(String moduleName) {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
				"//h5[normalize-space()='" + moduleName + "']/ancestor::div[contains(@class,'card')]//img")));
	}

	public WebElement getDescription(String moduleName) {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
				"//h5[normalize-space()='" + moduleName + "']/ancestor::div[contains(@class,'card')]//p")));
	}

	public String getFieldRequiredErrorMessage(String labelName) {
		try {
			WebElement errorElement = driver.findElement(By.xpath(
					"//label[normalize-space()='" + labelName + "']/parent::div//div[contains(text(),'is required')]"));
			return errorElement.getText().trim();
		} catch (Exception e) {
			return "";
		}
	}

}
