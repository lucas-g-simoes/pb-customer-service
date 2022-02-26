package com.simoes.customerservice.api.v1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@Component
public class APILoggingFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;

		log.info("------------------------------------------------------------------------");
		log.info("Request  {} : {}", req.getMethod(), req.getRequestURI());
		log.info("Remote Origin: {}", req.getRemoteAddr());


		if (req.getParameterMap().keySet().size() > 0) {
			log.info("Parameters:");
			req.getParameterMap().keySet().forEach(parameter -> log.info("--- " + parameter + ": " + req.getParameter(parameter)));
		}

		log.info("------------------------------------------------------------------------");
		chain.doFilter(request, response);
	}

}
