package hr.tvz.master.erasmus.repository;

import hr.tvz.master.erasmus.entity.Approval;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApprovalRepository extends JpaRepository<Approval,Long> {
}
