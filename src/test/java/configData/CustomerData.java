package configData;

import utilities.ConfigLoader;

public class CustomerData {

	public static String CHROME_URL;
	public static String APP_URL;
	public static String USERNAME;
	public static String PASSWORD;
	public static String PC_DB_NAME;
	public static String MASTER_DB_NAME;
	public static String MM_DB_NAME;
	public static String ACTIONSPERFORMEDBY;

	public static String USERNAME1;
	public static String PASSWORD1;
	public static String USERNAME2;
	public static String PASSWORD2;
	public static String USERNAME3;
	public static String PASSWORD3;
	public static String ACTUALHEADER;
	public static String EXPECTEDHEADER;
	public static String ACTUALDOCUEMNTNO;
	public static String EXPECTEDDOCUEMNTNO;
	public static String TEMPLATE_PATH;
	public static String OUTPUT_PATH;
	public static String SCRIPT_NUMBER;
	public static String TAKE_SCREENSHOTS;
	public static String TABLE_HEADERS;
	public static String TABLE_SEARCH_VALUES;

	public static String MASTER_MODULE;
	public static String CUSTOMER_NAME;
	public static String CITY;
	public static String CUSTOMER_CODE;
	public static String MARKET_REGION;
	public static String EDIT_CUSTOMER_IN_REVIEW_RETURN;

	public static String CUSTOMER_VIEW_ACTION;
	public static String CUSTOMER_RETURN_ACTION_IN_REVIEW;
	public static String CUSTOMER_RETURN_ACTION_IN_APPROVE;
	public static String EDIT_CUSTOMER_IN_APPROVE_RETURN;
	public static String REVIEW_RETURN_REMARKS;
	public static String APPROVE_RETURN_REMARKS;
	public static String REVIEW_REMARKS;
	public static String APPROVE_REMARKS;

	public static String INACTIVE_REMARKS;
	public static String CUSTOMER_INACTIVE_REJECT_IN_REVIEW_ACTION;
	public static String INACTIVE_REVIEW_REJECT_REMARKS;
	public static String INACTIVE_REVIEW__REMARKS;
	public static String CUSTOMER_INACTIVE_REJECT_IN_APPROVE_ACTION;
	public static String INACTIVE_APPROVE_REJECT_REMARKS;
	public static String INACTIVE_APPROVE_REMARKS;
	public static String ACTIVE_REMARKS;
	public static String CUSTOMER_ACTIVE_REJECT_IN_REVIEW_ACTION;
	public static String ACTIVE_REVIEW_REJECT_REMARKS;
	public static String ACTIVE_REVIEW__REMARKS;
	public static String CUSTOMER_ACTIVE_REJECT_IN_APPROVE_ACTION;
	public static String ACTIVE_APPROVE_REJECT_REMARKS;
	public static String ACTIVE_APPROVE_REMARKS;

	public static String CURRENT_CONFIG_NAME;

