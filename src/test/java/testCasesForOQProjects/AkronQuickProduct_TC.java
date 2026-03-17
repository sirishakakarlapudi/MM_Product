
package testCasesForOQProjects;

import static configData.AkronQuickProductData.*;


import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageObjects.AkronQuickProduct;
import testBase.BaseClass;
import utilities.DatabaseBackupUtil;
import utilities.ScreenshotUtil;
import utilities.SoftAssertionUtil;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class AkronQuickProduct_TC extends BaseClass {
	AkronQuickProduct product;
	String currentProductName;
	SoftAssertionUtil sa;

	@BeforeClass
	@Parameters({ "configFile" })
	public void setUp(@Optional("akronquickproduct.properties") String configFile) throws Exception {
		log.info("--- Starting AkronQuickProduct Test Case Setup with config: {} ---", configFile);

		// Load the dynamic property file
		configData.AkronQuickProductData.loadProperties(configFile);

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
		product = new AkronQuickProduct(driver);
		product.setTableHeaders(TABLE_HEADERS);
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
		product.waitForElementToVisible(product.getSearchBox());
		Assert.assertTrue(product.getSearchBox().isDisplayed(), "Search box is not visible!");
		log.info("Searching for Application URL: {}", APP_URL);
		product.searchBox(APP_URL);
		product.waitForLoading();
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
			product.login(USERNAME, PASSWORD, PC_DB_NAME);
		} else {
			product.login(USERNAME1, PASSWORD1, PC_DB_NAME);
		}
	}

	@Test(groups = { "moduleselect" }, priority = 4)
	public void moduleClick() throws Throwable {
		log.info("--- Clicking Module ---");
		product.click_titleMasters();
		log.info("Clicked on Masters title");
		ScreenshotUtil.capture();
		product.waitForLoading();
		product.masterClick(MASTER_MODULE);
		log.info("Clicked on Master Module: {}", MASTER_MODULE);
		product.masterClick(SUB_MASTER_MODULE);
		log.info("Clicked on Sub Master Module: {}", SUB_MASTER_MODULE);
		product.waitForLoading();
		ScreenshotUtil.capture();
	}

	@Test(groups = { "Creation" }, priority = 5)
	public void Creation_Of_Product() throws Throwable {
		log.info("--- Navigating to Create Product Screen ---");
		product.Create();
		log.info("Clicked on Create button");
		product.waitForLoading();
		ScreenshotUtil.capture();
		ScreenshotUtil.nextStep();
		log.info("--- Creating Product Group: {} ---", PRODUCT_NAME);
		currentProductName = PRODUCT_NAME; // Initialize with base name
		product.productName(PRODUCT_NAME);
		product.productCode(PRODUCT_CODE);
		product.productDesc(PRODUCT_DESCRIPTION);
		product.selStorageLocation(STORAGE_LOCATION);
		product.storageCondition(STORAGE_CONDITION);
		product.checkSamplingActivity(SAMPLING_ACTIVITY);

		if ("Yes".equalsIgnoreCase(SAMPLING_ACTIVITY)) {
			product.checkCleaningAgent(CLEANING_AGENT);
		}

		String[] ndcNumbers = NDC_NUMBER.split(",");
		String[] shortCodes = SHORT_CODE.split(",");
		String[] packSizes = PACK_SIZE.split(",");
		String[] ndcDescriptions = NDC_DESCRIPTION.split(",");
		String[] uoms = UOM.split(",");

		for (int i = 0; i < ndcNumbers.length; i++) {

			product.ndcNumber(ndcNumbers[i].trim());
			product.shortCode(shortCodes[i].trim());

			String uomToUse = (uoms.length == 1) ? uoms[0].trim() : uoms[i].trim();
			product.selUOM(uomToUse);

			product.packSize(packSizes[i].trim());

			if (ndcDescriptions.length > i && !ndcDescriptions[i].trim().isEmpty()) {
				product.ndcDesc(ndcDescriptions[i].trim());
			}

			if (i < ndcNumbers.length - 1) {
				product.enterRow();
			}
		}

		product.clickSubmit();
		log.info("Clicked Submit");
		ScreenshotUtil.capture();

		product.authenticate(product.currentPassword);
		String authToast = product.waitForToast();
		product.waitForLoading();
		ScreenshotUtil.capture();
		sa.assertEquals(authToast, "Quick Product created successfully", "Created Toaster message",
				"Creation failed with message: " + authToast);
		ScreenshotUtil.nextStep();
		ScreenshotUtil.capture();
		sa.assertAll();

	}

	@Test(groups = { "ClickActions" }, priority = 6)
	public void Click_Actions_For_Review() throws Throwable {

		if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
			product.switchUser(USERNAME2, PASSWORD2, PC_DB_NAME, MASTER_MODULE, SUB_MASTER_MODULE);
		}
		log.info("--- Attempting to open Actions Menu for: {} ---", currentProductName);
		ScreenshotUtil.nextStep();
		product.clickActions(currentProductName);
		log.info("Successfully opened Actions menu for {}", currentProductName);
		ScreenshotUtil.capture();

	}

	@Test(groups = { "ClickView" }, priority = 7)
	public void Click_View() throws Throwable {
		if (PRODUCT_VIEW_ACTION.equalsIgnoreCase("yes")) {
			log.info("--- Viewing Quick Product: {} ---", currentProductName);
			ScreenshotUtil.nextStep();
			product.clickView(currentProductName);
			log.info("View screen opened");
			product.waitForLoading();
			ScreenshotUtil.capture();
			product.clickBack();
			log.info("Clicked Back button");
			product.waitForLoading();
			ScreenshotUtil.capture();
		} else {
			log.info("View step skipped based on configuration");
		}
	}

	@Test(groups = { "productReviewReturn_productEdit" }, priority = 8)
	public void product_Review_Return_and_Edit() throws Throwable {

		if (PRODUCT_RETURN_ACTION_IN_REVIEW.equalsIgnoreCase("yes")) {

			if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
				product.switchUser(USERNAME2, PASSWORD2, PC_DB_NAME, MASTER_MODULE, SUB_MASTER_MODULE);
			}
			log.info("--- Initiating Review Return Flow for: {} ---", currentProductName);
			log.info("Opening actions menu to access Review/Return");
			product.clickActions(currentProductName);
			ScreenshotUtil.nextStep();
			log.info("Clicking Review to trigger return dialog");
			product.clickReview();
			product.waitForLoading();
			ScreenshotUtil.capture();
			product.enterRemarks(REVIEW_RETURN_REMARKS);
			log.info("Entered Return remarks: {}", REVIEW_RETURN_REMARKS);
			ScreenshotUtil.capture();
			product.clickReturn();
			log.info("Clicked Return button");
			ScreenshotUtil.capture();
			product.authenticate(product.currentPassword);
			String returnToast = product.waitForToast();
			product.waitForLoading();
			sa.assertEquals(returnToast, "Quick Product returned successfully", "Returned toaster message",
					returnToast);
			sa.assertAll();

			if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
				product.switchUser(USERNAME1, PASSWORD1, PC_DB_NAME, MASTER_MODULE, SUB_MASTER_MODULE);
			}

			// This part will now execute for BOTH single and multiple users

			log.info("Opening Edit screen (After Review Return)");

			product.clickEdit(currentProductName);
			product.waitForLoading();
			ScreenshotUtil.capture();
			ScreenshotUtil.nextStep();
			if (EDIT_PRODUCT_IN_REVIEW_RETURN != null && !EDIT_PRODUCT_IN_REVIEW_RETURN.trim().isEmpty()) {
				log.info("Updating Name to: {}", EDIT_PRODUCT_IN_REVIEW_RETURN);
				product.productName(EDIT_PRODUCT_IN_REVIEW_RETURN);
				currentProductName = EDIT_PRODUCT_IN_REVIEW_RETURN;
			}

			product.clickUpdate();
			log.info("Clicked Update");
			ScreenshotUtil.capture();
			product.authenticate(product.currentPassword);
			String editToast = product.waitForToast();
			product.waitForLoading();
			sa.assertEquals(editToast, "Quick Product updated successfully", "Updated toaster messege", editToast);
			sa.assertAll();

		} else {
			log.info("Product Review Return and Edit skipped based on configuration");
		}
	}

	@Test(groups = { "productReview" }, priority = 9)
	public void productReview() throws Throwable {

		if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
			product.switchUser(USERNAME2, PASSWORD2, PC_DB_NAME, MASTER_MODULE, SUB_MASTER_MODULE);
		}
		log.info("--- Initiating Review Flow for: {} ---", currentProductName);
		log.info("Opening actions menu to access Review/Return");
		product.clickActions(currentProductName);
		ScreenshotUtil.capture();
		log.info("Clicking Review to trigger return dialog");
		product.clickReview();
		product.waitForLoading();
		ScreenshotUtil.capture();
		product.enterRemarks(REVIEW_REMARKS);
		log.info("Entered Review remarks: {}", REVIEW_REMARKS);
		ScreenshotUtil.capture();
		product.clickReview();
		log.info("Clicked Review button");
		ScreenshotUtil.capture();
		product.authenticate(product.currentPassword);
		String reviewToast = product.waitForToast();
		product.waitForLoading();
		sa.assertEquals(reviewToast, "Quick Product reviewed successfully", "Review toaster message", reviewToast);
		sa.assertAll();

	}

	@Test(groups = { "productApproveReturn_productEdit_productReview" }, priority = 10)
	public void product_Approve_Return_and_Edit_and_Review() throws Throwable {

		if (PRODUCT_RETURN_ACTION_IN_APPROVE.equalsIgnoreCase("yes")) {

			if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
				product.switchUser(USERNAME3, PASSWORD3, PC_DB_NAME, MASTER_MODULE, SUB_MASTER_MODULE);
			}
			log.info("--- Initiating Approve Return Flow for: {} ---", currentProductName);
			log.info("Opening actions menu to access Approve/Return");
			ScreenshotUtil.nextStep();
			product.clickActions(currentProductName);
			ScreenshotUtil.capture();
			ScreenshotUtil.nextStep();
			log.info("Clicking Approve to trigger return dialog");
			product.clickApprove();
			product.waitForLoading();
			ScreenshotUtil.capture();
			product.enterRemarks(APPROVE_RETURN_REMARKS);
			log.info("Entered Return remarks: {}", APPROVE_RETURN_REMARKS);
			ScreenshotUtil.capture();
			product.clickReturn();
			log.info("Clicked Return button");
			ScreenshotUtil.capture();
			product.authenticate(product.currentPassword);
			String returnToast = product.waitForToast();
			product.waitForLoading();
			sa.assertEquals(returnToast, "Quick Product returned successfully", "Returned toaster message",
					returnToast);
			sa.assertAll();

			if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
				product.switchUser(USERNAME1, PASSWORD1, PC_DB_NAME, MASTER_MODULE, SUB_MASTER_MODULE);
			}

			// This part will now execute for BOTH single and multiple users

			log.info("Opening Edit screen (After Return)");

			product.clickEdit(currentProductName);
			product.waitForLoading();
			ScreenshotUtil.capture();

			if (EDIT_PRODUCT_IN_APPROVE_RETURN != null && !EDIT_PRODUCT_IN_APPROVE_RETURN.trim().isEmpty()) {
				log.info("Updating Name to: {}", EDIT_PRODUCT_IN_APPROVE_RETURN);
				product.productName(EDIT_PRODUCT_IN_APPROVE_RETURN);
				currentProductName = EDIT_PRODUCT_IN_APPROVE_RETURN;
			}

			product.clickUpdate();
			log.info("Clicked Update");
			ScreenshotUtil.capture();
			product.authenticate(product.currentPassword);
			String editToast = product.waitForToast();
			product.waitForLoading();
			sa.assertEquals(editToast, "Quick Product updated successfully", "Updated toaster messege", editToast);

			if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
				product.switchUser(USERNAME2, PASSWORD2, PC_DB_NAME, MASTER_MODULE, SUB_MASTER_MODULE);
			}
			log.info("--- Initiating Review Flow for: {} ---", currentProductName);
			log.info("Opening actions menu to access Review/Return");
			product.clickActions(currentProductName);
			ScreenshotUtil.capture();
			log.info("Clicking Review to trigger return dialog");
			product.clickReview();
			product.waitForLoading();
			ScreenshotUtil.capture();
			product.enterRemarks(REVIEW_REMARKS);
			log.info("Entered Review remarks: {}", REVIEW_REMARKS);
			ScreenshotUtil.capture();
			product.clickReview();
			log.info("Clicked Review button");
			ScreenshotUtil.capture();
			product.authenticate(product.currentPassword);
			String reviewToast = product.waitForToast();
			product.waitForLoading();
			sa.assertEquals(reviewToast, "Quick Product reviewed successfully", "Review toaster message", reviewToast);
			sa.assertAll();

		} else {
			log.info("Product Approve Return and Edit and Review skipped based on configuration");
		}
	}

	@Test(groups = { "productApprove" }, priority = 11)
	public void productApprove() throws Throwable {

		if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
			log.info("--- Approving Product: {} ---", currentProductName);
			product.switchUser(USERNAME3, PASSWORD3, PC_DB_NAME, MASTER_MODULE, SUB_MASTER_MODULE);
		}
		log.info("--- Performing Final Approval for: {} ---", currentProductName);

		product.waitForLoading();
		log.info("Opening actions menu for approval");
		product.clickActions(currentProductName);
		ScreenshotUtil.capture();
		log.info("Clicking Approve button in the list");
		product.clickApprove();
		ScreenshotUtil.capture();
		product.enterRemarks(APPROVE_REMARKS);
		log.info("Entered Approve remarks: {}", APPROVE_REMARKS);
		ScreenshotUtil.capture();
		product.clickApprove();
		log.info("Submitted Approval");
		ScreenshotUtil.capture();
		product.authenticate(product.currentPassword);
		String approveToast = product.waitForToast();
		product.waitForLoading();
		sa.assertEquals(approveToast, "Quick Product approved successfully", "Approved toaster messege", approveToast);
		sa.assertAll();

	}

	@Test(groups = { "duplicationcheck" }, priority = 12)
	public void Duplication_Check() throws Throwable {

		if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
			log.info("--- Approving Facility: {} ---", currentProductName);
			product.switchUser(USERNAME1, PASSWORD1, PC_DB_NAME, MASTER_MODULE, SUB_MASTER_MODULE);
		}
		log.info("--- Checking Duplication Check with Quick Product In Create page: {} ---", currentProductName);
		product.masterClick(SUB_MASTER_MODULE);
		log.info("Clicked on Sub Master Module: {}", SUB_MASTER_MODULE);
		product.waitForLoading();
		ScreenshotUtil.capture();
		product.Create();
		product.productName(currentProductName);
		ScreenshotUtil.capture();// Using currentDeptName to ensure we test with the actual name in the system);
		product.clickProductName();
		product.waitForToast();
		ScreenshotUtil.capture();
		product.waitForLoading();
		sa.assertAll();
	}

	@Test(groups = { "Logout" }, priority = 13)
	public void Logout() throws Throwable {
		log.info("--- Executing Final Logout ---");
		ScreenshotUtil.nextStep();
		product.logout();
		log.info("Clicked logout button");
		product.waitForToast();
		product.waitForLoading();
		ScreenshotUtil.capture();
		log.info("--- Department Test Case Execution Finished ---");
	}

	@Test(groups = { "DB Back" }, priority = 14)
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
