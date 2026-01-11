package configData;

import utilities.ConfigLoader;

public class EquipmentData {
	 private static final ConfigLoader loginconfig = new ConfigLoader("login.properties");
	 private static final ConfigLoader equipmentconfig = new ConfigLoader("equipment.properties");
    public static final String USERNAME = loginconfig.get("username");
    public static final String PASSWORD = loginconfig.get("password");
    public static final String APP_URL  = loginconfig.get("app.url");
    public static final String ACTUALHEADER  = equipmentconfig.get("actualHeader");
    public static final String EXPECTEDHEADER  = equipmentconfig.get("expectedHeader");
    public static final String TEMPLATE_PATH  = equipmentconfig.get("templatePath");
    public static final String OUTPUT_PATH = equipmentconfig.get("outputPath");
    public static final String MASTER_MODULE = equipmentconfig.get("masterModule");
    public static final String EQUIPMENT_NAME = equipmentconfig.get("equipmentName");
    public static final String EQUIPMENT_ID = equipmentconfig.get("equipmentID");
    public static final String EDIT_EQUIPMENT_NAME = equipmentconfig.get("editequipmentName");
    public static final String EQUIPMENT_TYPE = equipmentconfig.get("equipmentType");
    public static final String CAPACITY_IN_KG = equipmentconfig.get("capacityInKg");
    public static final String OPERATIONAL_RANGE_MIN = equipmentconfig.get("operationalRangeMin");
    public static final String OPERATIONAL_RANGE_MAX = equipmentconfig.get("operationalRangeMax");
    public static final String DEPARTMENT = equipmentconfig.get("department");
    public static final String FACILITY = equipmentconfig.get("facility");
    public static final String WEIGHING_BALANCE_FACILITY = equipmentconfig.get("weighingBalanceFacility");
    
}
