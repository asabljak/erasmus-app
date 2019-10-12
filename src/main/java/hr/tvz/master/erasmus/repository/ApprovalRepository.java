package hr.tvz.master.erasmus.repository;

import hr.tvz.master.erasmus.entity.mobility.Approval;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApprovalRepository extends JpaRepository<Approval,Long> {
//    List<Approval> findByMobility_Id(Long mobilityId);
}
