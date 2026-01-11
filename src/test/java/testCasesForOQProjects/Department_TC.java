package testCasesForOQProjects;

import static configData.DepartmentData.*;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pageObjects.Department;
import testBase.BaseClass;
import utilities.ScreenshotUtil;
import utilities.WaitUtil;

public class Department_TC extends BaseClass {
	Department dept;

	@BeforeClass
	public void setUp() throws Exception {

		ScreenshotUtil.loadTemplateForEndAppend(TEMPLATE_PATH, OUTPUT_PATH);
		ScreenshotUtil.updateHeaderCellText(ACTUALHEADER, EXPECTEDHEADER);

		browserOpen();
		dept = new Department(driver);
		// Configure table to identify rows by "Department Name" column
		dept.setTableHeaders("Department Name");

	}

	@Test(priority = 1)
	public void Step_4_1_1() throws Throwable {
		driver.get("https://www.google.co.in/");
		WaitUtil.waitForVisible(driver, dept.getSearchBox(), 10);
		dept.searchBox(APP_URL);
		WaitUtil.waitForPageLoad(driver, 10);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.1");
		driver.get(APP_URL);
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.1");
		dept.userName(USERNAME);
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.1");
		dept.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.1");
		dept.loginButton();
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.1");
		dept.click_titleMasters();
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.1");
		dept.masterClick(MASTER_MODULE);
		ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.1");
		dept.Create();
		ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.1");

	}

	@Test(priority = 2)
	public void Step_4_1_2() throws Throwable {
		dept.deptName(DEPT_NAME);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.2");
		dept.deptDesc(DEPT_DESC);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.2");
		dept.createSubmit();
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.2");
		dept.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.2");
		dept.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.2");
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.2");

	}

	@Test(priority = 3)
	public void Step_4_1_3() throws Throwable {
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.3");
	}

	@Test(priority = 4)
	public void Step_4_1_4() throws Throwable {
		dept.clickActions(DEPT_NAME);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.4");

	}

	@Test(priority = 5)
	public void Step_4_1_5() throws Throwable {
		dept.clickApprove();
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.5");
		dept.enterRemarks("Remarks");
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.5");
		dept.clickReturn();
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.5");
		dept.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.5");
		dept.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.5");
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.5");
	}

	@Test(priority = 6)
	public void Step_4_1_6() throws Throwable {
		dept.clickEdit(DEPT_NAME);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.6");
		dept.deptName(EDIT_DEPT_NAME);
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.6");
		dept.clickUpdate();
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.6");
		dept.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.6");
		dept.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.6");
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.6");

	}

	@Test(priority = 7)
	public void Step_4_1_7() throws Throwable {
		dept.clickActions(EDIT_DEPT_NAME);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.7");
		dept.clickApprove();
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.7");
		dept.enterRemarks("Remarks");
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.7");
		dept.clickApprove();
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.7");
		dept.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.7");
		dept.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.7");
		ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.7");

	}

	@Test(priority = 8)
	public void Step_4_1_8() throws Throwable {
		dept.click_profileDropdown();
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.8");
		dept.logout();
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.8");

	}

}