package org.example;

import io.appium.java_client.AppiumDriver;
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
        options.setDeviceName("Android");
        options.

        AndroidDriver driver = new AndroidDriver(urlAppium, null);
        System.out.printf("");

    }
}
