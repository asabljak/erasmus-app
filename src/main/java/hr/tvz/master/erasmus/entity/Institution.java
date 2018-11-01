package hr.tvz.master.erasmus.entity;

import javax.persistence.Entity;

@Entity
public class Institution extends AbstractErasmusEntity{
    private String name;
    private String city;
//    private Country Country;


    @Override
    protected boolean isValid() {
        return false;
    }
}
