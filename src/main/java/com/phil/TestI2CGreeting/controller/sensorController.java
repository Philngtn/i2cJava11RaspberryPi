package com.phil.TestI2CGreeting.controller;

import com.pi4j.io.i2c.I2CFactory;
import com.pi4j.platform.PlatformAlreadyAssignedException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class sensorController {

    @RequestMapping("/")
    public String greeting(){
        return "Hello World";
    }

    @RequestMapping("/temp")
    public String temp() throws InterruptedException, I2CFactory.UnsupportedBusNumberException, PlatformAlreadyAssignedException, IOException {
        return AM2315Controller.readTempAndHumidity();
    }

}
