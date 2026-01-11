
package testCasesForOQProjects;

import static configData.ProductSpecificationData.*;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pageObjects.ProductSpecification;
import testBase.BaseClass;
import utilities.DataProviders;
import utilities.ScreenshotUtil;
import utilities.WaitUtil;
import utilities.productSpecificationData;

public class ProductSpecification_TC extends BaseClass {
	ProductSpecification productspecification;


	@BeforeClass
	public void setUp() throws Exception {

		ScreenshotUtil.loadTemplateForEndAppend(TEMPLATE_PATH, OUTPUT_PATH);
		ScreenshotUtil.updateHeaderCellText(ACTUALHEADER, EXPECTEDHEADER);

		browserOpen();
		productspecification = new ProductSpecification(driver);

	}

	@Test(priority = 1)
	public void Step_4_1_1() throws Throwable {
		driver.get("https://www.google.co.in/");
		WaitUtil.waitForVisible(driver, productspecification.getSearchBox(), 10);
		productspecification.searchBox(APP_URL);
		WaitUtil.waitForPageLoad(driver, 10);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.1");
		driver.get(APP_URL);
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.1");
		productspecification.userName(USERNAME);
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.1");
		productspecification.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.1");
		productspecification.loginButton();
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.1");
		productspecification.click_titleMasters();
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.1");
		productspecification.masterClick(MASTER_MODULE);
		ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.1");
		productspecification.masterClick(MASTER_SUB_MODULE);
		ScreenshotUtil.takeStepScreenshot("08 for step No.4.1.1");
		productspecification.Create();
		ScreenshotUtil.takeStepScreenshot("09 for step No.4.1.1");

	}
	
	@Test(priority = 2)
	public void Step_4_1_2_PartA() throws Throwable {
		productspecification.selProductCode(PRODUCT_CODE);
		productspecification.specificationNumber(SPECIFICATION_NUMBER);
		
		Thread.sleep(5000);
	
		
		
	}

	@Test(priority = 3, dataProvider = "ProductspecificationData", dataProviderClass = DataProviders.class, singleThreaded = true)
	public void Step_4_1_2_PartB(productSpecificationData productspec) throws Throwable {
		
	

		
		String nameofthetest=productspec.get_NameOfTheTest();
		String reqSub=productspec.get_ReqSub();
		String specificationLimit=productspec.get_SpecificationLimit();
		String validation=productspec.get_Validation();
		String specificationLimitMin=productspec.get_SpecificationLimitMin();
		String specificationLimitMax=productspec.get_SpecificationLimitMax();
		String UOM=productspec.get_UOM();
		String button=productspec.get_Buttons();
		
		
		
		productspecification.nameOfTheTest(nameofthetest);
		  if ("Yes".equalsIgnoreCase(reqSub)) {
			  productspecification.selReqSub();
			}
		  
		  if (specificationLimit != null && !specificationLimit.trim().isEmpty()) {
			  productspecification.specificaitionLimit(specificationLimit);
			}
		  if(validation != null && !validation.trim().isEmpty()) {
			  productspecification.selValidation(validation);
			  if("Yes".equalsIgnoreCase(validation.trim())) {
				  productspecification.selValidation(validation);
				  productspecification.specificaitionLimitMin(specificationLimitMin);
				  productspecification.specificaitionLimitMax(specificationLimitMax);
				  productspecification.selUOM(UOM);
			  }
				
                  
                  
		  }
			if (button != null && !button.trim().isEmpty()) {
				if ("Add".equalsIgnoreCase(button.trim())) {
					productspecification.addButton();
				}
				else if ("Plus".equalsIgnoreCase(button.trim())) {
					productspecification.plusButton();
				}

			}
		}
		  
		 
		
		
		

	    
	
		
	
	
	
	@Test(priority = 4)
	public void Step_4_1_2_PartC() throws Throwable {
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.2");
		productspecification.createSubmit();
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.2");
		productspecification.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.2");
		productspecification.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.2");
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.2");
	}

	@Test(priority = 3)
	public void Step_4_1_3() throws Throwable {
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.3");
	}

	@Test(priority = 4)
	public void Step_4_1_4() throws Throwable {
		productspecification.clickActions(SPECIFICATION_NUMBER, "1");
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.4");

	}

	@Test(priority = 5)
	public void Step_4_1_5() throws Throwable {
		productspecification.clickReview();
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.5");
		productspecification.enterRemarks("Remarks");
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.5");
		productspecification.clickReturn();
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.5");
		productspecification.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.5");
		productspecification.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.5");
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.5");
	}

	@Test(priority = 6)
	public void Step_4_1_6() throws Throwable {
		productspecification.clickEdit(SPECIFICATION_NUMBER, "1");
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.6");
		productspecification.specificationNumber(EDIT_SPECIFICATION_NUMBER);
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.6");
		productspecification.clickUpdate();
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.6");
		productspecification.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.6");
		productspecification.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.6");
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.6");

	}

	@Test(priority = 7)
	public void Step_4_1_7() throws Throwable {
		productspecification.clickActions(EDIT_SPECIFICATION_NUMBER, "1");
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.7");
		productspecification.clickReview();
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.7");
		productspecification.enterRemarks("Remarks");
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.7");
		productspecification.clickReview();
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.7");
		productspecification.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.7");
		productspecification.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.7");
		ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.7");

	}

	@Test(priority = 8)
	public void Step_4_1_8() throws Throwable {
		productspecification.clickActions(EDIT_SPECIFICATION_NUMBER, "1");
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.7");
		productspecification.clickApprove();
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.7");
		productspecification.enterRemarks("Remarks");
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.7");
		productspecification.clickApprove();
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.7");
		productspecification.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.7");
		productspecification.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.7");
		ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.7");

	}

	
	@Test(priority = 9)
	public void Step_4_1_9() throws Throwable {
		productspecification.click_profileDropdown();
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.9");
		productspecification.logout();
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.9");

	}

}
