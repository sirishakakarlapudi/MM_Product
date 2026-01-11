
package testCasesForOQProjects;

import static configData.ProductData.*;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pageObjects.Product;
import testBase.BaseClass;
import utilities.ScreenshotUtil;
import utilities.WaitUtil;

public class Product_TC extends BaseClass {
	Product product;

	@BeforeClass
	public void setUp() throws Exception {

		ScreenshotUtil.loadTemplateForEndAppend(TEMPLATE_PATH, OUTPUT_PATH);
		ScreenshotUtil.updateHeaderCellText(ACTUALHEADER, EXPECTEDHEADER);

		browserOpen();
		product = new Product(driver);

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

		product.productDesc(PRODUCT_DESCRIPTION);
		String[] routecode = ROUTE_CODE.split(",");

		for (int i = 0; i < routecode.length; i++) {
		    String sc = routecode[i].trim();

		    product.selRouteCode(sc);
		    if (i < routecode.length - 1) {
		    	product.clickAddButton();
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
		product.clickActionsForPendig(PRODUCT);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.4");

	}

	@Test(priority = 5)
	public void Step_4_1_5() throws Throwable {
		product.clickCreateReview();
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
		product.clickEditForPending(PRODUCT);
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
		product.clickActionsForPendig(EDIT_PRODUCT);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.7");
		product.clickCreateReview();
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
		product.clickActionsForPendig(EDIT_PRODUCT);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.7");
		product.clickCreateApprove();
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
		product.clickActionsForActive(EDIT_PRODUCT);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.9");
		product.clickInactive();
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.9");
		product.enterRemarks("Remarks");
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.9");
		product.clickInactive();
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.9");
		product.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.9");
		product.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.9");

	}

	@Test(priority = 10)
	public void Step_4_1_10() throws Throwable {
		product.clickActionsForPendig(EDIT_PRODUCT);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.10");
		product.clickInactiveReview();
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.10");
		product.enterRemarks("Remarks");
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.10");
		product.clickReject();
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.10");
		product.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.10");
		product.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.10");

	}

	@Test(priority = 11)
	public void Step_4_1_11() throws Throwable {
		product.clickActionsForActive(EDIT_PRODUCT);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.11");
		product.clickInactive();
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.11");
		product.enterRemarks("Remarks");
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.11");
		product.createSubmit();
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.11");
		product.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.11");
		product.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.11");

	}

	@Test(priority = 12)
	public void Step_4_1_12() throws Throwable {
		product.clickActionsForPendig(EDIT_PRODUCT);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.12");
		product.clickInactiveReview();
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.12");
		product.enterRemarks("Remarks");
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.12");
		product.clickReview();
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.12");
		product.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.12");
		product.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.12");

	}

	@Test(priority = 13)
	public void Step_4_1_13() throws Throwable {
		product.clickActionsForPendig(EDIT_PRODUCT);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.13");
		product.clickInactiveApprove();
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.13");
		product.enterRemarks("Remarks");
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.13");
		product.clickApprove();
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.13");
		product.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.13");
		product.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.13");

	}

	@Test(priority = 14)
	public void Step_4_1_14() throws Throwable {
		product.clickActionsForPendig(EDIT_PRODUCT);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.14");
		product.clickActive();
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.14");
		String[] routecode = ACTIVE_ROUTE_CODE.split(",");

		for (int i = 0; i < routecode.length; i++) {
		    String sc = routecode[i].trim();

		    product.selRouteCode(sc);
		    if (i < routecode.length - 1) {
		    	product.clickAddButton();
		    }
		}
		product.enterRemarks("Remarks");
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.14");
		product.clickActive();
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.14");
		product.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.14");
		product.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.14");

	}

	@Test(priority = 15)
	public void Step_4_1_15() throws Throwable {
		product.clickActionsForPendig(EDIT_PRODUCT);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.15");
		product.clickCreateReview();
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.15");
		product.enterRemarks("Remarks");
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.15");
		product.clickReview();
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.15");
		product.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.15");
		product.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.15");

	}

	@Test(priority = 16)
	public void Step_4_1_16() throws Throwable {
		product.clickActionsForPendig(EDIT_PRODUCT);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.16");
		product.clickCreateApprove();
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.16");
		product.enterRemarks("Remarks");
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.16");
		product.clickApprove();
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.16");
		product.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.16");
		product.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.16");

	}

	@Test(priority = 17)
	public void Step_4_1_17() throws Throwable {
		product.clickActionsForActive(EDIT_PRODUCT);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.17");
		product.clickAddRoute();
		product.clickAddButton();
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.17");

		String[] routecode = EDIT_ROUTE_CODE.split(",");

		for (int i = 0; i < routecode.length; i++) {
		    String sc = routecode[i].trim();

		    product.selRouteCode(sc);
		    if (i < routecode.length - 1) {
		    	product.clickAddButton();
		    }
		}

		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.17");
		product.clickUpdate();
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.17");
		product.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.17");
		product.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.17");

	}

	@Test(priority = 19)
	public void Step_4_1_19() throws Throwable {
		product.clickActionsForPendig(EDIT_PRODUCT);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.19");
		product.clickCreateReview();
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.19");
		product.enterRemarks("Remarks");
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.19");
		product.clickReview();
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.19");
		product.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.19");
		product.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.19");

	}

	@Test(priority = 20)
	public void Step_4_1_20() throws Throwable {
		product.clickActionsForPendig(EDIT_PRODUCT);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.19");
		product.clickCreateApprove();
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.19");
		product.enterRemarks("Remarks");
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.19");
		product.clickApprove();
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.19");
		product.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.19");
		product.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.19");

	}

	@Test(priority = 21)
	public void Step_4_1_21() throws Throwable {
		product.click_profileDropdown();
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.17");
		product.logout();
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.17");

	}

}
