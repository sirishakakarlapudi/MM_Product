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

	/*
	 * ========================================================================= [
	 * LOCATORS ]
	 * =========================================================================
	 */

	@FindBy(xpath = "//input[@formcontrolname='name']")
	protected WebElement material_category_name;

	@FindBy(xpath = "//ng-multiselect-dropdown[@formcontrolname='sampledContainers']")
	protected WebElement dropdwn_samplingplan;

	@FindBy(xpath = "//ng-multiselect-dropdown[@formcontrolname='weightedContainers']")
	protected WebElement dropdwn_weightverificationplan;

	/*
	 * ========================================================================= [
	 * BASIC ACTIONS ]
	 * =========================================================================
	 */

	public void materialCategoryName(String name) {
		if (name != null && !name.trim().isEmpty()) {
			material_category_name.clear();
			waitAndSendKeys(material_category_name, name);
		}
	}

	public void selSamplingPlan(String samplingplan) {
		waitForElementandClick(dropdwn_samplingplan);
		WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//ng-multiselect-dropdown[@formcontrolname='sampledContainers']//li[normalize-space()='"
						+ samplingplan + "']")));
		option.click();
	}

	public void selWeightVerificationPlan(String weightverificationplan) {
		waitForElementandClick(dropdwn_weightverificationplan);
		WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//ng-multiselect-dropdown[@formcontrolname='weightedContainers']//li[normalize-space()='"
						+ weightverificationplan + "']")));
		option.click();
	}

}
