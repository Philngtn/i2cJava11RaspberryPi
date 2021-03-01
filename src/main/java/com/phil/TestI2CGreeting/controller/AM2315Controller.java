package com.phil.TestI2CGreeting.controller;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import com.pi4j.platform.PlatformAlreadyAssignedException;
import com.pi4j.util.Console;

import java.io.IOException;
import java.util.Arrays;

public class AM2315Controller {
    //AM2315 default add
    public static final int AM2315_ADDR = 0x5C;

    //AM2315 register
    public static final byte AM2315_CMD_READREG = (byte)0x03;
    public static final byte AM2315_REG_TEMP_H = (byte)0x02;
    public static final byte AM2315_REG_HUM_H = (byte)0x00;


    public static String readTempAndHumidity() throws InterruptedException, PlatformAlreadyAssignedException, IOException, I2CFactory.UnsupportedBusNumberException{

        // create Pi4J console wrapper/helper
        // (This is a utility class to abstract some of the boilerplate code)
        final Console console = new Console();

        // print program title/header
        console.title("<-- The Pi4J Project -->", "AM2315 Example");

        // allow for user to exit program using CTRL-C
        console.promptForExit();


        try {
            int[] ids = I2CFactory.getBusIds();
            console.println("Found follow I2C busses: " + Arrays.toString(ids));
        } catch (IOException exception) {
            console.println("I/O error during fetch of I2C busses occurred");
        }

        for (int number = I2CBus.BUS_0; number <= I2CBus.BUS_17; ++number) {
            try {
                @SuppressWarnings("unused")
                I2CBus bus = I2CFactory.getInstance(number);
                console.println("Supported I2C bus " + number + " found");
                break;
            } catch (IOException exception) {
                console.println("I/O error on I2C bus " + number + " occurred");
            } catch (I2CFactory.UnsupportedBusNumberException exception) {
                console.println("Unsupported I2C bus " + number + " required");
            }
        }


        // get the I2C bus to communicate on
        I2CBus i2c = I2CFactory.getInstance(I2CBus.BUS_1);

        // create an I2C device for an individual device on the bus that you want to communicate with
        // in this example we will use the default address for the AM2315 chip which is 0x5C.
        I2CDevice device = i2c.getDevice(AM2315_ADDR);
        console.println("Getting sensor address AM2315: " + String.format("0x%02x",device.getAddress()) + " (should be Ox5c)");


        // next we want to start taking measurements, so we need to power up the sensor
        device.write(AM2315_ADDR,(byte) 0x00);


        // next, lets perform am I2C READ operation to the AM2315 chip
        // we will read the 'ID' register from the chip to get its part number and silicon revision number
        // wait while the chip collects data
        Thread.sleep(10);
        // now we will perform our first I2C READ operation to retrieve raw integration
        // results from DATA_0 and DATA_1 registers
        console.println("**********************************/n");

        console.println("Reading DATA registers from AM2315");

        console.println("**********************************/n");

        int temperature = device.read(AM2315_REG_TEMP_H);
        if (temperature >= 32768){
            temperature = 32768 - temperature;
            temperature = temperature/10;
        }
        int humid = device.read(AM2315_REG_HUM_H)/10;

        // print raw integration results from DATA_0 and DATA_1 registers
        console.println("AM2315 Temperature = " + String.format("0x%02x", temperature));
        console.println("AM2315 Humidity = " + String.format("0x%02x", humid));

        return "AM2315 Temperature = " + String.format("0x%02x", temperature);
    }

}
