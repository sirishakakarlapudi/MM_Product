package testingBulkData;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageObjects.Facility;
import utilities.DataProviders;
import utilities.facilityData;

public class Facility_Bulk extends BaseClassBulk {

	Facility facility;

	@BeforeMethod

	public void pageObjects() {

		facility = new Facility(driver);

	}

	@Test(priority = 1)
	public void enterUserNameandPassword() throws Throwable {
		facility.userName("admin");
		facility.passWord("admin");
		facility.loginButton();

	}

	@Test(priority = 2)
	public void clickMaster() throws Throwable {

		facility.click_titleMasters();

	}

	@Test(priority = 3)
	public void clickFacility() throws Throwable {

		facility.masterClick("Facilities");
		facility.waitForCreateButton();

	}

	@Test(priority = 4, dataProvider = "FacilityFullData", dataProviderClass = DataProviders.class, singleThreaded = true)
	public void CreateFacilityWithBulk(facilityData facilitydata) throws Throwable {
		String facilityname = facilitydata.getFacilityName();
		String facilitytype = facilitydata.getFacilityType();
		String department = facilitydata.getDepartment();
		String storagecondition = facilitydata.getStorageCondition();
		String parentfacility = facilitydata.getParentFacility();
		String beforeappeditfacilityname = facilitydata.getBeforeAppEditFacilityName();
		String beforeappeditdept = facilitydata.getBeforeAppEditDept();
		String beforeappeditfacilitytype = facilitydata.getBeforeAppEditFacilityType();
		String beforeappeditstoragecondition = facilitydata.getBeforeAppEditStorageCondition();
		String beforeappeditparentfacility = facilitydata.getBeforeAppEditParentFacility();
		String approvals = facilitydata.getApprovals();
		String editfacilityname = facilitydata.getEditFacilityName();
		String editfacilitytype = facilitydata.getEditFacilityType();
		String editdept = facilitydata.getEditDept();
		String editstoragecondition = facilitydata.getEditStorageCondition();
		String editparentfacility = facilitydata.getEditParentFacility();

		facility.Create();
		facility.waitForSubmit();

		facility.facilityName(facilityname);

		if ("FACILITY".equalsIgnoreCase(facilitytype)) {
			System.out.println("Facility Type from Excel: " + facilitytype);
			facility.selFacilityType(facilitytype);
			facility.selDepartment(department);
			facility.storageCondition(storagecondition);
		} else {
			System.out.println("Facility Type from Excel: " + facilitytype);
			facility.selFacilityType(facilitytype);
			facility.selDepartment(department);
			facility.selParentFacility(parentfacility);
		}
		facility.createSubmit_editUpdate();
		facility.waitForLoading();
		facility.passWord("admin");
		facility.authenticateButton();
		facility.waitForPageButton();

		if ((beforeappeditfacilityname != null && !beforeappeditfacilityname.trim().isEmpty())
				|| (beforeappeditfacilitytype != null && !beforeappeditfacilitytype.trim().isEmpty())
				|| (beforeappeditdept != null && !beforeappeditdept.trim().isEmpty())
				|| (beforeappeditstoragecondition != null && !beforeappeditstoragecondition.trim().isEmpty())
				|| (beforeappeditparentfacility != null && !beforeappeditparentfacility.trim().isEmpty())) {
			facility.clickEdit(facilityname);
			facility.waitForLoading();
			if (beforeappeditfacilityname != null && !beforeappeditfacilityname.trim().isEmpty()) {
				facility.facilityName(beforeappeditfacilityname);
			}
			if (beforeappeditfacilitytype != null && !beforeappeditfacilitytype.trim().isEmpty()) {
				if ("FACILITY".equalsIgnoreCase(beforeappeditfacilitytype)) {

					System.out.println("Facility Type from Excel when type is Facility: " + facilitytype);
					facility.selFacilityType(beforeappeditfacilitytype);
					if (beforeappeditdept != null && !beforeappeditdept.trim().isEmpty()) {
						facility.selDepartment(beforeappeditdept);
					}
					if (beforeappeditstoragecondition != null && !beforeappeditstoragecondition.trim().isEmpty()) {
						facility.storageCondition(beforeappeditstoragecondition);
					}
				} else {
					System.out.println("Facility Type from Excel: " + facilitytype);
					facility.selFacilityType(beforeappeditfacilitytype);
					if (beforeappeditdept != null && !beforeappeditdept.trim().isEmpty()) {
						facility.selDepartment(beforeappeditdept);
					}
					if (beforeappeditparentfacility != null && !beforeappeditparentfacility.trim().isEmpty()) {
						facility.selParentFacility(beforeappeditparentfacility);
					}
				}

			}

			if (beforeappeditdept != null && !beforeappeditdept.trim().isEmpty()) {
				facility.selDepartment(beforeappeditdept);
			}

			if (beforeappeditstoragecondition != null && !beforeappeditstoragecondition.trim().isEmpty()) {
				facility.storageCondition(beforeappeditstoragecondition);
			}

			facility.createSubmit_editUpdate();
			facility.waitForLoading();
			facility.passWord("admin");
			facility.authenticateButton();
			facility.waitForPageButton();
		}

		String finalFacilityName = (beforeappeditfacilityname != null && !beforeappeditfacilityname.trim().isEmpty())
				? beforeappeditfacilityname
				: facilityname;

		facility.waitForLoading();
		facility.clickActions(finalFacilityName);
		if ("Accept".equalsIgnoreCase(approvals)) {
			facility.waitForLoading();
			facility.clickApprove();
			System.out.println("Accepting Department: " + finalFacilityName);
			facility.waitForLoading();
			facility.enterRemarks("Initial Approval");
			facility.clickSubmit();
			facility.waitForLoading();
			facility.passWord("admin");
			facility.authenticateButton();
			facility.toast();
			facility.waitForLoading();

		} else if ("Return".equalsIgnoreCase(approvals)) {
			facility.waitForLoading();
			facility.clickReturn();
			System.out.println("Returning Department: " + finalFacilityName);
			facility.waitForLoading();
			facility.enterRemarks("Initial Return");
			facility.clickSubmit();
			facility.waitForLoading();
			facility.passWord("admin");
			facility.waitForLoading();
			facility.authenticateButton();
			facility.toast();
			facility.waitForLoading();
		} else {
			System.out.println("Skipping invalid status: " + approvals);
			return;
		}
		if (!"Return".equalsIgnoreCase(approvals)) {
			System.out.println("Not returned. Skipping edit for: " + finalFacilityName);
			return;
		}

		facility.clickEdit(finalFacilityName);
		facility.waitForLoading();

		if (editfacilityname != null && !editfacilityname.trim().isEmpty()) {
			facility.facilityName(editfacilityname);
		}
		if (editfacilitytype != null && !editfacilitytype.trim().isEmpty()) {
			if ("FACILITY".equalsIgnoreCase(editfacilitytype)) {
				System.out.println("Facility Type from Excel when type is Facility: " + editfacilitytype);
				facility.selFacilityType(editfacilitytype);
				if (editdept != null && !editdept.trim().isEmpty()) {
					facility.selDepartment(editdept);
				}
				if (editstoragecondition != null && !editstoragecondition.trim().isEmpty()) {
					facility.storageCondition(editstoragecondition);
				}
			} else {
				System.out.println("Facility Type from Excel : " + editfacilitytype);
				Thread.sleep(3000);
				facility.selFacilityType(editfacilitytype);
				if (editdept != null && !editdept.trim().isEmpty()) {
					facility.selDepartment(editdept);
				}
				if (editparentfacility != null && !editparentfacility.trim().isEmpty()) {
					facility.selParentFacility(editparentfacility);
				}
			}

		}
		if (editdept != null && !editdept.trim().isEmpty()) {
			facility.selDepartment(beforeappeditdept);
		}

		if (editstoragecondition != null && !editstoragecondition.trim().isEmpty()) {
			facility.storageCondition(editstoragecondition);
		}

		facility.createSubmit_editUpdate();
		facility.waitForLoading();
		facility.passWord("admin");
		facility.authenticateButton();
		facility.waitForPageButton();

		String finalDeptName = (editfacilityname != null && !editfacilityname.trim().isEmpty()) ? editfacilityname
				: (beforeappeditfacilityname != null && !beforeappeditfacilityname.trim().isEmpty())
						? beforeappeditfacilityname
						: facilityname;

		if (!"Return".equalsIgnoreCase(approvals)) {
			System.out.println("Not returned. Skipping final approval for: " + finalDeptName);
			return;
		}
		facility.clickActions(finalDeptName);
		facility.waitForLoading();
		facility.clickApprove();
		System.out.println("Accepting Department: " + finalDeptName);
		facility.waitForLoading();
		facility.enterRemarks("Final Approval");
		facility.clickSubmit();
		facility.waitForLoading();
		facility.passWord("admin");
		facility.waitForLoading();
		facility.authenticateButton();
		facility.toast();
		facility.waitForLoading();

	}

}
