package testCasesForOQProjects;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageObjects.BasePage;
import testBase.BaseClass;
import utilities.DatabaseBackupUtil;
import utilities.ScreenshotUtil;
import utilities.SoftAssertionUtil;

/**
 * Standardized Base Test Case for OQ Modules. This class contains all the
 * common navigation, login, and multi-user workflow logic shared across
 * Manufacturer, Supplier, Material, Product Specification, etc.
 */
public abstract class OQBaseModule_TC extends BaseClass {

	protected BasePage pageObject;
	protected SoftAssertionUtil sa;
	protected String currentEntryName;
	private int reviewIterationCount = 0;
	private int approveIterationCount = 0;

	// These fields should be populated by the subclass in its @BeforeClass method
	protected String CONFIG_NAME;
	protected String CHROME_URL_VAL;
	protected String APP_URL_VAL;
	protected String USERNAME_VAL;
	protected String PASSWORD_VAL;
	protected String USERNAME1_VAL;
	protected String PASSWORD1_VAL;
	protected String USERNAME2_VAL;
	protected String PASSWORD2_VAL;
	protected String USERNAME3_VAL;
	protected String PASSWORD3_VAL;
	protected String ACTIONSPERFORMEDBY_VAL;
	protected String PC_DB_NAME_VAL;
	protected String MASTER_DB_NAME_VAL;
	protected String MM_DB_NAME_VAL;
	protected String TITLE_MODULE_VAL;
	protected String MASTER_MODULE_VAL;
	protected String SUB_MASTER_MODULE_VAL;
	protected String SCRIPT_NUMBER_VAL;
	protected String VIEW_ACTION_VAL;

	@BeforeMethod
	public void initSoftAssert() {
		sa = new SoftAssertionUtil();
	}

	@Test(groups = { "setup" })
	public void initialSetUp() throws Exception {
		ScreenshotUtil.nextStep();
		log.info("Navigating to Chrome URL: {}", CHROME_URL_VAL);
		driver.navigate().to(CHROME_URL_VAL);
		log.info("Waiting for search box to be visible");
		pageObject.waitForElementToVisible(pageObject.getSearchBox());
		Assert.assertTrue(pageObject.getSearchBox().isDisplayed(), "Search box is not visible!");
		log.info("Searching for Application URL: {}", APP_URL_VAL);
		pageObject.searchBox(APP_URL_VAL);
		pageObject.waitForLoading();
		capture();
	}

	@Test(groups = { "url" })
	public void url() throws Throwable {
		driver.navigate().to(APP_URL_VAL);
		log.info("Navigating to App URL: {}", APP_URL_VAL);
	}

	@Test(groups = { "userlogin" })
	public void userLoginBeforeCreate() throws Throwable {
		log.info("Executing the flow with single/multiple : {}", ACTIONSPERFORMEDBY_VAL);
		if (ACTIONSPERFORMEDBY_VAL.equalsIgnoreCase("single")) {
			pageObject.login(USERNAME_VAL, PASSWORD_VAL, PC_DB_NAME_VAL);
		} else {
			pageObject.login(USERNAME1_VAL, PASSWORD1_VAL, PC_DB_NAME_VAL);
		}
	}

	@Test(groups = { "moduleselect" })
	public void moduleClick() throws Throwable {
		log.info("--- Clicking Module Navigation ---");

		// 1. Handle Top Level Title
		if (TITLE_MODULE_VAL != null && !TITLE_MODULE_VAL.trim().isEmpty()) {
			beforeTitleClick();
			if (TITLE_MODULE_VAL.equalsIgnoreCase("MASTERS")) {
				pageObject.click_titleMasters();
			} else if (TITLE_MODULE_VAL.equalsIgnoreCase("MM")) {
				pageObject.click_titleMM();
			}
			log.info("Clicked on Title: {}", TITLE_MODULE_VAL);
			pageObject.waitForLoading();
			capture();
		}

		// 2. Handle Master Module Level
		if (MASTER_MODULE_VAL != null && !MASTER_MODULE_VAL.trim().isEmpty()) {
			beforeMasterClick();
			pageObject.masterClick(MASTER_MODULE_VAL);
			log.info("Clicked on Master Module: {}", MASTER_MODULE_VAL);
			pageObject.waitForLoading();
			capture();
		}

		// 3. Handle Sub Master Module Level
		if (SUB_MASTER_MODULE_VAL != null && !SUB_MASTER_MODULE_VAL.trim().isEmpty()) {
			beforeSubMasterClick();
			pageObject.masterClick(SUB_MASTER_MODULE_VAL);
			log.info("Clicked on Sub Master Module: {}", SUB_MASTER_MODULE_VAL);
			pageObject.waitForLoading();
			capture();
		}
	}

