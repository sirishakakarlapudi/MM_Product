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

import org.testng.Assert;
import org.openqa.selenium.WebElement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Department_TC extends BaseClass {
	Department dept;
	String currentDeptName; // Track the current name (including edits)
	String currentUsername; // Track the username of the currently logged-in user
	String currentPassword; // Track the password of the currently logged-in user
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

	@Test(groups = { "urlvalidation" }, priority = 2)
	public void URL_Validation() {
		log.info("--- Validating Application URL ---");

		String currentUrl = driver.getCurrentUrl();
		sa.assertEquals(currentUrl, APP_URL, "APP_URL",
				"URL Mismatch! Expected: " + APP_URL + ", but found: " + currentUrl);

		log.info("Application URL validation completed.");
		sa.assertAll();
	}

	@Test(groups = { "loginvalidation" }, dependsOnMethods = "url", priority = 3)
	public void Login_Field_Validation() {
		log.info("--- Validating Login Fields ---");

		// ================= EXPECTED FIELDS =================
		Map<String, String> expectedFields = new LinkedHashMap<>();
		expectedFields.put("Username", "Username");
		expectedFields.put("Password", "Password"); // enable if needed later

		// ================= EXPECTED BUTTONS =================
		List<String> expectedButtons = Arrays.asList("LOGIN");

		// ================= ACTUAL UI DATA =================
		List<String> actualFields = dept.getDisplayedFieldLabels();
		List<String> actualButtons = dept.getDisplayedButtons();

		actualFields.removeIf(f -> f == null || f.trim().isEmpty());
		actualButtons.removeIf(b -> b == null || b.trim().isEmpty());

		log.info("Actual Fields on UI  : " + actualFields);
		log.info("Actual Buttons on UI : " + actualButtons);

		List<String> expectedFieldNames = new ArrayList<>(expectedFields.keySet());

		// ================= STRICT FIELD CONTRACT =================
		boolean missingFields = actualFields.containsAll(expectedFieldNames);
		boolean extraFields = expectedFieldNames.containsAll(actualFields);

		sa.assertTrue(missingFields, "Fields contract: no fields missing",
				"Missing field(s). Expected: " + expectedFieldNames + " Found: " + actualFields);

		sa.assertTrue(extraFields, "Fields contract: no extra fields",
				"Extra field(s) present. Expected only: " + expectedFieldNames + " Found: " + actualFields);

		// ================= STRICT BUTTON CONTRACT =================
		boolean missingButtons = actualButtons.containsAll(expectedButtons);
		boolean extraButtons = expectedButtons.containsAll(actualButtons);

		sa.assertTrue(missingButtons, "Buttons contract: no buttons missing",
				"Missing button(s). Expected: " + expectedButtons + " Found: " + actualButtons);

		sa.assertTrue(extraButtons, "Buttons contract: no extra buttons",
				"Extra button(s) present. Expected only: " + expectedButtons + " Found: " + actualButtons);

		// ================= FIELD EXISTENCE, PLACEHOLDER & EDITABLE =================

		for (Map.Entry<String, String> entry : expectedFields.entrySet()) {

			String fieldName = entry.getKey();
			String expectedPlaceholder = entry.getValue();

			log.info("Validating field: " + fieldName);

			// Label must exist
			sa.assertTrue(dept.isLabelDisplayed(fieldName), "Label Name", fieldName);

			// Input must exist
			WebElement input = dept.getInputFieldForLabel(fieldName);
			sa.assertNotNull(input, "DOM Structure", fieldName);

			// Input visible
			sa.assertTrue(input.isDisplayed(), "Input", fieldName);

			// Editable
			sa.assertTrue(input.isEnabled(), "Enable ", fieldName);

			// Placeholder validation
			String actualPlaceholder = input.getDomAttribute("placeholder");
			sa.assertNotNull(expectedPlaceholder, "Placeholder in DOM ", fieldName);
			sa.assertEquals(actualPlaceholder, expectedPlaceholder, "Placeholder ", fieldName);

			log.info("✅ Field validated: " + fieldName);
		}

		// ================= BUTTON STATE =================
		for (String btnName : expectedButtons) {

			log.info("✅ Validating Button: " + btnName);

			WebElement btn = dept.getButtonByText(btnName);

			sa.assertNotNull(btn, "Button ", btnName);
			sa.assertTrue(btn.isDisplayed(), "Button", btnName);
			sa.assertTrue(btn.isEnabled(), "Button Enable", btnName);

			log.info("✅ Button validated: " + btnName);
		}

		log.info("Validating Company Logo");

		sa.assertNotNull(dept.getMaithriLogoImage(), "MAITHRI logo In DOM" , "Login Page" );

		sa.assertTrue(dept.getMaithriLogoImage().isDisplayed(), "MAITHRI logo ", "Login Page" );
		
		log.info("Company Logo Validation completed.");

		log.info("Login field validation completed.");

		try {
			ScreenshotUtil.capture();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Report all failures at the end
		sa.assertAll();
	}

	@Test(groups = { "userlogin" }, priority = 4)
	public void userLoginBeforeCreate() throws Throwable {
		log.info("Executing the flow with single/multiple : {}", ACTIONSPERFORMEDBY);
		if (ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
			login(USERNAME, PASSWORD);
		} else {
			login(USERNAME1, PASSWORD1);
		}
	}

	@Test(groups = { "allappvalidation" }, dependsOnMethods = "userLoginBeforeCreate", priority = 4)
	public void All_Apps_Validation() {
		log.info("--- Validating Module Selection Elements ---");

		// ================= EXPECT APPS =================
		Map<String, FieldDetails> expectedApps = new LinkedHashMap<>();

		expectedApps.put("MASTERS",
				new FieldDetails("Central repository for managing core data", "/content/images/settings.png"));
		expectedApps.put("MM (Material Management)", new FieldDetails(
				"Manages Inventory, Vendors, Product Cycle, Time & Costing.", "/content/images/mmicon.png"));

		for (Map.Entry<String, FieldDetails> entry : expectedApps.entrySet()) {

			String key = entry.getKey();
			FieldDetails data = entry.getValue();

			WebElement card = dept.getCardByTitle(key);
			sa.assertNotNull(card, "Card not found for ", key);

			WebElement icon = dept.getIcon(key);
			sa.assertTrue(icon.isDisplayed(), "Icon missing for ", key);
			sa.assertTrue(icon.getAttribute("src").contains(data.getIconPath()), "Wrong icon for ", key);

			WebElement desc = dept.getDescription(key);
			sa.assertEquals(desc.getText().trim(), data.getDescription(), "Description mismatch for ", key);

			log.info("✅ Home card validated: " + key);
		}

		// ================= ACTUAL UI DATA =================
		List<String> actualApps = dept.getAllAppsDisplayed();

		actualApps.removeIf(f -> f == null || f.trim().isEmpty());

		log.info("Actual Fields on UI  : " + actualApps);
		List<String> expectedAppsNames = new ArrayList<>(expectedApps.keySet());

		// ================= STRICT FIELD CONTRACT =================
		boolean missingApps = actualApps.containsAll(expectedAppsNames);
		boolean extraApps = expectedAppsNames.containsAll(actualApps);

		sa.assertTrue(missingApps, "Fields contract: no fields missing",
				"Missing field(s). Expected: " + expectedAppsNames + " Found: " + actualApps);

		sa.assertTrue(extraApps, "Fields contract: no extra fields",
				"Extra field(s) present. Expected only: " + expectedAppsNames + " Found: " + actualApps);

		log.info("Module validation completed.");
		sa.assertAll();
	}
	
	@Test(groups= {"breadcrumbsvalidationonhome"}, priority=4)
	public void BreadCrumbs_Validation_On_Home() throws Throwable {
		
		dept.click_titleMasters();

		
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

	@Test(groups = { "creationvalidation" }, dependsOnMethods = "moduleClick", priority = 6)
	public void Creation_Screen_Validation() throws Throwable {

		log.info("--- Validating Department Creation Screen ---");

		dept.Create();
		dept.waitForLoading();

		// ================= EXPECTED FIELDS =================
		Map<String, String> expectedFields = new LinkedHashMap<>();
		expectedFields.put("Department Name", "Department Name");
		expectedFields.put("Description", "Description"); // enable if needed later

		// ================= EXPECTED BUTTONS =================
		List<String> expectedButtons = Arrays.asList("Submit", "Cancel");

		// ================= FIELD EXISTENCE, PLACEHOLDER & EDITABLE =================
		for (Map.Entry<String, String> entry : expectedFields.entrySet()) {

			String fieldName = entry.getKey();
			String expectedPlaceholder = entry.getValue();

			log.info("Validating field: " + fieldName);

			// Label must exist
			sa.assertTrue(dept.isLabelDisplayed(fieldName), "Label Name", fieldName);

			// Input must exist
			WebElement input = dept.getInputFieldForLabel(fieldName);
			sa.assertNotNull(input, "DOM Structure", fieldName);

			// Input visible
			sa.assertTrue(input.isDisplayed(), "Input", fieldName);

			// Editable
			sa.assertTrue(input.isEnabled(), "Enable ", fieldName);

			// Placeholder validation
			String actualPlaceholder = input.getDomAttribute("placeholder");
			sa.assertNotNull(expectedButtons, "Button", fieldName);
			sa.assertEquals(actualPlaceholder, expectedPlaceholder, "Placeholder ", fieldName);

			log.info("✅ Field validated: " + fieldName);
		}

		// ================= ACTUAL UI DATA =================
		List<String> actualFields = dept.getDisplayedFieldLabels();
		List<String> actualButtons = dept.getDisplayedButtons();

		actualFields.removeIf(f -> f == null || f.trim().isEmpty());
		actualButtons.removeIf(b -> b == null || b.trim().isEmpty());

		log.info("Actual Fields on UI  : " + actualFields);
		log.info("Actual Buttons on UI : " + actualButtons);

		List<String> expectedFieldNames = new ArrayList<>(expectedFields.keySet());

		// ================= STRICT FIELD CONTRACT =================
		boolean missingFields = actualFields.containsAll(expectedFieldNames);
		boolean extraFields = expectedFieldNames.containsAll(actualFields);

		sa.assertTrue(missingFields, "Fields contract: no fields missing",
				"Missing field(s). Expected: " + expectedFieldNames + " Found: " + actualFields);

		sa.assertTrue(extraFields, "Fields contract: no extra fields",
				"Extra field(s) present. Expected only: " + expectedFieldNames + " Found: " + actualFields);

		// ================= STRICT BUTTON CONTRACT =================
		boolean missingButtons = actualButtons.containsAll(expectedButtons);
		boolean extraButtons = expectedButtons.containsAll(actualButtons);

		sa.assertTrue(missingButtons, "Buttons contract: no buttons missing",
				"Missing button(s). Expected: " + expectedButtons + " Found: " + actualButtons);

		sa.assertTrue(extraButtons, "Buttons contract: no extra buttons",
				"Extra button(s) present. Expected only: " + expectedButtons + " Found: " + actualButtons);

		// ================= BUTTON STATE =================
		for (String btnName : expectedButtons) {

			WebElement btn = dept.getButtonByText(btnName);

			sa.assertNotNull(btn, "Button ", btnName);
			sa.assertTrue(btn.isDisplayed(), "Button", btnName);
			sa.assertTrue(btn.isEnabled(), "Button Enable", btnName);

			log.info("✅ Button validated: " + btnName);
		}

		log.info("🎯 Creation Screen UI Contract validated successfully");

		dept.getCancelButton().click();
		dept.waitForLoading();

		sa.assertAll();
	}
	
	@Test(groups = { "breadcrumbsvalidationwwheninlistpage" }, dependsOnMethods = "moduleClick", priority = 6)
	public void BreadCrumbs_Validation_In_ListPage() throws Throwable {
		log.info("--- Validating Breadcrumbs in List Page ---");
		ScreenshotUtil.nextStep();

		List<String> expectedCrumbs = Arrays.asList("Home", MASTER_MODULE);
		List<String> actualCrumbs = dept.getBreadcrumbsText();

		log.info("Actual Breadcrumbs on UI  : " + actualCrumbs);

		// ================= STRICT BREADCRUMBS CONTRACT =================
		boolean missingCrumbs = actualCrumbs.containsAll(expectedCrumbs);
		boolean extraCrumbs = expectedCrumbs.containsAll(actualCrumbs);

		sa.assertTrue(missingCrumbs, "Breadcrumbs contract: no crumbs missing",
				"Missing breadcrumb(s). Expected: " + expectedCrumbs + " Found: " + actualCrumbs);

		sa.assertTrue(extraCrumbs, "Breadcrumbs contract: no extra crumbs",
				"Extra breadcrumb(s) present. Expected only: " + expectedCrumbs + " Found: " + actualCrumbs);
		
		
		//================== BREADCRUMBS CLICKABILITY =================
	

		log.info("🎯 Breadcrumbs in List Page validated successfully");

		sa.assertAll();
	
		
	}
	
	
	
	
	
	

	@Test(groups = { "Creation" }, dependsOnMethods = "Creation_Screen_Validation", priority = 5)
	public void Creation_Of_Department() throws Throwable {
		log.info("--- Navigating to Create Department Screen ---");
		ScreenshotUtil.nextStep();
		dept.Create();
		log.info("Clicked on Create button");
		dept.waitForLoading();
		ScreenshotUtil.capture();
		ScreenshotUtil.nextStep();
		log.info("--- Creating Department: {} ---", DEPT_NAME);
		ScreenshotUtil.nextStep();
		currentDeptName = DEPT_NAME; // Initialize with base name
		dept.deptName(DEPT_NAME);
		dept.deptDesc(DEPT_DESC);
		log.info("Entered Name and Description");
		ScreenshotUtil.capture();
		dept.createSubmit();
		log.info("Clicked Submit");
		ScreenshotUtil.capture();
		String authToast = authenticate(currentPassword);
		Assert.assertEquals(authToast, "Department Created Successfully", "Creation failed with message: " + authToast);
	}
	
	
	
	@Test(groups = { "redstarsandmandaoryincreate" }, dependsOnMethods = "Creation_Of_Department", priority = 6)
	
	public void RedStars_And_Mandatory_Fields_Validation_In_Create() throws Throwable {
		log.info("--- Validating Red Stars and Mandatory Fields in Create Department Screen ---");
		ScreenshotUtil.nextStep();
		dept.Create();
		log.info("Clicked on Create button");
		dept.waitForLoading();
		ScreenshotUtil.capture();
		 String fieldName = "Department Name";

		// Validate Red Stars
		sa.assertTrue(dept.isRedStarDisplayedForField(fieldName).isDisplayed(), "Red Star","Create Department");
		sa.assertTrue(dept.isNoValueEnteredInField(fieldName), "No Value ", fieldName);
		dept.getButtonByText("Submit").click();
		sa.assertTrue(dept.getErrorMessage(fieldName).isDisplayed(), "Error message ", fieldName);

		
		
		dept.deptName("Testing Red Star");
		sa.assertTrue(dept.isGreenStarDisplayedForField(fieldName).isDisplayed(), "Green Star","Create Department");
		sa.assertTrue(dept.isNoValueEnteredInField(fieldName), "No Value ", fieldName);
		dept.getButtonByText("Submit").click();
		
		// Validate Mandatory Fields

		
	   
		log.info("Validating mandatory field: " + fieldName);

		// Leave field empty and submit
		

	
	
		


		log.info("✅ Mandatory validation confirmed for: " + fieldName);


		log.info("Red Stars and Mandatory Fields validation completed.");

		dept.getCancelButton().click();
		dept.waitForLoading();

		sa.assertAll();
	}
	
	
	

	@Test(groups = { "actionsvalidation" }, dependsOnMethods = "Creation_Of_Department", priority = 6)
	public void ViewEdit_Icons_Validation() throws Throwable {
		if (ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
			// Get the current status from the table
			String currentStatus = dept.getStatus(currentDeptName);
			log.info("--- Current Status for {}: {} ---", currentDeptName, currentStatus);

			if (currentStatus.equalsIgnoreCase("draft")) {
				log.info("--- Validating View & Edit Icons for Draft/Single User ---");
				dept.validateIconsWithStatus(currentDeptName, true, true);
				log.info("Icons validation completed.");
			}
		}
	}

	@Test(groups = { "actionsvalidation" }, dependsOnMethods = "ViewEdit_Icons_Validation", priority = 6)
	public void Action_Menu_Validation() throws Throwable {
		if (ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
			// Get the current status from the table
			String currentStatus = dept.getStatus(currentDeptName);
			log.info("--- Current Status for {}: {} ---", currentDeptName, currentStatus);

			if (currentStatus.equalsIgnoreCase("draft")) {
				log.info(
						"--- Validating Action Menu Options (STRICT: Only Approve expected) for Draft/Single User ---");
				dept.validateActionsStrict(currentDeptName, Arrays.asList("Approve"));
				log.info("Action Menu strict validation completed.");
			}
		}
	}

	/*
	 * @Test(groups = { "editvalidation" }, dependsOnMethods =
	 * "Action_Menu_Validation", priority = 7) public void Edit_Page_Validation()
	 * throws Throwable { log.info("--- Starting Edit Page Validation ---");
	 * ScreenshotUtil.nextStep();
	 * 
	 * // Click Edit icon log.info("Clicking Edit icon for department: {}",
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
			String authToast = authenticate(currentPassword);
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
				switchUser(USERNAME2, PASSWORD2);
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
			String returnToast = authenticate(currentPassword);
			Assert.assertEquals(returnToast, "Department Returned Successfully",
					"Return failed with message: " + returnToast);

			if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
				switchUser(USERNAME1, PASSWORD1);
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
			authenticate(currentPassword);
		} else {
			log.info("Department Return and Edit skipped based on configuration");
		}
	}

	@Test(groups = { "DeptApprove" }, dependsOnMethods = "moduleClick", priority = 11)
	public void Department_Approve() throws Throwable {

		if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
			log.info("--- Approving Department: {} ---", currentDeptName);
			switchUser(USERNAME2, PASSWORD2);
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
		String approveToast = authenticate(currentPassword);
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

	/* ---------------- Helper Methods to Reduce Duplication ---------------- */

	private void login(String username, String password) throws Throwable {
		log.info("Logging in as: {}", username);
		dept.userNameandPassword(username, password);
		currentUsername = username;
		currentPassword = password;
		ScreenshotUtil.capture();
		dept.loginButton();
		dept.waitForToast();
		ScreenshotUtil.capture();
	}

	private void switchUser(String username, String password) throws Throwable {
		if (username.equalsIgnoreCase(currentUsername)) {
			log.info("Already logged in as {}, skipping switch.", username);
			return;
		}

		log.info("--- Switching User to: {} ---", username);
		ScreenshotUtil.nextStep();
		dept.click_profileDropdown();
		ScreenshotUtil.capture();
		dept.logout();
		log.info("Logged out previous user");
		ScreenshotUtil.capture();

		ScreenshotUtil.nextStep();
		login(username, password);
		dept.click_titleMasters();
		ScreenshotUtil.capture();
		dept.masterClick(MASTER_MODULE);
		log.info("Navigated back to Master Module: {}", MASTER_MODULE);
		dept.waitForLoading();
		ScreenshotUtil.capture();
	}

	private String authenticate(String password) throws Throwable {
		dept.passWord(password);
		log.info("Entered authentication password");
		ScreenshotUtil.capture();
		dept.authenticateButton();
		log.info("Clicked Authenticate");
		String toast = dept.waitForToast();
		dept.waitForLoading();
		ScreenshotUtil.capture();
		return toast;
	}

	public class FieldDetails {
		private String description;
		private String iconPath;

		public FieldDetails(String description, String iconPath) {
			this.description = description;
			this.iconPath = iconPath;
		}

		public String getDescription() {
			return description;
		}

		public String getIconPath() {
			return iconPath;
		}
	}

}