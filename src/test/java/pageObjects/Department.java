package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Department extends BasePage   {

	
	
	public Department(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(xpath = "//input[@id='field_name']")
	WebElement dept_name;

	
	
	public void deptName(String deptname) {
		dept_name.clear();
		waitAndSendKeys(dept_name, deptname);
	}
	
	@FindBy(xpath = "//input[@id='field_title']")
	WebElement dept_desc;

	public void deptDesc(String deptdesc) {
		dept_desc.clear();
		waitAndSendKeys(dept_desc, deptdesc);
		
	}

	
	


}
