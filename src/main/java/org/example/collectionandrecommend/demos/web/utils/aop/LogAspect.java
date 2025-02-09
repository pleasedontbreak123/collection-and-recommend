package org.example.collectionandrecommend.demos.web.utils.aop;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.Arrays;


@Aspect
@Component
public class LogAspect {

    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    // 切点表达式，匹配所有带有 @LogAnnotation 注解的方法
    @Pointcut("@annotation(org.example.collectionandrecommend.demos.web.utils.aop.LogAnnotation)")
    public void logPointCut() {}

    // 方法执行前的日志
    @Before("logPointCut()")
    public void logBefore(JoinPoint joinPoint) {
        // 获取方法的注解
        LogAnnotation logAnnotation = getMethodAnnotation(joinPoint);
        String logValue = logAnnotation != null ? logAnnotation.value() : "无说明";

        logger.info("====== 请求方法: {}.{}，参数: {}，说明: {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                Arrays.toString(joinPoint.getArgs()),
                logValue);
    }

    // 方法执行后的日志
    @AfterReturning(pointcut = "logPointCut()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        logger.info("====== 方法执行完成: {}.{}，返回值: {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                result);
    }

    // 方法抛出异常时的日志
    @AfterThrowing(pointcut = "logPointCut()", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable ex) {
        logger.error("====== 方法异常: {}.{}，异常: {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                ex.getMessage());
    }

    // 方法执行的耗时统计
    @Around("logPointCut()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        logger.info("====== 方法执行完成: {}.{}，耗时: {}ms",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                (endTime - startTime));
        return result;
    }

    private LogAnnotation getMethodAnnotation(JoinPoint joinPoint) {
        try {
            // 获取方法的参数类型（不是参数的值）
            Class<?>[] parameterTypes = Arrays.stream(joinPoint.getArgs())
                    .map(Object::getClass)  // 获取每个参数的类类型
                    .toArray(Class[]::new);  // 转换为 Class[] 数组

            // 使用参数类型来获取方法
            return joinPoint.getTarget().getClass()
                    .getMethod(joinPoint.getSignature().getName(), parameterTypes)
                    .getAnnotation(LogAnnotation.class);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }
}