package configData;

import utilities.ConfigLoader;

public class NDCData {
	 private static final ConfigLoader loginconfig = new ConfigLoader("login.properties");
	 private static final ConfigLoader ndcconfig = new ConfigLoader("ndc.properties");
    public static final String USERNAME = loginconfig.get("username");
    public static final String PASSWORD = loginconfig.get("password");
    public static final String APP_URL  = loginconfig.get("app.url");
    public static final String ACTUALHEADER  = ndcconfig.get("actualHeader");
    public static final String EXPECTEDHEADER  = ndcconfig.get("expectedHeader");
    public static final String TEMPLATE_PATH  = ndcconfig.get("templatePath");
    public static final String OUTPUT_PATH = ndcconfig.get("outputPath");
    public static final String MASTER_MODULE = ndcconfig.get("masterModule");
    public static final String MASTER_SUB_MODULE = ndcconfig.get("masterSubModule");
    public static final String NDC_NUMBER = ndcconfig.get("ndcNumber");
    
    

}
