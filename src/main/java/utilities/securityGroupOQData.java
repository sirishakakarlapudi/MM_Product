package utilities;
import java.util.Map;

public class securityGroupOQData {
	private String sgName;
	private String sgdesc;
	private String module;

    
    private String createdprivileges;
    
    private String updateprivileges;
   
  

    // Constructor accepts a row as a Map
    public securityGroupOQData(Map<String, String> row) {
        try {
        	this.sgName = getValue(row, "Security Group Name");
        	this.sgdesc = getValue(row, "Description");
			this.module = getValue(row, "Module");
           
            this.createdprivileges = getValue(row, "Privileges");
            this.updateprivileges = getValue(row, "Update Privileges");
            
           
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
    public String getSgName() {
		return sgName;
	}
    public String getSgDesc() {
		return sgdesc;
	}

	public String getModule() {
		return module;
	}

    public String get_Privileges() { return createdprivileges; }
    public String get_Update_Privileges() { return updateprivileges; }
  
  
}
