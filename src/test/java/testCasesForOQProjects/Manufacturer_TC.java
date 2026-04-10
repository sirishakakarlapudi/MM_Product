package testCasesForOQProjects;

import static configData.ManufacturerData.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.Manufacturer;
import utilities.ScreenshotUtil;

public class Manufacturer_TC extends OQBaseModule_TC {

    private Manufacturer manufacturer;

    @BeforeClass
    @Parameters({ "configFile" })
    public void setUp(@Optional("manufacturer.properties") String configFile) throws Exception {
        log.info("--- Starting Manufacturer Test Case Setup with config: {} ---", configFile);
        configData.ManufacturerData.loadProperties(configFile);

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
        VIEW_ACTION_VAL = MANUFACTURER_VIEW_ACTION;

        boolean screenshotsEnabled = "yes".equalsIgnoreCase(TAKE_SCREENSHOTS);
        ScreenshotUtil.setIsEnabled(screenshotsEnabled);
        if (screenshotsEnabled) {
            ScreenshotUtil.loadTemplateForEndAppend(TEMPLATE_PATH, OUTPUT_PATH);
            ScreenshotUtil.updateHeaderCellText(ACTUALHEADER, EXPECTEDHEADER);
            ScreenshotUtil.initScript(SCRIPT_NUMBER);
        }

        browserOpen();
        manufacturer = new Manufacturer(driver);
        this.pageObject = manufacturer; // Assign to generic base field
        manufacturer.setTableHeaders(TABLE_HEADERS);
    }

    @Test(groups = { "Creation" })
    public void Creation_Of_Manufacturer() throws Throwable {
        log.info("--- Navigating to Create Manufacturer Screen ---");
        manufacturer.Create();
        manufacturer.waitForLoading();
        ScreenshotUtil.capture();
        ScreenshotUtil.nextStep();
        
        log.info("--- Creating Manufacturer: {} ---", MANUFACTURER_NAME);
        currentEntryName = MANUFACTURER_NAME;
        manufacturer.manufacturerName(MANUFACTURER_NAME);
        manufacturer.manufacturerAddress(MANUFACTURER_ADDRESS);
        manufacturer.manufacturerState(MANUFACTURER_STATE);
        manufacturer.manufacturerCity(MANUFACTURER_CITY);
        manufacturer.manufacturerPincode(MANUFACTURER_PINCODE);
        manufacturer.selManufacturerRegion(MANUFACTURER_REGION);

        manufacturer.clickSubmit();
        ScreenshotUtil.capture();
        manufacturer.authenticate(manufacturer.currentPassword);
        String authToast = manufacturer.waitForToast();
        manufacturer.waitForLoading();
        sa.assertEquals(authToast, "Manufacturer created successfully", "Created Toaster message", authToast);
        sa.assertAll();
    }


    @Test(groups = { "manufacturerReviewReturn_manufacturerEdit" })
    public void manufacturer_Review_Return_and_Edit() throws Throwable {
        if (MANUFACTURER_RETURN_ACTION_IN_REVIEW.equalsIgnoreCase("yes")) {
            switchUserIfMulti(USERNAME2_VAL, PASSWORD2_VAL);
            performReturnReview(REVIEW_RETURN_REMARKS, "Manufacturer returned successfully");
            switchUserIfMulti(USERNAME1_VAL, PASSWORD1_VAL);
            performEdit(EDIT_MANUFACTURER_IN_REVIEW_RETURN, "Manufacturer updated successfully");
            sa.assertAll();
        }
    }

    @Test(groups = { "manufacturerReview" })
    public void manufacturerReview() throws Throwable {
        switchUserIfMulti(USERNAME2_VAL, PASSWORD2_VAL);
        performReview(REVIEW_REMARKS, "Manufacturer reviewed successfully");
        sa.assertAll();
    }

    @Test(groups = { "manufacturerApproveReturn_manufacturerEdit_manufacturerReview" })
    public void manufacturer_Approve_Return_and_Edit_and_Review() throws Throwable {
        if (MANUFACTURER_RETURN_ACTION_IN_APPROVE.equalsIgnoreCase("yes")) {
            switchUserIfMulti(USERNAME3_VAL, PASSWORD3_VAL);
            performReturnApprove(APPROVE_RETURN_REMARKS, "Manufacturer returned successfully");
            switchUserIfMulti(USERNAME1_VAL, PASSWORD1_VAL);
            performEdit(EDIT_MANUFACTURER_IN_APPROVE_RETURN, "Manufacturer updated successfully");
            switchUserIfMulti(USERNAME2_VAL, PASSWORD2_VAL);
            performReview(REVIEW_REMARKS, "Manufacturer reviewed successfully");
            sa.assertAll();
        }
    }

    @Test(groups = { "manufacturerApprove" })
    public void manufacturerApprove() throws Throwable {
        switchUserIfMulti(USERNAME3_VAL, PASSWORD3_VAL);
        performApprove(APPROVE_REMARKS, "Manufacturer approved successfully");
        sa.assertAll();
    }

    @Test(groups = { "checkStatus" })
    public void checkStatus() throws Throwable {
    	switchUserIfMulti(USERNAME1_VAL, PASSWORD1_VAL);
    }
    
    @Test(groups = { "ClickActions" })
    public void Click_Actions1() throws Throwable {
    	nextStep();
        switchUserIfMulti(USERNAME1_VAL, PASSWORD1_VAL);
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
        manufacturer.manufacturerName(newName);
    }
}
