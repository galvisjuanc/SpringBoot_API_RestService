package com.springboot_restservice_api.springboot_api.controller;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.springboot_restservice_api.springboot_api.entity.Greeting;

@RestController
public class GreetingController {

    @Autowired
    Greeting greeting;

    AtomicLong counter = new AtomicLong();

    @GetMapping("/greeting")
    public Greeting greetingUser(@RequestParam(value = "name") String name){
        
        greeting.setId(counter.incrementAndGet());
        greeting.setContent("Hey, I am Learning SpringBoot from " + name);
        return greeting;
    }
}
