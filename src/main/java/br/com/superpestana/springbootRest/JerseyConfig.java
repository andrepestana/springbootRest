package br.com.superpestana.springbootRest;

import java.util.logging.Logger;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.springframework.context.annotation.Configuration;

@Configuration 
public class JerseyConfig extends ResourceConfig {

	private static final Logger log = Logger.getLogger(JerseyConfig.class.getName());

	public JerseyConfig() {
		packages("br.com.superpestana.springbootRest");
		property(ServerProperties.RESPONSE_SET_STATUS_OVER_SEND_ERROR, "true");
		register(new LoggingFeature(log));
	}
}