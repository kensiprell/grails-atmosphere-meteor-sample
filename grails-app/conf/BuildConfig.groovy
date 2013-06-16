grails.servlet.version = "3.0" // Modified by atmosphere-meteor plugin on Mon Jun 10 21:21:24 CEST 2013. Previous version was 2.5.
grails.tomcat.nio = true // Added by atmosphere-meteor plugin on Mon Jun 10 21:21:24 CEST 2013.
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
	log "error" // log level of Ivy resolver, either "error", "warn", "info", "debug" or "verbose"
	checksums true // Whether to verify checksums on resolve

	repositories {
		inherits true // Whether to inherit repository definitions from plugins
		grailsPlugins()
		grailsHome()
		grailsCentral()
		mavenLocal()
		mavenCentral()
	}

	dependencies {
		// TODO update version
		compile("org.atmosphere:atmosphere-runtime:1.1.0.RC4") {  // Added by atmosphere-meteor plugin on Mon Jun 10 21:21:24 CEST 2013.
			excludes "slf4j-api", "atmosphere-ping"
		}
		compile "org.codehaus.jackson:jackson-core-asl:1.1.1" // Added by atmosphere-meteor plugin on Mon Jun 10 21:21:24 CEST 2013.
		test "org.gebish:geb-spock:$gebVersion"
		test "org.seleniumhq.selenium:selenium-chrome-driver:$seleniumVersion"
		test "org.seleniumhq.selenium:selenium-firefox-driver:$seleniumVersion"
		test "org.seleniumhq.selenium:selenium-support:$seleniumVersion"
		test "org.spockframework:spock-grails-support:0.7-groovy-2.0"
	}

	plugins {
		build ":tomcat:$grailsVersion"
		compile ":cache:1.0.1"
		// TODO update version and uncoment before github push
		compile ":atmosphere-meteor:0.5.0"
		runtime ":hibernate:$grailsVersion"
		runtime ":jquery:1.10.0"
		runtime ":resources:1.2"
		runtime ":database-migration:1.3.3"
		test ":geb:$gebVersion"
		test ":spock:0.7"
	}
}
