package testCasesForOQProjects;


import static configData.NDCData.*;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pageObjects.BasePage;

import testBase.BaseClass;
import utilities.DatabaseBackupUtil;
import utilities.ScreenshotUtil;
import utilities.SoftAssertionUtil;

public class NDC_TC extends BaseClass {
	BasePage ndc;
	String currentNdcNumber;
	SoftAssertionUtil sa;
	private int inactiveIterationCount = 0;
	private int activeIterationCount = 0;

	@BeforeClass
	@Parameters({ "configFile" })
	public void setUp(@Optional("ndc.properties") String configFile) throws Exception {
		log.info("--- Starting NDC Test Case Setup with config: {} ---", configFile);

		// Load the dynamic property file
		configData.NDCData.loadProperties(configFile);

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
		ndc = new BasePage(driver);
		ndc.setTableHeaders(TABLE_HEADERS);
		currentNdcNumber = NDC_NUMBER; // ✅ Initialize for use in all test methods
		log.info("Setup completed. Screenshots enabled: {}. NDC Number: {}", screenshotsEnabled, currentNdcNumber);
	}

	@BeforeMethod
	public void initSoftAssert() {
		sa = new SoftAssertionUtil();
	}

	@Test(groups = { "setup" })
	public void initialSetUp() throws Exception {
		ScreenshotUtil.nextStep();
		log.info("Navigating to Chrome URL: {}", CHROME_URL);
		driver.navigate().to(CHROME_URL);
		log.info("Waiting for search box to be visible");
		ndc.waitForElementToVisible(ndc.getSearchBox());
		Assert.assertTrue(ndc.getSearchBox().isDisplayed(), "Search box is not visible!");
		log.info("Searching for Application URL: {}", APP_URL);
		ndc.searchBox(APP_URL);
		ndc.waitForLoading();
		ScreenshotUtil.capture();
		log.info("Initial setup completed and screenshot captured");
	}

	@Test(groups = { "url" })
	public void url() throws Throwable {
		driver.navigate().to(APP_URL);
		log.info("Navigating to App URL: {}", APP_URL);
	}

	@Test(groups = { "userlogin" })
	public void userLoginBeforeCreate() throws Throwable {
		log.info("Executing the flow with single/multiple : {}", ACTIONSPERFORMEDBY);
		if (ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
			ndc.login(USERNAME, PASSWORD, PC_DB_NAME);
		} else {
			ndc.login(USERNAME1, PASSWORD1, PC_DB_NAME);
		}
	}

	@Test(groups = { "moduleselect" })
	public void moduleClick() throws Throwable {
		log.info("--- Clicking Module ---");
		ndc.click_titleMasters();
		log.info("Clicked on Masters title");
		ScreenshotUtil.capture();
		ndc.waitForLoading();
		ndc.masterClick(MASTER_MODULE);
		log.info("Clicked on Master Module: {}", MASTER_MODULE);
		ndc.masterClick(SUB_MASTER_MODULE);
		log.info("Clicked on Sub Master Module: {}", SUB_MASTER_MODULE);
		ndc.waitForLoading();
		ScreenshotUtil.capture();
	}

