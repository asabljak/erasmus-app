package hr.tvz.master.erasmus.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Course extends AbstractErasmusEntity {
    private String name;
    private String description;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Field> fields = new ArrayList<>();
    @OneToMany(fetch = FetchType.LAZY)
    private List<Subject> subjects = new ArrayList<>();

//    @Id
//    @GeneratedValue
//    @Column
//    protected Long id;
//    public String getName() {
//        return name;
//    }

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

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }
}
