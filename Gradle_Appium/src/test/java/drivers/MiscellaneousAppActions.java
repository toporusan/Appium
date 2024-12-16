package drivers;

import com.google.common.collect.ImmutableMap;

import org.openqa.selenium.DeviceRotation;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.Activity;

public class MiscellaneousAppActions extends AndroidDriverClass {

    @Test
    public void miscellaneousAppActions() {

        // начал работу с конкретного экрана
        startActivity("io.appium.android.apis","io.appium.android.apis.preference.PreferenceDependencies");
        WebElement el6 = driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"android:id/checkbox\")"));
        el6.click();
        screenOrientation(ScreenOrient.LANDSCAPE2);
        WebElement el7 = driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.RelativeLayout\").instance(1)"));
        el7.click();
        WebElement el8 = driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"android:id/edit\")"));
        el8.sendKeys("Faat");
        screenOrientation(ScreenOrient.PORTRAIT1);
        el6.click();
        el6.click();







    }
}
