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
	def gebVersion = "0.10.0"
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
		test "org.gebish:geb-spock:$gebVersion"
		test "org.seleniumhq.selenium:selenium-chrome-driver:$seleniumVersion"
		test "org.seleniumhq.selenium:selenium-firefox-driver:$seleniumVersion"
		test "org.seleniumhq.selenium:selenium-support:$seleniumVersion"
		test "org.spockframework:spock-grails-support:0.7-groovy-2.0"
	}

	plugins {
		build ":tomcat:8.0.22"

		compile ":asset-pipeline:2.2.3"
		compile ":atmosphere-meteor:1.0.4"
		compile ":cache:1.1.8"        

		runtime ":database-migration:1.4.0"
		runtime ":hibernate:3.6.10.18"
		runtime ":jquery:1.11.1"

		test ":geb:$gebVersion"
		test ":spock:0.7"
	}
}
