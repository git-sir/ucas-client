package com.ucsmy.ucas.config.log4j2.db;

import java.io.StringReader;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ucsmy.ucas.config.log4j2.LogCommUtil;
import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AppenderLoggingException;
import org.apache.logging.log4j.core.appender.ManagerFactory;
import org.apache.logging.log4j.core.appender.db.AbstractDatabaseManager;
import org.apache.logging.log4j.core.appender.db.jdbc.ColumnConfig;
import org.apache.logging.log4j.core.appender.db.jdbc.ConnectionSource;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.apache.logging.log4j.core.util.Closer;

/**
 * 自定义JdbcDatabaseManager，处理自定义参数和ThreadContext释放。 <br>
 * Created by chenqilin on 2017/6/2.
 */
public final class LogJdbcDatabaseManager extends AbstractDatabaseManager {
    private static final LogJdbcDatabaseManager.JdbcDatabaseManagerFactory INSTANCE = new LogJdbcDatabaseManager.JdbcDatabaseManagerFactory();
    private final List<LogJdbcDatabaseManager.Column> columns;
    private final ConnectionSource connectionSource;
    private final String sqlStatement;
    private Connection connection;
    private PreparedStatement statement;
    private boolean isBatchSupported;

    private LogJdbcDatabaseManager(String name, int bufferSize, ConnectionSource connectionSource, String sqlStatement, List<LogJdbcDatabaseManager.Column> columns) {
        super(name, bufferSize);
        this.connectionSource = connectionSource;
        this.sqlStatement = sqlStatement;
        this.columns = columns;
    }

    protected void startupInternal() throws Exception {
        this.connection = this.connectionSource.getConnection();
        DatabaseMetaData metaData = this.connection.getMetaData();
        this.isBatchSupported = metaData.supportsBatchUpdates();
        Closer.closeSilently(this.connection);
    }

    protected boolean shutdownInternal() {
        return this.connection == null && this.statement == null?true:this.commitAndClose();
    }

    protected void connectAndStart() {
        try {
            this.connection = this.connectionSource.getConnection();
            this.connection.setAutoCommit(false);
            // 添加自定义信息
            String sql = addCustomMsg(this.sqlStatement);
            this.statement = this.connection.prepareStatement(sql);
        } catch (SQLException var2) {
            throw new AppenderLoggingException("Cannot write logging event or flush buffer; JDBC manager cannot connect to the database.", var2);
        }
    }

    protected void writeInternal(LogEvent event) {
        StringReader reader = null;

        try {
            if(!this.isRunning() || this.connection == null || this.connection.isClosed() || this.statement == null || this.statement.isClosed()) {
                throw new AppenderLoggingException("Cannot write logging event; JDBC manager not connected to the database.");
            }

            int e = 1;
            Iterator i$ = this.columns.iterator();

            while(i$.hasNext()) {
                LogJdbcDatabaseManager.Column column = (LogJdbcDatabaseManager.Column)i$.next();
                if(column.isEventTimestamp) {
                    this.statement.setTimestamp(e++, new Timestamp(event.getTimeMillis()));
                } else if(column.isClob) {
                    reader = new StringReader(column.layout.toSerializable(event));
                    if(column.isUnicode) {
                        this.statement.setNClob(e++, reader);
                    } else {
                        this.statement.setClob(e++, reader);
                    }
                } else if(column.isUnicode) {
                    this.statement.setNString(e++, column.layout.toSerializable(event));
                } else {
                    this.statement.setString(e++, column.layout.toSerializable(event));
                }
            }

            if(this.isBatchSupported) {
                this.statement.addBatch();
            } else if(this.statement.executeUpdate() == 0) {
                throw new AppenderLoggingException("No records inserted in database table for log event in JDBC manager.");
            }
        } catch (SQLException var9) {
            throw new AppenderLoggingException("Failed to insert record for log event in JDBC manager: " + var9.getMessage(), var9);
        } finally {
            Closer.closeSilently(reader);
        }

    }

    protected boolean commitAndClose() {
        boolean closed = true;

        try {
            if(this.connection != null && !this.connection.isClosed()) {
                if(this.isBatchSupported) {
                    this.statement.executeBatch();
                }

                this.connection.commit();
            }
        } catch (SQLException var63) {
            throw new AppenderLoggingException("Failed to commit transaction logging event or flushing buffer.", var63);
        } finally {
            try {
                Closer.close(this.statement);
            } catch (Exception var61) {
                this.logWarn("Failed to close SQL statement logging event or flushing buffer", var61);
                closed = false;
            } finally {
                this.statement = null;
            }

            try {
                Closer.close(this.connection);
            } catch (Exception var59) {
                this.logWarn("Failed to close database connection logging event or flushing buffer", var59);
                closed = false;
            } finally {
                this.connection = null;
            }

        }

        return closed;
    }

