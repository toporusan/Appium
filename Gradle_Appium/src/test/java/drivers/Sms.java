package drivers;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import netscape.javascript.JSObject;

public class Sms {

    public AppiumDriverLocalService service; // appium драйвер
    public UiAutomator2Options options; // UiAutomator2Options конфигуратор
    public AndroidDriver driver; // AndroidDriver драйвер
    public URL urlAppium;

    String mainJsPath = "C:\\Users\\v.sultanov\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js";
    String deviceName = "R39M202T1ZP";


    String ipAddress = "127.0.0.1";
    int usingPort = 4723;

    @BeforeClass
    public void configureDriver() {
        service = new AppiumServiceBuilder().withAppiumJS(new File(mainJsPath))
                .withIPAddress(ipAddress)
                .usingPort(usingPort)
                .withArgument(() -> "--allow-cors")
                .withArgument(() -> "--log-level", "warn") // Устанавливаем уровень логирования на "warn" * сноски внизу
                .build();
        if (service.isRunning()) {
            service.stop();
        }

        service.start(); // Запускаем сервер Appium
        System.out.println("@BeforeClass: Начал работу appium server");
    }

    @BeforeMethod
    public void initDriver() {
        try {
            urlAppium = new URL("http://127.0.0.1:4723/");

            options = new UiAutomator2Options();
            options.setDeviceName(deviceName);
            options.setAutomationName("UiAutomator2");
            options.setNoReset(true);   // Сохранять данные приложения
            options.setFullReset(false); // Не сбрасывать приложение
            options.setAppPackage("com.android.mms");
            options.setAppActivity("com.android.mms.ui.SingleRecipientConversationActivity");


            driver = new AndroidDriver(urlAppium, options); // Инициализируем драйвер
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            //driver.rotate(ScreenOrientation.PORTRAIT);
            System.out.println("@BeforeMethod: Начал работу android driver UiAutomator2Options");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterMethod
    public void stopDriver() {
        if (driver != null) {
            //driver.rotate(ScreenOrientation.PORTRAIT);
            driver.quit(); // Завершение сессии драйвера
            System.out.println("@AfterMethod: Завершил работу android driver AndroidDriver");

        }
    }

    @AfterClass
    public void stopAppiumDriverLocalService() {
        if (service != null) {
            service.stop();
            System.out.println("@AfterClass: Завершил работу stopAppiumDriverLocalService +");
        }

    }

    @Test
    public void fetchSMS() {
        Object obj = ((JavascriptExecutor) driver).executeScript("mobile:listSms", ImmutableMap.of(
                "max", "4"));
        String json = obj.toString();
        /*System.out.println(json);*/

        String jsonString = json.replaceAll("[\\r\\n\\<\\#\\>\\*]+", " ").replace("\"", "\\\"");
        System.out.println(jsonString);

        Gson gson = new Gson();
        Response response = gson.fromJson(jsonString, Response.class);
        System.out.println(response.items.get(1).body);


    }
}
