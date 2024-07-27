package com.dsi.authredis.redis;


import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.UUID;

public class RedisSessionFilter implements Filter {
    private JedisPool jedisPool;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        jedisPool = new JedisPool("localhost", 6379);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String sessionId = getSessionId(req);
        RedisHttpSession session = getSession(sessionId);

        RedisHttpServletRequestWrapper wrappedRequest = new RedisHttpServletRequestWrapper(req, session);
        RedisHttpServletResponseWrapper wrappedResponse = new RedisHttpServletResponseWrapper(res, session);

        chain.doFilter(wrappedRequest, wrappedResponse);

        saveSession(session);
    }

    private String getSessionId(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session != null) {
            return session.getId();
        }
        String sessionId = UUID.randomUUID().toString();
        req.getSession().setAttribute("JSESSIONID", sessionId);
        return sessionId;
    }

    private RedisHttpSession getSession(String sessionId) {
        try (Jedis jedis = jedisPool.getResource()) {
            String sessionData = jedis.get(sessionId);
            if (sessionData == null) {
                return new RedisHttpSession(sessionId);
            }
            return new RedisHttpSession(sessionId, sessionData);
        }
    }

    private void saveSession(RedisHttpSession session) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.set(session.getId(), session.serialize());
        }
    }

    @Override
    public void destroy() {
        if (jedisPool != null) {
            jedisPool.close();
        }
    }
}