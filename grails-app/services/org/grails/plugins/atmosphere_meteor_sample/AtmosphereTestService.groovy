package org.grails.plugins.atmosphere_meteor_sample

import org.atmosphere.cpr.Broadcaster
import org.atmosphere.cpr.BroadcasterFactory
import grails.converters.JSON

class AtmosphereTestService {
	def atmosphereMeteorService

	def recordChat(data) {
		// This method could be used to persist chat messages to a data store.
		println "AtmosphereTestService.recordChat: ${data}"
	}

	def recordIncompleteMessage(data) {
		// This method could be used to persist errors to a data store.
		println "Error AtmosphereTestService.recordIncompleteMessage: ${data}"
	}

	def recordMaliciousUseWarning(data) {
		// This method could be used to persist potential malicious code to a data store.
		println "Warning AtmosphereTestService.recordMaliciousUseWarning: ${data}"
	}

	def sendDisconnectMessage(event, request) {
		// This method could be used to send chat participants a message that a user has left.
		def message = "A user has left the chat session"
		println message
		event.broadcaster().broadcast(message)
	}
	
	def triggerPublic() {
		String mapping = "/atmosphere/public"
		def finishedResponse = [type: "public", resource: mapping, message: "Finished."] as JSON

		def thread = new Thread()
		thread.start {
			for (int i = 0; i < 5; i++) {
				def publicResponse = publicResponse()
				atmosphereMeteorService.broadcast(mapping, publicResponse)
				sleep 2000
			}
			atmosphereMeteorService.broadcast(mapping, finishedResponse)
		}
	}

	def publicResponse() {
		String mapping = "/atmosphere/public"
		return [type: "public", resource: mapping, message: "Updated at " + new Date() + "."] as JSON
	}
}

