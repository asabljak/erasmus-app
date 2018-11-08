package hr.tvz.master.erasmus.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
//TODO relation mobility deos not exists?
@Entity
public class Mobility extends AbstractErasmusEntity {

    @OneToOne(targetEntity = AppUser.class)
    AppUser student;

    @OneToOne(targetEntity = Institution.class)
    Institution institution;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate mobilityStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate mobilityEnd;

    @ManyToMany(fetch = FetchType.LAZY)
    List<Approval> approvals;

    @OneToOne(targetEntity = MobilityStatus.class)
    MobilityStatus mobilityStatus;

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

    public List<Approval> getApprovals() {
        return approvals;
    }

    public void setApprovals(List<Approval> approvals) {
        this.approvals = approvals;
    }

    public MobilityStatus getMobilityStatus() {
        return mobilityStatus;
    }

    public void setMobilityStatus(MobilityStatus mobilityStatus) {
        this.mobilityStatus = mobilityStatus;
    }

    public boolean isValid() {
        return Stream.of(student, institution, mobilityStatus)
                .noneMatch(Objects::isNull);
    }
}
