package utilities;

import java.util.Map;

public class securitygroupData {

	private String sgName;
	private String sgdesc;
	private String module;
	private String privileges;
	private String statusaftercreate;
	private String statusafterapp;
	private String statusafteredit;
	private String approvals;
	private String status;
	private String statusbeforeappedit;
	private String beforeappeditsgname;
	private String beforeappeditsgdesc;
	private String beforeappeditmodule;
	private String befeoreappeditprivileges;
	private String editsgname;
	private String editsgdesc;
	private String editmodule;
	private String editprivileges;
	private String updateprivileges;
	private String afterupdateeditprivileges;
	private String updateapprovals;

	// Constructor accepts a row as a Map
	public securitygroupData(Map<String, String> row) {
		try {
			this.sgName = getValue(row, "Security Group Name");
			this.sgdesc = getValue(row, "Description");
			this.module = getValue(row, "Module");
			this.privileges = getValue(row, "Privileges");

			this.statusaftercreate = getValue(row, "Status After Create");
			this.beforeappeditsgname = getValue(row, "Before Approve Edit Security Group Name");
			this.beforeappeditsgdesc = getValue(row, "Before Approve Edit Security Group Desc");
			this.beforeappeditmodule = getValue(row, "Before Approve Edit Module");
			this.befeoreappeditprivileges = getValue(row, "Before Approve Edit Privileges");
			this.statusbeforeappedit = getValue(row, "Status BeforeApprove Edit");
			this.approvals = getValue(row, "Approvals(Accept/Return)");
			this.editsgname = getValue(row, "Edit Security Group Name");
			this.editsgdesc = getValue(row, "Edit Security Group Desc");
			this.editmodule = getValue(row, "Edit Module");
			this.editprivileges = getValue(row, "Edit Privileges");
			this.statusafterapp = getValue(row, "Status After Approval");

			this.statusafteredit = getValue(row, "Status After Edit");
			this.status = getValue(row, "Final Status");
			this.updateprivileges = getValue(row, "Update Privileges");
			this.afterupdateeditprivileges = getValue(row, "After Update Edit Privileges");
			this.updateapprovals = getValue(row, "Update Approvals(Accept/Return)");
		} catch (Exception e) {
			throw new IllegalArgumentException("Excel row data is incomplete or missing headers", e);
		}
		System.out.println("Available keys from Excel row: " + row.keySet());
	}

	private String getValue(Map<String, String> row, String key) {
		for (String k : row.keySet()) {
			if (k.trim().equalsIgnoreCase(key.trim())) {
				String value = row.get(k);
				return value == null ? "" : value.trim();
			}
		}
		return "";
	}

	// Getters
	public String getSgName() {
		return sgName;
	}

	public String getSgDesc() {
		return sgdesc;
	}

	public String getModule() {
		return module;
	}

	public String getPrivileges() {
		return privileges;
	}

	public String getStatus_AfterCreate() {
		return statusaftercreate;
	}

	public String getStatus_AfterApp() {
		return statusafterapp;
	}

	public String getStatus_AfterEdit() {
		return statusafteredit;
	}

	public String getApprovals() {
		return approvals;
	}

	public String getStatus() {
		return status;
	}

	public String get_BeforeAppEditSgName() {
		return beforeappeditsgname;
	}

	public String get_BeforeAppEditSgDesc() {
		return beforeappeditsgdesc;
	}

	public String get_BeforeAppEditModule() {
		return beforeappeditmodule;
	}

	public String get_BeforeAppEditPrivileges() {
		return befeoreappeditprivileges;
	}

	public String getStatus_BeforeAppEdit() {
		return statusbeforeappedit;
	}

	public String getEditSgName() {
		return editsgname;
	}

	public String getEditSgDesc() {
		return editsgdesc;
	}

	public String getEditModule() {
		return editmodule;
	}

	public String getEditPrivileges() {
		return editprivileges;
	}

	public String getUpdatePrivileges() {
		return updateprivileges;
	}

	public String getAfterUpdateEditPrivileges() {
		return afterupdateeditprivileges;
	}

	public String getUpdateApprovals() {
		return updateapprovals;
	}

}
