package com.infyvaritaas.ispl.service;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class RateLimitService {
    private static final int MAX_REQUESTS = 10;
    private static final long WINDOW_SECONDS = 60L;

    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    public boolean allow(String key) {
        Instant now = Instant.now();
        Bucket bucket = buckets.computeIfAbsent(key, k -> new Bucket(now));

        synchronized (bucket) {
            if (bucket.windowStart.plusSeconds(WINDOW_SECONDS).isBefore(now)) {
                bucket.windowStart = now;
                bucket.count = 0;
            }

            if (bucket.count >= MAX_REQUESTS) {
                return false;
            }

            bucket.count++;
            return true;
        }
    }

    private static class Bucket {
        private Instant windowStart;
        private int count;

        private Bucket(Instant windowStart) {
            this.windowStart = windowStart;
        }
    }
}
