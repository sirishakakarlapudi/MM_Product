package configData;

import utilities.ConfigLoader;

public class SecurityGroupData {
	 private static final ConfigLoader loginconfig = new ConfigLoader("login.properties");
	 private static final ConfigLoader securitygroupconfig = new ConfigLoader("securitygroup.properties");
    public static final String USERNAME = loginconfig.get("username");
    public static final String PASSWORD = loginconfig.get("password");
    public static final String APP_URL  = loginconfig.get("app.url");
    public static final String ACTUALHEADER  = securitygroupconfig.get("actualHeader");
    public static final String EXPECTEDHEADER  = securitygroupconfig.get("expectedHeader");
    public static final String TEMPLATE_PATH  = securitygroupconfig.get("templatePath");
    public static final String OUTPUT_PATH = securitygroupconfig.get("outputPath");
    public static final String MASTER_MODULE = securitygroupconfig.get("masterModule");
    public static final String SECURITYGROUP_NAME = securitygroupconfig.get("securitygroupName");
    public static final String SECURITYGROUP_DESC = securitygroupconfig.get("securitygroupDesc");
    public static final String SELECT_MODULE = securitygroupconfig.get("module");
    public static final String USER_PRIVILEGES = securitygroupconfig.get("privileges");
    public static final String EDIT_SECURITYGROUP_NAME = securitygroupconfig.get("editsecuritygroupName");
    public static final String EDIT_USER_PRIVILEGES = securitygroupconfig.get("editprivileges");
}
