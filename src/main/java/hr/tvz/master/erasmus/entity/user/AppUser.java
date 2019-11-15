package hr.tvz.master.erasmus.entity.user;

import hr.tvz.master.erasmus.entity.AbstractErasmusEntity;
import hr.tvz.master.erasmus.entity.institution.Field;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
public class AppUser extends AbstractErasmusEntity {

    private String name;
    private String surname;
    private String email;
    private String password;
    private String jmbag;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    private String phone;

    @OneToOne(targetEntity = Field.class)
    private Field homeCourse;

    private Integer yearOfStudy;

    private Boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "appUser.id"), inverseJoinColumns = @JoinColumn(name = "role.id"))
    private Set<Role> roles;

    public AppUser() {
    }

    public AppUser(AppUser appUser) {
        this.id = appUser.getId();
        this.name = appUser.getName();
        this.surname = appUser.getSurname();
        this.email = appUser.getEmail();
        this.password = appUser.getPassword();
        this.jmbag = appUser.getJmbag();
        this.birthday = appUser.getBirthday();
        this.phone = appUser.getPhone();
        this.homeCourse = appUser.getHomeCourse();
        this.yearOfStudy = appUser.getYearOfStudy();
        this.enabled = appUser.isEnabled();
        this.roles = appUser.getRoles();
        this.created = appUser.getCreated();
        this.updated = appUser.getUpdated();
        this.version = appUser.getVersion();
    }


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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getJmbag() {
        return jmbag;
    }

    public void setJmbag(String jmbag) {
        this.jmbag = jmbag;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Field getHomeCourse() {
        return homeCourse;
    }

    public void setHomeCourse(Field homeCourse) {
        this.homeCourse = homeCourse;
    }

    public Integer getYearOfStudy() {
        return yearOfStudy;
    }

    public void setYearOfStudy(Integer yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public boolean isValid() {
        return Stream.of(name, surname, email, roles)
                .noneMatch(Objects::isNull);
    }

    @Override
    public String toString() {
        StringBuilder appUserString =  new StringBuilder(this.name + " " + this.surname);
        if (!StringUtils.isEmpty(this.jmbag)) {
            appUserString.append(" (").append(this.jmbag).append(")");
        }

        return new String(appUserString);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AppUser)) return false;
        AppUser appUser = (AppUser) o;
        return Objects.equals(getName(), appUser.getName()) &&
                Objects.equals(getSurname(), appUser.getSurname()) &&
                Objects.equals(getEmail(), appUser.getEmail()) &&
                Objects.equals(getPassword(), appUser.getPassword()) &&
                Objects.equals(getJmbag(), appUser.getJmbag()) &&
                Objects.equals(getBirthday(), appUser.getBirthday()) &&
                Objects.equals(getPhone(), appUser.getPhone()) &&
                Objects.equals(getYearOfStudy(), appUser.getYearOfStudy()) &&
                Objects.equals(isEnabled(), appUser.isEnabled());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getSurname(), getEmail(), getPassword(), getJmbag(), getBirthday(), getPhone(),
                getHomeCourse(), getYearOfStudy(), isEnabled(), getRoles());
    }

    public List getIdRoles() {
        return roles.stream()
                .map(Role::getId)
                .collect(Collectors.toList());
    }
}
