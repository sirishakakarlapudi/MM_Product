
package testCasesForOQProjects;

import static configData.ChecklistData.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pageObjects.Checklist;
import testBase.BaseClass;
import utilities.DataProviders;
import utilities.ScreenshotUtil;
import utilities.WaitUtil;
import utilities.checklistData;

public class Checklist_TC extends BaseClass {
	Checklist bom;

	@BeforeClass
	public void setUp() throws Exception {

		ScreenshotUtil.loadTemplateForEndAppend(TEMPLATE_PATH, OUTPUT_PATH);
		ScreenshotUtil.updateHeaderCellText(ACTUALHEADER, EXPECTEDHEADER);

		browserOpen();
		bom = new Checklist(driver);

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
		bom.selChecklist(CHECKLIST_TYPE);
		bom.selCheckPointType(CHECK_POINT_TYPE);
		Thread.sleep(3000);
		if (CHECK_POINT_TYPE.equals("Options")) {
			bom.enterNumber(ENTER_NUMBER);
			String[] data = ENTER_DATA.split(",");
			for (int i = 0; i <= data.length; i++) {
				bom.enterData(i, data[i].trim());
			}

		}
	}

	@Test(priority = 3, dataProvider = "ChecklistData", dataProviderClass = DataProviders.class, singleThreaded = true)
	public void Step_4_1_2_PartB(checklistData checklist) throws Throwable {

		String description = checklist.get_Description();
		String reqSub = checklist.get_ReqSub();
		String button = checklist.get_Buttons();

		// Robust State Check: Determine if the current (latest) row is already a
		// container (Checked)
		boolean isContainerActive = false;
		java.util.List<org.openqa.selenium.WebElement> checkboxes = driver
				.findElements(org.openqa.selenium.By.xpath("//input[@formcontrolname='isSub']"));

		if (!checkboxes.isEmpty()) {
			isContainerActive = checkboxes.get(checkboxes.size() - 1).isSelected();
		}

		if (isContainerActive) {
			// --- SUB ITEM FLOW (The container is already open) ---
			bom.enterSubDescription(description);

			if ("Plus".equalsIgnoreCase(button)) {
				bom.plusButton();
			} else if ("Add".equalsIgnoreCase(button)) {
				bom.addButton();
			}

		} else {
			// --- MAIN ITEM FLOW (New row or existing non-container row) ---
			bom.enterDescription(description);

			// Check if this is a Container Header
			boolean isContainerHeader = "Yes".equalsIgnoreCase(reqSub);

			if (isContainerHeader) {
				// Enable Container Mode
				bom.selReqSub();

				// For Headers, we usually DON'T click Add (children will come next).
				// Only click if explicitly requested.
				if ("Add".equalsIgnoreCase(button)) {
					bom.addButton();
				}
			} else {
				// Standard Main Item
				// Click Add if explicitly requested OR if button is empty (Implicit Add)
				// EXCEPT for the last item ("Is De-dusting..."), where we don't want a trailing
				// row.
				boolean isLastItem = description != null
						&& description.contains("Is De-dusting performed for received containers/bags");

				if ("Add".equalsIgnoreCase(button) || ((button == null || button.trim().isEmpty()) && !isLastItem)) {
					bom.addButton();
				}
			}
		}
	

	
	  ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.2");
	  
	  bom.createSubmit();
	  ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.2");
	  bom.passWord(PASSWORD);
	  ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.2");
	  bom.authenticateButton();
	  ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.2");
	  ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.2");
	 

	 }

	
	  @Test(priority = 3) public void Step_4_1_3() throws Throwable {
	  ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.3"); 
	  }
	  
	  @Test(priority = 4) public void Step_4_1_4() throws Throwable {
	  bom.clickActions(CHECKLIST_TYPE);
	  ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.4");
	  
	  }
	  
	  @Test(priority = 5) public void Step_4_1_5() throws Throwable {
	  bom.clickReview(); ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.5");
	  bom.enterRemarks("Remarks");
	  ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.5"); bom.clickReturn();
	  ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.5");
	  bom.passWord(PASSWORD);
	  ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.5");
	  bom.authenticateButton();
	  ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.5");
	  ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.5"); 
	  }
	  
	  @Test(priority = 6) public void Step_4_1_6() throws Throwable {
	  bom.clickEdit(CHECKLIST_TYPE);
	  ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.6");
	  bom.selCheckPointType(EDIT_CHECK_POINT_TYPE);
		Thread.sleep(3000);
		if (EDIT_CHECK_POINT_TYPE.equals("Options")) {
			bom.enterNumber(ENTER_NUMBER);
			String[] data = ENTER_DATA.split(",");
			for (int i = 0; i <= data.length; i++) {
				bom.enterData(i, data[i].trim());
			}

		}
	  ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.6");
	  bom.clickUpdate();
	  ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.6");
	  bom.passWord(PASSWORD);
	  ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.6");
	  bom.authenticateButton();
	  ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.6");
	  ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.6");
	  
	  
	  }
	  
	  @Test(priority = 7) public void Step_4_1_7() throws Throwable {
	  bom.clickActions();
	  ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.7"); bom.clickReview();
	  ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.7");
	  bom.enterRemarks("Remarks");
	  ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.7"); bom.clickReview();
	  ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.7");
	  bom.passWord(PASSWORD);
	  ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.7");
	  bom.authenticateButton();
	  ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.7");
	  ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.7");
	  
	  
	  }
	  
	  
	  
	  @Test(priority = 8) public void Step_4_1_8() throws Throwable {
	  bom.clickActions(CHECKLIST_TYPE);
	  ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.7");
	  bom.clickApprove();
	  ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.7");
	  bom.enterRemarks("Remarks");
	  ScreenshotUtil.takeStepScreenshot("03 for step No.4.1.7");
	  bom.clickApprove();
	  ScreenshotUtil.takeStepScreenshot("04 for step No.4.1.7");
	  bom.passWord(PASSWORD);
	  ScreenshotUtil.takeStepScreenshot("05 for step No.4.1.7");
	  bom.authenticateButton();
	  ScreenshotUtil.takeStepScreenshot("06 for step No.4.1.7");
	  ScreenshotUtil.takeStepScreenshot("07 for step No.4.1.7");
	  
	  
	  }
	 

	@Test(priority = 9)
	public void Step_4_1_9() throws Throwable {
		bom.click_profileDropdown();
		ScreenshotUtil.takeStepScreenshot("01 for step No.4.1.8");
		bom.logout();
		ScreenshotUtil.takeStepScreenshot("02 for step No.4.1.8");

	}

}
