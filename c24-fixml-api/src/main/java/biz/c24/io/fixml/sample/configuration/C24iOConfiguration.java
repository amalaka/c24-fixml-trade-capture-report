package biz.c24.io.fixml.sample.configuration;

import biz.c24.io.api.data.Element;
import biz.c24.io.api.presentation.JsonSink;
import biz.c24.io.fixml.sample.storage.MongoDbWriter;
import biz.c24.io.spring.core.C24Model;
import biz.c24.io.spring.source.TextualSourceFactory;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.smoke.test.ConsolePrinter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import java.net.UnknownHostException;

/**
 * Created on behalf of C24 Technologies Ltd.
 *
 * @author mvickery
 * @since 03/04/2013
 */
@ImportResource(value = "classpath:META-INF/spring/flow-config.xml")
@Configuration
public class C24iOConfiguration {

    private final String mongoServer = "localhost";
    private final int mongoPort = 1723;
    private final String mongoDBName = "fixmlDB";
    private final String mongoCollectionName = "messages";

    @Bean(name = "debug")
    public ConsolePrinter getConsolePrinter() {
        return new ConsolePrinter();
    }

    @Bean(name = "mongoDbFixMlCollectionWriter")
    public MongoDbWriter getMongoDbWriter() throws UnknownHostException {
        MongoDbWriter mongoDbWriter = new MongoDbWriter(getFIXMLCollection(), getJsonSink());
        return mongoDbWriter;
    }

    @Bean(name = "textualSourceFactory")
    public TextualSourceFactory getTextualSourceFactory() {
        TextualSourceFactory textualSourceFactory = new TextualSourceFactory();
        textualSourceFactory.setEncoding("UTF-8");
        return textualSourceFactory;
    }

    @Bean(name = "fixmlModel")
    public C24Model getFixmlModel() {
        // TODO: get the correct element name for the FIXML element.
        Element element = new Element();
        return new C24Model(element);
    }

    @Bean
    public MongoClient getMongoClient() throws UnknownHostException {
        return new MongoClient(mongoServer, mongoPort);
    }

    @Bean
    public JsonSink getJsonSink() {
        return new JsonSink();
    }

    @Bean(name = "fixmlCollection")
    public DBCollection getFIXMLCollection() throws UnknownHostException {
        MongoClient mongoClient = getMongoClient();
        return  mongoClient.getDB(mongoDBName).getCollection(mongoCollectionName);
    }
}