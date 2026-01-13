package io.github.jakmodz.backend.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisRateLimiter {
    private final StringRedisTemplate stringRedisTemplate;

    @Autowired
    public RedisRateLimiter(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }
    public boolean isAllowed(String key, int limit, int timeWindowSeconds) {
        Long currentCount = stringRedisTemplate.opsForValue().increment(key);
        if (currentCount != null && currentCount == 1) {
            stringRedisTemplate.expire(key, java.time.Duration.ofSeconds(timeWindowSeconds));
        }
        return currentCount <= limit;
    }
}
