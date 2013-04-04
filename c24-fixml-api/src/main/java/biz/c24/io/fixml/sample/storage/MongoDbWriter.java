package biz.c24.io.fixml.sample.storage;

import biz.c24.io.api.data.ComplexDataObject;
import biz.c24.io.api.presentation.JsonSink;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.util.JSON;
import org.springframework.integration.Message;
import org.springframework.integration.annotation.ServiceActivator;

import java.io.IOException;
import java.io.StringWriter;

public class MongoDbWriter {

    private JsonSink sink;
    private DBCollection mongoDBCollection;

    public MongoDbWriter(final DBCollection mongoDBCollection, final JsonSink sink) {
        this.mongoDBCollection = mongoDBCollection;
        this.sink = sink;
    }

    @ServiceActivator
    public void store(final Message<?> message) {
        System.out.println("Storing...");
        if (message.getPayload() instanceof ComplexDataObject) {
            ComplexDataObject cdo = (ComplexDataObject) message.getPayload();
            try {
                StringWriter writer = new StringWriter();
                sink.setWriter(writer);
                sink.writeObject(cdo);
                BasicDBObject obj = (BasicDBObject) JSON.parse(writer.toString());
                mongoDBCollection.save(obj);
            } catch (IOException e) {
                System.out.println("An exception occurred while writing the cdo to the MongoDB database. " + e);
            }
        } else {
            throw new RuntimeException("buggered...");
        }
    }
}