package hr.tvz.master.erasmus.repository;

import hr.tvz.master.erasmus.entity.notification.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationTypeRepository extends JpaRepository<NotificationType,Long> {
}
