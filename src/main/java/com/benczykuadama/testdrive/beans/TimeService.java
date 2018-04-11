package com.benczykuadama.testdrive.beans;

import com.benczykuadama.testdrive.model.Time;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class TimeService {

    private static final String template = "You are still alive, my dear %s!";
    private final AtomicInteger counter = new AtomicInteger();

    public Time getTime() {
        return new Time(counter.incrementAndGet(), String.format(template, "Hateful mathe..cker"), LocalDateTime.now());
    }

}
