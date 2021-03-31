package com.zhoufu.springboottars.tars.controller;

import com.qq.tars.client.Communicator;
import com.qq.tars.client.CommunicatorFactory;
import com.zhoufu.springboottars.tars.client.testapp.HelloPrx;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: zhoufu
 * @Date: 2021/3/30 17:24
 * @description:
 */
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String testHello(@RequestParam Integer no){
        Communicator communicator = CommunicatorFactory.getInstance().getCommunicator();
        HelloPrx helloPrx = communicator.stringToProxy(HelloPrx.class, "Test.TestHelloServer.helloObj");
        return helloPrx.hello(no, "springBoot To tars say hello world");
    }
}
