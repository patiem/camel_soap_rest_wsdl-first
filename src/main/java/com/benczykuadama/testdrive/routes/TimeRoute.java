package com.benczykuadama.testdrive.routes;

import com.benczykuadama.testdrive.model.Time;
import com.benczykuadama.testdrive.wsdl.Input;
import com.benczykuadama.testdrive.wsdl.Output;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Component
public class TimeRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        restConfiguration()
                .component("servlet")
                .host("0.0.0.0").port(8085).bindingMode(RestBindingMode.json);


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



        from("cxf:bean:endpoint")
                .process(exchange -> {
                    final Input input = exchange.getIn().getBody(Input.class);

                    final Output output = new Output();
                    System.out.println(input.getRequestName());
                    output.setOutputResult(input.getRequestName().concat(", Welcome to JavaOutOfBounds.com"));
                    output.setTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

                    exchange.getOut().setBody(output);
                });

    }
}
