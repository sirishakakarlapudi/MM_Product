package configData;

import utilities.ConfigLoader;

public class AkronQuickProductData {

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
	public static String PRODUCT_NAME;
	public static String PRODUCT_CODE;
	public static String PRODUCT_DESCRIPTION;
	public static String STORAGE_LOCATION;
	public static String STORAGE_CONDITION;
	public static String SAMPLING_ACTIVITY;
	public static String CLEANING_AGENT;

	public static String NDC_NUMBER;
	public static String SHORT_CODE;
	public static String UOM;
	public static String PACK_SIZE;
	public static String NDC_DESCRIPTION;
	public static String EDIT_PRODUCT_IN_REVIEW_RETURN;

	public static String PRODUCT_VIEW_ACTION;
	public static String PRODUCT_RETURN_ACTION_IN_REVIEW;
	public static String PRODUCT_RETURN_ACTION_IN_APPROVE;
	public static String EDIT_PRODUCT_IN_APPROVE_RETURN;
	public static String REVIEW_RETURN_REMARKS;
	public static String APPROVE_RETURN_REMARKS;
	public static String REVIEW_REMARKS;
	public static String APPROVE_REMARKS;

	public static String CURRENT_CONFIG_NAME;

	public static void loadProperties(String fileName) {
		if (fileName == null || fileName.isEmpty()) {
			fileName = "akronquickproduct.properties";
		}

		CURRENT_CONFIG_NAME = fileName.split("\\.")[0];

		ConfigLoader loginconfig = new ConfigLoader("login.properties");
		ConfigLoader quickproductconfig = new ConfigLoader(fileName);

		CHROME_URL = loginconfig.get("chrome.url");
		APP_URL = loginconfig.get("app.url");
		USERNAME = loginconfig.get("username");
		PASSWORD = loginconfig.get("password");
		PC_DB_NAME = loginconfig.get("pc_DB_Name");
		MASTER_DB_NAME = loginconfig.get("master_DB_Name");
		MM_DB_NAME = loginconfig.get("mm_DB_Name");
		ACTIONSPERFORMEDBY = quickproductconfig.get("actions_PerformedBy");

		ACTUALHEADER = quickproductconfig.get("actualHeader");
		EXPECTEDHEADER = quickproductconfig.get("expectedHeader");
		ACTUALDOCUEMNTNO = quickproductconfig.get("actualDocumentNo");
		EXPECTEDDOCUEMNTNO = quickproductconfig.get("expectedDocumentNo");
		TEMPLATE_PATH = quickproductconfig.get("templatePath");
		OUTPUT_PATH = quickproductconfig.get("outputPath");
		SCRIPT_NUMBER = quickproductconfig.get("script_Number");
		TAKE_SCREENSHOTS = quickproductconfig.get("take_Screenshots");
		TABLE_HEADERS = quickproductconfig.get("table_Headers");
		TABLE_SEARCH_VALUES =  quickproductconfig.get("table_Search_Values");
		USERNAME1 = quickproductconfig.get("username1");
		PASSWORD1 = quickproductconfig.get("password1");
		USERNAME2 = quickproductconfig.get("username2");
		PASSWORD2 = quickproductconfig.get("password2");
		USERNAME3 = quickproductconfig.get("username3");
		PASSWORD3 = quickproductconfig.get("password3");

		MASTER_MODULE = quickproductconfig.get("masterModule");
		SUB_MASTER_MODULE = quickproductconfig.get("submasterModule");
		PRODUCT_NAME = quickproductconfig.get("productName");
		PRODUCT_CODE = quickproductconfig.get("productCode");
		PRODUCT_DESCRIPTION = quickproductconfig.get("productDescription");

		STORAGE_CONDITION = quickproductconfig.get("storageCondition");
		STORAGE_LOCATION = quickproductconfig.get("storageLocation");
		SAMPLING_ACTIVITY = quickproductconfig.get("samplingActivity");
		CLEANING_AGENT = quickproductconfig.get("cleaningAgent");
		NDC_NUMBER = quickproductconfig.get("ndcNumber");
		SHORT_CODE = quickproductconfig.get("shortCode");
		UOM = quickproductconfig.get("uom");
		PACK_SIZE = quickproductconfig.get("packsize");
		NDC_DESCRIPTION = quickproductconfig.get("ndcDescription");
		PRODUCT_VIEW_ACTION = quickproductconfig.get("product_View");
		PRODUCT_RETURN_ACTION_IN_REVIEW = quickproductconfig.get("product_Return_In_Review");
		EDIT_PRODUCT_IN_REVIEW_RETURN = quickproductconfig.get("editproduct_Review_Return");
		REVIEW_RETURN_REMARKS = quickproductconfig.get("review_Return_Remarks");
		REVIEW_REMARKS = quickproductconfig.get("reviewRemarks");
		PRODUCT_RETURN_ACTION_IN_APPROVE = quickproductconfig.get("product_Return_In_Approve");
		EDIT_PRODUCT_IN_APPROVE_RETURN = quickproductconfig.get("editproduct_Approve_Return");
		APPROVE_RETURN_REMARKS = quickproductconfig.get("approve_Return_Remarks");
		APPROVE_REMARKS = quickproductconfig.get("approveRemarks");

	}
}
