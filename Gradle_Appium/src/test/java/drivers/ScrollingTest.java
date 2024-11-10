package drivers;


import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import io.appium.java_client.AppiumBy;

public class ScrollingTest extends AndroidDriverClass{

    @Test
    public void scrolling() {
        WebElement el1 = driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"Views\")"));
        el1.click();

        WebElement el2 = scrollToElementByText("WebView");
        el2.click();
        //el2.click();

    }
    
}
