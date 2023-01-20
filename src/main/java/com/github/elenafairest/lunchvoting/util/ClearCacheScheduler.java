package com.github.elenafairest.lunchvoting.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
public class ClearCacheScheduler {
    @Autowired
    CacheManager cacheManager;

    @Scheduled(cron = "0 0 11 * * *")
    public void clearCache() {
        log.info("Cache restaurantsWithMenu is cleared");
        Objects.requireNonNull(cacheManager.getCache("restaurantsWithMenu")).clear();
    }
}
