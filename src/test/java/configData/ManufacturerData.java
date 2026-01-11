package configData;

import utilities.ConfigLoader;

public class ManufacturerData {
	 private static final ConfigLoader loginconfig = new ConfigLoader("login.properties");
	 private static final ConfigLoader manufacturerconfig = new ConfigLoader("manufacturer.properties");
    public static final String USERNAME = loginconfig.get("username");
    public static final String PASSWORD = loginconfig.get("password");
    public static final String APP_URL  = loginconfig.get("app.url");
    public static final String ACTUALHEADER  = manufacturerconfig.get("actualHeader");
    public static final String EXPECTEDHEADER  = manufacturerconfig.get("expectedHeader");
    public static final String TEMPLATE_PATH  = manufacturerconfig.get("templatePath");
    public static final String OUTPUT_PATH = manufacturerconfig.get("outputPath");
    public static final String MASTER_MODULE = manufacturerconfig.get("masterModule");
    public static final String MASTER_SUB_MODULE = manufacturerconfig.get("masterSubModule");
    public static final String MANUFACTURER_NAME = manufacturerconfig.get("manufacturerName");
    public static final String MANUFACTURER_ADDRESS = manufacturerconfig.get("manufacturerAddress");
    public static final String MANUFACTURER_CITY = manufacturerconfig.get("manufacturerCity");
    public static final String MANUFACTURER_STATE = manufacturerconfig.get("manufacturerState");
    public static final String MANUFACTURER_PINCODE = manufacturerconfig.get("manufacturerPincode");
    public static final String MANUFACTURER_REGION = manufacturerconfig.get("manufacturerRegion");
    public static final String EDIT_MANUFACTURER_NAME = manufacturerconfig.get("editmanufacturerName");
    

}
