package edu.umass.flashsale.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    public RedisService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public long reserve(String key, int amount) {
        String script = "local stock = tonumber(redis.call('GET', KEYS[1]) or '0') if stock <= 0 then return -1 end redis.call('DECRBY', KEYS[1], ARGV[1]) return stock - tonumber(ARGV[1])";
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>(script, Long.class);
        Long result = redisTemplate.execute(redisScript, Collections.singletonList(key), String.valueOf(amount));
        System.out.println("StockCount###: "+ result);
        return result == null ? -1 : result;
    }

    public void increment(String key, int amount) {
        redisTemplate.opsForValue().increment(key, amount);
    }
}