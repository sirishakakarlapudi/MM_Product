package configData;

import utilities.ConfigLoader;

public class MaterialSpecificationData {
	 private static final ConfigLoader loginconfig = new ConfigLoader("login.properties");
	 private static final ConfigLoader materialspecificationconfig = new ConfigLoader("materialspecification.properties");
    public static final String USERNAME = loginconfig.get("username");
    public static final String PASSWORD = loginconfig.get("password");
    public static final String APP_URL  = loginconfig.get("app.url");
    public static final String ACTUALHEADER  = materialspecificationconfig.get("actualHeader");
    public static final String EXPECTEDHEADER  = materialspecificationconfig.get("expectedHeader");
    public static final String TEMPLATE_PATH  = materialspecificationconfig.get("templatePath");
    public static final String OUTPUT_PATH = materialspecificationconfig.get("outputPath");
    public static final String MASTER_MODULE = materialspecificationconfig.get("masterModule");
    public static final String MASTER_SUB_MODULE = materialspecificationconfig.get("masterSubModule");
    public static final String MATERIAL_CODE = materialspecificationconfig.get("materialCode");
    public static final String SPECIFICATION_NUMBER = materialspecificationconfig.get("specificationNumber");
    public static final String REQUEST_TYPE = materialspecificationconfig.get("requestType");
    public static final String EDIT_SPECIFICATION_NUMBER = materialspecificationconfig.get("editspecificationNumber");
  
    

}
