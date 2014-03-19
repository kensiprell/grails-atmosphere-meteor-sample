grails.servlet.version = "3.0"
grails.tomcat.nio = true
grails.project.dependency.resolver = "maven"
grails.project.work.dir = "target"
grails.project.target.level = 1.6
grails.project.source.level = 1.6

grails.project.fork = [
		test: false,
		run: false,
		war: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, forkReserve: false],
		console: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256]
]

grails.project.dependency.resolution = {
	// http://search.maven.org/#browse%7C778853512
	def gebVersion = "0.9.2"
	// http://search.maven.org/#browse%7C-976095589
	def seleniumVersion = "2.40.0"
	inherits "global"
	log "error"
	checksums true
	legacyResolve false

	repositories {
		grailsPlugins()
		grailsHome()
		mavenLocal()
		grailsCentral()
		mavenCentral()
	}

	dependencies {
		//build "org.apache.tomcat:tomcat-catalina-ant:8.0.1" // required for tomcat8 plugin

		test "org.gebish:geb-spock:$gebVersion"
		test "org.seleniumhq.selenium:selenium-chrome-driver:$seleniumVersion"
		test "org.seleniumhq.selenium:selenium-firefox-driver:$seleniumVersion"
		test "org.seleniumhq.selenium:selenium-support:$seleniumVersion"
		test "org.spockframework:spock-grails-support:0.7-groovy-2.0"
	}

	plugins {
		build ":tomcat:7.0.50"
		//build ":tomcat:7.0.52.1"
		//compile ":tomcat8:8.0.1.1"

		compile ":atmosphere-meteor:0.8.0"
		compile ":cache:1.1.1" // comment out for tomcat8 plugin
		compile ":scaffolding:2.0.3"
		//compile ":asset-pipeline:1.7.1"

		runtime ":database-migration:1.3.8"
		runtime ":hibernate:3.6.10.10"
		runtime ":jquery:1.11.0.1"
		runtime ":resources:1.2.7"

		test ":geb:$gebVersion"
		test ":spock:0.7"
	}
}
