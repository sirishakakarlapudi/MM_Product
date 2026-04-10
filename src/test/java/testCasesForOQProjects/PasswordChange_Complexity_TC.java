package testCasesForOQProjects;


import static configData.DepartmentData.CURRENT_CONFIG_NAME;
import static configData.DepartmentData.MASTER_DB_NAME;
import static configData.DepartmentData.MM_DB_NAME;
import static configData.DepartmentData.PC_DB_NAME;
import static configData.DepartmentData.SCRIPT_NUMBER;
import static configData.PasswordChange_AndComplexityData.*;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pageObjects.Department;
import pageObjects.PasswordChange_AndComplexity;
import testBase.BaseClass;
import utilities.DatabaseBackupUtil;
import utilities.ScreenshotUtil;
import utilities.SoftAssertionUtil;

public class PasswordChange_Complexity_TC extends BaseClass {
	PasswordChange_AndComplexity pwd;
	SoftAssertionUtil sa;

	@BeforeClass
	@Parameters({ "configFile" })
	public void setUp(@Optional("passwordchange_complexity.properties") String configFile) throws Exception {
		log.info("--- Starting PasswordChange and Compelxity Test Case Setup with config: {} ---", configFile);

		// Load the dynamic property file
		configData.PasswordChange_AndComplexityData.loadProperties(configFile);

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
		pwd = new PasswordChange_AndComplexity(driver);
		sa = new SoftAssertionUtil();
		log.info("Setup completed. Screenshots enabled: {}", screenshotsEnabled);
	}

	 @BeforeMethod
	    public void initSoftAssert() {
	        sa = new SoftAssertionUtil();
	    }
	 
	 
	// ========================Google Search Box=============

		@Test(groups = { "setup" }, priority = 1)
		public void initialSetUp() throws Exception {
			ScreenshotUtil.nextStep();
			log.info("Navigating to Chrome URL: {}", CHROME_URL);
			driver.navigate().to(CHROME_URL);
			log.info("Waiting for search box to be visible");
			pwd.waitForElementToVisible(pwd.getSearchBox());
			Assert.assertTrue(pwd.getSearchBox().isDisplayed(), "Search box is not visible!");
			log.info("Searching for Application URL: {}", APP_URL);
			pwd.searchBox(APP_URL);
			pwd.waitForLoading();
			ScreenshotUtil.capture();
			log.info("Initial setup completed and screenshot captured");
		}

		// ===================URL Navigation========================

		@Test(groups = { "url" }, priority = 2)
		public void url() throws Throwable {
			ScreenshotUtil.nextStep();
			driver.navigate().to(APP_URL);
			log.info("Navigating to App URL: {}", APP_URL);
			ScreenshotUtil.capture();
		}
		
		
		// ====================URL Validation===================

		@Test(groups = { "userlogin" }, priority = 3)
		public void userLogin() throws Throwable {
			log.info("Executing the passwordchange flow for user: {}", USERNAME);
			pwd.login(USERNAME, TEMPORARY_PASSWORD);
		}
		
		
		// ====================Checking Current Password===================
		
		
		@Test(groups = {"Checking Current Password"},  priority = 4)
		public void checkingCurrentPassword() throws Throwable {
			ScreenshotUtil.nextStep();
			log.info("Checking the currentpassword field");
			pwd.currentPassword(TEMPORARY_PASSWORD);
			ScreenshotUtil.capture();
			pwd.currentPassword(INCORRECT_TEMPORARY_PASSWORD);
			pwd.newPassword(NEW_PASSWORD);
			pwd.confirmPassword(NEW_PASSWORD);
			pwd.clickSave();
			ScreenshotUtil.capture();	
		
		}
		
		
		// ====================Checking New Password===================
		
		@Test(groups = {"Checking New Password"},  priority = 5)
		public void checkingNewPassword() throws Throwable {
			ScreenshotUtil.nextStep();
			log.info("Checking the newpassword field");
			pwd.currentPassword(TEMPORARY_PASSWORD);
			ScreenshotUtil.capture();
			pwd.newPassword(NEW_PASSWORD_1);
			ScreenshotUtil.capture();
			pwd.newPassword(NEW_PASSWORD_2);
			ScreenshotUtil.capture();
			pwd.newPassword(NEW_PASSWORD);
			ScreenshotUtil.capture();
			pwd.confirmPassword(NEW_PASSWORD);
			pwd.clickSave();
			String Toast = pwd.waitForToast();
			ScreenshotUtil.capture();
			pwd.waitForLoading();
			Assert.assertTrue(
				   Toast.equals("Password Changed successfully Login with new Password"),
				    "Password Change failed with message: " + Toast
				);
			ScreenshotUtil.capture();	
			
		}
		
		// ====================Logging with New Password===================
		
		@Test(groups = {"Login with New Password"},  priority = 6)
		public void loginWithNewPasword() throws Throwable {
			log.info("Logging with new password flow for user: {}", USERNAME);
			pwd.login(USERNAME, NEW_PASSWORD);
			log.info("--- Clicking Module ---");
			pwd.click_titleMasters();
			log.info("Clicked on Masters title");
			pwd.waitForLoading();
			ScreenshotUtil.capture();
			
		}
		
		
		// ===========================LOGOUT========================

		@Test(groups = { "Logout" }, priority = 7)
		public void Logout() throws Throwable {
			log.info("--- Executing Final Logout ---");
			ScreenshotUtil.nextStep();
			pwd.logout();
			log.info("Clicked logout button");
			pwd.waitForToast();
			pwd.waitForLoading();
			ScreenshotUtil.capture();
			log.info("--- Password Change and Complexity Execution Finished ---");
		}

		// ===========================DATABASE BACKUP========================
		@Test(groups = { "DB Back" }, priority = 8)
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
