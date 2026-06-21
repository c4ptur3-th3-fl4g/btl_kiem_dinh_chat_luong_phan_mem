package mobile_testing_app.tests;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import mobile_testing_app.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class TicketTest extends BaseTest {

    public TicketTest(AndroidDriver driver) {
        super(driver);
    }

    public void runTicketTests() {
        try {
            closeAd();
            goToHomePageMenu();
            closeAd();
            navigateToTicket();
            Thread.sleep(5000);
            gotoUnwatchMovie();
            Thread.sleep(5000);
            gotoWatchMovie();
        } catch (Exception e) {
            System.err.println("Error during ticket tests: " + e.getMessage());
        }
    }

    private void navigateToTicket() {
        WebElement ticketButton = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("com.cgv.cinema.vn:id/my_ticket"))
        );
        ticketButton.click();
        System.out.println("Navigated to 'My Ticket' section.");
    }

    private void gotoUnwatchMovie() {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("com.cgv.cinema.vn:id/empty_text")));
            System.out.println("No unwatched movie tickets found (empty text displayed).");
        } catch (Exception e) {
            System.out.println("Unwatched movie tickets are available.");
        }
    }

    private void gotoWatchMovie() {
        try {
            WebElement watchedTab = wait.until(
                    ExpectedConditions.elementToBeClickable(By.xpath("//android.widget.LinearLayout[@content-desc='Watched movies']"))
            );
            watchedTab.click();
            System.out.println("Switched to 'Watched movies' tab.");

            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("com.cgv.cinema.vn:id/empty_text")));
            System.out.println("No watched movie tickets found (empty text displayed).");
        } catch (Exception e) {
            System.out.println("Watched movie tickets are available.");
        }
    }
}