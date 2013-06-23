package org.grails.plugins.atmosphere_meteor_sample

import org.atmosphere.config.service.AtmosphereHandlerService
import org.atmosphere.cpr.AtmosphereResponse
import org.atmosphere.handler.OnMessage
import org.atmosphere.interceptor.AtmosphereResourceLifecycleInterceptor
import org.atmosphere.interceptor.BroadcastOnPostAtmosphereInterceptor
import org.codehaus.jackson.map.ObjectMapper

@AtmosphereHandlerService(path = "/jabber/chat/12345",
		interceptors = [
		AtmosphereResourceLifecycleInterceptor.class,
		BroadcastOnPostAtmosphereInterceptor.class
		])
class ChatMeteorHandlerJF extends OnMessage<String> {

	private final ObjectMapper mapper = new ObjectMapper();

	@Override
	public void onMessage(AtmosphereResponse response, String message) throws IOException {
		response.write(mapper.writeValueAsString(mapper.readValue(message, Data.class)));
	}

	public final static class Data {

		private String type
		private String resource
		private String message

		public Data() {
			this("", "");
		}

		public Data(String type, String resource, String message) {
			this.type = type
			this.resource = resource
			this.message = message
			this.time = new Date().getTime()
		}

		public String getMessage() {
			return message;
		}

		public String getType() {
			return type;
		}

		public String getResource() {
			return resource;
		}

		public void setType(String type) {
			this.type = type;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public void setResource(String resource) {
			this.resource = resource;
		}

	}
}

