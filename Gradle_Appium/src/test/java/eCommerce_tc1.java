import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import drivers.AndroidDriverClass;
import io.appium.java_client.AppiumBy;

public class eCommerce_tc1 extends AndroidDriverClass {

    @Test
    public void fiiForm() {

        WebElement el1 = driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"com.androidsample.generalstore:id/nameField\")"));
        el1.sendKeys("Vasif mmdmdmdmdmdmddm");
        driver.hideKeyboard();
        driver.longPressKey();


    }
}
