package configData;

import utilities.ConfigLoader;

public class DepartmentData {
	 private static final ConfigLoader loginconfig = new ConfigLoader("login.properties");
	 private static final ConfigLoader deptconfig = new ConfigLoader("department.properties");
    public static final String USERNAME = loginconfig.get("username");
    public static final String PASSWORD = loginconfig.get("password");
    public static final String APP_URL  = loginconfig.get("app.url");
    public static final String ACTUALHEADER  = deptconfig.get("actualHeader");
    public static final String EXPECTEDHEADER  = deptconfig.get("expectedHeader");
    public static final String TEMPLATE_PATH  = deptconfig.get("templatePath");
    public static final String OUTPUT_PATH = deptconfig.get("outputPath");
    public static final String MASTER_MODULE = deptconfig.get("masterModule");
    public static final String DEPT_NAME = deptconfig.get("deptName");
    public static final String DEPT_DESC = deptconfig.get("deptDesc");
    public static final String EDIT_DEPT_NAME = deptconfig.get("editDeptName");
    
}
