package pageObjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import utilities.DataStorage;
import utilities.ScreenshotUtil;

public class MaterialInwardForSolid extends BasePage {

	public MaterialInwardForSolid(WebDriver driver) {
		super(driver);

	}

	/*-----------------------Material Inward Create--------------------------*/

	@FindBy(xpath = "//ng-multiselect-dropdown[@formcontrolname='materialCode']")
	WebElement drpdwn_materialcode;

	public void selMaterialCode(String materialcode) {
		waitForElementandClick(drpdwn_materialcode);
		clickRelativeOption(drpdwn_materialcode, "li", materialcode);

	}

	@FindBy(xpath = "//ng-multiselect-dropdown[@formcontrolname='supplierName']")
	WebElement drpdwn_supplier;

	public void selSupplier(String supplier) {
		waitForElementandClick(drpdwn_supplier);
		clickRelativeOption(drpdwn_supplier, "li", supplier);

	}

	@FindBy(xpath = "//ng-multiselect-dropdown[@formcontrolname='manufactureName']")
	WebElement drpdwn_manufacturer;

	public void selManufacturer(String manufacturer) {
		waitForElementandClick(drpdwn_manufacturer);
		clickRelativeOption(drpdwn_manufacturer, "li", manufacturer);

	}

	@FindBy(xpath = ("//input[@formcontrolname='quantityReceived']"))
	WebElement txt_quantityreceived;

	public void enterQuantityReceived(String quantityreceived) {
		txt_quantityreceived.clear();
		waitAndSendKeys(txt_quantityreceived, quantityreceived);
	}

	@FindBy(xpath = ("//input[@formcontrolname='poNo']"))
	WebElement txt_po;

	public void enterPO(String po) {
		txt_po.clear();
		waitAndSendKeys(txt_po, po);
	}

	@FindBy(xpath = ("//input[@formcontrolname='invoiceNo']"))
	WebElement txt_invoice;

	public void enterInvoice(String invoice) {
		txt_invoice.clear();
		waitAndSendKeys(txt_invoice, invoice);
	}

	@FindBy(xpath = ("//input[@formcontrolname='gateEntryNumber']"))
	WebElement txt_gateentry;

