package testCasesForOQProjects;

import static configData.DepartmentData.*;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import pageObjects.Department;
import testBase.BaseClass;
import utilities.DatabaseBackupUtil;
import utilities.ScreenshotUtil;
import org.testng.Assert;
import org.openqa.selenium.WebElement;
import java.util.Arrays;

public class Department_TC extends BaseClass {
	Department dept;
	String currentDeptName; // Track the current name (including edits)
	String currentUsername; // Track the username of the currently logged-in user
	String currentPassword; // Track the password of the currently logged-in user
	SoftAssert sa;

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
		sa = new SoftAssert();
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
		sa.assertEquals(currentUrl, APP_URL, "URL Mismatch! Expected: " + APP_URL + ", but found: " + currentUrl);

		log.info("Application URL validation completed.");
		sa.assertAll();
	}

	@Test(groups = { "loginvalidation" }, dependsOnMethods = "url", priority = 3)
	public void Login_Field_Validation() {
		log.info("--- Validating Login Fields ---");

		// 1. Validate Username Field
		sa.assertTrue(dept.getUsernameField().isDisplayed(), "Username field is not displayed!");
		String userPlaceholder = dept.getUsernameField().getDomAttribute("placeholder");
		sa.assertEquals(userPlaceholder, "Username", "Username placeholder mismatch!");

		// 2. Validate Password Field
		sa.assertTrue(dept.getPasswordField().isDisplayed(), "Password field is not displayed!");
		String passPlaceholder = dept.getPasswordField().getDomAttribute("placeholder");
		sa.assertEquals(passPlaceholder, "Password", "Password placeholder mismatch!");

		// 3. Validate Login Button
		sa.assertTrue(dept.getLoginButton().isDisplayed(), "Login button is not displayed!");
		String btnText = dept.getLoginButton().getText().trim();
		sa.assertEquals(btnText, "Login", "Login button text mismatch!");

		log.info("Login field validation completed.");
		try {
			ScreenshotUtil.capture();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Report all failures at the end
		sa.assertAll();
	}

	@Test(groups = { "userlogin" }, dependsOnMethods = "Login_Field_Validation", priority = 4)
	public void userLoginBeforeCreate() throws Throwable {
		log.info("Executing the flow with single/multiple : {}", ACTIONSPERFORMEDBY);
		if (ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
			login(USERNAME, PASSWORD);
		} else {
			login(USERNAME1, PASSWORD1);
		}
	}

	@Test(groups = { "modulevalidation" }, dependsOnMethods = "userLoginBeforeCreate", priority = 4)
	public void Module_Selection_Validation() {
		log.info("--- Validating Module Selection Elements ---");

		sa.assertTrue(dept.getMastersTitle().isDisplayed(), "Masters Module title is not displayed!");
		sa.assertEquals(dept.getMastersTitle().getText().trim(), "MASTERS", "Module title text mismatch!");
		log.info("Master validation completed.");

		WebElement moduleElement = dept.getModuleElement(MASTER_MODULE);
		sa.assertTrue(moduleElement.isDisplayed(), "Module '" + MASTER_MODULE + "' is not displayed!");
		sa.assertEquals(moduleElement.getText().trim(), MASTER_MODULE, "Module text mismatch!");

		log.info("Module validation completed.");
		sa.assertAll();
	}

	@Test(groups = { "moduleselect" }, dependsOnMethods = "Module_Selection_Validation", priority = 4)
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

	@Test(groups = { "creationvalidation" }, dependsOnMethods = "moduleClick", priority = 5)
	public void Creation_Screen_Validation() throws Throwable {
		log.info("--- Validating Department Creation Screen ---");

		// 1. Validate Create Button on List Screen
		WebElement createBtn = dept.waitForCreateButton();
		sa.assertTrue(createBtn.isDisplayed(), "Create button is not displayed on list screen!");

		// 2. Click Create to open the form
		dept.Create();
		log.info("Clicked Create button for validation");
		dept.waitForLoading();

		// 3. Validate Fields inside Creation Screen
		sa.assertTrue(dept.getDeptNameField().isDisplayed(), "Department Name field is not displayed!");
		sa.assertEquals(dept.getDeptNameField().getDomAttribute("placeholder"), "Enter Department Name",
				"Dept Name placeholder mismatch!");

		sa.assertTrue(dept.getDeptDescField().isDisplayed(), "Department Description field is not displayed!");
		sa.assertEquals(dept.getDeptDescField().getDomAttribute("placeholder"), "Enter Department Description",
				"Dept Desc placeholder mismatch!");

		// 4. Validate Buttons
		sa.assertTrue(dept.getSubmitButton().isDisplayed(), "Submit button is not displayed!");
		sa.assertTrue(dept.getCancelButton().isDisplayed(), "Cancel button is not displayed!");

		log.info("Creation screen validation completed.");

		// 5. Cancel to return to list for the next test method
		dept.getCancelButton().click();
		log.info("Clicked Cancel to return to list");
		dept.waitForLoading();

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
		String backupFolderName = (SCRIPT_NUMBER == null || SCRIPT_NUMBER.trim().isEmpty())
				? CURRENT_CONFIG_NAME
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
}