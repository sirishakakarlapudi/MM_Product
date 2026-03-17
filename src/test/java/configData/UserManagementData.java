package configData;

import utilities.ConfigLoader;

public class UserManagementData {

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
	public static String CURRENT_CONFIG_NAME;
	public static String EMPLOYEE_ID;
	public static String EMPLOYEE_NAME;
	public static String EMAIL;
	public static String MOBILE_NUMBER;
	public static String USER_NAME;
	public static String TEMPORARY_PASSWORD;
	public static String DEPARTMENT;
	public static String DESIGNATION;
	public static String MODULE;
	public static String SECURITY_GROUP;
	public static String USER_VIEW;
	public static String USER_EDIT;
	public static String EDIT_EMPLOYEE_NAME;
	public static String EDIT_EMAIL;
	public static String EDIT_MOBILE_NUMBER;
	public static String EDIT_DESIGNATION;
	public static String USER_EDIT_SECURITY_GROUP;
	public static String EDIT_MODULE;
	public static String EDIT_SECURITY_GROUP;
	public static String DATE_OF_JOINING;
	public static String JD_APPROVED_ON;
	public static String TRAINING_COMPLETED_ON;
	public static String JOB_CODE;
	public static String MODE_OF_TRAINING;
	public static String REQUIRE_QUESTIONNAIRE;

	public static void loadProperties(String fileName) {
		// Ensure we handle potential null or empty fileName
		if (fileName == null || fileName.isEmpty()) {
			fileName = "usermanagement.properties";
		}

		CURRENT_CONFIG_NAME = fileName.split("\\.")[0];

		ConfigLoader loginconfig = new ConfigLoader("login.properties");
		ConfigLoader usermanagementconfig = new ConfigLoader(fileName);

		CHROME_URL = loginconfig.get("chrome.url");
		APP_URL = loginconfig.get("app.url");
		USERNAME = loginconfig.get("username");
		PASSWORD = loginconfig.get("password");
		PC_DB_NAME = loginconfig.get("pc_DB_Name");
		MASTER_DB_NAME = loginconfig.get("master_DB_Name");
		MM_DB_NAME = loginconfig.get("mm_DB_Name");

		ACTIONSPERFORMEDBY = usermanagementconfig.get("actions_PerformedBy");
		USERNAME1 = usermanagementconfig.get("username1");
		PASSWORD1 = usermanagementconfig.get("password1");

		ACTUALHEADER = usermanagementconfig.get("actualHeader");
		EXPECTEDHEADER = usermanagementconfig.get("expectedHeader");
		ACTUALDOCUEMNTNO = usermanagementconfig.get("actualDocumentNo");
		EXPECTEDDOCUEMNTNO = usermanagementconfig.get("expectedDocumentNo");
		TEMPLATE_PATH = usermanagementconfig.get("templatePath");
		OUTPUT_PATH = usermanagementconfig.get("outputPath");
		SCRIPT_NUMBER = usermanagementconfig.get("script_Number");
		TAKE_SCREENSHOTS = usermanagementconfig.get("take_Screenshots");
		MASTER_MODULE = usermanagementconfig.get("master_Module");
		TABLE_HEADERS = usermanagementconfig.get("table_Headers");
		TABLE_SEARCH_VALUES = usermanagementconfig.get("table_Search_Values");
		SUB_MASTER_MODULE = usermanagementconfig.get("submasterModule");
		EMPLOYEE_ID = usermanagementconfig.get("employeeid");
		EMPLOYEE_NAME = usermanagementconfig.get("employeename");
		EMAIL = usermanagementconfig.get("email");
		MOBILE_NUMBER = usermanagementconfig.get("mobilenumber");
		USER_NAME = usermanagementconfig.get("empusername");
		TEMPORARY_PASSWORD = usermanagementconfig.get("temporarypassword");
		DEPARTMENT = usermanagementconfig.get("department");
		DESIGNATION = usermanagementconfig.get("designation");
		MODULE = usermanagementconfig.get("module");
		SECURITY_GROUP = usermanagementconfig.get("sgname");
		USER_VIEW = usermanagementconfig.get("user_View");
		USER_EDIT = usermanagementconfig.get("user_Edit");
		EDIT_EMPLOYEE_NAME = usermanagementconfig.get("edit_Employee_Name");
		EDIT_EMAIL = usermanagementconfig.get("edit_Email");
		EDIT_MOBILE_NUMBER = usermanagementconfig.get("edit_mobilenumber");
		EDIT_DESIGNATION = usermanagementconfig.get("edit_Designation");
		USER_EDIT_SECURITY_GROUP = usermanagementconfig.get("user_Edit_Security_Group");
		EDIT_MODULE = usermanagementconfig.get("edit_Module");
		EDIT_SECURITY_GROUP = usermanagementconfig.get("edit_Security_Group");

		DATE_OF_JOINING = usermanagementconfig.get("dateofjoining");
		JD_APPROVED_ON = usermanagementconfig.get("jdapprovedon");
		TRAINING_COMPLETED_ON = usermanagementconfig.get("trainingcompletedon");
		JOB_CODE = usermanagementconfig.get("jobcode");
		MODE_OF_TRAINING = usermanagementconfig.get("modeoftraining");
		REQUIRE_QUESTIONNAIRE = usermanagementconfig.get("requirequestionnaire");

	}
}
