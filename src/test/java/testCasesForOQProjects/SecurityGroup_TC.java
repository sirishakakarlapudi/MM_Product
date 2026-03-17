package testCasesForOQProjects;

import static configData.SecurityGroupData.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pageObjects.SecurityGroup;
import testBase.BaseClass;
import utilities.DataProviders;
import utilities.DatabaseBackupUtil;
import utilities.ScreenshotUtil;
import utilities.SoftAssertionUtil;
import utilities.securityGroupOQData;

public class SecurityGroup_TC extends BaseClass {

	SecurityGroup securitygroup;
	String currentSecurityGroupName; // Track the current name (including edits)
	SoftAssertionUtil sa;

	@BeforeClass
	@Parameters({ "configFile" })
	public void setUp(@Optional("securitygroup.properties") String configFile) throws Exception {
		log.info("--- Starting Security Group Test Case Setup with config: {} ---", configFile);

		// CORRECTED: Load SecurityGroup properties
		configData.SecurityGroupData.loadProperties(configFile);

		// Set conditional screenshot execution
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
		securitygroup.setTableHeaders(TABLE_HEADERS);
		log.info("Setup completed. Screenshots enabled: {}", screenshotsEnabled);
	}

	@BeforeMethod
	public void initSoftAssert() {
		sa = new SoftAssertionUtil();
	}

	@Test(groups = { "setup" }, priority = 1)
	public void initialSetUp() throws Exception {
		ScreenshotUtil.nextStep();
		log.info("Navigating to Chrome URL: {}", CHROME_URL);
		driver.navigate().to(CHROME_URL);
		log.info("Waiting for search box to be visible");
		securitygroup.waitForElementToVisible(securitygroup.getSearchBox());
		Assert.assertTrue(securitygroup.getSearchBox().isDisplayed(), "Search box is not visible!");
		log.info("Searching for Application URL: {}", APP_URL);
		securitygroup.searchBox(APP_URL);
		securitygroup.waitForLoading();
		ScreenshotUtil.capture();
		log.info("Initial setup completed and screenshot captured");
	}

	@Test(groups = { "url" }, priority = 2)
	public void url() throws Throwable {
		driver.navigate().to(APP_URL);
		log.info("Navigating to App URL: {}", APP_URL);
	}

	@Test(groups = { "userlogin" }, priority = 3)
	public void userLoginBeforeCreate() throws Throwable {
		log.info("Executing the flow with single/multiple : {}", ACTIONSPERFORMEDBY);
		if (ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
			securitygroup.login(USERNAME, PASSWORD, PC_DB_NAME);
		} else {
			securitygroup.login(USERNAME1, PASSWORD1, PC_DB_NAME);
		}
	}

	@Test(groups = { "moduleselect" }, priority = 4)
	public void moduleClick() throws Throwable {
		log.info("--- Clicking Module ---");
		ScreenshotUtil.nextStep();
		securitygroup.click_titleMasters();
		log.info("Clicked on Masters title");
		ScreenshotUtil.capture();
		securitygroup.waitForLoading();
		ScreenshotUtil.nextStep();
		securitygroup.masterClick(MASTER_MODULE);
		log.info("Clicked on Master Module: {}", MASTER_MODULE);
		securitygroup.waitForLoading();
		ScreenshotUtil.capture();
	}

	@Test(groups = { "Creation" }, priority = 5)
	public void Creation_Of_SecurityGroup() throws Throwable {
		log.info("--- Navigating to Create SecurityGroup Screen ---");
		securitygroup.Create();
		log.info("Clicked on Create button");
		securitygroup.waitForLoading();
		ScreenshotUtil.capture();
		ScreenshotUtil.nextStep();
		log.info("--- Creating Security Group: {} ---", SECURITYGROUP_NAME);
		ScreenshotUtil.nextStep();
		currentSecurityGroupName = SECURITYGROUP_NAME; // Initialize with base name

		securitygroup.securityGroupName(SECURITYGROUP_NAME);
		securitygroup.securityGroupDesc(SECURITYGROUP_DESC);
		securitygroup.selModule(SELECT_MODULE);
		ScreenshotUtil.capture();

		log.info("Entered Name, Description, Module");

		// ================= FETCH PRIVILEGES FROM SEPARATE CREATE CSV =================
		String createCsvPath = System.getProperty("user.dir") + "/src/test/resources/CSV_Data/SecurityGroup_Create.csv";
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
		ScreenshotUtil.capture();

		securitygroup.authenticate(securitygroup.currentPassword);
		String authToast = securitygroup.waitForToast();
		securitygroup.waitForLoading();
		ScreenshotUtil.capture();
		sa.assertEquals(authToast, "Security Group created successfully", "Created Toaster message",
				"Creation failed with message: " + authToast);
		ScreenshotUtil.nextStep();
		ScreenshotUtil.capture();

	}

