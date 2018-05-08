package com.benczykuadama.testdrive.routes;

import com.benczykuadama.testdrive.model.Time;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.model.rest.RestParamType;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.apache.cxf.jaxrs.utils.JAXRSUtils;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.MultivaluedMap;
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

        
        from("rest:get:processtime").routeId("processTime").to("direct:makeTime");

        from("direct:makeTime").routeId("makeTime")
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
                .to("direct:marsh");

        from("direct:marsh").routeId("marsh")
                .marshal()
                .json(JsonLibrary.Jackson);


        from("rest:get:manytime")
                .process(exchange -> {

                    String queryString = exchange.getIn().getHeader(Exchange.HTTP_QUERY, String.class);
                    MultivaluedMap<String, String> queryMap = JAXRSUtils.getStructuredParams(queryString, "&", false, false);
                    List<String> noName = Arrays.asList("You Who Have No Name");
                    List<String> names = queryMap.getOrDefault("name", noName);
                    exchange.getOut().setBody(names);
                })
                .split(body(), new AggregationStrategy() {
                    @Override
                    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
                        if (oldExchange == null) {
                            Time firstTime = newExchange.getIn().getBody(Time.class);
                            System.out.println("first:" + firstTime);
                            List<Time> times = new ArrayList<>();
                            times.add(firstTime);
                            newExchange.getIn().setBody(times);
                            return newExchange;
                        }

                        Time nextTime = newExchange.getIn().getBody(Time.class);
                        System.out.println("nest:" + nextTime);

                        ArrayList<Time> times = oldExchange.getIn().getBody(ArrayList.class);
                        System.err.println(times);

                        times.add(nextTime);
                        newExchange.getIn().setBody(times);
                        return newExchange;
                    }
                })
                    .to(("bean:timeService?method=getTime(${body})"))
                .end()
                .to("direct:marsh");

    }lambda""
}

//@Component
//class TimeAgrr implements AggregationStrategy {
//
//    @Override
//    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
//        if (oldExchange == null) {
//            Time firstTime = newExchange.getIn().getBody(Time.class);
//            System.out.println("first:" + firstTime);
//            List<Time> times = new ArrayList<>();
//            times.add(firstTime);
//            newExchange.getIn().setBody(times);
//            return newExchange;
//        }
//
//        Time nextTime = newExchange.getIn().getBody(Time.class);
//        System.out.println("nest:" + nextTime);
//
//        ArrayList<Time> times = oldExchange.getIn().getBody(ArrayList.class);
//        System.err.println(times);
//
//        times.add(nextTime);
//        newExchange.getIn().setBody(times);
//        return newExchange;
//    }
//}