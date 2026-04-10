package testCasesForOQProjects;

import static configData.MaterialCategoryData.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.MaterialCategory;
import utilities.ScreenshotUtil;

public class MaterialCategory_TC extends OQBaseModule_TC {

    private MaterialCategory category;

    @BeforeClass
    @Parameters({ "configFile" })
    public void setUp(@Optional("materialcategory.properties") String configFile) throws Exception {
        log.info("--- Starting Material Category Test Case Setup with config: {} ---", configFile);
        configData.MaterialCategoryData.loadProperties(configFile);

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
        VIEW_ACTION_VAL = MATERIAL_CATEGORY_VIEW_ACTION;

        boolean screenshotsEnabled = "yes".equalsIgnoreCase(TAKE_SCREENSHOTS);
        ScreenshotUtil.setIsEnabled(screenshotsEnabled);
        if (screenshotsEnabled) {
            ScreenshotUtil.loadTemplateForEndAppend(TEMPLATE_PATH, OUTPUT_PATH);
            ScreenshotUtil.updateHeaderCellText(ACTUALHEADER, EXPECTEDHEADER);
            ScreenshotUtil.initScript(SCRIPT_NUMBER);
        }

        browserOpen();
        category = new MaterialCategory(driver);
        this.pageObject = category;
        category.setTableHeaders(TABLE_HEADERS);
    }

    @Test(groups = { "Creation" })
    public void Creation_Of_Material_Category() throws Throwable {
        log.info("--- Navigating to Create Material Category Screen ---");
        nextStep();
        category.Create();
        category.waitForLoading();
        capture();
        nextStep();
        log.info("--- Creating Material Category: {} ---", MATERIAL_CATEGORY_NAME);
        currentEntryName = MATERIAL_CATEGORY_NAME;
        category.materialCategoryName(MATERIAL_CATEGORY_NAME);
        category.selSamplingPlan(SAMPLING_PLAN);
        category.selWeightVerificationPlan(WEIGHT_VERIFICATION_PLAN);
        category.clickSubmit();
        ScreenshotUtil.capture();
        category.authenticate(category.currentPassword);
        String authToast = category.waitForToast();
        category.waitForLoading();
        sa.assertEquals(authToast, "Material Category created successfully", "Created Toaster message", authToast);
        sa.assertAll();
    }


    @Test(groups = { "categoryReviewReturn_categoryEdit" })
    public void material_Category_Review_Return_and_Edit() throws Throwable {
        if (MATERIAL_CATEGORY_RETURN_ACTION_IN_REVIEW.equalsIgnoreCase("yes")) {
            switchUserIfMulti(USERNAME2_VAL, PASSWORD2_VAL);
            performReturnReview(REVIEW_RETURN_REMARKS, "Material Category returned successfully");
            switchUserIfMulti(USERNAME1_VAL, PASSWORD1_VAL);
            performEdit(EDIT_MATERIAL_CATEGORY_IN_REVIEW_RETURN, "Material Category updated successfully");
            sa.assertAll();
        }
    }

    @Test(groups = { "categoryReview" })
    public void materialCategoryReview() throws Throwable {
        switchUserIfMulti(USERNAME2_VAL, PASSWORD2_VAL);
        performReview(REVIEW_REMARKS, "Material Category reviewed successfully");
        sa.assertAll();
    }

    @Test(groups = { "categoryApproveReturn_categoryEdit_categoryReview" })
    public void material_Category_Approve_Return_and_Edit_and_Review() throws Throwable {
        if (MATERIAL_CATEGORY_RETURN_ACTION_IN_APPROVE.equalsIgnoreCase("yes")) {
            switchUserIfMulti(USERNAME3_VAL, PASSWORD3_VAL);
            performReturnApprove(APPROVE_RETURN_REMARKS, "Material Category returned successfully");
            switchUserIfMulti(USERNAME1_VAL, PASSWORD1_VAL);
            performEdit(EDIT_MATERIAL_CATEGORY_IN_APPROVE_RETURN, "Material Category updated successfully");
            switchUserIfMulti(USERNAME2_VAL, PASSWORD2_VAL);
            performReview(REVIEW_REMARKS, "Material Category reviewed successfully");
            sa.assertAll();
        }
    }

    @Test(groups = { "categoryApprove" })
    public void materialCategoryApprove() throws Throwable {
        switchUserIfMulti(USERNAME3_VAL, PASSWORD3_VAL);
        performApprove(APPROVE_REMARKS, "Material Category approved successfully");
        sa.assertAll();
    }
    @Test(groups = { "ClickActions" })
    public void Click_Actions1() throws Throwable {
        switchUserIfMulti(USERNAME1_VAL, PASSWORD1_VAL);
        Click_Actions();
    }

    @Test(groups = { "ClickActions" })
    public void Click_Actions2() throws Throwable {
        switchUserIfMulti(USERNAME2_VAL, PASSWORD2_VAL);
        Click_Actions();
    }

    @Override
    protected void beforeTitleClick() { nextStep(); }
    @Override
    protected void beforeMasterClick() { nextStep(); }

    @Override
    protected void updateEntryName(String newName) throws Throwable {
        category.materialCategoryName(newName);
    }
}
