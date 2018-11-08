package hr.tvz.master.erasmus.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import java.util.List;
//TODO dodati mobility
@Entity
public class Approval extends AbstractErasmusEntity {

    @OneToOne(targetEntity = ApprovalType.class)
    private ApprovalType approvalType;

    @OneToOne(targetEntity = AppUser.class)
    private AppUser coordinator;

    @OneToOne(targetEntity = AppUser.class)
    private AppUser student;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Document> documents;

    private String comment;

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

    public AppUser getStudent() {
        return student;
    }

    public void setStudent(AppUser student) {
        this.student = student;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
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
