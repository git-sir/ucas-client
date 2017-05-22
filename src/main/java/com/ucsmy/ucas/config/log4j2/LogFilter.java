package com.ucsmy.ucas.config.log4j2;


import com.ucsmy.ucas.config.shiro.ShiroRealmImpl;
import com.ucsmy.ucas.config.shiro.ShiroUtils;
import org.apache.logging.log4j.ThreadContext;
import org.apache.shiro.SecurityUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import java.io.IOException;

/**
 * 每次有新请求，都先获取ip地址和用户信息，记录在Log4j2上下文中
 * Created by chenqilin on 2017/5/8.
 */
public class LogFilter extends HttpServlet implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("in Log Filter");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ThreadContext.put("ipAddress", LogCommUtil.getIpAddress());
        if (SecurityUtils.getSubject().isAuthenticated()) {
            ShiroRealmImpl.LoginUser user = ShiroUtils.getContextUser();
            if (user != null) {
                ThreadContext.put("userId", user.getId());
                ThreadContext.put("userName", user.getUserName());
            }
        }
        chain.doFilter(request, response);
        ThreadContext.clearMap();
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
