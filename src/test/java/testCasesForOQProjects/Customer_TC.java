
package testCasesForOQProjects;
import static configData.CustomerData.*;


import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pageObjects.Customer;
import utilities.ScreenshotUtil;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class Customer_TC extends OQBaseModule_TC {
	Customer customer;
	int inactiveIterationCount = 0;
	int activeIterationCount = 0;

	@BeforeClass
	@Parameters({ "configFile" })
	public void setUp(@Optional("customer.properties") String configFile) throws Exception {
		log.info("--- Starting Customer Test Case Setup with config: {} ---", configFile);

		// Load the dynamic property file
		configData.CustomerData.loadProperties(configFile);

		// Map to OQBaseModule_TC required fields
		CONFIG_NAME = CURRENT_CONFIG_NAME;
		APP_URL_VAL = APP_URL;
		CHROME_URL_VAL = CHROME_URL;
		USERNAME_VAL = USERNAME;
		PASSWORD_VAL = PASSWORD;
		USERNAME1_VAL = USERNAME1;
		PASSWORD1_VAL = PASSWORD1;
		USERNAME2_VAL = USERNAME2;
		PASSWORD2_VAL = PASSWORD2;
		USERNAME3_VAL = USERNAME3;
		PASSWORD3_VAL = PASSWORD3;
		ACTIONSPERFORMEDBY_VAL = ACTIONSPERFORMEDBY;
		PC_DB_NAME_VAL = PC_DB_NAME;
		MASTER_DB_NAME_VAL = MASTER_DB_NAME;
		MM_DB_NAME_VAL = MM_DB_NAME;
		TITLE_MODULE_VAL = "MASTERS";
		MASTER_MODULE_VAL = MASTER_MODULE;
		SCRIPT_NUMBER_VAL = SCRIPT_NUMBER;
		VIEW_ACTION_VAL = CUSTOMER_VIEW_ACTION;

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
		this.pageObject = customer; // IMPORTANT: Initialize Base Page Object
		this.currentEntryName = CUSTOMER_NAME; // Set Base Field

		log.info("Setup completed. Screenshots enabled: {}. Customer Name: {}", screenshotsEnabled, currentEntryName);
	}

	@Override
	protected void performClickView() throws Throwable {
		customer.clickPendingView(currentEntryName);
	}

	@Override
	protected void updateEntryName(String newName) throws Throwable {
		customer.customerName(newName);
	}

	@Override
	protected void beforeMasterClick() {
		nextStep();
	}

	@Override
	protected void beforeClickActionsScreenshot() {
		nextStep();
	}

	@Override
	protected void beforeLogout() {
		nextStep();
	}

	@Override
	protected void beforeInactive() {
		inactiveIterationCount++;
		if (inactiveIterationCount == 1) {
			nextStep();
		}
	}

	@Override
	protected void beforeActive() {
		activeIterationCount++;
		if (activeIterationCount == 1) {
			nextStep();
		}
	}

	private String currentReviewContext = "";
	private String currentApproveContext = "";

	@Override
	protected void reviewMenuClick() {
		if ("INACTIVE".equalsIgnoreCase(currentReviewContext)) {
			customer.clickInactiveReview();
		} else if ("ACTIVE".equalsIgnoreCase(currentReviewContext)) {
			customer.clickActiveReview();
		} else {
			customer.clickReview();
		}
	}

	@Override
	protected void reviewSubmitClick() {
		customer.clickSubmit();
	}

	@Override
	protected void approveMenuClick() {
		if ("INACTIVE".equalsIgnoreCase(currentApproveContext)) {
			customer.clickInactiveApprove();
		} else if ("ACTIVE".equalsIgnoreCase(currentApproveContext)) {
			customer.clickActiveApprove();
		} else {
			customer.clickApprove();
		}
	}

	@Override
	protected void approveSubmitClick() {
		customer.clickSubmit();
	}

	@Override
	protected void returnReviewOpenClick() {
		customer.clickReturn();
	}

	@Override
	protected void returnApproveOpenClick() {
		customer.clickReturn();
	}

	@Override
	protected void returnSubmitClick() {
		customer.clickSubmit();
	}

	@Override
	protected void rejectReviewOpenClick() {
		customer.clickReject();
	}

	@Override
	protected void rejectApproveOpenClick() {
		customer.clickReject();
	}

	@Override
	protected void rejectSubmitClick() {
		customer.clickSubmit();
	}

	@Override
	protected void reviewIterationCount() {
	
	}
	
	@Test(groups = { "Creation" })
	public void Creation_Of_Customer() throws Throwable {
		log.info("--- Navigating to Create Customer Screen ---");
		nextStep();
		customer.Create();
		log.info("Clicked on Create button");
		customer.waitForLoading();
		capture();
		nextStep();
		log.info("--- Creating Customer: {} ---", CUSTOMER_NAME);
		currentEntryName = CUSTOMER_NAME; // Initialize with base name
		customer.customerName(CUSTOMER_NAME);
		customer.customerCity(CITY);
		customer.customerCode(CUSTOMER_CODE);

		String[] marketRegions = MARKET_REGION.split(",");

		for (int i = 0; i < marketRegions.length; i++) {
			customer.selMarketRegion(marketRegions[i].trim());
		}

		customer.clickSubmit();
		log.info("Clicked Submit");
		capture();

		customer.authenticate(customer.currentPassword);
		String authToast = customer.waitForToast();
		customer.waitForLoading();
		capture();
		sa.assertEquals(authToast, "Customer created successfully", "Created Toaster message",
				"Creation failed with message: " + authToast);
		nextStep();
		capture();
		sa.assertAll();
	}
	
	
	
	@Override
	protected void beforeEdit() {
		nextStep();
	}
	


	@Test(groups = { "customerReviewReturn_customerEdit" })
	public void customer_Review_Return_and_Edit() throws Throwable {
		if (CUSTOMER_RETURN_ACTION_IN_REVIEW.equalsIgnoreCase("yes")) {
			switchUserIfMulti(USERNAME2_VAL, PASSWORD2_VAL);
			log.info("--- Initiating Review Return Flow for: {} ---", currentEntryName);
			performReturnReview(REVIEW_RETURN_REMARKS, "Customer returned successfully");

			switchUserIfMulti(USERNAME1_VAL, PASSWORD1_VAL);
			performEdit(EDIT_CUSTOMER_IN_REVIEW_RETURN, "Customer updated successfully");
			sa.assertAll();
		} else {
			log.info("Customer Review Return and Edit skipped based on configuration");
		}
	}

	@Test(groups = { "customerReview" })
	public void customerReview() throws Throwable {
		switchUserIfMulti(USERNAME2_VAL, PASSWORD2_VAL);
		log.info("--- Initiating Review Flow for: {} ---", currentEntryName);
		performReview(REVIEW_REMARKS, "Customer reviewed successfully");
		sa.assertAll();
	}

	@Test(groups = { "customerApproveReturn_customerEdit_customerReview" })
	public void customer_Approve_Return_and_Edit_and_Review() throws Throwable {
		if (CUSTOMER_RETURN_ACTION_IN_APPROVE.equalsIgnoreCase("yes")) {
			switchUserIfMulti(USERNAME3_VAL, PASSWORD3_VAL);
			log.info("--- Initiating Approve Return Flow for: {} ---", currentEntryName);
			performReturnApprove(APPROVE_RETURN_REMARKS, "Customer returned successfully");

			switchUserIfMulti(USERNAME1_VAL, PASSWORD1_VAL);
			freezeStep();
			log.info("Opening Edit screen (After Return)");
			performEdit(EDIT_CUSTOMER_IN_APPROVE_RETURN, "Customer updated successfully");

			switchUserIfMulti(USERNAME2_VAL, PASSWORD2_VAL);
			log.info("--- Initiating Review Flow for: {} ---", currentEntryName);
			performReview(REVIEW_REMARKS, "Customer reviewed successfully");
			resumeStep();
			sa.assertAll();
		} else {
			log.info("Customer Approve Return and Edit and Review skipped based on configuration");
		}
	}

	@Test(groups = { "customerApprove" })
	public void customerApprove() throws Throwable {
		switchUserIfMulti(USERNAME3_VAL, PASSWORD3_VAL);
		log.info("--- Performing Final Approval for: {} ---", currentEntryName);
		performApprove(APPROVE_REMARKS, "Customer approved successfully");
		sa.assertAll();
	}

	
	@Override
	protected void inactiveSubmitClick() {
		customer.clickSubmit();
	}
	
	@Test(groups = { "ClickInactive" })
	public void Click_Inactive() throws Throwable {
		switchUserIfMulti(USERNAME1_VAL, PASSWORD1_VAL);
		log.info("--- Performing Inactive for: {} ---", currentEntryName);
		performInactive(INACTIVE_REMARKS, "Customer In-Active Request initiated successfully");
		sa.assertAll();
	}

	@Test(groups = { "Customer_Inactive_Review_Reject" })
	public void customer_Inactive_Review_Reject() throws Throwable {
		if (CUSTOMER_INACTIVE_REJECT_IN_REVIEW_ACTION.equalsIgnoreCase("yes")) {
			switchUserIfMulti(USERNAME2_VAL, PASSWORD2_VAL);
			log.info("--- Initiating Inactive Reject In Review Flow for: {} ---", currentEntryName);
			currentReviewContext = "INACTIVE";
			performReviewReject(INACTIVE_REVIEW_REJECT_REMARKS, "Customer inactivation rejected successfully");
			currentReviewContext = "";
			sa.assertAll();
		}
	}

	
	
	@Test(groups = { "Customer_Inactive_Review" })
	public void customer_Inactive_Review() throws Throwable {
		switchUserIfMulti(USERNAME2_VAL, PASSWORD2_VAL);
		log.info("--- Initiating Inactive Review Flow for: {} ---", currentEntryName);
		currentReviewContext = "INACTIVE";
		performReview(INACTIVE_REVIEW__REMARKS, "Customer inactivation reviewed successfully");
		currentReviewContext = "";
		sa.assertAll();
	}

	@Test(groups = { "Customer_Inactive_Approve_Reject" })
	public void customer_Inactive_Approve_Reject() throws Throwable {
		if (CUSTOMER_INACTIVE_REJECT_IN_APPROVE_ACTION.equalsIgnoreCase("yes")) {
			switchUserIfMulti(USERNAME3_VAL, PASSWORD3_VAL);
			log.info("--- Initiating Inactive Reject In Approve Flow for: {} ---", currentEntryName);
			freezeStep();
			currentApproveContext = "INACTIVE";
			performApproveReject(INACTIVE_APPROVE_REJECT_REMARKS, "Customer inactivation rejected successfully");
			currentApproveContext = "";
			sa.assertAll();
		}
	}

	@Test(groups = { "Customer_Inactive_Approve" })
	public void customer_Inactive_Approve() throws Throwable {
		switchUserIfMulti(USERNAME3_VAL, PASSWORD3_VAL);
		log.info("--- Initiating Inactive Approve Flow for: {} ---", currentEntryName);
		currentApproveContext = "INACTIVE";
		performApprove(INACTIVE_APPROVE_REMARKS, "Customer inactivation approved successfully");
		currentApproveContext = "";
		resumeStep();
		sa.assertAll();
	}

	
	@Override
	protected void activeSubmitClick() {
		customer.clickSubmit();
	}
	
	@Test(groups = { "ClickActive" })
	public void Click_Active() throws Throwable {
		switchUserIfMulti(USERNAME1_VAL, PASSWORD1_VAL);
		log.info("--- Performing Active for: {} ---", currentEntryName);
		performActive(ACTIVE_REMARKS, "Customer activation initiated successfully");
		sa.assertAll();
	}

	@Test(groups = { "Customer_Active_Review_Reject" })
	public void customer_Active_Review_Reject() throws Throwable {
		if (CUSTOMER_ACTIVE_REJECT_IN_REVIEW_ACTION.equalsIgnoreCase("yes")) {
			switchUserIfMulti(USERNAME2_VAL, PASSWORD2_VAL);
			log.info("--- Initiating Active Reject In Review Flow for: {} ---", currentEntryName);
			currentReviewContext = "ACTIVE";
			performReviewReject(ACTIVE_REVIEW_REJECT_REMARKS, "Customer activation rejected successfully");
			currentReviewContext = "";
			sa.assertAll();
		}
	}

	@Test(groups = { "Customer_Active_Review" })
	public void customer_Active_Review() throws Throwable {
		switchUserIfMulti(USERNAME2_VAL, PASSWORD2_VAL);
		log.info("--- Initiating Active Review Flow for: {} ---", currentEntryName);
		currentReviewContext = "ACTIVE";
		performReview(ACTIVE_REVIEW__REMARKS, "Customer activation reviewed successfully");
		currentReviewContext = "";
		sa.assertAll();
	}

	@Test(groups = { "Customer_Active_Approve_Reject" })
	public void customer_Active_Approve_Reject() throws Throwable {
		if (CUSTOMER_ACTIVE_REJECT_IN_APPROVE_ACTION.equalsIgnoreCase("yes")) {
			switchUserIfMulti(USERNAME3_VAL, PASSWORD3_VAL);
			log.info("--- Initiating Active Reject In Approve Flow for: {} ---", currentEntryName);
			freezeStep();
			currentApproveContext = "ACTIVE";
			performApproveReject(ACTIVE_APPROVE_REJECT_REMARKS, "Customer activation rejected successfully");
			currentApproveContext = "";
			sa.assertAll();
		}
	}

	@Test(groups = { "Customer_Active_Approve" })
	public void customer_Active_Approve() throws Throwable {
		switchUserIfMulti(USERNAME3_VAL, PASSWORD3_VAL);
		log.info("--- Initiating Active Approve Flow for: {} ---", currentEntryName);
		currentApproveContext = "ACTIVE";
		performApprove(ACTIVE_APPROVE_REMARKS, "Customer activated successfully");
		currentApproveContext = "";
		resumeStep();
		sa.assertAll();
	}

	
	
	
	// Repeated methods for TestNG groups/suites
	
	@Test(groups = { "ClickActions" })
	public void Click_Actions2() throws Throwable {
		switchUserIfMulti(USERNAME2, PASSWORD2);
		Click_Actions();
	}
	
	@Test(groups = { "ClickActions" })
	public void Click_Actions1() throws Throwable {
		switchUserIfMulti(USERNAME1, PASSWORD1);
		Click_Actions();
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
	
	
	
	


