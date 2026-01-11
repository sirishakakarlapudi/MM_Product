package utilities;
import java.util.Map;

public class productSpecificationData {

    
    private String nameofthetest;
    
    private String reqSub;
    private String specificationLimit;
    private String validation;
    private String specificationLimitMin;
    private String specificationLimitMax;
    private String UOM;
    private String button;
  

    // Constructor accepts a row as a Map
    public productSpecificationData(Map<String, String> row) {
        try {
           
            this.nameofthetest = getValue(row, "Name Of The Test");
            this.reqSub = getValue(row, "Req Sub");
            this.specificationLimit = getValue(row, "Specification Limit");
            this.validation = getValue(row, "Validation");
            this.specificationLimitMin = getValue(row, "Specification Limit Min");
            this.specificationLimitMax = getValue(row, "Specification Limit Max");
            this.UOM = getValue(row, "UOM");
            this.button = getValue(row, "Buttons");
           
        } catch (Exception e) {
            throw new IllegalArgumentException("Excel row data is incomplete or missing headers", e);
        }
        System.out.println("Available keys from Excel row: " + row.keySet());
    }

    private String getValue(Map<String, String> row, String key) {
        for (String k : row.keySet()) {
            if (k.trim().equalsIgnoreCase(key.trim())) {
                String value = row.get(k);
                return value == null ? "" : value.trim();
            }
        }
        return "";
    }
    // Getters
    
    public String get_NameOfTheTest() { return nameofthetest; }
 
    public String get_ReqSub() { return reqSub; }
    public String get_SpecificationLimit() { return specificationLimit; }
    public String get_Validation() { return validation; }
    public String get_SpecificationLimitMin() { return specificationLimitMin; }
    public String get_SpecificationLimitMax() { return specificationLimitMax; }
    public String get_UOM() { return UOM; }
    public String get_Buttons() { return button; }
  
}
