package com.dsi.authredis.redis;


import com.dsi.authredis.redis.RedisConfig;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;

import jakarta.inject.Inject;
import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class RedisService {

    @Inject
    private RedisConfig redisConfig;

    public void storeValue(String key, String value) {
        RedissonClient redissonClient = redisConfig.getRedissonClient();
        RBucket<String> bucket = redissonClient.getBucket(key);
        bucket.set(value);
    }

    public String retrieveValue(String key) {
        RedissonClient redissonClient = redisConfig.getRedissonClient();
        RBucket<String> bucket = redissonClient.getBucket(key);
        return bucket.get();
    }
}
