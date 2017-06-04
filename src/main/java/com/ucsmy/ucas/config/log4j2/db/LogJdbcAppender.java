package com.ucsmy.ucas.config.log4j2.db;

import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.appender.db.AbstractDatabaseAppender;
import org.apache.logging.log4j.core.appender.db.jdbc.ColumnConfig;
import org.apache.logging.log4j.core.appender.db.jdbc.ConnectionSource;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.util.Booleans;

/**
 * 自定义JdbcAppender，主要修改为使用自定义的LogJdbcDatabaseManager. <br>
 * Created by chenqilin on 2017/6/2.
 */
@Plugin(
        name = "LogJdbcAppender",
        category = "Core",
        elementType = "appender",
        printObject = true
)
public final class LogJdbcAppender extends AbstractDatabaseAppender<LogJdbcDatabaseManager> {
    private final String description = this.getName() + "{ manager=" + this.getManager() + " }";

    private LogJdbcAppender(String name, Filter filter, boolean ignoreExceptions, LogJdbcDatabaseManager manager) {
        super(name, filter, ignoreExceptions, manager);
    }

    public String toString() {
        return this.description;
    }

    @PluginFactory
    public static LogJdbcAppender createAppender(@PluginAttribute("name") String name, @PluginAttribute("ignoreExceptions") String ignore, @PluginElement("Filter") Filter filter, @PluginElement("ConnectionSource") ConnectionSource connectionSource, @PluginAttribute("bufferSize") String bufferSize, @PluginAttribute("tableName") String tableName, @PluginElement("ColumnConfigs") ColumnConfig[] columnConfigs) {
        int bufferSizeInt = AbstractAppender.parseInt(bufferSize, 0);
        boolean ignoreExceptions = Booleans.parseBoolean(ignore, true);
        StringBuilder managerName = (new StringBuilder("jdbcManager{ description=")).append(name).append(", bufferSize=").append(bufferSizeInt).append(", connectionSource=").append(connectionSource.toString()).append(", tableName=").append(tableName).append(", columns=[ ");
        int i = 0;
        ColumnConfig[] manager = columnConfigs;
        int len$ = columnConfigs.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            ColumnConfig column = manager[i$];
            if(i++ > 0) {
                managerName.append(", ");
            }

            managerName.append(column.toString());
        }

        managerName.append(" ] }");
        LogJdbcDatabaseManager var15 = LogJdbcDatabaseManager.getJDBCDatabaseManager(managerName.toString(), bufferSizeInt, connectionSource, tableName, columnConfigs);
        if(var15 == null) {
            return null;
        } else {
            return new LogJdbcAppender(name, filter, ignoreExceptions, var15);
        }
    }
}
