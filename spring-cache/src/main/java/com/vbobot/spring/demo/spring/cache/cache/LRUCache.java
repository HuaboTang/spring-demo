package com.vbobot.spring.demo.spring.cache.cache;

import java.io.Serializable;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LRUCache<K extends Comparable, V> implements Cache<K, V>,
        Serializable {
    private RedisCache<K, V> m_redisCache;
    private static Random random = new Random();
    private static final long serialVersionUID = 3674312987828041877L;

    //Map<K, Item> m_map = Collections.synchronizedMap(new HashMap<K, Item>());
    Map<K, Item> m_map = new ConcurrentHashMap<K, Item>();
    Item m_start = new Item();
    Item m_end = new Item();
    int m_maxSize;
    Object m_listLock = new Object();
    static class Item {
        private volatile Long m_lastLogTime = System.currentTimeMillis();
        private volatile AtomicInteger m_HitCount= new AtomicInteger(0);
        private volatile AtomicInteger m_TotalCount= new AtomicInteger(0);
        private volatile AtomicInteger m_RedisHitCount= new AtomicInteger(0);
        private volatile AtomicInteger m_RedisTotalCount= new AtomicInteger(0);
        private volatile SlidingWindow m_sw = new SlidingWindow(5000, 12);//5000ms一个统计块，共12块，记录的最近60s的请求总数

        private void Print() {
            Long now = System.currentTimeMillis();
            //一分钟打一次日志
            if (now - m_lastLogTime < 60*1000) {
                return;
            }
            m_lastLogTime = now;

            float percent1 = 0;
            float percent2 = 0;

            if (m_TotalCount.get() > 0) {
                percent1 = (float)m_HitCount.get()/m_TotalCount.get();
            }

            if (m_RedisTotalCount.get() > 0) {
                percent2 = (float)m_RedisHitCount.get()/m_RedisTotalCount.get();
            }

            log.info("LRUCache key {} HitCount {} TotalCount {} 命中率 {} RedisHitCount {} RedisTotalCount {} 命中率 {} qps {}",
                    key, m_HitCount.get(), m_TotalCount.get(), percent1, m_RedisHitCount.get(), m_RedisTotalCount.get(), percent2, (float)m_sw.GetSum()/60);
        }

        public Item(Comparable k, Object v, long e) {
            key = k;
            value = v;
            expires = e;
        }
        public Item() {}
        public Comparable key;
        public Object value;
        public long expires;
        public Item previous;
        public Item next;
    }
    void removeItem(Item item) {
        synchronized(m_listLock) {
            item.previous.next = item.next;
            item.next.previous = item.previous;
        }
    }
    void insertHead(Item item) {
        synchronized(m_listLock) {
            item.previous = m_start;
            item.next = m_start.next;
            m_start.next.previous = item;
            m_start.next = item;
        }
    }
    void moveToHead(Item item) {
        synchronized(m_listLock) {
            item.previous.next = item.next;
            item.next.previous = item.previous;
            item.previous = m_start;
            item.next = m_start.next;
            m_start.next.previous = item;
            m_start.next = item;
        }
    }

    private void init(int maxSize, RedisCache<K, V> redisCache) {
        m_redisCache = redisCache;
        m_maxSize = maxSize;
        m_start.next = m_end;
        m_end.previous = m_start;
    }

    public LRUCache() {
        init(10000, null);
    }

    public LRUCache(int maxSize) {
        init(maxSize, null);
    }

    public LRUCache(int maxSize, RedisCache<K, V> redisCache) {
        init(maxSize, redisCache);
    }

    @SuppressWarnings("unchecked")
    public Pair[] getAll() {
        Pair p[] = new Pair[m_maxSize];
        int count = 0;
        synchronized(m_listLock) {
            Item cur = m_start.next;
            while(cur!=m_end) {
                p[count] = new Pair(cur.key, cur.value);
                ++count;
                cur = cur.next;
            }
        }
        Pair np[] = new Pair[count];
        System.arraycopy(p, 0, np, 0, count);
        return np;
    }

    @Override
    @SuppressWarnings("unchecked")
    public V get(K key) {
        Item cur = m_map.get(key);
        if(cur==null) {
            return getRedisCache(key);
        }
        if(System.currentTimeMillis()>cur.expires) {
            m_map.remove(cur.key);
            removeItem(cur);
            return getRedisCache(key);
        }
        if(cur!=m_start.next) {
            moveToHead(cur);
        }
        V retValue = (V)cur.value;
        cur.m_sw.Increment();
        if (retValue != null) {
            cur.m_HitCount.incrementAndGet();
        }
        cur.m_TotalCount.incrementAndGet();
        cur.Print();
        return retValue;
    }

    @Override
    public V get(K obj, int breakDownRateZeroToHundred) {
        if (breakDownRateZeroToHundred > 0) {
            int tmp = random.nextInt(100);// 返回[0-99]
            if (tmp < breakDownRateZeroToHundred) {
                return null;
            }
        }
        return get(obj);
    }

    @Override
    public void put(K key, V obj) {
        put(key, obj, -1);
    }

    @Override
    public void put(K key, V value, long validTimeMs) {
        put(key, value, validTimeMs, validTimeMs);
    }

    public void put(K key, V value, long validTimeMs, long redisTimeMs) {
        Item cur = m_map.get(key);
        if(cur!=null) {
            cur.value = value;
            if(validTimeMs>0) {
                cur.expires = System.currentTimeMillis()+validTimeMs;
            }
            else {
                cur.expires = Long.MAX_VALUE;
            }
            moveToHead(cur);
            putRedisCache(key, value, redisTimeMs);
            return;
        }
        if(m_map.size()>=m_maxSize) {
            cur = m_end.previous;
            m_map.remove(cur.key);
            removeItem(cur);
        }
        long expires=0;
        if(validTimeMs>0) {
            expires = System.currentTimeMillis()+validTimeMs;
        }
        else {
            expires = Long.MAX_VALUE;
        }
        Item item = new Item(key, value, expires);
        insertHead(item);
        m_map.put(key, item);
        putRedisCache(key, value, redisTimeMs);
    }

    @Override
    public void remove(K key) {
        Item cur = m_map.get(key);
        if(cur==null) {
            return;
        }
        m_map.remove(key);
        removeItem(cur);

        if (m_redisCache != null) {
            m_redisCache.remove(key);
        }
    }

    @Override
    public int size() {
        return m_map.size();
    }

    private void putRedisCache(K key, V value, long redisTimeMs) {
        if (m_redisCache == null) {
            return;
        }
        if (redisTimeMs > 0) {
            m_redisCache.put(key, value, redisTimeMs, TimeUnit.MILLISECONDS);
        }
    }

    private V getRedisCache(K key) {
        if (m_redisCache == null) {
            return null;
        }
        return m_redisCache.get(key);
    }
}
