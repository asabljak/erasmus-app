package hr.tvz.master.erasmus.service;

import hr.tvz.master.erasmus.entity.user.AppUser;
import hr.tvz.master.erasmus.entity.user.Role;
import hr.tvz.master.erasmus.repository.AppUserRepository;
import hr.tvz.master.erasmus.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class AppUserService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    public boolean userHasRole(AppUser appUser, Long roleId) {
        return appUser.getRoles().stream().anyMatch(r -> r.getId().equals(roleId));
    }

    public AppUser addRole(AppUser appUser, Long roleId) {
        Role newRole = roleRepository.getOne(roleId);
        Set<Role> roles = appUser.getRoles();
        roles.add(newRole);
        appUser.setRoles(roles);
        return appUser;
    }

    public List<AppUser> findAllByRoleId(Long roleId) {
        return appUserRepository.findAllByRoles_Id(roleId);
    }
}