	@Test(groups = { "ClickActions" }, priority = 6)
	public void Click_Actions_After_Create() throws Throwable {
		log.info("--- Attempting to open Actions Menu for: {} ---", currentSecurityGroupName);
		ScreenshotUtil.nextStep();
		securitygroup.clickActions(currentSecurityGroupName);
		log.info("Successfully opened Actions menu for {}", currentSecurityGroupName);
		ScreenshotUtil.capture();

	}

	@Test(groups = { "ClickView" }, priority = 7)
	public void Click_View() throws Throwable {
		if (SECURITYGROUP_VIEW_ACTION.equalsIgnoreCase("yes")) {
			log.info("--- Viewing Security Group: {} ---", currentSecurityGroupName);
			ScreenshotUtil.nextStep();
			securitygroup.clickView(currentSecurityGroupName);
			log.info("View screen opened");
			securitygroup.waitForLoading();
			ScreenshotUtil.capture();
			securitygroup.clickBack();
			log.info("Clicked Back button");
			securitygroup.waitForLoading();
			ScreenshotUtil.capture();
		} else {
			log.info("View step skipped based on configuration");
		}
	}

	@Test(groups = { "securitygroupReturn_securitygroupEdit" }, priority = 8)
	public void securitygroup_Return_and_Edit() throws Throwable {

		if (SECURITYGROUP_RETURN_ACTION.equalsIgnoreCase("yes")) {

			if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
				securitygroup.switchUser(USERNAME2, PASSWORD2, PC_DB_NAME, MASTER_MODULE);
			}
			log.info("--- Initiating Return Flow for: {} ---", currentSecurityGroupName);
			ScreenshotUtil.nextStep();
			log.info("Opening actions menu to access Approve/Return");
			securitygroup.clickActions(currentSecurityGroupName);
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
			securitygroup.clickEdit(currentSecurityGroupName);
			securitygroup.waitForLoading();
			ScreenshotUtil.capture();
			ScreenshotUtil.nextStep();
			if (EDIT_SECURITYGROUP_NAME_AFTER_RETRUN != null
					&& !EDIT_SECURITYGROUP_NAME_AFTER_RETRUN.trim().isEmpty()) {
				log.info("Updating Name to: {}", EDIT_SECURITYGROUP_NAME_AFTER_RETRUN);
				securitygroup.securityGroupName(EDIT_SECURITYGROUP_NAME_AFTER_RETRUN);
				currentSecurityGroupName = EDIT_SECURITYGROUP_NAME_AFTER_RETRUN;
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

			securitygroup.clickUpdate();
			log.info("Clicked Update");
			ScreenshotUtil.capture();
			securitygroup.authenticate(securitygroup.currentPassword);
			String editToast = securitygroup.waitForToast();
			sa.assertEquals(editToast, "Security Group updated successfully", "Updated toaster messege", editToast);

		} else {
			log.info("SecurityGroup Return and Edit skipped based on configuration");
		}
	}

