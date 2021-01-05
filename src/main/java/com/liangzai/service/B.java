package com.liangzai.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class B {
    @Autowired
    C c;

    @Transactional
    public void add(){
        System.out.println("this is B");
        this.b();
    }

    @Transactional
    public void a(){
        this.b();
    }

    @Transactional
    public void b(){
        System.out.println("this is b Method");
    }
}
