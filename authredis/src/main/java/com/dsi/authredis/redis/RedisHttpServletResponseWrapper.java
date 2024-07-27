package com.dsi.authredis.redis;


import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

public class RedisHttpServletResponseWrapper extends HttpServletResponseWrapper {
    private final RedisHttpSession session;

    public RedisHttpServletResponseWrapper(HttpServletResponse response, RedisHttpSession session) {
        super(response);
        this.session = session;
    }

    // You may override methods to customize behavior if needed
}