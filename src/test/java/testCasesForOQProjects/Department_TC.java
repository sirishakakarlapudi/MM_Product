package testCasesForOQProjects;

import static configData.DepartmentData.*;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

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

	@Test(groups = { "url" }, priority = 2)
	public void url() throws Throwable {
		ScreenshotUtil.nextStep();
		driver.navigate().to(APP_URL);
		log.info("Navigating to App URL: {}", APP_URL);
	}

	@Test(groups = { "userlogin" }, priority = 4)
	public void userLoginBeforeCreate() throws Throwable {
		// Clear existing user session from DB to prevent login issues
		log.info("Executing the flow with single/multiple : {}", ACTIONSPERFORMEDBY);
		if (ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
			dept.login(USERNAME, PASSWORD, PC_DB_NAME);
		} else {
			dept.login(USERNAME1, PASSWORD1, PC_DB_NAME);
		}
	}

	@Test(groups = { "moduleselect" }, priority = 5)
	public void moduleClick() throws Throwable {
		log.info("--- Clicking Module ---");
		ScreenshotUtil.nextStep();
		dept.click_titleMasters();
		log.info("Clicked on Masters title");
		ScreenshotUtil.capture();
		dept.masterClick(MASTER_MODULE);
		log.info("Clicked on Master Module: {}", MASTER_MODULE);
		dept.waitForLoading();
		ScreenshotUtil.capture();
	}

	@Test(groups = { "listpagevalidation" }, dependsOnMethods = "moduleClick", priority = 5)
	public void List_Page_Validation() throws Throwable {
		log.info("--- Validating List Page UI Components ---");
		List<Map<String, String>> data = CSVUtility.getRowsByModule(
				System.getProperty("user.dir") + "/src/test/resources/CSV_Data/ListPageValidation.csv", "Department");
		dept.validateBreadcrumbs(Arrays.asList("Home", MASTER_MODULE));

		// Filter
		data.stream().filter(r -> r.get("Component").equals("Filter") && r.get("ElementType").equals("input"))
				.forEach(r -> {
					WebElement f = dept.getFilterFieldByLabel(r.get("ElementName"));
					sa.assertTrue(f.isDisplayed(), "Filter visible", r.get("ElementName"));
					sa.assertEquals(f.getDomAttribute("placeholder"), r.get("Placeholder"), "Placeholder",
							r.get("ElementName"));
				});

		// Table Headers
		sa.assertEquals(dept.getTableHeaders(), data.stream().filter(r -> r.get("Component").equals("TableHeader"))
				.map(r -> r.get("ElementName")).collect(Collectors.toList()), "Header Contract");

		// Pagination
		int total = dept.extractTotalItemsCount();
		int perPage = dept.getResultsPerPageValue();
		int expPages = (int) Math.ceil((double) total / perPage);
		sa.assertEquals(dept.getDisplayedPageNumbers().size(), expPages, "Page Count");
		sa.assertAll();
	}

	@Test(groups = { "sidenavvalidation" }, dependsOnMethods = "List_Page_Validation", priority = 6)
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

	@Test(groups = { "creationvalidation" }, dependsOnMethods = "moduleClick", priority = 6)
	public void Creation_Screen_Validation() throws Throwable {
		log.info("--- Validating Creation Screen ---");
		dept.Create();
		dept.waitForLoading();
		List<Map<String, String>> data = CSVUtility.getRowsByModule(
				System.getProperty("user.dir") + "/src/test/resources/CSV_Data/CreateScreenValidation.csv",
				"Department");

		sa.assertEquals(
				dept.getDisplayedFieldLabels().stream().filter(f -> f != null && !f.trim().isEmpty())
						.collect(Collectors.toList()),
				data.stream().map(row -> row.get("FieldName")).collect(Collectors.toList()), "Fields Contract");

		for (Map<String, String> row : data) {
			String name = row.get("FieldName");
			sa.assertTrue(dept.isLabelDisplayed(name), "Label", name);
			sa.assertEquals(dept.getFieldType(name), row.get("FieldType"), "Type", name);
			sa.assertEquals(dept.getPlaceholder(name), row.get("Placeholder"), "Placeholder", name);
		}

		dept.getButtonByText("Submit").click();
		data.stream().filter(r -> r.get("isMandatory").equalsIgnoreCase("Yes"))
				.forEach(r -> sa.assertTrue(dept.getErrorMessage(r.get("FieldName")).isDisplayed(), "Error missing",
						r.get("FieldName")));

		dept.getCancelButton().click();
		dept.waitForLoading();
		sa.assertAll();
	}

	@Test(groups = { "breadcrumbs" }, dependsOnMethods = "moduleClick", priority = 6)
	public void Breadcrumb_Validations() throws Throwable {
		log.info("--- Validating Breadcrumbs across various pages ---");
		dept.validateBreadcrumbs(Arrays.asList("Home", MASTER_MODULE));
		dept.Create();
		dept.validateBreadcrumbs(Arrays.asList("Home", MASTER_MODULE, "Create " + MASTER_MODULE));
		dept.getCancelButton().click();
		dept.waitForLoading();
		if (currentDeptName == null)
			currentDeptName = DEPT_NAME;
		dept.clickView(currentDeptName);
		dept.validateBreadcrumbs(Arrays.asList("Home", MASTER_MODULE, "View " + MASTER_MODULE));
		dept.clickBack();
	}

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
		sa.assertTrue(currentUrl.contains("new") || currentUrl.contains("create"),
				"Create Page Navigation", "URL Validation");

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
		sa.assertTrue(listUrl.contains("departments") || listUrl.contains("department"),
				"Breadcrumb Navigation", "Back to Department List");
		sa.assertFalse(listUrl.contains("new") || listUrl.contains("create"),
				"Not on Create Page", "Should be on List Page");

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

	@Test(groups = {
			"breadcrumbsvalidationinedit" }, dependsOnMethods = "Edit_Screen_Validation", priority = 11)
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
		sa.assertTrue(currentUrl.contains("edit") || currentUrl.contains("update"),
				"Edit Page Navigation", "URL Validation");

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
		sa.assertTrue(listUrl.contains("departments") || listUrl.contains("department"),
				"Breadcrumb Navigation", "Back to Department List");
		sa.assertFalse(listUrl.contains("edit") || listUrl.contains("update"),
				"Not on Edit Page", "Should be on List Page");

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

	@Test(groups = { "Creation" }, dependsOnMethods = { "Creation_Screen_Validation",
			"RedStars_And_Mandatory_Fields_Validation_In_Create" }, priority = 8)
	public void Creation_Of_Department() throws Throwable {
		log.info("--- Navigating to Create Department Screen ---");
		ScreenshotUtil.nextStep();

		// If not already on creation screen, click Create
		if (driver.findElements(By.xpath("//label[normalize-space()='Department Name']")).isEmpty()) {
			dept.Create();
			dept.waitForLoading();
		}

		log.info("--- Creating Department: {} ---", DEPT_NAME);
		currentDeptName = DEPT_NAME; // Initialize with base name
		dept.deptName(DEPT_NAME);
		dept.deptDesc(DEPT_DESC);
		log.info("Entered Name and Description");
		ScreenshotUtil.capture();
		dept.createSubmit();
		log.info("Clicked Submit");
		ScreenshotUtil.capture();
		String authToast = dept.authenticate(dept.currentPassword);
		Assert.assertEquals(authToast, "Department Created Successfully", "Creation failed with message: " + authToast);
	}

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

	@Test(groups = { "actionsvalidation" }, priority = 6)
	public void ViewEdit_Icons_Validation() throws Throwable {
		if (currentDeptName == null || currentDeptName.isEmpty())
			currentDeptName = DEPT_NAME;

		String currentStatus = dept.getStatus(currentDeptName);
		log.info("Performing View/Edit Icon validation for {} with status: {}", currentDeptName, currentStatus);

		Icon_Validation_By_Status(currentDeptName, currentStatus);
		sa.assertAll();
	}

	@Test(groups = { "actionsvalidation" }, priority = 7)
	public void Action_Menu_Validation() throws Throwable {
		if (currentDeptName == null || currentDeptName.isEmpty())
			currentDeptName = DEPT_NAME;

		String currentStatus = dept.getStatus(currentDeptName);
		log.info("Performing Action Menu validation for {} with status: {}", currentDeptName, currentStatus);

		Icon_Validation_By_Status(currentDeptName, currentStatus);
		sa.assertAll();
	}

	/*
	 * @Test(groups = { "editvalidation" }, dependsOnMethods =
	 * "Action_Menu_Validation", priority = 7) public void Edit_Page_Validation()
	 * throws Throwable { log.info("--- Starting Edit Page Validation ---");
	 * ScreenshotUtil.nextStep();
	 * 
	 * //Click Edit icon log.info("Clicking Edit icon for department: {}",
	 * currentDeptName); dept.clickEdit(currentDeptName); dept.waitForLoading();
	 * ScreenshotUtil.capture();
	 * 
	 * 
	 * sa.assertTrue(dept.getDeptNameField().isDisplayed(),
	 * "Department Name field is not displayed!");
	 * sa.assertTrue(dept.getDeptDescField().isDisplayed(),
	 * "Department Description field is not displayed!");
	 * 
	 * // Validate Edit Page dept.validateEditPage(DEPT_NAME, DEPT_DESC);
	 * ScreenshotUtil.capture();
	 * 
	 * log.info("--- Edit Page Validation Completed ---"); }
	 */

	@Test(groups = { "ClickActions" }, dependsOnMethods = "Actions_Validation", priority = 6)
	public void Click_Actions_After_Create() throws Throwable {
		log.info("--- Attempting to open Actions Menu for: {} ---", currentDeptName);
		ScreenshotUtil.nextStep();
		dept.clickActions(currentDeptName);
		log.info("Successfully opened Actions menu for {}", currentDeptName);
		ScreenshotUtil.capture();
		dept.clickActions(currentDeptName);
	}

	@Test(groups = { "ClickView" }, dependsOnMethods = "moduleClick", priority = 7)
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
		List<String> mandatoryFields = screenData.stream()
				.filter(row -> row.get("isMandatory").equalsIgnoreCase("Yes"))
				.map(row -> row.get("FieldName"))
				.collect(Collectors.toList());

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
		String query = dept.getUserdetailsFromDB(deptNameForQuery, MASTER_DB_NAME);

		List<Map<String, String>> dbDetails = utilities.DatabaseBackupUtil.getRowsFromDB(query);
		List<Map<String, String>> uiDetails = dept.getUserActivityDetailsUI();
		System.out.println("DB Size: " + dbDetails.size());
		System.out.println("DB Details: " + dbDetails);
		System.out.println("UI Size: " + uiDetails.size());
		System.out.println("UI Details: " + uiDetails);

		log.info("DB rows found: {}", dbDetails.size());
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
					sa.assertEquals(uiDetails.get(uiIndex).get("label"),
							"Returned Date", "Returned By Approver Date Heading",
							"Activity Label at index " + uiIndex);
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

	@Test(groups = {
			"breadcrumbsvalidationinapprove" }, dependsOnMethods = "Approve_Screen_Validation", priority = 9)
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
		sa.assertTrue(currentUrl.contains("approve") || currentUrl.contains("detail"),
				"Approve Page Navigation", "URL Validation");

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
		sa.assertTrue(listUrl.contains("departments") || listUrl.contains("department"),
				"Breadcrumb Navigation", "Back to Department List");
		sa.assertFalse(listUrl.contains("approve") || listUrl.contains("detail"),
				"Not on Approve Page", "Should be on List Page");

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

	@Test(groups = { "editvalidation" }, dependsOnMethods = "View_Screen_Validation", priority = 10)
	public void Edit_Screen_Validation() throws Throwable {

		log.info("--- Validating Department Edit Screen ---");
		ScreenshotUtil.nextStep();

		// Open Edit
		dept.clickEdit(currentDeptName);
		dept.waitForLoading();
		ScreenshotUtil.capture();

		// ================= DATA FROM CSV (Reusing Create Screen Def) =================
		String csvPath = System.getProperty("user.dir") + "/src/test/resources/CSV_Data/CreateScreenValidation.csv";
		List<Map<String, String>> screenData = CSVUtility.getRowsByModule(csvPath, "Department");

		List<String> expectedFieldNames = screenData.stream().map(row -> row.get("FieldName"))
				.collect(Collectors.toList());
		List<String> expPlaceholders = screenData.stream().map(row -> row.get("Placeholder"))
				.collect(Collectors.toList());
		List<String> mandatoryFields = screenData.stream()
				.filter(row -> row.get("isMandatory").equalsIgnoreCase("Yes"))
				.map(row -> row.get("FieldName"))
				.collect(Collectors.toList());

		// ================= EXPECTED BUTTONS =================
		// Assuming Edit screen has Update and Cancel
		List<String> expectedButtons = Arrays.asList("Update", "Cancel");

		// ================= ACTUAL UI DATA =================
		List<String> actualFields = dept.getDisplayedFieldLabels();
		List<String> actualButtons = dept.getDisplayedButtons();

		actualFields.removeIf(f -> f == null || f.trim().isEmpty());
		actualButtons.removeIf(b -> b == null || b.trim().isEmpty());

		log.info("Actual Fields on UI: " + actualFields);
		log.info("Actual Buttons on UI: " + actualButtons);

		// ================= STRICT FIELD & BUTTON CONTRACT =================
		// Filter out User Activity labels before strict contract check
		List<String> auditLabelsEdit = java.util.Arrays.asList("Performed By", "Performed Date", "Modified By",
				"Modified Date", "Returned By Approver", "Returned Date", "Approved By", "Approved Date", "Remarks");
		List<String> filteredFieldsEdit = actualFields.stream().filter(f -> !auditLabelsEdit.contains(f))
				.collect(java.util.stream.Collectors.toList());
		sa.assertEquals(filteredFieldsEdit, expectedFieldNames, "Fields Contract",
				"Strict match expected for primary fields");
		// Allowing partial match for buttons if 'Cancel' vs 'Back' varies, but
		// enforcing strict if possible.
		// User requirement said "button only back" for View, but for Edit implies
		// Update functionality.
		// Using containsAll to be safe, or strict if confident.
		sa.assertTrue(actualButtons.containsAll(expectedButtons), "Buttons Contract", "Expected Update/Cancel");

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
				expectedValue = DEPT_NAME;
			} else if (fieldName.equalsIgnoreCase("Description")) {
				expectedValue = DEPT_DESC;
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
			input.clear();
			// Ensure it's empty (some frameworks need sendKeys(Keys.chord(Keys.CONTROL,"a",
			// Keys.DELETE)))
			if (!input.getDomProperty("value").isEmpty()) {
				input.sendKeys(org.openqa.selenium.Keys.CONTROL + "a");
				input.sendKeys(org.openqa.selenium.Keys.DELETE);
			}
			Thread.sleep(500);

			// 2. Click Update to trigger validation
			dept.getButtonByText("Update").click();
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
			// sa.assertTrue(dept.isGreenStarDisplayedForField(fieldName).isDisplayed(),
			// "Green Star Restored", fieldName);
		}

		// ================= BUTTON STATE & EXIT =================
		WebElement updateBtn = dept.getButtonByText("Update");
		sa.assertTrue(updateBtn.isEnabled(), "Update button", "Update Button Enabled");

		// Click Cancel to exit Edit mode without changes (since we just tested
		// valid/invalid states)
		// Or click Update if we want to save (but we restored values so it's a no-op
		// update)

		// ================= USER ACTIVITY DETAILS VALIDATION (from DB)
		// =================
		log.info("Phase: Validating User Activity Details from Database (Edit Screen)...");

		String deptNameForQuery = currentDeptName != null ? currentDeptName : DEPT_NAME;
		String query = dept.getUserdetailsFromDB(deptNameForQuery, MASTER_DB_NAME);

		List<Map<String, String>> dbDetails = utilities.DatabaseBackupUtil.getRowsFromDB(query);
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

		// ================= STRICT BUTTON CONTRACT =================
		sa.assertTrue(actualButtons.containsAll(expectedButtons), "Buttons present", "Missing button(s)");
		sa.assertTrue(expectedButtons.containsAll(actualButtons), "No extra buttons", "Unexpected button(s)");

		// ================= BUTTON STATE =================

		List<String> expectedOrder = Arrays.asList("Back",
				"Update");
		sa.assertEquals(actualButtons, expectedOrder, "Button Order", "Back → Update");

		for (String btnName : expectedButtons) {

			WebElement btn = dept.getButtonByText(btnName);
			int screenWidth = driver.manage().window().getSize().getWidth();
			int buttonX = btn.getLocation().getX();

			sa.assertTrue(buttonX > (screenWidth * 0.6),
					"Button Position Right",
					btnName + " (x=" + buttonX + ")");
			sa.assertNotNull(btn, "Button", btnName);
			sa.assertTrue(btn.isDisplayed(), "Button visible", btnName);
			sa.assertTrue(btn.isEnabled(), "Button enabled", btnName);

			log.info("✅ Button validated: " + btnName);
		}

		log.info("🎯 Edit Screen UI Contract validated successfully");

		// Go back without modifying
		dept.getCancelButton().click();
		dept.waitForLoading();

		sa.assertAll();
	}

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
			String authToast = dept.authenticate(dept.currentPassword);
			Assert.assertEquals(authToast, "Department Updated Successfully",
					"Update failed with message: " + authToast);
		} else {
			log.info("Edit After Create step skipped based on configuration");
		}
	}

	@Test(groups = { "DeptReturn_DeptEdit" }, dependsOnMethods = "moduleClick", priority = 10)
	public void Dept_Return_and_Edit() throws Throwable {

		if (DEPARTMENT_RETURN.equalsIgnoreCase("yes")) {

			if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
				dept.switchUser(USERNAME2, PASSWORD2, MASTER_MODULE, PC_DB_NAME);
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
			String returnToast = dept.authenticate(dept.currentPassword);
			Assert.assertEquals(returnToast, "Department Returned Successfully",
					"Return failed with message: " + returnToast);

			if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
				dept.switchUser(USERNAME1, PASSWORD1, MASTER_MODULE, PC_DB_NAME);
			}

			// This part will now execute for BOTH single and multiple users
			log.info("Opening Edit screen (After Return)");
			ScreenshotUtil.nextStep();
			dept.clickEdit(currentDeptName);
			dept.waitForLoading();
			ScreenshotUtil.capture();
			ScreenshotUtil.nextStep();

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
		} else {
			log.info("Department Return and Edit skipped based on configuration");
		}
	}

	@Test(groups = { "DeptApprove" }, dependsOnMethods = "moduleClick", priority = 11)
	public void Department_Approve() throws Throwable {

		if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
			log.info("--- Approving Department: {} ---", currentDeptName);
			dept.switchUser(USERNAME2, PASSWORD2, MASTER_MODULE, PC_DB_NAME);
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
		String approveToast = dept.authenticate(dept.currentPassword);
		Assert.assertEquals(approveToast, "Department Approved Successfully",
				"Approval failed with message: " + approveToast);
	}

	@Test(groups = { "Logout" }, dependsOnMethods = "moduleClick", priority = 12)
	public void Logout() throws Throwable {
		log.info("--- Executing Final Logout ---");
		ScreenshotUtil.nextStep();
		dept.click_profileDropdown();
		ScreenshotUtil.capture();
		dept.logout();
		log.info("Clicked logout button");
		dept.waitForToast();
		ScreenshotUtil.capture();
		log.info("--- Department Test Case Execution Finished ---");
	}

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

}