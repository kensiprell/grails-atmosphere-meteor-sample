grails.servlet.version = "3.0"
grails.tomcat.nio = true // Modified by atmosphere-meteor plugin on Tue Oct 08 18:53:11 CEST 2013.
grails.project.dependency.resolver = "maven"
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.work.dir = "target/work"
grails.project.target.level = 1.6
grails.project.source.level = 1.6
// TODO comment before github push
grails.plugin.location.atmosphere_meteor = "/Users/Ken/Development/Plugins/grails-atmosphere-meteor"

grails.project.fork = [
		test: false,
		run: false,
		war: false,
		console: false
]

grails.project.dependency.resolution = {
	// http://search.maven.org/#browse%7C778853512
	def gebVersion = "0.9.1"
	// http://search.maven.org/#browse%7C-976095589
	def seleniumVersion = "2.35.0"
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
		compile('org.atmosphere:atmosphere-runtime:2.0.3') { // Modified by atmosphere-meteor plugin on Tue Oct 08 18:53:11 CEST 2013.
			excludes "slf4j-api"
		}
		compile 'org.codehaus.jackson:jackson-core-asl:1.9.13' // Modified by atmosphere-meteor plugin on Tue Oct 08 18:53:11 CEST 2013.
		test "org.gebish:geb-spock:$gebVersion"
		test "org.seleniumhq.selenium:selenium-chrome-driver:$seleniumVersion"
		test "org.seleniumhq.selenium:selenium-firefox-driver:$seleniumVersion"
		test "org.seleniumhq.selenium:selenium-support:$seleniumVersion"
		test "org.spockframework:spock-grails-support:0.7-groovy-2.0"
	}

	plugins {
		build ":tomcat:7.0.42"
		compile ":cache:1.1.1"
		// TODO update version and uncomment before github push
		//compile ":atmosphere-meteor:0.6.2"
		runtime ":hibernate:3.6.10.1"
		runtime ":jquery:1.10.2"
		runtime ":resources:1.2.1"
		runtime ":database-migration:1.3.6"
		test ":geb:$gebVersion"
		test ":spock:0.7"
	}
}
