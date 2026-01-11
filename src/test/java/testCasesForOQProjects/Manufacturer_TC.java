
package testCasesForOQProjects;


	import static configData.ManufacturerData.*;

	import org.testng.annotations.BeforeClass;
	import org.testng.annotations.Test;

	import pageObjects.Manufacturer;
	import testBase.BaseClass;
	import utilities.ScreenshotUtil;
	import utilities.WaitUtil;

	public class Manufacturer_TC extends BaseClass {
		Manufacturer manufacturer;

		@BeforeClass
		public void setUp() throws Exception {

			ScreenshotUtil.loadTemplateForEndAppend(TEMPLATE_PATH, OUTPUT_PATH);
			ScreenshotUtil.updateHeaderCellText(ACTUALHEADER, EXPECTEDHEADER);

			browserOpen();
			manufacturer = new Manufacturer(driver);

		}

		@Test(priority = 1)
		public void Step_4_1_1() throws Throwable {
			driver.get("https://www.google.co.in/");
			WaitUtil.waitForVisible(driver, manufacturer.getSearchBox(), 10);
			manufacturer.searchBox(APP_URL);
			WaitUtil.waitForPageLoad(driver, 10);
			ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.1");
			driver.get(APP_URL);
			ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.1");
			manufacturer.userName(USERNAME);
			ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.1");
			manufacturer.passWord(PASSWORD);
			ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.1");
			manufacturer.loginButton();
			ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.1");
			manufacturer.click_titleMasters();
			  ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.1");
			  manufacturer.masterClick(MASTER_MODULE);
				ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.1");
				manufacturer.masterClick(MASTER_SUB_MODULE);
					ScreenshotUtil.takeStepScreenshot("08 for step No.4.1.1");
					manufacturer.Create();
			  ScreenshotUtil.takeStepScreenshot("09 for step No.4.1.1"); 
			

		}

		@Test(priority = 2)
		public void Step_4_1_2() throws Throwable {
			manufacturer.manufacturerName(MANUFACTURER_NAME);
			manufacturer.manufactureAddress(MANUFACTURER_ADDRESS);
			manufacturer.manufactureState(MANUFACTURER_STATE);
			manufacturer.manufactureCity(MANUFACTURER_CITY);
			manufacturer.manufacturePincode(MANUFACTURER_PINCODE);
			manufacturer.selManufactureRegion(MANUFACTURER_REGION);
			
			
			 ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.2");
			 manufacturer.createSubmit();
			 ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.2");
			 manufacturer.passWord(PASSWORD);
			 ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.2");
			 manufacturer.authenticateButton();
			 ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.2");
			 ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.2");

			
		}

		
		  @Test(priority = 3) 
		  public void Step_4_1_3() throws Throwable {
			  ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.3");
			  }
		  @Test(priority = 4)
			public void Step_4_1_4() throws Throwable {
			  manufacturer.clickActions(MANUFACTURER_NAME);
				ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.4");
				
			}
		  
		  @Test(priority = 5)
			public void Step_4_1_5() throws Throwable {
			  manufacturer.clickReview();
				ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.5");
				manufacturer.enterRemarks("Remarks");
				ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.5");
				manufacturer.clickReturn();
				ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.5");
				manufacturer.passWord(PASSWORD);
				ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.5");
				manufacturer.authenticateButton();
				ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.5");
				ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.5");
			}
		  
		  @Test(priority = 6)
			public void Step_4_1_6() throws Throwable {
			  manufacturer.clickEdit(MANUFACTURER_NAME);
				ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.6");
				manufacturer.manufacturerName(EDIT_MANUFACTURER_NAME);
				ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.6");
				manufacturer.clickUpdate();
				ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.6");
				manufacturer.passWord(PASSWORD);
				ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.6");
				manufacturer.authenticateButton();
				ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.6");
				ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.6");
			

			}
		  
		  @Test(priority = 7)
		          public void Step_4_1_7() throws Throwable {
			  manufacturer.clickActions(EDIT_MANUFACTURER_NAME);
			  ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.7");	
			  manufacturer.clickReview();
			  ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.7");
			  manufacturer.enterRemarks("Remarks");
			  ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.7");
			  manufacturer.clickReview();
			  ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.7");
			  manufacturer.passWord(PASSWORD);
			  ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.7");
			  manufacturer.authenticateButton();
			  ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.7");
			  ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.7");
			  
			  
		  }
		  
		  
		  
		  @Test(priority = 8)
          public void Step_4_1_8() throws Throwable {
			  manufacturer.clickActions(EDIT_MANUFACTURER_NAME);
	  ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.7");	
	  manufacturer.clickApprove();
	  ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.7");
	  manufacturer.enterRemarks("Remarks");
	  ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.7");
	  manufacturer.clickApprove();
	  ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.7");
	  manufacturer.passWord(PASSWORD);
	  ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.7");
	  manufacturer.authenticateButton();
	  ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.7");
	  ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.7");
	  
	  
  }
		  
		  @Test(priority = 9) public void Step_4_1_9() throws Throwable {
			  manufacturer.click_profileDropdown();
			  ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.8"); 
			  manufacturer.logout(); 
			  ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.8");
			  
			  }





	}


