package testCasesForOQProjects;

import static configData.MaterialInwardForSolidData.*;

import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pageObjects.MaterialInwardForSolid;
import testBase.BaseClass;
import utilities.ScreenshotUtil;

public class Material_Inward_TC extends BaseClass {
	MaterialInwardForSolid inward;

	@BeforeClass
	public void setUp() throws Exception {

		ScreenshotUtil.loadTemplateForEndAppend(TEMPLATE_PATH, OUTPUT_PATH);
		ScreenshotUtil.updateHeaderCellText(ACTUALHEADER, EXPECTEDHEADER);

		browserOpen();
		inward = new MaterialInwardForSolid(driver);

		inward.setTableHeaders(TABLE_HEADERS);
		ScreenshotUtil.initScript("4.16");

	}

	@Test(priority = 1)
	public void Step_4_1_1() throws Throwable {
		ScreenshotUtil.nextStep();
		driver.get(APP_URL);
		ScreenshotUtil.capture();
		inward.userName(USERNAME);
		ScreenshotUtil.capture();
		inward.passWord(PASSWORD);
		ScreenshotUtil.capture();
		inward.loginButton();
		ScreenshotUtil.capture();
		ScreenshotUtil.nextStep();
		inward.click_titleMM();
		ScreenshotUtil.capture();
		inward.masterClick(MASTER_MODULE);
		ScreenshotUtil.nextStep();
		ScreenshotUtil.capture();
		// inward.Create();
		ScreenshotUtil.capture();

	}

	@Test(priority = 2)
	public void Step_4_1_2() throws Throwable {
		ScreenshotUtil.nextStep();
		inward.selMaterialCode(MATERIAL_CODE);
		inward.selSupplier(SUPPLIER);
		inward.selManufacturer(MANUFACTURER);
		inward.enterQuantityReceived(QUANTITY_RECEIVED);
		inward.enterPO(PO_NUMBER);
		inward.enterInvoice(INVOICE_NUMBER);
		inward.enterGateEntry(GATE_ENTRY_NUMBER);
		if (inward.quantityReceivedisDisplayed()) {
			inward.selQuantityReceivedIn(QUANTITY_RECEIVED_IN);

			if (inward.vehicleNumberisDisplayed()) {
				inward.entervehicleNumber(VEHICLE_NUMBER);
			}
		}

		if (inward.isNoOfPacksEnabled()) {
			inward.enterNumberOfBatches(NUMBER_OF_BATCHES);
		}

		String[] mfgbatchno = MFG_BATCH_NUMBER.split(",");
		String[] batchquantity = BATCH_QUANTITY.split(",");
		String[] noofpacks = NUMBER_OF_PACKS.split(",");

		for (int i = 0; i < mfgbatchno.length; i++) {

			inward.enterMfgBatchNo(mfgbatchno[i].trim(), i);
			inward.enterBatchQuantity(batchquantity[i].trim(), i);
			ScreenshotUtil.capture();

			if (inward.noOfPacksisDisplayed())

			{
				inward.enterNoOfPacks(noofpacks[i].trim(), i);
				inward.clickSave(i);
				ScreenshotUtil.capture();
			}

		}
		Thread.sleep(5000);

		ScreenshotUtil.capture();
		inward.createSubmit();
		ScreenshotUtil.capture();
		inward.passWord(PASSWORD);
		ScreenshotUtil.capture();
		inward.authenticateButton();
		ScreenshotUtil.capture();
		ScreenshotUtil.capture();
		inward.waitForLoading();
		if (CLICK_MATERIAL_INWARD_VIEW.equalsIgnoreCase("yes")) {
			inward.clickView(TABLE_SEARCH_VALUES);
			ScreenshotUtil.capture();
			int viewcount = inward.getViewPackDetailsButtonCount();
			for (int i = 0; i < viewcount; i++) {
				inward.clickViewPackDetails(i);
				ScreenshotUtil.capture();
				inward.clickClosePackDetails();
				ScreenshotUtil.capture();
			}

			if (DOWNLOAD_MATERIALINWARD_PDF.equalsIgnoreCase("yes")) {
				inward.click_PDF();
				ScreenshotUtil.capture();
				if (CAPTURE_MATERIAL_INWARD_PDF.equalsIgnoreCase("yes")) {
					utilities.PDFUtil.openAndCapturePDF(driver, downloadPath, "Material Inward report.pdf", null);
				}
			}
			inward.waitForLoading();
			inward.clickBack();
			ScreenshotUtil.capture();
		}

		if (CLICK_MATERIAL_INWARD_EDIT.equalsIgnoreCase("yes")) {

			inward.clickEdit(TABLE_SEARCH_VALUES);
			ScreenshotUtil.capture(); // 13
			inward.enterPO(EDIT_PO_NUMBER);
			ScreenshotUtil.capture(); // 14
			inward.clickUpdate();
			ScreenshotUtil.capture(); // 15
			inward.passWord(PASSWORD);
			ScreenshotUtil.capture(); // 16
			inward.authenticateButton();
			ScreenshotUtil.capture(); // 17
			ScreenshotUtil.capture(); // 18
			inward.waitForLoading();
		}

		// Use the reusable PDF utility to view, scroll, and capture all pages

	}

