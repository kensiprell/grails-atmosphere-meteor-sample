package org.grails.plugins.atmosphere_meteor_sample

class AtmosphereTestController {
	def atmosphereMeteor
	def atmosphereTestService
	
	def index() {
		if (!atmosphereMeteor.broadcasterFactory) {
			throw new RuntimeException("atmosphereMeteor.broadcasterFactory is null")
		}
		if (!atmosphereMeteor.framework) {
			throw new RuntimeException("atmosphereMeteor.framework is null")
		}
		render(view: "index")
	}
	
	def triggerPublic() {
		atmosphereTestService.triggerPublic()
		render "success"
	}
}
