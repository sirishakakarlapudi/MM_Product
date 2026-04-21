package utilities;

import java.util.Map;

public class UserManagementOQData {

	private String empid;
	private String empname;
	private String email;
	private String mobilenumber;
	private String username;
	private String temporarypassword;
	private String department;
	private String designation;
	private String module;
	private String securitygroupname;
	private String dateofjoining;
	private String jdapprovedon;
	private String trainingcompletedon;
	private String jobcode;
	private String modeoftraining;
	private String requirequestionnaire;
	private String newpassword;

	public UserManagementOQData(Map<String, String> row) {
		try {
			this.empid = getValue(row, "Employee ID");
			this.empname = getValue(row, "Employee Name");
			this.email = getValue(row, "Email");
			this.mobilenumber = getValue(row, "Mobile Number");
			this.username = getValue(row, "Username");
			this.temporarypassword = getValue(row, "Temporary Password");
			this.department = getValue(row, "Department");
			this.designation = getValue(row, "Designation");
			this.module = getValue(row, "Module");
			this.securitygroupname = getValue(row, "Security Group Name");

			this.dateofjoining = getValue(row, "Date Of Joining");
			this.jdapprovedon = getValue(row, "JD Approved On");
			this.trainingcompletedon = getValue(row, "Training Completed On");
			this.jobcode = getValue(row, "Job Code");
			this.modeoftraining = getValue(row, "Mode Of Training");
			this.requirequestionnaire = getValue(row, "Require Questionnaire");
			this.newpassword= getValue(row, "New Password");

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

	public String getEmpID() {
		return empid;
	}

	public String getEmpName() {
		return empname;
	}

	public String getEmail() {
		return email;
	}

	public String getMobileNumber() {
		return mobilenumber;
	}

	public String getUsername() {
		return username;
	}

	public String getTemporaryPassword() {
		return temporarypassword;
	}

	public String getDepartment() {
		return department;
	}

	public String getDesignation() {
		return designation;
	}

	public String getModule() {
		return module;
	}

	public String getSecurityGroupName() {
		return securitygroupname;
	}

	public String getDateOfJoining() {
		return dateofjoining;
	}

	public String getJDApprovedOn() {
		return jdapprovedon;
	}

	public String getTrainingCompletedOn() {
		return trainingcompletedon;
	}

	public String getJobCode() {
		return jobcode;
	}

	public String getModeOfTraining() {
		return modeoftraining;
	}

	public String getRequireQuestionnaire() {
		return requirequestionnaire;
	}
	
	public String getNewPassword() {
		return newpassword;
	}

}
