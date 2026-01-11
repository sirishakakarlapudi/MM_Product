package testingBulkData;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageObjects.Supplier;
import pageObjects.UserManagement;

public class Supplier_Bulk extends BaseClassBulk {
	
	Supplier supplier;

	@BeforeMethod
	public void pageObjects() {

		supplier = new Supplier(driver);

	}

	@Test(priority = 1)
	public void enterUserNameandPassword() throws Throwable {
		supplier.userName("admin");
		supplier.passWord("admin");
		supplier.loginButton();

	}

	@Test(priority = 2)
	public void clickMaster() throws Throwable {

		supplier.click_titleMasters();

	}

	@Test(priority = 3)
	public void clickSecurityGroup() throws Throwable {

		supplier.masterClick("Vendor Details");
		supplier.masterClick("Supplier");
		supplier.waitForCreateButton();

	}


}
