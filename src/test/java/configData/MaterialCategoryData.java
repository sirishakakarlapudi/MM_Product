package configData;

import utilities.ConfigLoader;

public class MaterialCategoryData {

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

    public static String MATERIAL_CATEGORY_NAME;
    public static String SAMPLING_PLAN;
    public static String WEIGHT_VERIFICATION_PLAN;

    public static String EDIT_MATERIAL_CATEGORY_IN_REVIEW_RETURN;

    public static String MATERIAL_CATEGORY_VIEW_ACTION;
    public static String MATERIAL_CATEGORY_RETURN_ACTION_IN_REVIEW;
    public static String MATERIAL_CATEGORY_RETURN_ACTION_IN_APPROVE;
    public static String EDIT_MATERIAL_CATEGORY_IN_APPROVE_RETURN;
    public static String REVIEW_RETURN_REMARKS;
    public static String APPROVE_RETURN_REMARKS;
    public static String REVIEW_REMARKS;
    public static String APPROVE_REMARKS;

    public static String CURRENT_CONFIG_NAME;

    public static void loadProperties(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            fileName = "materialcategory.properties";
        }

        CURRENT_CONFIG_NAME = fileName.split("\\.")[0];

        ConfigLoader loginconfig = new ConfigLoader("login.properties");
        ConfigLoader materialcategoryconfig = new ConfigLoader(fileName);

        CHROME_URL = loginconfig.get("chrome.url");
        APP_URL = loginconfig.get("app.url");
        USERNAME = loginconfig.get("username");
        PASSWORD = loginconfig.get("password");
        PC_DB_NAME = loginconfig.get("pc_DB_Name");
        MASTER_DB_NAME = loginconfig.get("master_DB_Name");
        MM_DB_NAME = loginconfig.get("mm_DB_Name");
        ACTIONSPERFORMEDBY = materialcategoryconfig.get("actions_PerformedBy");

        ACTUALHEADER = materialcategoryconfig.get("actualHeader");
        EXPECTEDHEADER = materialcategoryconfig.get("expectedHeader");
        ACTUALDOCUEMNTNO = materialcategoryconfig.get("actualDocumentNo");
        EXPECTEDDOCUEMNTNO = materialcategoryconfig.get("expectedDocumentNo");
        TEMPLATE_PATH = materialcategoryconfig.get("templatePath");
        OUTPUT_PATH = materialcategoryconfig.get("outputPath");
        SCRIPT_NUMBER = materialcategoryconfig.get("script_Number");
        TAKE_SCREENSHOTS = materialcategoryconfig.get("take_Screenshots");
        TABLE_HEADERS = materialcategoryconfig.get("table_Headers");
        TABLE_SEARCH_VALUES = materialcategoryconfig.get("table_Search_Values");
        USERNAME1 = materialcategoryconfig.get("username1");
        PASSWORD1 = materialcategoryconfig.get("password1");
        USERNAME2 = materialcategoryconfig.get("username2");
        PASSWORD2 = materialcategoryconfig.get("password2");
        USERNAME3 = materialcategoryconfig.get("username3");
        PASSWORD3 = materialcategoryconfig.get("password3");

        MASTER_MODULE = materialcategoryconfig.get("master_Module");
        SUB_MASTER_MODULE = materialcategoryconfig.get("sub_Master_Module");

        MATERIAL_CATEGORY_NAME = materialcategoryconfig.get("materialCategoryName");
        SAMPLING_PLAN = materialcategoryconfig.get("samplingPlan");
        WEIGHT_VERIFICATION_PLAN = materialcategoryconfig.get("weightVerificationPlan");

        MATERIAL_CATEGORY_VIEW_ACTION = materialcategoryconfig.get("materialCategory_View");
        MATERIAL_CATEGORY_RETURN_ACTION_IN_REVIEW = materialcategoryconfig.get("materialCategory_Return_In_Review");
        EDIT_MATERIAL_CATEGORY_IN_REVIEW_RETURN = materialcategoryconfig.get("editmaterialCategory_Review_Return");
        REVIEW_RETURN_REMARKS = materialcategoryconfig.get("review_Return_Remarks");
        REVIEW_REMARKS = materialcategoryconfig.get("reviewRemarks");
        MATERIAL_CATEGORY_RETURN_ACTION_IN_APPROVE = materialcategoryconfig.get("materialCategory_Return_In_Approve");
        EDIT_MATERIAL_CATEGORY_IN_APPROVE_RETURN = materialcategoryconfig.get("editmaterialCategory_Approve_Return");
        APPROVE_RETURN_REMARKS = materialcategoryconfig.get("approve_Return_Remarks");
        APPROVE_REMARKS = materialcategoryconfig.get("approveRemarks");

    }
}
