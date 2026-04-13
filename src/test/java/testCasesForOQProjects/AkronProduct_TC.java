package testCasesForOQProjects;

import static configData.AkronProductData.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pageObjects.AkronQuickProduct;
import utilities.ScreenshotUtil;

public class AkronProduct_TC extends OQBaseModule_TC {
	AkronQuickProduct product;

	private int inactiveIterationCount = 0;
	private int activeIterationCount = 0;
	private int actionIterationCount = 0;
	private String currentReviewContext = "";
	private String currentApproveContext = "";

	@BeforeClass
	@Parameters({ "configFile" })
	public void setUp(@Optional("akronproduct.properties") String configFile) throws Exception {
		log.info("--- Starting Akron Product Test Case Setup with config: {} ---", configFile);

		// Load the dynamic property file
		configData.AkronProductData.loadProperties(configFile);

		// Map to OQBaseModule_TC required fields
		CONFIG_NAME = configData.AkronProductData.CURRENT_CONFIG_NAME;
		CHROME_URL_VAL = configData.AkronProductData.CHROME_URL;
		APP_URL_VAL = configData.AkronProductData.APP_URL;
		USERNAME_VAL = configData.AkronProductData.USERNAME;
		PASSWORD_VAL = configData.AkronProductData.PASSWORD;
		USERNAME1_VAL = configData.AkronProductData.USERNAME1;
		PASSWORD1_VAL = configData.AkronProductData.PASSWORD1;
		USERNAME2_VAL = configData.AkronProductData.USERNAME2;
		PASSWORD2_VAL = configData.AkronProductData.PASSWORD2;
		USERNAME3_VAL = configData.AkronProductData.USERNAME3;
		PASSWORD3_VAL = configData.AkronProductData.PASSWORD3;
		ACTIONSPERFORMEDBY_VAL = configData.AkronProductData.ACTIONSPERFORMEDBY;
		PC_DB_NAME_VAL = configData.AkronProductData.PC_DB_NAME;
		MASTER_DB_NAME_VAL = configData.AkronProductData.MASTER_DB_NAME;
		MM_DB_NAME_VAL = configData.AkronProductData.MM_DB_NAME;
		MASTER_MODULE_VAL = configData.AkronProductData.MASTER_MODULE;
		SUB_MASTER_MODULE_VAL = configData.AkronProductData.SUB_MASTER_MODULE;
		SCRIPT_NUMBER_VAL = configData.AkronProductData.SCRIPT_NUMBER;

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
		this.pageObject = product; // IMPORTANT: Initialize Base Page Object

		// Keep for custom workflows
		currentEntryName = PRODUCT_NAME; // Set Base Field

		log.info("Setup completed. Screenshots enabled: {}. Product Name: {}", screenshotsEnabled, currentEntryName);
	}

	@Override
	protected void performClickView() throws Throwable {
		product.clickPendingView(currentEntryName);
	}

	@Test(groups = { "AddNDC" })
	public void Click_Add_NDC() throws Throwable {

		switchUserIfMulti(USERNAME1_VAL, PASSWORD1_VAL);
		log.info("--- Performing Add NDC for: {} ---", currentEntryName);
		product.waitForLoading();
		nextStep();
		log.info("Opening actions menu for Add NDC");
		product.clickActions(currentEntryName);
		capture();
		log.info("Clicking Add NDC button in the list");
		product.clickAddNDC();
		capture();
		product.enterRow_for_addndc();
		String[] ndcNumbers = ADD_NDC_NDC_NUMBER.split(",");
		String[] shortCodes = ADD_NDC_SHORT_CODE.split(",");
		String[] packSizes = ADD_NDC_PACK_SIZE.split(",");
		String[] ndcDescriptions = ADD_NDC_NDC_DESCRIPTION.split(",");
		String[] uoms = ADD_NDC_UOM.split(",");
		String[] gtnNumbers = ADD_NDC_GTN_NUMBER.split(",");

		for (int i = 0; i < ndcNumbers.length; i++) {

			product.ndcNumber(ndcNumbers[i].trim());
			product.shortCode(shortCodes[i].trim());

			String uomToUse = (uoms.length == 1) ? uoms[0].trim() : uoms[i].trim();
			product.selUOM(uomToUse);

			product.packSize(packSizes[i].trim());
			product.gtnNumber(gtnNumbers[i].trim());

			if (ndcDescriptions.length > i && !ndcDescriptions[i].trim().isEmpty()) {
				product.ndcDesc(ndcDescriptions[i].trim());
			}

			if (i < ndcNumbers.length - 1) {
				product.enterRow_for_addndc();
			}
		}

		capture();
		product.clickUpdate();
		log.info("Added new NDC");
		capture();
		product.authenticate(product.currentPassword);
		String addndcToast = product.waitForToast();
		product.waitForLoading();
		sa.assertEquals(addndcToast, "NDC added request initiated successfully", "When new NDC added toaster messege",
				addndcToast);
		sa.assertAll();

	}

