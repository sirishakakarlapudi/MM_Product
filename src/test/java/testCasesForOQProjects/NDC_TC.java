package testCasesForOQProjects;

import static configData.NDCData.*;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pageObjects.BasePage;

import testBase.BaseClass;
import utilities.ScreenshotUtil;
import utilities.WaitUtil;

public class NDC_TC extends BaseClass {
	BasePage ndc;

	@BeforeClass
	public void setUp() throws Exception {

		ScreenshotUtil.loadTemplateForEndAppend(TEMPLATE_PATH, OUTPUT_PATH);
		ScreenshotUtil.updateHeaderCellText(ACTUALHEADER, EXPECTEDHEADER);

		browserOpen();
		ndc = new BasePage(driver);

	}

	@Test(priority = 1)
	public void Step_4_1_1() throws Throwable {
		driver.get("https://www.google.co.in/");
		WaitUtil.waitForVisible(driver, ndc.getSearchBox(), 10);
		ndc.searchBox(APP_URL);
		WaitUtil.waitForPageLoad(driver, 10);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.1");
		driver.get(APP_URL);
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.1");
		ndc.userName(USERNAME);
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.1");
		ndc.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.1");
		ndc.loginButton();
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.1");
		ndc.click_titleMasters();
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.1");
		ndc.masterClick(MASTER_MODULE);
		ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.1");
		ndc.masterClick(MASTER_SUB_MODULE);
		ScreenshotUtil.takeStepScreenshot("08 for step No.4.1.1");
	}
	
	@Test(priority = 2)
	public void Step_4_1_2() throws Throwable {
		ndc.clickActions(NDC_NUMBER);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.2");
		ndc.clickInactive();
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.2");
		ndc.enterRemarks("Remarks");
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.2");
		ndc.clickInactive();
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.2");
		ndc.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.2");
		ndc.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.2");
	
		
	}
	
	@Test(priority = 3)
	public void Step_4_1_3() throws Throwable {
		ndc.clickActions(NDC_NUMBER);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.3");
		ndc.clickInactiveReview();
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.3");
		ndc.enterRemarks("Remarks");
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.3");
		ndc.clickReject();
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.3");
		ndc.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.3");
		ndc.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.3");
		
	}
	
	@Test(priority = 4)
	public void Step_4_1_4() throws Throwable {
		ndc.clickActions(NDC_NUMBER);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.4");
		ndc.clickInactive();
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.4");
		ndc.enterRemarks("Remarks");
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.4");
		ndc.clickInactive();
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.4");
		ndc.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.4");
		ndc.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.4");
	}
	
	@Test(priority = 5)
	public void Step_4_1_5() throws Throwable {
		ndc.clickActions(NDC_NUMBER);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.5");
		ndc.clickInactiveReview();
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.5");
		ndc.enterRemarks("Remarks");
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.5");
		ndc.clickReview();
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.5");
		ndc.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.5");
		ndc.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.5");
		
		
	}
	
	@Test(priority = 6)
	public void Step_4_1_6() throws Throwable {
		ndc.clickActions(NDC_NUMBER);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.6");
		ndc.clickInactiveApprove();
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.6");
		ndc.enterRemarks("Remarks");
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.6");
		ndc.clickApprove();
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.6");
		ndc.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.6");
		ndc.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.6");
		
	}
	@Test(priority = 7)
	public void Step_4_1_7() throws Throwable {
		ndc.clickActions(NDC_NUMBER);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.7");
		ndc.clickActive();
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.7");
		ndc.enterRemarks("Remarks");
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.7");
		ndc.clickActive();
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.7");
		ndc.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.7");
		ndc.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.7");
		
	}
	@Test(priority = 8)
	public void Step_4_1_8() throws Throwable {
		
		ndc.clickActions(NDC_NUMBER);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.8");
		ndc.clickReview();
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.8");
		ndc.enterRemarks("Remarks");
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.8");
		ndc.clickReview();
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.8");
		ndc.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.8");
		ndc.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.8");
		
	}
	
	@Test(priority = 9)
	public void Step_4_1_9() throws Throwable {
		ndc.clickActions(NDC_NUMBER);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.9");
		ndc.clickApprove();
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.9");
		ndc.enterRemarks("Remarks");
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.9");
		ndc.clickApprove();
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.9");
		ndc.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.9");
		ndc.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.9");
	}
	
	@Test(priority = 10)
	public void Step_4_1_10() throws Throwable {
		ndc.click_profileDropdown();
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.10");
		ndc.logout();
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.10");

	}
	
	

	
	
	

}
