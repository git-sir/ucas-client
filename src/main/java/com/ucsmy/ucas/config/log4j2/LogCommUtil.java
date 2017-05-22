package com.ucsmy.ucas.config.log4j2;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ucsmy.commons.utils.StringUtils;
import com.ucsmy.commons.utils.UUIDGenerator;
import com.ucsmy.ucas.manage.entity.ManageLogInfo;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.ThreadContext;
import org.aspectj.lang.Signature;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.util.*;

/**
 * Log4j2通用方法
 * Created by chenqilin on 2017/5/4.
 */
public class LogCommUtil {

    // mybatis Executor里对查询和增删改操作的方法名称
    private static final String METHOD_QUERY = "query";
    private static final String METHOD_UPDATE = "update";

    /**
     * 系统换行符
     */
    public static final String LINE_SEPERATOR = System.getProperty("line.separator");

    /**
     * 方法参数开始标志
     */
    private static final String PARAM_BEGIN = "(";

    /**
     * 方法参数结束标志
     */
    private static final String PARAM_END = ")";

    /**
     * 方法参数分隔标志
     */
    private static final String PARAM_SPILT = ",";

    /**
     * 方法名路径前缀
     */
    private static final String PATH_BEGIN = "com.";

    /**
     * 获取当前请求的sessionId <br>
     * 2017/5/18 修改：在一个Service方法里调用多次其他层有日志输出的service，需要记录对列编号
     * @return
     */
    public static String getServiceSessionId() {
        String sessionId = getRequestSessionId();
        String idList = ThreadContext.get(sessionId + "List");
        if (StringUtils.isEmpty(idList)) {
            idList = "0";
            ThreadContext.put(sessionId + "List", idList);
            return sessionId + "no" + "0";
        } else {
            String[] ids = idList.split(PARAM_SPILT);
            Integer maxId = Integer.parseInt(ids[ids.length - 1]);
            Integer id = maxId + 1;
            idList = idList + "," + id;
            ThreadContext.put(sessionId + "List", idList);
            return sessionId + "no" + id;
        }
    }

