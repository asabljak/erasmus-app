package hr.tvz.master.erasmus.repository;

import hr.tvz.master.erasmus.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends  JpaRepository<Course,Long> {
}
