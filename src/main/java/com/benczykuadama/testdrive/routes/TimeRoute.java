package com.benczykuadama.testdrive.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TimeRoute extends RouteBuilder {

//    @Value("${port}")
//    private int port;
//
//    @Value("${host}")
//    private String host;

    @Override
    public void configure() throws Exception {

        restConfiguration()
                .component("restlet")
                .host("0.0.0.0").port(8085).bindingMode(RestBindingMode.json);

        rest().path("/cameltime").consumes("application/json").produces("application/json")
                .get()
                .param().name("name").defaultValue("filthy worm").type(RestParamType.query).dataType("String").endParam()
                .to("bean:timeService?method=getTime(${header.name})");

    }
}