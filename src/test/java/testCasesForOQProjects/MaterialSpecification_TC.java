
package testCasesForOQProjects;

import static configData.MaterialSpecificationData.*;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pageObjects.MaterialSpecification;
import testBase.BaseClass;
import utilities.DataProviders;
import utilities.ScreenshotUtil;
import utilities.WaitUtil;

import utilities.materialSpecificationData;

public class MaterialSpecification_TC extends BaseClass {
	MaterialSpecification materialspecification;

	@BeforeClass
	public void setUp() throws Exception {

		ScreenshotUtil.loadTemplateForEndAppend(TEMPLATE_PATH, OUTPUT_PATH);
		ScreenshotUtil.updateHeaderCellText(ACTUALHEADER, EXPECTEDHEADER);

		browserOpen();
		materialspecification = new MaterialSpecification(driver);

	}

	@Test(priority = 1)
	public void Step_4_1_1() throws Throwable {
		driver.get("https://www.google.co.in/");
		WaitUtil.waitForVisible(driver, materialspecification.getSearchBox(), 10);
		materialspecification.searchBox(APP_URL);
		WaitUtil.waitForPageLoad(driver, 10);
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.1");
		driver.get(APP_URL);
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.1");
		materialspecification.userName(USERNAME);
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.1");
		materialspecification.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.1");
		materialspecification.loginButton();
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.1");
		materialspecification.click_titleMasters();
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.1");
		materialspecification.masterClick(MASTER_MODULE);
		ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.1");
		materialspecification.masterClick(MASTER_SUB_MODULE);
		ScreenshotUtil.takeStepScreenshot("08 for step No.4.1.1");
		materialspecification.Create();
		ScreenshotUtil.takeStepScreenshot("09 for step No.4.1.1");

	}

	@Test(priority = 2)
	public void Step_4_1_2_PartA() throws Throwable {
		materialspecification.selMaterialCode(MATERIAL_CODE);
		materialspecification.specificationNumber(SPECIFICATION_NUMBER);
		materialspecification.selRequestType(REQUEST_TYPE);
		Thread.sleep(5000);

	}

	@Test(priority = 3, dataProvider = "MaterialspecificationData", dataProviderClass = DataProviders.class, singleThreaded = true)
	public void Step_4_1_2_PartB(materialSpecificationData materialspec) throws Throwable {

		String nameofthetest = materialspec.get_NameOfTheTest();
		String reqSub = materialspec.get_ReqSub();
		String specificationLimit = materialspec.get_SpecificationLimit();
		String validation = materialspec.get_Validation();
		String specificationLimitMin = materialspec.get_SpecificationLimitMin();
		String specificationLimitMax = materialspec.get_SpecificationLimitMax();
		String UOM = materialspec.get_UOM();
		String button = materialspec.get_Buttons();

		materialspecification.nameOfTheTest(nameofthetest);
		if ("Yes".equalsIgnoreCase(reqSub)) {
			materialspecification.selReqSub();
		}

		if (specificationLimit != null && !specificationLimit.trim().isEmpty()) {
			materialspecification.specificaitionLimit(specificationLimit);
		}
		if (validation != null && !validation.trim().isEmpty()) {
			materialspecification.selValidation(validation);
			if ("Yes".equalsIgnoreCase(validation.trim())) {
				materialspecification.selValidation(validation);
				materialspecification.specificaitionLimitMin(specificationLimitMin);
				materialspecification.specificaitionLimitMax(specificationLimitMax);
				materialspecification.selUOM(UOM);
			}

		}
		if (button != null && !button.trim().isEmpty()) {
			if ("Add".equalsIgnoreCase(button.trim())) {
				materialspecification.addButton();
			} else if ("Plus".equalsIgnoreCase(button.trim())) {
				materialspecification.plusButton();
			}

		}
	}

	@Test(priority = 4)
	public void Step_4_1_2_PartC() throws Throwable {
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.2");
		materialspecification.createSubmit();
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.2");
		materialspecification.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.2");
		materialspecification.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.2");
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.2");
	}

	@Test(priority = 3)
	public void Step_4_1_3() throws Throwable {
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.3");
	}

	@Test(priority = 4)
	public void Step_4_1_4() throws Throwable {
		materialspecification.clickActions(SPECIFICATION_NUMBER, "1");
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.4");

	}

	@Test(priority = 5)
	public void Step_4_1_5() throws Throwable {
		materialspecification.clickReview();
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.5");
		materialspecification.enterRemarks("Remarks");
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.5");
		materialspecification.clickReturn();
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.5");
		materialspecification.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.5");
		materialspecification.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.5");
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.5");
	}

	@Test(priority = 6)
	public void Step_4_1_6() throws Throwable {
		materialspecification.clickEdit(SPECIFICATION_NUMBER, "1");
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.6");
		materialspecification.specificationNumber(EDIT_SPECIFICATION_NUMBER);
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.6");
		materialspecification.clickUpdate();
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.6");
		materialspecification.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.6");
		materialspecification.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.6");
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.6");

	}

	@Test(priority = 7)
	public void Step_4_1_7() throws Throwable {
		materialspecification.clickActions(EDIT_SPECIFICATION_NUMBER, "1");
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.7");
		materialspecification.clickReview();
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.7");
		materialspecification.enterRemarks("Remarks");
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.7");
		materialspecification.clickReview();
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.7");
		materialspecification.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.7");
		materialspecification.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.7");
		ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.7");

	}

	@Test(priority = 8)
	public void Step_4_1_8() throws Throwable {
		materialspecification.clickActions(EDIT_SPECIFICATION_NUMBER, "1");
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.7");
		materialspecification.clickApprove();
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.7");
		materialspecification.enterRemarks("Remarks");
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.7");
		materialspecification.clickApprove();
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.7");
		materialspecification.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.7");
		materialspecification.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.7");
		ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.7");

	}

	@Test(priority = 9)
	public void Step_4_1_9() throws Throwable {
		materialspecification.click_profileDropdown();
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.9");
		materialspecification.logout();
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.9");

	}

}
