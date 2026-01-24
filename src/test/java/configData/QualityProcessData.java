package configData;

import utilities.ConfigLoader;

public class QualityProcessData {

	private static final ConfigLoader loginconfig = new ConfigLoader("login.properties");
	private static final ConfigLoader qualityprocessconfig = new ConfigLoader("qualityprocess.properties");
	public static final String USERNAME = loginconfig.get("username");
	public static final String PASSWORD = loginconfig.get("password");
	public static final String APP_URL = loginconfig.get("app.url");
	public static final String ACTUALHEADER = qualityprocessconfig.get("actualHeader");
	public static final String EXPECTEDHEADER = qualityprocessconfig.get("expectedHeader");
	public static final String TEMPLATE_PATH = qualityprocessconfig.get("templatePath");
	public static final String OUTPUT_PATH = qualityprocessconfig.get("outputPath");
	public static final String MASTER_MODULE = qualityprocessconfig.get("masterModule");
	public static final String SUB_MASTER_MODULE = qualityprocessconfig.get("submasterModule");
	public static final String TABLE_HEADERS = qualityprocessconfig.get("tableHeaders");
	public static final String TABLE_SEARCH_VALUES = qualityprocessconfig.get("tableSearchValues");
	public static final String CHANGED_TABLE_HEADERS = qualityprocessconfig.get("tableHeaders");
	public static final String CHANGED_TABLE_SEARCH_VALUES = qualityprocessconfig.get("tableSearchValues");
	public static final String ASSIGN_AR_NUMBER = qualityprocessconfig.get("assignARNumber");
	public static final String SAMPLE_QUANTITY = qualityprocessconfig.get("sampleQuantity");
	public static final String SAMPLING_ROOM_ID = qualityprocessconfig.get("samplingRoomID");
	public static final String WEIGHING_BALANCE_ID = qualityprocessconfig.get("weighingBalanceID");
	public static final String CHECK_PACK_NUMBERS = qualityprocessconfig.get("checkPackNumbers");
	public static final String CLICK_MATERIAL_SAMPLING_VIEW = qualityprocessconfig.get("clickMaterialSamplingView");
	public static final String DOWNLOAD_MATERIAL_SAMPLING_PDF = qualityprocessconfig.get("clickMaterialSamplingPDF");
	public static final String CAPTURE_MATERIAL_SAMPLING_PDF = qualityprocessconfig.get("captureMaterialSamplingPDF");
	public static final String MATERIAL_APPROVE_OR_REJECT = qualityprocessconfig.get("materialApprove/Reject");
	
	
	
	

}
