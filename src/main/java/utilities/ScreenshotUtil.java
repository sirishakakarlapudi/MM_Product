package utilities;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.poi.util.Units;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ScreenshotUtil {
    private static final Logger log = LogManager.getLogger(ScreenshotUtil.class);

    // === Keep shared state as static ===
    private static XWPFDocument templateDoc;
    private static XWPFDocument outputDoc;
    private static String outputPath;

    private static class ScreenshotEntry {
        String label;
        byte[] imageBytes;

        ScreenshotEntry(String label, byte[] imageBytes) {
            this.label = label;
            this.imageBytes = imageBytes;
        }
    }

    private static final List<ScreenshotEntry> screenshots = new ArrayList<>();
    private static final List<IBodyElement> templateBodyElements = new ArrayList<>();

    // === Dynamic Numbering State ===
    private static String currentScriptId = "4.1";
    private static String currentStepId = "4.1.1";
    private static int stepCounter = 0;
    private static int screenshotCount = 1;
    private static boolean isEnabled = true; // Default to true

    public static void setIsEnabled(boolean enabled) {
        isEnabled = enabled;
        log.info("📸 Screenshot capture is now: " + (enabled ? "ENABLED" : "DISABLED"));
    }

    /**
     * Initializes the Script prefix (e.g., "4.1") and resets the step counter.
     */
    public static void initScript(String prefix) {
        currentScriptId = prefix.trim();
        stepCounter = 0;
    }

    /**
     * Automatically increments to the next step (e.g., 4.1.1 -> 4.1.2)
     * and resets the screenshot counter to 01.
     */
    public static void nextStep() {
        stepCounter++;
        currentStepId = currentScriptId + "." + stepCounter;
        screenshotCount = 1;
        log.info("📍 Current Step set to: " + currentStepId);
    }

    /**
     * Manual override to jump to a specific step number or ID if needed
     */
    public static void setStepId(String stepId) {
        currentStepId = stepId;
        // If it's a standard format like "4.1.5", try to sync the counter
        try {
            String[] parts = stepId.split("\\.");
            if (parts.length >= 3) {
                stepCounter = Integer.parseInt(parts[parts.length - 1]);
            }
        } catch (Exception e) {
            // Non-standard ID, just keep the counter where it is
        }
        screenshotCount = 1;
    }

    /**
     * Captures screenshot with automatic numbering: "XX for step No.Y.Y.Y"
     */
    public static void capture() throws Exception {
        if (!isEnabled)
            return;
        String label = String.format("%02d for step No.%s", screenshotCount++, currentStepId);
        takeStepScreenshot(label);
    }

    /**
     * Captures screenshot with automatic numbering and a suffix (e.g., " - Page 1")
     */
    public static void capture(String suffix) throws Exception {
        if (!isEnabled)
            return;
        String label = String.format("%02d for step No.%s%s", screenshotCount++, currentStepId, suffix);
        takeStepScreenshot(label);
    }

    /**
     * Manual label override if absolutely necessary
     */
    public static void takeCustomScreenshot(String customLabel) throws Exception {
        takeStepScreenshot(customLabel);
    }

    // === Load Template and Prepare Output ===
    public static void loadTemplateForEndAppend(String templatePath, String outputPath) throws Exception {
        FileInputStream fis = new FileInputStream(templatePath);
        templateDoc = new XWPFDocument(fis);
        fis.close();

        ScreenshotUtil.outputPath = outputPath;

        // ✅ Clone full structure: header, footer, margins
        outputDoc = new XWPFDocument(templateDoc.getPackage());

        // ✅ Store original body content for re-insertion
        templateBodyElements.clear();
        templateBodyElements.addAll(templateDoc.getBodyElements());

        // ✅ Clear body (screenshots will be added first)
        while (outputDoc.getBodyElements().size() > 0) {
            outputDoc.removeBodyElement(0);
        }
    }

    // === Update header text ===
    public static void updateHeaderCellText(String searchText, String newText) {
        try {
            XWPFHeaderFooterPolicy policy = outputDoc.getHeaderFooterPolicy();
            if (policy == null) {
                System.err.println("❌ No header/footer policy found in outputDoc.");
                return;
            }

            XWPFHeader header = policy.getDefaultHeader();
            if (header == null) {
                log.warn("❌ No default header found in outputDoc.");
                return;
            }

            for (XWPFTable table : header.getTables()) {
                for (XWPFTableRow row : table.getRows()) {
                    for (XWPFTableCell cell : row.getTableCells()) {
                        String cellText = cell.getText();
                        if (cellText != null && cellText.trim().equalsIgnoreCase(searchText.trim())) {
                            // Clear all paragraphs from cell
                            while (cell.getParagraphs().size() > 0) {
                                cell.removeParagraph(0);
                            }

                            // Add new content
                            XWPFParagraph newPara = cell.addParagraph();
                            XWPFRun run = newPara.createRun();
                            run.setText(newText);
                            run.setFontFamily("Times New Roman");
                            run.setFontSize(12);
                            run.setBold(true);
                            System.out.println("✅ Header cell updated: " + searchText + " → " + newText);
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("❌ Exception while updating header: " + e.getMessage());
        }
    }

    // === Capture Screenshot ===
    public static void takeStepScreenshot(String label) throws Exception {
        if (!isEnabled)
            return;

        // ⏳ Small delay to ensure UI is settled and loading is truly finished
        Thread.sleep(1000);

        Robot robot = new Robot();
        Rectangle screen = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        BufferedImage image = robot.createScreenCapture(screen);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        screenshots.add(new ScreenshotEntry(label, baos.toByteArray()));
        baos.close();

    }

    // === Save Final Document ===
    public static void insertScreenshotsAndAppendTemplate() throws Exception {
        if (!isEnabled) {
            log.info("⏭️ Screenshot generation skipped (DISABLED).");
            return;
        }
        // 1. Insert screenshots first
        for (ScreenshotEntry entry : screenshots) {
            XWPFParagraph para = outputDoc.createParagraph();
            XWPFRun run = para.createRun();
            run.setBold(true);
            run.setFontFamily("Times New Roman");
            run.setFontSize(12);
            run.setText("Screenshot - " + entry.label);

            ByteArrayInputStream bais = new ByteArrayInputStream(entry.imageBytes);
            run.addPicture(bais, Document.PICTURE_TYPE_PNG, entry.label + ".png", Units.toEMU(650), Units.toEMU(360));
            bais.close();
        }

        // 2. Append original template content
        for (IBodyElement element : templateBodyElements) {
            if (element instanceof XWPFParagraph) {
                XWPFParagraph oldPara = (XWPFParagraph) element;
                XWPFParagraph newPara = outputDoc.createParagraph();
                newPara.getCTP().set(oldPara.getCTP().copy());
            } else if (element instanceof XWPFTable) {
                XWPFTable oldTable = (XWPFTable) element;
                XWPFTable newTable = outputDoc.createTable();
                newTable.getCTTbl().set(oldTable.getCTTbl().copy());
            }
        }

        // 3. Write to disk
        FileOutputStream fos = new FileOutputStream(outputPath);
        outputDoc.write(fos);
        fos.close();
        outputDoc.close();
        templateDoc.close();

        log.info("✅ Document saved: " + outputPath);
    }
}
