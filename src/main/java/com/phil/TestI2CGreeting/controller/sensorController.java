package com.phil.TestI2CGreeting.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class sensorController {
    @RequestMapping
    public String greeting(){
        return "Hello World";
    }
}