	@Test(groups = { "Click_Review_Return_And_Edit_For_ADD_NDC" })
	public void Click_Review_Return_And_Edit_For_Add_NDC() throws Throwable {
		if (PRODUCT_INACTIVE_REJECT_IN_REVIEW_ACTION != null
				&& PRODUCT_INACTIVE_REJECT_IN_REVIEW_ACTION.equalsIgnoreCase("yes")) {

			switchUserIfMulti(USERNAME2_VAL, PASSWORD2_VAL);

			log.info("--- Initiating Add NDC Review Return Flow for: {} ---", currentEntryName);
			performReturnReview(REVIEW_RETURN_REMARKS, "Product returned successfully");
			switchUserIfMulti(USERNAME1_VAL, PASSWORD1_VAL);
			log.info("Opening Edit NDC screen (After Review Return)");
			product.clickEdit(currentEntryName);
			product.waitForLoading();
			capture();

			log.info("Making changes based on Edit action:{}", ADD_NDC_EDIT_ACTION_AFTER_REVIEW_RETURN);

			switch (ADD_NDC_EDIT_ACTION_AFTER_REVIEW_RETURN.toUpperCase()) {

			case "ADD":
				log.info("Executing Add Flow");
				product.enterRow_for_addndc();

				String[] add_ndcNumbers = ADD_NDC_EDIT_NEW_NDC_NUMBER_AFTER_REVIEW_RETURN.split(",");
				String[] add_shortCodes = ADD_NDC_EDIT_NEW_SHORT_CODE_AFTER_REVIEW_RETURN.split(",");
				String[] add_packSizes = ADD_NDC_EDIT_NEW_PACK_SIZE_AFTER_REVIEW_RETURN.split(",");
				String[] add_ndcDescriptions = ADD_NDC_EDIT_NEW_NDC_DESCRIPTION_AFTER_REVIEW_RETURN.split(",");
				String[] uoms = ADD_NDC_UOM.split(",");
				String[] add_gtnNumbers = ADD_NDC_EDIT_NEW_GTN_NUMBER_AFTER_REVIEW_RETURN.split(",");

				for (int i = 0; i < add_ndcNumbers.length; i++) {

					product.ndcNumber(add_ndcNumbers[i].trim());
					product.shortCode(add_shortCodes[i].trim());

					String uomToUse = (uoms.length == 1) ? uoms[0].trim() : uoms[i].trim();
					product.selUOM(uomToUse);

					product.packSize(add_packSizes[i].trim());
					product.gtnNumber(add_gtnNumbers[i].trim());

					if (add_ndcDescriptions.length > i && !add_ndcDescriptions[i].trim().isEmpty()) {
						product.ndcDesc(add_ndcDescriptions[i].trim());
					}

					if (i < add_ndcNumbers.length - 1) {
						product.enterRow_for_addndc();
					}
				}
				capture();
				log.info("Added new NDC row");
				break;

			case "EDIT":
				log.info("Executing Edit Flow");

				String[] edit_ndcNumbers = ADD_NDC_EDIT_EXISTING_NDC_NUMBER_AFTER_REVIEW_RETURN.split(",");
				String[] edit_shortCodes = ADD_NDC_EDIT_EXISTING_SHORT_CODE_AFTER_REVIEW_RETURN.split(",");
				String[] edit_packSizes = ADD_NDC_EDIT_EXISTING_PACK_SIZE_AFTER_REVIEW_RETURN.split(",");
				String[] edit_ndcDescriptions = ADD_NDC_EDIT_EXISTING_NDC_DESCRIPTION_AFTER_REVIEW_RETURN.split(",");
				String[] edit_gtnNumbers = ADD_NDC_EDIT_EXISTING_GTN_NUMBER_AFTER_REVIEW_RETURN.split(",");
				String[] indexArr = ADD_NDC_EDIT_EXISTING_INDEX_AFTER_REVIEW_RETURN.split(",");

				for (int i = 0; i < indexArr.length; i++) {

					String target = indexArr[i].trim();
					int rowIndex = -1;
					try {
						rowIndex = Integer.parseInt(target);
					} catch (NumberFormatException e) {
						rowIndex = product.getRowIndexByNDCNumber(target);
					}

					if (edit_ndcNumbers.length > i && !edit_ndcNumbers[i].trim().isEmpty()) {
						product.ndcNumber_AtIndex(edit_ndcNumbers[i].trim(), rowIndex);
					}

					if (edit_shortCodes.length > i && !edit_shortCodes[i].trim().isEmpty()) {
						product.shortCode_AtIndex(edit_shortCodes[i].trim(), rowIndex);
					}

					if (edit_packSizes.length > i && !edit_packSizes[i].trim().isEmpty()) {
						product.packSize_AtIndex(edit_packSizes[i].trim(), rowIndex);
					}

					if (edit_ndcDescriptions.length > i && !edit_ndcDescriptions[i].trim().isEmpty()) {
						product.ndcDesc_AtIndex(edit_ndcDescriptions[i].trim(), rowIndex);

					}
					if (edit_gtnNumbers.length > i && !edit_gtnNumbers[i].trim().isEmpty()) {
						product.ndcDesc_AtIndex(edit_gtnNumbers[i].trim(), rowIndex);
					}

					log.info("Edited NDC row {}", rowIndex);
				}
				capture();
				break;

			case "DELETE":
				log.info("Executing Delete Flow");
				String[] del_indexArr = ADD_NDC_EDIT_DELETE_INDEX_AFTER_REVIEW_RETURN.split(",");

				List<Integer> indexes = new ArrayList<>();

				for (String idx : del_indexArr) {
					String target = idx.trim();
					try {
						indexes.add(Integer.parseInt(target));
					} catch (NumberFormatException e) {
						indexes.add(product.getRowIndexByNDCNumber(target));
					}
				}

				// Sort in descending order
				Collections.sort(indexes, Collections.reverseOrder());

				for (int rowIndex : indexes) {

					if (rowIndex == 0) {

						product.delRow(); // delete last row
						log.info("Deleted last row");

					} else {

						product.delRow_AtIndex(rowIndex);
						log.info("Deleted row at index {}", rowIndex);
					}
				}
				capture();
				break;

			default:
				throw new RuntimeException("Invalid NDC action: " + ADD_NDC_EDIT_ACTION_AFTER_REVIEW_RETURN);
			}

			product.clickUpdate();
			log.info("Clicked Update newly edited and added NDC");
			capture();
			product.authenticate(product.currentPassword);
			String editToast = product.waitForToast();
			product.waitForLoading();
			sa.assertEquals(editToast, "Product updated successfully", "Edit NDC updated toaster message", editToast);
			sa.assertAll();
		} else {
			log.info("Add NDC Review Return and Edit skipped based on configuration");
		}
	}

