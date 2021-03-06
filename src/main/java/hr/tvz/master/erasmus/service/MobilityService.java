package hr.tvz.master.erasmus.service;

import hr.tvz.master.erasmus.entity.document.Document;
import hr.tvz.master.erasmus.entity.mobility.Approval;
import hr.tvz.master.erasmus.entity.mobility.ApprovalType;
import hr.tvz.master.erasmus.entity.mobility.Mobility;
import hr.tvz.master.erasmus.entity.notification.Notification;
import hr.tvz.master.erasmus.entity.notification.NotificationType;
import hr.tvz.master.erasmus.entity.user.AppUser;
import hr.tvz.master.erasmus.entity.user.Role;
import hr.tvz.master.erasmus.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MobilityService {

    @Autowired
    private MobilityRepository mobilityRepository;

    @Autowired
    private MobilityStatusRepository mobilityStatusRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private ApprovalTypeRepository approvalTypeRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private NotificationTypeRepository notificationTypeRepository;

    @Autowired
    private  ApprovalRepository approvalRepository;

    public Mobility getOne(Long id) {
        return mobilityRepository.getOne(id);
    }

    public List<Mobility> findAll() {
        return mobilityRepository.findAll();
    }

    public Mobility save(Mobility mobility) {
        return mobilityRepository.save(mobility);
    }

    @Transactional
    public void applyMobility(Mobility mobility, List<Document> documents) {
        documentRepository.saveAll(documents);
        mobilityRepository.save(mobility);

        Approval approval = new Approval();
        approval.setApprovalType(approvalTypeRepository.getOne(ApprovalType.APPLIED));
        approval.setDocuments(documents);
        approval.setMobility(mobility);
        approvalRepository.save(approval);

        //notifikacija
        Notification notification = new Notification();
        notification.setApproval(approval);
        notification.setSender(mobility.getStudent());
        notification.setNotificationType(notificationTypeRepository.getOne(NotificationType.APPLY));
        List<AppUser> coords = appUserRepository.findAllByRoles_Id(Role.ROLE_COORDINATOR);
        notification.setReceivers(coords);
        notification.setMessage("Korisnik " + mobility.getStudent() + " se prijavio za mobilnost.");
        notification.setActionRequired(true);
        notificationRepository.save(notification);
    }
}
