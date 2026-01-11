package configData;

import utilities.ConfigLoader;

public class ChecklistData {

	private static final ConfigLoader loginconfig = new ConfigLoader("login.properties");
	private static final ConfigLoader bomconfig = new ConfigLoader("checklist.properties");
	public static final String USERNAME = loginconfig.get("username");
	public static final String PASSWORD = loginconfig.get("password");
	public static final String APP_URL = loginconfig.get("app.url");
	public static final String ACTUALHEADER = bomconfig.get("actualHeader");
	public static final String EXPECTEDHEADER = bomconfig.get("expectedHeader");
	public static final String TEMPLATE_PATH = bomconfig.get("templatePath");
	public static final String OUTPUT_PATH = bomconfig.get("outputPath");
	public static final String MASTER_MODULE = bomconfig.get("masterModule");
	public static final String CHECKLIST_TYPE = bomconfig.get("checklistType");
	public static final String CHECK_POINT_TYPE = bomconfig.get("checkPointType");
	public static final String ENTER_NUMBER = bomconfig.get("enterNumber");
	public static final String ENTER_DATA= bomconfig.get("enterData");
	public static final String EDIT_CHECK_POINT_TYPE = bomconfig.get("editcheckPointType");
	
}