	@Test(groups = { "Click_Review_For_ADD_NDC" })
	public void Click_Review_For_Add_NDC() throws Throwable {

		switchUserIfMulti(USERNAME2_VAL, PASSWORD2_VAL);
		log.info("--- Initiating Add NDC Review Flow for: {} ---", currentEntryName);
		performReview(REVIEW_REMARKS, "Product reviewed successfully");
		sa.assertAll();
	}

	@Test(groups = { "Click_Approve_Return_And_Edit_And_Review_For_ADD_NDC" })
	public void Click_Approve_Return_And_Edit_And_Review_For_Add_NDC() throws Throwable {
		if (PRODUCT_INACTIVE_REJECT_IN_APPROVE_ACTION != null
				&& PRODUCT_INACTIVE_REJECT_IN_APPROVE_ACTION.equalsIgnoreCase("yes")) {
				switchUserIfMulti(USERNAME3_VAL, PASSWORD3_VAL);
			freezeStep();
			log.info("--- Initiating Add NDC Approve Return Flow for: {} ---", currentEntryName);
			performReturnApprove(APPROVE_RETURN_REMARKS, "Product returned successfully");
			switchUserIfMulti(USERNAME1_VAL, PASSWORD1_VAL);
			log.info("Opening Edit NDC screen (After Approve Return)");
			freezeStep();
			product.clickEdit(currentEntryName);
			product.waitForLoading();
			capture();
			nextStep();

			log.info("Making changes based on Edit action:{}", ADD_NDC_EDIT_ACTION_AFTER_APPROVE_RETURN);

			switch (ADD_NDC_EDIT_ACTION_AFTER_APPROVE_RETURN.toUpperCase()) {

			case "ADD":
				log.info("Executing Add Flow");
				product.enterRow_for_addndc();

				String[] add_ndcNumbers = ADD_NDC_EDIT_NEW_NDC_NUMBER_AFTER_APPROVE_RETURN.split(",");
				String[] add_shortCodes = ADD_NDC_EDIT_NEW_SHORT_CODE_AFTER_APPROVE_RETURN.split(",");
				String[] add_packSizes = ADD_NDC_EDIT_NEW_PACK_SIZE_AFTER_APPROVE_RETURN.split(",");
				String[] add_ndcDescriptions = ADD_NDC_EDIT_NEW_NDC_DESCRIPTION_AFTER_APPROVE_RETURN.split(",");
				String[] add_gtnNumbers = ADD_NDC_EDIT_NEW_GTN_NUMBER_AFTER_APPROVE_RETURN.split(",");

				String[] uoms = ADD_NDC_UOM.split(",");

				for (int i = 0; i < add_ndcNumbers.length; i++) {

					product.ndcNumber(add_ndcNumbers[i].trim());
					product.shortCode(add_shortCodes[i].trim());

					String uomToUse = (uoms.length == 1) ? uoms[0].trim() : uoms[i].trim();
					product.selUOM(uomToUse);

					product.packSize(add_packSizes[i].trim());
					product.gtnNumber(add_gtnNumbers[i].trim());

					if (add_ndcDescriptions.length > i && !add_ndcDescriptions[i].trim().isEmpty()) {
						product.ndcDesc(add_ndcDescriptions[i].trim());
					}

					if (i < add_ndcNumbers.length - 1) {
						product.enterRow_for_addndc();
					}
				}
				capture();
				log.info("Added new NDC row");
				break;

			case "EDIT":
				log.info("Executing Edit Flow");

				String[] edit_ndcNumbers = ADD_NDC_EDIT_EXISTING_NDC_NUMBER_AFTER_APPROVE_RETURN.split(",");
				String[] edit_shortCodes = ADD_NDC_EDIT_EXISTING_SHORT_CODE_AFTER_APPROVE_RETURN.split(",");
				String[] edit_packSizes = ADD_NDC_EDIT_EXISTING_PACK_SIZE_AFTER_APPROVE_RETURN.split(",");
				String[] edit_ndcDescriptions = ADD_NDC_EDIT_EXISTING_NDC_DESCRIPTION_AFTER_APPROVE_RETURN.split(",");
				String[] edit_gtnNumbers = ADD_NDC_EDIT_EXISTING_GTN_NUMBER_AFTER_APPROVE_RETURN.split(",");
				String[] indexArr = ADD_NDC_EDIT_EXISTING_INDEX_AFTER_APPROVE_RETURN.split(",");

				for (int i = 0; i < indexArr.length; i++) {

					String target = indexArr[i].trim();
					int rowIndex = -1;
					try {
						rowIndex = Integer.parseInt(target);
					} catch (NumberFormatException e) {
						rowIndex = product.getRowIndexByNDCNumber(target);
					}

					if (edit_ndcNumbers.length > i && !edit_ndcNumbers[i].trim().isEmpty()) {
						product.ndcNumber_AtIndex(edit_ndcNumbers[i].trim(), rowIndex);
					}

					if (edit_shortCodes.length > i && !edit_shortCodes[i].trim().isEmpty()) {
						product.shortCode_AtIndex(edit_shortCodes[i].trim(), rowIndex);
					}

					if (edit_packSizes.length > i && !edit_packSizes[i].trim().isEmpty()) {
						product.packSize_AtIndex(edit_packSizes[i].trim(), rowIndex);
					}

					if (edit_ndcDescriptions.length > i && !edit_ndcDescriptions[i].trim().isEmpty()) {
						product.ndcDesc_AtIndex(edit_ndcDescriptions[i].trim(), rowIndex);
					}
					if (edit_gtnNumbers.length > i && !edit_gtnNumbers[i].trim().isEmpty()) {
						product.ndcDesc_AtIndex(edit_gtnNumbers[i].trim(), rowIndex);
					}

					log.info("Edited NDC row {}", rowIndex);
				}
				capture();
				break;

			case "DELETE":
				log.info("Executing Delete Flow");
				String[] del_indexArr = ADD_NDC_EDIT_DELETE_INDEX_AFTER_APPROVE_RETURN.split(",");

				List<Integer> indexes = new ArrayList<>();

				for (String idx : del_indexArr) {
					String target = idx.trim();
					try {
						indexes.add(Integer.parseInt(target));
					} catch (NumberFormatException e) {
						indexes.add(product.getRowIndexByNDCNumber(target));
					}
				}

				// Sort in descending order
				Collections.sort(indexes, Collections.reverseOrder());

				for (int rowIndex : indexes) {

					if (rowIndex == 0) {

						product.delRow(); // delete last row
						log.info("Deleted last row");

					} else {

						product.delRow_AtIndex(rowIndex);
						log.info("Deleted row at index {}", rowIndex);
					}
				}
				capture();
				break;

			default:
				throw new RuntimeException("Invalid NDC action: " + ADD_NDC_EDIT_ACTION_AFTER_APPROVE_RETURN);
			}

			product.clickUpdate();
			log.info("Clicked Update");
			capture();
			product.authenticate(product.currentPassword);
			String editToast = product.waitForToast();
			product.waitForLoading();
			sa.assertEquals(editToast, "Product updated successfully", "Updated Edit NDC toaster message", editToast);

		} else {
			log.info("Product Add NDC Approve Return and Edit skipped based on configuration");
		}
	}

