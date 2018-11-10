package hr.tvz.master.erasmus.entity.mobility;

import hr.tvz.master.erasmus.entity.AbstractErasmusEntity;
import hr.tvz.master.erasmus.entity.user.AppUser;
import hr.tvz.master.erasmus.entity.document.Document;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import java.util.List;

@Entity
public class Approval extends AbstractErasmusEntity {

    @OneToOne(targetEntity = ApprovalType.class)
    private ApprovalType approvalType;

    @OneToOne(targetEntity = AppUser.class)
    private AppUser coordinator;

//    @OneToOne(targetEntity = AppUser.class)
//    private AppUser student;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Document> documents;

    @OneToOne
    private Mobility mobility;

    private String comment;

    //Getters and setters

    public ApprovalType getApprovalType() {
        return approvalType;
    }

    public void setApprovalType(ApprovalType approvalType) {
        this.approvalType = approvalType;
    }

    public AppUser getCoordinator() {
        return coordinator;
    }

    public void setCoordinator(AppUser coordinator) {
        this.coordinator = coordinator;
    }

//    public AppUser getStudent() {
//        return student;
//    }
//
//    public void setStudent(AppUser student) {
//        this.student = student;
//    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    public Mobility getMobility() {
        return mobility;
    }

    public void setMobility(Mobility mobility) {
        this.mobility = mobility;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean isValid() {
        return false;
    }
}
