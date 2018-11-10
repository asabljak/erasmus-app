package hr.tvz.master.erasmus.repository;

import hr.tvz.master.erasmus.entity.mobility.MobilityStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MobilityStatusRepository extends JpaRepository<MobilityStatus, Long> {
}
