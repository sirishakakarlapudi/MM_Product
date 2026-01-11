package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class UserManagement extends BasePage {

	public UserManagement(WebDriver driver) {
		super(driver);

	}

	@FindBy(xpath = "//input[@formcontrolname='employeeId']")
	WebElement txt_employeeID;

	public void enterEmployeeID(String employeeid) throws Exception {
		waitAndSendKeys(txt_employeeID, employeeid);

	}

	@FindBy(xpath = "//input[@formcontrolname='employeeName']")
	WebElement txt_employeeName;

	public void enterEmployeeName(String employeename) throws Exception {
		waitAndSendKeys(txt_employeeName, employeename);

	}

	@FindBy(xpath = "//input[@formcontrolname='email']")
	WebElement txt_email;

	public void email(String email) {
		waitAndSendKeys(txt_email, email);
	}

	@FindBy(xpath = "//input[@formcontrolname='mobileNumber']")
	WebElement txt_mobilenumber;

	public void mobileNumber(String mobilenumber) {
		waitAndSendKeys(txt_mobilenumber, mobilenumber);
	}

	@FindBy(xpath = "//input[@formcontrolname='userName']")
	WebElement txt_username;

	public void empUserName(String username) {
		waitAndSendKeys(txt_username, username);
	}

	@FindBy(xpath = "//input[@formcontrolname='temporaryPassword']")
	WebElement txt_temporarypassword;

	public void temporaryPassword(String temporarypassword) {
		waitAndSendKeys(txt_temporarypassword, temporarypassword);
	}

	@FindBy(xpath = "//ng-multiselect-dropdown[@formcontrolname='department']//div/span")
	WebElement dept_drpdwn;

	public void deptSelect(String deptname) throws Exception {
		waitForElementandClick(dept_drpdwn);

		WebElement deptXpath = wait.until(
				ExpectedConditions.elementToBeClickable(By.xpath("//div[normalize-space()='" + deptname + "']")));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();", deptXpath);
		deptXpath.click();
	}

	@FindBy(xpath = "//input[@formcontrolname='designation']")
	WebElement txt_designation;

	public void designation(String designation) {
		waitAndSendKeys(txt_designation, designation);
	}

	@FindBy(xpath = "//ng-multiselect-dropdown[@formcontrolname='selectedModules']//div/span")
	List<WebElement> module_drpdwn;

	public void moduleSelect(String modulename) throws Exception {
		WebElement modulelatestDropdown = module_drpdwn.get(module_drpdwn.size() - 1);

		waitForElementandClick(modulelatestDropdown);

		WebElement moduleXpath = wait.until(ExpectedConditions.elementToBeClickable(
				(By.xpath("(//ng-multiselect-dropdown[@formcontrolname='selectedModules'])[" + module_drpdwn.size()
						+ "]//ul[@class='item2']/li/div[normalize-space()='" + modulename + "']"))));

		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", moduleXpath);
		moduleXpath.click();

	}

	@FindBy(xpath = "//ng-multiselect-dropdown[@formcontrolname='userLevel']//div/span")
	List<WebElement> sgName_drpdwn;

	public void sgNameSelect(String usersgname) throws Exception {
		WebElement sglatestDropdown = sgName_drpdwn.get(sgName_drpdwn.size() - 1);
		waitForElementandClick(sglatestDropdown);
		String[] sgArray = usersgname.split(",");

		for (String sgname : sgArray) {
			sgname = sgname.trim();

			WebElement sgNameXpath = wait.until(ExpectedConditions
					.elementToBeClickable((By.xpath("(//ng-multiselect-dropdown[@formcontrolname='userLevel'])["
							+ module_drpdwn.size() + "]//div[normalize-space()='" + sgname + "']"))));

			((JavascriptExecutor) driver).executeScript("arguments[0].click();", sgNameXpath);

		}

	}

	public void sgNameSelect(String moduleName, String userSgNames) throws Exception {

		WebElement module_SgXpath = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
				"(//ng-multiselect-dropdown[@formcontrolname='selectedModules']//span[@class='selected-item']/span[contains(text(),'"+moduleName+"')])/ancestor::div[contains(@class,'col-md')]/following-sibling::div//ng-multiselect-dropdown[@formcontrolname='userLevel']//span[@class='dropdown-multiselect__caret']")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", module_SgXpath);
		String[] sgArray = userSgNames.split(",");

		for (String sgname : sgArray) {
			sgname = sgname.trim();

			WebElement sgNameXpath = wait.until(ExpectedConditions
					.elementToBeClickable(By.xpath("(//ng-multiselect-dropdown[@formcontrolname='selectedModules']//span[@class='selected-item']/span[contains(text(),'"+moduleName+"')])/ancestor::div[contains(@class,'col-md')]/following-sibling::div//ng-multiselect-dropdown[@formcontrolname='userLevel']//div[normalize-space()='" + sgname + "']")));

			((JavascriptExecutor) driver).executeScript("arguments[0].click();", sgNameXpath);

		}

	}

	/*
	 * public void sgNameSelect(String moduleName, String userSgNames) throws
	 * Exception {
	 * 
	 * String[] sgArray = userSgNames.split(",");
	 * 
	 * WebElement moduleDropdown =
	 * wait.until(ExpectedConditions.elementToBeClickable( By.xpath(
	 * "//ng-multiselect-dropdown[@formcontrolname='selectedModules']//div[contains(@class,'multiselect-dropdown')]//span")
	 * )); waitForElementandClick(moduleDropdown);
	 * 
	 * WebElement moduleOption = wait.until(ExpectedConditions.elementToBeClickable(
	 * By.xpath("//div[@class='dropdown-list']//div[normalize-space()='" +
	 * moduleName + "']")) ); ((JavascriptExecutor)
	 * driver).executeScript("arguments[0].click();", moduleOption);
	 * 
	 * // Step 4: Locate the row containing the selected module WebElement moduleRow
	 * = wait.until(ExpectedConditions.visibilityOfElementLocated( By.xpath(
	 * "//div[contains(@class,'modal-body')]//div[contains(@class,'row')][.//ng-multiselect-dropdown[contains(.,'"
	 * + moduleName + "')]]") ));
	 * 
	 * // Step 5: Find the corresponding Security Group dropdown inside that same
	 * row WebElement sgDropdown = moduleRow.findElement( By.xpath(
	 * ".//ng-multiselect-dropdown[@formcontrolname='userLevel']//div/span") );
	 * waitForElementandClick(sgDropdown);
	 * 
	 * // Step 6: Select each SG name dynamically for (String sgname : sgArray) {
	 * sgname = sgname.trim(); WebElement sgOption =
	 * wait.until(ExpectedConditions.elementToBeClickable(
	 * By.xpath("//div[@class='dropdown-list']//div[normalize-space()='" + sgname +
	 * "']")) ); ((JavascriptExecutor)
	 * driver).executeScript("arguments[0].click();", sgOption); }
	 * 
	 * 
	 * }
	 */

	@FindBy(xpath = "//button[normalize-space()='Add']")
	WebElement btn_add;

	public void clickAdd() {
		btn_add.click();
	}

	@FindBy(xpath = "//span[normalize-space()='Edit Security Group']")
	WebElement btn_editsg;

	public void clickEditSG() {
		waitForElementandClick(btn_editsg);
	}

}
