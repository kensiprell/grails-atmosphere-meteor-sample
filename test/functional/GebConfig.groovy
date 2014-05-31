// http://www.gebish.org/manual/current/configuration.html
// http://code.google.com/p/selenium/wiki/FirefoxDriver

import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.firefox.FirefoxDriver

// grails test-app functional:
driver = { new ChromeDriver() }

environments {
	// grails -Dgeb.env=chrome test-app functional:
	chrome {
		driver = { new ChromeDriver() }
	}
	// grails -Dgeb.env=firefox test-app functional:
	firefox {
		driver = { new FirefoxDriver() }
	}
}
