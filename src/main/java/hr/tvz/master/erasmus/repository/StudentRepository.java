package hr.tvz.master.erasmus.repository;

import hr.tvz.master.erasmus.entity.user.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
//TODO trenutno prikazuje sve appUsere. napraviti da prikazuje samo studente
public interface StudentRepository extends JpaRepository<AppUser,Long> {
}
