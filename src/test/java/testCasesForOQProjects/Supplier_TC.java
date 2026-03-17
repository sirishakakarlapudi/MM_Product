package testCasesForOQProjects;

import static configData.SupplierData.*;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageObjects.Supplier;
import testBase.BaseClass;
import utilities.DatabaseBackupUtil;
import utilities.ScreenshotUtil;
import utilities.SoftAssertionUtil;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class Supplier_TC extends BaseClass {
	Supplier supplier;
	String currentSupplierName;
	SoftAssertionUtil sa;

	@BeforeClass
	@Parameters({ "configFile" })
	public void setUp(@Optional("supplier.properties") String configFile) throws Exception {
		log.info("--- Starting Supplier Test Case Setup with config: {} ---", configFile);

		// Load the dynamic property file
		configData.SupplierData.loadProperties(configFile);

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
		supplier = new Supplier(driver);
		supplier.setTableHeaders(TABLE_HEADERS);
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
		supplier.waitForElementToVisible(supplier.getSearchBox());
		Assert.assertTrue(supplier.getSearchBox().isDisplayed(), "Search box is not visible!");
		log.info("Searching for Application URL: {}", APP_URL);
		supplier.searchBox(APP_URL);
		supplier.waitForLoading();
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
			supplier.login(USERNAME, PASSWORD, PC_DB_NAME);
		} else {
			supplier.login(USERNAME1, PASSWORD1, PC_DB_NAME);
		}
	}

	@Test(groups = { "moduleselect" })
	public void moduleClick() throws Throwable {
		log.info("--- Clicking Module ---");
		supplier.click_titleMasters();
		log.info("Clicked on Masters title");
		ScreenshotUtil.capture();
		supplier.waitForLoading();
		supplier.masterClick(MASTER_MODULE);
		log.info("Clicked on Master Module: {}", MASTER_MODULE);
		supplier.waitForLoading();
		ScreenshotUtil.capture();
		supplier.masterClick(SUB_MASTER_MODULE);
		log.info("Clicked on Sub Master Module: {}", SUB_MASTER_MODULE);
		supplier.waitForLoading();
		ScreenshotUtil.capture();
	}

	@Test(groups = { "Creation" })
	public void Creation_Of_Supplier() throws Throwable {
		log.info("--- Navigating to Create Supplier Screen ---");
		supplier.Create();
		log.info("Clicked on Create button");
		supplier.waitForLoading();
		ScreenshotUtil.capture();
		ScreenshotUtil.nextStep();
		log.info("--- Creating Supplier: {} ---", SUPPLIER_NAME);
		currentSupplierName = SUPPLIER_NAME;
		supplier.supplierName(SUPPLIER_NAME);
		supplier.supplierAddress(SUPPLIER_ADDRESS);
		supplier.supplierState(SUPPLIER_STATE);
		supplier.supplierCity(SUPPLIER_CITY);
		supplier.supplierPincode(SUPPLIER_PINCODE);
		supplier.selSupplierRegion(SUPPLIER_REGION);

		supplier.clickSubmit();
		log.info("Clicked Submit");
		ScreenshotUtil.capture();

		supplier.authenticate(supplier.currentPassword);
		String authToast = supplier.waitForToast();
		supplier.waitForLoading();
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
			supplier.switchUser(USERNAME2, PASSWORD2, PC_DB_NAME, MASTER_MODULE, SUB_MASTER_MODULE);
		}
		log.info("--- Attempting to open Actions Menu for: {} ---", currentSupplierName);
		ScreenshotUtil.nextStep();
		supplier.clickActions(currentSupplierName);
		log.info("Successfully opened Actions menu for {}", currentSupplierName);
		ScreenshotUtil.capture();

	}

	@Test(groups = { "ClickView" })
	public void Click_View() throws Throwable {
		if (SUPPLIER_VIEW_ACTION.equalsIgnoreCase("yes")) {
			log.info("--- Viewing Supplier: {} ---", currentSupplierName);
			ScreenshotUtil.nextStep();
			supplier.clickView(currentSupplierName);
			log.info("View screen opened");
			supplier.waitForLoading();
			ScreenshotUtil.capture();
			supplier.clickBack();
			log.info("Clicked Back button");
			supplier.waitForLoading();
			ScreenshotUtil.capture();
		} else {
			log.info("View step skipped based on configuration");
		}
	}

	@Test(groups = { "supplierReviewReturn_supplierEdit" })
	public void supplier_Review_Return_and_Edit() throws Throwable {

		if (SUPPLIER_RETURN_ACTION_IN_REVIEW.equalsIgnoreCase("yes")) {

			if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
				supplier.switchUser(USERNAME2, PASSWORD2, PC_DB_NAME, MASTER_MODULE, SUB_MASTER_MODULE);
			}
			log.info("--- Initiating Review Return Flow for: {} ---", currentSupplierName);
			log.info("Opening actions menu to access Review/Return");
			supplier.clickActions(currentSupplierName);
			ScreenshotUtil.nextStep();
			log.info("Clicking Review to trigger return dialog");
			supplier.clickReview();
			supplier.waitForLoading();
			ScreenshotUtil.capture();
			supplier.enterRemarks(REVIEW_RETURN_REMARKS);
			log.info("Entered Return remarks: {}", REVIEW_RETURN_REMARKS);
			ScreenshotUtil.capture();
			supplier.clickReturn();
			log.info("Clicked Return button");
			ScreenshotUtil.capture();
			supplier.authenticate(supplier.currentPassword);
			String returnToast = supplier.waitForToast();
			supplier.waitForLoading();
			sa.assertEquals(returnToast, "Supplier returned successfully", "Returned toaster message",
					returnToast);
			sa.assertAll();

			if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
				supplier.switchUser(USERNAME1, PASSWORD1, PC_DB_NAME, MASTER_MODULE, SUB_MASTER_MODULE);
			}

			log.info("Opening Edit screen (After Review Return)");

			supplier.clickEdit(currentSupplierName);
			supplier.waitForLoading();
			ScreenshotUtil.capture();
			ScreenshotUtil.nextStep();
			if (EDIT_SUPPLIER_IN_REVIEW_RETURN != null && !EDIT_SUPPLIER_IN_REVIEW_RETURN.trim().isEmpty()) {
				log.info("Updating Name to: {}", EDIT_SUPPLIER_IN_REVIEW_RETURN);
				supplier.supplierName(EDIT_SUPPLIER_IN_REVIEW_RETURN);
				currentSupplierName = EDIT_SUPPLIER_IN_REVIEW_RETURN;
			}

			supplier.clickUpdate();
			log.info("Clicked Update");
			ScreenshotUtil.capture();
			supplier.authenticate(supplier.currentPassword);
			String editToast = supplier.waitForToast();
			supplier.waitForLoading();
			sa.assertEquals(editToast, "Supplier updated successfully", "Updated toaster messege", editToast);
			sa.assertAll();

		} else {
			log.info("Supplier Review Return and Edit skipped based on configuration");
		}
	}

	@Test(groups = { "supplierReview" })
	public void supplierReview() throws Throwable {

		if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
			supplier.switchUser(USERNAME2, PASSWORD2, PC_DB_NAME, MASTER_MODULE, SUB_MASTER_MODULE);
		}
		log.info("--- Initiating Review Flow for: {} ---", currentSupplierName);
		log.info("Opening actions menu to access Review/Return");
		supplier.clickActions(currentSupplierName);
		ScreenshotUtil.capture();
		log.info("Clicking Review to trigger return dialog");
		supplier.clickReview();
		supplier.waitForLoading();
		ScreenshotUtil.capture();
		supplier.enterRemarks(REVIEW_REMARKS);
		log.info("Entered Review remarks: {}", REVIEW_REMARKS);
		ScreenshotUtil.capture();
		supplier.clickReview();
		log.info("Clicked Review button");
		ScreenshotUtil.capture();
		supplier.authenticate(supplier.currentPassword);
		String reviewToast = supplier.waitForToast();
		supplier.waitForLoading();
		sa.assertEquals(reviewToast, "Supplier reviewed successfully", "Review toaster message", reviewToast);
		sa.assertAll();

	}

	@Test(groups = { "supplierApproveReturn_supplierEdit_supplierReview" })
	public void supplier_Approve_Return_and_Edit_and_Review() throws Throwable {

		if (SUPPLIER_RETURN_ACTION_IN_APPROVE.equalsIgnoreCase("yes")) {

			if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
				supplier.switchUser(USERNAME3, PASSWORD3, PC_DB_NAME, MASTER_MODULE, SUB_MASTER_MODULE);
			}
			log.info("--- Initiating Approve Return Flow for: {} ---", currentSupplierName);
			log.info("Opening actions menu to access Approve/Return");
			ScreenshotUtil.nextStep();
			supplier.clickActions(currentSupplierName);
			ScreenshotUtil.capture();
			ScreenshotUtil.nextStep();
			log.info("Clicking Approve to trigger return dialog");
			supplier.clickApprove();
			supplier.waitForLoading();
			ScreenshotUtil.capture();
			supplier.enterRemarks(APPROVE_RETURN_REMARKS);
			log.info("Entered Return remarks: {}", APPROVE_RETURN_REMARKS);
			ScreenshotUtil.capture();
			supplier.clickReturn();
			log.info("Clicked Return button");
			ScreenshotUtil.capture();
			supplier.authenticate(supplier.currentPassword);
			String returnToast = supplier.waitForToast();
			supplier.waitForLoading();
			sa.assertEquals(returnToast, "Supplier returned successfully", "Returned toaster message",
					returnToast);
			sa.assertAll();

			if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
				supplier.switchUser(USERNAME1, PASSWORD1, PC_DB_NAME, MASTER_MODULE, SUB_MASTER_MODULE);
			}

			log.info("Opening Edit screen (After Return)");

			supplier.clickEdit(currentSupplierName);
			supplier.waitForLoading();
			ScreenshotUtil.capture();

			if (EDIT_SUPPLIER_IN_APPROVE_RETURN != null && !EDIT_SUPPLIER_IN_APPROVE_RETURN.trim().isEmpty()) {
				log.info("Updating Name to: {}", EDIT_SUPPLIER_IN_APPROVE_RETURN);
				supplier.supplierName(EDIT_SUPPLIER_IN_APPROVE_RETURN);
				currentSupplierName = EDIT_SUPPLIER_IN_APPROVE_RETURN;
			}

			supplier.clickUpdate();
			log.info("Clicked Update");
			ScreenshotUtil.capture();
			supplier.authenticate(supplier.currentPassword);
			String editToast = supplier.waitForToast();
			supplier.waitForLoading();
			sa.assertEquals(editToast, "Supplier updated successfully", "Updated toaster messege", editToast);
			sa.assertAll();

		} else {
			log.info("Supplier Approve Return and Edit and Review skipped based on configuration");
		}
	}

	@Test(groups = { "supplierApprove" })
	public void supplierApprove() throws Throwable {

		if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
			log.info("--- Approving Supplier: {} ---", currentSupplierName);
			supplier.switchUser(USERNAME3, PASSWORD3, PC_DB_NAME, MASTER_MODULE, SUB_MASTER_MODULE);
		}
		log.info("--- Performing Final Approval for: {} ---", currentSupplierName);

		supplier.waitForLoading();
		log.info("Opening actions menu for approval");
		supplier.clickActions(currentSupplierName);
		ScreenshotUtil.capture();
		log.info("Clicking Approve button in the list");
		supplier.clickApprove();
		ScreenshotUtil.capture();
		supplier.enterRemarks(APPROVE_REMARKS);
		log.info("Entered Approve remarks: {}", APPROVE_REMARKS);
		ScreenshotUtil.capture();
		supplier.clickApprove();
		log.info("Submitted Approval");
		ScreenshotUtil.capture();
		supplier.authenticate(supplier.currentPassword);
		String approveToast = supplier.waitForToast();
		supplier.waitForLoading();
		sa.assertEquals(approveToast, "Supplier approved successfully", "Approved toaster messege", approveToast);
		sa.assertAll();

	}

	@Test(groups = { "Logout" })
	public void Logout() throws Throwable {
		log.info("--- Executing Final Logout ---");
		ScreenshotUtil.nextStep();
		supplier.logout();
		log.info("Clicked logout button");
		supplier.waitForToast();
		supplier.waitForLoading();
		ScreenshotUtil.capture();
		log.info("--- Supplier Test Case Execution Finished ---");
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
