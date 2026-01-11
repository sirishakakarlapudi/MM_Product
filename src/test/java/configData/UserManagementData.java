package configData;

import utilities.ConfigLoader;

public class UserManagementData {
	
	 private static final ConfigLoader loginconfig = new ConfigLoader("login.properties");
	 private static final ConfigLoader usermanagementconfig = new ConfigLoader("usermanagement.properties");
    public static final String USERNAME = loginconfig.get("username");
    public static final String PASSWORD = loginconfig.get("password");
    public static final String APP_URL  = loginconfig.get("app.url");
    public static final String ACTUALHEADER  = usermanagementconfig.get("actualHeader");
    public static final String EXPECTEDHEADER  = usermanagementconfig.get("expectedHeader");
    public static final String TEMPLATE_PATH  = usermanagementconfig.get("templatePath");
    public static final String OUTPUT_PATH = usermanagementconfig.get("outputPath");
    public static final String MASTER_MODULE = usermanagementconfig.get("masterModule");
    public static final String SUB_MASTER_MODULE = usermanagementconfig.get("submasterModule");
    public static final String EMPLOYEE_ID = usermanagementconfig.get("employeeId");
    public static final String EMPLOYEE_NAME = usermanagementconfig.get("employeeName");
    public static final String EMAIL = usermanagementconfig.get("email");
    public static final String MOBILE_NUMBER = usermanagementconfig.get("mobileNumber");
    public static final String USER_NAME = usermanagementconfig.get("userName");
    public static final String TEMPORARY_PASSWORD = usermanagementconfig.get("temporaryPassword");
   public static final String DEPARTMENT = usermanagementconfig.get("department");
   public static final String DESIGNATION = usermanagementconfig.get("designation");
   public static final String MODULE = usermanagementconfig.get("module");
   public static final String SECURITY_GROUP = usermanagementconfig.get("sgname");

}
