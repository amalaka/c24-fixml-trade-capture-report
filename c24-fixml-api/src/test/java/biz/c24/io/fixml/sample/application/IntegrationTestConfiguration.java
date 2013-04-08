package biz.c24.io.fixml.sample.application;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * Created on behalf of C24 Technologies Ltd.
 *
 * @author mvickery
 * @since 08/04/2013
 */
@ImportResource(value = {
        "classpath:META-INF/spring/test-cache-channel-queue.xml",
        "classpath:META-INF/spring/test-c24-db-store.xml"
})
@Configuration
public class IntegrationTestConfiguration {
}
