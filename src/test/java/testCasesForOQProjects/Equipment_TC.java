
package testCasesForOQProjects;

import static configData.EquipmentData.*;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pageObjects.Equipment;
import testBase.BaseClass;
import utilities.ScreenshotUtil;
import utilities.WaitUtil;

public class Equipment_TC extends BaseClass {
	Equipment equipment;

	@BeforeClass
	public void setUp() throws Exception {

		ScreenshotUtil.loadTemplateForEndAppend(TEMPLATE_PATH, OUTPUT_PATH);
		ScreenshotUtil.updateHeaderCellText(ACTUALHEADER, EXPECTEDHEADER);

		browserOpen();
		equipment = new Equipment(driver);

	}

	@Test(priority = 1)
	public void Step_4_1_1() throws Throwable {
		driver.get("https://www.google.co.in/");
		WaitUtil.waitForVisible(driver, equipment.getSearchBox(), 10);
		equipment.searchBox(APP_URL);
		WaitUtil.waitForPageLoad(driver, 10);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.1");
		driver.get(APP_URL);
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.1");
		equipment.userName(USERNAME);
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.1");
		equipment.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.1");
		equipment.loginButton();
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.1");
		equipment.click_titleMasters();
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.1");
		equipment.masterClick(MASTER_MODULE);
		ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.1");
		equipment.Create();
		ScreenshotUtil.takeStepScreenshot("09 for step No.4.1.1");

	}

	@Test(priority = 2)
	public void Step_4_1_2() throws Throwable {
		equipment.equipmentName(EQUIPMENT_NAME);
		equipment.equipmentId(EQUIPMENT_ID);
		equipment.selEquipmentType(EQUIPMENT_TYPE);
		equipment.capacityInKg(CAPACITY_IN_KG);
		equipment.operationalRangeMin(OPERATIONAL_RANGE_MIN);
		equipment.operationalRangeMax(OPERATIONAL_RANGE_MAX);
		equipment.selDepartment(DEPARTMENT);
		equipment.selFacility(FACILITY);
		equipment.selWeighingBalanceFacility(WEIGHING_BALANCE_FACILITY);
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.2");
		equipment.createSubmit();
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.2");
		equipment.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.2");
		equipment.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.2");
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.2");

	}

	@Test(priority = 3)
	public void Step_4_1_3() throws Throwable {
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.3");
	}

	@Test(priority = 4)
	public void Step_4_1_4() throws Throwable {
		equipment.clickActions(EQUIPMENT_NAME);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.4");

	}

	@Test(priority = 5)
	public void Step_4_1_5() throws Throwable {
		equipment.clickReview();
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.5");
		equipment.enterRemarks("Remarks");
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.5");
		equipment.clickReturn();
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.5");
		equipment.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.5");
		equipment.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.5");
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.5");
	}

	@Test(priority = 6)
	public void Step_4_1_6() throws Throwable {
		equipment.clickEdit(EQUIPMENT_NAME);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.6");
		equipment.equipmentName(EDIT_EQUIPMENT_NAME);
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.6");
		equipment.clickUpdate();
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.6");
		equipment.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.6");
		equipment.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.6");
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.6");

	}

	@Test(priority = 7)
	public void Step_4_1_7() throws Throwable {
		equipment.clickActions(EDIT_EQUIPMENT_NAME);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.7");
		equipment.clickReview();
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.7");
		equipment.enterRemarks("Remarks");
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.7");
		equipment.clickReview();
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.7");
		equipment.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.7");
		equipment.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.7");
		ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.7");

	}

	@Test(priority = 8)
	public void Step_4_1_8() throws Throwable {
		equipment.clickActions(EDIT_EQUIPMENT_NAME);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.7");
		equipment.clickApprove();
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.7");
		equipment.enterRemarks("Remarks");
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.7");
		equipment.clickApprove();
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.7");
		equipment.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.7");
		equipment.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.7");
		ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.7");

	}

	
	@Test(priority = 9)
	public void Step_4_1_9() throws Throwable {
		equipment.click_profileDropdown();
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.9");
		equipment.logout();
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.9");

	}

}
