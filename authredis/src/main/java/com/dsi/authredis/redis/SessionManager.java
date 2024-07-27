package com.dsi.authredis.redis;

import redis.clients.jedis.Jedis;

public class SessionManager {

    public static void storeSession(String sessionId, Object sessionData) {
        Jedis jedis = RedisConfig.getJedis();
        try {
            jedis.setex(sessionId, 3600, sessionData.toString()); // Store session data with a 1-hour expiration
        } finally {
            RedisConfig.closeJedis(jedis);
        }
    }

    public static String getSession(String sessionId) {
        Jedis jedis = RedisConfig.getJedis();
        try {
            return jedis.get(sessionId); // Retrieve session data
        } finally {
            RedisConfig.closeJedis(jedis);
        }
    }

    public static void deleteSession(String sessionId) {
        Jedis jedis = RedisConfig.getJedis();
        try {
            jedis.del(sessionId); // Remove session data
        } finally {
            RedisConfig.closeJedis(jedis);
        }
    }
}
