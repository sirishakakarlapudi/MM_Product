package testCasesForOQProjects;



import static configData.SecurityGroupData.*;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pageObjects.Department;
import pageObjects.SecurityGroup;
import testBase.BaseClass;
import utilities.DatabaseBackupUtil;
import utilities.ScreenshotUtil;
import utilities.SoftAssertionUtil;
import utilities.WaitUtil;


public class SecurityGroup_TC extends BaseClass {
	
	
		SecurityGroup securitygroup;
		String currentSecurityGroupName; // Track the current name (including edits)
		SoftAssertionUtil sa;

		@BeforeClass
		@Parameters({ "configFile" })
		public void setUp(@Optional("securitygroup.properties") String configFile) throws Exception {
			log.info("--- Starting Department Test Case Setup with config: {} ---", configFile);

			// Load the dynamic property file
			configData.DepartmentData.loadProperties(configFile);

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
			sa = new SoftAssertionUtil();
			securitygroup.setTableHeaders(TABLE_HEADERS);
			log.info("Setup completed. Screenshots enabled: {}", screenshotsEnabled);
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
			ScreenshotUtil.nextStep();
			driver.navigate().to(APP_URL);
			log.info("Navigating to App URL: {}", APP_URL);
		}
		
		@Test(groups = { "userlogin" }, priority = 4)
		public void userLoginBeforeCreate() throws Throwable {
			log.info("Executing the flow with single/multiple : {}", ACTIONSPERFORMEDBY);
			if (ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
				securitygroup.login(USERNAME, PASSWORD);
			} else {
				securitygroup.login(USERNAME1, PASSWORD1);
			}
		}

		@Test(groups = { "moduleselect" }, priority = 5)
		public void moduleClick() throws Throwable {
			log.info("--- Clicking Module ---");
			ScreenshotUtil.nextStep();
			securitygroup.click_titleMasters();
			log.info("Clicked on Masters title");
			ScreenshotUtil.capture();
			securitygroup.masterClick(MASTER_MODULE);
			log.info("Clicked on Master Module: {}", MASTER_MODULE);
			securitygroup.waitForLoading();
			ScreenshotUtil.capture();
		}
	
