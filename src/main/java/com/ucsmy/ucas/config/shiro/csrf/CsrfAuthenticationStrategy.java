package com.ucsmy.ucas.config.shiro.csrf;

import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CsrfAuthenticationStrategy {
	private CsrfTokenRepository csrfTokenRepository;

	public void setCsrfTokenRepository(CsrfTokenRepository csrfTokenRepository) {
		Assert.notNull(csrfTokenRepository, "csrfTokenRepository cannot be null");
		this.csrfTokenRepository = csrfTokenRepository;
	}

	public void onAuthentication(HttpServletRequest request, HttpServletResponse response) {
        boolean containsToken = this.csrfTokenRepository.loadToken(request) != null;
        if(containsToken) {
            this.csrfTokenRepository.saveToken(null, request, response);
            CsrfToken newToken = this.csrfTokenRepository.generateToken(request);
            this.csrfTokenRepository.saveToken(newToken, request, response);
            request.setAttribute(CsrfToken.class.getName(), newToken);
            request.setAttribute(newToken.getParameterName(), newToken);
        }

    }
}
