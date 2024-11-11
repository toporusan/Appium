package drivers;


import com.google.common.collect.ImmutableMap;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumBy;

public class SwipeDemo extends AndroidDriverClass {

    @Test
    public void swipeDemoTest() {
        WebElement el1 = driver.findElement(
                AppiumBy.androidUIAutomator("new UiSelector().text(\"Views\")")
        );
        el1.click();
        WebElement el2 = driver.findElement(
                AppiumBy.androidUIAutomator("new UiSelector().text(\"Gallery\")")
        );
        el2.click();
        WebElement el3 = driver.findElement(
                AppiumBy.androidUIAutomator("new UiSelector().text(\"1. Photos\")")
        );
        el3.click();
        WebElement el4 = driver.findElement(
                By.xpath("//android.widget.ImageView[1]")
        );

        Assert.assertEquals(driver.findElement(
                        By.xpath("//android.widget.ImageView[1]"))
                .getAttribute("focusable"), "true"
        );

        ((JavascriptExecutor) driver).executeScript("mobile: swipeGesture", ImmutableMap.of(
                "elementId", ((RemoteWebElement) el4).getId(),
                "left", 100, "top", 100, "width", 200, "height", 200,
                "direction", "left",
                "percent", 0.75,
                "speed", 2000
        ));


// Получить координаты
        Point location = el4.getLocation();
        int x = location.getX();
        int y = location.getY();

// Получить размеры элемента
        Dimension size = el4.getSize();
        int width = size.getWidth();
        int height = size.getHeight();

// Использовать эти данные для свайпа
        System.out.println("X: " + x + " Y: " + y);
        System.out.println("Width: " + width + " Height: " + height);


        for (int i = 0; i < 5; i++) {
            ((JavascriptExecutor) driver).executeScript("mobile: swipeGesture", ImmutableMap.of(
                    "left", 34,               // Начало области по X
                    "top", 220,               // Начало области по Y
                    "width", 374,             // Ширина области
                    "height", 242,            // Высота области
                    "direction", "left",      // Направление свайпа (влево)
                    "percent", 0.75,          // Процент от области
                    "speed", 2000             // Скорость свайпа
            ));
        }


        Assert.assertEquals(driver.findElement(
                        By.xpath("//android.widget.ImageView[1]"))
                .getAttribute("focusable"), "false"
        );

    }

}
