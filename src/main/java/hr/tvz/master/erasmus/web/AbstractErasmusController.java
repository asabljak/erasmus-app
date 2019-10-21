package hr.tvz.master.erasmus.web;

import hr.tvz.master.erasmus.entity.user.AppUser;
import org.springframework.security.core.context.SecurityContextHolder;

public class AbstractErasmusController {
    public AppUser getLoggedInUser() {
        return (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
