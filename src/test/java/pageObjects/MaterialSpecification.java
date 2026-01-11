package pageObjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MaterialSpecification extends BasePage {

	public MaterialSpecification(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(xpath = "//ng-multiselect-dropdown[@formcontrolname='materialCode']//div/span")
	WebElement dropdwn_materialCode;

	public void selMaterialCode(String materialcode) {
		waitForElementandClick(dropdwn_materialCode);
		clickDropdownOption("//ng-multiselect-dropdown[@formcontrolname='materialCode']", materialcode);
	}

	@FindBy(xpath = "//input[@formcontrolname='specificationNumber']")
	WebElement txt_specificationnumber;

	public void specificationNumber(String specificationnumber) {
		txt_specificationnumber.clear();
		waitAndSendKeys(txt_specificationnumber, specificationnumber);
	}

	@FindBy(xpath = "//ng-multiselect-dropdown[@formcontrolname='requestType']//div/span")
	WebElement dropdwn_requestType;

	public void selRequestType(String requesttype) {
		waitForElementandClick(dropdwn_requestType);
		clickDropdownOption("//ng-multiselect-dropdown[@formcontrolname='requestType']", requesttype);
	}

	@FindBy(xpath = "//textarea[@formcontrolname='name']")
	List<WebElement> txt_nameofthetest;

	public void nameOfTheTest(String nameofthetest) {

		enterValueInLatestElement(txt_nameofthetest, nameofthetest);
	}

	@FindBy(xpath = "//textarea[@formcontrolname='limit']")
	List<WebElement> txt_specificationlimit;

	public void specificaitionLimit(String specificationlimit) {

		enterValueInLatestElement(txt_specificationlimit, specificationlimit);
	}

	@FindBy(xpath = "//select[@formcontrolname='validation']")
	List<WebElement> dropdwn_validation;

	public void selValidation(String validation) {
		WebElement latestValidation = clickLatestElement(dropdwn_validation);
		clickRelativeOption(latestValidation, "option", validation);
	}

	@FindBy(xpath = "//input[@formcontrolname='isSub']")
	List<WebElement> sel_reqsub;

	public void selReqSub() {
		clickLatestElement(sel_reqsub);
	}

	@FindBy(xpath = "//input[@formcontrolname='valMinVal']")
	List<WebElement> txt_specificationlimitMin;

	public void specificaitionLimitMin(String specificationlimitmin) {
		enterValueInLatestElement(txt_specificationlimitMin, specificationlimitmin);
	}

	@FindBy(xpath = "//input[@formcontrolname='valMaxVal']")
	List<WebElement> txt_specificationlimitMax;

	public void specificaitionLimitMax(String specificationlimitmax) {
		enterValueInLatestElement(txt_specificationlimitMax, specificationlimitmax);
	}

	@FindBy(xpath = "//p-autocomplete[@formcontrolname='uomId']//chevrondownicon")
	List<WebElement> dropdwn_uom;

	public void selUOM(String uom) throws Exception {
		clickLatestElement(dropdwn_uom);
		clickListOption(uom);
	}

	@FindBy(xpath = "//button[normalize-space()='Add']")
	WebElement btn_add;

	public void addButton() throws Exception {
		scrollAndClick(btn_add);
	}

	@FindBy(xpath = "//i[@class='fa fa-plus']")
	WebElement btn_plus;

	public void plusButton() throws Exception {
		scrollAndClick(btn_plus);
	}
}