    /**
     * 获取请求中的sessionId
     * @return
     */
    public static String getRequestSessionId() {
        String sessionId = "default";
        Object requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            HttpSession session = request.getSession();
            if (session != null) {
                sessionId = session.getId();
            }
        }
        return sessionId;
    }

    /**
     * 清除上下文中sessionId相关信息
     * @param sessionId
     */
    public static void removeThreadContext(String sessionId) {
        ThreadContext.remove(sessionId);
        ThreadContext.remove(sessionId + "SQL");
        String requestSessionId = getRequestSessionId();
        String idList = ThreadContext.get(requestSessionId + "List");
        if (StringUtils.isNotEmpty(idList)) {
            String[] ids = idList.split(PARAM_SPILT);
            int index = 0;
            for (int i = 0; i < ids.length; i++) {
                if (ids[i].equals(sessionId)) {
                    index = i;
                    break;
                }
            }
            for (int i = index; i < ids.length - 1; i++) {
                ids[i] = ids[i + 1];
            }
            if (ids.length > 0) {
                StringBuilder modifyIds = new StringBuilder();
                for (String id : ids) {
                    if (modifyIds.length() > 0) {
                        modifyIds.append(PARAM_SPILT).append(id);
                    } else {
                        modifyIds.append(id);
                    }
                }
                ThreadContext.put(sessionId + "List", modifyIds.toString());
            } else {
                ThreadContext.remove(sessionId + "List");
            }
        }
    }

    /**
     * 截取拦截的方法名
     * @param signature
     * @return
     */
    public static String getMethodName(Signature signature) {
        String pointSignature = signature.toString();
        String name = signature.getName();
        int begin = pointSignature.indexOf(PATH_BEGIN);
        int end = pointSignature.indexOf(name);
        return pointSignature.substring(begin, end + name.length());
    }

    /**
     * 获取拦截方法的入参列表，格式：
     * type - 参数类型
     * value - 参数值
     * @return
     */
    public static String getInputParamList(Signature signature, Object[] values) {
        // 获取入参类型
        String pointSignature = signature.toString();
        int begin = pointSignature.indexOf(PARAM_BEGIN);
        int end = pointSignature.indexOf(PARAM_END);
        String subString = pointSignature.substring(begin + 1, end);
        String[] paramType = subString.split(PARAM_SPILT);
        List<Map<String, Object>> inParamList = new ArrayList<>();
        if (StringUtils.isNotEmpty(subString)) {
            for (int i = 0; i < paramType.length; i++) {
                Map<String, Object> param = new HashMap<>();
                param.put("type", paramType[i]);
                param.put("value", JSON.toJSONString(values[i]));
                inParamList.add(param);
            }
        }
        StringBuilder input = new StringBuilder();
        input.append(LINE_SEPERATOR).append("【入参列表】").append(inParamList);
        return input.toString();
    }

    /**
     * 获取拦截方法的输出内容
     * @param signature
     * @param value
     * @return
     */
    public static String getOutputParam(Signature signature, Object value) {
        if (value != null) {
            Map<String, Object> outputDate = new HashMap<>();
            String outputType = signature.toString().substring(0, signature.toString().indexOf(" "));
            outputDate.put("type", outputType);
            outputDate.put("value", JSON.toJSONString(value));
            StringBuilder output = new StringBuilder();
            output.append(LINE_SEPERATOR).append("【输出数据】").append(outputDate);
            return output.toString();
        }
        return null;
    }

    /**
     * 获取操作用户的信息
     * @return
     */
    public static String getUserInfo() {
        JSONObject userJson = new JSONObject();
        if (StringUtils.isNotEmpty(ThreadContext.get("userId"))) {
            userJson.put("userId", ThreadContext.get("userId"));
        }
        if (StringUtils.isNotEmpty(ThreadContext.get("userName"))) {
            userJson.put("userId", ThreadContext.get("userName"));
        }
        return userJson.toJSONString();
    }

    /**
     * 获取Ip地址
     * @return
     */
    public static String getIpAddress() {
        if (StringUtils.isNotEmpty(ThreadContext.get("ipAddress"))) {
            return ThreadContext.get("ipAddress");
        }
        Object requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            String ipAddress = request.getHeader("x-forwarded-for");
            if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
            }
            if ("127.0.0.1".equals(ipAddress)) {
                try {
                    ipAddress = InetAddress.getLocalHost().getHostAddress();
                } catch (UnknownHostException e) {
                    ipAddress = "";
                }
            }
            return ipAddress;
        }
        return null;
    }

    /**
     * 获取存入数据库的实体对象
     * @param info 输出到日志的信息，需要过滤IP地址和用户信息
     * @return
     */
    public static ManageLogInfo getManageLogInfo(String info) {
        // 过滤info信息
        int ipInfo = info.indexOf("【IP信息】");
        int userInfo = info.indexOf("【操作员信息】");
        info = info.replaceAll("【IP信息】.*[\\t\\n\\r]", "");
        info = info.replaceAll("【操作员信息】.*[\\t\\n\\r]", "");
        info = info.replaceAll("[\t\n\r]", "");
        if (info.length() > 255) {
            info = info.substring(0, 255);
        }
        ManageLogInfo logInfo = new ManageLogInfo();
        // TODO 现在只是随便生成一个ID，之后重写LogEventFactory给每个LogEvent生成一个EVENT_ID
        logInfo.setId(UUIDGenerator.generate());
        logInfo.setCreateTime(new Date());
        logInfo.setIpAddress(getIpAddress());
        logInfo.setUserId(ThreadContext.get("userId"));
        logInfo.setUserName(ThreadContext.get("userName"));
        logInfo.setLogLevel(Level.INFO.toString());
        logInfo.setMessage(info);
        return logInfo;
    }

    /**
     * 从拦截器里获取的方法里提取执行sql
     * @param invocation
     * @return
     */
    public static void getSqlLog(Invocation invocation) {
        // 判断ThreadContext里是否有SessionIdSql
        String sessionId = getServiceSessionId();
        if ("default".equals(sessionId)) {
            return;
        }
        String value = ThreadContext.get(sessionId + "SQL");
        // 提取方法
        String methodName = invocation.getMethod().getName();
        MappedStatement mappedStatement = null;
        BoundSql boundSql = null;
        if (value != null && Boolean.valueOf(value) && StringUtils.isNotEmpty(ThreadContext.get(sessionId))) {
            Object[] args = invocation.getArgs();
            mappedStatement = (MappedStatement) args[0];
            if (METHOD_UPDATE.equals(methodName)) {
                Object parameters = args[1];
                boundSql = mappedStatement.getBoundSql(parameters);
            } else if (METHOD_QUERY.equals(methodName)) {
                Object parameters = null;
                if (args.length > 2) {
                    parameters = args[1];
                }
                if (args.length == 4) {
                    boundSql = mappedStatement.getBoundSql(parameters);
                } else if (args.length == 6){
                    boundSql = (BoundSql) args[5];
                }
            }
        }
        if (mappedStatement != null) {
            // 添加到日志记录中
            StringBuilder logInfo = new StringBuilder(ThreadContext.get(sessionId));
            logInfo.append(LINE_SEPERATOR).append("【执行SQL】").append(printSql(mappedStatement.getConfiguration(), boundSql));
            ThreadContext.put(sessionId, logInfo.toString());
        }
    }

    /**
     * 转换特定类型的参数值
     * @param parameter
     * @return
     */
    private static String getParameterValue(Object parameter) {
        String value = null;
        if (parameter instanceof String) {
            value = "'" + parameter.toString() + "'";
        } else if (parameter instanceof Date) {
            DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
            value = "'" + formatter.format(new Date(((Date) parameter).getTime())) + "'";
        } else {
            if (parameter != null) {
                value = parameter.toString();
            } else {
                value = "''";
            }
        }
        return value;
    }

    /**
     * 打印SQL
     * @param configuration
     * @param boundSql
     * @return
     */
    private static String printSql(Configuration configuration, BoundSql boundSql) {
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        if (parameterMappings.size() > 0 && parameterObject != null) {
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                sql = sql.replaceFirst("\\?", getParameterValue(parameterObject));
            } else {
                MetaObject metaObject = configuration.newMetaObject(parameterObject);
                for (ParameterMapping parameterMapping : parameterMappings) {
                    String propertyName = parameterMapping.getProperty();
                    if (metaObject.hasGetter(propertyName)) {
                        Object obj = metaObject.getValue(propertyName);
                        sql = sql.replaceFirst("\\?", getParameterValue(obj));
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        Object obj = boundSql.getAdditionalParameter(propertyName);
                        sql = sql.replaceFirst("\\?", getParameterValue(obj));
                    }
                }
            }
        }
        return sql;
    }
}
