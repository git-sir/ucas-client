package com.ucsmy.ucas.commons.aop.exception.aspect;


import com.ucsmy.ucas.commons.aop.exception.ServiceException;
import com.ucsmy.ucas.commons.aop.exception.result.AosResult;
import com.ucsmy.ucas.commons.aop.exception.result.ResultConst;
import com.ucsmy.ucas.commons.aop.exception.utils.JsonUtils;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * webControl异常切面
 * 默认spring aop不会拦截controller层，使用该类需要在spring公共配置文件中注入改bean，
 * 另外需要配置<aop:aspectj-autoproxy proxy-target-class="true"/>
 */
//@Aspect
//@Component
//@Order(2)
public class WebExceptionAspect {

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    private void webPointcut() {}

    /**
     * 拦截web层异常，记录异常日志，并返回友好信息到前端
     * 目前只拦截Exception，是否要拦截Error需再做考虑
     *
     * @param e 异常对象
     */
    @AfterThrowing(pointcut = "webPointcut()", throwing = "e")
    public void handleThrowing(Exception e) {
        //不需要再记录ServiceException，因为在service异常切面中已经记录过
        if (!(e instanceof ServiceException)) {
        }
        String errorMsg = StringUtils.isEmpty(e.getMessage()) ? "系统异常" : e.getMessage();
        writeContent(errorMsg);
    }

    /**
     * 将内容输出到浏览器
     *
     * @param content 输出内容
     */
    private void writeContent(String content) {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        response.reset();
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/json;charset=UTF-8");
        response.setHeader("icop-content-type", "exception");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        AosResult result = new AosResult();
        result.setRetcode( String.valueOf(ResultConst.ERROR));
        result.setRetmsg(content);
        writer.print(JsonUtils.formatObjectToJson(result));
        writer.flush();
        writer.close();
    }
}
