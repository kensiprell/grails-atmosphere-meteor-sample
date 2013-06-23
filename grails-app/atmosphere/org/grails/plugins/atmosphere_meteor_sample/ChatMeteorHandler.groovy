package org.grails.plugins.atmosphere_meteor_sample

import org.atmosphere.client.TrackMessageSizeInterceptor
import org.atmosphere.config.service.MeteorService
import org.atmosphere.cpr.ApplicationConfig
import org.atmosphere.interceptor.AtmosphereResourceLifecycleInterceptor
import org.atmosphere.interceptor.BroadcastOnPostAtmosphereInterceptor
import org.atmosphere.interceptor.HeartbeatInterceptor
import org.atmosphere.cpr.DefaultBroadcaster

import static org.atmosphere.cpr.AtmosphereResource.TRANSPORT.LONG_POLLING
import static org.atmosphere.cpr.AtmosphereResource.TRANSPORT.WEBSOCKET

import grails.converters.JSON
import org.atmosphere.websocket.WebSocketEventListenerAdapter

import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import org.atmosphere.cpr.AtmosphereResourceEventListenerAdapter
import org.atmosphere.cpr.Broadcaster
import org.atmosphere.cpr.BroadcasterFactory
import org.atmosphere.cpr.Meteor
import org.json.simple.JSONObject
import org.grails.plugins.atmosphere_meteor.ApplicationContextHolder

@MeteorService(
		//path = "/jabber/chat*",
		interceptors = [
			TrackMessageSizeInterceptor.class,
			AtmosphereResourceLifecycleInterceptor.class,
			HeartbeatInterceptor.class
		])
class ChatMeteorHandler extends HttpServlet {

	def atmosphereTestService = ApplicationContextHolder.getBean("atmosphereTestService")

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String mapping = "/jabber/chat" + request.getPathInfo()

		Meteor m = Meteor.build(request)
		m.addListener(new AtmosphereResourceEventListenerAdapter())
		Broadcaster b = BroadcasterFactory.getDefault().lookup(DefaultBroadcaster.class, mapping, true)
		m.setBroadcaster(b)


		m.resumeOnBroadcast((m.transport() == LONG_POLLING)).suspend(-1)

/*
		if (m.transport().equals(LONG_POLLING)) {
			request.setAttribute(ApplicationConfig.RESUME_ON_BROADCAST, Boolean.TRUE)
		}
*/

		//m.suspend(-1)

		println "doGet atmosphereResource: " + m.atmosphereResource
		println "doGet broadcaster: " + m.broadcaster
		println "doGet broadcasters match: " + (m.broadcaster == b)

/*
		if (m.transport().equals(WEBSOCKET)) {
			m.addListener(new WebSocketEventListenerAdapter())
			println "WEBSOCKET"
		} else {
			println "ELSE"
			m.addListener(new AtmosphereResourceEventListenerAdapter())
		}
*/

		//response.setContentType("text/html;charset=UTF-8")

		//Broadcaster b = BroadcasterFactory.getDefault().lookup(DefaultBroadcaster.class, mapping, true)
		//println "doGet m: $m"
		//m.setBroadcaster(b)
		//println "m.transport: " + m.transport()
		//println "m.true: " + (m.transport().toString() == "LONG_POLLING")
		//println "m.true: " + (m.transport() == LONG_POLLING)
		//m.resumeOnBroadcast((m.transport() == LONG_POLLING)).suspend(-1)
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String mapping = "/jabber/chat" + request.getPathInfo();

		def data = JSON.parse(request.getReader().readLine()) as JSONObject
		String type = data.containsKey("type") ? data.type.toString() : null
		String resource = data.containsKey("resource") ? data.resource.toString() : null
		String message = data.containsKey("message") ? data.message.toString() : null

		if (type == null || resource == null || message == null) {
			atmosphereTestService.recordIncompleteMessage(data)
		} else {
			if (message.toLowerCase().contains("<script")) {
				atmosphereTestService.recordMaliciousUseWarning(date)
			} else {
				Broadcaster b = BroadcasterFactory.getDefault().lookup(DefaultBroadcaster.class, mapping)
				println "doPost b: $b"
				println "doPost data: $data"
				println "doPost extra: " + b.atmosphereResources
				b.broadcast(data)
				//Meteor m = Meteor.lookup(request)
				//println "doPost m: $m"
				//m.broadcast(data)
				atmosphereTestService.recordChat(data)
			}
		}
	}
}
