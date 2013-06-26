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

class PublicMeteorHandler extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

		Broadcaster b = BroadcasterFactory.getDefault().lookup(SimpleBroadcaster.class, "/jabber/public", true)
		Meteor m = Meteor.build(request)

		m.addListener(new AtmosphereResourceEventListenerAdapter())
		m.setBroadcaster(b)
		m.resumeOnBroadcast((m.transport() == LONG_POLLING)).suspend(-1)
	}
}
