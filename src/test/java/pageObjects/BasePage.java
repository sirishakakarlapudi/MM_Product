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
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
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

	protected final Logger log = LogManager.getLogger(this.getClass());
	private String[] tableHeaders = null; // Default null implies "Legacy Mode" (Column 1)

	public BasePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	/*--------------------	Helper Methods---------------------*/

	protected void waitForElementandClick(WebElement element) {

		wait.until(ExpectedConditions.visibilityOf(element));
		log.debug("The element is visible: {}", element);
		wait.until(ExpectedConditions.elementToBeClickable(element)).click();
		log.debug("Clicked on the element: {}", element);
	}

	
	public String waitForToast() {
		String message = "";
		try {
			// 1. Wait until the toast is visible
			WebElement toast = wait
					.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.p-toast-detail")));

			// 2. Safely try to capture the text (handling quick disappearance)
			try {
				message = toast.getText().trim();
			} catch (StaleElementReferenceException e) {
				// If it becomes stale immediately, try to find it one last time
				message = driver.findElement(By.cssSelector("div.p-toast-detail")).getText().trim();
			}
			log.info("Toast Message captured: {}", message);
		} catch (Exception e) {
			log.warn("Toast appeared but text was not captured (it may have disappeared too quickly).");
		}
		return message;
	}

	private void triggerInputAndChange(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].dispatchEvent(new Event('input', {bubbles: true}));", element);
		js.executeScript("arguments[0].dispatchEvent(new Event('change', {bubbles: true}));", element);
		js.executeScript("arguments[0].dispatchEvent(new Event('blur', {bubbles: true}));", element);
	}

	protected void waitAndSendKeys(WebElement element, String value) {
		wait.until(ExpectedConditions.visibilityOf(element));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
		element.sendKeys(value.trim());
		log.debug("Entered value: {}", value);
		triggerInputAndChange(element);

	}

	public void waitForLoading() {
		try {
			// 1. Check if loader appears within a strict 200ms window
			WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofMillis(200));
			shortWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.loader-container")));

			log.debug("⏳ Application is loading... waiting for spinner to disappear.");

			// 2. If it appeared, wait for it to disappear (max 10s default)
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.loader-container")));
			log.debug("✅ Loading complete.");
		} catch (Exception e) {
			// If it never appeared (or already disappeared), don't perform any further
			// waits.
			// This prevents the "blank wait" issue you were facing.
		}
	}

	public void waitForElementToVisible(WebElement element) {
		wait.until(ExpectedConditions.visibilityOf(element));

	}

	public void scrollAndClick(WebElement element) {

		// Scroll element into view (centered)
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", element);

		// Wait until clickable
		wait.until(ExpectedConditions.elementToBeClickable(element));

		// Click using JS (safe for Angular)
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
	}
	
	
	
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

	public void scrollToElement(WebElement element) {

		// Scroll element into view (centered)
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", element);

		// Wait until clickable
		wait.until(ExpectedConditions.elementToBeClickable(element));

	}

	
	

	/*---------------------Google chrome Search box------------------*/

	@FindBy(name = "q")
	WebElement searchbox;

	public WebElement getSearchBox() {
		return searchbox;
	}

	public void searchBox(String url) throws Exception {
		waitAndSendKeys(searchbox, url);
	}

	
	/*-------------------------Methods For UserName, Password, Login--------------------------*/

	@FindBy(css = "input[autocomplete='username']")
	protected WebElement txt_username;

	@FindBy(id = "password")
	WebElement txt_password;

	public void userNameandPassword(String username, String password) throws Exception {
		waitAndSendKeys(txt_username, username);
		log.debug("Entered username: {}", username);
		waitAndSendKeys(txt_password, password);
		log.debug("Entered password: {}", password);

	}

	public void userName(String username) throws Exception {
		waitAndSendKeys(txt_username, username);
		log.debug("Entered username: {}", username);
	}

	@FindBy(xpath = "//button[@type='submit']")
	WebElement btn_login;

	public void loginButton() {

		waitForElementandClick(btn_login);
		log.debug("Clicked on Login button");
	}


	

	/*-------------------Validation for Company Logo-------------------*/
	
	@FindBy(xpath = "content/images/maithri.png")
	WebElement maithriLogoImage;
	
	public WebElement getMaithriLogoImage() {
		return maithriLogoImage;
	}
	

	/*-----------------------------Apps Xpaths----------------------*/

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

	/*-----------------------Masters and module Validation----------------------*/

	public WebElement getMastersTitle() {
		return title_masters;
	}
	
	
	@FindBy(xpath = "//div[@class='mt-auto']//h5")
	List<WebElement> allapps_headings;

	public List<String> getAllAppsDisplayed() {

	    List<String> fields = new ArrayList<>();

	    for (WebElement allapps : allapps_headings) {
	        if (allapps.isDisplayed()) {
	            String text = allapps.getText().trim();
	            if (!text.isEmpty()) {
	                fields.add(text);
	            }
	        }
	    }
	    return fields;
	}
	
	
	public WebElement getCardByTitle(String title) {
	    return driver.findElement(By.xpath("//h5[normalize-space()='" + title + "']/ancestor::div[contains(@class,'card')]"));
	}

	public WebElement getIcon(String title) {
	    return driver.findElement(By.xpath("//h5[normalize-space()='" + title + "']/ancestor::div[contains(@class,'card')]//img"));
	}

	public WebElement getDescription(String title) {
	    return driver.findElement(By.xpath("//h5[normalize-space()='" + title + "']/following-sibling::p"));
	}

	public class FieldDetails {
	    private String description;
	    private String iconPath;

	    public FieldDetails(String description, String iconPath) {
	        this.description = description;
	        this.iconPath = iconPath;
	    }

	    public String getDescription() { return description; }
	    public String getIconPath() { return iconPath; }
	}

	
	

	public WebElement getModuleElement(String moduleName) {
		return driver.findElement(By.xpath("//a[normalize-space()='" + moduleName + "']"));
	}

	/*------------------------Master Module Xpath--------------------*/

	public void masterClick(String mastername) throws Exception {
		WebElement xpath = wait.until(
				ExpectedConditions.elementToBeClickable(By.xpath("//a[normalize-space()='" + mastername + "']")));
		xpath.click();

	}

	/*-----------------Validating BreadCrumbs------------------------*/
	
	@FindBy(css = "ul.breadcrumb li.breadcrumb-item")
	List<WebElement> breadcrumbItems;
	
	public List<String> getBreadcrumbsText() {
		List<String> breadcrumbs = new ArrayList<>();
		for (WebElement item : breadcrumbItems) {
			if (item.isDisplayed()) {
				breadcrumbs.add(item.getText().trim());
			}
		}
		return breadcrumbs;
	}	
	
	
	@FindBy(css = "ul.breadcrumb li.breadcrumb-item a")
	List<WebElement> breadcrumbLinks;
	
	public List<String> getBreadcrumbLinks() {
		List<String> links = new ArrayList<>();
		for (WebElement item : breadcrumbLinks) {
			if (item.isDisplayed()) {
				links.add(item.getAttribute("href").trim());
			}
		}
		return links;
	}
	
	
	@FindBy(css = "ul.breadcrumb li.breadcrumb-item i.be-chevron-right")
	List<WebElement> breadcrumbArrows;

	public List<WebElement> getBreadcrumbArrows() {
		return breadcrumbArrows;
	}
	

	@FindBy(css = "ul.breadcrumb li.breadcrumb-item span.be-home")
	WebElement homeIcon;

	public WebElement getHomeIcon() {
		return homeIcon;
	}
	
	
	
	
	/*----------------Profile Drop Down-----------------------*/

	@FindBy(xpath = "//button[@class='profile-btn']/i")
	WebElement profile_dropdown;

	public void click_profileDropdown() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0, 0);");
		profile_dropdown.click();
	}

	/*-----------------Password Input  and Authenticate Button-----------------------*/

	public void passWord(String password) throws Exception {
		waitAndSendKeys(txt_password, password);
		log.debug("Entered password: {}", password);
	}

	@FindBy(xpath = "//button[normalize-space()='Authenticate']")
	WebElement btn_authenticate;

	public void authenticateButton() {
		btn_authenticate.click();
	}

	/*--------------------Logout-----------------*/

	@FindBy(xpath = "//li[normalize-space()='Logout']")
	WebElement logout;

	public void logout() {
		Actions actions = new Actions(driver);
		actions.moveToElement(logout).perform();
		logout.click();
	}

	/*---------------------Create Button------------------*/

	@FindBy(xpath = "//span[normalize-space()='Create']")
	WebElement create;

	public void Create() throws Throwable {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0, 0);");
		waitForElementandClick(create);

	}

	/*---------------Submit button In Create Screen---------------------*/
	@FindBy(xpath = "//button[@id='save-entity' or normalize-space()='Submit']")
	WebElement click_submit;

	public void createSubmit() throws InterruptedException {
		scrollAndClickWithPageDown(click_submit);
	}

	public WebElement getSubmitButton() {
		return click_submit;
	}

	/*----------------Cancel Button in create Screen-----*/

	@FindBy(xpath = "//button[normalize-space()='Cancel']")
	WebElement click_cancel;

	public WebElement getCancelButton() {
		return click_cancel;
	}
	
	
	
	
	
	/*------------Red Stars and Green Stars--------------------*/
	
	
	public WebElement isRedStarDisplayedForField(String fieldname ) {
		        return driver.findElement(By.xpath("//label[contains(text(),'"+fieldname+"')]/ancestor::div[@class='card']//span[@class='not-valid']"));
		
	}
	
	public WebElement isGreenStarDisplayedForField(String fieldname ) {
        return driver.findElement(By.xpath("//label[contains(text(),'"+fieldname+"')]/ancestor::div[@class='card']//span[@class='valid']"));

}
	
	
	/*--------------------Error Messages----------------*/
	
	public WebElement getErrorMessage(String fieldname ) {
        return driver.findElement(By.xpath("//label[contains(text(),'"+fieldname+"')]/ancestor::div[@class='card']//validation-message"));

}
		
	/*---------Checking Whether Value is entered in Input field or not----------*/
	
	public boolean isNoValueEnteredInField(String fieldname ) {
        WebElement element=driver.findElement(By.xpath("//label[contains(text(),'"+fieldname+"')]/ancestor::div[@class='card']//input[@placeholder='"+fieldname+"']"));
        
        String classes = element.getAttribute("class");

        return classes.contains("ng-invalid");
	}
	
	public boolean isValueEnteredInField(String fieldname ) {
        WebElement element=driver.findElement(By.xpath("//label[contains(text(),'"+fieldname+"')]/ancestor::div[@class='card']//input[@placeholder='"+fieldname+"']"));
        
        String classes = element.getAttribute("class");

        return classes.contains("invalid");
	}
	

	public void toast() {
		waitForToast();
	}

	protected void jsClick(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", element);
	}

	protected void jsScrollToElement(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
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

	@FindBy(xpath = "//span[normalize-space()='PDF']")
	WebElement click_pdf;

	public void click_PDF() {

		waitForElementandClick(click_pdf);

	}

	public boolean pdfisDisplayed() {
		try {
			return click_pdf.isDisplayed();
		} catch (NoSuchElementException e) {
			return false;
		}

	}

	@FindBy(xpath = "//button[normalize-space()='Back']")
	WebElement click_back;

	public void clickBack() throws InterruptedException {
		scrollAndClick(click_back);
	}

	/*----------------Methods for Actions List--------------------*/

	/*--------To get the table Headers---------------*/

	public void setTableHeaders(String commaSeparatedHeaders) {
		if (commaSeparatedHeaders != null && !commaSeparatedHeaders.isEmpty()) {
			this.tableHeaders = commaSeparatedHeaders.split(",");
			// Trim each header to handle spaces around commas
			for (int i = 0; i < this.tableHeaders.length; i++) {
				this.tableHeaders[i] = this.tableHeaders[i].trim();
			}
		}
	}

	/*-----------to Find the given Header column index-----------*/

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

	/*------------------To Perform table Actions like Actions, View, Edit-----------------*/

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
			log.info("🔍 Scanning Page {} of {} for items: {}", p, totalPages, Arrays.toString(expectedValues));
			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(rowXpath)));
			List<WebElement> rows = driver.findElements(By.xpath(rowXpath));

			for (int r = 1; r <= rows.size(); r++) {
				try {
					boolean rowMatches = true;

					// CHECK ALL CONFIGURED COLUMNS
					for (int i = 0; i < targetIndices.size(); i++) {
						int colIndex = targetIndices.get(i);
						String expectedVal = expectedValues[i];

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
						log.info("✅ Item Found Matching: {} on Row {} of Page {}", Arrays.toString(expectedValues), r,
								p);
						isFound = true;

						// Click Action/Edit Button
						WebElement actionBtn = driver
								.findElement(By.xpath(rowXpath + "[" + r + "]" + actionButtonSubPath));
						((JavascriptExecutor) driver).executeScript("arguments[0].click();", actionBtn);
						log.debug("Clicked the action button for the matching row.");
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
					log.info("➡️ Navigating to next page (Page {})...", p + 1);
					WebElement nextPage = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(nextBtnXpath)));
					((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});",
							nextPage);
					nextPage.click();
					Thread.sleep(3000);
					((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
				} catch (Exception e) {
					log.error("❌ Next page button (Page {}) was not clickable or not found.", p + 1);
					break;
				}
			}
		}

		if (!isFound) {
			log.error("❌ Item not found after scanning all {} pages: {}", totalPages, Arrays.toString(expectedValues));
			ScreenshotUtil.takeStepScreenshot("ItemNotFound_" + Arrays.toString(expectedValues));
		}
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
	
	
	
	
	@FindBy(xpath = "//label")
	List<WebElement> allLabels;

	public List<String> getDisplayedFieldLabels() {

	    List<String> fields = new ArrayList<>();

	    for (WebElement label : allLabels) {
	        if (label.isDisplayed()) {
	            String text = label.getText().trim();
	            if (!text.isEmpty()) {
	                fields.add(text);
	            }
	        }
	    }
	    return fields;
	}
	
	
	
	
	
	@FindBy(xpath = "//button")
	List<WebElement> allButtons;

	public List<String> getDisplayedButtons() {

	    List<String> buttons = new ArrayList<>();

	    for (WebElement btn : allButtons) {
	        if (btn.isDisplayed()) {
	            String text = btn.getText().trim();
	            if (!text.isEmpty()) {
	                buttons.add(text);
	            }
	        }
	    }
	    return buttons;
	}


	/*--------------To Validate Item Actions like View, Edit, Approve------------------*/

	public void validateIcons(List<WebElement> pageCountElements, String rowXpath,
			String paginatorButtonPrefix, boolean expectView, boolean expectEdit, String... expectedValues)
			throws Exception {

		int totalPages = (pageCountElements.size() == 0) ? 1 : pageCountElements.size();
		List<Integer> targetIndices = getDynamicColumnIndices();

		for (int p = 1; p <= totalPages; p++) {
			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(rowXpath)));
			List<WebElement> rows = driver.findElements(By.xpath(rowXpath));

			for (int r = 1; r <= rows.size(); r++) {
				try {
					boolean rowMatches = true;
					for (int i = 0; i < targetIndices.size(); i++) {
						WebElement cell = driver
								.findElement(By.xpath(rowXpath + "[" + r + "]/td[" + targetIndices.get(i) + "]"));
						if (i == 0)
							((JavascriptExecutor) driver)
									.executeScript("arguments[0].scrollIntoView({block:'center'});", cell);
						if (!cell.getText().trim().equalsIgnoreCase(expectedValues[i].trim())) {
							rowMatches = false;
							break;
						}
					}

					if (rowMatches) {
						log.info("✅ Item Found for Icon Validation: {} on Row {} of Page {}",
								Arrays.toString(expectedValues), r, p);

						// Check View Icon
						boolean viewDisplayed = false;
						try {
							WebElement viewIcon = driver
									.findElement(By.xpath(rowXpath + "[" + r + "]//i[contains(@class,'pi-eye')]"));
							viewDisplayed = viewIcon.isDisplayed();
						} catch (NoSuchElementException e) {
						}

						if (expectView && !viewDisplayed)
							throw new RuntimeException("Expected View Icon to be displayed, but it was MISSING.");
						if (!expectView && viewDisplayed)
							throw new RuntimeException("Expected View Icon to NOT be displayed, but it was PRESENT.");

						// Check Edit Icon
						boolean editDisplayed = false;
						try {
							WebElement editIcon = driver
									.findElement(By.xpath(rowXpath + "[" + r + "]//i[contains(@class,'pi-pencil')]"));
							editDisplayed = editIcon.isDisplayed();
						} catch (NoSuchElementException e) {
						}

						if (expectEdit && !editDisplayed)
							throw new RuntimeException("Expected Edit Icon to be displayed, but it was MISSING.");
						if (!expectEdit && editDisplayed)
							throw new RuntimeException("Expected Edit Icon to NOT be displayed, but it was PRESENT.");

						log.info(
								"✓ Icons validated successfully. View Expected: {}, Actual: {}. Edit Expected: {}, Actual: {}.",
								expectView, viewDisplayed, expectEdit, editDisplayed);
						return;
					}
				} catch (Exception e) {
				}
			}
		}
		throw new RuntimeException("Item not found for icon validation: " + Arrays.toString(expectedValues));
	}

	public void validateActionMenuOptions(List<WebElement> pageCountElements, String rowXpath,
			String paginatorButtonPrefix, String expectedOption, String... expectedValues) throws Exception {

		int totalPages = (pageCountElements.size() == 0) ? 1 : pageCountElements.size();
		List<Integer> targetIndices = getDynamicColumnIndices();

		for (int p = 1; p <= totalPages; p++) {
			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(rowXpath)));
			List<WebElement> rows = driver.findElements(By.xpath(rowXpath));

			for (int r = 1; r <= rows.size(); r++) {
				try {
					boolean rowMatches = true;
					for (int i = 0; i < targetIndices.size(); i++) {
						WebElement cell = driver
								.findElement(By.xpath(rowXpath + "[" + r + "]/td[" + targetIndices.get(i) + "]"));
						if (i == 0)
							((JavascriptExecutor) driver)
									.executeScript("arguments[0].scrollIntoView({block:'center'});", cell);
						if (!cell.getText().trim().equalsIgnoreCase(expectedValues[i].trim())) {
							rowMatches = false;
							break;
						}
					}

					if (rowMatches) {
						log.info("✅ Item Found for Action Validation: {} on Row {} of Page {}",
								Arrays.toString(expectedValues), r, p);
						WebElement actionBtn = driver.findElement(
								By.xpath(rowXpath + "[" + r + "]//span[contains(@class,'pi-ellipsis-v')]"));
						((JavascriptExecutor) driver).executeScript("arguments[0].click();", actionBtn);
						Thread.sleep(1000);

						WebElement option = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
								"//li//span[normalize-space()='" + expectedOption + "'] | //button[normalize-space()='"
										+ expectedOption + "']")));

						if (!option.isDisplayed())
							throw new RuntimeException("Action option '" + expectedOption + "' not displayed!");
						log.info("✓ Action option '{}' is displayed.", expectedOption);
						return; // Found and validated
					}
				} catch (Exception e) {
				}
			}
		}
		throw new RuntimeException("Item not found for action validation: " + Arrays.toString(expectedValues));
	}

	public String getItemStatus(List<WebElement> pageCountElements, String rowXpath,
			String paginatorButtonPrefix, String... expectedValues) throws Exception {

		int totalPages = (pageCountElements.size() == 0) ? 1 : pageCountElements.size();
		List<Integer> targetIndices = getDynamicColumnIndices();

		for (int p = 1; p <= totalPages; p++) {
			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(rowXpath)));
			List<WebElement> rows = driver.findElements(By.xpath(rowXpath));

			for (int r = 1; r <= rows.size(); r++) {
				try {
					boolean rowMatches = true;
					for (int i = 0; i < targetIndices.size(); i++) {
						WebElement cell = driver
								.findElement(By.xpath(rowXpath + "[" + r + "]/td[" + targetIndices.get(i) + "]"));
						if (i == 0)
							((JavascriptExecutor) driver)
									.executeScript("arguments[0].scrollIntoView({block:'center'});", cell);
						if (!cell.getText().trim().equalsIgnoreCase(expectedValues[i].trim())) {
							rowMatches = false;
							break;
						}
					}

					if (rowMatches) {
						// Find the Status column index
						List<WebElement> headerCells = driver.findElements(By.xpath("//thead//tr//th"));
						int statusColumnIndex = -1;
						for (int h = 0; h < headerCells.size(); h++) {
							String headerText = headerCells.get(h).getText().trim();
							if (headerText.equalsIgnoreCase("Status")
									|| headerText.equalsIgnoreCase("Approval Status")) {
								statusColumnIndex = h + 1; // XPath is 1-indexed
								break;
							}
						}

						if (statusColumnIndex == -1) {
							throw new RuntimeException("Status column not found in table headers");
						}

						// Get the status value from the matched row
						WebElement statusCell = driver
								.findElement(By.xpath(rowXpath + "[" + r + "]/td[" + statusColumnIndex + "]"));
						String status = statusCell.getText().trim();
						log.info("✅ Found item: {} with Status: '{}'", Arrays.toString(expectedValues), status);
						return status;
					}
				} catch (Exception e) {
					// Continue searching
				}
			}
		}
		throw new RuntimeException("Item not found to retrieve status: " + Arrays.toString(expectedValues));
	}

	public void validateActionMenuStrict(List<WebElement> pageCountElements, String rowXpath,
			String paginatorButtonPrefix, List<String> expectedActions, String... expectedItemValues) throws Exception {

		int totalPages = (pageCountElements.size() == 0) ? 1 : pageCountElements.size();
		List<Integer> targetIndices = getDynamicColumnIndices();

		for (int p = 1; p <= totalPages; p++) {
			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(rowXpath)));
			List<WebElement> rows = driver.findElements(By.xpath(rowXpath));

			for (int r = 1; r <= rows.size(); r++) {
				try {
					boolean rowMatches = true;
					for (int i = 0; i < targetIndices.size(); i++) {
						WebElement cell = driver
								.findElement(By.xpath(rowXpath + "[" + r + "]/td[" + targetIndices.get(i) + "]"));
						if (i == 0)
							((JavascriptExecutor) driver)
									.executeScript("arguments[0].scrollIntoView({block:'center'});", cell);
						if (!cell.getText().trim().equalsIgnoreCase(expectedItemValues[i].trim())) {
							rowMatches = false;
							break;
						}
					}

					if (rowMatches) {
						log.info("✅ Item Found for Strict Action Validation: {} on Row {} of Page {}",
								Arrays.toString(expectedItemValues), r, p);
						WebElement actionBtn = driver.findElement(
								By.xpath(rowXpath + "[" + r + "]//span[contains(@class,'pi-ellipsis-v')]"));
						((JavascriptExecutor) driver).executeScript("arguments[0].click();", actionBtn);
						Thread.sleep(1500); // Wait for animation

						// Get all visible menu items
						List<WebElement> menuItems = driver.findElements(By.xpath(
								"//ul[@role='menu']//li//span | //div[contains(@class,'p-menu-overlay')]//li//span"));
						// Fallback locator if p-menu structure varies, usually it's in a body overlay
						if (menuItems.isEmpty())
							menuItems = driver.findElements(By.xpath("//body//div//ul//li//a//span"));

						List<String> actualActions = new ArrayList<>();
						for (WebElement item : menuItems) {
							if (item.isDisplayed() && !item.getText().trim().isEmpty()) {
								actualActions.add(item.getText().trim());
							}
						}

						// Close menu to be clean
						((JavascriptExecutor) driver).executeScript("document.body.click()");

						log.info("Actual Actions Found: {}", actualActions);
						log.info("Expected Actions: {}", expectedActions);

						// 1. Check for Unexpected
						for (String actual : actualActions) {
							if (!expectedActions.contains(actual)) {
								throw new RuntimeException("Unexpected Action Found: '" + actual + "' (Expected only: "
										+ expectedActions + ")");
							}
						}

						// 2. Check for Missing
						for (String expected : expectedActions) {
							if (!actualActions.contains(expected)) {
								throw new RuntimeException(
										"Expected Action Missing: '" + expected + "' (Found: " + actualActions + ")");
							}
						}

						log.info("✓ Strict Action Menu Validation Passed.");
						return;
					}
				} catch (Exception e) {
					// Handle StaleElement etc
					if (e instanceof RuntimeException && e.getMessage().contains("Action"))
						throw e; // Rethrow validation errors
				}
			}
		}
		throw new RuntimeException("Item not found for action validation: " + Arrays.toString(expectedItemValues));
	}

	/*---------------------To click on Actions--------------------*/

	@FindBy(xpath = "//span[@class='p-paginator-pages ng-star-inserted']/button")
	List<WebElement> page_count;

	public void clickActions(String... expItemNames) throws Exception {
		performTableActionGeneric(page_count, "//tbody[@role='rowgroup']/tr",
				"//span[@class='p-paginator-pages ng-star-inserted']/button",
				"//span[contains(@class,'pi-ellipsis-v')]", expItemNames);

	}

	public void clickActions(String commaSeparatedValues) throws Exception {
		clickActions(splitAndTrim(commaSeparatedValues));
	}

	/*------------------To click on Edit------------------*/

	public void clickEdit(String... expItemNames) throws Exception {
		performTableActionGeneric(page_count, "//tbody[@role='rowgroup']/tr",
				"//span[@class='p-paginator-pages ng-star-inserted']/button",
				"//span[contains(@class,'be-pencil')] | //i[contains(@class,'pi-pencil')]", // Merged locators
				expItemNames);
	}

	public void clickEdit(String commaSeparatedValues) throws Exception {
		clickEdit(splitAndTrim(commaSeparatedValues));
	}

	@FindBy(xpath = "//li//span[normalize-space()='Update'] | //button[normalize-space()='Update']")
	WebElement btn_update;

	public void clickUpdate() throws Exception {
		scrollAndClick(btn_update);

	}

	/*---------------To click On View--------------------*/

	public void clickView(String... expItemNames) throws Exception {
		performTableActionGeneric(page_count, "//tbody[@role='rowgroup']/tr",
				"//span[@class='p-paginator-pages ng-star-inserted']/button",
				"//span[contains(@class,'be-pencil')] | //i[contains(@class,'pi-eye')]", // Merged locators
				expItemNames);
	}

	public void clickView(String commaSeparatedValues) throws Exception {
		clickView(splitAndTrim(commaSeparatedValues));
	}

	/*-------------------------To click Approve---------------*/

	@FindBy(xpath = "//li//span[normalize-space()='Approve'] | //button[normalize-space()='Approve']")
	WebElement btn_approve;

	public void clickApprove() {
		wait.until(ExpectedConditions.visibilityOf(btn_approve));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", btn_approve);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn_approve);

	}

	/*----------------------To click On Return------------*/

	@FindBy(xpath = "//li//span[normalize-space()='Return'] | //button[normalize-space()='Return']")
	WebElement btn_return;

	public void clickReturn() {

		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", btn_return);

		((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn_return);

	}

	/*-----------------------Remarks Field---------------*/

	@FindBy(xpath = "//input[@formcontrolname='remarks'] | //textarea[@formcontrolname='remarks']")
	WebElement txt_remarks;

	public void enterRemarks(String remarks) {
		waitAndSendKeys(txt_remarks, remarks);

	}

	@FindBy(xpath = "//div[@class='card table-data'][1]//span[@class='p-paginator-pages ng-star-inserted']/button")
	List<WebElement> pending_page_count;

	@FindBy(xpath = "//div[@class='card table-data'][2]//span[@class='p-paginator-pages ng-star-inserted']/button")
	List<WebElement> active_page_count;

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

	public int getColumnIndexByHeader(String headerName) {
		List<WebElement> headers = driver.findElements(By.xpath("//thead//th"));
		for (int i = 0; i < headers.size(); i++) {
			String headerText = headers.get(i).getText().trim();
			if (headerText.equalsIgnoreCase(headerName)) {
				return i + 1; // XPath index starts from 1
			}
		}
		throw new RuntimeException("Column not found: " + headerName);
	}

	public String getRowTextByHeader(String targetHeader, String rowXpath, List<WebElement> pageCountElements,
			String paginatorButtonPrefix, String... expectedValues) throws Exception {

		int totalPages = (pageCountElements.size() == 0) ? 1 : pageCountElements.size();
		List<Integer> identifierIndices = getDynamicColumnIndices();
		int targetColIndex = getColumnIndexByHeader(targetHeader);

		for (int p = 1; p <= totalPages; p++) {
			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(rowXpath)));
			List<WebElement> rows = driver.findElements(By.xpath(rowXpath));

			for (int r = 1; r <= rows.size(); r++) {
				try {
					boolean rowMatches = true;
					for (int i = 0; i < identifierIndices.size(); i++) {
						WebElement cell = driver
								.findElement(By.xpath(rowXpath + "[" + r + "]/td[" + identifierIndices.get(i) + "]"));
						if (!cell.getText().trim().equalsIgnoreCase(expectedValues[i].trim())) {
							rowMatches = false;
							break;
						}
					}

					if (rowMatches) {
						WebElement targetCell = driver
								.findElement(By.xpath(rowXpath + "[" + r + "]/td[" + targetColIndex + "]"));
						return targetCell.getText().trim();
					}
				} catch (Exception e) {
				}
			}

			if (p < totalPages) {
				driver.findElement(By.xpath(paginatorButtonPrefix + "[" + (p + 1) + "]")).click();
				Thread.sleep(2000);
			}
		}
		return "";
	}

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

	/**
	 * Legacy method support - Redirects to generic
	 */
	protected void performTableAction(List<WebElement> pageCountElements, String rowXpath, String paginatorButtonPrefix,
			String itemName, String actionButtonSubPath) throws Exception {
		performTableActionGeneric(pageCountElements, rowXpath, paginatorButtonPrefix, actionButtonSubPath, itemName);
	}

	@FindBy(xpath = "//button[normalize-space()='Submit']")
	WebElement button_submit;

	public void clickSubmit() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", button_submit);

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
		WebElement element = parent.findElement(locator);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
		element.click();
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
