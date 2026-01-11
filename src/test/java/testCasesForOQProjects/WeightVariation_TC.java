
package testCasesForOQProjects;


	import static configData.WeightVariationData.*;
import org.testng.annotations.BeforeClass;
	import org.testng.annotations.Test;

	import pageObjects.WeightVariation;
	import testBase.BaseClass;
	import utilities.ScreenshotUtil;
	import utilities.WaitUtil;

	public class WeightVariation_TC extends BaseClass {
		WeightVariation weightvariation;

		@BeforeClass
		public void setUp() throws Exception {

			ScreenshotUtil.loadTemplateForEndAppend(TEMPLATE_PATH, OUTPUT_PATH);
			ScreenshotUtil.updateHeaderCellText(ACTUALHEADER, EXPECTEDHEADER);

			browserOpen();
			weightvariation = new WeightVariation(driver);
			

		}

		@Test(priority = 1)
		public void Step_4_1_1() throws Throwable {
			driver.get("https://www.google.co.in/");
			WaitUtil.waitForVisible(driver, weightvariation.getSearchBox(), 10);
			weightvariation.searchBox(APP_URL);
			WaitUtil.waitForPageLoad(driver, 10);
			ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.1");
			driver.get(APP_URL);
			ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.1");
			weightvariation.userName(USERNAME);
			ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.1");
			weightvariation.passWord(PASSWORD);
			ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.1");
			weightvariation.loginButton();
			  ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.1");
			  weightvariation.click_titleMM();
			  ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.1");
			  weightvariation.masterClick(MASTER_MODULE);
				ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.1");
				weightvariation.Create();
			  ScreenshotUtil.takeStepScreenshot("09 for step No.4.1.1"); 
			

		}

		@Test(priority = 2)
		public void Step_4_1_2() throws Throwable {
			weightvariation.selBalanceCapacity(BALANCE_CAPACITY	);
			weightvariation.selWeightType(WEIGHT_TYPE);
			Thread.sleep(3000);
			
			
			
			
			String[] grossweightfrom = GROSS_WEIGHT_FROM.split(",");
			String[] grossweightto = GROSS_WEIGHT_TO.split(",");
			String[] allowedvariation = ALLOWED_VARIATION.split(",");
			

			
			for (int i = 0; i < allowedvariation.length; i++) {

				weightvariation.fromWeight(grossweightfrom[i].trim());
				weightvariation.toWeight(grossweightto[i].trim());
				weightvariation.allowedVariation(allowedvariation[i].trim());
                if (i < allowedvariation.length - 1) {
                	weightvariation.enterRow();
                }
			}
			ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.2");
			
			weightvariation.createSubmit();
			  ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.2");
			  weightvariation.passWord(PASSWORD);
			  ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.2");
			  weightvariation.authenticateButton();
			  ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.2");
			  ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.2");
			 

			
		}

		
		
		  @Test(priority = 3) 
		  public void Step_4_1_3() throws Throwable {
		  ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.3"); }
		  
		  @Test(priority = 4) 
		  public void Step_4_1_4() throws Throwable {
			  weightvariation.clickActions(BALANCE_CAPACITY);
		  ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.4");
		  
		  }
		  
		  @Test(priority = 5) 
		  public void Step_4_1_5() throws Throwable {
			  weightvariation.clickReview(); ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.5");
			  weightvariation.enterRemarks("Remarks");
		  ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.5"); 
		  weightvariation.clickReturn();
		  ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.5");
		  weightvariation.passWord(PASSWORD);
		  ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.5");
		  weightvariation.authenticateButton();
		  ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.5");
		  ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.5"); }
		  
		  @Test(priority = 6) 
		  public void Step_4_1_6() throws Throwable {
			  weightvariation.clickEdit(BALANCE_CAPACITY);
		  ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.6");
		  weightvariation.selWeightType(EDIT_WEIGHT_TYPE);
		  String[] packweight = PACK_WEIGHT.split(",");
			String[] minnetweight = MIN_WEIGHT.split(",");
			String[] maxnetweight = MAX_WEIGHT.split(",");
			
			for (int i = 0; i < packweight.length; i++) {

				weightvariation.minNetWeight(minnetweight[i].trim());
				weightvariation.maxNetWeight(maxnetweight[i].trim());
				weightvariation.packWeight(packweight[i].trim());
              if (i < packweight.length - 1) {
            	  weightvariation.enterRow();
              }
			}
		  
		  ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.6");
		  weightvariation.clickUpdate();
		  ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.6");
		  weightvariation.passWord(PASSWORD);
		  ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.6");
		  weightvariation.authenticateButton();
		  ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.6");
		  ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.6");
		  
		  
		  }
		  
		  @Test(priority = 7) public void Step_4_1_7() throws Throwable {
			  weightvariation.clickActions(BALANCE_CAPACITY);
		  ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.7"); 
		  weightvariation.clickReview();
		  ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.7");
		  weightvariation.enterRemarks("Remarks");
		  ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.7"); 
		  weightvariation.clickReview();
		  ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.7");
		  weightvariation.passWord(PASSWORD);
		  ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.7");
		  weightvariation.authenticateButton();
		  ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.7");
		  ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.7");
		  
		  
		  }
		  
		  
		  
		  @Test(priority = 8) public void Step_4_1_8() throws Throwable {
			  weightvariation.clickActions(BALANCE_CAPACITY);
		  ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.7");
		  weightvariation.clickApprove();
		  ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.7");
		  weightvariation.enterRemarks("Remarks");
		  ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.7");
		  weightvariation.clickApprove();
		  ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.7");
		  weightvariation.passWord(PASSWORD);
		  ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.7");
		  weightvariation.authenticateButton();
		  ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.7");
		  ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.7");
		  
		  
		  }
		 
		  @Test(priority = 9) public void Step_4_1_9() throws Throwable {
			  weightvariation.click_profileDropdown();
			  ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.8"); 
			  weightvariation.logout(); 
			  ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.8");
			  
			  }





	}