	protected void beforeTitleClick() {
	}

	protected void beforeMasterClick() {
	}

	protected void beforeSubMasterClick() {
	}

	protected void beforeClickActionsScreenshot() {
	}

	@Test(groups = { "ClickActions" })
	public void Click_Actions() throws Throwable {
		log.info("--- Attempting to open Actions Menu for: {} ---", currentEntryName);
		beforeClickActionsScreenshot();
		pageObject.clickActions(currentEntryName);
		log.info("Successfully opened Actions menu for {}", currentEntryName);
		capture();
		afterClickActionsScreenshot();
	}

	protected void afterClickActionsScreenshot() throws Throwable {
		freezeCapture();
		try {
			pageObject.clickActions(currentEntryName);
		} catch (Throwable e) {
			log.error("Failed to re-click actions during freeze capture", e);
		}
		resumeCapture();
	}

	@Test(groups = { "ClickView" })
	public void Click_View() throws Throwable {
		if (VIEW_ACTION_VAL != null && VIEW_ACTION_VAL.equalsIgnoreCase("yes")) {
			log.info("--- Viewing {}: {} ---", CONFIG_NAME, currentEntryName);
			nextStep();
			performClickView();
			pageObject.waitForLoading();
			capture();
			pageObject.clickBack();
			pageObject.waitForLoading();
			capture();
		}
	}

	protected void performClickView() throws Throwable {
		pageObject.clickView(currentEntryName);
	}

	protected void beforeLogout() {
	}

	@Test(groups = { "Logout" })
	public void Logout() throws Throwable {
		log.info("--- Executing Final Logout ---");
		beforeLogout();
		pageObject.logout();
		log.info("Clicked logout button");
		pageObject.waitForToast();
		pageObject.waitForLoading();
		pageObject.waitForToastDisappear();
		capture();
	}

	@Test(groups = { "DB Back" })
	public void Database_Backup() {
		log.info("--- Initiating Post-Test Database Backup ---");
		String bkpName = (SCRIPT_NUMBER_VAL == null || SCRIPT_NUMBER_VAL.trim().isEmpty()) ? CONFIG_NAME
				: SCRIPT_NUMBER_VAL;
		DatabaseBackupUtil.backupPostgres(bkpName, PC_DB_NAME_VAL, "postgres", "root", "localhost", "5432");
		DatabaseBackupUtil.backupPostgres(bkpName, MASTER_DB_NAME_VAL, "postgres", "root", "localhost", "5432");
		DatabaseBackupUtil.backupPostgres(bkpName, MM_DB_NAME_VAL, "postgres", "root", "localhost", "5432");
	}

	protected void switchUserIfMulti(String username, String password, String titlename) throws Throwable {
		if (!ACTIONSPERFORMEDBY_VAL.equalsIgnoreCase("single")) {
			String[] path = getNavigationPath();
			pageObject.switchUser(username, password,titlename, PC_DB_NAME_VAL, path);
		}
	}

	protected String[] getNavigationPath() {
		List<String> pathList = new ArrayList<>();
		if (MASTER_MODULE_VAL != null && !MASTER_MODULE_VAL.trim().isEmpty()) {
			pathList.add(MASTER_MODULE_VAL);
		}
		if (SUB_MASTER_MODULE_VAL != null && !SUB_MASTER_MODULE_VAL.trim().isEmpty()) {
			pathList.add(SUB_MASTER_MODULE_VAL);
		}
		return pathList.toArray(new String[0]);
	}

	protected void beforeEdit() {
	}

	protected void beforeRemarks() {
	}

	protected void beforeInactive() {
	}

	protected void beforeActive() {
	}

	protected void performEdit(String updateName, String successMessage) throws Throwable {
		log.info("Opening Edit screen");
		beforeEdit();
		pageObject.clickEdit(currentEntryName);
		pageObject.waitForLoading();
		capture();
		if (updateName != null && !updateName.trim().isEmpty()) {
			// This needs to be handled individually because the field locator is
			// module-specific
			updateEntryName(updateName);
			currentEntryName = updateName;
		}
		pageObject.clickUpdate();
		capture();
		pageObject.authenticate(pageObject.currentPassword);
		String toast = pageObject.waitForToast();
		pageObject.waitForToastDisappear();
		pageObject.waitForLoading();
		capture();
		sa.assertEquals(toast, successMessage, "Updated toaster message", toast);
	}

