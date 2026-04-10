package configData;

import utilities.ConfigLoader;

public class ProductSpecificationData {

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
    public static String ACTUALDOCUMENTNO;
    public static String EXPECTEDDOCUMENTNO;
    public static String TEMPLATE_PATH;
    public static String OUTPUT_PATH;
    public static String SCRIPT_NUMBER;
    public static String TAKE_SCREENSHOTS;
    public static String TABLE_HEADERS;

    public static String MASTER_MODULE;
    public static String SUB_MASTER_MODULE;

    // Creation Fields
    public static String PRODUCT_CODE;
    public static String SPECIFICATION_NUMBER;

    // Edit Fields
    public static String EDIT_SPECIFICATION_NUMBER_IN_REVIEW_RETURN;
    public static String EDIT_SPECIFICATION_NUMBER_IN_APPROVE_RETURN;

    // Workflow Flags (yes/no)
    public static String PRODUCTSPEC_VIEW_ACTION;
    public static String PRODUCTSPEC_RETURN_ACTION_IN_REVIEW;
    public static String PRODUCTSPEC_RETURN_ACTION_IN_APPROVE;
    

    // Remarks
    public static String REVIEW_RETURN_REMARKS;
    public static String REVIEW_REMARKS;
    public static String APPROVE_RETURN_REMARKS;
    public static String APPROVE_REMARKS;

    // DB Config
    public static String CURRENT_CONFIG_NAME;
    
    
    //Update Spec Details
    public static String UPDATE_NAME_OF_THE_TEST;
    public static String UPDATE_SPECIFICATION;
    public static String UPDATE_VALIDATION;
    public static String PRODUCTSPEC_UPDATE_AFTER_APPROVE;

    public static void loadProperties(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            fileName = "productspecification.properties";
        }

        CURRENT_CONFIG_NAME = fileName.split("\\.")[0];

        ConfigLoader loginconfig = new ConfigLoader("login.properties");
        ConfigLoader config = new ConfigLoader(fileName);

        CHROME_URL = loginconfig.get("chrome.url");
        APP_URL = loginconfig.get("app.url");
        USERNAME = loginconfig.get("username");
        PASSWORD = loginconfig.get("password");
        PC_DB_NAME = loginconfig.get("pc_DB_Name");
        MASTER_DB_NAME = loginconfig.get("master_DB_Name");
        MM_DB_NAME = loginconfig.get("mm_DB_Name");
        ACTIONSPERFORMEDBY = config.get("actions_PerformedBy");

        ACTUALHEADER = config.get("actualHeader");
        EXPECTEDHEADER = config.get("expectedHeader");
        ACTUALDOCUMENTNO = config.get("actualDocumentNo");
        EXPECTEDDOCUMENTNO = config.get("expectedDocumentNo");
        TEMPLATE_PATH = config.get("templatePath");
        OUTPUT_PATH = config.get("outputPath");
        SCRIPT_NUMBER = config.get("script_Number");
        TAKE_SCREENSHOTS = config.get("take_Screenshots");
        TABLE_HEADERS = config.get("table_Headers");

        USERNAME1 = config.get("username1");
        PASSWORD1 = config.get("password1");
        USERNAME2 = config.get("username2");
        PASSWORD2 = config.get("password2");
        USERNAME3 = config.get("username3");
        PASSWORD3 = config.get("password3");

        MASTER_MODULE = config.get("master_Module");
        SUB_MASTER_MODULE = config.get("sub_Master_Module");

        PRODUCT_CODE = config.get("productCode");
        SPECIFICATION_NUMBER = config.get("specificationNumber");
        EDIT_SPECIFICATION_NUMBER_IN_REVIEW_RETURN = config.get("editSpecificationNumber_In_Review_Return");
        EDIT_SPECIFICATION_NUMBER_IN_APPROVE_RETURN = config.get("editSpecificationNumber_In_Approve_Return");
        PRODUCTSPEC_VIEW_ACTION = config.get("productSpec_View");
        PRODUCTSPEC_RETURN_ACTION_IN_REVIEW = config.get("productSpec_Return_In_Review");
        PRODUCTSPEC_RETURN_ACTION_IN_APPROVE = config.get("productSpec_Return_In_Approve");

        REVIEW_RETURN_REMARKS = config.get("review_Return_Remarks");
        REVIEW_REMARKS = config.get("reviewRemarks");
        APPROVE_RETURN_REMARKS = config.get("approve_Return_Remarks");
        APPROVE_REMARKS = config.get("approveRemarks");
        
        
        UPDATE_NAME_OF_THE_TEST = config.get("update_Name_Of_The_Test");
        UPDATE_SPECIFICATION = config.get("update_Specification");
        UPDATE_VALIDATION = config.get("update_Validation");
        PRODUCTSPEC_UPDATE_AFTER_APPROVE = config.get("productspec_Update_After_Approve");
       
    }
}
