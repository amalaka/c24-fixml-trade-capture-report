package biz.c24.io.fixml.sample.storage;

import biz.c24.io.api.data.ComplexDataObject;
import org.springframework.integration.Message;
import org.springframework.integration.annotation.ServiceActivator;

public class MongoDbWriter {

    public MongoDbWriter(final String serverName, String port) {
    }
    
    @ServiceActivator
    public void store(final Message<?> message) {
       
        System.out.println("Storing...");
        if (message.getPayload() instanceof ComplexDataObject) {
            // store
        }
        else {
            throw new RuntimeException("buggered...");
        }
        
    }
}