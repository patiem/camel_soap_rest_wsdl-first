package com.benczykuadama.testdrive.beans;

import org.springframework.stereotype.Component;

@Component
public class MessageCreator {

    private static final String template = "You are still alive, my dear %s!";

    public String message(String name) {
        return String.format(template, name);
    }
}
