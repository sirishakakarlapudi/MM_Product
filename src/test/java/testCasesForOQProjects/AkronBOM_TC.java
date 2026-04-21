package testCasesForOQProjects;

import static configData.AkronBOMData.*;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.AkronBOM;
import utilities.ScreenshotUtil;

public class AkronBOM_TC extends OQBaseModule_TC {

	private AkronBOM bom;

	@BeforeClass
	@Parameters({ "configFile" })
	public void setUp(@Optional("BOM/oqakronbom.properties") String configFile) throws Exception {
		log.info("--- Starting Akron BOM Test Case Setup with config: {} ---", configFile);
		configData.AkronBOMData.loadProperties(configFile);

		// Map static variables to base class fields
		CONFIG_NAME = CURRENT_CONFIG_NAME;
		CHROME_URL_VAL = CHROME_URL;
		APP_URL_VAL = APP_URL;
		USERNAME_VAL = USERNAME;
		PASSWORD_VAL = PASSWORD;
		USERNAME1_VAL = USERNAME4;
		PASSWORD1_VAL = PASSWORD4;
		USERNAME2_VAL = USERNAME5;
		PASSWORD2_VAL = PASSWORD5;
		USERNAME3_VAL = USERNAME6;
		PASSWORD3_VAL = PASSWORD6;
		ACTIONSPERFORMEDBY_VAL = ACTIONSPERFORMEDBY;
		PC_DB_NAME_VAL = PC_DB_NAME;
		MASTER_DB_NAME_VAL = MASTER_DB_NAME;
		MM_DB_NAME_VAL = MM_DB_NAME;
		TITLE_MODULE_VAL = "MM";
		MASTER_MODULE_VAL = MASTER_MODULE;
		SCRIPT_NUMBER_VAL = SCRIPT_NUMBER;
		VIEW_ACTION_VAL = BOM_VIEW_ACTION;

		boolean screenshotsEnabled = "yes".equalsIgnoreCase(TAKE_SCREENSHOTS);
		ScreenshotUtil.setIsEnabled(screenshotsEnabled);
		if (screenshotsEnabled) {
			ScreenshotUtil.loadTemplateForEndAppend(TEMPLATE_PATH, OUTPUT_PATH);
			ScreenshotUtil.updateHeaderCellText(ACTUALHEADER, EXPECTEDHEADER);
			ScreenshotUtil.initScript(SCRIPT_NUMBER);
		}

		browserOpen();
		bom = new AkronBOM(driver);
		this.pageObject = bom;
		bom.setTableHeaders(TABLE_HEADERS);
	}

	@Test(groups = { "Creation" })
	public void Creation_Of_BOM() throws Throwable {
		log.info("--- Executing Creation of BOM ---");
		bom.Create();
		capture();
		nextStep();
		log.info("--- Filling BOM Header Details ---");
		currentEntryName = PRODUCT_CODE;
		bom.selProductCode(PRODUCT_CODE);
		bom.location(LOCATION);
		bom.batchSize(BATCH_SIZE);
		bom.selBatchSizeUOM(BATCH_SIZE_UOM);
		bom.yieldMinRange(YIELD_RANGE_MIN);
		bom.yieldMaxRange(YIELD_RANGE_MAX);
		bom.yieldTarget(YIELD_RANGE_TARGET);
		bom.checkStandardBom(STANDARD_BOM);
		capture();

		log.info("--- Filling BOM Material Items ---");
		String[] materialCodes = MATERIAL_CODE.split(",");
		String[] standardQuantity = STANDARD_QUANTITY.split(",");
		String[] tolerance = TOLERANCE.split(",");

		for (int i = 0; i < materialCodes.length; i++) {
			bom.selMaterialCode(materialCodes[i].trim());
			bom.standardQuantity(standardQuantity[i].trim());
			bom.tolerance(tolerance[i].trim());
			if (i < materialCodes.length - 1) {
				bom.enterRow();
			}
		}
		capture();
		bom.clickSubmit();
		capture();
		bom.authenticate(bom.currentPassword);
		String authToast = bom.waitForToast();
		sa.assertEquals(authToast, "Bill Of Materials created successfully", "Created Toaster message", authToast);
		sa.assertAll();
	}

	@Test(groups = { "ClickActions" })
	public void Click_Actions1() throws Throwable {
		switchUserIfMulti(USERNAME4, PASSWORD4, TITLE_MODULE_VAL);
		log.info("--- Attempting to open Actions Menu (Index 1) for: {} ---", currentEntryName);
		bom.clickActions(currentEntryName, "1");
		log.info("Successfully opened Actions menu for {}", currentEntryName);
		capture();
	}

