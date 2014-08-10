package org.grails.plugins.atmosphere_meteor_sample

class AtmosphereTestController {
	def atmosphereTestService

	def index() {
		render(view: "index")
	}
	
	def triggerPublic() {
		atmosphereTestService.triggerPublic()
		render "success"
	}
}
