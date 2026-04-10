package pageObjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import utilities.DatabaseBackupUtil;
import utilities.ScreenshotUtil;
import utilities.HelperMethods;

public class BasePage extends HelperMethods {

	public BasePage(WebDriver driver) {
		super(driver);
	}

	public String currentUsername;
	public String currentPassword;

	/*
	 * ========================================================================= [
	 * LOCATORS ]
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

	@FindBy(xpath = "//button[normalize-space()='Update'] | //span[normalize-space()='Update']")
	protected WebElement updateButton;

	@FindBy(xpath = "//button[normalize-space()='Search']")
	protected WebElement searchButton;

	@FindBy(css = "input[autocomplete='username']")
	protected WebElement txt_username;

	@FindBy(id = "password")
	protected WebElement txt_password;

	@FindBy(xpath = "//button[normalize-space()='Submit']")
	protected WebElement btn_submit;

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

	@FindBy(xpath = "//input[@formcontrolname='remarks'] | //textarea[@formcontrolname='remarks'] ")
	protected WebElement txt_remarks;

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

	@FindBy(xpath = "//li//span[normalize-space()='Return'] | //button[normalize-space()='Return']")
	protected WebElement returnButton;

	@FindBy(xpath = "//li//span[normalize-space()='Review'] | //button[normalize-space()='Review']")
	protected WebElement reviewButton;

	@FindBy(xpath = "//li//span[normalize-space()='Inactive'] | //button[normalize-space()='Inactive'] | //li//span[normalize-space()='In Active']")
	protected WebElement inactiveButton;

	@FindBy(xpath = "//li//span[normalize-space()='Active'] | //button[normalize-space()='Active']")
	protected WebElement activeButton;

	@FindBy(xpath = "//li//span[normalize-space()='Add NDC']")
	protected WebElement addndcButton;

	

	@FindBy(xpath = "//li//span[normalize-space()='Reject'] | //button[normalize-space()='Reject']")
	protected WebElement rejectButton;

	@FindBy(xpath = "//button[normalize-space()='Add'] | //span[normalize-space()='Add']")
	protected WebElement addButton;

	@FindBy(xpath = "//div[contains(@class,'modal-content')]")
	protected WebElement authenticatepopup;

	@FindBy(xpath = "//h5[contains(@class,'modal-title')]//span[text()='Authenticate']")
	protected WebElement authTitle;

	@FindBy(xpath = "//i[contains(@class,'fa-eye')]")
	protected WebElement passwordeyeIcon;

	@FindBy(xpath = "//button[normalize-space()='Authenticate']")
	protected WebElement btn_authenticate;

	@FindBy(xpath = "//div//button[@aria-label='Close']")
	protected WebElement authenticatepopup_close;

	@FindBy(xpath = "//div[contains(@class,'modal-content')]//label")
	protected List<WebElement> authPopupLabels;

	@FindBy(xpath = "//div[contains(@class,'modal-content')]//input")
	protected List<WebElement> authPopupInputs;

	@FindBy(xpath = "//div[contains(@class,'custom-panel-content')]")
	protected WebElement filterContentBlock;

	String rowXapth = "//tbody[@role='rowgroup']/tr";
	String pendingrowXapth = "(//div[@class='card table-data'])[1]//tbody[@role='rowgroup']/tr";
	String activerowXapth = "(//div[@class='card table-data'])[2]//tbody[@role='rowgroup']/tr";
	String paginatorButtonPrefix = "//span[@class='p-paginator-pages ng-star-inserted']/button";
	String pendingPaginatorButtonPrefix = "(//div[@class='card table-data'])[1]//span[@class='p-paginator-pages ng-star-inserted']/button";
	String activePaginatorButtonPrefix = "(//div[@class='card table-data'])[2]//span[@class='p-paginator-pages ng-star-inserted']/button";
	String viewIconXpath = "//*[contains(@class,'pi-eye')]";
	String editIconXpath = "//*[contains(@class,'be-pencil') or contains(@class,'pi-pencil')]";
	String actionsIconXpath = "//span[contains(@class,'pi-ellipsis-v')]";

	String sqlQueryToDeleteUser = "DELETE FROM user_session WHERE user_id = (SELECT id FROM sys_user WHERE login = ?)";

	@FindBy(xpath = "content/images/maithri.png")
	WebElement maithriLogoImage;

	public WebElement getMaithriLogoImage() {
		return maithriLogoImage;
	}

	/*
	 * ========================================================================= [
	 * BASIC ACTIONS ]
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

	public void clickCancel() {
		scrollAndClick(cancelButton);
	}

	public void clickSubmit() {
		scrollAndClick(btn_submit);

	}

	public WebElement getSubmitButton() {
		return wait.until(ExpectedConditions.elementToBeClickable(btn_submit));
	}

	public void clickUpdate() throws Throwable {
		scrollAndClickWithPageDown(updateButton);

	}

	public void clickSearch() {
		scrollAndClick(searchButton);

	}

	public WebElement getUpdateButton() {
		return wait.until(ExpectedConditions.elementToBeClickable(updateButton));
	}

	public void masterClick(String mastername) {
		WebElement master = wait.until(
				ExpectedConditions.elementToBeClickable(By.xpath("//a[normalize-space()='" + mastername + "']")));
		waitForElementandClick(master);
	}

	public void enterRemarks(String remarks) {
		waitAndSendKeys(txt_remarks, remarks);
	}

	public void closeAuthenticatePopup() {
		waitForElementandClick(authenticatepopup_close);
	}

	public void waitForAuthPopupInvisibility() {
		try {
			wait.until(ExpectedConditions.invisibilityOf(authenticatepopup));
		} catch (Exception e) {
			log.warn("Auth popup did not become invisible within timeout");
		}
	}

	public void passWord(String password) {
		waitAndSendKeys(txt_password, password);
	}

	public void authenticateButton() {
		waitForElementandClick(btn_authenticate);
	}

	/*
	 * ========================================================================= [
	 * BUSINESS ACTIONS ]
	 * =========================================================================
	 */

