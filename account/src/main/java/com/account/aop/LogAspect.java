package com.account.aop;

import cn.hutool.core.date.DateUtil;
import com.account.util.WebToolUtils;
import com.account.util.SqlUtils;
import com.alibaba.fastjson.JSON;
import org.apache.ibatis.session.SqlSessionFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;


/**
 * Copyright: Copyright (C) 2022 XXX, Inc. All rights reserved. <p>
 * Company: 霉运有限公司<p>
 *
 * @author luojinhua
 * @since 2022/5/4 13:58
 */
@Aspect
@Component
public class LogAspect {

    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Pointcut("execution(* com.account.dao..*.*(..))")
    public void getPointcut() {
        // do something in Around
    }

    @Around("getPointcut()")
    public Object logAround(ProceedingJoinPoint point) throws Throwable {
        if (!log.isDebugEnabled()) {
            return point.proceed();
        }
        long beginTime = System.currentTimeMillis();
        Object result = point.proceed();
        long time = System.currentTimeMillis() - beginTime;
        Map<String,Object> map = SqlUtils.getMybatisSql(point,sqlSessionFactory);
        printLog(point, time,map);

        return result;
    }

    private void printLog(ProceedingJoinPoint joinPoint, long time,Map<String,Object> map) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //请求的 类名、方法名
        String clazz = signature.getDeclaringTypeName();
        String method = signature.getName();
        //请求的参数
//        Object[] args = joinPoint.getArgs();
        String nowTime = DateUtil.now();
        Object sql = map.get("sql");
        Object args = map.get("args");
        String ip = WebToolUtils.getIpAddress();
        log.debug("请求时间:[{}],请求IP:[{}] 类名:[{}], 方法名:[{}], 参数列表:{}, SQL:[{}], 耗时:{}ms",nowTime,ip,clazz, method, JSON.toJSONString(args),sql,time);
    }
}
