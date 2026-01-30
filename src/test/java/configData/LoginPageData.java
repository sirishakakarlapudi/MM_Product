package configData;

import utilities.ConfigLoader;

public class LoginPageData {
	 private static final ConfigLoader config = new ConfigLoader("login.properties");
    public static final String USERNAME = config.get("username");
    public static final String PASSWORD = config.get("password");
    public static final String APP_URL  = config.get("app.url");
    public static final String ACTUALHEADER  = config.get("actualHeader");
    public static final String EXPECTEDHEADER  = config.get("expectedHeader");
    public static final String TEMPLATE_PATH  = config.get("templatePath");
    public static final String OUTPUT_PATH = config.get("outputPath");
    public static final String PC_DB_NAME = config.get("pc_DB_Name");
    public static final String MASTER_DB_NAME = config.get("master_DB_Name");
    public static final String MM_DB_NAME = config.get("mm_DB_Name");
    
}
