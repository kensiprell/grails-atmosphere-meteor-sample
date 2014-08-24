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
	def gebVersion = "0.9.3"
	// http://search.maven.org/#browse%7C-976095589
	def seleniumVersion = "2.41.0"
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
		mavenRepo "http://oss.sonatype.org/content/repositories/snapshots"
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
		build ":tomcat:7.0.54"
		//compile ":tomcat8:8.0.1.1"

		compile ":asset-pipeline:1.9.7"
		compile ":atmosphere-meteor:1.0.0"
		compile ":cache:1.1.7"        // comment out for tomcat8 plugin

		runtime ":database-migration:1.4.0"
		runtime ":hibernate:3.6.10.17"
		runtime ":jquery:1.11.1"

		test ":geb:$gebVersion"
		test ":spock:0.7"
	}
}
