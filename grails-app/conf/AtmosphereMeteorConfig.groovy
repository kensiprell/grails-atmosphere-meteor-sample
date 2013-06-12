import org.grails.plugins.atmosphere_meteor_sample.DefaultMeteorHandler

defaultMapping = "/jabber/*"

servlets = [
	MeteorServlet: [
		className: "org.grails.plugins.atmosphere_meteor_sample.DefaultMeteorServlet",
		mapping: "/jabber/*",
		handler: DefaultMeteorHandler
	],
	MeteorServletChat: [
		className: "org.grails.plugins.atmosphere_meteor_sample.DefaultMeteorServlet",
		mapping: "/jabber/chat/*",
		handler: DefaultMeteorHandler
	],
	MeteorServletNotification: [
		className: "org.grails.plugins.atmosphere_meteor_sample.DefaultMeteorServlet",
		mapping: "/jabber/notification/*",
		handler: DefaultMeteorHandler
	],
	MeteorServletPublic: [
		className: "org.grails.plugins.atmosphere_meteor_sample.DefaultMeteorServlet",
		mapping: "/jabber/public/*",
		handler: DefaultMeteorHandler,
		initParams: [
			"org.atmosphere.cpr.broadcaster.shareableThreadPool": "false",
		]
	]
]

defaultInitParams = [
	"org.atmosphere.cpr.CometSupport.maxInactiveActivity": "30000",
	"org.atmosphere.cpr.broadcaster.shareableThreadPool": "true",
	"org.atmosphere.cpr.broadcasterLifeCyclePolicy": "EMPTY_DESTROY"
]
