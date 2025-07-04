package org.example.collectionandrecommend.demos.web.utils;

import com.github.pagehelper.PageInfo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


import java.util.*;
import java.util.concurrent.*;

public class LocalCache<K, V> {

    private final int maxSize;
    private final long defaultTtlMillis;

    private final Map<K, CacheObject<V>> cacheMap;
    //单线程线程池
    private final ScheduledExecutorService cleaner = Executors.newSingleThreadScheduledExecutor();

    public LocalCache(int maxSize, long defaultTtlMillis) {
        this.maxSize = maxSize;
        this.defaultTtlMillis = defaultTtlMillis;

        // 使用 LinkedHashMap 实现 LRU（需要同步包装）
        // 我们通过 Java 原生的 LinkedHashMap 来实现 自动维护访问顺序，并且结合 removeEldestEntry 方法控制 LRU 淘汰
        this.cacheMap = Collections.synchronizedMap(new LinkedHashMap<K, CacheObject<V>>(16, 0.75f, true) {
            protected boolean removeEldestEntry(Map.Entry<K, CacheObject<V>> eldest) {
                return size() > LocalCache.this.maxSize;
            }
        });

        startCleaner();
    }

    private static class CacheObject<V> {
        final V value;
        final long expireAt;

        CacheObject(V value, long expireAt) {
            this.value = value;
            this.expireAt = expireAt;
        }

        boolean isExpired() {
            return System.currentTimeMillis() > expireAt;
        }
    }

    public void put(K key, V value) {
        put(key, value, defaultTtlMillis);
    }

    public void put(K key, V value, long ttlMillis) {
        long expireAt = System.currentTimeMillis() + ttlMillis;
        cacheMap.put(key, new CacheObject<>(value, expireAt));
    }

    public V get(K key) {
        CacheObject<V> obj = cacheMap.get(key);
        if (obj == null || obj.isExpired()) {
            cacheMap.remove(key);
            return null;
        }
        return obj.value;
    }

    public void invalidate(K key) {
        cacheMap.remove(key);
    }

    public void clear() {
        cacheMap.clear();
    }

    public int size() {
        return cacheMap.size();
    }

    // 启动定时清理线程
    private void startCleaner() {
        cleaner.scheduleAtFixedRate(() -> {
            long now = System.currentTimeMillis();
            synchronized (cacheMap) {
                Iterator<Map.Entry<K, CacheObject<V>>> it = cacheMap.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<K, CacheObject<V>> entry = it.next();
                    if (entry.getValue().isExpired()) {
                        it.remove();
                    }
                }
            }
        }, 1, 1, TimeUnit.MINUTES); // 每 1 分钟清理一次
    }

    public static <T> PageInfo<T> paginateFromCache(List<T> fullList, int pageNum, int pageSize) {
        if (fullList == null || fullList.isEmpty()) {
            return new PageInfo<>(Collections.emptyList());
        }

        int total = fullList.size();
        int startIndex = (pageNum - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, total);

        if (startIndex >= total) {
            return new PageInfo<>(Collections.emptyList());
        }

        List<T> pageList = fullList.subList(startIndex, endIndex);

        PageInfo<T> pageInfo = new PageInfo<>(pageList);
        pageInfo.setPageNum(pageNum);
        pageInfo.setPageSize(pageSize);
        pageInfo.setTotal(total);
        pageInfo.setPages((total + pageSize - 1) / pageSize);

        return pageInfo;
    }
}

