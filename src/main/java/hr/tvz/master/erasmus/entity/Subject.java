package hr.tvz.master.erasmus.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Subject extends AbstractErasmusEntity {
    private String name;
    private String description;
    private int ectsValue;
    private String language;

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

    public int getEctsValue() {
        return ectsValue;
    }

    public void setEctsValue(int ectsValue) {
        this.ectsValue = ectsValue;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
