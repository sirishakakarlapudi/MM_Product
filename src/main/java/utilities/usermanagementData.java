package utilities;

import java.util.Map;

public class usermanagementData {
	
	
	private String empid;
	private String empname;
	private String email;
	private String mobilenumber;
	private String username;
	private String temporarypassword;
	private String department;
	private String designation;
	private String module1;
	private String securitygroupname1;
	private String add;
	private String module2;
	private String securitygroupname2;
	private String editempname;
	private String editemail;
	private String editmobilenumber;
	private String editdepartment;
	private String editdesignation;
	private String editsecuritygroupname1;
	private String editsecuritygroupname2;
	private String editmodule1;
	private String editmodule2;
	private String editadd;
	
	public usermanagementData(Map<String, String> row) {
		try {
			this.empid = getValue(row, "Employee ID");
			this.empname = getValue(row, "Employee Name");
			this.email = getValue(row, "Email");
			this.mobilenumber = getValue(row, "Mobile Number");
			this.username=getValue(row,"Username");
			this.temporarypassword=getValue(row,"Temporary Password");
			this.department=getValue(row,"Department");
			this.designation=getValue(row,"Designation");
			this.module1=getValue(row,"Module1");
			this.securitygroupname1=getValue(row,"Security Group Name1");
			this.add=getValue(row,"Add");
			this.module2=getValue(row,"Module2");
			this.securitygroupname2=getValue(row,"Security Group Name2");
			
			
			this.editempname = getValue(row, "Edit Employee Name");
			this.editemail = getValue(row, "Edit Email");
			this.editmobilenumber = getValue(row, "Edit Mobile Number");
			
			
			this.editdepartment=getValue(row,"Edit Department");
			this.editdesignation=getValue(row,"Edit Designation");
			this.editmodule1=getValue(row,"Edit Module1");
			this.editsecuritygroupname1=getValue(row,"Edit Security Group Name1");
			this.add=getValue(row,"Add");
			this.editmodule2=getValue(row,"Edit Module2");
			this.editsecuritygroupname2=getValue(row,"Edit Security Group Name2");
			this.editadd=getValue(row,"Edit Add");
			
		
		
	}
		catch (Exception e) {
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
		
		public String getModule1() {
			return module1;
		}
		
		public String getSecurityGroupName1() {
			return securitygroupname1;
		}
		
		public String getAdd() {
			return add;
		}
		public String getModule2() {
			return module2;
		}
		
		public String getSecurityGroupName2() {
			return securitygroupname2;
		}
		
		
		public String getEditEmpName() {
			return editempname;
		}

		public String getEditEmail() {
			return editemail;
		}
		public String getEditMobileNumber() {
			return editmobilenumber;
		}
		
		public String getEditDepartment() {
			return editdepartment;
		}
		
		public String getEditDesignation() {
			return editdesignation;
		}
		
		public String getEditSecurityGroupName1() {
			return editsecuritygroupname1;
		}
		public String getEditSecurityGroupName2() {
			return editsecuritygroupname2;
		}
		
		public String getEditModule1() {
			return editmodule1;
		}
		public String getEditModule2() {
			return editmodule2;
		}
		public String getEditAdd() {
			return editadd;
		}
		

}
