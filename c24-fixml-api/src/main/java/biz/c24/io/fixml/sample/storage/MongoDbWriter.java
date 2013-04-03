package biz.c24.io.fixml.sample.storage;

import org.springframework.integration.Message;
import org.springframework.integration.annotation.ServiceActivator;

public class MongoDbWriter {

    public MongoDbWriter(final String serverName, String port) {
    }
    
    @ServiceActivator
    public void store(final Message<?> message) {
       System.out.println("Storing..."); 
    }
}