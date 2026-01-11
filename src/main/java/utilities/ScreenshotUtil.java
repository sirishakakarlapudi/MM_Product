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

public class ScreenshotUtil {

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
                System.err.println("❌ No default header found in outputDoc.");
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
            System.err.println("❌ Exception while updating header: " + e.getMessage());
        }
    }

    // === Capture Screenshot ===
    public static void takeStepScreenshot(String label) throws Exception {
        //Thread.sleep(3000); // Optional wait
        Robot robot = new Robot();
        Rectangle screen = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        BufferedImage image = robot.createScreenCapture(screen);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        screenshots.add(new ScreenshotEntry(label, baos.toByteArray()));
        baos.close();
        Thread.sleep(2000);
    }

    // === Save Final Document ===
    public static void insertScreenshotsAndAppendTemplate() throws Exception {
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

        System.out.println("✅ Document saved: " + outputPath);
    }
}
