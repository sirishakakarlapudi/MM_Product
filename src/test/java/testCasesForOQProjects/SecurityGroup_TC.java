package testCasesForOQProjects;

import static configData.SecurityGroupData.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pageObjects.SecurityGroup;
import utilities.DataProviders;
import utilities.ScreenshotUtil;
import utilities.securityGroupOQData;

public class SecurityGroup_TC extends OQBaseModule_TC {
	private SecurityGroup securitygroup;
	private String createCsvFile;

	@BeforeClass
	@Parameters({ "configFile", "createCsvFile" })
	public void setUp(@Optional("securitygroup.properties") String configFile,
			@Optional("SecurityGroup_Create.csv") String createCsvFile) throws Exception {
		log.info("--- Starting Security Group Test Case Setup with config: {} and CSV: {} ---", configFile, createCsvFile);

		this.createCsvFile = createCsvFile;

		// Load SecurityGroup properties
		configData.SecurityGroupData.loadProperties(configFile);

		// Map static variables to base class fields
		CONFIG_NAME = CURRENT_CONFIG_NAME;
		CHROME_URL_VAL = CHROME_URL;
		APP_URL_VAL = APP_URL;
		USERNAME_VAL = USERNAME;
		PASSWORD_VAL = PASSWORD;
		USERNAME1_VAL = USERNAME1;
		PASSWORD1_VAL = PASSWORD1;
		USERNAME3_VAL = USERNAME3;
		PASSWORD3_VAL = PASSWORD3;
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
		securitygroup = new SecurityGroup(driver);
		this.pageObject = securitygroup;
		securitygroup.setTableHeaders(TABLE_HEADERS);
		log.info("Setup completed. Screenshots enabled: {}", screenshotsEnabled);
	}

	@Override
	protected void updateEntryName(String newName) throws Throwable {
		securitygroup.securityGroupName(newName);
	}

	@Test(groups = { "Creation" })
	public void Creation_Of_SecurityGroup() throws Throwable {
		log.info("--- Navigating to Create SecurityGroup Screen ---");
		securitygroup.Create();
		securitygroup.waitForLoading();
		capture();
		nextStep();

		log.info("--- Creating Security Group: {} ---", SECURITYGROUP_NAME);
		currentEntryName = SECURITYGROUP_NAME;

		securitygroup.securityGroupName(SECURITYGROUP_NAME);
		securitygroup.securityGroupDesc(SECURITYGROUP_DESC);
		securitygroup.selModule(SELECT_MODULE);
		capture();

		log.info("Entered Name, Description, Module");

		// Privilege logic from CSV
		String createCsvPath = System.getProperty("user.dir") + "/src/test/resources/CSV_Data/" + createCsvFile;
		List<Map<String, String>> createData = utilities.CSVUtility.getAllRows(createCsvPath);
		String creationPrivileges = "";

		if (!createData.isEmpty()) {
			creationPrivileges = createData.stream().map(row -> row.get("Privileges"))
					.filter(val -> val != null && !val.trim().isEmpty()).collect(Collectors.joining(","));
			log.info("Fetched Creation Privileges from CSV: {}", creationPrivileges);
		} else {
			creationPrivileges = USER_PRIVILEGES; // Fallback to properties
			log.warn("No data found in Creation CSV. Using property value.");
		}
		// ============================================================================

		securitygroup.selPrivileges(creationPrivileges);
		securitygroup.scrollAndCaptureSelectedPrivileges();
		// Removed extra ScreenshotUtil.capture() as it's now handled inside the loop
		securitygroup.clickSubmit();
		log.info("Clicked Submit");
		capture();

		securitygroup.authenticate(securitygroup.currentPassword);
		String authToast = securitygroup.waitForToast();
		securitygroup.waitForToastDisappear();
		securitygroup.waitForLoading();
		capture();
		sa.assertEquals(authToast, "Security Group created successfully", "Created Toaster message", authToast);
		sa.assertAll();
	}

	@Test(groups = { "ClickActions" })
	public void Click_Actions1() throws Throwable {
		log.info("--- Attempting to open Actions Menu for: {} ---", currentEntryName);
		nextStep();
		securitygroup.clickActions(currentEntryName);
		log.info("Successfully opened Actions menu for {}", currentEntryName);
		capture();
	}

