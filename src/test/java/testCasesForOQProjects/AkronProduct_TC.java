package testCasesForOQProjects;

import static configData.AkronProductData.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pageObjects.AkronQuickProduct;
import testBase.BaseClass;
import utilities.DatabaseBackupUtil;
import utilities.ScreenshotUtil;
import utilities.SoftAssertionUtil;

public class AkronProduct_TC extends BaseClass {
	AkronQuickProduct product;

	String currentProductName;
	SoftAssertionUtil sa;
	private int inactiveIterationCount = 0;
	private int activeIterationCount = 0;
	private int actionIterationCount = 0;

	@BeforeClass
	@Parameters({ "configFile" })
	public void setUp(@Optional("akronproduct.properties") String configFile) throws Exception {
		log.info("--- Starting NDC Test Case Setup with config: {} ---", configFile);

		// Load the dynamic property file
		configData.AkronProductData.loadProperties(configFile);

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
		currentProductName = PRODUCT_NAME; // ✅ Initialize for use in all test methods
		log.info("Setup completed. Screenshots enabled: {}. NDC Number: {}", screenshotsEnabled, currentProductName);
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
		product.waitForElementToVisible(product.getSearchBox());
		Assert.assertTrue(product.getSearchBox().isDisplayed(), "Search box is not visible!");
		log.info("Searching for Application URL: {}", APP_URL);
		product.searchBox(APP_URL);
		product.waitForLoading();
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
			product.login(USERNAME, PASSWORD, PC_DB_NAME);
		} else {
			product.login(USERNAME1, PASSWORD1, PC_DB_NAME);
		}
	}

	@Test(groups = { "moduleselect" })
	public void moduleClick() throws Throwable {
		log.info("--- Clicking Module ---");
		product.click_titleMasters();
		log.info("Clicked on Masters title");
		ScreenshotUtil.capture();
		product.waitForLoading();
		product.masterClick(MASTER_MODULE);
		log.info("Clicked on Master Module: {}", MASTER_MODULE);
		product.masterClick(SUB_MASTER_MODULE);
		log.info("Clicked on Sub Master Module: {}", SUB_MASTER_MODULE);
		product.waitForLoading();
		ScreenshotUtil.capture();
	}

	@Test(groups = { "ClickActions" })
	public void Click_Actions() throws Throwable {
		log.info("--- Attempting to open Actions Menu for: {} ---", currentProductName);
		product.clickActions(currentProductName);
		log.info("Successfully opened Actions menu for {}", currentProductName);
		ScreenshotUtil.capture();
		product.clickActions(currentProductName);
	}

	@Test(groups = { "ClickView" })
	public void Click_View() throws Throwable {
		if (PRODUCT_VIEW_ACTION.equalsIgnoreCase("yes")) {
			if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
				product.switchUser(USERNAME2, PASSWORD2, PC_DB_NAME, MASTER_MODULE, SUB_MASTER_MODULE);
			}
			log.info("--- Viewing Quick Product: {} ---", currentProductName);
			product.clickPendingView(currentProductName);
			log.info("View screen opened");
			product.waitForLoading();
			ScreenshotUtil.capture();
			product.clickBack();
			log.info("Clicked Back button");
			product.waitForLoading();
			ScreenshotUtil.capture();
		} else {
			log.info("View step skipped based on configuration");
		}
	}

	@Test(groups = { "AddNDC" })
	public void Click_Add_NDC() throws Throwable {

		if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
			log.info("--- Inactivating Product: {} ---", currentProductName);
			product.switchUser(USERNAME1, PASSWORD1, PC_DB_NAME, MASTER_MODULE, SUB_MASTER_MODULE);
		}
		log.info("--- Performing Add NDC for: {} ---", currentProductName);

		product.waitForLoading();
		ScreenshotUtil.nextStep();
		log.info("Opening actions menu for Add NDC");
		product.clickActions(currentProductName);
		ScreenshotUtil.capture();
		log.info("Clicking Add NDC button in the list");
		product.clickAddNDC();
		ScreenshotUtil.capture();
		product.enterRow_for_addndc();
		String[] ndcNumbers = ADD_NDC_NDC_NUMBER.split(",");
		String[] shortCodes = ADD_NDC_SHORT_CODE.split(",");
		String[] packSizes = ADD_NDC_PACK_SIZE.split(",");
		String[] ndcDescriptions = ADD_NDC_NDC_DESCRIPTION.split(",");
		String[] uoms = ADD_NDC_UOM.split(",");

		for (int i = 0; i < ndcNumbers.length; i++) {

			product.ndcNumber(ndcNumbers[i].trim());
			product.shortCode(shortCodes[i].trim());

			String uomToUse = (uoms.length == 1) ? uoms[0].trim() : uoms[i].trim();
			product.selUOM(uomToUse);

			product.packSize(packSizes[i].trim());

			if (ndcDescriptions.length > i && !ndcDescriptions[i].trim().isEmpty()) {
				product.ndcDesc(ndcDescriptions[i].trim());
			}

			if (i < ndcNumbers.length - 1) {
				product.enterRow_for_addndc();
			}
		}

		ScreenshotUtil.capture();
		product.clickUpdate();
		log.info("Added new NDC");
		ScreenshotUtil.capture();
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
			if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
				product.switchUser(USERNAME2, PASSWORD2, PC_DB_NAME, MASTER_MODULE, SUB_MASTER_MODULE);
			}
			log.info("--- Initiating Add NDC Review Return Flow for: {} ---", currentProductName);
			log.info("Opening actions menu to access Add NDC Review/Return");
			product.clickActions(currentProductName);
			ScreenshotUtil.nextStep();
			log.info("Clicking Review to trigger return dialog");
			product.clickReview();
			product.waitForLoading();
			ScreenshotUtil.capture();
			product.enterRemarks("Return to initiator for changes");
			log.info("Entered Add NDC Return remarks");
			ScreenshotUtil.capture();
			product.clickReturn();
			log.info("Clicked Return button");
			ScreenshotUtil.capture();
			product.authenticate(product.currentPassword);
			String returnToast = product.waitForToast();
			product.waitForLoading();
			sa.assertEquals(returnToast, "Product returned successfully", "Add NDC return toaster message",
					returnToast);

			if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
				product.switchUser(USERNAME1, PASSWORD1, PC_DB_NAME, MASTER_MODULE, SUB_MASTER_MODULE);
			}

			log.info("Opening Edit NDC screen (After Review Return)");

			product.clickEdit(currentProductName);
			product.waitForLoading();
			ScreenshotUtil.capture();

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

					for (int i = 0; i < add_ndcNumbers.length; i++) {

						product.ndcNumber(add_ndcNumbers[i].trim());
						product.shortCode(add_shortCodes[i].trim());

						String uomToUse = (uoms.length == 1) ? uoms[0].trim() : uoms[i].trim();
						product.selUOM(uomToUse);

						product.packSize(add_packSizes[i].trim());

						if (add_ndcDescriptions.length > i && !add_ndcDescriptions[i].trim().isEmpty()) {
							product.ndcDesc(add_ndcDescriptions[i].trim());
						}

						if (i < add_ndcNumbers.length - 1) {
							product.enterRow_for_addndc();
						}
					}
					ScreenshotUtil.capture();
					log.info("Added new NDC row");
					break;

				case "EDIT":
					log.info("Executing Edit Flow");

					String[] edit_ndcNumbers = ADD_NDC_EDIT_EXISTING_NDC_NUMBER_AFTER_REVIEW_RETURN.split(",");
					String[] edit_shortCodes = ADD_NDC_EDIT_EXISTING_SHORT_CODE_AFTER_REVIEW_RETURN.split(",");
					String[] edit_packSizes = ADD_NDC_EDIT_EXISTING_PACK_SIZE_AFTER_REVIEW_RETURN.split(",");
					String[] edit_ndcDescriptions = ADD_NDC_EDIT_EXISTING_NDC_DESCRIPTION_AFTER_REVIEW_RETURN
							.split(",");
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

						log.info("Edited NDC row {}", rowIndex);
					}
					ScreenshotUtil.capture();
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
					ScreenshotUtil.capture();
					break;

				default:
					throw new RuntimeException("Invalid NDC action: " + ADD_NDC_EDIT_ACTION_AFTER_REVIEW_RETURN);
			}

			product.clickUpdate();
			log.info("Clicked Update newly edited and added NDC");
			ScreenshotUtil.capture();
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
		if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
			product.switchUser(USERNAME2, PASSWORD2, PC_DB_NAME, MASTER_MODULE, SUB_MASTER_MODULE);
		}
		log.info("--- Initiating Add NDC Review Flow for: {} ---", currentProductName);
		log.info("Opening actions menu to access Add NDC Review/Return");
		product.clickActions(currentProductName);
		ScreenshotUtil.capture();
		log.info("Clicking Review to trigger return dialog");
		product.clickReview();
		product.waitForLoading();
		ScreenshotUtil.capture();
		product.enterRemarks("Approved in Review");
		log.info("Entered Add NDC Review remarks");
		ScreenshotUtil.capture();
		product.clickReview();
		log.info("Clicked Review button");
		ScreenshotUtil.capture();
		product.authenticate(product.currentPassword);
		String reviewToast = product.waitForToast();
		product.waitForLoading();
		sa.assertEquals(reviewToast, "Product reviewed successfully", "Add NDC Review toaster message", reviewToast);
		sa.assertAll();
	}

	@Test(groups = { "Click_Approve_Return_And_Edit_And_Review_For_ADD_NDC" })
	public void Click_Approve_Return_And_Edit_And_Review_For_Add_NDC() throws Throwable {
		if (PRODUCT_INACTIVE_REJECT_IN_APPROVE_ACTION != null
				&& PRODUCT_INACTIVE_REJECT_IN_APPROVE_ACTION.equalsIgnoreCase("yes")) {
			if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
				product.switchUser(USERNAME3, PASSWORD3, PC_DB_NAME, MASTER_MODULE, SUB_MASTER_MODULE);
			}
			log.info("--- Initiating Add NDC Approve Return Flow for: {} ---", currentProductName);
			product.clickActions(currentProductName);
			product.clickApprove();
			product.waitForLoading();
			ScreenshotUtil.capture();
			product.enterRemarks("Return to initiator from Approve");
			log.info("Entered Approve Return remarks");
			ScreenshotUtil.capture();
			product.clickReturn();
			log.info("Clicked Return button");
			ScreenshotUtil.capture();
			product.authenticate(product.currentPassword);
			String returnToast = product.waitForToast();
			product.waitForLoading();
			sa.assertEquals(returnToast, "Product returned successfully", "Approve Return toaster message",
					returnToast);

			if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
				product.switchUser(USERNAME1, PASSWORD1, PC_DB_NAME, MASTER_MODULE, SUB_MASTER_MODULE);
			}

			log.info("Opening Edit NDC screen (After Approve Return)");
			ScreenshotUtil.freezeStepNumbering();
			product.clickEdit(currentProductName);
			product.waitForLoading();
			ScreenshotUtil.capture();
			ScreenshotUtil.nextStep();

			log.info("Making changes based on Edit action:{}", ADD_NDC_EDIT_ACTION_AFTER_APPROVE_RETURN);

			switch (ADD_NDC_EDIT_ACTION_AFTER_APPROVE_RETURN.toUpperCase()) {

				case "ADD":
					log.info("Executing Add Flow");
					product.enterRow_for_addndc();

					String[] add_ndcNumbers = ADD_NDC_EDIT_NEW_NDC_NUMBER_AFTER_APPROVE_RETURN.split(",");
					String[] add_shortCodes = ADD_NDC_EDIT_NEW_SHORT_CODE_AFTER_APPROVE_RETURN.split(",");
					String[] add_packSizes = ADD_NDC_EDIT_NEW_PACK_SIZE_AFTER_APPROVE_RETURN.split(",");
					String[] add_ndcDescriptions = ADD_NDC_EDIT_NEW_NDC_DESCRIPTION_AFTER_APPROVE_RETURN.split(",");
					String[] uoms = ADD_NDC_UOM.split(",");

					for (int i = 0; i < add_ndcNumbers.length; i++) {

						product.ndcNumber(add_ndcNumbers[i].trim());
						product.shortCode(add_shortCodes[i].trim());

						String uomToUse = (uoms.length == 1) ? uoms[0].trim() : uoms[i].trim();
						product.selUOM(uomToUse);

						product.packSize(add_packSizes[i].trim());

						if (add_ndcDescriptions.length > i && !add_ndcDescriptions[i].trim().isEmpty()) {
							product.ndcDesc(add_ndcDescriptions[i].trim());
						}

						if (i < add_ndcNumbers.length - 1) {
							product.enterRow_for_addndc();
						}
					}
					ScreenshotUtil.capture();
					log.info("Added new NDC row");
					break;

				case "EDIT":
					log.info("Executing Edit Flow");

					String[] edit_ndcNumbers = ADD_NDC_EDIT_EXISTING_NDC_NUMBER_AFTER_APPROVE_RETURN.split(",");
					String[] edit_shortCodes = ADD_NDC_EDIT_EXISTING_SHORT_CODE_AFTER_APPROVE_RETURN.split(",");
					String[] edit_packSizes = ADD_NDC_EDIT_EXISTING_PACK_SIZE_AFTER_APPROVE_RETURN.split(",");
					String[] edit_ndcDescriptions = ADD_NDC_EDIT_EXISTING_NDC_DESCRIPTION_AFTER_APPROVE_RETURN
							.split(",");
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

						log.info("Edited NDC row {}", rowIndex);
					}
					ScreenshotUtil.capture();
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
					ScreenshotUtil.capture();
					break;

				default:
					throw new RuntimeException("Invalid NDC action: " + ADD_NDC_EDIT_ACTION_AFTER_APPROVE_RETURN);
			}

			product.clickUpdate();
			log.info("Clicked Update");
			ScreenshotUtil.capture();
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
		if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
			product.switchUser(USERNAME3, PASSWORD3, PC_DB_NAME, MASTER_MODULE, SUB_MASTER_MODULE);
		}
		log.info("--- Initiating Add NDC Approve Flow for: {} ---", currentProductName);
		product.clickActions(currentProductName);
		ScreenshotUtil.capture();
		log.info("Clicking Approve to trigger dialog");
		product.clickApprove();
		product.waitForLoading();
		ScreenshotUtil.capture();
		product.enterRemarks("Approved Add NDC");
		log.info("Entered Add NDC Approve remarks");
		ScreenshotUtil.capture();
		product.clickApprove();
		log.info("Clicked Approve button");
		ScreenshotUtil.capture();
		product.authenticate(product.currentPassword);
		String toast = product.waitForToast();
		product.waitForLoading();
		ScreenshotUtil.resumeStepNumbering();
		sa.assertEquals(toast, "Product approved successfully", "Add NDC Approve toaster message", toast);
		sa.assertAll();
	}

	@Test(groups = { "ClickInactive" })
	public void Click_Inactive() throws Throwable {
		inactiveIterationCount++;

		if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
			log.info("--- Inactivating Product: {} ---", currentProductName);
			product.switchUser(USERNAME1, PASSWORD1, PC_DB_NAME, MASTER_MODULE, SUB_MASTER_MODULE);
		}
		log.info("--- Performing Inactive for: {} ---", currentProductName);

		product.waitForLoading();
		if (inactiveIterationCount == 1) {
			ScreenshotUtil.nextStep();
		}
		log.info("Opening actions menu for Inactive");
		product.clickActions(currentProductName);
		ScreenshotUtil.capture();
		log.info("Clicking Inactive button in the list");
		product.clickInactive();
		ScreenshotUtil.capture();
		product.enterRemarks(INACTIVE_REMARKS);
		log.info("Entered Inactive remarks: {}", INACTIVE_REMARKS);
		ScreenshotUtil.capture();
		product.clickInactive();
		log.info("Inactive requested");
		ScreenshotUtil.capture();
		product.authenticate(product.currentPassword);
		String inactiveToast = product.waitForToast();
		product.waitForLoading();
		sa.assertEquals(inactiveToast, "Product Inactive Request initiated successfully", "Inactive toaster messege",
				inactiveToast);
		sa.assertAll();

	}

	@Test(groups = { "Product_Inactive_Review_Reject" })
	public void Product_Inactive_Review_Reject() throws Throwable {

		if (PRODUCT_INACTIVE_REJECT_IN_REVIEW_ACTION.equalsIgnoreCase("yes")) {

			if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
				product.switchUser(USERNAME2, PASSWORD2, PC_DB_NAME, MASTER_MODULE, SUB_MASTER_MODULE);
			}
			log.info("--- Initiating Inactive Reject In Review Flow for: {} ---", currentProductName);
			log.info("Opening actions menu to access InActive Review/Reject");
			product.clickActions(currentProductName);
			ScreenshotUtil.capture();
			log.info("Clicking Review to trigger return dialog");
			product.clickInactiveReview();
			product.waitForLoading();
			ScreenshotUtil.capture();
			product.enterRemarks(INACTIVE_REVIEW_REJECT_REMARKS);
			log.info("Entered Inactive Review Reject remarks: {}", INACTIVE_REVIEW_REJECT_REMARKS);
			ScreenshotUtil.capture();
			product.clickReject();
			log.info("Clicked Reject button");
			ScreenshotUtil.capture();
			product.authenticate(product.currentPassword);
			String rejectToast = product.waitForToast();
			product.waitForLoading();
			sa.assertEquals(rejectToast, "Product Rejected successfully", "Inactive Review Reject  toaster message",
					rejectToast);
			sa.assertAll();

		}
	}

	@Test(groups = { "Product_Inactive_Review" })
	public void Product_Inactive_Review() throws Throwable {

		if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
			product.switchUser(USERNAME2, PASSWORD2, PC_DB_NAME, MASTER_MODULE, SUB_MASTER_MODULE);
		}
		log.info("--- Initiating Inactive Review Flow for: {} ---", currentProductName);
		log.info("Opening actions menu to access Inactive Review/Reject");

		product.clickActions(currentProductName);
		ScreenshotUtil.capture();
		log.info("Clicking Review to trigger return dialog");
		product.clickInactiveReview();
		product.waitForLoading();
		ScreenshotUtil.capture();
		product.enterRemarks(INACTIVE_REVIEW__REMARKS);
		log.info("Entered Inactive Review remarks: {}", INACTIVE_REVIEW_REJECT_REMARKS);
		ScreenshotUtil.capture();
		product.clickInactiveReview();
		log.info("Clicked Review button");
		ScreenshotUtil.capture();
		product.authenticate(product.currentPassword);
		String reviewToast = product.waitForToast();
		product.waitForLoading();
		sa.assertEquals(reviewToast, "Product Reviewed successfully", "Inactive Review toaster message", reviewToast);
		sa.assertAll();

	}

	@Test(groups = { "Product_Inactive_Approve_Reject" })
	public void Product_Inactive_Approve_Reject() throws Throwable {

		if (PRODUCT_INACTIVE_REJECT_IN_APPROVE_ACTION.equalsIgnoreCase("yes")) {

			if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
				product.switchUser(USERNAME3, PASSWORD3, PC_DB_NAME, MASTER_MODULE, SUB_MASTER_MODULE);
			}
			log.info("--- Initiating Inactive Reject In Approve Flow for: {} ---", currentProductName);
			log.info("Opening actions menu to access Inactive Approve/Reject");
			ScreenshotUtil.freezeStepNumbering();
			product.clickActions(currentProductName);
			ScreenshotUtil.capture();
			log.info("Clicking Approve to trigger return dialog");
			product.clickInactiveApprove();
			product.waitForLoading();
			ScreenshotUtil.capture();
			product.enterRemarks(INACTIVE_APPROVE_REJECT_REMARKS);
			log.info("Entered Inactive Approve Reject remarks: {}", INACTIVE_APPROVE_REJECT_REMARKS);
			ScreenshotUtil.capture();
			product.clickReject();
			log.info("Clicked Reject button");
			ScreenshotUtil.capture();
			product.authenticate(product.currentPassword);
			String rejectToast = product.waitForToast();
			product.waitForLoading();
			sa.assertEquals(rejectToast, "Product Rejected successfully", "Inactive Approve Reject  toaster message",
					rejectToast);
			sa.assertAll();

		}
	}

	@Test(groups = { "Product_Inactive_Approve" })
	public void Product_Inactive_Approve() throws Throwable {

		if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
			product.switchUser(USERNAME3, PASSWORD3, PC_DB_NAME, MASTER_MODULE, SUB_MASTER_MODULE);
		}
		log.info("--- Initiating Inactive Approve Flow for: {} ---", currentProductName);
		log.info("Opening actions menu to access Inactive Approve/Reject");
		product.clickActions(currentProductName);
		ScreenshotUtil.capture();
		log.info("Clicking Approve to trigger return dialog");
		product.clickInactiveApprove();
		product.waitForLoading();
		ScreenshotUtil.capture();
		product.enterRemarks(INACTIVE_APPROVE_REMARKS);
		log.info("Entered Inactive Approve remarks: {}", INACTIVE_APPROVE_REMARKS);
		ScreenshotUtil.capture();
		product.clickInactiveApprove();
		log.info("Clicked Approve button");
		ScreenshotUtil.capture();
		product.authenticate(product.currentPassword);
		String approveToast = product.waitForToast();
		product.waitForLoading();
		ScreenshotUtil.resumeStepNumbering();
		sa.assertEquals(approveToast, "Product Inactivated successfully", "Inactivated toaster message", approveToast);
		sa.assertAll();

	}

	@Test(groups = { "ClickActive" })
	public void Click_Active() throws Throwable {
		activeIterationCount++;

		if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
			log.info("--- Activating Product: {} ---", currentProductName);
			product.switchUser(USERNAME1, PASSWORD1, PC_DB_NAME, MASTER_MODULE, SUB_MASTER_MODULE);
		}
		log.info("--- Performing Active for: {} ---", currentProductName);

		product.waitForLoading();
		log.info("Opening actions menu for Active");
		product.clickActions(currentProductName);
		ScreenshotUtil.capture();
		log.info("Clicking Active button in the list");
		if (activeIterationCount == 1) {
			ScreenshotUtil.nextStep();
		}
		product.clickActive();
		ScreenshotUtil.capture();
		product.enterRemarks(ACTIVE_REMARKS);
		log.info("Entered Active remarks: {}", ACTIVE_REMARKS);
		ScreenshotUtil.capture();
		product.clickActive();
		log.info("Active requested");
		ScreenshotUtil.capture();
		product.authenticate(product.currentPassword);
		String activeToast = product.waitForToast();
		product.waitForLoading();
		sa.assertEquals(activeToast, "Product active request initiated successfully", "Active toaster message",
				activeToast);
		sa.assertAll();

	}

	@Test(groups = { "Product_Active_Review_Reject" })
	public void Product_Active_Review_Reject() throws Throwable {

		if (PRODUCT_ACTIVE_REJECT_IN_REVIEW_ACTION.equalsIgnoreCase("yes")) {

			if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
				product.switchUser(USERNAME2, PASSWORD2, PC_DB_NAME, MASTER_MODULE, SUB_MASTER_MODULE);
			}
			log.info("--- Initiating Active Reject In Review Flow for: {} ---", currentProductName);
			log.info("Opening actions menu to access Active Review/Reject");
			product.clickActions(currentProductName);
			ScreenshotUtil.capture();
			ScreenshotUtil.nextStep();
			log.info("Clicking Review to trigger return dialog");
			product.clickActiveReview();
			product.waitForLoading();
			ScreenshotUtil.capture();
			product.enterRemarks(ACTIVE_REVIEW_REJECT_REMARKS);
			log.info("Entered Inactive Review Reject remarks: {}", ACTIVE_REVIEW_REJECT_REMARKS);
			ScreenshotUtil.capture();
			product.clickReject();
			log.info("Clicked Reject button");
			ScreenshotUtil.capture();
			product.authenticate(product.currentPassword);
			String rejectToast = product.waitForToast();
			product.waitForLoading();
			sa.assertEquals(rejectToast, "Product active request rejected successfully",
					"Active Review Reject  toaster message",
					rejectToast);
			sa.assertAll();

		}
	}

	@Test(groups = { "Product_Active_Review" })
	public void Product_Active_Review() throws Throwable {

		if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
			product.switchUser(USERNAME2, PASSWORD2, PC_DB_NAME, MASTER_MODULE, SUB_MASTER_MODULE);
		}
		log.info("--- Initiating Active Review Flow for: {} ---", currentProductName);
		log.info("Opening actions menu to access InActive Review/Reject");
		product.clickActions(currentProductName);
		ScreenshotUtil.capture();
		log.info("Clicking Review to trigger return dialog");
		product.clickActiveReview();
		product.waitForLoading();
		ScreenshotUtil.capture();
		product.enterRemarks(ACTIVE_REVIEW__REMARKS);
		log.info("Entered Active Review remarks: {}", ACTIVE_REVIEW__REMARKS);
		ScreenshotUtil.capture();
		product.clickActiveReview();
		log.info("Clicked Review button");
		ScreenshotUtil.capture();
		product.authenticate(product.currentPassword);
		String reviewToast = product.waitForToast();
		product.waitForLoading();
		sa.assertEquals(reviewToast, "Product active request reviewed successfully", "Active Review toaster message",
				reviewToast);
		sa.assertAll();

	}

	@Test(groups = { "Product_Active_Approve_Reject" })
	public void Product_Active_Approve_Reject() throws Throwable {

		if (PRODUCT_ACTIVE_REJECT_IN_APPROVE_ACTION.equalsIgnoreCase("yes")) {

			if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
				product.switchUser(USERNAME3, PASSWORD3, PC_DB_NAME, MASTER_MODULE, SUB_MASTER_MODULE);
			}
			log.info("--- Initiating Active Reject In Approve Flow for: {} ---", currentProductName);
			log.info("Opening actions menu to access Active Approve/Reject");
			ScreenshotUtil.freezeStepNumbering();
			product.clickActions(currentProductName);
			ScreenshotUtil.nextStep();
			ScreenshotUtil.capture();
			log.info("Clicking Approve to trigger return dialog");
			product.clickActiveApprove();
			product.waitForLoading();
			ScreenshotUtil.capture();
			product.enterRemarks(ACTIVE_APPROVE_REJECT_REMARKS);
			log.info("Entered Active Approve Reject remarks: {}", ACTIVE_APPROVE_REJECT_REMARKS);
			ScreenshotUtil.capture();
			product.clickReject();
			log.info("Clicked Reject button");
			ScreenshotUtil.capture();
			product.authenticate(product.currentPassword);
			String rejectToast = product.waitForToast();
			product.waitForLoading();
			sa.assertEquals(rejectToast, "Product active request rejected successfully",
					"Active Approve Reject toaster message",
					rejectToast);
			sa.assertAll();

		}
	}

	@Test(groups = { "Product_Active_Approve" })
	public void Product_Active_Approve() throws Throwable {

		if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
			product.switchUser(USERNAME3, PASSWORD3, PC_DB_NAME, MASTER_MODULE, SUB_MASTER_MODULE);
		}
		log.info("--- Initiating Active Approve Flow for: {} ---", currentProductName);
		product.clickActions(currentProductName);
		ScreenshotUtil.capture();
		log.info("Clicking Approve to trigger return dialog");
		product.clickActiveApprove();
		product.waitForLoading();
		ScreenshotUtil.capture();
		product.enterRemarks(ACTIVE_APPROVE_REMARKS);
		log.info("Entered Active Approve remarks: {}", ACTIVE_APPROVE_REMARKS);
		ScreenshotUtil.capture();
		product.clickInactiveApprove();
		log.info("Clicked Approve button");
		ScreenshotUtil.capture();
		product.authenticate(product.currentPassword);
		String approveToast = product.waitForToast();
		product.waitForLoading();
		sa.assertEquals(approveToast, "Product activated successfully", "Activated toaster message", approveToast);
		ScreenshotUtil.resumeStepNumbering();
		sa.assertAll();

	}

	@Test(groups = { "Logout" })
	public void Logout() throws Throwable {
		log.info("--- Executing Final Logout ---");
		product.logout();
		log.info("Clicked logout button");
		product.waitForToast();
		product.waitForLoading();
		ScreenshotUtil.capture();
		log.info("--- Department Test Case Execution Finished ---");
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

	@Test(groups = { "ClickActionsRep" })
	public void Click_Actions_1() throws Throwable {
		if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
			product.switchUser(USERNAME1, PASSWORD1, PC_DB_NAME, MASTER_MODULE, SUB_MASTER_MODULE);
		}
		ScreenshotUtil.nextStep();
		Click_Actions();
	}

	@Test(groups = { "ClickActionsRep" })
	public void Click_Actions_2() throws Throwable {
		if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
			product.switchUser(USERNAME2, PASSWORD2, PC_DB_NAME, MASTER_MODULE, SUB_MASTER_MODULE);
		}
		Click_Actions();
	}

	@Test(groups = { "ClickActionsRep" })
	public void Click_Actions_3() throws Throwable {
		if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
			product.switchUser(USERNAME1, PASSWORD1, PC_DB_NAME, MASTER_MODULE, SUB_MASTER_MODULE);
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

	@Test(groups = { "Product_Inactive_ReviewRep" })
	public void Product_Active_Review_2() throws Throwable {
		Product_Active_Review();
	}

}
