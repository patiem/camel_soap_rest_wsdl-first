package com.benczykuadama.testdrive;

import com.benczykuadama.testdrive.beans.OutputService;
import com.benczykuadama.testdrive.model.Time;
import com.benczykuadama.testdrive.routes.TimeRoute;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.camel.util.jndi.JndiContext;
import org.junit.Before;
import org.junit.Test;

import javax.naming.Context;

public class TimeRouteTest extends CamelTestSupport {

    @Before
    public void mockEndpoints() throws Exception {
        AdviceWithRouteBuilder mockResult = new AdviceWithRouteBuilder() {

            @Override
            public void configure() throws Exception {
                // mock the for testing
                interceptSendToEndpoint("direct:marsh")
                        .to("mock:result")
                        .skipSendToOriginalEndpoint();


            }
        };
        context.getRouteDefinition("marsh")
                .adviceWith(context, mockResult);
    }


    @Test
    public void getsJsonWithMessageWhenNoNameIsProvided() throws Exception {

        String correctMessage = "You are still alive, my dear My PRECIOUS!";
        MockEndpoint mock = getMockEndpoint("mock:result");

        context.start();
        template.requestBody("rest:get:processtime", "");

        System.out.println("ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ");

        mock.expectedMessageCount(1);
        mock.assertIsSatisfied();

        Time time = mock.getExchanges().get(0).getIn().getBody(Time.class);
        assertEquals(correctMessage, time.getMessage());

        context.stop();
    }

    @Test
    public void getsJsonWithMessageWheNameIsProvided() throws Exception {

        String correctMessage = "You are still alive, my dear Lolek!";
        MockEndpoint mock = getMockEndpoint("mock:result");

        context.start();
        template.requestBodyAndHeader("rest:get:processtime", "", "name", "Lolek");

        System.out.println("ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ");

        mock.expectedMessageCount(1);
        mock.assertIsSatisfied();

        Time time = mock.getExchanges().get(0).getIn().getBody(Time.class);
        assertEquals(correctMessage, time.getMessage());
        assertNotNull(time.getNow());
        context.stop();
    }

    @Override
    public boolean isUseAdviceWith() {
        return true;
    }

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new TimeRoute();
    }

    @Override
    protected Context createJndiContext() throws Exception {
        JndiContext context = new JndiContext();
        context.bind("timeService", new OutputService());
        return context;
    }
}

