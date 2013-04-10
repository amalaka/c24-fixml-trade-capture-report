package biz.c24.io.fixml.sample.application;

import biz.c24.io.fixml.sample.configuration.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.integration.endpoint.SourcePollingChannelAdapter;

/**
 * Created on behalf of C24 Technologies Ltd.
 *
 * @author mvickery
 * @since 03/04/2013
 */
public class FixmlToCompassToMongoDbRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(FixmlToCompassToMongoDbRunner.class);
    private static final String BEAN_CONFIGURATION_ERROR = "Bean configuration error.";

    public static void main(String[] args) {

        System.out.println("Starting application...(loading Spring Integration contexts)");
        System.setProperty("IO_HOME", "/Applications/C24");
        new FixmlToCompassToMongoDbRunner().loadSpringContainer();
        System.out.println("...loaded.");
    }

    private void loadSpringContainer() {
        try {
            AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
            
//            applicationContext.register(C24ExternalPropertiesConfiguration.class);
//            applicationContext.register(C24iOConfiguration.class);
//            applicationContext.register(C24MongoDbConfiguration.class);
//            applicationContext.register(C24DbConfiguration.class);
            
            applicationContext.register(C24MongoDbFlowConfiguration.class);
            applicationContext.refresh();

            if (applicationContext.containsBean("file-reading-adapter")) {
                LOGGER.info("Triggering start of file consumption..");
                Object bean = applicationContext.getBean("file-reading-adapter");
                if (bean instanceof SourcePollingChannelAdapter)
                    ((SourcePollingChannelAdapter) bean).start();
                else
                    throw new IllegalStateException(BEAN_CONFIGURATION_ERROR);
            } else {
                throw new IllegalStateException(BEAN_CONFIGURATION_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Error loading Spring container: ", e);
        }
    }
}