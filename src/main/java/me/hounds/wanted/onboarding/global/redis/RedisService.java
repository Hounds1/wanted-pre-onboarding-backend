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

    // TODO: 2023-08-21 Update , Delete 시 Redis에 반영

    private final StringRedisTemplate redisTemplate;

    public void writeToRedis(final String key, final String value) {
        log.info("[RedisService] :: The Data has been saved into Redis. [{}]", key);
        redisTemplate.opsForValue().set(key, value);
    }

    public void writeToRedisWithTTL(final String key, final String value, final long mins) {
        log.info("[RedisService] :: The Data has been saved into Redis with TTL. [{}]", key);
        redisTemplate.opsForValue().set(key, value, mins, TimeUnit.MINUTES);
    }

    public String readFromRedis(final String key) {
        String result = redisTemplate.opsForValue().get(key);
        if (result != null)
            log.info("[RedisService] :: This data has been retrieved from Redis. : [{}]", key);

        return result;
    }

    public void removeFromRedis(final String key) {
        redisTemplate.delete(key);
    }

    public boolean removeKeysWithPattern(final String pattern) {
        log.info("[RedisService] :: Removing keys matching the pattern '[{}]' from Redis.", pattern);

        Set<String> keys = redisTemplate.keys(pattern);

        assert keys != null;
        if (!keys.isEmpty()) {
            log.info("[RedisService] :: All keys matching the pattern have been removed from Redis. : [{}]", pattern);
            redisTemplate.delete(keys);
            return true;
        } else {
            log.info("[RedisService] :: Result is empty.");
            return true;
        }
    }
}