	@Test(priority = 8)
	public void Step_4_1_3() throws Throwable {
		ScreenshotUtil.nextStep(); // 4.1.3
		inward.clickActions(TABLE_SEARCH_VALUES);
		ScreenshotUtil.capture(); // 01
		inward.clickPreInspectionReport();
		ScreenshotUtil.capture(); // 02

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
		ScreenshotUtil.capture(); // 03
		String projectPath = System.getProperty("user.dir");
		String uploadloadPath = projectPath + "\\src\\test\\resources\\Pdf Folder\\Dummy.pdf";

		inward.uploadFile(uploadloadPath);
		inward.enterRemarksPreInspection("Completed Pre-Inspection");
		ScreenshotUtil.capture(); // 04
		inward.createSubmit();
		ScreenshotUtil.capture(); // 05
		inward.passWord(PASSWORD);
		ScreenshotUtil.capture(); // 06
		inward.authenticateButton();
		ScreenshotUtil.capture(); // 07
		ScreenshotUtil.capture(); // 08

	}

	@Test(priority = 9)
	public void Step_4_1_4() throws Throwable {
		ScreenshotUtil.nextStep(); // 4.1.4
		if (PRE_INSPECTION_RETURN.equalsIgnoreCase("yes")) {
			inward.clickActions(TABLE_SEARCH_VALUES);
			ScreenshotUtil.capture(); // 01
			inward.clickReviewPreInspectionReport();
			ScreenshotUtil.capture(); // 02
			inward.enterRemarksPreInspection("Return The Pre-Inspection");
			ScreenshotUtil.capture(); // 03
			inward.clickReturn();
			ScreenshotUtil.capture(); // 04
			inward.passWord(PASSWORD);
			ScreenshotUtil.capture(); // 05
			inward.authenticateButton();
			ScreenshotUtil.capture(); // 06
			ScreenshotUtil.capture(); // 07
			inward.waitForLoading();

			// Start of Step 4.1.5 logic
			ScreenshotUtil.nextStep();
			inward.clickActions(TABLE_SEARCH_VALUES);
			ScreenshotUtil.capture(); // 01
			inward.clickEditPreInspectionReport();
			ScreenshotUtil.capture();

			// Edit checklist/descriptions for specific indexes
			inward.editPreInspectionChecklist(PRE_INSPECTION_EDIT_CHECKBOX_VALUES, PRE_INSPECTION_EDIT_INDEXES);
			ScreenshotUtil.capture();

			inward.enterRemarksPreInspection("Completed Pre-Inspection Again");
			ScreenshotUtil.capture();
			inward.clickUpdate();
			ScreenshotUtil.capture();
			inward.passWord(PASSWORD);
			ScreenshotUtil.capture();
			inward.authenticateButton();
			ScreenshotUtil.capture();
			ScreenshotUtil.capture();
		}
	}

	@Test(priority = 10)

