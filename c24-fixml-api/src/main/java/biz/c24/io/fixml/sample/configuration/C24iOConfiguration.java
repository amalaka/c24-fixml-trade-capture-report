package biz.c24.io.fixml.sample.configuration;

import biz.c24.io.api.presentation.JsonSink;
import biz.c24.io.api.presentation.JsonSource;
import biz.c24.io.spring.core.C24Model;
import biz.c24.io.spring.source.XmlSourceFactory;
import com.smoke.test.ConsolePrinter;
import org.fixprotocol.fixml44.FIXMLElement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created on behalf of C24 Technologies Ltd.
 *
 * @author mvickery
 * @since 03/04/2013
 */
//@ImportResource(value = {
//        "classpath:META-INF/spring/flow-config.xml"
//})
@Configuration
public class C24iOConfiguration {

    @Bean(name = "debug")
    public ConsolePrinter getConsolePrinter() {
        return new ConsolePrinter();
    }

    @Bean(name = "xmlSourceFactory")
    public XmlSourceFactory getTextualSourceFactory() {
        XmlSourceFactory xmlSourceFactory = new XmlSourceFactory();
        xmlSourceFactory.setEncoding("UTF-8");
        return xmlSourceFactory;
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
    public JsonSink getJsonSink() {
        return new JsonSink();
    }

    @Bean
    public JsonSource getJsonSource() {
        return new JsonSource();
    }
}