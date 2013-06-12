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
	inherits("global") {
	}
	log "error" // log level of Ivy resolver, either "error", "warn", "info", "debug" or "verbose"
	checksums true // Whether to verify checksums on resolve
	legacyResolve false // whether to do a secondary resolve on plugin installation, not advised and here for backwards compatibility

	repositories {
		inherits true // Whether to inherit repository definitions from plugins
		grailsPlugins()
		grailsHome()
		grailsCentral()
		mavenLocal()
		mavenCentral()
	}

	dependencies {
		compile("org.atmosphere:atmosphere-runtime:1.1.0.RC3") {  // Added by atmosphere-meteor plugin on Mon Jun 10 21:21:24 CEST 2013.
			excludes "slf4j-api", "atmosphere-ping"
		}
		compile "org.codehaus.jackson:jackson-core-asl:1.1.1" // Added by atmosphere-meteor plugin on Mon Jun 10 21:21:24 CEST 2013.
	}

	plugins {
		build ":tomcat:$grailsVersion"
		compile ":cache:1.0.1"
		// TODO uncoment before github push
		compile ":atmosphere-meteor:0.4.5"
		runtime ":hibernate:$grailsVersion"
		runtime ":jquery:1.10.0"
		runtime ":resources:1.2"
		runtime ":database-migration:1.3.3"
	}
}
