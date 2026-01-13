package io.github.jakmodz.backend.security;

import io.github.jakmodz.backend.exceptions.RateLimitExceededException;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class RateLimitAspect {

    private final RedisRateLimiter stringRedisTemplate;
    private final HttpServletRequest request;

    @Autowired
    public RateLimitAspect(RedisRateLimiter stringRedisTemplate, HttpServletRequest request) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.request = request;
    }

    @Around("@annotation(RateLimit)")
    public Object rateLimit(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RateLimit rateLimit = method.getAnnotation(RateLimit.class);

        String clientIp = request.getRemoteAddr();
        String redisKey = "rate_limit:" + clientIp + ":" + method.getName();

        boolean allowed = stringRedisTemplate.isAllowed(redisKey, rateLimit.limit(), rateLimit.timeWindowSeconds());
        if (!allowed)
            throw new RateLimitExceededException("Rate limit exceeded. Try again later.");

        return joinPoint.proceed();
    }
}
