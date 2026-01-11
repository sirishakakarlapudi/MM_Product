
package testCasesForOQProjects;

import static configData.AkronQuickProductData.*;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pageObjects.AkronQuickProduct;
import testBase.BaseClass;
import utilities.ScreenshotUtil;
import utilities.WaitUtil;

public class AkronQuickProduct_TC extends BaseClass {
	AkronQuickProduct product;

	@BeforeClass
	public void setUp() throws Exception {

		ScreenshotUtil.loadTemplateForEndAppend(TEMPLATE_PATH, OUTPUT_PATH);
		ScreenshotUtil.updateHeaderCellText(ACTUALHEADER, EXPECTEDHEADER);

		browserOpen();
		product = new AkronQuickProduct(driver);

	}

	@Test(priority = 1)
	public void Step_4_1_1() throws Throwable {
		driver.get("https://www.google.co.in/");
		WaitUtil.waitForVisible(driver, product.getSearchBox(), 10);
		product.searchBox(APP_URL);
		WaitUtil.waitForPageLoad(driver, 10);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.1");
		driver.get(APP_URL);
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.1");
		product.userName(USERNAME);
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.1");
		product.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.1");
		product.loginButton();
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.1");
		product.click_titleMasters();
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.1");
		product.masterClick(MASTER_MODULE);
		ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.1");
		product.masterClick(MASTER_SUB_MODULE);
		ScreenshotUtil.takeStepScreenshot("08 for step No.4.1.1");
		product.Create();
		ScreenshotUtil.takeStepScreenshot("09 for step No.4.1.1");

	}

	@Test(priority = 2)
	public void Step_4_1_2() throws Throwable {
		product.product(PRODUCT);
		product.productCode(PRODUCT_CODE);
		product.productDesc(PRODUCT_DESCRIPTION);
		product.selStorageLocation(STORAGE_LOCATION);
		product.checksamplingActivity(SAMPLING_ACTIVITY);
		
		if ("Yes".equalsIgnoreCase(SAMPLING_ACTIVITY)) {
			product.checkcleaningAgent(CLEANING_AGENT);
		}
		
		
		String[] ndcNumbers = NDC_NUMBER.split(",");
		String[] packSizes = PACK_SIZE.split(",");
		String[] ndcDescriptions = NDC_DESCRIPTION.split(",");
		String[] uoms = UOM.split(",");

		
		for (int i = 0; i < ndcNumbers.length; i++) {

		    product.ndcNumber(ndcNumbers[i].trim());

		    String uomToUse = (uoms.length == 1) 
		                        ? uoms[0].trim()
		                        : uoms[i].trim();
		    product.selUOM(uomToUse);

		    product.packSize(packSizes[i].trim());

		  
		    if (ndcDescriptions.length > i && !ndcDescriptions[i].trim().isEmpty()) {
		        product.ndcDesc(ndcDescriptions[i].trim());
		    }

		    if (i < ndcNumbers.length - 1) {
		        product.enterRow();
		    }
		}



		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.2");
		product.createSubmit();
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.2");
		product.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.2");
		product.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.2");
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.2");

	}

	@Test(priority = 3)
	public void Step_4_1_3() throws Throwable {
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.3");
	}

	@Test(priority = 4)
	public void Step_4_1_4() throws Throwable {
		product.clickActions(PRODUCT);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.4");

	}

	@Test(priority = 5)
	public void Step_4_1_5() throws Throwable {
		product.clickReview();
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.5");
		product.enterRemarks("Remarks");
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.5");
		product.clickReturn();
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.5");
		product.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.5");
		product.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.5");
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.5");
	}

	@Test(priority = 6)
	public void Step_4_1_6() throws Throwable {
		product.clickEdit(PRODUCT);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.6");
		product.product(EDIT_PRODUCT);
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.6");
		product.clickUpdate();
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.6");
		product.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.6");
		product.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.6");
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.6");

	}

	@Test(priority = 7)
	public void Step_4_1_7() throws Throwable {
		product.clickActions(EDIT_PRODUCT);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.7");
		product.clickReview();
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.7");
		product.enterRemarks("Remarks");
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.7");
		product.clickReview();
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.7");
		product.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.7");
		product.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.7");
		ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.7");

	}

	@Test(priority = 8)
	public void Step_4_1_8() throws Throwable {
		product.clickActions(EDIT_PRODUCT);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.7");
		product.clickApprove();
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.7");
		product.enterRemarks("Remarks");
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.7");
		product.clickApprove();
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.7");
		product.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.7");
		product.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.7");
		ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.7");

	}

	
	@Test(priority = 9)
	public void Step_4_1_9() throws Throwable {
		product.click_profileDropdown();
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.9");
		product.logout();
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.9");

	}

}
