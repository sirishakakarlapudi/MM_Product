
package testCasesForOQProjects;


	import static configData.AkronMaterialData.*;

	import org.testng.annotations.BeforeClass;
	import org.testng.annotations.Test;

	import pageObjects.Material;
	import testBase.BaseClass;
	import utilities.ScreenshotUtil;
	import utilities.WaitUtil;

	public class AkronMaterial_TC extends BaseClass {
		Material material;

		@BeforeClass
		public void setUp() throws Exception {

			ScreenshotUtil.loadTemplateForEndAppend(TEMPLATE_PATH, OUTPUT_PATH);
			ScreenshotUtil.updateHeaderCellText(ACTUALHEADER, EXPECTEDHEADER);

			browserOpen();
			material = new Material(driver);

		}

		@Test(priority = 1)
		public void Step_4_1_1() throws Throwable {
			driver.get("https://www.google.co.in/");
			WaitUtil.waitForVisible(driver, material.getSearchBox(), 10);
			material.searchBox(APP_URL);
			WaitUtil.waitForPageLoad(driver, 10);
			ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.1");
			driver.get(APP_URL);
			ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.1");
			material.userName(USERNAME);
			ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.1");
			material.passWord(PASSWORD);
			ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.1");
			material.loginButton();
			  ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.1");
			  material.click_titleMasters();
			  ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.1");
			  material.masterClick(MASTER_MODULE);
				ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.1");
				material.masterClick(MASTER_SUB_MODULE);
					ScreenshotUtil.takeStepScreenshot("08 for step No.4.1.1");
					material.Create();
			  ScreenshotUtil.takeStepScreenshot("09 for step No.4.1.1"); 
			

		}

		@Test(priority = 2)
		public void Step_4_1_2() throws Throwable {
			material.materialName(MATERIAL_NAME);
			material.materialCode(MATERIAL_CODE);
			material.selTypeOfMaterial(TYPE_OF_MATERIAL);
			material.selMaterialCategory(MATERIAL_CATEGORY);
			material.selUOM(UOM);
			material.selStorageLocation(STORAGE_LOCATION);
			material.checksamplingActivity(SAMPLING_ACTIVITY);
			material.checkdispensingActivity(DISPENSING_ACTIVITY);
		
			material.checkweightVerification(WEIGHT_VERIFICATION);
			if ("Yes".equalsIgnoreCase(SAMPLING_ACTIVITY) 
			        || "Yes".equalsIgnoreCase(DISPENSING_ACTIVITY)) {

			    material.checkcleaningAgent(CLEANING_AGENT);
			}

			material.selSupplier(SUPPLIER);
			ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.2");
			material.checkManufacturerNo();
			ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.2");
			material.selManufacturer(MANUFACTURER);
			ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.2");
			
			material.clickSaveDetails();
			ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.2");
			material.btnManufacturerNo();
			ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.2");
			 material.createSubmit();
			 ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.2");
			 material.passWord(PASSWORD);
			 ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.2");
			 material.authenticateButton();
			 ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.2");
			 ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.2");

			
		}

		
		  @Test(priority = 3) 
		  public void Step_4_1_3() throws Throwable {
			  ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.3");
			  }
		  @Test(priority = 4)
			public void Step_4_1_4() throws Throwable {
			  material.clickActions(MATERIAL_NAME);
				ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.4");
				
			}
		  
		  @Test(priority = 5)
			public void Step_4_1_5() throws Throwable {
			  material.clickReview();
				ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.5");
				material.enterRemarks("Remarks");
				ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.5");
				material.clickReturn();
				ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.5");
				material.passWord(PASSWORD);
				ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.5");
				material.authenticateButton();
				ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.5");
				ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.5");
			}
		  
		  @Test(priority = 6)
			public void Step_4_1_6() throws Throwable {
			  material.clickEdit(MATERIAL_NAME);
				ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.6");
				material.materialName(EDIT_MATERIAL_NAME);
				ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.6");
				material.clickUpdate();
				ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.6");
				material.passWord(PASSWORD);
				ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.6");
				material.authenticateButton();
				ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.6");
				ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.6");
			

			}
		  
		  @Test(priority = 7)
		          public void Step_4_1_7() throws Throwable {
			  material.clickActions(EDIT_MATERIAL_NAME);
			  ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.7");	
			  material.clickReview();
			  ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.7");
			  material.enterRemarks("Remarks");
			  ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.7");
			  material.clickReview();
			  ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.7");
			  material.passWord(PASSWORD);
			  ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.7");
			  material.authenticateButton();
			  ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.7");
			  ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.7");
			  
			  
		  }
		  
		  
		  
		  @Test(priority = 8)
          public void Step_4_1_8() throws Throwable {
			  material.clickActions(EDIT_MATERIAL_NAME);
	  ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.7");	
	  material.clickApprove();
	  ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.7");
	  material.enterRemarks("Remarks");
	  ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.7");
	  material.clickApprove();
	  ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.7");
	  material.passWord(PASSWORD);
	  ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.7");
	  material.authenticateButton();
	  ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.7");
	  ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.7");
	  
	  
  }
		  
		  @Test(priority = 9) public void Step_4_1_9() throws Throwable {
			  material.click_profileDropdown();
			  ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.8"); 
			  material.logout(); 
			  ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.8");
			  
			  }





	}