	public void Step_4_1_6_1() throws Throwable {

		ScreenshotUtil.nextStep();
		if (PRE_INSPECTION_REJECT.equalsIgnoreCase("yes"))
			inward.clickActions(TABLE_SEARCH_VALUES);
		ScreenshotUtil.capture();
		inward.clickReviewPreInspectionReport();
		ScreenshotUtil.capture();
		inward.clickReject();

		String[] batchPackPairs = BATCH_NUMBERS_PACK_NUMBERS.split(",");
		String[] reasons = BATCH_REJECTED_REASONS.split(",");

		String[] batchNumbers = new String[batchPackPairs.length];
		String[] packNumbers = new String[batchPackPairs.length];

		for (int i = 0; i < batchPackPairs.length; i++) {
			String currentPair = batchPackPairs[i].trim();
			if (currentPair.contains(":")) {
				String[] parts = currentPair.split(":");
				batchNumbers[i] = parts[0].trim();
				packNumbers[i] = parts[1].trim();
			} else {
				batchNumbers[i] = currentPair;
				packNumbers[i] = ""; // Empty string signals no pack number
			}
		}

		inward.enterRejectedBatchDetailsBulk(batchNumbers, packNumbers, reasons);
		ScreenshotUtil.capture();
		inward.enterRemarksInRejectedBatchDetails("REJECTED_REMARKS");
		ScreenshotUtil.capture();
		inward.createSubmit();
		ScreenshotUtil.capture();
		inward.passWord(PASSWORD);
		ScreenshotUtil.capture();
		inward.authenticateButton();
		ScreenshotUtil.capture();
		ScreenshotUtil.capture();
		inward.waitForLoading();
		inward.clickActions(TABLE_SEARCH_VALUES);
		inward.clickQaReviewPreinspectionReport();
		ScreenshotUtil.capture();
		inward.waitForLoading();
		ScreenshotUtil.capture();

		// Case 1: QA Review for Full Rejection section
		if (QA_FULL_REJECT_BATCHES != null && !QA_FULL_REJECT_BATCHES.isEmpty()) {
			System.out.println(">>> Processing QA Full Rejection for: " + QA_FULL_REJECT_BATCHES);
			inward.checkFullReject();
			inward.selFullReject(QA_FULL_REJECT_BATCHES);
			inward.enterFullRejectRemarks(QA_FULL_REJECT_REASON);
			ScreenshotUtil.capture();
		}

		// Case 2: QA Review for Partial Rejection table (Row-level)
		if (QA_PARTIAL_REJECTION_DATA != null && !QA_PARTIAL_REJECTION_DATA.isEmpty()) {
			System.out.println(">>> Processing QA Partial Rejection table data: " + QA_PARTIAL_REJECTION_DATA);
			String[] partialRows = QA_PARTIAL_REJECTION_DATA.split(",");
			for (String row : partialRows) {
				String[] data = row.split(":");
				// Layout: Batch : Pack : Action (R/A/S) : SegRemarks
				if (data.length >= 3) {
					String batch = data[0].trim();
					String pack = data[1].trim();
					String action = data[2].trim();
					String segRemarks = (data.length > 3) ? data[3].trim() : "";
					inward.processPartialRejection(batch, pack, action, segRemarks);
				}
			}
			ScreenshotUtil.capture();
		}
		inward.enterRemarksPreInspection("QA Review Completed");
		inward.createSubmit();
		ScreenshotUtil.capture();
		inward.passWord(PASSWORD);
		ScreenshotUtil.capture();
		inward.authenticateButton();
		ScreenshotUtil.capture();
		ScreenshotUtil.capture();
		inward.waitForLoading();

	}

	@Test(priority = 11)
	public void Step_4_1_6() throws Throwable {
		ScreenshotUtil.nextStep(); // 4.1.6
		inward.clickActions(TABLE_SEARCH_VALUES);
		ScreenshotUtil.capture(); // 01 for step No.4.1.6
		inward.clickReviewPreInspectionReport();
		ScreenshotUtil.capture(); // 02
		inward.enterRemarksPreInspection("Pre-Inspection Completed and Approved");
		ScreenshotUtil.capture(); // 03
		inward.clickApprove();
		ScreenshotUtil.capture(); // 04
		inward.passWord(PASSWORD);
		ScreenshotUtil.capture(); // 05
		inward.authenticateButton();
		ScreenshotUtil.capture(); // 06
		ScreenshotUtil.capture(); // 07

	}

