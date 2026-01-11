package configData;

import utilities.ConfigLoader;

public class RouteCodeData {
	 private static final ConfigLoader loginconfig = new ConfigLoader("login.properties");
	 private static final ConfigLoader routecodeconfig = new ConfigLoader("routecode.properties");
    public static final String USERNAME = loginconfig.get("username");
    public static final String PASSWORD = loginconfig.get("password");
    public static final String APP_URL  = loginconfig.get("app.url");
    public static final String ACTUALHEADER  = routecodeconfig.get("actualHeader");
    public static final String EXPECTEDHEADER  = routecodeconfig.get("expectedHeader");
    public static final String TEMPLATE_PATH  = routecodeconfig.get("templatePath");
    public static final String OUTPUT_PATH = routecodeconfig.get("outputPath");
    public static final String MASTER_MODULE = routecodeconfig.get("masterModule");
    public static final String MASTER_SUB_MODULE = routecodeconfig.get("masterSubModule");
    public static final String ROUTE_CODE = routecodeconfig.get("routeCode");
    public static final String ROUTE_DESCRIPTION= routecodeconfig.get("routeDescription");
    public static final String STAGE_CODE= routecodeconfig.get("stageCode");
    public static final String EDIT_STAGE_CODE= routecodeconfig.get("editstageCode");
    public static final String ACTIVE_STAGE_CODE= routecodeconfig.get("activestageCode");
    public static final String EDIT_ROUTE_CODE = routecodeconfig.get("editrouteCode");
    
    

}