    public static LogJdbcDatabaseManager getJDBCDatabaseManager(String name, int bufferSize, ConnectionSource connectionSource, String tableName, ColumnConfig[] columnConfigs) {
        return (LogJdbcDatabaseManager)AbstractDatabaseManager.getManager(name, new LogJdbcDatabaseManager.FactoryData(bufferSize, connectionSource, tableName, columnConfigs), getFactory());
    }

    private static LogJdbcDatabaseManager.JdbcDatabaseManagerFactory getFactory() {
        return INSTANCE;
    }

    private static final class Column {
        private final PatternLayout layout;
        private final boolean isEventTimestamp;
        private final boolean isUnicode;
        private final boolean isClob;

        private Column(PatternLayout layout, boolean isEventDate, boolean isUnicode, boolean isClob) {
            this.layout = layout;
            this.isEventTimestamp = isEventDate;
            this.isUnicode = isUnicode;
            this.isClob = isClob;
        }
    }

    private static final class JdbcDatabaseManagerFactory implements ManagerFactory<LogJdbcDatabaseManager, LogJdbcDatabaseManager.FactoryData> {
        private JdbcDatabaseManagerFactory() {
        }

        public LogJdbcDatabaseManager createManager(String name, LogJdbcDatabaseManager.FactoryData data) {
            StringBuilder columnPart = new StringBuilder();
            StringBuilder valuePart = new StringBuilder();
            ArrayList columns = new ArrayList();
            int i = 0;
            ColumnConfig[] sqlStatement = data.columnConfigs;
            int len$ = sqlStatement.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                ColumnConfig config = sqlStatement[i$];
                if(i++ > 0) {
                    columnPart.append(',');
                    valuePart.append(',');
                }

                columnPart.append(config.getColumnName());
                if(config.getLiteralValue() != null) {
                    valuePart.append(config.getLiteralValue());
                } else {
                    columns.add(new LogJdbcDatabaseManager.Column(config.getLayout(), config.isEventTimestamp(), config.isUnicode(), config.isClob()));
                    valuePart.append('?');
                }
            }
            addCustomCol(columnPart, valuePart);
            String var11 = "INSERT INTO " + data.tableName + " (" + columnPart + ") VALUES (" + valuePart + ')';
            return new LogJdbcDatabaseManager(name, data.getBufferSize(), data.connectionSource, var11, columns);
        }
    }

    private static final class FactoryData extends AbstractFactoryData {
        private final ColumnConfig[] columnConfigs;
        private final ConnectionSource connectionSource;
        private final String tableName;

        protected FactoryData(int bufferSize, ConnectionSource connectionSource, String tableName, ColumnConfig[] columnConfigs) {
            super(bufferSize);
            this.connectionSource = connectionSource;
            this.tableName = tableName;
            this.columnConfigs = columnConfigs;
        }
    }

    /**
     * 添加自定义信息的预占符到SQL中
     * @param columnPart 列名
     * @param valuePart 字段值
     */
    private static void addCustomCol(StringBuilder columnPart, StringBuilder valuePart) {
        columnPart.append(",").append(LogCommUtil.COL_IP_ADDRESS);
        columnPart.append(",").append(LogCommUtil.COL_USER_ID);
        columnPart.append(",").append(LogCommUtil.COL_USER_NAME);
        // 值要与字段对应
        valuePart.append(",").append(LogCommUtil.MSG_IP_ADDRESS);
        valuePart.append(",").append(LogCommUtil.MSG_USER_ID);
        valuePart.append(",").append(LogCommUtil.MSG_USER_NAME);
    }

    /**
     * 将预占符替换成自定义信息
     * @param statement
     * @return
     */
    private static String addCustomMsg(String statement) {
        String ipAddress = ThreadContext.get(LogCommUtil.MSG_IP_ADDRESS);
        String userId = ThreadContext.get(LogCommUtil.MSG_USER_ID);
        String userName = ThreadContext.get(LogCommUtil.MSG_USER_NAME);
        statement = statement.replace(LogCommUtil.MSG_IP_ADDRESS, ipAddress != null ? "\"" + ipAddress + "\"": "null");
        statement = statement.replace(LogCommUtil.MSG_USER_ID, userId != null ? "\"" + userId + "\"" : "null");
        statement = statement.replace(LogCommUtil.MSG_USER_NAME, userName != null ? "\"" + userName + "\"": " null");
        ThreadContext.remove(LogCommUtil.MSG_IP_ADDRESS);
        ThreadContext.remove(LogCommUtil.MSG_USER_ID);
        ThreadContext.remove(LogCommUtil.MSG_USER_NAME);
        return statement;
    }
}
