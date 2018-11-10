package hr.tvz.master.erasmus.entity.institution;

import hr.tvz.master.erasmus.entity.AbstractErasmusEntity;

import javax.persistence.Entity;
import java.util.Objects;
import java.util.stream.Stream;

@Entity
public class Field extends AbstractErasmusEntity {
    private String code;
    private String name;

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

    @Override
    public boolean isValid() {
        return Stream.of(code, name)
                .noneMatch(Objects::isNull);
    }

    @Override
    public String toString() {
        return this.getCode() + " - " + this.getName();
    }
}
