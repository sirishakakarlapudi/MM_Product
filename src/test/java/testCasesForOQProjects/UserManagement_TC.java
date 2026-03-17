package testCasesForOQProjects;

import static configData.UserManagementData.*;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pageObjects.UserManagement;
import testBase.BaseClass;
import utilities.DataProviders;
import utilities.DatabaseBackupUtil;
import utilities.ScreenshotUtil;
import utilities.SoftAssertionUtil;
import utilities.UserManagementOQData;

public class UserManagement_TC extends BaseClass {
	UserManagement user;
	String currentemployeeid;
	SoftAssertionUtil sa;

	@BeforeClass
	@Parameters({ "configFile" })
	public void setUp(@Optional("usermanagement.properties") String configFile) throws Exception {
		log.info("--- Starting UserManagement Test Case Setup with config: {} ---", configFile);

		// Load the dynamic property file
		configData.UserManagementData.loadProperties(configFile);

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
		user = new UserManagement(driver);
		user.setTableHeaders(TABLE_HEADERS);
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
		user.waitForElementToVisible(user.getSearchBox());
		Assert.assertTrue(user.getSearchBox().isDisplayed(), "Search box is not visible!");
		log.info("Searching for Application URL: {}", APP_URL);
		user.searchBox(APP_URL);
		user.waitForLoading();
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
			user.login(USERNAME, PASSWORD, PC_DB_NAME);
		} else {
			user.login(USERNAME1, PASSWORD1, PC_DB_NAME);
		}
	}

	@Test(groups = { "moduleselect" }, priority = 4)
	public void moduleClick() throws Throwable {
		log.info("--- Clicking Module ---");
		ScreenshotUtil.nextStep();
		user.click_titleMasters();
		log.info("Clicked on Masters title");
		user.waitForLoading();
		ScreenshotUtil.capture();
		ScreenshotUtil.nextStep();
		user.masterClick(MASTER_MODULE);
		log.info("Clicked on Master Module: {}", MASTER_MODULE);
		ScreenshotUtil.capture();
		user.masterClick(SUB_MASTER_MODULE);
		log.info("Clicked on Master Module: {}", SUB_MASTER_MODULE);
		ScreenshotUtil.capture();
	}

	@Test(groups = { "Creation" }, priority = 5)
	public void Creation_Of_User() throws Throwable {
		ScreenshotUtil.nextStep();
		user.Create();
		log.info("--- Navigating to User Creation Screen ---");
		log.info("--- Creating User: {} ---", EMPLOYEE_ID);
		System.out.println("employee id" + EMPLOYEE_ID);
		currentemployeeid = EMPLOYEE_ID;
		user.employeeID(EMPLOYEE_ID);
		user.employeeName(EMPLOYEE_NAME);

		if (EMAIL != null && !EMAIL.trim().isEmpty()) {
			user.email(EMAIL);
		}
		if (MOBILE_NUMBER != null && !MOBILE_NUMBER.trim().isEmpty()) {
			user.mobileNumber(MOBILE_NUMBER);
		}
		user.userName(USER_NAME);
		user.temporaryPassword(TEMPORARY_PASSWORD);
		user.deptSelect(DEPARTMENT);
		user.designation(DESIGNATION);

		if (user.headingTrainingCriteria().isDisplayed()) {
			log.info("Training Criteria heading displayed. Filling details...");
			user.clickDateOfJoining();
			user.selectDate(DATE_OF_JOINING);
			user.clickJDApproved();
			user.selectDate(JD_APPROVED_ON);
			user.clickTrainingCompletedDate();
			user.selectDate(TRAINING_COMPLETED_ON);
			user.jobCode(JOB_CODE);
			user.modeOfTraining(MODE_OF_TRAINING);
			user.requireQuestionnaire(REQUIRE_QUESTIONNAIRE);
		}

		user.moduleSelect(MODULE);
		user.sgNameSelect(SECURITY_GROUP);
		ScreenshotUtil.capture();

		user.clickSubmit();
		log.info("Clicked Submit");
		ScreenshotUtil.capture();
		user.authenticate(user.currentPassword);
		String authToast = user.waitForToast();
		user.waitForLoading();
		ScreenshotUtil.capture();
		Assert.assertEquals(authToast, "User created successfully", "Creation failed with message: " + authToast);
		ScreenshotUtil.nextStep();
		ScreenshotUtil.capture();

	}

	@Test(groups = { "ClickActions" }, priority = 6)
	public void Click_Actions_After_Create() throws Throwable {
		log.info("--- Attempting to open Actions Menu for: {} ---", currentemployeeid);
		ScreenshotUtil.nextStep();
		user.clickActions(currentemployeeid);
		log.info("Successfully opened Actions menu for {}", currentemployeeid);
		ScreenshotUtil.capture();
	}

	// ========================CLICKING VIEW TO VERIFY
	// DETAILS========================

	@Test(groups = { "ClickView" }, priority = 7)
	public void Click_View() throws Throwable {
		if (USER_VIEW.equalsIgnoreCase("yes")) {
			log.info("--- Viewing Department: {} ---", currentemployeeid);
			ScreenshotUtil.nextStep();
			user.clickView(currentemployeeid);
			log.info("View screen opened");
			user.waitForLoading();
			ScreenshotUtil.capture();
			user.clickBack();
			log.info("Clicked Back button");
			user.waitForLoading();
			ScreenshotUtil.capture();
		} else {
			log.info("View step skipped based on configuration");
		}
	}

	// ========================USER EDIT========================

	@Test(groups = { "ClickEdit" }, priority = 8)
	public void Click_Edit() throws Throwable {

		if (USER_EDIT.equalsIgnoreCase("yes")) {
			if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
				user.switchUser(USERNAME1, PASSWORD1, PC_DB_NAME, MASTER_MODULE);
			}

			// This part will now execute for BOTH single and multiple users
			log.info("Opening Edit screen)");
			ScreenshotUtil.nextStep();
			user.clickEdit(currentemployeeid);
			user.waitForLoading();
			ScreenshotUtil.capture();

			if (EDIT_EMPLOYEE_NAME != null && !EDIT_EMPLOYEE_NAME.trim().isEmpty()) {
				log.info("Updating Employee Name to: {}", EDIT_EMPLOYEE_NAME);
				user.employeeName(EDIT_EMPLOYEE_NAME);
			}

			if (EDIT_EMAIL != null && !EDIT_EMAIL.trim().isEmpty()) {
				log.info("Updating Emial to: {}", EDIT_EMAIL);
				user.email(EDIT_EMAIL);
			}

			if (EDIT_MOBILE_NUMBER != null && !EDIT_MOBILE_NUMBER.trim().isEmpty()) {
				log.info("Updating Mobile Number to: {}", EDIT_MOBILE_NUMBER);
				user.employeeName(EDIT_MOBILE_NUMBER);

			}
			if (EDIT_DESIGNATION != null && !EDIT_DESIGNATION.trim().isEmpty()) {
				log.info("Updating Designation to: {}", EDIT_DESIGNATION);
				user.employeeName(EDIT_DESIGNATION);

			}

			ScreenshotUtil.capture();
			user.clickUpdate();
			log.info("Clicked Update");
			ScreenshotUtil.capture();
			user.authenticate(user.currentPassword);
			user.waitForLoading();
			ScreenshotUtil.capture();
		} else {
			log.info("User Edit skipped based on configuration");
		}

	}

	// ========================CREATING BULK USERS========================

	@Test(priority = 9, dataProvider = "UserManagementOQ", dataProviderClass = DataProviders.class, singleThreaded = true)
	public void createUserWithBulkData(UserManagementOQData userdata) throws Throwable {
		String empid = userdata.getEmpID();
		String empname = userdata.getEmpName();
		String email = userdata.getEmail();
		String mobilenumber = userdata.getMobileNumber();
		String username = userdata.getUsername();
		String temppassword = userdata.getTemporaryPassword();
		String department = userdata.getDepartment();
		String designation = userdata.getDesignation();
		String module = userdata.getModule();
		String sgname = userdata.getSecurityGroupName();
		log.info("--- Creating User: {} ---", empid);
		user.Create();
		user.waitForLoading();
		ScreenshotUtil.capture();

		user.employeeID(empid);
		user.employeeName(empname);
		if (email != null && !email.trim().isEmpty()) {
			user.email(email);
		}
		if (mobilenumber != null && !mobilenumber.trim().isEmpty()) {
			user.mobileNumber(mobilenumber);
		}

		user.userName(username);
		user.temporaryPassword(temppassword);
		user.deptSelect(department);
		user.designation(designation);

		if (user.headingTrainingCriteria().isDisplayed()) {
			log.info("Training Criteria heading displayed in Bulk Data. Filling details...");
			user.clickDateOfJoining();
			user.selectDate(userdata.getDateOfJoining());
			user.clickJDApproved();
			user.selectDate(userdata.getJDApprovedOn());
			user.clickTrainingCompletedDate();
			user.selectDate(userdata.getTrainingCompletedOn());
			user.jobCode(userdata.getJobCode());
			user.modeOfTraining(userdata.getModeOfTraining());
			user.requireQuestionnaire(userdata.getRequireQuestionnaire());
		}

		user.moduleSelect(module);
		user.sgNameSelect(sgname);
		ScreenshotUtil.capture();
		user.clickSubmit();
		log.info("Clicked Submit");
		ScreenshotUtil.capture();
		user.authenticate(user.currentPassword);
		user.waitForLoading();
		ScreenshotUtil.capture();

	}

	// =====================DUPLICATION CHECK IN CREATE PAGE========================

	@Test(groups = { "duplicationcheck" }, priority = 10)
	public void Duplication_Check() throws Throwable {

		ScreenshotUtil.nextStep();
		log.info("--- Checking Duplication Check with Employee ID In Create page: {} ---", currentemployeeid);
		user.Create();
		user.employeeID(currentemployeeid);
		user.clickEmployeeID();
		user.waitForToast();
	}

	// ===========================LOGOUT========================

	@Test(groups = { "Logout" }, priority = 11)
	public void Logout() throws Throwable {
		log.info("--- Executing Final Logout ---");
		ScreenshotUtil.nextStep();
		user.logout();
		log.info("Clicked logout button");
		user.waitForToast();
		user.waitForLoading();
		ScreenshotUtil.capture();
		log.info("--- UserManagement Test Case Execution Finished ---");
	}

	// ===========================DATABASE BACKUP========================
	@Test(groups = { "DB Back" }, priority = 12)
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