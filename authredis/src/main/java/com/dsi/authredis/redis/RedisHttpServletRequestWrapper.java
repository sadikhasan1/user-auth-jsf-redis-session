package com.dsi.authredis.redis;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpSession;

public class RedisHttpServletRequestWrapper extends HttpServletRequestWrapper {
    private final RedisHttpSession session;

    public RedisHttpServletRequestWrapper(HttpServletRequest request, RedisHttpSession session) {
        super(request);
        this.session = session;
    }

    @Override
    public HttpSession getSession() {
        return session;
    }

    @Override
    public HttpSession getSession(boolean create) {
        return session;
    }
}