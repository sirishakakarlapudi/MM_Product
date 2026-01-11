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
    
}
