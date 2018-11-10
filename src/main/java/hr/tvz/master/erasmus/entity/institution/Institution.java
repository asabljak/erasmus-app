package hr.tvz.master.erasmus.entity.institution;

import hr.tvz.master.erasmus.entity.AbstractErasmusEntity;

import javax.persistence.Entity;
import java.util.Objects;
import java.util.stream.Stream;

@Entity
public class Institution extends AbstractErasmusEntity {
    private String name;
    private String code;
    private String city;
    private String country;
    private Integer numberOfStudents;
    private String information;
    private String webUrl;

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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getNumberOfStudents() {
        return numberOfStudents;
    }

    public void setNumberOfStudents(Integer numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    @Override
    public boolean isValid() {
        return Stream.of(name, code, city, country, numberOfStudents)
                .noneMatch(Objects::isNull);
    }
}
