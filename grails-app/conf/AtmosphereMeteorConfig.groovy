import org.grails.plugins.atmosphere_meteor_sample.ChatMeteorHandler
import org.grails.plugins.atmosphere_meteor_sample.DefaultMeteorHandler
import org.grails.plugins.atmosphere_meteor_sample.PublicMeteorHandler

def defaultMapping = "/atmosphere/*"

def defaultInitParams = [
		"org.atmosphere.cpr.broadcasterCacheClass": "org.atmosphere.cache.UUIDBroadcasterCache",
		"org.atmosphere.cpr.AtmosphereInterceptor": """
			org.atmosphere.client.TrackMessageSizeInterceptor,
			org.atmosphere.interceptor.AtmosphereResourceLifecycleInterceptor,
			org.atmosphere.interceptor.HeartbeatInterceptor
		"""
]

servlets = [
		MeteorServlet: [
				className: "org.grails.plugins.atmosphere_meteor_sample.DefaultMeteorServlet",
				mapping: defaultMapping,
				handler: DefaultMeteorHandler,
				initParams: defaultInitParams
		],
		MeteorServletChat: [
				className: "org.grails.plugins.atmosphere_meteor_sample.DefaultMeteorServlet",
				mapping: "/atmosphere/chat/*",
				handler: ChatMeteorHandler,
				initParams: defaultInitParams
		],
		MeteorServletNotification: [
				className: "org.grails.plugins.atmosphere_meteor_sample.DefaultMeteorServlet",
				mapping: "/atmosphere/notification/*",
				handler: DefaultMeteorHandler,
				initParams: defaultInitParams
		],
		MeteorServletPublic: [
				className: "org.grails.plugins.atmosphere_meteor_sample.DefaultMeteorServlet",
				mapping: "/atmosphere/public/*",
				handler: PublicMeteorHandler,
				initParams: [
						"org.atmosphere.cpr.broadcasterCacheClass": "org.atmosphere.cache.UUIDBroadcasterCache",
				]
		]
]

