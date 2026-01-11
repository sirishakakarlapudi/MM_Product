
package utilities;

public class Capture {
    public static void step(String step) throws Exception {
        ScreenshotUtil.takeStepScreenshot("Screenshot " + step);
    }
}
