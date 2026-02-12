package utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSVUtility {

    /**
     * Reads a CSV file and returns rows matching the module name.
     * Simplification: Uses a basic comma split.
     * Note: Avoid using commas INSIDE your cell values with this simple version.
     */
    public static List<Map<String, String>> getRowsByModule(String filePath, String moduleName) {
        List<Map<String, String>> data = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // 1. Read the first line to get Headers
            String firstLine = br.readLine();
            if (firstLine == null)
                return data;
            String[] headers = firstLine.split(",");

            // 2. Read each data line
            String line;
            while ((line = br.readLine()) != null) {
                // Split line by comma
                String[] values = line.split(",");

                // 3. If the first column matches our module (e.g., "Department")
                if (values.length > 0 && values[0].trim().equalsIgnoreCase(moduleName)) {
                    Map<String, String> row = new HashMap<>();

                    for (int i = 0; i < headers.length; i++) {
                        String cellValue = (i < values.length) ? values[i].trim() : "";
                        // Remove quotes if present
                        cellValue = cellValue.replace("\"", "");
                        row.put(headers[i].trim(), cellValue);
                    }
                    data.add(row);
                }
            }
        } catch (Exception e) {
            System.err.println("Error reading CSV: " + e.getMessage());
        }
        return data;
    }

    /**
     * Gets the expected actions for a given module and status from
     * ActionValidation.csv
     * Returns a list of action names that should be available (marked as "Yes")
     * 
     * @param filePath   Path to ActionValidation.csv
     * @param moduleName Module name (e.g., "Department", "Facility")
     * @param status     Status of the record (e.g., "Draft", "Active", "Approved")
     * @return List of expected action names (e.g., ["View", "Edit", "Delete"])
     */
    public static List<String> getExpectedActionsByStatus(String filePath, String moduleName, String status) {
        List<String> expectedActions = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // 1. Read headers
            String firstLine = br.readLine();
            if (firstLine == null)
                return expectedActions;
            String[] headers = firstLine.split(",");

            // 2. Find the matching row
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                // Check if this row matches our module and status
                if (values.length >= 2 &&
                        values[0].trim().equalsIgnoreCase(moduleName) &&
                        values[1].trim().equalsIgnoreCase(status)) {

                    // 3. Collect all actions marked as "Yes"
                    for (int i = 2; i < headers.length && i < values.length; i++) {
                        String actionName = headers[i].trim();
                        String actionValue = values[i].trim();

                        if (actionValue.equalsIgnoreCase("Yes")) {
                            expectedActions.add(actionName);
                        }
                    }
                    break; // Found the matching row, no need to continue
                }
            }
        } catch (Exception e) {
            System.err.println("Error reading ActionValidation CSV: " + e.getMessage());
        }
        return expectedActions;
    }

    public static String[] splitAndTrim(String commaSeparated) {
        if (commaSeparated == null || commaSeparated.isEmpty())
            return new String[0];
        String[] parts = commaSeparated.split(",");
        for (int i = 0; i < parts.length; i++)
            parts[i] = parts[i].trim();
        return parts;
    }
}
