package hr.tvz.master.erasmus.repository;

import hr.tvz.master.erasmus.entity.Field;
import hr.tvz.master.erasmus.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface SubjectRepository extends CrudRepository<Subject, Long>, JpaRepository<Subject, Long> {
}
