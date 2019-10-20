package hr.tvz.master.erasmus.entity.mobility;

import hr.tvz.master.erasmus.entity.AbstractErasmusEntity;

import javax.persistence.Entity;
import java.util.Objects;
import java.util.stream.Stream;

@Entity
public class MobilityStatus extends AbstractErasmusEntity {
    public static final Long REQUESTED = new Long(1);
    public static final Long CREATED = new Long(2);
    public static final Long REJECTED = new Long(3);
    public static final Long HOST_REJECTED = new Long(4);
    public static final Long ACTIVE = new Long(5);
    public static final Long CANCELLED = new Long(6);
    public static final Long DONE = new Long(7);

//    private String code;
    private String name;
    private String description;

    public MobilityStatus() {
    }

    public MobilityStatus(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

//    public String getCode() {
//        return code;
//    }
//
//    public void setCode(String code) {
//        this.code = code;
//    }

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
