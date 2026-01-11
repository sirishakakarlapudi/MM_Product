package utilities;

import java.util.Map;

public class facilityData {
	
	private String facilityName;
	private String facilityType;
	private String department;
	private String storageCondition;
	private String parentFacility;
	private String beforeapproveeditfacilityname;
	private String beforeapproveeditfacilitytype;
	private String beforeapproveeditdept;
	private String beforeapproveeditstoragecondition;
	private String beforeapproveeditparentfacility;
	private String approvals;
	private String editfacilityname;
	private String editfacilitytype;
	private String editdept;
	private String editstoragecondition;
	private String editparentfacility;
	private String statusafteredit;
	
	private String finalstatus;

	
	
	  private String getValue(Map<String, String> row, String key) {
	        for (String k : row.keySet()) {
	            if (k.trim().equalsIgnoreCase(key.trim())) {
	                String value = row.get(k);
	                return value == null ? "" : value.trim();
	            }
	        }
	        return "";
	  }
	
	// Constructor accepts a row as a Map
	 public facilityData(Map<String, String> row) {
	        try {
	        	this.facilityName = getValue(row, "Facility Name");
	        	this.facilityType = getValue(row, "Facility Type");
	        	this.department = getValue(row, "Department");
	        	this.storageCondition = getValue(row, "Storage Condition");
	        	this.parentFacility = getValue(row, "Parent Facility");
	        	this.beforeapproveeditfacilityname=getValue(row, "Before Approve Edit Facility Name");
	        	this.beforeapproveeditfacilitytype=getValue(row, "Before Approve Edit Facility Type");
	        	this.beforeapproveeditdept=getValue(row, "Before Approve Edit Dept");
	        	this.beforeapproveeditstoragecondition=getValue(row, "Before Approve Edit Storage Condition");
	        	this.beforeapproveeditparentfacility=getValue(row, "Before Approve Edit Parent Facility");
	        	this.approvals = getValue(row, "Approvals(Accept/Return)");
	        	this.editfacilityname=getValue(row, "Edit Facility Name");
	        	this.editfacilitytype=getValue(row, "Edit Facility Type");
	        	this.editdept=getValue(row, "Edit Dept");
	        	this.editstoragecondition=getValue(row, "Edit Storage Condition");
	        	this.editparentfacility=getValue(row, "Edit Parent Facility");
	        	this.statusafteredit = getValue(row, "Status After Edit");
	        	this.finalstatus = getValue(row, "Final Status");
	        	
	        	
	        	
			} catch (Exception e) {
				throw new IllegalArgumentException("Excel row data is incomplete or missing headers", e);
	     
	        	
	        	
	        }
	        }
	 
	 	// Getters
		public String getFacilityName() {
			return facilityName;
		}

		public String getFacilityType() {
			return facilityType;
		}

		public String getDepartment() {
			return department;
		}

		public String getStorageCondition() {
			return storageCondition;
		}

		public String getParentFacility() {
			return parentFacility;
		}
		
		public String getBeforeAppEditDept() {
			return beforeapproveeditdept;
		}

		public String getBeforeAppEditFacilityName() {
			return beforeapproveeditfacilityname;
		}

		public String getBeforeAppEditFacilityType() {
			return beforeapproveeditfacilitytype;
		}

		public String getBeforeAppEditStorageCondition() {
			return beforeapproveeditstoragecondition;
		}
		
		public String getBeforeAppEditParentFacility() {
			return beforeapproveeditparentfacility;
		}

		public String getApprovals() {
			return approvals;
		}

		public String getEditFacilityName() {
			return editfacilityname;
		}
		
		public String getEditFacilityType() {
			return editfacilitytype;
		}
		
		public String getEditDept() {
			return editdept;
		}
		
		public String getEditStorageCondition() {
			return editstoragecondition;
		}
		
		public String getEditParentFacility() {
			return editparentfacility;
		}
		
		public String getStatus_AfterEdit() {
			return statusafteredit;
		}
		
		public String getStatus() {
			return finalstatus;
		}
		

}

