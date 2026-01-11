package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SecurityGroup extends BasePage {
	public SecurityGroup(WebDriver driver) {
		super(driver);
	}
	
	
	@FindBy(xpath="//input[@formcontrolname='name']")
	WebElement security_group_name;
	public void securityGroupName(String sgname) {
		security_group_name.clear();
		waitAndSendKeys(security_group_name, sgname);
	}
	
	
	@FindBy(xpath="//input[@formcontrolname='description']")
	WebElement security_group_desc;
	
	public void securityGroupDesc(String sgdesc) {
		security_group_desc.clear();
		waitAndSendKeys(security_group_desc, sgdesc);
	}
	
	
	
	@FindBy(xpath="//span[@class='dropdown-multiselect__caret']")
	WebElement clk_module;
	

	
	
	
	public void selModule(String selmodule) {
		waitForElementandClick(clk_module);
		WebElement selModuleXpath = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//ul[@class='item2']/li[normalize-space()='"+selmodule+"']")));
		selModuleXpath.click();
	}
	
	
	
	
	public void selPrivileges(String userprivileges) {
		String[] sgArray = userprivileges.split(",");

		for (String privilegename : sgArray) {
		    privilegename = privilegename.trim(); // in case of extra spaces
		    WebElement privilegeXpath = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//label[normalize-space()='" + privilegename + "']")));
		    System.out.println("Privilege to select: " + privilegename);

		    ((JavascriptExecutor) driver).executeScript(
		        "arguments[0].scrollIntoView({behavior: 'auto', block: 'center', inline: 'nearest'});",
		        privilegeXpath
		    );

		    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", privilegeXpath);
		}
	}
	
	
	

}
