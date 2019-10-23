package hr.tvz.master.erasmus.service;

import hr.tvz.master.erasmus.entity.mobility.Approval;
import hr.tvz.master.erasmus.entity.mobility.ApprovalType;
import hr.tvz.master.erasmus.entity.mobility.Mobility;
import hr.tvz.master.erasmus.entity.mobility.MobilityStatus;
import hr.tvz.master.erasmus.entity.notification.Notification;
import hr.tvz.master.erasmus.entity.user.AppUser;
import hr.tvz.master.erasmus.entity.user.Role;
import hr.tvz.master.erasmus.repository.ApprovalRepository;
import hr.tvz.master.erasmus.repository.ApprovalTypeRepository;
import hr.tvz.master.erasmus.repository.MobilityStatusRepository;
import hr.tvz.master.erasmus.repository.NotificationRepository;
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

        notificationRepository.save(getResposeNotification(approvedBy ,notification.getSender(), approval, reason));
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

        notificationRepository.save(getResposeNotification(rejectedBy, notification.getSender(), approval, reason));
    }

    private Notification getResposeNotification(AppUser sender, AppUser receiver, Approval approval, String reason) {
        Notification responseNotification = new Notification();
        responseNotification.setSender(sender);
        responseNotification.setReceivers(Arrays.asList(receiver));
        responseNotification.setApproval(approval);
        responseNotification.setMessage(getApproveNotificationMessage(sender, approval, reason));
        responseNotification.setActionRequired(false);
        return responseNotification;
    }

    private String getApproveNotificationMessage(AppUser appUser, Approval approval, String reason) {
        StringBuilder message = new StringBuilder();
        if (approval.isSuccessful()) {
            message.append("Vaša prijava je odobrena. ");
        } else  {
            message.append("Vaša prijava je odbijena. ");
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
            notification.setReceivers(Arrays.asList(mobility.getStudent()));
            notifications.add(notification);
        }

        approvalRepository.saveAll(approvals);
        notificationRepository.saveAll(notifications);

    }
}
