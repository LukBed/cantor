package pl.lukbed.ecantor.courses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Getter
public class CoursesStatus {
    @JsonProperty("publicationDate") private LocalDateTime publicationDate;
    @JsonProperty("items") private List<Course> items;

    public Optional<Course> getCourseOfCode(String code) {
        return items.stream()
                .filter(course -> course.getCode().equals(code))
                .findFirst();
    }
}
