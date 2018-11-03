package hr.tvz.master.erasmus.repository;

import hr.tvz.master.erasmus.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,Long> {
}
