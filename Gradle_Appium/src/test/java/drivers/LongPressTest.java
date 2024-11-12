package drivers;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumBy;

public class LongPressTest extends AndroidDriverClass{

    @Test
    public void longClik() {
        WebElement el1 = driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"Views\")"));
        el1.click();
        WebElement el2 = driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"Expandable Lists\")"));
        el2.click();
        WebElement el3 = driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"1. Custom Adapter\")"));
        el3.click();
        WebElement el4 = driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"People Names\")"));
        longClick_JavascriptExecutor(el4,3000);
        WebElement el5 = driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"Sample menu\")"));
        String sampleMenuText = el5.getText();
        Assert.assertTrue( el5.isDisplayed(),"is not present");
        Assert.assertEquals(sampleMenuText, "Sample menu");
        System.out.println(sampleMenuText);


    }

}
