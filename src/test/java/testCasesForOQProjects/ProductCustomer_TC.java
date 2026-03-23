
package testCasesForOQProjects;
import static configData.ProductCustomerData.*;


import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageObjects.Customer;
import testBase.BaseClass;
import utilities.DatabaseBackupUtil;
import utilities.ScreenshotUtil;
import utilities.SoftAssertionUtil;

import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class ProductCustomer_TC extends BaseClass {
	Customer customer;
	String currentCustomerName;
	SoftAssertionUtil sa;
	int inactiveIterationCount = 0;
	int activeIterationCount = 0;

	@BeforeClass
	@Parameters({ "configFile" })
	public void setUp(@Optional("customer.properties") String configFile) throws Exception {
		log.info("--- Starting Customer Test Case Setup with config: {} ---", configFile);

		// Load the dynamic property file
		configData.CustomerData.loadProperties(configFile);

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
		customer = new Customer(driver);
		customer.setTableHeaders(TABLE_HEADERS);
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
		customer.waitForElementToVisible(customer.getSearchBox());
		Assert.assertTrue(customer.getSearchBox().isDisplayed(), "Search box is not visible!");
		log.info("Searching for Application URL: {}", APP_URL);
		customer.searchBox(APP_URL);
		customer.waitForLoading();
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
			customer.login(USERNAME, PASSWORD, PC_DB_NAME);
		} else {
			customer.login(USERNAME1, PASSWORD1, PC_DB_NAME);
		}
	}

	@Test(groups = { "moduleselect" })
	public void moduleClick() throws Throwable {
		log.info("--- Clicking Module ---");
		ScreenshotUtil.nextStep();
		customer.click_titleMasters();
		log.info("Clicked on Masters title");
		ScreenshotUtil.capture();
		customer.waitForLoading();
		ScreenshotUtil.nextStep();
		customer.masterClick(MASTER_MODULE);
		log.info("Clicked on Master Module: {}", MASTER_MODULE);
		customer.waitForLoading();
		ScreenshotUtil.capture();
	}

	@Test(groups = { "Creation" })
	public void Creation_Of_Customer() throws Throwable {
		log.info("--- Navigating to Create Customer Screen ---");
		customer.Create();
		log.info("Clicked on Create button");
		customer.waitForLoading();
		ScreenshotUtil.capture();
		ScreenshotUtil.nextStep();
		log.info("--- Creating Customer: {} ---", CUSTOMER_NAME);
		currentCustomerName = CUSTOMER_NAME; // Initialize with base name
		customer.customerName(CUSTOMER_NAME);
		customer.customerCity(CITY);
		customer.customerCode(CUSTOMER_CODE);

		String[] marketRegions = MARKET_REGION.split(",");

		for (int i = 0; i < marketRegions.length; i++) {

			customer.selMarketRegion(marketRegions[i].trim());

		}

		customer.clickSubmit();
		log.info("Clicked Submit");
		ScreenshotUtil.capture();

		customer.authenticate(customer.currentPassword);
		String authToast = customer.waitForToast();
		customer.waitForLoading();
		ScreenshotUtil.capture();
		sa.assertEquals(authToast, "Customer created successfully", "Created Toaster message",
				"Creation failed with message: " + authToast);
		ScreenshotUtil.nextStep();
		ScreenshotUtil.capture();
		sa.assertAll();

	}

	@Test(groups = { "ClickActions" })
	public void Click_Actions_For_Review() throws Throwable {

		if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
			customer.switchUser(USERNAME2, PASSWORD2, PC_DB_NAME, MASTER_MODULE);
		}
		log.info("--- Attempting to open Actions Menu for: {} ---", currentCustomerName);
		ScreenshotUtil.nextStep();
		customer.clickActions(currentCustomerName);
		log.info("Successfully opened Actions menu for {}", currentCustomerName);
		ScreenshotUtil.capture();
		

	}
	

	@Test(groups = { "ClickView" })
	public void Click_View() throws Throwable {
		if (CUSTOMER_VIEW_ACTION.equalsIgnoreCase("yes")) {
			log.info("--- Viewing Customer: {} ---", currentCustomerName);
			ScreenshotUtil.nextStep();
			customer.clickPendingView(currentCustomerName);
			log.info("View screen opened");
			customer.waitForLoading();
			ScreenshotUtil.capture();
			customer.clickBack();
			log.info("Clicked Back button");
			customer.waitForLoading();
			ScreenshotUtil.capture();
		} else {
			log.info("View step skipped based on configuration");
		}
	}

	@Test(groups = { "customerReviewReturn_customerEdit" })
	public void customer_Review_Return_and_Edit() throws Throwable {

		if (CUSTOMER_RETURN_ACTION_IN_REVIEW.equalsIgnoreCase("yes")) {

			switchUserIfMulti(USERNAME2, PASSWORD2);
			log.info("--- Initiating Review Return Flow for: {} ---", currentCustomerName);
			performReturnReview(REVIEW_RETURN_REMARKS);
			
			switchUserIfMulti(USERNAME1, PASSWORD1);
			performEdit(EDIT_CUSTOMER_IN_REVIEW_RETURN);
			sa.assertAll();

		} else {
			log.info("Customer Review Return and Edit skipped based on configuration");
		}
	}

	@Test(groups = { "customerReview" })
	public void customerReview() throws Throwable {

		switchUserIfMulti(USERNAME2, PASSWORD2);
		log.info("--- Initiating Review Flow for: {} ---", currentCustomerName);
		performReview(REVIEW_REMARKS);
		sa.assertAll();

	}

	@Test(groups = { "customerApproveReturn_customerEdit_customerReview" })
	public void customer_Approve_Return_and_Edit_and_Review() throws Throwable {

		if (CUSTOMER_RETURN_ACTION_IN_APPROVE.equalsIgnoreCase("yes")) {

			
			switchUserIfMulti(USERNAME3, PASSWORD3);
			log.info("--- Initiating Approve Return Flow for: {} ---", currentCustomerName);
			performReturnApprove(APPROVE_RETURN_REMARKS);
			
			switchUserIfMulti(USERNAME1, PASSWORD1);
			log.info("Opening Edit screen (After Return)");
			performEdit(EDIT_CUSTOMER_IN_APPROVE_RETURN);
			ScreenshotUtil.freezeStepNumbering();
			
			

			switchUserIfMulti(USERNAME2, PASSWORD2);
			log.info("--- Initiating Review Flow for: {} ---", currentCustomerName);
			performReview(REVIEW_REMARKS);
			ScreenshotUtil.resumeStepNumbering();
			sa.assertAll();
			
			

		} else {
			log.info("Customer Approve Return and Edit and Review skipped based on configuration");
		}
	}

	@Test(groups = { "customerApprove" })
	public void customerApprove() throws Throwable {

		if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
			log.info("--- Approving Customer: {} ---", currentCustomerName);
			customer.switchUser(USERNAME3, PASSWORD3, PC_DB_NAME, MASTER_MODULE);
		}
		log.info("--- Performing Final Approval for: {} ---", currentCustomerName);

		customer.waitForLoading();
		log.info("Opening actions menu for approval");
		customer.clickActions(currentCustomerName);
		ScreenshotUtil.capture();
		log.info("Clicking Approve button in the list");
		customer.clickApprove();
		ScreenshotUtil.capture();
		customer.enterRemarks(APPROVE_REMARKS);
		log.info("Entered Approve remarks: {}", APPROVE_REMARKS);
		ScreenshotUtil.capture();
		customer.clickSubmit();
		log.info("Submitted Approval");
		ScreenshotUtil.capture();
		customer.authenticate(customer.currentPassword);
		String approveToast = customer.waitForToast();
		customer.waitForLoading();
		sa.assertEquals(approveToast, "Customer approved successfully", "Approved toaster messege", approveToast);
		sa.assertAll();

	}

	@Test(groups = { "ClickInactive" })
	public void Click_Inactive() throws Throwable {
		inactiveIterationCount++;

		switchUserIfMulti(USERNAME1, PASSWORD1);
		log.info("--- Performing Inactive for: {} ---", currentCustomerName);

		customer.waitForLoading();
		log.info("Opening actions menu for Inactive");
		customer.clickActions(currentCustomerName);
		ScreenshotUtil.capture();
		
		  if (inactiveIterationCount == 1) { 
			  ScreenshotUtil.nextStep(); }
		 
		log.info("Clicking Inactive button in the list");
		customer.clickInactive();
		ScreenshotUtil.capture();
		customer.enterRemarks(INACTIVE_REMARKS);
		log.info("Entered Inactive remarks: {}", INACTIVE_REMARKS);
		ScreenshotUtil.capture();
		customer.clickSubmit();
		log.info("Inactive requested");
		ScreenshotUtil.capture();
		customer.authenticate(customer.currentPassword);
		String inactiveToast = customer.waitForToast();
		customer.waitForLoading();
		sa.assertEquals(inactiveToast, "Customer Inactive Request initiated successfully", "Inactive toaster messege",
				inactiveToast);
		sa.assertAll();

	}

	@Test(groups = { "Customer_Inactive_Review_Reject" })
	public void customer_Inactive_Review_Reject() throws Throwable {

		if (CUSTOMER_INACTIVE_REJECT_IN_REVIEW_ACTION.equalsIgnoreCase("yes")) {

			switchUserIfMulti(USERNAME2, PASSWORD2);
			log.info("--- Initiating Inactive Reject In Review Flow for: {} ---", currentCustomerName);
			log.info("Opening actions menu to access InActive Review/Reject");
			customer.clickActions(currentCustomerName);
			ScreenshotUtil.capture();
			ScreenshotUtil.nextStep();
			log.info("Clicking Reject to trigger reject pop up");
			customer.clickReject();
			customer.waitForLoading();
			ScreenshotUtil.capture();
			customer.enterRemarks(INACTIVE_REVIEW_REJECT_REMARKS);
			log.info("Entered Inactive Review Reject remarks: {}", INACTIVE_REVIEW_REJECT_REMARKS);
			ScreenshotUtil.capture();
			customer.clickSubmit();
			log.info("Clicked Submit button");
			ScreenshotUtil.capture();
			customer.authenticate(customer.currentPassword);
			String rejectToast = customer.waitForToast();
			customer.waitForLoading();
			sa.assertEquals(rejectToast, "Customer Reject successfully", "Inactive Review Reject  toaster message",
					rejectToast);
			sa.assertAll();

		}
	}

	@Test(groups = { "Customer_Inactive_Review" })
	public void customer_Inactive_Review() throws Throwable {

		switchUserIfMulti(USERNAME2, PASSWORD2);
		log.info("--- Initiating Inactive Review Flow for: {} ---", currentCustomerName);
		log.info("Opening actions menu to access Inactive Review/Reject");

		customer.clickActions(currentCustomerName);
		ScreenshotUtil.capture();
		log.info("Clicking Review to trigger Review popup");
		customer.clickInactiveReview();
		customer.waitForLoading();
		ScreenshotUtil.capture();
		customer.enterRemarks(INACTIVE_REVIEW__REMARKS);
		log.info("Entered Inactive Review remarks: {}", INACTIVE_REVIEW_REJECT_REMARKS);
		ScreenshotUtil.capture();
		customer.clickSubmit();
		log.info("Clicked Submit button");
		ScreenshotUtil.capture();
		customer.authenticate(customer.currentPassword);
		String reviewToast = customer.waitForToast();
		customer.waitForLoading();
		sa.assertEquals(reviewToast, "Customer Reviewed successfully", "Inactive Review toaster message",
				reviewToast);
		sa.assertAll();

	}

	@Test(groups = { "Customer_Inactive_Approve_Reject" })
	public void customer_Inactive_Approve_Reject() throws Throwable {

		if (CUSTOMER_INACTIVE_REJECT_IN_APPROVE_ACTION.equalsIgnoreCase("yes")) {

			switchUserIfMulti(USERNAME3, PASSWORD3);
			log.info("--- Initiating Inactive Reject In Approve Flow for: {} ---", currentCustomerName);
			log.info("Opening actions menu to access Inactive Approve/Reject");
			ScreenshotUtil.freezeStepNumbering();
			customer.clickActions(currentCustomerName);
			ScreenshotUtil.capture();
			log.info("Clicking reject to trigger reject pop up");
			customer.clickReject();
			customer.waitForLoading();
			ScreenshotUtil.capture();
			customer.enterRemarks(INACTIVE_APPROVE_REJECT_REMARKS);
			log.info("Entered Inactive Approve Reject remarks: {}", INACTIVE_APPROVE_REJECT_REMARKS);
			ScreenshotUtil.capture();
			customer.clickSubmit();
			log.info("Clicked Submit button");
			ScreenshotUtil.capture();
			customer.authenticate(customer.currentPassword);
			String rejectToast = customer.waitForToast();
			customer.waitForLoading();
			sa.assertEquals(rejectToast, "Customer Reject successfully", "Inactive Approve Reject  toaster message",
					rejectToast);
			sa.assertAll();

		}
	}

	@Test(groups = { "Customer_Inactive_Approve" })
	public void customer_Inactive_Approve() throws Throwable {

		switchUserIfMulti(USERNAME3, PASSWORD3);
		log.info("--- Initiating Inactive Approve Flow for: {} ---", currentCustomerName);
		log.info("Opening actions menu to access Inactive Approve/Reject");
		customer.clickActions(currentCustomerName);
		ScreenshotUtil.capture();
		log.info("Clicking Approve to trigger approve pop up");
		customer.clickInactiveApprove();
		customer.waitForLoading();
		ScreenshotUtil.capture();
		customer.enterRemarks(INACTIVE_APPROVE_REMARKS);
		log.info("Entered Inactive Approve remarks: {}", INACTIVE_APPROVE_REMARKS);
		ScreenshotUtil.capture();
		customer.clickSubmit();
		log.info("Clicked Submit button");
		ScreenshotUtil.capture();
		customer.authenticate(customer.currentPassword);
		String approveToast = customer.waitForToast();
		customer.waitForLoading();
		sa.assertEquals(approveToast, "Customer Inactivated successfully", "Inactivated toaster message",
				approveToast);
		ScreenshotUtil.resumeStepNumbering();
		sa.assertAll();

	}

	@Test(groups = { "ClickActive" })
	public void Click_Active() throws Throwable {
		activeIterationCount++;	
		switchUserIfMulti(USERNAME1, PASSWORD1);
		log.info("--- Performing Active for: {} ---", currentCustomerName);

		customer.waitForLoading();
		log.info("Opening actions menu for Active");
		customer.clickActions(currentCustomerName);
		ScreenshotUtil.capture();
		log.info("Clicking Active button in the list");
		
		  if (activeIterationCount == 1) { 
			  ScreenshotUtil.nextStep(); }
		 
		customer.clickActive();
		ScreenshotUtil.capture();
		customer.enterRemarks(ACTIVE_REMARKS);
		log.info("Entered Active remarks: {}", ACTIVE_REMARKS);
		ScreenshotUtil.capture();
		customer.clickSubmit();
		log.info("Active requested");
		ScreenshotUtil.capture();
		customer.authenticate(customer.currentPassword);
		String activeToast = customer.waitForToast();
		customer.waitForLoading();
		sa.assertEquals(activeToast, "Customer active request initiated successfully", "Active toaster messege",
				activeToast);
		sa.assertAll();

	}

	@Test(groups = { "Customer_Active_Review_Reject" })
	public void customer_Active_Review_Reject() throws Throwable {

		if (CUSTOMER_ACTIVE_REJECT_IN_REVIEW_ACTION.equalsIgnoreCase("yes")) {

			switchUserIfMulti(USERNAME2, PASSWORD2);
			log.info("--- Initiating Active Reject In Review Flow for: {} ---", currentCustomerName);
			log.info("Opening actions menu to access Active Review/Reject");
			customer.clickActions(currentCustomerName);
			ScreenshotUtil.capture();
			ScreenshotUtil.nextStep();
			log.info("Clicking Return to trigger return pop up");
			customer.clickReject();
			customer.waitForLoading();
			ScreenshotUtil.capture();
			customer.enterRemarks(ACTIVE_REVIEW_REJECT_REMARKS);
			log.info("Entered Inactive Review Reject remarks: {}", ACTIVE_REVIEW_REJECT_REMARKS);
			ScreenshotUtil.capture();
			customer.clickSubmit();
			log.info("Clicked Submit button");
			ScreenshotUtil.capture();
			customer.authenticate(customer.currentPassword);
			String rejectToast = customer.waitForToast();
			customer.waitForLoading();
			sa.assertEquals(rejectToast, "Customer Reject successfully", "Active Review Reject  toaster message",
					rejectToast);
			sa.assertAll();

		}
	}

	@Test(groups = { "Customer_Active_Review" })
	public void customer_Active_Review() throws Throwable {

		switchUserIfMulti(USERNAME2, PASSWORD2);
		log.info("--- Initiating Active Review Flow for: {} ---", currentCustomerName);
		log.info("Opening actions menu to access InActive Review/Reject");
		customer.clickActions(currentCustomerName);
		ScreenshotUtil.capture();
		log.info("Clicking Review to trigger Review pop up");
		customer.clickActiveReview();
		customer.waitForLoading();
		ScreenshotUtil.capture();
		customer.enterRemarks(ACTIVE_REVIEW__REMARKS);
		log.info("Entered Active Review remarks: {}", ACTIVE_REVIEW__REMARKS);
		ScreenshotUtil.capture();
		customer.clickSubmit();
		log.info("Clicked Submit button");
		ScreenshotUtil.capture();
		customer.authenticate(customer.currentPassword);
		String reviewToast = customer.waitForToast();
		customer.waitForLoading();
		sa.assertEquals(reviewToast, "Customer Reviewed successfully", "Active Review toaster message",
				reviewToast);
		sa.assertAll();

	}

	@Test(groups = { "Customer_Active_Approve_Reject" })
	public void customer_Active_Approve_Reject() throws Throwable {

		if (CUSTOMER_ACTIVE_REJECT_IN_APPROVE_ACTION.equalsIgnoreCase("yes")) {

			switchUserIfMulti(USERNAME3, PASSWORD3);
			log.info("--- Initiating Active Reject In Approve Flow for: {} ---", currentCustomerName);
			log.info("Opening actions menu to access Active Approve/Reject");
			ScreenshotUtil.freezeStepNumbering();
			customer.clickActions(currentCustomerName);
			ScreenshotUtil.capture();
			log.info("Clicking Reject to trigger reject pop up");
			customer.clickReject();
			customer.waitForLoading();
			ScreenshotUtil.capture();
			customer.enterRemarks(ACTIVE_APPROVE_REJECT_REMARKS);
			log.info("Entered Active Approve Reject remarks: {}", ACTIVE_APPROVE_REJECT_REMARKS);
			ScreenshotUtil.capture();
			customer.clickSubmit();
			log.info("Clicked Submit button");
			ScreenshotUtil.capture();
			customer.authenticate(customer.currentPassword);
			String rejectToast = customer.waitForToast();
			customer.waitForLoading();
			sa.assertEquals(rejectToast, "Customer Reject successfully", "Active Approve Reject toaster message",
					rejectToast);
			sa.assertAll();

		}
	}

	@Test(groups = { "Customer_Active_Approve" })
	public void customer_Active_Approve() throws Throwable {

		switchUserIfMulti(USERNAME3, PASSWORD3);
		log.info("--- Initiating Active Approve Flow for: {} ---", currentCustomerName);
		customer.clickActions(currentCustomerName);
		ScreenshotUtil.capture();
		log.info("Clicking Approve to trigger approve pop up");
		customer.clickActiveApprove();
		customer.waitForLoading();
		ScreenshotUtil.capture();
		customer.enterRemarks(ACTIVE_APPROVE_REMARKS);
		log.info("Entered Active Approve remarks: {}", ACTIVE_APPROVE_REMARKS);
		ScreenshotUtil.capture();
		customer.clickSubmit();
		log.info("Clicked Approve button");
		ScreenshotUtil.capture();
		customer.authenticate(customer.currentPassword);
		String approveToast = customer.waitForToast();
		customer.waitForLoading();
		sa.assertEquals(approveToast, "Customer Activated successfully", "Activated toaster message",
				approveToast);
		ScreenshotUtil.resumeStepNumbering();
		sa.assertAll();

	}

	@Test(groups = { "Logout" })
	public void Logout() throws Throwable {
		log.info("--- Executing Final Logout ---");
		ScreenshotUtil.nextStep();
		customer.logout();
		log.info("Clicked logout button");
		customer.waitForToast();
		customer.waitForLoading();
		ScreenshotUtil.capture();
		log.info("--- Customer Test Case Execution Finished ---");
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
	
	
	
	// --- HELPER METHODS ---

	
		private void performReview(String remarks) throws Throwable {
			
			
			log.info("Opening actions menu to access Review/Return");
			customer.clickActions(currentCustomerName);
			ScreenshotUtil.capture();
			ScreenshotUtil.nextStep();
			log.info("Clicking Review to trigger Review pop up");
			customer.clickReview();
			customer.waitForLoading();
			ScreenshotUtil.capture();
			customer.enterRemarks(REVIEW_REMARKS);
			log.info("Entered Review remarks: {}", REVIEW_REMARKS);
			ScreenshotUtil.capture();
			customer.clickSubmit();
			log.info("Clicked Submit button");
			ScreenshotUtil.capture();
			customer.authenticate(customer.currentPassword);
			String reviewToast = customer.waitForToast();
			customer.waitForLoading();
			sa.assertEquals(reviewToast, "Customer reviewed successfully", "Review toaster message", reviewToast);
			
		}

		private void performEdit(String updateName) throws Throwable {
			log.info("Opening Edit screen (After Review Return)");
			ScreenshotUtil.nextStep();
			customer.clickEdit(currentCustomerName);
			customer.waitForLoading();
			ScreenshotUtil.capture();
			if (updateName != null && !updateName.trim().isEmpty()) {
				log.info("Updating Name to: {}", updateName);
				customer.customerName(updateName);
				currentCustomerName = updateName;
			}

			customer.clickUpdate();
			log.info("Clicked Update");
			ScreenshotUtil.capture();
			customer.authenticate(customer.currentPassword);
			String editToast = customer.waitForToast();
			customer.waitForLoading();
			sa.assertEquals(editToast, "Customer updated successfully", "Updated toaster messege", editToast);
			
		}

		private void performReturnReview(String remarks) throws Throwable {
			log.info("Opening actions menu to access Review/Return");
			customer.clickActions(currentCustomerName);
			ScreenshotUtil.nextStep();
			log.info("Clicking Return to trigger return pop up");
			customer.clickReturn();
			customer.waitForLoading();
			ScreenshotUtil.capture();
			customer.enterRemarks(REVIEW_RETURN_REMARKS);
			log.info("Entered Return remarks: {}", REVIEW_RETURN_REMARKS);
			ScreenshotUtil.capture();
			customer.clickSubmit();
			log.info("Clicked Submit button");
			ScreenshotUtil.capture();
			customer.authenticate(customer.currentPassword);
			String returnToast = customer.waitForToast();
			customer.waitForLoading();
			sa.assertEquals(returnToast, "Customer returned successfully", "Returned toaster message",
					returnToast);
		}

		private void performReturnApprove(String remarks) throws Throwable {
			log.info("Opening actions menu to access Approve/Return");
			customer.clickActions(currentCustomerName);
			ScreenshotUtil.capture();
			ScreenshotUtil.nextStep();
			log.info("Clicking Return to trigger return pop up");
			customer.clickReturn();
			customer.waitForLoading();
			ScreenshotUtil.capture();
			customer.enterRemarks(APPROVE_RETURN_REMARKS);
			log.info("Entered Return remarks: {}", APPROVE_RETURN_REMARKS);
			ScreenshotUtil.capture();
			customer.clickSubmit();
			log.info("Clicked Submit button");
			ScreenshotUtil.capture();
			customer.authenticate(customer.currentPassword);
			String returnToast = customer.waitForToast();
			customer.waitForLoading();
			sa.assertEquals(returnToast, "Customer returned successfully", "Returned toaster message",
					returnToast);
			
		}
		
		
		private void switchUserIfMulti(String username, String password) throws Throwable {
			if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
				customer.switchUser(username, password, PC_DB_NAME, MASTER_MODULE, SUB_MASTER_MODULE);
			}
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

	@Test(groups = { "Customer_Inactive_ReviewRep" })
	public void customer_Inactive_Review_1() throws Throwable {
		customer_Inactive_Review();
	}

	@Test(groups = { "Customer_Inactive_ReviewRep" })
	public void customer_Inactive_Review_2() throws Throwable {
		customer_Inactive_Review();
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

	@Test(groups = { "Customer_Active_ReviewRep" })
	public void customer_Active_Review_1() throws Throwable {
		customer_Active_Review();
	}

	@Test(groups = { "Customer_Inactive_ReviewRep" })
	public void customer_Active_Review_2() throws Throwable {
		customer_Active_Review();
	}
	
	
	
	

}
