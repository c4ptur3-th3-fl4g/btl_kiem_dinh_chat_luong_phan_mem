package mobile_testing_app;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

import java.net.URI;
import java.time.Duration;

public class Main {
    private static final String CGV_PACKAGE = "com.cgv.cinema.vn";

    public static void main(String[] args) {
        AndroidDriver driver = null;
        try {
            UiAutomator2Options options = new UiAutomator2Options()
                    .setPlatformName("Android")
                    .setPlatformVersion(config("ANDROID_VERSION", "15"))
                    .setDeviceName(config("ANDROID_DEVICE_NAME", "Android 15 device"))
                    .setAutomationName("UiAutomator2")
                    .setAppPackage(CGV_PACKAGE)
                    // CGV 2.10.12 (targetSdk 35) starts through SplashScreen.
                    .setAppActivity("com.cgv.cinema.vn.ui.SplashScreen")
                    .setAppWaitActivity("com.cgv.cinema.vn.*")
                    .setAutoGrantPermissions(true)
                    .setNoReset(Boolean.parseBoolean(config("APPIUM_NO_RESET", "true")))
                    .setNewCommandTimeout(Duration.ofSeconds(120));

            String udid = config("ANDROID_UDID", "");
            if (!udid.isBlank()) {
                options.setUdid(udid);
            }

            URI serverUri = URI.create(config("APPIUM_SERVER_URL", "http://127.0.0.1:4723"));
            var server = serverUri.toURL();
            driver = new AndroidDriver(server, options);
            new Assignment(driver).runTest();
        } catch (Exception e) {
            System.err.println("Appium initialization/test error: " + e.getMessage());
            e.printStackTrace(System.err);
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }

    private static String config(String name, String defaultValue) {
        String systemValue = System.getProperty(name);
        if (systemValue != null && !systemValue.isBlank()) {
            return systemValue;
        }
        String environmentValue = System.getenv(name);
        return environmentValue == null || environmentValue.isBlank() ? defaultValue : environmentValue;
    }
}
