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
		// TODO Auto-generated constructor stub
	}
	

	
	
	@FindBy (xpath= "//ng-multiselect-dropdown[@formcontrolname='routeCode']//div/span")
	WebElement dropdwn_productCode;
	public void selProductCode(String productcode) {
		waitForElementandClick(dropdwn_productCode);
		WebElement productCodeXpath = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//ng-multiselect-dropdown[@formcontrolname='routeCode']//li[normalize-space()='"
						+ productcode + "']")));
		productCodeXpath.click();
	}
	
	@FindBy(xpath="//input[@formcontrolname='specificationNumber']")
	WebElement txt_specificationnumber;
	
	
	public void specificationNumber(String specificationnumber) {
		txt_specificationnumber.clear();
		waitAndSendKeys(txt_specificationnumber, specificationnumber);
	}
	
	
	
	@FindBy(xpath="//textarea[@formcontrolname='name']")
	List<WebElement> txt_nameofthetest;
	
	
	public void nameOfTheTest(String nameofthetest) {
		
		WebElement latestNameOfTheTest = txt_nameofthetest.get(txt_nameofthetest.size() - 1);
		latestNameOfTheTest.clear();
		waitAndSendKeys(latestNameOfTheTest, nameofthetest);
	}
	
	
	
	
	@FindBy(xpath="//textarea[@formcontrolname='limit']")
	List<WebElement> txt_specificationlimit;
	
	
	public void specificaitionLimit (String specificationlimit) {
		
		WebElement latestSpecificationLimit = txt_specificationlimit.get(txt_specificationlimit.size() - 1);
		latestSpecificationLimit.clear();
		waitAndSendKeys(latestSpecificationLimit, specificationlimit);
	}
	
	@FindBy(xpath="//select[@formcontrolname='validation']")
	List<WebElement> dropdwn_validation;

	public void selValidation(String validation) {
		
		WebElement latestValidation = dropdwn_validation.get(dropdwn_validation.size() - 1);
		waitForElementandClick(latestValidation);
		WebElement validationXpath = wait.until(ExpectedConditions.elementToBeClickable(
				latestValidation.findElement(By.xpath(".//option[normalize-space()='" + validation + "']"))));
		validationXpath.click();
	}
	
	@FindBy(xpath="//input[@formcontrolname='isSub']")
	List<WebElement> sel_reqsub;

	public void selReqSub() {
		WebElement latestReqSub = sel_reqsub.get(sel_reqsub.size() - 1);
		waitForElementandClick(latestReqSub);
	}
	
	@FindBy(xpath="//input[@formcontrolname='valMinVal']")
	List<WebElement> txt_specificationlimitMin;
	
	
	public void specificaitionLimitMin (String specificationlimitmin) {
		WebElement latestSpecificationLimitMin = txt_specificationlimitMin.get(txt_specificationlimitMin.size() - 1);
		latestSpecificationLimitMin.clear();
		waitAndSendKeys(latestSpecificationLimitMin, specificationlimitmin);
	}
	
	@FindBy(xpath="//input[@formcontrolname='valMaxVal']")
	List<WebElement> txt_specificationlimitMax;
	
	
	public void specificaitionLimitMax (String specificationlimitmax) {
		WebElement latestSpecificationLimitMAX = txt_specificationlimitMax.get(txt_specificationlimitMax.size() - 1);
		latestSpecificationLimitMAX.clear();
		waitAndSendKeys(latestSpecificationLimitMAX, specificationlimitmax);
	}
	
	@FindBy (xpath= "//p-autocomplete[@formcontrolname='uomId']//chevrondownicon")
	List<WebElement> dropdwn_uom;

	public void selUOM(String uom) throws Exception {
		WebElement latestUOM = dropdwn_uom.get(dropdwn_uom.size() - 1);
		latestUOM.click();
		WebElement uomXpath = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[normalize-space()='"+uom+"']")));
		((JavascriptExecutor) driver).executeScript(
				  "arguments[0].scrollIntoView({behavior: 'auto', block: 'center', inline: 'nearest'});", 
				  uomXpath);
		uomXpath.click();
	}
	
	
	@FindBy(xpath="//button[normalize-space()='Add']")
		WebElement btn_add;
	public void addButton() throws Exception {
        scrollAndClick(btn_add);	
	}
	
	
	@FindBy(xpath="//i[@class='fa fa-plus']")
	List<WebElement> btn_plus;

	public void plusButton() throws Exception {
		WebElement latestBtnPlus = btn_plus.get(btn_plus.size() - 1);
		scrollAndClick(latestBtnPlus);
	}
}
