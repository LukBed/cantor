package pl.lukbed.ecantor.courses;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

public class CoursesService {
    private final String url;

    CoursesService(String url) {
        this.url = url;
    }

    public CoursesStatus getCurrencyCourses() {
        var restTemplate = new RestTemplate();
        var httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>("body", httpHeaders);
        return restTemplate.exchange(url, HttpMethod.GET, entity, CoursesStatus.class).getBody();
    }
}
