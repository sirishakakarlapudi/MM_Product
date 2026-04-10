package pageObjects;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import utilities.ScreenshotUtil;

public class AkronQuickProduct extends BasePage {

	public AkronQuickProduct(WebDriver driver) {
		super(driver);
	}

	/*
	 * =========================================================================
	 * [ LOCATORS ]
	 * =========================================================================
	 */

	@FindBy(xpath = "//input[@formcontrolname='name']")
	protected WebElement product_name;

	@FindBy(xpath = "//input[@formcontrolname='productCode']")
	protected WebElement product_code;

	@FindBy(xpath = "//input[@formcontrolname='description']")
	protected WebElement product_desc;
	
	@FindBy(xpath = "//ng-multiselect-dropdown[@formcontrolname='uomId']")
	protected WebElement product_uom;
	
	@FindBy(xpath = "//input[@formcontrolname='mainNdcNumber']")
	protected WebElement product_ndc_number;

	@FindBy(xpath = "//textarea[@formcontrolname='storageCondition']")
	protected WebElement storage_condition;

	@FindBy(xpath = "//ng-multiselect-dropdown[@formcontrolname='storageLocation']//div/span")
	protected WebElement storage_location_drpdwn;

	@FindBy(xpath = "//p-checkbox[@inputid='isSamplingRequired1']")
	protected WebElement sampling_required_yes;

	@FindBy(xpath = "//p-checkbox[@inputid='isSamplingRequired2']")
	protected WebElement sampling_required_no;

	@FindBy(xpath = "//p-checkbox[@inputid='isCleaningCheckBox1']")
	protected WebElement cleaning_agent_required_yes;

	@FindBy(xpath = "//p-checkbox[@inputid='isCleaningCheckBox2']")
	protected WebElement cleaning_agent_required_no;

	@FindBy(xpath = "//input[@formcontrolname='stageName']")
	protected List<WebElement> ndc_number_fields;

	@FindBy(xpath = "//input[@formcontrolname='shortCode']")
	protected List<WebElement> short_code;

	@FindBy(xpath = "//p-autocomplete[@formcontrolname='uom']//chevrondownicon")
	protected List<WebElement> uom_dropdown_icons;

	@FindBy(xpath = "//input[@formcontrolname='packSize']")
	protected List<WebElement> pack_size_fields;
	
	@FindBy(xpath = "//textarea[@formcontrolname='gtnNumber']")
	protected List<WebElement> gtn_number;

	@FindBy(xpath = "//input[@formcontrolname='stageDes']")
	protected List<WebElement> ndc_desc_fields;

	@FindBy(xpath = "//input[@formcontrolname='status']")
	protected List<WebElement> ndc_status;

	/*
	 * =========================================================================
	 * [ BASIC ACTIONS ]
	 * =========================================================================
	 */

	public void productName(String name) {
		if (name != null && !name.trim().isEmpty()) {
			product_name.clear();
			waitAndSendKeys(product_name, name);
		}
	}

	public void clickProductName() {
		product_name.click();;
	}

	public void clickProduct() throws Throwable {
		ScreenshotUtil.capture();
		product_name.click();
	}

	public void productCode(String code) {
		if (code != null && !code.trim().isEmpty()) {
			product_code.clear();
			waitAndSendKeys(product_code, code);
		}
	}

	public void productNDCNumber(String productndcnumber) {
		if (productndcnumber != null && !productndcnumber.trim().isEmpty()) {
			product_ndc_number.clear();
			waitAndSendKeys(product_ndc_number, productndcnumber);
		}
	}
	public void productDesc(String desc) {
		if (desc != null && !desc.trim().isEmpty()) {
			product_desc.clear();
			waitAndSendKeys(product_desc, desc);
		}
	}

	public void storageCondition(String storagecondition) {
		if (storagecondition != null && !storagecondition.trim().isEmpty()) {
			storage_condition.clear();
			waitAndSendKeys(storage_condition, storagecondition);
		}
	}

	public void checkSamplingActivity(String value) {
		if ("Yes".equalsIgnoreCase(value)) {
			waitForElementandClick(sampling_required_yes);
		} else {
			waitForElementandClick(sampling_required_no);
		}
	}

	public void checkCleaningAgent(String value) {
		if ("Yes".equalsIgnoreCase(value)) {
			waitForElementandClick(cleaning_agent_required_yes);
		} else {
			waitForElementandClick(cleaning_agent_required_no);
		}
	}

