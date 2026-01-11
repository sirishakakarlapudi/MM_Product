package configData;

import utilities.ConfigLoader;

public class MaterialCategoryData {
	 private static final ConfigLoader loginconfig = new ConfigLoader("login.properties");
	 private static final ConfigLoader materialcategoryconfig = new ConfigLoader("materialcategory.properties");
    public static final String USERNAME = loginconfig.get("username");
    public static final String PASSWORD = loginconfig.get("password");
    public static final String APP_URL  = loginconfig.get("app.url");
    public static final String ACTUALHEADER  = materialcategoryconfig.get("actualHeader");
    public static final String EXPECTEDHEADER  = materialcategoryconfig.get("expectedHeader");
    public static final String TEMPLATE_PATH  = materialcategoryconfig.get("templatePath");
    public static final String OUTPUT_PATH = materialcategoryconfig.get("outputPath");
    public static final String MASTER_MODULE = materialcategoryconfig.get("masterModule");
    public static final String MASTER_SUB_MODULE = materialcategoryconfig.get("masterSubModule");
    public static final String MATERIAL_CATEGORY_NAME = materialcategoryconfig.get("materialCategoryName");
    public static final String SAMPLING_PLAN = materialcategoryconfig.get("samplingPlan");
    public static final String WEIGHT_VERIFICATION_PLAN = materialcategoryconfig.get("weightVerificationPlan");
    public static final String EDIT_MATERIAL_CATEGORY_NAME = materialcategoryconfig.get("editmaterialCategoryName");
    

}
