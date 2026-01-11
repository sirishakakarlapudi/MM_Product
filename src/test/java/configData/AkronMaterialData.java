package configData;

import utilities.ConfigLoader;

public class AkronMaterialData {
	 private static final ConfigLoader loginconfig = new ConfigLoader("login.properties");
	 private static final ConfigLoader materialconfig = new ConfigLoader("akronmaterial.properties");
    public static final String USERNAME = loginconfig.get("username");
    public static final String PASSWORD = loginconfig.get("password");
    public static final String APP_URL  = loginconfig.get("app.url");
    public static final String ACTUALHEADER  = materialconfig.get("actualHeader");
    public static final String EXPECTEDHEADER  = materialconfig.get("expectedHeader");
    public static final String TEMPLATE_PATH  = materialconfig.get("templatePath");
    public static final String OUTPUT_PATH = materialconfig.get("outputPath");
    public static final String MASTER_MODULE = materialconfig.get("masterModule");
    public static final String MASTER_SUB_MODULE = materialconfig.get("masterSubModule");
    public static final String MATERIAL_NAME = materialconfig.get("materialName");
    public static final String MATERIAL_CODE = materialconfig.get("materialCode");
    public static final String MATERIAL_CATEGORY= materialconfig.get("materialCategory");
    public static final String TYPE_OF_MATERIAL= materialconfig.get("typeOfMaterial");
    public static final String UOM= materialconfig.get("uom");
    public static final String STORAGE_LOCATION= materialconfig.get("storageLocation");
    public static final String SAMPLING_ACTIVITY= materialconfig.get("samplingActivity");
    public static final String DISPENSING_ACTIVITY= materialconfig.get("dispensingActivity");
    public static final String WEIGHT_VERIFICATION= materialconfig.get("weightVerification");
    public static final String CLEANING_AGENT= materialconfig.get("cleaningAgent");
    public static final String SUPPLIER= materialconfig.get("supplier");
    public static final String MANUFACTURER= materialconfig.get("manufacturer");
    public static final String EDIT_MATERIAL_NAME = materialconfig.get("editmaterialName");
    
    

}
