package drivers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import drivers.AndroidDriverClass;
import io.appium.java_client.AppiumBy;

public class eCommerce_tc1 extends AndroidDriverClass {

    @Test
    public void filForm() {

        WebElement el1 = driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"com.androidsample.generalstore:id/nameField\")"));
        el1.sendKeys("Vasif Sultanov");
        WebElement el2 = driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/radioFemale"));
        el2.click();
        WebElement el3 = driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/spinnerCountry"));
        el3.click();
        //WebElement el4 = driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"Antarctica\")"));
        //el4.click();
        /*scrollToElementByText("Nigeria");*/
        scroll_JavascriptExecutor("down",50,200,200);

    }
}
