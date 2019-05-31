package com.zhaoming.test;

import com.zhaoming.TestPlatformApplication;
import com.zhaoming.bean.platfrom.ServiceProcessor;
import com.zhaoming.bean.platfrom.ServiceSelector;
import com.zhaoming.bean.platfrom.enums.Versions;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={TestPlatformApplication.class})
public class Test {
    @Autowired
    ServiceSelector selector;
    @org.junit.Test
    public void testTwo(){
        System.out.println("test");
        ServiceProcessor processor = selector.select("zm.test", Versions.V2);
        System.out.println(processor.getClass());
    }

    @Before
    public void testBefore(){
        System.out.println("before");
    }

    @After
    public void testAfter(){
        System.out.println("after");
    }
}
