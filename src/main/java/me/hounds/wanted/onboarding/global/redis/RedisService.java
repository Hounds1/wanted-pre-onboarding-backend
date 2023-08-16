package me.hounds.wanted.onboarding.global.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisService {

    private final StringRedisTemplate redisTemplate;

    public void writeToRedis(final String key, final String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void writeToRedisWithTTL(final String key, final String value, final long hours) {
        redisTemplate.opsForValue().set(key, value, hours, TimeUnit.HOURS);
    }

    public String readFromRedis(final String key) {
        String result = redisTemplate.opsForValue().get(key);
        if (result != null)
            log.info("This data has been retrieved from Redis. : [{}]", key);

        return result;
    }

    public void removeFromRedis(final String key) {
        redisTemplate.delete(key);
    }

    public boolean removeKeysWithPattern(final String pattern) {
        log.info("Removing keys matching the pattern '[{}]' from Redis.", pattern);

        Set<String> keys = redisTemplate.keys(pattern);

        assert keys != null;
        if (!keys.isEmpty()) {
            log.info("All keys matching the pattern have been removed from Redis. : [{}]", pattern);
            redisTemplate.delete(keys);
            return true;
        } else {
            log.info("Result is empty.");
            return true;
        }
    }
}
