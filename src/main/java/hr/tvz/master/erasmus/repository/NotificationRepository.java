package hr.tvz.master.erasmus.repository;

import hr.tvz.master.erasmus.entity.notification.Notification;
import hr.tvz.master.erasmus.entity.user.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification,Long> {

    List<Notification> findBySeenIsNull();
    List<Notification> findByReceiversContains(AppUser appUser);
}
