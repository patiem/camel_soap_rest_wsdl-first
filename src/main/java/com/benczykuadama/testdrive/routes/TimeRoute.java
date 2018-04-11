package com.benczykuadama.testdrive.routes;

import com.benczykuadama.testdrive.model.Time;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Component
public class TimeRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        restConfiguration()
                .component("restlet")
                .host("localhost").port("8086").bindingMode(RestBindingMode.json);

        rest().path("/cameltime").consumes("application/json").produces("application/json")
                .get().to("bean:timeService?method=getTime");

//        from("direct:firstRoute")
//                .log("Camel body: ${body}");
    }
}
