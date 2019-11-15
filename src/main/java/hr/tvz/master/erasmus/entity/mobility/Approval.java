package hr.tvz.master.erasmus.entity.mobility;

import hr.tvz.master.erasmus.entity.AbstractErasmusEntity;
import hr.tvz.master.erasmus.entity.document.Document;
import hr.tvz.master.erasmus.entity.user.AppUser;

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

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Document> documents;

    @OneToOne
    private Mobility mobility;

    private Boolean isSuccessful;

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

    public Boolean isSuccessful() {
        return isSuccessful;
    }

    public void setSuccessful(Boolean successful) {
        isSuccessful = successful;
    }

    @Override
    public boolean isValid() {
        return false;
    }
}
