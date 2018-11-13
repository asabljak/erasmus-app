package hr.tvz.master.erasmus.entity.institution;

import hr.tvz.master.erasmus.entity.AbstractErasmusEntity;

import javax.persistence.Entity;
import java.util.Objects;
import java.util.stream.Stream;

@Entity
public class SemesterType extends AbstractErasmusEntity {
    public static final Long SUMMER = new Long(1);
    public static final Long WINTER = new Long(2);

    private String name;
    private String code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isValid() {
        return Stream.of(name, code)
                .noneMatch(Objects::isNull);
    }
}