	@Test(priority = 12)
	public void Step_4_1_7() throws Throwable {

		if (VIEW_PRE_INSPECTION_REPORT.equalsIgnoreCase("yes")) {
			inward.clickActions(TABLE_SEARCH_VALUES);
			ScreenshotUtil.capture();
			inward.clickViewPreInspectionReport();
			ScreenshotUtil.capture();
			if (DOWNLOAD_PRE_INSPECTION_REPORT_PDF.equalsIgnoreCase("yes")) {
				inward.click_PDF();
				ScreenshotUtil.capture();
				if (CAPTURE_PRE_INSPECTION_REPORT_PDF.equalsIgnoreCase("yes")) {
					utilities.PDFUtil.openAndCapturePDF(driver, downloadPath, "Pre-Inspection report.pdf", null);
				}

			}
			inward.clickBack();
			ScreenshotUtil.capture();
			inward.waitForLoading();

		}

		if (QA_REVIEW_VIEW.equalsIgnoreCase("yes")) {
			inward.clickActions(TABLE_SEARCH_VALUES);
			ScreenshotUtil.capture();
			inward.clickQAReviewView();
			ScreenshotUtil.capture();
			inward.clickBack();
			ScreenshotUtil.capture();
			inward.waitForLoading();
		}

	}

	@Test(priority = 13)
	public void Step_4_1_8() throws Throwable {
		ScreenshotUtil.nextStep(); // Automatically handles the next number
		// 1. Initial Actions click
		if (!inward.weightVerificationButtonisDisplayed()) {
			inward.clickActions(TABLE_SEARCH_VALUES);
		}
		ScreenshotUtil.capture(); // 01

		if (inward.weightVerificationButtonisDisplayed()) {
			// Open modal to see which flow we have
			inward.clickWeightVerification();
			ScreenshotUtil.capture(); // 02

			if (inward.labelInhouseBatchNumberisDisplayed()) {
				System.out.println("📦 Multi-Batch Flow (Below 5000kg) detected.");
				String[] mfgBatches = WEIGHT_VERIFICATION_BATCH.split(",");

				for (int i = 0; i < mfgBatches.length; i++) {
					String mfgBatch = mfgBatches[i].trim();

					if (i > 0) {
						if (!inward.weightVerificationButtonisDisplayed()) {
							inward.clickActions(TABLE_SEARCH_VALUES);
						}
						inward.clickWeightVerification();
					}

					List<String> inhouseBatches = inward.getInhouseBatches(mfgBatch);
					if (inhouseBatches.isEmpty()) {
						inward.clickCloseIconInPopUp();
						throw new RuntimeException("❌ No inhouse batches found for: " + mfgBatch);
					}

					for (int j = 0; j < inhouseBatches.size(); j++) {
						String inhouse = inhouseBatches.get(j);
						boolean cleaningPerformed = false;

						if (j > 0) {
							if (!inward.weightVerificationButtonisDisplayed()) {
								inward.clickActions(TABLE_SEARCH_VALUES);
							}
							inward.clickWeightVerification();
						}

						inward.clickWeightVerificationForInhouse(inhouse);
						ScreenshotUtil.capture();

						inward.selBalanceId(BALANCE_ID);
						inward.selWeightType(WEIGHT_TYPE);

						String[] weightasperlabel = WEIGHT_AS_PER_LABEL.split(",");
						String[] actualweight = ACTUAL_WEIGHT.split(",");
						String[] packnumbers = PACK_NUMBERS.split(",");

						inward.enterWeightsBulk(packnumbers, weightasperlabel, actualweight);
						inward.enterRemarks("Weight Verification Completed for batch: " + inhouse);

						String projectPath = System.getProperty("user.dir");
						String uploadloadPath = projectPath + "\\src\\test\\resources\\Pdf Folder\\Dummy.pdf";
						inward.uploadAttachmentIfDisplayed(uploadloadPath);

						inward.createSubmit();

						if (inward.startButtonisDisplayed()) {
							cleaningPerformed = true;
							inward.clickStart();
							ScreenshotUtil.capture();
							inward.passWord(PASSWORD);
							inward.authenticateButton();
							inward.waitForLoading();

							if (!inward.weighingAreaCleaningButtonisDisplayed()) {
								inward.clickActions(TABLE_SEARCH_VALUES);
							}

							inward.clickWeighingAreaCleaning();
							inward.waitForLoading();
							inward.clickEnd();
							inward.passWord(PASSWORD);
							inward.authenticateButton();
						} else {
							inward.passWord(PASSWORD);
							inward.authenticateButton();
						}
						inward.waitForLoading();

						// Post-verification View Report Flow
						if (VIEW_WEIGHT_VERIFICATION.equalsIgnoreCase("yes")) {
							inward.clickActions(TABLE_SEARCH_VALUES);
							inward.clickViewWeightVerification();
							inward.waitForLoading();
							inward.clickViewIconInRow(inhouse);
							ScreenshotUtil.capture();

							if (DOWNLOAD_WEIGHT_VERIFICATION_PDF.equalsIgnoreCase("yes")) {
								inward.click_PDF();
								ScreenshotUtil.capture();
								if (CAPTURE_WEIGHT_VERIFICATION_PDF.equalsIgnoreCase("yes")) {
									utilities.PDFUtil.openAndCapturePDF(driver, downloadPath,
											"Weight Verification report.pdf", null);
								}
							}
							inward.clickBack();
							inward.waitForLoading();
						}

						// Post-verification View Cleaning Flow
						if (cleaningPerformed && VIEW_CLEANING_AREA.equalsIgnoreCase("yes")) {
							inward.clickActions(TABLE_SEARCH_VALUES);
							if (inward.viewWeighingAreaCleaningButtonisDisplayed()) {
								inward.clickViewWeighingAreaCleaning();
								ScreenshotUtil.capture();
								inward.clickCloseModal();
								ScreenshotUtil.capture();
							}
						}
					}
				}
			} else {
				System.out.println("⚖️ Simple Flow (Above 5000kg) detected.");
				inward.enterGrossWeight(GROSS_WEIGHT);
				inward.enterTareWeight(TARE_WEIGHT);
				inward.enterRemarks("Completed Simple Flow Verification");
				inward.createSubmit();
				inward.passWord(PASSWORD);
				inward.authenticateButton();
				inward.waitForLoading();

				if (VIEW_WEIGHT_VERIFICATION.equalsIgnoreCase("yes")) {
					inward.clickActions(TABLE_SEARCH_VALUES);
					inward.clickViewWeightVerification();
					ScreenshotUtil.capture();
					inward.clickCloseModal();
					ScreenshotUtil.capture();
				}
			}
		}
	}

