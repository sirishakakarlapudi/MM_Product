package configData;

import utilities.ConfigLoader;

public class PasswordChange_AndComplexityData {
	public static String CURRENT_CONFIG_NAME;
	public static String CHROME_URL;
	public static String APP_URL;
	public static String USERNAME;
	public static String TEMPORARY_PASSWORD;
	public static String ACTUALHEADER;
	public static String EXPECTEDHEADER;
	public static String ACTUALDOCUEMNTNO;
	public static String EXPECTEDDOCUEMNTNO;
	public static String TEMPLATE_PATH;
	public static String OUTPUT_PATH;
	public static String SCRIPT_NUMBER;
	public static String TAKE_SCREENSHOTS;
	public static String NEW_PASSWORD;
	public static String CONFIRM_PASSWORD;
	public static String INCORRECT_TEMPORARY_PASSWORD;
	public static String NEW_PASSWORD_1;
	public static String NEW_PASSWORD_2;
	public static String NEW_PASSWORD_3;
	
	
	
	
	
	public static void loadProperties(String fileName) {
		// Ensure we handle potential null or empty fileName
		if (fileName == null || fileName.isEmpty()) {
			fileName = "passwordchange_complexity.properties";
		}

		// Store config name without extension for backup folder fallback
		CURRENT_CONFIG_NAME = fileName.split("\\.")[0];

		ConfigLoader loginconfig = new ConfigLoader("login.properties");
		ConfigLoader pwdchange_complexityconfig = new ConfigLoader(fileName);

		CHROME_URL = loginconfig.get("chrome.url");
		APP_URL = loginconfig.get("app.url");
		USERNAME = pwdchange_complexityconfig.get("username");
		TEMPORARY_PASSWORD = pwdchange_complexityconfig.get("temporaryPassword");
		ACTUALHEADER = pwdchange_complexityconfig.get("actualHeader");
		EXPECTEDHEADER = pwdchange_complexityconfig.get("expectedHeader");
		ACTUALDOCUEMNTNO = pwdchange_complexityconfig.get("actualDocumentNo");
		EXPECTEDDOCUEMNTNO = pwdchange_complexityconfig.get("expectedDocumentNo");
		TEMPLATE_PATH = pwdchange_complexityconfig.get("templatePath");
		OUTPUT_PATH = pwdchange_complexityconfig.get("outputPath");
		SCRIPT_NUMBER = pwdchange_complexityconfig.get("script_Number");
		TAKE_SCREENSHOTS = pwdchange_complexityconfig.get("take_Screenshots");
		NEW_PASSWORD = pwdchange_complexityconfig.get("newPassword");
		CONFIRM_PASSWORD = pwdchange_complexityconfig.get("confirmPassword");
		INCORRECT_TEMPORARY_PASSWORD = pwdchange_complexityconfig.get("incorrect_TemporaryPassword");
		NEW_PASSWORD_1 = pwdchange_complexityconfig.get("newPassword_1");
		NEW_PASSWORD_2 = pwdchange_complexityconfig.get("newPassword_2");
		NEW_PASSWORD_3 = pwdchange_complexityconfig.get("newPassword_3");
	}

}
