package app.core;

import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * Created by anton_arakcheyev on 02/12/2018.
 */

@Configuration
@PropertySource({
        "classpath:resources/app.properties"
})
@ComponentScan({
        "app.core"
})
@ImportResource({
    /*"classpath:*Hibernate-context.xml"*/
})
public class RootConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer(){
        return new PropertySourcesPlaceholderConfigurer();

    }
}
