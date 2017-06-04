package com.ucsmy.ucas.config;


import com.ucsmy.ucas.config.log4j2.LogFilter;
import com.ucsmy.ucas.config.shiro.MyFormAuthenticationFilter;
import com.ucsmy.ucas.config.shiro.MyRolesAuthorizationFilter;
import com.ucsmy.ucas.config.shiro.ShiroRealmImpl;
import com.ucsmy.ucas.config.shiro.SpecCharFilter;
import com.ucsmy.ucas.config.shiro.csrf.CsrfAuthenticationStrategy;
import com.ucsmy.ucas.config.shiro.csrf.CsrfFilter;
import com.ucsmy.ucas.config.shiro.csrf.CsrfTokenRepository;
import com.ucsmy.ucas.config.shiro.csrf.HttpSessionCsrfTokenRepository;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro配置
 * Created by ucs_zhongtingyuan on 2017/3/28.
 */
@Configuration
public class ShiroConfig {
    private static Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

    @Bean
    public AuthorizingRealm getShiroRealm() {
        return new ShiroRealmImpl();
    }

    @Bean(name = "cacheManager")
    public CacheManager getCacheManager() {
        return new MemoryConstrainedCacheManager();
    }

    @Bean(name = "csrfTokenRepository")
    public CsrfTokenRepository getHttpSessionCsrfTokenRepository() {
        return new HttpSessionCsrfTokenRepository();
    }

    @Bean(name = "csrfAuthenticationStrategy")
    public CsrfAuthenticationStrategy getCsrfAuthenticationStrategy() {
        CsrfAuthenticationStrategy cas = new CsrfAuthenticationStrategy();
        cas.setCsrfTokenRepository(getHttpSessionCsrfTokenRepository());
        return cas;
    }


    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
        daap.setProxyTargetClass(true);
        return daap;
    }

    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager() {
        DefaultWebSecurityManager dwsm = new DefaultWebSecurityManager();
        dwsm.setRealm(getShiroRealm());
        dwsm.setCacheManager(getCacheManager());
        return dwsm;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
        aasa.setSecurityManager(getDefaultWebSecurityManager());
        return new AuthorizationAttributeSourceAdvisor();
    }

        @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean
                .setSecurityManager(getDefaultWebSecurityManager());
        // 增加Filter
        shiroFilterFactoryBean.getFilters().put("authc", new MyFormAuthenticationFilter(getHttpSessionCsrfTokenRepository()));
        shiroFilterFactoryBean.getFilters().put("roles", new MyRolesAuthorizationFilter());
        shiroFilterFactoryBean.getFilters().put("csrf", new CsrfFilter(getHttpSessionCsrfTokenRepository()));
        // 为日志输出添加ip和用户信息的上下文
        shiroFilterFactoryBean.getFilters().put("log", new LogFilter());
        shiroFilterFactoryBean.getFilters().put("spec",new SpecCharFilter());
        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setSuccessUrl("/main/index");
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/images/**", "anon");
        filterChainDefinitionMap.put("/Javascript/**", "anon");
        filterChainDefinitionMap.put("/libs/**", "anon");
        filterChainDefinitionMap.put("/logout", "logout");
        filterChainDefinitionMap.put("/pages/login/**", "anon");
        filterChainDefinitionMap.put("/getRsa/**", "anon");
        filterChainDefinitionMap.put("/bindAccount/**", "anon");
        filterChainDefinitionMap.put("/pages/bind/**", "anon");
        filterChainDefinitionMap.put("/favicon.ico/**", "anon");
        filterChainDefinitionMap.put("/**", "spec,log,csrf,authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
         return shiroFilterFactoryBean;
    }

}
