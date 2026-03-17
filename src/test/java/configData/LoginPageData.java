package configData;

import utilities.ConfigLoader;

public class LoginPageData {

	public static String CHROME_URL;
	public static String APP_URL;
	public static String CSV_PATH;
	public static String CSV_MODULE_NAME;
	public static String USERNAME;
	public static String PASSWORD;
	public static String PC_DB_NAME;
	public static String MASTER_DB_NAME;
	public static String MM_DB_NAME;
	public static String ACTIONSPERFORMEDBY;
	public static String USERNAME1;
	public static String PASSWORD1;
	public static String MASTER_MODULE;

	public static String ACTUALHEADER;
	public static String EXPECTEDHEADER;
	public static String ACTUALDOCUEMNTNO;
	public static String EXPECTEDDOCUEMNTNO;
	public static String TEMPLATE_PATH;
	public static String OUTPUT_PATH;
	public static String SCRIPT_NUMBER;
	public static String TAKE_SCREENSHOTS;

	public static String LOGIN_EXPECTED_FIELDS;
	public static String LOGIN_EXPECTED_BUTTONS;
	public static String LOGIN_EXPECTED_LINKS;
	public static String LOGIN_EXPECTED_IMAGES;
	public static String CURRENT_CONFIG_NAME;
	public static String ALL_APPS_MODULE_MAPPING;
	public static String SIDE_NAV_MODULE_MAPPING;

	public static void loadProperties(String fileName) {
		// Ensure we handle potential null or empty fileName
		if (fileName == null || fileName.isEmpty()) {
			fileName = "login.properties";
		}

		// Store config name without extension for backup folder fallback
		CURRENT_CONFIG_NAME = fileName.split("\\.")[0];

		ConfigLoader loginconfig = new ConfigLoader(fileName);

		CHROME_URL = loginconfig.get("chrome.url");
		APP_URL = loginconfig.get("app.url");
		CSV_PATH = loginconfig.get("csv_Path");
		CSV_MODULE_NAME = loginconfig.get("csv_Module_Name");
		USERNAME = loginconfig.get("username");
		PASSWORD = loginconfig.get("password");
		PC_DB_NAME = loginconfig.get("pc_DB_Name");
		MASTER_DB_NAME = loginconfig.get("master_DB_Name");
		MM_DB_NAME = loginconfig.get("mm_DB_Name");
		ACTIONSPERFORMEDBY = loginconfig.get("actions_PerformedBy");
		ALL_APPS_MODULE_MAPPING = loginconfig.get("all_apps_module_mapping");
		SIDE_NAV_MODULE_MAPPING = loginconfig.get("side_nav_module_mapping");
		USERNAME1 = loginconfig.get("username1");
		PASSWORD1 = loginconfig.get("password1");
		MASTER_MODULE = loginconfig.get("master_Module");

		ACTUALHEADER = loginconfig.get("actualHeader");
		EXPECTEDHEADER = loginconfig.get("expectedHeader");
		ACTUALDOCUEMNTNO = loginconfig.get("actualDocumentNo");
		EXPECTEDDOCUEMNTNO = loginconfig.get("expectedDocumentNo");
		TEMPLATE_PATH = loginconfig.get("templatePath");
		OUTPUT_PATH = loginconfig.get("outputPath");
		SCRIPT_NUMBER = loginconfig.get("script_Number");
		TAKE_SCREENSHOTS = loginconfig.get("take_Screenshots");

		LOGIN_EXPECTED_FIELDS = loginconfig.get("login_Expected_Fields");
		LOGIN_EXPECTED_BUTTONS = loginconfig.get("login_Expected_Buttons");
		LOGIN_EXPECTED_LINKS = loginconfig.get("login_Expected_Links");
		LOGIN_EXPECTED_IMAGES = loginconfig.get("login_Expected_Images");

	}

}
