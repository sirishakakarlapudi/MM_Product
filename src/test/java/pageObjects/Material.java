package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class Material extends BasePage {

	public Material(WebDriver driver) {
		super(driver);
	}

	/*
	 * ========================================================================= [
	 * LOCATORS ]
	 * =========================================================================
	 */

	@FindBy(xpath = "//input[@formcontrolname='materialName']")
	protected WebElement material_name;

	@FindBy(xpath = "//input[@formcontrolname='materialCode']")
	protected WebElement material_code;

	@FindBy(xpath = "//ng-multiselect-dropdown[@formcontrolname='materialCategory']")
	protected WebElement drpdwn_material_category;
	
	@FindBy(xpath = "//ng-multiselect-dropdown[@formcontrolname='materialNameOrCode']")
	protected WebElement drpdwn_material_name_code;

	@FindBy(xpath = "//ng-multiselect-dropdown[@formcontrolname='typeOfMaterial']")
	protected WebElement drpdwn_type_of_material;

	@FindBy(xpath = "//ng-multiselect-dropdown[@formcontrolname='uomId']")
	protected WebElement drpdwn_uom;

	@FindBy(xpath = "//input[@formcontrolname='storageCondition']")
	protected WebElement material_storage_condition;

	@FindBy(xpath = "//ng-multiselect-dropdown[@formcontrolname='storageLocation']")
	protected WebElement drpdwn_storage_location;

	// Checkboxes for various activities
	@FindBy(xpath = "//p-checkbox[@inputid='isSamplingRequired1']")
	protected WebElement chk_sampling_required_yes;

	@FindBy(xpath = "//p-checkbox[@inputid='isSamplingRequired2']")
	protected WebElement chk_sampling_required_no;

	@FindBy(xpath = "//p-checkbox[@inputid='isDesCheckBox1']")
	protected WebElement chk_dispensing_required_yes;

	@FindBy(xpath = "//p-checkbox[@inputid='isDesCheckBox2']")
	protected WebElement chk_dispensing_required_no;

	@FindBy(xpath = "//p-checkbox[@inputid='isSampleCheckBox1']")
	protected WebElement chk_mixed_analysis_yes;
	
	@FindBy(xpath= "//label[normalize-space()='Mixed Analysis']")
	protected WebElement mixed_analysis_label;
	
	@FindBy(xpath= "//label[normalize-space()='Receiving Bay']")
	protected WebElement receiving_bay_label;

	@FindBy(xpath = "//p-checkbox[@inputid='isSampleCheckBox2']")
	protected WebElement chk_mixed_analysis_no;

	@FindBy(xpath = "//p-checkbox[@inputid='isWeightCheckBox1']")
	protected WebElement chk_weight_verification_yes;

	@FindBy(xpath = "//p-checkbox[@inputid='isWeightCheckBox2']")
	protected WebElement chk_weight_verification_no;

	@FindBy(xpath = "//p-checkbox[@inputid='isReceivingCheckBox1']")
	protected WebElement chk_receiving_bay_yes;

	@FindBy(xpath = "//p-checkbox[@inputid='isReceivingCheckBox2']")
	protected WebElement chk_receiving_bay_no;

	@FindBy(xpath = "//p-checkbox[@inputid='isCleaningCheckBox1']")
	protected WebElement chk_cleaning_agent_yes;

	@FindBy(xpath = "//p-checkbox[@inputid='isCleaningCheckBox2']")
	protected WebElement chk_cleaning_agent_no;

	@FindBy(xpath = "//p-autocomplete[@formcontrolname='supplierName']//div//div/span")
	protected WebElement supplier_name_input;

	@FindBy(xpath = "//p-checkbox[@inputid='ismanufacturerCheckBox1']")
	protected WebElement chk_manufacturer_yes;

	@FindBy(xpath = "//p-checkbox[@inputid='ismanufacturerCheckBox2']")
	protected WebElement chk_manufacturer_no;

	@FindBy(xpath = "//p-autocomplete[@formcontrolname='manufactureName']//div//div/span")
	protected WebElement manufacturer_name_input;

	@FindBy(xpath = "//button[normalize-space()='Save Details']")
	protected WebElement btn_save_details;

	@FindBy(xpath = "//button[normalize-space()='Yes']")
	protected WebElement btn_dialog_yes;

	@FindBy(xpath = "//button[normalize-space()='No']")
	protected WebElement btn_dialog_no;

	@FindBy(xpath = "//p-autocomplete[@formcontrolname='supplierName']//chevrondownicon")
	protected WebElement supplier_arrow;

	@FindBy(xpath = "//p-autocomplete[@formcontrolname='manufactureName']//chevrondownicon")
	protected WebElement manufacturer_arrow;

	/*
	 * ========================================================================= [
	 * BASIC ACTIONS ]
	 * =========================================================================
	 */

	public void materialName(String name) {
		if (name != null) {
			material_name.clear();
			waitAndSendKeys(material_name, name);
		}
	}

	public void materialCode(String code) {
		if (code != null) {
			material_code.clear();
			waitAndSendKeys(material_code, code);
		}
	}

	public void selMaterialCategory(String category) {
		waitForElementandClick(drpdwn_material_category);
		WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//ng-multiselect-dropdown[@formcontrolname='materialCategory']//li[normalize-space()='"
						+ category + "']")));
		option.click();
	}

	public void selMaterialName_selMaterialCode(String name_code) {
		waitForElementandClick(drpdwn_material_name_code);
		WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//ng-multiselect-dropdown[@formcontrolname='materialNameOrCode']//li[normalize-space()='"
						+ name_code + "']")));
		option.click();
	}
	
	public void selTypeOfMaterial(String type) {
		waitForElementandClick(drpdwn_type_of_material);
		WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//ng-multiselect-dropdown[@formcontrolname='typeOfMaterial']//li[normalize-space()='" + type
						+ "']")));
		option.click();
	}

	public void selUOM(String uom) {
		waitForElementandClick(drpdwn_uom);
		WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//ng-multiselect-dropdown[@formcontrolname='uomId']//li[normalize-space()='" + uom + "']")));
		option.click();
	}

	public void storageCondition(String condition) {
		if (condition != null) {
			material_storage_condition.clear();
			waitAndSendKeys(material_storage_condition, condition);
		}
	}
	public boolean isStorageConditionDisplayed() {
		try {
			return material_storage_condition.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public void selStorageLocation(String location) {
		waitForElementandClick(drpdwn_storage_location);
		WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//ng-multiselect-dropdown[@formcontrolname='storageLocation']//li[normalize-space()='"
						+ location + "']")));
		option.click();
	}

	public void setSamplingActivity(String value) {
		if ("Yes".equalsIgnoreCase(value)) {
			waitForElementandClick(chk_sampling_required_yes);
		} else {
			waitForElementandClick(chk_sampling_required_no);
		}
	}

	public void setDispensingActivity(String value) {
		if ("Yes".equalsIgnoreCase(value)) {
			waitForElementandClick(chk_dispensing_required_yes);
		} else {
			waitForElementandClick(chk_dispensing_required_no);
		}
	}

	public void setMixedAnalysis(String value) {
		if ("Yes".equalsIgnoreCase(value)) {
			waitForElementandClick(chk_mixed_analysis_yes);
		} else {
			waitForElementandClick(chk_mixed_analysis_no);
		}
	}
	public boolean isMixedAnalysisIsDisplayed() {
		try {
			return mixed_analysis_label.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public void setWeightVerification(String value) {
		if ("Yes".equalsIgnoreCase(value)) {
			waitForElementandClick(chk_weight_verification_yes);
		} else {
			waitForElementandClick(chk_weight_verification_no);
		}
	}

	public void setReceivingBay(String value) {
		if ("Yes".equalsIgnoreCase(value)) {
			waitForElementandClick(chk_receiving_bay_yes);
		} else {
			waitForElementandClick(chk_receiving_bay_no);
		}
	}

	
	
	public boolean isReceivingBayIsDisplayed() {
		try {
			return receiving_bay_label.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}
	
	public void setCleaningAgent(String value) {
		if ("Yes".equalsIgnoreCase(value)) {
			waitForElementandClick(chk_cleaning_agent_yes);
		} else {
			waitForElementandClick(chk_cleaning_agent_no);
		}
	}

	public void selSupplier(String name) {
		waitForElementandClick(supplier_arrow);
		waitForLoading();
		WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//p-autocomplete[@formcontrolname='supplierName']//li[normalize-space()='" + name + "']")));
		option.click();
	}

	public void setManufacturerRequired(String value) {
		if ("Yes".equalsIgnoreCase(value)) {
			waitForElementandClick(chk_manufacturer_yes);
		} else {
			waitForElementandClick(chk_manufacturer_no);
		}
	}

	public void selManufacturer(String name) {
		waitForElementandClick(manufacturer_arrow);
		waitForLoading();
		WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath(
						"//p-autocomplete[@formcontrolname='manufactureName']//li[normalize-space()='" + name + "']")));
		option.click();
	}

	public void clickSaveDetails() {
		waitForElementandClick(btn_save_details);
	}

	public void confirmDialog(String value) {
		if ("Yes".equalsIgnoreCase(value)) {
			waitForElementandClick(btn_dialog_yes);
		} else {
			waitForElementandClick(btn_dialog_no);
		}
	}
}
