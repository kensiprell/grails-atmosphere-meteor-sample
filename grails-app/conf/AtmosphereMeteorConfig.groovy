import org.grails.plugins.atmosphere_meteor_sample.ChatMeteorHandler
import org.grails.plugins.atmosphere_meteor_sample.DefaultMeteorHandler
import org.grails.plugins.atmosphere_meteor_sample.PublicMeteorHandler

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
				handler: ChatMeteorHandler,
				initParams: [
						//"org.atmosphere.useNative": "true",
						//"org.atmosphere.cpr.broadcasterCacheClass": "org.atmosphere.cache.UUIDBroadcasterCache",
				]
		],
		MeteorServletNotification: [
				className: "org.grails.plugins.atmosphere_meteor_sample.DefaultMeteorServlet",
				mapping: "/jabber/notification/*",
				handler: DefaultMeteorHandler
		],
		MeteorServletPublic: [
				className: "org.grails.plugins.atmosphere_meteor_sample.DefaultMeteorServlet",
				mapping: "/jabber/public/*",
				handler: PublicMeteorHandler,
				initParams: [
						"org.atmosphere.useNative": "true",
						"org.atmosphere.cpr.broadcasterCacheClass": "org.atmosphere.cache.UUIDBroadcasterCache",
				]
		]
]
servlets = [
		MeteorServlet: [
				className: "org.grails.plugins.atmosphere_meteor_sample.DefaultMeteorServlet",
				mapping: "/jabber/*",
				handler: DefaultMeteorHandler
		],
		MeteorServletChat: [
				className: "org.grails.plugins.atmosphere_meteor_sample.DefaultMeteorServlet",
				mapping: "/jabber/chat/*",
				handler: ChatMeteorHandler,
				initParams: [
						//"org.atmosphere.useNative": "true",
						//"org.atmosphere.cpr.broadcasterCacheClass": "org.atmosphere.cache.UUIDBroadcasterCache",
				]
		],
		MeteorServletNotification: [
				className: "org.grails.plugins.atmosphere_meteor_sample.DefaultMeteorServlet",
				mapping: "/jabber/notification/*",
				handler: DefaultMeteorHandler
		],
		MeteorServletPublic: [
				className: "org.grails.plugins.atmosphere_meteor_sample.DefaultMeteorServlet",
				mapping: "/jabber/public/*",
				handler: PublicMeteorHandler,
				initParams: [
						"org.atmosphere.useNative": "true",
						"org.atmosphere.cpr.broadcasterCacheClass": "org.atmosphere.cache.UUIDBroadcasterCache",
				]
		]
]

defaultInitParams = [
		"org.atmosphere.useNative": "true",
		"org.atmosphere.cpr.broadcasterCacheClass": "org.atmosphere.cache.UUIDBroadcasterCache",
		"org.atmosphere.cpr.AtmosphereInterceptor": """
			org.atmosphere.client.TrackMessageSizeInterceptor,
			org.atmosphere.interceptor.AtmosphereResourceLifecycleInterceptor,
			org.atmosphere.interceptor.HeartbeatInterceptor
		"""
]
