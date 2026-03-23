package testCasesForOQProjects;

import static configData.DepartmentData.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageObjects.Department;
import testBase.BaseClass;
import utilities.DatabaseBackupUtil;
import utilities.ScreenshotUtil;
import utilities.SoftAssertionUtil;

// Refactored to use BasePage helper methods and static FieldDetails

import utilities.CSVUtility;
import java.util.stream.Collectors;
import org.testng.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Department_TC extends BaseClass {
	Department dept;
	String currentDeptName; // Track the current name (including edits)
	SoftAssertionUtil sa;

	@BeforeClass
	@Parameters({ "configFile" })
	public void setUp(@Optional("department.properties") String configFile) throws Exception {
		log.info("--- Starting Department Test Case Setup with config: {} ---", configFile);

		// Load the dynamic property file
		configData.DepartmentData.loadProperties(configFile);

		// Set conditional screenshot execution
		boolean screenshotsEnabled = "yes".equalsIgnoreCase(TAKE_SCREENSHOTS);
		ScreenshotUtil.setIsEnabled(screenshotsEnabled);

		if (screenshotsEnabled) {
			log.info("📸 Processing screenshot template and headers...");
			ScreenshotUtil.loadTemplateForEndAppend(TEMPLATE_PATH, OUTPUT_PATH);
			ScreenshotUtil.updateHeaderCellText(ACTUALHEADER, EXPECTEDHEADER);
			ScreenshotUtil.initScript(SCRIPT_NUMBER);
		}

		browserOpen();
		dept = new Department(driver);
		sa = new SoftAssertionUtil();
		dept.setTableHeaders(TABLE_HEADERS);
		log.info("Setup completed. Screenshots enabled: {}", screenshotsEnabled);
	}

	 @BeforeMethod
	    public void initSoftAssert() {
	        sa = new SoftAssertionUtil();
	    }
	
	// ========================Google Search Box=============

	@Test(groups = { "setup" }, priority = 1)
	public void initialSetUp() throws Exception {
		ScreenshotUtil.nextStep();
		log.info("Navigating to Chrome URL: {}", CHROME_URL);
		driver.navigate().to(CHROME_URL);
		log.info("Waiting for search box to be visible");
		dept.waitForElementToVisible(dept.getSearchBox());
		Assert.assertTrue(dept.getSearchBox().isDisplayed(), "Search box is not visible!");
		log.info("Searching for Application URL: {}", APP_URL);
		dept.searchBox(APP_URL);
		dept.waitForLoading();
		ScreenshotUtil.capture();
		log.info("Initial setup completed and screenshot captured");
	}

	// ===================URL Navigation========================

	@Test(groups = { "url" }, priority = 2)
	public void url() throws Throwable {
		driver.navigate().to(APP_URL);
		log.info("Navigating to App URL: {}", APP_URL);
	}

	// ====================URL Validation===================

	@Test(groups = { "userlogin" }, priority = 3)
	public void userLoginBeforeCreate() throws Throwable {
		// Clear existing user session from DB to prevent login issues
		log.info("Executing the flow with single/multiple : {}", ACTIONSPERFORMEDBY);
		if (ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
			dept.login(USERNAME, PASSWORD, PC_DB_NAME);
		} else {
			dept.login(USERNAME1, PASSWORD1, PC_DB_NAME);
		}
	}

	// ===========================APP AND MASTER Click========================

	@Test(groups = { "moduleselect" }, priority = 4)
	public void moduleClick() throws Throwable {
		log.info("--- Clicking Module ---");
		ScreenshotUtil.nextStep();
		dept.click_titleMasters();
		log.info("Clicked on Masters title");
		dept.waitForLoading();
		ScreenshotUtil.capture();
		ScreenshotUtil.nextStep();
		dept.masterClick(MASTER_MODULE);
		log.info("Clicked on Master Module: {}", MASTER_MODULE);
		dept.waitForLoading();
		ScreenshotUtil.capture();
	}

	// ===========================CREATION OF DEPARTMENT========================

	@Test(groups = { "Creation" }, priority = 5)
	public void Creation_Of_Department() throws Throwable {
		log.info("--- Navigating to Create Department Screen ---");
		log.info("--- Creating Department: {} ---", DEPT_NAME);
		currentDeptName = DEPT_NAME;
		dept.Create();
		dept.waitForLoading();
		ScreenshotUtil.capture();
		ScreenshotUtil.nextStep();
		dept.deptName(DEPT_NAME);
		dept.deptDesc(DEPT_DESC);
		log.info("Entered Name and Description");
		ScreenshotUtil.capture();
		dept.clickSubmit();
		log.info("Clicked Submit");
		ScreenshotUtil.capture();
		dept.authenticate(dept.currentPassword);
		String authToast = dept.waitForToast();
		dept.waitForLoading();
		ScreenshotUtil.capture();
		Assert.assertEquals(authToast, "Department created successfully", "Creation failed with message: " + authToast);
		ScreenshotUtil.nextStep();
		ScreenshotUtil.capture();
	}

	// ===========================CLICKING ACTIONS MENU TO VERIFY
	// OPTIONS========================

	@Test(groups = { "ClickActions" }, priority = 6)
	public void Click_Actions_After_Create() throws Throwable {
		log.info("--- Attempting to open Actions Menu for: {} ---", currentDeptName);
		ScreenshotUtil.nextStep();
		dept.clickActions(currentDeptName);
		log.info("Successfully opened Actions menu for {}", currentDeptName);
		ScreenshotUtil.capture();
	}

	// ========================CLICKING VIEW TO VERIFY
	// DETAILS========================

	@Test(groups = { "ClickView" }, priority = 7)
	public void Click_View() throws Throwable {
		if (DEPARTMENT_VIEW.equalsIgnoreCase("yes")) {
			log.info("--- Viewing Department: {} ---", currentDeptName);
			ScreenshotUtil.nextStep();
			dept.clickView(currentDeptName);
			log.info("View screen opened");
			dept.waitForLoading();
			ScreenshotUtil.capture();
			dept.clickBack();
			log.info("Clicked Back button");
			dept.waitForLoading();
			ScreenshotUtil.capture();
		} else {
			log.info("View step skipped based on configuration");
		}
	}

	// ===========================RETURN AND EDIT========================

	@Test(groups = { "DeptReturn_DeptEdit" }, priority = 8)
	public void Dept_Return_and_Edit() throws Throwable {

		if (DEPARTMENT_RETURN.equalsIgnoreCase("yes")) {

			if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
				dept.switchUser(USERNAME2, PASSWORD2, PC_DB_NAME, MASTER_MODULE);
			}

			log.info("--- Initiating Return Flow for: {} ---", currentDeptName);
			ScreenshotUtil.nextStep();
			log.info("Opening actions menu to access Approve/Return");
			dept.clickActions(currentDeptName);
			ScreenshotUtil.capture();
			log.info("Clicking Approve to trigger return dialog");
			dept.clickApprove();
			dept.waitForLoading();
			ScreenshotUtil.capture();
			dept.enterRemarks(RETURN_REMARKS);
			log.info("Entered Return remarks: {}", RETURN_REMARKS);
			ScreenshotUtil.capture();
			dept.clickReturn();
			log.info("Clicked Return button");
			ScreenshotUtil.capture();
			dept.authenticate(dept.currentPassword);
			String returnToast = dept.waitForToast();
			Assert.assertEquals(returnToast, "Department returned successfully",
					"Return failed with message: " + returnToast);

			if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
				dept.switchUser(USERNAME1, PASSWORD1, PC_DB_NAME, MASTER_MODULE);
			}

			// This part will now execute for BOTH single and multiple users
			log.info("Opening Edit screen (After Return)");
			dept.waitForLoading();
			ScreenshotUtil.capture();
			ScreenshotUtil.nextStep();
			dept.clickEdit(currentDeptName);
			dept.waitForLoading();
			ScreenshotUtil.capture();

			if (EDIT_DEPT_NAME_AFTER_RETRUN != null && !EDIT_DEPT_NAME_AFTER_RETRUN.trim().isEmpty()) {
				log.info("Updating Name to: {}", EDIT_DEPT_NAME_AFTER_RETRUN);
				dept.deptName(EDIT_DEPT_NAME_AFTER_RETRUN);
				currentDeptName = EDIT_DEPT_NAME_AFTER_RETRUN; // Update tracker
			}

			if (EDIT_DEPT_DESC_AFTER_RETURN != null && !EDIT_DEPT_DESC_AFTER_RETURN.trim().isEmpty()) {
				log.info("Updating Description to: {}", EDIT_DEPT_DESC_AFTER_RETURN);
				dept.deptDesc(EDIT_DEPT_DESC_AFTER_RETURN);
			}

			ScreenshotUtil.capture();
			dept.clickUpdate();
			log.info("Clicked Update");
			ScreenshotUtil.capture();
			dept.authenticate(dept.currentPassword);
			dept.waitForLoading();
			ScreenshotUtil.capture();
		} else {
			log.info("Department Return and Edit skipped based on configuration");
		}
	}

	// ===========================DEPARTMENT APPROVAL========================

	@Test(groups = { "DeptApprove" }, priority = 9)
	public void Department_Approve() throws Throwable {

		if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
			log.info("--- Approving Department: {} ---", currentDeptName);
			dept.switchUser(USERNAME2, PASSWORD2, PC_DB_NAME, MASTER_MODULE);
		}
		log.info("--- Performing Final Approval for: {} ---", currentDeptName);
		ScreenshotUtil.nextStep();
		log.info("Opening actions menu for approval");
		dept.clickActions(currentDeptName);
		ScreenshotUtil.capture();
		log.info("Clicking Approve button in the list");
		dept.clickApprove();
		ScreenshotUtil.capture();
		dept.enterRemarks(APPROVE_REMARKS);
		log.info("Entered Approve remarks: {}", APPROVE_REMARKS);
		ScreenshotUtil.capture();
		dept.clickApprove();
		log.info("Submitted Approval");
		ScreenshotUtil.capture();
		dept.authenticate(dept.currentPassword);
		String approveToast = dept.waitForToast();
		dept.waitForLoading();
		ScreenshotUtil.capture();
		Assert.assertEquals(approveToast, "Department approved successfully",
				"Approval failed with message: " + approveToast);
	}

	// ===============Search filters========================
	@Test(groups = { "SearchFilter" }, priority = 10)
	public void Search_Filter() throws Throwable {
		log.info("--- Validating Search Filters for: {} ---", currentDeptName);
		ScreenshotUtil.nextStep();
		if (!dept.isFilterPanelExpanded()) {
			log.info("Filter panel is already not expanded so clicking on Filter button to expand it");
			dept.clickFilterToggle();
		}
		dept.clickDepartmentNameFilter(currentDeptName);
		ScreenshotUtil.capture();
		dept.clickSearch();
		dept.waitForLoading();
		ScreenshotUtil.capture();
	}

	// =====================DUPLICATION CHECK IN CREATE PAGE========================

	@Test(groups = { "duplicationcheck" }, priority = 11)
	public void Duplication_Check() throws Throwable {

		ScreenshotUtil.nextStep();
		log.info("--- Checking Duplication Check with Department In Create page: {} ---", currentDeptName);
		dept.Create();
		dept.deptName(currentDeptName);
		ScreenshotUtil.capture();// Using currentDeptName to ensure we test with the actual name in the system);
		dept.clickDeptName();
		dept.clickSubmit();
		dept.waitForToast();
		ScreenshotUtil.capture();
	}

	// ===========================LOGOUT========================

	@Test(groups = { "Logout" }, priority = 12)
	public void Logout() throws Throwable {
		log.info("--- Executing Final Logout ---");
		ScreenshotUtil.nextStep();
		dept.logout();
		log.info("Clicked logout button");
		dept.waitForToast();
		dept.waitForLoading();
		ScreenshotUtil.capture();
		log.info("--- Department Test Case Execution Finished ---");
	}

	// ===========================DATABASE BACKUP========================
	@Test(groups = { "DB Back" }, priority = 13)
	public void Database_Backup() {
		log.info("--- Initiating Post-Test Database Backup ---");

		// Fallback logic: Use config filename if script number is missing
		String backupFolderName = (SCRIPT_NUMBER == null || SCRIPT_NUMBER.trim().isEmpty()) ? CURRENT_CONFIG_NAME
				: SCRIPT_NUMBER;

		log.info("Backup folder name determined: {}", backupFolderName);

		// Parameters: folderName, dbName, dbUser, dbPass, host, port
		DatabaseBackupUtil.backupPostgres(backupFolderName, PC_DB_NAME, "postgres", "root", "localhost", "5432");
		DatabaseBackupUtil.backupPostgres(backupFolderName, MASTER_DB_NAME, "postgres", "root", "localhost", "5432");
		DatabaseBackupUtil.backupPostgres(backupFolderName, MM_DB_NAME, "postgres", "root", "localhost", "5432");
	}

	// ===========================LIST PAGE VALIDATION========================

	@Test(groups = { "listpagevalidation" }, priority = 11)
	public void List_Page_Validation() throws Throwable {
		log.info("--- Validating Department List Page UI Components ---");
		ScreenshotUtil.capture();
		// ================= DATA FROM CSV =================
		String csvPath = System.getProperty("user.dir") + "/src/test/resources/CSV_Data/ListPageValidation.csv";
		List<Map<String, String>> listPageData = CSVUtility.getRowsByModule(csvPath, "Department");
		// Separate data by component
		List<String> expectedFilterFields = listPageData.stream()
				.filter(row -> row.get("Component").equals("Filter") && row.get("ElementType").equals("input"))
				.map(row -> row.get("ElementName")).collect(Collectors.toList());
		List<String> expectedTableHeaders = listPageData.stream()
				.filter(row -> row.get("Component").equals("TableHeader")).map(row -> row.get("ElementName"))
				.collect(Collectors.toList());
		// ================= 1. BREADCRUMBS VALIDATION =================
		log.info("Phase 1: Validating Breadcrumbs...");
		List<String> actualBreadcrumbs = dept.getBreadcrumbsText();
		List<String> expectedBreadcrumbs = Arrays.asList("Home", MASTER_MODULE);
		sa.assertEquals(actualBreadcrumbs, expectedBreadcrumbs, "Breadcrumbs", "List Page");
		// ================= 2. FILTER PANEL VALIDATION =================
		log.info("Phase 2: Validating Filter Panel...");
		// Check filter toggle button exists
		WebElement filterToggle = dept.getFilterToggleButton();
		sa.assertTrue(filterToggle.isDisplayed(), "Filter Toggle Button", "Visibility");
		// By default, filter should be expanded (visible)
		boolean filterExpanded = dept.isFilterPanelExpanded();
		sa.assertTrue(filterExpanded, "Filter Panel Default State", "Should be expanded by default");
		// Validate filter fields
		for (String fieldName : expectedFilterFields) {
			log.info("Validating filter field: {}", fieldName);
			WebElement filterField = dept.getFilterFieldByLabel(fieldName);
			sa.assertNotNull(filterField, "Filter Field Existence", fieldName);
			sa.assertTrue(filterField.isDisplayed(), "Filter Field Visibility", fieldName);
			sa.assertTrue(filterField.isEnabled(), "Filter Field Enabled", fieldName);
			// Validate placeholder
			String actualPlaceholder = filterField.getDomAttribute("placeholder");
			String expectedPlaceholder = listPageData.stream()
					.filter(row -> row.get("ElementName").equals(fieldName) && row.get("Component").equals("Filter"))
					.map(row -> row.get("Placeholder")).findFirst().orElse("");
			sa.assertEquals(actualPlaceholder, expectedPlaceholder, "Filter Field Placeholder", fieldName);
		}
		// Validate Clear and Search buttons
		WebElement clearBtn = dept.getFilterButton("Clear");
		WebElement searchBtn = dept.getFilterButton("Search");
		sa.assertTrue(clearBtn.isDisplayed() && clearBtn.isEnabled(), "Clear Button", "Filter Panel");
		sa.assertTrue(searchBtn.isDisplayed() && searchBtn.isEnabled(), "Search Button", "Filter Panel");
		// Test filter collapse
		log.info("Testing filter panel collapse...");
		dept.clickFilterToggle();
		Thread.sleep(300);
		boolean filterCollapsed = !dept.isFilterPanelExpanded();
		sa.assertTrue(filterCollapsed, "Filter Panel Collapse", "Should be collapsed after toggle");
		ScreenshotUtil.capture();
		// Expand back for next validations
		dept.clickFilterToggle();
		Thread.sleep(300);
		ScreenshotUtil.capture();
		// ================= 3. CREATE BUTTON VALIDATION (PRIVILEGE-BASED)
		// =================
		log.info("Phase 3: Validating Create Button (Privilege-based)...");
		boolean hasCreatePrivilege = dept.hasPrivilege("DEPARTMENT_CREATE", "ROLE_ADMIN", "DEPARTMENT_ADMIN");
		boolean createButtonVisible = dept.isCreateButtonDisplayed();
		if (hasCreatePrivilege) {
			sa.assertTrue(createButtonVisible, "Create Button Visibility", "User has CREATE privilege");
			WebElement createBtn = dept.getCreateButton();
			sa.assertTrue(createBtn.isEnabled(), "Create Button Enabled", "User has CREATE privilege");
		} else {
			sa.assertFalse(createButtonVisible, "Create Button Hidden", "User does NOT have CREATE privilege");
		}
		// ================= 4. TABLE HEADER BAR VALIDATION =================
		log.info("Phase 4: Validating Table Header Bar...");
		// Preferences dropdown (left)
		WebElement preferencesDropdown = dept.getPreferencesDropdown();
		sa.assertTrue(preferencesDropdown.isDisplayed(), "Preferences Dropdown", "Visibility");
		// Total items count (center)
		String itemCountText = dept.getItemCountText();
		sa.assertTrue(itemCountText.contains("Item(s) found") || itemCountText.contains("found"), "Item Count Display",
				"Text: " + itemCountText);
		// PDF button (right)
		WebElement pdfButton = dept.getPDFButton();
		sa.assertTrue(pdfButton.isDisplayed(), "PDF Button", "Visibility");
		// Excel button (right)
		WebElement excelButton = dept.getExcelButton();
		sa.assertTrue(excelButton.isDisplayed(), "Excel Button", "Visibility");
		// ================= 5. TABLE COLUMNS VALIDATION =================
		log.info("Phase 5: Validating Table Columns...");
		List<String> actualTableHeaders = dept.getTableHeaders();
		log.info("Actual Table Headers: {}", actualTableHeaders);
		log.info("Expected Table Headers: {}", expectedTableHeaders);
		sa.assertEquals(actualTableHeaders, expectedTableHeaders, "Table Columns Contract", "Strict match expected");
		// ================= 6. PAGINATION VALIDATION =================
		log.info("Phase 6: Validating Pagination...");
		// Previous button
		WebElement prevPageBtn = dept.getPaginationButton("Previous");
		sa.assertTrue(prevPageBtn.isDisplayed(), "Previous Page Button", "Visibility");
		// Next button
		WebElement nextPageBtn = dept.getPaginationButton("Next");
		sa.assertTrue(nextPageBtn.isDisplayed(), "Next Page Button", "Visibility");
		// Rows per page dropdown
		WebElement rowsPerPageDropdown = dept.getRowsPerPageDropdown();
		sa.assertTrue(rowsPerPageDropdown.isDisplayed(), "Rows Per Page Dropdown", "Visibility");
		// Validate current page indicator
		String currentPageInfo = dept.getCurrentPageInfo();
		sa.assertTrue(currentPageInfo.matches("\\d+ - \\d+ of \\d+"), "Current Page Info Format",
				"Expected format: 'X - Y of Z', Actual: " + currentPageInfo);
		// ================= 6A. TABLE ROW COUNT VALIDATION =================
		log.info("Phase 6A: Validating Table Row Count vs Total Count...");
		int totalItemsCount = dept.extractTotalItemsCount();
		log.info("Total Items from header: {}", totalItemsCount);
		sa.assertTrue(totalItemsCount > 0, "Total Items Count Extraction", "Should be positive number");
		int tableRowCount = dept.getTableRowCount();
		log.info("Visible table rows: {}", tableRowCount);
		sa.assertTrue(tableRowCount > 0, "Table Row Count", "Should have visible rows");
		// Get current results per page
		int resultsPerPage = dept.getResultsPerPageValue();
		log.info("Results per page: {}", resultsPerPage);
		sa.assertTrue(resultsPerPage > 0, "Results Per Page Value", "Should be positive number");
		// Validate that table row count matches expected for current page
		int expectedRowsOnPage = Math.min(resultsPerPage, totalItemsCount);
		sa.assertEquals(tableRowCount, expectedRowsOnPage, "Table Row Count Match",
				"Expected " + expectedRowsOnPage + " rows, found " + tableRowCount);
		// ================= 6B. PAGE NUMBERS VALIDATION =================
		log.info("Phase 6B: Validating Page Numbers based on Results Per Page...");
		List<Integer> displayedPageNumbers = dept.getDisplayedPageNumbers();
		log.info("Displayed page numbers: {}", displayedPageNumbers);
		int expectedTotalPages = dept.calculateExpectedPages(totalItemsCount, resultsPerPage);
		log.info("Expected total pages: {} (Total: {}, Per Page: {})", expectedTotalPages, totalItemsCount,
				resultsPerPage);
		// Validate page count
		sa.assertEquals(displayedPageNumbers.size(), expectedTotalPages, "Page Numbers Count",
				"For " + totalItemsCount + " items with " + resultsPerPage + " per page");
		// Validate page numbers are sequential from 1 to expectedTotalPages
		List<Integer> expectedPageNumbers = new ArrayList<>();
		for (int i = 1; i <= expectedTotalPages; i++) {
			expectedPageNumbers.add(i);
		}
		sa.assertEquals(displayedPageNumbers, expectedPageNumbers, "Page Numbers Sequence",
				"Should be 1, 2, 3... up to " + expectedTotalPages);
		ScreenshotUtil.capture();
		// ================= 6C. RANGE DISPLAY VALIDATION =================
		log.info("Phase 6C: Validating Range Display (e.g., '1 - 10 of 31')...");
		int[] rangeInfo = dept.parseRangeInfo();
		int startRange = rangeInfo[0];
		int endRange = rangeInfo[1];
		int totalFromRange = rangeInfo[2];
		log.info("Parsed range: {} - {} of {}", startRange, endRange, totalFromRange);
		// Validate range parsing succeeded
		sa.assertTrue(startRange > 0, "Range Start", "Should be positive");
		sa.assertTrue(endRange > 0, "Range End", "Should be positive");
		sa.assertTrue(totalFromRange > 0, "Total from Range", "Should be positive");
		// Validate range start is 1 for first page
		sa.assertEquals(startRange, 1, "Range Start", "Should start at 1 for first page");
		// Validate range end matches expected (min of resultsPerPage or total items)
		int expectedEndRange = Math.min(resultsPerPage, totalItemsCount);
		sa.assertEquals(endRange, expectedEndRange, "Range End",
				"Should be " + expectedEndRange + " for first page with " + resultsPerPage + " per page");
		// Validate total in range matches header total
		sa.assertEquals(totalFromRange, totalItemsCount, "Total Count Consistency",
				"Range total should match header total");
		ScreenshotUtil.capture();
		// ================= 6D. PAGINATION WITH DIFFERENT RESULTS PER PAGE
		// =================
		log.info("Phase 6D: Testing pagination with different Results Per Page values...");
		// Store all results per page values to test
		int[] resultsPerPageValues = { 10, 20, 50, 100 };
		for (int perPageValue : resultsPerPageValues) {
			// Skip if total items is less than or equal to the per page value
			if (totalItemsCount <= perPageValue && perPageValue != 10) {
				log.info("Skipping {} per page (total items: {} <= {})", perPageValue, totalItemsCount, perPageValue);
				continue;
			}
			log.info("═══════════════════════════════════════════════════════");
			log.info("Testing with {} Results Per Page...", perPageValue);
			log.info("═══════════════════════════════════════════════════════");
			// Change results per page
			dept.selectResultsPerPage(perPageValue);
			dept.waitForLoading();
			Thread.sleep(500); // Extra wait for UI to stabilize
			// Verify the change was successful
			int actualPerPage = dept.getResultsPerPageValue();
			sa.assertEquals(actualPerPage, perPageValue, "Results Per Page Setting", "Should be " + perPageValue);
			// Calculate expected pages
			int expectedPages = dept.calculateExpectedPages(totalItemsCount, perPageValue);
			log.info("Total Items: {}, Per Page: {}, Expected Pages: {}", totalItemsCount, perPageValue, expectedPages);
			// Validate page numbers displayed
			List<Integer> displayedPages = dept.getDisplayedPageNumbers();
			sa.assertEquals(displayedPages.size(), expectedPages, "Page Count with " + perPageValue + " per page",
					"For " + totalItemsCount + " items");
			ScreenshotUtil.capture();
			// ===== ITERATE THROUGH ALL PAGES AND VALIDATE RANGE =====
			log.info("Clicking through all {} pages and validating range display...", expectedPages);
			for (int pageNum = 1; pageNum <= expectedPages; pageNum++) {
				log.info("--- Validating Page {} of {} ---", pageNum, expectedPages);
				// Click on the page number (if not already on it)
				if (pageNum > 1) {
					dept.clickPageNumber(pageNum);
					dept.waitForLoading();
				}
				// Verify current page is correct
				int currentPage = dept.getCurrentPageNumber();
				sa.assertEquals(currentPage, pageNum, "Current Page Number", "Should be on page " + pageNum);
				// Calculate expected range for this page
				int expectedStart = ((pageNum - 1) * perPageValue) + 1;
				int expectedEnd = Math.min(pageNum * perPageValue, totalItemsCount);
				// Start simple wait
				// Wait for range info to update (simple sleep)
				Thread.sleep(1000);
				// Get actual range from UI
				int[] rangeInfo1 = dept.parseRangeInfo();
				int actualStart = rangeInfo1[0];
				int actualEnd = rangeInfo1[1];
				int totalFromRange1 = rangeInfo1[2];
				log.info("Expected Range: {}-{} of {}", expectedStart, expectedEnd, totalItemsCount);
				log.info("Actual Range: {}-{} of {}", actualStart, actualEnd, totalFromRange1);
				// Validate range start
				sa.assertEquals(actualStart, expectedStart, "Range Start (Page " + pageNum + ")",
						"Per Page: " + perPageValue);
				// Validate range end
				sa.assertEquals(actualEnd, expectedEnd, "Range End (Page " + pageNum + ")",
						"Per Page: " + perPageValue);
				// Validate total count consistency
				sa.assertEquals(totalFromRange, totalItemsCount, "Total Count (Page " + pageNum + ")",
						"Should always be " + totalItemsCount);
				// Validate table row count matches expected
				int expectedRowsOnPage1 = expectedEnd - expectedStart + 1;
				int actualRowsOnPage = dept.getTableRowCount();
				sa.assertEquals(actualRowsOnPage, expectedRowsOnPage1, "Table Rows (Page " + pageNum + ")",
						"Should have " + expectedRowsOnPage1 + " rows");
				log.info("✅ Page {} validated: {}-{} of {} ({} rows)", pageNum, actualStart, actualEnd, totalFromRange,
						actualRowsOnPage);
			}
			ScreenshotUtil.capture();
			log.info("✅ Completed validation for {} results per page", perPageValue);
			// Go back to page 1 for next iteration
			if (expectedPages > 1) {
				dept.clickPageNumber(1);
				dept.waitForLoading();
			}
		}
		log.info("🎯 List Page UI validation completed successfully");
		ScreenshotUtil.capture();
		sa.assertAll();
	}

	// ==================================SIDE-NAV VALIDATION========================

	@Test(groups = { "sidenavvalidation" }, dependsOnMethods = "List_Page_Validation", priority = 12)
	public void SideNav_Validation() throws Throwable {
		log.info("--- Validating Side-Navigation Modules Based on DB Privileges ---");

		// 1. Get dynamically expected side-nav modules from DB
		List<String> expectedModules = dept.getExpectedSideNavFromDB(dept.currentUsername, SIDE_NAV_MODULE_MAPPING,
				PC_DB_NAME);
		log.info("Dynamically determined expected side-nav modules: {}", expectedModules);

		// 2. Expand each parent module and collect visible items incrementally (to
		// handle accordion behavior)
		List<String> finalActualModules = new ArrayList<>();

		// Initial capture for static/already-open modules
		finalActualModules.addAll(dept.getAllSideNavModulesDisplayed());

		String[] mappings = SIDE_NAV_MODULE_MAPPING.split(";");
		for (String map : mappings) {
			String[] parts = map.split(":");
			if (parts.length > 2) {
				String parentModule = parts[0].trim();
				if (expectedModules.contains(parentModule)) {
					log.info("Expanding for incremental capture: {}", parentModule);
					dept.expandSideNavModule(parentModule);
					// Collect newly visible sub-modules
					for (String active : dept.getAllSideNavModulesDisplayed()) {
						if (!finalActualModules.contains(active))
							finalActualModules.add(active);
					}
				}
			}
		}
		ScreenshotUtil.capture();

		System.out.println("====================================================================");
		System.out.println("SIDE-NAV VALIDATION FOR USER: " + dept.currentUsername);
		System.out.println("Expected Modules from DB Mapping: " + expectedModules);
		System.out.println("Actual Combined Modules from UI : " + finalActualModules);
		System.out.println("====================================================================");

		// 3. Conditional Assertion
		List<String> missing = expectedModules.stream().filter(e -> !finalActualModules.contains(e)).toList();
		boolean isAdmin = utilities.DatabaseBackupUtil.hasAuthority(dept.currentUsername, "ROLE_ADMIN");

		if (isAdmin) {
			log.info("Performing STRICT sidebar validation for ROLE_ADMIN: UI must exactly match DB Mapping.");
			sa.assertEquals(finalActualModules.size(), expectedModules.size(), "Side-Nav Count mismatch",
					"Total Modules");
			sa.assertTrue(missing.isEmpty(), "Module Presence", "Missing: " + missing);

			List<String> extra = finalActualModules.stream().filter(a -> !expectedModules.contains(a)).toList();
			sa.assertTrue(extra.isEmpty(), "Module Restriction", "Extra: " + extra);
		} else {
			log.info("Performing PRESENCE sidebar validation for User {}: UI must contain all DB-authorized modules.",
					dept.currentUsername);
			// For non-admin, we only care if they HAVE what they should have. Extra
			// visibility is ignored here.
			sa.assertTrue(missing.isEmpty(), "Module Presence",
					"Database-authorized modules missing from UI: " + missing);
		}

		log.info("Side-Navigation validation successfully completed.");
		sa.assertAll();
	}

	// ===========================CREATION SCREEN VALIDATION========================

	@Test(groups = { "creationvalidation" }, priority = 13)
	public void Creation_Screen_Validation() throws Throwable {
		log.info("--- Validating Department Creation Screen ---");
		dept.Create();
		dept.waitForLoading();
		ScreenshotUtil.capture();
		// ================= DATA FROM CSV =================
		String csvPath = System.getProperty("user.dir") + "/src/test/resources/CSV_Data/CreateScreenValidation.csv";
		List<Map<String, String>> screenData = CSVUtility.getRowsByModule(csvPath, "Department");
		List<String> expectedFieldNames = screenData.stream().map(row -> row.get("FieldName"))
				.collect(Collectors.toList());
		List<String> expTypes = screenData.stream().map(row -> row.get("FieldType")).collect(Collectors.toList());
		List<String> expPlaceholders = screenData.stream().map(row -> row.get("Placeholder"))
				.collect(Collectors.toList());
		List<String> mandatoryFields = screenData.stream().filter(row -> row.get("isMandatory").equalsIgnoreCase("Yes"))
				.map(row -> row.get("FieldName")).collect(Collectors.toList());
		// ================= EXPECTED BUTTONS =================
		List<String> expectedButtons = Arrays.asList("Cancel", "Submit");
		// ================= ACTUAL UI DATA =================
		List<String> actualFields = dept.getDisplayedFieldLabels();
		List<String> actualButtons = dept.getDisplayedButtons();
		log.info("Actual Fields on UI  : " + actualFields);
		log.info("Actual Buttons on UI : " + actualButtons);
		// ================= STRICT FIELD CONTRACT =================
		sa.assertEquals(actualFields, expectedFieldNames, "Fields Contract", "Strict match expected for fields");
		// ================= STRICT BUTTON CONTRACT =================
		sa.assertEquals(actualButtons, expectedButtons, "Buttons Contract", "Strict match expected for buttons");
		// ================= INDIVIDUAL FIELD VALIDATION =================
		for (int i = 0; i < expectedFieldNames.size(); i++) {
			String fieldName = expectedFieldNames.get(i).trim();
			String expectedType = expTypes.get(i).trim();
			String expectedPH = expPlaceholders.get(i).trim();
			log.info("Validating Field: [" + fieldName + "]");
			// existence & visibility
			sa.assertTrue(dept.isLabelDisplayed(fieldName), "Label visibility", fieldName);
			WebElement input = dept.getInputFieldForLabel(fieldName);
			sa.assertNotNull(input, "Field existence in DOM", fieldName);
			sa.assertTrue(input.isDisplayed(), "Field visibility", fieldName);
			// Type validation (Input vs Drop)
			String actualType = dept.getFieldType(fieldName);
			sa.assertEquals(actualType, expectedType, "Field Type", fieldName);
			// Placeholder validation
			String actualPH = dept.getPlaceholder(fieldName);
			sa.assertEquals(actualPH, expectedPH, "Placeholder", fieldName);
			// Editable check
			sa.assertTrue(input.isEnabled(), "Field Editability", fieldName);
			// Mandatory Star check
			if (mandatoryFields.contains(fieldName)) {
				boolean hasRedStar = false;
				try {
					hasRedStar = dept.isRedStarDisplayedForField(fieldName).isDisplayed();
				} catch (Exception e) {
				}
				sa.assertTrue(hasRedStar, "Mandatory Red Star", fieldName);
			}
		}
		// ================= BUTTON STATE =================
		for (String btnName : expectedButtons) {
			WebElement btn = dept.getButtonByText(btnName);
			sa.assertNotNull(btn, "Button", btnName);
			sa.assertTrue(btn.isDisplayed(), "Button visible", btnName);
			sa.assertTrue(btn.isEnabled(), "Button enabled", btnName);
		}
		// ==============CHECKING CANCEL BUTTON===========
		log.info("Checking CANCEL button is working or not");
		dept.clickCancel();
		dept.waitForLoading();
		ScreenshotUtil.capture();
		log.info("Clicking Create button again to go back to creation screen");
		dept.Create();

		// ================= MANDATORY FIELD VALIDATION (3 PHASES) =================
		// PHASE 1: CLICK SUBMIT WHILE EMPTY -> VERIFY ERROR MESSAGES & RED STARS
		log.info("Phase 1: Submitting empty form to verify error messages...");
		Thread.sleep(500); // Wait for error messages to appear
		ScreenshotUtil.capture();
		for (String fieldName : mandatoryFields) {
			log.info("Checking error message and red star for: " + fieldName);
			sa.assertTrue(dept.getErrorMessage(fieldName).isDisplayed(), "Error Message Displayed", fieldName);
			sa.assertTrue(dept.isRedStarDisplayedForField(fieldName).isDisplayed(), "Red Star visibility", fieldName);
		}
		// PHASE 2: PROVIDE INPUT -> VERIFY GREEN STAR (COLOR CHANGE)
		log.info("Phase 2: Providing input to mandatory fields to verify green stars...");
		for (String fieldName : mandatoryFields) {
			if (fieldName.equalsIgnoreCase("Department Name")) {
				dept.deptName("OQ Validation Test3");
			} else {
				// Fill other mandatory fields if any
				dept.getInputFieldForLabel(fieldName).sendKeys("Validation Data");
			}
			log.info("Checking green star (color change) for: " + fieldName);
			sa.assertTrue(dept.isGreenStarDisplayedForField(fieldName).isDisplayed(),
					"Green Star (Valid Input Indicator)", fieldName);
		}
		ScreenshotUtil.capture();
		// PHASE 3: FILL ALL MANDATORY & SUBMIT -> VERIFY AUTHENTICATION POPUP
		log.info("Phase 3: Submitting complete form for Authentication check...");
		dept.clickSubmit();
		dept.waitForLoading();
		boolean authDisplayed = dept.isAuthPopupDisplayed();
		sa.assertTrue(authDisplayed, "Authentication Popup Displayed", "Submit Flow");
		log.info("Authentication Popup visibility: " + authDisplayed);
		ScreenshotUtil.capture();

		// ================= CHECKING AUTHENTICATION POPUP UI ELEMENTS & FUNCTIONALITY
		// =================

		// Validating Authenticate pop up UI elements
		log.info("Phase 3.1: Validating Authentication Popup UI elements...");
		sa.assertTrue(dept.isAuthPopupDisplayed(), "Auth Popup Visibility", "Authentication Popup");

		// 1. Header Message
		String actualAuthTitle = dept.getAuthPopupTitle();
		log.info("Auth Popup Title: " + actualAuthTitle);
		sa.assertTrue(dept.isAuthPopupTitleDisplayed(), "Auth Popup Title", "Authentication P Title");
		sa.assertEquals(actualAuthTitle, "Authenticate", "Authenticate Title", "Auth Popup Header");

		// 2. Field Placeholder
		String actualAuthPH = dept.getAuthPasswordPlaceholder();
		log.info("Auth Password Placeholder: " + actualAuthPH);
		sa.assertEquals(actualAuthPH, "Your password", "Password Placeholder", "Auth Password Placeholder");

		// 3. Authenticate Button Text
		String actualAuthBtnText = dept.getAuthButtonText();
		log.info("Auth Button Text and Button: " + actualAuthBtnText);
		sa.assertTrue(dept.isAuthButtondDisplayed(), "Auth Button Visibility", "Authentication button");
		sa.assertEquals(actualAuthBtnText, "Authenticate", "Authenticate Button Text", "Auth Button Text");

		// 4. Detailed Field Assertions (Count, Visibility, Type, Editability)
		log.info("Checking Authentication Popup fields detail...");
		int labelCount = dept.getAuthPopupLabelCount();
		int inputCount = dept.getAuthPopupInputCount();
		log.info("Auth Popup Label Count: " + labelCount + ", Input Count: " + inputCount);
		sa.assertTrue(labelCount >= 1, "Auth Popup Label Count", "At least 1 label expected");
		sa.assertEquals(inputCount, 1, "Auth Popup Input Count", "Exactly 1 input field expected");

		sa.assertTrue(dept.isAuthPopupPasswordDisplayed(), "Password field visibility", "Auth Popup");
		sa.assertEquals(dept.getAuthPopupInputTagName(), "input", "Field Tag Type", "Auth Password Field");
		sa.assertTrue(dept.isAuthPopupPasswordFieldEnabled(), "Field Editable", "Auth Password Field");

		// 5. Close Icon Visibility
		sa.assertTrue(dept.isAuthPopupClosedDisplayed(), "Authenticate Close icon", "Auth Popup Close Icon Visibility");

		// 6. Eye Icon Visibility
		sa.assertTrue(dept.isAuthPasswordEyeiconDisplayed(), "Password eyeicon", "Auth Password Eye Icon Visibility");

		// 7. Eye Icon Toggle Validation
		log.info("Checking Password Eye Icon Toggle functionality...");
		String initialType = dept.getAuthPasswordFieldType();
		sa.assertEquals(initialType, "password", "Password masked", "Initial Password field type");

		dept.clickAuthPasswordEyeIcon();
		log.info("Clicked Eye Icon to show password");
		String shownType = dept.getAuthPasswordFieldType();
		sa.assertEquals(shownType, "text", "Password unmasked", "Password field type after clicking eye icon");

		dept.clickAuthPasswordEyeIcon();
		log.info("Clicked Eye Icon again to hide password");
		String hiddenType = dept.getAuthPasswordFieldType();
		sa.assertEquals(hiddenType, "password", "Password masked again", "Password field type after toggling back");

		log.info("All Authentication Popup UI elements, field properties and eye-icon toggle validated successfully.");

		// 8. Closing Authentication Popup

		dept.closeAuthenticatePopup();
		dept.waitForAuthPopupInvisibility();
		sa.assertFalse(dept.isAuthPopupDisplayed(), "Authentication Popup is not Displayed", "Submit Flow");
		log.info("Authentication Popup closed successfully ");

		// ================= CHECKING SUBMIT BUTTON FUNCTIONALITY AFTER CLOSING AUTH
		// POPUP =================
		log.info("Rechecking Submit button functionality after closing auth popup... ");
		WebElement btn = dept.getButtonByText("Submit");
		sa.assertTrue(btn.isEnabled(), "Button enabled", "Submit");
		dept.clickSubmit();

		// ====================== CHECKING DOUBLE CLICK FUCNTIONALITY AFTER
		// AUTHENTICATION=================
		log.info("Checking double click of Submit button functionality after authentication");
		dept.authenticateWithoutScreenshot(dept.currentPassword);
		btn = dept.getSubmitButton();
		dept.waitForElementToDisabled(btn);
		sa.assertTrue(!dept.isButtonEnabled(btn), "Button disabled", "Submit");
		log.info("Submit button is disabled after authentication");

		log.info("🎯 Creation Screen UI Contract validated successfully");
		ScreenshotUtil.capture();
		sa.assertAll();

	}

	// ===========================EDIT SCREEN VALIDATION========================

	@Test(groups = { "editvalidation" }, priority = 10)
	public void Edit_Screen_Validation() throws Throwable {

		log.info("--- Validating Department Edit Screen ---");
		ScreenshotUtil.nextStep();

		// Open Edit
		dept.clickEdit("OQ Validation Test3");
		dept.waitForLoading();
		ScreenshotUtil.capture();

		// ================= DATA FROM CSV (Reusing Create Screen Def) =================
		String csvPath = System.getProperty("user.dir") + "/src/test/resources/CSV_Data/CreateScreenValidation.csv";
		List<Map<String, String>> screenData = CSVUtility.getRowsByModule(csvPath, "Department");

		List<String> expectedFieldNames = screenData.stream().map(row -> row.get("FieldName"))
				.collect(Collectors.toList());
		List<String> expPlaceholders = screenData.stream().map(row -> row.get("Placeholder"))
				.collect(Collectors.toList());
		List<String> mandatoryFields = screenData.stream().filter(row -> row.get("isMandatory").equalsIgnoreCase("Yes"))
				.map(row -> row.get("FieldName")).collect(Collectors.toList());

		// ================= EXPECTED BUTTONS =================
		// Assuming Edit screen has Update and Cancel
		List<String> expectedButtons = Arrays.asList("Update", "Back");

		// ================= ACTUAL UI DATA =================
		List<String> actualFields = dept.getDisplayedFieldLabels();
		List<String> actualButtons = dept.getDisplayedButtons();

		log.info("Actual Fields on UI: " + actualFields);
		log.info("Actual Buttons on UI: " + actualButtons);

		// ================= STRICT FIELD =================
		// Filter out User Activity labels before strict contract check
		List<String> auditLabelsEdit = java.util.Arrays.asList("Performed By", "Performed Date", "Modified By",
				"Modified Date", "Returned By Approver", "Returned Date", "Approved By", "Approved Date", "Remarks");
		List<String> filteredFieldsEdit = actualFields.stream().filter(f -> !auditLabelsEdit.contains(f))
				.collect(java.util.stream.Collectors.toList());
		sa.assertEquals(filteredFieldsEdit, expectedFieldNames, "Fields Contract",
				"Strict match expected for primary fields");

		// ================= INDIVIDUAL FIELD VALIDATION =================
		for (int i = 0; i < expectedFieldNames.size(); i++) {
			String fieldName = expectedFieldNames.get(i).trim();
			String csvPlaceholder = expPlaceholders.get(i).trim();

			log.info("Validating Edit Field: [" + fieldName + "]");

			// 1. Label & Input Existence
			sa.assertTrue(dept.isLabelDisplayed(fieldName), "Label visibility", fieldName);
			WebElement input = dept.getInputFieldForLabel(fieldName);
			sa.assertNotNull(input, "Input in DOM", fieldName);
			sa.assertTrue(input.isDisplayed(), "Input visible", fieldName);

			// 2. ENABLED State (Must be Editable)
			sa.assertTrue(input.isEnabled(), "Field Editable", fieldName + " should be editable in Edit mode");

			// 3. Placeholder Validation
			// Even with value, placeholder attribute should exist
			String actualPlaceholder = input.getDomAttribute("placeholder");
			sa.assertEquals(actualPlaceholder, csvPlaceholder, "Placeholder", fieldName);

			// 4. PRE-FILLED DATA Validation
			String actualValue = input.getDomProperty("value");
			String expectedValue = "";
			if (fieldName.equalsIgnoreCase("Department Name")) {
				expectedValue = "OQ Validation Test3";
			} else if (fieldName.equalsIgnoreCase("Description")) {
				expectedValue = "";
			}

			// In Edit Mode, data should be retained
			if (expectedValue != null && !expectedValue.isEmpty()) {
				sa.assertEquals(actualValue, expectedValue, "Retained Value", fieldName);
			}

			// 5. Initial Star Check (Assuming Valid Data -> Green Star or Hidden Red Star)
			// User requirement specifically asks to check Red Star when empty.
			// We skip initial star check here to verify it strictly in negative test below.
		}

		// ================= MANDATORY FIELDS NEGATIVE TEST =================
		log.info("--- Performing Negative Validation on Mandatory Fields ---");

		for (String fieldName : mandatoryFields) {
			log.info("Testing mandatory validation for: " + fieldName);

			WebElement input = dept.getInputFieldForLabel(fieldName);
			String originalValue = input.getDomProperty("value");

			// 1. Clear the field
			// input.clear();
			dept.clearField(input);
			// Ensure it's empty (some frameworks need sendKeys(Keys.chord(Keys.CONTROL,"a",
			// Keys.DELETE)))
			if (!input.getDomProperty("value").isEmpty()) {
				input.sendKeys(org.openqa.selenium.Keys.CONTROL + "a");
				input.sendKeys(org.openqa.selenium.Keys.DELETE);
			}
			Thread.sleep(500);

			// 2. Click Update to trigger validation
			dept.clickUpdate();
			Thread.sleep(500); // Wait for error

			// 3. Verify Error & Red Star
			sa.assertTrue(dept.getErrorMessage(fieldName).isDisplayed(), "Error Message Displayed", fieldName);
			try {
				sa.assertTrue(dept.isRedStarDisplayedForField(fieldName).isDisplayed(), "Red Star Displayed",
						fieldName);
			} catch (Exception e) {
				sa.assertFail("Red Star missing for empty mandatory field: " + fieldName);
			}

			// 4. Restore Value (Crucial to proceed to next field/step)
			input.sendKeys(originalValue);
			Thread.sleep(500);

			// Optional: Verify Green star appears after restoring?
			sa.assertTrue(dept.isGreenStarDisplayedForField(fieldName).isDisplayed(), "Green Star Restored", fieldName);
		}

		// PHASE 3: FILL ALL MANDATORY & SUBMIT -> VERIFY AUTHENTICATION POPUP
		log.info("Phase 3: Submitting complete form for Authentication check...");
		dept.clickUpdate();
		dept.waitForLoading();
		boolean authDisplayed = dept.isAuthPopupDisplayed();
		sa.assertTrue(authDisplayed, "Authentication Popup Displayed", "Submit Flow");
		log.info("Authentication Popup visibility: " + authDisplayed);
		ScreenshotUtil.capture();

		dept.closeAuthenticatePopup();
		dept.waitForAuthPopupInvisibility();
		sa.assertFalse(dept.isAuthPopupDisplayed(), "Authentication Popup is not Displayed", "Submit Flow");
		log.info("Authentication Popup closed successfully ");

		// ================= CHECKING UPDATE BUTTON FUNCTIONALITY AFTER CLOSING AUTH
		// POPUP =================
		log.info("Rechecking Update button functionality after closing auth popup... ");
		WebElement btn = dept.getUpdateButton();
		sa.assertTrue(btn.isEnabled(), "Button enabled", "Update");
		dept.clickUpdate();

		// ====================== CHECKING DOUBLE CLICK OF UPDATE FUCNTIONALITY AFTER
		// AUTHENTICATION=================
		log.info("Checking double click of Update button functionality after authentication");
		dept.authenticateWithoutScreenshot(dept.currentPassword);

		btn = dept.getButtonByText("Update");
		dept.waitForElementToDisabled(btn);
		sa.assertTrue(!dept.isButtonEnabled(btn), "Button disabled", "Update");
		log.info("Update button is disabled after authentication");

		dept.clickEdit("OQ Validation Test3");
		dept.waitForLoading();

		// ================= USER ACTIVITY DETAILS VALIDATION (from DB)
		// =================
		log.info("Phase: Validating User Activity Details from Database (Edit Screen)...");

		String deptNameForQuery = "OQ Validation Test3"; // currentDeptName != null ? currentDeptName : DEPT_NAME;
		List<Map<String, String>> dbDetails = dept.getUserdetailsFromDB(deptNameForQuery, MASTER_DB_NAME);
		List<Map<String, String>> uiDetails = dept.getUserActivityDetailsUI();

		log.info("DB rows found: {}", dbDetails.size());
		sa.assertTrue(uiDetails.size() > 0, "User Activity Visibility",
				"At least one activity row should be visible on Edit screen");

		// Map UI labels to expected patterns
		int uiIndex = 0;
		if (uiDetails.isEmpty()) {
			log.error("❌ No User Activity labels found on Edit UI section. Skipping details validation.");
		} else {
			SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat uiFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

			for (int i = 0; i < dbDetails.size(); i++) {
				if (uiIndex >= uiDetails.size()) {
					log.error("❌ UI has fewer activity fields than Database rows. Found: {}, Expected more for row: {}",
							uiDetails.size(), i);
					break;
				}
				Map<String, String> dbRow = dbDetails.get(i);
				String status = dbRow.get("status_name");
				String dbUser = dbRow.get("created_by");
				String dbDateStr = dbRow.get("created_date");
				String dbComments = dbRow.get("comments");

				// Format Date with IST (+5:30) offset
				String expectedDate = "";
				try {
					if (dbDateStr != null && !dbDateStr.isEmpty()) {
						if (dbDateStr.contains(".")) {
							dbDateStr = dbDateStr.substring(0, dbDateStr.indexOf("."));
						}
						Date date = dbFormat.parse(dbDateStr);
						long localTimeMs = date.getTime() + (330L * 60 * 1000);
						expectedDate = uiFormat.format(new Date(localTimeMs));
					}
				} catch (Exception e) {
					log.warn("Failed to parse date: {}", dbDateStr);
					expectedDate = dbDateStr;
				}

				if (i == 0) {
					// Row 1: Always Performed By
					sa.assertEquals(uiDetails.get(uiIndex).get("label"), "Performed By", "Performed By Heading",
							"Edit Activity Label index " + uiIndex);
					sa.assertEquals(uiDetails.get(uiIndex).get("value"), dbUser, "Performed By username",
							"Performed By Value");
					uiIndex++;
					sa.assertEquals(uiDetails.get(uiIndex).get("label"), "Performed Date", "Performed Date Heading",
							"Edit Activity Label index " + uiIndex);
					sa.assertEquals(uiDetails.get(uiIndex).get("value"), expectedDate, "Performed on date",
							"Performed Date Value");
					uiIndex++;
				} else if ("DRAFT".equalsIgnoreCase(status)) {
					sa.assertEquals(uiDetails.get(uiIndex).get("label"), "Modified By", "Modified By Heading",
							"Edit Activity Label index " + uiIndex);
					sa.assertEquals(uiDetails.get(uiIndex).get("value"), dbUser, "Modified By username",
							"Modified By Value");
					uiIndex++;
					sa.assertEquals(uiDetails.get(uiIndex).get("label"), "Modified Date", "Modified Date Heading",
							"Edit Activity Label index " + uiIndex);
					sa.assertEquals(uiDetails.get(uiIndex).get("value"), expectedDate, "Modified on date",
							"Modified Date Value");
					uiIndex++;
				} else if ("RETURN".equalsIgnoreCase(status)) {
					sa.assertEquals(uiDetails.get(uiIndex).get("label"), "Returned By Approver", "Returned By Heading",
							"Edit Activity Label index " + uiIndex);
					sa.assertEquals(uiDetails.get(uiIndex).get("value"), dbUser, "Returned By username",
							"Returned By Value");
					uiIndex++;
					sa.assertEquals(uiDetails.get(uiIndex).get("label"), "Returned Date", "Returned Date Heading",
							"Edit Activity Label index " + uiIndex);
					sa.assertEquals(uiDetails.get(uiIndex).get("value"), expectedDate, "Returned on date",
							"Returned Date Value");
					uiIndex++;
					sa.assertEquals(uiDetails.get(uiIndex).get("label"), "Remarks", "Remarks Heading",
							"Edit Activity Label index " + uiIndex);
					sa.assertEquals(uiDetails.get(uiIndex).get("value"), dbComments, "Remarks Value",
							"Activity Remarks");
					uiIndex++;
				} else if ("APPROVE".equalsIgnoreCase(status)) {
					sa.assertEquals(uiDetails.get(uiIndex).get("label"), "Approved By", "Approved By Heading",
							"Edit Activity Label index " + uiIndex);
					sa.assertEquals(uiDetails.get(uiIndex).get("value"), dbUser, "Approved By username",
							"Approved By Value");
					uiIndex++;
					sa.assertEquals(uiDetails.get(uiIndex).get("label"), "Approved Date", "Approved Date Heading",
							"Edit Activity Label index " + uiIndex);
					sa.assertEquals(uiDetails.get(uiIndex).get("value"), expectedDate, "Approved on date",
							"Approved Date Value");
					uiIndex++;
					sa.assertEquals(uiDetails.get(uiIndex).get("label"), "Remarks", "Remarks Heading",
							"Edit Activity Label index " + uiIndex);
					sa.assertEquals(uiDetails.get(uiIndex).get("value"), dbComments, "Remarks Value",
							"Activity Remarks");
					uiIndex++;
				}
			}
		}

		// ================= STRICT BUTTON CONTRACT =================
		sa.assertTrue(actualButtons.containsAll(expectedButtons), "Buttons present", "Missing button(s)");
		sa.assertTrue(expectedButtons.containsAll(actualButtons), "No extra buttons", "Unexpected button(s)");

		// ================= BUTTON STATE =================

		List<String> expectedOrder = Arrays.asList("Back", "Update");
		sa.assertEquals(actualButtons, expectedOrder, "Button Order", "Back → Update");

		for (String btnName : expectedButtons) {

			WebElement expbtn = dept.getButtonByText(btnName);
			int screenWidth = driver.manage().window().getSize().getWidth();
			int buttonX = btn.getLocation().getX();

			sa.assertTrue(buttonX > (screenWidth * 0.6), "Button Position Right", btnName + " (x=" + buttonX + ")");
			sa.assertNotNull(expbtn, "Button", btnName);
			sa.assertTrue(expbtn.isDisplayed(), "Button visible", btnName);
			sa.assertTrue(expbtn.isEnabled(), "Button enabled", btnName);

			log.info("✅ Button validated: " + btnName);
		}

		// ====================BACK BUTTON VALIDATION=====================
		log.info("Checking BACK button is working or not");
		dept.clickBack();
		dept.waitForLoading();
		ScreenshotUtil.capture();
		log.info("Clicking Edit button again to go back to Edit screen");
		dept.clickEdit(currentDeptName);

		log.info("🎯 Edit Screen UI Contract validated successfully");
		sa.assertAll();
	}

	// =====================DUPLICATION CHECK IN CREATE PAGE========================

	@Test(groups = { "duplicationcheck" }, dependsOnMethods = "Creation_Screen_Validation", priority = 14)
	public void Duplication_Check_In_Create_Page() throws Throwable {

		log.info("--- Navigating to Create Department Screen for Duplication Check ---");
		ScreenshotUtil.nextStep();
		log.info("--- Checking Duplication Check with Department In Create page: {} ---", DEPT_NAME);
		currentDeptName = DEPT_NAME;
		dept.Create();
		dept.deptName(DEPT_NAME);
		dept.clickSubmit();
		String errorTaost = dept.waitForToast();
		Assert.assertEquals(errorTaost, "Department name already exists",
				"Approval failed with message: " + errorTaost);
		log.info("Checking error message and red star for: " + "Department Name");
		sa.assertTrue(dept.getErrorMessage("Department Name").isDisplayed(), "Error Message Displayed",
				"Department Name");
		sa.assertTrue(dept.isRedStarDisplayedForField("Department Name").isDisplayed(), "Red Star visibility",
				"Department Name");

	}

	@Test(groups = { "duplicationcheck" }, dependsOnMethods = "Creation_Screen_Validation", priority = 14)
	public void Duplication_Check_In_Edit_Page() throws Throwable {

		log.info("--- Navigating to Edit Department Screen for Duplication Check ---");
		ScreenshotUtil.nextStep();
		log.info("--- Checking Duplication Check with Department in Edit page: {} ---", DEPT_NAME);
		currentDeptName = DEPT_NAME;
		dept.clickEdit(currentDeptName);
		log.info(
				"--- Checking Duplication Check with Department in Edit page when already created name is given: {} ---",
				DUPLICATE_DEPARTMENT_NAME);
		dept.waitForLoading();
		dept.deptName(DUPLICATE_DEPARTMENT_NAME);
		dept.clickSubmit();
		String errorTaost = dept.waitForToast();
		Assert.assertEquals(errorTaost, "Department name already exists",
				"Approval failed with message: " + errorTaost);
		log.info("Checking error message and red star for: " + "Department Name");
		sa.assertTrue(dept.getErrorMessage("Department Name").isDisplayed(), "Error Message Displayed",
				"Department Name");
		sa.assertTrue(dept.isRedStarDisplayedForField("Department Name").isDisplayed(), "Red Star visibility",
				"Department Name");
		log.info("--- Checking Duplication Check with Department in Edit page with its name only: {} ---", DEPT_NAME);
		dept.deptName(DEPT_NAME);
		dept.clickSubmit();
		boolean authDisplayed = dept.isAuthPopupDisplayed();
		sa.assertTrue(authDisplayed, "Authentication Popup Displayed", "Submit Flow");
	}

	// ===========================BREADCRUMBS VALIDATION IN LIST
	// PAGE========================

	@Test(groups = { "breadcrumbsvalidationinlistpage" }, dependsOnMethods = "moduleClick", priority = 6)
	public void BreadCrumbs_Validation_In_ListPage() throws Throwable {
		log.info("--- Validating Breadcrumbs in Department List Page ---");
		ScreenshotUtil.nextStep();
		// 1. Validate Current URL
		String currentUrl = driver.getCurrentUrl();
		log.info("Current URL on Department List: {}", currentUrl);
		sa.assertTrue(currentUrl.contains("departments") || currentUrl.contains("department"), "Department Navigation",
				"URL Validation");
		// 2. Validate Breadcrumb Labels (Strict Case Sensitive)
		List<String> actualBreadcrumbs = dept.getBreadcrumbsText();
		log.info("Actual Breadcrumbs on UI: {}", actualBreadcrumbs);
		List<String> expectedBreadcrumbs = Arrays.asList("Home", MASTER_MODULE);
		sa.assertEquals(actualBreadcrumbs, expectedBreadcrumbs, "Breadcrumbs Sequence",
				"Exact Text Match (Case Sensitive)");
		// 3. Validate Separator Arrow
		List<WebElement> arrows = dept.getBreadcrumbArrows();
		boolean arrowVisible = !arrows.isEmpty() && arrows.get(0).isDisplayed();
		sa.assertTrue(arrowVisible, "Breadcrumb Separator", "Arrow Presence between Home and " + MASTER_MODULE);
		// 4. Home icon check
		WebElement homeIcon = dept.getHomeIcon();
		sa.assertTrue(homeIcon.isDisplayed(), "Home Icon", "Presence");
		// 5. Verify Home link can navigate back
		log.info("Clicking on Home breadcrumb to verify navigation back to All Apps...");
		dept.HomeBreadcrumbLink();
		dept.waitForLoading();
		ScreenshotUtil.capture();
		String homeUrl = driver.getCurrentUrl();
		sa.assertTrue(homeUrl.contains("/home"), "Breadcrumb Navigation", "Back to All Apps Page");
		// 6. Navigate back to Department list and verify
		log.info("Navigating back to Department module...");
		dept.click_titleMasters();
		dept.waitForLoading();
		dept.masterClick(MASTER_MODULE);
		dept.waitForLoading();
		ScreenshotUtil.capture();
		log.info("Breadcrumbs, Arrows, and URL validation completed for Department List Page.");
		sa.assertAll();
	}

	// ===========================BREADCRUMBS VALIDATION IN CREATE
	// PAGE========================
	@Test(groups = {
			"breadcrumbsvalidationincreate" }, dependsOnMethods = "BreadCrumbs_Validation_In_ListPage", priority = 7)
	public void BreadCrumbs_Validation_In_CreatePage() throws Throwable {
		log.info("--- Validating Breadcrumbs in Department Create Page ---");
		ScreenshotUtil.nextStep();

		// Navigate to Create page
		dept.Create();
		dept.waitForLoading();
		ScreenshotUtil.capture();

		// 1. Validate Current URL
		String currentUrl = driver.getCurrentUrl();
		log.info("Current URL on Department Create: {}", currentUrl);
		sa.assertTrue(currentUrl.contains("new") || currentUrl.contains("create"), "Create Page Navigation",
				"URL Validation");

		// 2. Validate Breadcrumb Labels (Strict Case Sensitive)
		List<String> actualBreadcrumbs = dept.getBreadcrumbsText();
		log.info("Actual Breadcrumbs on UI: {}", actualBreadcrumbs);

		List<String> expectedBreadcrumbs = Arrays.asList("Home", MASTER_MODULE, "Create " + MASTER_MODULE);
		sa.assertEquals(actualBreadcrumbs, expectedBreadcrumbs, "Breadcrumbs Sequence",
				"Exact Text Match (Case Sensitive)");

		// 3. Validate Separator Arrows (should have 2 arrows now)
		List<WebElement> arrows = dept.getBreadcrumbArrows();
		sa.assertTrue(arrows.size() >= 2, "Breadcrumb Separators", "At least 2 arrows expected");
		sa.assertTrue(arrows.get(0).isDisplayed(), "First Arrow", "Between Home and " + MASTER_MODULE);
		sa.assertTrue(arrows.get(1).isDisplayed(), "Second Arrow", "Between " + MASTER_MODULE + " and Create");

		// 4. Home icon check
		WebElement homeIcon = dept.getHomeIcon();
		sa.assertTrue(homeIcon.isDisplayed(), "Home Icon", "Presence");

		// 5. Verify Department breadcrumb link navigates to list page
		log.info("Clicking on '{}' breadcrumb to verify navigation to list page...", MASTER_MODULE);
		dept.masterClick(MASTER_MODULE);
		dept.waitForLoading();
		ScreenshotUtil.capture();

		String listUrl = driver.getCurrentUrl();
		sa.assertTrue(listUrl.contains("departments") || listUrl.contains("department"), "Breadcrumb Navigation",
				"Back to Department List");
		sa.assertFalse(listUrl.contains("new") || listUrl.contains("create"), "Not on Create Page",
				"Should be on List Page");

		// 6. Navigate back to Create and verify Home link
		log.info("Navigating back to Create page...");
		dept.Create();
		dept.waitForLoading();

		log.info("Clicking on Home breadcrumb to verify navigation back to All Apps...");
		dept.HomeBreadcrumbLink();
		dept.waitForLoading();
		ScreenshotUtil.capture();

		String homeUrl = driver.getCurrentUrl();
		sa.assertTrue(homeUrl.contains("/home"), "Breadcrumb Navigation", "Back to All Apps Page");

		// 7. Navigate back to Department module for next tests
		log.info("Navigating back to Department module...");
		dept.click_titleMasters();
		dept.waitForLoading();
		dept.masterClick(MASTER_MODULE);
		dept.waitForLoading();
		ScreenshotUtil.capture();

		log.info("Breadcrumbs, Arrows, and URL validation completed for Department Create Page.");
		sa.assertAll();
	}

	// ===========================BREADCRUMBS VALIDATION IN VIEW
	// PAGE========================
	@Test(groups = { "breadcrumbsvalidationinview" }, dependsOnMethods = "Creation_Of_Department", priority = 9)
	public void BreadCrumbs_Validation_In_ViewPage() throws Throwable {
		log.info("--- Validating Breadcrumbs in Department View Page ---");
		ScreenshotUtil.nextStep();
		// Navigate to View page
		if (currentDeptName == null || currentDeptName.isEmpty()) {
			currentDeptName = DEPT_NAME; // Fallback if not set
		}
		dept.clickView(currentDeptName);
		dept.waitForLoading();
		ScreenshotUtil.capture();
		// 1. Validate Current URL
		String currentUrl = driver.getCurrentUrl();
		log.info("Current URL on Department View: {}", currentUrl);
		sa.assertTrue(currentUrl.contains("view") || currentUrl.contains("details"), "View Page Navigation",
				"URL Validation");
		// 2. Validate Breadcrumb Labels (Strict Case Sensitive)
		List<String> actualBreadcrumbs = dept.getBreadcrumbsText();
		log.info("Actual Breadcrumbs on UI: {}", actualBreadcrumbs);
		List<String> expectedBreadcrumbs = Arrays.asList("Home", MASTER_MODULE, "View " + MASTER_MODULE);
		sa.assertEquals(actualBreadcrumbs, expectedBreadcrumbs, "Breadcrumbs Sequence",
				"Exact Text Match (Case Sensitive)");
		// 3. Validate Separator Arrows (should have 2 arrows now)
		List<WebElement> arrows = dept.getBreadcrumbArrows();
		sa.assertTrue(arrows.size() >= 2, "Breadcrumb Separators", "At least 2 arrows expected");
		sa.assertTrue(arrows.get(0).isDisplayed(), "First Arrow", "Between Home and " + MASTER_MODULE);
		sa.assertTrue(arrows.get(1).isDisplayed(), "Second Arrow", "Between " + MASTER_MODULE + " and View");
		// 4. Home icon check
		WebElement homeIcon = dept.getHomeIcon();
		sa.assertTrue(homeIcon.isDisplayed(), "Home Icon", "Presence");
		// 5. Verify Department breadcrumb link navigates to list page
		log.info("Clicking on '{}' breadcrumb to verify navigation to list page...", MASTER_MODULE);
		dept.masterClick(MASTER_MODULE);
		dept.waitForLoading();
		ScreenshotUtil.capture();
		String listUrl = driver.getCurrentUrl();
		sa.assertTrue(listUrl.contains("departments") || listUrl.contains("department"), "Breadcrumb Navigation",
				"Back to Department List");
		sa.assertFalse(listUrl.contains("view") || listUrl.contains("details"), "Not on View Page",
				"Should be on List Page");
		// 6. Navigate back to View and verify Home link
		log.info("Navigating back to View page...");
		dept.clickView(currentDeptName);
		dept.waitForLoading();
		log.info("Clicking on Home breadcrumb to verify navigation back to All Apps...");
		dept.HomeBreadcrumbLink();
		dept.waitForLoading();
		ScreenshotUtil.capture();
		String homeUrl = driver.getCurrentUrl();
		sa.assertTrue(homeUrl.contains("/home"), "Breadcrumb Navigation", "Back to All Apps Page");
		// 7. Navigate back to Department module for next tests
		log.info("Navigating back to Department module...");
		dept.click_titleMasters();
		dept.waitForLoading();
		dept.masterClick(MASTER_MODULE);
		dept.waitForLoading();
		ScreenshotUtil.capture();
		log.info("Breadcrumbs, Arrows, and URL validation completed for Department View Page.");
		sa.assertAll();
	}

	// ===========================BREADCRUMBS VALIDATION IN EDIT
	// PAGE========================

	@Test(groups = { "breadcrumbsvalidationinedit" }, dependsOnMethods = "Edit_Screen_Validation", priority = 11)
	public void BreadCrumbs_Validation_In_EditPage() throws Throwable {
		log.info("--- Validating Breadcrumbs in Department Edit Page ---");
		ScreenshotUtil.nextStep();

		// Navigate to Edit page
		if (currentDeptName == null || currentDeptName.isEmpty()) {
			currentDeptName = DEPT_NAME;
		}
		dept.clickEdit(currentDeptName);
		dept.waitForLoading();
		ScreenshotUtil.capture();

		// 1. Validate Current URL
		String currentUrl = driver.getCurrentUrl();
		log.info("Current URL on Department Edit: {}", currentUrl);
		sa.assertTrue(currentUrl.contains("edit") || currentUrl.contains("update"), "Edit Page Navigation",
				"URL Validation");

		// 2. Validate Breadcrumb Labels
		List<String> actualBreadcrumbs = dept.getBreadcrumbsText();
		log.info("Actual Breadcrumbs on UI: {}", actualBreadcrumbs);

		List<String> expectedBreadcrumbs = Arrays.asList("Home", MASTER_MODULE, "Edit " + MASTER_MODULE);
		sa.assertEquals(actualBreadcrumbs, expectedBreadcrumbs, "Breadcrumbs Sequence",
				"Exact Text Match (Case Sensitive)");

		// 3. Validate Separator Arrows
		List<WebElement> arrows = dept.getBreadcrumbArrows();
		sa.assertTrue(arrows.size() >= 2, "Breadcrumb Separators", "At least 2 arrows expected");
		sa.assertTrue(arrows.get(0).isDisplayed(), "First Arrow", "Between Home and " + MASTER_MODULE);
		sa.assertTrue(arrows.get(1).isDisplayed(), "Second Arrow", "Between " + MASTER_MODULE + " and Edit");

		// 4. Home icon check
		WebElement homeIcon = dept.getHomeIcon();
		sa.assertTrue(homeIcon.isDisplayed(), "Home Icon", "Presence");

		// 5. Verify Department breadcrumb link navigates to list page
		log.info("Clicking on '{}' breadcrumb to verify navigation to list page...", MASTER_MODULE);
		dept.masterClick(MASTER_MODULE);
		dept.waitForLoading();
		ScreenshotUtil.capture();

		String listUrl = driver.getCurrentUrl();
		sa.assertTrue(listUrl.contains("departments") || listUrl.contains("department"), "Breadcrumb Navigation",
				"Back to Department List");
		sa.assertFalse(listUrl.contains("edit") || listUrl.contains("update"), "Not on Edit Page",
				"Should be on List Page");

		// 6. Navigate back to Edit and verify Home link
		log.info("Navigating back to Edit page...");
		dept.clickEdit(currentDeptName);
		dept.waitForLoading();

		log.info("Clicking on Home breadcrumb to verify navigation back to All Apps...");
		dept.HomeBreadcrumbLink();
		dept.waitForLoading();
		ScreenshotUtil.capture();

		String homeUrl = driver.getCurrentUrl();
		sa.assertTrue(homeUrl.contains("/home"), "Breadcrumb Navigation", "Back to All Apps Page");

		// 7. Navigate back to Department module for next tests
		log.info("Navigating back to Department module...");
		dept.click_titleMasters();
		dept.waitForLoading();
		dept.masterClick(MASTER_MODULE);
		dept.waitForLoading();
		ScreenshotUtil.capture();

		log.info("Breadcrumbs, Arrows, and URL validation completed for Department Edit Page.");
		sa.assertAll();
	}

	// ===========================ACTIONS ICONS & MENU VALIDATION BASED ON
	// STATUS========================

	public void Icon_Validation_By_Status(String itemName, String status) throws Throwable {
		log.info("--- Validating Action Icons for [{}] with Status: [{}] ---", itemName, status);

		DatabaseBackupUtil.setActiveDb(PC_DB_NAME);
		String username = dept.currentUsername;

		// Privilege Check: Admin or Specific Privilege
		boolean isAdmin = DatabaseBackupUtil.hasAuthority(username, "ROLE_ADMIN")
				|| DatabaseBackupUtil.hasAuthority(username, "DEPARTMENT_ADMIN");

		boolean hasViewPriv = isAdmin || DatabaseBackupUtil.hasAuthority(username, "DEPARTMENT_VIEW");
		boolean hasEditPriv = isAdmin || DatabaseBackupUtil.hasAuthority(username, "DEPARTMENT_EDIT");
		boolean hasApprovePriv = isAdmin || DatabaseBackupUtil.hasAuthority(username, "DEPARTMENT_APPROVE");

		// 1. View Icon Validation
		// Status: Draft, Returned By Approver, Active
		List<String> viewStatuses = Arrays.asList("Draft", "Returned By Approver", "Active");
		boolean expectView = hasViewPriv && viewStatuses.stream().anyMatch(s -> s.equalsIgnoreCase(status));

		log.info("View Icon - Privilege: {}, Target Statuses: {}, Current Status: {} -> Expected Visibility: {}",
				hasViewPriv, viewStatuses, status, expectView);

		try {
			dept.validateViewIconWithStatus(itemName, expectView);
			sa.assertTrue(true, "View Icon Visibility (Status: " + status + ")", itemName);
		} catch (Exception e) {
			sa.assertTrue(false, "View Icon Visibility (Status: " + status + ") - Expected: " + expectView + ", Error: "
					+ e.getMessage(), itemName);
		}

		// 2. Edit Icon Validation
		// Status: Draft, Returned By Approver
		List<String> editStatuses = Arrays.asList("Draft", "Returned By Approver");
		boolean expectEdit = hasEditPriv && editStatuses.stream().anyMatch(s -> s.equalsIgnoreCase(status));

		log.info("Edit Icon - Privilege: {}, Target Statuses: {}, Current Status: {} -> Expected Visibility: {}",
				hasEditPriv, editStatuses, status, expectEdit);

		try {
			dept.validateEditIconWithStatus(itemName, expectEdit);
			sa.assertTrue(true, "Edit Icon Visibility (Status: " + status + ")", itemName);
		} catch (Exception e) {
			sa.assertTrue(false, "Edit Icon Visibility (Status: " + status + ") - Expected: " + expectEdit + ", Error: "
					+ e.getMessage(), itemName);
		}

		// 3. Action Menu (Approve) Validation
		// Status: Draft
		List<String> expectedActions = new ArrayList<>();
		if (hasApprovePriv && status.equalsIgnoreCase("Draft")) {
			expectedActions.add("Approve");
		}

		log.info("Approve Action - Privilege: {}, Target Status: Draft, Current Status: {} -> Expected Actions: {}",
				hasApprovePriv, status, expectedActions);

		try {
			dept.validateActionsStrict(itemName, expectedActions);
			sa.assertTrue(true, "Approve Action Visibility (Status: " + status + ")", itemName);
		} catch (Exception e) {
			sa.assertTrue(false, "Approve Action Visibility (Status: " + status + ") - Expected: " + expectedActions
					+ ", Error: " + e.getMessage(), itemName);
		}
	}

	// ===========================VIEW/EDIT ICONS VALIDATION IN LIST PAGE BASED ON
	// STATUS========================
	@Test(groups = { "actionsvalidation" }, priority = 6)
	public void ViewEdit_Icons_Validation() throws Throwable {
		if (currentDeptName == null || currentDeptName.isEmpty())
			currentDeptName = DEPT_NAME;

		String currentStatus = dept.getStatus(currentDeptName);
		log.info("Performing View/Edit Icon validation for {} with status: {}", currentDeptName, currentStatus);

		Icon_Validation_By_Status(currentDeptName, currentStatus);
		sa.assertAll();
	}

	// ===========================ACTIONS MENU VALIDATION IN LIST PAGE BASED ON
	// STATUS========================

	@Test(groups = { "actionsvalidation" }, priority = 7)
	public void Action_Menu_Validation() throws Throwable {
		if (currentDeptName == null || currentDeptName.isEmpty())
			currentDeptName = DEPT_NAME;

		String currentStatus = dept.getStatus(currentDeptName);
		log.info("Performing Action Menu validation for {} with status: {}", currentDeptName, currentStatus);

		Icon_Validation_By_Status(currentDeptName, currentStatus);
		sa.assertAll();
	}

	// ===========================CLICKING VIEW ICON TO VERIFY VIEW SCREEN

	// ===========================VIEW SCREEN VALIDATION========================

	@Test(groups = { "viewvalidation" }, priority = 8)
	public void View_Screen_Validation() throws Throwable {

		log.info("--- Validating Department View Screen ---");
		ScreenshotUtil.nextStep();

		// Open View
		dept.clickView(DEPT_NAME);
		dept.waitForLoading();
		ScreenshotUtil.capture();

		// ================= DATA FROM CSV (Reusing Create Screen Def) =================
		String csvPath = System.getProperty("user.dir") + "/src/test/resources/CSV_Data/CreateScreenValidation.csv";
		List<Map<String, String>> screenData = CSVUtility.getRowsByModule(csvPath, "Department");

		List<String> expectedFieldNames = screenData.stream().map(row -> row.get("FieldName"))
				.collect(Collectors.toList());
		List<String> expPlaceholders = screenData.stream().map(row -> row.get("Placeholder"))
				.collect(Collectors.toList());
		List<String> mandatoryFields = screenData.stream().filter(row -> row.get("isMandatory").equalsIgnoreCase("Yes"))
				.map(row -> row.get("FieldName")).collect(Collectors.toList());

		// ================= EXPECTED BUTTONS =================
		List<String> expectedButtons = Arrays.asList("Back");

		// ================= ACTUAL UI DATA =================
		List<String> actualFields = dept.getDisplayedFieldLabels();
		List<String> actualButtons = dept.getDisplayedButtons();

		actualFields.removeIf(f -> f == null || f.trim().isEmpty());
		actualButtons.removeIf(b -> b == null || b.trim().isEmpty());

		log.info("Actual Fields on UI: " + actualFields);
		log.info("Actual Buttons on UI: " + actualButtons);

		// ================= STRICT FIELD & BUTTON CONTRACT =================
		// Filter out User Activity labels before strict contract check
		List<String> auditLabels = java.util.Arrays.asList("Performed By", "Performed Date", "Modified By",
				"Modified Date", "Returned By Approver", "Returned Date", "Approved By", "Approved Date", "Remarks");
		List<String> filteredFields = actualFields.stream().filter(f -> !auditLabels.contains(f))
				.collect(Collectors.toList());
		sa.assertEquals(filteredFields, expectedFieldNames, "Fields Contract",
				"Strict match expected for primary fields");
		sa.assertEquals(actualButtons, expectedButtons, "Buttons Contract",
				"Strict match expected for buttons (Only Back)");

		// ================= INDIVIDUAL FIELD VALIDATION =================
		for (int i = 0; i < expectedFieldNames.size(); i++) {
			String fieldName = expectedFieldNames.get(i).trim();
			String csvPlaceholder = expPlaceholders.get(i).trim();
			boolean isMandatory = mandatoryFields.contains(fieldName);

			log.info("Validating View Field: [" + fieldName + "] (Mandatory: " + isMandatory + ")");

			// 1. Label & Input Existence
			sa.assertTrue(dept.isLabelDisplayed(fieldName), "Label visibility", fieldName);
			WebElement input = dept.getInputFieldForLabel(fieldName);
			sa.assertNotNull(input, "Input in DOM", fieldName);
			sa.assertTrue(input.isDisplayed(), "Input visible", fieldName);

			// 2. DISABLED State (Must be read-only)
			sa.assertFalse(input.isEnabled(), "Field Disabled", fieldName + " should be read-only in View mode");

			// 3. NO Red Stars (Mandatory indicators hidden)
			boolean hasRedStar = false;
			try {
				hasRedStar = dept.isRedStarDisplayedForField(fieldName).isDisplayed();
			} catch (Exception e) {
				// Expected: element not found
			}
			sa.assertFalse(hasRedStar, "No Red Star", fieldName + " should NOT show mandatory star in View mode");

			// 4. DATA Validation (Value vs Placeholder)
			String actualValue = input.getDomProperty("value");
			String actualPlaceholder = input.getDomAttribute("placeholder");

			String expectedValue = "";
			if (fieldName.equalsIgnoreCase("Department Name")) {
				expectedValue = DEPT_NAME;
			} else if (fieldName.equalsIgnoreCase("Description")) {
				expectedValue = DEPT_DESC;
			}

			if (isMandatory) {
				// CASE A: MANDATORY FIELDS (Must have value)
				sa.assertFalse(actualValue == null || actualValue.isEmpty(), "Mandatory Value Missing",
						fieldName + " is mandatory and must have a value");
				sa.assertEquals(actualValue, expectedValue, "Mandatory Field Value", fieldName);
			} else {
				// CASE B: NON-MANDATORY (Can be empty)
				if (expectedValue != null && !expectedValue.isEmpty()) {
					// Sub-case B1: Data was provided
					sa.assertEquals(actualValue, expectedValue, "Optional Field Value", fieldName);
				} else {
					// Sub-case B2: Data was NOT provided (Empty)
					sa.assertEquals(actualValue, "", "Optional Field Empty", fieldName);
					// If empty, verify placeholder is shown (as requested)
					if (actualPlaceholder != null) {
						sa.assertEquals(actualPlaceholder, csvPlaceholder, "Placeholder (Empty Optional Field)",
								fieldName);
					}
				}
			}
		}

		// ================= USER ACTIVITY DETAILS VALIDATION (from DB)
		// =================
		log.info("Phase: Validating User Activity Details from Database...");

		String deptNameForQuery = currentDeptName != null ? currentDeptName : DEPT_NAME;
		List<Map<String, String>> dbDetails = dept.getUserdetailsFromDB(deptNameForQuery, MASTER_DB_NAME);

		List<Map<String, String>> uiDetails = dept.getUserActivityDetailsUI();
		System.out.println("DB Size: " + dbDetails.size());
		System.out.println("DB Details: " + dbDetails);
		System.out.println("UI Size: " + uiDetails.size());
		System.out.println("UI Details: " + uiDetails);

		log.info("DB rows found: {}", dbDetails.size());
		sa.assertTrue(dbDetails.size() > 0, "Database Audit Trail",
				"No audit records found in DB for: " + deptNameForQuery);
		sa.assertTrue(uiDetails.size() > 0, "User Activity Visibility", "At least one activity row should be visible");

		// Map UI labels to expected patterns
		int uiIndex = 0;
		if (uiDetails.isEmpty()) {
			log.error("❌ No User Activity labels found on UI after the heading. Skipping details validation.");
		} else {
			SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat uiFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

			for (int i = 0; i < dbDetails.size(); i++) {
				if (uiIndex >= uiDetails.size()) {
					log.error("❌ UI has fewer activity fields than Database rows. Found: {}, Expected more for row: {}",
							uiDetails.size(), i);
					break;
				}
				Map<String, String> dbRow = dbDetails.get(i);
				String status = dbRow.get("status_name");
				String dbUser = dbRow.get("created_by");
				String dbDateStr = dbRow.get("created_date"); // e.g., 2026-02-11 07:33:55.167454
				String dbComments = dbRow.get("comments");

				// Format Date with IST (+5:30) offset
				String expectedDate = "";
				try {
					if (dbDateStr != null && !dbDateStr.isEmpty()) {
						if (dbDateStr.contains(".")) {
							dbDateStr = dbDateStr.substring(0, dbDateStr.indexOf("."));
						}
						Date date = dbFormat.parse(dbDateStr);
						// Add 5:30 offset (5*60 + 30 = 330 minutes)
						long localTimeMs = date.getTime() + (330L * 60 * 1000);
						expectedDate = uiFormat.format(new Date(localTimeMs));
					}
				} catch (Exception e) {
					log.warn("Failed to parse date: {}", dbDateStr);
					expectedDate = dbDateStr;
				}

				if (i == 0) {
					// Row 1: Always Performed By
					sa.assertEquals(uiDetails.get(uiIndex).get("label"), "Performed By", "Performed By Heading",
							"Activity Label at index " + uiIndex);
					sa.assertEquals(uiDetails.get(uiIndex).get("value"), dbUser, "Performed By username",
							"Performed By Value");
					uiIndex++;
					sa.assertEquals(uiDetails.get(uiIndex).get("label"), "Performed Date", "Performed Date Heading",

							"Activity Label at index " + uiIndex);
					sa.assertEquals(uiDetails.get(uiIndex).get("value"), expectedDate, "Performed on date",
							"Performed Date Value");
					uiIndex++;
				} else if ("DRAFT".equalsIgnoreCase(status)) {
					// Subsequent Drafts: Modified By
					sa.assertEquals(uiDetails.get(uiIndex).get("label"), "Modified By", "Performed By Heading",
							"Activity Label at index " + uiIndex);
					sa.assertEquals(uiDetails.get(uiIndex).get("value"), dbUser, "Modified By username",
							"Modified By Value");
					uiIndex++;
					sa.assertEquals(uiDetails.get(uiIndex).get("label"), "Modified Date", "Modified Date Heading",
							"Activity Label at index " + uiIndex);
					sa.assertEquals(uiDetails.get(uiIndex).get("value"), expectedDate, "Modified on date",
							"Modified Date Value");
					uiIndex++;
				} else if ("RETURN".equalsIgnoreCase(status)) {
					// Returned By Approver
					sa.assertEquals(uiDetails.get(uiIndex).get("label"), "Returned By Approver", "Performed By Heading",
							"Activity Label at index " + uiIndex);
					sa.assertEquals(uiDetails.get(uiIndex).get("value"), dbUser, "Returned By Approver By username",
							"Returned By Value");
					uiIndex++;
					sa.assertEquals(uiDetails.get(uiIndex).get("label"), "Returned Date",
							"Returned By Approver Date Heading", "Activity Label at index " + uiIndex);
					sa.assertEquals(uiDetails.get(uiIndex).get("value"), expectedDate, "Returned By Approver on date",
							"Returned Date Value");
					uiIndex++;
					sa.assertEquals(uiDetails.get(uiIndex).get("label"), "Remarks",
							"Returned By Approver Remarks Heading", "Activity Label at index " + uiIndex);
					sa.assertEquals(uiDetails.get(uiIndex).get("value"), dbComments,
							"Returned By Approver given remarks", "Remarks (Return) Value");
					uiIndex++;
				} else if ("APPROVED".equalsIgnoreCase(status) || "APPROVE".equalsIgnoreCase(status)) {
					// Approved By
					sa.assertEquals(uiDetails.get(uiIndex).get("label"), "Approved By", "Performed By Heading",
							"Activity Label at index " + uiIndex);
					sa.assertEquals(uiDetails.get(uiIndex).get("value"), dbUser, "Approved By username",
							"Approved By Value");
					uiIndex++;
					sa.assertEquals(uiDetails.get(uiIndex).get("label"), "Approved Date", "Approved Date Heading",
							"Activity Label at index " + uiIndex);
					sa.assertEquals(uiDetails.get(uiIndex).get("value"), expectedDate, "Approved on date",
							"Approved Date Value");
					uiIndex++;
					sa.assertEquals(uiDetails.get(uiIndex).get("label"), "Remarks", "Approver Remarks Heading",
							"Activity Label at index " + uiIndex);
					sa.assertEquals(uiDetails.get(uiIndex).get("value"), dbComments, "Approver given remarks",
							"Remarks (Approve) Value");
					uiIndex++;
				}
			}
		}

		// ================= BACK BUTTON VALIDATION =================
		WebElement backBtn = dept.getButtonByText("Back");
		sa.assertNotNull(backBtn, "Back Button", "Existence");
		sa.assertTrue(backBtn.isDisplayed(), "Back Button", "Visibility");
		sa.assertTrue(backBtn.isEnabled(), "Back Button", "Enabled");

		// Click Back to return
		backBtn.click();
		dept.waitForLoading();
		ScreenshotUtil.capture();

		sa.assertAll();
	}
	// ===========================APPROVE SCREEN VALIDATION========================

	@Test(groups = { "approvevalidation" }, dependsOnMethods = "Action_Menu_Validation", priority = 9)
	public void Approve_Screen_Validation() throws Throwable {
		log.info("--- Validating Department Approve Screen ---");
		ScreenshotUtil.nextStep();

		if (currentDeptName == null || currentDeptName.isEmpty()) {
			currentDeptName = DEPT_NAME;
		}

		// 1. Open Approve screen from Actions menu
		log.info("Opening Approve screen for: {}", currentDeptName);
		dept.clickActions(currentDeptName);
		dept.clickApprove();
		dept.waitForLoading();
		ScreenshotUtil.capture();

		// 2. Data-Driven Validation for existing fields (must be read-only, matching
		// View)
		String csvPath = System.getProperty("user.dir") + "/src/test/resources/CSV_Data/CreateScreenValidation.csv";
		List<Map<String, String>> screenData = CSVUtility.getRowsByModule(csvPath, "Department");

		for (Map<String, String> row : screenData) {
			String fieldName = row.get("FieldName").trim();
			log.info("Validating read-only field: [{}] in Approve screen", fieldName);

			sa.assertTrue(dept.isLabelDisplayed(fieldName), "Label visibility", fieldName);
			WebElement input = dept.getInputFieldForLabel(fieldName);
			sa.assertNotNull(input, "Input existence", fieldName);
			sa.assertFalse(input.isEnabled(), "Field Disabled", fieldName + " should be read-only");

			// Verify data retention
			String actualValue = input.getDomProperty("value");
			String expectedValue = fieldName.equalsIgnoreCase("Department Name") ? DEPT_NAME : DEPT_DESC;
			sa.assertEquals(actualValue, expectedValue, "Pre-filled data match", fieldName);
		}

		// 3. Remarks Field Validation (Approve Screen Specific)
		log.info("Validating additional Remarks field...");
		String remarksLabel = "Remarks";
		sa.assertTrue(dept.isLabelDisplayed(remarksLabel), "Remarks", "Remarks label visibility");
		WebElement remarksInput = dept.getInputFieldForLabel(remarksLabel);
		sa.assertNotNull(remarksInput, "Remarks input Field In DOM", "Remarks field existence");
		sa.assertTrue(remarksInput.isDisplayed(), "Remarks Input Field", "Remarks field visibility");
		sa.assertTrue(remarksInput.isEnabled(), "Remarks Field editable",
				"Remarks field must be EDITABLE in Approve mode");
		sa.assertEquals(remarksInput.getDomAttribute("placeholder"), "Remarks", "Placeholder",
				"Remarks placeholder mismatch");

		// 4. Mandatory Remarks Check (Negative Test)
		log.info("Starting Negative Validation for Remarks...");
		remarksInput.clear();
		// Some frameworks need a forced clear
		if (!remarksInput.getDomProperty("value").isEmpty()) {
			remarksInput.sendKeys(org.openqa.selenium.Keys.CONTROL + "a");
			remarksInput.sendKeys(org.openqa.selenium.Keys.DELETE);
		}

		// Check initial Red Star
		try {
			sa.assertTrue(dept.isRedStarDisplayedForField(remarksLabel).isDisplayed(), "Red Star",
					"Initial Red Star for Remarks");
		} catch (Exception e) {
			sa.assertFail("Red star missing for empty mandatory Remarks field");
		}

		// Click Approve to trigger validation error
		dept.getButtonByText("Approve").click();
		Thread.sleep(800);
		sa.assertTrue(dept.getErrorMessage(remarksLabel).isDisplayed(), "Error Message",
				"Error message visibility for empty Remarks");

		// 5. Positive Remarks Validation (Indicator color change)
		log.info("Entering remarks to verify indicator changes to Green...");
		remarksInput.sendKeys("Validation: Mandatory check passed");
		Thread.sleep(800);
		try {
			sa.assertTrue(dept.isGreenStarDisplayedForField(remarksLabel).isDisplayed(), "Green Star",
					"Green Star visibility after input");
		} catch (Exception e) {
			sa.assertFail("Indicator did not turn Green after entering data in Remarks");
		}
		// Error message should be gone
		sa.assertFalse(
				driver.findElements(By
						.xpath("//label[contains(text(),'Remarks')]/ancestor::div[@class='card']//validation-message"))
						.stream().anyMatch(WebElement::isDisplayed),
				"Error Message", "Error message should disappear after valid input");

		// ================= USER ACTIVITY DETAILS VALIDATION (from DB)
		// =================
		log.info("Phase: Validating User Activity Details from Database (Edit Screen)...");

		String deptNameForQuery = currentDeptName != null ? currentDeptName : DEPT_NAME;
		List<Map<String, String>> dbDetails = dept.getUserdetailsFromDB(deptNameForQuery, MASTER_DB_NAME);
		List<Map<String, String>> uiDetails = dept.getUserActivityDetailsUI();

		log.info("DB rows found: {}", dbDetails.size());
		sa.assertTrue(uiDetails.size() > 0, "User Activity Visibility",
				"At least one activity row should be visible on Edit screen");

		// Map UI labels to expected patterns
		int uiIndex = 0;
		if (uiDetails.isEmpty()) {
			log.error("❌ No User Activity labels found on Edit UI section. Skipping details validation.");
		} else {
			SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat uiFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

			for (int i = 0; i < dbDetails.size(); i++) {
				if (uiIndex >= uiDetails.size()) {
					log.error("❌ UI has fewer activity fields than Database rows. Found: {}, Expected more for row: {}",
							uiDetails.size(), i);
					break;
				}
				Map<String, String> dbRow = dbDetails.get(i);
				String status = dbRow.get("status_name");
				String dbUser = dbRow.get("created_by");
				String dbDateStr = dbRow.get("created_date");
				String dbComments = dbRow.get("comments");

				// Format Date with IST (+5:30) offset
				String expectedDate = "";
				try {
					if (dbDateStr != null && !dbDateStr.isEmpty()) {
						if (dbDateStr.contains(".")) {
							dbDateStr = dbDateStr.substring(0, dbDateStr.indexOf("."));
						}
						Date date = dbFormat.parse(dbDateStr);
						long localTimeMs = date.getTime() + (330L * 60 * 1000);
						expectedDate = uiFormat.format(new Date(localTimeMs));
					}
				} catch (Exception e) {
					log.warn("Failed to parse date: {}", dbDateStr);
					expectedDate = dbDateStr;
				}

				if (i == 0) {
					// Row 1: Always Performed By
					sa.assertEquals(uiDetails.get(uiIndex).get("label"), "Performed By", "Performed By Heading",
							"Edit Activity Label index " + uiIndex);
					sa.assertEquals(uiDetails.get(uiIndex).get("value"), dbUser, "Performed By username",
							"Performed By Value");
					uiIndex++;
					sa.assertEquals(uiDetails.get(uiIndex).get("label"), "Performed Date", "Performed Date Heading",
							"Edit Activity Label index " + uiIndex);
					sa.assertEquals(uiDetails.get(uiIndex).get("value"), expectedDate, "Performed on date",
							"Performed Date Value");
					uiIndex++;
				} else if ("DRAFT".equalsIgnoreCase(status)) {
					sa.assertEquals(uiDetails.get(uiIndex).get("label"), "Modified By", "Modified By Heading",
							"Edit Activity Label index " + uiIndex);
					sa.assertEquals(uiDetails.get(uiIndex).get("value"), dbUser, "Modified By username",
							"Modified By Value");
					uiIndex++;
					sa.assertEquals(uiDetails.get(uiIndex).get("label"), "Modified Date", "Modified Date Heading",
							"Edit Activity Label index " + uiIndex);
					sa.assertEquals(uiDetails.get(uiIndex).get("value"), expectedDate, "Modified on date",
							"Modified Date Value");
					uiIndex++;
				} else if ("RETURN".equalsIgnoreCase(status)) {
					sa.assertEquals(uiDetails.get(uiIndex).get("label"), "Returned By Approver", "Returned By Heading",
							"Edit Activity Label index " + uiIndex);
					sa.assertEquals(uiDetails.get(uiIndex).get("value"), dbUser, "Returned By username",
							"Returned By Value");
					uiIndex++;
					sa.assertEquals(uiDetails.get(uiIndex).get("label"), "Returned Date", "Returned Date Heading",
							"Edit Activity Label index " + uiIndex);
					sa.assertEquals(uiDetails.get(uiIndex).get("value"), expectedDate, "Returned on date",
							"Returned Date Value");
					uiIndex++;
					sa.assertEquals(uiDetails.get(uiIndex).get("label"), "Remarks", "Remarks Heading",
							"Edit Activity Label index " + uiIndex);
					sa.assertEquals(uiDetails.get(uiIndex).get("value"), dbComments, "Remarks Value",
							"Activity Remarks");
					uiIndex++;
				} else if ("APPROVE".equalsIgnoreCase(status)) {
					sa.assertEquals(uiDetails.get(uiIndex).get("label"), "Approved By", "Approved By Heading",
							"Edit Activity Label index " + uiIndex);
					sa.assertEquals(uiDetails.get(uiIndex).get("value"), dbUser, "Approved By username",
							"Approved By Value");
					uiIndex++;
					sa.assertEquals(uiDetails.get(uiIndex).get("label"), "Approved Date", "Approved Date Heading",
							"Edit Activity Label index " + uiIndex);
					sa.assertEquals(uiDetails.get(uiIndex).get("value"), expectedDate, "Approved on date",
							"Approved Date Value");
					uiIndex++;
					sa.assertEquals(uiDetails.get(uiIndex).get("label"), "Remarks", "Remarks Heading",
							"Edit Activity Label index " + uiIndex);
					sa.assertEquals(uiDetails.get(uiIndex).get("value"), dbComments, "Remarks Value",
							"Activity Remarks");
					uiIndex++;
				}
			}
		}

		log.info("Clicking Cancel to exit Edit screen...");
		WebElement cancelBtn = dept.getButtonByText("Cancel");
		if (cancelBtn == null)
			cancelBtn = dept.getButtonByText("Back"); // Fallback

		cancelBtn.click();
		dept.waitForLoading();
		ScreenshotUtil.capture();

		sa.assertAll();

		// 6. Strict Button Contract
		log.info("Validating button sequence: Back, Return, Approve");
		List<String> expectedButtons = Arrays.asList("Back", "Return", "Approve");
		List<String> actualButtons = dept.getDisplayedButtons();
		actualButtons.removeIf(b -> b == null || b.trim().isEmpty());
		sa.assertEquals(actualButtons, expectedButtons, "Approve buttons", "Approve Screen Buttons Match");

		// 7. Exit screen
		log.info("Clicking Back to return to list page...");
		dept.getButtonByText("Back").click();
		dept.waitForLoading();
		ScreenshotUtil.capture();

		sa.assertAll();
	}

	// ===========================BREADCRUMBS VALIDATION IN APPROVE
	// PAGE========================
	@Test(groups = { "breadcrumbsvalidationinapprove" }, dependsOnMethods = "Approve_Screen_Validation", priority = 9)
	public void BreadCrumbs_Validation_In_ApprovePage() throws Throwable {
		log.info("--- Validating Breadcrumbs in Department Approve Page ---");
		ScreenshotUtil.nextStep();

		// Navigate to Approve page
		if (currentDeptName == null || currentDeptName.isEmpty()) {
			currentDeptName = DEPT_NAME;
		}
		dept.clickActions(currentDeptName);
		dept.clickApprove();
		dept.waitForLoading();
		ScreenshotUtil.capture();

		// 1. Validate Current URL
		String currentUrl = driver.getCurrentUrl();
		log.info("Current URL on Department Approve: {}", currentUrl);
		sa.assertTrue(currentUrl.contains("approve") || currentUrl.contains("detail"), "Approve Page Navigation",
				"URL Validation");

		// 2. Validate Breadcrumb Labels
		List<String> actualBreadcrumbs = dept.getBreadcrumbsText();
		log.info("Actual Breadcrumbs on UI: {}", actualBreadcrumbs);

		List<String> expectedBreadcrumbs = Arrays.asList("Home", MASTER_MODULE, "Approve " + MASTER_MODULE);
		sa.assertEquals(actualBreadcrumbs, expectedBreadcrumbs, "Breadcrumbs Sequence",
				"Exact Text Match (Case Sensitive)");

		// 3. Validate Separator Arrows
		List<WebElement> arrows = dept.getBreadcrumbArrows();
		sa.assertTrue(arrows.size() >= 2, "Breadcrumb Separators", "At least 2 arrows expected");
		sa.assertTrue(arrows.get(0).isDisplayed(), "First Arrow", "Between Home and " + MASTER_MODULE);
		sa.assertTrue(arrows.get(1).isDisplayed(), "Second Arrow", "Between " + MASTER_MODULE + " and Approve");

		// 4. Home icon check
		WebElement homeIcon = dept.getHomeIcon();
		sa.assertTrue(homeIcon.isDisplayed(), "Home Icon", "Presence");

		// 5. Verify Department breadcrumb link navigates to list page
		log.info("Clicking on '{}' breadcrumb to verify navigation to list page...", MASTER_MODULE);
		dept.masterClick(MASTER_MODULE);
		dept.waitForLoading();
		ScreenshotUtil.capture();

		String listUrl = driver.getCurrentUrl();
		sa.assertTrue(listUrl.contains("departments") || listUrl.contains("department"), "Breadcrumb Navigation",
				"Back to Department List");
		sa.assertFalse(listUrl.contains("approve") || listUrl.contains("detail"), "Not on Approve Page",
				"Should be on List Page");

		// 6. Navigate back to Approve and verify Home link
		log.info("Navigating back to Approve page...");
		dept.clickActions(currentDeptName);
		dept.clickApprove();
		dept.waitForLoading();

		log.info("Clicking on Home breadcrumb to verify navigation back to All Apps...");
		dept.HomeBreadcrumbLink();
		dept.waitForLoading();
		ScreenshotUtil.capture();

		String homeUrl = driver.getCurrentUrl();
		sa.assertTrue(homeUrl.contains("/home"), "Breadcrumb Navigation", "Back to All Apps Page");

		// 7. Navigate back to Department module for next tests
		log.info("Navigating back to Department module...");
		dept.click_titleMasters();
		dept.waitForLoading();
		dept.masterClick(MASTER_MODULE);
		dept.waitForLoading();
		ScreenshotUtil.capture();

		log.info("Breadcrumbs, Arrows, and URL validation completed for Department Approve Page.");
		sa.assertAll();
	}

	// ===========================EDIT AFTER CREATE========================

	@Test(groups = { "EditAfterCreate" }, dependsOnMethods = "moduleClick", priority = 8)
	public void Edit_After_Create() throws Throwable {

		if (DEPARTMENT_EDIT_AFTER_CREATE.equalsIgnoreCase("yes")) {
			log.info("--- Editing Department (After Creation): {} ---", currentDeptName);
			ScreenshotUtil.nextStep();
			dept.clickEdit(currentDeptName);
			log.info("Edit screen opened");
			dept.waitForLoading();
			ScreenshotUtil.capture();
			ScreenshotUtil.nextStep();

			if (EDIT_DEPT_NAME_AFTER_CREATE != null && !EDIT_DEPT_NAME_AFTER_CREATE.trim().isEmpty()) {
				log.info("Updating Name to: {}", EDIT_DEPT_NAME_AFTER_CREATE);
				dept.deptName(EDIT_DEPT_NAME_AFTER_CREATE);
				currentDeptName = EDIT_DEPT_NAME_AFTER_CREATE;
			}

			if (EDIT_DEPT_DESC_AFTER_CREATE != null && !EDIT_DEPT_DESC_AFTER_CREATE.trim().isEmpty()) {
				log.info("Updating Description to: {}", EDIT_DEPT_DESC_AFTER_CREATE);
				dept.deptDesc(EDIT_DEPT_DESC_AFTER_CREATE);
			}

			ScreenshotUtil.capture();
			dept.clickUpdate();
			log.info("Clicked Update");
			ScreenshotUtil.capture();
			dept.authenticate(dept.currentPassword);
			String authToast = dept.waitForToast();

			Assert.assertEquals(authToast, "Department Updated Successfully",
					"Update failed with message: " + authToast);
		} else {
			log.info("Edit After Create step skipped based on configuration");
		}
	}

}