	public void login(String username, String password, String dbname) throws Throwable {
		this.currentUsername = username;
		this.currentPassword = password;
		DatabaseBackupUtil.setActiveDb(dbname);
		DatabaseBackupUtil.executeUpdate(sqlQueryToDeleteUser, username);
		log.info("Logging in as: {}", username);
		ScreenshotUtil.nextStep();
		waitAndSendKeys(txt_username, username);
		waitAndSendKeys(txt_password, password);
		waitForElementandClick(btn_login);
		String Toast = waitForToast();
		ScreenshotUtil.capture();
		waitForLoading();
		Assert.assertTrue(
			    Toast.equals("Login successful") || Toast.equals("Please Change the Password"),
			    "Creation failed with message: " + Toast
			);
		ScreenshotUtil.capture();
	}

	public void login(String username, String password) throws Throwable {
		this.currentUsername = username;
		this.currentPassword = password;
		waitAndSendKeys(txt_username, username);
		waitAndSendKeys(txt_password, password);
		ScreenshotUtil.capture();
		waitForElementandClick(btn_login);
		waitForToast();
		ScreenshotUtil.capture();
	}

	public void switchUser(String username, String password, String dbname, String... modulePath) throws Throwable {
		if (username.equalsIgnoreCase(currentUsername)) {
			log.info("Already logged in as {}, skipping switch.", username);
			return;
		}
		log.info("--- Switching User to: {} ---", username);
		logout();
		log.info("Logged out previous user");
		waitForLoading();
		login(username, password, dbname);
		click_titleMasters();
		ScreenshotUtil.capture();
		waitForLoading();

		for (String module : modulePath) {
			if (module != null && !module.trim().isEmpty()) {
				masterClick(module.trim());
				log.info("Navigated to Module: {}", module);
				waitForLoading();
			}
		}

		ScreenshotUtil.capture();

	}

	public void authenticate(String password) throws Throwable {
		waitAndSendKeys(txt_password, password);
		log.info("Entered authentication password");
		ScreenshotUtil.capture();
		waitForElementandClick(btn_authenticate);
		log.info("Clicked Authenticate");
		waitForToast();
		ScreenshotUtil.capture();
	}

	public void authenticateWithoutScreenshot(String password) throws Throwable {
		waitAndSendKeys(txt_password, password);
		log.info("Entered authentication password");
		waitForElementandClick(btn_authenticate);
		log.info("Clicked Authenticate");
	}

	public void logout() throws Throwable {
		waitForElementandClick(profile_dropdown);
		waitForElementToVisible(logout_item);
		ScreenshotUtil.capture();
		new Actions(driver).moveToElement(logout_item).perform();
		logout_item.click();
		ScreenshotUtil.capture();
	}

