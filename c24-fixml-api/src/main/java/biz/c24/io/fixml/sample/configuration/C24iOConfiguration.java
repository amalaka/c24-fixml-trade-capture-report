package biz.c24.io.fixml.sample.configuration;

import biz.c24.io.api.data.Element;
import biz.c24.io.api.presentation.JsonSink;
import biz.c24.io.fixml.sample.storage.MongoDbWriter;
import biz.c24.io.spring.core.C24Model;
import biz.c24.io.spring.source.XmlSourceFactory;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.smoke.test.ConsolePrinter;
import org.fixprotocol.fixml44.FIXMLElement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;

import java.net.UnknownHostException;

/**
 * Created on behalf of C24 Technologies Ltd.
 *
 * @author mvickery
 * @since 03/04/2013
 */
@ImportResource(value = {
        "classpath:META-INF/spring/gem-exception-management.xml",
        "classpath:META-INF/spring/flow-config.xml"
})
@PropertySource(value = "classpath:META-INF/spring/application.properties")
@Configuration
public class C24iOConfiguration {

    @Value("${mongo.db.server}")
    private String mongoServer;
    @Value("${mongo.db.port}")
    private int mongoPort;
    @Value("${mongo.db.name}")
    private String mongoDBName;
    @Value("${mongo.collection.messages.name}")
    private String mongoCollectionName;

    @Bean(name = "debug")
    public ConsolePrinter getConsolePrinter() {
        return new ConsolePrinter();
    }

    @Bean
    public static PropertyPlaceholderConfigurer getProperties() {
        PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
        ppc.setLocation(new ClassPathResource("META-INF/spring/application.properties"));
        ppc.setIgnoreUnresolvablePlaceholders(true);
        return ppc;
    }

    @Bean(name = "mongoDbFixMlCollectionWriter")
    public MongoDbWriter getMongoDbWriter() throws UnknownHostException {
        MongoDbWriter mongoDbWriter = new MongoDbWriter(getFIXMLCollection(), getJsonSink());
        return mongoDbWriter;
    }

    @Bean(name = "xmlSourceFactory")
    public XmlSourceFactory getTextualSourceFactory() {
        XmlSourceFactory xmlSourceFactory = new XmlSourceFactory();
        xmlSourceFactory.setEncoding("UTF-8");
        return xmlSourceFactory;
    }

    @Bean(name = "fixmlModel")
    public C24Model getFixmlModel() {
        Element element = new FIXMLElement();
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
        return mongoClient.getDB(mongoDBName).getCollection(mongoCollectionName);
    }
}