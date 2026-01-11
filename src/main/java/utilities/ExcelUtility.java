package utilities;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility {

    private static String path;
    
    public static FileInputStream fi;
	public static XSSFWorkbook workbook;
	public static XSSFSheet sheet;
	public static XSSFRow row;
	public static XSSFCell cell;
	public FileOutputStream fo;

    
    // Existing methods: getRowCount, getCellCount, getCellData ...

   
    
    
    public static int getRowCount(String path, String sheetName) throws IOException {
		fi= new FileInputStream(path);
		workbook = new XSSFWorkbook(fi);
		sheet= workbook.getSheet(sheetName);
		int rowCount=sheet.getLastRowNum();
		workbook.close();
		fi.close();
		return rowCount;
		
	}
	
	public static int getCellCount(String path, String sheetName, int rowNum) throws IOException {
		fi= new FileInputStream(path);
		workbook= new XSSFWorkbook(fi);
		sheet =workbook.getSheet(sheetName);
		row=sheet.getRow(rowNum);
		int cellCount=row.getLastCellNum();
		workbook.close();
		fi.close();
		return cellCount;
		
	}
	/*public static String getCellData(String path, String sheetName, int rowNum, int cellNum) throws IOException {
		fi=new FileInputStream(path);
		workbook= new XSSFWorkbook(fi);
		sheet= workbook.getSheet(sheetName);
		row=sheet.getRow(rowNum);
		cell=row.getCell(cellNum);
		DataFormatter formatter= new DataFormatter();
		String data;
		try {
			//data=cell.toString();    //method one to get data from the cell
			data = formatter.formatCellValue(cell); //return the formatted values of a cell as a string regardless of the cell type
		}
		catch(Exception e) {
			data="";  // if there is not data in the cell it may throw an error message No data found instead of error message we are asking throwing data value as"" 
			
		}
		workbook.close();
		fi.close();
		return data;
		
	}*/
	public static String getCellData(String path, String sheetName, int rowNum, int cellNum) throws IOException {

	    fi = new FileInputStream(path);
	    workbook = new XSSFWorkbook(fi);
	    sheet = workbook.getSheet(sheetName);

	    // ✅ Sheet check
	    if (sheet == null) {
	        workbook.close();
	        fi.close();
	        return "";
	    }

	    row = sheet.getRow(rowNum);

	    // ✅ Row check (THIS WAS MISSING)
	    if (row == null) {
	        workbook.close();
	        fi.close();
	        return "";
	    }

	    cell = row.getCell(cellNum);

	    // ✅ Cell check
	    if (cell == null) {
	        workbook.close();
	        fi.close();
	        return "";
	    }

	    DataFormatter formatter = new DataFormatter();
	    String data;

	    try {
	        data = formatter.formatCellValue(cell);
	    } catch (Exception e) {
	        data = "";
	    }

	    workbook.close();
	    fi.close();
	    return data;
	}

	public void setCellData(String path, String sheetName, int rowNum, int cellNum, String data) throws IOException {
		File xlfile= new File(path);
		if (!xlfile.exists()) {    //if file not exists then create new file
			workbook= new XSSFWorkbook();
			fo= new FileOutputStream(path);
			workbook.write(fo);
		}
		fi =new FileInputStream(path);
		workbook= new XSSFWorkbook(fi);
		if(workbook.getSheetIndex(sheetName)==-1)   //if sheet not exists then create new sheet
			workbook.createSheet(sheetName);
		sheet=workbook.getSheet(sheetName);
		if (sheet.getRow(rowNum)==null)
			sheet.createRow(rowNum);
		row=sheet.getRow(rowNum);
		cell=row.createCell(cellNum);
		cell.setCellValue(data);
		fo= new FileOutputStream(path);
		workbook.write(fo);
		workbook.close();
		fi.close();
		fo.close();
			
			
		}
	/*public static List<Map<String, String>> getRowsAsMap(String filePath, String sheetName) throws Exception {
	    int totalRows = getRowCount(filePath, sheetName);
	    int totalCols = getCellCount(filePath, sheetName, 0); // assuming first row has headers

	    // Read headers (trimmed)
	    String[] headers = new String[totalCols];
	    for (int j = 0; j < totalCols; j++) {
	        headers[j] = getCellData(filePath, sheetName, 0, j).trim();
	    }

	    List<Map<String, String>> rowsList = new ArrayList<>();

	    // Read data rows
	    for (int i = 1; i <= totalRows; i++) { // row 1 is headers
	        Map<String, String> rowMap = new LinkedHashMap<>();
	        for (int j = 0; j < totalCols; j++) {
	            String header = headers[j].trim();
	            String value = getCellData(filePath,sheetName, i, j);
	            rowMap.put(header, value == null ? "" : value.trim());
	        }
	        rowsList.add(rowMap);
	    }

	    return rowsList;
	}*/
	public static List<Map<String, String>> getRowsAsMap(String filePath, String sheetName) throws Exception {

	    int totalRows = getRowCount(filePath, sheetName);
	    int totalCols = getCellCount(filePath, sheetName, 0); // header row

	    // Read headers
	    String[] headers = new String[totalCols];
	    for (int j = 0; j < totalCols; j++) {
	        headers[j] = getCellData(filePath, sheetName, 0, j).trim();
	    }

	    List<Map<String, String>> rowsList = new ArrayList<>();

	    // Read data rows
	    for (int i = 1; i <= totalRows; i++) {

	        // ✅ Skip completely empty rows (VERY IMPORTANT)
	        String firstCell = getCellData(filePath, sheetName, i, 0);
	        if (firstCell == null || firstCell.trim().isEmpty()) {
	            continue;
	        }

	        Map<String, String> rowMap = new LinkedHashMap<>();

	        for (int j = 0; j < totalCols; j++) {
	            String header = headers[j];
	            String value = getCellData(filePath, sheetName, i, j);
	            rowMap.put(header, value == null ? "" : value.trim());
	        }

	        rowsList.add(rowMap);
	    }

	    return rowsList;
	}


}
