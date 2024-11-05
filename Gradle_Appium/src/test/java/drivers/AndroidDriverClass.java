package drivers;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;


import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class AndroidDriverClass {

    public AppiumDriverLocalService service; // appium драйвер
    public UiAutomator2Options options; // UiAutomator2Options конфигуратор
    public AndroidDriver driver; // AndroidDriver драйвер
    public URL urlAppium;

    // Home
    /*String mainJsPathWindows = "C:\\Users\\sulta\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js";
    String deviceName = "Pixel_3a_API_35_extension_level_13_x86_64";
    String setApp = "D:\\androidLessons\\Appium\\ApiDemos-debug.apk";*/

    // Work
    String mainJsPathWindows = "C:\\Users\\v.sultanov\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js";
    String deviceName = "Pixel_3a_API_35_extension_level_13_x86_64";
    String setApp = "C:\\Users\\v.sultanov\\0_D_Disk\\Projects\\Appium\\ApiDemos-debug.apk";

    String ipAddress = "127.0.0.1";
    int usingPort = 4723;

    @BeforeClass
    public void configureDriver() {
        service = new AppiumServiceBuilder().withAppiumJS(new File(mainJsPathWindows))
                .withIPAddress(ipAddress).usingPort(usingPort).build();
        if (service.isRunning()) {
            service.stop();
        }
        service.start(); // Запускаем сервер Appium
        System.out.println("@BeforeClass: Начал работу appium server");
    }

    @BeforeMethod
    public void initDriver() {
        try {
            urlAppium = new URL("http://127.0.0.1:4723");
            options = new UiAutomator2Options();
            options.setDeviceName(deviceName);
            options.setApp(setApp);

            driver = new AndroidDriver(urlAppium, options); // Инициализируем драйвер
            System.out.println("@BeforeMethod: Начал работу android driver UiAutomator2Options");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterMethod
    public void stopDriver() {
        if (driver != null) {
            driver.quit(); // Завершение сессии драйвера
            System.out.println("Завершил работу android driver AndroidDriver");
        }
    }

    @AfterClass
    public void stopAppiumDriverLocalService() {
        if (service != null) {
            service.stop();
            System.out.println("Завершил работу stopAppiumDriverLocalService");
        }
    }
}


