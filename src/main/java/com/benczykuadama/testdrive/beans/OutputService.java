package com.benczykuadama.testdrive.beans;

import com.benczykuadama.testdrive.wsdl.Output;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OutputService {

    @Autowired
    HitCounter counter;
    @Autowired
    MessageCreator messageCreator;
    @Autowired
    TimeSupplier timeSupplier;


    public Output getOutput(String name) {
        Output output = new Output();
        output.setCount(counter.visitNumber());
        output.setTime(timeSupplier.timeOfVisit());
        output.setMessage(messageCreator.message(name));
        return output;
    }

}
