package hr.tvz.master.erasmus.repository;

import hr.tvz.master.erasmus.entity.institution.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
}
