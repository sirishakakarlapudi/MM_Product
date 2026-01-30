package testCasesForOQProjects;

import static configData.QuickProductData.*;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pageObjects.QuickProduct;
import testBase.BaseClass;
import utilities.ScreenshotUtil;
import utilities.WaitUtil;

public class QuickProduct_TC extends BaseClass {
	QuickProduct quickproduct;

	@BeforeClass
	public void setUp() throws Exception {

		ScreenshotUtil.loadTemplateForEndAppend(TEMPLATE_PATH, OUTPUT_PATH);
		ScreenshotUtil.updateHeaderCellText(ACTUALHEADER, EXPECTEDHEADER);

		browserOpen();
		quickproduct = new QuickProduct(driver);

	}

	@Test(priority = 1)
	public void Step_4_1_1() throws Throwable {
		driver.get("https://www.google.co.in/");
		WaitUtil.waitForVisible(driver, quickproduct.getSearchBox(), 10);
		quickproduct.searchBox(APP_URL);
		WaitUtil.waitForPageLoad(driver, 10);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.1");
		driver.get(APP_URL);
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.1");
		quickproduct.userName(USERNAME);
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.1");
		quickproduct.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.1");
		quickproduct.loginButton();
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.1");
		quickproduct.click_titleMasters();
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.1");
		quickproduct.masterClick(MASTER_MODULE);
		ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.1");
		quickproduct.masterClick(MASTER_SUB_MODULE);
		ScreenshotUtil.takeStepScreenshot("08 for step No.4.1.1");
		quickproduct.Create();
		ScreenshotUtil.takeStepScreenshot("09 for step No.4.1.1");

	}

	@Test(priority = 2)
	public void Step_4_1_2() throws Throwable {
		//quickproduct.product(PRODUCT);

		//quickproduct.productDesc(PRODUCT_DESCRIPTION);
		String[] routecode = ROUTE_CODE.split(",");

		for (int i = 0; i < routecode.length; i++) {
			String sc = routecode[i].trim();

		//	quickproduct.selRouteCode(sc);
			if (i < routecode.length - 1) {
				//quickproduct.clickAddButton();
			}
		}

		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.2");
		//quickproduct.createSubmit_editUpdate();
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.2");
		quickproduct.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.2");
		quickproduct.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.2");
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.2");

	}

	@Test(priority = 3)
	public void Step_4_1_3() throws Throwable {
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.3");
	}

	@Test(priority = 4)
	public void Step_4_1_4() throws Throwable {
		quickproduct.clickActionsForPendig(PRODUCT);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.4");

	}

	@Test(priority = 5)
	public void Step_4_1_5() throws Throwable {
		quickproduct.clickCreateReview();
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.5");
		quickproduct.enterRemarks("Remarks");
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.5");
		quickproduct.clickReturn();
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.5");
		quickproduct.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.5");
		quickproduct.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.5");
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.5");
	}

	@Test(priority = 6)
	public void Step_4_1_6() throws Throwable {
		quickproduct.clickEditForPending(PRODUCT);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.6");
		//quickproduct.product(EDIT_PRODUCT);
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.6");
		//quickproduct.createSubmit_editUpdate();
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.6");
		quickproduct.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.6");
		quickproduct.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.6");
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.6");

	}

	@Test(priority = 7)
	public void Step_4_1_7() throws Throwable {
		quickproduct.clickActionsForPendig(EDIT_PRODUCT);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.7");
		quickproduct.clickCreateReview();
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.7");
		quickproduct.enterRemarks("Remarks");
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.7");
		quickproduct.clickReview();
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.7");
		quickproduct.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.7");
		quickproduct.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.7");
		ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.7");

	}

	@Test(priority = 8)
	public void Step_4_1_8() throws Throwable {
		quickproduct.clickActionsForPendig(EDIT_PRODUCT);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.7");
		quickproduct.clickCreateApprove();
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.7");
		quickproduct.enterRemarks("Remarks");
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.7");
		quickproduct.clickApprove();
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.7");
		quickproduct.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.7");
		quickproduct.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.7");
		ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.7");

	}
}
