package pageObjects;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

	public LoginPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//div[@class='selected-product']")
	WebElement dropdwn_allApps;

	public void click_allAppsdropdown() {
		dropdwn_allApps.click();
	}

	@FindBy(xpath = "//ul[@class='dropdown-product-list ng-star-inserted']//div[normalize-space()='MASTER']")
	WebElement dropdwn_Master;

	public void click_dropdownMaster() {
		dropdwn_Master.click();
	}

	@FindBy(xpath = "//ul[@class='dropdown-product-list ng-star-inserted']//div[normalize-space()='MM']")
	WebElement dropdwn_MM;

	public void click_dropdownMM() {
		dropdwn_MM.click();
	}

	@FindBy(xpath = "//button[normalize-space()='Yes']")
	WebElement confirmation_yes;

	@FindBy(xpath = "//button[normalize-space()='No']")
	WebElement confirmation_no;

	@FindBy(xpath = "//div[contains(@class,'modal-content')] | //div[contains(@id,'modal')]")
	WebElement confirmation_dialog;

	public void click_confirmYes() {
		waitForElementandClick(confirmation_yes);
	}

	public void click_confirmNo() {
		waitForElementandClick(confirmation_no);
	}

	public boolean isConfirmationPopupDisplayed() {
		try {
			return confirmation_yes.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	@FindBy(xpath = "//li[normalize-space()='Profile']/i")
	WebElement profile;

	public void profile() {
		profile.click();
	}

	@FindBy(xpath = "//li[normalize-space()='Logout']")
	WebElement logout;

	public void logout() {
		Actions actions = new Actions(driver);
		actions.moveToElement(logout).perform();
		logout.click();
	}

	@FindBy(xpath = "//a")
	List<WebElement> allLinks;

	public List<String> getDisplayedLinks() {
		return allLinks.stream().filter(WebElement::isDisplayed).map(e -> e.getText().trim()).filter(t -> !t.isEmpty())
				.collect(Collectors.toList());
	}

	public List<String> getDisplayedImages() {
		return allImages.stream().filter(WebElement::isDisplayed).map(e -> e.getAttribute("src"))
				.filter(s -> s != null && !s.isEmpty()).map(s -> s.substring(s.lastIndexOf('/') + 1))
				.collect(Collectors.toList());
	}

	@FindBy(xpath = "//h5[normalize-space()='MASTERS']")
	WebElement titleMastersHeader;

	public void click_titleMasters() {
		waitForElementandClick(titleMastersHeader);
	}

	// ================= HEADER APP DROPDOWN METHODS =================

	@FindBy(xpath = "//div[@class='selected-product']//span[1]")
	WebElement selectedAppTitle;

	@FindBy(xpath = "//div[@class='selected-product']//img")
	WebElement selectedAppIcon;

	public String getSelectedAppTitle() {
		return selectedAppTitle.getText().trim();
	}

	public String getSelectedAppIconSrc() {
		String src = selectedAppIcon.getAttribute("src");
		return (src != null) ? src.substring(src.lastIndexOf('/') + 1) : "";
	}

	@FindBy(xpath = "//ul[contains(@class,'dropdown-product-list')]//li[contains(@class,'dropdown-product-item')]")
	List<WebElement> dropdownAppItems;

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

	public void clickAppByTitle(String title) {
		jsClick(wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
				"//ul[contains(@class,'dropdown-product-list')]//div[contains(@class,'product-title')][translate(normalize-space(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz')='"
						+ title.toLowerCase() + "']"))));
	}

	// ================= USER ACCOUNT / PROFILE LOCATORS =================

	@FindBy(xpath = "//button[@class='profile-btn']//i")
	WebElement accountDropdown;

	@FindBy(xpath = "//button[@class='profile-btn']//i//span[normalize-space()='Profile']")
	WebElement profileMenuItem;

	public void navigateToProfile() {
		waitForElementandClick(accountDropdown);
		waitForElementandClick(profileMenuItem);
		waitForLoading();
	}

	public void click_profileDropdown() {
		waitForElementandClick(accountDropdown);
	}

	@FindBy(id = "userName")
	WebElement profile_username;

	@FindBy(id = "employeeName")
	WebElement profile_employeeName;

	@FindBy(id = "employeeId")
	WebElement profile_employeeId;

	@FindBy(id = "department")
	WebElement profile_department;

	@FindBy(id = "userRole")
	WebElement profile_userRole;

	@FindBy(xpath = "//textarea[@formcontrolname='privilages']")
	WebElement profile_privileges;

	@FindBy(id = "email")
	WebElement profile_email;

	@FindBy(id = "mobileNumber")
	WebElement profile_mobileNumber;

	@FindBy(id = "currentPassword")
	WebElement profile_currentPassword;

	@FindBy(id = "newPassword")
	WebElement profile_newPassword;

	@FindBy(id = "confirmNewPassword")
	WebElement profile_confirmNewPassword;

	public String getProfileFieldPlaceholder(String fieldId) {
		return driver.findElement(By.id(fieldId)).getAttribute("placeholder");
	}

	public String getProfileFieldValue(String fieldId) {
		return driver.findElement(By.id(fieldId)).getAttribute("value");
	}

	public String getPrivilegesValue() {
		return profile_privileges.getAttribute("value");
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

	public List<String> getExpectedAppsFromModQuery(String username, String pcDbName) throws Exception {
		log.info("Checking dynamic app visibility for user: {} using mod_id query", username);
		utilities.DatabaseBackupUtil.setActiveDb(pcDbName);

		String sql = "select mod_id from security_group where id in(SELECT security_group_id FROM public.user_security_group where party_id=(select id from party where ip=?))";
		List<String> modIds = utilities.DatabaseBackupUtil.getMultipleValuesFromDB(sql, username);

		List<String> expectedApps = new java.util.ArrayList<>();
		for (String modId : modIds) {
			if ("1".equals(modId)) {
				if (!expectedApps.contains("MASTERS")) {
					expectedApps.add("MASTERS");
				}
			} else if ("2".equals(modId)) {
				if (!expectedApps.contains("MM (Material Management)")) {
					expectedApps.add("MM (Material Management)");
				}
			}
		}

		if (expectedApps.isEmpty()) {
			log.info("No mod_ids found or matched. Defaulting to show both MASTERS and MM.");
			expectedApps.add("MASTERS");
			expectedApps.add("MM (Material Management)");
		}
		log.info("Expected apps from mod_id query: {}", expectedApps);
		return expectedApps;
	}

}
