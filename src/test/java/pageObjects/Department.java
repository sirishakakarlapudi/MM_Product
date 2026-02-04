package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import utilities.SoftAssertionUtil;

import java.util.ArrayList;
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

	
	
	@FindBy(xpath = "//button")
	List<WebElement> allButtons;

	public List<String> getDisplayedButtons() {
	    List<String> buttons = new ArrayList<>();
	    for (WebElement btn : allButtons) {
	        if (btn.isDisplayed()) {
	            buttons.add(btn.getText().trim());
	        }
	    }
	    return buttons;
	}

	public WebElement getButtonByText(String text) {
	    try {
	        String xpath =
	            "//button[translate(normalize-space(.),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='" 
	            + text.toUpperCase() + "']";
	        return driver.findElement(By.xpath(xpath));
	    } catch (Exception e) {
	        return null;
	    }
	}

	
	public boolean isLabelDisplayed(String labelText) {
	    try {
	        return driver.findElement(
	            By.xpath("//label[normalize-space()='" + labelText + "']")
	        ).isDisplayed();
	    } catch (Exception e) {
	        return false;
	    }
	}
	
	
	
	public WebElement getInputFieldForLabel(String labelText) {
	    String xpath =
	        "//label[normalize-space()='" + labelText + "']/ancestor::div[contains(@class,'form-group')]//input";
	    try {
	        return driver.findElement(By.xpath(xpath));
	    } catch (Exception e) {
	        return null;
	    }
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
	
	
	

	/*
	 * public void validateEditPage(String expectedName, String expectedDesc) throws
	 * Exception {
	 * 
	 * // log.info("--- Validating Edit Department Page ---"); SoftAssertionUtil
	 * softAssert = new SoftAssertionUtil();
	 * 
	 * // Validate field labels WebElement nameLabel = driver .findElement(By.
	 * xpath("//label[contains(text(),'Department Name') or contains(text(),'Name')]"
	 * )); WebElement descLabel = driver.findElement( By.
	 * xpath("//label[contains(text(),'Department Description') or contains(text(),'Description')]"
	 * ));
	 * 
	 * softAssert.assertTrue(nameLabel.isDisplayed(),
	 * "Department Name label should be displayed on Edit page");
	 * log.info("✓ Department Name label is displayed");
	 * 
	 * softAssert.assertTrue(descLabel.isDisplayed(),
	 * "Department Description label should be displayed on Edit page");
	 * log.info("✓ Department Description label is displayed");
	 * 
	 * // Validate buttons WebElement updateButton = driver .findElement(By.
	 * xpath("//button[contains(text(),'Update') or contains(@class,'update')]"));
	 * WebElement backButton = driver .findElement(By.
	 * xpath("//button[contains(text(),'Back') or contains(text(),'Cancel')]"));
	 * 
	 * softAssert.assertTrue(updateButton.isDisplayed(),
	 * "Update button should be displayed on Edit page");
	 * log.info("✓ Update button is displayed");
	 * 
	 * softAssert.assertTrue(backButton.isDisplayed(),
	 * "Back button should be displayed on Edit page");
	 * log.info("✓ Back button is displayed");
	 * 
	 * // Validate fields are editable softAssert.assertTrue(dept_name.isEnabled(),
	 * "Department Name field should be editable");
	 * log.info("✓ Department Name field is editable");
	 * 
	 * softAssert.assertTrue(dept_desc.isEnabled(),
	 * "Department Description field should be editable");
	 * log.info("✓ Department Description field is editable");
	 * 
	 * // Validate pre-filled data String actualName =
	 * dept_name.getAttribute("value"); String actualDesc =
	 * dept_desc.getAttribute("value");
	 * 
	 * softAssert.assertEquals(actualName, expectedName,
	 * "Department Name should match the original value");
	 * log.info("✓ Department Name is pre-filled correctly: {}", actualName);
	 * 
	 * softAssert.assertEquals(actualDesc, expectedDesc,
	 * "Department Description should match the original value");
	 * log.info("✓ Department Description is pre-filled correctly: {}", actualDesc);
	 * 
	 * // Assert all validations softAssert.assertAll();
	 * log.info("--- Edit Department Page Validation Completed Successfully ---"); }
	 */
}
