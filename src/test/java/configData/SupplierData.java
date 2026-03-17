package configData;

import utilities.ConfigLoader;

public class SupplierData {

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

    public static String SUPPLIER_NAME;
    public static String SUPPLIER_ADDRESS;
    public static String SUPPLIER_STATE;
    public static String SUPPLIER_CITY;
    public static String SUPPLIER_PINCODE;
    public static String SUPPLIER_REGION;

    public static String EDIT_SUPPLIER_IN_REVIEW_RETURN;

    public static String SUPPLIER_VIEW_ACTION;
    public static String SUPPLIER_RETURN_ACTION_IN_REVIEW;
    public static String SUPPLIER_RETURN_ACTION_IN_APPROVE;
    public static String EDIT_SUPPLIER_IN_APPROVE_RETURN;
    public static String REVIEW_RETURN_REMARKS;
    public static String APPROVE_RETURN_REMARKS;
    public static String REVIEW_REMARKS;
    public static String APPROVE_REMARKS;

    public static String CURRENT_CONFIG_NAME;

    public static void loadProperties(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            fileName = "supplier.properties";
        }

        CURRENT_CONFIG_NAME = fileName.split("\\.")[0];

        ConfigLoader loginconfig = new ConfigLoader("login.properties");
        ConfigLoader supplierconfig = new ConfigLoader(fileName);

        CHROME_URL = loginconfig.get("chrome.url");
        APP_URL = loginconfig.get("app.url");
        USERNAME = loginconfig.get("username");
        PASSWORD = loginconfig.get("password");
        PC_DB_NAME = loginconfig.get("pc_DB_Name");
        MASTER_DB_NAME = loginconfig.get("master_DB_Name");
        MM_DB_NAME = loginconfig.get("mm_DB_Name");
        ACTIONSPERFORMEDBY = supplierconfig.get("actions_PerformedBy");

        ACTUALHEADER = supplierconfig.get("actualHeader");
        EXPECTEDHEADER = supplierconfig.get("expectedHeader");
        ACTUALDOCUEMNTNO = supplierconfig.get("actualDocumentNo");
        EXPECTEDDOCUEMNTNO = supplierconfig.get("expectedDocumentNo");
        TEMPLATE_PATH = supplierconfig.get("templatePath");
        OUTPUT_PATH = supplierconfig.get("outputPath");
        SCRIPT_NUMBER = supplierconfig.get("script_Number");
        TAKE_SCREENSHOTS = supplierconfig.get("take_Screenshots");
        TABLE_HEADERS = supplierconfig.get("table_Headers");
        TABLE_SEARCH_VALUES = supplierconfig.get("table_Search_Values");
        USERNAME1 = supplierconfig.get("username1");
        PASSWORD1 = supplierconfig.get("password1");
        USERNAME2 = supplierconfig.get("username2");
        PASSWORD2 = supplierconfig.get("password2");
        USERNAME3 = supplierconfig.get("username3");
        PASSWORD3 = supplierconfig.get("password3");

        MASTER_MODULE = supplierconfig.get("master_Module");
        SUB_MASTER_MODULE = supplierconfig.get("sub_Master_Module");

        SUPPLIER_NAME = supplierconfig.get("supplierName");
        SUPPLIER_ADDRESS = supplierconfig.get("address");
        SUPPLIER_STATE = supplierconfig.get("state");
        SUPPLIER_CITY = supplierconfig.get("city");
        SUPPLIER_PINCODE = supplierconfig.get("pincode");
        SUPPLIER_REGION = supplierconfig.get("region");

        SUPPLIER_VIEW_ACTION = supplierconfig.get("supplier_View");
        SUPPLIER_RETURN_ACTION_IN_REVIEW = supplierconfig.get("supplier_Return_In_Review");
        EDIT_SUPPLIER_IN_REVIEW_RETURN = supplierconfig.get("editsupplier_Review_Return");
        REVIEW_RETURN_REMARKS = supplierconfig.get("review_Return_Remarks");
        REVIEW_REMARKS = supplierconfig.get("reviewRemarks");
        SUPPLIER_RETURN_ACTION_IN_APPROVE = supplierconfig.get("supplier_Return_In_Approve");
        EDIT_SUPPLIER_IN_APPROVE_RETURN = supplierconfig.get("editsupplier_Approve_Return");
        APPROVE_RETURN_REMARKS = supplierconfig.get("approve_Return_Remarks");
        APPROVE_REMARKS = supplierconfig.get("approveRemarks");

    }
}
