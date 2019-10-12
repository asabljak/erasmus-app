package hr.tvz.master.erasmus.repository;

import hr.tvz.master.erasmus.entity.notification.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification,Long> {

//    @Query("SELECT n FROM Notification n WHERE :appUser IN n.receivers AND n.seen IS NULL")
    List<Notification> findBySeenIsNull();
}
