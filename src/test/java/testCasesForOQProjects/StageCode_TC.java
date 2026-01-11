
package testCasesForOQProjects;


	import static configData.StageCodeData.*;

	import org.testng.annotations.BeforeClass;
	import org.testng.annotations.Test;

	import pageObjects.StageCode;
	import testBase.BaseClass;
	import utilities.ScreenshotUtil;
	import utilities.WaitUtil;

	public class StageCode_TC extends BaseClass {
		StageCode stage;

		@BeforeClass
		public void setUp() throws Exception {

			ScreenshotUtil.loadTemplateForEndAppend(TEMPLATE_PATH, OUTPUT_PATH);
			ScreenshotUtil.updateHeaderCellText(ACTUALHEADER, EXPECTEDHEADER);

			browserOpen();
			stage = new StageCode(driver);

		}

		@Test(priority = 1)
		public void Step_4_1_1() throws Throwable {
			driver.get("https://www.google.co.in/");
			WaitUtil.waitForVisible(driver, stage.getSearchBox(), 10);
			stage.searchBox(APP_URL);
			WaitUtil.waitForPageLoad(driver, 10);
			ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.1");
			driver.get(APP_URL);
			ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.1");
			stage.userName(USERNAME);
			ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.1");
			stage.passWord(PASSWORD);
			ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.1");
			stage.loginButton();
			  ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.1");
			  stage.click_titleMasters();
			  ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.1");
			  stage.masterClick(MASTER_MODULE);
				ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.1");
				stage.masterClick(MASTER_SUB_MODULE);
					ScreenshotUtil.takeStepScreenshot("08 for step No.4.1.1");
					stage.Create();
			  ScreenshotUtil.takeStepScreenshot("09 for step No.4.1.1"); 
			

		}

	@Test(priority = 2)
		public void Step_4_1_2() throws Throwable {
			stage.stageCode(STAGE_CODE);
			
			stage.selUOM(UOM);
			stage.stageCodeDesc(STAGE_DESCRIPTION);
			stage.selStorageLocation(STORAGE_LOCATION);
			stage.checksamplingActivity(SAMPLING_ACTIVITY);
			stage.checkdispensingActivity(DISPENSING_ACTIVITY);
			
			if ("Yes".equalsIgnoreCase(SAMPLING_ACTIVITY) 
			        || "Yes".equalsIgnoreCase(DISPENSING_ACTIVITY)) {

				stage.checkcleaningAgent(CLEANING_AGENT);
			}

			
			
			;
			ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.2");
			stage.createSubmit();
			 ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.2");
			 stage.passWord(PASSWORD);
			 ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.2");
			 stage.authenticateButton();
			 ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.2");
			 ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.2");

			
		}

		
		  @Test(priority = 3) 
		  public void Step_4_1_3() throws Throwable {
			  ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.3");
			  }
		  @Test(priority = 4)
			public void Step_4_1_4() throws Throwable {
			  stage.clickActionsForPendig(STAGE_CODE);
				ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.4");
				
			}
		  
		  @Test(priority = 5)
			public void Step_4_1_5() throws Throwable {
			  stage.clickCreateReview();
				ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.5");
				stage.enterRemarks("Remarks");
				ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.5");
				stage.clickReturn();
				ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.5");
				stage.passWord(PASSWORD);
				ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.5");
				stage.authenticateButton();
				ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.5");
				ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.5");
			}
		  
		  @Test(priority = 6)
			public void Step_4_1_6() throws Throwable {
			  stage.clickEditForPending(STAGE_CODE);
				ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.6");
				stage.stageCode(EDIT_STAGE_CODE);
				ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.6");
				stage.clickUpdate();
				ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.6");
				stage.passWord(PASSWORD);
				ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.6");
				stage.authenticateButton();
				ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.6");
				ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.6");
			

			}
		  
		  @Test(priority = 7)
		          public void Step_4_1_7() throws Throwable {
			  stage.clickActionsForPendig(EDIT_STAGE_CODE);
			  ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.7");	
			  stage.clickCreateReview();
			  ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.7");
			  stage.enterRemarks("Remarks");
			  ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.7");
			  stage.clickReview();
			  ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.7");
			  stage.passWord(PASSWORD);
			  ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.7");
			  stage.authenticateButton();
			  ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.7");
			  ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.7");
			  
			  
		  }
		  
		  
		  
		  @Test(priority = 8)
          public void Step_4_1_8() throws Throwable {
			  stage.clickActionsForPendig(EDIT_STAGE_CODE);
	  ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.7");	
	  stage.clickCreateApprove();
	  ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.7");
	  stage.enterRemarks("Remarks");
	  ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.7");
	  stage.clickApprove();
	  ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.7");
	  stage.passWord(PASSWORD);
	  ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.7");
	  stage.authenticateButton();
	  ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.7");
	  ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.7");
	  
	  
  }
		  @Test(priority = 9)
          public void Step_4_1_9() throws Throwable {
			  stage.clickActionsForActive(EDIT_STAGE_CODE);
			          ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.9");
			          stage.clickInactive();
			          ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.9");
			          stage.enterRemarks("Remarks");
			          ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.9");
			          stage.clickInactive();
			          ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.9");
			          stage.passWord(PASSWORD);
			          ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.9");
			          stage.authenticateButton();
			          ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.9");
			          
			  
		  }
		  @Test(priority = 10)
          public void Step_4_1_10() throws Throwable {
			  stage.clickActionsForPendig(EDIT_STAGE_CODE);
			  ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.10");
			  stage.clickInactiveReview();
			  ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.10");
			  stage.enterRemarks("Remarks");
			  ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.10");
			  stage.clickReject();
			  ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.10");
			  stage.passWord(PASSWORD);
			  ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.10");
			  stage.authenticateButton();
			  ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.10");
			  
		  }
		  
		  @Test(priority = 11)
          public void Step_4_1_11() throws Throwable {
			  stage.clickActionsForActive(EDIT_STAGE_CODE);
	          ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.11");
	          stage.clickInactive();
	          ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.11");
	          stage.enterRemarks("Remarks");
	          ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.11");
	          stage.clickInactive();
	          ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.11");
	          stage.passWord(PASSWORD);
	          ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.11");
	          stage.authenticateButton();
	          ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.11");
	          
			  
		  }
		  
		  @Test(priority = 12)
          public void Step_4_1_12() throws Throwable {
			  stage.clickActionsForPendig(EDIT_STAGE_CODE);
			  ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.12");
			  stage.clickInactiveReview();
			  ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.12");
			  stage.enterRemarks("Remarks");
			  ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.12");
			  stage.clickReview();
			  ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.12");
			  stage.passWord(PASSWORD);
			  ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.12");
			  stage.authenticateButton();
			  ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.12");
			  
		  }
		  
		  @Test(priority = 13)
          public void Step_4_1_13() throws Throwable {
			  stage.clickActionsForPendig(EDIT_STAGE_CODE);
			  ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.13");
			  stage.clickInactiveApprove();
			  ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.13");
			  stage.enterRemarks("Remarks");
			  ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.13");
			  stage.clickApprove();
			  ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.13");
			  stage.passWord(PASSWORD);
			  ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.13");
			  stage.authenticateButton();
			  ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.13");
			  
		  }
		  
		  
		  
		  @Test(priority = 14)
          public void Step_4_1_14() throws Throwable {
			  stage.clickActionsForPendig(EDIT_STAGE_CODE);
			  ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.14");
			 stage.clickActive();
			 ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.14");
			 stage.enterRemarks("Remarks");
			 ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.14");
			 stage.clickActive();
			 ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.14");
			 stage.passWord(PASSWORD);
			 ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.14");
			 stage.authenticateButton();
			 ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.14");
			 
			  
		  }
		  @Test(priority = 15)
          public void Step_4_1_15() throws Throwable {
			  stage.clickActionsForPendig(EDIT_STAGE_CODE);
			  ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.15");
			  stage.clickCreateReview();
			  ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.15");
			  stage.enterRemarks("Remarks");
			  ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.15");
			  stage.clickReview();
			  ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.15");
			  stage.passWord(PASSWORD);
			  ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.15");
			  stage.authenticateButton();
			  ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.15");
			  
			  
			  
		  }
		  @Test(priority = 16)
          public void Step_4_1_16() throws Throwable {
			  stage.clickActionsForPendig(EDIT_STAGE_CODE);
			  ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.16");
			     stage.clickCreateApprove();
			     ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.16");
			     stage.enterRemarks("Remarks");
			     ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.16");
			     stage.clickApprove();
			     ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.16");
			     stage.passWord(PASSWORD);
			     ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.16");
			     stage.authenticateButton();
			     ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.16");
			     
			  
		  }
		  
		  
		  
		  
		  @Test(priority =17 ) public void Step_4_1_17() throws Throwable {
			  stage.click_profileDropdown();
			  ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.17"); 
			  stage.logout(); 
			  ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.17");
			  
			  }





	}