	@Test(groups = { "Click_Approve_For_ADD_NDC" })
	public void Click_Approve_For_Add_NDC() throws Throwable {
			switchUserIfMulti(USERNAME3_VAL, PASSWORD3_VAL);
		
		log.info("--- Initiating Add NDC Approve Flow for: {} ---", currentEntryName);
		performApprove(APPROVE_REMARKS,"Product approved successfully");
		resumeStep();
		sa.assertAll();
	}

	@Test(groups = { "ClickInactive" })
	public void Click_Inactive() throws Throwable {
		switchUserIfMulti(USERNAME1_VAL, PASSWORD1_VAL);
		log.info("--- Performing Inactive for: {} ---", currentEntryName);
		performInactive(INACTIVE_REMARKS, "Product Inactive Request initiated successfully");
	}

	@Test(groups = { "Product_Inactive_Review_Reject" })
	public void Product_Inactive_Review_Reject() throws Throwable {
		if (PRODUCT_INACTIVE_REJECT_IN_REVIEW_ACTION.equalsIgnoreCase("yes")) {
			switchUserIfMulti(USERNAME2_VAL, PASSWORD2_VAL);
			log.info("--- Initiating Inactive Reject In Review Flow for: {} ---", currentEntryName);
			log.info("Opening actions menu to access InActive Review/Reject");
			currentReviewContext = "INACTIVE";
			performReviewReject(INACTIVE_REVIEW_REJECT_REMARKS, "Product Rejected successfully");
			currentReviewContext = "";
			sa.assertAll();
		}
	}

