package utilities;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.testng.annotations.DataProvider;

public class DataProviders {
	
	//Data provider 1
	@DataProvider(name = "DepartmentFullData")
	public Object[][] getDepartmentsSubset() throws Exception {
	    String filePath = System.getProperty("user.dir") + "\\src\\test\\resources\\TestData For Bulk\\MM.xlsx";
	    
		System.out.println("Excel path: " + filePath);
	    System.out.println("File exists: " + new File(filePath).exists());

	    // ExcelUtil reads Excel rows as Map<String, String>
	    List<Map<String, String>> rows = ExcelUtility.getRowsAsMap(filePath, "Department");

	    Object[][] data = new Object[rows.size()][1];
	    for (int i = 0; i < rows.size(); i++) {
	        data[i][0] = new departmentData(rows.get(i));
	    }

	    return data;
	}
	
	
	@DataProvider(name = "FacilityFullData")
	public Object[][] getFacilitiesSubset() throws Exception {
	    String filePath = System.getProperty("user.dir") + "\\src\\test\\resources\\TestData For Bulk\\MM.xlsx";
	    
		System.out.println("Excel path: " + filePath);
	    System.out.println("File exists: " + new File(filePath).exists());

	    // ExcelUtil reads Excel rows as Map<String, String>
	    List<Map<String, String>> rows = ExcelUtility.getRowsAsMap(filePath, "Facilities");

	    Object[][] data = new Object[rows.size()][1];
	    for (int i = 0; i < rows.size(); i++) {
	        data[i][0] = new facilityData(rows.get(i));
	    }

	    return data;
	}
	
	
	@DataProvider(name = "SecurityGroupFullData")
	public Object[][] getSecurityGroupSubset() throws Exception {
	    String filePath = System.getProperty("user.dir") + "\\src\\test\\resources\\TestData For Bulk\\MM.xlsx";
	    
		System.out.println("Excel path: " + filePath);
	    System.out.println("File exists: " + new File(filePath).exists());

	    // ExcelUtil reads Excel rows as Map<String, String>
	    List<Map<String, String>> rows = ExcelUtility.getRowsAsMap(filePath, "Security Group");

	    Object[][] data = new Object[rows.size()][1];
	    for (int i = 0; i < rows.size(); i++) {
	        data[i][0] = new securitygroupData(rows.get(i));
	    }

	    return data;
	}
	
	
	
	@DataProvider(name = "UserManagementFullData")
	public Object[][] getUserManagementSubset() throws Exception {
	    String filePath = System.getProperty("user.dir") + "\\src\\test\\resources\\TestData For Bulk\\MM.xlsx";
	    
		System.out.println("Excel path: " + filePath);
	    System.out.println("File exists: " + new File(filePath).exists());

	    // ExcelUtil reads Excel rows as Map<String, String>
	    List<Map<String, String>> rows = ExcelUtility.getRowsAsMap(filePath, "User Management");

	    Object[][] data = new Object[rows.size()][1];
	    for (int i = 0; i < rows.size(); i++) {
	        data[i][0] = new usermanagementData(rows.get(i));
	    }

	    return data;
	}
	
	@DataProvider(name = "SupplierData")
	public Object[][] getSupplierSubset() throws Exception {
	    String filePath = System.getProperty("user.dir") + "\\src\\test\\resources\\TestData For Bulk\\MM.xlsx";
	    
		System.out.println("Excel path: " + filePath);
	    System.out.println("File exists: " + new File(filePath).exists());

	    // ExcelUtil reads Excel rows as Map<String, String>
	    List<Map<String, String>> rows = ExcelUtility.getRowsAsMap(filePath, "Supplier");

	    Object[][] data = new Object[rows.size()][1];
	    for (int i = 0; i < rows.size(); i++) {
	        data[i][0] = new supplierData(rows.get(i));
	    }

	    return data;
	}

	
	
	@DataProvider(name = "MaterialspecificationData")
	public Object[][] getMaterialSpecificationSubset() throws Exception {
	    String filePath = System.getProperty("user.dir") + "\\src\\test\\resources\\OQ TestData\\OQ SpecData.xlsx";
	    
		System.out.println("Excel path: " + filePath);
	    System.out.println("File exists: " + new File(filePath).exists());

	    // ExcelUtil reads Excel rows as Map<String, String>
	    List<Map<String, String>> rows = ExcelUtility.getRowsAsMap(filePath, "MaterialSpecification");

	    Object[][] data = new Object[rows.size()][1];
	    for (int i = 0; i < rows.size(); i++) {
	        data[i][0] = new materialSpecificationData(rows.get(i));
	    }

	    return data;
	}
	
	@DataProvider(name = "ProductspecificationData")
	public Object[][] getProductSpecificationSubset() throws Exception {
	    String filePath = System.getProperty("user.dir") + "\\src\\test\\resources\\OQ TestData\\OQ SpecData.xlsx";
	    
		System.out.println("Excel path: " + filePath);
	    System.out.println("File exists: " + new File(filePath).exists());

	    // ExcelUtil reads Excel rows as Map<String, String>
	    List<Map<String, String>> rows = ExcelUtility.getRowsAsMap(filePath, "ProductSpecification");

	    Object[][] data = new Object[rows.size()][1];
	    for (int i = 0; i < rows.size(); i++) {
	        data[i][0] = new productSpecificationData(rows.get(i));
	    }

	    return data;
	}
	
	@DataProvider(name = "ChecklistData")
	public Object[][] getChecklistSubset() throws Exception {
	    String filePath = System.getProperty("user.dir") + "\\src\\test\\resources\\OQ TestData\\OQ SpecData.xlsx";
	    
		System.out.println("Excel path: " + filePath);
	    System.out.println("File exists: " + new File(filePath).exists());

	    // ExcelUtil reads Excel rows as Map<String, String>
	    List<Map<String, String>> rows = ExcelUtility.getRowsAsMap(filePath, "Checklist");

	    Object[][] data = new Object[rows.size()][1];
	    for (int i = 0; i < rows.size(); i++) {
	        data[i][0] = new checklistData(rows.get(i));
	    }

	    return data;
	}
}
