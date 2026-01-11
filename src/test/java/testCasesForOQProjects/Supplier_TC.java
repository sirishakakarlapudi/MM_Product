
package testCasesForOQProjects;


	import static configData.SupplierData.*;

	import org.testng.annotations.BeforeClass;
	import org.testng.annotations.Test;

	import pageObjects.Supplier;
	import testBase.BaseClass;
	import utilities.ScreenshotUtil;
	import utilities.WaitUtil;

	public class Supplier_TC extends BaseClass {
		Supplier supplier;

		@BeforeClass
		public void setUp() throws Exception {

			ScreenshotUtil.loadTemplateForEndAppend(TEMPLATE_PATH, OUTPUT_PATH);
			ScreenshotUtil.updateHeaderCellText(ACTUALHEADER, EXPECTEDHEADER);

			browserOpen();
			supplier = new Supplier(driver);

		}

		@Test(priority = 1)
		public void Step_4_1_1() throws Throwable {
			driver.get("https://www.google.co.in/");
			WaitUtil.waitForVisible(driver, supplier.getSearchBox(), 10);
			supplier.searchBox(APP_URL);
			WaitUtil.waitForPageLoad(driver, 10);
			ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.1");
			driver.get(APP_URL);
			ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.1");
			supplier.userName(USERNAME);
			ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.1");
			supplier.passWord(PASSWORD);
			ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.1");
			supplier.loginButton();
			ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.1");
			supplier.click_titleMasters();
			  ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.1");
			  supplier.masterClick(MASTER_MODULE);
				ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.1");
				 supplier.masterClick(MASTER_SUB_MODULE);
					ScreenshotUtil.takeStepScreenshot("08 for step No.4.1.1");
				supplier.Create();
			  ScreenshotUtil.takeStepScreenshot("09 for step No.4.1.1"); 
			

		}

		@Test(priority = 2)
		public void Step_4_1_2() throws Throwable {
			supplier.supplierName(SUPPLIER_NAME);
			supplier.supplierAddress(SUPPLIER_ADDRESS);
			supplier.supplierState(SUPPLIER_STATE);
			supplier.supplierCity(SUPPLIER_CITY);
			supplier.supplierPincode(SUPPLIER_PINCODE);
			supplier.selSupplierRegion(SUPPLIER_REGION);
			
			
			 ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.2");
			 supplier.createSubmit();
			 ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.2");
			 supplier.passWord(PASSWORD);
			 ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.2");
			 supplier.authenticateButton();
			 ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.2");
			 ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.2");

			
		}

		
		  @Test(priority = 3) 
		  public void Step_4_1_3() throws Throwable {
			  ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.3");
			  }
		  @Test(priority = 4)
			public void Step_4_1_4() throws Throwable {
			  supplier.clickActions(SUPPLIER_NAME);
				ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.4");
				
			}
		  
		  @Test(priority = 5)
			public void Step_4_1_5() throws Throwable {
			  supplier.clickReview();
				ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.5");
				supplier.enterRemarks("Remarks");
				ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.5");
				supplier.clickReturn();
				ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.5");
				supplier.passWord(PASSWORD);
				ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.5");
				supplier.authenticateButton();
				ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.5");
				ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.5");
			}
		  
		  @Test(priority = 6)
			public void Step_4_1_6() throws Throwable {
			  supplier.clickEdit(SUPPLIER_NAME);
				ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.6");
				supplier.supplierName(EDIT_SUPPLIER_NAME);
				ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.6");
				supplier.clickUpdate();
				ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.6");
				supplier.passWord(PASSWORD);
				ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.6");
				supplier.authenticateButton();
				ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.6");
				ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.6");
			

			}
		  
		  @Test(priority = 7)
		          public void Step_4_1_7() throws Throwable {
			  supplier.clickActions(EDIT_SUPPLIER_NAME);
			  ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.7");	
			  supplier.clickReview();
			  ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.7");
			  supplier.enterRemarks("Remarks");
			  ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.7");
			  supplier.clickReview();
			  ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.7");
			  supplier.passWord(PASSWORD);
			  ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.7");
			  supplier.authenticateButton();
			  ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.7");
			  ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.7");
			  
			  
		  }
		  
		  
		  
		  @Test(priority = 8)
          public void Step_4_1_8() throws Throwable {
	  supplier.clickActions(EDIT_SUPPLIER_NAME);
	  ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.7");	
	  supplier.clickApprove();
	  ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.7");
	  supplier.enterRemarks("Remarks");
	  ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.7");
	  supplier.clickApprove();
	  ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.7");
	  supplier.passWord(PASSWORD);
	  ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.7");
	  supplier.authenticateButton();
	  ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.7");
	  ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.7");
	  
	  
  }
		  
		  @Test(priority = 9) public void Step_4_1_9() throws Throwable {
			  supplier.click_profileDropdown();
			  ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.8"); 
			  supplier.logout(); 
			  ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.8");
			  
			  }





	}