		@Test(groups = { "sidenavvalidation" }, dependsOnMethods = "moduleClick", priority = 5)
		public void SideNav_Validation() throws Throwable {
			log.info("--- Validating Side-Navigation Modules Based on DB Privileges ---");

			// 1. Get dynamically expected side-nav modules from DB
			List<String> expectedModules = securitygroup.getExpectedSideNavFromDB(securitygroup.currentUsername, SIDE_NAV_MODULE_MAPPING,
					PC_DB_NAME);
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
		
		
		
		@Test(groups = { "Creation" }, priority = 5)
		public void Creation_Of_SecurityGroup() throws Throwable {
			log.info("--- Navigating to Create SecurityGroup Screen ---");
			ScreenshotUtil.nextStep();
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
			securitygroup.selPrivileges(USER_PRIVILEGES); 
			log.info("Entered Name and Description");
			ScreenshotUtil.capture();
			securitygroup.createSubmit();
			log.info("Clicked Submit");
			ScreenshotUtil.capture();
			String authToast = securitygroup.authenticate(securitygroup.currentPassword);
			Assert.assertEquals(authToast, "SecurityGroup Created Successfully", "Creation failed with message: " + authToast);
		}

		@Test(groups = { "ClickActions" }, dependsOnMethods = "Actions_Validation", priority = 6)
		public void Click_Actions_After_Create() throws Throwable {
			log.info("--- Attempting to open Actions Menu for: {} ---", currentSecurityGroupName);
			ScreenshotUtil.nextStep();
			securitygroup.clickActions(currentSecurityGroupName);
			log.info("Successfully opened Actions menu for {}", currentSecurityGroupName);
			ScreenshotUtil.capture();
			securitygroup.clickActions(currentSecurityGroupName);
		}

		@Test(groups = { "ClickView" }, dependsOnMethods = "moduleClick", priority = 7)
		public void Click_View() throws Throwable {
			if (SECURITYGROUP_VIEW_ACTION.equalsIgnoreCase("yes")) {
				log.info("--- Viewing Department: {} ---", currentSecurityGroupName);
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

		
		@Test(groups = { "EditAfterCreate" }, dependsOnMethods = "moduleClick", priority = 8)
		public void Edit_After_Create() throws Throwable {

			if (SECURITYGROUP_EDIT_AFTER_CREATE_ACTION.equalsIgnoreCase("yes")) {
				log.info("--- Editing Department (After Creation): {} ---", currentSecurityGroupName);
				ScreenshotUtil.nextStep();
				securitygroup.clickEdit(currentSecurityGroupName);
				log.info("Edit screen opened");
				securitygroup.waitForLoading();
				ScreenshotUtil.capture();
				ScreenshotUtil.nextStep();

				if (EDIT_SECURITYGROUP_NAME_AFTER_CREATE != null && !EDIT_SECURITYGROUP_NAME_AFTER_CREATE.trim().isEmpty()) {
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
				if (EDIT_USER_PRIVILEGES_AFTER_CREATE != null && !EDIT_USER_PRIVILEGES_AFTER_CREATE.trim().isEmpty()) {
					log.info("Updating Privilegs to: {}", EDIT_USER_PRIVILEGES_AFTER_CREATE);
					securitygroup.selPrivileges(EDIT_USER_PRIVILEGES_AFTER_CREATE);
					
				}
				
				ScreenshotUtil.capture();
				securitygroup.clickUpdate();
				log.info("Clicked Update");
				ScreenshotUtil.capture();
				String authToast = securitygroup.authenticate(securitygroup.currentPassword);
				Assert.assertEquals(authToast, "SecurityGroup Updated Successfully",
						"Update failed with message: " + authToast);
			} else {
				log.info("Edit After Create step skipped based on configuration");
			}
		}
		
		@Test(groups = { "securitygroupReturn_securitygroupEdit" }, dependsOnMethods = "moduleClick", priority = 10)
		public void securitygroup_Return_and_Edit() throws Throwable {

			if (SECURITYGROUP_RETURN_ACTION.equalsIgnoreCase("yes")) {

				if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
					securitygroup.switchUser(USERNAME2, PASSWORD2, MASTER_MODULE);
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
				String returnToast = securitygroup.authenticate(securitygroup.currentPassword);
				Assert.assertEquals(returnToast, "SecurityGroup Returned Successfully",
						"Return failed with message: " + returnToast);

				if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
					securitygroup.switchUser(USERNAME1, PASSWORD1, MASTER_MODULE);
				}

				// This part will now execute for BOTH single and multiple users
				log.info("Opening Edit screen (After Return)");
				ScreenshotUtil.nextStep();
				securitygroup.clickEdit(currentSecurityGroupName);
				securitygroup.waitForLoading();
				ScreenshotUtil.capture();
				ScreenshotUtil.nextStep();
				if (EDIT_SECURITYGROUP_NAME_AFTER_RETRUN != null && !EDIT_SECURITYGROUP_NAME_AFTER_RETRUN.trim().isEmpty()) {
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
				if (EDIT_USER_PRIVILEGES_AFTER_RETURN != null && !EDIT_USER_PRIVILEGES_AFTER_RETURN.trim().isEmpty()) {
					log.info("Updating Privilegs to: {}", EDIT_USER_PRIVILEGES_AFTER_RETURN);
					securitygroup.selPrivileges(EDIT_USER_PRIVILEGES_AFTER_RETURN);
					
				}

				ScreenshotUtil.capture();
				securitygroup.clickUpdate();
				log.info("Clicked Update");
				ScreenshotUtil.capture();
				securitygroup.authenticate(securitygroup.currentPassword);
			} else {
				log.info("SecurityGroup Return and Edit skipped based on configuration");
			}
		}

		
		@Test(groups = { "securitygroupApprove" }, dependsOnMethods = "moduleClick", priority = 11)
		public void securitygroup_Approve() throws Throwable {

			if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
				log.info("--- Approving Department: {} ---", currentSecurityGroupName);
				securitygroup.switchUser(USERNAME2, PASSWORD2, MASTER_MODULE);
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
			String approveToast = securitygroup.authenticate(securitygroup.currentPassword);
			Assert.assertEquals(approveToast, "SecurityGroup Approved Successfully",
					"Approval failed with message: " + approveToast);
		}
		
		
		@Test(groups = { "securitygroupUpdate" }, priority = 11)
		public void securitygroup_Update() throws Throwable {
			if (UPDATE_PRIVILEGES_ACTION.equalsIgnoreCase("yes")) {
			
			if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
				log.info("--- Approving Department: {} ---", currentSecurityGroupName);
				securitygroup.switchUser(USERNAME1, PASSWORD1, MASTER_MODULE);
			}
			ScreenshotUtil.nextStep();
			log.info("Opening actions menu to access Update Privileges");
			securitygroup.clickActions(currentSecurityGroupName);
			ScreenshotUtil.capture();
			log.info("Clicking Update to trigger return dialog");
			securitygroup.clickUpdate();
			securitygroup.waitForLoading();
			ScreenshotUtil.capture();
			securitygroup.selPrivileges(UPDATE_USER_PRIVILEGES_AFTER_ACTIVE);
			log.info("Update Privilegs {}", UPDATE_USER_PRIVILEGES_AFTER_ACTIVE);
			ScreenshotUtil.capture();
			securitygroup.clickUpdate();
			log.info("Clicked Update button");
			ScreenshotUtil.capture();
			String returnToast = securitygroup.authenticate(securitygroup.currentPassword);
			Assert.assertEquals(returnToast, "SecurityGroup Updated Successfully",
					"Update failed with message: " + returnToast);
			
			
			}
		}
		
		@Test(groups = { "securitygroupUpdate" }, priority = 11)
		public void securitygroup_Active_Return() throws Throwable {
		
		
			if (SECURITYGROUP_ACTIVE_RETURN_ACTION.equalsIgnoreCase("yes")) {
				if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
					securitygroup.switchUser(USERNAME2, PASSWORD2, MASTER_MODULE);
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
				String returnToast = securitygroup.authenticate(securitygroup.currentPassword);
				Assert.assertEquals(returnToast, "SecurityGroup Returned Successfully",
						"Active Return failed with message: " + returnToast);


			} else {
				log.info("Active Return step skipped based on configuration");
			}
			
		}
		

		@Test(groups = { "securitygroupUpdateApprove" }, priority = 11)
		public void securitygroup_Active_Approve() throws Throwable {
			
			
			if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
				log.info("--- Approving Department: {} ---", currentSecurityGroupName);
				securitygroup.switchUser(USERNAME2, PASSWORD2, MASTER_MODULE);
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
			String approveToast = securitygroup.authenticate(securitygroup.currentPassword);
			Assert.assertEquals(approveToast, "SecurityGroup Approved Successfully",
					"Approval failed with message: " + approveToast);
		}
		
			
		
		

		@Test(groups = { "Logout" }, dependsOnMethods = "moduleClick", priority = 12)
		public void Logout() throws Throwable {
			log.info("--- Executing Final Logout ---");
			ScreenshotUtil.nextStep();
			securitygroup.click_profileDropdown();
			ScreenshotUtil.capture();
			securitygroup.logout();
			log.info("Clicked logout button");
			securitygroup.waitForToast();
			ScreenshotUtil.capture();
			log.info("--- Department Test Case Execution Finished ---");
		}

		@Test(groups = { "DB Back" }, priority = 13)
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
		
		 


}
