package utilities;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import java.io.File;

public class PDFUtil {

    /**
     * Reusable method to open a downloaded PDF in a new tab, scroll through it,
     * take screenshots of all pages, and return to the parent window.
     * 
     * @param driver     The WebDriver instance
     * @param folderPath The path where the PDF is downloaded
     * @param fileName   The name of the PDF file (e.g., "Report.pdf")
     * @param stepLabel  The prefix label for the screenshots
     */
    public static void openAndCapturePDF(WebDriver driver, String folderPath, String fileName, String stepLabel)
            throws Exception {
        File pdf = new File(folderPath + File.separator + fileName);

        // 1. Wait until file exists (up to 10 seconds)
        int attempts = 0;
        while (!pdf.exists() && attempts < 20) {
            Thread.sleep(500);
            attempts++;
        }

        if (!pdf.exists()) {
            System.out.println("❌ PDF file not found after waiting: " + pdf.getAbsolutePath());
            return;
        }

        String parentWindow = driver.getWindowHandle();

        // 2. Security Bypass: Open blank tab first, then navigate to local file
        ((JavascriptExecutor) driver).executeScript("window.open('about:blank', '_blank');");

        // Switch focus to the new tab
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(parentWindow)) {
                driver.switchTo().window(handle);
                break;
            }
        }

        // Navigate to the PDF file
        driver.get(pdf.toURI().toString());
        Thread.sleep(5000); // Time for PDF viewer to initialize

        // 3. Ensure the PDF viewer has focus by clicking the center of the window
        org.openqa.selenium.Dimension size = driver.manage().window().getSize();
        new Actions(driver)
                .moveByOffset(size.getWidth() / 2, size.getHeight() / 2)
                .click()
                .perform();
        Thread.sleep(1000);

        // 4. Dynamic Scroll and Capture
        long previousScroll = -1;
        for (int i = 1; i <= 10; i++) {
            ScreenshotUtil.takeStepScreenshot(stepLabel + " - Page " + i);

            // Send Space key to scroll down
            new Actions(driver).sendKeys(Keys.SPACE).perform();
            Thread.sleep(3000); // Wait for rendering

            // Check current scroll position
            long currentScroll = ((Number) ((JavascriptExecutor) driver).executeScript(
                    "return window.pageYOffset || document.documentElement.scrollTop || 0;")).longValue();

            // Break if bottom reached (scroll position stops changing)
            if (currentScroll > 0 && currentScroll == previousScroll) {
                break;
            }

            // Fallback: stop if we can't read scroll but have done several pages
            if (currentScroll == 0 && i >= 4) {
                break;
            }

            previousScroll = currentScroll;
        }

        // 5. Cleanup: Close tab and return to parent
        driver.close();
        driver.switchTo().window(parentWindow);
    }
}