	@Test(groups = { "ClickActions" })
	public void Click_Actions2() throws Throwable {
		switchUserIfMulti(USERNAME5, PASSWORD5, TITLE_MODULE_VAL);
		log.info("--- Attempting to open Actions Menu (Index 1) for: {} ---", currentEntryName);
		bom.clickActions(currentEntryName, "1");
		log.info("Successfully opened Actions menu for {}", currentEntryName);
		capture();
	}

	@Test(groups = { "bomReviewReturn_bomEdit" })
	public void bom_Review_Return_and_Edit() throws Throwable {
		if (BOM_RETURN_ACTION_IN_REVIEW.equalsIgnoreCase("yes")) {
			switchUserIfMulti(USERNAME5, PASSWORD5, TITLE_MODULE_VAL);
			performReturnReview(REVIEW_RETURN_REMARKS, "Bill Of Materials returned successfully");
			switchUserIfMulti(USERNAME4, PASSWORD4, TITLE_MODULE_VAL);
			performEdit(EDIT_BATCH_SIZE_IN_REVIEW_RETURN, "Bill Of Materials updated successfully");
			sa.assertAll();
		}
	}

	@Test(groups = { "bomReview" })
	public void bomReview() throws Throwable {
		switchUserIfMulti(USERNAME5, PASSWORD5, TITLE_MODULE_VAL);
		performReview(REVIEW_REMARKS, "Bill Of Materials reviewed successfully");
		sa.assertAll();
	}

	@Test(groups = { "bomApproveReturn_bomEdit_bomReview" })
	public void bom_Approve_Return_and_Edit_and_Review() throws Throwable {
		if (BOM_RETURN_ACTION_IN_APPROVE.equalsIgnoreCase("yes")) {
			switchUserIfMulti(USERNAME6, PASSWORD6, TITLE_MODULE_VAL);
			performReturnApprove(APPROVE_RETURN_REMARKS, "Bill Of Materials returned successfully");
			switchUserIfMulti(USERNAME4, PASSWORD4, TITLE_MODULE_VAL);
			performEdit(EDIT_BATCH_SIZE_IN_APPROVE_RETURN, "Bill Of Materials updated successfully");
			switchUserIfMulti(USERNAME5, PASSWORD5, TITLE_MODULE_VAL);
			performReview(REVIEW_REMARKS, "Bill Of Materials reviewed successfully");
			sa.assertAll();
		}
	}

	@Test(groups = { "bomApprove" })
	public void bomApprove() throws Throwable {
		switchUserIfMulti(USERNAME6, PASSWORD6, TITLE_MODULE_VAL);
		performApprove(APPROVE_REMARKS, "Bill Of Materials approved successfully");
		sa.assertAll();
	}

	@Test(groups = { "BOMUpdate" })
	public void bomUpdate() throws Throwable {
		if (BOM_UPDATE_AFTER_APPROVE.equalsIgnoreCase("yes")) {
			switchUserIfMulti(USERNAME4, PASSWORD4, TITLE_MODULE_VAL);
			performUpdate("1");

			switchUserIfMulti(USERNAME5, PASSWORD5, TITLE_MODULE_VAL);
			performReview(REVIEW_REMARKS, "Bill Of Materials reviewed successfully", "2");

			switchUserIfMulti(USERNAME6, PASSWORD6, TITLE_MODULE_VAL);
			performApprove(APPROVE_REMARKS, "Bill Of Materials approved successfully", "2");
			sa.assertAll();
		}
	}

	private void performUpdate(String... actions) throws Throwable {
		bom.clickActions(combine(currentEntryName, actions));
		capture();
		bom.clickUpdate();
		bom.waitForLoading();
		capture();
		Thread.sleep(5000);
		capture();
		bom.authenticate(bom.currentPassword);
		String toast = bom.waitForToast();
		pageObject.waitForToastDisappear();
		bom.waitForLoading();
		capture();
		sa.assertEquals(toast, "Bill Of Materials version updated successfully", "Update toast", toast);
	}

	@Override
	protected void beforeRemarks() {
		nextStep();
	}

	@Override
	protected void performClickView() throws Throwable {
		bom.clickView(currentEntryName);
	}

	@Override
	protected void updateEntryName(String newBatchSize) throws Throwable {
		bom.batchSize(newBatchSize);
		currentEntryName=PRODUCT_CODE;
		
	}

}
