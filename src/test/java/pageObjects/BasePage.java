package pageObjects;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import utilities.DatabaseBackupUtil;
import utilities.ScreenshotUtil;
import utilities.HelperMethods;

public class BasePage extends HelperMethods {

	public BasePage(WebDriver driver) {
		super(driver);
	}

	/*
	 * =========================================================================
	 * [ LOCATORS ]
	 * =========================================================================
	 */

	@FindBy(xpath = "//ul[contains(@class,'breadcrumb')]//li[contains(@class,'breadcrumb-item')]")
	protected List<WebElement> breadcrumbItems;

	@FindBy(xpath = "//ul[contains(@class,'breadcrumb')]//li//i[contains(@class,'be-chevron-right')]")
	protected List<WebElement> breadcrumbArrows;

	@FindBy(xpath = "//span[contains(@class,'be-home')] | //i[contains(@class,'be-home')]")
	protected WebElement homeIcon;

	@FindBy(xpath = "//ul[contains(@class,'breadcrumb')]//a[contains(@href,'/home')]")
	protected WebElement homeBreadcrumbLink;

	@FindBy(xpath = "//button[normalize-space()='Cancel'] | //button[contains(@class,'cancel')]")
	protected WebElement cancelButton;

	@FindBy(xpath = "//button[normalize-space()='Back'] | //button[contains(@class,'back')]")
	protected WebElement backButton;

	@FindBy(css = "input[autocomplete='username']")
	protected WebElement txt_username;

	@FindBy(id = "password")
	protected WebElement txt_password;

	@FindBy(xpath = "//button[@type='submit']")
	protected WebElement btn_login;

	@FindBy(xpath = "//button[@class='profile-btn']/i")
	protected WebElement profile_dropdown;

	@FindBy(xpath = "//li[normalize-space()='Logout']")
	protected WebElement logout_item;

	@FindBy(xpath = "//h5[normalize-space()='MASTERS']")
	protected WebElement title_masters;

	@FindBy(xpath = "//h5[normalize-space()='MM (Material Management)']")
	protected WebElement title_mm;

	@FindBy(xpath = "//button[contains(text(),'Create')] | //span[normalize-space()='Create']")
	protected WebElement btn_create;

	@FindBy(name = "q")
	protected WebElement searchbox;

	@FindBy(xpath = "//label")
	protected List<WebElement> allLabels;

	@FindBy(xpath = "//button")
	protected List<WebElement> allButtons;

	@FindBy(xpath = "//span[@class='p-paginator-pages ng-star-inserted']/button")
	protected List<WebElement> pageCountElements;

	@FindBy(xpath = "//li//span[normalize-space()='Approve'] | //button[normalize-space()='Approve']")
	protected WebElement approveButton;

	String rowXapth = "//tbody[@role='rowgroup']/tr";
	String paginatorButtonPrefix = "//span[@class='p-paginator-pages ng-star-inserted']/button";
	String viewIconXpath = "//*[contains(@class,'pi-eye')]";
	String editIconXpath = "//*[contains(@class,'be-pencil') or contains(@class,'pi-pencil')]";
	String actionsIconXpath = "//span[contains(@class,'pi-ellipsis-v')]";

	/*
	 * =========================================================================
	 * [ BASIC ACTIONS ]
	 * =========================================================================
	 */

	public void searchBox(String url) throws Exception {
		waitAndSendKeys(searchbox, url);
	}

	public void click_titleMasters() {
		waitForElementandClick(title_masters);
	}

	public void click_titleMM() {
		waitForElementandClick(title_mm);
	}

	public void clearLoginFields() {
		clearField(txt_username);
		clearField(txt_password);
		log.info("Cleared login fields UI");
	}

	public void clickBack() {
		scrollAndClick(backButton);
	}

	public void HomeBreadcrumbLink() {
		scrollAndClick(homeBreadcrumbLink);
	}

	/*
	 * =========================================================================
	 * [ BUSINESS ACTIONS ]
	 * =========================================================================
	 */

	public void login(String username, String password, String dbname) throws Throwable {
		DatabaseBackupUtil.setActiveDb(dbname);
		DatabaseBackupUtil.executeUpdate(
				"DELETE FROM user_session WHERE user_id = (SELECT id FROM sys_user WHERE login = ?)", username);
		log.info("Logging in as: {}", username);
		clearLoginFields();
		waitAndSendKeys(txt_username, username);
		waitAndSendKeys(txt_password, password);
		ScreenshotUtil.capture();
		waitForElementandClick(btn_login);
		waitForToast();
		ScreenshotUtil.capture();
	}

