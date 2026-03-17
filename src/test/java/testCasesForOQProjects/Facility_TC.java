package testCasesForOQProjects;

import static configData.FacilityData.*;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pageObjects.Facility;
import testBase.BaseClass;
import utilities.DatabaseBackupUtil;
import utilities.ScreenshotUtil;
import utilities.SoftAssertionUtil;

public class Facility_TC extends BaseClass {
	Facility facility;
	String currentFacilityName;
	String currentFacilityType;// Track the current name (including edits)
	SoftAssertionUtil sa;

	@BeforeClass
	@Parameters({ "configFile" })
	public void setUp(@Optional("facility.properties") String configFile) throws Exception {
		log.info("--- Starting Facility Test Case Setup with config: {} ---", configFile);

		// CORRECTED: Load Facility properties
		configData.FacilityData.loadProperties(configFile);

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
		facility = new Facility(driver);
		facility.setTableHeaders(TABLE_HEADERS);
		log.info("Setup completed. Screenshots enabled: {}", screenshotsEnabled);
	}

	@BeforeMethod
	public void initSoftAssert() {
		sa = new SoftAssertionUtil();
	}

	@Test(groups = { "setup" }, priority = 1)
	public void initialSetUp() throws Exception {
		ScreenshotUtil.nextStep();
		log.info("Navigating to Chrome URL: {}", CHROME_URL);
		driver.navigate().to(CHROME_URL);
		log.info("Waiting for search box to be visible");
		facility.waitForElementToVisible(facility.getSearchBox());
		Assert.assertTrue(facility.getSearchBox().isDisplayed(), "Search box is not visible!");
		log.info("Searching for Application URL: {}", APP_URL);
		facility.searchBox(APP_URL);
		facility.waitForLoading();
		ScreenshotUtil.capture();
		log.info("Initial setup completed and screenshot captured");
	}

	@Test(groups = { "url" }, priority = 2)
	public void url() throws Throwable {
		driver.navigate().to(APP_URL);
		log.info("Navigating to App URL: {}", APP_URL);
	}

	@Test(groups = { "userlogin" }, priority = 3)
	public void userLoginBeforeCreate() throws Throwable {
		log.info("Executing the flow with single/multiple : {}", ACTIONSPERFORMEDBY);
		if (ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
			facility.login(USERNAME, PASSWORD, PC_DB_NAME);
		} else {
			facility.login(USERNAME1, PASSWORD1, PC_DB_NAME);
		}
	}

	@Test(groups = { "moduleselect" }, priority = 4)
	public void moduleClick() throws Throwable {
		log.info("--- Clicking Module ---");
		ScreenshotUtil.nextStep();
		facility.click_titleMasters();
		log.info("Clicked on Masters title");
		ScreenshotUtil.capture();
		facility.waitForLoading();
		ScreenshotUtil.nextStep();
		facility.masterClick(MASTER_MODULE);
		log.info("Clicked on Master Module: {}", MASTER_MODULE);
		facility.waitForLoading();
		ScreenshotUtil.capture();
	}

	@Test(groups = { "Creation" }, priority = 5)
	public void Creation_Of_Facility() throws Throwable {
		log.info("--- Navigating to Create Facility Screen ---");
		facility.Create();
		log.info("Clicked on Create button");
		facility.waitForLoading();
		ScreenshotUtil.capture();
		ScreenshotUtil.nextStep();
		log.info("--- Creating Facility Group: {} ---", FACILITY_NAME);
		ScreenshotUtil.nextStep();
		currentFacilityName = FACILITY_NAME; // Initialize with base name

		facility.facilityName(FACILITY_NAME);
		facility.selFacilityType(FACILITY_TYPE);

		facility.selDepartment(DEPARTMENT);
		if ("FACILITY".equalsIgnoreCase(FACILITY_TYPE)) {
			facility.storageCondition(STORAGE_CONDITION);
		} else {
			facility.selParentFacility(PARENT_FACILITY);
		}

		ScreenshotUtil.capture();

		facility.clickSubmit();
		log.info("Clicked Submit");
		ScreenshotUtil.capture();

		facility.authenticate(facility.currentPassword);
		String authToast = facility.waitForToast();
		facility.waitForLoading();
		ScreenshotUtil.capture();
		sa.assertEquals(authToast, "Facility created successfully", "Created Toaster message",
				"Creation failed with message: " + authToast);
		ScreenshotUtil.nextStep();
		ScreenshotUtil.capture();

	}

	@Test(groups = { "ClickActions" }, priority = 6)
	public void Click_Actions_After_Create() throws Throwable {
		log.info("--- Attempting to open Actions Menu for: {} ---", currentFacilityName);
		ScreenshotUtil.nextStep();
		facility.clickActions(currentFacilityName);
		log.info("Successfully opened Actions menu for {}", currentFacilityName);
		ScreenshotUtil.capture();

	}

	@Test(groups = { "ClickView" }, priority = 7)
	public void Click_View() throws Throwable {
		if (FACILITY_VIEW_ACTION.equalsIgnoreCase("yes")) {
			log.info("--- Viewing Security Group: {} ---", currentFacilityName);
			ScreenshotUtil.nextStep();
			facility.clickView(currentFacilityName);
			log.info("View screen opened");
			facility.waitForLoading();
			ScreenshotUtil.capture();
			facility.clickBack();
			log.info("Clicked Back button");
			facility.waitForLoading();
			ScreenshotUtil.capture();
		} else {
			log.info("View step skipped based on configuration");
		}
	}

