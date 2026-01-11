package testingBulkData;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageObjects.Department;

import utilities.DataProviders;
import utilities.departmentData;

public class Department_Bulk extends BaseClassBulk {
	Department dept;

	@BeforeMethod
	public void pageObjects() {

		dept = new Department(driver);

	}

	@Test(priority = 1)
	public void enterUserNameandPassword() throws Throwable {
		dept.userName("admin");
		dept.passWord("admin");
		dept.loginButton();

	}

	@Test(priority = 2)
	public void clickMaster() throws Throwable {

		dept.click_titleMasters();

	}

	@Test(priority = 3)
	public void clickDepartment() throws Throwable {

		dept.masterClick("Departments");
		dept.waitForCreateButton();

	}

	@Test(priority = 5, dataProvider = "DepartmentFullData", dataProviderClass = DataProviders.class, singleThreaded = true)
	public void createDepartmentWithBulkData(departmentData deptdata) throws Throwable {
		String deptname = deptdata.getDeptName();
		String desc = deptdata.getDesc();

		dept.Create();
		dept.waitForSubmit();

		dept.deptName(deptname);
		System.out.println("DeptName from Excel: " + deptname);
		System.out.println("Desc from Excel: " + desc);
		if (desc != null && !desc.trim().isEmpty()) {
			dept.deptDesc(desc);
		}
		dept.createSubmit_editUpdate();
		dept.passWord("admin");
		dept.authenticateButton();
		dept.waitForPageButton();

	}

	@Test(priority = 6, dataProvider = "DepartmentFullData", dataProviderClass = DataProviders.class, singleThreaded = true)
	public void BeforeApproveeditDepartment(departmentData deptdata) throws Exception {
		String deptName = deptdata.getDeptName();
		String beforeappeditddept = deptdata.get_BeforeAppEditDept();
		String beforeappeditddesc = deptdata.get_BeforeAppEditDesc();

		// Only proceed if at least one of the edit fields has a value
		if ((beforeappeditddept != null && !beforeappeditddept.trim().isEmpty())
				|| (beforeappeditddesc != null && !beforeappeditddesc.trim().isEmpty())) {

			dept.clickEdit(deptName);

			if (beforeappeditddept != null && !beforeappeditddept.trim().isEmpty()) {
				dept.deptName(beforeappeditddept);
			}
			if (beforeappeditddesc != null && !beforeappeditddesc.trim().isEmpty()) {
				dept.deptDesc(beforeappeditddept);
			}

			dept.createSubmit_editUpdate();
			dept.passWord("admin");
			dept.authenticateButton();
			dept.waitForPageButton();
		} else {
			System.out.println("No edit values provided for: " + deptName + " → Skipping edit step.");
		}
	}

	@Test(priority = 7, dataProvider = "DepartmentFullData", dataProviderClass = DataProviders.class, singleThreaded = true)
	public void approveOrReturnDepartment(departmentData deptdata) throws Exception {
		String deptName = deptdata.getDeptName();
		String approvals = deptdata.getApprovals();
		String beforeappeditdept = deptdata.get_BeforeAppEditDept();
		String beforeappeditdesc = deptdata.get_BeforeAppEditDesc();

		String finalDeptName = (beforeappeditdept != null && !beforeappeditdept.trim().isEmpty()) ? beforeappeditdept
				: deptName;
		dept.waitForLoading();
		dept.clickActions(finalDeptName);
		if ("Accept".equalsIgnoreCase(approvals)) {

			dept.waitForLoading();
			dept.clickApprove();
			System.out.println("Accepting Department: " + finalDeptName);
			dept.waitForLoading();
			dept.enterRemarks("Initial Approval");
			dept.clickSubmit();
			dept.waitForLoading();
			dept.passWord("admin");
			dept.authenticateButton();
			dept.toast();
			dept.waitForLoading();

		} else if ("Return".equalsIgnoreCase(approvals)) {
			dept.waitForLoading();
			dept.clickReturn();
			System.out.println("Returning Department: " + finalDeptName);
			dept.waitForLoading();
			dept.enterRemarks("Initial Return");
			dept.clickSubmit();
			dept.waitForLoading();
			dept.passWord("admin");
			dept.waitForLoading();
			dept.authenticateButton();
			dept.toast();
			dept.waitForLoading();
		} else {
			System.out.println("Skipping invalid status: " + approvals);
			return;
		}

	}

	@Test(priority = 8, dataProvider = "DepartmentFullData", dataProviderClass = DataProviders.class, singleThreaded = true)
	public void editDepartment(departmentData deptdata) throws Exception {
		String deptName = deptdata.getDeptName();
		String approvals = deptdata.getApprovals();
		String editDept = deptdata.getEditDept();
		String editDesc = deptdata.getEditDesc();
		String beforeappeditdept = deptdata.get_BeforeAppEditDept();

		String finalDeptName = (beforeappeditdept != null && !beforeappeditdept.trim().isEmpty()) ? beforeappeditdept
				: deptName;

		if (!"Return".equalsIgnoreCase(approvals)) {
			System.out.println("Not returned. Skipping edit for: " + finalDeptName);
			return;
		}

		dept.clickEdit(finalDeptName);

		if (editDept != null && !editDept.trim().isEmpty()) {
			dept.deptName(editDept);
		}
		if (editDesc != null && !editDesc.trim().isEmpty()) {
			dept.deptDesc(editDesc);
		}

		dept.createSubmit_editUpdate();
		dept.passWord("admin");
		dept.authenticateButton();
		dept.waitForPageButton();

	}

	@Test(priority = 9, dataProvider = "DepartmentFullData", dataProviderClass = DataProviders.class, singleThreaded = true)
	public void finalApprovalDepartment(departmentData deptdata) throws Exception {
		String deptName = deptdata.getDeptName();
		String approvals = deptdata.getApprovals();
		String editDept = deptdata.getEditDept();
		String beforeappeditdept = deptdata.get_BeforeAppEditDept();
		String finalDeptName = (editDept != null && !editDept.trim().isEmpty()) ? editDept
				: (beforeappeditdept != null && !beforeappeditdept.trim().isEmpty()) ? beforeappeditdept : deptName;

		if (!"Return".equalsIgnoreCase(approvals)) {
			System.out.println("Not returned. Skipping final approval for: " +
					finalDeptName);
			return;
		}

		dept.clickActions(finalDeptName);
		dept.waitForLoading();
		dept.clickApprove();
		System.out.println("Accepting Department: " + finalDeptName);
		dept.waitForLoading();
		dept.enterRemarks("Final Approval");
		dept.clickSubmit();
		dept.waitForLoading();
		dept.passWord("admin");
		dept.waitForLoading();
		dept.authenticateButton();
		dept.toast();
		dept.waitForLoading();
	}

}