	protected void reviewOpenClick() {
		reviewMenuClick();
	}

	protected void reviewSubmitClick() {
		reviewMenuClick();
	}

	protected void returnReviewOpenClick() {
		reviewOpenClick();
	}

	protected void rejectReviewOpenClick() {
		reviewOpenClick();
	}

	protected void reviewMenuClick() {
		pageObject.clickReview();
	}

	
	
	
	
	protected void reviewIterationCount() {
		if (reviewIterationCount == 3)
			nextStep();
	}

	protected void performReview(String remarks, String successMessage, String... actions) throws Throwable {
		reviewIterationCount++;
		pageObject.clickActions(combine(currentEntryName, actions));
		capture();
		reviewIterationCount();
		reviewOpenClick();
		pageObject.waitForLoading();
		capture();
		pageObject.enterRemarks(remarks);
		capture();
		reviewSubmitClick();
		capture();
		pageObject.authenticate(pageObject.currentPassword);
		String toast = pageObject.waitForToast();
		pageObject.waitForToastDisappear();
		pageObject.waitForLoading();
		capture();
		sa.assertEquals(toast, successMessage, "Review toast", successMessage);
	}

	protected void approveOpenClick() {
		approveMenuClick();
	}

	protected void approveSubmitClick() {
		approveMenuClick();
	}

	protected void returnApproveOpenClick() {
		approveOpenClick();
	}

	protected void rejectApproveOpenClick() {
		approveOpenClick();
	}

	protected void approveMenuClick() {
		pageObject.clickApprove();
	}


	
	protected void approveIterationCount() {
		if (approveIterationCount == 2)
			nextStep();
	}

	
	protected void performApprove(String remarks, String successMessage, String... actions) throws Throwable {
		approveIterationCount++;
		pageObject.clickActions(combine(currentEntryName, actions));
		capture();
		approveIterationCount();
		approveOpenClick();
		pageObject.waitForLoading();
		capture();
		pageObject.enterRemarks(remarks);
		approveSubmitClick();
		capture();
		pageObject.authenticate(pageObject.currentPassword);
		String toast = pageObject.waitForToast();
		pageObject.waitForToastDisappear();
		pageObject.waitForLoading();
		capture();
		sa.assertEquals(toast, successMessage, "Approve toast", successMessage);
	}

	protected void performEdit(String updateName, String successMessage, String... actions) throws Throwable {
		pageObject.clickEdit(combine(currentEntryName, actions));
		pageObject.waitForLoading();
		capture();
		if (updateName != null && !updateName.trim().isEmpty()) {
			updateEntryName(updateName);
			currentEntryName = updateName;
		}
		capture();
		pageObject.clickUpdate();
		capture();
		pageObject.authenticate(pageObject.currentPassword);
		String toast = pageObject.waitForToast();
		pageObject.waitForToastDisappear();
		pageObject.waitForLoading();
		capture();
		sa.assertEquals(toast, successMessage, "Edit toast", successMessage);
	}

	protected void noScreenshotForActionsInReviewReturn(String... actions) throws Throwable {

		pageObject.clickActions(combine(currentEntryName, actions));

	}
	protected void returnSubmitClick() {
		pageObject.clickReturn();
	}
	
	protected void performReturnReview(String remarks, String successMessage, String... actions) throws Throwable {
		log.info("Opening actions menu to access Review/Return");
		noScreenshotForActionsInReviewReturn(actions);
		capture();
		nextStep();
		returnReviewOpenClick();
		pageObject.waitForLoading();
		capture();
		beforeRemarks();
		pageObject.enterRemarks(remarks);
		capture();
		returnSubmitClick();
		capture();
		pageObject.authenticate(pageObject.currentPassword);
		String toast = pageObject.waitForToast();
		pageObject.waitForLoading();
		capture();
		sa.assertEquals(toast, successMessage, "Return Review toast", successMessage);
	}

	protected void performReturnApprove(String remarks, String successMessage, String... actions) throws Throwable {
		log.info("Opening actions menu to access Approve/Return");
		pageObject.clickActions(combine(currentEntryName, actions));
		capture();
		nextStep();
		returnApproveOpenClick();
		pageObject.waitForLoading();
		capture();
		beforeRemarks();
		pageObject.enterRemarks(remarks);
		capture();
		returnSubmitClick();
		capture();
		pageObject.authenticate(pageObject.currentPassword);
		String toast = pageObject.waitForToast();
		pageObject.waitForToastDisappear();
		pageObject.waitForLoading();
		capture();
		sa.assertEquals(toast, successMessage, "Return Approve toast", successMessage);
	}

	
	protected void inactiveSubmitClick() {
		pageObject.clickInactive();
	}
	
