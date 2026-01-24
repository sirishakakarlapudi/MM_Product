package pageObjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class QualityProcess extends BasePage {

	public QualityProcess(WebDriver driver) {
		super(driver);

	}

	/*------------------------------Assign AR Number-------------------*/

	@FindBy(xpath = "//span[normalize-space()='Assign AR Number']")
	WebElement button_assignarnumber;

	public void clickAssignARNumber() {
		waitForElementandClick(button_assignarnumber);

	}

	@FindBy(xpath = ("//input[@formcontrolname='updateArNO']"))
	WebElement txt_enterarno;

	public void enterARNO(String noofbatches) {
		txt_enterarno.clear();
		waitAndSendKeys(txt_enterarno, noofbatches);
	}

	@FindBy(xpath = "//button[normalize-space()='Submit']")
	WebElement button_submit;

	public void clickSubmit() {
		waitForElementandClick(button_submit);
	}

	/*-------------------Sampling Start button in Actions and pop up----------------*/

	@FindBy(xpath = "//span[normalize-space()='Sampling Start'] | //button[normalize-space()='Sampling Start']")
	WebElement button_samplingstart;

	public void clickSamplingStart() {
		waitForElementandClick(button_samplingstart);

	}

	@FindBy(xpath = "//div[@role='dialog']//button[normalize-space()='Sampling Start']")
	WebElement button_samplingstart_popup;

	public void clickSamplingStartInPopup() {
		waitForElementandClick(button_samplingstart_popup);

	}

	public boolean samplingStartButtonisDisplayed() {
		try {
			return button_samplingstart.isDisplayed();
		} catch (NoSuchElementException e) {
			return false;
		}

	}
	/*---------------------Sampling Flow---------------------------*/

	@FindBy(xpath = ("//input[@formcontrolname='sample']"))
	WebElement txt_samplequantity;

	public void enterSampleQuantity(String samplequantity) {
		txt_samplequantity.clear();
		waitAndSendKeys(txt_samplequantity, samplequantity);
	}

	@FindBy(xpath = ("//input[@formcontrolname='isCheck']"))
	List<WebElement> check_packnumbers;

	@FindBy(xpath = ("//input[@formcontrolname='containerNo']"))
	List<WebElement> packnumbers;

	public boolean packNumbersisDisplayed() {
		{
			return packnumbers.size() > 0 && packnumbers.get(0).isDisplayed();
		}
	}

	@FindBy(xpath = "//input[@formcontrolname='result']")
	WebElement txt_packsToBeSampled;

	/*------------Sometimes when the pack numbers are not given as per the number of packs to be sampled ,
	 this method will take dynamically the required number of packs from the table and checks ------*/

	public void checkPackNumbersBulk(String[] providedPackNumbers) throws Exception {
		waitForElementToVisible(txt_packsToBeSampled);
		String requiredCountStr = txt_packsToBeSampled.getAttribute("value");
		int requiredCount = (requiredCountStr != null && !requiredCountStr.isEmpty())
				? Integer.parseInt(requiredCountStr)
				: 0;

		System.out.println("📋 Application requires " + requiredCount + " packs to be sampled.");

		// 2. Get all available pack numbers from the table rows
		List<WebElement> tableRows = driver
				.findElements(By.xpath("//div[@formarrayname='packNo']/div"));
		List<String> availablePacks = new ArrayList<>();
		for (WebElement row : tableRows) {
			String val = row.findElement(By.xpath(".//input[@formcontrolname='containerNo']")).getAttribute("value");
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
			System.out.println("📦 Checked Pack " + finalPackList.get(i) + " [" + (i + 1) + "/" + requiredCount + "]");
			checkByPackNumber(finalPackList.get(i));
		}
	}

	/*-----------------------Check Packs for the given Pack Number--------------------------*/

	public void checkByPackNumber(String packNumber) {
		// 1. Get all rows in the table
		List<WebElement> rows = driver.findElements(By.xpath("//div[@formarrayname='packNo']/div"));

		WebElement targetRow = null;
		// 2. Iterate through rows to find the match using actual property value
		for (WebElement row : rows) {
			WebElement countInput = row.findElement(By.xpath(".//input[@formcontrolname='containerNo']"));
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
		WebElement checkBoxPackNumber = targetRow.findElement(By.xpath(".//input[@formcontrolname='isCheck']"));
		// 4. Interaction
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", targetRow);

		waitForElementandClick(checkBoxPackNumber);

		System.out.println("✅ Correctly identified and checked Pack: " + packNumber);
	}

	/*-----------------------Sampling CheckBook in Create Sampling------------------*/

	@FindBy(xpath = "//ng-multiselect-dropdown[@formcontrolname='samplingRoomId']")
	WebElement drpdwn_samplingroomid;

	public void selSamplingRoomId(String samplingroomid) {
		waitForElementandClick(drpdwn_samplingroomid);
		clickRelativeOption(drpdwn_samplingroomid, "li", samplingroomid);

	}

	@FindBy(xpath = "//ng-multiselect-dropdown[@formcontrolname='weightBalanceId']")
	WebElement drpdwn_weighingbalanceid;

	public void selWeighingBalanceId(String weighingbalanceid) {
		waitForElementandClick(drpdwn_weighingbalanceid);
		clickRelativeOption(drpdwn_weighingbalanceid, "li", weighingbalanceid);

	}

	public boolean wweighingBalanceIDisDisplayed() {
		try {
			return drpdwn_weighingbalanceid.isDisplayed();
		} catch (NoSuchElementException e) {
			return false;
		}

	}
	
	/*--------------------View Material Sampling button in Actions--------------*/
	
	@FindBy(xpath = "//span[normalize-space()='View Raw Material Sampling']")
	WebElement button_viewmaterialsampling;

	public void clickViewMaterialSampling() {
		waitForElementandClick(button_viewmaterialsampling);
	}
	
	
	
	/*----------------------------Test Result button in Actions-------------------*/
	
	@FindBy(xpath = "//span[normalize-space()='Test Result']")
	WebElement button_testresult;

	public void clickTestResult() {
		waitForElementandClick(button_testresult);
	}
	
	
	/*--------------------Create Test Report-----------------*/
	
	
	
	

}
