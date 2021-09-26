package org.springboot.SpringBootWithMongoDB.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class ValidationConfig {

    @Bean
    public ValidatingMongoEventListener validatingMongoEventListener() {
        //This is "EventListener" that will be trigger before persisting any data to the database and while persisting the data this will going
        //to trigger if user sends any "Null" values. So, this will go to sends "ConstraintViolationException".

        return new ValidatingMongoEventListener(validator());
    }

    @Bean
    public LocalValidatorFactoryBean validator() {
        //"LocalValidatorFactoryBean" class is the implementation class for validation.

        return new LocalValidatorFactoryBean();
    }
}
