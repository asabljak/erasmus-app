package hr.tvz.master.erasmus.entity;

import javax.persistence.Entity;
import java.util.Objects;
import java.util.stream.Stream;

@Entity
public class ApprovalType extends AbstractErasmusEntity {
    private String name;
    private String description;

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

    public boolean isValid() {
        return Stream.of(name, description)
                .noneMatch(Objects::isNull);
    }
}
