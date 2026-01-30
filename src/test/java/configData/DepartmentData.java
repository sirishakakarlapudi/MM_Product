package configData;

import utilities.ConfigLoader;

public class DepartmentData {
	public static String CHROME_URL;
	public static String APP_URL;
	public static String USERNAME;
	public static String PASSWORD;
	public static String PC_DB_NAME;
	public static String MASTER_DB_NAME;
	public static String MM_DB_NAME;
	public static String ACTIONSPERFORMEDBY;
	public static String STATUS;
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
	public static String DEPT_NAME;
	public static String DEPT_DESC;
	public static String DEPARTMENT_VIEW;
	public static String DEPARTMENT_EDIT_AFTER_CREATE;
	public static String EDIT_DEPT_NAME_AFTER_CREATE;
	public static String EDIT_DEPT_DESC_AFTER_CREATE;
	public static String DEPARTMENT_RETURN;
	public static String RETURN_REMARKS;
	public static String EDIT_DEPT_NAME_AFTER_RETRUN;
	public static String EDIT_DEPT_DESC_AFTER_RETURN;
	public static String APPROVE_REMARKS;
	public static String CURRENT_CONFIG_NAME;

	public static void loadProperties(String fileName) {
		// Ensure we handle potential null or empty fileName
		if (fileName == null || fileName.isEmpty()) {
			fileName = "department.properties";
		}

		// Store config name without extension for backup folder fallback
		CURRENT_CONFIG_NAME = fileName.split("\\.")[0];

		ConfigLoader loginconfig = new ConfigLoader("login.properties");
		ConfigLoader deptconfig = new ConfigLoader(fileName);

		CHROME_URL = loginconfig.get("chrome.url");
		APP_URL = loginconfig.get("app.url");
		USERNAME = loginconfig.get("username");
		PASSWORD = loginconfig.get("password");
		PC_DB_NAME = loginconfig.get("pc_DB_Name");
		MASTER_DB_NAME = loginconfig.get("master_DB_Name");
		MM_DB_NAME = loginconfig.get("mm_DB_Name");

		ACTIONSPERFORMEDBY = deptconfig.get("actions_PerformedBy");
		STATUS = deptconfig.get("status");
		USERNAME1 = deptconfig.get("username1");
		PASSWORD1 = deptconfig.get("password1");
		USERNAME2 = deptconfig.get("username2");
		PASSWORD2 = deptconfig.get("password2");
		ACTUALHEADER = deptconfig.get("actualHeader");
		EXPECTEDHEADER = deptconfig.get("expectedHeader");
		TEMPLATE_PATH = deptconfig.get("templatePath");
		OUTPUT_PATH = deptconfig.get("outputPath");
		SCRIPT_NUMBER = deptconfig.get("script_Number");
		TAKE_SCREENSHOTS = deptconfig.get("take_Screenshots");
		MASTER_MODULE = deptconfig.get("master_Module");
		TABLE_HEADERS = deptconfig.get("table_Headers");
		TABLE_SEARCH_VALUES = deptconfig.get("table_Search_Values");
		DEPT_NAME = deptconfig.get("dept_Name");
		DEPT_DESC = deptconfig.get("dept_Desc");
		DEPARTMENT_VIEW = deptconfig.get("dept_View");
		DEPARTMENT_EDIT_AFTER_CREATE = deptconfig.get("dept_Edit_After_Create");
		EDIT_DEPT_NAME_AFTER_CREATE = deptconfig.get("edit_Dept_Name_After_Create");
		EDIT_DEPT_DESC_AFTER_CREATE = deptconfig.get("edit_Dept_Desc_After_Create");
		DEPARTMENT_RETURN = deptconfig.get("dept_Return");
		RETURN_REMARKS = deptconfig.get("return_Remarks");
		EDIT_DEPT_NAME_AFTER_RETRUN = deptconfig.get("edit_Dept_Name_After_Return");
		EDIT_DEPT_DESC_AFTER_RETURN = deptconfig.get("edit_Dept_Desc_After_Return");
		APPROVE_REMARKS = deptconfig.get("approve_Remarks");
	}
}
