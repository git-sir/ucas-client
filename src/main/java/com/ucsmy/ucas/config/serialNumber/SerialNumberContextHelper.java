package com.ucsmy.ucas.config.serialNumber;

import com.ucsmy.ucas.config.serialNumber.getter.AbstractSerialNumberGenerator;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 提供访问Spring上下文的bean，让工具类的Static方法可以使用来获取配置里的serialNumberGenerator
 * Created by chenqilin on 2017/5/26.
 */
public class SerialNumberContextHelper implements ApplicationContextAware {

    private static ApplicationContext context = null;

    /**
     * 默认的流水号Bean Name
     */
    private static final String GENERATOR_NAME = "serialNumberGenerator";

    /**
     * 备用的流水号Bean Name
     */
    private static final String BACKUP_GENERATOR_NAME = "backupSerialNumberGenerator";

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    /**
     * 获取配置里的流水号生成器
     * @return
     */
    public static AbstractSerialNumberGenerator getSerialNumberGenerator() {
        return getBean(GENERATOR_NAME);
    }

    /**
     * 获取配置里的流水号生成器
     * @return
     */
    public static AbstractSerialNumberGenerator getBackupSerialNumberGenerator() {
        return getBean(BACKUP_GENERATOR_NAME);
    }

    private static AbstractSerialNumberGenerator getBean(String name) {
        return (AbstractSerialNumberGenerator) context.getBean(name);
    }

}
