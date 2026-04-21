
package testCasesForOQProjects;

import static configData.AkronQuickProductData.*;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageObjects.AkronQuickProduct;
import testBase.BaseClass;
import utilities.DatabaseBackupUtil;
import utilities.ScreenshotUtil;
import utilities.SoftAssertionUtil;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class AkronQuickProduct_TC extends OQBaseModule_TC {
	private AkronQuickProduct product;
	private int editIterationCount=0;
	private String sampleNdcNumber;

	@BeforeClass
	@Parameters({ "configFile" })
	public void setUp(@Optional("akronquickproduct.properties") String configFile) throws Exception {
		log.info("--- Starting AkronQuickProduct Test Case Setup with config: {} ---", configFile);

		// Load AkronQuickProduct properties
		configData.AkronQuickProductData.loadProperties(configFile);

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
		VIEW_ACTION_VAL = PRODUCT_VIEW_ACTION;

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
		this.pageObject = product;
		product.setTableHeaders(TABLE_HEADERS);
		log.info("Setup completed. Screenshots enabled: {}", screenshotsEnabled);
	}

	@Override
	protected void updateEntryName(String newName) throws Throwable {
		product.productName(newName);
	}
	
	@Override
	protected void beforeClickActionsScreenshot() {
		capture();
		nextStep();
	}
	
	@Override
	protected void beforeEdit() {
		editIterationCount++;
		if(editIterationCount==1)
		nextStep();
	}

	@Test(groups = { "Creation" })
	public void Creation_Of_Product() throws Throwable {
		log.info("--- Navigating to Create Product Screen ---");
		product.Create();
		product.waitForLoading();
		capture();
		nextStep();

		log.info("--- Creating Product: {} ---", PRODUCT_NAME);
		currentEntryName = PRODUCT_NAME;

		product.productName(PRODUCT_NAME);
		product.productCode(PRODUCT_CODE);
		product.productDesc(PRODUCT_DESCRIPTION);
		product.productNDCNumber(PRODUCT_NDC_NUMBER);
		product.selProductUom(PRODUCT_UOM);
		product.selStorageLocation(STORAGE_LOCATION);
		product.storageCondition(STORAGE_CONDITION);
		product.checkSamplingActivity(SAMPLING_ACTIVITY);

		if ("Yes".equalsIgnoreCase(SAMPLING_ACTIVITY)) {
			product.checkCleaningAgent(CLEANING_AGENT);
		}

		String[] ndcNumbers = NDC_NUMBER.split(",");
		String[] shortCodes = SHORT_CODE.split(",");
		String[] packSizes = PACK_SIZE.split(",");
		String[] ndcDescriptions = NDC_DESCRIPTION.split(",");
		String[] uoms = UOM.split(",");
		String[] gtnNumbers = GTN_NUMBER.split(",");

		for (int i = 0; i < ndcNumbers.length; i++) {
			product.ndcNumber(ndcNumbers[i].trim());
			sampleNdcNumber= ndcNumbers[i].trim();
			product.shortCode(shortCodes[i].trim());
			String uomToUse = (uoms.length == 1) ? uoms[0].trim() : uoms[i].trim();
			product.selUOM(uomToUse);
			product.packSize(packSizes[i].trim());
			product.gtnNumber(gtnNumbers[i].trim());

			if (ndcDescriptions.length > i && !ndcDescriptions[i].trim().isEmpty()) {
				product.ndcDesc(ndcDescriptions[i].trim());
			}

			if (i < ndcNumbers.length - 1) {
				product.enterRow();
			}
		}

		capture();
		product.clickSubmit();
		log.info("Clicked Submit");
		capture();

		product.authenticate(product.currentPassword);
		String authToast = product.waitForToast();
		product.waitForToastDisappear();
		product.waitForLoading();
		capture();
		nextStep();
		capture();
		sa.assertEquals(authToast, "Quick Product created successfully", "Created Toaster message", authToast);
		sa.assertAll();
	}

	
	@Test(groups = { "productReviewReturn_productEdit" })
	public void product_Review_Return_and_Edit() throws Throwable {
		if (PRODUCT_RETURN_ACTION_IN_REVIEW.equalsIgnoreCase("yes")) {
			switchUserIfMulti(USERNAME2_VAL, PASSWORD2_VAL);
			performReturnReview(REVIEW_RETURN_REMARKS, "Quick Product returned successfully");
			switchUserIfMulti(USERNAME1_VAL, PASSWORD1_VAL);
			performEdit(EDIT_PRODUCT_IN_REVIEW_RETURN,"Quick Product updated successfully" );
			sa.assertAll();

		} else {
			log.info("Product Review Return and Edit skipped based on configuration");
		}
	}

	@Test(groups = { "productReview" })
	public void productReview() throws Throwable {
		switchUserIfMulti(USERNAME2_VAL, PASSWORD2_VAL);
		performReview(REVIEW_REMARKS, "Quick Product reviewed successfully");
		sa.assertAll();
	}

	@Test(groups = { "productApproveReturn_productEdit_productReview" })
	public void product_Approve_Return_and_Edit_and_Review() throws Throwable {
		if (PRODUCT_RETURN_ACTION_IN_APPROVE.equalsIgnoreCase("yes")) {
			switchUserIfMulti(USERNAME3_VAL, PASSWORD3_VAL);
			capture();
			nextStep();
			performReturnApprove(APPROVE_RETURN_REMARKS, "Quick Product returned successfully");

			switchUserIfMulti(USERNAME1_VAL, PASSWORD1_VAL);

			performEdit(EDIT_PRODUCT_IN_APPROVE_RETURN, "Quick Product updated successfully");

			switchUserIfMulti(USERNAME2_VAL, PASSWORD2_VAL);
			performReview(REVIEW_REMARKS, "Quick Product reviewed successfully");
			sa.assertAll();

		} else {
			log.info("Product Approve Return and Edit and Review skipped based on configuration");
		}
	}

	@Test(groups = { "productApprove" })
	public void productApprove() throws Throwable {
		switchUserIfMulti(USERNAME3_VAL, PASSWORD3_VAL);
		performApprove(APPROVE_REMARKS, "Quick Product approved successfully");
		product.waitForLoading();
		product.scrollToBottom(driver);
		capture();
		product.masterClick("NDC");
		log.info("Clicked on NDC Module");
		product.waitForLoading();
		capture();
		sa.assertAll();
	}

	@Test(groups = { "duplicationcheck" })
	public void Duplication_Check() throws Throwable {
		switchUserIfMulti(USERNAME1_VAL, PASSWORD1_VAL);
		log.info("--- Checking Duplication Check ---");
		product.Create();
		capture();
		product.productName(currentEntryName);
		capture();
		product.productCode(PRODUCT_CODE);
		capture();
		product.ndcNumber(sampleNdcNumber);
		capture();
		product.clickProductName();
		product.waitForToast();
		product.waitForToastDisappear();
		capture();
	}
	
	@Test(groups = { "ClickActions" })
	public void Click_Actions2() throws Throwable {
		switchUserIfMulti(USERNAME2, PASSWORD2);
		Click_Actions();
	}
	
	@Override
	protected void beforeLogout() {
		nextStep();
	}
	
}
