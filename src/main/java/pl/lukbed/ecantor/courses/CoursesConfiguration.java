package pl.lukbed.ecantor.courses;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

@Configuration
class CoursesConfiguration {
    @Bean
    CoursesService currencyApi(@Value("${ecantor.currency.external-webservice-url}") String webserviceUrl) {
        return new CoursesService(webserviceUrl);
    }
}
