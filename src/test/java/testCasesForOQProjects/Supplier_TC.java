package testCasesForOQProjects;

import static configData.SupplierData.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.Supplier;
import utilities.ScreenshotUtil;

public class Supplier_TC extends OQBaseModule_TC {

    private Supplier supplier;

    @BeforeClass
    @Parameters({ "configFile" })
    public void setUp(@Optional("supplier.properties") String configFile) throws Exception {
        log.info("--- Starting Supplier Test Case Setup with config: {} ---", configFile);
        configData.SupplierData.loadProperties(configFile);

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
        VIEW_ACTION_VAL = SUPPLIER_VIEW_ACTION;

        boolean screenshotsEnabled = "yes".equalsIgnoreCase(TAKE_SCREENSHOTS);
        ScreenshotUtil.setIsEnabled(screenshotsEnabled);
        if (screenshotsEnabled) {
            ScreenshotUtil.loadTemplateForEndAppend(TEMPLATE_PATH, OUTPUT_PATH);
            ScreenshotUtil.updateHeaderCellText(ACTUALHEADER, EXPECTEDHEADER);
            ScreenshotUtil.initScript(SCRIPT_NUMBER);
        }

        browserOpen();
        supplier = new Supplier(driver);
        this.pageObject = supplier;
        supplier.setTableHeaders(TABLE_HEADERS);
    }

    @Test(groups = { "Creation" })
    public void Creation_Of_Supplier() throws Throwable {
        log.info("--- Navigating to Create Supplier Screen ---");
        supplier.Create();
        supplier.waitForLoading();
        capture();
        nextStep();
        
        log.info("--- Creating Supplier: {} ---", SUPPLIER_NAME);
        currentEntryName = SUPPLIER_NAME;
        supplier.supplierName(SUPPLIER_NAME);
        supplier.supplierAddress(SUPPLIER_ADDRESS);
        supplier.supplierState(SUPPLIER_STATE);
        supplier.supplierCity(SUPPLIER_CITY);
        supplier.supplierPincode(SUPPLIER_PINCODE);
        supplier.selSupplierRegion(SUPPLIER_REGION);

        supplier.clickSubmit();
        capture();
        supplier.authenticate(supplier.currentPassword);
        String authToast = supplier.waitForToast();
        supplier.waitForToastDisappear();
        supplier.waitForLoading();
        capture();
        sa.assertEquals(authToast, "Supplier created successfully", "Created Toaster message", authToast);
        sa.assertAll();
    }


    @Test(groups = { "supplierReviewReturn_supplierEdit" })
    public void supplier_Review_Return_and_Edit() throws Throwable {
        if (SUPPLIER_RETURN_ACTION_IN_REVIEW.equalsIgnoreCase("yes")) {
            switchUserIfMulti(USERNAME2_VAL, PASSWORD2_VAL);
            performReturnReview(REVIEW_RETURN_REMARKS, "Supplier returned successfully");
            switchUserIfMulti(USERNAME1_VAL, PASSWORD1_VAL);
            performEdit(EDIT_SUPPLIER_IN_REVIEW_RETURN, "Supplier updated successfully");
            sa.assertAll();
        }
    }

    @Test(groups = { "supplierReview" })
    public void supplierReview() throws Throwable {
        switchUserIfMulti(USERNAME2_VAL, PASSWORD2_VAL);
        performReview(REVIEW_REMARKS, "Supplier reviewed successfully");
        sa.assertAll();
    }

    @Test(groups = { "supplierApproveReturn_supplierEdit_supplierReview" })
    public void supplier_Approve_Return_and_Edit_and_Review() throws Throwable {
        if (SUPPLIER_RETURN_ACTION_IN_APPROVE.equalsIgnoreCase("yes")) {
            switchUserIfMulti(USERNAME3_VAL, PASSWORD3_VAL);
            performReturnApprove(APPROVE_RETURN_REMARKS, "Supplier returned successfully");
            switchUserIfMulti(USERNAME1_VAL, PASSWORD1_VAL);
            performEdit(EDIT_SUPPLIER_IN_APPROVE_RETURN, "Supplier updated successfully");
            switchUserIfMulti(USERNAME2_VAL, PASSWORD2_VAL);
            performReview(REVIEW_REMARKS, "Supplier reviewed successfully");
            sa.assertAll();
        }
    }

    @Test(groups = { "supplierApprove" })
    public void supplierApprove() throws Throwable {
        switchUserIfMulti(USERNAME3_VAL, PASSWORD3_VAL);
        performApprove(APPROVE_REMARKS, "Supplier approved successfully");
        sa.assertAll();
    }
    
    @Test(groups = { "checkStatus" })
    public void checkStatus() throws Throwable {
    	switchUserIfMulti(USERNAME1_VAL, PASSWORD1_VAL);
    }
    
   
    @Test(groups = { "ClickActions" })
    public void Click_Actions1() throws Throwable {
        switchUserIfMulti(USERNAME1_VAL, PASSWORD1_VAL);
        nextStep();
        Click_Actions();
    }

    @Test(groups = { "ClickActions" })
    public void Click_Actions2() throws Throwable {
        switchUserIfMulti(USERNAME2_VAL, PASSWORD2_VAL);
        Click_Actions();
    }
    @Override
    protected void beforeRemarks() { nextStep(); }

    
    @Override
    protected void updateEntryName(String newName) throws Throwable {
        supplier.supplierName(newName);
    }
}
