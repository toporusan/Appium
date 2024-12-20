package drivers;

import com.google.common.collect.ImmutableMap;

import org.openqa.selenium.DeviceRotation;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.w3c.dom.events.EventListener;


import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

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

    enum ScreenOrient {
        LANDSCAPE1, LANDSCAPE2, PORTRAIT1, PORTRAIT2
    }


    // Home
    //String mainJsPath = "C:\\Users\\sulta\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js";
    //String deviceName = "emulator-5554";
    //String setApp = "D:\\androidLessons\\Appium\\ApiDemos-debug.apk";
    //String setApp = "D:\\androidLessons\\Appium\\ApiDemos-debug.apk";


    // Mac
    // String mainJsPath = "/opt/homebrew/lib/node_modules/appium/build/lib/main.js";
    // String deviceName = "emulator-5554";
    // String setApp = "/Users/Toporusan/Projects/Appium/ApiDemos-debug.apk";

    // Work
    String mainJsPath = "C:\\Users\\v.sultanov\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js";
    String deviceName = "R39M202T1ZP";
    String setApp = "C:\\Users\\v.sultanov\\0_D_Disk\\Projects\\Appium\\Anorbank.apk";


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
            options.setApp(setApp);
            options.setAutomationName("UiAutomator2");
            options.setNoReset(true);   // Сохранять данные приложения
            options.setFullReset(false); // Не сбрасывать приложение

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

    // ПОЛЬЗОВАТЕЛЬСКИЕ ЖЕСТЫ **

    // Долгий клик(зажатие)
    public void longClick_JavascriptExecutor(WebElement element, int duration) {
        ((JavascriptExecutor) driver).executeScript("mobile: longClickGesture", ImmutableMap.of(
                "elementId", ((RemoteWebElement) element).getId(),
                "duration", duration
        ));
    }


    // Скролл до конкретного элемента, посредством androidUIAutomator метод UiScrollable
    public WebElement scrollToElementByText(String text) {
        // Формируем строку для прокрутки до элемента с заданным текстом
        String uiScrollableString = String.format("new UiScrollable(new UiSelector()).scrollIntoView(text(\"%s\"))", text);
        // Прокручиваем экран до нужного элемента
        driver.findElement(AppiumBy.androidUIAutomator(uiScrollableString));
        // Теперь ждем, пока элемент появится на экране
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.androidUIAutomator("new UiSelector().text(\"" + text + "\")")));
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

    // Найти координаты элемента и его длину/ширину
    public void findElementCoordination(WebElement element) {
        // Получить координаты
        Point location = element.getLocation();
        int x = location.getX();
        int y = location.getY();

        // Получить размеры элемента
        Dimension size = element.getSize();
        int width = size.getWidth();
        int height = size.getHeight();

        // Использовать эти данные для свайпа
        System.out.println("Coordinate: " + "X: " + x + " Y: " + y);
        System.out.println("Width: " + width + " Height: " + height);
    }

    // Свайп посредством JavascriptExecutor
    public void swipeToEllement(WebElement element, String direction, int speed) {
        ((JavascriptExecutor) driver).executeScript("mobile: swipeGesture", ImmutableMap.of(
                "elementId", ((RemoteWebElement) element).getId(),
                "direction", direction,
                "percent", 0.75,
                "speed", speed
        ));

    }

    // Свайп посредством JavascriptExecutor / много свайпов по заданным координатам
    public void swipe_JavascriptExecutor(WebElement element, String direction, int swipeCount, int speed) {
        Point location = element.getLocation();
        int x = location.getX();
        int y = location.getY();

        // Получить размеры элемента
        Dimension size = element.getSize();
        int width = size.getWidth();
        int height = size.getHeight();

        int swipes = 0;
        do {
            ((JavascriptExecutor) driver).executeScript("mobile: swipeGesture", ImmutableMap.of(
                    "left", x,               // Начало области по X
                    "top", y,               // Начало области по Y
                    "width", width,             // Ширина области
                    "height", height,            // Высота области
                    "direction", direction,      // Направление свайпа (влево)
                    "percent", 0.75,          // Процент от области
                    "speed", speed             // Скорость свайпа
            ));

            swipes++;
        } while (swipes < swipeCount - 1);

    }

    public void dragDrop_JavascriptExecutor(WebElement element, int x, int y) {
        ((JavascriptExecutor) driver).executeScript("mobile: dragGesture", ImmutableMap.of(
                "elementId", ((RemoteWebElement) element).getId(),
                "speed", 3500, // можно опционалоно
                "endX", x,
                "endY", y
        ));
    }


    public void screenOrientation(ScreenOrient s) {
        switch (s) {
            case LANDSCAPE1 -> {
                driver.rotate(ScreenOrientation.LANDSCAPE);
            }
            case LANDSCAPE2 -> {
                driver.rotate(new DeviceRotation(0, 0, 90));
            }
            case PORTRAIT1 -> {
                driver.rotate(ScreenOrientation.PORTRAIT);
            }
            case PORTRAIT2 -> {
                driver.rotate(new DeviceRotation(0, 0, 0));
            }
        }

    }

    // Старт с конкретной активити , осткрыть сразу нужный экран напрямую

     /*
    Команда для терминала(command line) для определения activityName и package ,
    необходимо навестись на конкретный экран, а затем ввести команду:

    -для mac/linux
    adb shell dumpsys window | grep -E 'mCurrentFocus'
    -для windows
    adb shell dumpsys window | find "mCurrentFocus"
     */

    /* не получится использовать т.к. данный метод deprecated
    Activity activity = new Activity();
    driver.startActivity(activity);
    */

    public void startActivity(String packageName, String activityName) {
        ((JavascriptExecutor) driver).executeScript("mobile: startActivity",
                ImmutableMap.of("intent", packageName + "/" + activityName));
    }


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
