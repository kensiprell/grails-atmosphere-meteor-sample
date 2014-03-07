grails.servlet.version = "3.0"
grails.tomcat.nio = true
grails.project.dependency.resolver = "maven"
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.work.dir = "target/work"
grails.project.target.level = 1.6
grails.project.source.level = 1.6

grails.project.fork = [
		// configure settings for compilation JVM, note that if you alter the Groovy version forked compilation is required
		//  compile: [maxMemory: 256, minMemory: 64, debug: false, maxPerm: 256, daemon:true],

		// configure settings for the test-app JVM, uses the daemon by default
		//test: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, daemon: true],
		test: false,
		// configure settings for the run-app JVM
		//run: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, forkReserve:false],
		run: false,
		// configure settings for the run-war JVM
		war: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, forkReserve: false],
		// configure settings for the Console UI JVM
		console: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256]
]

grails.project.dependency.resolution = {
	// http://search.maven.org/#browse%7C778853512
	def gebVersion = "0.9.2"
	// http://search.maven.org/#browse%7C-976095589
	def seleniumVersion = "2.39.0"
	inherits("global") {
	}
	log "error"
	checksums true
	legacyResolve false

	repositories {
		inherits true
		grailsPlugins()
		grailsHome()
		mavenLocal()
		grailsCentral()
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
		//build ":tomcat:7.0.52.1"
		build ":tomcat:7.0.50"

		// TODO update version
		compile ":atmosphere-meteor:0.7.1"
		compile ":cache:1.1.1"
		compile ":scaffolding:2.0.2"
		compile ":asset-pipeline:1.6.2"

		runtime ":database-migration:1.3.8"
		runtime ":hibernate:3.6.10.9"
		runtime ":jquery:1.11.0.1"
		//runtime ":resources:1.2.7"

		test ":geb:$gebVersion"
		test ":spock:0.7"
	}
}
