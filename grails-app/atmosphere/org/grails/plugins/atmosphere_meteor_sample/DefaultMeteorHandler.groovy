package org.grails.plugins.atmosphere_meteor_sample

import org.atmosphere.cpr.AtmosphereResourceEventListenerAdapter
import org.atmosphere.cpr.Broadcaster
import org.atmosphere.cpr.BroadcasterFactory
import org.atmosphere.cpr.DefaultBroadcaster
import org.atmosphere.cpr.Meteor

import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import grails.converters.JSON
import grails.util.Holders

class DefaultMeteorHandler extends HttpServlet {

	def atmosphereMeteor = Holders.applicationContext.getBean("atmosphereMeteor")

		@Override
	void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String mapping = URLDecoder.decode(request.getHeader("X-AtmosphereMeteor-Mapping"), "UTF-8")
		Broadcaster b = atmosphereMeteor.broadcasterFactory.lookup(DefaultBroadcaster.class, mapping, true)
		Meteor m = Meteor.build(request)

		m.addListener(new AtmosphereResourceEventListenerAdapter())
		m.setBroadcaster(b)
	}

	@Override
	void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		def jsonMap = JSON.parse(request.getReader().readLine().trim()) as Map
		String mapping = URLDecoder.decode(request.getHeader("X-AtmosphereMeteor-Mapping"), "UTF-8")

		Broadcaster b = atmosphereMeteor.broadcasterFactory.lookup(mapping)
		b.broadcast(jsonMap)
	}
}