	public void Create() throws Throwable {
		scrollAndClick(btn_create);
	}

	public void clickView(String... expItemNames) throws Exception {
		performTableActionGeneric(pageCountElements, rowXapth, paginatorButtonPrefix, viewIconXpath, expItemNames);
	}
	public void clickPendingView(String... expItemNames) throws Exception {
		performTableActionGeneric(pageCountElements, pendingrowXapth, pendingPaginatorButtonPrefix, viewIconXpath, expItemNames);
	}
	
	public void clickActiveView(String... expItemNames) throws Exception {
		performTableActionGeneric(pageCountElements, activerowXapth, activePaginatorButtonPrefix, viewIconXpath, expItemNames);
	}
	public void clickEdit(String... expItemNames) throws Exception {
		performTableActionGeneric(pageCountElements, rowXapth, paginatorButtonPrefix, editIconXpath, expItemNames);
	}

	public void clickActions(String... expItemNames) throws Exception {
		performTableActionGeneric(pageCountElements, rowXapth, paginatorButtonPrefix, actionsIconXpath, expItemNames);
	}

	public void clickApprove() {
		clickVisibleMenuByText("Approve");
	}
	

	public void clickReview() {
		clickVisibleMenuByText("Review");
	}

	public void clickReturn() {
		clickVisibleMenuByText("Return");
	}

