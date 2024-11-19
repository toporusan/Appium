package drivers;

import com.google.common.collect.ImmutableMap;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumBy;

public class DragDropDemo extends AndroidDriverClass {

    @Test
    public void dragDropTest() {

        WebElement el1 = driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"Views\")"));
        el1.click();
        WebElement el2= driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"Drag and Drop\")"));
        el2.click();
        System.out.printf("Draggggg");

        WebElement circle1 = driver.findElement(By.id("io.appium.android.apis:id/drag_dot_1"));
        WebElement circle2 = driver.findElement(By.id("io.appium.android.apis:id/drag_dot_2"));
        WebElement circle3 = driver.findElement(By.id("io.appium.android.apis:id/drag_dot_3"));

        findElementCoordination(circle2);
        System.out.printf("sdfsdfsdfsdfsdfs******************");

/*        ((JavascriptExecutor) driver).executeScript("mobile: dragGesture", ImmutableMap.of(
                "elementId", ((RemoteWebElement) circle1).getId(),
                "endX", 100,
                "endY", 100
        ));*/

    }
}
