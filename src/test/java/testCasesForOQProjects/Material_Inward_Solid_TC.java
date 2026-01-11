package testCasesForOQProjects;

import static configData.MaterialInwardForSolidData.*;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pageObjects.MaterialInwardForSolid;
import testBase.BaseClass;
import utilities.ScreenshotUtil;

public class Material_Inward_Solid_TC extends BaseClass {
	MaterialInwardForSolid inward;

	@BeforeClass
	public void setUp() throws Exception {

		ScreenshotUtil.loadTemplateForEndAppend(TEMPLATE_PATH, OUTPUT_PATH);
		ScreenshotUtil.updateHeaderCellText(ACTUALHEADER, EXPECTEDHEADER);

		browserOpen();
		inward = new MaterialInwardForSolid(driver);

		inward.setTableHeaders(TABLE_HEADERS);

	}

	@Test(priority = 1)
	public void Step_4_1_1() throws Throwable {
		/*
		 * driver.get("https://www.google.co.in/"); WaitUtil.waitForVisible(driver,
		 * inward.getSearchBox(), 10); inward.searchBox(APP_URL);
		 * WaitUtil.waitForPageLoad(driver, 10);
		 * ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.1");
		 */
		driver.get(APP_URL);
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.1");
		inward.userName(USERNAME);
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.1");
		inward.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.1");
		inward.loginButton();
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.1");
		inward.click_titleMM();
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.1");
		inward.masterClick(MASTER_MODULE);
		ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.1");
		// inward.Create();
		// ScreenshotUtil.takeStepScreenshot("09 for step No.4.1.1");

	}

	/*
	 * @Test(priority = 1)
	 * public void Step_4_1_2() throws Throwable {
	 * 
	 * 
	 * inward.selMaterialCode(MATERIAL_CODE);
	 * inward.selSupplier(SUPPLIER);
	 * inward.selManufacturer(MANUFACTURER);
	 * inward.enterQuantityReceived(QUANTITY_RECEIVED);
	 * inward.enterPO(PO_NUMBER);
	 * inward.enterInvoice(INVOICE_NUMBER);
	 * inward.enterGateEntry(GATE_ENTRY_NUMBER);
	 * inward.enterNumberOfBatches(NUMBER_OF_BATCHES);
	 * 
	 * 
	 * String[] mfgbatchno = MFG_BATCH_NUMBER.split(",");
	 * String[] batchquantity=BATCH_QUANTITY.split(",");
	 * String[] noofpacks = NUMBER_OF_PACKS.split(",");
	 * 
	 * 
	 * 
	 * for (int i = 0; i < mfgbatchno.length; i++) {
	 * 
	 * inward.enterMfgBatchNo(mfgbatchno[i].trim(), i);
	 * inward.enterBatchQuantity(batchquantity[i].trim(), i);
	 * inward.enterNoOfPacks(noofpacks[i].trim(), i); inward.clickSave(i);
	 * 
	 * }
	 * Thread.sleep(10000);
	 * 
	 * ScreenshotUtil.takeStepScreenshot("10 for step No.4.1.2");
	 * inward.createSubmit_editUpdate();
	 * ScreenshotUtil.takeStepScreenshot("11 for step No.4.1.2");
	 * inward.passWord(PASSWORD);
	 * ScreenshotUtil.takeStepScreenshot("12 for step No.4.1.2");
	 * inward.authenticateButton();
	 * ScreenshotUtil.takeStepScreenshot("13 for step No.4.1.2");
	 * ScreenshotUtil.takeStepScreenshot("14 for step No.4.1.2");
	 * 
	 * 
	 * inward.clickView(TABLE_SEARCH_VALUES);
	 * ScreenshotUtil.takeStepScreenshot("15 for step No.4.1.2");
	 * int viewcount = inward.getViewPackDetailsButtonCount();
	 * for (int i = 0; i < viewcount; i++) {
	 * inward.clickViewPackDetails(i);
	 * ScreenshotUtil.takeStepScreenshot("16 for step No.4.1.2");
	 * inward.clickClosePackDetails();
	 * ScreenshotUtil.takeStepScreenshot("17 for step No.4.1.2");
	 * }
	 * inward.click_PDF();
	 * ScreenshotUtil.takeStepScreenshot("18 for step No.4.1.2");
	 * inward.clickBack();
	 * ScreenshotUtil.takeStepScreenshot("16 for step No.4.1.2");
	 * 
	 * inward.clickEdit(TABLE_SEARCH_VALUES);
	 * ScreenshotUtil.takeStepScreenshot("18 for step No.4.1.2");
	 * inward.enterPO(EDIT_PO_NUMBER);
	 * ScreenshotUtil.takeStepScreenshot("19 for step No.4.1.2");
	 * inward.clickUpdate();
	 * ScreenshotUtil.takeStepScreenshot("11 for step No.4.1.2");
	 * inward.passWord(PASSWORD);
	 * ScreenshotUtil.takeStepScreenshot("12 for step No.4.1.2");
	 * inward.authenticateButton();
	 * ScreenshotUtil.takeStepScreenshot("13 for step No.4.1.2");
	 * ScreenshotUtil.takeStepScreenshot("14 for step No.4.1.2");
	 * 
	 * 
	 * // Use the reusable PDF utility to view, scroll, and capture all pages
	 * utilities.PDFUtil.openAndCapturePDF(driver, downloadPath,
	 * "Material Inward report.pdf", "19 for step No.4.1.2");
	 * 
	 * }
	 */