	public void logout() {
		waitForElementandClick(profile_dropdown);
		waitForElementToVisible(logout_item);
		new org.openqa.selenium.interactions.Actions(driver).moveToElement(logout_item).perform();
		logout_item.click();
	}

	public void Create() throws Throwable {
		scrollAndClick(btn_create);
	}

	public void clickView(String... expItemNames) throws Exception {
		performTableActionGeneric(
				pageCountElements,
				rowXapth, paginatorButtonPrefix,
				viewIconXpath, expItemNames);
	}

	public void clickEdit(String... expItemNames) throws Exception {
		performTableActionGeneric(
				pageCountElements,
				rowXapth, paginatorButtonPrefix,
				editIconXpath, expItemNames);
	}

	public void clickActions(String... expItemNames) throws Exception {
		performTableActionGeneric(
				pageCountElements,
				rowXapth, paginatorButtonPrefix,
				actionsIconXpath, expItemNames);
	}

	public void clickApprove() {
		try {
			WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(approveButton));
			jsScrollToElement(btn);
			jsClick(btn);
		} catch (Exception e) {
			log.error("Approve button not found/clickable");
		}
	}

	public void validateIcon(boolean expected, String iconClass, String... name) throws Exception {
		scanTable(rowXapth, paginatorButtonPrefix, TableActionType.CHECK_VISIBILITY,
				"//*[contains(@class,'" + iconClass + "')]", expected, name);
	}

	public void validateBreadcrumbs(List<String> expected) {
		List<String> actual = getBreadcrumbsText();
		if (!actual.equals(expected)) {
			throw new RuntimeException("Breadcrumbs mismatch. Exp: " + expected + ", Found: " + actual);
		}
		log.info("✓ Breadcrumbs Match: " + actual);
	}

	/*
	 * =========================================================================
	 * [ GETTERS / HELPERS ]
	 * =========================================================================
	 */

	public WebElement getSearchBox() {
		return searchbox;
	}

	public List<String> getBreadcrumbsText() {
		return breadcrumbItems.stream()
				.map(e -> e.getText().trim())
				.filter(t -> !t.isEmpty())
				.collect(Collectors.toList());
	}

	public List<String> getDisplayedFieldLabels() {
		return allLabels.stream()
				.filter(WebElement::isDisplayed)
				.map(e -> e.getText().trim())
				.filter(t -> !t.isEmpty())
				.collect(Collectors.toList());
	}

	public List<String> getDisplayedButtons() {
		return allButtons.stream()
				.filter(WebElement::isDisplayed)
				.map(e -> e.getText().trim())
				.filter(t -> !t.isEmpty())
				.collect(Collectors.toList());
	}

	public boolean isLabelDisplayed(String labelText) {
		try {
			return driver.findElement(By.xpath("//label[normalize-space()='" + labelText + "']")).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public String getFieldType(String labelText) {
		try {
			WebElement element = driver.findElement(By.xpath("//label[normalize-space()='" + labelText
					+ "']/following-sibling::*//input | //label[normalize-space()='" + labelText
					+ "']/following-sibling::input"));
			String tagName = element.getTagName();
			String className = element.getAttribute("class");
			if (tagName.equalsIgnoreCase("input"))
				return "input";
			if (className.contains("dropdown"))
				return "drop";
			return tagName;
		} catch (Exception e) {
			return "unknown";
		}
	}

	public String getPlaceholder(String labelText) {
		try {
			WebElement element = driver.findElement(By.xpath("//label[normalize-space()='" + labelText
					+ "']/following-sibling::*//input | //label[normalize-space()='" + labelText
					+ "']/following-sibling::input"));
			return element.getAttribute("placeholder");
		} catch (Exception e) {
			return "";
		}
	}

	public WebElement getErrorMessage(String fieldname) {
		return driver.findElement(By.xpath("//label[contains(text(),'" + fieldname
				+ "')]/ancestor::div[contains(@class,'form-group')]//validation-message"));
	}

	public WebElement isRedStarDisplayedForField(String fieldname) {
		return driver
				.findElement(By.xpath("//label[contains(text(),'" + fieldname + "')]//span[contains(@class,'star')]"));
	}

	public WebElement isGreenStarDisplayedForField(String fieldname) {
		return driver.findElement(By.xpath("//label[contains(text(),'" + fieldname
				+ "')]//span[contains(@class,'valid') and not(contains(@class,'not-valid'))]"));
	}
}
