package configData;

import utilities.ConfigLoader;

public class AkronProductData {

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
	public static String PRODUCT_NAME;
	public static String PRODUCT_VIEW_ACTION;
	public static String REVIEW_RETURN_REMARKS;
	public static String REVIEW_REMARKS;
	public static String APPROVE_RETURN_REMARKS;
	public static String APPROVE_REMARKS;
	public static String INACTIVE_REMARKS;
	
	public static String PRODUCT_INACTIVE_REJECT_IN_REVIEW_ACTION;
	public static String INACTIVE_REVIEW_REJECT_REMARKS;
	public static String INACTIVE_REVIEW__REMARKS;
	public static String PRODUCT_INACTIVE_REJECT_IN_APPROVE_ACTION;
	public static String INACTIVE_APPROVE_REJECT_REMARKS;
	public static String INACTIVE_APPROVE_REMARKS;
	public static String ACTIVE_REMARKS;
	public static String PRODUCT_ACTIVE_REJECT_IN_REVIEW_ACTION;
	public static String ACTIVE_REVIEW_REJECT_REMARKS;
	public static String ACTIVE_REVIEW__REMARKS;
	public static String PRODUCT_ACTIVE_REJECT_IN_APPROVE_ACTION;
	public static String ACTIVE_APPROVE_REJECT_REMARKS;
	public static String ACTIVE_APPROVE_REMARKS;
	public static String ADD_NDC_NDC_NUMBER;
	public static String ADD_NDC_SHORT_CODE;
	public static String ADD_NDC_PACK_SIZE;
	public static String ADD_NDC_NDC_DESCRIPTION;
	public static String ADD_NDC_UOM;
	public static String ADD_NDC_GTN_NUMBER;
	public static String ADD_NDC_EDIT_ACTION_AFTER_REVIEW_RETURN;
	public static String ADD_NDC_EDIT_EXISTING_INDEX_AFTER_REVIEW_RETURN;
	public static String ADD_NDC_EDIT_EXISTING_NDC_NUMBER_AFTER_REVIEW_RETURN;
	public static String ADD_NDC_EDIT_EXISTING_SHORT_CODE_AFTER_REVIEW_RETURN;
	public static String ADD_NDC_EDIT_EXISTING_PACK_SIZE_AFTER_REVIEW_RETURN;
	public static String ADD_NDC_EDIT_EXISTING_NDC_DESCRIPTION_AFTER_REVIEW_RETURN;
	public static String ADD_NDC_EDIT_EXISTING_GTN_NUMBER_AFTER_REVIEW_RETURN;
	public static String ADD_NDC_EDIT_NEW_NDC_NUMBER_AFTER_REVIEW_RETURN;
	public static String ADD_NDC_EDIT_NEW_SHORT_CODE_AFTER_REVIEW_RETURN;
	public static String ADD_NDC_EDIT_NEW_PACK_SIZE_AFTER_REVIEW_RETURN;
	public static String ADD_NDC_EDIT_NEW_NDC_DESCRIPTION_AFTER_REVIEW_RETURN;
	public static String ADD_NDC_EDIT_NEW_GTN_NUMBER_AFTER_REVIEW_RETURN;
	public static String ADD_NDC_EDIT_DELETE_INDEX_AFTER_REVIEW_RETURN;
	public static String ADD_NDC_EDIT_ACTION_AFTER_APPROVE_RETURN;
	public static String ADD_NDC_EDIT_EXISTING_INDEX_AFTER_APPROVE_RETURN;
	public static String ADD_NDC_EDIT_EXISTING_NDC_NUMBER_AFTER_APPROVE_RETURN;
	public static String ADD_NDC_EDIT_EXISTING_SHORT_CODE_AFTER_APPROVE_RETURN;
	public static String ADD_NDC_EDIT_EXISTING_PACK_SIZE_AFTER_APPROVE_RETURN;
	public static String ADD_NDC_EDIT_EXISTING_NDC_DESCRIPTION_AFTER_APPROVE_RETURN;
	public static String ADD_NDC_EDIT_EXISTING_GTN_NUMBER_AFTER_APPROVE_RETURN;
	public static String ADD_NDC_EDIT_NEW_NDC_NUMBER_AFTER_APPROVE_RETURN;
	public static String ADD_NDC_EDIT_NEW_SHORT_CODE_AFTER_APPROVE_RETURN;
	public static String ADD_NDC_EDIT_NEW_PACK_SIZE_AFTER_APPROVE_RETURN;
	public static String ADD_NDC_EDIT_NEW_NDC_DESCRIPTION_AFTER_APPROVE_RETURN;
	public static String ADD_NDC_EDIT_NEW_GTN_NUMBER_AFTER_APPROVE_RETURN;
	public static String ADD_NDC_EDIT_DELETE_INDEX_AFTER_APPROVE_RETURN;

