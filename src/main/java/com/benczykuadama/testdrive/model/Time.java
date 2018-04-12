package com.benczykuadama.testdrive.model;


import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;


public class Time {

    private static final String template = "You are still alive, my dear %s!";
    private static final AtomicInteger counter = new AtomicInteger();

    private Integer id;
    private LocalDateTime now;
    private String message;

    public Time() {
        id = counter.incrementAndGet();
    }

    public Time(Integer id, String message, LocalDateTime now) {
        this.id = id;
        this.message = String.format(template, message);
        this.now = now;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public LocalDateTime getNow() {
        return now;
    }

    public void setNow(LocalDateTime now) {
        this.now = now;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = String.format(template, message);
    }

}
