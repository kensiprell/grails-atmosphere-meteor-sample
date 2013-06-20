package org.grails.plugins.atmosphere_meteor_sample

import grails.converters.JSON
import org.atmosphere.cpr.Broadcaster
import org.atmosphere.cpr.BroadcasterFactory
import org.atmosphere.util.SimpleBroadcaster

class AtmosphereTestController {

	def index() {
		render(view: "index")
	}

	def triggerPublic() {
		def finishedResponse = [type: "public", resource: "/jabber/public", message: "Finished."] as JSON
		Broadcaster b = BroadcasterFactory.getDefault().lookup(SimpleBroadcaster.class, "/jabber/public", true)

		def thread = Thread.start {
			for (int i = 0; i < 5; i++) {
				def publicResponse = publicResponse()
				b.broadcast(publicResponse)
				sleep 4000
			}
			b.broadcast(finishedResponse)
		}

		render "success"
	}

	def publicResponse() {
		return [type: "public", resource: "/jabber/public", message: "Updated at " + new Date() + "."] as JSON
	}
}
