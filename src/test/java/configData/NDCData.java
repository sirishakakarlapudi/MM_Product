package configData;

import utilities.ConfigLoader;

public class NDCData {

	public static String CURRENT_CONFIG_NAME;
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
	public static String MASTER_MODULE;
	public static String SUB_MASTER_MODULE;
	public static String TABLE_HEADERS;
	public static String TABLE_SEARCH_VALUES;
	public static String NDC_NUMBER;
	public static String NDC_VIEW_ACTION;
	public static String INACTIVE_REMARKS;
	public static String NDC_INACTIVE_REJECT_IN_REVIEW_ACTION;
	public static String INACTIVE_REVIEW_REJECT_REMARKS;
	public static String INACTIVE_REVIEW__REMARKS;
	public static String NDC_INACTIVE_REJECT_IN_APPROVE_ACTION;
	public static String INACTIVE_APPROVE_REJECT_REMARKS;
	public static String INACTIVE_APPROVE_REMARKS;
	public static String ACTIVE_REMARKS;
	public static String NDC_ACTIVE_REJECT_IN_REVIEW_ACTION;
	public static String ACTIVE_REVIEW_REJECT_REMARKS;
	public static String ACTIVE_REVIEW__REMARKS;
	public static String NDC_ACTIVE_REJECT_IN_APPROVE_ACTION;
	public static String ACTIVE_APPROVE_REJECT_REMARKS;
	public static String ACTIVE_APPROVE_REMARKS;

	public static void loadProperties(String fileName) {
		// Ensure we handle potential null or empty fileName
		if (fileName == null || fileName.isEmpty()) {
			fileName = "ndc.properties";
		}

		// Store config name without extension for backup folder fallback
		CURRENT_CONFIG_NAME = fileName.split("\\.")[0];

		ConfigLoader loginconfig = new ConfigLoader("login.properties");
		ConfigLoader ndcconfig = new ConfigLoader(fileName);

		CHROME_URL = loginconfig.get("chrome.url");
		APP_URL = loginconfig.get("app.url");
		USERNAME = loginconfig.get("username");
		PASSWORD = loginconfig.get("password");
		PC_DB_NAME = loginconfig.get("pc_DB_Name");
		MASTER_DB_NAME = loginconfig.get("master_DB_Name");
		MM_DB_NAME = loginconfig.get("mm_DB_Name");

		ACTIONSPERFORMEDBY = ndcconfig.get("actions_PerformedBy");
		USERNAME1 = ndcconfig.get("username1");
		PASSWORD1 = ndcconfig.get("password1");
		USERNAME2 = ndcconfig.get("username2");
		PASSWORD2 = ndcconfig.get("password2");
		USERNAME3 = ndcconfig.get("username3");
		PASSWORD3 = ndcconfig.get("password3");
		ACTUALHEADER = ndcconfig.get("actualHeader");
		EXPECTEDHEADER = ndcconfig.get("expectedHeader");
		ACTUALDOCUEMNTNO = ndcconfig.get("actualDocumentNo");
		EXPECTEDDOCUEMNTNO = ndcconfig.get("expectedDocumentNo");
		TEMPLATE_PATH = ndcconfig.get("templatePath");
		OUTPUT_PATH = ndcconfig.get("outputPath");
		SCRIPT_NUMBER = ndcconfig.get("script_Number");
		TAKE_SCREENSHOTS = ndcconfig.get("take_Screenshots");
		MASTER_MODULE = ndcconfig.get("master_Module");
		SUB_MASTER_MODULE = ndcconfig.get("sub_Master_Module");
		TABLE_HEADERS = ndcconfig.get("table_Headers");
		TABLE_SEARCH_VALUES = ndcconfig.get("table_Search_Values");
		NDC_NUMBER = ndcconfig.get("ndcNumber");
		INACTIVE_REMARKS = ndcconfig.get("inactive_Remarks");
		NDC_VIEW_ACTION = ndcconfig.get("ndc_View");
		NDC_INACTIVE_REJECT_IN_REVIEW_ACTION = ndcconfig.get("ndc_Inactive_Reject_In_Review");
		INACTIVE_REVIEW_REJECT_REMARKS = ndcconfig.get("inactive_Review_Reject_Remarks");
		INACTIVE_REVIEW__REMARKS = ndcconfig.get("inactive_Review_Remarks");
		NDC_INACTIVE_REJECT_IN_APPROVE_ACTION = ndcconfig.get("ndc_Inactive_Reject_In_Approve");
		INACTIVE_APPROVE_REJECT_REMARKS = ndcconfig.get("inactive_Approve_Reject_Remarks");
		INACTIVE_APPROVE_REMARKS = ndcconfig.get("inactive_Approve_Remarks");
		ACTIVE_REMARKS = ndcconfig.get("active_Remarks");
		NDC_ACTIVE_REJECT_IN_REVIEW_ACTION = ndcconfig.get("ndc_Active_Reject_In_Review");
		ACTIVE_REVIEW_REJECT_REMARKS = ndcconfig.get("active_Review_Reject_Remarks");
		ACTIVE_REVIEW__REMARKS = ndcconfig.get("active_Review_Remarks");
		NDC_ACTIVE_REJECT_IN_APPROVE_ACTION = ndcconfig.get("ndc_Active_Reject_In_Approve");
		ACTIVE_APPROVE_REJECT_REMARKS = ndcconfig.get("active_Approve_Reject_Remarks");
		ACTIVE_APPROVE_REMARKS = ndcconfig.get("active_Approve_Remarks");
	}

}
