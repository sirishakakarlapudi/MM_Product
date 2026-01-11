
package testCasesForOQProjects;

import static configData.RouteCodeData.*;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pageObjects.RouteCode;
import testBase.BaseClass;
import utilities.ScreenshotUtil;
import utilities.WaitUtil;

public class RouteCode_TC extends BaseClass {
	RouteCode route;

	@BeforeClass
	public void setUp() throws Exception {

		ScreenshotUtil.loadTemplateForEndAppend(TEMPLATE_PATH, OUTPUT_PATH);
		ScreenshotUtil.updateHeaderCellText(ACTUALHEADER, EXPECTEDHEADER);

		browserOpen();
		route = new RouteCode(driver);

	}

	@Test(priority = 1)
	public void Step_4_1_1() throws Throwable {
		driver.get("https://www.google.co.in/");
		WaitUtil.waitForVisible(driver, route.getSearchBox(), 10);
		route.searchBox(APP_URL);
		WaitUtil.waitForPageLoad(driver, 10);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.1");
		driver.get(APP_URL);
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.1");
		route.userName(USERNAME);
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.1");
		route.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.1");
		route.loginButton();
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.1");
		route.click_titleMasters();
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.1");
		route.masterClick(MASTER_MODULE);
		ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.1");
		route.masterClick(MASTER_SUB_MODULE);
		ScreenshotUtil.takeStepScreenshot("08 for step No.4.1.1");
		route.Create();
		ScreenshotUtil.takeStepScreenshot("09 for step No.4.1.1");

	}

	@Test(priority = 2)
	public void Step_4_1_2() throws Throwable {
		route.routeCode(ROUTE_CODE);

		route.routeCodeDesc(ROUTE_DESCRIPTION);
		String[] stagecode = STAGE_CODE.split(",");

		for (int i = 0; i < stagecode.length; i++) {
		    String sc = stagecode[i].trim();

		    route.selStageCode(sc);
		    if (i < stagecode.length - 1) {
		        route.clickAddButton();
		    }
		}


		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.2");
		route.createSubmit();
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.2");
		route.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.2");
		route.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.2");
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.2");

	}

	@Test(priority = 3)
	public void Step_4_1_3() throws Throwable {
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.3");
	}

	@Test(priority = 4)
	public void Step_4_1_4() throws Throwable {
		route.clickActionsForPendig(ROUTE_CODE);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.4");

	}

	@Test(priority = 5)
	public void Step_4_1_5() throws Throwable {
		route.clickCreateReview();
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.5");
		route.enterRemarks("Remarks");
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.5");
		route.clickReturn();
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.5");
		route.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.5");
		route.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.5");
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.5");
	}

	@Test(priority = 6)
	public void Step_4_1_6() throws Throwable {
		route.clickEditForPending(ROUTE_CODE);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.6");
		route.routeCode(EDIT_ROUTE_CODE);
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.6");
		route.clickUpdate();
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.6");
		route.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.6");
		route.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.6");
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.6");

	}

	@Test(priority = 7)
	public void Step_4_1_7() throws Throwable {
		route.clickActionsForPendig(EDIT_ROUTE_CODE);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.7");
		route.clickCreateReview();
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.7");
		route.enterRemarks("Remarks");
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.7");
		route.clickReview();
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.7");
		route.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.7");
		route.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.7");
		ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.7");

	}

	@Test(priority = 8)
	public void Step_4_1_8() throws Throwable {
		route.clickActionsForPendig(EDIT_ROUTE_CODE);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.7");
		route.clickCreateApprove();
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.7");
		route.enterRemarks("Remarks");
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.7");
		route.clickApprove();
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.7");
		route.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.7");
		route.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.7");
		ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.7");

	}

	@Test(priority = 9)
	public void Step_4_1_9() throws Throwable {
		route.clickActionsForActive(EDIT_ROUTE_CODE);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.9");
		route.clickInactive();
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.9");
		route.enterRemarks("Remarks");
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.9");
		route.clickInactive();
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.9");
		route.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.9");
		route.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.9");

	}