	@Test(groups = { "securitygroupApprove" }, priority = 9)
	public void securitygroup_Approve() throws Throwable {

		if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
			log.info("--- Approving Security Group: {} ---", currentSecurityGroupName);
			securitygroup.switchUser(USERNAME2, PASSWORD2, PC_DB_NAME, MASTER_MODULE);
		}
		log.info("--- Performing Final Approval for: {} ---", currentSecurityGroupName);
		ScreenshotUtil.nextStep();
		log.info("Opening actions menu for approval");
		securitygroup.clickActions(currentSecurityGroupName);
		ScreenshotUtil.capture();
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
		String approveToast = securitygroup.waitForToast();
		sa.assertEquals(approveToast, "Security Group approved successfully", "Approved toaster messege", approveToast);

	}

	@Test(groups = { "securitygroupUpdate" }, priority = 10)
	public void securitygroup_Update() throws Throwable {
		currentSecurityGroupName = EDIT_SECURITYGROUP_NAME_AFTER_RETRUN;
		if (UPDATE_PRIVILEGES_ACTION.equalsIgnoreCase("yes")) {

			if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
				log.info("--- Changing User for Update: {} ---", currentSecurityGroupName);
				securitygroup.switchUser(USERNAME1, PASSWORD1, PC_DB_NAME, MASTER_MODULE);
			}
			ScreenshotUtil.nextStep();
			log.info("Opening actions menu to access Update Privileges");
			securitygroup.clickActions(currentSecurityGroupName);
			ScreenshotUtil.capture();
			log.info("Clicking Update to trigger privilege dialog");
			securitygroup.clickUpdate();
			securitygroup.waitForLoading();
			ScreenshotUtil.capture();

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
			sa.assertEquals(updateToast, "Security Group updated successfully", "Updated toaster messege", updateToast);
		}
	}

	@Test(groups = { "securitygroupUpdateApprove" }, priority = 11)
	public void securitygroup_Active_Approve() throws Throwable {

		if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
			log.info("--- Approving Department: {} ---", currentSecurityGroupName);
			securitygroup.switchUser(USERNAME2, PASSWORD2, PC_DB_NAME, MASTER_MODULE);
		}
		log.info("--- Performing Final Approval for: {} ---", currentSecurityGroupName);
		ScreenshotUtil.nextStep();
		log.info("Opening actions menu for approval");
		securitygroup.clickActions(currentSecurityGroupName);
		ScreenshotUtil.capture();
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
		String approveToast = securitygroup.waitForToast();
		sa.assertEquals(approveToast, "Security Group approved successfully", "Approved toaster messege", approveToast);
	}

	// ===============Search filters========================
	@Test(groups = { "SearchFilter" }, priority = 12)
	public void Search_Filter() throws Throwable {
		log.info("--- Validating Search Filters for: {} ---", currentSecurityGroupName);
		ScreenshotUtil.nextStep();
		if (!securitygroup.isFilterPanelExpanded()) {
			log.info("Filter panel is already not expanded so clicking on Filter button to expand it");
			securitygroup.clickFilterToggle();
		}
		securitygroup.clickSecurityGroupNameFilter(currentSecurityGroupName);
		ScreenshotUtil.capture();
		securitygroup.clickSearch();
		securitygroup.waitForLoading();
		ScreenshotUtil.capture();
	}

	// =====================DUPLICATION CHECK IN CREATE PAGE========================

	@Test(groups = { "duplicationcheck" }, priority = 13)
	public void Duplication_Check() throws Throwable {

		ScreenshotUtil.nextStep();
		log.info("--- Checking Duplication Check with Department In Create page: {} ---", currentSecurityGroupName);
		securitygroup.Create();
		securitygroup.securityGroupName(currentSecurityGroupName);
		ScreenshotUtil.capture();// Using currentDeptName to ensure we test with the actual name in the system);
		securitygroup.clickSecurityGroupName();
		securitygroup.clickSubmit();
		securitygroup.waitForToast();
		ScreenshotUtil.capture();
	}

	@Test(groups = { "Logout" }, priority = 18)
	public void Logout() throws Throwable {
		log.info("--- Executing Final Logout ---");
		ScreenshotUtil.nextStep();
		securitygroup.logout();
		log.info("Clicked logout button");
		securitygroup.waitForToast();
		securitygroup.waitForLoading();
		ScreenshotUtil.capture();
		log.info("--- Department Test Case Execution Finished ---");
	}

	@Test(groups = { "DB Back" }, priority = 19)
	public void Database_Backup() {
		log.info("--- Initiating Post-Test Database Backup ---");

		// Fallback logic: Use config filename if script number is missing
		String backupFolderName = (SCRIPT_NUMBER == null || SCRIPT_NUMBER.trim().isEmpty()) ? CURRENT_CONFIG_NAME
				: SCRIPT_NUMBER;

		log.info("Backup folder name determined: {}", backupFolderName);

		// Parameters: folderName, dbName, dbUser, dbPass, host, port
		DatabaseBackupUtil.backupPostgres(backupFolderName, PC_DB_NAME, "postgres", "root", "localhost", "5432");
		DatabaseBackupUtil.backupPostgres(backupFolderName, MASTER_DB_NAME, "postgres", "root", "localhost", "5432");
		DatabaseBackupUtil.backupPostgres(backupFolderName, MM_DB_NAME, "postgres", "root", "localhost", "5432");
	}

	@Test(groups = { "sidenavvalidation" }, dependsOnMethods = "moduleClick", priority = 16)
	public void SideNav_Validation() throws Throwable {
		log.info("--- Validating Side-Navigation Modules Based on DB Privileges ---");

		// 1. Get dynamically expected side-nav modules from DB
		List<String> expectedModules = securitygroup.getExpectedSideNavFromDB(securitygroup.currentUsername,
				SIDE_NAV_MODULE_MAPPING, PC_DB_NAME);
		log.info("Dynamically determined expected side-nav modules: {}", expectedModules);

		// 2. Expand each parent module and collect visible items incrementally (to
		// handle accordion behavior)
		List<String> finalActualModules = new ArrayList<>();

		// Initial capture for static/already-open modules
		finalActualModules.addAll(securitygroup.getAllSideNavModulesDisplayed());

		String[] mappings = SIDE_NAV_MODULE_MAPPING.split(";");
		for (String map : mappings) {
			String[] parts = map.split(":");
			if (parts.length > 2) {
				String parentModule = parts[0].trim();
				if (expectedModules.contains(parentModule)) {
					log.info("Expanding for incremental capture: {}", parentModule);
					securitygroup.expandSideNavModule(parentModule);
					// Collect newly visible sub-modules
					for (String active : securitygroup.getAllSideNavModulesDisplayed()) {
						if (!finalActualModules.contains(active))
							finalActualModules.add(active);
					}
				}
			}
		}
		ScreenshotUtil.capture();

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
			log.info("--- Editing Department (After Creation): {} ---", currentSecurityGroupName);
			ScreenshotUtil.nextStep();
			securitygroup.clickEdit(currentSecurityGroupName);
			log.info("Edit screen opened");
			securitygroup.waitForLoading();
			ScreenshotUtil.capture();
			ScreenshotUtil.nextStep();

			if (EDIT_SECURITYGROUP_NAME_AFTER_CREATE != null
					&& !EDIT_SECURITYGROUP_NAME_AFTER_CREATE.trim().isEmpty()) {
				log.info("Updating Name to: {}", EDIT_SECURITYGROUP_NAME_AFTER_CREATE);
				securitygroup.securityGroupName(EDIT_SECURITYGROUP_NAME_AFTER_CREATE);
				currentSecurityGroupName = EDIT_SECURITYGROUP_NAME_AFTER_CREATE;
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
			Assert.assertEquals(authToast, "SecurityGroup Updated Successfully",
					"Update failed with message: " + authToast);
		} else {
			log.info("Edit After Create step skipped based on configuration");
		}
	}

	@Test(groups = { "securitygroupUpdate" }, priority = 18)
	public void securitygroup_Active_Return() throws Throwable {

		if (SECURITYGROUP_ACTIVE_RETURN_ACTION.equalsIgnoreCase("yes")) {
			if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
				securitygroup.switchUser(USERNAME2, PASSWORD2, PC_DB_NAME, MASTER_MODULE);
			}
			log.info("--- Initiating Active Return Flow for: {} ---", currentSecurityGroupName);
			ScreenshotUtil.nextStep();
			log.info("Opening actions menu to access Approve/Return");
			securitygroup.clickActions(currentSecurityGroupName);
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
			Assert.assertEquals(returnToast, "SecurityGroup Returned Successfully",
					"Active Return failed with message: " + returnToast);

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
