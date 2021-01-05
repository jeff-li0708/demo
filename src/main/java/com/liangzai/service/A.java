package com.liangzai.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class A {
    @Autowired
    B b;
    public void test(){
        b.add();
        String s = "";
        char a = 'a';
        char i = (char) (a + 1);

    }
}
