package drivers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.appium.java_client.AppiumBy;


public class SwipeDemo extends AndroidDriverClass {

    @Test
    public void swipeDemoTest() {
        WebElement el1 = driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"Views\")"));
        el1.click();
        WebElement el2 = driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"Gallery\")"));
        el2.click();
        WebElement el3 = driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"1. Photos\")"));
        el3.click();
        WebElement el4 = driver.findElement(By.xpath("//android.widget.ImageView[1]"));

        Assert.assertEquals(driver.findElement(By.xpath("//android.widget.ImageView[1]"))
                .getAttribute("focusable"), "true"
        );

        swipeToEllement(el4, "LEFT", 2000);

        Assert.assertEquals(driver.findElement(By.xpath("//android.widget.ImageView[1]"))
                .getAttribute("focusable"), "false"
        );

    }


}
