package com.benczykuadama.testdrive.routes;

import com.benczykuadama.testdrive.model.Time;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.json.simple.JsonArray;
import org.apache.camel.model.dataformat.JsonDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.model.rest.RestParamType;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.apache.cxf.jaxrs.utils.JAXRSUtils;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.StreamingOutput;
import java.time.LocalDateTime;
import java.util.*;


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
                    List<String> noName = Arrays.asList("You Who Have No Name");
                    List<String> names = queryMap.getOrDefault("name", noName);
                    exchange.getOut().setBody(names);
                })
                .split(body())
                .to(("bean:timeService?method=getTime(${body})"))
                .log("${body}").end()

                .aggregate(new AggregationStrategy() {
                    @Override
                    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
                        if (oldExchange == null) {
                            List<Time> list = new ArrayList<>();
                            list.add((Time) newExchange.getIn().getBody());
                            newExchange.getIn().setBody(list);
                            return newExchange;
                        } else {
                            Object oldIn = oldExchange.getIn().getBody();
                            ArrayList<Time> list = null;
                            if(oldIn instanceof ArrayList) {
                                list = (ArrayList<Time>) oldIn;
                            } else {
                                list = new ArrayList<Time>();
                                list.add((Time) oldIn);
                            }
                            list.add((Time) newExchange.getIn().getBody());
                            newExchange.getIn().setBody(list);
                            return newExchange;
                        }
                })



//        from("rest:get:manytime")
//                .process(exchange -> {
//                    String queryString = exchange.getIn().getHeader(Exchange.HTTP_QUERY, String.class);
//                    MultivaluedMap<String, String> queryMap = JAXRSUtils.getStructuredParams(queryString, "&", false, false);
//
//                    List<String> noName = Arrays.asList("You Who Have No Name");
//                    List<String> names = queryMap.getOrDefault("name", noName);
//
////                    Map<String, List<String>> body = new HashMap();
////                    body.put("names", names);
//                    exchange.getOut().setBody(names);
//                })
//                .split(simple("${body.names}"))
//                .process(exchange -> {
//                    System.out.printf("aaaa");
//                    System.out.println(exchange.getIn().getHeaders());
//                    System.out.println(exchange.getIn().getBody());
//                })
//                .marshal().json(JsonLibrary.Jackson);
    }
}