	@Test(groups = { "Product_Inactive_Review" })
	public void Product_Inactive_Review() throws Throwable {
		switchUserIfMulti(USERNAME2_VAL, PASSWORD2_VAL);
		log.info("--- Initiating Inactive Review Flow for: {} ---", currentEntryName);
		freezeStep();
		currentReviewContext = "INACTIVE";
		performReview(INACTIVE_REVIEW__REMARKS, "Product Reviewed successfully");
		currentReviewContext = "";
		sa.assertAll();
	}
	

	@Test(groups = { "Product_Inactive_Approve_Reject" })
	public void Product_Inactive_Approve_Reject() throws Throwable {
		if (PRODUCT_INACTIVE_REJECT_IN_APPROVE_ACTION.equalsIgnoreCase("yes")) {
			switchUserIfMulti(USERNAME3_VAL, PASSWORD3_VAL);
			log.info("--- Initiating Inactive Reject In ApproveFlow for: {} ---", currentEntryName);
			log.info("Opening actions menu to access InActive Approve/Reject");
			currentApproveContext = "INACTIVE";
			performApproveReject(INACTIVE_APPROVE_REJECT_REMARKS, "Product Rejected successfully");
			currentApproveContext = "";
			sa.assertAll();
		}
	}

	@Test(groups = { "Product_Inactive_Approve" })
	public void Product_Inactive_Approve() throws Throwable {
		switchUserIfMulti(USERNAME3_VAL, PASSWORD3_VAL);
		log.info("--- Initiating Inactive Approve Flow for: {} ---", currentEntryName);
		currentApproveContext = "INACTIVE";
		performReview(INACTIVE_REVIEW__REMARKS, "Product Reviewed successfully");
		currentApproveContext = "";
		resumeStep();
		sa.assertAll();
	}

