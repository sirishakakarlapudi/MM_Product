package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import utilities.SoftAssertionUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Department extends BasePage {

	public Department(WebDriver driver) {
		super(driver);

	}

	@FindBy(xpath = "//input[@id='field_name']")
	WebElement dept_name;

	public void deptName(String deptname) {
		if (deptname != null && !deptname.trim().isEmpty()) {
			dept_name.clear();
			waitAndSendKeys(dept_name, deptname);
		}
	}

	@FindBy(xpath = "//input[@id='field_title']")
	WebElement dept_desc;

	public void deptDesc(String deptdesc) {
		if (deptdesc != null && !deptdesc.trim().isEmpty()) {
			dept_desc.clear();
			waitAndSendKeys(dept_desc, deptdesc);
		}
	}

	public WebElement getDeptNameField() {
		return dept_name;
	}

	public WebElement getDeptDescField() {
		return dept_desc;
	}

	@FindBy(xpath = "//button")
	List<WebElement> allButtons;

	public List<String> getDisplayedButtons() {
		List<String> buttons = new ArrayList<>();
		for (WebElement btn : allButtons) {
			if (btn.isDisplayed()) {
				buttons.add(btn.getText().trim());
			}
		}
		return buttons;
	}

	@FindBy(xpath = "//a")
	List<WebElement> allLinks;

	public List<String> getDisplayedLinks() {
		List<String> links = new ArrayList<>();
		for (WebElement link : allLinks) {
			if (link.isDisplayed()) {
				String text = link.getText().trim();
				if (!text.isEmpty()) {
					links.add(text);
				}
			}
		}
		return links;
	}

	@FindBy(xpath = "//div[contains(@class,'sign-up')]//img")
	List<WebElement> allImages;

	public List<String> getDisplayedImages() {
		List<String> images = new ArrayList<>();
		for (WebElement img : allImages) {
			if (img.isDisplayed()) {
				String src = img.getAttribute("src");
				if (src != null && !src.isEmpty()) {
					// Just getting the filename for simpler validation
					String fileName = src.substring(src.lastIndexOf('/') + 1);
					images.add(fileName);
				}
			}
		}
		return images;
	}

	public void validateViewIconWithStatus(String name, boolean exp) throws Exception {
		validateIcon(exp, "pi-eye", name);
	}

	public void validateEditIconWithStatus(String name, boolean exp) throws Exception {
		validateIcon(exp, "pi-pencil", name);
	}

	public void validateActionsStrict(String name, List<String> expItems) throws Exception {
		List<String> actual = (List<String>) scanTable("//tbody[@role='rowgroup']/tr",
				"//span[@class='p-paginator-pages ng-star-inserted']/button", TableActionType.CHECK_MENU, "", false,
				name);
		if (!actual.containsAll(expItems) || !expItems.containsAll(actual))
			throw new RuntimeException("Actions mismatch. Exp: " + expItems + ", Found: " + actual);
	}

	public String getStatus(String name) throws Exception {
		return super.getStatus(name);
	}

	// ================= LIST PAGE VALIDATION METHODS =================

	/**
	 * Get the filter toggle button (Filter icon/text)
	 */
	public WebElement getFilterToggleButton() {
		return driver
				.findElement(By.xpath("//div[contains(@class,'toggle-icon')]//span[contains(@class,'be-filter')]"));
	}

	/**
	 * Check if filter panel is expanded
	 */
	public boolean isFilterPanelExpanded() {
		try {
			WebElement filterContent = driver.findElement(By.xpath("//div[contains(@class,'custom-panel-content')]"));
			String style = filterContent.getAttribute("style");
			return style != null && style.contains("opacity: 1");
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Click filter toggle to expand/collapse
	 */
	public void clickFilterToggle() {
		getFilterToggleButton().click();
	}

	/**
	 * Get filter field by label name
	 */
	public WebElement getFilterFieldByLabel(String labelName) {
		return driver.findElement(By.xpath(
				"//label[normalize-space()='" + labelName + "']/following-sibling::input | " +
						"//label[normalize-space()='" + labelName + "']/following-sibling::*/input"));
	}

	/**
	 * Get filter button (Clear or Search)
	 */
	public WebElement getFilterButton(String buttonText) {
		return driver.findElement(By.xpath(
				"//div[@class='filter-buttons']//button[contains(text(),'" + buttonText + "')]"));
	}

	/**
	 * Check if create button is displayed
	 */
	public boolean isCreateButtonDisplayed() {
		try {
			return driver.findElement(By.xpath("//button[contains(text(),'Create') or contains(@class,'create')]"))
					.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Get create button
	 */
	public WebElement getCreateButton() {
		return driver.findElement(By.xpath("//button[contains(text(),'Create') or contains(@class,'create')]"));
	}

	/**
	 * Check if user has specific privilege (CREATE, EDIT, DELETE, etc.)
	 */
	public boolean hasPrivilege(String privilegeType) {
		// This should query the database or check session/token
		// For now, returning true as placeholder - implement actual logic
		try {
			return utilities.DatabaseBackupUtil.hasAuthority(currentUsername, privilegeType);
		} catch (Exception e) {
			return true; // Default to true for testing
		}
	}

	/**
	 * Get preferences dropdown
	 */
	public WebElement getPreferencesDropdown() {
		return driver
				.findElement(By.xpath("//div[@class='dropdown preference-dropdown']//p[@class='dropdown-trigger']"));
	}

	/**
	 * Get item count text (e.g., "31 Item(s) found")
	 */
	public String getItemCountText() {
		return driver.findElement(By.xpath("//div[@class='title']")).getText().trim();
	}

	/**
	 * Get PDF button
	 */
	public WebElement getPDFButton() {
		return driver.findElement(By.xpath("//span[@class='dropdown-trigger pdf-button']"));
	}

	/**
	 * Get Excel button
	 */
	public WebElement getExcelButton() {
		return driver.findElement(By.xpath("//span[@class='dropdown-trigger excel-button']"));
	}

	/**
	 * Get all table headers
	 */
	public List<String> getTableHeaders() {
		List<String> headers = new ArrayList<>();
		List<WebElement> headerElements = driver.findElements(By.xpath("//thead[@class='p-datatable-thead']//th"));
		for (WebElement header : headerElements) {
			String text = header.getText().trim();
			if (!text.isEmpty()) {
				headers.add(text);
			}
		}
		return headers;
	}

	/**
	 * Get pagination button (First, Previous, Next, Last)
	 */
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
		return driver.findElement(By.xpath(xpath));
	}

	/**
	 * Get rows per page dropdown
	 */
	public WebElement getRowsPerPageDropdown() {
		return driver.findElement(By.xpath("//p-dropdown[@styleclass='p-paginator-rpp-options']"));
	}

	/**
	 * Get current page info (e.g., "1 - 10 of 31")
	 */
	public String getCurrentPageInfo() {
		return driver.findElement(By.xpath("//span[contains(@class,'p-paginator-current')]")).getText().trim();
	}

	/**
	 * Get the count of visible table rows in the current page
	 */
	public int getTableRowCount() {
		List<WebElement> rows = driver.findElements(By.xpath("//tbody[@role='rowgroup']/tr"));
		return rows.size();
	}

	/**
	 * Extract total items count from "X Item(s) found" text
	 * 
	 * @return Total number of items (e.g., 31 from "31 Item(s) found")
	 */
	public int extractTotalItemsCount() {
		String itemCountText = getItemCountText();
		// Extract number from text like "31 Item(s) found"
		String[] parts = itemCountText.split(" ");
		if (parts.length > 0) {
			try {
				return Integer.parseInt(parts[0].trim());
			} catch (NumberFormatException e) {
				return -1;
			}
		}
		return -1;
	}

	/**
	 * Get currently selected Results Per Page value
	 * 
	 * @return Number of results per page (e.g., 10, 20, 50, 100)
	 */
	public int getResultsPerPageValue() {
		try {
			// Get the currently selected dropdown value
			WebElement dropdown = driver.findElement(
					By.xpath(
							"//p-dropdown[@styleclass='p-paginator-rpp-options']//span[contains(@class,'p-dropdown-label')]"));
			String value = dropdown.getText().trim();
			return Integer.parseInt(value);
		} catch (Exception e) {
			return -1;
		}
	}

	/**
	 * Get all displayed page numbers
	 * 
	 * @return List of page numbers (e.g., [1, 2, 3, 4])
	 */
	public List<Integer> getDisplayedPageNumbers() {
		List<Integer> pageNumbers = new ArrayList<>();
		List<WebElement> pageButtons = driver
				.findElements(By.xpath("//span[@class='p-paginator-pages ng-star-inserted']/button"));
		for (WebElement button : pageButtons) {
			try {
				String text = button.getText().trim();
				if (!text.isEmpty()) {
					pageNumbers.add(Integer.parseInt(text));
				}
			} catch (NumberFormatException e) {
				// Skip non-numeric buttons
			}
		}
		return pageNumbers;
	}

	/**
	 * Calculate expected number of pages based on total items and results per page
	 * 
	 * @param totalItems     Total number of items
	 * @param resultsPerPage Results per page
	 * @return Expected number of pages
	 */
	public int calculateExpectedPages(int totalItems, int resultsPerPage) {
		if (resultsPerPage <= 0)
			return 0;
		return (int) Math.ceil((double) totalItems / resultsPerPage);
	}

	/**
	 * Parse range info from "1 - 10 of 31" format
	 * 
	 * @return Array of [startRange, endRange, totalCount]
	 */
	public int[] parseRangeInfo() {
		String rangeText = getCurrentPageInfo();
		// Parse "1 - 10 of 31" format
		try {
			String[] parts = rangeText.split(" ");
			if (parts.length >= 5) {
				int start = Integer.parseInt(parts[0].trim());
				int end = Integer.parseInt(parts[2].trim());
				int total = Integer.parseInt(parts[4].trim());
				return new int[] { start, end, total };
			}
		} catch (Exception e) {
			return new int[] { -1, -1, -1 };
		}
		return new int[] { -1, -1, -1 };
	}

	/**
	 * Click on Results Per Page dropdown to open options
	 */
	public void clickResultsPerPageDropdown() {
		WebElement dropdown = driver.findElement(By.xpath(
				"//p-dropdown[@styleclass='p-paginator-rpp-options']//div[contains(@class,'p-dropdown-trigger')]"));
		scrollAndClick(dropdown);
	}

	/**
	 * Select a specific Results Per Page value
	 * 
	 * @param value The value to select (e.g., 10, 20, 50, 100)
	 */
	public void selectResultsPerPage(int value) throws InterruptedException {
		clickResultsPerPageDropdown();
		Thread.sleep(500); // Wait for dropdown to open
		WebElement option = driver.findElement(By.xpath("//li[@role='option']//span[text()='" + value + "']"));
		waitForElementandClick(option);
		Thread.sleep(500); // Wait for page to update
	}

	/**
	 * Click on a specific page number
	 * 
	 * @param pageNumber The page number to click (1, 2, 3, etc.)
	 */
	public void clickPageNumber(int pageNumber) throws InterruptedException {
		try {
			WebElement pageButton = driver.findElement(
					By.xpath(
							"//span[@class='p-paginator-pages ng-star-inserted']/button[@aria-label='" + pageNumber
									+ "']"));

			// Scroll into view
			((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
					"arguments[0].scrollIntoView({block: 'center'});", pageButton);
			Thread.sleep(200);

			pageButton.click();
			Thread.sleep(500); // Wait for page to load
			log.info("Clicked page number: {}", pageNumber);
		} catch (Exception e) {
			log.error("Failed to click page number: {}", pageNumber);
			throw e;
		}
	}

	/**
	 * Get the current active page number
	 * 
	 * @return Current page number (1, 2, 3, etc.)
	 */
	public int getCurrentPageNumber() {
		try {
			WebElement activePage = driver.findElement(
					By.xpath(
							"//span[@class='p-paginator-pages ng-star-inserted']/button[contains(@class,'p-highlight')]"));
			return Integer.parseInt(activePage.getText().trim());
		} catch (Exception e) {
			// If no highlighted page, assume page 1
			return 1;
		}
	}

	/**
	 * Check if pagination exists (more than one page)
	 * 
	 * @return true if pagination exists, false otherwise
	 */
	public boolean isPaginationPresent() {
		try {
			List<WebElement> pageButtons = driver.findElements(
					By.xpath("//span[@class='p-paginator-pages ng-star-inserted']/button"));
			return pageButtons.size() > 1;
		} catch (Exception e) {
			return false;
		}
	}

	

	public List<Map<String, String>> getUserActivityDetailsUI() {
		List<Map<String, String>> uiDetails = new ArrayList<>();

		try {
			String headingXpath = "//*[normalize-space()='User Activity Details']";
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(headingXpath)));

			// Find all labels in the section
			List<WebElement> allLabels = driver
					.findElements(By.xpath("//*[normalize-space()='User Activity Details']/following::label"));
			log.info("UI Capture: Found {} labels in User Activity Details", allLabels.size());

			for (WebElement labelElement : allLabels) {
				String label = labelElement.getText().trim();
				if (label.isEmpty())
					continue;

				String value = "";
				try {
					// 1. Try to find an input/textarea associated with this label
					List<WebElement> inputs = labelElement.findElements(By.xpath(
							"./following-sibling::input | ./following-sibling::*/input | ./following::input[1]"));

					if (!inputs.isEmpty()) {
						value = inputs.get(0).getAttribute("value");
					}

					// 2. Fallback: If no input value was found, capture the text of the next
					// sibling (for span/div/p based values)
					if (value == null || value.trim().isEmpty()) {
						value = labelElement.findElement(By.xpath("./following-sibling::*[1]")).getText().trim();
					}
				} catch (Exception e) {
					log.debug("Minimal value search failed for label [{}], attempting fallback text capture", label);
					try {
						value = labelElement.findElement(By.xpath("./following::*[1]")).getText().trim();
					} catch (Exception e2) {
					}
				}

				java.util.Map<String, String> fieldData = new java.util.LinkedHashMap<>();
				fieldData.put("label", label);
				fieldData.put("value", (value != null) ? value.trim() : "");
				uiDetails.add(fieldData);

				log.info("Captured Activity UI - Label: [{}], Value: [{}]", label, fieldData.get("value"));
			}
		} catch (Exception e) {
			log.error("Failed to capture User Activity Details from UI: {}", e.getMessage());
		}

		return uiDetails;
	}
}
