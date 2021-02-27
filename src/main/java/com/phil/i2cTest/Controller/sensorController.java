package com.phil.i2cTest.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class sensorController {
    // Welcomes screen
    @RequestMapping("/")
    public String greeting(){
        return "Hello world";
    }
}
