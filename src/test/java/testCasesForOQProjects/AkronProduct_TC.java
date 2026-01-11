package testCasesForOQProjects;

import static configData.AkronProductData.*;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pageObjects.BasePage;

import testBase.BaseClass;
import utilities.ScreenshotUtil;
import utilities.WaitUtil;

public class AkronProduct_TC extends BaseClass {
	BasePage product;

	@BeforeClass
	public void setUp() throws Exception {

		ScreenshotUtil.loadTemplateForEndAppend(TEMPLATE_PATH, OUTPUT_PATH);
		ScreenshotUtil.updateHeaderCellText(ACTUALHEADER, EXPECTEDHEADER);

		browserOpen();
		product = new BasePage(driver);

	}

	@Test(priority = 1)
	public void Step_4_1_1() throws Throwable {
		driver.get("https://www.google.co.in/");
		WaitUtil.waitForVisible(driver, product.getSearchBox(), 10);
		product.searchBox(APP_URL);
		WaitUtil.waitForPageLoad(driver, 10);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.1");
		driver.get(APP_URL);
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.1");
		product.userName(USERNAME);
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.1");
		product.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.1");
		product.loginButton();
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.1");
		product.click_titleMasters();
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.1");
		product.masterClick(MASTER_MODULE);
		ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.1");
		product.masterClick(MASTER_SUB_MODULE);
		ScreenshotUtil.takeStepScreenshot("08 for step No.4.1.1");
	}

	@Test(priority = 2)
	public void Step_4_1_2() throws Throwable {
		product.clickActions(PRODUCT);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.2");
		product.clickInactive();
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.2");
		product.enterRemarks("Remarks");
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.2");
		product.clickInactive();
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.2");
		product.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.2");
		product.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.2");

	}

	@Test(priority = 3)
	public void Step_4_1_3() throws Throwable {
		product.clickActions(NDC_NUMBER);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.3");
		product.clickInactiveReview();
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.3");
		product.enterRemarks("Remarks");
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.3");
		product.clickReject();
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.3");
		product.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.3");
		product.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.3");

	}

	@Test(priority = 4)
	public void Step_4_1_4() throws Throwable {
		product.clickActions(NDC_NUMBER);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.4");
		product.clickInactive();
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.4");
		product.enterRemarks("Remarks");
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.4");
		product.clickInactive();
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.4");
		product.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.4");
		product.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.4");
	}

	@Test(priority = 5)
	public void Step_4_1_5() throws Throwable {
		product.clickActions(NDC_NUMBER);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.5");
		product.clickInactiveReview();
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.5");
		product.enterRemarks("Remarks");
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.5");
		product.clickReview();
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.5");
		product.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.5");
		product.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.5");

	}

	@Test(priority = 6)
	public void Step_4_1_6() throws Throwable {
		product.clickActions(NDC_NUMBER);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.6");
		product.clickInactiveApprove();
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.6");
		product.enterRemarks("Remarks");
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.6");
		product.clickApprove();
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.6");
		product.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.6");
		product.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.6");

	}

	@Test(priority = 7)
	public void Step_4_1_7() throws Throwable {
		product.clickActions(NDC_NUMBER);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.7");
		product.clickActive();
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.7");
		product.enterRemarks("Remarks");
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.7");
		product.clickActive();
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.7");
		product.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.7");
		product.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.7");

	}

	@Test(priority = 8)
	public void Step_4_1_8() throws Throwable {

		product.clickActions(NDC_NUMBER);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.8");
		product.clickReview();
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.8");
		product.enterRemarks("Remarks");
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.8");
		product.clickReview();
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.8");
		product.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.8");
		product.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.8");

	}

	@Test(priority = 9)
	public void Step_4_1_9() throws Throwable {
		product.clickActions(NDC_NUMBER);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.9");
		product.clickApprove();
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.9");
		product.enterRemarks("Remarks");
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.9");
		product.clickApprove();
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.9");
		product.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.9");
		product.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.9");
	}

	@Test(priority = 10)
	public void Step_4_1_10() throws Throwable {
		product.click_profileDropdown();
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.10");
		product.logout();
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.10");

	}

}
