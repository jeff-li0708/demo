package com.liangzai.service;

import bean.User;
import com.github.benmanes.caffeine.cache.Cache;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class D {

    Logger logger = LoggerFactory.getLogger(D.class);
    @Autowired
    Cache<String,Object> referRateCache;

    public User getUser(){
        String key = "张三";
        logger.info("get cache*********"+Thread.currentThread());
        Object o = referRateCache.get(key, k -> createUser(key));
        if (o !=null && o instanceof  User){
            return (User) o;
        }
        logger.info("return null");
        return null;
    }
    private User createUser(String key){
        User user = new User(1L,key);
        logger.info("------------------------------------>cache miss:"+Thread.currentThread());
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return user;
    }
}
