package testCasesForOQProjects;

import static configData.MaterialSpecificationData.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.MaterialSpecification;
import utilities.DataProviders;
import utilities.ScreenshotUtil;
import utilities.materialSpecificationData;

public class MaterialSpecification_TC extends OQBaseModule_TC {

    private MaterialSpecification materialspecification;
    

    @BeforeClass
    @Parameters({ "configFile" })
    public void setUp(@Optional("oqmaterialspecification.properties") String configFile) throws Exception {
        log.info("--- Starting Material Specification Test Case Setup with config: {} ---", configFile);
        configData.MaterialSpecificationData.loadProperties(configFile);

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
        VIEW_ACTION_VAL = MATERIALSPEC_VIEW_ACTION;

        boolean screenshotsEnabled = "yes".equalsIgnoreCase(TAKE_SCREENSHOTS);
        ScreenshotUtil.setIsEnabled(screenshotsEnabled);
        if (screenshotsEnabled) {
            ScreenshotUtil.loadTemplateForEndAppend(TEMPLATE_PATH, OUTPUT_PATH);
            ScreenshotUtil.updateHeaderCellText(ACTUALHEADER, EXPECTEDHEADER);
            ScreenshotUtil.initScript(SCRIPT_NUMBER);
        }

        browserOpen();
        materialspecification = new MaterialSpecification(driver);
        this.pageObject = materialspecification;
        materialspecification.setTableHeaders(TABLE_HEADERS);
    }

    @Test(groups = { "Creation" })
    public void Creation_Of_MaterialSpecification() throws Throwable {
        log.info("--- Navigating to Create Material Specification Screen ---");
        materialspecification.Create();
        materialspecification.waitForLoading();
        ScreenshotUtil.capture();
        ScreenshotUtil.nextStep();
        log.info("--- Selecting Material Code: {} ---", MATERIAL_CODE);
        currentEntryName = SPECIFICATION_NUMBER;
        materialspecification.selMaterialCode(MATERIAL_CODE);
        materialspecification.specificationNumber(SPECIFICATION_NUMBER);
        materialspecification.selRequestType(REQUEST_TYPE);
        Thread.sleep(5000);
        ScreenshotUtil.capture();
    }

    @Test(groups = { "Creation" }, dataProvider = "MaterialspecificationData", dataProviderClass = DataProviders.class, singleThreaded = true)
    public void Creation_Of_MaterialSpecification_Rows(materialSpecificationData materialspec) throws Throwable {
        materialspecification.nameOfTheTest(materialspec.get_NameOfTheTest());
        if ("Yes".equalsIgnoreCase(materialspec.get_ReqSub())) {
            materialspecification.selReqSub();
        }
        if (materialspec.get_SpecificationLimit() != null && !materialspec.get_SpecificationLimit().trim().isEmpty()) {
            materialspecification.specificaitionLimit(materialspec.get_SpecificationLimit());
        }
        if (materialspec.get_Validation() != null && !materialspec.get_Validation().trim().isEmpty()) {
            materialspecification.selValidation(materialspec.get_Validation());
            if ("Yes".equalsIgnoreCase(materialspec.get_Validation().trim())) {
                materialspecification.specificaitionLimitMin(materialspec.get_SpecificationLimitMin());
                materialspecification.specificaitionLimitMax(materialspec.get_SpecificationLimitMax());
                materialspecification.selUOM(materialspec.get_UOM());
            }
        }
        if (materialspec.get_Buttons() != null && !materialspec.get_Buttons().trim().isEmpty()) {
            if ("Add".equalsIgnoreCase(materialspec.get_Buttons().trim())) {
                materialspecification.addButton();
            } else if ("Plus".equalsIgnoreCase(materialspec.get_Buttons().trim())) {
                materialspecification.plusButton();
            }
        }
    }

    @Test(groups = { "Creation" })
    public void Creation_Of_MaterialSpecification_Submit() throws Throwable {
        materialspecification.createSubmit();
        materialspecification.authenticate(materialspecification.currentPassword);
        String authToast = materialspecification.waitForToast();
        sa.assertEquals(authToast, "Material Specification created successfully", "Created Toaster message", authToast);
        sa.assertAll();
    }

    @Test(groups = { "ClickActions" })
    public void Click_Actions1() throws Throwable {
        switchUserIfMulti(USERNAME1_VAL, PASSWORD1_VAL);
        log.info("--- Attempting to open Actions Menu (Index 1) for: {} ---", currentEntryName);
        materialspecification.clickActions(currentEntryName, "1");
        log.info("Successfully opened Actions menu for {}", currentEntryName);
        capture();
        afterClickActionsScreenshot("1");
    }

    @Test(groups = { "ClickActions" })
    public void Click_Actions2() throws Throwable {
        switchUserIfMulti(USERNAME2_VAL, PASSWORD2_VAL);
        log.info("--- Attempting to open Actions Menu (Index 2) for: {} ---", currentEntryName);
        materialspecification.clickActions(currentEntryName, "1");
        log.info("Successfully opened Actions menu index 1 for {}", currentEntryName);
        capture();
        afterClickActionsScreenshot("1");
    }

