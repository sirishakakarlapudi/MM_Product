package utilities;

import java.util.Map;

public class supplierData {
	
	
	private String suppliername;
	private String address;
	private String city;
	private String state;
	private String pincode;
	private String region;
	
	private String beforerevieweditsuppliername;
	private String beforerevieweditaddress;
	private String beforerevieweditcity;
	private String beforerevieweditstate;
	private String beforerevieweditpincode;
	private String beforerevieweditregion;
	private String reviews;
	private String approvals;
	private String editafterreviewreturnsuppliername;
	private String editafterreviewreturnaddress;
	private String editafterreviewreturncity;
	private String editafterreviewreturnstate;
	private String editafterreviewreturnpincode;
	private String editafterreviewreturnregion;
	private String editafterapprovereturnsuppliername;
	private String editafterapprovereturnaddress;
	private String editafterapprovereturncity;
	private String editafterapprovereturnstate;
	private String editafterapprovereturnpincode;
	private String editafterapprovereturnregion;

	public supplierData(Map<String, String> row) {
		try {
			
			this.suppliername = getValue(row, "Supplier Name");
		this.address = getValue(row, "Address");
		this.city = getValue(row, "City");
		this.state = getValue(row, "State");
		this.pincode= getValue(row, "Pincode");
		this.region= getValue(row, "Region");
		this.editafterreviewreturnsuppliername = getValue(row, "Edit After Review Return Supplier Name");
		this.editafterreviewreturnaddress = getValue(row, "Edit After Review Return Address");
		this.editafterreviewreturncity = getValue(row, "Edit After Review Return City");
		this.editafterreviewreturnstate = getValue(row, "Edit After Review Return State");
		this.editafterreviewreturnpincode= getValue(row, "Edit After Review Return Pincode");
		this.editafterreviewreturnregion= getValue(row, "Edit After Review Return Region");
		this.beforerevieweditsuppliername = getValue(row, "Before Review Edit Supplier Name");
		this.beforerevieweditaddress = getValue(row, "Before Review Edit Address");
		this.beforerevieweditcity = getValue(row, "Before Review Edit City");
		this.beforerevieweditstate = getValue(row, "Before Review Edit State");
		this.beforerevieweditpincode= getValue(row, "Before Review Edit Pincode");
		this.beforerevieweditregion= getValue(row, "Before Review Edit Region");
		this.reviews=getValue(row, "Reviews(Review/Return)");
		this.approvals=getValue(row, "Approvals(Accept/Return)");
		this.editafterapprovereturnsuppliername = getValue(row, "Edit After Approve Return Supplier Name");
		this.editafterapprovereturnaddress = getValue(row, "Edit After Approve Return Address");
		this.editafterapprovereturncity = getValue(row, "Edit After Approve Return City");
		this.editafterapprovereturnstate = getValue(row, "Edit After Approve Return State");
		this.editafterapprovereturnpincode= getValue(row, "Edit After Approve Return Pincode");
		this.editafterapprovereturnregion= getValue(row, "Edit After Approve Return Region");
		
		
		
			
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

		public String getSupplierName() {
			return suppliername;
		}

		public String getAddress() {
			return address;
		}

		public String getCity() {
			return city;
		}

		public String getState() {
			return state;
		}

		public String getPincode() {
			return pincode;
		}

		public String getRegion() {
			return region;
		}

		public String getBeforeReviewEditSupplierName() {
			return beforerevieweditsuppliername;
		}

		public String getBeforeReviewEditAddress() {
			return beforerevieweditaddress;
		}

		public String getBeforeReviewEditCity() {
			return beforerevieweditcity;
		}

		public String getBeforeReviewEditState() {
			return beforerevieweditstate;
		}

		public String getBeforeReviewEditPincode() {
			return beforerevieweditpincode;
		}

		public String getBeforeReviewEditRegion() {
			return beforerevieweditregion;
		}
		
		public String getReviews() {
			return reviews;
		}

		public String getApprovals() {
			return approvals;
		}

		public String getEditAfterReviewReturnSupplierName() {
			return editafterreviewreturnsuppliername;
		}

		public String getEditAfterReviewReturnAddress() {
			return editafterreviewreturnaddress;
		}

		public String getEditAfterReviewReturnCity() {
			return editafterreviewreturncity;
		}

		public String getEditAfterReviewReturnState() {
			return editafterreviewreturnstate;
		}

		public String getEditAfterReviewReturnPincode() {
			return editafterreviewreturnpincode;
		}

		public String getEditAfterReviewReturnRegion() {
			return editafterreviewreturnregion;
		}

		public String getEditAfterApproveReturnSupplierName() {
			return editafterapprovereturnsuppliername;
		}

		public String getEditAfterApproveReturnAddress() {
			return editafterapprovereturnaddress;
		}

		public String getEditAfterApproveReturnCity() {
			return editafterapprovereturncity;
		}

		public String getEditAfterApproveReturnState() {
			return editafterapprovereturnstate;
		}

		public String getEditAfterApproveReturnPincode() {
			return editafterapprovereturnpincode;
		}

		public String getEditAfterApproveReturnRegion() {
			return editafterapprovereturnregion;
		}
		
}
