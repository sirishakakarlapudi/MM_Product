package pageObjects;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import utilities.ScreenshotUtil;

public class UserManagement extends BasePage {

	public UserManagement(WebDriver driver) {
		super(driver);
	}

	/*
	 * =========================================================================
	 * [ LOCATORS ]
	 * =========================================================================
	 */

	@FindBy(xpath = "//input[@formcontrolname='employeeId']")
	protected WebElement employee_id;

	@FindBy(xpath = "//input[@formcontrolname='employeeName']")
	protected WebElement employee_name;

	@FindBy(xpath = "//input[@formcontrolname='email']")
	protected WebElement employee_email;

	@FindBy(xpath = "//input[@formcontrolname='mobileNumber']")
	protected WebElement employee_mobile;

	@FindBy(xpath = "//input[@formcontrolname='userName']")
	protected WebElement employee_username;

	@FindBy(xpath = "//input[@formcontrolname='temporaryPassword']")
	protected WebElement employee_password;

	@FindBy(xpath = "//ng-multiselect-dropdown[@formcontrolname='department']//div/span")
	protected WebElement department_drpdwn;

	@FindBy(xpath = "//input[@formcontrolname='designation']")
	protected WebElement employee_designation;

	@FindBy(xpath = "//ng-multiselect-dropdown[@formcontrolname='selectedModules']//div/span")
	protected List<WebElement> module_drpdwn;

	@FindBy(xpath = "//ng-multiselect-dropdown[@formcontrolname='userLevel']//div/span")
	protected List<WebElement> sg_name_drpdwn;

	@FindBy(xpath = "//p-calendar[@formcontrolname='dateOfJoining']")
	protected WebElement date_joining;

	@FindBy(xpath = "//p-calendar[@formcontrolname='jdApprovedOn']")
	protected WebElement date_jd_approved;

	@FindBy(xpath = "//p-calendar[@formcontrolname='trainingCompletedDate']")
	protected WebElement date_training_completed;

	@FindBy(xpath = "//button[@aria-label='Choose Year']")
	protected WebElement choose_year_month;

	@FindBy(xpath = "//h5[normalize-space()='Training Criteria']")
	protected WebElement training_criteria_heading;

	@FindBy(xpath = "//select[@formcontrolname='trainingType']")
	protected WebElement training_mode_drpdwn;

	@FindBy(xpath = "//select[@formcontrolname='requireQuestionnaire']")
	protected WebElement questionnaire_drpdwn;

	@FindBy(xpath = "//ng-multiselect-dropdown[@formcontrolname='selectedJobCodes']//div/span")
	protected WebElement job_code_drpdwn;

	@FindBy(xpath = "//button[normalize-space()='Add']")
	protected WebElement add_button;

	@FindBy(xpath = "//span[normalize-space()='Edit Security Group']")
	protected WebElement edit_sg_button;

	/*
	 * =========================================================================
	 * [ BASIC ACTIONS ]
	 * =========================================================================
	 */

	public void employeeID(String id) {
		if (id != null && !id.trim().isEmpty()) {
			employee_id.clear();
			waitAndSendKeys(employee_id, id);
		}
	}

	public void clickEmployeeID() throws Throwable {
		ScreenshotUtil.capture();
		employee_id.click();
	}

	public void employeeName(String name) {
		if (name != null && !name.trim().isEmpty()) {
			employee_name.clear();
			waitAndSendKeys(employee_name, name);
		}
	}

	public void email(String email) {
		if (email != null && !email.trim().isEmpty()) {
			employee_email.clear();
			waitAndSendKeys(employee_email, email);
		}
	}

	public void mobileNumber(String mobile) {
		if (mobile != null && !mobile.trim().isEmpty()) {
			employee_mobile.clear();
			waitAndSendKeys(employee_mobile, mobile);
		}
	}

	public void userName(String username) {
		if (username != null && !username.trim().isEmpty()) {
			employee_username.clear();
			waitAndSendKeys(employee_username, username);
		}
	}

	public void temporaryPassword(String password) {
		if (password != null && !password.trim().isEmpty()) {
			employee_password.clear();
			waitAndSendKeys(employee_password, password);
		}
	}

	public void designation(String designation) {
		if (designation != null && !designation.trim().isEmpty()) {
			employee_designation.clear();
			waitAndSendKeys(employee_designation, designation);
		}
	}

	public void clickAdd() {
		add_button.click();
	}

	public void clickEditSG() {
		waitForElementandClick(edit_sg_button);
	}

	public void clickDateOfJoining() {
		waitForElementandClick(date_joining);
	}

	public void clickTrainingCompletedDate() {
		waitForElementandClick(date_training_completed);
	}

	public void clickJDApproved() {
		waitForElementandClick(date_jd_approved);
	}

	public void clickChooseYear_Month() {
		waitForElementandClick(choose_year_month);
	}

	/*
	 * =========================================================================
	 * [ BUSINESS ACTIONS ]
	 * =========================================================================
	 */

	public void deptSelect(String deptname) throws Exception {
		waitForElementandClick(department_drpdwn);
		WebElement deptXpath = wait.until(
				ExpectedConditions.elementToBeClickable(By.xpath("//div[normalize-space()='" + deptname + "']")));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();", deptXpath);
		deptXpath.click();
	}

