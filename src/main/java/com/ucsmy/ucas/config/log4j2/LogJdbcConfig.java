package com.ucsmy.ucas.config.log4j2;

import com.ucsmy.commons.utils.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.db.jdbc.ColumnConfig;
import org.apache.logging.log4j.core.appender.db.jdbc.ConnectionSource;
import org.apache.logging.log4j.core.appender.db.jdbc.JdbcAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 使用spring的dataSource来配置log4j2的jdbcAppender <br>
 * 不在log4j2.yml里配置，因为加载log4j2.yml的时候还未加载applicaiton.yml
 * Created by chenqilin on 2017/5/8.
 */
@ConfigurationProperties(prefix = "log")
@PropertySource("classpath:config/common.yml")
@Component
public class LogJdbcConfig {

    @Autowired
    private DataSource dataSource;

    private String outputJdbc;

    public String getOutputJdbc() {
        return outputJdbc;
    }

    public void setOutputJdbc(String outputJdbc) {
        this.outputJdbc = outputJdbc;
    }

    /**
     * 配置数据库连接信息
     */
    class DataConnection implements ConnectionSource {

        private DataSource dataSource;

        public DataConnection(DataSource dataSource) {
            this.dataSource = dataSource;
        }

        @Override
        public Connection getConnection() throws SQLException {
            return this.dataSource.getConnection();
        }
    }

    @PostConstruct
    private void init() {
        String needInit = getOutputJdbc();
        if (StringUtils.isNotEmpty(needInit) && Boolean.parseBoolean(needInit)) {
            final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
            final Configuration config = ctx.getConfiguration();
            ColumnConfig[] columnConfigs = {
                    ColumnConfig.createColumnConfig(config, "id", "%u", null, null, null, null),
                    ColumnConfig.createColumnConfig(config, "create_time", "%d{yyyy-MM-dd HH:mm:ss}", null, null, null, null),
                    ColumnConfig.createColumnConfig(config, "ip_address", "%X{ipAddress}", null, null, null, null),
                    ColumnConfig.createColumnConfig(config, "log_level", "%level", null, null, null, null),
                    ColumnConfig.createColumnConfig(config, "message", "%maxLen{%xEx{short}}{247}", null, null, null, null),
                    ColumnConfig.createColumnConfig(config, "user_name", "%X{userName}", null, null, null, null),
                    ColumnConfig.createColumnConfig(config, "user_id", "%X{userId}", null, null, null, null)
            };
            Appender appender = JdbcAppender.createAppender("JDBC", "true", null
                    , new DataConnection(dataSource), "0", "manage_log_info", columnConfigs);
            appender.start();
            config.addAppender(appender);
            LoggerConfig rootLogger = config.getRootLogger();
            rootLogger.addAppender(appender, Level.ERROR, null);
            ctx.updateLoggers();
        }
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

}
