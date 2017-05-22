package com.ucsmy.ucas.config.shiro.csrf;

public class MissingCsrfTokenException extends CsrfException {
	public MissingCsrfTokenException(String actualToken) {
        super("Could not verify the provided CSRF token because your session was not found.");
    }
}
