package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.util.List;

public class Department extends BasePage {

	public Department(WebDriver driver) {
		super(driver);

	}

	@FindBy(xpath = "//input[@id='field_name']")
	WebElement dept_name;

	public void deptName(String deptname) {
		if (deptname != null && !deptname.trim().isEmpty()) {
			dept_name.clear();
			waitAndSendKeys(dept_name, deptname);
		}
	}

	@FindBy(xpath = "//input[@id='field_title']")
	WebElement dept_desc;

	public void deptDesc(String deptdesc) {
		if (deptdesc != null && !deptdesc.trim().isEmpty()) {
			dept_desc.clear();
			waitAndSendKeys(dept_desc, deptdesc);
		}
	}

	public WebElement getDeptNameField() {
		return dept_name;
	}

	public WebElement getDeptDescField() {
		return dept_desc;
	}

	public void validateIconsWithStatus(String deptName, boolean expectView, boolean expectEdit) throws Exception {
		validateIcons(page_count, "//tbody[@role='rowgroup']/tr",
				"//span[@class='p-paginator-pages ng-star-inserted']/button", expectView, expectEdit, deptName);
	}

	public void validateApproveAction(String deptName) throws Exception {
		validateActionMenuOptions(page_count, "//tbody[@role='rowgroup']/tr",
				"//span[@class='p-paginator-pages ng-star-inserted']/button", "Approve", deptName);
	}

	public void validateActionsStrict(String deptName, List<String> expectedActions) throws Exception {
		validateActionMenuStrict(page_count, "//tbody[@role='rowgroup']/tr",
				"//span[@class='p-paginator-pages ng-star-inserted']/button", expectedActions, deptName);
	}

	public String getStatus(String deptName) throws Exception {
		return getItemStatus(page_count, "//tbody[@role='rowgroup']/tr",
				"//span[@class='p-paginator-pages ng-star-inserted']/button", deptName);
	}
}
