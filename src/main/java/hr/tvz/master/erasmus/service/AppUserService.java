package hr.tvz.master.erasmus.service;

import hr.tvz.master.erasmus.entity.user.AppUser;
import org.springframework.stereotype.Service;

@Service
public class AppUserService {

//    @Autowired
//    private DocumentRepository documentRepository;

    public boolean userHasRole(AppUser appUser, Long roleId) {
        return appUser.getRoles().stream().anyMatch(r -> r.getId().equals(roleId));
    }
}
