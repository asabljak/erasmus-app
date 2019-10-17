package hr.tvz.master.erasmus.entity.mobility;

import hr.tvz.master.erasmus.entity.AbstractErasmusEntity;

import javax.persistence.Entity;
import java.util.Objects;
import java.util.stream.Stream;

@Entity
public class ApprovalType extends AbstractErasmusEntity {
    public static final Long APPLIED = new Long(1);
    public static final Long SUBJECTS = new Long(2);
    public static final Long DOCUMENTATION_BEFORE_DELIVERED = new Long(3);
    public static final Long DOCUMENTATION_AFTER_DELIVERED = new Long(4);
    public static final Long GRANT = new Long(5);
    public static final Long SCOLARSHIP_BEFORE = new Long(6);
    public static final Long SCHOLARSHIP_AFTER = new Long(7);
    public static final Long SOCIAL_SCHOLARSHIP = new Long(8);


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
