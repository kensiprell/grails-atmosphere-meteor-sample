package org.grails.plugins.atmosphere_meteor_sample

import org.atmosphere.cpr.AtmosphereResourceEventListenerAdapter
import org.atmosphere.cpr.Broadcaster
import org.atmosphere.cpr.BroadcasterFactory
import org.atmosphere.cpr.DefaultBroadcaster
import org.atmosphere.cpr.Meteor

import grails.converters.JSON

import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import org.grails.plugins.atmosphere_meteor.ApplicationContextHolder

class ChatMeteorHandler extends HttpServlet {

	def atmosphereTestService = ApplicationContextHolder.getBean("atmosphereTestService")

	@Override
	void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String mapping = "/jabber/chat" + request.getPathInfo()
		Broadcaster b = BroadcasterFactory.getDefault().lookup(DefaultBroadcaster.class, mapping, true)
		Meteor m = Meteor.build(request)

		m.addListener(new AtmosphereResourceEventListenerAdapter())
		m.setBroadcaster(b)
	}

	@Override
	void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String mapping = "/jabber/chat" + request.getPathInfo();
		def jsonMap = JSON.parse(request.getReader().readLine().trim()) as Map
		String type = jsonMap.containsKey("type") ? jsonMap.type.toString() : null
		String message = jsonMap.containsKey("message") ? jsonMap.message.toString() : null

		if (type == null || message == null) {
			atmosphereTestService.recordIncompleteMessage(jsonMap)
		} else {
			if (message.toLowerCase().contains("<script")) {
				atmosphereTestService.recordMaliciousUseWarning(jsonMap)
			} else {
				atmosphereTestService.recordChat(jsonMap)
				Broadcaster b = BroadcasterFactory.getDefault().lookup(DefaultBroadcaster.class, mapping)
				b.broadcast(jsonMap as JSON)
			}
		}
	}
}
