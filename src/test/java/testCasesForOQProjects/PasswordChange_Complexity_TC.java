package testCasesForOQProjects;

import static configData.PasswordChange_AndComplexityData.*;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pageObjects.PasswordChange_AndComplexity;
import utilities.DataProviders;
import utilities.ScreenshotUtil;
import utilities.UserManagementOQData;

public class PasswordChange_Complexity_TC extends OQBaseModule_TC {
	PasswordChange_AndComplexity pwd;

	@BeforeClass
	@Parameters({ "configFile" })
	public void setUp(@Optional("passwordchange_complexity.properties") String configFile) throws Exception {
		log.info("--- Starting PasswordChange and Complexity Test Case Setup with config: {} ---", configFile);

		// Load the dynamic property file
		configData.PasswordChange_AndComplexityData.loadProperties(configFile);

		// Initialize Base Class Variables
		CONFIG_NAME = "Password Change & Complexity";
		CHROME_URL_VAL = CHROME_URL;
		APP_URL_VAL = APP_URL;
		USERNAME_VAL = USERNAME1;
		PASSWORD_VAL = TEMPORARY_PASSWORD; // Using temporary password for initial login
		SCRIPT_NUMBER_VAL = SCRIPT_NUMBER;

		// Set conditional screenshot execution
		boolean screenshotsEnabled = "yes".equalsIgnoreCase(TAKE_SCREENSHOTS);
		ScreenshotUtil.setIsEnabled(screenshotsEnabled);

		if (screenshotsEnabled) {
			log.info("📸 Processing screenshot template and headers...");
			ScreenshotUtil.loadTemplateForEndAppend(TEMPLATE_PATH, OUTPUT_PATH);
			ScreenshotUtil.updateHeaderCellText(ACTUALHEADER, EXPECTEDHEADER);
			ScreenshotUtil.initScript(SCRIPT_NUMBER_VAL);
		}

		browserOpen();
		pwd = new PasswordChange_AndComplexity(driver);
		pageObject = pwd; // Essential for base class methods
		log.info("Setup completed. Screenshots enabled: {}", screenshotsEnabled);
	}

	@Override
	protected void updateEntryName(String newName) throws Throwable {
		// No-op for this functional test module
		log.info("updateEntryName called with '{}', but not applicable for Password complexity module.", newName);
	}

	// ==================== Standarcized Login wrapper ====================

	@Test(groups = { "userlogin" })
	public void userLogin() throws Throwable {
		log.info("Executing the passwordchange flow for user: {}", USERNAME_VAL);
		pwd.login(USERNAME1, TEMPORARY_PASSWORD);
	}
	
	
	
	@Test(dataProvider = "UserManagementOQ", dataProviderClass = DataProviders.class, singleThreaded = true)
	public void changeUserPasswordWithBulkData(UserManagementOQData userdata) throws Throwable {
		
		String username = userdata.getUsername();
		String temppassword = userdata.getTemporaryPassword();
		String newpassword = userdata.getNewPassword();
		pwd.login(username, temppassword);
		pwd.currentPassword(temppassword);
		pwd.newPassword(newpassword);
		pwd.confirmPassword(newpassword);
		pwd.clickSave();
	}
	
	

	// ==================== Custom Modules Specific Tests ====================

	@Test(groups = { "Checking Current Password" })
	public void checkingCurrentPassword() throws Throwable {
		ScreenshotUtil.nextStep();
		log.info("Checking the currentpassword field validation");
		pwd.currentPassword(TEMPORARY_PASSWORD);
		ScreenshotUtil.capture();
		pwd.currentPassword(INCORRECT_TEMPORARY_PASSWORD);
		pwd.newPassword(NEW_PASSWORD);
		pwd.confirmPassword(NEW_PASSWORD);
		pwd.clickSave();
		ScreenshotUtil.capture();
	}

	@Test(groups = { "Checking New Password" })
	public void checkingNewPassword() throws Throwable {
		ScreenshotUtil.nextStep();
		log.info("Checking the newpassword field complexity");
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
		Assert.assertTrue(Toast.equals("Password Changed successfully Login with new Password"),
				"Password Change failed with message: " + Toast);
		ScreenshotUtil.capture();
	}

	@Test(groups = { "Login with New Password" })
	public void loginWithNewPasword() throws Throwable {
		log.info("Logging with new password for user: {}", USERNAME_VAL);
		pwd.login(USERNAME1, NEW_PASSWORD);
		log.info("--- Verifying Masters Page Access ---");
		pwd.click_titleMasters();
		log.info("Clicked on Masters title");
		pwd.waitForLoading();
		ScreenshotUtil.capture();
	}
}