	@Test(priority = 10)
	public void Step_4_1_10() throws Throwable {
		route.clickActionsForPendig(EDIT_ROUTE_CODE);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.10");
		route.clickInactiveReview();
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.10");
		route.enterRemarks("Remarks");
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.10");
		route.clickReject();
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.10");
		route.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.10");
		route.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.10");

	}

	@Test(priority = 11)
	public void Step_4_1_11() throws Throwable {
		route.clickActionsForActive(EDIT_ROUTE_CODE);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.11");
		route.clickInactive();
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.11");
		route.enterRemarks("Remarks");
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.11");
		route.clickInactive();
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.11");
		route.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.11");
		route.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.11");

	}

	@Test(priority = 12)
	public void Step_4_1_12() throws Throwable {
		route.clickActionsForPendig(EDIT_ROUTE_CODE);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.12");
		route.clickInactiveReview();
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.12");
		route.enterRemarks("Remarks");
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.12");
		route.clickReview();
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.12");
		route.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.12");
		route.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.12");

	}

	@Test(priority = 13)
	public void Step_4_1_13() throws Throwable {
		route.clickActionsForPendig(EDIT_ROUTE_CODE);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.13");
		route.clickInactiveApprove();
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.13");
		route.enterRemarks("Remarks");
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.13");
		route.clickApprove();
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.13");
		route.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.13");
		route.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.13");

	}

	@Test(priority = 14)
	public void Step_4_1_14() throws Throwable {
		route.clickActionsForPendig(EDIT_ROUTE_CODE);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.14");
		route.clickActive();
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.14");
		String[] stagecode = ACTIVE_STAGE_CODE.split(",");

		for (int i = 0; i < stagecode.length; i++) {
		    String sc = stagecode[i].trim();

		    route.selStageCode(sc);
		    if (i < stagecode.length - 1) {
		        route.clickAddButton();
		    }
		}
		route.enterRemarks("Remarks");
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.14");
		route.clickActive();
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.14");
		route.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.14");
		route.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.14");

	}

	@Test(priority = 15)
	public void Step_4_1_15() throws Throwable {
		route.clickActionsForPendig(EDIT_ROUTE_CODE);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.15");
		route.clickCreateReview();
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.15");
		route.enterRemarks("Remarks");
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.15");
		route.clickReview();
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.15");
		route.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.15");
		route.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.15");

	}

	@Test(priority = 16)
	public void Step_4_1_16() throws Throwable {
		route.clickActionsForPendig(EDIT_ROUTE_CODE);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.16");
		route.clickCreateApprove();
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.16");
		route.enterRemarks("Remarks");
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.16");
		route.clickApprove();
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.16");
		route.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.16");
		route.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.16");

	}

	@Test(priority = 17)
	public void Step_4_1_17() throws Throwable {
		route.clickActionsForActive(EDIT_ROUTE_CODE);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.17");
		route.clickAddStage();
		route.clickAddButton();
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.17");

		String[] stagecode = EDIT_STAGE_CODE.split(",");

		for (int i = 0; i < stagecode.length; i++) {
		    String sc = stagecode[i].trim();

		    route.selStageCode(sc);

		    // Click Add ONLY if there is a next stage code
		    if (i < stagecode.length - 1) {
		        route.clickAddButton();
		    }
		}

		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.17");
		route.clickUpdate();
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.17");
		route.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.17");
		route.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.17");

	}

	@Test(priority = 19)
	public void Step_4_1_19() throws Throwable {
		route.clickActionsForPendig(EDIT_ROUTE_CODE);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.19");
		route.clickCreateReview();
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.19");
		route.enterRemarks("Remarks");
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.19");
		route.clickReview();
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.19");
		route.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.19");
		route.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.19");

	}

	@Test(priority = 20)
	public void Step_4_1_20() throws Throwable {
		route.clickActionsForPendig(EDIT_ROUTE_CODE);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.19");
		route.clickCreateApprove();
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.19");
		route.enterRemarks("Remarks");
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.19");
		route.clickApprove();
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.19");
		route.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.19");
		route.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.19");

	}

	@Test(priority = 21)
	public void Step_4_1_21() throws Throwable {
		route.click_profileDropdown();
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.17");
		route.logout();
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.17");

	}

}
