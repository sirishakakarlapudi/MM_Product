package testCasesForOQProjects;

import static configData.ProductSpecificationData.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.ProductSpecification;
import utilities.DataProviders;
import utilities.ScreenshotUtil;
import utilities.productSpecificationData;

public class ProductSpecification_TC extends OQBaseModule_TC {

    private ProductSpecification productspecification;

    @BeforeClass
    @Parameters({ "configFile" })
    public void setUp(@Optional("productspecification.properties") String configFile) throws Exception {
        log.info("--- Starting Product Specification Test Case Setup with config: {} ---", configFile);
        configData.ProductSpecificationData.loadProperties(configFile);

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
        VIEW_ACTION_VAL = PRODUCTSPEC_VIEW_ACTION;

        boolean screenshotsEnabled = "yes".equalsIgnoreCase(TAKE_SCREENSHOTS);
        ScreenshotUtil.setIsEnabled(screenshotsEnabled);
        if (screenshotsEnabled) {
            ScreenshotUtil.loadTemplateForEndAppend(TEMPLATE_PATH, OUTPUT_PATH);
            ScreenshotUtil.updateHeaderCellText(ACTUALHEADER, EXPECTEDHEADER);
            ScreenshotUtil.initScript(SCRIPT_NUMBER);
        }

        browserOpen();
        productspecification = new ProductSpecification(driver);
        this.pageObject = productspecification;
        productspecification.setTableHeaders(TABLE_HEADERS);
    }

    @Override
    protected void updateEntryName(String newName) throws Throwable {
        productspecification.specificationNumber(newName);
    }

    @Test(groups = { "Creation" })
    public void Creation_Of_ProductSpecification() throws Throwable {
        log.info("--- Navigating to Create Product Specification Screen ---");
        productspecification.Create();
        productspecification.waitForLoading();
        capture();
        nextStep();

        log.info("--- Selecting Product Code: {} ---", PRODUCT_CODE);
        currentEntryName = SPECIFICATION_NUMBER;
        productspecification.selProductCode(PRODUCT_CODE);
        productspecification.specificationNumber(SPECIFICATION_NUMBER);
        Thread.sleep(5000);
        capture();
    }

    @Test(groups = { "Creation" }, dataProvider = "ProductspecificationData", dataProviderClass = DataProviders.class, singleThreaded = true)
    public void Creation_Of_ProductSpecification_Rows(productSpecificationData productspec) throws Throwable {
        productspecification.nameOfTheTest(productspec.get_NameOfTheTest());
        if ("Yes".equalsIgnoreCase(productspec.get_ReqSub())) {
            productspecification.selReqSub();
        }
        if (productspec.get_SpecificationLimit() != null && !productspec.get_SpecificationLimit().trim().isEmpty()) {
            productspecification.specificaitionLimit(productspec.get_SpecificationLimit());
        }
        if (productspec.get_Validation() != null && !productspec.get_Validation().trim().isEmpty()) {
            productspecification.selValidation(productspec.get_Validation());
            if ("Yes".equalsIgnoreCase(productspec.get_Validation().trim())) {
                productspecification.specificaitionLimitMin(productspec.get_SpecificationLimitMin());
                productspecification.specificaitionLimitMax(productspec.get_SpecificationLimitMax());
                productspecification.selUOM(productspec.get_UOM());
            }
        }
        if (productspec.get_Buttons() != null && !productspec.get_Buttons().trim().isEmpty()) {
            if ("Add".equalsIgnoreCase(productspec.get_Buttons().trim())) {
                productspecification.addButton();
            } else if ("Plus".equalsIgnoreCase(productspec.get_Buttons().trim())) {
                productspecification.plusButton();
            }
        }
    }

    @Test(groups = { "Creation" })
    public void Creation_Of_ProductSpecification_Submit() throws Throwable {
        productspecification.clickSubmit();
        productspecification.authenticate(productspecification.currentPassword);
        String authToast = productspecification.waitForToast();
        sa.assertEquals(authToast, "Product Specification created successfully", "Created Toaster message", authToast);
        sa.assertAll();
    }

    @Test(groups = { "ClickActions" })
    public void Click_Actions1() throws Throwable {
        switchUserIfMulti(USERNAME1_VAL, PASSWORD1_VAL);
        log.info("--- Attempting to open Actions Menu (Index 1) for: {} ---", currentEntryName);
        productspecification.clickActions(currentEntryName, "1");
        log.info("Successfully opened Actions menu for {}", currentEntryName);
        capture();
    }

