package configData;

import utilities.ConfigLoader;

public class MaterialInwardForSolidData {
        private static final ConfigLoader loginconfig = new ConfigLoader("login.properties");
        private static final ConfigLoader inwardconfig = new ConfigLoader("materialinwardforsolid.properties");
        public static final String USERNAME = loginconfig.get("username");
        public static final String PASSWORD = loginconfig.get("password");
        public static final String APP_URL = loginconfig.get("app.url");
        public static final String ACTUALHEADER = inwardconfig.get("actualHeader");
        public static final String EXPECTEDHEADER = inwardconfig.get("expectedHeader");
        public static final String TEMPLATE_PATH = inwardconfig.get("templatePath");
        public static final String OUTPUT_PATH = inwardconfig.get("outputPath");
        public static final String MASTER_MODULE = inwardconfig.get("masterModule");
        public static final String MATERIAL_CODE = inwardconfig.get("materialCode");
        public static final String SUPPLIER = inwardconfig.get("supplier");
        public static final String MANUFACTURER = inwardconfig.get("manufacturer");
        public static final String QUANTITY_RECEIVED = inwardconfig.get("quantityReceived");
        public static final String PO_NUMBER = inwardconfig.get("poNumber");
        public static final String EDIT_PO_NUMBER = inwardconfig.get("poNumberEdit");
        public static final String INVOICE_NUMBER = inwardconfig.get("invoiceNumber");
        public static final String GATE_ENTRY_NUMBER = inwardconfig.get("gateEntryNumber");
        public static final String QUANTITY_RECEIVED_IN = inwardconfig.get("quantityReceivedIn");
        public static final String VEHICLE_NUMBER = inwardconfig.get("vehicleNumber");
        public static final String NUMBER_OF_BATCHES = inwardconfig.get("numberOfBatches");
        public static final String MFG_BATCH_NUMBER = inwardconfig.get("mfgBatchNo");
        public static final String BATCH_QUANTITY = inwardconfig.get("batchQuantity");
        public static final String NUMBER_OF_PACKS = inwardconfig.get("numberOfPacks");
        public static final String CLICK_MATERIAL_INWARD_VIEW = inwardconfig.get("clickMaterialInwardView");
        public static final String DOWNLOAD_MATERIALINWARD_PDF = inwardconfig.get("downloadMaterialInwardPDF");
        public static final String CAPTURE_MATERIAL_INWARD_PDF = inwardconfig.get("captureMaterialInwardPDF");
        public static final String CLICK_MATERIAL_INWARD_EDIT = inwardconfig.get("clickMaterialInwardEdit");
        public static final String DESCRIPTION_ANSWERS = inwardconfig.get("DescriptionAnswers");
        public static final String CHECKLIST_ANSWERS = inwardconfig.get("checklistAnswers");
        public static final String PRE_INSPECTION_RETURN = inwardconfig.get("preInspectionReturn");
        public static final String PRE_INSPECTION_EDIT_CHECKBOX_VALUES = inwardconfig
                        .get("preInspectionEditCheckboxesValues");
        public static final String PRE_INSPECTION_EDIT_DESCRIPTION_VALUES = inwardconfig
                        .get("preInspectionEditDescriptionValues");
        public static final String PRE_INSPECTION_EDIT_INDEXES = inwardconfig.get("preInspectionEditIndexes");
        public static final String PRE_INSPECTION_REJECT = inwardconfig.get("preInspectionReject");
        public static final String BATCH_NUMBERS_PACK_NUMBERS = inwardconfig.get("batchNumbers_packNumbers");
        public static final String BATCH_REJECTED_REASONS = inwardconfig.get("batchRejectedReasons");
        public static final String QA_FULL_REJECT_BATCHES = inwardconfig.get("qaFullRejectBatches");
        public static final String QA_FULL_REJECT_REASON = inwardconfig.get("qaFullRejectReason");
        public static final String QA_PARTIAL_REJECTION_DATA = inwardconfig.get("qaPartialRejectionData");
        public static final String VIEW_PRE_INSPECTION_REPORT = inwardconfig.get("viewPreInspectionReport");
        public static final String DOWNLOAD_PRE_INSPECTION_REPORT_PDF = inwardconfig
                        .get("downloadPreInspectionReportPdf");
        public static final String CAPTURE_PRE_INSPECTION_REPORT_PDF = inwardconfig
                        .get("capturePreInspectionReportPdf");
        public static final String QA_REVIEW_VIEW = inwardconfig.get("qaReviewView");
        public static final String REJECTED_BATCH_DETAILS_FLOW = inwardconfig.get("rejectedBatchDetailsFlow");
        public static final String REJECTED_BATCH_DETAILS_INVOICE_NUMBER = inwardconfig
                        .get("rejectedBatchDetailsInvoiceNumber");
        public static final String REJECTED_BATCH_DETAILS_REMARKS = inwardconfig.get("rejectedBatchDetailsRemarks");
        public static final String TABLE_HEADERS = inwardconfig.get("tableHeaders");
        public static final String TABLE_SEARCH_VALUES = inwardconfig.get("tableSearchValues");
        public static final String WEIGHT_VERIFICATION_BATCH = inwardconfig.get("weightVerificationBatch");
        public static final String WEIGHT_VERIFICATION_BATCH_SEGREGATED_PACK = inwardconfig
                        .get("weightVerificationBatchSegregatedPack");
        public static final String BALANCE_ID = inwardconfig.get("balanceId");
        public static final String WEIGHT_TYPE = inwardconfig.get("weightType");
        public static final String WEIGHT_AS_PER_LABEL = inwardconfig.get("weightAsPerLabel");
        public static final String ACTUAL_WEIGHT = inwardconfig.get("actualWeight");
        public static final String PACK_NUMBERS = inwardconfig.get("packNumbers");
        public static final String GROSS_WEIGHT = inwardconfig.get("grossWeight");
        public static final String TARE_WEIGHT = inwardconfig.get("tareWeight");

}
