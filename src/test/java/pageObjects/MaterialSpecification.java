package pageObjects;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class MaterialSpecification extends BasePage {

	public MaterialSpecification(WebDriver driver) {
		super(driver);
	}

	/*
	 * ========================================================================= [
	 * LOCATORS ]
	 * =========================================================================
	 */

	@FindBy(xpath = "//ng-multiselect-dropdown[@formcontrolname='materialCode']//div/span")
	protected WebElement materialspec_materialcode_drpdwn;

	@FindBy(xpath = "//input[@formcontrolname='specificationNumber']")
	protected WebElement materialspec_specification_number;

	@FindBy(xpath = "//ng-multiselect-dropdown[@formcontrolname='requestType']//div/span")
	protected WebElement materialspec_requestType_drpdwn;

	@FindBy(xpath = "//textarea[@formcontrolname='name']")
	protected List<WebElement> materialspec_name_of_test;

	@FindBy(xpath = "//textarea[@formcontrolname='limit']")
	protected List<WebElement> materialspec_specification_limit;

	@FindBy(xpath = "//select[@formcontrolname='validation']")
	protected List<WebElement> materialspec_validation_drpdwn;

	@FindBy(xpath = "//input[@formcontrolname='isSub']")
	protected List<WebElement> materialspec_req_sub;

	@FindBy(xpath = "//input[@formcontrolname='valMinVal']")
	protected List<WebElement> materialspec_specification_limit_min;

	@FindBy(xpath = "//input[@formcontrolname='valMaxVal']")
	protected List<WebElement> materialspec_specification_limit_max;

	@FindBy(xpath = "//p-autocomplete[@formcontrolname='uomId']//chevrondownicon")
	protected List<WebElement> materialspec_uom_drpdwn;

	@FindBy(xpath = "//button[normalize-space()='Add']")
	protected WebElement btn_add;

	@FindBy(xpath = "//i[@class='fa fa-plus']")
	protected List<WebElement> btn_plus;

	/*
	 * ========================================================================= [
	 * BASIC ACTIONS ]
	 * =========================================================================
	 */

	public void selMaterialCode(String materialcode) {
		if (materialcode != null && !materialcode.trim().isEmpty()) {
			waitForElementandClick(materialspec_materialcode_drpdwn);
			WebElement materialCodeXpath = wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//ng-multiselect-dropdown[@formcontrolname='materialCode']//li[normalize-space()='"
							+ materialcode + "']")));
			materialCodeXpath.click();
		}
	}

	public void specificationNumber(String specificationnumber) {
		if (specificationnumber != null && !specificationnumber.trim().isEmpty()) {
			materialspec_specification_number.clear();
			waitAndSendKeys(materialspec_specification_number, specificationnumber);
		}
	}

	public void selRequestType(String requesttype) {
		if (requesttype != null && !requesttype.trim().isEmpty()) {
			waitForElementandClick(materialspec_requestType_drpdwn);
			WebElement requestTypeXpath = wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//ng-multiselect-dropdown[@formcontrolname='requestType']//li[normalize-space()='"
							+ requesttype + "']")));
			requestTypeXpath.click();
		}
	}

	public void nameOfTheTest(String nameofthetest) {
		if (nameofthetest != null && !nameofthetest.trim().isEmpty()) {
			WebElement latestNameOfTheTest = materialspec_name_of_test.get(materialspec_name_of_test.size() - 1);
			latestNameOfTheTest.clear();
			waitAndSendKeys(latestNameOfTheTest, nameofthetest);
		}
	}

	public void specificaitionLimit(String specificationlimit) {
		if (specificationlimit != null && !specificationlimit.trim().isEmpty()) {
			WebElement latestSpecificationLimit = materialspec_specification_limit
					.get(materialspec_specification_limit.size() - 1);
			latestSpecificationLimit.clear();
			waitAndSendKeys(latestSpecificationLimit, specificationlimit);
		}
	}

	public void selValidation(String validation) {
		if (validation != null && !validation.trim().isEmpty()) {
			WebElement latestValidation = materialspec_validation_drpdwn.get(materialspec_validation_drpdwn.size() - 1);
			waitForElementandClick(latestValidation);
			WebElement validationXpath = wait.until(ExpectedConditions.elementToBeClickable(
					latestValidation.findElement(By.xpath(".//option[normalize-space()='" + validation + "']"))));
			validationXpath.click();
		}
	}

	public void selReqSub() {
		WebElement latestReqSub = materialspec_req_sub.get(materialspec_req_sub.size() - 1);
		waitForElementandClick(latestReqSub);
	}

	public void specificaitionLimitMin(String specificationlimitmin) {
		if (specificationlimitmin != null && !specificationlimitmin.trim().isEmpty()) {
			WebElement latestMin = materialspec_specification_limit_min.get(materialspec_specification_limit_min.size() - 1);
			latestMin.clear();
			waitAndSendKeys(latestMin, specificationlimitmin);
		}
	}

	public void specificaitionLimitMax(String specificationlimitmax) {
		if (specificationlimitmax != null && !specificationlimitmax.trim().isEmpty()) {
			WebElement latestMax = materialspec_specification_limit_max.get(materialspec_specification_limit_max.size() - 1);
			latestMax.clear();
			waitAndSendKeys(latestMax, specificationlimitmax);
		}
	}

	public void selUOM(String uom) throws Exception {
		if (uom != null && !uom.trim().isEmpty()) {
			WebElement latestUOM = materialspec_uom_drpdwn.get(materialspec_uom_drpdwn.size() - 1);
			latestUOM.click();
			WebElement uomXpath = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[normalize-space()='" + uom + "']")));
			((JavascriptExecutor) driver).executeScript(
					"arguments[0].scrollIntoView({behavior: 'auto', block: 'center', inline: 'nearest'});", uomXpath);
			uomXpath.click();
		}
	}

	public void addButton() throws Exception {
		scrollAndClick(btn_add);
	}

	public void plusButton() throws Exception {
		WebElement latestBtnPlus = btn_plus.get(btn_plus.size() - 1);
		scrollAndClick(latestBtnPlus);
	}
    
    // Additional methods for consistency with ProductSpecification flows
    public void createSubmit() {
        clickSubmit();
    }
}