	@Test(groups = { "ClickActive" })
	public void Click_Active() throws Throwable {
		switchUserIfMulti(USERNAME1_VAL, PASSWORD1_VAL);
		log.info("--- Performing Active for: {} ---", currentEntryName);
		performActive(ACTIVE_REMARKS, "Product active request initiated successfully");
	}

	@Test(groups = { "Product_Active_Review_Reject" })
	public void Product_Active_Review_Reject() throws Throwable {
		if (PRODUCT_ACTIVE_REJECT_IN_REVIEW_ACTION.equalsIgnoreCase("yes")) {
			switchUserIfMulti(USERNAME2_VAL, PASSWORD2_VAL);
			log.info("--- Initiating Active Reject In Review Flow for: {} ---", currentEntryName);
			log.info("Opening actions menu to access Active Review/Reject");
			currentReviewContext = "ACTIVE";
			performReviewReject(ACTIVE_REVIEW_REJECT_REMARKS, "Product active request rejected successfully");
			currentReviewContext = "";
			
			sa.assertAll();
		}
	}

	@Test(groups = { "Product_Active_Review" })
	public void Product_Active_Review() throws Throwable {
		switchUserIfMulti(USERNAME2_VAL, PASSWORD2_VAL);
		log.info("--- Initiating Active Review Flow for: {} ---", currentEntryName);

		currentReviewContext = "ACTIVE";
		performReview(ACTIVE_REVIEW__REMARKS, "Product active request reviewed successfully");
		currentReviewContext = "";

		sa.assertAll();
	}

	@Test(groups = { "Product_Active_Approve_Reject" })
	public void Product_Active_Approve_Reject() throws Throwable {
		if (PRODUCT_ACTIVE_REJECT_IN_APPROVE_ACTION.equalsIgnoreCase("yes")) {
			switchUserIfMulti(USERNAME3_VAL, PASSWORD3_VAL);
			log.info("--- Initiating Active Reject In Approve Flow for: {} ---", currentEntryName);
			freezeStep();
			log.info("Opening actions menu to access Active Approve/Reject");
			currentApproveContext = "ACTIVE";
			performApproveReject(ACTIVE_APPROVE_REJECT_REMARKS, "Product active request rejected successfully");
			currentApproveContext = "";
			sa.assertAll();
		}
	}

