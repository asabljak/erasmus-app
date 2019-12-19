package hr.tvz.master.erasmus.entity.mobility;

import hr.tvz.master.erasmus.entity.AbstractErasmusEntity;
import hr.tvz.master.erasmus.entity.institution.Institution;
import hr.tvz.master.erasmus.entity.user.AppUser;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.time.LocalDate;
import java.util.Objects;
import java.util.stream.Stream;

@Entity
public class Mobility extends AbstractErasmusEntity {

    @OneToOne(targetEntity = AppUser.class)
    private AppUser student;

    @OneToOne(targetEntity = Institution.class)
    private Institution institution;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate mobilityStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate mobilityEnd;

    @OneToOne(targetEntity = MobilityStatus.class)
    private MobilityStatus mobilityStatus; //TODO treba li ovo uopće??

    private Integer points;

    //Getters and setters

    public AppUser getStudent() {
        return student;
    }

    public void setStudent(AppUser student) {
        this.student = student;
    }

    public Institution getInstitution() {
        return institution;
    }

    public void setInstitution(Institution institution) {
        this.institution = institution;
    }

    public LocalDate getMobilityStart() {
        return mobilityStart;
    }

    public void setMobilityStart(LocalDate mobilityStart) {
        this.mobilityStart = mobilityStart;
    }

    public LocalDate getMobilityEnd() {
        return mobilityEnd;
    }

    public void setMobilityEnd(LocalDate mobilityEnd) {
        this.mobilityEnd = mobilityEnd;
    }

    public MobilityStatus getMobilityStatus() {
        return mobilityStatus;
    }

    public void setMobilityStatus(MobilityStatus mobilityStatus) {
        this.mobilityStatus = mobilityStatus;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public boolean isValid() {
        return Stream.of(student, institution, mobilityStatus)
                .noneMatch(Objects::isNull);
    }
}
