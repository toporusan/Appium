package drivers;

import org.openqa.selenium.DeviceRotation;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumBy;

public class MiscellaneousAppActions extends AndroidDriverClass {

    @Test
    public void miscellaneousAppActions() {
        WebElement el4 = driver.findElement(AppiumBy.accessibilityId("Preference"));
        el4.click();
        WebElement el5 = driver.findElement(AppiumBy.accessibilityId("3. Preference dependencies"));
        el5.click();
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
