package configData;

import utilities.ConfigLoader;

public class MaterialData {

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

    public static String MATERIAL_NAME;
    public static String MATERIAL_CODE;
    public static String MATERIAL_CATEGORY;
    public static String TYPE_OF_MATERIAL;
    public static String UOM;
    public static String STORAGE_CONDITION;
    public static String STORAGE_LOCATION;
    public static String SAMPLING_ACTIVITY;
    public static String DISPENSING_ACTIVITY;
    public static String MIXED_ANALYSIS;
    public static String WEIGHT_VERIFICATION;
    public static String RECEIVING_BAY;
    public static String CLEANING_AGENT;
    public static String SUPPLIER;
    public static String MANUFACTURER;

    public static String EDIT_MATERIAL_IN_REVIEW_RETURN;

    public static String MATERIAL_VIEW_ACTION;
    public static String MATERIAL_RETURN_ACTION_IN_REVIEW;
    public static String MATERIAL_RETURN_ACTION_IN_APPROVE;
    public static String EDIT_MATERIAL_IN_APPROVE_RETURN;
    public static String REVIEW_RETURN_REMARKS;
    public static String APPROVE_RETURN_REMARKS;
    public static String REVIEW_REMARKS;
    public static String APPROVE_REMARKS;

    public static String CURRENT_CONFIG_NAME;

    public static void loadProperties(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            fileName = "material.properties";
        }

        CURRENT_CONFIG_NAME = fileName.split("\\.")[0];

        ConfigLoader loginconfig = new ConfigLoader("login.properties");
        ConfigLoader materialconfig = new ConfigLoader(fileName);

        CHROME_URL = loginconfig.get("chrome.url");
        APP_URL = loginconfig.get("app.url");
        USERNAME = loginconfig.get("username");
        PASSWORD = loginconfig.get("password");
        PC_DB_NAME = loginconfig.get("pc_DB_Name");
        MASTER_DB_NAME = loginconfig.get("master_DB_Name");
        MM_DB_NAME = loginconfig.get("mm_DB_Name");
        ACTIONSPERFORMEDBY = materialconfig.get("actions_PerformedBy");

        ACTUALHEADER = materialconfig.get("actualHeader");
        EXPECTEDHEADER = materialconfig.get("expectedHeader");
        ACTUALDOCUEMNTNO = materialconfig.get("actualDocumentNo");
        EXPECTEDDOCUEMNTNO = materialconfig.get("expectedDocumentNo");
        TEMPLATE_PATH = materialconfig.get("templatePath");
        OUTPUT_PATH = materialconfig.get("outputPath");
        SCRIPT_NUMBER = materialconfig.get("script_Number");
        TAKE_SCREENSHOTS = materialconfig.get("take_Screenshots");
        TABLE_HEADERS = materialconfig.get("table_Headers");
        TABLE_SEARCH_VALUES = materialconfig.get("table_Search_Values");
        USERNAME1 = materialconfig.get("username1");
        PASSWORD1 = materialconfig.get("password1");
        USERNAME2 = materialconfig.get("username2");
        PASSWORD2 = materialconfig.get("password2");
        USERNAME3 = materialconfig.get("username3");
        PASSWORD3 = materialconfig.get("password3");

        MASTER_MODULE = materialconfig.get("master_Module");
        SUB_MASTER_MODULE = materialconfig.get("sub_Master_Module");

        MATERIAL_NAME = materialconfig.get("materialName");
        MATERIAL_CODE = materialconfig.get("materialCode");
        MATERIAL_CATEGORY = materialconfig.get("materialCategory");
        TYPE_OF_MATERIAL = materialconfig.get("typeOfMaterial");
        UOM = materialconfig.get("uom");
        STORAGE_CONDITION = materialconfig.get("storageCondition");
        STORAGE_LOCATION = materialconfig.get("storageLocation");
        SAMPLING_ACTIVITY = materialconfig.get("samplingActivity");
        DISPENSING_ACTIVITY = materialconfig.get("dispensingActivity");
        MIXED_ANALYSIS = materialconfig.get("mixedAnalysis");
        WEIGHT_VERIFICATION = materialconfig.get("weightVerification");
        RECEIVING_BAY = materialconfig.get("receivingBay");
        CLEANING_AGENT = materialconfig.get("cleaningAgent");
        SUPPLIER = materialconfig.get("supplier");
        MANUFACTURER = materialconfig.get("manufacturer");

        MATERIAL_VIEW_ACTION = materialconfig.get("material_View");
        MATERIAL_RETURN_ACTION_IN_REVIEW = materialconfig.get("material_Return_In_Review");
        EDIT_MATERIAL_IN_REVIEW_RETURN = materialconfig.get("editmaterial_Review_Return");
        REVIEW_RETURN_REMARKS = materialconfig.get("review_Return_Remarks");
        REVIEW_REMARKS = materialconfig.get("reviewRemarks");
        MATERIAL_RETURN_ACTION_IN_APPROVE = materialconfig.get("material_Return_In_Approve");
        EDIT_MATERIAL_IN_APPROVE_RETURN = materialconfig.get("editmaterial_Approve_Return");
        APPROVE_RETURN_REMARKS = materialconfig.get("approve_Return_Remarks");
        APPROVE_REMARKS = materialconfig.get("approveRemarks");

    }
}
