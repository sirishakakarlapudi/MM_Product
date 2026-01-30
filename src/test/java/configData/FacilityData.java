package configData;

import utilities.ConfigLoader;

public class FacilityData {
    private static final ConfigLoader loginconfig = new ConfigLoader("login.properties");
    private static final ConfigLoader facilityconfig = new ConfigLoader("facility.properties");
    public static final String USERNAME = loginconfig.get("username");
    public static final String PASSWORD = loginconfig.get("password");
    public static final String APP_URL = loginconfig.get("app.url");
    public static final String ACTUALHEADER = facilityconfig.get("actualHeader");
    public static final String EXPECTEDHEADER = facilityconfig.get("expectedHeader");
    public static final String TEMPLATE_PATH = facilityconfig.get("templatePath");
    public static final String OUTPUT_PATH = facilityconfig.get("outputPath");
    public static final String MASTER_MODULE = facilityconfig.get("masterModule");
    public static final String FACILITY_NAME = facilityconfig.get("facilityName");
    public static final String FACILITY_TYPE = facilityconfig.get("facilityType");
    public static final String DEPARTMENT = facilityconfig.get("department");
    public static final String PARENT_FACILITY = facilityconfig.get("parentFacility");
    public static final String EDIT_FACILITY_NAME = facilityconfig.get("editFacilityName");

}
