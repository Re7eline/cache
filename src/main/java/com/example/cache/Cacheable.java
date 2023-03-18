package com.example.cache;

public class Cacheable {

    private final Object value;
    private final long createTime;
    private final long timeToLive;

    public Cacheable(Object value, long timeToLive) {
        this.value = value;
        this.createTime = System.currentTimeMillis();
        this.timeToLive = timeToLive;
    }

    public Object getValue() {
        return value;
    }

    public long getCreateTime() {
        return createTime;
    }

    public long getTimeToLive() {
        return timeToLive;
    }
}

