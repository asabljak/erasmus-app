package hr.tvz.master.erasmus.repository;

import hr.tvz.master.erasmus.entity.institution.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends  JpaRepository<Course,Long> {
    List<Course> findByInstitution_Id(Long institutionId);
}