    @Test(groups = { "ClickActions" })
    public void Click_Actions2() throws Throwable {
        switchUserIfMulti(USERNAME2_VAL, PASSWORD2_VAL);
        log.info("--- Attempting to open Actions Menu (Index 1) for: {} ---", currentEntryName);
        productspecification.clickActions(currentEntryName, "1");
        log.info("Successfully opened Actions menu for {}", currentEntryName);
        capture();
    }

    @Test(groups = { "productSpecReviewReturn_productSpecEdit" })
    public void productSpec_Review_Return_and_Edit() throws Throwable {
        if (PRODUCTSPEC_RETURN_ACTION_IN_REVIEW.equalsIgnoreCase("yes")) {
            switchUserIfMulti(USERNAME2_VAL, PASSWORD2_VAL);
            performReturnReview(REVIEW_RETURN_REMARKS, "Product Specification returned successfully", "1");
            
            switchUserIfMulti(USERNAME1_VAL, PASSWORD1_VAL);
            nextStep();
            performEdit(EDIT_SPECIFICATION_NUMBER_IN_REVIEW_RETURN, "Product Specification updated successfully", "1");
            sa.assertAll();
        }
    }

    @Test(groups = { "productSpecReview" })
    public void productSpecReview() throws Throwable {
        switchUserIfMulti(USERNAME2_VAL, PASSWORD2_VAL);
        performReview(REVIEW_REMARKS, "Product Specification reviewed successfully", "1");
        sa.assertAll();
    }

    @Test(groups = { "productSpecApproveReturn_productSpecEdit_productSpecReview" })
    public void productSpec_Approve_Return_and_Edit_and_Review() throws Throwable {
        if (PRODUCTSPEC_RETURN_ACTION_IN_APPROVE.equalsIgnoreCase("yes")) {
            switchUserIfMulti(USERNAME3_VAL, PASSWORD3_VAL);
            performReturnApprove(APPROVE_RETURN_REMARKS, "Product Specification returned successfully", "1");
            
            switchUserIfMulti(USERNAME1_VAL, PASSWORD1_VAL);
            nextStep();
            performEdit(EDIT_SPECIFICATION_NUMBER_IN_APPROVE_RETURN, "Product Specification updated successfully", "1");
            
            switchUserIfMulti(USERNAME2_VAL, PASSWORD2_VAL);
            performReview(REVIEW_REMARKS, "Product Specification reviewed successfully", "1");
            sa.assertAll();
        }
    }

    @Test(groups = { "productSpecApprove" })
    public void productSpecApprove() throws Throwable {
        switchUserIfMulti(USERNAME3_VAL, PASSWORD3_VAL);
        performApprove(APPROVE_REMARKS, "Product Specification approved successfully", "1");
        sa.assertAll();
    }

    @Test(groups = { "ProductSpecUpdate" })
    public void prodSpecUpdate() throws Throwable {
        if (PRODUCTSPEC_UPDATE_AFTER_APPROVE.equalsIgnoreCase("yes")) {
            switchUserIfMulti(USERNAME1_VAL, PASSWORD1_VAL);
            performUpdate(UPDATE_NAME_OF_THE_TEST, UPDATE_SPECIFICATION, UPDATE_VALIDATION, "1");
            
            switchUserIfMulti(USERNAME2_VAL, PASSWORD2_VAL);
            performReview(REVIEW_REMARKS, "Product Specification reviewed successfully", "2");
            
            switchUserIfMulti(USERNAME3_VAL, PASSWORD3_VAL);
            performApprove(APPROVE_REMARKS, "Product Specification approved successfully", "2");
            sa.assertAll();
        }
    }

    private void performUpdate(String nameofthetest, String specLimit, String val, String... actions) throws Throwable {
        productspecification.clickActions(combine(currentEntryName, actions));
        capture();
        productspecification.clickUpdate();
        productspecification.waitForLoading();
        capture();
        Thread.sleep(5000);
        capture();
        productspecification.clickAddButton();
        capture();
        productspecification.nameOfTheTest(nameofthetest);
        if (specLimit != null) productspecification.specificaitionLimit(specLimit);
        if (val != null) productspecification.selValidation(val);
        capture();
        productspecification.clickSubmit();
        capture();
        productspecification.authenticate(productspecification.currentPassword);
        String toast = productspecification.waitForToast();
        pageObject.waitForToastDisappear();
        productspecification.waitForLoading();
        capture();
        sa.assertEquals(toast, "Product Specification version updated successfully", "Update toast", toast);
    }

    @Override
    protected void performClickView() throws Throwable {
        productspecification.clickView(currentEntryName, "1");
    }
}