	@Test(groups = { "securitygroupReturn_securitygroupEdit" }, priority = 8)
	public void securitygroup_Return_and_Edit() throws Throwable {

		if (SECURITYGROUP_RETURN_ACTION.equalsIgnoreCase("yes")) {

			if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
				securitygroup.switchUser(USERNAME3, PASSWORD3, PC_DB_NAME, MASTER_MODULE);
			}
			log.info("--- Initiating Return Flow for: {} ---", currentEntryName);
			ScreenshotUtil.nextStep();
			log.info("Opening actions menu to access Approve/Return");
			securitygroup.clickActions(currentEntryName);
			ScreenshotUtil.capture();
			log.info("Clicking Approve to trigger return dialog");
			securitygroup.clickApprove();
			securitygroup.waitForLoading();
			ScreenshotUtil.capture();
			securitygroup.enterRemarks(RETURN_REMARKS);
			log.info("Entered Return remarks: {}", RETURN_REMARKS);
			ScreenshotUtil.capture();
			securitygroup.clickReturn();
			log.info("Clicked Return button");
			ScreenshotUtil.capture();
			securitygroup.authenticate(securitygroup.currentPassword);
			String returnToast = securitygroup.waitForToast();
			sa.assertEquals(returnToast, "Security Group returned successfully", "Returned toaster message",
					returnToast);

			if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
				securitygroup.switchUser(USERNAME1, PASSWORD1, PC_DB_NAME, MASTER_MODULE);
			}

			// This part will now execute for BOTH single and multiple users
			log.info("Opening Edit screen (After Return)");
			ScreenshotUtil.nextStep();
			securitygroup.clickEdit(currentEntryName);
			securitygroup.waitForLoading();
			ScreenshotUtil.capture();
			ScreenshotUtil.nextStep();
			if (EDIT_SECURITYGROUP_NAME_AFTER_RETRUN != null
					&& !EDIT_SECURITYGROUP_NAME_AFTER_RETRUN.trim().isEmpty()) {
				log.info("Updating Name to: {}", EDIT_SECURITYGROUP_NAME_AFTER_RETRUN);
				securitygroup.securityGroupName(EDIT_SECURITYGROUP_NAME_AFTER_RETRUN);
				currentEntryName = EDIT_SECURITYGROUP_NAME_AFTER_RETRUN;
			}

			if (EDIT_DESC_AFTER_RETURN != null && !EDIT_DESC_AFTER_RETURN.trim().isEmpty()) {
				log.info("Updating Description to: {}", EDIT_DESC_AFTER_RETURN);
				securitygroup.securityGroupDesc(EDIT_DESC_AFTER_RETURN);
			}
			if (EDIT_MODULE_AFTER_RETURN != null && !EDIT_MODULE_AFTER_RETURN.trim().isEmpty()) {
				log.info("Updating Module to: {}", EDIT_MODULE_AFTER_RETURN);
				securitygroup.selModule(EDIT_MODULE_AFTER_RETURN);

			}

			// ================= FETCH PRIVILEGES FROM SEPARATE UPDATE CSV =================
			String updateCsvPath = System.getProperty("user.dir")
					+ "/src/test/resources/CSV_Data/SecurityGroup_Update.csv";
			List<Map<String, String>> updateData = utilities.CSVUtility.getAllRows(updateCsvPath);
			String editPrivileges = "";

			if (!updateData.isEmpty()) {
				editPrivileges = updateData.stream().map(row -> row.get("Edit Privileges"))
						.filter(val -> val != null && !val.trim().isEmpty()).collect(Collectors.joining(","));
				log.info("Fetched Edit Privileges from CSV: {}", editPrivileges);
			} else {
				editPrivileges = EDIT_USER_PRIVILEGES_AFTER_RETURN; // Fallback
				log.warn("No data found in Edit CSV. Using property value.");
			}
			// ============================================================================

			if (editPrivileges != null && !editPrivileges.trim().isEmpty()) {
				log.info("Updating Privileges to: {}", editPrivileges);
				securitygroup.selPrivileges(editPrivileges);
			}

