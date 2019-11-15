package hr.tvz.master.erasmus.entity.notification;

import hr.tvz.master.erasmus.entity.AbstractErasmusEntity;

import javax.persistence.Entity;
import java.util.Objects;
import java.util.stream.Stream;

@Entity
public class NotificationType extends AbstractErasmusEntity {
    public static final Long APPROVAL = new Long(1);
    public static final Long REVIEW = new Long(2);
    public static final Long INTERVIEW = new Long(3);
    public static final Long SUBJECTS_APPROVAL = new Long(4);
    public static final Long RESPONSE = new Long(5);

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