	@Test(groups = { "ClickActions" })
	public void Click_Actions() throws Throwable {

		if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
			ndc.switchUser(USERNAME1, PASSWORD1, PC_DB_NAME, MASTER_MODULE, SUB_MASTER_MODULE);
		}
		log.info("--- Attempting to open Actions Menu for: {} ---", currentNdcNumber);
		ScreenshotUtil.nextStep();
		ndc.clickActions(currentNdcNumber);
		log.info("Successfully opened Actions menu for {}", currentNdcNumber);
		ScreenshotUtil.capture();
		ndc.clickActions(currentNdcNumber);
	}

	@Test(groups = { "ClickView" })
	public void Click_View() throws Throwable {
		if (NDC_VIEW_ACTION.equalsIgnoreCase("yes")) {
			log.info("--- Viewing Quick Product: {} ---", currentNdcNumber);
			ScreenshotUtil.nextStep();
			ndc.clickView(currentNdcNumber);
			log.info("View screen opened");
			ndc.waitForLoading();
			ScreenshotUtil.capture();
			ndc.clickBack();
			log.info("Clicked Back button");
			ndc.waitForLoading();
			ScreenshotUtil.capture();
		} else {
			log.info("View step skipped based on configuration");
		}
	}

	@Test(groups = { "ClickInactive" })
	public void Click_Inactive() throws Throwable {
		inactiveIterationCount++;

		if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
			log.info("--- Inactivating Product: {} ---", currentNdcNumber);
			ndc.switchUser(USERNAME1, PASSWORD1, PC_DB_NAME, MASTER_MODULE, SUB_MASTER_MODULE);
		}
		log.info("--- Performing Inactive for: {} ---", currentNdcNumber);

		ndc.waitForLoading();
		if (inactiveIterationCount == 1) {
			ScreenshotUtil.nextStep();
		}
		log.info("Opening actions menu for Inactive");
		ndc.clickActions(currentNdcNumber);
		ScreenshotUtil.capture();
		log.info("Clicking Inactive button in the list");
		ndc.clickInactive();
		ScreenshotUtil.capture();
		ndc.enterRemarks(INACTIVE_REMARKS);
		log.info("Entered Inactive remarks: {}", INACTIVE_REMARKS);
		ScreenshotUtil.capture();
		ndc.clickInactive();
		log.info("Inactive requested");
		ScreenshotUtil.capture();
		ndc.authenticate(ndc.currentPassword);
		String inactiveToast = ndc.waitForToast();
		ndc.waitForLoading();
		sa.assertEquals(inactiveToast, "NDC Inactive Request initiated successfully", "Inactive toaster messege",
				inactiveToast);
		sa.assertAll();

	}

	@Test(groups = { "Ndc_Inactive_Review_Reject" })
	public void ndc_Inactive_Review_Reject() throws Throwable {

		if (NDC_INACTIVE_REJECT_IN_REVIEW_ACTION.equalsIgnoreCase("yes")) {

			if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
				ndc.switchUser(USERNAME2, PASSWORD2, PC_DB_NAME, MASTER_MODULE, SUB_MASTER_MODULE);
			}
			log.info("--- Initiating Inactive Reject In Review Flow for: {} ---", currentNdcNumber);
			log.info("Opening actions menu to access InActive Review/Reject");
			ndc.clickActions(currentNdcNumber);
			ScreenshotUtil.capture();
			ScreenshotUtil.nextStep();
			log.info("Clicking Review to trigger return dialog");
			ndc.clickInactiveReview();
			ndc.waitForLoading();
			ScreenshotUtil.capture();
			ndc.enterRemarks(INACTIVE_REVIEW_REJECT_REMARKS);
			log.info("Entered Inactive Review Reject remarks: {}", INACTIVE_REVIEW_REJECT_REMARKS);
			ScreenshotUtil.capture();
			ndc.clickReject();
			log.info("Clicked Reject button");
			ScreenshotUtil.capture();
			ndc.authenticate(ndc.currentPassword);
			String rejectToast = ndc.waitForToast();
			ndc.waitForLoading();
			sa.assertEquals(rejectToast, "NDC Reject successfully", "Inactive Review Reject  toaster message",
					rejectToast);
			sa.assertAll();

		}
	}

	@Test(groups = { "Ndc_Inactive_Review" })
	public void ndc_Inactive_Review() throws Throwable {

		if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
			ndc.switchUser(USERNAME2, PASSWORD2, PC_DB_NAME, MASTER_MODULE, SUB_MASTER_MODULE);
		}
		log.info("--- Initiating Inactive Review Flow for: {} ---", currentNdcNumber);
		log.info("Opening actions menu to access Inactive Review/Reject");

		ndc.clickActions(currentNdcNumber);
		ScreenshotUtil.capture();
		log.info("Clicking Review to trigger return dialog");
		ndc.clickInactiveReview();
		ndc.waitForLoading();
		ScreenshotUtil.capture();
		ndc.enterRemarks(INACTIVE_REVIEW__REMARKS);
		log.info("Entered Inactive Review remarks: {}", INACTIVE_REVIEW_REJECT_REMARKS);
		ScreenshotUtil.capture();
		ndc.clickInactiveReview();
		log.info("Clicked Review button");
		ScreenshotUtil.capture();
		ndc.authenticate(ndc.currentPassword);
		String reviewToast = ndc.waitForToast();
		ndc.waitForLoading();
		sa.assertEquals(reviewToast, "NDC Reviewed successfully", "Inactive Review toaster message",
				reviewToast);
		sa.assertAll();

	}

	@Test(groups = { "Ndc_Inactive_Approve_Reject" })
	public void ndc_Inactive_Approve_Reject() throws Throwable {

		if (NDC_INACTIVE_REJECT_IN_APPROVE_ACTION.equalsIgnoreCase("yes")) {

			if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
				ndc.switchUser(USERNAME3, PASSWORD3, PC_DB_NAME, MASTER_MODULE, SUB_MASTER_MODULE);
			}
			log.info("--- Initiating Inactive Reject In Approve Flow for: {} ---", currentNdcNumber);
			log.info("Opening actions menu to access Inactive Approve/Reject");
			ScreenshotUtil.freezeStepNumbering();
			ndc.clickActions(currentNdcNumber);
			ScreenshotUtil.capture();
			log.info("Clicking Approve to trigger return dialog");
			ndc.clickInactiveApprove();
			ndc.waitForLoading();
			ScreenshotUtil.capture();
			ndc.enterRemarks(INACTIVE_APPROVE_REJECT_REMARKS);
			log.info("Entered Inactive Approve Reject remarks: {}", INACTIVE_APPROVE_REJECT_REMARKS);
			ScreenshotUtil.capture();
			ndc.clickReject();
			log.info("Clicked Reject button");
			ScreenshotUtil.capture();
			ndc.authenticate(ndc.currentPassword);
			String rejectToast = ndc.waitForToast();
			ndc.waitForLoading();
			sa.assertEquals(rejectToast, "NDC Reject successfully", "Inactive Approve Reject  toaster message",
					rejectToast);
			sa.assertAll();

		}
	}

	@Test(groups = { "Ndc_Inactive_Approve" })
	public void ndc_Inactive_Approve() throws Throwable {

		if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
			ndc.switchUser(USERNAME3, PASSWORD3, PC_DB_NAME, MASTER_MODULE, SUB_MASTER_MODULE);
		}
		log.info("--- Initiating Inactive Approve Flow for: {} ---", currentNdcNumber);
		log.info("Opening actions menu to access Inactive Approve/Reject");
		ndc.clickActions(currentNdcNumber);
		ScreenshotUtil.capture();
		log.info("Clicking Approve to trigger return dialog");
		ndc.clickInactiveApprove();
		ndc.waitForLoading();
		ScreenshotUtil.capture();
		ndc.enterRemarks(INACTIVE_APPROVE_REMARKS);
		log.info("Entered Inactive Approve remarks: {}", INACTIVE_APPROVE_REMARKS);
		ScreenshotUtil.capture();
		ndc.clickInactiveApprove();
		log.info("Clicked Approve button");
		ScreenshotUtil.capture();
		ndc.authenticate(ndc.currentPassword);
		String approveToast = ndc.waitForToast();
		ndc.waitForLoading();
		sa.assertEquals(approveToast, "NDC Inactivated successfully", "Inactivated toaster message",
				approveToast);
		sa.assertAll();

	}

	@Test(groups = { "ClickActive" })
	public void Click_Active() throws Throwable {
		activeIterationCount++;
		ScreenshotUtil.resumeStepNumbering();
		if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
			log.info("--- Activating Product: {} ---", currentNdcNumber);
			ndc.switchUser(USERNAME1, PASSWORD1, PC_DB_NAME, MASTER_MODULE, SUB_MASTER_MODULE);
		}
		log.info("--- Performing Active for: {} ---", currentNdcNumber);

		ndc.waitForLoading();
		log.info("Opening actions menu for Active");
		ndc.clickActions(currentNdcNumber);
		ScreenshotUtil.capture();
		log.info("Clicking Active button in the list");
		if (activeIterationCount == 1) {
			ScreenshotUtil.nextStep();
		}
		ndc.clickActive();
		ScreenshotUtil.capture();
		ndc.enterRemarks(ACTIVE_REMARKS);
		log.info("Entered Active remarks: {}", ACTIVE_REMARKS);
		ScreenshotUtil.capture();
		ndc.clickActive();
		log.info("Active requested");
		ScreenshotUtil.capture();
		ndc.authenticate(ndc.currentPassword);
		String activeToast = ndc.waitForToast();
		ndc.waitForLoading();
		sa.assertEquals(activeToast, "NDC active request initiated successfully", "Active toaster messege",
				activeToast);
		sa.assertAll();

	}

	@Test(groups = { "Ndc_Active_Review_Reject" })
	public void ndc_Active_Review_Reject() throws Throwable {

		if (NDC_ACTIVE_REJECT_IN_REVIEW_ACTION.equalsIgnoreCase("yes")) {

			if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
				ndc.switchUser(USERNAME2, PASSWORD2, PC_DB_NAME, MASTER_MODULE, SUB_MASTER_MODULE);
			}
			log.info("--- Initiating Active Reject In Review Flow for: {} ---", currentNdcNumber);
			log.info("Opening actions menu to access Active Review/Reject");
			ndc.clickActions(currentNdcNumber);
			ScreenshotUtil.capture();
			ScreenshotUtil.nextStep();
			log.info("Clicking Review to trigger return dialog");
			ndc.clickActiveReview();
			ndc.waitForLoading();
			ScreenshotUtil.capture();
			ndc.enterRemarks(ACTIVE_REVIEW_REJECT_REMARKS);
			log.info("Entered Inactive Review Reject remarks: {}", ACTIVE_REVIEW_REJECT_REMARKS);
			ScreenshotUtil.capture();
			ndc.clickReject();
			log.info("Clicked Reject button");
			ScreenshotUtil.capture();
			ndc.authenticate(ndc.currentPassword);
			String rejectToast = ndc.waitForToast();
			ndc.waitForLoading();
			sa.assertEquals(rejectToast, "NDC Reject successfully", "Active Review Reject  toaster message",
					rejectToast);
			sa.assertAll();

		}
	}

	@Test(groups = { "Ndc_Active_Review" })
	public void ndc_Active_Review() throws Throwable {

		if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
			ndc.switchUser(USERNAME2, PASSWORD2, PC_DB_NAME, MASTER_MODULE, SUB_MASTER_MODULE);
		}
		log.info("--- Initiating Active Review Flow for: {} ---", currentNdcNumber);
		log.info("Opening actions menu to access InActive Review/Reject");
		ndc.clickActions(currentNdcNumber);
		ScreenshotUtil.capture();
		log.info("Clicking Review to trigger return dialog");
		ndc.clickActiveReview();
		ndc.waitForLoading();
		ScreenshotUtil.capture();
		ndc.enterRemarks(ACTIVE_REVIEW__REMARKS);
		log.info("Entered Active Review remarks: {}", ACTIVE_REVIEW__REMARKS);
		ScreenshotUtil.capture();
		ndc.clickActiveReview();
		log.info("Clicked Review button");
		ScreenshotUtil.capture();
		ndc.authenticate(ndc.currentPassword);
		String reviewToast = ndc.waitForToast();
		ndc.waitForLoading();
		sa.assertEquals(reviewToast, "NDC Reviewed successfully", "Active Review toaster message",
				reviewToast);
		sa.assertAll();

	}

	@Test(groups = { "Ndc_Active_Approve_Reject" })
	public void ndc_Active_Approve_Reject() throws Throwable {

		if (NDC_ACTIVE_REJECT_IN_APPROVE_ACTION.equalsIgnoreCase("yes")) {

			if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
				ndc.switchUser(USERNAME3, PASSWORD3, PC_DB_NAME, MASTER_MODULE, SUB_MASTER_MODULE);
			}
			log.info("--- Initiating Active Reject In Approve Flow for: {} ---", currentNdcNumber);
			log.info("Opening actions menu to access Active Approve/Reject");
			ScreenshotUtil.freezeStepNumbering();
			ndc.clickActions(currentNdcNumber);
			ScreenshotUtil.capture();
			log.info("Clicking Approve to trigger return dialog");
			ndc.clickActiveApprove();
			ndc.waitForLoading();
			ScreenshotUtil.capture();
			ndc.enterRemarks(ACTIVE_APPROVE_REJECT_REMARKS);
			log.info("Entered Active Approve Reject remarks: {}", ACTIVE_APPROVE_REJECT_REMARKS);
			ScreenshotUtil.capture();
			ndc.clickReject();
			log.info("Clicked Reject button");
			ScreenshotUtil.capture();
			ndc.authenticate(ndc.currentPassword);
			String rejectToast = ndc.waitForToast();
			ndc.waitForLoading();
			sa.assertEquals(rejectToast, "NDC Reject successfully", "Active Approve Reject toaster message",
					rejectToast);
			sa.assertAll();

		}
	}

	@Test(groups = { "Ndc_Active_Approve" })
	public void ndc_Active_Approve() throws Throwable {

		if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
			ndc.switchUser(USERNAME3, PASSWORD3, PC_DB_NAME, MASTER_MODULE, SUB_MASTER_MODULE);
		}
		log.info("--- Initiating Active Approve Flow for: {} ---", currentNdcNumber);
		ndc.clickActions(currentNdcNumber);
		ScreenshotUtil.capture();
		log.info("Clicking Approve to trigger return dialog");
		ndc.clickActiveApprove();
		ndc.waitForLoading();
		ScreenshotUtil.capture();
		ndc.enterRemarks(ACTIVE_APPROVE_REMARKS);
		log.info("Entered Active Approve remarks: {}", ACTIVE_APPROVE_REMARKS);
		ScreenshotUtil.capture();
		ndc.clickInactiveApprove();
		log.info("Clicked Approve button");
		ScreenshotUtil.capture();
		ndc.authenticate(ndc.currentPassword);
		String approveToast = ndc.waitForToast();
		ndc.waitForLoading();
		sa.assertEquals(approveToast, "NDC Activated successfully", "Activated toaster message",
				approveToast);
		ScreenshotUtil.resumeStepNumbering();
		sa.assertAll();

	}

	@Test(groups = { "Logout" })
	public void Logout() throws Throwable {
		log.info("--- Executing Final Logout ---");
		ndc.logout();
		log.info("Clicked logout button");
		ndc.waitForToast();
		ndc.waitForLoading();
		ScreenshotUtil.capture();
		log.info("--- Department Test Case Execution Finished ---");
	}

	@Test(groups = { "DB Back" })
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

	@Test(groups = { "ClickInactiveRep" })
	public void Click_Inactive_1() throws Throwable {
		Click_Inactive();
	}

	@Test(groups = { "ClickInactiveRep" })
	public void Click_Inactive_2() throws Throwable {
		Click_Inactive();
	}

	@Test(groups = { "ClickInactiveRep" })
	public void Click_Inactive_3() throws Throwable {
		Click_Inactive();
	}

	@Test(groups = { "Ndc_Inactive_ReviewRep" })
	public void ndc_Inactive_Review_1() throws Throwable {
		ndc_Inactive_Review();
	}

	@Test(groups = { "Ndc_Inactive_ReviewRep" })
	public void ndc_Inactive_Review_2() throws Throwable {
		ndc_Inactive_Review();
	}

	@Test(groups = { "ClickActiveRep" })
	public void Click_Active_1() throws Throwable {
		Click_Active();
	}

	@Test(groups = { "ClickActiveRep" })
	public void Click_Active_2() throws Throwable {
		Click_Active();
	}

	@Test(groups = { "ClickActiveRep" })
	public void Click_Active_3() throws Throwable {
		Click_Active();
	}

	@Test(groups = { "Ndc_Active_ReviewRep" })
	public void ndc_Active_Review_1() throws Throwable {
		ndc_Active_Review();
	}

	@Test(groups = { "Ndc_Inactive_ReviewRep" })
	public void ndc_Active_Review_2() throws Throwable {
		ndc_Active_Review();
	}

}
