import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class AppiumBasics {

    @Test
    public void appiumTest() {

        URL urlAppium = null;
        try {
            urlAppium = new URL("http://127.0.0.1:4723");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName("Pixel_3a_API_35_extension_level_13_x86_64");
        //options.setDeviceName("6P6L8LW4SWXOWWJ7");
        options.setApp("C:\\Users\\v.sultanov\\0_D_Disk\\Projects\\Appium\\src\\test\\java\\resources\\ApiDemos-debug.apk");

        AndroidDriver driver = new AndroidDriver(urlAppium, options);


    }
}