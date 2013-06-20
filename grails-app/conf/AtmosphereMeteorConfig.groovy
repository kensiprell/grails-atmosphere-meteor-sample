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
		"org.atmosphere.useNative": "true",
		"org.atmosphere.cpr.CometSupport.maxInactiveActivity": "30000",
		"org.atmosphere.cpr.broadcasterLifeCyclePolicy": "EMPTY_DESTROY",
		"org.atmosphere.cpr.broadcasterCacheClass": "org.atmosphere.cache.UUIDBroadcasterCache",
		"org.atmosphere.cpr.AtmosphereInterceptor": """
			org.atmosphere.interceptor.AtmosphereResourceLifecycleInterceptor,
			org.atmosphere.interceptor.HeartbeatInterceptor
		"""
]

/*
			org.atmosphere.client.TrackMessageSizeInterceptor
*/