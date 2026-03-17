package configData;

import utilities.ConfigLoader;

public class FacilityData {

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
	public static String ACTUALHEADER;
	public static String EXPECTEDHEADER;
	public static String ACTUALDOCUEMNTNO;
	public static String EXPECTEDDOCUEMNTNO;
	public static String TEMPLATE_PATH;
	public static String OUTPUT_PATH;
	public static String SCRIPT_NUMBER;
	public static String TAKE_SCREENSHOTS;
	public static String MASTER_MODULE;
	public static String TABLE_HEADERS;
	public static String TABLE_SEARCH_VALUES;
	public static String FACILITY_NAME;
	public static String FACILITY_TYPE;
	public static String DEPARTMENT;
	public static String PARENT_FACILITY;
	public static String EDIT_FACILITY_NAME;
	public static String STORAGE_CONDITION;
	public static String EDIT_FACILITY_TYPE;
	public static String EDIT_DEPARTMENT;
	public static String EDIT_PARENT_FACILITY;
	public static String EDIT_STORAGE_CONDITION;
	public static String FACILITY_VIEW_ACTION;
	public static String FACILITY_RETURN_ACTION;
	public static String RETURN_REMARKS;
	public static String APPROVE_REMARKS;

	public static void loadProperties(String fileName) {
		// Ensure we handle potential null or empty fileName
		if (fileName == null || fileName.isEmpty()) {
			fileName = "facility.properties";
		}

		// Store config name without extension for backup folder fallback
		CURRENT_CONFIG_NAME = fileName.split("\\.")[0];

		ConfigLoader loginconfig = new ConfigLoader("login.properties");
		ConfigLoader facilityconfig = new ConfigLoader(fileName);

		CHROME_URL = loginconfig.get("chrome.url");
		APP_URL = loginconfig.get("app.url");
		USERNAME = loginconfig.get("username");
		PASSWORD = loginconfig.get("password");
		PC_DB_NAME = loginconfig.get("pc_DB_Name");
		MASTER_DB_NAME = loginconfig.get("master_DB_Name");
		MM_DB_NAME = loginconfig.get("mm_DB_Name");
		ACTIONSPERFORMEDBY = facilityconfig.get("actions_PerformedBy");
		USERNAME1 = facilityconfig.get("username1");
		PASSWORD1 = facilityconfig.get("password1");
		USERNAME2 = facilityconfig.get("username2");
		PASSWORD2 = facilityconfig.get("password2");
		ACTUALHEADER = facilityconfig.get("actualHeader");
		EXPECTEDHEADER = facilityconfig.get("expectedHeader");
		ACTUALDOCUEMNTNO = facilityconfig.get("actualDocumentNo");
		EXPECTEDDOCUEMNTNO = facilityconfig.get("expectedDocumentNo");
		TEMPLATE_PATH = facilityconfig.get("templatePath");
		OUTPUT_PATH = facilityconfig.get("outputPath");
		SCRIPT_NUMBER = facilityconfig.get("script_Number");
		TAKE_SCREENSHOTS = facilityconfig.get("take_Screenshots");
		MASTER_MODULE = facilityconfig.get("master_Module");
		TABLE_HEADERS = facilityconfig.get("table_Headers");
		TABLE_SEARCH_VALUES = facilityconfig.get("table_Search_Values");
		FACILITY_NAME = facilityconfig.get("facilityName");
		FACILITY_TYPE = facilityconfig.get("facilityType");
		DEPARTMENT = facilityconfig.get("department");
		PARENT_FACILITY = facilityconfig.get("parentFacility");
		STORAGE_CONDITION = facilityconfig.get("storageCondition");
		EDIT_FACILITY_NAME = facilityconfig.get("editFacilityName");
		EDIT_FACILITY_TYPE = facilityconfig.get("editFacilityType");
		EDIT_DEPARTMENT = facilityconfig.get("editDepartment");
		EDIT_PARENT_FACILITY = facilityconfig.get("editParentFacility");
		EDIT_STORAGE_CONDITION = facilityconfig.get("editStorageCondition");
		FACILITY_VIEW_ACTION = facilityconfig.get("facility_View");
		FACILITY_RETURN_ACTION = facilityconfig.get("facility_Return");
		RETURN_REMARKS =facilityconfig.get("returnRemarks");
		APPROVE_REMARKS =facilityconfig.get("approveRemarks");

	}

}