	public static void loadProperties(String fileName) {
		if (fileName == null || fileName.isEmpty()) {
			fileName = "akronquickproduct.properties";
		}

		CURRENT_CONFIG_NAME = fileName.split("\\.")[0];

		ConfigLoader loginconfig = new ConfigLoader("login.properties");
		ConfigLoader customerconfig = new ConfigLoader(fileName);

		CHROME_URL = loginconfig.get("chrome.url");
		APP_URL = loginconfig.get("app.url");
		USERNAME = loginconfig.get("username");
		PASSWORD = loginconfig.get("password");
		PC_DB_NAME = loginconfig.get("pc_DB_Name");
		MASTER_DB_NAME = loginconfig.get("master_DB_Name");
		MM_DB_NAME = loginconfig.get("mm_DB_Name");
		ACTIONSPERFORMEDBY = customerconfig.get("actions_PerformedBy");

		ACTUALHEADER = customerconfig.get("actualHeader");
		EXPECTEDHEADER = customerconfig.get("expectedHeader");
		ACTUALDOCUEMNTNO = customerconfig.get("actualDocumentNo");
		EXPECTEDDOCUEMNTNO = customerconfig.get("expectedDocumentNo");
		TEMPLATE_PATH = customerconfig.get("templatePath");
		OUTPUT_PATH = customerconfig.get("outputPath");
		SCRIPT_NUMBER = customerconfig.get("script_Number");
		TAKE_SCREENSHOTS = customerconfig.get("take_Screenshots");
		TABLE_HEADERS = customerconfig.get("table_Headers");
		TABLE_SEARCH_VALUES = customerconfig.get("table_Search_Values");
		USERNAME1 = customerconfig.get("username1");
		PASSWORD1 = customerconfig.get("password1");
		USERNAME2 = customerconfig.get("username2");
		PASSWORD2 = customerconfig.get("password2");
		USERNAME3 = customerconfig.get("username3");
		PASSWORD3 = customerconfig.get("password3");

		MASTER_MODULE = customerconfig.get("master_Module");

		CUSTOMER_NAME = customerconfig.get("customerName");
		CITY = customerconfig.get("city");
		CUSTOMER_CODE = customerconfig.get("customerCode");
		MARKET_REGION = customerconfig.get("marketRegion");
		CUSTOMER_VIEW_ACTION = customerconfig.get("customer_View");
		CUSTOMER_RETURN_ACTION_IN_REVIEW = customerconfig.get("customer_Return_In_Review");
		EDIT_CUSTOMER_IN_REVIEW_RETURN = customerconfig.get("editcustomer_Review_Return");
		REVIEW_RETURN_REMARKS = customerconfig.get("review_Return_Remarks");
		REVIEW_REMARKS = customerconfig.get("reviewRemarks");
		CUSTOMER_RETURN_ACTION_IN_APPROVE = customerconfig.get("customer_Return_In_Approve");
		EDIT_CUSTOMER_IN_APPROVE_RETURN = customerconfig.get("editcustomer_Approve_Return");
		APPROVE_RETURN_REMARKS = customerconfig.get("approve_Return_Remarks");
		APPROVE_REMARKS = customerconfig.get("approveRemarks");

		INACTIVE_REMARKS = customerconfig.get("inactive_Remarks");
		CUSTOMER_INACTIVE_REJECT_IN_REVIEW_ACTION = customerconfig.get("customer_Inactive_Reject_In_Review");
		INACTIVE_REVIEW_REJECT_REMARKS = customerconfig.get("inactive_Review_Reject_Remarks");
		INACTIVE_REVIEW__REMARKS = customerconfig.get("inactive_Review_Remarks");
		CUSTOMER_INACTIVE_REJECT_IN_APPROVE_ACTION = customerconfig.get("customer_Inactive_Reject_In_Approve");
		INACTIVE_APPROVE_REJECT_REMARKS = customerconfig.get("inactive_Approve_Reject_Remarks");
		INACTIVE_APPROVE_REMARKS = customerconfig.get("inactive_Approve_Remarks");
		ACTIVE_REMARKS = customerconfig.get("active_Remarks");
		CUSTOMER_ACTIVE_REJECT_IN_REVIEW_ACTION = customerconfig.get("customer_Active_Reject_In_Review");
		ACTIVE_REVIEW_REJECT_REMARKS = customerconfig.get("active_Review_Reject_Remarks");
		ACTIVE_REVIEW__REMARKS = customerconfig.get("active_Review_Remarks");
		CUSTOMER_ACTIVE_REJECT_IN_APPROVE_ACTION = customerconfig.get("customer_Active_Reject_In_Approve");
		ACTIVE_APPROVE_REJECT_REMARKS = customerconfig.get("active_Approve_Reject_Remarks");
		ACTIVE_APPROVE_REMARKS = customerconfig.get("active_Approve_Remarks");

	}
}
