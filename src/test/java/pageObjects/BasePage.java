package pageObjects;

import java.time.Duration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utilities.ScreenshotUtil;

public class BasePage {
	WebDriver driver;
	WebDriverWait wait;

	private final Logger log = LogManager.getLogger(this.getClass());
	private String[] tableHeaders = null; // Default null implies "Legacy Mode" (Column 1)

	public BasePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, Duration.ofSeconds(5));
	}

	protected void waitForElementandClick(WebElement element) {
		/*
		 * try { // Optimized: Check for loader presence with 0 implicit wait to avoid
		 * delays driver.manage().timeouts().implicitlyWait(Duration.ZERO);
		 * List<WebElement> loaders =
		 * driver.findElements(By.cssSelector("div.loader-container")); if
		 * (!loaders.isEmpty() && loaders.get(0).isDisplayed()) { // Only if loader
		 * exists and is visible, restore default wait and wait for it to // disappear
		 * driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		 * wait.until(ExpectedConditions.invisibilityOf(loaders.get(0))); } else { //
		 * Restore default implicit wait immediately if no loader found
		 * driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); } } catch
		 * (Exception e) { // Ensure defaults are restored in case of error
		 * driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); }
		 */

		// Standard element interaction
		wait.until(ExpectedConditions.visibilityOf(element));
		System.out.println("found on element");

		wait.until(ExpectedConditions.elementToBeClickable(element)).click();
		System.out.println("clicked on element");
	}

	public void toast() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.p-toast-detail")));
	}

	public WebElement waitForCreateButton() {
		try {
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.loader-container")));
		} catch (TimeoutException e) {
			System.out.println("Loader still visible, but proceeding...");
		}
		return wait.until(ExpectedConditions.elementToBeClickable(create));
	}

	@FindBy(xpath = "//span[@class='p-paginator-pages ng-star-inserted']")
	WebElement page_button;

	public WebElement waitForPageButton() {
		try {
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.loader-container")));
		} catch (TimeoutException e) {
			System.out.println("Loader still visible, but proceeding...");
		}
		return wait.until(ExpectedConditions.elementToBeClickable(page_button));
	}

	private void triggerInputAndChange(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].dispatchEvent(new Event('input', {bubbles: true}));", element);
		js.executeScript("arguments[0].dispatchEvent(new Event('change', {bubbles: true}));", element);
		js.executeScript("arguments[0].dispatchEvent(new Event('blur', {bubbles: true}));", element);
	}

	protected void waitAndSendKeys(WebElement element, String value) {
		wait.until(ExpectedConditions.visibilityOf(element));
		element.sendKeys(value);
		triggerInputAndChange(element);
	}

	public void waitForLoading() {
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.loader-container")));
	}

	@FindBy(name = "q")
	WebElement searchbox;

	public WebElement getSearchBox() {
		return searchbox;
	}

	public void searchBox(String url) throws Exception {
		waitAndSendKeys(searchbox, url);
	}

	@FindBy(css = "input[autocomplete='username']")
	protected WebElement txt_username;

	public void scrollAndClickWithPageDown(WebElement element) throws InterruptedException {

		Actions actions = new Actions(driver);
		int maxScrolls = 5;

		for (int i = 0; i < maxScrolls; i++) {
			actions.sendKeys(Keys.PAGE_DOWN).perform();
			Thread.sleep(300);

			if (element.isDisplayed()) {
				break;
			}
		}

		element.click();
	}

	public void scrollAndClick(WebElement element) {

		// Scroll element into view (centered)
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", element);

		// Wait until clickable
		wait.until(ExpectedConditions.elementToBeClickable(element));

		// Click using JS (safe for Angular)
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
	}

	public void userName(String username) throws Exception {
		waitAndSendKeys(txt_username, username);
		log.info("Entered username: {}", username);

	}

	@FindBy(id = "password")
	WebElement txt_password;

	public void passWord(String password) throws Exception {
		waitAndSendKeys(txt_password, password);
		log.info("Entered password: {}", password);
	}

	@FindBy(xpath = "//button[@type='submit']")
	WebElement btn_login;

	public void loginButton() {

		btn_login.click();
		log.info("Clicked on Login button");
	}

	@FindBy(xpath = "//h5[normalize-space()='MASTERS']")
	WebElement title_masters;

	public void click_titleMasters() {

		waitForElementandClick(title_masters);

	}

	@FindBy(xpath = "//h5[normalize-space()='MM (Material Management)']")
	WebElement title_mm;

	public void click_titleMM() {

		waitForElementandClick(title_mm);

	}

	@FindBy(xpath = "//span[normalize-space()='PDF']")
	WebElement click_pdf;

	public void click_PDF() {

		waitForElementandClick(click_pdf);

	}

	@FindBy(xpath = "//button[normalize-space()='Back']")
	WebElement click_back;

	public void clickBack() throws InterruptedException {
		scrollAndClickWithPageDown(click_back);
	}

	@FindBy(xpath = "//button[@class='profile-btn']/i")
	WebElement profile_dropdown;

	public void click_profileDropdown() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0, 0);");
		profile_dropdown.click();
	}

	@FindBy(xpath = "//li[normalize-space()='Logout']")
	WebElement logout;

	public void logout() {
		Actions actions = new Actions(driver);
		actions.moveToElement(logout).perform();
		logout.click();
	}

	public void masterClick(String mastername) throws Exception {
		WebElement xpath = wait.until(
				ExpectedConditions.elementToBeClickable(By.xpath("//a[normalize-space()='" + mastername + "']")));
		xpath.click();

	}

	@FindBy(xpath = "//span[normalize-space()='Create']")
	WebElement create;

	public void Create() throws Throwable {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0, 0);");
		waitForElementandClick(create);

	}

	@FindBy(xpath = "//button[@id='save-entity' or normalize-space()='Submit']")
	WebElement click_submit;

	public void createSubmit() throws InterruptedException  {
		scrollAndClickWithPageDown(click_submit);
	}

	public WebElement waitForSubmit() {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
				"//button[@id='save-entity'] | //button[@type='button'] | //button[normalize-space()='Update']")));

	}

	@FindBy(xpath = "//button[normalize-space()='Authenticate']")
	WebElement btn_authenticate;

	public void authenticateButton() {
		btn_authenticate.click();
	}

	@FindBy(xpath = "//span[@class='p-paginator-pages ng-star-inserted']/button")
	List<WebElement> page_count;

	@FindBy(xpath = "//div[@class='card table-data'][1]//span[@class='p-paginator-pages ng-star-inserted']/button")
	List<WebElement> pending_page_count;

	@FindBy(xpath = "//div[@class='card table-data'][2]//span[@class='p-paginator-pages ng-star-inserted']/button")
	List<WebElement> active_page_count;

	public void clickActions(String... expItemNames) throws Exception {
		performTableActionGeneric(page_count, "//tbody[@role='rowgroup']/tr",
				"//span[@class='p-paginator-pages ng-star-inserted']/button",
				"//span[contains(@class,'pi-ellipsis-v')]", expItemNames);

	}

	public void clickActions(String commaSeparatedValues) throws Exception {
		clickActions(splitAndTrim(commaSeparatedValues));
	}

	public void clickActionsForPendig(String expItemName) throws Exception {
		performTableAction(pending_page_count, "//div[@class='card table-data'][1]//tbody[@role='rowgroup']/tr",
				"//div[@class='card table-data'][1]//span[@class='p-paginator-pages ng-star-inserted']/button",
				expItemName, "//i[contains(@class,'pi-ellipsis-v')]");

	}

	public void clickActionsForActive(String expItemName) throws Exception {
		performTableAction(active_page_count, "//div[@class='card table-data'][2]//tbody[@role='rowgroup']/tr",
				"//div[@class='card table-data'][2]//span[@class='p-paginator-pages ng-star-inserted']/button",
				expItemName, "//i[contains(@class,'pi-ellipsis-v')]");

	}

	/*
	 * public int getColumnIndexByHeader(String headerName) {
	 * 
	 * List<WebElement> headers = driver.findElements( By.xpath("//thead//th"));
	 * 
	 * for (int i = 0; i < headers.size(); i++) { String headerText =
	 * headers.get(i).getText().trim();
	 * 
	 * if (headerText.equalsIgnoreCase(headerName)) { return i + 1; // XPath index
	 * starts from 1 } } throw new RuntimeException("Column not found: " +
	 * headerName); }
	 */

	/**
	 * CONFIGURATION METHOD Call this at the start of your test to define how to
	 * find rows. Example: department.setTableHeaders("Department Name"); Example 2:
	 * department.setTableHeaders("Department Name", "Dept Code");
	 */
	public void setTableHeaders(String... headers) {
		this.tableHeaders = headers;
	}

	/**
	 * Overloaded method to set table headers using a single comma-separated string.
	 * Useful for reading from properties files.
	 */
	public void setTableHeaders(String commaSeparatedHeaders) {
		if (commaSeparatedHeaders != null && !commaSeparatedHeaders.isEmpty()) {
			this.tableHeaders = commaSeparatedHeaders.split(",");
			// Trim each header to handle spaces around commas
			for (int i = 0; i < this.tableHeaders.length; i++) {
				this.tableHeaders[i] = this.tableHeaders[i].trim();
			}
		}
	}

	/**
	 * Internal helper to find column indices dynamically based on header names.
	 * Returns a list of indices (1-based for XPath usage).
	 */
	private List<Integer> getDynamicColumnIndices() {
		List<Integer> indices = new ArrayList<>();

		// If no headers configured, default to Column 1 (Legacy Support)
		if (tableHeaders == null || tableHeaders.length == 0) {
			indices.add(1);
			return indices;
		}

		// Locate all header elements
		List<WebElement> headerElements = driver.findElements(By.xpath("//thead//th"));

		for (String expectedHeader : tableHeaders) {
			boolean found = false;
			for (int i = 0; i < headerElements.size(); i++) {
				if (headerElements.get(i).getText().trim().equalsIgnoreCase(expectedHeader)) {
					indices.add(i + 1); // XPath 1-based index
					found = true;
					break;
				}
			}
			if (!found) {
				throw new RuntimeException("Header not found in table: " + expectedHeader);
			}
		}
		return indices;
	}

	/*
	 * public void clickActions(String expItemtName, String version) throws
	 * Exception {
	 * 
	 * Boolean isFound = Boolean.FALSE; int specNoCol =
	 * getColumnIndexByHeader("Specification Number"); int versionCol =
	 * getColumnIndexByHeader("Version");
	 * 
	 * for (int p = 1; p <= page_count.size(); p++) {
	 * System.out.println("Number of pages" + page_count.size());
	 * wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(
	 * "//tbody[@role='rowgroup']/tr"))); List<WebElement> item_list =
	 * driver.findElements(By.xpath("//tbody[@role='rowgroup']/tr")); for (int r =
	 * 1; r <= item_list.size(); r++) { WebElement specCell = driver.findElement(
	 * By.xpath("//tbody[@role='rowgroup']/tr[" + r + "]/td[" + specNoCol + "]"));
	 * WebElement versionCell = driver.findElement(
	 * By.xpath("//tbody[@role='rowgroup']/tr[" + r + "]/td[" + versionCol + "]"));
	 * ((JavascriptExecutor)
	 * driver).executeScript("arguments[0].scrollIntoView({block:'center'});",
	 * specCell); ((JavascriptExecutor)
	 * driver).executeScript("arguments[0].scrollIntoView({block:'center'});",
	 * versionCell); String specitemName = specCell.getText().trim(); String
	 * versionItem = versionCell.getText().trim();
	 * 
	 * System.out.println("Expected: [" + expItemtName.trim() + "]");
	 * System.out.println("Actual:   [" + specitemName.trim() + "]");
	 * System.out.println("Equal? " +
	 * specitemName.equalsIgnoreCase(expItemtName.trim()));
	 * 
	 * System.out.println("Expected Version: [" + version.trim() + "]");
	 * System.out.println("Actual Version:   [" + versionItem.trim() + "]");
	 * System.out.println("Version Equal? " +
	 * versionItem.equalsIgnoreCase(version.trim()));
	 * 
	 * if (specitemName.equalsIgnoreCase(expItemtName) &&
	 * versionItem.equalsIgnoreCase(version)) { isFound = true; System.out
	 * .println("Item Found In Actions → SpecNo: " + specitemName + " , Version: " +
	 * versionItem);
	 * 
	 * WebElement actionButton = driver.findElement(By.xpath( "//tbody/tr[" + r +
	 * "]//span[@class='p-button-icon pi pi-ellipsis-v ng-star-inserted']"));
	 * ((JavascriptExecutor) driver).executeScript("arguments[0].click();",
	 * actionButton); Thread.sleep(3000);
	 * 
	 * return;
	 * 
	 * } }
	 * 
	 * if (isFound == false && p < page_count.size()) { WebElement nextPage =
	 * wait.until(ExpectedConditions.elementToBeClickable(
	 * By.xpath("//span[@class='p-paginator-pages ng-star-inserted']/button[" + (p +
	 * 1) + "]"))); ((JavascriptExecutor)
	 * driver).executeScript("arguments[0].scrollIntoView({block: 'center'});",
	 * nextPage); ((JavascriptExecutor)
	 * driver).executeScript("arguments[0].click();", nextPage); Thread.sleep(5000);
	 * ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
	 * 
	 * }
	 * 
	 * } if (isFound == false) { System.out.println("Item not Found in Actions"); }
	 * 
	 * }
	 */
	public void clickEdit(String... expItemNames) throws Exception {
		performTableActionGeneric(page_count, "//tbody[@role='rowgroup']/tr",
				"//span[@class='p-paginator-pages ng-star-inserted']/button",
				"//span[contains(@class,'be-pencil')] | //i[contains(@class,'pi-pencil')]", // Merged locators
				expItemNames);
	}

	public void clickEdit(String commaSeparatedValues) throws Exception {
		clickEdit(splitAndTrim(commaSeparatedValues));
	}

	public void clickView(String... expItemNames) throws Exception {
		performTableActionGeneric(page_count, "//tbody[@role='rowgroup']/tr",
				"//span[@class='p-paginator-pages ng-star-inserted']/button",
				"//span[contains(@class,'be-pencil')] | //i[contains(@class,'pi-eye')]", // Merged locators
				expItemNames);
	}

	public void clickView(String commaSeparatedValues) throws Exception {
		clickView(splitAndTrim(commaSeparatedValues));
	}

	private String[] splitAndTrim(String commaSeparated) {
		if (commaSeparated == null || commaSeparated.isEmpty()) {
			return new String[0];
		}
		String[] parts = commaSeparated.split(",");
		for (int i = 0; i < parts.length; i++) {
			parts[i] = parts[i].trim();
		}
		return parts;
	}

	public void clickEditForPending(String expItemName) throws Exception {
		performTableAction(pending_page_count, "//div[@class='card table-data'][1]//tbody[@role='rowgroup']/tr",
				"//div[@class='card table-data'][1]//span[@class='p-paginator-pages ng-star-inserted']/button",
				expItemName, "//i[contains(@class,'pi-pencil')] | //span[contains(@class,'be-pencil')]");
	}

	/**
	 * Generic helper to verify item existence and click action/edit.
	 */
	/**
	 * Generic helper to verify item existence and click action/edit.
	 */
	protected void performTableActionGeneric(List<WebElement> pageCountElements, String rowXpath,
			String paginatorButtonPrefix, String actionButtonSubPath, String... expectedValues) throws Exception {

		boolean isFound = false;

		int totalPages = (pageCountElements.size() == 0) ? 1 : pageCountElements.size();

		// Resolve Indices ONCE before scanning pages
		List<Integer> targetIndices = getDynamicColumnIndices();

		// Validation: Mismatch between config and passed values
		if (tableHeaders != null && tableHeaders.length != expectedValues.length) {
			throw new IllegalArgumentException("Header config count (" + tableHeaders.length
					+ ") does not match expected values count (" + expectedValues.length + ")");
		}

		for (int p = 1; p <= totalPages; p++) {
			// System.out.println("Scanning Page " + p);
			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(rowXpath)));
			List<WebElement> rows = driver.findElements(By.xpath(rowXpath));

			for (int r = 1; r <= rows.size(); r++) {
				try {
					boolean rowMatches = true;

					// CHECK ALL CONFIGURED COLUMNS
					for (int i = 0; i < targetIndices.size(); i++) {
						int colIndex = targetIndices.get(i);
						String expectedVal = expectedValues[i];

						// If tableHeaders is null (Legacy), we use index 1 (which is
						// targetIndices.get(0)).
						// So this loop runs once.

						WebElement cell = driver.findElement(By.xpath(rowXpath + "[" + r + "]/td[" + colIndex + "]"));

						if (i == 0) { // Scroll only on first check
							((JavascriptExecutor) driver)
									.executeScript("arguments[0].scrollIntoView({block:'center'});", cell);
						}

						String currentName = cell.getText().trim();

						if (!currentName.equalsIgnoreCase(expectedVal.trim())) {
							rowMatches = false;
							break;
						}
					}

					if (rowMatches) {
						System.out.println("✅ Item Found Matching: " + Arrays.toString(expectedValues));
						isFound = true;

						// Click Action/Edit Button
						WebElement actionBtn = driver
								.findElement(By.xpath(rowXpath + "[" + r + "]" + actionButtonSubPath));
						((JavascriptExecutor) driver).executeScript("arguments[0].click();", actionBtn);
						// Small wait for processing
						Thread.sleep(2000);
						return;
					}
				} catch (Exception e) {
					// Ignore row error (stale element etc)
				}
			}

			// Navigate to Next Page
			if (!isFound && p < totalPages) {
				String nextBtnXpath = paginatorButtonPrefix + "[" + (p + 1) + "]";
				try {
					WebElement nextPage = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(nextBtnXpath)));
					((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});",
							nextPage);
					nextPage.click();
					Thread.sleep(3000);
					((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
				} catch (Exception e) {
					System.out.println("Next page not found or clickable");
					break;
				}
			}
		}

		if (!isFound) {
			System.out.println("❌ Item not found: " + Arrays.toString(expectedValues));
			ScreenshotUtil.takeStepScreenshot("ItemNotFound_" + Arrays.toString(expectedValues));
		}
	}

	/**
	 * Legacy method support - Redirects to generic
	 */
	protected void performTableAction(List<WebElement> pageCountElements, String rowXpath, String paginatorButtonPrefix,
			String itemName, String actionButtonSubPath) throws Exception {
		performTableActionGeneric(pageCountElements, rowXpath, paginatorButtonPrefix, actionButtonSubPath, itemName);
	}

	/*
	 * public void clickEdit(String expItemName, String version) throws Exception {
	 * boolean isFound = false;
	 * 
	 * int specNoCol = getColumnIndexByHeader("Specification Number"); int
	 * versionCol = getColumnIndexByHeader("Version");
	 * 
	 * for (int p = 1; p <= page_count.size(); p++) {
	 * System.out.println("Number of pages" + page_count.size()); // Wait until
	 * table rows are present
	 * wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(
	 * "//tbody[@role='rowgroup']/tr"))); List<WebElement> item_list =
	 * driver.findElements(By.xpath("//tbody[@role='rowgroup']/tr"));
	 * 
	 * for (int r = 1; r <= item_list.size(); r++) { WebElement specCell =
	 * driver.findElement( By.xpath("//tbody[@role='rowgroup']/tr[" + r + "]/td[" +
	 * specNoCol + "]")); WebElement versionCell = driver.findElement(
	 * By.xpath("//tbody[@role='rowgroup']/tr[" + r + "]/td[" + versionCol + "]"));
	 * ((JavascriptExecutor)
	 * driver).executeScript("arguments[0].scrollIntoView({block:'center'});",
	 * specCell); ((JavascriptExecutor)
	 * driver).executeScript("arguments[0].scrollIntoView({block:'center'});",
	 * versionCell); String specitemName = specCell.getText().trim(); String
	 * versionItem = versionCell.getText().trim(); System.out.println("Expected: ["
	 * + expItemName.trim() + "]"); System.out.println("Actual:   [" +
	 * specitemName.trim() + "]"); System.out.println("Equal? " +
	 * specitemName.equalsIgnoreCase(expItemName.trim()));// trim fixes space //
	 * mismatch System.out.println("Expected Version: [" + version.trim() + "]");
	 * System.out.println("Actual Version:   [" + versionItem.trim() + "]");
	 * System.out.println("Version Equal? " +
	 * versionItem.equalsIgnoreCase(version.trim())); if
	 * (specitemName.equalsIgnoreCase(expItemName.trim())) { isFound = true;
	 * System.out .println("Item Found In Actions → SpecNo: " + specitemName +
	 * " , Version: " + versionItem);
	 * 
	 * WebElement editIcon = driver .findElement(By.xpath("//tbody/tr[" + r +
	 * "]//span[contains(@class,'be-pencil')]")); ((JavascriptExecutor)
	 * driver).executeScript("arguments[0].click();", editIcon);
	 * 
	 * // Wait for edit form to appear //
	 * wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
	 * "//input[@formcontrolname='name']"))); return; } }
	 * 
	 * // Go to next page if not found if (!isFound && p < page_count.size()) {
	 * WebElement nextPage = driver.findElement(
	 * By.xpath("//span[@class='p-paginator-pages ng-star-inserted']/button[" + (p +
	 * 1) + "]")); ((JavascriptExecutor)
	 * driver).executeScript("arguments[0].scrollIntoView({block: 'center'});",
	 * nextPage); nextPage.click(); Thread.sleep(3000); // small wait for new rows
	 * to load } }
	 * 
	 * if (!isFound) { System.out.println("❌ Item not found: " + expItemName);
	 * ScreenshotUtil.takeStepScreenshot("ItemNotFound_" + expItemName); } }
	 */
	@FindBy(xpath = "//li//span[normalize-space()='Approve'] | //button[normalize-space()='Approve']")
	WebElement btn_approve;

	public void clickApprove() {

		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", btn_approve);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn_approve);

	}

	@FindBy(xpath = "//li//span[normalize-space()='Return'] | //button[normalize-space()='Return']")
	WebElement btn_return;

	public void clickReturn() {

		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", btn_return);

		((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn_return);

	}

	@FindBy(xpath = "//input[@formcontrolname='remarks']")
	WebElement txt_remarks;

	public void enterRemarks(String remarks) {
		WebElement txt_remarks = driver
				.findElement(By.xpath("//input[@formcontrolname='remarks'] | //textarea[@formcontrolname='remarks']"));
		waitAndSendKeys(txt_remarks, remarks);

	}

	@FindBy(xpath = "//button[normalize-space()='Submit']")
	WebElement button_submit;

	public void clickSubmit() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", button_submit);

	}

	public void clickUpdate() throws Exception {
		WebElement updateButton = driver
				.findElement(By.xpath("//li//span[normalize-space()='Update'] | //button[normalize-space()='Update']"));

		((JavascriptExecutor) driver).executeScript("arguments[0].click();", updateButton);

	}

	public void clickReview() throws InterruptedException {
		WebElement reviewButton = driver
				.findElement(By.xpath("//li//span[normalize-space()='Review'] | //button[normalize-space()='Review']"));

		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", reviewButton);
		scrollAndClickWithPageDown(reviewButton);

	}

	public void clickCreateReview() throws InterruptedException {
		WebElement reviewButton = driver.findElement(By.xpath("//li[1]//span[normalize-space()='Review']"));
		scrollAndClickWithPageDown(reviewButton);

	}

	public void clickInactiveReview() throws Exception {
		WebElement reviewButton = driver.findElement(
				By.xpath("//li[3]//span[normalize-space()='Review'] | //li[4]//span[normalize-space()='Review']"));
		scrollAndClickWithPageDown(reviewButton);

	}

	public void clickCreateApprove() throws Exception {
		WebElement approveButton = driver.findElement(By.xpath("//li[2]//span[normalize-space()='Approve']"));
		scrollAndClickWithPageDown(approveButton);

	}

	public void clickInactiveApprove() throws Exception {
		WebElement approveButton = driver.findElement(
				By.xpath("//li[4]//span[normalize-space()='Approve'] | //li[5]//span[normalize-space()='Approve']"));
		scrollAndClickWithPageDown(approveButton);

	}

	public void clickInactive() throws Exception {
		WebElement inactiveButton = driver
				.findElement(By.xpath("//span[normalize-space()='Inactive'] | //button[normalize-space()='Inactive']"));
		scrollAndClickWithPageDown(inactiveButton);
	}

	@FindBy(xpath = "//button[normalize-space()='Reject']")
	WebElement btn_reject;

	public void clickReject() throws Exception {

		scrollAndClickWithPageDown(btn_reject);

	}

	public void clickActive() throws Exception {
		WebElement activeButton = driver
				.findElement(By.xpath("//span[normalize-space()='Active'] | //button[normalize-space()='Active']"));
		scrollAndClickWithPageDown(activeButton);
	}

	public void clickAddStage() throws Exception {
		WebElement addStageButton = driver.findElement(By.xpath("//span[normalize-space()='Add Stage']"));
		scrollAndClickWithPageDown(addStageButton);
	}

	public void clickAddRoute() throws Exception {
		WebElement addStageButton = driver.findElement(By.xpath("//span[normalize-space()='Add Stage']"));
		scrollAndClickWithPageDown(addStageButton);
	}

	public void enterValueInLatestElement(List<WebElement> elements, String value) {
		if (elements == null || elements.isEmpty()) {
			throw new RuntimeException("Element list is empty or null.");
		}
		WebElement latestElement = elements.get(elements.size() - 1);
		waitAndSendKeys(latestElement, value);
		log.info("Entered value '{}' into the latest element.", value);

	}

	public void enterValueInElementAtIndex(List<WebElement> elements, int index, String value) {
		if (elements == null || index < 0 || index >= elements.size()) {
			throw new RuntimeException("Invalid index " + index + " for element list of size "
					+ (elements == null ? "null" : elements.size()));
		}
		WebElement element = elements.get(index);
		waitAndSendKeys(element, value);
		log.info("Entered value '{}' into element at index {}.", value, index);
	}

	/*
	 * public void enterValueInRandomElement(List<WebElement> elements, String
	 * value) { if (elements == null || elements.isEmpty()) { throw new
	 * RuntimeException("Element list is empty or null."); } int randomIndex = (int)
	 * (Math.random() * elements.size()); WebElement element =
	 * elements.get(randomIndex); waitAndSendKeys(element, value);
	 * log.info("Entered value '{}' into random element at index {}.", value,
	 * randomIndex); }
	 */

	public WebElement clickLatestElement(List<WebElement> elements) {
		if (elements == null || elements.isEmpty()) {
			throw new RuntimeException("Element list is empty or null.");
		}
		WebElement latestElement = elements.get(elements.size() - 1);
		waitForElementandClick(latestElement);
		log.info("Clicked on the latest element.");
		return latestElement;
	}

	public WebElement clickElementAtIndex(List<WebElement> elements, int index) {
		if (elements == null || index < 0 || index >= elements.size()) {
			throw new RuntimeException("Invalid index " + index + " for element list of size "
					+ (elements == null ? "null" : elements.size()));
		}
		WebElement element = elements.get(index);
		waitForElementandClick(element);
		log.info("Clicked on element at index {}.", index);
		return element;
	}

	/*
	 * public WebElement clickRandomElement(List<WebElement> elements) { if
	 * (elements == null || elements.isEmpty()) { throw new
	 * RuntimeException("Element list is empty or null."); } int randomIndex = (int)
	 * (Math.random() * elements.size()); WebElement element =
	 * elements.get(randomIndex); waitForElementandClick(element);
	 * log.info("Clicked on random element at index {}.", randomIndex); return
	 * element; }
	 */

	public WebElement getLatestElement(List<WebElement> elements) {
		if (elements == null || elements.isEmpty()) {
			throw new RuntimeException("Element list is empty or null.");
		}
		return elements.get(elements.size() - 1);
	}

	public void clickListOption(String text) {
		clickVisibleElementByXpath("//li[normalize-space()='" + text + "']");
	}

	public void clickElementByText(String tagName, String text) {
		clickVisibleElementByXpath("//" + tagName + "[normalize-space()='" + text + "']");
	}

	public void clickDropdownOption(String containerXpath, String text) {
		clickVisibleElementByXpath(containerXpath + "//li[normalize-space()='" + text + "']");
	}

	public void clickRelativeOption(WebElement parent, String tagName, String text) {

		By locator = By.xpath(".//" + tagName + "[contains(normalize-space(),'" + text + "')]");
		wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(parent, locator));
		wait.until(ExpectedConditions.elementToBeClickable(locator)).click();

	}

	protected void clickVisibleElementByXpath(String xpath) {
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
		List<WebElement> elements = driver.findElements(By.xpath(xpath));

		boolean isClicked = false;
		for (WebElement element : elements) {
			if (element.isDisplayed()) {
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", element);
				try {
					element.click();
				} catch (Exception e) {
					// Fallback for JS click if standard click fails
					((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
				}
				isClicked = true;
				break;
			}
		}

		if (!isClicked) {
			// Try waiting for visibility of the first one if none were immediately
			// displayed (race condition)
			try {
				WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
				element.click();
			} catch (Exception e) {
				throw new RuntimeException(
						"Element with XPath '" + xpath + "' found in DOM but none were visible/clickable.");
			}
		}
	}

}
