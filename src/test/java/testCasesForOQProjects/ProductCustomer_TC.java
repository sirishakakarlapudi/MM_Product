
package testCasesForOQProjects;
import static configData.ProductCustomerData.*;


import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pageObjects.ProductCustomer;
import utilities.ScreenshotUtil;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class ProductCustomer_TC extends OQBaseModule_TC {
	ProductCustomer productCustomer;

	@BeforeClass
	@Parameters({ "configFile" })
	public void setUp(@Optional("productcustomer.properties") String configFile) throws Exception {
		log.info("--- Starting Product Customer Test Case Setup with config: {} ---", configFile);

		// Load the dynamic property file
		configData.ProductCustomerData.loadProperties(configFile);

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
		SUB_MASTER_MODULE_VAL = SUB_MASTER_MODULE;
		SCRIPT_NUMBER_VAL = SCRIPT_NUMBER;
		VIEW_ACTION_VAL = PRODUCT_CUSTOMER_VIEW_ACTION;

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
		productCustomer = new ProductCustomer(driver);
		productCustomer.setTableHeaders(TABLE_HEADERS);
		this.pageObject = productCustomer; // IMPORTANT: Initialize Base Page Object
		this.currentEntryName = PRODUCT; // Set Base Field

		log.info("Setup completed. Screenshots enabled: {}. Product Name: {}", screenshotsEnabled, currentEntryName);
	}

	@Override
	protected void performClickView() throws Throwable {
		productCustomer.clickPendingView(currentEntryName);
	}

	@Override
	protected void updateEntryName(String newName) throws Throwable {
		productCustomer.selCustomer(newName);
	}

	@Override
	protected void beforeMasterClick() {
		nextStep();
	}


	@Override
	protected void beforeClickActionsScreenshot() {
		nextStep();
	}

	
	private int inactiveIterationCount = 0;
	private int activeIterationCount = 0;

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

	@Override
	protected void reviewSubmitClick() {
		productCustomer.clickSubmit();
	}

	@Override
	protected void approveSubmitClick() {
		productCustomer.clickSubmit();
	}

	@Override
	protected void returnSubmitClick() {
		productCustomer.clickSubmit();
	}

	@Override
	protected void returnReviewOpenClick() {
		productCustomer.clickReturn();
	}

	@Override
	protected void returnApproveOpenClick() {
		productCustomer.clickReturn();
	}

	@Override
	protected void rejectReviewOpenClick() {
		productCustomer.clickReject();
	}

	@Override
	protected void rejectApproveOpenClick() {
		productCustomer.clickReject();
	}

	@Override
	protected void rejectSubmitClick() {
		productCustomer.clickSubmit();
	}

	@Override
	protected void inactiveSubmitClick() {
		productCustomer.clickSubmit();
	}

	@Override
	protected void activeSubmitClick() {
		productCustomer.clickSubmit();
	}
	
	@Override
	protected void reviewIterationCount() {
	
	}

	@Test(groups = { "Creation" })
	public void Creation_Of_Product_Customer() throws Throwable {
		log.info("--- Navigating to Create Product Customer Screen ---");
		productCustomer.Create();
		log.info("Clicked on Create button");
		productCustomer.waitForLoading();
		capture();
		nextStep();
		log.info("--- Creating Product Customer: {} ---", PRODUCT);
		currentEntryName = PRODUCT; // Initialize with base name
		productCustomer.selProduct(PRODUCT);

		String[] regions = MARKET_REGION.split(";");
		String[] customerGroups = CUSTOMER.split(";");

		for (int i = 0; i < regions.length; i++) {
			productCustomer.selMarketRegion(regions[i].trim());
			productCustomer.selMultipleCustomers(customerGroups[i]);

			if (i < regions.length - 1) {
				productCustomer.clickAddButton();
			}
		}
		productCustomer.clickSubmit();
		log.info("Clicked Submit");
		capture();

		productCustomer.authenticate(productCustomer.currentPassword);
		String authToast = productCustomer.waitForToast();
		productCustomer.waitForLoading();
		capture();
		sa.assertEquals(authToast, "Product Customer created successfully", "Created Toaster message",
				"Creation failed with message: " + authToast);
		nextStep();
		capture();
		sa.assertAll();
	}

	
	
	@Test(groups = { "productcustomerReviewReturn_customerEdit" })
	public void product_Customer_Review_Return_and_Edit() throws Throwable {
		if (PRODUCT_CUSTOMER_RETURN_ACTION_IN_REVIEW.equalsIgnoreCase("yes")) {
			switchUserIfMulti(USERNAME2_VAL, PASSWORD2_VAL);
			log.info("--- Initiating Review Return Flow for: {} ---", currentEntryName);
			performReturnReview(REVIEW_RETURN_REMARKS, "Product Customer returned successfully");

			switchUserIfMulti(USERNAME1_VAL, PASSWORD1_VAL);
			performEdit(EDIT_CUSTOMER_IN_REVIEW_RETURN, "Product Customer updated successfully");
			sa.assertAll();
		} else {
			log.info("Product Customer Review Return and Edit skipped based on configuration");
		}
	}

	@Test(groups = { "productcustomerReview" })
	public void productCustomerReview() throws Throwable {
		switchUserIfMulti(USERNAME2_VAL, PASSWORD2_VAL);
		log.info("--- Initiating Review Flow for: {} ---", currentEntryName);
		performReview(REVIEW_REMARKS, "Product Customer reviewed successfully");
		sa.assertAll();
	}

	@Test(groups = { "productcustomerApproveReturn_customerEdit_customerReview" })
	public void product_Customer_Approve_Return_and_Edit_and_Review() throws Throwable {
		if (PRODUCT_CUSTOMER_RETURN_ACTION_IN_APPROVE.equalsIgnoreCase("yes")) {
			switchUserIfMulti(USERNAME3_VAL, PASSWORD3_VAL);
			log.info("--- Initiating Approve Return Flow for: {} ---", currentEntryName);
			performReturnApprove(APPROVE_RETURN_REMARKS, "Product Customer returned successfully");

			switchUserIfMulti(USERNAME1_VAL, PASSWORD1_VAL);
			freezeStep();
			log.info("Opening Edit screen (After Return)");
			performEdit(EDIT_CUSTOMER_IN_APPROVE_RETURN, "Product Customer updated successfully");

			switchUserIfMulti(USERNAME2_VAL, PASSWORD2_VAL);
			log.info("--- Initiating Review Flow for: {} ---", currentEntryName);
			performReview(REVIEW_REMARKS, "Product Customer reviewed successfully");
			resumeStep();
			sa.assertAll();
		} else {
			log.info("Product Customer Approve Return and Edit and Review skipped based on configuration");
		}
	}

	@Test(groups = { "productcustomerApprove" })
	public void productCustomerApprove() throws Throwable {
		switchUserIfMulti(USERNAME3_VAL, PASSWORD3_VAL);
		log.info("--- Performing Final Approval for: {} ---", currentEntryName);
		performApprove(APPROVE_REMARKS, "Product Customer approved successfully");
		sa.assertAll();
	}

	@Test(groups = { "ClickInactive" })
	public void Click_Inactive_Local() throws Throwable {
		switchUserIfMulti(USERNAME1_VAL, PASSWORD1_VAL);
		log.info("--- Performing Inactive for: {} ---", currentEntryName);
		performInactive(INACTIVE_REMARKS, "Product Customer Inactive Request initiated successfully");
		sa.assertAll();
	}

	@Test(groups = { "Product_Customer_Inactive_Review_Reject" })
	public void product_Customer_Inactive_Review_Reject() throws Throwable {
		if (PRODUCT_CUSTOMER_INACTIVE_REJECT_IN_REVIEW_ACTION.equalsIgnoreCase("yes")) {
			switchUserIfMulti(USERNAME2_VAL, PASSWORD2_VAL);
			log.info("--- Initiating Inactive Reject In Review Flow for: {} ---", currentEntryName);
			performReviewReject(INACTIVE_REVIEW_REJECT_REMARKS, "Product Customer Reject successfully");
			sa.assertAll();
		}
	}

	@Test(groups = { "Product _Customer_Inactive_Review" })
	public void product_Customer_Inactive_Review() throws Throwable {
		switchUserIfMulti(USERNAME2_VAL, PASSWORD2_VAL);
		log.info("--- Initiating Inactive Review Flow for: {} ---", currentEntryName);
		performReview(INACTIVE_REVIEW__REMARKS, "Product Customer Reviewed successfully");
		sa.assertAll();
	}

	@Test(groups = { "Customer_Inactive_Approve_Reject" })
	public void product_Customer_Inactive_Approve_Reject() throws Throwable {
		if (PRODUCT_CUSTOMER_INACTIVE_REJECT_IN_APPROVE_ACTION.equalsIgnoreCase("yes")) {
			switchUserIfMulti(USERNAME3_VAL, PASSWORD3_VAL);
			log.info("--- Initiating Inactive Reject In Approve Flow for: {} ---", currentEntryName);
			freezeStep();
			performApproveReject(INACTIVE_APPROVE_REJECT_REMARKS, "Product Customer Reject successfully");
			sa.assertAll();
		}
	}

	@Test(groups = { "Customer_Inactive_Approve" })
	public void product_Customer_Inactive_Approve() throws Throwable {
		switchUserIfMulti(USERNAME3_VAL, PASSWORD3_VAL);
		log.info("--- Initiating Inactive Approve Flow for: {} ---", currentEntryName);
		performApprove(INACTIVE_APPROVE_REMARKS, "Product Customer Inactivated successfully");
		resumeStep();
		sa.assertAll();
	}

	@Test(groups = { "ClickActive" })
	public void Click_Active_Local() throws Throwable {
		switchUserIfMulti(USERNAME1_VAL, PASSWORD1_VAL);
		log.info("--- Performing Active for: {} ---", currentEntryName);
		performActive(ACTIVE_REMARKS, "Product Customer active request initiated successfully");
		sa.assertAll();
	}

	@Test(groups = { "Customer_Active_Review_Reject" })
	public void product_Customer_Active_Review_Reject() throws Throwable {
		if (PRODUCT_CUSTOMER_ACTIVE_REJECT_IN_REVIEW_ACTION.equalsIgnoreCase("yes")) {
			switchUserIfMulti(USERNAME2_VAL, PASSWORD2_VAL);
			log.info("--- Initiating Active Reject In Review Flow for: {} ---", currentEntryName);
			performReviewReject(ACTIVE_REVIEW_REJECT_REMARKS, "Product Customer Reject successfully");
			sa.assertAll();
		}
	}

	@Test(groups = { "Product_Customer_Active_Review" })
	public void product_Customer_Active_Review() throws Throwable {
		switchUserIfMulti(USERNAME2_VAL, PASSWORD2_VAL);
		log.info("--- Initiating Active Review Flow for: {} ---", currentEntryName);
		performReview(ACTIVE_REVIEW__REMARKS, "Product Customer Reviewed successfully");
		sa.assertAll();
	}

	@Test(groups = { "Product_Customer_Active_Approve_Reject" })
	public void product_Customer_Active_Approve_Reject() throws Throwable {
		if (PRODUCT_CUSTOMER_ACTIVE_REJECT_IN_APPROVE_ACTION.equalsIgnoreCase("yes")) {
			switchUserIfMulti(USERNAME3_VAL, PASSWORD3_VAL);
			log.info("--- Initiating Active Reject In Approve Flow for: {} ---", currentEntryName);
			freezeStep();
			performApproveReject(ACTIVE_APPROVE_REJECT_REMARKS, "Product Customer Reject successfully");
			sa.assertAll();
		}
	}

	@Test(groups = { "Product_Customer_Active_Approve" })
	public void product_Customer_Active_Approve() throws Throwable {
		switchUserIfMulti(USERNAME3_VAL, PASSWORD3_VAL);
		log.info("--- Initiating Active Approve Flow for: {} ---", currentEntryName);
		performApprove(ACTIVE_APPROVE_REMARKS, "Product Customer Activated successfully");
		resumeStep();
		sa.assertAll();
	}
	
	
	@Override
	protected void performEdit(String updateCustomer, String successMessage) throws Throwable {
		productCustomer.clickEdit(currentEntryName);
		productCustomer.waitForLoading();
		capture();
		if (updateCustomer != null && !updateCustomer.trim().isEmpty()) {
			productCustomer.selCustomer(updateCustomer);
			currentEntryName = updateCustomer;
		}
		productCustomer.clickUpdate();
		capture();
		productCustomer.enterRemarks("Made the changes");
		capture();
		productCustomer.clickSubmit();
		capture();
		productCustomer.authenticate(productCustomer.currentPassword);
		String editToast = productCustomer.waitForToast();
		productCustomer.waitForLoading();
		capture();
		sa.assertEquals(editToast, successMessage, "Updated toaster message", editToast);
	}
	
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
		Click_Inactive_Local();
	}

	@Test(groups = { "ClickInactiveRep" })
	public void Click_Inactive_2() throws Throwable {
		Click_Inactive_Local();
	}

	@Test(groups = { "ClickInactiveRep" })
	public void Click_Inactive_3() throws Throwable {
		Click_Inactive_Local();
	}

	@Test(groups = { "Product_Customer_Inactive_ReviewRep" })
	public void product_customer_Inactive_Review_1() throws Throwable {
		product_Customer_Inactive_Review();
	}

	@Test(groups = { "Product_Customer_Inactive_ReviewRep" })
	public void product_customer_Inactive_Review_2() throws Throwable {
		product_Customer_Inactive_Review();
	}

	@Test(groups = { "ClickActiveRep" })
	public void Click_Active_1() throws Throwable {
		Click_Active_Local();
	}

	@Test(groups = { "ClickActiveRep" })
	public void Click_Active_2() throws Throwable {
		Click_Active_Local();
	}

	@Test(groups = { "ClickActiveRep" })
	public void Click_Active_3() throws Throwable {
		Click_Active_Local();
	}

	@Test(groups = { "Product_Customer_Active_ReviewRep" })
	public void product_customer_Active_Review_1() throws Throwable {
		product_Customer_Active_Review();
	}

	@Test(groups = { "Product_Customer_Inactive_ReviewRep" })
	public void product_customer_Active_Review_2() throws Throwable {
		product_Customer_Active_Review();
	}


}
