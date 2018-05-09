package com.benczykuadama.testdrive.routes;

import com.benczykuadama.testdrive.wsdl.Input;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;


@Component
public class TimeRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        restConfiguration()
                .component("servlet")
                .host("0.0.0.0").port(8085)
                .bindingMode(RestBindingMode.json);

        rest().produces("application/json").get("/processtime").to("direct:makeTime");

        from("direct:makeTime").routeId("makeTime")
                .process(exchange -> {
                    String name = exchange.getIn().getHeader("name", String.class);
                    if (name == null) exchange.getIn().setHeader("name", "My PRECIOUS");
                })
                .to("bean:outputService?method=getOutput(${header.name})");


        from("cxf:bean:endpoint")
                .process(exchange -> {
                    final Input input = exchange.getIn().getBody(Input.class);
                    exchange.getIn().setHeader("name", input.getRequestName());
                })
                .to("direct:makeTime");
    }
}