	public static void loadProperties(String fileName) {
		// Ensure we handle potential null or empty fileName
		if (fileName == null || fileName.isEmpty()) {
			fileName = "akronproduct.properties";
		}

		// Store config name without extension for backup folder fallback
		CURRENT_CONFIG_NAME = fileName.split("\\.")[0];

		ConfigLoader loginconfig = new ConfigLoader("login.properties");
		ConfigLoader productconfig = new ConfigLoader(fileName);

		CHROME_URL = loginconfig.get("chrome.url");
		APP_URL = loginconfig.get("app.url");
		USERNAME = loginconfig.get("username");
		PASSWORD = loginconfig.get("password");
		PC_DB_NAME = loginconfig.get("pc_DB_Name");
		MASTER_DB_NAME = loginconfig.get("master_DB_Name");
		MM_DB_NAME = loginconfig.get("mm_DB_Name");

		ACTIONSPERFORMEDBY = productconfig.get("actions_PerformedBy");
		USERNAME1 = productconfig.get("username1");
		PASSWORD1 = productconfig.get("password1");
		USERNAME2 = productconfig.get("username2");
		PASSWORD2 = productconfig.get("password2");
		USERNAME3 = productconfig.get("username3");
		PASSWORD3 = productconfig.get("password3");
		ACTUALHEADER = productconfig.get("actualHeader");
		EXPECTEDHEADER = productconfig.get("expectedHeader");
		ACTUALDOCUEMNTNO = productconfig.get("actualDocumentNo");
		EXPECTEDDOCUEMNTNO = productconfig.get("expectedDocumentNo");
		TEMPLATE_PATH = productconfig.get("templatePath");
		OUTPUT_PATH = productconfig.get("outputPath");
		SCRIPT_NUMBER = productconfig.get("script_Number");
		TAKE_SCREENSHOTS = productconfig.get("take_Screenshots");
		MASTER_MODULE = productconfig.get("master_Module");
		SUB_MASTER_MODULE = productconfig.get("sub_Master_Module");
		TABLE_HEADERS = productconfig.get("table_Headers");
		TABLE_SEARCH_VALUES = productconfig.get("table_Search_Values");
		PRODUCT_NAME = productconfig.get("productName");
		PRODUCT_VIEW_ACTION = productconfig.get("product_View");
		REVIEW_RETURN_REMARKS = productconfig.get("review_Return_Remarks");
		REVIEW_REMARKS = productconfig.get("reviewRemarks");
		APPROVE_RETURN_REMARKS = productconfig.get("approve_Return_Remarks");
		APPROVE_REMARKS = productconfig.get("approveRemarks");
		INACTIVE_REMARKS = productconfig.get("inactive_Remarks");
		PRODUCT_INACTIVE_REJECT_IN_REVIEW_ACTION = productconfig.get("product_Inactive_Reject_In_Review");
		INACTIVE_REVIEW_REJECT_REMARKS = productconfig.get("inactive_Review_Reject_Remarks");
		INACTIVE_REVIEW__REMARKS = productconfig.get("inactive_Review_Remarks");
		PRODUCT_INACTIVE_REJECT_IN_APPROVE_ACTION = productconfig.get("product_Inactive_Reject_In_Approve");
		INACTIVE_APPROVE_REJECT_REMARKS = productconfig.get("inactive_Approve_Reject_Remarks");
		INACTIVE_APPROVE_REMARKS = productconfig.get("inactive_Approve_Remarks");
		ACTIVE_REMARKS = productconfig.get("active_Remarks");
		PRODUCT_ACTIVE_REJECT_IN_REVIEW_ACTION = productconfig.get("product_Active_Reject_In_Review");
		ACTIVE_REVIEW_REJECT_REMARKS = productconfig.get("active_Review_Reject_Remarks");
		ACTIVE_REVIEW__REMARKS = productconfig.get("active_Review_Remarks");
		PRODUCT_ACTIVE_REJECT_IN_APPROVE_ACTION = productconfig.get("product_Active_Reject_In_Approve");
		ACTIVE_APPROVE_REJECT_REMARKS = productconfig.get("active_Approve_Reject_Remarks");
		ACTIVE_APPROVE_REMARKS = productconfig.get("active_Approve_Remarks");
		
		ADD_NDC_NDC_NUMBER = productconfig.get("add_NDC_NDC_Number");
		ADD_NDC_SHORT_CODE = productconfig.get("add_NDC_Short_Code");
		ADD_NDC_PACK_SIZE = productconfig.get("add_NDC_Pack_Size");
		ADD_NDC_NDC_DESCRIPTION = productconfig.get("add_NDC_NDC_Description");
		ADD_NDC_UOM= productconfig.get("add_NDC_UOM");
		ADD_NDC_GTN_NUMBER= productconfig.get("add_GTN_Number");
		ADD_NDC_EDIT_ACTION_AFTER_REVIEW_RETURN = productconfig.get("add_NDC_Edit_Action_After_Review_Return");
		ADD_NDC_EDIT_EXISTING_INDEX_AFTER_REVIEW_RETURN = productconfig.get("add_NDC_Edit_Existing_Index_After_Review_Return");
		ADD_NDC_EDIT_EXISTING_NDC_NUMBER_AFTER_REVIEW_RETURN = productconfig.get("add_NDC_Edit_Existing_NDC_Number_After_Review_Return");
		ADD_NDC_EDIT_EXISTING_SHORT_CODE_AFTER_REVIEW_RETURN = productconfig.get("add_NDC_Edit_Existing_Short_Code_After_Review_Return");
		ADD_NDC_EDIT_EXISTING_PACK_SIZE_AFTER_REVIEW_RETURN = productconfig.get("add_NDC_Edit_Existing_Pack_Size_After_Review_Return");
		ADD_NDC_EDIT_EXISTING_NDC_DESCRIPTION_AFTER_REVIEW_RETURN = productconfig.get("add_NDC_Edit_Existing_NDC_Description_After_Review_Return");
		ADD_NDC_EDIT_EXISTING_GTN_NUMBER_AFTER_REVIEW_RETURN = productconfig.get("add_NDC_Edit_Existing_GTN_Number_After_Review_Return");
		ADD_NDC_EDIT_NEW_NDC_NUMBER_AFTER_REVIEW_RETURN = productconfig.get("add_NDC_Edit_New_NDC_Number_After_Review_Return");
		ADD_NDC_EDIT_NEW_SHORT_CODE_AFTER_REVIEW_RETURN = productconfig.get("add_NDC_Edit_New_Short_Code_After_Review_Return");
		ADD_NDC_EDIT_NEW_PACK_SIZE_AFTER_REVIEW_RETURN = productconfig.get("add_NDC_Edit_New_Pack_Size_After_Review_Return");
		ADD_NDC_EDIT_NEW_NDC_DESCRIPTION_AFTER_REVIEW_RETURN = productconfig.get("add_NDC_Edit_New_NDC_Description_After_Review_Return");
		ADD_NDC_EDIT_NEW_GTN_NUMBER_AFTER_REVIEW_RETURN = productconfig.get("add_NDC_Edit_New_GTN_Number_After_Review_Return");
		ADD_NDC_EDIT_DELETE_INDEX_AFTER_REVIEW_RETURN =productconfig.get("add_NDC_Edit_Delete_Index_After_Review_Return");
		
		ADD_NDC_EDIT_ACTION_AFTER_APPROVE_RETURN = productconfig.get("add_NDC_Edit_Action_After_Approve_Return");
		ADD_NDC_EDIT_EXISTING_INDEX_AFTER_APPROVE_RETURN = productconfig.get("add_NDC_Edit_Existing_Index_After_Approve_Return");
		ADD_NDC_EDIT_EXISTING_NDC_NUMBER_AFTER_APPROVE_RETURN = productconfig.get("add_NDC_Edit_Existing_NDC_Number_After_Approve_Return");
		ADD_NDC_EDIT_EXISTING_SHORT_CODE_AFTER_APPROVE_RETURN = productconfig.get("add_NDC_Edit_Existing_Short_Code_After_Approve_Return");
		ADD_NDC_EDIT_EXISTING_PACK_SIZE_AFTER_APPROVE_RETURN = productconfig.get("add_NDC_Edit_Existing_Pack_Size_After_Approve_Return");
		ADD_NDC_EDIT_EXISTING_NDC_DESCRIPTION_AFTER_APPROVE_RETURN = productconfig.get("add_NDC_Edit_Existing_NDC_Description_After_Approvew_Return");
		ADD_NDC_EDIT_EXISTING_GTN_NUMBER_AFTER_APPROVE_RETURN = productconfig.get("add_NDC_Edit_Existing_GTN_Number_After_Approvew_Return");
		ADD_NDC_EDIT_NEW_NDC_NUMBER_AFTER_APPROVE_RETURN = productconfig.get("add_NDC_Edit_New_NDC_Number_After_Approve_Return");
		ADD_NDC_EDIT_NEW_SHORT_CODE_AFTER_APPROVE_RETURN= productconfig.get("add_NDC_Edit_New_Short_Code_After_Approve_Return");
		ADD_NDC_EDIT_NEW_PACK_SIZE_AFTER_APPROVE_RETURN = productconfig.get("add_NDC_Edit_New_Pack_Size_After_Approve_Return");
		ADD_NDC_EDIT_NEW_NDC_DESCRIPTION_AFTER_APPROVE_RETURN= productconfig.get("add_NDC_Edit_New_NDC_Description_After_Approve_Return");
		ADD_NDC_EDIT_NEW_GTN_NUMBER_AFTER_APPROVE_RETURN= productconfig.get("add_NDC_Edit_New_GTN_Number_After_Approve_Return");
		ADD_NDC_EDIT_DELETE_INDEX_AFTER_APPROVE_RETURN =productconfig.get("add_NDC_Edit_Delete_Index_After_Approve_Return");
	}

}
