package testCasesForOQProjects;

import static configData.MaterialCategoryData.*;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageObjects.MaterialCategory;
import testBase.BaseClass;
import utilities.DatabaseBackupUtil;
import utilities.ScreenshotUtil;
import utilities.SoftAssertionUtil;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class MaterialCategory_TC extends BaseClass {
	MaterialCategory category;
	String currentCategoryName;
	SoftAssertionUtil sa;

	@BeforeClass
	@Parameters({ "configFile" })
	public void setUp(@Optional("materialcategory.properties") String configFile) throws Exception {
		log.info("--- Starting Material Category Test Case Setup with config: {} ---", configFile);

		// Load the dynamic property file
		configData.MaterialCategoryData.loadProperties(configFile);

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
		category = new MaterialCategory(driver);
		category.setTableHeaders(TABLE_HEADERS);
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
		category.waitForElementToVisible(category.getSearchBox());
		Assert.assertTrue(category.getSearchBox().isDisplayed(), "Search box is not visible!");
		log.info("Searching for Application URL: {}", APP_URL);
		category.searchBox(APP_URL);
		category.waitForLoading();
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
			category.login(USERNAME, PASSWORD, PC_DB_NAME);
		} else {
			category.login(USERNAME1, PASSWORD1, PC_DB_NAME);
		}
	}

	@Test(groups = { "moduleselect" })
	public void moduleClick() throws Throwable {
		log.info("--- Clicking Module ---");
		category.click_titleMasters();
		log.info("Clicked on Masters title");
		ScreenshotUtil.capture();
		category.waitForLoading();
		category.masterClick(MASTER_MODULE);
		log.info("Clicked on Master Module: {}", MASTER_MODULE);
		category.waitForLoading();
		ScreenshotUtil.capture();
		category.masterClick(SUB_MASTER_MODULE);
		log.info("Clicked on Sub Master Module: {}", SUB_MASTER_MODULE);
		category.waitForLoading();
		ScreenshotUtil.capture();
	}

	@Test(groups = { "Creation" })
	public void Creation_Of_Material_Category() throws Throwable {
		log.info("--- Navigating to Create Material Category Screen ---");
		category.Create();
		log.info("Clicked on Create button");
		category.waitForLoading();
		ScreenshotUtil.capture();
		ScreenshotUtil.nextStep();
		log.info("--- Creating Material Category: {} ---", MATERIAL_CATEGORY_NAME);
		currentCategoryName = MATERIAL_CATEGORY_NAME;
		category.materialCategoryName(MATERIAL_CATEGORY_NAME);
		category.selSamplingPlan(SAMPLING_PLAN);
		category.selWeightVerificationPlan(WEIGHT_VERIFICATION_PLAN);

		category.clickSubmit();
		log.info("Clicked Submit");
		ScreenshotUtil.capture();

		category.authenticate(category.currentPassword);
		String authToast = category.waitForToast();
		category.waitForLoading();
		ScreenshotUtil.capture();
		sa.assertEquals(authToast, "Material category created successfully", "Created Toaster message",
				"Creation failed with message: " + authToast);
		ScreenshotUtil.nextStep();
		ScreenshotUtil.capture();
		sa.assertAll();

	}

	@Test(groups = { "ClickActions" })
	public void Click_Actions_For_Review() throws Throwable {

		if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
			category.switchUser(USERNAME2, PASSWORD2, PC_DB_NAME, MASTER_MODULE, SUB_MASTER_MODULE);
		}
		log.info("--- Attempting to open Actions Menu for: {} ---", currentCategoryName);
		ScreenshotUtil.nextStep();
		category.clickActions(currentCategoryName);
		log.info("Successfully opened Actions menu for {}", currentCategoryName);
		ScreenshotUtil.capture();

	}

	@Test(groups = { "ClickView" })
	public void Click_View() throws Throwable {
		if (MATERIAL_CATEGORY_VIEW_ACTION.equalsIgnoreCase("yes")) {
			log.info("--- Viewing Material Category: {} ---", currentCategoryName);
			ScreenshotUtil.nextStep();
			category.clickView(currentCategoryName);
			log.info("View screen opened");
			category.waitForLoading();
			ScreenshotUtil.capture();
			category.clickBack();
			log.info("Clicked Back button");
			category.waitForLoading();
			ScreenshotUtil.capture();
		} else {
			log.info("View step skipped based on configuration");
		}
	}

	@Test(groups = { "categoryReviewReturn_categoryEdit" })
	public void material_Category_Review_Return_and_Edit() throws Throwable {

		if (MATERIAL_CATEGORY_RETURN_ACTION_IN_REVIEW.equalsIgnoreCase("yes")) {

			if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
				category.switchUser(USERNAME2, PASSWORD2, PC_DB_NAME, MASTER_MODULE, SUB_MASTER_MODULE);
			}
			log.info("--- Initiating Review Return Flow for: {} ---", currentCategoryName);
			log.info("Opening actions menu to access Review/Return");
			category.clickActions(currentCategoryName);
			ScreenshotUtil.nextStep();
			log.info("Clicking Review to trigger return dialog");
			category.clickReview();
			category.waitForLoading();
			ScreenshotUtil.capture();
			category.enterRemarks(REVIEW_RETURN_REMARKS);
			log.info("Entered Return remarks: {}", REVIEW_RETURN_REMARKS);
			ScreenshotUtil.capture();
			category.clickReturn();
			log.info("Clicked Return button");
			ScreenshotUtil.capture();
			category.authenticate(category.currentPassword);
			String returnToast = category.waitForToast();
			category.waitForLoading();
			sa.assertEquals(returnToast, "Material category returned successfully", "Returned toaster message",
					returnToast);
			sa.assertAll();

			if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
				category.switchUser(USERNAME1, PASSWORD1, PC_DB_NAME, MASTER_MODULE, SUB_MASTER_MODULE);
			}

			log.info("Opening Edit screen (After Review Return)");

			category.clickEdit(currentCategoryName);
			category.waitForLoading();
			ScreenshotUtil.capture();
			ScreenshotUtil.nextStep();
			if (EDIT_MATERIAL_CATEGORY_IN_REVIEW_RETURN != null
					&& !EDIT_MATERIAL_CATEGORY_IN_REVIEW_RETURN.trim().isEmpty()) {
				log.info("Updating Name to: {}", EDIT_MATERIAL_CATEGORY_IN_REVIEW_RETURN);
				category.materialCategoryName(EDIT_MATERIAL_CATEGORY_IN_REVIEW_RETURN);
				currentCategoryName = EDIT_MATERIAL_CATEGORY_IN_REVIEW_RETURN;
			}

			category.clickUpdate();
			log.info("Clicked Update");
			ScreenshotUtil.capture();
			category.authenticate(category.currentPassword);
			String editToast = category.waitForToast();
			category.waitForLoading();
			sa.assertEquals(editToast, "Material category updated successfully", "Updated toaster messege", editToast);
			sa.assertAll();

		} else {
			log.info("Material Category Review Return and Edit skipped based on configuration");
		}
	}

	@Test(groups = { "categoryReview" })
	public void materialCategoryReview() throws Throwable {

		if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
			category.switchUser(USERNAME2, PASSWORD2, PC_DB_NAME, MASTER_MODULE, SUB_MASTER_MODULE);
		}
		log.info("--- Initiating Review Flow for: {} ---", currentCategoryName);
		log.info("Opening actions menu to access Review/Return");
		category.clickActions(currentCategoryName);
		ScreenshotUtil.capture();
		log.info("Clicking Review to trigger return dialog");
		category.clickReview();
		category.waitForLoading();
		ScreenshotUtil.capture();
		category.enterRemarks(REVIEW_REMARKS);
		log.info("Entered Review remarks: {}", REVIEW_REMARKS);
		ScreenshotUtil.capture();
		category.clickReview();
		log.info("Clicked Review button");
		ScreenshotUtil.capture();
		category.authenticate(category.currentPassword);
		String reviewToast = category.waitForToast();
		category.waitForLoading();
		sa.assertEquals(reviewToast, "Material category reviewed successfully", "Review toaster message", reviewToast);
		sa.assertAll();

	}

	@Test(groups = { "categoryApproveReturn_categoryEdit_categoryReview" })
	public void material_Category_Approve_Return_and_Edit_and_Review() throws Throwable {

		if (MATERIAL_CATEGORY_RETURN_ACTION_IN_APPROVE.equalsIgnoreCase("yes")) {

			if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
				category.switchUser(USERNAME3, PASSWORD3, PC_DB_NAME, MASTER_MODULE, SUB_MASTER_MODULE);
			}
			log.info("--- Initiating Approve Return Flow for: {} ---", currentCategoryName);
			log.info("Opening actions menu to access Approve/Return");
			ScreenshotUtil.nextStep();
			category.clickActions(currentCategoryName);
			ScreenshotUtil.capture();
			ScreenshotUtil.nextStep();
			log.info("Clicking Approve to trigger return dialog");
			category.clickApprove();
			category.waitForLoading();
			ScreenshotUtil.capture();
			category.enterRemarks(APPROVE_RETURN_REMARKS);
			log.info("Entered Return remarks: {}", APPROVE_RETURN_REMARKS);
			ScreenshotUtil.capture();
			category.clickReturn();
			log.info("Clicked Return button");
			ScreenshotUtil.capture();
			category.authenticate(category.currentPassword);
			String returnToast = category.waitForToast();
			category.waitForLoading();
			sa.assertEquals(returnToast, "Material category returned successfully", "Returned toaster message",
					returnToast);
			sa.assertAll();

			if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
				category.switchUser(USERNAME1, PASSWORD1, PC_DB_NAME, MASTER_MODULE, SUB_MASTER_MODULE);
			}

			log.info("Opening Edit screen (After Return)");

			category.clickEdit(currentCategoryName);
			category.waitForLoading();
			ScreenshotUtil.capture();

			if (EDIT_MATERIAL_CATEGORY_IN_APPROVE_RETURN != null
					&& !EDIT_MATERIAL_CATEGORY_IN_APPROVE_RETURN.trim().isEmpty()) {
				log.info("Updating Name to: {}", EDIT_MATERIAL_CATEGORY_IN_APPROVE_RETURN);
				category.materialCategoryName(EDIT_MATERIAL_CATEGORY_IN_APPROVE_RETURN);
				currentCategoryName = EDIT_MATERIAL_CATEGORY_IN_APPROVE_RETURN;
			}

			category.clickUpdate();
			log.info("Clicked Update");
			ScreenshotUtil.capture();
			category.authenticate(category.currentPassword);
			String editToast = category.waitForToast();
			category.waitForLoading();
			sa.assertEquals(editToast, "Material category updated successfully", "Updated toaster messege", editToast);
			sa.assertAll();

		} else {
			log.info("Material Category Approve Return and Edit and Review skipped based on configuration");
		}
	}

	@Test(groups = { "categoryApprove" })
	public void materialCategoryApprove() throws Throwable {

		if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
			log.info("--- Approving Material Category: {} ---", currentCategoryName);
			category.switchUser(USERNAME3, PASSWORD3, PC_DB_NAME, MASTER_MODULE, SUB_MASTER_MODULE);
		}
		log.info("--- Performing Final Approval for: {} ---", currentCategoryName);

		category.waitForLoading();
		log.info("Opening actions menu for approval");
		category.clickActions(currentCategoryName);
		ScreenshotUtil.capture();
		log.info("Clicking Approve button in the list");
		category.clickApprove();
		ScreenshotUtil.capture();
		category.enterRemarks(APPROVE_REMARKS);
		log.info("Entered Approve remarks: {}", APPROVE_REMARKS);
		ScreenshotUtil.capture();
		category.clickApprove();
		log.info("Submitted Approval");
		ScreenshotUtil.capture();
		category.authenticate(category.currentPassword);
		String approveToast = category.waitForToast();
		category.waitForLoading();
		sa.assertEquals(approveToast, "Material category approved successfully", "Approved toaster messege",
				approveToast);
		sa.assertAll();

	}

	@Test(groups = { "Logout" })
	public void Logout() throws Throwable {
		log.info("--- Executing Final Logout ---");
		ScreenshotUtil.nextStep();
		category.logout();
		log.info("Clicked logout button");
		category.waitForToast();
		category.waitForLoading();
		ScreenshotUtil.capture();
		log.info("--- Material Category Test Case Execution Finished ---");
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
