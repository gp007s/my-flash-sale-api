package edu.umass.flashsale.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.Collections;


@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, Long> redisTemplate;

    public long checkItemAvailabilityAndReserve(String key, int amount) {
        System.out.println("Key ### : "+ key);
        String LUA_SCRIPT =
                "local itemCount = tonumber(redis.call('GET', KEYS[1]) or '0') " +
                        "if itemCount <= 0 then " +
                        "    return -1 " +
                        "end " +
                        "redis.call('DECRBY', KEYS[1], ARGV[1]) " +
                        "return itemCount - tonumber(ARGV[1])";
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>(LUA_SCRIPT, Long.class);
        Long result = redisTemplate.execute(redisScript, Collections.singletonList(key), amount);
        System.out.println("Remaining Items ### : "+ result);
        return result == null ? -1 : result;
    }
    public void loadTheKeyValueFirstTime(String key, long amount) {
        redisTemplate.opsForValue().set(key, amount);
    }

    public Long getAvailableItemCount(String key) {
        return redisTemplate.opsForValue().get("stock:"+key);
    }

    public void decrement(String key, int amount) {
        redisTemplate.opsForValue().decrement(key, amount);
    }
}