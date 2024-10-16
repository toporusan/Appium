package org.example;

import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        System.out.println("Capabilities: " + capabilities);
        System.out.println( "Hello World!" );
    }
    
}
