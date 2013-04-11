package biz.c24.io.fixml.sample.application;

import biz.c24.io.fixml.sample.configuration.C24MongoDbPollerConfiguration;
import biz.c24.io.fixml.sample.configuration.C24ExternalPropertiesConfiguration;
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
public class MongoDbToFileRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(MongoDbToFileRunner.class);
    private static final String BEAN_CONFIGURATION_ERROR = "Bean configuration error.";

    public static void main(String[] args) {

        System.out.println("Starting application...(loading Spring Integration contexts)");
        System.setProperty("IO_HOME", "/Applications/C24");
        new MongoDbToFileRunner().loadSpringContainer();
        System.out.println("...loaded.");
    }

    private void loadSpringContainer() {
        try {
            AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
            
            applicationContext.register(C24ExternalPropertiesConfiguration.class);
            applicationContext.register(C24MongoDbPollerConfiguration.class);
            applicationContext.refresh();

            if (applicationContext.containsBean("mongo-inbound-adapter")) {
                LOGGER.info("Triggering start of file consumption..");
                Object bean = applicationContext.getBean("mongo-inbound-adapter");
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