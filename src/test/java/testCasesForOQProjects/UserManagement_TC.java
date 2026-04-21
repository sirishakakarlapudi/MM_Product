package testCasesForOQProjects;

import static configData.UserManagementData.*;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pageObjects.UserManagement;
import utilities.DataProviders;
import utilities.ScreenshotUtil;
import utilities.UserManagementOQData;

public class UserManagement_TC extends OQBaseModule_TC {
	UserManagement user;
	String currentemployeeid;

	@BeforeClass
	@Parameters({ "configFile" })
	public void setUp(@Optional("usermanagement.properties") String configFile) throws Exception {
		log.info("--- Starting UserManagement Test Case Setup with config: {} ---", configFile);

		// Load the dynamic property file
		configData.UserManagementData.loadProperties(configFile);

		// Initialize Base Class Variables
		CONFIG_NAME = CURRENT_CONFIG_NAME;
		CHROME_URL_VAL = CHROME_URL;
		APP_URL_VAL = APP_URL;
		USERNAME_VAL = USERNAME;
		PASSWORD_VAL = PASSWORD;
		USERNAME1_VAL = USERNAME1;
		PASSWORD1_VAL = PASSWORD1;
		ACTIONSPERFORMEDBY_VAL = ACTIONSPERFORMEDBY;
		PC_DB_NAME_VAL = PC_DB_NAME;
		MASTER_DB_NAME_VAL = MASTER_DB_NAME;
		MM_DB_NAME_VAL = MM_DB_NAME;
		TITLE_MODULE_VAL = "MASTERS"; // Hardcoded in previous version
		MASTER_MODULE_VAL = MASTER_MODULE;
		SUB_MASTER_MODULE_VAL = SUB_MASTER_MODULE;
		SCRIPT_NUMBER_VAL = SCRIPT_NUMBER;
		VIEW_ACTION_VAL = USER_VIEW;

		// Set conditional screenshot execution
		boolean screenshotsEnabled = "yes".equalsIgnoreCase(TAKE_SCREENSHOTS);
		ScreenshotUtil.setIsEnabled(screenshotsEnabled);

		if (screenshotsEnabled) {
			log.info("📸 Processing screenshot template and headers...");
			ScreenshotUtil.loadTemplateForEndAppend(TEMPLATE_PATH, OUTPUT_PATH);
			ScreenshotUtil.updateHeaderCellText(ACTUALHEADER, EXPECTEDHEADER);
			ScreenshotUtil.initScript(SCRIPT_NUMBER_VAL);
		}

		browserOpen();
		user = new UserManagement(driver);
		pageObject = user; // Essential for base class methods
		user.setTableHeaders(TABLE_HEADERS);
		log.info("Setup completed. Screenshots enabled: {}", screenshotsEnabled);
	}

	@Override
	protected void updateEntryName(String newName) throws Throwable {
		user.employeeName(newName);
	}

	// ==================== Module Specific Tests ====================

	@Test(groups = { "Creation" })
	public void Creation_Of_User() throws Throwable {
		ScreenshotUtil.nextStep();
		user.Create();
		log.info("--- Navigating to User Creation Screen ---");
		log.info("--- Creating User: {} ---", EMPLOYEE_ID);
		currentemployeeid = EMPLOYEE_ID;
		currentEntryName = EMPLOYEE_ID; // Set for base class actions
		
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

	@Test(dataProvider = "UserManagementOQ", dataProviderClass = DataProviders.class, singleThreaded = true)
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

	@Test(groups = { "duplicationcheck" })
	public void Duplication_Check() throws Throwable {
		ScreenshotUtil.nextStep();
		log.info("--- Checking Duplication Check with Employee ID: {} ---", currentEntryName);
		user.Create();
		user.employeeID(currentEntryName);
		user.clickEmployeeID();
		user.waitForToast();
	}

	@Test(groups = { "ClickEdit" })
	public void Click_Edit() throws Throwable {
		if (USER_EDIT.equalsIgnoreCase("yes")) {
			switchUserIfMulti(USERNAME1_VAL, PASSWORD1_VAL);

			log.info("Opening Edit screen");
			ScreenshotUtil.nextStep();
			user.clickEdit(currentEntryName);
			user.waitForLoading();
			ScreenshotUtil.capture();

			if (EDIT_EMPLOYEE_NAME != null && !EDIT_EMPLOYEE_NAME.trim().isEmpty()) {
				log.info("Updating Employee Name to: {}", EDIT_EMPLOYEE_NAME);
				user.employeeName(EDIT_EMPLOYEE_NAME);
				currentEntryName = EDIT_EMPLOYEE_NAME;
			}

			if (EDIT_EMAIL != null && !EDIT_EMAIL.trim().isEmpty()) {
				log.info("Updating Email to: {}", EDIT_EMAIL);
				user.email(EDIT_EMAIL);
			}

			if (EDIT_MOBILE_NUMBER != null && !EDIT_MOBILE_NUMBER.trim().isEmpty()) {
				log.info("Updating Mobile Number");
				user.mobileNumber(EDIT_MOBILE_NUMBER);
			}
			
			if (EDIT_DESIGNATION != null && !EDIT_DESIGNATION.trim().isEmpty()) {
				log.info("Updating Designation");
				user.designation(EDIT_DESIGNATION);
			}

			ScreenshotUtil.capture();
			user.clickUpdate();
			user.authenticate(user.currentPassword);
			user.waitForLoading();
			ScreenshotUtil.capture();
		}
	}
}