	protected void performInactive(String remarks, String successMessage, String... actions) throws Throwable {
		pageObject.clickActions(combine(currentEntryName, actions));
		capture();
		beforeInactive();
		pageObject.clickInactive();
		pageObject.waitForLoading();
		capture();
		pageObject.enterRemarks(remarks);
		capture();
		inactiveSubmitClick();
		capture();
		pageObject.authenticate(pageObject.currentPassword);
		String toast = pageObject.waitForToast();
		pageObject.waitForToastDisappear();
		pageObject.waitForLoading();
		capture();
		sa.assertEquals(toast, successMessage, "Inactive toaster message", toast);
	}

	protected void activeSubmitClick() {
		pageObject.clickActive();
	}
	
	protected void performActive(String remarks, String successMessage, String... actions) throws Throwable {
		pageObject.clickActions(combine(currentEntryName, actions));
		capture();
		beforeActive();
		pageObject.clickActive();
		pageObject.waitForLoading();
		capture();
		pageObject.enterRemarks(remarks);
		capture();
		activeSubmitClick();
		capture();
		pageObject.authenticate(pageObject.currentPassword);
		String toast = pageObject.waitForToast();
		pageObject.waitForToastDisappear();
		pageObject.waitForLoading();
		capture();
		sa.assertEquals(toast, successMessage, "Active toaster message", toast);
	}

	/*
	 * protected void performReject(String remarks, String successMessage, String...
	 * actions) throws Throwable {
	 * pageObject.clickActions(combine(currentEntryName, actions));
	 * capture();
	 * pageObject.clickReject();
	 * pageObject.waitForLoading();
	 * capture();
	 * pageObject.enterRemarks(remarks);
	 * capture();
	 * pageObject.clickReject();
	 * capture();
	 * pageObject.authenticate(pageObject.currentPassword);
	 * String toast = pageObject.waitForToast();
	 * pageObject.waitForToastDisappear();
	 * pageObject.waitForLoading();
	 * capture();
	 * sa.assertEquals(toast, successMessage, "Reject toaster message", toast);
	 * }
	 */
	
	protected void rejectSubmitClick() {
		pageObject.clickReject();
	}
	
	
	protected void performReviewReject(String remarks, String successMessage) throws Throwable {
		pageObject.clickActions(currentEntryName);
		capture();
		nextStep();
		rejectReviewOpenClick();
		pageObject.waitForLoading();
		capture();
		pageObject.enterRemarks(remarks);
		capture();
		rejectSubmitClick();
		capture();
		pageObject.authenticate(pageObject.currentPassword);
		String toast = pageObject.waitForToast();
		pageObject.waitForToastDisappear();
		pageObject.waitForLoading();
		capture();
		sa.assertEquals(toast, successMessage, "Reject toaster message", toast);
	}

	protected void performApproveReject(String remarks, String successMessage) throws Throwable {
		pageObject.clickActions(currentEntryName);
		capture();
		rejectApproveOpenClick();
		pageObject.waitForLoading();
		capture();
		pageObject.enterRemarks(remarks);
		capture();
		rejectSubmitClick();
		capture();
		pageObject.authenticate(pageObject.currentPassword);
		String toast = pageObject.waitForToast();
		pageObject.waitForToastDisappear();
		pageObject.waitForLoading();
		capture();
		sa.assertEquals(toast, successMessage, "Reject toaster message", toast);
	}

	protected String[] combine(String first, String... rest) {
		String[] result = new String[rest.length + 1];
		result[0] = first;
		System.arraycopy(rest, 0, result, 1, rest.length);
		return result;
	}

	// Standard Screenshot wrappers to allow subclass overriding
	protected void nextStep() {
		try {
			ScreenshotUtil.nextStep();
		} catch (Exception e) {
			log.error("nextStep failed", e);
		}
	}

	protected void capture() {
		try {
			ScreenshotUtil.capture();
		} catch (Exception e) {
			log.error("capture failed", e);
		}
	}

	protected void freezeCapture() {
		ScreenshotUtil.freezeCapture();
	}

	protected void resumeCapture() {
		ScreenshotUtil.resumeCapture();
	}

	protected void freezeStep() {
		ScreenshotUtil.freezeStepNumbering();
	}

	protected void resumeStep() {
		ScreenshotUtil.resumeStepNumbering();
	}

	// Abstract hook for module-specific name update field
	protected abstract void updateEntryName(String newName) throws Throwable;
}
