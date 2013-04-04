package biz.c24.io.fixml.sample.storage;

import biz.c24.io.api.data.ComplexDataObject;
import biz.c24.io.api.presentation.JsonSink;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.util.JSON;
import com.mongodb.util.JSONParseException;
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
    public void store(final ComplexDataObject complexDataObject) {
        System.out.println("Storing...");
        try {
            StringWriter writer = new StringWriter();
            sink.setWriter(writer);
            sink.writeObject(complexDataObject);
            BasicDBObject obj = (BasicDBObject) JSON.parse(writer.toString());
            mongoDBCollection.save(obj);
        } catch (JSONParseException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}