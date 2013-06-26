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

import org.json.simple.JSONObject

import org.grails.plugins.atmosphere_meteor.ApplicationContextHolder

class ChatMeteorHandler extends HttpServlet {

	def atmosphereTestService = ApplicationContextHolder.getBean("atmosphereTestService")

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String mapping = "/jabber/chat" + request.getPathInfo()
		Meteor m = Meteor.build(request)
		Broadcaster b = BroadcasterFactory.getDefault().lookup(DefaultBroadcaster.class, mapping, true)

		m.addListener(new AtmosphereResourceEventListenerAdapter())
		m.setBroadcaster(b)
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
				b.broadcast(data)
				atmosphereTestService.recordChat(data)
			}
		}
	}
}
