package configData;

import utilities.ConfigLoader;

public class ManufacturerData {

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

    public static String MANUFACTURER_NAME;
    public static String MANUFACTURER_ADDRESS;
    public static String MANUFACTURER_STATE;
    public static String MANUFACTURER_CITY;
    public static String MANUFACTURER_PINCODE;
    public static String MANUFACTURER_REGION;

    public static String EDIT_MANUFACTURER_IN_REVIEW_RETURN;

    public static String MANUFACTURER_VIEW_ACTION;
    public static String MANUFACTURER_RETURN_ACTION_IN_REVIEW;
    public static String MANUFACTURER_RETURN_ACTION_IN_APPROVE;
    public static String EDIT_MANUFACTURER_IN_APPROVE_RETURN;
    public static String REVIEW_RETURN_REMARKS;
    public static String APPROVE_RETURN_REMARKS;
    public static String REVIEW_REMARKS;
    public static String APPROVE_REMARKS;

    public static String CURRENT_CONFIG_NAME;

    public static void loadProperties(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            fileName = "manufacturer.properties";
        }

        CURRENT_CONFIG_NAME = fileName.split("\\.")[0];

        ConfigLoader loginconfig = new ConfigLoader("login.properties");
        ConfigLoader manufacturerconfig = new ConfigLoader(fileName);

        CHROME_URL = loginconfig.get("chrome.url");
        APP_URL = loginconfig.get("app.url");
        USERNAME = loginconfig.get("username");
        PASSWORD = loginconfig.get("password");
        PC_DB_NAME = loginconfig.get("pc_DB_Name");
        MASTER_DB_NAME = loginconfig.get("master_DB_Name");
        MM_DB_NAME = loginconfig.get("mm_DB_Name");
        ACTIONSPERFORMEDBY = manufacturerconfig.get("actions_PerformedBy");

        ACTUALHEADER = manufacturerconfig.get("actualHeader");
        EXPECTEDHEADER = manufacturerconfig.get("expectedHeader");
        ACTUALDOCUEMNTNO = manufacturerconfig.get("actualDocumentNo");
        EXPECTEDDOCUEMNTNO = manufacturerconfig.get("expectedDocumentNo");
        TEMPLATE_PATH = manufacturerconfig.get("templatePath");
        OUTPUT_PATH = manufacturerconfig.get("outputPath");
        SCRIPT_NUMBER = manufacturerconfig.get("script_Number");
        TAKE_SCREENSHOTS = manufacturerconfig.get("take_Screenshots");
        TABLE_HEADERS = manufacturerconfig.get("table_Headers");
        TABLE_SEARCH_VALUES = manufacturerconfig.get("table_Search_Values");
        USERNAME1 = manufacturerconfig.get("username1");
        PASSWORD1 = manufacturerconfig.get("password1");
        USERNAME2 = manufacturerconfig.get("username2");
        PASSWORD2 = manufacturerconfig.get("password2");
        USERNAME3 = manufacturerconfig.get("username3");
        PASSWORD3 = manufacturerconfig.get("password3");

        MASTER_MODULE = manufacturerconfig.get("master_Module");
        SUB_MASTER_MODULE = manufacturerconfig.get("sub_Master_Module");

        MANUFACTURER_NAME = manufacturerconfig.get("manufacturerName");
        MANUFACTURER_ADDRESS = manufacturerconfig.get("address");
        MANUFACTURER_STATE = manufacturerconfig.get("state");
        MANUFACTURER_CITY = manufacturerconfig.get("city");
        MANUFACTURER_PINCODE = manufacturerconfig.get("pincode");
        MANUFACTURER_REGION = manufacturerconfig.get("region");

        MANUFACTURER_VIEW_ACTION = manufacturerconfig.get("manufacturer_View");
        MANUFACTURER_RETURN_ACTION_IN_REVIEW = manufacturerconfig.get("manufacturer_Return_In_Review");
        EDIT_MANUFACTURER_IN_REVIEW_RETURN = manufacturerconfig.get("editmanufacturer_Review_Return");
        REVIEW_RETURN_REMARKS = manufacturerconfig.get("review_Return_Remarks");
        REVIEW_REMARKS = manufacturerconfig.get("reviewRemarks");
        MANUFACTURER_RETURN_ACTION_IN_APPROVE = manufacturerconfig.get("manufacturer_Return_In_Approve");
        EDIT_MANUFACTURER_IN_APPROVE_RETURN = manufacturerconfig.get("editmanufacturer_Approve_Return");
        APPROVE_RETURN_REMARKS = manufacturerconfig.get("approve_Return_Remarks");
        APPROVE_REMARKS = manufacturerconfig.get("approveRemarks");

    }
}
