package pageObjects;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class WeightVariation extends BasePage {

	public WeightVariation(WebDriver driver) {
		super(driver);
		
	}
	
	@FindBy(xpath = "//ng-multiselect-dropdown[@formcontrolname='balenceCapacity']")
    WebElement drpdwn_balancecapacity;

    public void selBalanceCapacity(String balancecapacity) {
        waitForElementandClick(drpdwn_balancecapacity);
        clickRelativeOption(drpdwn_balancecapacity,"li", balancecapacity);
    
    }
    
    
    @FindBy(xpath = "//ng-multiselect-dropdown[@formcontrolname='weightType']")
    WebElement drpdwn_weighttype;

    public void selWeightType(String weighttype) {
        waitForElementandClick(drpdwn_weighttype);
        clickRelativeOption(drpdwn_weighttype,"li", weighttype);
        
    }
    
    @FindBy(xpath = "//input[@formcontrolname='minWeight']")
    List<WebElement> txt_minweight;

    public void fromWeight(String minweight) {
    	enterValueInLatestElement(txt_minweight, minweight);
     
    }
	
    @FindBy(xpath = "//input[@formcontrolname='maxWeight']")
    List<WebElement> txt_maxweight;

    public void toWeight(String maxweight) {
    	enterValueInLatestElement(txt_maxweight, maxweight);
        
    }

    @FindBy(xpath = "//input[@formcontrolname='allowedVariation']")
    List<WebElement> txt_allowedvariation;

    public void allowedVariation(String allowedvariation) {
    	enterValueInLatestElement(txt_allowedvariation, allowedvariation);
        
 }
    public void enterRow() throws Exception {
		Robot r=new Robot();
		clickLatestElement(txt_allowedvariation);
		r.keyPress(KeyEvent.VK_ENTER);
		r.keyRelease(KeyEvent.VK_ENTER);
		
	
		
}
    
    @FindBy(xpath = "//input[@formcontrolname='netPackWeight']")
    List<WebElement> txt_packweight;

    public void packWeight(String packweight) {
    	enterValueInLatestElement(txt_packweight, packweight);
        
 }
    @FindBy(xpath = "//input[@formcontrolname='minNetWeight']")
    List<WebElement> txt_minnetweight;

    public void minNetWeight(String minnetweight) {
    	enterValueInLatestElement(txt_minnetweight, minnetweight);
        
 }
    
    @FindBy(xpath = "//input[@formcontrolname='maxNetWeight']")
    List<WebElement> txt_maxnetweight;

    public void maxNetWeight(String maxnetweight) {
    	enterValueInLatestElement(txt_maxnetweight, maxnetweight);
        
 }
    
}
