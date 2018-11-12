package hr.tvz.master.erasmus.repository;

import hr.tvz.master.erasmus.entity.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
}
