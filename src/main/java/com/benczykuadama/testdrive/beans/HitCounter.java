package com.benczykuadama.testdrive.beans;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class HitCounter {

    private final AtomicInteger counter = new AtomicInteger();

    public Integer visitNumber() {
        return counter.incrementAndGet();
    }

}