    protected void afterClickActionsScreenshot(String... actions) throws Throwable {
        freezeCapture();
        try {
            materialspecification.clickActions(combine(currentEntryName, actions));
        } catch (Exception e) {
            log.error("Failed to re-click actions during freeze capture", e);
        }
        resumeCapture();
    }



    @Test(groups = { "materialSpecReviewReturn_materialSpecEdit" })
    public void materialSpec_Review_Return_and_Edit() throws Throwable {
        if (MATERIALSPEC_RETURN_ACTION_IN_REVIEW.equalsIgnoreCase("yes")) {
            switchUserIfMulti(USERNAME2_VAL, PASSWORD2_VAL);
            performReturnReview(REVIEW_RETURN_REMARKS, "Material Specification returned successfully", "1");
            switchUserIfMulti(USERNAME1_VAL, PASSWORD1_VAL);
            performEdit(EDIT_SPECIFICATION_NUMBER_IN_REVIEW_RETURN, "Material Specification updated successfully", "1");
            sa.assertAll();
        }
    }

    @Test(groups = { "materialSpecReview" })
    public void materialSpecReview() throws Throwable {
        switchUserIfMulti(USERNAME2_VAL, PASSWORD2_VAL);
        performReview(REVIEW_REMARKS, "Material Specification reviewed successfully", "1");
        sa.assertAll();
    }

    @Test(groups = { "materialSpecApproveReturn_materialSpecEdit_materialSpecReview" })
    public void materialSpec_Approve_Return_and_Edit_and_Review() throws Throwable {
        if (MATERIALSPEC_RETURN_ACTION_IN_APPROVE.equalsIgnoreCase("yes")) {
            switchUserIfMulti(USERNAME3_VAL, PASSWORD3_VAL);
            performReturnApprove(APPROVE_RETURN_REMARKS, "Material Specification returned successfully", "1");
            switchUserIfMulti(USERNAME1_VAL, PASSWORD1_VAL);
            performEdit(EDIT_SPECIFICATION_NUMBER_IN_APPROVE_RETURN, "Material Specification updated successfully", "1");
            switchUserIfMulti(USERNAME2_VAL, PASSWORD2_VAL);
            performReview(REVIEW_REMARKS, "Material Specification reviewed successfully", "1");
            sa.assertAll();
        }
    }

    @Test(groups = { "materialSpecApprove" })
    public void materialSpecApprove() throws Throwable {
        switchUserIfMulti(USERNAME3_VAL, PASSWORD3_VAL);
        performApprove(APPROVE_REMARKS, "Material Specification approved successfully", "1");
        sa.assertAll();
    }

    @Test(groups = { "MaterialSpecUpdate" })
    public void materialSpecUpdate() throws Throwable {
        if (MATERIALSPEC_UPDATE_AFTER_APPROVE.equalsIgnoreCase("yes")) {
            switchUserIfMulti(USERNAME1_VAL, PASSWORD1_VAL);
            performUpdate(UPDATE_NAME_OF_THE_TEST, UPDATE_SPECIFICATION, UPDATE_VALIDATION, "1");
            switchUserIfMulti(USERNAME2_VAL, PASSWORD2_VAL);
            performReview(REVIEW_REMARKS, "Material Specification reviewed successfully", "2");
            switchUserIfMulti(USERNAME3_VAL, PASSWORD3_VAL);
            performApprove(APPROVE_REMARKS, "Material Specification approved successfully", "2");
            sa.assertAll();
        }
    }

    
    
    private void performUpdate(String nameofthetest, String specLimit, String val, String... actions) throws Throwable {
    	materialspecification.clickActions(combine(currentEntryName, actions));
    	capture();
    	materialspecification.clickUpdate();
    	materialspecification.waitForLoading();
    	capture();
    	Thread.sleep(5000);
    	capture();
    	materialspecification.clickAddButton();
    	capture();
    	materialspecification.nameOfTheTest(nameofthetest);
        if (specLimit != null) materialspecification.specificaitionLimit(specLimit);
        if (val != null) materialspecification.selValidation(val);
        capture();
        materialspecification.clickSubmit();
        capture();
        materialspecification.authenticate(materialspecification.currentPassword);
        String toast=materialspecification.waitForToast();
        pageObject.waitForToastDisappear();
        materialspecification.waitForLoading();
        capture();
        sa.assertEquals(toast, "Material Specification updated successfully", "Update toast", "Material Specification updated successfully");
    }
 
    @Override
    protected void performClickView() throws Throwable {
        materialspecification.clickPendingView(currentEntryName, "1");
    }

    @Override
    protected void updateEntryName(String newName) throws Throwable {
        materialspecification.specificationNumber(newName);
    }
}
