package com.benczykuadama.testdrive.routes;

import com.benczykuadama.testdrive.model.Time;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

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


        from("rest:get:/processtime")
                .process(exchange ->  {
                        Time time = new Time();
                        String name = exchange.getIn().getHeader("name", "My PRECIOUS", String.class);
                        time.setMessage(name);
                        exchange.getOut().setBody(time);
                })
                .process( exchange -> {
                        Time time = exchange.getIn().getBody(Time.class);
                        time.setNow(LocalDateTime.now());
                        exchange.getOut().setBody(time);
                })
                .marshal().json(JsonLibrary.Jackson);
    }
}