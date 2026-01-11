package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class MaterialCategory extends BasePage {

	public MaterialCategory(WebDriver driver) {
		super(driver);
		
	}
	
	@FindBy(xpath="//input[@formcontrolname='name']")
	WebElement txt_materialcategory;
	
	public void materialCategoryName(String materialcategory) {
		txt_materialcategory.clear();
		waitAndSendKeys(txt_materialcategory, materialcategory);
	}
	
	
	
	
	@FindBy(xpath = "//ng-multiselect-dropdown[@formcontrolname='sampledContainers']")
	WebElement dropdwn_samplingplan;

	public void selSamplingPlan(String samplingplan) {
		waitForElementandClick(dropdwn_samplingplan);
		WebElement samplingplanXpath =        wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("//ng-multiselect-dropdown[@formcontrolname='sampledContainers']//li[normalize-space()='"+samplingplan+"']")));
		samplingplanXpath.click();
	}
	
	
	
	
	@FindBy(xpath="//ng-multiselect-dropdown[@formcontrolname='weightedContainers']")
	WebElement dropdwn_weightverificationplan;
	public void selWeightVerificationPlan(String weightverificationplan) {
		waitForElementandClick(dropdwn_weightverificationplan);
		WebElement weightverificationplanXpath =        wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("//ng-multiselect-dropdown[@formcontrolname='weightedContainers']//li[normalize-space()='"+weightverificationplan+"']")));
		weightverificationplanXpath.click();
	}

}
