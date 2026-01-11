package testCasesForOQProjects;

import static configData.UserManagementData.*;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import pageObjects.UserManagement;
import testBase.BaseClass;
import utilities.ScreenshotUtil;
import utilities.WaitUtil;

public class UserManagement_TC extends BaseClass {
	UserManagement user;

	@BeforeClass
	public void setUp() throws Exception {

		ScreenshotUtil.loadTemplateForEndAppend(TEMPLATE_PATH, OUTPUT_PATH);
		ScreenshotUtil.updateHeaderCellText(ACTUALHEADER, EXPECTEDHEADER);

		browserOpen();
		user = new UserManagement(driver);

	}

	@Test(priority = 1)
	public void Step_4_1_1() throws Throwable {
		driver.get("https://www.google.co.in/");
		WaitUtil.waitForVisible(driver, user.getSearchBox(), 10);
		user.searchBox(APP_URL);
		WaitUtil.waitForPageLoad(driver, 10);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.1");
		driver.get(APP_URL);
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.1");
		user.userName(USERNAME);
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.1");
		user.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.1");
		user.loginButton();
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.1");
		user.click_titleMasters();
		  ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.1");
		  user.masterClick(MASTER_MODULE);
			ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.1");
			user.Create();
		  ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.1"); 
		

	}

	@Test(priority = 2)
	public void Step_4_1_2() throws Throwable {
		user.enterEmployeeID(EMPLOYEE_ID);
		user.enterEmployeeName(EMPLOYEE_NAME);
		
		user.empUserName(USER_NAME);
		user.temporaryPassword(TEMPORARY_PASSWORD);
		user.deptSelect(DEPARTMENT);
		user.designation(DESIGNATION);
		user.moduleSelect(MODULE);
		user.sgNameSelect(SECURITY_GROUP);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.2");
		 user.createSubmit();
		 ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.2");
		 user.passWord(PASSWORD);
		 ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.2");
		 user.authenticateButton();
		 ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.2");
		 ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.2");

		
	}
}