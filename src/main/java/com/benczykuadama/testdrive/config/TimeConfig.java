package com.benczykuadama.testdrive.config;

import com.benczykuadama.testdrive.wsdl.Greetings;
import org.apache.camel.component.cxf.CxfEndpoint;
import org.apache.cxf.Bus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TimeConfig {

    @Autowired
    private Bus bus;

    @Bean
    public CxfEndpoint endpoint() {

        CxfEndpoint cxfEndpoint = new CxfEndpoint();
        cxfEndpoint.setAddress("/greetings");
        cxfEndpoint.setServiceClass(Greetings.class);
        //cxfEndpoint.setWsdlURL("wsdl/students.wsdl");
        cxfEndpoint.setBus(bus);
        return cxfEndpoint;
    }

}
