package hr.tvz.master.erasmus.repository;

import hr.tvz.master.erasmus.entity.institution.Institution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstitutionRepository extends  JpaRepository<Institution,Long> {
}
