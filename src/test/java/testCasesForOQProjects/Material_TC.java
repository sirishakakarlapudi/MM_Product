package testCasesForOQProjects;

import static configData.MaterialData.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.Material;
import utilities.ScreenshotUtil;

public class Material_TC extends OQBaseModule_TC {

	private Material material;

	@BeforeClass
	@Parameters({ "configFile" })
	public void setUp(@Optional("material.properties") String configFile) throws Exception {
		log.info("--- Starting Material Test Case Setup with config: {} ---", configFile);
		configData.MaterialData.loadProperties(configFile);

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
		VIEW_ACTION_VAL = MATERIAL_VIEW_ACTION;

		boolean screenshotsEnabled = "yes".equalsIgnoreCase(TAKE_SCREENSHOTS);
		ScreenshotUtil.setIsEnabled(screenshotsEnabled);
		if (screenshotsEnabled) {
			ScreenshotUtil.loadTemplateForEndAppend(TEMPLATE_PATH, OUTPUT_PATH);
			ScreenshotUtil.updateHeaderCellText(ACTUALHEADER, EXPECTEDHEADER);
			ScreenshotUtil.initScript(SCRIPT_NUMBER);
		}

		browserOpen();
		material = new Material(driver);
		this.pageObject = material;
		material.setTableHeaders(TABLE_HEADERS);
	}

	@Test(groups = { "Creation" })
	public void Creation_Of_Material() throws Throwable {
		log.info("--- Navigating to Create Material Screen ---");
		nextStep();
		material.Create();
		material.waitForLoading();
		ScreenshotUtil.capture();
		nextStep();

		log.info("--- Creating Material: {} ---", MATERIAL_NAME);
		currentEntryName = MATERIAL_NAME;
		material.materialName(MATERIAL_NAME);
		material.materialCode(MATERIAL_CODE);
		material.selTypeOfMaterial(TYPE_OF_MATERIAL);
		material.selMaterialCategory(MATERIAL_CATEGORY);
		material.selUOM(UOM);

		if (material.isStorageConditionDisplayed()) {
			material.storageCondition(STORAGE_CONDITION);
		}
		material.selStorageLocation(STORAGE_LOCATION);
		material.setSamplingActivity(SAMPLING_ACTIVITY);
		material.setDispensingActivity(DISPENSING_ACTIVITY);
		if (material.isMixedAnalysisIsDisplayed()) {
			material.setMixedAnalysis(MIXED_ANALYSIS);
		}
		material.setWeightVerification(WEIGHT_VERIFICATION);

		if (material.isReceivingBayIsDisplayed()) {
			if ("Yes".equalsIgnoreCase(WEIGHT_VERIFICATION)) {
				material.setReceivingBay(RECEIVING_BAY);
			}
		}

		if ("Yes".equalsIgnoreCase(SAMPLING_ACTIVITY) || "Yes".equalsIgnoreCase(DISPENSING_ACTIVITY)) {
			material.setCleaningAgent(CLEANING_AGENT);
		}
		capture();
		material.selSupplier(SUPPLIER);
		material.waitForLoading();
		capture();
		material.setManufacturerRequired("No");
		material.waitForLoading();
		capture();
		material.selManufacturer(MANUFACTURER);
		material.waitForLoading();
		capture();
		material.clickSaveDetails();
		material.confirmDialog("No");
		capture();
		material.waitForLoading();
		material.clickSubmit();
		ScreenshotUtil.capture();
		material.authenticate(material.currentPassword);
		String authToast = material.waitForToast();
		material.waitForLoading();
		sa.assertEquals(authToast, "Material created successfully", "Created Toaster message", authToast);
		sa.assertAll();
	}

	@Test(groups = { "materialReviewReturn_materialEdit" })
	public void material_Review_Return_and_Edit() throws Throwable {
		if (MATERIAL_RETURN_ACTION_IN_REVIEW.equalsIgnoreCase("yes")) {
			switchUserIfMulti(USERNAME2_VAL, PASSWORD2_VAL);
			performReturnReview(REVIEW_RETURN_REMARKS, "Material returned successfully");
			switchUserIfMulti(USERNAME1_VAL, PASSWORD1_VAL);
			performEdit(EDIT_MATERIAL_IN_REVIEW_RETURN, "Material updated succesfully");
			sa.assertAll();
		}
	}

