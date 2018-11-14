package hr.tvz.master.erasmus.repository;

import hr.tvz.master.erasmus.entity.institution.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    List<Subject> findAllByCourse_Id(Long courseId);
}