			capture();
			securitygroup.clickUpdate();
			log.info("Clicked Update");
			ScreenshotUtil.capture();
			securitygroup.authenticate(securitygroup.currentPassword);
			securitygroup.waitForToastDisappear();
			securitygroup.waitForLoading();
			capture();
			sa.assertAll();
		}
	}

	@Test(groups = { "securitygroupApprove" })
	public void securitygroup_Approve() throws Throwable {
		switchUserIfMulti(USERNAME3, PASSWORD3);
		performApprove(APPROVE_REMARKS, "Security Group approved successfully");
		sa.assertAll();
	}

	@Test(groups = { "securitygroupUpdate" })
	public void securitygroup_Update() throws Throwable {

		if (UPDATE_PRIVILEGES_ACTION.equalsIgnoreCase("yes")) {
			switchUserIfMulti(USERNAME1_VAL, PASSWORD1_VAL);

			log.info("Opening actions menu to access Update Privileges");
			nextStep();
			securitygroup.clickActions(currentEntryName);
			capture();
			log.info("Clicking Update to trigger privilege dialog");
			securitygroup.clickUpdate();
			securitygroup.waitForLoading();
			capture();

			// ================= FETCH PRIVILEGES FROM SEPARATE UPDATE CSV =================
			String updateCsvPath = System.getProperty("user.dir")
					+ "/src/test/resources/CSV_Data/SecurityGroup_Update.csv";
			List<Map<String, String>> updateData = utilities.CSVUtility.getAllRows(updateCsvPath);
			String activeUpdatePrivileges = "";

			if (!updateData.isEmpty()) {
				activeUpdatePrivileges = updateData.stream().map(row -> row.get("Update Privileges"))
						.filter(val -> val != null && !val.trim().isEmpty()).collect(Collectors.joining(","));
				log.info("Fetched Update Privileges (Active) from CSV: {}", activeUpdatePrivileges);
			} else {
				activeUpdatePrivileges = UPDATE_USER_PRIVILEGES_AFTER_ACTIVE; // Fallback
				log.warn("No data found in Update CSV. Using property value.");
			}
			// ============================================================================

			securitygroup.selPrivileges(activeUpdatePrivileges);
			log.info("Updating Privileges to: {}", activeUpdatePrivileges);
			securitygroup.scrollAndCaptureSelectedPrivileges();
			securitygroup.clickUpdate();
			log.info("Clicked Update button");
			ScreenshotUtil.capture();
			securitygroup.authenticate(securitygroup.currentPassword);
			String updateToast = securitygroup.waitForToast();
			securitygroup.waitForToastDisappear();
			sa.assertEquals(updateToast, "Security Group updated successfully", "Updated toaster message", updateToast);
			sa.assertAll();
		}
	}

	@Test(groups = { "securitygroupUpdateApprove" })
	public void securitygroup_Active_Approve() throws Throwable {
		switchUserIfMulti(USERNAME2_VAL, PASSWORD2_VAL);
		performApprove(APPROVE_REMARKS, "Security Group approved successfully");
		sa.assertAll();
	}

	@Test(groups = { "SearchFilter" })
	public void Search_Filter() throws Throwable {
		log.info("--- Validating Search Filters ---");
		nextStep();
		if (!securitygroup.isFilterPanelExpanded()) {
			log.info("Filter panel is already not expanded so clicking on Filter button to expand it");
			securitygroup.clickFilterToggle();
		}
		securitygroup.clickSecurityGroupNameFilter(currentEntryName);
		capture();
		securitygroup.clickSearch();
		securitygroup.waitForLoading();
		capture();
	}

	// =====================DUPLICATION CHECK IN CREATE PAGE========================

	@Test(groups = { "duplicationcheck" }, priority = 13)
	public void Duplication_Check() throws Throwable {

		ScreenshotUtil.nextStep();
		log.info("--- Checking Duplication Check with Department In Create page: {} ---", currentEntryName);
		securitygroup.Create();
		securitygroup.securityGroupName(currentEntryName);
		capture();
		securitygroup.clickSecurityGroupName();
		securitygroup.clickSubmit();
		securitygroup.waitForToast();
		securitygroup.waitForToastDisappear();
		capture();
	}

	@Test(groups = { "sidenavvalidation" }, dependsOnMethods = "moduleClick")
	public void SideNav_Validation() throws Throwable {
		log.info("--- Validating Side-Navigation Modules ---");

		List<String> expectedModules = securitygroup.getExpectedSideNavFromDB(securitygroup.currentUsername,
				SIDE_NAV_MODULE_MAPPING, PC_DB_NAME_VAL);

		List<String> finalActualModules = new ArrayList<>();
		finalActualModules.addAll(securitygroup.getAllSideNavModulesDisplayed());

		String[] mappings = SIDE_NAV_MODULE_MAPPING.split(";");
		for (String map : mappings) {
			String[] parts = map.split(":");
			if (parts.length > 2) {
				String parentModule = parts[0].trim();
				if (expectedModules.contains(parentModule)) {
					securitygroup.expandSideNavModule(parentModule);
					for (String active : securitygroup.getAllSideNavModulesDisplayed()) {
						if (!finalActualModules.contains(active))
							finalActualModules.add(active);
					}
				}
			}
		}
		capture();

		System.out.println("====================================================================");
		System.out.println("SIDE-NAV VALIDATION FOR USER: " + securitygroup.currentUsername);
		System.out.println("Expected Modules from DB Mapping: " + expectedModules);
		System.out.println("Actual Combined Modules from UI : " + finalActualModules);
		System.out.println("====================================================================");

		// 3. Conditional Assertion
		List<String> missing = expectedModules.stream().filter(e -> !finalActualModules.contains(e)).toList();
		boolean isAdmin = utilities.DatabaseBackupUtil.hasAuthority(securitygroup.currentUsername, "ROLE_ADMIN");

		if (isAdmin) {
			log.info("Performing STRICT sidebar validation for ROLE_ADMIN: UI must exactly match DB Mapping.");
			sa.assertEquals(finalActualModules.size(), expectedModules.size(), "Side-Nav Count mismatch",
					"Total Modules");
			sa.assertTrue(missing.isEmpty(), "Module Presence", "Missing: " + missing);

			List<String> extra = finalActualModules.stream().filter(a -> !expectedModules.contains(a)).toList();
			sa.assertTrue(extra.isEmpty(), "Module Restriction", "Extra: " + extra);
		} else {
			log.info("Performing PRESENCE sidebar validation for User {}: UI must contain all DB-authorized modules.",
					securitygroup.currentUsername);
			// For non-admin, we only care if they HAVE what they should have. Extra
			// visibility is ignored here.
			sa.assertTrue(missing.isEmpty(), "Module Presence",
					"Database-authorized modules missing from UI: " + missing);
		}

		log.info("Side-Navigation validation successfully completed.");
		sa.assertAll();
	}

	@Test(groups = { "EditAfterCreate" }, dependsOnMethods = "moduleClick", priority = 17)
	public void Edit_After_Create() throws Throwable {

		if (SECURITYGROUP_EDIT_AFTER_CREATE_ACTION.equalsIgnoreCase("yes")) {
			log.info("--- Editing Department (After Creation): {} ---", currentEntryName);
			ScreenshotUtil.nextStep();
			securitygroup.clickEdit(currentEntryName);
			log.info("Edit screen opened");
			securitygroup.waitForLoading();
			ScreenshotUtil.capture();
			ScreenshotUtil.nextStep();

			if (EDIT_SECURITYGROUP_NAME_AFTER_CREATE != null
					&& !EDIT_SECURITYGROUP_NAME_AFTER_CREATE.trim().isEmpty()) {
				log.info("Updating Name to: {}", EDIT_SECURITYGROUP_NAME_AFTER_CREATE);
				securitygroup.securityGroupName(EDIT_SECURITYGROUP_NAME_AFTER_CREATE);
				currentEntryName = EDIT_SECURITYGROUP_NAME_AFTER_CREATE;
			}

			if (EDIT_DESC_AFTER_CREATE != null && !EDIT_DESC_AFTER_CREATE.trim().isEmpty()) {
				log.info("Updating Description to: {}", EDIT_DESC_AFTER_CREATE);
				securitygroup.securityGroupDesc(EDIT_DESC_AFTER_CREATE);
			}
			if (EDIT_MODULE_AFTER_CREATE != null && !EDIT_MODULE_AFTER_CREATE.trim().isEmpty()) {
				log.info("Updating Module to: {}", EDIT_MODULE_AFTER_CREATE);
				securitygroup.selModule(EDIT_MODULE_AFTER_CREATE);

			}

			// ================= FETCH PRIVILEGES FROM SEPARATE UPDATE CSV =================
			String updateCsvPath = System.getProperty("user.dir")
					+ "/src/test/resources/CSV_Data/SecurityGroup_Update.csv";
			List<Map<String, String>> updateData = utilities.CSVUtility.getAllRows(updateCsvPath);
			String editPrivileges = "";

			if (!updateData.isEmpty()) {
				editPrivileges = updateData.stream().map(row -> row.get("Update Privileges"))
						.filter(val -> val != null && !val.trim().isEmpty()).collect(Collectors.joining(","));
				log.info("Fetched Edit After Create Privileges from CSV: {}", editPrivileges);
			} else {
				editPrivileges = EDIT_USER_PRIVILEGES_AFTER_CREATE; // Fallback
				log.warn("No data found in Update CSV. Using property value.");
			}
			// ============================================================================

			if (editPrivileges != null && !editPrivileges.trim().isEmpty()) {
				log.info("Updating Privileges to: {}", editPrivileges);
				securitygroup.selPrivileges(editPrivileges);
				securitygroup.scrollAndCaptureSelectedPrivileges();
			}

			securitygroup.clickUpdate();
			log.info("Clicked Update");
			ScreenshotUtil.capture();
			securitygroup.authenticate(securitygroup.currentPassword);
			String authToast = securitygroup.waitForToast();
			//Assert.assertEquals(authToast, "SecurityGroup Updated Successfully",
					//"Update failed with message: " + authToast);
		} else {
			log.info("Edit After Create step skipped based on configuration");
		}
	}

	@Test(groups = { "securitygroupUpdate" }, priority = 18)
	public void securitygroup_Active_Return() throws Throwable {

		if (SECURITYGROUP_ACTIVE_RETURN_ACTION.equalsIgnoreCase("yes")) {
			if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
				securitygroup.switchUser(USERNAME3, PASSWORD3, PC_DB_NAME, MASTER_MODULE);
			}
			log.info("--- Initiating Active Return Flow for: {} ---", currentEntryName);
			ScreenshotUtil.nextStep();
			log.info("Opening actions menu to access Approve/Return");
			securitygroup.clickActions(currentEntryName);
			ScreenshotUtil.capture();
			log.info("Clicking Approve to trigger return dialog");
			securitygroup.clickApprove();
			securitygroup.waitForLoading();
			ScreenshotUtil.capture();
			securitygroup.enterRemarks(ACTIVE_RETURN_REMARKS);
			log.info("Entered Active Return remarks: {}", ACTIVE_RETURN_REMARKS);
			ScreenshotUtil.capture();
			securitygroup.clickReturn();
			log.info("Clicked Return button");
			ScreenshotUtil.capture();
			securitygroup.authenticate(securitygroup.currentPassword);
			String returnToast = securitygroup.waitForToast();
		//	Assert.assertEquals(returnToast, "SecurityGroup Returned Successfully",
					//"Active Return failed with message: " + returnToast);

		} else {
			log.info("Active Return step skipped based on configuration");
		}

	}

	@Test(priority = 14, dataProvider = "SecurityGroupOQData", dataProviderClass = DataProviders.class, singleThreaded = true)
	public void createSecurityGroupWithBulkData(securityGroupOQData sgdata) throws Throwable {
		log.info("Creation Of Bulk Data");
		String sgname = sgdata.getSgName();
		String sgdesc = sgdata.getSgDesc();
		String module = sgdata.getModule();

		String privileges = sgdata.get_Privileges();

		securitygroup.Create();
		log.info("Clicked on Create Button");
		securitygroup.waitForLoading();
		log.info("--- Creating Security Group: {} ---", sgname);

		securitygroup.securityGroupName(sgname);
		log.info("------SgName from Excel:   {}--- " + sgname);

		log.info("----SgDesc from Excel: {}----" + sgdesc);
		securitygroup.securityGroupDesc(sgdesc);
		securitygroup.selModule(module);
		log.info("Entered Name, Description, Module");

		securitygroup.selPrivileges(privileges);
		securitygroup.clickSubmit();
		securitygroup.authenticate(securitygroup.currentPassword);
		securitygroup.waitForLoading();

		log.info("--- Performing Approval for: {} ---", sgname);
		securitygroup.clickActions(sgname);
		log.info("Clicking Approve button in the list");
		securitygroup.clickApprove();
		ScreenshotUtil.capture();
		securitygroup.enterRemarks(APPROVE_REMARKS);
		log.info("Entered Approve remarks: {}", APPROVE_REMARKS);
		ScreenshotUtil.capture();
		securitygroup.clickApprove();
		log.info("Submitted Approval");
		ScreenshotUtil.capture();
		securitygroup.authenticate(securitygroup.currentPassword);

	}

}
