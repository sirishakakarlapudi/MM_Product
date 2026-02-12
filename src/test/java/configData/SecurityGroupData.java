package configData;

import utilities.ConfigLoader;

public class SecurityGroupData {

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
	public static String ACTUALHEADER;
	public static String EXPECTEDHEADER;
	public static String TEMPLATE_PATH;
	public static String OUTPUT_PATH;
	public static String SCRIPT_NUMBER;
	public static String TAKE_SCREENSHOTS;
	public static String MASTER_MODULE;
	public static String TABLE_HEADERS;
	public static String TABLE_SEARCH_VALUES;
	public static String SECURITYGROUP_NAME;
	public static String SECURITYGROUP_DESC;
	public static String SELECT_MODULE;
	public static String USER_PRIVILEGES;
	public static String SECURITYGROUP_VIEW_ACTION;
	public static String SECURITYGROUP_EDIT_AFTER_CREATE_ACTION;
	public static String EDIT_SECURITYGROUP_NAME_AFTER_CREATE;
	public static String EDIT_USER_PRIVILEGES_AFTER_CREATE;
	public static String EDIT_MODULE_AFTER_CREATE;
	public static String EDIT_DESC_AFTER_CREATE;
	public static String SECURITYGROUP_RETURN_ACTION;
	public static String RETURN_REMARKS;
	public static String EDIT_SECURITYGROUP_NAME_AFTER_RETRUN;
	public static String EDIT_USER_PRIVILEGES_AFTER_RETURN;
	public static String EDIT_MODULE_AFTER_RETURN;
	public static String EDIT_DESC_AFTER_RETURN;
	public static String APPROVE_REMARKS;
	public static String UPDATE_USER_PRIVILEGES_AFTER_ACTIVE;
	public static String UPDATE_PRIVILEGES_ACTION;
	public static String SECURITYGROUP_ACTIVE_RETURN_ACTION;
	public static String ACTIVE_RETURN_REMARKS;
	public static String UPDATE_USER_PRIVILEGES_AFTER_ACTIVE_RETURN;
	public static String ACTIVE_APPROVE_REMARKS;
	public static String CURRENT_CONFIG_NAME;
	public static String SIDE_NAV_MODULE_MAPPING;

	public static void loadProperties(String fileName) {
		// Ensure we handle potential null or empty fileName
		if (fileName == null || fileName.isEmpty()) {
			fileName = "department.properties";
		}

		// Store config name without extension for backup folder fallback
		CURRENT_CONFIG_NAME = fileName.split("\\.")[0];

		ConfigLoader loginconfig = new ConfigLoader("login.properties");
		ConfigLoader securitygroupconfig = new ConfigLoader(fileName);

		CHROME_URL = loginconfig.get("chrome.url");
		APP_URL = loginconfig.get("app.url");
		USERNAME = loginconfig.get("username");
		PASSWORD = loginconfig.get("password");
		PC_DB_NAME = loginconfig.get("pc_DB_Name");
		MASTER_DB_NAME = loginconfig.get("master_DB_Name");
		MM_DB_NAME = loginconfig.get("mm_DB_Name");
	
		SIDE_NAV_MODULE_MAPPING = loginconfig.get("side_nav_module_mapping");
		
		ACTIONSPERFORMEDBY = securitygroupconfig.get("actions_PerformedBy");
		USERNAME1 = securitygroupconfig.get("username1");
		PASSWORD1 = securitygroupconfig.get("password1");
		USERNAME2 = securitygroupconfig.get("username2");
		PASSWORD2 = securitygroupconfig.get("password2");
		ACTUALHEADER = securitygroupconfig.get("actualHeader");
		EXPECTEDHEADER = securitygroupconfig.get("expectedHeader");
		TEMPLATE_PATH = securitygroupconfig.get("templatePath");
		OUTPUT_PATH = securitygroupconfig.get("outputPath");
		SCRIPT_NUMBER = securitygroupconfig.get("script_Number");
		TAKE_SCREENSHOTS = securitygroupconfig.get("take_Screenshots");
		MASTER_MODULE = securitygroupconfig.get("master_Module");
		TABLE_HEADERS = securitygroupconfig.get("table_Headers");
		TABLE_SEARCH_VALUES = securitygroupconfig.get("table_Search_Values");
		SECURITYGROUP_NAME = securitygroupconfig.get("securitygroup_Name");
		SECURITYGROUP_DESC = securitygroupconfig.get("securitygroup_Desc");
		SELECT_MODULE =securitygroupconfig.get("select_Module");
		USER_PRIVILEGES= securitygroupconfig.get("user_Privileges");
		SECURITYGROUP_VIEW_ACTION = securitygroupconfig.get("securitygroup_View_Action");
		SECURITYGROUP_EDIT_AFTER_CREATE_ACTION = securitygroupconfig.get("securitygroup_Edit_After_Create_Action");
		EDIT_SECURITYGROUP_NAME_AFTER_CREATE = securitygroupconfig.get("edit_Securitygroup_Name_After_Create");
		EDIT_USER_PRIVILEGES_AFTER_CREATE = securitygroupconfig.get("edit_UserPrivileges_After_Create");
		EDIT_MODULE_AFTER_CREATE= securitygroupconfig.get("edit_Module_After_Create");
		EDIT_DESC_AFTER_CREATE= securitygroupconfig.get("edit_Securitygroup_Desc_After_Create");
		SECURITYGROUP_RETURN_ACTION = securitygroupconfig.get("securitygroup_Return_Action");
		RETURN_REMARKS = securitygroupconfig.get("return_Remarks");
		EDIT_SECURITYGROUP_NAME_AFTER_RETRUN = securitygroupconfig.get("edit_Securitygroup_Name_After_Return");
		EDIT_USER_PRIVILEGES_AFTER_RETURN = securitygroupconfig.get("edit_Securitygroup_UserPrivileges_After_Return");
		EDIT_MODULE_AFTER_RETURN = securitygroupconfig.get("edit_Module_After_Return");
		EDIT_DESC_AFTER_RETURN = securitygroupconfig.get("edit_Securitygroup_Desc_After_Return");
		APPROVE_REMARKS = securitygroupconfig.get("approve_Remarks");
		UPDATE_PRIVILEGES_ACTION = securitygroupconfig.get("update_Privileges_Action");
		UPDATE_USER_PRIVILEGES_AFTER_ACTIVE = securitygroupconfig.get("update_UserPrivileges_After_Active");
		SECURITYGROUP_ACTIVE_RETURN_ACTION = securitygroupconfig.get("securitygroup_Active_Return_Action");
		ACTIVE_RETURN_REMARKS = securitygroupconfig.get("active_Return_Remarks");
		UPDATE_USER_PRIVILEGES_AFTER_ACTIVE_RETURN = securitygroupconfig.get("update_UserPrivileges_After_Active_Return");
		ACTIVE_APPROVE_REMARKS = securitygroupconfig.get("active_Approve_Remarks");
		
		
	}

}
