package configData;

import utilities.ConfigLoader;

public class ProductSpecificationData {
	 private static final ConfigLoader loginconfig = new ConfigLoader("login.properties");
	 private static final ConfigLoader productspecificationconfig = new ConfigLoader("productspecification.properties");
    public static final String USERNAME = loginconfig.get("username");
    public static final String PASSWORD = loginconfig.get("password");
    public static final String APP_URL  = loginconfig.get("app.url");
    public static final String ACTUALHEADER  = productspecificationconfig.get("actualHeader");
    public static final String EXPECTEDHEADER  = productspecificationconfig.get("expectedHeader");
    public static final String TEMPLATE_PATH  = productspecificationconfig.get("templatePath");
    public static final String OUTPUT_PATH = productspecificationconfig.get("outputPath");
    public static final String MASTER_MODULE = productspecificationconfig.get("masterModule");
    public static final String MASTER_SUB_MODULE = productspecificationconfig.get("masterSubModule");
    public static final String PRODUCT_CODE = productspecificationconfig.get("productCode");
    public static final String SPECIFICATION_NUMBER = productspecificationconfig.get("specificationNumber");
  public static final String EDIT_SPECIFICATION_NUMBER = productspecificationconfig.get("editspecificationNumber");
  
    

}
