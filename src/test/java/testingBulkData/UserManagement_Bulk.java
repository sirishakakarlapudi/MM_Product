package testingBulkData;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageObjects.UserManagement;
import utilities.DataProviders;
import utilities.usermanagementData;

public class UserManagement_Bulk extends BaseClassBulk {

	UserManagement userManagement;

	@BeforeMethod
	public void pageObjects() {

		userManagement = new UserManagement(driver);

	}

	@Test(priority = 1)
	public void enterUserNameandPassword() throws Throwable {
		userManagement.userName("admin");
		userManagement.passWord("admin");
		userManagement.loginButton();

	}

	@Test(priority = 2)
	public void clickMaster() throws Throwable {

		userManagement.click_titleMasters();

	}

	@Test(priority = 3)
	public void clickSecurityGroup() throws Throwable {

		userManagement.masterClick("User Management");
		userManagement.masterClick("Users List");
		userManagement.waitForCreateButton();

	}

	@Test(priority = 5, dataProvider = "UserManagementFullData", dataProviderClass = DataProviders.class, singleThreaded = true)
	public void createUserWithBulkData(usermanagementData userdata) throws Throwable {
		String empid = userdata.getEmpID();
		String empname = userdata.getEmpName();
		String email = userdata.getEmail();
		String mobilenumber = userdata.getMobileNumber();
		String username = userdata.getUsername();
		String temppassword = userdata.getTemporaryPassword();
		String department = userdata.getDepartment();
		String designation = userdata.getDesignation();
		String module1 = userdata.getModule1();
		String sgname1 = userdata.getSecurityGroupName1();
		String add = userdata.getAdd();
		String module2 = userdata.getModule2();
		String sgname2 = userdata.getSecurityGroupName2();

		userManagement.Create();
		userManagement.waitForSubmit();

		userManagement.enterEmployeeID(empid);
		userManagement.enterEmployeeName(empname);
		if (email != null && !email.trim().isEmpty()) {
			userManagement.email(email);
		}
		if (mobilenumber != null && !mobilenumber.trim().isEmpty()) {
			userManagement.mobileNumber(mobilenumber);
		}

		userManagement.empUserName(username);
		userManagement.temporaryPassword(temppassword);
		userManagement.deptSelect(department);
		userManagement.designation(designation);
		userManagement.moduleSelect(module1);
		userManagement.sgNameSelect(sgname1);
		if (add != null && !add.trim().isEmpty()) {
			userManagement.clickAdd();
			userManagement.waitForLoading();
			userManagement.moduleSelect(module2);
			userManagement.sgNameSelect(sgname2);
			userManagement.waitForLoading();
		}
		userManagement.createSubmit_editUpdate();
		userManagement.waitForLoading();
		userManagement.passWord("admin");
		userManagement.authenticateButton();
		userManagement.waitForPageButton();

	}

	@Test(priority = 5, dataProvider = "UserManagementFullData", dataProviderClass = DataProviders.class, singleThreaded = true)
	public void editUserWithBulkData(usermanagementData userdata) throws Throwable {
		String empid = userdata.getEmpID();
		String editempname = userdata.getEditEmpName();
		String editemail = userdata.getEditEmail();
		String editmobilenumber = userdata.getEditMobileNumber();
		String editdesignation = userdata.getEditDesignation();
		String editdepartment = userdata.getEditDepartment();
		String editsecuritygroupname1 = userdata.getEditSecurityGroupName1();
		String editsecuritygroupname2 = userdata.getEditSecurityGroupName2();
		String editmodule1 = userdata.getEditModule1();
		String editmodule2 = userdata.getEditModule2();
		String editadd = userdata.getEditAdd();
		

		if (editempname != null && !editempname.trim().isEmpty() || editemail != null && !editemail.trim().isEmpty()
				|| editmobilenumber != null && !editmobilenumber.trim().isEmpty()
				|| editdesignation != null && !editdesignation.trim().isEmpty()
				|| editdepartment != null && !editdepartment.trim().isEmpty()) {
		
			userManagement.clickEdit(empid);
			userManagement.waitForSubmit();
			if (editempname != null && !editempname.trim().isEmpty()) {
				userManagement.enterEmployeeName(editempname);
			}
			if (editemail != null && !editemail.trim().isEmpty()) {
				userManagement.email(editemail);
			}
			if (editmobilenumber != null && !editmobilenumber.trim().isEmpty()) {
				userManagement.mobileNumber(editmobilenumber);
			}
			if (editdesignation != null && !editdesignation.trim().isEmpty()) {
				userManagement.designation(editdesignation);
			}
			if (editdepartment != null && !editdepartment.trim().isEmpty()) {
				userManagement.deptSelect(editdepartment);
			}
			userManagement.createSubmit_editUpdate();
			userManagement.waitForLoading();
			userManagement.passWord("admin");
			userManagement.authenticateButton();
			userManagement.toast();
			userManagement.waitForPageButton();
			

		}
		if (editsecuritygroupname1 != null && !editsecuritygroupname1.trim().isEmpty()
				|| editsecuritygroupname2 != null && !editsecuritygroupname2.trim().isEmpty()) {
			userManagement.clickActions(empid);
			System.out.println("Clicked on Actions");
			userManagement.clickEditSG();
			if (editsecuritygroupname1 != null && !editsecuritygroupname1.trim().isEmpty()) {
				userManagement.sgNameSelect(editmodule1, editsecuritygroupname1);
			}
			
			if (editadd != null && !editadd.trim().isEmpty()) {
				userManagement.clickAdd();
				userManagement.waitForLoading();
				userManagement.moduleSelect(editmodule2);
				userManagement.sgNameSelect(editsecuritygroupname2);
				userManagement.waitForLoading();
			}
			
			if (editsecuritygroupname2 != null && !editsecuritygroupname2.trim().isEmpty() && editadd == null) {
                userManagement.sgNameSelect(editmodule2, editsecuritygroupname2);}
			userManagement.waitForLoading();
			userManagement.createSubmit_editUpdate();
			userManagement.waitForLoading();
			userManagement.passWord("admin");
			userManagement.authenticateButton();
			userManagement.toast();
			userManagement.waitForPageButton();

		}

	}
}
