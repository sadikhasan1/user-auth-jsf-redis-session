package com.dsi.authredis.redis;


import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;

import java.io.Serial;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RedisHttpSession implements HttpSession, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final String id;
    private final Map<String, Object> attributes;
    private final long creationTime;
    private final long lastAccessedTime;
    private int maxInactiveInterval;

    public RedisHttpSession(String id) {
        this.id = id;
        this.attributes = new ConcurrentHashMap<>();
        this.creationTime = System.currentTimeMillis();
        this.lastAccessedTime = creationTime;
        this.maxInactiveInterval = 1800; // 30 minutes
    }

    public RedisHttpSession(String id, String serializedData) {
        this.id = id;
        this.attributes = deserialize(serializedData);
        this.creationTime = System.currentTimeMillis();
        this.lastAccessedTime = creationTime;
        this.maxInactiveInterval = 1800; // 30 minutes
    }

    @Override
    public long getCreationTime() {
        return creationTime;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public long getLastAccessedTime() {
        return lastAccessedTime;
    }

    @Override
    public ServletContext getServletContext() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setMaxInactiveInterval(int interval) {
        this.maxInactiveInterval = interval;
    }

    @Override
    public int getMaxInactiveInterval() {
        return maxInactiveInterval;
    }



    @Override
    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    //@Override
    public Object getValue(String name) {
        return attributes.get(name);
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        return new Enumeration<String>() {
            private final String[] keys = attributes.keySet().toArray(new String[0]);
            private int index = 0;

            @Override
            public boolean hasMoreElements() {
                return index < keys.length;
            }

            @Override
            public String nextElement() {
                return keys[index++];
            }
        };
    }

    //@Override
    public String[] getValueNames() {
        return attributes.keySet().toArray(new String[0]);
    }

    @Override
    public void setAttribute(String name, Object value) {
        attributes.put(name, value);
    }

    //@Override
    public void putValue(String name, Object value) {
        attributes.put(name, value);
    }

    @Override
    public void removeAttribute(String name) {
        attributes.remove(name);
    }

    //@Override
    public void removeValue(String name) {
        attributes.remove(name);
    }

    @Override
    public void invalidate() {
        attributes.clear();
    }

    @Override
    public boolean isNew() {
        return true;
    }

    public String serialize() {
        // Simplified serialization; implement as needed
        return attributes.toString();
    }

    private Map<String, Object> deserialize(String data) {
        // Simplified deserialization; implement as needed
        return new ConcurrentHashMap<>();
    }
}