	public void clickInactive() {
		try {
			WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(inactiveButton));
			jsScrollToElement(btn);
			clickWithArrow(btn);
			jsClick(btn);
		} catch (Exception e) {
			log.error("Inactive button not found/clickable");
		}
	}

	private void clickVisibleMenuByText(String text) {
		try {
			String xpath = "//li[contains(@class,'p-menuitem')]//span[normalize-space()='" + text
					+ "'] | //button[normalize-space()='" + text + "']";
			List<WebElement> elements = driver.findElements(By.xpath(xpath));
			boolean clicked = false;
			for (WebElement el : elements) {
				if (el.isDisplayed()) {
					clickWithArrow(el);
					jsClick(el);
					log.info("Clicked visible menu item: {}", text);
					clicked = true;
					break;
				}
			}
			if (!clicked) {
				log.error("No visible '{}' menu item found among {} matches", text, elements.size());
			}
		} catch (Exception e) {
			log.error("Exception clicking '{}' menu item: {}", text, e.getMessage());
		}
	}

	public void clickInactiveReview() {
		clickVisibleMenuByText("Review");
	}

	public void clickInactiveApprove() {
		clickVisibleMenuByText("Approve");
	}

	public void clickActive() {
		try {
			WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(activeButton));
			jsScrollToElement(btn);
			clickWithArrow(btn);
			jsClick(btn);
		} catch (Exception e) {
			log.error("Active button not found/clickable");
		}
	}

	public void clickAddNDC() {
		try {
			WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(addndcButton));
			jsScrollToElement(btn);
			clickWithArrow(btn);
			jsClick(btn);
		} catch (Exception e) {
			log.error("Add NDC button not found/clickable");
		}
	}

	public void clickActiveReview() {
		clickVisibleMenuByText("Review");
	}

	public void clickActiveApprove() {
		clickVisibleMenuByText("Approve");
	}

	

	public void clickReject() {
		clickVisibleMenuByText("Reject");
	}

	public void clickAddButton() {
		waitForElementandClick(addButton);
	}

	public void clickAddStage() {
		clickVisibleElementByText("Add Stage");
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

	public boolean isAuthPopupDisplayed() {
		try {
			return authenticatepopup.isDisplayed();
		} catch (Exception e) {
			return false;
		}

	}

	public boolean isAuthPopupTitleDisplayed() {
		try {
			return authTitle.isDisplayed();
		} catch (Exception e) {
			return false;
		}

	}

	public boolean isAuthPopupPasswordDisplayed() {
		try {
			return txt_password.isDisplayed();
		} catch (Exception e) {
			return false;
		}

	}

	public boolean isAuthButtondDisplayed() {
		try {
			return btn_authenticate.isDisplayed();
		} catch (Exception e) {
			return false;
		}

	}

	public boolean isAuthPopupClosedDisplayed() {
		try {
			return authenticatepopup_close.isDisplayed();
		} catch (Exception e) {
			return false;
		}

	}

	public boolean isAuthPasswordEyeiconDisplayed() {
		try {
			return passwordeyeIcon.isDisplayed();
		} catch (Exception e) {
			return false;
		}

	}

	public String getAuthPopupTitle() {
		return wait.until(ExpectedConditions.visibilityOf(authTitle)).getText().trim();
	}

	public String getAuthPasswordPlaceholder() {
		return wait.until(ExpectedConditions.visibilityOf(txt_password)).getAttribute("placeholder");
	}

	public String getAuthButtonText() {
		return wait.until(ExpectedConditions.visibilityOf(btn_authenticate)).getText().trim();
	}

	public String getAuthPasswordFieldType() {
		return wait.until(ExpectedConditions.visibilityOf(txt_password)).getAttribute("type");
	}

	public void clickAuthPasswordEyeIcon() {
		waitForElementandClick(passwordeyeIcon);
	}

	public int getAuthPopupLabelCount() {
		return authPopupLabels.size();
	}

	public int getAuthPopupInputCount() {
		return authPopupInputs.size();
	}

	public String getAuthPopupInputTagName() {
		return txt_password.getTagName();
	}

	public boolean isAuthPopupPasswordFieldEnabled() {
		return txt_password.isEnabled();
	}

	public boolean isFilterPanelExpanded() {
		try {

			String style = filterContentBlock.getAttribute("style");
			return style != null && style.contains("opacity: 1");
		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * ========================================================================= [
	 * GETTERS / HELPERS ]
	 * =========================================================================
	 */

	public WebElement getSearchBox() {
		return searchbox;
	}

	public List<String> getBreadcrumbsText() {
		return breadcrumbItems.stream().map(e -> e.getText().trim()).filter(t -> !t.isEmpty())
				.collect(Collectors.toList());
	}

	public List<WebElement> getBreadcrumbArrows() {
		return breadcrumbArrows;
	}

	public WebElement getHomeIcon() {
		return homeIcon;
	}

	public List<String> getDisplayedFieldLabels() {
		return allLabels.stream().filter(WebElement::isDisplayed).map(e -> e.getText().trim()).filter(t -> !t.isEmpty())
				.collect(Collectors.toList());
	}

	public List<String> getDisplayedButtons() {
		return allButtons.stream().filter(WebElement::isDisplayed).map(e -> e.getText().trim())
				.filter(t -> !t.isEmpty()).collect(Collectors.toList());
	}

	public String getStatus(String name) throws Exception {
		return super.getStatus(name);
	}

	public boolean isLabelDisplayed(String labelText) {
		try {
			return wait
					.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//label[normalize-space()='" + labelText + "']")))
					.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public WebElement getInputFieldForLabel(String labelText) {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[normalize-space()='"
				+ labelText + "']/following-sibling::*//input | //label[normalize-space()='" + labelText
				+ "']/following-sibling::input")));

	}

	public String getFieldType(String labelText) {
		try {
			WebElement element = wait
					.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[normalize-space()='"
							+ labelText + "']/following-sibling::*//input | //label[normalize-space()='" + labelText
							+ "']/following-sibling::input")));
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
			WebElement element = wait
					.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[normalize-space()='"
							+ labelText + "']/following-sibling::*//input | //label[normalize-space()='" + labelText
							+ "']/following-sibling::input")));
			return element.getAttribute("placeholder");
		} catch (Exception e) {
			return "";
		}
	}

	public WebElement getErrorMessage(String fieldname) {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[contains(text(),'" + fieldname
				+ "')]/ancestor::div[contains(@class,'form-group')]//validation-message")));
	}

	public WebElement isRedStarDisplayedForField(String fieldname) {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[contains(text(),'" + fieldname
				+ "')]/following-sibling::sys-validation-star/span[@class='not-valid']")));
	}

	public WebElement isGreenStarDisplayedForField(String fieldname) {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[contains(text(),'" + fieldname
				+ "')]/following-sibling::sys-validation-star/span[@class='valid']")));
	}

	public WebElement getFilterFieldByLabel(String labelName) {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
				"//label[normalize-space()='" + labelName + "']/following-sibling::input | //label[normalize-space()='"
						+ labelName + "']/following-sibling::*/input")));
	}

	public WebElement getFilterButton(String buttonText) {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//div[@class='filter-buttons']//button[contains(text(),'" + buttonText + "')]")));
	}

	public boolean isCreateButtonDisplayed() {
		try {
			return wait
					.until(ExpectedConditions.visibilityOfElementLocated(
							By.xpath("//button[contains(text(),'Create') or contains(@class,'create')]")))
					.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public WebElement getFilterToggleButton() {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//div[contains(@class,'toggle-icon')]//span[contains(@class,'be-filter')]")));
	}

	public void clickFilterToggle() {
		getFilterToggleButton().click();
	}

	public WebElement getCreateButton() {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//button[contains(text(),'Create') or contains(@class,'create')]")));
	}

	public WebElement getPreferencesDropdown() {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//div[@class='dropdown preference-dropdown']//p[@class='dropdown-trigger']")));
	}

	public String getItemCountText() {
		return driver.findElement(By.xpath("//div[@class='title']")).getText().trim();
	}

	public WebElement getPDFButton() {
		return wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//span[@class='dropdown-trigger pdf-button']")));
	}

	public WebElement getExcelButton() {
		return wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//span[@class='dropdown-trigger excel-button']")));
	}

	public List<String> getTableHeaders() {
		return driver.findElements(By.xpath("//thead[@class='p-datatable-thead']//th")).stream()
				.map(e -> e.getText().trim()).filter(t -> !t.isEmpty()).collect(Collectors.toList());
	}

	public WebElement getPaginationButton(String buttonType) {
		String xpath = "";
		switch (buttonType) {
			case "First":
				xpath = "//button[contains(@class,'p-paginator-first')]";
				break;
			case "Previous":
				xpath = "//button[contains(@class,'p-paginator-prev')]";
				break;
			case "Next":
				xpath = "//button[contains(@class,'p-paginator-next')]";
				break;
			case "Last":
				xpath = "//button[contains(@class,'p-paginator-last')]";
				break;
		}
		return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
	}

	public WebElement getRowsPerPageDropdown() {
		return wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//div[contains(@class,'p-paginator-rpp-options')]")));
	}

	public String getCurrentPageInfo() {
		return wait
				.until(ExpectedConditions
						.visibilityOfElementLocated(By.xpath("//span[contains(@class,'p-paginator-current')]")))
				.getText().trim();
	}

	public int getTableRowCount() {
		return driver.findElements(By.xpath("//tbody[@role='rowgroup']/tr")).size();
	}

	public int extractTotalItemsCount() {
		try {
			String[] parts = getItemCountText().split(" ");
			return Integer.parseInt(parts[0].trim());
		} catch (Exception e) {
			return -1;
		}
	}

	public int getResultsPerPageValue() {
		try {
			return Integer.parseInt(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
					"//p-dropdown[@styleclass='p-paginator-rpp-options']//span[contains(@class,'p-dropdown-label')]")))
					.getText().trim());
		} catch (Exception e) {
			return -1;
		}
	}

	public List<Integer> getDisplayedPageNumbers() {
		return driver.findElements(By.xpath("//span[@class='p-paginator-pages ng-star-inserted']/button")).stream()
				.map(e -> {
					try {
						return Integer.parseInt(e.getText().trim());
					} catch (Exception ex) {
						return null;
					}
				}).filter(n -> n != null).collect(Collectors.toList());
	}

	public int calculateExpectedPages(int totalItems, int resultsPerPage) {
		if (resultsPerPage <= 0)
			return 0;
		return (int) Math.ceil((double) totalItems / resultsPerPage);
	}

	public int[] parseRangeInfo() {
		try {
			String[] parts = getCurrentPageInfo().split(" ");
			return new int[] { Integer.parseInt(parts[0]), Integer.parseInt(parts[2]), Integer.parseInt(parts[4]) };
		} catch (Exception e) {
			return new int[] { -1, -1, -1 };
		}
	}

	public int getCurrentPageNumber() {
		try {
			return Integer.parseInt(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
					"//span[@class='p-paginator-pages ng-star-inserted']/button[contains(@class,'p-highlight')]")))
					.getText().trim());
		} catch (Exception e) {
			return 1;
		}
	}

	public boolean isPaginationPresent() {
		return driver.findElements(By.xpath("//span[@class='p-paginator-pages ng-star-inserted']/button")).size() > 1;
	}

	public List<Map<String, String>> getUserActivityDetailsUI() {
		List<Map<String, String>> uiDetails = new ArrayList<>();
		try {
			String headingXpath = "//*[normalize-space()='User Activity Details']";
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(headingXpath)));
			List<WebElement> allLabels = driver
					.findElements(By.xpath("//*[normalize-space()='User Activity Details']/following::label"));
			for (WebElement labelElement : allLabels) {
				String label = labelElement.getText().trim();
				if (label.isEmpty())
					continue;
				String value = "";
				try {
					List<WebElement> inputs = labelElement.findElements(By.xpath(
							"./following-sibling::input | ./following-sibling::*/input | ./following::input[1]"));
					if (!inputs.isEmpty())
						value = inputs.get(0).getAttribute("value");
					if (value == null || value.trim().isEmpty())
						value = labelElement.findElement(By.xpath("./following-sibling::*[1]")).getText().trim();
				} catch (Exception e) {
					try {
						value = labelElement.findElement(By.xpath("./following::*[1]")).getText().trim();
					} catch (Exception e2) {
					}
				}
				Map<String, String> fieldData = new java.util.LinkedHashMap<>();
				fieldData.put("label", label);
				fieldData.put("value", (value != null) ? value.trim() : "");
				uiDetails.add(fieldData);
			}
		} catch (Exception e) {
			log.error("Failed to capture User Activity Details from UI: {}", e.getMessage());
		}
		return uiDetails;
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

	public List<String> getExpectedSideNavFromDB(String username, String mapping, String dbName) throws Exception {
		log.info("Fetching expected side-nav modules for user: {} from DB: {}", username, dbName);
		utilities.DatabaseBackupUtil.setActiveDb(dbName);
		String sql = "select mod_id from security_group where id in(SELECT security_group_id FROM public.user_security_group where party_id=(select id from party where ip=?))";
		List<String> modIds = utilities.DatabaseBackupUtil.getMultipleValuesFromDB(sql, username);
		List<String> expectedModules = new ArrayList<>();
		String[] pairs = mapping.split(";");
		for (String pair : pairs) {
			String[] parts = pair.split(":");
			if (parts.length < 2)
				continue;
			String moduleName = parts[0].trim();
			String assignedModId = parts[1].trim();
			if (assignedModId.equalsIgnoreCase("all") || modIds.contains(assignedModId)) {
				expectedModules.add(moduleName);
			}
		}
		return expectedModules;
	}

	public List<String> getAllSideNavModulesDisplayed1() {
		List<String> modules = new ArrayList<>();
		// Capturing text from both <a> and <span> to handle different module structures
		// in accordion
		List<WebElement> menuItems = driver
				.findElements(By.xpath("//ul[@id='accordion']//li//*[self::a or self::span][normalize-space()!='']"));
		for (WebElement item : menuItems) {
			if (item.isDisplayed()) {
				String text = item.getText().trim();
				if (!text.isEmpty() && !modules.contains(text)) {
					modules.add(text);
				}
			}
		}
		log.info("Actual Side-Nav modules visible on UI: {}", modules);
		return modules;
	}

	public void expandSideNavModule(String parentModule) {
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//li[contains(@class,'nav-item')]//span[normalize-space()='" + parentModule + "']")));
		scrollAndClick(element);
		waitForLoading();
	}

	public void expandSideNavModule1(String moduleName) {

		try {
			log.info("Expanding Side-Nav module: {}", moduleName);
			// Updated locator to support accordion structure with <a> tags
			WebElement main = driver.findElement(By.xpath("//ul[@id='accordion']//li[.//a[normalize-space()='"
					+ moduleName + "']] | //aside//li[.//span[normalize-space()='" + moduleName
					+ "']] | //nav//li[.//span[normalize-space()='" + moduleName + "']]"));
			String classes = main.getAttribute("class");
			if (!classes.contains("open") && !classes.contains("active") && !classes.contains("expanded")) {
				// Click the link associated with the module name to toggle expansion
				scrollAndClick(main.findElement(By.xpath(".//a[normalize-space()='" + moduleName + "']")));
				waitForLoading();
			}

		} catch (Exception e) {
			log.warn("Could not expand side-nav module: {}. (Maybe it has no sub-menu or locator mismatch)",
					moduleName);
		}
	}

	public List<String> getAllSideNavModulesDisplayed() {
		return driver.findElements(By.xpath("//li[contains(@class,'nav-item')]//span")).stream()
				.map(e -> e.getText().trim()).filter(t -> !t.isEmpty()).collect(Collectors.toList());
	}
	
	
	
	
	
	
}
