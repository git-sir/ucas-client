package com.ucsmy.ucas.config.shiro.csrf;

import org.apache.shiro.SecurityUtils;
import org.springframework.util.Assert;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

public class CsrfFilter implements Filter {
	public static final RequiresCsrfMatcher DEFAULT_CSRF_MATCHER = new RequiresCsrfMatcher();

	private RequiresCsrfMatcher requiresCsrfMatcher;
	private CsrfTokenRepository tokenRepository;
	
	public void setTokenRepository(CsrfTokenRepository tokenRepository) {
		Assert.notNull(tokenRepository, "csrfTokenRepository cannot be null");
		this.tokenRepository = tokenRepository;
	}

	public CsrfFilter(CsrfTokenRepository tokenRepository) {
		this.requiresCsrfMatcher = DEFAULT_CSRF_MATCHER;
		this.tokenRepository = tokenRepository;
	}
	
	@Override
	public void init(FilterConfig paramFilterConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}
	
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		if (!(servletRequest instanceof HttpServletRequest && servletResponse instanceof HttpServletResponse)) {
			throw new ServletException("CsrfFilter just supports HTTP requests");
		}
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		String code = request.getParameter("code");
		if(code==null){
			if (!SecurityUtils.getSubject().isAuthenticated()) {
				filterChain.doFilter(servletRequest, servletResponse);
				return;
			}
		}


		CsrfToken csrfToken = this.tokenRepository.loadToken(request);
		boolean missingToken = csrfToken == null;

		if(missingToken){
			csrfToken = this.tokenRepository.generateToken(request);
			this.tokenRepository.saveToken(csrfToken, request, response);
		}
		request.setAttribute(CsrfToken.class.getName(), csrfToken);
		request.setAttribute(csrfToken.getParameterName(), csrfToken);
		request.setAttribute(csrfToken.getTokenType(), csrfToken);
		if (!this.requiresCsrfMatcher.matches(request)) {
			filterChain.doFilter(servletRequest, servletResponse);
			return;
		}

		String actualToken = request.getHeader(csrfToken.getHeaderName());
		if (actualToken == null) {
			actualToken = request.getParameter(csrfToken.getParameterName());
		}

		if (csrfToken.getToken().equals(actualToken)) {
			filterChain.doFilter(servletRequest, servletResponse);
			return;
		}

		if (missingToken) {
			response.sendError(444, "Could not verify the provided CSRF token because your session was not found.");
		} else {
			response.sendError(444, "Invalid CSRF Token \'" + actualToken + "\' was found on the request parameter \'" + csrfToken.getParameterName() + "\' or header \'" + csrfToken.getHeaderName() + "\'.");
		}
	}

	private static final class RequiresCsrfMatcher {
        private final HashSet<String> allowedMethods;

        private RequiresCsrfMatcher() {
            this.allowedMethods = new HashSet<String>(Arrays.asList(new String[]{"GET", "HEAD", "TRACE", "OPTIONS"}));
        }

        public boolean matches(HttpServletRequest request) {
            return !this.allowedMethods.contains(request.getMethod());
        }
    }
}
