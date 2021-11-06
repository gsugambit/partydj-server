package com.gsugambit.partydjserver.http;

import static com.gsugambit.partydjserver.constants.Constants.OPERATION_ID;
import java.io.IOException;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * HTTP Intercepter to add operation id if not present
 */
@Configuration
@Component
public class OperationIdIntercepter extends OncePerRequestFilter {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(OperationIdIntercepter.class);

	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String requestOpId = request.getHeader(OPERATION_ID);
		String responseOpId = response.getHeader(OPERATION_ID);
		if (StringUtils.hasLength(requestOpId) && StringUtils.hasLength(responseOpId)) {
			MDC.put(OPERATION_ID, responseOpId);
		} else {
			if (StringUtils.hasLength(responseOpId)) {
				MDC.put(OPERATION_ID, responseOpId);
			} else {
				if (StringUtils.hasLength(requestOpId)) {
					MDC.put(OPERATION_ID, requestOpId);
					response.addHeader(OPERATION_ID, requestOpId);
				} else {
					String threadOpId = MDC.get(OPERATION_ID);
					if(!StringUtils.hasLength(threadOpId)) {
						threadOpId = UUID.randomUUID().toString();
					}
					MDC.put(OPERATION_ID, threadOpId);
 					String requestAddress = request.getRemoteAddr() + ":" + request.getRemotePort();
					LOGGER.trace("request from: {} did not contain an {} header. Generated for response: {}",
							requestAddress, OPERATION_ID, threadOpId);
					response.addHeader(OPERATION_ID, threadOpId);
				}
			}
		}

		chain.doFilter(request, response);
	}

}
