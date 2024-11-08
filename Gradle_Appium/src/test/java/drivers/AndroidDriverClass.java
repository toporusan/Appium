package drivers;

import com.google.common.collect.ImmutableMap;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;


import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import io.appium.java_client.AppiumBy;
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
    String mainJsPathWindows = "C:\\Users\\sulta\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js";
    String deviceName = "Pixel_3a_API_35_extension_level_13_x86_64";
    String setApp = "D:\\androidLessons\\Appium\\ApiDemos-debug.apk";

    // Work
    //String mainJsPathWindows = "C:\\Users\\v.sultanov\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js";
    //String deviceName = "Pixel_3a_API_35_extension_level_13_x86_64";
    //String setApp = "C:\\Users\\v.sultanov\\0_D_Disk\\Projects\\Appium\\ApiDemos-debug.apk";

    String ipAddress = "127.0.0.1";
    int usingPort = 4723;

    @BeforeClass
    public void configureDriver() {
        service = new AppiumServiceBuilder().withAppiumJS(new File(mainJsPathWindows))
                .withIPAddress(ipAddress)
                .usingPort(usingPort)
                .withArgument(() -> "--log-level", "warn") // Устанавливаем уровень логирования на "warn"
                .build();
        if (service.isRunning()) {
            service.stop();
        }
        service.start(); // Запускаем сервер Appium
        System.out.println("@BeforeClass: Начал работу appium server");
    }

    /*
       --log-level" — это аргумент командной строки, который задает уровень логирования
    при запуске Appium сервера.
    Возможные значения для этого аргумента:
    "debug" — выводит все сообщения (включая отладочные).
    "info" — выводит общую информацию (по умолчанию).
    "warn" — выводит только предупреждения и ошибки.
    "error" — выводит только ошибки.

    "warn" — это значение аргумента, которое определяет,
    что Appium будет выводить только предупреждения и ошибки в
    консоль, минимизируя вывод лишней информации (например, успешные загрузки драйверов).
    */

    @BeforeMethod
    public void initDriver() {
        try {
            urlAppium = new URL("http://127.0.0.1:4723");
            options = new UiAutomator2Options();
            options.setDeviceName(deviceName);
            options.setApp(setApp);

            driver = new AndroidDriver(urlAppium, options); // Инициализируем драйвер
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
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
            System.out.println("Завершил работу stopAppiumDriverLocalService +");
        }
    }

    // ПОЛЬЗОВАТЕЛЬСКИЕ ЖЕСТЫ

    /*
    JavascriptExecutor — это интерфейс в WebDriver, который предоставляет возможность выполнять
    произвольный JavaScript код внутри браузера (или мобильного приложения, если используется в Appium).
    Этот интерфейс расширяет функциональность WebDriver, позволяя делать такие операции, которые не
    поддерживаются стандартными методами WebDriver.

    Основные методы интерфейса JavascriptExecutor:

    1. executeScript(String script, Object... args):
    Это основной метод интерфейса JavascriptExecutor.
    Он выполняет синхронный JavaScript код и возвращает результат выполнения скрипта.

    2. executeAsyncScript(String script, Object... args):
    Этот метод выполняет асинхронный JavaScript код. Он полезен, когда нужно дождаться завершения
     асинхронной операции, такой как загрузка данных, выполнение AJAX-запросов или других асинхронных
     задач.
    */

    // Долгий клик(зажатие)
    public void longClickAction_JavascriptExecutor(WebElement element, int duration) {
        ((JavascriptExecutor) driver).executeScript("mobile: longClickGesture", ImmutableMap.of(
                "elementId", ((RemoteWebElement) element).getId(),
                "duration", duration
        ));

    }

    // Скролл посредством androidUIAutomator метод UiScrollable
    public void scrollToElement(WebElement element) {
        String text = element.getText(); // Получаем текст из элемента
        // Формируем строку для прокрутки до элемента с нужным текстом
        String uiScrollableString = String.format("new UiScrollable(new UiSelector()).scrollIntoView(text(\"%s\"))", text);
        // Прокручиваем и находим элемент
        driver.findElement(AppiumBy.androidUIAutomator(uiScrollableString));
    }

    // Скролл посредством JavascriptExecutor
    public void scroll_JavascriptExecutor(String direction, double percent, int left, int top) {
        Dimension screenSize = driver.manage().window().getSize(); // для авто вычисления длины и ширины
        int screenWidth = screenSize.getWidth();
        int screenHeight = screenSize.getHeight();

        boolean canScrollMore = (Boolean) ((JavascriptExecutor) driver)
                .executeScript("mobile: scrollGesture", ImmutableMap.of(
                "left", left, "top", top, "width", screenWidth, "height", screenHeight / 2,
                "direction", direction,
                "percent", percent
        ));
    }



    public WebElement scrollToElementByText(String text) {
        // Формируем строку для прокрутки до элемента с заданным текстом
        String uiScrollableString = String.format("new UiScrollable(new UiSelector()).scrollIntoView(text(\"%s\"))", text);

        // Прокручиваем экран до нужного элемента
        driver.findElement(AppiumBy.androidUIAutomator(uiScrollableString));

        // Теперь ждем, пока элемент появится на экране
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.androidUIAutomator("new UiSelector().text(\"" + text + "\")")));
    }

}


