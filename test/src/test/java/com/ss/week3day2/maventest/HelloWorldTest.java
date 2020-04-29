package com.ss.week3day2.maventest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class HelloWorldTest {


    @Test
    public void printHelloWorldTest() {

        HelloWorld helloWorld = new HelloWorld();
        
        assertEquals(helloWorld.printHelloWorld(-200), -1);
        assertEquals(helloWorld.printHelloWorld(101), 1);
        assertEquals(helloWorld.printHelloWorld(10), 0);

    }
}