	@Test(groups = { "Product_Active_Approve" })
	public void Product_Active_Approve() throws Throwable {
		switchUserIfMulti(USERNAME3_VAL, PASSWORD3_VAL);
		log.info("--- Initiating Active Approve Flow for: {} ---", currentEntryName);


		currentApproveContext = "ACTIVE";
		performApprove(ACTIVE_APPROVE_REMARKS, "Product active request reviewed successfully");
		currentApproveContext = "";
		resumeStep();
		sa.assertAll();
	}

	@Test(groups = { "ClickActionsRep" })
	public void Click_Actions_1() throws Throwable {
		if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
			switchUserIfMulti(USERNAME1_VAL, PASSWORD1_VAL);
		}
		actionIterationCount++;
		if(actionIterationCount==3){
		}
		else {
		nextStep();}
		Click_Actions();
	}

	@Test(groups = { "ClickActionsRep" })
	public void Click_Actions_2() throws Throwable {
		if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
			switchUserIfMulti(USERNAME2_VAL, PASSWORD2_VAL);
		}
		Click_Actions();
	}

	@Test(groups = { "ClickActionsRep" })
	public void Click_Actions_3() throws Throwable {
		if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
			switchUserIfMulti(USERNAME1_VAL, PASSWORD1_VAL);
		}
		Click_Actions();
	}

	@Test(groups = { "Click_Review_For_ADD_NDC" })
	public void Click_Review_For_ADD_NDC_1() throws Throwable {
		Click_Review_For_Add_NDC();
	}

	@Test(groups = { "Click_Review_For_ADD_NDC" })
	public void Click_Review_For_ADD_NDC_2() throws Throwable {
		Click_Review_For_Add_NDC();
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

	@Test(groups = { "Product_Inactive_ReviewRep" })
	public void Product_Inactive_Review_1() throws Throwable {
		Product_Inactive_Review();
	}

	@Test(groups = { "Product_Inactive_ReviewRep" })
	public void Product_Inactive_Review_2() throws Throwable {
		Product_Inactive_Review();
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

	@Test(groups = { "Product_Active_ReviewRep" })
	public void Product_Active_Review_1() throws Throwable {
		Product_Active_Review();
	}

	@Test(groups = { "Product_Active_ReviewRep" })
	public void Product_Active_Review_2() throws Throwable {
		Product_Active_Review();
	}

	@Override
	protected void updateEntryName(String newName) throws Throwable {
		// Not implemented for AkronQuickProduct explicitly
	}

	@Override
	protected void beforeClickActionsScreenshot() {
		nextStep();
	}
	
	@Override
	protected void reviewMenuClick() {
		if ("INACTIVE".equalsIgnoreCase(currentReviewContext)) {
			product.clickInactiveReview();
		} else if ("ACTIVE".equalsIgnoreCase(currentReviewContext)) {
			product.clickActiveReview();
		} else {
			product.clickReview();
		}
	}

	
	@Override
	protected void approveMenuClick() {
		if ("INACTIVE".equalsIgnoreCase(currentApproveContext)) {
			product.clickInactiveApprove();
		} else if ("ACTIVE".equalsIgnoreCase(currentApproveContext)) {
			product.clickActiveApprove();
		} else {
			product.clickApprove();
		}
	}
	
	@Override
	protected void noScreenshotForActionsInReviewReturn(String... actions) throws Throwable {
		freezeCapture();
		try {
			pageObject.clickActions(combine(currentEntryName, actions));
		} catch (Throwable e) {
			log.error("Failed to re-click actions during freeze capture", e);
		}
		resumeCapture();
	}

	@Override
	protected void beforeInactive() {
		inactiveIterationCount++;
		if(inactiveIterationCount==1)
		nextStep();
		
	}
	
	@Override
	protected void beforeActive() {
		activeIterationCount++;
		if(activeIterationCount==1)
		nextStep();
	}
	
	


}
