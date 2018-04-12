package com.benczykuadama.testdrive.controller;

import java.util.concurrent.atomic.AtomicInteger;

//@RestController
public class TimeController {

    private static final String template = "You are still alive, my dear %s!";
    private final AtomicInteger counter = new AtomicInteger();


//    @RequestMapping("/time")
//    public Time time(@RequestParam(value = "name", defaultValue = "filthy worm") String name) {
//        return new Time(counter.incrementAndGet(), String.format(template, name), LocalDateTime.now());
//    }

//    @RequestMapping("/health")
//    public String health() {
//        return "Okk";
//    }
}
