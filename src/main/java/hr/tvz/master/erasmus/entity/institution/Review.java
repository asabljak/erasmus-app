package hr.tvz.master.erasmus.entity.institution;

import hr.tvz.master.erasmus.entity.AbstractErasmusEntity;
import hr.tvz.master.erasmus.entity.user.AppUser;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.util.Objects;
import java.util.stream.Stream;

@Entity
public class Review extends AbstractErasmusEntity {

    @OneToOne(targetEntity = Institution.class)
    private Institution institution;

    @OneToOne(targetEntity = AppUser.class)
    private AppUser appUser;

    private Double rating;

    private String pros;

    private String cons;

    private String opinion;

    //Getters and setters

    public Institution getInstitution() {
        return institution;
    }

    public void setInstitution(Institution institution) {
        this.institution = institution;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getPros() {
        return pros;
    }

    public void setPros(String pros) {
        this.pros = pros;
    }

    public String getCons() {
        return cons;
    }

    public void setCons(String cons) {
        this.cons = cons;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    @Override
    protected boolean isValid() {
        return Stream.of(institution, appUser, rating, pros, cons, opinion)
                .noneMatch(Objects::isNull);
    }
}