	public void ndcNumber(String ndcnumber) {

		enterValueInLatestElement(ndc_number_fields, ndcnumber);
	}

	public int getRowIndexByNDCNumber(String currentNdcNumber) {
		for (int i = 0; i < ndc_number_fields.size(); i++) {
			String val = ndc_number_fields.get(i).getAttribute("value").trim();
			if (val.equalsIgnoreCase(currentNdcNumber)) {
				return i;
			}
		}
		throw new RuntimeException("Could not find row with NDC Number: " + currentNdcNumber);
	}

	public void ndcNumber_AtIndex(String ndcnumber, int index) {
		enterValueInElementAtIndex(ndc_number_fields, index, ndcnumber);
	}

	public void shortCode(String shortcode) {

		enterValueInLatestElement(short_code, shortcode);
	}

	public void shortCode_AtIndex(String shortcode, int index) {
		enterValueInElementAtIndex(short_code, index, shortcode);
	}

	public void packSize(String packsize) {

		enterValueInLatestElement(pack_size_fields, packsize);
	}
	
	
	public void packSize_AtIndex(String packsize, int index) {
		enterValueInElementAtIndex(pack_size_fields, index, packsize);
	}
	
	public void gtnNumber(String gtnNumber) {
		enterValueInLatestElement(gtn_number, gtnNumber);
	}

	public void gtnNumber_AtIndex(String gtnNumber, int index) {
		enterValueInElementAtIndex(gtn_number, index, gtnNumber);
	}

	public void ndcDesc(String ndcdesc) {

		enterValueInLatestElement(ndc_desc_fields, ndcdesc);
	}

	public void ndcDesc_AtIndex(String ndcdesc, int index) {
		enterValueInElementAtIndex(ndc_desc_fields, index, ndcdesc);
	}

	/*
	 * =========================================================================
	 * [ BUSINESS ACTIONS ]
	 * =========================================================================
	 */

	public void selStorageLocation(String location) {
		waitForElementandClick(storage_location_drpdwn);
		WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//ng-multiselect-dropdown[@formcontrolname='storageLocation']//li[normalize-space()='"
						+ location + "']")));
		option.click();
	}
	
	
	public void selProductUom(String productuom) {
		waitForElementandClick(product_uom);
		WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//ng-multiselect-dropdown[@formcontrolname='uomId']//li[normalize-space()='"
						+ productuom + "']")));
		option.click();
	}

	public void selUOM(String uom) {
		WebElement icon = uom_dropdown_icons.get(uom_dropdown_icons.size() - 1);
		icon.click();
		WebElement option = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//p-autocomplete[@formcontrolname='uom']//li[normalize-space()='"+ uom +"']")));
		((JavascriptExecutor) driver).executeScript(
				"arguments[0].scrollIntoView({behavior: 'auto', block: 'center', inline: 'nearest'});",
				option);
		option.click();
	}

	public void enterRow() throws Exception {
		Robot r = new Robot();
		WebElement lastField = ndc_desc_fields.get(ndc_desc_fields.size() - 1);
		lastField.click();
		r.keyPress(KeyEvent.VK_ENTER);
		r.keyRelease(KeyEvent.VK_ENTER);
		Thread.sleep(500); // Wait for row to be added
	}

	public void enterRow_for_addndc() throws Exception {
		Robot r = new Robot();
		WebElement lastField = ndc_status.get(ndc_status.size() - 1);
		lastField.click();
		r.keyPress(KeyEvent.VK_ENTER);
		r.keyRelease(KeyEvent.VK_ENTER);
		Thread.sleep(500); // Wait for row to be added
	}

	public void delRow() throws Exception {
		Robot r = new Robot();
		WebElement lastField = ndc_desc_fields.get(ndc_desc_fields.size() - 1);
		lastField.click();
		r.keyPress(KeyEvent.VK_DELETE);
		r.keyRelease(KeyEvent.VK_DELETE);
		Thread.sleep(500); // Wait for row to be deleted
	}

	public void delRow_AtIndex(int index) throws Exception {
		Robot r = new Robot();
		WebElement lastField = ndc_desc_fields.get(index);
		lastField.click();
		r.keyPress(KeyEvent.VK_DELETE);
		r.keyRelease(KeyEvent.VK_DELETE);
		Thread.sleep(500); // Wait for row to be deleted
	}

	/*
	 * =========================================================================
	 * [ GETTERS / HELPERS ]
	 * =========================================================================
	 */

	public WebElement getProductNameField() {
		return product_name;
	}

}
