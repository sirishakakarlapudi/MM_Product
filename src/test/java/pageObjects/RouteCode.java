package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class RouteCode extends BasePage {
	
	
	
	public RouteCode(WebDriver driver) {
		super(driver);
		
	}

	@FindBy (xpath= "//input[@formcontrolname='routeCode']")
	WebElement txt_routecode;

	public void routeCode(String routecode) {
		txt_routecode.clear();
		waitAndSendKeys(txt_routecode, routecode);

	}

	@FindBy (xpath= "//input[@formcontrolname='routeDescription']")
	WebElement txt_routedesc;



	public void routeCodeDesc(String routecodedesc) {
		txt_routedesc.clear();
		waitAndSendKeys(txt_routedesc, routecodedesc);

	}
	
	@FindBy (xpath= "//ng-multiselect-dropdown[@formcontrolname='stageCode']//div/span")
	List<WebElement> dropdwn_stageCode;

	public void selStageCode(String stagecode) throws Exception {
		WebElement latestStageCode = dropdwn_stageCode.get(dropdwn_stageCode.size() - 1);
		latestStageCode.click();
		WebElement stageCodeXpath = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//ng-multiselect-dropdown[@formcontrolname='stageCode']//div[contains(@class,'dropdown-list') and not(@hidden)]//li//div[normalize-space()='" + stagecode + "']")));
		stageCodeXpath.click();
	}
	
	
	@FindBy (xpath="//button[normalize-space()='Add']")
	WebElement clickAddButton;

	public void clickAddButton() {
		waitForElementandClick(clickAddButton);
	}
	
	


}

