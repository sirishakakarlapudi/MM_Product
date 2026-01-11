package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import utilities.DataStorage;
import utilities.ScreenshotUtil;

public class MaterialInwardForSolid extends BasePage {

	public MaterialInwardForSolid(WebDriver driver) {
		super(driver);

	}

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

	@FindBy(xpath = ("//input[@formcontrolname='noOfBatches']"))
	WebElement txt_noofbatches;

	public void enterNumberOfBatches(String noofbatches) {
		txt_noofbatches.clear();
		waitAndSendKeys(txt_noofbatches, noofbatches);
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

	@FindBy(xpath = "//button[normalize-space()='Save']")
	WebElement btn_save;

	public void clickSave(int index) throws Exception {
		WebElement latestElement = txt_noofpacks.get(index);
		// reuse existing method

		Actions actions = new Actions(driver);
		actions.doubleClick(latestElement).perform();

		wait.until(ExpectedConditions.elementToBeClickable(btn_save)).click();

	}

	@FindBy(xpath = "//span[normalize-space()='Pre-Inspection Report']")
	WebElement button_preinspectionreport;

	public void clickPreInspectionReport() {
		scrollAndClick(button_preinspectionreport);

	}

	@FindBy(xpath = "//span[normalize-space()='Review Pre-Inspection Report']")
	WebElement button_reviewpreinspectionreport;

	public void clickReviewPreInspectionReport() {
		scrollAndClick(button_reviewpreinspectionreport);

	}

	@FindBy(xpath = "//input[@formcontrolname='mainInputField' or @formcontrolname='subInputField']")
	List<WebElement> txt_alldescriptioninput;

	public void enterDescriptionInput(String descriptioninput, int index) {

		enterValueInElementAtIndex(txt_alldescriptioninput, index, descriptioninput);
	}

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

	public boolean areDescriptionInputsPresent() {
		try {
			// Check if list is not empty and the first element is displayed
			return txt_alldescriptioninput != null && !txt_alldescriptioninput.isEmpty()
					&& txt_alldescriptioninput.get(0).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean areCheckboxesPresent() {
		try {
			List<WebElement> checkboxes = driver.findElements(By.xpath("//input[contains(@class,'option-box')]"));
			return checkboxes != null && !checkboxes.isEmpty() && checkboxes.get(0).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Edits checklist items by providing a comma-separated string of values and a
	 * comma-separated string of indexes.
	 * 
	 * @param valuesString  Comma-separated values (e.g., "Yes,No,NA" or
	 *                      "Desc1,Desc2")
	 * @param indexesString Comma-separated indexes (e.g., "0,1,2")
	 */
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

	@FindBy(xpath = "//input[@type='file']")
	WebElement input_fileUpload;

	public void uploadFile(String filePath) {
		// File uploads MUST use standard sendKeys and cannot use JS .value injection
		// We use presenceOfElement because file inputs are often hidden (0x0 size)
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@type='file']")));
		input_fileUpload.sendKeys(filePath);
	}

	@FindBy(xpath = "//textarea[@formcontrolname='allEntryRemarks']")
	WebElement txt_remarks;

	public void enterRemarksPreInspection(String remarks) {
		waitAndSendKeys(txt_remarks, remarks);
	}

	@FindBy(xpath = "//span[normalize-space()='Edit Pre-Inspection Report']")
	WebElement button_editpreinspectionreport;

	public void clickEditPreInspectionReport() {
		scrollAndClick(button_editpreinspectionreport);

	}

	// Captured values for further flow
	public static String capturedInhouseBatchNumber = "";
	public static String capturedGRNNumber = "";

	@FindBy(xpath = "//input[@formcontrolname='grnNo']")
	WebElement grnNo_display;

	@FindBy(xpath = "//div[@formarrayname='batchDetails']//div[contains(@class,'col-md-12')]")
	List<WebElement> list_batchDetailsRows;

	@FindBy(xpath = "//span[normalize-space()='Weight Verification']")
	WebElement button_weightverification;

	/**
	 * Scans the weight verification dialog for a specific Mfg OR Inhouse Batch
	 * Number,
	 * captures both numbers and the GRN Number, saves them to
	 * runtimeData.properties,
	 * and clicks the corresponding row's Weight Verification button.
	 * 
	 * @param searchBatchNo The Mfg or Inhouse Batch Number to search for
	 */
	/**
	 * Scans the weight verification dialog for a specific Mfg OR Inhouse Batch
	 * Number,
	 * captures both numbers and the GRN Number, and saves them to
	 * runtimeData.properties.
	 * 
	 * @param searchBatchNo The Mfg or Inhouse Batch Number to search for
	 */
	public void captureWeightVerificationData(String searchBatchNo) {
		if (searchBatchNo == null || searchBatchNo.isEmpty()) {
			throw new RuntimeException("❌ Search Batch Number is empty.");
		}

		String target = searchBatchNo.trim();
		System.out.println("🔍 Capturing all associated batches for Mfg: [" + target + "]");

		// 1. Wait for values to load
		wait.until(d -> {
			String val = grnNo_display.getAttribute("value");
			return val != null && !val.trim().isEmpty();
		});

		capturedGRNNumber = grnNo_display.getAttribute("value").trim();
		DataStorage.save("GRN Number", capturedGRNNumber);

		By rowXpath = By.xpath("//div[@formarrayname='batchDetails']/div[contains(@class,'col-md-12')]");
		wait.until(ExpectedConditions.presenceOfElementLocated(rowXpath));
		wait.until(d -> {
			List<WebElement> r = d.findElements(rowXpath);
			if (r.isEmpty())
				return false;
			String v = r.get(0).findElement(By.xpath(".//input[@formcontrolname='mfgDate']")).getAttribute("value");
			return v != null && !v.trim().isEmpty();
		});

		// 2. Scan and store with Indexes (to support separate flows)
		List<WebElement> rows = driver.findElements(rowXpath);
		int matchCount = 0;
		String allInhouse = "";

		for (WebElement row : rows) {
			String mfg = row.findElement(By.xpath(".//input[@formcontrolname='mfgDate']")).getAttribute("value").trim();
			String inhouse = row.findElement(By.xpath(".//input[@formcontrolname='batchNo']")).getAttribute("value")
					.trim();

			if (mfg.equalsIgnoreCase(target)) {
				matchCount++;
				// Save as Indexed Key: e.g. LM-1Batch-112_1, LM-1Batch-112_2
				DataStorage.save(target + "_" + matchCount, inhouse);

				// Build comma list for overview
				allInhouse = allInhouse.isEmpty() ? inhouse : allInhouse + "," + inhouse;
				System.out.println("⭐ Match " + matchCount + ": " + inhouse);
			}
		}

		// Save Summary and Count
		if (matchCount > 0) {
			DataStorage.save(target, allInhouse);
			DataStorage.save(target + "_Count", String.valueOf(matchCount));
			System.out.println("✅ Total " + matchCount + " Inhouse batches found for Mfg: " + target);
		} else {
			throw new RuntimeException("❌ No matching rows found for Mfg Batch: " + target);
		}
	}

	/**
	 * Flexible method to click Weight Verification.
	 * 
	 * @param identifier  Can be Mfg Batch or Inhouse Batch
	 * @param indexOrType If a number (1, 2...), it treats identifier as Mfg Batch
	 *                    and clicks that index.
	 *                    If "B" or "Batch", it treats identifier as a literal
	 *                    Inhouse Batch (for complex pack numbers).
	 */
	public void clickWeightVerificationAction(String identifier, String indexOrType) {
		String target = identifier.trim();
		boolean clicked = false;
		int mfgOccurrence = 0;

		List<WebElement> rows = driver
				.findElements(By.xpath("//div[@formarrayname='batchDetails']/div[contains(@class,'col-md-12')]"));

		System.out.println("🖱️ Attempting Click: [" + target + "] with spec [" + indexOrType + "]");

		for (int i = 0; i < rows.size(); i++) {
			WebElement row = rows.get(i);
			String mfg = row.findElement(By.xpath(".//input[@formcontrolname='mfgDate']")).getAttribute("value").trim();
			String inhouse = row.findElement(By.xpath(".//input[@formcontrolname='batchNo']")).getAttribute("value")
					.trim();

			// Mode 1: Literal Search (for complex inhouse batches)
			if (indexOrType.equalsIgnoreCase("B") || indexOrType.equalsIgnoreCase("Batch")) {
				if (inhouse.equalsIgnoreCase(target)) {
					WebElement btn = row.findElement(By.xpath(".//button[contains(.,'Weight Verification')]"));
					scrollAndClick(btn);
					System.out.println("✅ Match Found! Clicked Inhouse Batch: " + inhouse);
					clicked = true;
					break;
				}
			}
			// Mode 2: Mfg Batch + Index
			else {
				if (mfg.equalsIgnoreCase(target)) {
					mfgOccurrence++;
					int targetIndex = Integer.parseInt(indexOrType);
					if (mfgOccurrence == targetIndex) {
						WebElement btn = row.findElement(By.xpath(".//button[contains(.,'Weight Verification')]"));
						scrollAndClick(btn);
						System.out.println("✅ Match Found! Clicked Mfg " + mfg + " at index " + mfgOccurrence);
						clicked = true;
						break;
					}
				}
			}
		}

		if (!clicked) {
			throw new RuntimeException(
					"❌ Weight Verification not found for [" + target + "] spec [" + indexOrType + "]");
		}
	}

	public void clickWeightVerification() {
		scrollAndClick(button_weightverification);

	}
}
