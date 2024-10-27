import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLOutput;

public class Test {

    AppiumDriverLocalService service;
    UiAutomator2Options options;
    AndroidDriver driver;

    URL urlAppium;
    //String mainJsPathWindows = "C:\\Users\\v.sultanov\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js";
    String mainJsPathWindows = "C:\\Users\\sulta\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js";
    String ipAddress = "127.0.0.1";
    int usingPort = 4723;
    //String deviceName = "Pixel_3a_API_35_extension_level_13_x86_64";
    //String setApp = "C:\\Users\\v.sultanov\\0_D_Disk\\Projects\\Appium\\ApiDemos-debug.apk";
    String deviceName = "Pixel_3a_API_35_extension_level_13_x86_64";
    String setApp = "D:\\androidLessons\\Appium\\ApiDemos-debug.apk";


    @BeforeClass
    public void beforeClass() {
        service = new AppiumServiceBuilder().withAppiumJS(new File(mainJsPathWindows))
                .withIPAddress(ipAddress).usingPort(usingPort).build();

        if (service.isRunning()) {
            service.stop();
        }
        //service.stop();
        service.start(); // Запускаем сервер Appium
        System.out.println("@BeforeClass: Начал работу appium server");

    }

    @BeforeSuite
    public void testConfigurations() {
        try {
            urlAppium = new URL("http://127.0.0.1:4723");
            System.out.println(urlAppium);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        options = new UiAutomator2Options();
        options.setDeviceName(deviceName);
        options.setApp(setApp);

        System.out.println("@BeforeSuite: Начал работу android driver UiAutomator2Options");

    }

    @org.testng.annotations.Test
    public void appiumTest() {
        driver = new AndroidDriver(urlAppium, options);
        System.out.println(" TEST 1 ------------>>>>> Ваши тесты...");

    }

    @AfterSuite
    public void afterSuite() {
        if (service != null) {
            service.stop();
        }
        System.out.println("@AfterSuite: Завершил работу appium server");
    }

    @AfterClass
    public void afterClass() {

        if (driver != null) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            driver.quit();
            System.out.println("@AfterClass: Завершил работу android driver UiAutomator2Options");
        }
    }
}