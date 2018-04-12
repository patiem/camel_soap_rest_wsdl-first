package com.benczykuadama.testdrive.routes;

import com.benczykuadama.testdrive.model.Time;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.model.rest.RestParamType;
import org.apache.cxf.jaxrs.utils.JAXRSUtils;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.MultivaluedMap;
import java.time.LocalDateTime;


@Component
public class TimeRoute extends RouteBuilder {

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
                .process(exchange -> {
                    Time time = new Time();
                    String name = exchange.getIn().getHeader("name", "My PRECIOUS", String.class);
                    time.setMessage(name);
                    exchange.getOut().setBody(time);
                })
                .process(exchange -> {
                    Time time = exchange.getIn().getBody(Time.class);
                    time.setNow(LocalDateTime.now());
                    exchange.getOut().setBody(time);
                })
                .marshal().json(JsonLibrary.Jackson);


        from("rest:get:manytime")
                .process(exchange -> {
                    String queryString = exchange.getIn().getHeader(Exchange.HTTP_QUERY, String.class);
                    MultivaluedMap<String, String> queryMap = JAXRSUtils.getStructuredParams(queryString, "&", false, false);
                    System.out.println(queryString);
                    System.out.println(queryMap);
                    for(String param : queryMap.keySet()) System.out.println(queryMap.getFirst(param));

                })
                .marshal().json(JsonLibrary.Jackson);
    }
}