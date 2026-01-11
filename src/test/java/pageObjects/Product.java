package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class Product extends BasePage {

	public Product(WebDriver driver) {
		super(driver);

	}
	
	
	@FindBy (xpath= "//input[@formcontrolname='productName']")
	WebElement txt_product;

	public void product(String product) {
		txt_product.clear();
		waitAndSendKeys(txt_product, product);

	}

	
	@FindBy (xpath= "//input[@formcontrolname='productDescription']")
	WebElement txt_productdesc;



	public void productDesc(String productdesc) {
		txt_productdesc.clear();
		waitAndSendKeys(txt_productdesc, productdesc);

	}
	
	
	@FindBy (xpath= "//ng-multiselect-dropdown[@formcontrolname='routeCode']//div/span")
	List<WebElement> dropdwn_routeCode;

	public void selRouteCode(String routecode) throws Exception {
		WebElement latestRouteCode = dropdwn_routeCode.get(dropdwn_routeCode.size() - 1);
		latestRouteCode.click();
		WebElement routeCodeXpath = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//ng-multiselect-dropdown[@formcontrolname='routeCode']//div[contains(@class,'dropdown-list') and not(@hidden)]//li//div[normalize-space()='" + routecode + "']")));
		routeCodeXpath.click();
	}
	
	
	@FindBy (xpath="//button[normalize-space()='Add']")
	WebElement clickAddButton;

	public void clickAddButton() {
		waitForElementandClick(clickAddButton);
	}
}
