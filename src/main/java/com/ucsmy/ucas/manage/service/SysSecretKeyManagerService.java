package com.ucsmy.ucas.manage.service;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by ucs_panwenbo on 2017/4/14.
 */
public interface SysSecretKeyManagerService {


    Map<String,String> getRsaPubKey(HttpSession httpsesssion);

    Map<String,String> getAesKey(HttpSession httpsesssion);
}


