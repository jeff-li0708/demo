package com;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author lcj
 * @time 2019/2/22
 * @return
 */
@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes = ApiWebApplication.class)
public class BaseTest {
    @Test
    public void test1() {
        System.out.println(11);
    }
}
