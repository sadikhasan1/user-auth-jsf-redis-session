package com.dsi.authredis.redis;

import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;

import jakarta.servlet.http.HttpSession;


public class RedisSessionManager {

    private final RedissonClient redissonClient;
    private final RMap<String, HttpSession> sessions;

    public RedisSessionManager(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
        this.sessions = redissonClient.getMap("http-sessions");
    }

    public void storeSession(String sessionId, HttpSession session) {
        sessions.put(sessionId, session);
    }

    public HttpSession retrieveSession(String sessionId) {
        return sessions.get(sessionId);
    }

    public void deleteSession(String sessionId) {
        sessions.remove(sessionId);
    }
}