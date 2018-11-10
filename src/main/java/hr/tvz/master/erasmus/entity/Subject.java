package hr.tvz.master.erasmus.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import java.util.Objects;
import java.util.stream.Stream;

@Entity
public class Subject extends AbstractErasmusEntity {
    private String name;

    private String description;

    private Integer ectsValue;

    private String language;

    @OneToOne(targetEntity = Course.class, fetch = FetchType.EAGER)
    private Course course;

    //Getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getEctsValue() {
        return ectsValue;
    }

    public void setEctsValue(Integer ectsValue) {
        this.ectsValue = ectsValue;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public boolean isValid() {
        return Stream.of(name, description, ectsValue, language)
                .noneMatch(Objects::isNull);
    }
}
