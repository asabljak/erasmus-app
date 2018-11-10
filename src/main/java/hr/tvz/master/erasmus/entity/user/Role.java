package hr.tvz.master.erasmus.entity.user;

import hr.tvz.master.erasmus.entity.AbstractErasmusEntity;

import javax.persistence.*;

@Entity
public class Role extends AbstractErasmusEntity {

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
