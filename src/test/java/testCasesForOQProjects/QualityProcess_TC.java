package testCasesForOQProjects;

import static configData.MaterialInwardForSolidData.CAPTURE_MATERIAL_INWARD_PDF;
import static configData.MaterialInwardForSolidData.CLICK_MATERIAL_INWARD_VIEW;
import static configData.MaterialInwardForSolidData.DOWNLOAD_MATERIALINWARD_PDF;
import static configData.MaterialInwardForSolidData.TABLE_SEARCH_VALUES;
import static configData.QualityProcessData.*;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pageObjects.QualityProcess;
import testBase.BaseClass;
import utilities.ScreenshotUtil;

public class QualityProcess_TC extends BaseClass {
	QualityProcess qualityprocess;

	@BeforeClass
	public void setUp() throws Exception {

		ScreenshotUtil.loadTemplateForEndAppend(TEMPLATE_PATH, OUTPUT_PATH);
		ScreenshotUtil.updateHeaderCellText(ACTUALHEADER, EXPECTEDHEADER);

		browserOpen();
		qualityprocess = new QualityProcess(driver);

		qualityprocess.setTableHeaders(TABLE_HEADERS);

	}

	@Test(priority = 1)
	public void Step_4_1_1() throws Throwable {

		driver.get(APP_URL);
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.1");
		qualityprocess.userName(USERNAME);
		ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.1");
		qualityprocess.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.1");
		qualityprocess.loginButton();
		ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.1");
		qualityprocess.click_titleMM();
		ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.1");
		qualityprocess.masterClick(MASTER_MODULE);
		ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.1");
		qualityprocess.masterClick(SUB_MASTER_MODULE);
		ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.1");
		ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.1");
		
	}

	@Test(priority = 2)
	public void Step_4_1_2() throws Throwable {
		qualityprocess.clickActions(TABLE_SEARCH_VALUES);
		ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.1");
		qualityprocess.clickAssignARNumber();
		ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.1");
		qualityprocess.enterARNO(ASSIGN_AR_NUMBER);
		ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.1");
		qualityprocess.clickSubmit();
		ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.1");
		qualityprocess.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.1");
		qualityprocess.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.1");
		ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.1");

	}

	@Test(priority = 3)
	public void Step_4_1_3() throws Throwable {
		qualityprocess.clickActions(TABLE_SEARCH_VALUES);;
		ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.1");
		boolean samplingStartButtonisDisplayed = qualityprocess.samplingStartButtonisDisplayed();
		if (samplingStartButtonisDisplayed == true) {

			qualityprocess.clickSamplingStart();
			ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.1");
			System.out.println("Clicked in actions");
			qualityprocess.clickSamplingStart();
			System.out.println("Clicked in pop up");
			ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.1");
			qualityprocess.enterSampleQuantity(SAMPLE_QUANTITY);
			boolean packNumbersisDisplayed = qualityprocess.packNumbersisDisplayed();
			if (packNumbersisDisplayed == true) {
				String[] packnumbers = CHECK_PACK_NUMBERS.split(",");
				qualityprocess.checkPackNumbersBulk(packnumbers);
			}
			qualityprocess.selSamplingRoomId(SAMPLING_ROOM_ID);
			ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.1");
			boolean weighingBalanceIDisDisplayed = qualityprocess.packNumbersisDisplayed();
			if (weighingBalanceIDisDisplayed == true) {
				qualityprocess.selWeighingBalanceId(WEIGHING_BALANCE_ID);
				ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.1");
			}

			qualityprocess.createSubmit();
			ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.1");
			qualityprocess.passWord(PASSWORD);
			ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.1");
			qualityprocess.authenticateButton();
			ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.1");
			ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.1");
			
			if (CLICK_MATERIAL_SAMPLING_VIEW.equalsIgnoreCase("yes")) {
				qualityprocess.clickView(TABLE_SEARCH_VALUES);
				ScreenshotUtil.takeStepScreenshot("15 for step No.4.1.2");
				boolean pdfisDisplayed=qualityprocess.packNumbersisDisplayed();
				if (DOWNLOAD_MATERIAL_SAMPLING_PDF.equalsIgnoreCase("yes") && pdfisDisplayed == true) {
					qualityprocess.click_PDF();
					ScreenshotUtil.takeStepScreenshot("18 for step No.4.1.2");
					if (CAPTURE_MATERIAL_SAMPLING_PDF.equalsIgnoreCase("yes")) {
						utilities.PDFUtil.openAndCapturePDF(driver, downloadPath, "Material Inward report.pdf",
								"19 for step No.4.1.2");
					}
				}
				qualityprocess.waitForLoading();
				qualityprocess.clickBack();
			}
		}
	}
	
	
	@Test(priority = 4)
	public void Step_4_1_4() throws Throwable {
		qualityprocess.clickActions(TABLE_SEARCH_VALUES);;
		ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.1");
		
		Thread.sleep(10000);
		
		qualityprocess.enterRemarks("Approved");
		if(MATERIAL_APPROVE_OR_REJECT.equalsIgnoreCase("approve")) {
			qualityprocess.clickApprove();
		}
		else {
			qualityprocess.clickReject();
		}
		
		qualityprocess.passWord(PASSWORD);
		ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.1");
		qualityprocess.authenticateButton();
		ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.1");
		ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.1");
		qualityprocess.waitForLoading();
		
		
		
	}

}