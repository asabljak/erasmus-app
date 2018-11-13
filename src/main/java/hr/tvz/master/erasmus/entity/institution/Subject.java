package hr.tvz.master.erasmus.entity.institution;

import hr.tvz.master.erasmus.entity.AbstractErasmusEntity;

import javax.persistence.*;
import java.util.Objects;
import java.util.stream.Stream;

@Entity
public class Subject extends AbstractErasmusEntity {
    private String name;

    private String description;

    private Integer ectsValue;

    private String language;

    private Integer year;

    @ManyToOne(targetEntity = SemesterType.class)
    private SemesterType semesterType;

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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public SemesterType getSemesterType() {
        return semesterType;
    }

    public void setSemesterType(SemesterType semesterType) {
        this.semesterType = semesterType;
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
