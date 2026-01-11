package configData;

import utilities.ConfigLoader;

public class WeightVariationData {
    private static final ConfigLoader loginconfig = new ConfigLoader("login.properties");
    private static final ConfigLoader weightvariationconfig = new ConfigLoader("weightvariation.properties");
    public static final String USERNAME = loginconfig.get("username");
    public static final String PASSWORD = loginconfig.get("password");
    public static final String APP_URL = loginconfig.get("app.url");
    public static final String ACTUALHEADER = weightvariationconfig.get("actualHeader");
    public static final String EXPECTEDHEADER = weightvariationconfig.get("expectedHeader");
    public static final String TEMPLATE_PATH = weightvariationconfig.get("templatePath");
    public static final String OUTPUT_PATH = weightvariationconfig.get("outputPath");
    public static final String MASTER_MODULE = weightvariationconfig.get("masterModule");
    public static final String BALANCE_CAPACITY = weightvariationconfig.get("balanceCapacity");
    public static final String WEIGHT_TYPE = weightvariationconfig.get("weightType");
    public static final String EFFECTIVE_DATE = weightvariationconfig.get("effectiveDate");
    public static final String GROSS_WEIGHT_FROM = weightvariationconfig.get("grossweightfrom");
    public static final String ALLOWED_VARIATION = weightvariationconfig.get("allowedVariation");
    public static final String GROSS_WEIGHT_TO = weightvariationconfig.get("grossweightto");
    public static final String EDIT_WEIGHT_TYPE = weightvariationconfig.get("editweightType");
    public static final String PACK_WEIGHT = weightvariationconfig.get("packweight");
    public static final String MIN_WEIGHT = weightvariationconfig.get("minWeight");
    public static final String MAX_WEIGHT = weightvariationconfig.get("maxWeight");
    
}