package biz.c24.io.fixml.sample.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

/**
 * Created on behalf of C24 Technologies Ltd.
 *
 * @author mvickery
 * @since 10/04/2013
 */
@ImportResource(value = {
        "classpath:META-INF/spring/mongo-exception-management.xml",
        "classpath:META-INF/spring/mongo-store-compass.xml",
        "classpath:META-INF/spring/flow-config.xml"
})
@Import({
        C24ExternalPropertiesConfiguration.class,
        C24iOConfiguration.class,
        C24MongoDbConfiguration.class,
        C24DbConfiguration.class}
)
@Configuration
public class C24MongoDbFlowConfiguration {

}