package mobile_testing_app.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import mobile_testing_app.BaseTest;
import mobile_testing_app.utils.AppiumUtils;

public class RegisterTest extends BaseTest {

    public RegisterTest(AndroidDriver driver) {
        super(driver);
    }

    public void runRegisterTests() {
        try {
//            closeAd();
//            openMenu();
//            navigateToLogin1();
//            navigateToLogin2();
            fillRegistrationDetails();
            submitRegistration();
            verifyRegistrationSuccess();
        } catch (Exception e) {
            System.err.println("Error during register tests: " + e.getMessage());
        }
    }

    private void navigateToLogin1() {
        try {
            WebElement userName = wait.until(ExpectedConditions.elementToBeClickable(By.id("com.cgv.cinema.vn:id/user_name")));
            userName.click();
            System.out.println("Navigated to register screen.");
        } catch (Exception e) {
            System.err.println("Failed to navigate to login screen: " + e.getMessage());
        }
    }

    private void navigateToLogin2() {
        try {
            WebElement userName = wait.until(ExpectedConditions.elementToBeClickable(By.id("com.cgv.cinema.vn:id/btn_sign_up")));
            userName.click();
            System.out.println("Navigated to register screen.");
        } catch (Exception e) {
            System.err.println("Failed to navigate to login screen: " + e.getMessage());
        }
    }

    private void fillRegistrationDetails() {
        driver.findElement(By.id("com.cgv.cinema.vn:id/edt_name")).sendKeys("Nguyen Van A");
        driver.findElement(By.id("com.cgv.cinema.vn:id/edt_phone_number")).sendKeys("0123456789");
        driver.findElement(By.id("com.cgv.cinema.vn:id/edt_email")).sendKeys("test@example.com");
        driver.findElement(By.id("com.cgv.cinema.vn:id/edt_password")).sendKeys("Abc123!@#");
//        selectDateOfBirth();
        selectGender();
        selectCity();
        selectDistrict();
        selectPreferredTheater();
        driver.findElement(By.id("com.cgv.cinema.vn:id/edt_referral_code")).sendKeys("REF123");
    }

//    private void selectDateOfBirth() {
//        driver.findElement(By.id("com.cgv.cinema.vn:id/auto_dob")).click();
//        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("android:id/date_picker")));
//
//        // Chọn ngày
//        scrollAndSelectDate("//android.widget.NumberPicker[1]//android.widget.EditText", "01");
//        // Chọn tháng
//        scrollAndSelectDate("//android.widget.NumberPicker[2]//android.widget.EditText", "Jan");
//        // Chọn năm
//        scrollAndSelectDate("//android.widget.NumberPicker[3]//android.widget.EditText", "1990");
//
//        driver.findElement(By.id("android:id/button1")).click();
//    }

    private void selectGender() {
        driver.findElement(By.id("com.cgv.cinema.vn:id/auto_gender")).click();
        driver.findElement(By.xpath("//android.widget.CheckedTextView[@resource-id='com.cgv.cinema.vn:id/text1' and @text='Male']")).click();
    }

    private void selectCity() {
        driver.findElement(By.id("com.cgv.cinema.vn:id/auto_city")).click();
        scrollWithUiScrollable("Other");
    }

    private void selectDistrict() {
        driver.findElement(By.id("com.cgv.cinema.vn:id/auto_district")).click();
        scrollWithUiScrollable("Other");
    }

    private void selectPreferredTheater() {
        driver.findElement(By.id("com.cgv.cinema.vn:id/auto_prefer_theater")).click();
        scrollWithUiScrollable("CGV Tràng Tiền Plaza");
    }

    private void submitRegistration() {
        driver.findElement(By.id("com.cgv.cinema.vn:id/signup_text_1")).click();
        driver.findElement(By.id("com.cgv.cinema.vn:id/signup_text_2")).click();
        driver.findElement(By.id("com.cgv.cinema.vn:id/signup_text_3")).click();
        driver.findElement(By.id("com.cgv.cinema.vn:id/signup_text_4")).click();
        driver.findElement(By.id("com.cgv.cinema.vn:id/btn_register")).click();
    }

    private void verifyRegistrationSuccess() {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("com.cgv.cinema.vn:id/msg_register_success")));
        String message = driver.findElement(By.id("com.cgv.cinema.vn:id/msg_register_success")).getText();
        System.out.println("Thông báo đăng ký: " + message);
    }

    private void scrollWithUiScrollable(String targetText) {
        boolean found = false;
        int maxScrollAttempts = 10; // Giới hạn số lần cuộn để tránh vòng lặp vô hạn
        int attempts = 0;

        while (!found && attempts < maxScrollAttempts) {
            try {
                WebElement element = driver.findElement(AppiumBy.androidUIAutomator(
                        "new UiScrollable(new UiSelector().scrollable(true).instance(0))" +
                                ".setAsVerticalList()" +
                                ".scrollIntoView(new UiSelector().text(\"" + targetText + "\"))"
                ));
                if (element.isDisplayed()) {  // Kiểm tra nếu phần tử xuất hiện
                    element.click();
                    found = true;
                    System.out.println("Tìm thấy và chọn phần tử: " + targetText);
                }
            } catch (Exception e) {
                System.out.println("Không tìm thấy phần tử: " + targetText + ", thử cuộn tiếp...");
                swipeDown();  // Nếu không tìm thấy, vuốt xuống để tải thêm dữ liệu
                attempts++;
            }
        }

        if (!found) {
            System.err.println("Không thể tìm thấy phần tử sau khi cuộn: " + targetText);
        }
    }

    private void swipeDown() {
        AppiumUtils.scrollUsingCoordinatesDown(driver);
    }



    private void scrollAndSelectDate(String pickerXpath, String value) {
        boolean found = false;
        while (!found) {
            try {
                WebElement picker = driver.findElement(By.xpath(pickerXpath));
                if (picker.getText().equals(value)) {
                    picker.click();
                    found = true;
                } else {
                    AppiumUtils.scrollUsingCoordinatesDown(driver);
                }
            } catch (Exception e) {
                System.err.println("Failed to select date: " + e.getMessage());
            }
        }
    }
}
