package com.ffs.auth.repository.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ffs.auth.Token;
import com.ffs.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisUtil {

    private final RedisTemplate<String, Object> redisTemplate;
    private final JsonUtil jsonUtil;
    private final RedisTemplate<String, Object> redisBlackListTemplate;

    public void test() {
        log.debug("hello test");
    }

    public void set(String key, Object o, long expireTime) throws JsonProcessingException {
        log.debug("Set to redis. key={}, object={}, expireTime={}", key, o, expireTime);
//        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer(o.getClass()));
        String value = jsonUtil.objectToJson(o);
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value, expireTime, TimeUnit.MILLISECONDS);
        log.debug("Success to save in redis. key={}, object={}, expireTime={}", key, o, expireTime);

        log.debug("Get test----");
        String obj = String.valueOf(get(key));
        log.debug("Found token : {}", obj);
        Token token = jsonUtil.jsonToObject(obj, Token.class);
        log.debug("Found token : {}", token);
    }

    public Object get(String key) throws JsonProcessingException {
        return redisTemplate.opsForValue().get(key);
    }

    public boolean delete(String key) {
        return Boolean.TRUE.equals(redisTemplate.delete(key));
    }

    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    public void setBlackList(String key, Object o, long minutes) {
        log.debug("BlackList to redis. key={}, object={}, expireTime={}", key, o, minutes);

        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer(o.getClass()));
        redisTemplate.opsForValue().set(key, o, minutes, TimeUnit.MILLISECONDS);
    }

    public Object getBlackList(String key) {
        return redisBlackListTemplate.opsForValue().get(key);
    }

    public boolean deleteBlackList(String key) {
        return Boolean.TRUE.equals(redisBlackListTemplate.delete(key));
    }

    public boolean hasKeyBlackList(String key) {
        return Boolean.TRUE.equals(redisBlackListTemplate.hasKey(key));
    }
}