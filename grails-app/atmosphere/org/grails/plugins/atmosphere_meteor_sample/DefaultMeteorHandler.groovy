package org.grails.plugins.atmosphere_meteor_sample

import org.atmosphere.cpr.AtmosphereResourceEventListenerAdapter
import org.atmosphere.cpr.Broadcaster
import org.atmosphere.cpr.BroadcasterFactory
import org.atmosphere.cpr.Meteor
import org.atmosphere.util.SimpleBroadcaster
import static org.atmosphere.cpr.AtmosphereResource.TRANSPORT.LONG_POLLING

import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import org.json.simple.JSONObject

import grails.converters.JSON

class DefaultMeteorHandler extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String mapping = URLDecoder.decode(request.getHeader("X-AtmosphereMeteor-Mapping"), "UTF-8")

		Meteor m = Meteor.build(request)
		m.addListener(new AtmosphereResourceEventListenerAdapter())

		response.setContentType("text/html;charset=UTF-8")

		Broadcaster b = BroadcasterFactory.getDefault().lookup(SimpleBroadcaster.class, mapping, true)
		m.setBroadcaster(b)
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

		def data = JSON.parse(request.getReader().readLine()) as JSONObject
		String mapping = URLDecoder.decode(request.getHeader("X-AtmosphereMeteor-Mapping"), "UTF-8")

		Broadcaster b = BroadcasterFactory.getDefault().lookup(mapping)
		b.broadcast(data)
	}
}
