package biz.c24.io.fixml.sample.configuration;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * Created on behalf of C24 Technologies Ltd.
 *
 * @author Matt Vickery
 * @since 09/04/2013
 */
@Configuration
public class C24ExternalPropertiesConfiguration {

    @Bean
    public static PropertyPlaceholderConfigurer getProperties() {
        PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
        ppc.setLocation(new ClassPathResource("META-INF/spring/application.properties"));
        ppc.setIgnoreUnresolvablePlaceholders(true);
        return ppc;
    }
}
