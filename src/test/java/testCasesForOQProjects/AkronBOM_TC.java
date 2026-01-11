
package testCasesForOQProjects;


	import static configData.AkronBOMData.*;
import org.testng.annotations.BeforeClass;
	import org.testng.annotations.Test;

	import pageObjects.AkronBOM;
	import testBase.BaseClass;
	import utilities.ScreenshotUtil;
	import utilities.WaitUtil;

	public class AkronBOM_TC extends BaseClass {
		AkronBOM bom;

		@BeforeClass
		public void setUp() throws Exception {

			ScreenshotUtil.loadTemplateForEndAppend(TEMPLATE_PATH, OUTPUT_PATH);
			ScreenshotUtil.updateHeaderCellText(ACTUALHEADER, EXPECTEDHEADER);

			browserOpen();
			bom = new AkronBOM(driver);
			bom.setTableHeaders("Product Code");

		}

		@Test(priority = 1)
		public void Step_4_1_1() throws Throwable {
			driver.get("https://www.google.co.in/");
			WaitUtil.waitForVisible(driver, bom.getSearchBox(), 10);
			bom.searchBox(APP_URL);
			WaitUtil.waitForPageLoad(driver, 10);
			ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.1");
			driver.get(APP_URL);
			ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.1");
			bom.userName(USERNAME);
			ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.1");
			bom.passWord(PASSWORD);
			ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.1");
			bom.loginButton();
			  ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.1");
			  bom.click_titleMM();
			  ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.1");
			  bom.masterClick(MASTER_MODULE);
				ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.1");
				bom.Create();
			  ScreenshotUtil.takeStepScreenshot("09 for step No.4.1.1"); 
			

		}

		@Test(priority = 2)
		public void Step_4_1_2() throws Throwable {
			bom.selProductCode(PRODUCT_CODE	);
			bom.location(LOCATION);
			bom.batchSize(BATCH_SIZE);
			bom.selBatchSizeUOM(BATCH_SIZE_UOM);
			bom.yieldMinRange(YIELD_RANGE_MIN);
			bom.yieldMaxRange(YIELD_RANGE_MAX);
			bom.yieldTarget(YIELD_RANGE_TARGET);
			bom.checkStandardBom(STANDARD_BOM);
			
			
			
			String[] materialCodes = MATERIAL_CODE.split(",");
			String[] standardQuantity = STANDARD_QUANTITY.split(",");
			String[] tolerance = TOLERANCE.split(",");
			

			
			for (int i = 0; i < materialCodes.length; i++) {

				bom.selMaterialCode(materialCodes[i].trim());
                bom.standardQuantity(standardQuantity[i].trim());
                bom.tolerance(tolerance[i].trim());
                if (i < materialCodes.length - 1) {
                    bom.enterRow();
                }
			}
			ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.2");
			/*
			 * bom.createSubmit_editUpdate();
			 * ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.2");
			 * bom.passWord(PASSWORD);
			 * ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.2");
			 * bom.authenticateButton();
			 * ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.2");
			 * ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.2");
			 */

			
		}

		
		/*
		 * @Test(priority = 3) public void Step_4_1_3() throws Throwable {
		 * ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.3"); }
		 * 
		 * @Test(priority = 4) public void Step_4_1_4() throws Throwable {
		 * bom.clickActions(PRODUCT_CODE,"1");
		 * ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.4");
		 * 
		 * }
		 * 
		 * @Test(priority = 5) public void Step_4_1_5() throws Throwable {
		 * bom.clickReview(); ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.5");
		 * bom.enterRemarks("Remarks");
		 * ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.5"); bom.clickReturn();
		 * ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.5");
		 * bom.passWord(PASSWORD);
		 * ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.5");
		 * bom.authenticateButton();
		 * ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.5");
		 * ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.5"); }
		 * 
		 * @Test(priority = 6) public void Step_4_1_6() throws Throwable {
		 * bom.clickEdit(PRODUCT_CODE,"1");
		 * ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.6");
		 * bom.batchSize(EDIT_BATCH_SIZE);
		 * ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.6");
		 * bom.createSubmit_editUpdate();
		 * ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.6");
		 * bom.passWord(PASSWORD);
		 * ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.6");
		 * bom.authenticateButton();
		 * ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.6");
		 * ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.6");
		 * 
		 * 
		 * }
		 * 
		 * @Test(priority = 7) public void Step_4_1_7() throws Throwable {
		 * bom.clickActions(PRODUCT_CODE,"1");
		 * ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.7"); bom.clickReview();
		 * ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.7");
		 * bom.enterRemarks("Remarks");
		 * ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.7"); bom.clickReview();
		 * ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.7");
		 * bom.passWord(PASSWORD);
		 * ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.7");
		 * bom.authenticateButton();
		 * ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.7");
		 * ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.7");
		 * 
		 * 
		 * }
		 * 
		 * 
		 * 
		 * @Test(priority = 8) public void Step_4_1_8() throws Throwable {
		 * bom.clickActions(PRODUCT_CODE, "1");
		 * ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.7");
		 * bom.clickApprove();
		 * ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.7");
		 * bom.enterRemarks("Remarks");
		 * ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.7");
		 * bom.clickApprove();
		 * ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.7");
		 * bom.passWord(PASSWORD);
		 * ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.7");
		 * bom.authenticateButton();
		 * ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.7");
		 * ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.7");
		 * 
		 * 
		 * }
		 */
		  @Test(priority = 9) public void Step_4_1_9() throws Throwable {
			  bom.click_profileDropdown();
			  ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.8"); 
			  bom.logout(); 
			  ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.8");
			  
			  }





	}


