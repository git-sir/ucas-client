package com.ucsmy.ucas.config.shiro.csrf;

public class CsrfException extends RuntimeException {
	public CsrfException(String msg) {
        super(msg);
    }

    public CsrfException(String msg, Throwable t) {
        super(msg, t);
    }
}
