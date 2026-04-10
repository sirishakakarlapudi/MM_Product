package configData;

import utilities.ConfigLoader;

public class EquipmentData {

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

    public static String EQUIPMENT_NAME;
    public static String EQUIPMENT_ID;
    public static String EQUIPMENT_TYPE;
    public static String CAPACITY_IN_KG;
    public static String OPERATIONAL_RANGE_MIN;
    public static String OPERATIONAL_RANGE_MAX;
    public static String DEPARTMENT;
    public static String FACILITY;
    public static String WEIGHING_BALANCE_FACILITY;

    public static String EQUIPMENT_VIEW_ACTION;
    public static String EQUIPMENT_RETURN_ACTION_IN_REVIEW;
    public static String EDIT_EQUIPMENT_IN_REVIEW_RETURN;
    public static String REVIEW_RETURN_REMARKS;
    public static String REVIEW_REMARKS;
    public static String EQUIPMENT_RETURN_ACTION_IN_APPROVE;
    public static String EDIT_EQUIPMENT_IN_APPROVE_RETURN;
    public static String APPROVE_RETURN_REMARKS;
    public static String APPROVE_REMARKS;

    public static String CURRENT_CONFIG_NAME;

    public static void loadProperties(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            fileName = "equipment.properties";
        }

        CURRENT_CONFIG_NAME = fileName.split("\\.")[0];

        ConfigLoader loginconfig = new ConfigLoader("login.properties");
        ConfigLoader equipmentconfig = new ConfigLoader(fileName);

        CHROME_URL = loginconfig.get("chrome.url");
        APP_URL = loginconfig.get("app.url");
        USERNAME = loginconfig.get("username");
        PASSWORD = loginconfig.get("password");
        PC_DB_NAME = loginconfig.get("pc_DB_Name");
        MASTER_DB_NAME = loginconfig.get("master_DB_Name");
        MM_DB_NAME = loginconfig.get("mm_DB_Name");
        
        ACTIONSPERFORMEDBY = equipmentconfig.get("actions_PerformedBy");
        USERNAME1 = equipmentconfig.get("username1");
        PASSWORD1 = equipmentconfig.get("password1");
        USERNAME2 = equipmentconfig.get("username2");
        PASSWORD2 = equipmentconfig.get("password2");
        USERNAME3 = equipmentconfig.get("username3");
        PASSWORD3 = equipmentconfig.get("password3");

        ACTUALHEADER = equipmentconfig.get("actualHeader");
        EXPECTEDHEADER = equipmentconfig.get("expectedHeader");
        ACTUALDOCUEMNTNO = equipmentconfig.get("actualDocumentNo");
        EXPECTEDDOCUEMNTNO = equipmentconfig.get("expectedDocumentNo");
        TEMPLATE_PATH = equipmentconfig.get("templatePath");
        OUTPUT_PATH = equipmentconfig.get("outputPath");
        SCRIPT_NUMBER = equipmentconfig.get("script_Number");
        TAKE_SCREENSHOTS = equipmentconfig.get("take_Screenshots");
        
        TABLE_HEADERS = equipmentconfig.get("table_Headers");
        TABLE_SEARCH_VALUES = equipmentconfig.get("table_Search_Values");

        MASTER_MODULE = equipmentconfig.get("master_Module");
     
        EQUIPMENT_NAME = equipmentconfig.get("equipmentName");
        EQUIPMENT_ID = equipmentconfig.get("equipmentID");
        EQUIPMENT_TYPE = equipmentconfig.get("equipmentType");
        CAPACITY_IN_KG = equipmentconfig.get("capacityInKg");
        OPERATIONAL_RANGE_MIN = equipmentconfig.get("operationalRangeMin");
        OPERATIONAL_RANGE_MAX = equipmentconfig.get("operationalRangeMax");
        DEPARTMENT = equipmentconfig.get("department");
        FACILITY = equipmentconfig.get("facility");
        WEIGHING_BALANCE_FACILITY = equipmentconfig.get("weighingBalanceFacility");

        EQUIPMENT_VIEW_ACTION = equipmentconfig.get("equipment_View");
        EQUIPMENT_RETURN_ACTION_IN_REVIEW = equipmentconfig.get("equipment_Return_In_Review");
        EDIT_EQUIPMENT_IN_REVIEW_RETURN = equipmentconfig.get("editequipment_Review_Return");
        REVIEW_RETURN_REMARKS = equipmentconfig.get("review_Return_Remarks");
        REVIEW_REMARKS = equipmentconfig.get("reviewRemarks");
        EQUIPMENT_RETURN_ACTION_IN_APPROVE = equipmentconfig.get("equipment_Return_In_Approve");
        EDIT_EQUIPMENT_IN_APPROVE_RETURN = equipmentconfig.get("editequipment_Approve_Return");
        APPROVE_RETURN_REMARKS = equipmentconfig.get("approve_Return_Remarks");
        APPROVE_REMARKS = equipmentconfig.get("approveRemarks");
    }
}