	@Test(priority = 16)
	public void Step_4_1_15() throws Throwable {

		if (REJECTED_BATCH_DETAILS_FLOW.equalsIgnoreCase("yes")) {
			String[] invoices = REJECTED_BATCH_DETAILS_INVOICE_NUMBER.split(",");
			String[] remarksList = REJECTED_BATCH_DETAILS_REMARKS.split(",");
			int iteration = 0;

			while (true) {
				inward.clickActions(TABLE_SEARCH_VALUES);

				// Decision Point 1: Check if the button is even there
				if (!inward.rejectedBatchDetailsButtonisDisplayed()) {
					System.out.println(">>> No more rejected batches found in Actions menu. Flow completed.");
					inward.clickActions(TABLE_SEARCH_VALUES); // Close menu
					break;
				}

				inward.clickRejectedBatchDetails();
				inward.waitForLoading();
				Thread.sleep(800);

				String currentInvoice = (iteration < invoices.length) ? invoices[iteration].trim() : invoices[0].trim();
				String currentRemarks = (iteration < remarksList.length) ? remarksList[iteration].trim()
						: remarksList[0].trim();

				inward.selectAndReturnBatch(0);
				inward.enterInvoiceNumber(currentInvoice);
				inward.enterRejectedStockReturnRemarks(currentRemarks);
				ScreenshotUtil.capture();
				Thread.sleep(3000);

				inward.clickSubmit();
				inward.passWord(PASSWORD);
				inward.authenticateButton();

				inward.waitForLoading();

				iteration++;
			}
			ScreenshotUtil.capture();
		}

	}

	@Test(priority = 21)
	public void Step_4_1_21() throws Throwable {
		ScreenshotUtil.nextStep();
		inward.click_profileDropdown();
		ScreenshotUtil.capture();
		inward.logout();
		ScreenshotUtil.capture();

	}

}
