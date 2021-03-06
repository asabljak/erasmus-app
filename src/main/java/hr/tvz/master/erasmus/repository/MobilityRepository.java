package hr.tvz.master.erasmus.repository;

import hr.tvz.master.erasmus.entity.mobility.Mobility;
import hr.tvz.master.erasmus.entity.user.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MobilityRepository extends  JpaRepository<Mobility,Long> {
    List<Mobility> findAllByMobilityStatus_Id(long mobilityStatusId);
    List<Mobility> findAllByStudent(AppUser student);
}
