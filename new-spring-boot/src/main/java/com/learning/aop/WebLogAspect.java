package com.learning.aop;

import com.alibaba.fastjson.JSON;
import com.learning.vo.WebLog;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;


@Aspect
@Component
@Log4j2
public class WebLogAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebLogAspect.class);
    @Pointcut("@annotation(WebLogAop)")
    public void WebLog(){

    }

    @Around("WebLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {

        long startTime = System.currentTimeMillis();
        //获取当前请求对象
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //记录请求信息(通过Logstash传入Elasticsearch)
        WebLog webLog = new WebLog();
        Signature signature = joinPoint.getSignature();

        MethodSignature methodSignature = (MethodSignature) signature;

        Method method = methodSignature.getMethod();
        WebLogAop declaredAnnotation = method.getAnnotation(WebLogAop.class);
        String description = declaredAnnotation.description();
        Object result = joinPoint.proceed();


//        if (method.isAnnotationPresent(ApiOperation.class)) {
//            ApiOperation log = method.getAnnotation(ApiOperation.class);
//            webLog.setDescription(log.value());
//        }
        long endTime = System.currentTimeMillis();
        String urlStr = request.getRequestURL().toString();
//        webLog.setBasePath(StrUtil.removeSuffix(urlStr, URLUtil.url(urlStr).getPath()));
        webLog.setIp(request.getRemoteUser());
        Map<String,Object> logMap = new HashMap<>();
        logMap.put("spendTime",webLog.getSpendTime());
        logMap.put("description",webLog.getDescription());
        String s = JSON.toJSONString(webLog);

        LOGGER.info("{}", JSON.parse(s));
        return result;
    }
}