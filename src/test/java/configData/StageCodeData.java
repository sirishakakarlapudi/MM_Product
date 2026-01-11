package configData;

import utilities.ConfigLoader;

public class StageCodeData {
	 private static final ConfigLoader loginconfig = new ConfigLoader("login.properties");
	 private static final ConfigLoader stagecodeconfig = new ConfigLoader("stagecode.properties");
    public static final String USERNAME = loginconfig.get("username");
    public static final String PASSWORD = loginconfig.get("password");
    public static final String APP_URL  = loginconfig.get("app.url");
    public static final String ACTUALHEADER  = stagecodeconfig.get("actualHeader");
    public static final String EXPECTEDHEADER  = stagecodeconfig.get("expectedHeader");
    public static final String TEMPLATE_PATH  = stagecodeconfig.get("templatePath");
    public static final String OUTPUT_PATH = stagecodeconfig.get("outputPath");
    public static final String MASTER_MODULE = stagecodeconfig.get("masterModule");
    public static final String MASTER_SUB_MODULE = stagecodeconfig.get("masterSubModule");
    public static final String STAGE_CODE = stagecodeconfig.get("stageCode");
    public static final String UOM= stagecodeconfig.get("uom");
    public static final String STAGE_DESCRIPTION= stagecodeconfig.get("stageDescription");
    public static final String STORAGE_LOCATION= stagecodeconfig.get("storageLocation");
    public static final String SAMPLING_ACTIVITY= stagecodeconfig.get("samplingActivity");
    public static final String DISPENSING_ACTIVITY= stagecodeconfig.get("dispensingActivity");
    public static final String CLEANING_AGENT= stagecodeconfig.get("cleaningAgent");
    public static final String EDIT_STAGE_CODE = stagecodeconfig.get("editstagecode");
    
    

}
