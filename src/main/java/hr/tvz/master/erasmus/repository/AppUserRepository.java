package hr.tvz.master.erasmus.repository;

import hr.tvz.master.erasmus.entity.user.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByEmail(String email);
//    List<AppUser> findAllByRoles(Role role);
    List<AppUser> findAllByRoles_Id(Long id);
}
