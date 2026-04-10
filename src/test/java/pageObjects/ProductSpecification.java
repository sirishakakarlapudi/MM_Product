package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ProductSpecification extends BasePage {

	public ProductSpecification(WebDriver driver) {
		super(driver);
	}

	/*
	 * ========================================================================= [
	 * LOCATORS ]
	 * =========================================================================
	 */

	@FindBy(xpath = "//ng-multiselect-dropdown[@formcontrolname='routeCode']//div/span")
	protected WebElement productspec_productcode_drpdwn;

	@FindBy(xpath = "//input[@formcontrolname='specificationNumber']")
	protected WebElement productspec_specification_number;

	@FindBy(xpath = "//textarea[@formcontrolname='name']")
	protected List<WebElement> productspec_name_of_test;

	@FindBy(xpath = "//textarea[@formcontrolname='limit']")
	protected List<WebElement> productspec_specification_limit;

	@FindBy(xpath = "//select[@formcontrolname='validation']")
	protected List<WebElement> productspec_validation_drpdwn;

	@FindBy(xpath = "//input[@formcontrolname='isSub']")
	protected List<WebElement> productspec_req_sub;

	@FindBy(xpath = "//input[@formcontrolname='valMinVal']")
	protected List<WebElement> productspec_specification_limit_min;

	@FindBy(xpath = "//input[@formcontrolname='valMaxVal']")
	protected List<WebElement> productspec_specification_limit_max;

	@FindBy(xpath = "//p-autocomplete[@formcontrolname='uomId']//chevrondownicon")
	protected List<WebElement> productspec_uom_drpdwn;

	@FindBy(xpath = "//button[normalize-space()='Add']")
	protected WebElement btn_add;

	@FindBy(xpath = "//i[@class='fa fa-plus']")
	protected List<WebElement> btn_plus;

	/*
	 * ========================================================================= [
	 * BASIC ACTIONS ]
	 * =========================================================================
	 */

	public void selProductCode(String productcode) {
		if (productcode != null && !productcode.trim().isEmpty()) {
			waitForElementandClick(productspec_productcode_drpdwn);
			WebElement productCodeXpath = wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//ng-multiselect-dropdown[@formcontrolname='routeCode']//li[normalize-space()='"
							+ productcode + "']")));
			productCodeXpath.click();
		}
	}

	public void specificationNumber(String specificationnumber) {
		if (specificationnumber != null && !specificationnumber.trim().isEmpty()) {
			productspec_specification_number.clear();
			waitAndSendKeys(productspec_specification_number, specificationnumber);
		}
	}

	public void nameOfTheTest(String nameofthetest) {
		if (nameofthetest != null && !nameofthetest.trim().isEmpty()) {
			WebElement latestNameOfTheTest = productspec_name_of_test.get(productspec_name_of_test.size() - 1);
			latestNameOfTheTest.clear();
			waitAndSendKeys(latestNameOfTheTest, nameofthetest);
		}
	}

	public void specificaitionLimit(String specificationlimit) {
		if (specificationlimit != null && !specificationlimit.trim().isEmpty()) {
			WebElement latestSpecificationLimit = productspec_specification_limit
					.get(productspec_specification_limit.size() - 1);
			latestSpecificationLimit.clear();
			waitAndSendKeys(latestSpecificationLimit, specificationlimit);
		}
	}

	public void selValidation(String validation) {
		if (validation != null && !validation.trim().isEmpty()) {
			WebElement latestValidation = productspec_validation_drpdwn.get(productspec_validation_drpdwn.size() - 1);
			waitForElementandClick(latestValidation);
			WebElement validationXpath = wait.until(ExpectedConditions.elementToBeClickable(
					latestValidation.findElement(By.xpath(".//option[normalize-space()='" + validation + "']"))));
			validationXpath.click();
		}
	}

	public void selReqSub() {
		WebElement latestReqSub = productspec_req_sub.get(productspec_req_sub.size() - 1);
		waitForElementandClick(latestReqSub);
	}

	public void specificaitionLimitMin(String specificationlimitmin) {
		if (specificationlimitmin != null && !specificationlimitmin.trim().isEmpty()) {
			WebElement latestMin = productspec_specification_limit_min.get(productspec_specification_limit_min.size() - 1);
			latestMin.clear();
			waitAndSendKeys(latestMin, specificationlimitmin);
		}
	}

	public void specificaitionLimitMax(String specificationlimitmax) {
		if (specificationlimitmax != null && !specificationlimitmax.trim().isEmpty()) {
			WebElement latestMax = productspec_specification_limit_max.get(productspec_specification_limit_max.size() - 1);
			latestMax.clear();
			waitAndSendKeys(latestMax, specificationlimitmax);
		}
	}

	public void selUOM(String uom) throws Exception {
		if (uom != null && !uom.trim().isEmpty()) {
			WebElement latestUOM = productspec_uom_drpdwn.get(productspec_uom_drpdwn.size() - 1);
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
}