	@Test(groups = { "facilityReturn_facilityEdit" }, priority = 8)
	public void facility_Return_and_Edit() throws Throwable {

		if (FACILITY_RETURN_ACTION.equalsIgnoreCase("yes")) {

			if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
				facility.switchUser(USERNAME2, PASSWORD2, PC_DB_NAME, MASTER_MODULE);
			}
			log.info("--- Initiating Return Flow for: {} ---", currentFacilityName);
			ScreenshotUtil.nextStep();
			log.info("Opening actions menu to access Approve/Return");
			facility.clickActions(currentFacilityName);
			ScreenshotUtil.capture();
			log.info("Clicking Approve to trigger return dialog");
			facility.clickApprove();
			facility.waitForLoading();
			ScreenshotUtil.capture();
			facility.enterRemarks(RETURN_REMARKS);
			log.info("Entered Return remarks: {}", RETURN_REMARKS);
			ScreenshotUtil.capture();
			facility.clickReturn();
			log.info("Clicked Return button");
			ScreenshotUtil.capture();
			facility.authenticate(facility.currentPassword);
			facility.waitForLoading();
			String returnToast = facility.waitForToast();
			sa.assertEquals(returnToast, "Facility returned successfully", "Returned toaster message", returnToast);

			if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
				facility.switchUser(USERNAME1, PASSWORD1, PC_DB_NAME, MASTER_MODULE);
			}

			// This part will now execute for BOTH single and multiple users

			log.info("Opening Edit screen (After Return)");
			ScreenshotUtil.nextStep();
			facility.clickEdit(currentFacilityName);
			facility.waitForLoading();
			ScreenshotUtil.capture();
			ScreenshotUtil.nextStep();
			if (EDIT_FACILITY_NAME != null && !EDIT_FACILITY_NAME.trim().isEmpty()) {
				log.info("Updating Name to: {}", EDIT_FACILITY_NAME);
				facility.facilityName(EDIT_FACILITY_NAME);
				currentFacilityName = EDIT_FACILITY_NAME;
			}

			if (EDIT_FACILITY_TYPE != null && !EDIT_FACILITY_TYPE.trim().isEmpty()) {
				facility.selFacilityType(EDIT_FACILITY_TYPE);
				currentFacilityType = EDIT_FACILITY_TYPE;
			}

			if (EDIT_DEPARTMENT != null && !EDIT_DEPARTMENT.trim().isEmpty()) {

				facility.selDepartment(EDIT_DEPARTMENT);
			}
			if ((EDIT_STORAGE_CONDITION != null && !EDIT_STORAGE_CONDITION.trim().isEmpty())
					| (EDIT_PARENT_FACILITY != null && !EDIT_PARENT_FACILITY.trim().isEmpty())) {

				if ("FACILITY".equalsIgnoreCase(currentFacilityType)) {

					if (EDIT_STORAGE_CONDITION != null && !EDIT_STORAGE_CONDITION.trim().isEmpty()) {
						facility.storageCondition(EDIT_STORAGE_CONDITION);
					}
				} else {
					if (EDIT_PARENT_FACILITY != null && !EDIT_PARENT_FACILITY.trim().isEmpty()) {
						facility.selParentFacility(EDIT_PARENT_FACILITY);
					}
				}

			}

			facility.clickUpdate();
			log.info("Clicked Update");
			ScreenshotUtil.capture();
			facility.authenticate(facility.currentPassword);
			String editToast = facility.waitForToast();
			sa.assertEquals(editToast, "Facility updated successfully", "Updated toaster messege", editToast);

		} else {
			log.info("Facility Return and Edit skipped based on configuration");
		}
	}

	@Test(groups = { "facilityApprove" }, priority = 9)
	public void facility_Approve() throws Throwable {

		if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
			log.info("--- Approving Facility: {} ---", currentFacilityName);
			facility.switchUser(USERNAME2, PASSWORD2, PC_DB_NAME, MASTER_MODULE);
		}
		log.info("--- Performing Final Approval for: {} ---", currentFacilityName);
		ScreenshotUtil.nextStep();
		log.info("Opening actions menu for approval");
		facility.clickActions(currentFacilityName);
		ScreenshotUtil.capture();
		log.info("Clicking Approve button in the list");
		facility.clickApprove();
		ScreenshotUtil.capture();
		facility.enterRemarks(APPROVE_REMARKS);
		log.info("Entered Approve remarks: {}", APPROVE_REMARKS);
		ScreenshotUtil.capture();
		facility.clickApprove();
		log.info("Submitted Approval");
		ScreenshotUtil.capture();
		facility.authenticate(facility.currentPassword);
		String approveToast = facility.waitForToast();
		sa.assertEquals(approveToast, "Facility approved successfully", "Approved toaster messege", approveToast);

	}

	@Test(groups = { "Logout" }, priority = 18)
	public void Logout() throws Throwable {
		log.info("--- Executing Final Logout ---");
		ScreenshotUtil.nextStep();
		facility.logout();
		log.info("Clicked logout button");
		facility.waitForToast();
		facility.waitForLoading();
		ScreenshotUtil.capture();
		log.info("--- Department Test Case Execution Finished ---");
	}

	@Test(groups = { "DB Back" }, priority = 19)
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
