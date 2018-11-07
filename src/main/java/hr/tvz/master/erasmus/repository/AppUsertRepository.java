package hr.tvz.master.erasmus.repository;

import hr.tvz.master.erasmus.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUsertRepository extends JpaRepository<AppUser,Long> {
}
