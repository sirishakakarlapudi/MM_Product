package utilities;
import java.util.Map;

public class checklistData {

    
    private String description;
    
    private String reqSub;
   
    private String button;
  

    // Constructor accepts a row as a Map
    public checklistData(Map<String, String> row) {
        try {
           
            this.description = getValue(row, "Description");
            this.reqSub = getValue(row, "Req Sub");
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
    
    public String get_Description() { return description; }
    public String get_ReqSub() { return reqSub; }
    public String get_Buttons() { return button; }
  
}
