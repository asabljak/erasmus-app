package hr.tvz.master.erasmus.repository;

import hr.tvz.master.erasmus.entity.mobility.Approval;
import hr.tvz.master.erasmus.entity.mobility.Mobility;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApprovalRepository extends JpaRepository<Approval,Long> {
    List<Approval> findByMobility(Mobility mobility);
    List<Approval> findByMobility_Id(Long mobilityId);
}
