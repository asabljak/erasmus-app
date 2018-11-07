package hr.tvz.master.erasmus.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

@Entity
public class AppUser extends AbstractErasmusEntity{
    private String name;
    private String surname;
    private String email;
    private String password;
    private String jmbag;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    private String phone;

    @OneToOne(targetEntity = Course.class)
    private Course homeCourse;

    private Integer yearOfStudy;

    private int active;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "appUser.id"), inverseJoinColumns = @JoinColumn(name = "role.id"))   //TODO popraviti join
    private Set<Role> roles;

    public AppUser() {
    }

    public AppUser(AppUser appUser) {
        this.name = appUser.getName();
        this.surname = appUser.getSurname();
        this.email = appUser.getEmail();
        this.password = appUser.getPassword();
        this.jmbag = appUser.getJmbag();
        this.birthday = appUser.getBirthday();
        this.phone = appUser.getPhone();
        this.homeCourse = appUser.getHomeCourse();
        this.yearOfStudy = appUser.getYearOfStudy();
        this.active = appUser.getActive();
        this.roles = appUser.getRoles();
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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Course getHomeCourse() {
        return homeCourse;
    }

    public void setHomeCourse(Course homeCourse) {
        this.homeCourse = homeCourse;
    }

    public Integer getYearOfStudy() {
        return yearOfStudy;
    }

    public void setYearOfStudy(Integer yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean isValid() {
        return Stream.of(name, surname, email)
                .noneMatch(Objects::isNull);
    }
}
