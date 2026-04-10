package testCasesForOQProjects;

import static configData.EquipmentData.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.Equipment;
import utilities.ScreenshotUtil;

public class Equipment_TC extends OQBaseModule_TC {

    private Equipment equipment;

    @BeforeClass
    @Parameters({ "configFile" })
    public void setUp(@Optional("equipment.properties") String configFile) throws Exception {
        log.info("--- Starting Equipment Test Case Setup with config: {} ---", configFile);
        configData.EquipmentData.loadProperties(configFile);

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
        SUB_MASTER_MODULE_VAL = ""; // Equipment doesn't have a sub-module in navigation
        SCRIPT_NUMBER_VAL = SCRIPT_NUMBER;
        VIEW_ACTION_VAL = EQUIPMENT_VIEW_ACTION;

        boolean screenshotsEnabled = "yes".equalsIgnoreCase(TAKE_SCREENSHOTS);
        ScreenshotUtil.setIsEnabled(screenshotsEnabled);
        if (screenshotsEnabled) {
            ScreenshotUtil.loadTemplateForEndAppend(TEMPLATE_PATH, OUTPUT_PATH);
            ScreenshotUtil.updateHeaderCellText(ACTUALHEADER, EXPECTEDHEADER);
            ScreenshotUtil.initScript(SCRIPT_NUMBER);
        }

        browserOpen();
        equipment = new Equipment(driver);
        this.pageObject = equipment;
        equipment.setTableHeaders(TABLE_HEADERS);
    }

    @Test(groups = { "Creation" })
    public void Creation_Of_Equipment() throws Throwable {
        log.info("--- Navigating to Create Equipment Screen ---");
        equipment.Create();
        equipment.waitForLoading();
        ScreenshotUtil.capture();
        ScreenshotUtil.nextStep();
        
        log.info("--- Creating Equipment: {} ---", EQUIPMENT_NAME);
        currentEntryName = EQUIPMENT_NAME;
        equipment.equipmentName(EQUIPMENT_NAME);
        equipment.equipmentId(EQUIPMENT_ID);
        equipment.selEquipmentType(EQUIPMENT_TYPE);
        equipment.capacityInKg(CAPACITY_IN_KG);
        equipment.operationalRangeMin(OPERATIONAL_RANGE_MIN);
        equipment.operationalRangeMax(OPERATIONAL_RANGE_MAX);
        equipment.selDepartment(DEPARTMENT);
        equipment.selFacility(FACILITY);
        equipment.selWeighingBalanceFacility(WEIGHING_BALANCE_FACILITY);

        equipment.clickSubmit();
        ScreenshotUtil.capture();
        equipment.authenticate(equipment.currentPassword);
        String authToast = equipment.waitForToast();
        equipment.waitForLoading();
        sa.assertEquals(authToast, "Equipment created successfully", "Created Toaster message", authToast);
        sa.assertAll();
    }


    @Test(groups = { "equipmentReviewReturn_equipmentEdit" })
    public void equipment_Review_Return_and_Edit() throws Throwable {
        if (EQUIPMENT_RETURN_ACTION_IN_REVIEW.equalsIgnoreCase("yes")) {
            switchUserIfMulti(USERNAME2_VAL, PASSWORD2_VAL);
            performReturnReview(REVIEW_RETURN_REMARKS, "Equipment returned successfully");
            switchUserIfMulti(USERNAME1_VAL, PASSWORD1_VAL);
            performEdit(EDIT_EQUIPMENT_IN_REVIEW_RETURN, "Equipment updated successfully");
            sa.assertAll();
        }
    }

    @Test(groups = { "equipmentReview" })
    public void equipmentReview() throws Throwable {
        switchUserIfMulti(USERNAME2_VAL, PASSWORD2_VAL);
        performReview(REVIEW_REMARKS, "Equipment reviewed successfully");
        sa.assertAll();
    }

    @Test(groups = { "equipmentApproveReturn_equipmentEdit_equipmentReview" })
    public void equipment_Approve_Return_and_Edit_and_Review() throws Throwable {
        if (EQUIPMENT_RETURN_ACTION_IN_APPROVE.equalsIgnoreCase("yes")) {
            switchUserIfMulti(USERNAME3_VAL, PASSWORD3_VAL);
            performReturnApprove(APPROVE_RETURN_REMARKS, "Equipment returned successfully");
            switchUserIfMulti(USERNAME1_VAL, PASSWORD1_VAL);
            performEdit(EDIT_EQUIPMENT_IN_APPROVE_RETURN, "Equipment updated successfully");
            switchUserIfMulti(USERNAME2_VAL, PASSWORD2_VAL);
            performReview(REVIEW_REMARKS, "Equipment reviewed successfully");
            sa.assertAll();
        }
    }

    @Test(groups = { "equipmentApprove" })
    public void equipmentApprove() throws Throwable {
        switchUserIfMulti(USERNAME3_VAL, PASSWORD3_VAL);
        performApprove(APPROVE_REMARKS, "Equipment approved successfully");
        sa.assertAll();
    }

    @Test(groups = { "ClickActions" })
    public void Click_Actions_1() throws Throwable {
        switchUserIfMulti(USERNAME1_VAL, PASSWORD1_VAL);
        Click_Actions();
    }

    @Test(groups = { "ClickActions" })
    public void Click_Actions_2() throws Throwable {
        switchUserIfMulti(USERNAME2_VAL, PASSWORD2_VAL);
        Click_Actions();
    }

    @Override
    protected void afterClickActionsScreenshot() throws Throwable {
        freezeStep();
        try {
            equipment.clickActions(currentEntryName);
        } catch (Exception e) {
            log.error("Failed to re-click actions during freeze step", e);
        }
        resumeStep();
    }

    @Override
    protected void updateEntryName(String newName) throws Throwable {
        equipment.equipmentName(newName);
    }
}
