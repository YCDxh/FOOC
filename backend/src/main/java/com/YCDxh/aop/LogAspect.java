package com.YCDxh.aop;

import com.YCDxh.mapper.OperaLogMapper;
import com.YCDxh.model.entity.OperaLog;
import com.YCDxh.utils.JwtUtils;
import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * @author YCDxhg
 */
@Slf4j
@Component//交给spring管理
@Aspect //切面类
public class LogAspect {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private OperaLogMapper operateLogMapper;

    @Around("@annotation(com.YCDxh.aop.Log)")//环绕
    public Object recordLog(ProceedingJoinPoint joinPoint) throws Throwable {
        //操作人ID - 当前登录员工ID
        //获取请求头中的jwt令牌, 解析令牌
        String jwt = request.getHeader("token");
        jwt = request.getParameter("token");
        Claims claims = JwtUtils.parseJwtClaims(jwt);
        System.out.println(claims);
        Integer operateUser = (Integer) claims.get("userId");

        //操作时间
        LocalDateTime operateTime = LocalDateTime.now();

        //操作类名
        String className = joinPoint.getTarget().getClass().getName();

        //操作方法名
        String methodName = joinPoint.getSignature().getName();

        //操作方法参数
        Object[] args = joinPoint.getArgs();
        String methodParams = Arrays.toString(args);

        long begin = System.currentTimeMillis();
        //调用原始目标方法运行
        Object result = joinPoint.proceed();
        long end = System.currentTimeMillis();

        //方法返回值
        String returnValue = JSONObject.toJSONString(result);

        //耗时（单位：毫秒）
        Long costTime = end - begin;


        //记录操作日志
        OperaLog operateLog = new OperaLog(null, operateUser, operateTime, className, methodName, methodParams, returnValue, costTime);
        operateLogMapper.insert(operateLog);

        log.info("AOP记录操作日志: {}", operateLog);

        return result;
    }

}
