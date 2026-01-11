package testCasesForOQProjects;

import static configData.FacilityData.*;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pageObjects.Facility;
import testBase.BaseClass;
import utilities.ScreenshotUtil;
import utilities.WaitUtil;

public class Facility_TC extends BaseClass {
	Facility facility;

	@BeforeClass
	public void setUp() throws Exception {

		ScreenshotUtil.loadTemplateForEndAppend(TEMPLATE_PATH, OUTPUT_PATH);
		ScreenshotUtil.updateHeaderCellText(ACTUALHEADER, EXPECTEDHEADER);

		browserOpen();
		facility = new Facility(driver);

	}

	@Test(priority = 1)
	public void Step_4_1_1() throws Throwable {
		driver.get("https://www.google.co.in/");
		WaitUtil.waitForVisible(driver, facility.getSearchBox(), 10);
		facility.searchBox(APP_URL);
		WaitUtil.waitForPageLoad(driver, 10);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.1");
		driver.get(APP_URL);
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.1");
		facility.userName(USERNAME);
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.1");
		facility.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.1");
		facility.loginButton();
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.1");
		facility.click_titleMasters();
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.1");
		facility.masterClick(MASTER_MODULE);
		ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.1");
		facility.Create();
		ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.1");

	}

	@Test(priority = 2)
	public void Step_4_1_2() throws Throwable {
		facility.facilityName(FACILITY_NAME);
		facility.selFacilityType(FACILITY_TYPE);
		facility.selDepartment(DEPARTMENT);
		if (!"FACILITY".equalsIgnoreCase(FACILITY_TYPE)) {
			facility.selParentFacility(PARENT_FACILITY);
		}

		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.2");
		facility.createSubmit();
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.2");
		facility.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.2");
		facility.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.2");
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.2");

	}

	@Test(priority = 3)
	public void Step_4_1_3() throws Throwable {
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.3");
	}

	@Test(priority = 4)
	public void Step_4_1_4() throws Throwable {
		facility.clickActions(FACILITY_NAME);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.4");

	}

	@Test(priority = 5)
	public void Step_4_1_5() throws Throwable {
		facility.clickApprove();
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.5");
		facility.enterRemarks("Remarks");
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.5");
		facility.clickReturn();
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.5");
		facility.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.5");
		facility.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.5");
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.5");
	}

	@Test(priority = 6)
	public void Step_4_1_6() throws Throwable {
		facility.clickEdit(FACILITY_NAME);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.6");
		facility.facilityName(EDIT_FACILITY_NAME);
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.6");
		facility.clickUpdate();
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.6");
		facility.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.6");
		facility.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.6");
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.6");

	}

	@Test(priority = 7)
	public void Step_4_1_7() throws Throwable {
		facility.clickActions(EDIT_FACILITY_NAME);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.7");
		facility.clickApprove();
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.7");
		facility.enterRemarks("Remarks");
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.7");
		facility.clickApprove();
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.7");
		facility.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.7");
		facility.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.7");
		ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.7");

	}

	@Test(priority = 8)
	public void Step_4_1_8() throws Throwable {
		facility.click_profileDropdown();
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.8");
		facility.logout();
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.8");

	}

}
