package com.zhoufu.springboottars.tars.server.testapp.impl;

import com.qq.tars.spring.annotation.TarsServant;
import com.zhoufu.springboottars.tars.server.testapp.HelloServant;

/**
 * @Author: zhoufu
 * @Date: 2021/3/30 17:22
 * @description:
 */
@TarsServant("helloObj")
public class HelloServantImpl implements HelloServant {
    @Override
    public String hello(int no, String name) {
        return " no = " + no + " name = " + name + System.currentTimeMillis();
    }
}
