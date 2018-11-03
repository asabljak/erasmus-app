package hr.tvz.master.erasmus.entity;

import javax.persistence.MappedSuperclass;
import java.util.Objects;
import java.util.stream.Stream;

@MappedSuperclass
public class AppUser extends AbstractErasmusEntity{
    private String name;
    private String surname;
    private String email;
//    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    protected boolean isValid() {
        return Stream.of(name, surname, email)
                .noneMatch(Objects::isNull);
    }
}
