package com.dsi.authredis.redis;

import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class SessionFilter implements Filter {

    @Inject
    private RedisConfig redisConfig;

    private RedisSessionManager sessionManager;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        sessionManager = new RedisSessionManager(redisConfig.getRedissonClient());
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String sessionId = httpRequest.getRequestedSessionId();
        HttpSession session = sessionManager.retrieveSession(sessionId);

        if (session == null) {
            session = httpRequest.getSession(true);
            sessionManager.storeSession(sessionId, session);
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Cleanup resources if necessary
    }
}