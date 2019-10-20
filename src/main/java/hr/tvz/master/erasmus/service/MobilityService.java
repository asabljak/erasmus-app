package hr.tvz.master.erasmus.service;

import hr.tvz.master.erasmus.entity.document.Document;
import hr.tvz.master.erasmus.entity.mobility.Approval;
import hr.tvz.master.erasmus.entity.mobility.ApprovalType;
import hr.tvz.master.erasmus.entity.mobility.Mobility;
import hr.tvz.master.erasmus.entity.mobility.MobilityStatus;
import hr.tvz.master.erasmus.entity.notification.Notification;
import hr.tvz.master.erasmus.entity.user.AppUser;
import hr.tvz.master.erasmus.entity.user.Role;
import hr.tvz.master.erasmus.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

        Approval approval = new Approval();
        approval.setApprovalType(approvalTypeRepository.getOne(ApprovalType.APPLIED));
        approval.setDocuments(documents);
        approvalRepository.save(approval);

        mobility = addApproval(mobility, approval);
        mobility.setMobilityStatus(mobilityStatusRepository.getOne(MobilityStatus.REQUESTED));
        mobilityRepository.save(mobility);

        //notifikacija
        Notification notification = new Notification();
        notification.setApproval(approval);
        notification.setSender(mobility.getStudent());
        List<AppUser> coords = appUserRepository.findAllByRoles_Id(Role.ROLE_COORDINATOR);
        notification.setReceivers(coords);
        notification.setMessage("Korisnik " + mobility.getStudent() + " se prijavio za mobilnost.");
        notification.setActionRequired(true);
        notificationRepository.save(notification);
    }

    public Mobility addApproval(Mobility mobility, Approval approval) {
        List<Approval> approvals = mobility.getApprovals();
        if (approvals == null) {
            approvals = new ArrayList<>();
        }
        approvals.add(approval);

        //ako je aproval applied true, postavi mobility u created
        if (approval.getApprovalType().getId().equals(ApprovalType.APPLIED) && approval.isSuccessful()) {
            mobility.setMobilityStatus(mobilityStatusRepository.getOne(MobilityStatus.CREATED));
        }
        mobility.setApprovals(approvals);
        return mobility;
    }

}
