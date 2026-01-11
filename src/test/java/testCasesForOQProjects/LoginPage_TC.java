package testCasesForOQProjects;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pageObjects.LoginPage;
import testBase.BaseClass;
import utilities.ScreenshotUtil;
import utilities.WaitUtil;

import static configData.LoginPageData.*;

public class LoginPage_TC extends BaseClass {
	LoginPage login;

	@BeforeClass
	public void setUp() throws Exception {

		ScreenshotUtil.loadTemplateForEndAppend(TEMPLATE_PATH, OUTPUT_PATH);
		ScreenshotUtil.updateHeaderCellText(ACTUALHEADER, EXPECTEDHEADER);

		browserOpen();
		login = new LoginPage(driver);

	}

	@Test(priority = 1)
	public void Step_4_1_1() throws Throwable {
		driver.get("https://www.google.co.in/");
		WaitUtil.waitForVisible(driver, login.getSearchBox(), 10);
		login.searchBox(APP_URL);
		WaitUtil.waitForPageLoad(driver, 10);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.1");
		driver.get(APP_URL);
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.1");

	}

	@Test(priority = 2)
	public void Step_4_1_2() throws Throwable {

		login.userName(USERNAME );
		login.passWord(PASSWORD);
		login.loginButton();
	}

	
	  @Test(priority = 3) public void Step_4_1_3() throws Throwable {
	  login.click_titleMasters();
	  }
	  
	  @Test(priority = 4) public void Step_4_1_4() throws Throwable {
	  login.click_allAppsdropdown("01 for step No.4.1.4");
	  ; }
	  
	  @Test(priority = 5) public void Step_4_1_5() throws Throwable {
	  login.click_dropdownMM("01 for step No.4.1.5");
	  ; }
	  
	  @Test(priority = 6) public void Step_4_1_6() throws Throwable {
	  login.click_confirmYes("01 for step No.4.1.6");
; }
	  
	  @Test(priority = 7) public void Step_4_1_7() throws Throwable {
	  login.click_profileDropdown();
	  ; }
	  
	  @Test(priority = 8) public void Step_4_1_8() throws Throwable {
	  login.profile("01 for step No.4.1.8");; }
	  
	  @Test(priority = 9) public void Step_4_1_9() throws Throwable {
	  login.click_profileDropdown();
	  ;
	  
	  login.logout("02 for step No.4.1.9"); 
	  
	  }
	 

}
