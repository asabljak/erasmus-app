package hr.tvz.master.erasmus.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Entity
public class Course extends AbstractErasmusEntity {
    private String name;
    private String description;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Field> fields = new ArrayList<>();

    @OneToOne(targetEntity = Institution.class)
    private  Institution institution;

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

    public Institution getInstitution() {
        return institution;
    }

    public void setInstitution(Institution institution) {
        this.institution = institution;
    }

    public boolean isValid() {
        return Stream.of(name, description, fields, institution)
                .noneMatch(Objects::isNull);
    }
}
