package configData;

import utilities.ConfigLoader;

public class QuickProductData {
	 private static final ConfigLoader loginconfig = new ConfigLoader("login.properties");
	 private static final ConfigLoader quickproductconfig = new ConfigLoader("quickproduct.properties");
    public static final String USERNAME = loginconfig.get("username");
    public static final String PASSWORD = loginconfig.get("password");
    public static final String APP_URL  = loginconfig.get("app.url");
    public static final String ACTUALHEADER  = quickproductconfig.get("actualHeader");
    public static final String EXPECTEDHEADER  = quickproductconfig.get("expectedHeader");
    public static final String TEMPLATE_PATH  = quickproductconfig.get("templatePath");
    public static final String OUTPUT_PATH = quickproductconfig.get("outputPath");
    public static final String MASTER_MODULE = quickproductconfig.get("masterModule");
    public static final String MASTER_SUB_MODULE = quickproductconfig.get("masterSubModule");
    public static final String PRODUCT = quickproductconfig.get("product");
    public static final String PRODUCT_DESCRIPTION= quickproductconfig.get("productDescription");
    public static final String ROUTE_CODE= quickproductconfig.get("routeCode");
    public static final String ROUTE_DESCRIPTION= quickproductconfig.get("routeDescription");
    public static final String EDIT_PRODUCT = quickproductconfig.get("editproduct");
    public static final String STAGE_CODE = quickproductconfig.get("stageCode");
    public static final String UOM= quickproductconfig.get("uom");
    public static final String STAGE_DESCRIPTION= quickproductconfig.get("stageDescription");
    public static final String STORAGE_LOCATION= quickproductconfig.get("storageLocation");
    public static final String SAMPLING_ACTIVITY= quickproductconfig.get("samplingActivity");
    public static final String DISPENSING_ACTIVITY= quickproductconfig.get("dispensingActivity");
    public static final String CLEANING_AGENT= quickproductconfig.get("cleaningAgent");
    
    
    

}
