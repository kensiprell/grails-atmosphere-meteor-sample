// http://www.gebish.org/manual/current/configuration.html
// http://code.google.com/p/selenium/wiki/FirefoxDriver

import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.chrome.ChromeDriver

// grails test-app functional:
driver = { new FirefoxDriver() }

environments {
	// grails -Dgeb.env=chrome test-app functional:
	// http://code.google.com/p/selenium/wiki/ChromeDriver
	chrome {
		System.setProperty("webdriver.chrome.driver","/Users/Ken/Library/selenium-drivers/chromedriver")
		driver = { new ChromeDriver() }
	}
}
