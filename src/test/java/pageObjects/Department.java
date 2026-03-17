package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import utilities.DatabaseBackupUtil;
import utilities.ScreenshotUtil;

import java.util.List;
import java.util.Map;

public class Department extends BasePage {

	public Department(WebDriver driver) {
		super(driver);
	}
	
	
	
	String sqlQueryToGet_DepartmentUserDetails =
	        "SELECT r.id, r.name, r.created_by, r.created_date, r.comments,s.name AS status_name FROM request r " +
	        "JOIN product_store ps ON ps.id::text = r.name " +
	        "JOIN status s ON s.id = r.status_id " +
	        "WHERE ps.name = ? " +
	        "ORDER BY r.id ASC";

	/*
	 * =========================================================================
	 * [ LOCATORS ]
	 * =========================================================================
	 */

	@FindBy(xpath = "//input[@id='field_name']")
	protected WebElement dept_name;

	@FindBy(xpath = "//input[@id='field_title']")
	protected WebElement dept_desc;

	@FindBy(xpath = "//button")
	protected List<WebElement> allButtons;
	
	@FindBy(xpath = "//input[@formcontrolname='department']")
	protected WebElement department_filter;

	

	/*
	 * =========================================================================
	 * [ BASIC ACTIONS ]
	 * =========================================================================
	 */

	public void deptName(String name) {
		if (name != null && !name.trim().isEmpty()) {
			dept_name.clear();
			waitAndSendKeys(dept_name, name);
		}
	}

	public void clickDeptName() throws Throwable {
	ScreenshotUtil.capture();
	dept_name.click();
	}
	
	
	public void deptDesc(String desc) {
		if (desc != null && !desc.trim().isEmpty()) {
			dept_desc.clear();
			waitAndSendKeys(dept_desc, desc);
		}
	}

	public void clickDeptdesc() {
		dept_desc.click();
	}
	
	
	public void clickDepartmentNameFilter(String deptname) {
		waitAndSendKeys(department_filter, deptname);
	}
	
	
	
	public void clickResultsPerPageDropdown() {
		scrollAndClick(getRowsPerPageDropdown());
	}

	public void clickPageNumber(int pageNumber) throws InterruptedException {
		try {
			WebElement pageButton = driver.findElement(
					By.xpath("//span[@class='p-paginator-pages ng-star-inserted']/button[@aria-label='" + pageNumber
							+ "']"));
			jsScrollToElement(pageButton);
			Thread.sleep(200);
			pageButton.click();
			Thread.sleep(500);
			log.info("Clicked page number: {}", pageNumber);
		} catch (Exception e) {
			log.error("Failed to click page number: {}", pageNumber);
			throw e;
		}
	}

	/*
	 * =========================================================================
	 * [ BUSINESS ACTIONS ]
	 * =========================================================================
	 */

	public void selectResultsPerPage(int value) throws InterruptedException {
		clickResultsPerPageDropdown();
		Thread.sleep(500);
		WebElement option = driver.findElement(By.xpath("//li[@role='option']//span[normalize-space()='" + value + "']"));
		waitForElementandClick(option);
		Thread.sleep(500);
	}

	public void validateViewIconWithStatus(String name, boolean exp) throws Exception {
		validateIcon(exp, "pi-eye", name);
	}

	public void validateEditIconWithStatus(String name, boolean exp) throws Exception {
		validateIcon(exp, "pi-pencil", name);
	}

	public void validateActionsStrict(String name, List<String> expItems) throws Exception {
	
	
		List<String> actual = (List<String>) scanTable("//tbody[@role='rowgroup']/tr",
				"//span[@class='p-paginator-pages ng-star-inserted']/button", TableActionType.CHECK_MENU, "", false,
				name);
		if (!actual.containsAll(expItems) || !expItems.containsAll(actual))
			throw new RuntimeException("Actions mismatch. Exp: " + expItems + ", Found: " + actual);
	}

	/*
	 * =========================================================================
	 * [ GETTERS / HELPERS ]
	 * =========================================================================
	 */

	public WebElement getDeptNameField() {
		return dept_name;
	}

	public WebElement getDeptDescField() {
		return dept_desc;
	}

	


	public boolean hasPrivilege(String... privilegeType) {
		try {
			return utilities.DatabaseBackupUtil.hasAuthority(currentUsername, privilegeType);
		} catch (Exception e) {
			return true;
		}
	}
	
	
	public List<Map<String, String>> getUserdetailsFromDB(String deptname, String dbName) throws Exception {
	    log.info("Fetching user details for department: {} from DB: {}", deptname, dbName);

	    DatabaseBackupUtil.setActiveDb(dbName);

	    return DatabaseBackupUtil.getRowsFromDB(sqlQueryToGet_DepartmentUserDetails, deptname);
	}

	

}

	