	/*
	 * @Test(priority = 8)
	 * public void Step_4_1_3() throws Throwable {
	 * 
	 * inward.clickActions(TABLE_SEARCH_VALUES);
	 * ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.3");
	 * inward.clickPreInspectionReport();
	 * ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.3");
	 * 
	 * if (inward.areDescriptionInputsPresent()) {
	 * if (PRE_INSPECTION_CHECKLIST != null && !PRE_INSPECTION_CHECKLIST.isEmpty())
	 * {
	 * String[] preInspectionchecklist = PRE_INSPECTION_CHECKLIST.split(",");
	 * for (int i = 0; i < preInspectionchecklist.length; i++) {
	 * inward.enterDescriptionInput(preInspectionchecklist[i].trim(), i);
	 * }
	 * } else {
	 * System.out.
	 * println("Description inputs found but PRE_INSPECTION_CHECKLIST property is empty/null."
	 * );
	 * }
	 * } else if (inward.areCheckboxesPresent()) {
	 * if (CHECKLIST_ANSWERS != null && !CHECKLIST_ANSWERS.isEmpty()) {
	 * String[] checklistAnswers = CHECKLIST_ANSWERS.split(",");
	 * for (int i = 0; i < checklistAnswers.length; i++) {
	 * inward.selectChecklistOption(i, checklistAnswers[i].trim());
	 * }
	 * } else {
	 * System.out.
	 * println("Checkboxes found but CHECKLIST_ANSWERS property is empty/null.");
	 * }
	 * } else {
	 * System.out.
	 * println("No checklist description inputs or checkboxes found on the page.");
	 * }
	 * ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.3");
	 * String projectPath = System.getProperty("user.dir");
	 * String uploadloadPath = projectPath +
	 * "\\src\\test\\resources\\Pdf Folder\\Dummy.pdf";
	 * 
	 * inward.uploadFile(uploadloadPath);
	 * inward.enterRemarksPreInspection("Completed Pre-Inspection");
	 * ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.3");
	 * inward.createSubmit();
	 * ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.3");
	 * inward.passWord(PASSWORD);
	 * ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.3");
	 * inward.authenticateButton();
	 * ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.3");
	 * ScreenshotUtil.takeStepScreenshot("08 for step No.4.1.3");
	 * 
	 * }
	 * 
	 * @Test(priority = 9)
	 * public void Step_4_1_4() throws Throwable {
	 * 
	 * inward.clickActions(TABLE_SEARCH_VALUES);
	 * ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.4");
	 * inward.clickReviewPreInspectionReport();
	 * ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.4");
	 * inward.enterRemarksPreInspection("Return The Pre-Inspection");
	 * ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.4");
	 * inward.clickReturn();
	 * ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.4");
	 * inward.passWord(PASSWORD);
	 * ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.4");
	 * inward.authenticateButton();
	 * ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.4");
	 * ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.4");
	 * 
	 * }
	 * 
	 * @Test(priority = 10)
	 * public void Step_4_1_5() throws Throwable {
	 * inward.clickActions(TABLE_SEARCH_VALUES);
	 * ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.5");
	 * inward.clickEditPreInspectionReport();
	 * ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.5");
	 * 
	 * // Edit checklist/descriptions for specific indexes
	 * inward.editPreInspectionChecklist(PRE_INSPECTION_EDIT_VALUES,
	 * PRE_INSPECTION_EDIT_INDEXES);
	 * ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.5");
	 * 
	 * inward.enterRemarksPreInspection("Completed Pre-Inspection Again");
	 * ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.5");
	 * inward.clickUpdate();
	 * ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.5");
	 * inward.passWord(PASSWORD);
	 * ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.5");
	 * inward.authenticateButton();
	 * ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.5");
	 * ScreenshotUtil.takeStepScreenshot("08 for step No.4.1.5");
	 * }
	 */
	/*
	 * @Test(priority = 11) public void Step_4_1_6() throws Throwable {
	 * inward.clickActions(TABLE_SEARCH_VALUES);
	 * ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.4");
	 * inward.clickReviewPreInspectionReport();
	 * ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.4");
	 * inward.enterRemarksPreInspection("Pre-Inspection Completed and Approved");
	 * ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.4");
	 * inward.clickApprove();
	 * ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.4");
	 * inward.passWord(PASSWORD);
	 * ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.4");
	 * inward.authenticateButton();
	 * ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.4");
	 * ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.4");
	 * 
	 * }
	 */

	@Test(priority = 12)
	public void Step_4_1_7() throws Throwable {
		inward.clickWeightVerificationForRow();

		// Get mfg batches from properties
		String[] mfgBatches = MFG_BATCH_NUMBER.split(",");

		for (String batch : mfgBatches) {
			String currentBatch = batch.trim();

			// 1. Capture the data (Inhouse + GRN) for this specific batch
			inward.captureWeightVerificationData(currentBatch);

			// 2. Click the Weight Verification button for THIS specific row

			ScreenshotUtil.takeStepScreenshot("WeightVerification_" + currentBatch);
		}

		// Information is now stored in runtimeData.properties for future steps
		System.out.println("🚀 All batch data captured and stored in file.");
	}
	@Test(priority = 13)
	public void Step_4_1_8() throws Throwable {
		
		
		
	}
	
	
	
	@Test(priority = 21)
	public void Step_4_1_9() throws Throwable {
		inward.click_profileDropdown();
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.8");
		inward.logout();
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.8");

	}

}
