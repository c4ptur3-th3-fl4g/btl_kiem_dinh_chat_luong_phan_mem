package mobile_testing_app.tests;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import mobile_testing_app.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LogoutTest extends BaseTest {

    public LogoutTest(AndroidDriver driver) {
        super(driver);
    }

    public void runLogoutTests() {
        try {
            openMenu();
            testLogoutCancel();
            openMenu();
            testLogoutOk();
        } catch (Exception e) {
            System.err.println("Error during logout tests: " + e.getMessage());
        }
    }

    private void testLogoutCancel() {
        try {
            WebElement logoutBtn = wait.until(
                    ExpectedConditions.elementToBeClickable(By.xpath("//android.widget.TextView[@text='Log Out']"))
            );
            logoutBtn.click();
            WebElement cancelBtn = wait.until(
                    ExpectedConditions.elementToBeClickable(By.id("android:id/button2"))
            );
            cancelBtn.click();
            System.out.println("Tested logout with cancel.");
        } catch (Exception e) {
            System.err.println("Logout cancel test failed: " + e.getMessage());
        }
    }

    private void testLogoutOk() {
        try {
            WebElement logoutBtn = wait.until(
                    ExpectedConditions.elementToBeClickable(By.xpath("//android.widget.TextView[@text='Log Out']"))
            );
            logoutBtn.click();
            WebElement okBtn = wait.until(
                    ExpectedConditions.elementToBeClickable(By.id("android:id/button1"))
            );
            okBtn.click();
            System.out.println("Tested logout with OK.");
        } catch (Exception e) {
            System.err.println("Logout OK test failed: " + e.getMessage());
        }
    }
}