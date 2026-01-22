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
		inward.Create();
		ScreenshotUtil.takeStepScreenshot("09 for step No.4.1.1");

	}

	@Test(priority = 1)
	public void Step_4_1_2() throws Throwable {

		inward.selMaterialCode(MATERIAL_CODE);
		inward.selSupplier(SUPPLIER);
		inward.selManufacturer(MANUFACTURER);
		inward.enterQuantityReceived(QUANTITY_RECEIVED);
		inward.enterPO(PO_NUMBER);
		inward.enterInvoice(INVOICE_NUMBER);
		inward.enterGateEntry(GATE_ENTRY_NUMBER);
		boolean quantityReceivedisDisplayed = inward.quantityReceivedisDisplayed();
		if (quantityReceivedisDisplayed == true) {
			inward.selQuantityReceivedIn(QUANTITY_RECEIVED_IN);

			boolean vehicleNumberisDisplayed = inward.vehicleNumberisDisplayed();
			if (vehicleNumberisDisplayed == true) {
				inward.entervehicleNumber(VEHICLE_NUMBER);
			}
		}
		if (NUMBER_OF_BATCHES != null && !NUMBER_OF_BATCHES.isEmpty()) {
			inward.enterNumberOfBatches(NUMBER_OF_BATCHES);
			System.out.println("Number of Batches: " + NUMBER_OF_BATCHES);
		} else {
			System.out.println("NUMBER_OF_BATCHES property is empty or null.");
		}
		

		String[] mfgbatchno = MFG_BATCH_NUMBER.split(",");
		String[] batchquantity = BATCH_QUANTITY.split(",");
		String[] noofpacks = NUMBER_OF_PACKS.split(",");
		

		for (int i = 0; i < mfgbatchno.length; i++) {

			inward.enterMfgBatchNo(mfgbatchno[i].trim(), i);
			inward.enterBatchQuantity(batchquantity[i].trim(), i);
			if(noofpacks != null && noofpacks.length > i) {
			inward.enterNoOfPacks(noofpacks[i].trim(), i);
			inward.clickSave(i);
			ScreenshotUtil.takeStepScreenshot("09 for step No.4.1.2");
			}
			

		}
		Thread.sleep(10000);

		ScreenshotUtil.takeStepScreenshot("10 for step No.4.1.2");
		inward.createSubmit();
		ScreenshotUtil.takeStepScreenshot("11 for step No.4.1.2");
		inward.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("12 for step No.4.1.2");
		inward.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("13 for step No.4.1.2");
		ScreenshotUtil.takeStepScreenshot("14 for step No.4.1.2");
		inward.waitForLoading();
		if (CLICK_MATERIAL_INWARD_VIEW.equalsIgnoreCase("yes")) {
			inward.clickView(TABLE_SEARCH_VALUES);
			ScreenshotUtil.takeStepScreenshot("15 for step No.4.1.2");
			int viewcount = inward.getViewPackDetailsButtonCount();
			for (int i = 0; i < viewcount; i++) {
				inward.clickViewPackDetails(i);
				ScreenshotUtil.takeStepScreenshot("16 for step No.4.1.2");
				inward.clickClosePackDetails();
				ScreenshotUtil.takeStepScreenshot("17 for step No.4.1.2");
			}

			if (DOWNLOAD_MATERIALINWARD_PDF.equalsIgnoreCase("yes")) {
				inward.click_PDF();
				ScreenshotUtil.takeStepScreenshot("18 for step No.4.1.2");
				if (CAPTURE_MATERIAL_INWARD_PDF.equalsIgnoreCase("yes")) {
					utilities.PDFUtil.openAndCapturePDF(driver, downloadPath, "Material Inward report.pdf",
							"19 for step No.4.1.2");
				}
			}

			inward.clickBack();
			ScreenshotUtil.takeStepScreenshot("16 for step No.4.1.2");
		}

		if (CLICK_MATERIAL_INWARD_EDIT.equalsIgnoreCase("yes")) {

			inward.clickEdit(TABLE_SEARCH_VALUES);
			ScreenshotUtil.takeStepScreenshot("18 for step No.4.1.2");
			inward.enterPO(EDIT_PO_NUMBER);
			ScreenshotUtil.takeStepScreenshot("19 for step No.4.1.2");
			inward.clickUpdate();
			ScreenshotUtil.takeStepScreenshot("11 for step No.4.1.2");
			inward.passWord(PASSWORD);
			ScreenshotUtil.takeStepScreenshot("12 for step No.4.1.2");
			inward.authenticateButton();
			ScreenshotUtil.takeStepScreenshot("13 for step No.4.1.2");
			ScreenshotUtil.takeStepScreenshot("14 for step No.4.1.2");
			inward.waitForLoading();
		}

		// Use the reusable PDF utility to view, scroll, and capture all pages

	}

	@Test(priority = 8)
	public void Step_4_1_3() throws Throwable {

		inward.clickActions(TABLE_SEARCH_VALUES);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.3");
		inward.clickPreInspectionReport();
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.3");

		if (inward.areDescriptionInputsPresent()) {
			if (DESCRIPTION_ANSWERS != null && !DESCRIPTION_ANSWERS.isEmpty()) {
				String[] preInspectionchecklist = DESCRIPTION_ANSWERS.split(",");
				for (int i = 0; i < preInspectionchecklist.length; i++) {
					inward.enterDescriptionInput(preInspectionchecklist[i].trim(), i);
				}
			} else {
				System.out.println("Description inputs found but PRE_INSPECTION_CHECKLIST property is empty/null.");
			}
		} else if (inward.areCheckboxesPresent()) {
			if (CHECKLIST_ANSWERS != null && !CHECKLIST_ANSWERS.isEmpty()) {
				String[] checklistAnswers = CHECKLIST_ANSWERS.split(",");
				for (int i = 0; i < checklistAnswers.length; i++) {
					inward.selectChecklistOption(i, checklistAnswers[i].trim());
				}
			} else {
				System.out.println("Checkboxes found but CHECKLIST_ANSWERS property is empty/null.");
			}
		} else {
			System.out.println("No checklist description inputs or checkboxes found on the page.");
		}
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.3");
		String projectPath = System.getProperty("user.dir");
		String uploadloadPath = projectPath + "\\src\\test\\resources\\Pdf Folder\\Dummy.pdf";

		inward.uploadFile(uploadloadPath);
		inward.enterRemarksPreInspection("Completed Pre-Inspection");
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.3");
		inward.createSubmit();
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.3");
		inward.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.3");
		inward.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.3");
		ScreenshotUtil.takeStepScreenshot("08 for step No.4.1.3");

	}

	@Test(priority = 9)
	public void Step_4_1_4() throws Throwable {
		if (PRE_INSPECTION_RETURN.equalsIgnoreCase("yes")) {
			inward.clickActions(TABLE_SEARCH_VALUES);
			ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.4");
			inward.clickReviewPreInspectionReport();
			ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.4");
			inward.enterRemarksPreInspection("Return The Pre-Inspection");
			ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.4");
			inward.clickReturn();
			ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.4");
			inward.passWord(PASSWORD);
			ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.4");
			inward.authenticateButton();
			ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.4");
			ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.4");
			inward.waitForLoading();
			inward.clickActions(TABLE_SEARCH_VALUES);
			ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.5");
			inward.clickEditPreInspectionReport();
			ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.5");

			// Edit checklist/descriptions for specific indexes
			inward.editPreInspectionChecklist(PRE_INSPECTION_EDIT_CHECKBOX_VALUES, PRE_INSPECTION_EDIT_INDEXES);
			ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.5");

			inward.enterRemarksPreInspection("Completed Pre-Inspection Again");
			ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.5");
			inward.clickUpdate();
			ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.5");
			inward.passWord(PASSWORD);
			ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.5");
			inward.authenticateButton();
			ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.5");
			ScreenshotUtil.takeStepScreenshot("08 for step No.4.1.5");
		}
	}

	@Test(priority = 11)
	public void Step_4_1_6() throws Throwable {
		inward.clickActions(TABLE_SEARCH_VALUES);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.4");
		inward.clickReviewPreInspectionReport();
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.4");
		inward.enterRemarksPreInspection("Pre-Inspection Completed and Approved");
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.4");
		inward.clickApprove();
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.4");
		inward.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.4");
		inward.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.4");
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.4");

	}

	@Test(priority = 12)
	public void Step_4_1_8() throws Throwable {
		inward.clickActions(TABLE_SEARCH_VALUES);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.8");
		inward.clickWeightVerification();
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.8");
		inward.captureWeightVerificationData(WEIGHT_VERIFICATION_BATCH, WEIGHT_VERIFICATION_BATCH_SEGREGATED_PACK);
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.8");
	}

	@Test(priority = 13)
	public void Step_4_1_9() throws Throwable {

		inward.selBalanceId(BALANCE_ID);

		inward.selWeightType(WEIGHT_TYPE);

		String[] weightasperlabel = WEIGHT_AS_PER_LABEL.split(",");
		String[] actualweight = ACTUAL_WEIGHT.split(",");
		String[] packnumbers = PACK_NUMBERS.split(",");

		inward.enterWeightsBulk(packnumbers, weightasperlabel, actualweight);
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.8");
		inward.enterRemarks("Weight Verification Completed");
		String projectPath = System.getProperty("user.dir");
		String uploadloadPath = projectPath + "\\src\\test\\resources\\Pdf Folder\\Dummy.pdf";
		inward.uploadAttachmentIfDisplayed(uploadloadPath);
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.8");
		inward.createSubmit();
		boolean startButtonsisDisplayed = inward.startButtonisDisplayed();
		if (startButtonsisDisplayed == true) {
			inward.clickStart();
			ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.8");
			inward.passWord(PASSWORD);
			ScreenshotUtil.takeStepScreenshot("08 for step No.4.1.8");
			inward.authenticateButton();
			ScreenshotUtil.takeStepScreenshot("09 for step No.4.1.8");
			inward.waitForLoading();
			inward.clickActions(TABLE_SEARCH_VALUES);
			inward.clickWeighingAreaCleaning();
			ScreenshotUtil.takeStepScreenshot("10 for step No.4.1.8");
			inward.waitForLoading();
			inward.clickEnd();

		}
		ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.8");
		inward.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("08 for step No.4.1.8");
		inward.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("09 for step No.4.1.8");
		ScreenshotUtil.takeStepScreenshot("10 for step No.4.1.8");
		inward.waitForLoading();

	}

	@Test(priority = 21)
	public void Step_4_1_21() throws Throwable {
		inward.click_profileDropdown();
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.8");
		inward.logout();
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.8");

	}

}
