package com.gsugambit.partydjserver.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Class to log and record all spring events
 * @author gsugambit
 *
 */
@Component
public class ApplicationEventListener implements ApplicationListener<ApplicationEvent> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationEventListener.class);

	public void onApplicationEvent(ApplicationEvent event) {
		LOGGER.debug("Event: {}", event.getClass().getSimpleName());

	}

}

