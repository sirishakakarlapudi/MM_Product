package configData;

import utilities.ConfigLoader;

public class SupplierData {
	
	
	 private static final ConfigLoader loginconfig = new ConfigLoader("login.properties");
	 private static final ConfigLoader supplierconfig = new ConfigLoader("supplier.properties");
    public static final String USERNAME = loginconfig.get("username");
    public static final String PASSWORD = loginconfig.get("password");
    public static final String APP_URL  = loginconfig.get("app.url");
    public static final String ACTUALHEADER  = supplierconfig.get("actualHeader");
    public static final String EXPECTEDHEADER  = supplierconfig.get("expectedHeader");
    public static final String TEMPLATE_PATH  = supplierconfig.get("templatePath");
    public static final String OUTPUT_PATH = supplierconfig.get("outputPath");
    public static final String MASTER_MODULE = supplierconfig.get("masterModule");
    public static final String MASTER_SUB_MODULE = supplierconfig.get("masterSubModule");
    public static final String SUPPLIER_NAME = supplierconfig.get("supplierName");
    public static final String SUPPLIER_ADDRESS = supplierconfig.get("supplierAddress");
    public static final String SUPPLIER_CITY = supplierconfig.get("supplierCity");
    public static final String SUPPLIER_STATE = supplierconfig.get("supplierState");
    public static final String SUPPLIER_PINCODE = supplierconfig.get("supplierPincode");
    public static final String SUPPLIER_REGION = supplierconfig.get("supplierRegion");
    public static final String EDIT_SUPPLIER_NAME = supplierconfig.get("editsupplierName");
    

}
