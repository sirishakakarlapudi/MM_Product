package testCasesForOQProjects;

import static configData.SecurityGroupData.*;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pageObjects.SecurityGroup;
import testBase.BaseClass;
import utilities.ScreenshotUtil;
import utilities.WaitUtil;


public class SecurityGroup_TC extends BaseClass {
	SecurityGroup securitygroup;
	

		@BeforeClass
		public void setUp() throws Exception {

			ScreenshotUtil.loadTemplateForEndAppend(TEMPLATE_PATH, OUTPUT_PATH);
			ScreenshotUtil.updateHeaderCellText(ACTUALHEADER, EXPECTEDHEADER);

			browserOpen();
			securitygroup = new SecurityGroup(driver);

		}

		@Test(priority = 1)
		public void Step_4_1_1() throws Throwable {
			driver.get("https://www.google.co.in/");
			WaitUtil.waitForVisible(driver, securitygroup.getSearchBox(), 10);
			securitygroup.searchBox(APP_URL);
			WaitUtil.waitForPageLoad(driver, 10);
			ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.1");
			driver.get(APP_URL);
			ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.1");
			securitygroup.userName(USERNAME);
			ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.1");
			securitygroup.passWord(PASSWORD);
			ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.1");
			securitygroup.loginButton();
			ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.1");
			securitygroup.click_titleMasters();
			  ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.1");
			  securitygroup.masterClick(MASTER_MODULE);
				ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.1");
				securitygroup.Create();
			  ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.1"); 
			

		}

		@Test(priority = 2)
		public void Step_4_1_2() throws Throwable {
			securitygroup.securityGroupName(SECURITYGROUP_NAME);
			securitygroup.securityGroupDesc(SECURITYGROUP_DESC);
			securitygroup.selModule(SELECT_MODULE);
			securitygroup.selPrivileges(USER_PRIVILEGES); 
			 ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.2");
			 securitygroup.createSubmit();
			 ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.2");
			 securitygroup.passWord(PASSWORD);
			 ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.2");
			 securitygroup.authenticateButton();
			 ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.2");
			 ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.2");

			
		}

		
		  @Test(priority = 3) 
		  public void Step_4_1_3() throws Throwable {
			  ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.3");
			  }
		  @Test(priority = 4)
			public void Step_4_1_4() throws Throwable {
			  securitygroup.clickActions(SECURITYGROUP_NAME);
				ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.4");
				
			}
		  
		  @Test(priority = 5)
			public void Step_4_1_5() throws Throwable {
			  securitygroup.clickApprove();
				ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.5");
				securitygroup.enterRemarks("Remarks");
				ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.5");
				securitygroup.clickReturn();
				ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.5");
				securitygroup.passWord(PASSWORD);
				ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.5");
				securitygroup.authenticateButton();
				ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.5");
				ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.5");
			}
		  
		  @Test(priority = 6)
			public void Step_4_1_6() throws Throwable {
			  securitygroup.clickEdit(SECURITYGROUP_NAME);
				ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.6");
				securitygroup.securityGroupName(EDIT_SECURITYGROUP_NAME);
				ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.6");
				securitygroup.clickUpdate();
				ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.6");
				securitygroup.passWord(PASSWORD);
				ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.6");
				securitygroup.authenticateButton();
				ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.6");
				ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.6");
			

			}
		  
		  @Test(priority = 7)
		          public void Step_4_1_7() throws Throwable {
			  securitygroup.clickActions(EDIT_SECURITYGROUP_NAME);
			  ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.7");	
			  securitygroup.clickApprove();
			  ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.7");
			  securitygroup.enterRemarks("Remarks");
			  ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.7");
			  securitygroup.clickApprove();
			  ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.7");
			  securitygroup.passWord(PASSWORD);
			  ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.7");
			  securitygroup.authenticateButton();
			  ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.7");
			  ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.7");
			  
			  
		  }
		  @Test(priority = 8) public void Step_4_1_8() throws Throwable {
			  securitygroup.clickActions(EDIT_SECURITYGROUP_NAME);
			  ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.8");
			  securitygroup.clickUpdate();
			  ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.8");
			  securitygroup.selPrivileges(EDIT_USER_PRIVILEGES);
			  ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.8");
			  securitygroup.clickUpdate();
			  ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.8");
			  securitygroup.passWord(PASSWORD);
			  ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.8");
			  securitygroup.authenticateButton();
			  ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.8");
			  ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.8");
			  
			  
		  }
		  
		  @Test(priority = 9) public void Step_4_1_9() throws Throwable {
				securitygroup.clickActions(EDIT_SECURITYGROUP_NAME);
				ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.9");
				securitygroup.clickApprove();
				ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.9");
				securitygroup.enterRemarks("Remarks");
				ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.9");
				securitygroup.clickApprove();
				ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.9");
				securitygroup.passWord(PASSWORD);
				ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.9");
				securitygroup.authenticateButton();
				ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.9");
				ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.9");
		  }
		  
		  
		  @Test(priority = 10) public void Step_4_1_10() throws Throwable {
			  securitygroup.click_profileDropdown();
			  ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.9"); 
			  securitygroup.logout(); 
			  ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.9");
			  
			  }


}
