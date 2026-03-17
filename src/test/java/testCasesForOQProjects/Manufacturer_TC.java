package testCasesForOQProjects;

import static configData.ManufacturerData.*;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageObjects.Manufacturer;
import testBase.BaseClass;
import utilities.DatabaseBackupUtil;
import utilities.ScreenshotUtil;
import utilities.SoftAssertionUtil;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class Manufacturer_TC extends BaseClass {
	Manufacturer manufacturer;
	String currentManufacturerName;
	SoftAssertionUtil sa;

	@BeforeClass
	@Parameters({ "configFile" })
	public void setUp(@Optional("manufacturer.properties") String configFile) throws Exception {
		log.info("--- Starting Manufacturer Test Case Setup with config: {} ---", configFile);

		// Load the dynamic property file
		configData.ManufacturerData.loadProperties(configFile);

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
		manufacturer = new Manufacturer(driver);
		manufacturer.setTableHeaders(TABLE_HEADERS);
		log.info("Setup completed. Screenshots enabled: {}", screenshotsEnabled);
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
		manufacturer.waitForElementToVisible(manufacturer.getSearchBox());
		Assert.assertTrue(manufacturer.getSearchBox().isDisplayed(), "Search box is not visible!");
		log.info("Searching for Application URL: {}", APP_URL);
		manufacturer.searchBox(APP_URL);
		manufacturer.waitForLoading();
		ScreenshotUtil.capture();
		log.info("Initial setup completed and screenshot captured");
	}

	@Test(groups = { "url" })
	public void url() throws Throwable {
		driver.navigate().to(APP_URL);
		log.info("Navigating to App URL: {}", APP_URL);
	}

	@Test(groups = { "userlogin" }, priority = 3)
	public void userLoginBeforeCreate() throws Throwable {
		log.info("Executing the flow with single/multiple : {}", ACTIONSPERFORMEDBY);
		if (ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
			manufacturer.login(USERNAME, PASSWORD, PC_DB_NAME);
		} else {
			manufacturer.login(USERNAME1, PASSWORD1, PC_DB_NAME);
		}
	}

	@Test(groups = { "moduleselect" })
	public void moduleClick() throws Throwable {
		log.info("--- Clicking Module ---");
		manufacturer.click_titleMasters();
		log.info("Clicked on Masters title");
		ScreenshotUtil.capture();
		manufacturer.waitForLoading();
		manufacturer.masterClick(MASTER_MODULE);
		log.info("Clicked on Master Module: {}", MASTER_MODULE);
		manufacturer.waitForLoading();
		ScreenshotUtil.capture();
		manufacturer.masterClick(SUB_MASTER_MODULE);
		log.info("Clicked on Sub Master Module: {}", SUB_MASTER_MODULE);
		manufacturer.waitForLoading();
		ScreenshotUtil.capture();
	}

	@Test(groups = { "Creation" })
	public void Creation_Of_Manufacturer() throws Throwable {
		log.info("--- Navigating to Create Manufacturer Screen ---");
		manufacturer.Create();
		log.info("Clicked on Create button");
		manufacturer.waitForLoading();
		ScreenshotUtil.capture();
		ScreenshotUtil.nextStep();
		log.info("--- Creating Manufacturer: {} ---", MANUFACTURER_NAME);
		currentManufacturerName = MANUFACTURER_NAME;
		manufacturer.manufacturerName(MANUFACTURER_NAME);
		manufacturer.manufacturerAddress(MANUFACTURER_ADDRESS);
		manufacturer.manufacturerState(MANUFACTURER_STATE);
		manufacturer.manufacturerCity(MANUFACTURER_CITY);
		manufacturer.manufacturerPincode(MANUFACTURER_PINCODE);
		manufacturer.selManufacturerRegion(MANUFACTURER_REGION);

		manufacturer.clickSubmit();
		log.info("Clicked Submit");
		ScreenshotUtil.capture();

		manufacturer.authenticate(manufacturer.currentPassword);
		String authToast = manufacturer.waitForToast();
		manufacturer.waitForLoading();
		ScreenshotUtil.capture();
		sa.assertEquals(authToast, "Supplier created successfully", "Created Toaster message",
				"Creation failed with message: " + authToast);
		ScreenshotUtil.nextStep();
		ScreenshotUtil.capture();
		sa.assertAll();

	}

	@Test(groups = { "ClickActions" })
	public void Click_Actions_For_Review() throws Throwable {

		if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
			manufacturer.switchUser(USERNAME2, PASSWORD2, PC_DB_NAME, MASTER_MODULE, SUB_MASTER_MODULE);
		}
		log.info("--- Attempting to open Actions Menu for: {} ---", currentManufacturerName);
		ScreenshotUtil.nextStep();
		manufacturer.clickActions(currentManufacturerName);
		log.info("Successfully opened Actions menu for {}", currentManufacturerName);
		ScreenshotUtil.capture();

	}

	@Test(groups = { "ClickView" })
	public void Click_View() throws Throwable {
		if (MANUFACTURER_VIEW_ACTION.equalsIgnoreCase("yes")) {
			log.info("--- Viewing Manufacturer: {} ---", currentManufacturerName);
			ScreenshotUtil.nextStep();
			manufacturer.clickView(currentManufacturerName);
			log.info("View screen opened");
			manufacturer.waitForLoading();
			ScreenshotUtil.capture();
			manufacturer.clickBack();
			log.info("Clicked Back button");
			manufacturer.waitForLoading();
			ScreenshotUtil.capture();
		} else {
			log.info("View step skipped based on configuration");
		}
	}

	@Test(groups = { "manufacturerReviewReturn_manufacturerEdit" })
	public void manufacturer_Review_Return_and_Edit() throws Throwable {

		if (MANUFACTURER_RETURN_ACTION_IN_REVIEW.equalsIgnoreCase("yes")) {

			if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
				manufacturer.switchUser(USERNAME2, PASSWORD2, PC_DB_NAME, MASTER_MODULE, SUB_MASTER_MODULE);
			}
			log.info("--- Initiating Review Return Flow for: {} ---", currentManufacturerName);
			log.info("Opening actions menu to access Review/Return");
			manufacturer.clickActions(currentManufacturerName);
			ScreenshotUtil.nextStep();
			log.info("Clicking Review to trigger return dialog");
			manufacturer.clickReview();
			manufacturer.waitForLoading();
			ScreenshotUtil.capture();
			manufacturer.enterRemarks(REVIEW_RETURN_REMARKS);
			log.info("Entered Return remarks: {}", REVIEW_RETURN_REMARKS);
			ScreenshotUtil.capture();
			manufacturer.clickReturn();
			log.info("Clicked Return button");
			ScreenshotUtil.capture();
			manufacturer.authenticate(manufacturer.currentPassword);
			String returnToast = manufacturer.waitForToast();
			manufacturer.waitForLoading();
			sa.assertEquals(returnToast, "Supplier returned successfully", "Returned toaster message",
					returnToast);
			sa.assertAll();

			if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
				manufacturer.switchUser(USERNAME1, PASSWORD1, PC_DB_NAME, MASTER_MODULE, SUB_MASTER_MODULE);
			}

			log.info("Opening Edit screen (After Review Return)");

			manufacturer.clickEdit(currentManufacturerName);
			manufacturer.waitForLoading();
			ScreenshotUtil.capture();
			ScreenshotUtil.nextStep();
			if (EDIT_MANUFACTURER_IN_REVIEW_RETURN != null && !EDIT_MANUFACTURER_IN_REVIEW_RETURN.trim().isEmpty()) {
				log.info("Updating Name to: {}", EDIT_MANUFACTURER_IN_REVIEW_RETURN);
				manufacturer.manufacturerName(EDIT_MANUFACTURER_IN_REVIEW_RETURN);
				currentManufacturerName = EDIT_MANUFACTURER_IN_REVIEW_RETURN;
			}

			manufacturer.clickUpdate();
			log.info("Clicked Update");
			ScreenshotUtil.capture();
			manufacturer.authenticate(manufacturer.currentPassword);
			String editToast = manufacturer.waitForToast();
			manufacturer.waitForLoading();
			sa.assertEquals(editToast, "Supplier updated successfully", "Updated toaster messege", editToast);
			sa.assertAll();

		} else {
			log.info("Manufacturer Review Return and Edit skipped based on configuration");
		}
	}

	@Test(groups = { "manufacturerReview" })
	public void manufacturerReview() throws Throwable {

		if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
			manufacturer.switchUser(USERNAME2, PASSWORD2, PC_DB_NAME, MASTER_MODULE, SUB_MASTER_MODULE);
		}
		log.info("--- Initiating Review Flow for: {} ---", currentManufacturerName);
		log.info("Opening actions menu to access Review/Return");
		manufacturer.clickActions(currentManufacturerName);
		ScreenshotUtil.capture();
		log.info("Clicking Review to trigger return dialog");
		manufacturer.clickReview();
		manufacturer.waitForLoading();
		ScreenshotUtil.capture();
		manufacturer.enterRemarks(REVIEW_REMARKS);
		log.info("Entered Review remarks: {}", REVIEW_REMARKS);
		ScreenshotUtil.capture();
		manufacturer.clickReview();
		log.info("Clicked Review button");
		ScreenshotUtil.capture();
		manufacturer.authenticate(manufacturer.currentPassword);
		String reviewToast = manufacturer.waitForToast();
		manufacturer.waitForLoading();
		sa.assertEquals(reviewToast, "Supplier reviewed successfully", "Review toaster message", reviewToast);
		sa.assertAll();

	}

	@Test(groups = { "manufacturerApproveReturn_manufacturerEdit_manufacturerReview" })
	public void manufacturer_Approve_Return_and_Edit_and_Review() throws Throwable {

		if (MANUFACTURER_RETURN_ACTION_IN_APPROVE.equalsIgnoreCase("yes")) {

			if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
				manufacturer.switchUser(USERNAME3, PASSWORD3, PC_DB_NAME, MASTER_MODULE, SUB_MASTER_MODULE);
			}
			log.info("--- Initiating Approve Return Flow for: {} ---", currentManufacturerName);
			log.info("Opening actions menu to access Approve/Return");
			ScreenshotUtil.nextStep();
			manufacturer.clickActions(currentManufacturerName);
			ScreenshotUtil.capture();
			ScreenshotUtil.nextStep();
			log.info("Clicking Approve to trigger return dialog");
			manufacturer.clickApprove();
			manufacturer.waitForLoading();
			ScreenshotUtil.capture();
			manufacturer.enterRemarks(APPROVE_RETURN_REMARKS);
			log.info("Entered Return remarks: {}", APPROVE_RETURN_REMARKS);
			ScreenshotUtil.capture();
			manufacturer.clickReturn();
			log.info("Clicked Return button");
			ScreenshotUtil.capture();
			manufacturer.authenticate(manufacturer.currentPassword);
			String returnToast = manufacturer.waitForToast();
			manufacturer.waitForLoading();
			sa.assertEquals(returnToast, "Supplier returned successfully", "Returned toaster message",
					returnToast);
			sa.assertAll();

			if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
				manufacturer.switchUser(USERNAME1, PASSWORD1, PC_DB_NAME, MASTER_MODULE, SUB_MASTER_MODULE);
			}

			log.info("Opening Edit screen (After Return)");

			manufacturer.clickEdit(currentManufacturerName);
			manufacturer.waitForLoading();
			ScreenshotUtil.capture();

			if (EDIT_MANUFACTURER_IN_APPROVE_RETURN != null && !EDIT_MANUFACTURER_IN_APPROVE_RETURN.trim().isEmpty()) {
				log.info("Updating Name to: {}", EDIT_MANUFACTURER_IN_APPROVE_RETURN);
				manufacturer.manufacturerName(EDIT_MANUFACTURER_IN_APPROVE_RETURN);
				currentManufacturerName = EDIT_MANUFACTURER_IN_APPROVE_RETURN;
			}

			manufacturer.clickUpdate();
			log.info("Clicked Update");
			ScreenshotUtil.capture();
			manufacturer.authenticate(manufacturer.currentPassword);
			String editToast = manufacturer.waitForToast();
			manufacturer.waitForLoading();
			sa.assertEquals(editToast, "Supplier updated successfully", "Updated toaster messege", editToast);
			sa.assertAll();

		} else {
			log.info("Manufacturer Approve Return and Edit and Review skipped based on configuration");
		}
	}

	@Test(groups = { "manufacturerApprove" })
	public void manufacturerApprove() throws Throwable {

		if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
			log.info("--- Approving Manufacturer: {} ---", currentManufacturerName);
			manufacturer.switchUser(USERNAME3, PASSWORD3, PC_DB_NAME, MASTER_MODULE, SUB_MASTER_MODULE);
		}
		log.info("--- Performing Final Approval for: {} ---", currentManufacturerName);

		manufacturer.waitForLoading();
		log.info("Opening actions menu for approval");
		manufacturer.clickActions(currentManufacturerName);
		ScreenshotUtil.capture();
		log.info("Clicking Approve button in the list");
		manufacturer.clickApprove();
		ScreenshotUtil.capture();
		manufacturer.enterRemarks(APPROVE_REMARKS);
		log.info("Entered Approve remarks: {}", APPROVE_REMARKS);
		ScreenshotUtil.capture();
		manufacturer.clickApprove();
		log.info("Submitted Approval");
		ScreenshotUtil.capture();
		manufacturer.authenticate(manufacturer.currentPassword);
		String approveToast = manufacturer.waitForToast();
		manufacturer.waitForLoading();
		sa.assertEquals(approveToast, "Supplier approved successfully", "Approved toaster messege", approveToast);
		sa.assertAll();

	}

	@Test(groups = { "Logout" })
	public void Logout() throws Throwable {
		log.info("--- Executing Final Logout ---");
		ScreenshotUtil.nextStep();
		manufacturer.logout();
		log.info("Clicked logout button");
		manufacturer.waitForToast();
		manufacturer.waitForLoading();
		ScreenshotUtil.capture();
		log.info("--- Manufacturer Test Case Execution Finished ---");
	}

	@Test(groups = { "DB Back" })
	public void Database_Backup() {
		log.info("--- Initiating Post-Test Database Backup ---");

		String backupFolderName = (SCRIPT_NUMBER == null || SCRIPT_NUMBER.trim().isEmpty()) ? CURRENT_CONFIG_NAME
				: SCRIPT_NUMBER;

		log.info("Backup folder name determined: {}", backupFolderName);

		DatabaseBackupUtil.backupPostgres(backupFolderName, PC_DB_NAME, "postgres", "root", "localhost", "5432");
		DatabaseBackupUtil.backupPostgres(backupFolderName, MASTER_DB_NAME, "postgres", "root", "localhost", "5432");
		DatabaseBackupUtil.backupPostgres(backupFolderName, MM_DB_NAME, "postgres", "root", "localhost", "5432");
	}

}
