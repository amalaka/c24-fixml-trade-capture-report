package biz.c24.io.fixml.sample.storage;

import biz.c24.io.api.data.ComplexDataObject;
import biz.c24.io.api.presentation.JsonSink;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.MongoException;
import com.mongodb.util.JSON;
import com.mongodb.util.JSONParseException;
import org.springframework.integration.annotation.ServiceActivator;

import java.io.IOException;
import java.io.StringWriter;

public class MongoDbWriter {

    private final JsonSink sink;
    private final DBCollection mongoDBCollection;

    public MongoDbWriter(final DBCollection mongoDBCollection, final JsonSink sink) {
        this.mongoDBCollection = mongoDBCollection;
        this.sink = sink;
    }

    @ServiceActivator
    public ComplexDataObject store(final ComplexDataObject complexDataObject) {
        System.out.println("Storing...");
        try {
            StringWriter writer = new StringWriter();
            sink.setWriter(writer);
            sink.writeObject(complexDataObject);
            BasicDBObject obj = (BasicDBObject) JSON.parse(writer.toString());
            mongoDBCollection.save(obj);
            return complexDataObject;
        } catch (JSONParseException e) {
            e.printStackTrace();
            throw new IllegalStateException(e.getMessage(), e);
        } catch (MongoException.Network e) {
            throw new IllegalStateException("mongoDB not accessible - ensure that it's running and available for service.");
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException(e.getMessage(), e);
        }
    }
}