	public void enterGateEntry(String gateentry) {
		// Use JS to Force-Set value specifically for this field to bypass the '000'
		// auto-formatting
		wait.until(ExpectedConditions.visibilityOf(txt_gateentry));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].value = arguments[1];", txt_gateentry, gateentry);
		js.executeScript("arguments[0].dispatchEvent(new Event('input', { bubbles: true }));", txt_gateentry);
		js.executeScript("arguments[0].dispatchEvent(new Event('change', { bubbles: true }));", txt_gateentry);
		js.executeScript("arguments[0].dispatchEvent(new Event('blur', { bubbles: true }));", txt_gateentry);
	}

	@FindBy(xpath = "//ng-multiselect-dropdown[@formcontrolname='quantityInRecived']")
	WebElement drpdwn_quantityinreceived;

	public boolean quantityReceivedisDisplayed() {
		try {
			return drpdwn_quantityinreceived.isDisplayed();
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public void selQuantityReceivedIn(String quantityreceivedin) {
		waitForElementandClick(drpdwn_quantityinreceived);
		clickRelativeOption(drpdwn_quantityinreceived, "li", quantityreceivedin);

	}

	@FindBy(xpath = ("//input[@formcontrolname='vehicleNumber']"))
	WebElement txt_vehiclenumber;

	public boolean vehicleNumberisDisplayed() {
		try {
			return txt_vehiclenumber.isDisplayed();
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public void entervehicleNumber(String vehiclenumber) {
		txt_vehiclenumber.clear();
		waitAndSendKeys(txt_vehiclenumber, vehiclenumber);
	}

	@FindBy(xpath = ("//input[@formcontrolname='noOfBatches']"))
	WebElement txt_noofbatches;

	public void enterNumberOfBatches(String noofbatches) {
		txt_noofbatches.clear();
		waitAndSendKeys(txt_noofbatches, noofbatches);
	}

	public boolean isNoOfPacksEnabled() {
		return txt_noofbatches.isEnabled();
	}

	@FindBy(xpath = ("//input[@formcontrolname='batchNo']"))
	List<WebElement> txt_mfgbatchno;

	public void enterMfgBatchNo(String mfgbatchno, int index) {
		enterValueInElementAtIndex(txt_mfgbatchno, index, mfgbatchno);
	}

	@FindBy(xpath = ("//input[@formcontrolname='wiseQty']"))
	List<WebElement> txt_batchquantity;

	public void enterBatchQuantity(String batchquantity, int index) {
		enterValueInElementAtIndex(txt_batchquantity, index, batchquantity);
	}

	@FindBy(xpath = ("//input[@formcontrolname='noOfPacks']"))
	List<WebElement> txt_noofpacks;

	public void enterNoOfPacks(String noofpacks, int index) {
		enterValueInElementAtIndex(txt_noofpacks, index, noofpacks);
	}

	public boolean noOfPacksisDisplayed() {
		{
			return txt_noofpacks.size() > 0 && txt_noofpacks.get(0).isDisplayed();
		}
	}

	/*-----------------------To Save No Of Packs In Material Inward--------------------------*/

	@FindBy(xpath = "//button[normalize-space()='Save']")
	WebElement btn_save;

	@FindBy(xpath = "//div[@role='dialog']")
	WebElement packdetails_popup;

	public void clickSave(int index) throws Exception {
		WebElement latestElement = txt_noofpacks.get(index);

		((JavascriptExecutor) driver)
				.executeScript("arguments[0].dispatchEvent(new Event('dblclick', { bubbles: true }));", latestElement);

		wait.until(ExpectedConditions.visibilityOf(packdetails_popup));
		wait.until(ExpectedConditions.elementToBeClickable(btn_save)).click();

		wait.until(ExpectedConditions.invisibilityOf(packdetails_popup));
	}

	/*--------------To View and Close Pack Details In View Material Inward -------------*/

	@FindBy(xpath = "//button[normalize-space()='View']")
	List<WebElement> btn_viewPackdetails;

	public void clickViewPackDetails(int index) {
		clickElementAtIndex(btn_viewPackdetails, index);
	}

	public int getViewPackDetailsButtonCount() {
		return btn_viewPackdetails.size();
	}

	@FindBy(xpath = "//span[normalize-space()='Close']")
	WebElement btn_closePackdetails;

	public void clickClosePackDetails() {
		waitForElementandClick(btn_closePackdetails);
	}

	/*-----------------------Pre-Inspection Report Button in Actions--------------------------*/

	@FindBy(xpath = "//span[normalize-space()='Pre-Inspection Report']")
	WebElement button_preinspectionreport;

	public void clickPreInspectionReport() {
		scrollAndClick(button_preinspectionreport);

	}

	/*---------------------Checklist Description Type------------------------*/

	@FindBy(xpath = "//input[@formcontrolname='mainInputField' or @formcontrolname='subInputField']")
	List<WebElement> txt_alldescriptioninput;

	public void enterDescriptionInput(String descriptioninput, int index) {

		enterValueInElementAtIndex(txt_alldescriptioninput, index, descriptioninput);
	}

	/*---------------------Checklist Checkbox Type------------------------*/

	@FindBy(xpath = "//table/thead/tr/th")
	List<WebElement> get_checkboxheaders;

	@FindBy(xpath = "//tbody/tr[.//input[contains(@class,'option-box')]]")
	List<WebElement> get_checkboxrows;

	@FindBy(xpath = "//input[contains(@class,'option-box')]")
	List<WebElement> get_checkboxes;

	public void selectChecklistOption(int rowIndex, String optionName) {

		// 1. Find Headers dynamically to get current state
		int targetColumnIndex = -1;
		int currentCheckboxIndex = 0;

		for (WebElement header : get_checkboxheaders) {
			String headerText = header.getText().trim();

			if (headerText.equalsIgnoreCase(optionName)) {
				targetColumnIndex = currentCheckboxIndex;
				break;
			}

			if (!headerText.isEmpty() && !headerText.equalsIgnoreCase("Check Point")
					&& !headerText.equalsIgnoreCase("Text")) {
				currentCheckboxIndex++;
			}
		}

		if (targetColumnIndex == -1) {
			System.out.println("Header '" + optionName + "' not found in table.");
			return;
		}

		if (rowIndex < get_checkboxrows.size()) {
			WebElement row = get_checkboxrows.get(rowIndex);

			// Find checkboxes ONLY within this specific row
			List<WebElement> checkboxes = row.findElements(By.xpath(".//input[contains(@class,'option-box')]"));

			if (targetColumnIndex < checkboxes.size()) {
				WebElement checkbox = checkboxes.get(targetColumnIndex);
				scrollAndClick(checkbox);
			} else {
				System.out.println("Checkbox index " + targetColumnIndex + " is out of bounds for row " + rowIndex);
			}
		} else {
			System.out.println("Row index " + rowIndex + " is not found in the checklist table.");
		}
	}

	/*--------------------To check Checklist Type is Description------------------------*/

	public boolean areDescriptionInputsPresent() {
		try {
			// Check if list is not empty and the first element is displayed
			return txt_alldescriptioninput != null && !txt_alldescriptioninput.isEmpty()
					&& txt_alldescriptioninput.get(0).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	/*--------------------To check Checklist Type is Checkbox------------------------*/

	public boolean areCheckboxesPresent() {
		try {
			List<WebElement> checkboxes = driver.findElements(By.xpath("//input[contains(@class,'option-box')]"));
			return checkboxes != null && !checkboxes.isEmpty() && checkboxes.get(0).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	/*-----------------------File Upload in Pre-Inspection--------------------------*/

	@FindBy(xpath = "//input[@type='file']")
	WebElement input_fileUpload;

	public void uploadFile(String filePath) {
		// File uploads MUST use standard sendKeys and cannot use JS .value injection
		// We use presenceOfElement because file inputs are often hidden (0x0 size)
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@type='file']")));
		input_fileUpload.sendKeys(filePath);
	}

	/*-----------------------Remarks in Pre-Inspection--------------------------*/

	@FindBy(xpath = "//textarea[@formcontrolname='allEntryRemarks']")
	WebElement txt_remarks;

	public void enterRemarksPreInspection(String remarks) {
		waitAndSendKeys(txt_remarks, remarks);
	}

	/*-----------------------Review Pre-Inspection Report Button in Actions--------------------------*/

	@FindBy(xpath = "//span[normalize-space()='Review Pre-Inspection Report']")
	WebElement button_reviewpreinspectionreport;

	public void clickReviewPreInspectionReport() {
		scrollAndClick(button_reviewpreinspectionreport);

	}

	/*-----------------------Edit Pre-Inspection Report Button In Actions--------------------------*/

	@FindBy(xpath = "//span[normalize-space()='Edit Pre-Inspection Report']")
	WebElement button_editpreinspectionreport;

	public void clickEditPreInspectionReport() {
		scrollAndClick(button_editpreinspectionreport);

	}

	/*--------------------Edit Pre-Inspection Checklist By giving the value and index------------------------*/

	public void editPreInspectionChecklist(String valuesString, String indexesString) {
		if (valuesString == null || valuesString.isEmpty() || indexesString == null || indexesString.isEmpty()) {
			System.out.println("Values or indexes string is empty. Skipping edit.");
			return;
		}

		String[] values = valuesString.split(",");
		String[] indexStrings = indexesString.split(",");

		if (values.length != indexStrings.length) {
			System.out.println("Mismatch between values count (" + values.length + ") and indexes count ("
					+ indexStrings.length + ").");
			return;
		}

		boolean isDescription = areDescriptionInputsPresent();
		boolean isCheckbox = areCheckboxesPresent();

		for (int i = 0; i < values.length; i++) {
			int index = Integer.parseInt(indexStrings[i].trim());
			String value = values[i].trim();

			if (isDescription) {
				enterDescriptionInput(value, index);
			} else if (isCheckbox) {
				selectChecklistOption(index, value);
			} else {
				System.out.println("Neither description inputs nor checkboxes are present on the page.");
				break;
			}
		}
	}

	/*---------------Review Pre-inspection screen Rejected Batch Details------------*/

	@FindBy(xpath = "//p-autocomplete[@formcontrolname='packNo']//chevrondownicon")
	List<WebElement> button_packNo;

	public void clickPackNumber(int packno) {
		clickLatestElement(button_packNo);
		waitForElementandClick(driver.findElement(By.xpath("//li[normalize-space()='" + packno + "']")));

	}

	@FindBy(xpath = "//p-autocomplete[@formcontrolname='batchNo']//chevrondownicon")
	List<WebElement> button_batchNo;

	public void clickBatchNumber(String batchno) {
		clickLatestElement(button_batchNo);
		waitForElementandClick(driver.findElement(By.xpath("//li[normalize-space()='" + batchno + "']")));
	}

	@FindBy(xpath = "//textarea[@formcontrolname='reason']")
	List<WebElement> txt_reason;

	public void enterReason(String value, int index) {
		enterValueInElementAtIndex(txt_reason, index, value);
	}

	@FindBy(xpath = "//textarea[@formcontrolname='remark']")
	WebElement txt_remark;

	public void enterRemarksInRejectedBatchDetails(String remarks) {
		waitAndSendKeys(txt_remark, remarks);
	}

	public void enterRejectedBatchDetailsBulk(String[] batchNumbers, String[] packNumbers, String[] reasons)
			throws Exception {
		if (batchNumbers.length == 0)
			return;

		for (int i = 0; i < batchNumbers.length; i++) {
			clickBatchNumber(batchNumbers[i].trim());

			// Only click pack number if it is provided
			if (packNumbers[i] != null && !packNumbers[i].trim().isEmpty()) {
				clickPackNumber(Integer.parseInt(packNumbers[i].trim()));
			}

			enterReason(reasons[i].trim(), i);

			// Press Enter to add a new row if it's not the last batch
			if (i < batchNumbers.length - 1) {
				txt_reason.get(i).sendKeys(Keys.ENTER);
				Thread.sleep(1000); // Small wait for new row to be generated
			}
		}
	}

	/*------------- QA Review Pre-Inspection Report In Actions------------------*/

	@FindBy(xpath = "//span[normalize-space()='QA Review Pre-Inspection Report']")
	WebElement button_qareviewpreinspectionreport;

	public void clickQaReviewPreinspectionReport() {
		waitForElementandClick(button_qareviewpreinspectionreport);
	}

	/*--------------------------QA Review Pre-Inspection Report------------------------*/

	@FindBy(xpath = "//input[@formcontrolname='fullReject']")
	WebElement check_fullreject;

	public void checkFullReject() {

		scrollAndClick(check_fullreject);

	}

	@FindBy(xpath = "//ng-multiselect-dropdown[@formcontrolname='fullBatchNo']")
	WebElement dropdwn_fullreject;

	public void selFullReject(String batchnos) {
		waitForElementandClick(dropdwn_fullreject);
		String[] batches = batchnos.split(",");
		for (String batch : batches) {
			clickRelativeOption(dropdwn_fullreject, "li", batch.trim());
		}
		// Click on remarks field to close the dropdown list
		jsClick(txt_fullrejectremarks);
	}

	@FindBy(xpath = "//textarea[@formcontrolname='fullRejectRemarks']")
	WebElement txt_fullrejectremarks;

	public void enterFullRejectRemarks(String fullrejectremarks) {
		waitAndSendKeys(txt_fullrejectremarks, fullrejectremarks);
	}

	@FindBy(xpath = "//table[.//input[@formcontrolname='reject']]//tbody/tr")
	List<WebElement> list_qaPartialTableRows;

	/**
	 * Matches batch/pack from data to the table and performs action
	 */
	public void processPartialRejection(String batch, String pack, String action, String segRemarks) {
		boolean found = false;
		String targetBatch = batch.trim();
		String targetPack = pack.trim();

		System.out
				.println("Processing: Batch [" + targetBatch + "], Pack [" + targetPack + "], Action [" + action + "]");

		// Fetch rows from the specific quality batches list array
		String rowXpath = "//tbody[@formarrayname='qualityBatchesList']/tr";
		List<WebElement> rows = driver.findElements(By.xpath(rowXpath));

		if (rows.isEmpty()) {
			System.out.println("❌ ERROR: No rows found in 'qualityBatchesList' tbody!");
			return;
		}

		for (int i = 0; i < rows.size(); i++) {
			WebElement row = rows.get(i);

			try {

				WebElement batchInput = row.findElement(By.xpath(".//input[@formcontrolname='viewBatchNo']"));
				WebElement packInput = row.findElement(By.xpath(".//input[@formcontrolname='viewPackNo']"));

				// Extract value from the input fields via JS accurately
				String tableBatch = batchInput.getAttribute("value");
				String tablePack = packInput.getAttribute("value");
				tableBatch = (tableBatch != null) ? tableBatch.trim() : "";
				tablePack = (tablePack != null) ? tablePack.trim() : "";

				System.out.println(
						"Row [" + (i + 1) + "] data -> UI Batch: [" + tableBatch + "], UI Pack: [" + tablePack + "]");

				if (tableBatch.equalsIgnoreCase(targetBatch) && tablePack.equalsIgnoreCase(targetPack)) {
					found = true;
					System.out.println("✅ MATCH FOUND at Row " + (i + 1) + "! Proceeding with Action: " + action);

					jsScrollToElement(row);

					String controlName = "";
					String act = action.toLowerCase();
					if (act.startsWith("r"))
						controlName = "reject";
					else if (act.startsWith("a"))
						controlName = "approve";
					else if (act.startsWith("s"))
						controlName = "segregated";

					if (!controlName.isEmpty()) {
						WebElement checkbox = row
								.findElement(By.xpath(".//input[@formcontrolname='" + controlName + "']"));

						if (checkbox.isEnabled()) {
							jsClick(checkbox);
							System.out.println(">>> Multi-select action [" + controlName + "] successful.");

							if (controlName.equals("segregated") && segRemarks != null && !segRemarks.isEmpty()) {
								try {
									Thread.sleep(500); // Wait for UI transition
									WebElement remarkField = row
											.findElement(By.xpath(".//textarea[@formcontrolname='segregatedRemarks']"));
									jsScrollToElement(remarkField);

									// Using JS to bypass potential readonly or focus issues
									((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];",
											remarkField, segRemarks);
									((JavascriptExecutor) driver).executeScript(
											"arguments[0].dispatchEvent(new Event('input', {bubbles: true}));",
											remarkField);
									System.out.println(">>> Segregated Remarks entered successfully: " + segRemarks);
								} catch (Exception e) {
									System.out.println(">>> Failed to enter remarks via JS: " + e.getMessage());
								}
							}
						} else {
							System.out.println(
									"⚠️ WARNING: Checkbox [" + controlName + "] is DISABLED in row " + (i + 1));
						}
					}
					break;
				}
			} catch (Exception e) {
				System.out.println("Skipping row " + (i + 1) + " due to interaction error: " + e.getMessage());
			}
		}

		if (!found) {
			System.out.println(
					"❌ FAILED: Batch [" + targetBatch + "] with Pack [" + targetPack + "] not matched in table.");
		}
	}

	/*-------------View Pre-Inspection Report in Actions---------------------*/

	@FindBy(xpath = "//span[normalize-space()='View Pre-Inspection Report']")
	WebElement button_viewpreinspectionreport;

	public void clickViewPreInspectionReport() {
		scrollAndClick(button_viewpreinspectionreport);

	}

	/*----------------------QA Review View Button in Actions---------------------*/

	@FindBy(xpath = "//span[normalize-space()='View Pre-Inspection Report']")
	WebElement button_qareviewview;

	public void clickQAReviewView() {
		scrollAndClick(button_qareviewview);

	}

	/*-----------------Rejected Batch Details In Actions-------------------*/

	@FindBy(xpath = "//span[normalize-space()='Rejected Batch Details']")
	WebElement button_rejectedbatchdetails;

	public void clickRejectedBatchDetails() {
		scrollAndClick(button_rejectedbatchdetails);

	}

	public boolean rejectedBatchDetailsButtonisDisplayed() {
		try {
			return button_rejectedbatchdetails.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	/*---------------Rejected Batch Details-----------------*/

	@FindBy(xpath = "//tbody[@formarrayname='mfgBatchArray']/tr")
	List<WebElement> get_allrows;

	public int getRejectedBatchRowCount() {
		return driver.findElements(By.xpath("//tbody[@formarrayname='mfgBatchArray']/tr")).size();
	}

	public void selectAndReturnBatch(int index) throws Exception {
		List<WebElement> rows = driver.findElements(By.xpath("//tbody[@formarrayname='mfgBatchArray']/tr"));
		if (index >= rows.size())
			return;

		WebElement row = rows.get(index);
		WebElement checkbox = row.findElement(By.xpath(".//input[@formcontrolname='checkStage']"));
		if (!checkbox.isSelected()) {
			jsClick(checkbox);
		}
		WebElement btn = row.findElement(By.xpath(".//button[normalize-space()='Rejected Stock Return']"));
		jsClick(btn);
		Thread.sleep(1000);
	}

	@FindBy(xpath = "//input[@formcontrolname='returnInvoiceNo']")
	WebElement txt_invoicenumber;

	public void enterInvoiceNumber(String invoicenumber) {
		waitAndSendKeys(txt_invoicenumber, invoicenumber);
	}

	@FindBy(xpath = "//input[@formcontrolname='returnRemarks']")
	WebElement txt_stockreturnremarks;

	public void enterRejectedStockReturnRemarks(String remarks) {
		waitAndSendKeys(txt_stockreturnremarks, remarks);
	}

	public void clickSubmit() {
		clickSubmitStockReturn();
	}

	/*-------------------------------Weight Verification below 5000kgs----------------*/
	@FindBy(xpath = "//label[normalize-space()='Inhouse Batch Number']")
	WebElement label_inhousebatchnumber;

	public boolean labelInhouseBatchNumberisDisplayed() {
		try {
			return label_inhousebatchnumber.isDisplayed();
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	/*-----------------------To Click on Weight Verification button for a particular batch in Create Weight Verification pop up--------------------------*/

	@FindBy(xpath = "//div[@formarrayname='batchDetails']//div[contains(@class,'row')]")
	List<WebElement> weightVerificationRows;

	/**
	 * Identifies the correct row in the Weight Verification modal, captures the
	 * Inhouse Batch Number, saves it to DataStorage, and clicks the verification
	 * button.
	 * 
	 * @param targetMfgBatch The Manufacturing Batch Number to search for.
	 * @param batchSuffix    Optional suffix (e.g., "[23]") to distinguish between
	 *                       multiple inhouse batches for the same Mfg Batch.
	 */
	public List<String> getInhouseBatches(String targetMfgBatch) throws InterruptedException {
		// Wait for rows to exist
		wait.until(ExpectedConditions.visibilityOfAllElements(weightVerificationRows));

		// Small delay to ensure Angular data binding populates the @value of inputs
		Thread.sleep(2000);

		List<String> inhouseBatches = new ArrayList<>();
		for (WebElement row : weightVerificationRows) {
			try {
				WebElement mfgInput = row.findElement(By.xpath(".//input[@formcontrolname='mfgDate']"));
				String actualMfg = mfgInput.getAttribute("value");

				if (actualMfg != null && actualMfg.trim().equalsIgnoreCase(targetMfgBatch.trim())) {
					WebElement inhouseInput = row.findElement(By.xpath(".//input[@formcontrolname='batchNo']"));
					String inhouseVal = inhouseInput.getAttribute("value");
					if (inhouseVal != null && !inhouseVal.trim().isEmpty()) {
						inhouseBatches.add(inhouseVal.trim());
					}
				}
			} catch (Exception e) {
				// Log and continue to next row
			}
		}

		inhouseBatches.sort((a, b) -> {
			if (a.equalsIgnoreCase(targetMfgBatch))
				return -1;
			if (b.equalsIgnoreCase(targetMfgBatch))
				return 1;
			return a.compareTo(b);
		});
		return inhouseBatches;
	}

	@FindBy(xpath = "//div[@role='dialog']//button[contains(@class,'header-close')]")
	WebElement icon_clos;

	public void clickCloseIconInPopUp() {
		try {
			scrollAndClick(icon_clos);
			System.out.println("✅ Closed Weight Verification modal.");
		} catch (Exception e) {
			System.out.println("ℹ️ Close button not clickable or already closed.");
		}
	}

	public void clickWeightVerificationForInhouse(String inhouseBatch) throws Exception {
		wait.until(ExpectedConditions.visibilityOfAllElements(weightVerificationRows));
		boolean found = false;
		for (WebElement row : weightVerificationRows) {
			WebElement inhouseInput = row.findElement(By.xpath(".//input[@formcontrolname='batchNo']"));
			String actualInhouse = inhouseInput.getAttribute("value").trim();
			if (actualInhouse.equalsIgnoreCase(inhouseBatch)) {
				DataStorage.save("capturedInhouseBatch", actualInhouse);
				WebElement btn = row.findElement(By.xpath(".//button[normalize-space()='Weight Verification']"));
				scrollAndClick(btn);
				found = true;
				break;
			}
		}
		if (!found) {
			throw new RuntimeException(
					"❌ Could not find weight verification button for Inhouse Batch: " + inhouseBatch);
		}
	}

	public void captureWeightVerificationData(String targetMfgBatch, String batchSuffix) throws Exception {
		wait.until(ExpectedConditions.visibilityOfAllElements(weightVerificationRows));
		boolean found = false;

		for (WebElement row : weightVerificationRows) {
			// Based on provided HTML: batchNo is Inhouse, mfgDate is Mfg Batch
			WebElement inhouseInput = row.findElement(By.xpath(".//input[@formcontrolname='batchNo']"));
			WebElement mfgInput = row.findElement(By.xpath(".//input[@formcontrolname='mfgDate']"));

			String actualInhouse = inhouseInput.getAttribute("value").trim();
			String actualMfg = mfgInput.getAttribute("value").trim();

			System.out.println("🔍 Checking Row -> Mfg: " + actualMfg + ", Inhouse: " + actualInhouse);

			if (actualMfg.equalsIgnoreCase(targetMfgBatch)) {
				// If suffix is provided, it must match. If not, it clicks the first one
				// matching Mfg Batch.
				if (batchSuffix == null || batchSuffix.isEmpty() || actualInhouse.contains(batchSuffix)) {

					// 1. Capture and store the Inhouse Batch Number
					DataStorage.save("capturedInhouseBatch", actualInhouse);
					System.out.println("✅ Captured Inhouse Batch Number: " + actualInhouse);

					// 2. Click the Weight Verification button for this specific row
					WebElement btn = row.findElement(By.xpath(".//button[normalize-space()='Weight Verification']"));
					scrollAndClick(btn);

					found = true;
					break;
				}
			}
		}

		if (!found) {
			String errorMsg = "❌ Could not find weight verification row for Mfg Batch: " + targetMfgBatch;
			if (batchSuffix != null && !batchSuffix.isEmpty()) {
				errorMsg += " with suffix: " + batchSuffix;
			}
			ScreenshotUtil.takeStepScreenshot("WeightVerificationRowNotFound");
			throw new RuntimeException(errorMsg);
		}
	}

	/*------------Create Weight Verification Record-----------*/

	@FindBy(xpath = "//ng-multiselect-dropdown[@formcontrolname='balenceCapacity']")
	WebElement drpdwn_balanceid;

	public void selBalanceId(String balanceid) {
		waitForElementandClick(drpdwn_balanceid);
		clickRelativeOption(drpdwn_balanceid, "li", balanceid);

	}

	@FindBy(xpath = "//ng-multiselect-dropdown[@formcontrolname='weightType']")
	WebElement drpdwn_weighttype;

	public void selWeightType(String weighttype) {

		WebElement dropdown = drpdwn_weighttype.findElement(By.xpath(".//span[@class='dropdown-multiselect__caret']"));

		waitForElementandClick(dropdown);
		clickRelativeOption(drpdwn_weighttype, "li", weighttype);

	}

	@FindBy(xpath = "//input[@formcontrolname='grossWtPer']")
	WebElement txt_wtperlabel;

	public void enterWtPerLabel(String wtperlabel) {
		waitAndSendKeys(txt_wtperlabel, wtperlabel);
	}

	@FindBy(xpath = "//input[@formcontrolname='actualWt']")
	WebElement txt_actualwt;

	@FindBy(xpath = "//input[@formcontrolname='result']")
	WebElement txt_packsToBeWeighed;

	public void enterActualWt(String actualwt) {
		waitAndSendKeys(txt_actualwt, actualwt);
	}

	/*-----Sometimes when the pack numbers sre not given as per the number of packs to be weighed ,
	 *  this method will take dynamically the required number of packs from the table and fills the weights  
	 *  and if the weight as per label and actual weight is not given for all pack numbers it will take the latest weight value
	 *   and give it to all pack numbers------*/

	public void enterWeightsBulk(String[] providedPackNumbers, String[] labelWeights, String[] actualWeights)
			throws Exception {

		String requiredCountStr = txt_packsToBeWeighed.getAttribute("value");
		int requiredCount = (requiredCountStr != null && !requiredCountStr.isEmpty())
				? Integer.parseInt(requiredCountStr)
				: 0;

		System.out.println("📋 Application requires " + requiredCount + " packs to be weighed.");

		// 2. Get all available pack numbers from the table rows
		List<WebElement> tableRows = driver
				.findElements(By.xpath("//tbody[@formarrayname='weightVarificationArray']/tr"));
		List<String> availablePacks = new ArrayList<>();
		for (WebElement row : tableRows) {
			String val = row.findElement(By.xpath(".//input[@formcontrolname='countNo']")).getAttribute("value");
			if (val != null)
				availablePacks.add(val.trim());
		}

		// 3. Determine which packs to actually enter
		List<String> finalPackList = new ArrayList<>();

		// First, try to add user-provided packs ONLY if they exist in the table
		if (providedPackNumbers != null && providedPackNumbers.length > 0 && !providedPackNumbers[0].trim().isEmpty()) {
			for (String p : providedPackNumbers) {
				String trimmed = p.trim();
				if (availablePacks.contains(trimmed)) {
					finalPackList.add(trimmed);
				} else {
					System.out.println(
							"⚠️ Provided pack '" + trimmed + "' not found in table. Will auto-pick a replacement.");
				}
			}
		}

		// 4. Fill the remaining spots dynamically from the available list
		if (finalPackList.size() < requiredCount) {
			System.out.println("ℹ️ Adding dynamic packs to meet the required count of " + requiredCount);
			for (String available : availablePacks) {
				if (finalPackList.size() >= requiredCount)
					break;
				if (!finalPackList.contains(available)) {
					finalPackList.add(available);
				}
			}
		}

		// 4. Enter weights for all selected packs using the fallback logic
		for (int i = 0; i < finalPackList.size(); i++) {
			String currentLabel = (i < labelWeights.length) ? labelWeights[i] : labelWeights[labelWeights.length - 1];
			String currentActual = (i < actualWeights.length) ? actualWeights[i]
					: actualWeights[actualWeights.length - 1];

			System.out.println("📦 Filling Pack " + finalPackList.get(i) + " [" + (i + 1) + "/" + requiredCount + "]");
			enterWeightsByPackNumber(finalPackList.get(i), currentLabel, currentActual);
		}
	}

	/*-----------------------Enter Weights for the given Pack Number--------------------------*/

	public void enterWeightsByPackNumber(String packNumber, String labelWeight, String actualWeight) {
		// 1. Get all rows in the table
		List<WebElement> rows = driver.findElements(By.xpath("//tbody[@formarrayname='weightVarificationArray']/tr"));

		WebElement targetRow = null;
		// 2. Iterate through rows to find the match using actual property value
		for (WebElement row : rows) {
			WebElement countInput = row.findElement(By.xpath(".//input[@formcontrolname='countNo']"));
			String currentPackNo = countInput.getAttribute("value"); // Reads property, not just attribute

			if (currentPackNo != null && currentPackNo.trim().equals(packNumber)) {
				targetRow = row;
				break;
			}
		}
		if (targetRow == null) {
			throw new RuntimeException(
					"❌ Pack Number '" + packNumber + "' not found in the weight verification table.");
		}
		// 3. Locate inputs in the identified row (using names from your HTML)
		WebElement labelInput = targetRow.findElement(By.xpath(".//input[@formcontrolname='grossWtPer']"));
		WebElement actualInput = targetRow.findElement(By.xpath(".//input[@formcontrolname='actualWt']"));
		// 4. Interaction
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", targetRow);

		labelInput.clear();
		waitAndSendKeys(labelInput, labelWeight);

		actualInput.clear();
		waitAndSendKeys(actualInput, actualWeight);
		System.out.println("✅ Correctly identified and filled Pack: " + packNumber);
	}

	/*-----------------------Upload Attachment If Displayed--------------------------*/

	@FindBy(xpath = "//input[@formcontrolname='attachment']")
	WebElement input_verificationAttachment;

	public void uploadAttachmentIfDisplayed(String filePath) {
		try {
			// Check if attachment field is displayed within 3 seconds
			if (input_verificationAttachment.isDisplayed()) {
				scrollToElement(input_verificationAttachment);
				waitAndSendKeys(input_verificationAttachment, filePath);
				System.out.println("✅ File attached successfully: " + filePath);
			}
		} catch (Exception e) {
			System.out.println("ℹ️ Attachment field not displayed, skipping upload.");
		}
	}

	/*--------------------To Click Start and End button----------------*/

	@FindBy(xpath = "//button[normalize-space()='Start']")
	WebElement button_start;

	public boolean startButtonisDisplayed() {
		try {
			return button_start.isDisplayed();
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public void clickStart() {
		waitForElementandClick(button_start);
	}

	@FindBy(xpath = "//span[normalize-space()='Weighing Area Cleaning']")
	WebElement button_weighingareacleaning;

	public void clickWeighingAreaCleaning() {
		waitForElementandClick(button_weighingareacleaning);
	}

	public boolean weighingAreaCleaningButtonisDisplayed() {
		try {
			return button_weighingareacleaning.isDisplayed();
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	@FindBy(xpath = "//button[normalize-space()='End']")
	WebElement button_end;

	public void clickEnd() {
		waitForElementandClick(button_end);
	}

	/*-----------------Weight Verification above 5000 casee---------------*/

	@FindBy(xpath = "//input[@formcontrolname='grossWeight']")
	WebElement txt_grossweight;

	public void enterGrossWeight(String grossweight) {
		waitAndSendKeys(txt_grossweight, grossweight);
	}

	@FindBy(xpath = "//input[@formcontrolname='actualWeight']")
	WebElement txt_tareweight;

	public void enterTareWeight(String tareweight) {
		waitAndSendKeys(txt_tareweight, tareweight);
	}

	/*-------------View/Weight Verification Buttons in Actions---------------------*/

	@FindBy(xpath = "//span[normalize-space()='View Weight Verification' or normalize-space()='Weight Verification']")
	WebElement button_unifiedWeightVerification;

	public void clickViewWeightVerification() {
		scrollAndClick(button_unifiedWeightVerification);
	}

	public boolean weightVerificationButtonisDisplayed() {
		try {
			return button_unifiedWeightVerification.isDisplayed();
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	/*-------------View Weighing Area Cleaning in Actions---------------------*/

	@FindBy(xpath = "//span[normalize-space()='View Weighing Area Cleaning']")
	WebElement button_viewweighingareacleaning;

	public void clickViewWeighingAreaCleaning() {
		scrollAndClick(button_viewweighingareacleaning);
	}

	public boolean viewWeighingAreaCleaningButtonisDisplayed() {
		try {
			return button_viewweighingareacleaning.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Finds a row in a table by its text (e.g. Batch No) and clicks the View icon
	 */
	public void clickViewIconInRow(String matchText) {
		String xpath = "//tr[td[contains(normalize-space(), '" + matchText + "')]]//i[contains(@class,'eye')] | "
				+ "//tr[td[contains(normalize-space(), '" + matchText + "')]]//button[contains(@class,'view')] | "
				+ "//tr[td[contains(normalize-space(), '" + matchText + "')]]//span[contains(@class,'eye')]";
		WebElement eyeIcon = driver.findElement(By.xpath(xpath));
		scrollAndClick(eyeIcon);
	}

	@FindBy(xpath = "//button[normalize-space()='Back']")
	WebElement button_back;

	public void clickBack() {
		scrollAndClick(button_back);
	}

	@FindBy(xpath = "//div[contains(@class,'p-dialog-header')]//button[contains(@class,'p-dialog-header-close')] | //button[@aria-label='Close']")
	WebElement button_closeModal;

	public void clickCloseModal() {
		scrollAndClick(button_closeModal);
	}

	@FindBy(xpath = "//button[normalize-space()='Submit']")
	WebElement button_submitReturn;

	public void clickSubmitStockReturn() {
		scrollAndClick(button_submitReturn);
	}

	public void clickWeightVerification() {
		clickViewWeightVerification();
	}

}