	public void moduleSelect(String modulename) throws Exception {
		WebElement activeDropdown = module_drpdwn.get(module_drpdwn.size() - 1);
		waitForElementandClick(activeDropdown);
		WebElement moduleXpath = wait.until(ExpectedConditions.elementToBeClickable(
				(By.xpath("(//ng-multiselect-dropdown[@formcontrolname='selectedModules'])[" + module_drpdwn.size()
						+ "]//ul[@class='item2']/li/div[normalize-space()='" + modulename + "']"))));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", moduleXpath);
		moduleXpath.click();
	}

	public void sgNameSelect(String usersgname) throws Exception {
		WebElement activeDropdown = sg_name_drpdwn.get(sg_name_drpdwn.size() - 1);
		waitForElementandClick(activeDropdown);
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
				"(//ng-multiselect-dropdown[@formcontrolname='selectedModules']//span[@class='selected-item']/span[contains(text(),'"
						+ moduleName
						+ "')])/ancestor::div[contains(@class,'col-md')]/following-sibling::div//ng-multiselect-dropdown[@formcontrolname='userLevel']//span[@class='dropdown-multiselect__caret']")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", module_SgXpath);
		String[] sgArray = userSgNames.split(",");
		for (String sgname : sgArray) {
			sgname = sgname.trim();
			WebElement sgNameXpath = wait.until(ExpectedConditions
					.elementToBeClickable(By.xpath(
							"(//ng-multiselect-dropdown[@formcontrolname='selectedModules']//span[@class='selected-item']/span[contains(text(),'"
									+ moduleName
									+ "')])/ancestor::div[contains(@class,'col-md')]/following-sibling::div//ng-multiselect-dropdown[@formcontrolname='userLevel']//div[normalize-space()='"
									+ sgname + "']")));
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", sgNameXpath);
		}
	}

	public void modeOfTraining(String modeoftraining) throws Exception {
		Select select = new Select(training_mode_drpdwn);
		select.selectByVisibleText(modeoftraining);
	}

	public void requireQuestionnaire(String requirequestionanaire) throws Exception {
		Select select = new Select(questionnaire_drpdwn);
		select.selectByVisibleText(requirequestionanaire);
	}

	public void jobCode(String jobcodes) throws Exception {
		waitForElementandClick(job_code_drpdwn);
		String[] codeArray = jobcodes.split(",");
		for (String jobcode : codeArray) {
			jobcode = jobcode.trim();
			WebElement codeXpath = wait.until(
					ExpectedConditions.elementToBeClickable(By.xpath("//div[normalize-space()='" + jobcode + "']")));
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollIntoView();", codeXpath);
			codeXpath.click();
		}
	}

	public void selectDate(String date) throws Exception {
		if (date == null || date.trim().isEmpty()) {
			log.warn("Date string is empty, skipping selection.");
			return;
		}

		log.info("RAW date from Excel/Config: '{}'", date);

		String[] dateParts = date.split("[-/]");

		if (dateParts.length < 3) {
			throw new IllegalArgumentException("Invalid date format: " + date + ". Expected dd-mm-yyyy or dd/mm/yyyy");
		}

		int dayInt = Integer.parseInt(dateParts[0].trim());
		int monthInt = Integer.parseInt(dateParts[1].trim());
		int yearInt = Integer.parseInt(dateParts[2].trim());

		if (yearInt < 100) {
			if (yearInt <= 30) {
				yearInt += 2000;
			} else {
				yearInt += 1900;
			}
			log.info("2-digit year detected ({}). Smart-corrected to: {}", dateParts[2], yearInt);
		}

		if (monthInt > 12 && dayInt <= 12) {
			log.warn("Auto-correcting swapped Month/Day format. Raw Month: {}, Raw Day: {}", monthInt, dayInt);
			int temp = dayInt;
			dayInt = monthInt;
			monthInt = temp;
		}

		if (monthInt < 1 || monthInt > 12) {
			throw new IllegalArgumentException(
					"Wait! Month index " + monthInt + " is still invalid after processing. Raw input was: " + date);
		}

		String[] months = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
		String monthName = months[monthInt - 1];
		String yearStr = String.valueOf(yearInt);

		log.info("Final Selection Plan: Day={}, Month={}, Year={}", dayInt, monthName, yearStr);

		clickChooseYear_Month();
		selectYear(yearStr);
		selectYearMonth(monthName);

		WebElement dayElement = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//td[not(contains(@class, 'p-datepicker-other-month'))]//span[normalize-space()='" + dayInt
						+ "']")));
		dayElement.click();
	}

	public void selectYear(String year) {
		WebElement yearXpath = wait.until(
				ExpectedConditions.elementToBeClickable(By.xpath("//span[normalize-space()='" + year + "']")));
		yearXpath.click();
	}

	public void selectYearMonth(String month) {
		WebElement monthXpath = wait.until(
				ExpectedConditions.elementToBeClickable(By.xpath("//span[normalize-space()='" + month + "']")));
		monthXpath.click();
	}

	/*
	 * =========================================================================
	 * [ GETTERS / HELPERS ]
	 * =========================================================================
	 */

	public WebElement headingTrainingCriteria() {
		return training_criteria_heading;
	}

}
