package hr.tvz.master.erasmus.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Stream;

@Entity
public class Student extends AppUser {
    private String jmbag;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    private String phone;

    @OneToOne(targetEntity = Course.class)
    private Course homeCourse;
    private Integer yearOfStudy;

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

//    public String getBirthday() {
//        return birthday;
//    }
//
//    public void setBirthday(String birthday) {
//        this.birthday = birthday;
//    }

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

    @Override
    public boolean isValid() {
        return super.isValid()
                && Stream.of(jmbag, birthday, homeCourse, phone, yearOfStudy)
                .noneMatch(Objects::isNull);
    }
}
