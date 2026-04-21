package configData;

import utilities.ConfigLoader;

public class AkronBOMData {
	public static String CURRENT_CONFIG_NAME;
	public static String CHROME_URL;
	public static String APP_URL;
	public static String USERNAME;
	public static String PASSWORD;
	public static String USERNAME4;
	public static String PASSWORD4;
	public static String USERNAME5;
	public static String PASSWORD5;
	public static String USERNAME6;
	public static String PASSWORD6;
	public static String ACTIONSPERFORMEDBY;
	public static String PC_DB_NAME;
	public static String MASTER_DB_NAME;
	public static String MM_DB_NAME;
	public static String ACTUALHEADER;
	public static String EXPECTEDHEADER;
	public static String SCRIPT_NUMBER;
	public static String TEMPLATE_PATH;
	public static String OUTPUT_PATH;
	public static String MASTER_MODULE;

	
	public static String PRODUCT_CODE;
	public static String LOCATION;
	public static String BATCH_SIZE;
	public static String BATCH_SIZE_UOM;
	public static String YIELD_RANGE_MIN;
	public static String YIELD_RANGE_MAX;
	public static String YIELD_RANGE_TARGET;
	public static String STANDARD_BOM;
	
	public static String MATERIAL_CODE;
	public static String STANDARD_QUANTITY;
	public static String TOLERANCE;
	
	public static String EDIT_BATCH_SIZE_IN_REVIEW_RETURN;
	public static String EDIT_BATCH_SIZE_IN_APPROVE_RETURN;
	
	
	public static String BOM_VIEW_ACTION;
	public static String BOM_RETURN_ACTION_IN_REVIEW;
	public static String BOM_RETURN_ACTION_IN_APPROVE;
	public static String BOM_UPDATE_AFTER_APPROVE;
	
	public static String REVIEW_REMARKS;
	public static String APPROVE_REMARKS;
	public static String REVIEW_RETURN_REMARKS;
	public static String APPROVE_RETURN_REMARKS;
	
	public static String TAKE_SCREENSHOTS;
	public static String TABLE_HEADERS;

	public static void loadProperties(String fileName) {
		CURRENT_CONFIG_NAME = fileName;
		ConfigLoader loginconfig = new ConfigLoader("Login/login.properties");
		ConfigLoader bomconfig = new ConfigLoader(fileName);

		USERNAME = loginconfig.get("username");
		PASSWORD = loginconfig.get("password");
		APP_URL = loginconfig.get("app.url");
		CHROME_URL = loginconfig.get("chrome.url");

		USERNAME4 = loginconfig.get("username4");
		PASSWORD4 = loginconfig.get("password4");
		USERNAME5 = loginconfig.get("username5");
		PASSWORD5 = loginconfig.get("password5");
		USERNAME6 = loginconfig.get("username6");
		PASSWORD6 = loginconfig.get("password6");

		ACTIONSPERFORMEDBY = bomconfig.get("actions_PerformedBy");
		PC_DB_NAME = loginconfig.get("pc_DB_Name");
		MASTER_DB_NAME = loginconfig.get("master_DB_Name");
		MM_DB_NAME = loginconfig.get("mm_DB_Name");

		ACTUALHEADER = bomconfig.get("actualHeader");
		EXPECTEDHEADER = bomconfig.get("expectedHeader");
		SCRIPT_NUMBER = bomconfig.get("script_Number");
		TEMPLATE_PATH = bomconfig.get("templatePath");
		OUTPUT_PATH = bomconfig.get("outputPath");
		MASTER_MODULE = bomconfig.get("master_Module");
		

		PRODUCT_CODE = bomconfig.get("productCode");
		LOCATION = bomconfig.get("location");
		BATCH_SIZE = bomconfig.get("batchSize");
		BATCH_SIZE_UOM = bomconfig.get("batchSizeUom");
		YIELD_RANGE_MIN = bomconfig.get("yieldMinRange");
		YIELD_RANGE_MAX = bomconfig.get("yieldMaxRange");
		YIELD_RANGE_TARGET = bomconfig.get("yieldTarget");
		STANDARD_BOM = bomconfig.get("standardBom");

		MATERIAL_CODE = bomconfig.get("materialCode");
		STANDARD_QUANTITY = bomconfig.get("standardQuantity");
		TOLERANCE = bomconfig.get("tolerance");

		EDIT_BATCH_SIZE_IN_REVIEW_RETURN = bomconfig.get("editBatch_Size_In_Review_Return");
		EDIT_BATCH_SIZE_IN_APPROVE_RETURN = bomconfig.get("editBatch_Size_In_Approve_Return");
	

		BOM_VIEW_ACTION = bomconfig.get("bom_View");
		BOM_RETURN_ACTION_IN_REVIEW = bomconfig.get("bom_Return_In_Review");
		BOM_RETURN_ACTION_IN_APPROVE = bomconfig.get("bom_Return_In_Approve");
		BOM_UPDATE_AFTER_APPROVE = bomconfig.get("bom_Update_After_Approve");

		REVIEW_REMARKS = bomconfig.get("reviewRemarks");
		APPROVE_REMARKS = bomconfig.get("approveRemarks");
		REVIEW_RETURN_REMARKS = bomconfig.get("review_Return_Remarks");
		APPROVE_RETURN_REMARKS = bomconfig.get("approve_Return_Remarks");

		TAKE_SCREENSHOTS = bomconfig.get("take_Screenshots");
		TABLE_HEADERS = bomconfig.get("table_Headers");
	}
}
