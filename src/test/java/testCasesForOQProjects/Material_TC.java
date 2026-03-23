package testCasesForOQProjects;

import static configData.MaterialData.*;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageObjects.Material;
import testBase.BaseClass;
import utilities.DatabaseBackupUtil;
import utilities.ScreenshotUtil;
import utilities.SoftAssertionUtil;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class Material_TC extends BaseClass {
	Material material;
	String currentMaterialName;
	SoftAssertionUtil sa;

	@BeforeClass
	@Parameters({ "configFile" })
	public void setUp(@Optional("material.properties") String configFile) throws Exception {
		log.info("--- Starting Material Test Case Setup with config: {} ---", configFile);

		// Load the dynamic property file
		configData.MaterialData.loadProperties(configFile);

		// Set conditional screenshot execution
		boolean screenshotsEnabled = "yes".equalsIgnoreCase(TAKE_SCREENSHOTS);
		ScreenshotUtil.setIsEnabled(screenshotsEnabled);

		if (screenshotsEnabled) {
			log.info("📸 Processing screenshot template and headers...");
			ScreenshotUtil.loadTemplateForEndAppend(TEMPLATE_PATH, OUTPUT_PATH);
			ScreenshotUtil.updateHeaderCellText(ACTUALHEADER, EXPECTEDHEADER);
			ScreenshotUtil.initScript(SCRIPT_NUMBER);
		}

		browserOpen();
		material = new Material(driver);
		material.setTableHeaders(TABLE_HEADERS);
		log.info("Setup completed. Screenshots enabled: {}", screenshotsEnabled);
	}

	@BeforeMethod
	public void initSoftAssert() {
		sa = new SoftAssertionUtil();
	}

	@Test(groups = { "setup" })
	public void initialSetUp() throws Exception {
		ScreenshotUtil.nextStep();
		log.info("Navigating to Chrome URL: {}", CHROME_URL);
		driver.navigate().to(CHROME_URL);
		log.info("Waiting for search box to be visible");
		material.waitForElementToVisible(material.getSearchBox());
		Assert.assertTrue(material.getSearchBox().isDisplayed(), "Search box is not visible!");
		log.info("Searching for Application URL: {}", APP_URL);
		material.searchBox(APP_URL);
		material.waitForLoading();
		ScreenshotUtil.capture();
		log.info("Initial setup completed and screenshot captured");
	}

	@Test(groups = { "url" })
	public void url() throws Throwable {
		driver.navigate().to(APP_URL);
		log.info("Navigating to App URL: {}", APP_URL);
	}

	@Test(groups = { "userlogin" })
	public void userLoginBeforeCreate() throws Throwable {
		log.info("Executing the flow with single/multiple : {}", ACTIONSPERFORMEDBY);
		if (ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
			material.login(USERNAME, PASSWORD, PC_DB_NAME);
		} else {
			material.login(USERNAME1, PASSWORD1, PC_DB_NAME);
		}
	}

	@Test(groups = { "moduleselect" })
	public void moduleClick() throws Throwable {
		log.info("--- Clicking Module ---");
		material.click_titleMasters();
		log.info("Clicked on Masters title");
		ScreenshotUtil.capture();
		material.waitForLoading();
		material.masterClick(MASTER_MODULE);
		log.info("Clicked on Master Module: {}", MASTER_MODULE);
		material.waitForLoading();
		ScreenshotUtil.capture();
		material.masterClick(SUB_MASTER_MODULE);
		log.info("Clicked on Sub Master Module: {}", SUB_MASTER_MODULE);
		material.waitForLoading();
		ScreenshotUtil.capture();
	}

	@Test(groups = { "Creation" })
	public void Creation_Of_Material() throws Throwable {
		log.info("--- Navigating to Create Material Screen ---");
		ScreenshotUtil.nextStep();
		material.Create();
		log.info("Clicked on Create button");
		material.waitForLoading();
		ScreenshotUtil.capture();
		ScreenshotUtil.nextStep();
		log.info("--- Creating Material: {} ---", MATERIAL_NAME);
		currentMaterialName = MATERIAL_NAME;
		material.materialName(MATERIAL_NAME);
		material.materialCode(MATERIAL_CODE);
		material.selTypeOfMaterial(TYPE_OF_MATERIAL);
		material.selMaterialCategory(MATERIAL_CATEGORY);
		material.selUOM(UOM);
		if(material.isStorageConditionDisplayed()) {
		
		material.storageCondition(STORAGE_CONDITION);
		}
		material.selStorageLocation(STORAGE_LOCATION);
		material.setSamplingActivity(SAMPLING_ACTIVITY);
		material.setDispensingActivity(DISPENSING_ACTIVITY);
		if(material.isMixedAnalysisIsDisplayed()) {
		material.setMixedAnalysis(MIXED_ANALYSIS);
		}
		material.setWeightVerification(WEIGHT_VERIFICATION);

		if(material.isReceivingBayIsDisplayed()) {
		if ("Yes".equalsIgnoreCase(WEIGHT_VERIFICATION)) {
			material.setReceivingBay(RECEIVING_BAY);
		}
		}

		if ("Yes".equalsIgnoreCase(SAMPLING_ACTIVITY) || "Yes".equalsIgnoreCase(DISPENSING_ACTIVITY)) {
			material.setCleaningAgent(CLEANING_AGENT);
		}

		material.selSupplier(SUPPLIER);
		material.waitForLoading();
		ScreenshotUtil.capture();
		material.setManufacturerRequired("No"); 
		material.waitForLoading();
		ScreenshotUtil.capture();// Assuming Yes for now as per old TC logic
		material.selManufacturer(MANUFACTURER);
		material.waitForLoading();
		ScreenshotUtil.capture();

		material.clickSaveDetails();
		log.info("Clicked Save Details");
		ScreenshotUtil.capture();

		material.confirmDialog("No"); // Assuming 'No' to more manufacturers as per old logic
		log.info("Clicked 'No' in add more manufacturers dialog");
		ScreenshotUtil.capture();

		material.clickSubmit();
		log.info("Clicked Submit");
		ScreenshotUtil.capture();

		material.authenticate(material.currentPassword);
		String authToast = material.waitForToast();
		material.waitForLoading();
		ScreenshotUtil.capture();
		sa.assertEquals(authToast, "Material created successfully", "Created Toaster message",
				"Creation failed with message: " + authToast);
		ScreenshotUtil.capture();
		sa.assertAll();

	}

	@Test(groups = { "ClickActions" })
	public void Click_Actions() throws Throwable {

	
		log.info("--- Attempting to open Actions Menu for: {} ---", currentMaterialName);
		ScreenshotUtil.nextStep();
		material.clickActions(currentMaterialName);
		log.info("Successfully opened Actions menu for {}", currentMaterialName);
		ScreenshotUtil.capture();

	}

	@Test(groups = { "ClickView" })
	public void Click_View() throws Throwable {
		if (MATERIAL_VIEW_ACTION.equalsIgnoreCase("yes")) {
			log.info("--- Viewing Material: {} ---", currentMaterialName);
			ScreenshotUtil.nextStep();
			material.clickView(currentMaterialName);
			log.info("View screen opened");
			material.waitForLoading();
			ScreenshotUtil.capture();
			material.clickBack();
			log.info("Clicked Back button");
			material.waitForLoading();
			ScreenshotUtil.capture();
		} else {
			log.info("View step skipped based on configuration");
		}
	}

	@Test(groups = { "materialReviewReturn_materialEdit" })
	public void material_Review_Return_and_Edit() throws Throwable {

		if (MATERIAL_RETURN_ACTION_IN_REVIEW.equalsIgnoreCase("yes")) {

			switchUserIfMulti(USERNAME2, PASSWORD2);
			log.info("--- Initiating Review Return Flow for: {} ---", currentMaterialName);
			performReturnReview(REVIEW_RETURN_REMARKS);
			
			switchUserIfMulti(USERNAME1, PASSWORD1);
			log.info("Opening Edit screen (After Review Return)");
			performEdit(EDIT_MATERIAL_IN_REVIEW_RETURN);
			sa.assertAll();

		} else {
			log.info("Material Review Return and Edit skipped based on configuration");
		}
	}

	@Test(groups = { "materialReview" })
	public void materialReview() throws Throwable {

		switchUserIfMulti(USERNAME2, PASSWORD2);
		log.info("--- Initiating Review Flow for: {} ---", currentMaterialName);
		performReview(REVIEW_REMARKS);
		sa.assertAll();


	}

	@Test(groups = { "materialApproveReturn_materialEdit_materialReview" })
	public void material_Approve_Return_and_Edit_and_Review() throws Throwable {

		if (MATERIAL_RETURN_ACTION_IN_APPROVE.equalsIgnoreCase("yes")) {

			switchUserIfMulti(USERNAME3, PASSWORD3);
			log.info("--- Initiating Approve Return Flow for: {} ---", currentMaterialName);
			performReturnApprove(APPROVE_RETURN_REMARKS);

			switchUserIfMulti(USERNAME1, PASSWORD1);
			log.info("Opening Edit screen (After Return)");
			performEdit(MATERIAL_RETURN_ACTION_IN_APPROVE);
			
			switchUserIfMulti(USERNAME2, PASSWORD2);
			log.info("--- Initiating Review Flow for: {} ---", currentMaterialName);
			performReview(REVIEW_REMARKS);
			
			sa.assertAll();

		} else {
			log.info("Material Approve Return and Edit and Review skipped based on configuration");
		}
	}

	@Test(groups = { "materialApprove" })
	public void materialApprove() throws Throwable {

		switchUserIfMulti(USERNAME3, PASSWORD3);
		log.info("--- Performing Final Approval for: {} ---", currentMaterialName);
		performApprove(APPROVE_REMARKS);
		sa.assertAll();

	}
	
	@Test(groups = { "materialUpdate" })
	public void materialUpdate() throws Throwable {
		
		if (MATERIAL_UPDATE_AFTER_APPROVE.equalsIgnoreCase("yes")) {

		switchUserIfMulti(USERNAME1, PASSWORD1);
		log.info("Opening Update screen (After Approve)");
		performUpdate(UPDATE_SUPPLIER, UPDATE_MANUFACTURER);
		
		switchUserIfMulti(USERNAME2, PASSWORD2);
		log.info("--- Initiating Review Flow for: {} ---", currentMaterialName);
		performReview(REVIEW_REMARKS);
		
		
		switchUserIfMulti(USERNAME3, PASSWORD3);
		log.info("--- Initiating Review Flow for: {} ---", currentMaterialName);
		performApprove(APPROVE_REMARKS);
		
		sa.assertAll();
		}
		
		else {
			log.info("Material Update skipped based on configuration");
		}
		
	}
	
	@Test(groups = { "vendorDetails" })
	public void vendorDetails() throws Throwable {
		
		switchUserIfMulti(USERNAME1, PASSWORD1);
		log.info("Opening Update screen (After Approve)");
		material.masterClick("Vendor Details");
		log.info("Clicked on Vendor Details Module");
		material.waitForLoading();
		ScreenshotUtil.capture();
		material.masterClick("Vendor Details List");
		log.info("Clicked on Vendor Details List Module");
		material.waitForLoading();
		ScreenshotUtil.capture();
		material.selMaterialName_selMaterialCode(currentMaterialName + "[ " + MATERIAL_CODE + " ]");
		ScreenshotUtil.capture();
		material.waitForLoading();
		material.clickSearch();
		ScreenshotUtil.capture();
	}
	

	

	@Test(groups = { "Logout" })
	public void Logout() throws Throwable {
		log.info("--- Executing Final Logout ---");
		ScreenshotUtil.nextStep();
		material.logout();
		log.info("Clicked logout button");
		material.waitForToast();
		material.waitForLoading();
		ScreenshotUtil.capture();
		log.info("--- Material Test Case Execution Finished ---");
	}

	@Test(groups = { "DB Back" })
	public void Database_Backup() {
		log.info("--- Initiating Post-Test Database Backup ---");

		String backupFolderName = (SCRIPT_NUMBER == null || SCRIPT_NUMBER.trim().isEmpty()) ? CURRENT_CONFIG_NAME
				: SCRIPT_NUMBER;

		log.info("Backup folder name determined: {}", backupFolderName);

		DatabaseBackupUtil.backupPostgres(backupFolderName, PC_DB_NAME, "postgres", "root", "localhost", "5432");
		DatabaseBackupUtil.backupPostgres(backupFolderName, MASTER_DB_NAME, "postgres", "root", "localhost", "5432");
		DatabaseBackupUtil.backupPostgres(backupFolderName, MM_DB_NAME, "postgres", "root", "localhost", "5432");
	}
	
	
	// --- HELPER METHODS ---

	
			private void performReview(String remarks) throws Throwable {
				log.info("Opening actions menu to access Review/Return");
				material.clickActions(currentMaterialName);
				ScreenshotUtil.capture();
				log.info("Clicking Review to trigger return dialog");
				material.clickReview();
				material.waitForLoading();
				ScreenshotUtil.capture();
				material.enterRemarks(remarks);
				log.info("Entered Review remarks: {}", remarks);
				ScreenshotUtil.capture();
				material.clickReview();
				log.info("Clicked Review button");
				ScreenshotUtil.capture();
				material.authenticate(material.currentPassword);
				String reviewToast = material.waitForToast();
				material.waitForLoading();
				sa.assertEquals(reviewToast, "Material reviewed successfully", "Review toaster message", reviewToast);
				
			}

			
			private void performApprove(String remarks) throws Throwable {
				
				log.info("Opening actions menu for approval");
				material.clickActions(currentMaterialName);
				ScreenshotUtil.capture();
				log.info("Clicking Approve button in the list");
				material.clickApprove();
				ScreenshotUtil.capture();
				material.enterRemarks(APPROVE_REMARKS);
				log.info("Entered Approve remarks: {}", remarks);
				ScreenshotUtil.capture();
				material.clickApprove();
				log.info("Submitted Approval");
				ScreenshotUtil.capture();
				material.authenticate(material.currentPassword);
				String approveToast = material.waitForToast();
				material.waitForLoading();
				sa.assertEquals(approveToast, "Material approved successfully", "Approved toaster messege", approveToast);
			
			}
			
			private void performEdit(String updateName) throws Throwable {
				log.info("Opening Edit screen");
				material.clickEdit(currentMaterialName);
				material.waitForLoading();
				ScreenshotUtil.capture();

				if (updateName != null && !updateName.trim().isEmpty()) {
					log.info("Updating Name to: {}", updateName);
					material.materialName(updateName);
					currentMaterialName = updateName;
				}

				material.clickUpdate();
				log.info("Clicked Update");
				ScreenshotUtil.capture();
				material.authenticate(material.currentPassword);
				String editToast = material.waitForToast();
				material.waitForLoading();
				sa.assertEquals(editToast, "Material updated successfully", "Updated toaster messege", editToast);
				
			}

			private void performReturnReview(String remarks) throws Throwable {
				log.info("Opening actions menu to access Review/Return");
				material.clickActions(currentMaterialName);
				ScreenshotUtil.capture();
				log.info("Clicking Review to trigger return dialog");
				material.clickReview();
				material.waitForLoading();
				ScreenshotUtil.capture();
				material.enterRemarks(remarks);
				log.info("Entered Return remarks: {}", remarks);
				ScreenshotUtil.capture();
				material.clickReturn();
				log.info("Clicked Return button");
				ScreenshotUtil.capture();
				material.authenticate(material.currentPassword);
				String returnToast = material.waitForToast();
				material.waitForLoading();
				sa.assertEquals(returnToast, "Material returned successfully", "Returned toaster message", returnToast);
				
			}

			private void performReturnApprove(String remarks) throws Throwable {
				log.info("Opening actions menu to access Approve/Return");
				material.clickActions(currentMaterialName);
				ScreenshotUtil.capture();
				log.info("Clicking Approve to trigger return dialog");
				material.clickApprove();
				material.waitForLoading();
				ScreenshotUtil.capture();
				material.enterRemarks(remarks);
				log.info("Entered Return remarks: {}", remarks);
				ScreenshotUtil.capture();
				material.clickReturn();
				log.info("Clicked Return button");
				ScreenshotUtil.capture();
				material.authenticate(material.currentPassword);
				String returnToast = material.waitForToast();
				material.waitForLoading();
				sa.assertEquals(returnToast, "Material returned successfully", "Returned toaster message", returnToast);
				
			}
			
			
			private void switchUserIfMulti(String username, String password) throws Throwable {
				if (!ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
					material.switchUser(username, password, PC_DB_NAME, MASTER_MODULE, SUB_MASTER_MODULE);
				}
			}

		
		@Test(groups = { "ClickActionRep" })
		public void Click_Actions_1() throws Throwable {
			
			switchUserIfMulti(USERNAME1, PASSWORD1);
			
			Click_Actions();	
			}
		
		
		
		@Test(groups = { "ClickActionRep" })
		public void Click_Actions_2() throws Throwable {
			
			switchUserIfMulti(USERNAME2, PASSWORD2);
			
			Click_Actions();	
		}
		
		
		private void performUpdate(String updateSupplier, String updateManufacturer) throws Throwable {
			log.info("Opening actions menu to access Updatde");
			material.clickActions(currentMaterialName);
			ScreenshotUtil.capture();
			log.info("Opening Update screen");
			material.clickUpdate();
			material.waitForLoading();
			ScreenshotUtil.capture();

			if (updateSupplier != null && !updateSupplier.trim().isEmpty()) {
				material.selSupplier(updateSupplier);
				material.waitForLoading();
				ScreenshotUtil.capture();
				material.setManufacturerRequired("No"); 
				material.waitForLoading();
				ScreenshotUtil.capture();
				if(updateManufacturer != null && !updateManufacturer.trim().isEmpty()) { // Assuming Yes for now as per old TC logic
				material.selManufacturer(updateManufacturer);
				}else {
					material.selManufacturer(MANUFACTURER);	
				}
				
				material.waitForLoading();
				ScreenshotUtil.capture();

				material.clickSaveDetails();
				log.info("Clicked Save Details");
				ScreenshotUtil.capture();

				material.confirmDialog("No"); // Assuming 'No' to more manufacturers as per old logic
				log.info("Clicked 'No' in add more manufacturers dialog");
				ScreenshotUtil.capture();
			}
			material.waitForLoading();
			material.clickUpdate();
			log.info("Clicked Update");
			ScreenshotUtil.capture();
			material.authenticate(material.currentPassword);
			String editToast = material.waitForToast();
			material.waitForLoading();
			sa.assertEquals(editToast, "Material updated successfully", "Updated toaster messege", editToast);
			
		}


}
