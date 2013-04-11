package biz.c24.io.fixml.sample.configuration;

import biz.c24.io.api.presentation.JsonSource;
import biz.c24.io.fixml.sample.converter.XMLConverter;
import biz.c24.io.fixml.sample.enricher.CdoEnricher;
import biz.c24.io.fixml.sample.reader.MongoDbReader;
import biz.c24.io.fixml.sample.storage.CompassFileStore;
import biz.c24.io.spring.core.C24Model;
import com.smoke.test.ConsolePrinter;
import org.fixprotocol.fixml44.FIXMLElement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import java.io.File;

/**
 * Created on behalf of C24 Technologies Ltd.
 *
 * @author mvickery
 * @since 03/04/2013
 */
@ImportResource(value = {
        "classpath:META-INF/spring/flow-db-read.xml"
})
@Configuration
public class C24MongoDbPollerConfiguration {

    @Value("${mongo.db.server}")
    private String mongoServer;
    @Value("${mongo.db.port}")
    private int mongoPort;
    @Value("${mongo.db.name}")
    private String mongoDBName;
    @Value("${mongo.collection.fixml.name}")
    private String mongoCollectionName;

    @Value("${file.source.dir")
    private String fileSource;
    @Value("${file.compass.valid.dir}")
    private String validDir;
    @Value("${file.compass.valid.dir}")
    private String invalidDir;

    @Bean(name = "debug")
    public ConsolePrinter getConsolePrinter() {
        return new ConsolePrinter();
    }

    @Bean(name = "fixmlModel")
    public C24Model getFixmlModel() {
        return new C24Model(getFIXMLElement());
    }

    @Bean
    public FIXMLElement getFIXMLElement() {
        return new FIXMLElement();
    }

    @Bean
    public JsonSource getJsonSource()
    {
        JsonSource jsonSource = new JsonSource();
        return jsonSource;
    }

    @Bean(name = "parse-json")
    public MongoDbReader getMongoDbReader()
    {
        MongoDbReader mongoReader = new MongoDbReader(getJsonSource(), getFixmlModel());
        return mongoReader;
    }

    @Bean(name = "header-enricher")
    public CdoEnricher getEnricher()
    {
        return new CdoEnricher();
    }

    @Bean(name = "xml-converter")
    public XMLConverter getXMLConverter()
    {
        return new XMLConverter();
    }

    @Bean(name = "compass-file-store")
    public CompassFileStore getFileUtils()
    {
        return new CompassFileStore(getValidDir(), getInvalidDir());
    }

    @Bean
    public File getValidDir()
    {
        return new File(validDir);
    }
    @Bean
    public File getInvalidDir()
    {
        return new File(invalidDir);
    }
}