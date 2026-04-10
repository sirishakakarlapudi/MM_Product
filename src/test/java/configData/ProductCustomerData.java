package configData;

import utilities.ConfigLoader;

public class ProductCustomerData {

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
	public static String SUB_MASTER_MODULE;
	public static String PRODUCT;
	public static String MARKET_REGION;
	public static String CUSTOMER;
	public static String EDIT_CUSTOMER_IN_REVIEW_RETURN;

	public static String PRODUCT_CUSTOMER_VIEW_ACTION;
	public static String PRODUCT_CUSTOMER_RETURN_ACTION_IN_REVIEW;
	public static String PRODUCT_CUSTOMER_RETURN_ACTION_IN_APPROVE;
	public static String EDIT_CUSTOMER_IN_APPROVE_RETURN;
	public static String REVIEW_RETURN_REMARKS;
	public static String APPROVE_RETURN_REMARKS;
	public static String REVIEW_REMARKS;
	public static String APPROVE_REMARKS;

	public static String INACTIVE_REMARKS;
	public static String PRODUCT_CUSTOMER_INACTIVE_REJECT_IN_REVIEW_ACTION;
	public static String INACTIVE_REVIEW_REJECT_REMARKS;
	public static String INACTIVE_REVIEW__REMARKS;
	public static String PRODUCT_CUSTOMER_INACTIVE_REJECT_IN_APPROVE_ACTION;
	public static String INACTIVE_APPROVE_REJECT_REMARKS;
	public static String INACTIVE_APPROVE_REMARKS;
	public static String ACTIVE_REMARKS;
	public static String PRODUCT_CUSTOMER_ACTIVE_REJECT_IN_REVIEW_ACTION;
	public static String ACTIVE_REVIEW_REJECT_REMARKS;
	public static String ACTIVE_REVIEW__REMARKS;
	public static String PRODUCT_CUSTOMER_ACTIVE_REJECT_IN_APPROVE_ACTION;
	public static String ACTIVE_APPROVE_REJECT_REMARKS;
	public static String ACTIVE_APPROVE_REMARKS;

	public static String CURRENT_CONFIG_NAME;

	public static void loadProperties(String fileName) {
		if (fileName == null || fileName.isEmpty()) {
			fileName = "productcustomer.properties";
		}

		CURRENT_CONFIG_NAME = fileName.split("\\.")[0];

		ConfigLoader loginconfig = new ConfigLoader("login.properties");
		ConfigLoader productcustomerconfig = new ConfigLoader(fileName);

		CHROME_URL = loginconfig.get("chrome.url");
		APP_URL = loginconfig.get("app.url");
		USERNAME = loginconfig.get("username");
		PASSWORD = loginconfig.get("password");
		PC_DB_NAME = loginconfig.get("pc_DB_Name");
		MASTER_DB_NAME = loginconfig.get("master_DB_Name");
		MM_DB_NAME = loginconfig.get("mm_DB_Name");
		ACTIONSPERFORMEDBY = productcustomerconfig.get("actions_PerformedBy");

		ACTUALHEADER = productcustomerconfig.get("actualHeader");
		EXPECTEDHEADER = productcustomerconfig.get("expectedHeader");
		ACTUALDOCUEMNTNO = productcustomerconfig.get("actualDocumentNo");
		EXPECTEDDOCUEMNTNO = productcustomerconfig.get("expectedDocumentNo");
		TEMPLATE_PATH = productcustomerconfig.get("templatePath");
		OUTPUT_PATH = productcustomerconfig.get("outputPath");
		SCRIPT_NUMBER = productcustomerconfig.get("script_Number");
		TAKE_SCREENSHOTS = productcustomerconfig.get("take_Screenshots");
		TABLE_HEADERS = productcustomerconfig.get("table_Headers");
		TABLE_SEARCH_VALUES = productcustomerconfig.get("table_Search_Values");
		USERNAME1 = productcustomerconfig.get("username1");
		PASSWORD1 = productcustomerconfig.get("password1");
		USERNAME2 = productcustomerconfig.get("username2");
		PASSWORD2 = productcustomerconfig.get("password2");
		USERNAME3 = productcustomerconfig.get("username3");
		PASSWORD3 = productcustomerconfig.get("password3");
		MASTER_MODULE = productcustomerconfig.get("master_Module");
		SUB_MASTER_MODULE = productcustomerconfig.get("sub_Master_Module");
		PRODUCT = productcustomerconfig.get("product");
		CUSTOMER = productcustomerconfig.get("customer");
		MARKET_REGION = productcustomerconfig.get("marketRegion");
		PRODUCT_CUSTOMER_VIEW_ACTION = productcustomerconfig.get("productcustomer_View");
		PRODUCT_CUSTOMER_RETURN_ACTION_IN_REVIEW = productcustomerconfig.get("productcustomer_Return_In_Review");
		EDIT_CUSTOMER_IN_REVIEW_RETURN = productcustomerconfig.get("editcustomer_Review_Return");
		REVIEW_RETURN_REMARKS = productcustomerconfig.get("review_Return_Remarks");
		REVIEW_REMARKS = productcustomerconfig.get("reviewRemarks");
		PRODUCT_CUSTOMER_RETURN_ACTION_IN_APPROVE = productcustomerconfig.get("productcustomer_Return_In_Approve");
		EDIT_CUSTOMER_IN_APPROVE_RETURN = productcustomerconfig.get("editcustomer_Approve_Return");
		APPROVE_RETURN_REMARKS = productcustomerconfig.get("approve_Return_Remarks");
		APPROVE_REMARKS = productcustomerconfig.get("approveRemarks");

		INACTIVE_REMARKS = productcustomerconfig.get("inactive_Remarks");
		PRODUCT_CUSTOMER_INACTIVE_REJECT_IN_REVIEW_ACTION = productcustomerconfig.get("productcustomer_Inactive_Reject_In_Review");
		INACTIVE_REVIEW_REJECT_REMARKS = productcustomerconfig.get("inactive_Review_Reject_Remarks");
		INACTIVE_REVIEW__REMARKS = productcustomerconfig.get("inactive_Review_Remarks");
		PRODUCT_CUSTOMER_INACTIVE_REJECT_IN_APPROVE_ACTION = productcustomerconfig.get("productcustomer_Inactive_Reject_In_Approve");
		INACTIVE_APPROVE_REJECT_REMARKS = productcustomerconfig.get("inactive_Approve_Reject_Remarks");
		INACTIVE_APPROVE_REMARKS = productcustomerconfig.get("inactive_Approve_Remarks");
		ACTIVE_REMARKS = productcustomerconfig.get("active_Remarks");
		PRODUCT_CUSTOMER_ACTIVE_REJECT_IN_REVIEW_ACTION = productcustomerconfig.get("productcustomer_Active_Reject_In_Review");
		ACTIVE_REVIEW_REJECT_REMARKS = productcustomerconfig.get("active_Review_Reject_Remarks");
		ACTIVE_REVIEW__REMARKS = productcustomerconfig.get("active_Review_Remarks");
		PRODUCT_CUSTOMER_ACTIVE_REJECT_IN_APPROVE_ACTION = productcustomerconfig.get("productcustomer_Active_Reject_In_Approve");
		ACTIVE_APPROVE_REJECT_REMARKS = productcustomerconfig.get("active_Approve_Reject_Remarks");
		ACTIVE_APPROVE_REMARKS = productcustomerconfig.get("active_Approve_Remarks");

	}
}
