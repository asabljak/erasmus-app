package hr.tvz.master.erasmus.repository;

import hr.tvz.master.erasmus.entity.institution.SemesterType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SemesterTypeRepository extends JpaRepository<SemesterType, Long> {
}
