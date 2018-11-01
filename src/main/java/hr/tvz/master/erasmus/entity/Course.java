package hr.tvz.master.erasmus.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Entity
public class Course extends AbstractErasmusEntity {
    private String name;
    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Field> fields = new ArrayList<>();
//    @OneToMany(fetch = FetchType.LAZY)
//    private List<Subject> subjects = new ArrayList<>();

    public String getName() {
        return this.name;
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

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

//    public List<Subject> getSubjects() {
//        return subjects;
//    }
//
//    public void setSubjects(List<Subject> subjects) {
//        this.subjects = subjects;
//    }

    public boolean isValid() {
        return Stream.of(name, description, fields)
                .noneMatch(Objects::isNull);
    }
}
