package hr.tvz.master.erasmus.entity.document;

import hr.tvz.master.erasmus.entity.AbstractErasmusEntity;
import hr.tvz.master.erasmus.entity.mobility.MobilityStatus;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Entity
public class DocumentType extends AbstractErasmusEntity {
    private String code;
    private String name;
    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<MobilityStatus> phases;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

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

    public List<MobilityStatus> getPhases() {
        return phases;
    }

    public void setPhases(List<MobilityStatus> phases) {
        this.phases = phases;
    }

    public boolean isValid() {
        return Stream.of(code, name, description, phases)
                .noneMatch(Objects::isNull);
    }
}
