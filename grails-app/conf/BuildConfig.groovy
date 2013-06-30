grails.servlet.version = "3.0"
grails.tomcat.nio = true
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.target.level = 1.6
grails.project.source.level = 1.6
// TODO comment before github push
//grails.plugin.location.atmosphere_meteor = "/Users/Ken/Development/Plugins/grails-atmosphere-meteor"

grails.project.dependency.resolution = {
	// http://search.maven.org/#browse%7C778853512
	def gebVersion = "0.9.0"
	// http://search.maven.org/#browse%7C-976095589
	def seleniumVersion = "2.33.0"
	inherits("global") {
	}
	log "error"
	checksums true

	repositories {
		inherits true
		grailsPlugins()
		grailsHome()
		grailsCentral()
		mavenLocal()
		mavenCentral()
	}

	dependencies {
		test "org.gebish:geb-spock:$gebVersion"
		test "org.seleniumhq.selenium:selenium-chrome-driver:$seleniumVersion"
		test "org.seleniumhq.selenium:selenium-firefox-driver:$seleniumVersion"
		test "org.seleniumhq.selenium:selenium-support:$seleniumVersion"
		test "org.spockframework:spock-grails-support:0.7-groovy-2.0"
	}

	plugins {
		build ":tomcat:$grailsVersion"
		compile ":cache:1.0.1"
		// TODO update version and uncomment before github push
		compile ":atmosphere-meteor:0.5.4"
		runtime ":hibernate:$grailsVersion"
		runtime ":jquery:1.10.0"
		runtime ":resources:1.2"
		runtime ":database-migration:1.3.3"
		test ":geb:$gebVersion"
		test ":spock:0.7"
	}
}