	@Test(groups = { "materialReview" })
	public void materialReview() throws Throwable {
		switchUserIfMulti(USERNAME2_VAL, PASSWORD2_VAL);
		performReview(REVIEW_REMARKS, "Material reviewed successfully");
		sa.assertAll();
	}

	@Test(groups = { "materialApproveReturn_materialEdit_materialReview" })
	public void material_Approve_Return_and_Edit_and_Review() throws Throwable {
		if (MATERIAL_RETURN_ACTION_IN_APPROVE.equalsIgnoreCase("yes")) {
			switchUserIfMulti(USERNAME3_VAL, PASSWORD3_VAL);
			performReturnApprove(APPROVE_RETURN_REMARKS, "Material returned successfully");
			switchUserIfMulti(USERNAME1_VAL, PASSWORD1_VAL);
			performEdit(EDIT_MATERIAL_IN_APPROVE_RETURN, "Material updated succesfully");
			switchUserIfMulti(USERNAME2_VAL, PASSWORD2_VAL);
			performReview(REVIEW_REMARKS, "Material reviewed successfully");
			sa.assertAll();
		}
	}

	@Test(groups = { "materialApprove" })
	public void materialApprove() throws Throwable {
		switchUserIfMulti(USERNAME3_VAL, PASSWORD3_VAL);
		performApprove(APPROVE_REMARKS, "Material approved successfully");
		sa.assertAll();
	}

	@Test(groups = { "materialUpdate" })
	public void materialUpdate() throws Throwable {
		if (MATERIAL_UPDATE_AFTER_APPROVE.equalsIgnoreCase("yes")) {
			switchUserIfMulti(USERNAME1_VAL, PASSWORD1_VAL);
			log.info("Opening Update screen (After Approve)");
			material.clickActions(currentEntryName);
			material.clickUpdate();
			material.waitForLoading();
			capture();
			material.clickAddButton();
			capture();
			material.selSupplier(UPDATE_SUPPLIER);
			material.waitForLoading();
			capture();
			material.setManufacturerRequired("No");
			material.waitForLoading();
			capture();
			material.selManufacturer(UPDATE_MANUFACTURER);
			material.waitForLoading();
			capture();
			material.clickSaveDetails();
			material.confirmDialog("No");
			material.waitForLoading();
			capture();
			material.clickUpdate();
			capture();
			material.authenticate(material.currentPassword);
			String toast = pageObject.waitForToast();
			pageObject.waitForToastDisappear();
			pageObject.waitForLoading();
			capture();
			sa.assertEquals(toast, "Material Updated Request raised successfully" , "Update Request toast", "Material Updated Request reviewed successfully");
			switchUserIfMulti(USERNAME2_VAL, PASSWORD2_VAL);
			performReview(REVIEW_REMARKS, "Material Updated Request reviewed successfully");
			switchUserIfMulti(USERNAME3_VAL, PASSWORD3_VAL);
			performApprove(APPROVE_REMARKS, "Material Update Request approved successfully");
			sa.assertAll();
			
		}
	}

	@Test(groups = { "vendorDetails" })
	public void vendorDetails() throws Throwable {
		log.info("--- Switching User to: {} ---", USERNAME1_VAL);
		material.logout();
		log.info("Logged out previous user");
		material.login(USERNAME1_VAL, PASSWORD1_VAL, PC_DB_NAME_VAL);
		material.click_titleMasters();
		capture();
		material.waitForLoading();
		material.masterClick("Vendor Details");
		capture();
		material.masterClick("Vendor Details List");
		capture();
		material.waitForLoading();
		material.selMaterialName_selMaterialCode(currentEntryName + "[ " + MATERIAL_CODE + " ]");
		capture();
		material.clickSearch();
		capture();
	}

	@Test(groups = { "ClickActions" })
	public void Click_Actions2() throws Throwable {
		switchUserIfMulti(USERNAME2_VAL, PASSWORD2_VAL);
		Click_Actions();
	}
	
	 @Override
	    protected void beforeLogout() { nextStep(); }

	@Override
	protected void updateEntryName(String newName) throws Throwable {
		material.materialName(newName);
	}
}
