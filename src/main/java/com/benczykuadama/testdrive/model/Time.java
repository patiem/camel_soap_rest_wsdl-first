package com.benczykuadama.testdrive.model;

import java.time.LocalDateTime;

public class Time {

    private Integer id;
    private LocalDateTime now;
    private String message;

    public Time(Integer id, String message) {
        this.id = id;
        this.message = message;
        this.now = LocalDateTime.now();
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
        this.message = message;
    }
}
