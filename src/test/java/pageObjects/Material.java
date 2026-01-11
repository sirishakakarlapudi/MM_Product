package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class Material extends BasePage {
	public Material(WebDriver driver) {
		super(driver);
	}
	
	@FindBy(xpath="//input[@formcontrolname='materialName']")
	WebElement txt_materialname;

	public void materialName(String materialname) {
		txt_materialname.clear();
		waitAndSendKeys(txt_materialname, materialname);
	}
	
	
	
	
	@FindBy(xpath="//input[@formcontrolname='materialCode']")
	WebElement txt_materialcode;

	public void materialCode(String materialcode) {
		txt_materialcode.clear();
		waitAndSendKeys(txt_materialcode, materialcode);
	}

	
	
	@FindBy(xpath="//ng-multiselect-dropdown[@formcontrolname='materialCategory']")
	WebElement dropdwn_materialcategory;

	public void selMaterialCategory(String materialcategory) {
		waitForElementandClick(dropdwn_materialcategory);
		WebElement samplingplanXpath =        wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("//ng-multiselect-dropdown[@formcontrolname='materialCategory']//li[normalize-space()='"+materialcategory+"']")));
		samplingplanXpath.click();
	}
	
	
	
	
	@FindBy(xpath="//ng-multiselect-dropdown[@formcontrolname='typeOfMaterial']")
	WebElement dropdwn_typeofmaterial;

	public void selTypeOfMaterial(String typeofmaterial) {
		waitForElementandClick(dropdwn_typeofmaterial);
		WebElement typeofmaterialXpath = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//ng-multiselect-dropdown[@formcontrolname='typeOfMaterial']//li[normalize-space()='"
						+ typeofmaterial + "']")));
		typeofmaterialXpath.click();
	}
	
	
	
	
	
	
	@FindBy(xpath="//ng-multiselect-dropdown[@formcontrolname='uomId']")
	WebElement dropdwn_uom;

	public void selUOM(String uom) {
		waitForElementandClick(dropdwn_uom);
		WebElement uomXpath = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//ng-multiselect-dropdown[@formcontrolname='uomId']//div[normalize-space()='" + uom + "']")));
		uomXpath.click();
	}
	
	@FindBy(xpath="//input[@formcontrolname='storageCondition']")
	WebElement txt_storagecondition;

	public void storageCondition(String storagecondition) {
		txt_storagecondition.clear();
		waitAndSendKeys(txt_storagecondition, storagecondition);
	}
	
	
	
	@FindBy(xpath="//ng-multiselect-dropdown[@formcontrolname='storageLocation']")
	WebElement dropdwn_storagelocation;

	public void selStorageLocation(String storagelocation) {
		waitForElementandClick(dropdwn_storagelocation);
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
        
        
        
        @FindBy(xpath="//p-checkbox[@inputid='isSampleCheckBox1']")
        WebElement chk_mixedanalysis_yes;
        
        @FindBy(xpath="//p-checkbox[@inputid='isSampleCheckBox2']")
        WebElement chk_mixedanalysis_no;
        
		public void checkmixedAnalysis(String mixedanalysis) {
			if (mixedanalysis.equalsIgnoreCase("Yes")) {
				waitForElementandClick(chk_mixedanalysis_yes);
			} else {
				waitForElementandClick(chk_mixedanalysis_no);
			}
		}
		
		
		@FindBy(xpath="//p-checkbox[@inputid='isWeightCheckBox1']")
		WebElement chk_weightverification_yes;
		@FindBy(xpath="//p-checkbox[@inputid='isWeightCheckBox2']")
		WebElement chk_weightverification_no;

		public void checkweightVerification(String weightverification) {
			if (weightverification.equalsIgnoreCase("Yes")) {
				waitForElementandClick(chk_weightverification_yes);
			} else {
				waitForElementandClick(chk_weightverification_no);
			}
		}
		
		
		@FindBy(xpath="//p-checkbox[@inputid='isReceivingCheckBox1']")
		WebElement chk_receivingbay_yes;
		@FindBy(xpath="//p-checkbox[@inputid='isReceivingCheckBox2']")
		WebElement chk_receivingbay_no;

		public void checkreceivingBay(String receivingbay) {
			if (receivingbay.equalsIgnoreCase("Yes")) {
				waitForElementandClick(chk_receivingbay_yes);
			} else {
				waitForElementandClick(chk_receivingbay_no);
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
		
		@FindBy(xpath="//p-autocomplete[@formcontrolname='supplierName']//chevrondownicon[@class='p-element p-icon-wrapper ng-star-inserted']")
		List<WebElement> dropdwn_supplier;

		public void selSupplier(String supplier) {
			waitForElementandClick(dropdwn_supplier.get(0));
			WebElement supplierXpath = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
					"//p-autocomplete[@formcontrolname='supplierName']//li[normalize-space()='" + supplier + "']")));
			supplierXpath.click();
		}
		
		@FindBy(xpath="//p-checkbox[@inputid='ismanufacturerCheckBox1']")
		WebElement chk_manufacturer_yes;
		@FindBy(xpath="//p-checkbox[@inputid='ismanufacturerCheckBox2']")
		WebElement chk_manufacturer_no;

		public void checkManufacturerYes() {
			
				waitForElementandClick(chk_manufacturer_yes);
		}
		public void checkManufacturerNo() {
				waitForElementandClick(chk_manufacturer_no);
			}
		
		
		@FindBy(xpath="//p-autocomplete[@formcontrolname='manufactureName']//chevrondownicon[@class='p-element p-icon-wrapper ng-star-inserted']")
		List<WebElement> dropdwn_manufacturer;

		public void selManufacturer(String manufacturer) {
			waitForElementandClick(dropdwn_manufacturer.get(0));
			WebElement manufacturerXpath = wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//p-autocomplete[@formcontrolname='manufactureName']//li[normalize-space()='"
							+ manufacturer + "']")));
			manufacturerXpath.click();
		}
		
		@FindBy(xpath="//button[normalize-space()='Save Details']")
		WebElement btn_savedetails;
		public void clickSaveDetails() {
			btn_savedetails.click();
		}
		
		
		@FindBy(xpath="//button[normalize-space()='Yes']")
		WebElement btn_addmanufacturerYes;
		public void btnManufacturerYes() {
			btn_addmanufacturerYes.click();
			
		}
		
		@FindBy(xpath="//button[normalize-space()='No']")
		WebElement btn_addmanufacturerNo;
		public void btnManufacturerNo() {
			btn_addmanufacturerNo.click();
			
		}
		
		
	}
	
	
	

