package com.ucsmy.ucas.manage.service;

import java.util.concurrent.TimeUnit;

/**
 * Created by ucs_zhongtingyuan on 2017/4/11.
 */
public interface SysCacheService {

    void set(String key, Object value, long invalid);

    Object get(String key);

    String getString(String key);
    //删除对应缓存数据
    void delete(String key);
    //查询key是否存在
    boolean hasKey(String key);
    //获取key过期时间
    long getExpire(String key);

    /**
     * 给key对应的Value做自增
     * @param key key值
     * @param delta 增量
     * @param timeout 过期时间
     * @param unit 时间单位
     * @return
     */
    Long increment(String key, long delta, long timeout, TimeUnit unit);

    /**
     * 给key对应的Value做自增
     * @param key key值
     * @param delta 增量
     * @return
     */
    Long increment(String key, long delta);

    /**
     * 删除符合匹配的key值
     * @param pattern
     */
    void deleteKeys(String pattern);
}
