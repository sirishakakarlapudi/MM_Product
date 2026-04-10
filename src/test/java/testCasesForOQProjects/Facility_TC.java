package testCasesForOQProjects;

import static configData.FacilityData.*;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pageObjects.Facility;
import testBase.BaseClass;
import utilities.DatabaseBackupUtil;
import utilities.ScreenshotUtil;
import utilities.SoftAssertionUtil;

public class Facility_TC extends OQBaseModule_TC {
	private Facility facility;
	private String currentFacilityType;

	@BeforeClass
	@Parameters({ "configFile" })
	public void setUp(@Optional("facility.properties") String configFile) throws Exception {
		log.info("--- Starting Facility Test Case Setup with config: {} ---", configFile);

		// Load Facility properties
		configData.FacilityData.loadProperties(configFile);

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
		ACTIONSPERFORMEDBY_VAL = ACTIONSPERFORMEDBY;
		PC_DB_NAME_VAL = PC_DB_NAME;
		MASTER_DB_NAME_VAL = MASTER_DB_NAME;
		MM_DB_NAME_VAL = MM_DB_NAME;
		TITLE_MODULE_VAL = "MASTERS";
		MASTER_MODULE_VAL = MASTER_MODULE;
		SCRIPT_NUMBER_VAL = SCRIPT_NUMBER;

		boolean screenshotsEnabled = "yes".equalsIgnoreCase(TAKE_SCREENSHOTS);
		ScreenshotUtil.setIsEnabled(screenshotsEnabled);

		if (screenshotsEnabled) {
			log.info("📸 Processing screenshot template and headers...");
			ScreenshotUtil.loadTemplateForEndAppend(TEMPLATE_PATH, OUTPUT_PATH);
			ScreenshotUtil.updateHeaderCellText(ACTUALHEADER, EXPECTEDHEADER);
			ScreenshotUtil.initScript(SCRIPT_NUMBER);
		}

		browserOpen();
		facility = new Facility(driver);
		this.pageObject = facility;
		facility.setTableHeaders(TABLE_HEADERS);
		log.info("Setup completed. Screenshots enabled: {}", screenshotsEnabled);
	}

	@Override
	protected void updateEntryName(String newName) throws Throwable {
		facility.facilityName(newName);
	}

	@Test(groups = { "Creation" })
	public void Creation_Of_Facility() throws Throwable {
		log.info("--- Navigating to Create Facility Screen ---");
		facility.Create();
		facility.waitForLoading();
		capture();
		nextStep();

		log.info("--- Creating Facility: {} ---", FACILITY_NAME);
		currentEntryName = FACILITY_NAME;
		currentFacilityType = FACILITY_TYPE;

		facility.facilityName(FACILITY_NAME);
		facility.selFacilityType(FACILITY_TYPE);
		facility.selDepartment(DEPARTMENT);

		if ("FACILITY".equalsIgnoreCase(FACILITY_TYPE)) {
			facility.storageCondition(STORAGE_CONDITION);
		} else {
			facility.selParentFacility(PARENT_FACILITY);
		}

		capture();
		facility.clickSubmit();
		log.info("Clicked Submit");
		capture();

		facility.authenticate(facility.currentPassword);
		String authToast = facility.waitForToast();
		facility.waitForToastDisappear();
		facility.waitForLoading();
		capture();
		sa.assertEquals(authToast, "Facility created successfully", "Created Toaster message", authToast);
		sa.assertAll();
	}

	@Test(groups = { "ClickActions" })
	public void Click_Actions1() throws Throwable {
		log.info("--- Attempting to open Actions Menu for: {} ---", currentEntryName);
		nextStep();
		facility.clickActions(currentEntryName);
		log.info("Successfully opened Actions menu for {}", currentEntryName);
		capture();
	}

	@Test(groups = { "facilityReturn_facilityEdit" })
	public void facility_Return_and_Edit() throws Throwable {
		if (FACILITY_RETURN_ACTION.equalsIgnoreCase("yes")) {
			switchUserIfMulti(USERNAME2_VAL, PASSWORD2_VAL);
			performReturnApprove(RETURN_REMARKS, "Facility returned successfully");

			switchUserIfMulti(USERNAME1_VAL, PASSWORD1_VAL);

			log.info("Opening Edit screen (After Return)");
			nextStep();
			facility.clickEdit(currentEntryName);
			facility.waitForLoading();
			capture();

			if (EDIT_FACILITY_NAME != null && !EDIT_FACILITY_NAME.trim().isEmpty()) {
				facility.facilityName(EDIT_FACILITY_NAME);
				currentEntryName = EDIT_FACILITY_NAME;
			}

			if (EDIT_FACILITY_TYPE != null && !EDIT_FACILITY_TYPE.trim().isEmpty()) {
				facility.selFacilityType(EDIT_FACILITY_TYPE);
				currentFacilityType = EDIT_FACILITY_TYPE;
			}

			if (EDIT_DEPARTMENT != null && !EDIT_DEPARTMENT.trim().isEmpty()) {
				facility.selDepartment(EDIT_DEPARTMENT);
			}

			if ((EDIT_STORAGE_CONDITION != null && !EDIT_STORAGE_CONDITION.trim().isEmpty())
					|| (EDIT_PARENT_FACILITY != null && !EDIT_PARENT_FACILITY.trim().isEmpty())) {

				if ("FACILITY".equalsIgnoreCase(currentFacilityType)) {
					if (EDIT_STORAGE_CONDITION != null && !EDIT_STORAGE_CONDITION.trim().isEmpty()) {
						facility.storageCondition(EDIT_STORAGE_CONDITION);
					}
				} else {
					if (EDIT_PARENT_FACILITY != null && !EDIT_PARENT_FACILITY.trim().isEmpty()) {
						facility.selParentFacility(EDIT_PARENT_FACILITY);
					}
				}
			}

			capture();
			facility.clickUpdate();
			capture();
			facility.authenticate(facility.currentPassword);
			facility.waitForToastDisappear();
			facility.waitForLoading();
			capture();
			sa.assertAll();
		}
	}

	@Test(groups = { "facilityApprove" })
	public void facility_Approve() throws Throwable {
		switchUserIfMulti(USERNAME2_VAL, PASSWORD2_VAL);
		performApprove(APPROVE_REMARKS, "Facility approved successfully");
		sa.assertAll();
	}
}
