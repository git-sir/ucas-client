package com.ucsmy.ucas.config.shiro;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ucs_xiaokailin on 2017/5/26.
 */
public class SpecCharFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //System.out.println("Class="+servletRequest.getClass());//ShiroHttpServletRequest
        HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;
//        System.out.println(getClass().getSimpleName()+"拦截到的url为:"+httpServletRequest.getRequestURI());
        //servletRequest.getParameterMap()中包含本次请求中全部非null的参数,其中每一个参数对应的值都被封装成数组
        Map<String, String[]> parameterMap = new HashMap(servletRequest.getParameterMap());
        //将请求参数中的前后空格去掉
        for (String key : parameterMap.keySet()) {
//            System.out.println("key = "+ key + " and value= " + Arrays.toString(parameterMap.get(key)));
            String[] values = parameterMap.get(key);
            if(values.length > 0){
                values[0] = values[0].trim();
            }
        }
        //把请求继续往后传递
        filterChain.doFilter(new ParameterRequestWrapper(httpServletRequest,parameterMap), servletResponse);
    }

    @Override
    public void destroy() {

    }

    /**
     * 自定义请求类，重写getParameterValues方法，返回最新的参数值
     */
    public class ParameterRequestWrapper extends HttpServletRequestWrapper {

        private Map paramsMap;

        public ParameterRequestWrapper(HttpServletRequest request, Map paramsMap) {
            super(request);
            this.paramsMap = paramsMap;
        }

        public String[] getParameterValues(String name) {
            Object v = paramsMap.get(name);
            if (v == null) {
                return null;
            } else if (v instanceof String[]) {
                return (String[]) v;
            } else if (v instanceof String) {
                return new String[] { (String) v };
            } else {
                return new String[] { v.toString() };
            }
        }

    }
}
