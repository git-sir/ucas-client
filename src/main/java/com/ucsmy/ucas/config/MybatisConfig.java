package com.ucsmy.ucas.config;

import com.ucsmy.commons.interceptor.MybatisLogInterceptor;
import com.ucsmy.commons.interceptor.MybatisPageInterceptor;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import javax.sql.DataSource;

/**
 * Mybatis配置
 * Created by ucs_zhongtingyuan on 2017/4/10.
 */
@Configuration
public class MybatisConfig {

    @Bean("sqlSessionFactory")
    @ConditionalOnMissingBean
    public SqlSessionFactory sqlSessionFactoryBean(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);

        // 增加拦截器
        MybatisPageInterceptor interceptor = new MybatisPageInterceptor();
        interceptor.setProperties(null);
        MybatisLogInterceptor logInterceptor = new MybatisLogInterceptor();
        bean.setPlugins(new Interceptor[] { interceptor, logInterceptor });

        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        bean.setMapperLocations(resolver.getResources("classpath:sql/mapper/*.xml"));
        return bean.getObject();
    }
}
