import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import drivers.AndroidDriverClass;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLOutput;

public class Test extends AndroidDriverClass {


    @org.testng.annotations.Test(priority = 1)
    public void appiumTest() {

        WebElement el1 = driver.findElement(AppiumBy.accessibilityId("Preference"));
        el1.click();
        WebElement el2 = driver.findElement(AppiumBy.accessibilityId("9. Switch"));
        el2.click();
        WebElement el3 = driver.findElement(AppiumBy.id("android:id/checkbox"));
        el3.click();
        WebElement el4 = driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"android:id/switch_widget\").instance(0)"));
        el4.click();
        WebElement el5 = driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"android:id/switch_widget\").instance(1)"));
        el5.click();

    }

    @org.testng.annotations.Test(priority = 2)
    public void appiumTest2() {

        WebElement el1 = driver.findElement(AppiumBy.accessibilityId("Preference"));
        el1.click();
        WebElement el2 = driver.findElement(AppiumBy.accessibilityId("9. Switch"));
        el2.click();
        WebElement el3 = driver.findElement(AppiumBy.id("android:id/checkbox"));
        el3.click();
        WebElement el4 = driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"android:id/switch_widget\").instance(0)"));
        el4.click();
        WebElement el5 = driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"android:id/switch_widget\").instance(1)"));
        el5.click();

    }

    @org.testng.annotations.Test(priority = 2)
    public void wifiSettingsName() {
        var el1 = driver.findElement(AppiumBy.accessibilityId("Graphics"));
        el1.click();
        var el2 = driver.findElement(AppiumBy.accessibilityId("CameraPreview"));
        el2.click();
        var el3 = driver.findElement(AppiumBy.accessibilityId("NFC"));
        el3.click();
        var el4 = driver.findElement(AppiumBy.accessibilityId("ForegroundDispatch"));
        el4.click();
        var el5 = driver.findElement(AppiumBy.id("android:id/aerr_close"));
        el5.click();
    }


}