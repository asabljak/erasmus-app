package hr.tvz.master.erasmus.entity.user;

import hr.tvz.master.erasmus.entity.AbstractErasmusEntity;

import javax.persistence.*;

@Entity
public class Role extends AbstractErasmusEntity {
    public static final Long ROLE_ADMIN = new Long(1);
    public static final Long ROLE_COORDINATOR = new Long(2);
    public static final Long ROLE_SUBJECT_COORDINATOR = new Long(3);
    public static final Long ROLE_ERASMUS_STUDENT = new Long(4);
    public static final Long ROLE_VISITOR = new Long(5);

    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    protected boolean isValid() {
        return role != null && role.length() > 0;
    }
}
