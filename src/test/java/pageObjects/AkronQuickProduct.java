package pageObjects;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class AkronQuickProduct extends BasePage {
	public AkronQuickProduct(WebDriver driver) {
		super(driver);
	}
	
	
	@FindBy(xpath="//input[@formcontrolname='name']")
	WebElement txt_product;
	
	
	public void product(String product) {
		txt_product.clear();
		waitAndSendKeys(txt_product, product);
	}
	
	@FindBy(xpath="//input[@formcontrolname='productCode']")
	WebElement txt_productcode;
	
	
	public void productCode(String productcode) {
		txt_productcode.clear();
		waitAndSendKeys(txt_productcode, productcode);
	}
	
	
	@FindBy(xpath="//input[@formcontrolname='description']")
	WebElement txt_productdesc;
	
	
	public void productDesc(String productdesc) {
		txt_productdesc.clear();
		waitAndSendKeys(txt_productdesc, productdesc);
	}
	
	
	@FindBy (xpath= "//ng-multiselect-dropdown[@formcontrolname='storageLocation']//div/span")
	WebElement dropdwn_storageLocation;
	public void selStorageLocation(String storagelocation) {
		waitForElementandClick(dropdwn_storageLocation);
		WebElement storagelocationXpath = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//ng-multiselect-dropdown[@formcontrolname='storageLocation']//li[normalize-space()='"
						+ storagelocation + "']")));
		storagelocationXpath.click();
	}
	
	@FindBy(xpath="//p-checkbox[@inputid='isSamplingRequired1']")
	WebElement chk_samplingactivity_yes;

	@FindBy(xpath="//p-checkbox[@inputid='isSamplingRequired2']")
	WebElement chk_samplingactivity_no;

	public void checksamplingActivity(String samplingactivity) {

		if (samplingactivity.equalsIgnoreCase("Yes")) {
			waitForElementandClick(chk_samplingactivity_yes);
		} else {
			waitForElementandClick(chk_samplingactivity_no);
		}
	}

	@FindBy(xpath="//p-checkbox[@inputid='isCleaningCheckBox1']")
	WebElement chk_cleaningagent_yes;
	@FindBy(xpath="//p-checkbox[@inputid='isCleaningCheckBox2']")
	WebElement chk_cleaningagent_no;

	public void checkcleaningAgent(String cleaningagent) {
	    if (cleaningagent.equalsIgnoreCase("Yes")) {
	        waitForElementandClick(chk_cleaningagent_yes);
	    } else {
	        waitForElementandClick(chk_cleaningagent_no);
	    }
	    
	}
	
	
	@FindBy (xpath= "//input[@formcontrolname='stageName']")
	List<WebElement> txt_ndcnumber;
	
	public void ndcNumber(String ndcnumber) throws Exception {
		WebElement latestStageCode = txt_ndcnumber.get(txt_ndcnumber.size() - 1);
		latestStageCode.sendKeys(ndcnumber);
	}

	
	@FindBy (xpath= "//p-autocomplete[@formcontrolname='uom']//chevrondownicon")
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
	
	@FindBy (xpath= "//input[@formcontrolname='packSize']")
	List<WebElement> txt_packsize;
	
	public void packSize(String packsize) throws Exception {
		WebElement latestPackSize = txt_packsize.get(txt_packsize.size() - 1);
		latestPackSize.sendKeys(packsize);
	}
	
	
	@FindBy (xpath= "//input[@formcontrolname='stageDes']")
	List<WebElement> txt_ndcdesc;
	
	public void ndcDesc(String ndcdesc) throws Exception {
		WebElement latestNdcDesc = txt_ndcdesc.get(txt_ndcdesc.size() - 1);
		latestNdcDesc.sendKeys(ndcdesc);
	}
	
	
	public void enterRow() throws Exception {
		Robot r=new Robot();
		WebElement latestNdcDesc=txt_ndcdesc.get(txt_ndcdesc.size()-1);
		latestNdcDesc.click();
		r.keyPress(KeyEvent.VK_ENTER);
		r.keyRelease(KeyEvent.VK_ENTER);
		
}
	
}
