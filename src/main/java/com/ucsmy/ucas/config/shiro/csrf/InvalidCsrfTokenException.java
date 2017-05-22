package com.ucsmy.ucas.config.shiro.csrf;

public class InvalidCsrfTokenException extends CsrfException {
    public InvalidCsrfTokenException(CsrfToken csrfToken, String actualToken) {
        super("Invalid CSRF Token \'" + actualToken + "\' was found on the request parameter \'" + csrfToken.getParameterName() + "\' or header \'" + csrfToken.getHeaderName() + "\'.");
    }
}
