package configData;

import utilities.ConfigLoader;

public class AkronProductData {
    private static final ConfigLoader loginconfig = new ConfigLoader("login.properties");
    private static final ConfigLoader productconfig = new ConfigLoader("akronproduct.properties");
    public static final String USERNAME = loginconfig.get("username");
    public static final String PASSWORD = loginconfig.get("password");
    public static final String APP_URL = loginconfig.get("app.url");
    public static final String ACTUALHEADER = productconfig.get("actualHeader");
    public static final String EXPECTEDHEADER = productconfig.get("expectedHeader");
    public static final String TEMPLATE_PATH = productconfig.get("templatePath");
    public static final String OUTPUT_PATH = productconfig.get("outputPath");
    public static final String MASTER_MODULE = productconfig.get("masterModule");
    public static final String MASTER_SUB_MODULE = productconfig.get("masterSubModule");
    public static final String PRODUCT = productconfig.get("product");
    public static final String NDC_NUMBER = productconfig.get("ndcNumber");

}
