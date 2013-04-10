package biz.c24.io.fixml.sample.configuration;

import biz.c24.io.api.presentation.JsonSink;
import biz.c24.io.api.presentation.JsonSource;
import biz.c24.io.fixml.sample.storage.MongoDbCollectionWrapper;
import biz.c24.io.fixml.sample.storage.MongoDbCollectionWrapperImpl;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.UnknownHostException;

@Configuration
public class C24MongoDbConfiguration {

    @Value("${mongo.db.server}")
    private String mongoServer;
    @Value("${mongo.db.port}")
    private int mongoPort;
    @Value("${mongo.db.name}")
    private String mongoDBName;
    @Value("${mongo.collection.fixml.name}")
    private String mongoFixmlCollectionName;
    @Value("${mongo.collection.compass.name}")
    private String mongoCompassCollectionName;
    @Value("${mongo.collection.exceptions.name}")
    private String mongoExceptionManagementCollectionName;
    @Autowired private JsonSource jsonSource;
    @Autowired private JsonSink jsonSink;

    @Bean(name = "mongoDbFixMlCollectionWrapper")
    public MongoDbCollectionWrapper getMongoDbFixMlCollectionWrapper() throws UnknownHostException {
        MongoDbCollectionWrapper mongoDbCollectionWrapper
                = new MongoDbCollectionWrapperImpl(getFixmlCollection(), jsonSink, jsonSource);
        return mongoDbCollectionWrapper;
    }

    @Bean(name = "mongoDbCompassCollectionWrapper")
    public MongoDbCollectionWrapper getMongoDbCompassCollectionWrapper() throws UnknownHostException {
        MongoDbCollectionWrapper mongoDbCollectionWrapper
                = new MongoDbCollectionWrapperImpl(getCompassCollection(), jsonSink, jsonSource);
        return mongoDbCollectionWrapper;
    }

    @Bean(name = "mongoDbExceptionManagementCollectionWrapper")
    public MongoDbCollectionWrapper getMongoDbExceptionManagementCollectionWrapper() throws UnknownHostException {
        MongoDbCollectionWrapper mongoDbCollectionWrapper
                = new MongoDbCollectionWrapperImpl(getExceptionManagementCollection(), jsonSink, jsonSource);
        return mongoDbCollectionWrapper;
    }

    @Bean(name = "fixmlCollection")
    public DBCollection getFixmlCollection() throws UnknownHostException {
        MongoClient mongoClient = getMongoClient();
        return mongoClient.getDB(mongoDBName).getCollection(mongoFixmlCollectionName);
    }

    @Bean(name = "compassCollection")
    public DBCollection getCompassCollection() throws UnknownHostException {
        MongoClient mongoClient = getMongoClient();
        return mongoClient.getDB(mongoDBName).getCollection(mongoCompassCollectionName);
    }

    @Bean(name = "exceptionManagementCollection")
    public DBCollection getExceptionManagementCollection() throws UnknownHostException {
        MongoClient mongoClient = getMongoClient();
        return mongoClient.getDB(mongoDBName).getCollection(mongoExceptionManagementCollectionName);
    }

    @Bean
    public MongoClient getMongoClient() throws UnknownHostException {
        return new MongoClient(mongoServer, mongoPort);
    }
}