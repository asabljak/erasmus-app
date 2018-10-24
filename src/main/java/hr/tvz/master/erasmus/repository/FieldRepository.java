package hr.tvz.master.erasmus.repository;

import hr.tvz.master.erasmus.entity.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface FieldRepository extends CrudRepository<Field, Long>, JpaRepository<Field,Long> {
}
