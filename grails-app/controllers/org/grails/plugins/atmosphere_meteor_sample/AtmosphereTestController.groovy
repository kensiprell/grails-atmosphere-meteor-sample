package org.grails.plugins.atmosphere_meteor_sample

import grails.converters.JSON
import org.atmosphere.cpr.Broadcaster
import org.atmosphere.cpr.BroadcasterFactory

class AtmosphereTestController {

	def index() {
		render(view: "index")
	}

	def triggerPublic() {
		String mapping = "/jabber/public"
		def finishedResponse = [type: "public", resource: mapping, message: "Finished."] as JSON
		Broadcaster b = BroadcasterFactory.getDefault().lookup(mapping, true)

		def thread = new Thread()
		thread.start {
			for (int i = 0; i < 5; i++) {
				def publicResponse = publicResponse()
				b.broadcast(publicResponse)
				sleep 2000
				println "publicResponse: $publicResponse"
				println "b: $b"
			}
			b.broadcast(finishedResponse)
			b.destroy()
			println "finished: $b"
		}

		render "success"
	}

	def publicResponse() {
		String mapping = "/jabber/public"
		return [type: "public", resource: mapping, message: "Updated at " + new Date() + "."] as JSON
	}
}
