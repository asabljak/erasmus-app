package hr.tvz.master.erasmus.service;

import hr.tvz.master.erasmus.entity.document.Document;
import hr.tvz.master.erasmus.entity.mobility.Approval;
import hr.tvz.master.erasmus.entity.mobility.ApprovalType;
import hr.tvz.master.erasmus.entity.mobility.Mobility;
import hr.tvz.master.erasmus.entity.mobility.MobilityStatus;
import hr.tvz.master.erasmus.entity.notification.Notification;
import hr.tvz.master.erasmus.entity.notification.NotificationType;
import hr.tvz.master.erasmus.entity.user.AppUser;
import hr.tvz.master.erasmus.entity.user.Role;
import hr.tvz.master.erasmus.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {

//    public static final String REVIEW_MSG = "Vaša mobilnost je završila. Molimo vas da podijelite Vaša iskustva " +
//            "recenziranjem ustanove na kojoj ste boravili za vrijeme mobilnosti.";

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private ApprovalRepository approvalRepository;

    @Autowired
    private ApprovalTypeRepository approvalTypeRepository;

    @Autowired
    private MobilityStatusRepository mobilityStatusRepository;

    @Autowired
    private  AppUserService appUserService;

    @Autowired
    private NotificationTypeRepository notificationTypeRepository;

    public Notification getOne(Long id) {
        return notificationRepository.getOne(id);
    }

    public List<Notification> getUnreadNotificationsForUser(AppUser appUser) {
        List<Notification> unseenNtifications = notificationRepository.findBySeenIsNull();
        return unseenNtifications.stream()
                .filter(n -> n.getReceivers().contains(appUser))
                .collect(Collectors.toList());
    }

    @Transactional
    public void approveNotification(Notification notification, AppUser approvedBy, String reason) {
        Approval approval = notification.getApproval();
        approval.setSuccessful(Boolean.TRUE);
        approvalRepository.save(approval);

        if (ApprovalType.APPLIED.equals(approval.getApprovalType().getId()) && approval.isSuccessful()) {
            Mobility mobility = approval.getMobility();
            mobility.setMobilityStatus(mobilityStatusRepository.getOne(MobilityStatus.CREATED));

            if (notification.getReceivers() != null) {
                appUserService.addRole(notification.getReceivers().get(0), Role.ROLE_ERASMUS_STUDENT);
            }
        }
        notification.setSeen(LocalDateTime.now());
        notification.setSeenBy(approvedBy);
        notificationRepository.save(notification);

        //TODO ako je sub.app, druga poruka
        notificationRepository.save(getResposneNotification(approvedBy ,notification.getSender(), approval, reason));
    }

    public void readNotification(Notification notification) {
        notification.setSeen(LocalDateTime.now());
        notificationRepository.save(notification);
    }

    @Transactional
    public void rejectNotification(Notification notification, AppUser rejectedBy, String reason) {
        Approval approval = notification.getApproval();
        approval.setSuccessful(Boolean.FALSE);
        approvalRepository.save(approval);

        notification.setSeen(LocalDateTime.now());
        notification.setSeenBy(rejectedBy);
        notificationRepository.save(notification);

        notificationRepository.save(getResposneNotification(rejectedBy, notification.getSender(), approval, reason));
    }

    private Notification getResposneNotification(AppUser sender, AppUser receiver, Approval approval, String reason) {
        Notification responseNotification = new Notification();
        responseNotification.setSender(sender);
        responseNotification.setReceivers(Arrays.asList(receiver));
        responseNotification.setApproval(approval);
        responseNotification.setMessage(getApproveNotificationMessage(sender, approval, reason));
        responseNotification.setActionRequired(false);
        responseNotification.setNotificationType(notificationTypeRepository.getOne(NotificationType.RESPONSE));
        return responseNotification;
    }

    private String getApproveNotificationMessage(AppUser appUser, Approval approval, String reason) {
        StringBuilder message = new StringBuilder();

        if (ApprovalType.APPLIED.equals(approval.getApprovalType().getId())) {
            message.append(approval.isSuccessful() ? "Vaša prijava je odobrena. " : "Vaša prijava je odbijena");
        } else if (ApprovalType.SUBJECTS.equals(approval.getApprovalType().getId())) {
            message.append(approval.isSuccessful() ? "Predmeti koje ste prijavili za upis na mobilnosti su odobreni. "
                    : "Predmeti koje ste prijavili za upis na mobilnosti su odbijeni");
        }

        if (!StringUtils.isEmpty(reason)) {
            message.append("Korisnik ").append(appUser).append(" poručuje: ").append(reason);
        }

        return  message.toString();
    }

    public List<Notification> getAllForUser(AppUser appUser) {
        return notificationRepository.findByReceiversContains(appUser);
    }

    public void sendInterviewCalls(List<Mobility> mobilities, String message) {
        List<Notification> notifications = new ArrayList<>();
        List<Approval> approvals = new ArrayList<>();

        for (Mobility mobility : mobilities) {
            Approval approval = new Approval();
            approval.setMobility(mobility);
            approval.setApprovalType(approvalTypeRepository.getOne(ApprovalType.GRANT));
            approvals.add(approval);

            Notification notification = new Notification();
            notification.setActionRequired(false);
            notification.setMessage(message);
            notification.setApproval(approval);
            notifications.add(notification);
        }

        approvalRepository.saveAll(approvals);
        notificationRepository.saveAll(notifications);
    }

    @Transactional
    public void sendLaApprovalRequest(AppUser sender, Document document) {
        Approval approval = new Approval();
        approval.setMobility(document.getMobility());
        approval.setDocuments(Arrays.asList(document));
        approval.setApprovalType(approvalTypeRepository.getOne(ApprovalType.SUBJECTS));
        approvalRepository.save(approval);

        Notification notification = new Notification();
        notification.setNotificationType(notificationTypeRepository.getOne(NotificationType.SUBJECTS_APPROVAL));
        notification.setReceivers(appUserService.findAllByRoleId(Role.ROLE_SUBJECT_COORDINATOR));
        notification.setMessage("Student " + sender + " predao je popunjeni Learning Agreement na pregled. Molimo pregledatje dokument.");
        notification.setActionRequired(Boolean.TRUE);
        notification.setApproval(approval);
        notification.setSender(sender);
        notification.setMobility(document.getMobility());
        notificationRepository.save(notification);
    }

//    public void askForReview(Mobility mobility) {
//        //kreira notifikaciju s gumbom koji otvvarra ekran za psasnje reviewa
//        // po tipu notifikacije zna koja polja na ekranu učitava
//
//        Notification notification = new Notification();
//        notification.setReceivers(Arrays.asList(mobility.getStudent()));
//        notification.setActionRequired(true);
//        notification.setMessage(REVIEW_MSG);
//        notification.setNotificationType(notificationTypeRepository.getOne(NotificationType.INTERVIEW));
//        notificationRepository.save(notification);
//    }
}
