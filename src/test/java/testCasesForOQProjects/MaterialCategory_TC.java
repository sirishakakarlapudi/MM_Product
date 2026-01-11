
package testCasesForOQProjects;


	import static configData.MaterialCategoryData.*;

	import org.testng.annotations.BeforeClass;
	import org.testng.annotations.Test;

	import pageObjects.MaterialCategory;
	import testBase.BaseClass;
	import utilities.ScreenshotUtil;
	import utilities.WaitUtil;

	public class MaterialCategory_TC extends BaseClass {
		MaterialCategory category;

		@BeforeClass
		public void setUp() throws Exception {

			ScreenshotUtil.loadTemplateForEndAppend(TEMPLATE_PATH, OUTPUT_PATH);
			ScreenshotUtil.updateHeaderCellText(ACTUALHEADER, EXPECTEDHEADER);

			browserOpen();
			category = new MaterialCategory(driver);

		}

		@Test(priority = 1)
		public void Step_4_1_1() throws Throwable {
			driver.get("https://www.google.co.in/");
			WaitUtil.waitForVisible(driver, category.getSearchBox(), 10);
			category.searchBox(APP_URL);
			WaitUtil.waitForPageLoad(driver, 10);
			ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.1");
			driver.get(APP_URL);
			ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.1");
			category.userName(USERNAME);
			ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.1");
			category.passWord(PASSWORD);
			ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.1");
			category.loginButton();
			  ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.1");
			category.click_titleMasters();
			  ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.1");
			  category.masterClick(MASTER_MODULE);
				ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.1");
				category.masterClick(MASTER_SUB_MODULE);
					ScreenshotUtil.takeStepScreenshot("08 for step No.4.1.1");
					category.Create();
			  ScreenshotUtil.takeStepScreenshot("09 for step No.4.1.1"); 
			

		}

		@Test(priority = 2)
		public void Step_4_1_2() throws Throwable {
			category.materialCategoryName(MATERIAL_CATEGORY_NAME);
			category.selSamplingPlan(SAMPLING_PLAN);
			category.selWeightVerificationPlan(WEIGHT_VERIFICATION_PLAN);
		
			
			 ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.2");
			 category.createSubmit();
			 ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.2");
			 category.passWord(PASSWORD);
			 ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.2");
			 category.authenticateButton();
			 ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.2");
			 ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.2");

			
		}

		
		  @Test(priority = 3) 
		  public void Step_4_1_3() throws Throwable {
			  ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.3");
			  }
		  @Test(priority = 4)
			public void Step_4_1_4() throws Throwable {
			  category.clickActions(MATERIAL_CATEGORY_NAME);
				ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.4");
				
			}
		  
		  @Test(priority = 5)
			public void Step_4_1_5() throws Throwable {
			  category.clickReview();
				ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.5");
				category.enterRemarks("Remarks");
				ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.5");
				category.clickReturn();
				ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.5");
				category.passWord(PASSWORD);
				ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.5");
				category.authenticateButton();
				ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.5");
				ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.5");
			}
		  
		  @Test(priority = 6)
			public void Step_4_1_6() throws Throwable {
			  category.clickEdit(MATERIAL_CATEGORY_NAME);
				ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.6");
				category.materialCategoryName(EDIT_MATERIAL_CATEGORY_NAME);
				ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.6");
				category.clickUpdate();
				ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.6");
				category.passWord(PASSWORD);
				ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.6");
				category.authenticateButton();
				ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.6");
				ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.6");
			

			}
		  
		  @Test(priority = 7)
		          public void Step_4_1_7() throws Throwable {
			  category.clickActions(EDIT_MATERIAL_CATEGORY_NAME);
			  ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.7");	
			  category.clickReview();
			  ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.7");
			  category.enterRemarks("Remarks");
			  ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.7");
			  category.clickReview();
			  ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.7");
			  category.passWord(PASSWORD);
			  ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.7");
			  category.authenticateButton();
			  ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.7");
			  ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.7");
			  
			  
		  }
		  
		  
		  
		  @Test(priority = 8)
          public void Step_4_1_8() throws Throwable {
			  category.clickActions(EDIT_MATERIAL_CATEGORY_NAME);
	  ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.7");	
	  category.clickApprove();
	  ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.7");
	  category.enterRemarks("Remarks");
	  ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.7");
	  category.clickApprove();
	  ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.7");
	  category.passWord(PASSWORD);
	  ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.7");
	  category.authenticateButton();
	  ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.7");
	  ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.7");
	  
	  
  }
		  
		  @Test(priority = 9) public void Step_4_1_9() throws Throwable {
			  category.click_profileDropdown();
			  ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.8"); 
			  category.logout(); 
			  ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.8");
			  
			  }





	}


