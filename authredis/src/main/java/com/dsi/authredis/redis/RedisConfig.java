package com.dsi.authredis.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisConfig {
    private static JedisPool jedisPool;

    static {
        jedisPool = new JedisPool("localhost", 6379); // Replace with your Redis host and port
    }

    public static Jedis getJedis() {
        return jedisPool.getResource();
    }

    public static void closeJedis(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }
}
