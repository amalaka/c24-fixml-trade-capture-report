package biz.c24.io.fixml.sample.storage;

import biz.c24.io.api.data.ComplexDataObject;
import biz.c24.io.api.presentation.JsonSink;
import biz.c24.io.api.presentation.JsonSource;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.MongoException;
import com.mongodb.util.JSON;
import com.mongodb.util.JSONParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.Transformer;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import static org.springframework.util.Assert.notNull;

public class MongoDbCollectionWrapperImpl<T extends ComplexDataObject> implements MongoDbCollectionWrapper<T> {

    private final Logger LOG = LoggerFactory.getLogger(MongoDbCollectionWrapperImpl.class);
    private final JsonSink sink;
    private final DBCollection mongoDBCollection;
    private final JsonSource source;

    public MongoDbCollectionWrapperImpl(final DBCollection mongoDBCollection, 
                                        final JsonSink jsonSink, 
                                        final JsonSource jsonSource) {
        
        notNull(mongoDBCollection, "Mandatory argument 'mongoDBCollection' missing.");
        notNull(jsonSink, "Mandatory argument 'jsonSink' missing.");
        notNull(jsonSource, "Mandatory argument 'jsonSource' missing.");
        
        this.mongoDBCollection = mongoDBCollection;
        this.sink = jsonSink;
        this.source = jsonSource;
    }

    @Transformer
    @Override
    public T store(final T complexDataObject) {
        
        try {
            LOG.info("Attempting to store ["+mongoDBCollection.getFullName()+"] a C24 ComplexDataObject...");
            
            StringWriter writer = new StringWriter();
            sink.setWriter(writer);
            sink.writeObject(complexDataObject);
            
            mongoDBCollection.save((BasicDBObject) JSON.parse(writer.toString()));
            LOG.info("Stored a new ComplexDataObject in ["+mongoDBCollection.getFullName()+"].");
            return complexDataObject;
        } catch (JSONParseException e) {
            throw new IllegalStateException(e.getMessage(), e);
        } catch (MongoException.Network e) {
            throw new IllegalStateException("mongoDB not accessible - ensure that it's running and available for service.");
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    @Transformer
    @Override
    public T query(String message) {

        StringReader reader = new StringReader(message);
        source.setReader(reader);
        //ComplexDataObject cdo = source.readObject(element);        
        
        // TODO: implement.
        throw new UnsupportedOperationException("not yet implemented.");
    }
}