package hr.tvz.master.erasmus.entity.notification;

import hr.tvz.master.erasmus.entity.AbstractErasmusEntity;
import hr.tvz.master.erasmus.entity.mobility.Approval;
import hr.tvz.master.erasmus.entity.user.AppUser;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Entity
public class Notification extends AbstractErasmusEntity {

    @OneToOne(targetEntity = AppUser.class)
    private AppUser sender;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<AppUser> receivers;

    @OneToOne(targetEntity = Approval.class)
    private Approval approval;

    private String message;

    private LocalDateTime seen;

    @OneToOne(targetEntity = AppUser.class)
    private AppUser seenBy;

    private boolean actionRequired;

    //Getters and setters

    public AppUser getSender() {
        return sender;
    }

    public void setSender(AppUser sender) {
        this.sender = sender;
    }

    public List<AppUser> getReceivers() {
        return receivers;
    }

    public void setReceivers(List<AppUser> receivers) {
        this.receivers = receivers;
    }

    public Approval getApproval() {
        return approval;
    }

    public void setApproval(Approval approval) {
        this.approval = approval;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getSeen() {
        return seen;
    }

    public void setSeen(LocalDateTime seen) {
        this.seen = seen;
    }

    public AppUser getSeenBy() {
        return seenBy;
    }

    public void setSeenBy(AppUser seenBy) {
        this.seenBy = seenBy;
    }

    public boolean isActionRequired() {
        return actionRequired;
    }

    public void setActionRequired(boolean actionRequired) {
        this.actionRequired = actionRequired;
    }

    @Override
    protected boolean isValid() {
        return Stream.of(sender, receivers, approval)
                .noneMatch(Objects::isNull);
    }

    @Override
    public String toString() {
        return this.getMessage();
    }
}
