package com.ucsmy.ucas.config.shiro.csrf;

import java.io.Serializable;

public interface CsrfToken extends Serializable {
	String getHeaderName();

    String getParameterName();

    String getToken();
    
    String getTokenType();
}
