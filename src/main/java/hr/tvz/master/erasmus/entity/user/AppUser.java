package hr.tvz.master.erasmus.entity.user;

import hr.tvz.master.erasmus.entity.institution.Field;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

@Entity
public class AppUser {
    @Id
    @GeneratedValue
    @Column
    protected Long id;

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

    @Column
    @CreationTimestamp
    protected LocalDateTime created;

    @Column
    @UpdateTimestamp
    protected LocalDateTime updated;

    @Column
    @Version
    protected Integer version;

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
        this.enabled = appUser.isEnabled();
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public boolean isValid() {
        return Stream.of(name, surname, email, roles)
                .noneMatch(Objects::isNull);
    }

    @Override
    public String toString() {
        StringBuilder appUserString =  new StringBuilder(this.name + " " + this.surname);
        if (this.jmbag != null) {
            appUserString.append(" (" + this.jmbag + ")");
        }

        return new String(appUserString);
    }
}
