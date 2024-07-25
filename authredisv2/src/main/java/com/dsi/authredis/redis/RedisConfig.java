package com.dsi.authredis.redis;

import org.redisson.api.RedissonClient;
import org.redisson.Redisson;
import org.redisson.config.Config;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Singleton;

@ApplicationScoped
public class RedisConfig {

    private RedissonClient redissonClient;

    @PostConstruct
    public void init() {
        String redisHost = System.getenv("REDIS_HOST");
        if (redisHost == null || redisHost.isEmpty()) {
            redisHost = "localhost";
        }

        // Create Redis configuration
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://" + redisHost + ":6379"); // Adjust the Redis host and port if needed

        // Create and initialize Redisson client
        redissonClient = Redisson.create(config);
    }

    public RedissonClient getRedissonClient() {
        return redissonClient;
    }

    @PreDestroy
    public void shutdown() {
        if (redissonClient != null) {
            redissonClient.shutdown();
        }
    }
}