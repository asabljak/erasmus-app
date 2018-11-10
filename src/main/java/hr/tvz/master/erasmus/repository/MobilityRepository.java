package hr.tvz.master.erasmus.repository;

import hr.tvz.master.erasmus.entity.mobility.Mobility;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MobilityRepository extends  JpaRepository<Mobility,Long> {
}
