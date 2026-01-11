package testDocuments;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xwpf.usermodel.*;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pageObjects.LoginPage;
import testBase.BaseClass;
import utilities.ScreenshotUtil;
import utilities.WaitUtil;

public class TestingLoginPage extends BaseClass {
    LoginPage login;

    // Paths
    private static final String SCREENSHOT_TEMPLATE = "src/test/resources/MM Product Documents/Reference.docx";
    private static final String SCREENSHOT_OUTPUT   = "target/LoginScreenshot.docx";
    private static final String TESTSCRIPT_TEMPLATE = "src/test/resources/MM Product Documents/ReferenceTestScript.docx";
    private static final String TESTSCRIPT_OUTPUT   = "target/LoginTestScript.docx";

    @BeforeClass
    public void setUp() throws Exception {
        // Prepare screenshot doc
        ScreenshotUtil.loadTemplateForEndAppend(SCREENSHOT_TEMPLATE, SCREENSHOT_OUTPUT);

        // Prepare test script doc (copy reference → working copy)
        FileUtils.copyFile(new File(TESTSCRIPT_TEMPLATE), new File(TESTSCRIPT_OUTPUT));

        browserOpen();
        login = new LoginPage(driver);

        driver.get("https://www.google.co.in/");
        WaitUtil.waitForVisible(driver, login.getSearchBox(), 10);
        login.searchBox("http://localhost:8080/login");
        WaitUtil.waitForPageLoad(driver, 10);

        // Screenshot goes to screenshot doc
        ScreenshotUtil.takeStepScreenshot("Screenshot 4.1.1");

        driver.get("http://localhost:8080/login");
    }

    @Test
    public void updateMultipleSteps() throws Exception {
        Map<String, String[]> stepResults = new LinkedHashMap<>();
        stepResults.put("4.1.1", new String[]{"MM login page should appear", "Pass"});
        stepResults.put("4.1.2", new String[]{"User logged in successfully, Masters and MM modules displayed", "Pass"});
        stepResults.put("4.1.3", new String[]{"Masters page opened, menu items displayed correctly", "Fail"});

        updateExistingStepResults(TESTSCRIPT_OUTPUT, stepResults);
    }

    /**
     * Debug helper — prints what POI detects for each row.
     * Run this first to confirm Step IDs in your Word doc.
     */
    @Test
    public void debugStepIds() throws Exception {
        debugPrintDetectedStepIds(TESTSCRIPT_OUTPUT);
    }

    /* ----------------- Core Logic ----------------- */

    public void updateExistingStepResults(String filePath, Map<String, String[]> stepResults) throws Exception {
        boolean updatedAny = false;

        try (FileInputStream fis = new FileInputStream(filePath);
             XWPFDocument doc = new XWPFDocument(fis)) {

            int tableIndex = 0;
            for (XWPFTable table : doc.getTables()) {
                tableIndex++;
                String currentStepId = ""; // carry forward when merged cell empty
                int rowIndex = 0;

                for (XWPFTableRow row : table.getRows()) {
                    String rawStep = getCellTextSafe(row, 0);
                    String normalized = normalizeStepId(rawStep);

                    if (!normalized.isEmpty()) {
                        currentStepId = normalized;
                    }

                    if (currentStepId != null && !currentStepId.isEmpty() && stepResults.containsKey(currentStepId)) {
                        String actualResult = stepResults.get(currentStepId)[0];
                        String status       = stepResults.get(currentStepId)[1];

                        // Actual Results -> col 3
                        setCellTextSafe(row, 3, actualResult);

                        // Pass/Fail -> col 4
                        String pfValue;
                        if ("pass".equalsIgnoreCase(status)) {
                            pfValue = "☑Pass   □Fail";
                        } else if ("fail".equalsIgnoreCase(status)) {
                            pfValue = "□Pass   ☑Fail";
                        } else {
                            pfValue = "□Pass   □Fail";
                        }
                        setCellTextSafe(row, 4, pfValue);

                        System.out.println("[UPDATED] Step " + currentStepId + " -> " + status);
                        updatedAny = true;
                    }
                    rowIndex++;
                }
            }

            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                doc.write(fos);
            }
        }

        if (!updatedAny) {
            System.out.println("[WARN] No rows updated. Run debugStepIds() to check detected IDs.");
        } else {
            System.out.println("[OK] Updates applied to " + filePath);
        }
    }

    public void debugPrintDetectedStepIds(String filePath) throws Exception {
        try (FileInputStream fis = new FileInputStream(filePath);
             XWPFDocument doc = new XWPFDocument(fis)) {

            int tableNo = 0;
            for (XWPFTable table : doc.getTables()) {
                System.out.println("=== Table " + (++tableNo) + " ===");
                int rowIndex = 0;
                for (XWPFTableRow row : table.getRows()) {
                    String raw = getCellTextSafe(row, 0);
                    String normalized = normalizeStepId(raw);
                    String instr = getCellTextSafe(row, 1);
                    System.out.printf("Row %02d | raw:'%s' | normalized:'%s' | instr:'%s'%n",
                            rowIndex, raw, normalized, instr.length() > 30 ? instr.substring(0, 30) + "..." : instr);
                    rowIndex++;
                }
            }
        }
    }

    /* ----------------- Helpers ----------------- */

    private String getCellTextSafe(XWPFTableRow row, int cellIndex) {
        if (row == null) return "";
        if (row.getTableCells() == null || row.getTableCells().size() <= cellIndex) return "";
        XWPFTableCell cell = row.getCell(cellIndex);
        return cell == null ? "" : cell.getText();
    }

    private void setCellTextSafe(XWPFTableRow row, int cellIndex, String text) {
        while (row.getTableCells().size() <= cellIndex) {
            row.addNewTableCell();
        }
        XWPFTableCell cell = row.getCell(cellIndex);
        if (cell == null) {
            cell = row.addNewTableCell();
        }

        int paraCount = cell.getParagraphs().size();
        for (int i = paraCount - 1; i >= 0; i--) {
            try { cell.removeParagraph(i); } catch (Exception ignore) {}
        }

        XWPFParagraph p = cell.addParagraph();
        p.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun r = p.createRun();
        r.setText(text == null ? "" : text);
    }

    private String normalizeStepId(String s) {
        if (s == null) return "";
        s = s.replace('\u00A0', ' ')
             .replace("\r", " ")
             .replace("\n", " ")
             .replace("\t", " ")
             .trim();
        s = s.replaceAll("\\s+", " ");
        // Extract number pattern like 4.1.1
        Pattern p = Pattern.compile("([0-9]+(\\.[0-9]+)+)");
        Matcher m = p.matcher(s);
        if (m.find()) {
            return m.group(1);
        }
        String cleaned = s.replaceAll("[^0-9\\.]+", "");
        cleaned = cleaned.replaceAll("\\.+$", "");
        return cleaned;
    }
}
