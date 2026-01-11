package configData;

import utilities.ConfigLoader;

public class AkronBOMData {
    private static final ConfigLoader loginconfig = new ConfigLoader("login.properties");
    private static final ConfigLoader bomconfig = new ConfigLoader("akronbom.properties");
    public static final String USERNAME = loginconfig.get("username");
    public static final String PASSWORD = loginconfig.get("password");
    public static final String APP_URL = loginconfig.get("app.url");
    public static final String ACTUALHEADER = bomconfig.get("actualHeader");
    public static final String EXPECTEDHEADER = bomconfig.get("expectedHeader");
    public static final String TEMPLATE_PATH = bomconfig.get("templatePath");
    public static final String OUTPUT_PATH = bomconfig.get("outputPath");
    public static final String MASTER_MODULE = bomconfig.get("masterModule");
    public static final String PRODUCT_CODE = bomconfig.get("productCode");
    public static final String LOCATION = bomconfig.get("location");
    public static final String BATCH_SIZE = bomconfig.get("batchSize");
    public static final String BATCH_SIZE_UOM = bomconfig.get("batchSizeUom");
    public static final String YIELD_RANGE_MIN = bomconfig.get("yieldMinRange");
    public static final String YIELD_RANGE_MAX = bomconfig.get("yieldMaxRange");
    public static final String YIELD_RANGE_TARGET = bomconfig.get("yieldTarget");
    public static final String STANDARD_BOM = bomconfig.get("standardBom");
    public static final String MATERIAL_CODE = bomconfig.get("materialCode");
    public static final String STANDARD_QUANTITY = bomconfig.get("standardQuantity");
    public static final String TOLERANCE = bomconfig.get("tolerance");
    public static final String EDIT_BATCH_SIZE = bomconfig.get("editBatchSize");

}
