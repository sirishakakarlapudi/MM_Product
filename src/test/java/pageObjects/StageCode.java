package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;



public class StageCode extends BasePage {
	public StageCode(WebDriver driver) {
		super(driver);

	}


@FindBy (xpath= "//input[@formcontrolname='name']")
WebElement txt_stagecode;

public void stageCode(String stagecode) {
	txt_stagecode.clear();
	waitAndSendKeys(txt_stagecode, stagecode);

}


@FindBy (xpath= "//ng-multiselect-dropdown[@formcontrolname='uomId']//div/span")
WebElement dropdwn_uom;
public void selUOM(String uom) {
	waitForElementandClick(dropdwn_uom);
	WebElement uomXpath = wait.until(ExpectedConditions.elementToBeClickable(
			By.xpath("//ng-multiselect-dropdown[@formcontrolname='uomId']//div[normalize-space()='" + uom + "']")));
	uomXpath.click();
}





@FindBy (xpath= "//input[@formcontrolname='description']")
WebElement txt_stagedesc;



public void stageCodeDesc(String stagecodedesc) {
	txt_stagedesc.clear();
	waitAndSendKeys(txt_stagedesc, stagecodedesc);

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


@FindBy(xpath="//p-checkbox[@inputid='isDesCheckBox1']")
WebElement chk_dispensingactivity_yes;

@FindBy(xpath="//p-checkbox[@inputid='isDesCheckBox2']")
WebElement chk_dispensingactivity_no;

public void checkdispensingActivity(String dispensingactivity) {
    if (dispensingactivity.equalsIgnoreCase("Yes")) {
        waitForElementandClick(chk_dispensingactivity_yes);
    } else {
        waitForElementandClick(chk_dispensingactivity_no);
    }
}


@FindBy(xpath="//p-checkbox[@inputid='cleaningAgent1']")
WebElement chk_cleaningagent_yes;
@FindBy(xpath="//p-checkbox[@inputid='cleaningAgent2']")
WebElement chk_cleaningagent_no;

public void checkcleaningAgent(String cleaningagent) {
    if (cleaningagent.equalsIgnoreCase("Yes")) {
        waitForElementandClick(chk_cleaningagent_yes);
    } else {
        waitForElementandClick(chk_cleaningagent_no);
    }
    
}





}