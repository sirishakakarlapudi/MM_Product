package testCasesForOQProjects;


import static configData.NDCData.*;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pageObjects.BasePage;

import utilities.ScreenshotUtil;

public class NDC_TC extends OQBaseModule_TC {
	private int actionIterationCount = 0;
	@BeforeClass
	@Parameters({ "configFile" })
	public void setUp(@Optional("ndc.properties") String configFile) throws Exception {
		log.info("--- Starting NDC Test Case Setup with config: {} ---", configFile);

		// Load the dynamic property file
		configData.NDCData.loadProperties(configFile);

		// Map static variables to base class fields
		CONFIG_NAME = CURRENT_CONFIG_NAME;
		CHROME_URL_VAL = CHROME_URL;
		APP_URL_VAL = APP_URL;
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
		VIEW_ACTION_VAL = NDC_VIEW_ACTION;

		boolean screenshotsEnabled = "yes".equalsIgnoreCase(TAKE_SCREENSHOTS);
		ScreenshotUtil.setIsEnabled(screenshotsEnabled);

		if (screenshotsEnabled) {
			log.info("📸 Processing screenshot template and headers...");
			ScreenshotUtil.loadTemplateForEndAppend(TEMPLATE_PATH, OUTPUT_PATH);
			ScreenshotUtil.updateHeaderCellText(ACTUALHEADER, EXPECTEDHEADER);
			ScreenshotUtil.initScript(SCRIPT_NUMBER);
		}

		browserOpen();
		this.pageObject = new BasePage(driver);
		this.pageObject.setTableHeaders(TABLE_HEADERS);
		currentEntryName = NDC_NUMBER; // ✅ Initialize for use in all test methods
		log.info("Setup completed. Screenshots enabled: {}. NDC Number: {}", screenshotsEnabled, currentEntryName);
	}

	
	@Test(groups = { "ClickInactive" })
	public void Click_Inactive() throws Throwable {
		switchUserIfMulti(USERNAME1_VAL, PASSWORD1_VAL);
		log.info("--- Performing Inactive for: {} ---", currentEntryName);
		performInactive(INACTIVE_REMARKS, "NDC Inactive Request initiated successfully");
		sa.assertAll();
	}

	@Test(groups = { "Ndc_Inactive_Review_Reject" })
	public void ndc_Inactive_Review_Reject() throws Throwable {
		if (NDC_INACTIVE_REJECT_IN_REVIEW_ACTION.equalsIgnoreCase("yes")) {
			switchUserIfMulti(USERNAME2_VAL, PASSWORD2_VAL);
			log.info("--- Initiating Inactive Reject In Review Flow for: {} ---", currentEntryName);
			performReviewReject(INACTIVE_REVIEW_REJECT_REMARKS, "NDC Reject successfully");
			sa.assertAll();
		}
	}

	
	@Test(groups = { "Ndc_Inactive_Review" })
	public void ndc_Inactive_Review() throws Throwable {
		switchUserIfMulti(USERNAME2_VAL, PASSWORD2_VAL);
		log.info("--- Initiating Inactive Review Flow for: {} ---", currentEntryName);
		performReview(INACTIVE_REVIEW__REMARKS, "NDC Reviewed successfully");
		sa.assertAll();
	}

	@Test(groups = { "Ndc_Inactive_Approve_Reject" })
	public void ndc_Inactive_Approve_Reject() throws Throwable {
		if (NDC_INACTIVE_REJECT_IN_APPROVE_ACTION.equalsIgnoreCase("yes")) {
			switchUserIfMulti(USERNAME3_VAL, PASSWORD3_VAL);
			log.info("--- Initiating Inactive Reject In Approve Flow for: {} ---", currentEntryName);
			performApproveReject(INACTIVE_APPROVE_REJECT_REMARKS, "NDC Reject successfully");
			sa.assertAll();
		}
	}

	

	@Test(groups = { "Ndc_Inactive_Approve" })
	public void ndc_Inactive_Approve() throws Throwable {
		switchUserIfMulti(USERNAME3_VAL, PASSWORD3_VAL);
		log.info("--- Initiating Inactive Approve Flow for: {} ---", currentEntryName);
		performApprove(INACTIVE_APPROVE_REMARKS, "NDC Inactivated successfully");
		sa.assertAll();
	}

	@Test(groups = { "ClickActive" })
	public void Click_Active() throws Throwable {
		switchUserIfMulti(USERNAME1_VAL, PASSWORD1_VAL);
		log.info("--- Performing Active for: {} ---", currentEntryName);
		performActive(ACTIVE_REMARKS, "NDC active request initiated successfully");
		sa.assertAll();
	}

	@Test(groups = { "Ndc_Active_Review_Reject" })
	public void ndc_Active_Review_Reject() throws Throwable {
		if (NDC_ACTIVE_REJECT_IN_REVIEW_ACTION.equalsIgnoreCase("yes")) {
			switchUserIfMulti(USERNAME2_VAL, PASSWORD2_VAL);
			log.info("--- Initiating Active Reject In Review Flow for: {} ---", currentEntryName);
			performReviewReject(ACTIVE_REVIEW_REJECT_REMARKS, "NDC Reject successfully");
			sa.assertAll();
		}
	}

	@Test(groups = { "Ndc_Active_Review" })
	public void ndc_Active_Review() throws Throwable {
		switchUserIfMulti(USERNAME2_VAL, PASSWORD2_VAL);
		log.info("--- Initiating Active Review Flow for: {} ---", currentEntryName);
		performReview(ACTIVE_REVIEW__REMARKS, "NDC Reviewed successfully");
		sa.assertAll();
	}

	@Test(groups = { "Ndc_Active_Approve_Reject" })
	public void ndc_Active_Approve_Reject() throws Throwable {
		if (NDC_ACTIVE_REJECT_IN_APPROVE_ACTION.equalsIgnoreCase("yes")) {
			switchUserIfMulti(USERNAME3_VAL, PASSWORD3_VAL);
			ScreenshotUtil.freezeStepNumbering();
			log.info("--- Initiating Active Reject In Approve Flow for: {} ---", currentEntryName);
			performApproveReject(ACTIVE_APPROVE_REJECT_REMARKS, "NDC Reject successfully");
			sa.assertAll();
		}
	}

	@Test(groups = { "Ndc_Active_Approve" })
	public void ndc_Active_Approve() throws Throwable {
		switchUserIfMulti(USERNAME3_VAL, PASSWORD3_VAL);
		log.info("--- Initiating Active Approve Flow for: {} ---", currentEntryName);
		performApprove(ACTIVE_APPROVE_REMARKS, "NDC Activated successfully");
		ScreenshotUtil.resumeStepNumbering();
		sa.assertAll();
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
		nextStep();
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


	@Override
	protected void updateEntryName(String newName) throws Throwable {
		
		
	}
	
	@Override
	protected void beforeClickActionsScreenshot() {
		actionIterationCount++;
		if(actionIterationCount==1)
		nextStep();
		
	}



}
