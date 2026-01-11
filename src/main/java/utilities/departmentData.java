package utilities;
import java.util.Map;

public class departmentData {

    private String deptName;
    private String desc;
    
    private String statusaftercreate;
    private String statusafterapp;
    private String statusafteredit;
    private String approvals;
    private String status;
    private String editDept;
    private String editDesc;
    private String beforeappeditdept;
    private String beforeappeditdesc;
    private String statusbeforeappedit;

    // Constructor accepts a row as a Map
    public departmentData(Map<String, String> row) {
        try {
            this.deptName = getValue(row, "Department Name");
            this.desc = getValue(row, "Description");
            this.statusaftercreate = getValue(row, "Status After Create");
            this.beforeappeditdept = getValue(row, "Before Approve Edit Dept");
            this.beforeappeditdesc = getValue(row, "Before Approve Edit Desc");
            this.statusbeforeappedit = getValue(row, "Status BeforeApprove Edit");
            this.approvals = getValue(row, "Approvals(Accept/Return)");
            this.statusafterapp = getValue(row, "Status After Approval");
            this.editDept = getValue(row, "Edit Dept");
            this.editDesc = getValue(row, "Edit Desc");
            this.statusafteredit = getValue(row, "Status After Edit");
            this.status = getValue(row, "Final Status");
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
    public String getDeptName() { return deptName; }
    public String getDesc() { return desc; }
 
    public String getStatus_AfterCreate() { return statusaftercreate; }
    public String getStatus_AfterApp() { return statusafterapp; }
    public String getStatus_AfterEdit() { return statusafteredit; }
    public String getApprovals() { return approvals; }
    public String getStatus() { return status; }
    public String get_BeforeAppEditDept() { return beforeappeditdept; }
    public String get_BeforeAppEditDesc() { return beforeappeditdesc; }
    public String getStatus_BeforeAppEdit() { return statusbeforeappedit; }
    public String getEditDept() { return editDept; }
    public String getEditDesc() { return editDesc; }
}
