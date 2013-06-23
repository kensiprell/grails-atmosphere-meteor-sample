package org.grails.plugins.atmosphere_meteor_sample

import javax.servlet.ServletConfig
import javax.servlet.ServletException
import javax.servlet.http.HttpServlet
import org.atmosphere.cpr.MeteorServlet
import org.atmosphere.handler.ReflectorServletProcessor
import org.grails.plugins.atmosphere_meteor.ApplicationContextHolder

class DefaultMeteorServlet extends MeteorServlet {

	@Override
	public void init(ServletConfig sc) throws ServletException {

		HttpServlet handler
		String mapping
		String servletClass
		String servletName = sc.servletName
		def config = ApplicationContextHolder.atmosphereMeteorConfig
		def servlet = config.servlets.get(servletName)

		handler = servlet.handler.newInstance() as HttpServlet
		mapping = servlet.mapping
		servletClass = handler.class.getName()
		ReflectorServletProcessor r = new ReflectorServletProcessor(handler)
		r.setServletClassName(servletClass)
		framework.addAtmosphereHandler(mapping, r).initAtmosphereHandler(sc)
		logger.info("Installed MeteorServlet ${servletClass} mapped to ${mapping}")

		super.init(sc)
	}
}

