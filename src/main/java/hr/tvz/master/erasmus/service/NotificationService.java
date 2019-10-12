package hr.tvz.master.erasmus.service;

import hr.tvz.master.erasmus.entity.notification.Notification;
import hr.tvz.master.erasmus.entity.user.AppUser;
import hr.tvz.master.erasmus.repository.ApprovalRepository;
import hr.tvz.master.erasmus.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {

//    @Autowired
//    private MobilityRepository mobilityRepository;
//
//    @Autowired
//    private MobilityStatusRepository mobilityStatusRepository;
//
//    @Autowired
//    private DocumentRepository documentRepository;
//
//    @Autowired
//    private ApprovalTypeRepository approvalTypeRepository;
//
//    @Autowired
//    private AppUserRepository appUserRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private  ApprovalRepository approvalRepository;

//    public Mobility getOne(Long id) {
//        return mobilityRepository.getOne(id);
//    }
//
//    public List<Mobility> findAll() {
//        return mobilityRepository.findAll();
//    }
//
//    public Mobility save(Mobility mobility) {
//        return mobilityRepository.save(mobility);
//    }

    public List<Notification> getUnreadNotificationsForUser(AppUser appUser) {
        List<Notification> unseenNtifications = notificationRepository.findBySeenIsNull();
        return unseenNtifications.stream()
                .filter(n -> n.getReceivers().contains(appUser))
                .collect(Collectors.toList());
    }
}
