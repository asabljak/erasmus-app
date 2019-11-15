package hr.tvz.master.erasmus.web.notification;

import hr.tvz.master.erasmus.entity.mobility.Mobility;
import hr.tvz.master.erasmus.entity.mobility.MobilityStatus;
import hr.tvz.master.erasmus.entity.notification.Notification;
import hr.tvz.master.erasmus.entity.notification.NotificationType;
import hr.tvz.master.erasmus.entity.user.AppUser;
import hr.tvz.master.erasmus.repository.MobilityRepository;
import hr.tvz.master.erasmus.service.NotificationService;
import hr.tvz.master.erasmus.web.AbstractErasmusController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class NotificationController extends AbstractErasmusController {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationController.class);
    private static final String APPROVED = "approved";
    private static final String REJECTED = "rejected";
    private AppUser appUser;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private MobilityRepository mobilityRepository;

    @PreAuthorize("hasRole('COORDINATOR') or hasRole('SUBJECT_COORDINATOR') or hasRole('ERASMUS_STUDENT')")
    @GetMapping("/notifications")
    public String getAllForUser(Model model) {
        AppUser appUser = getLoggedInUser();

        List<Notification> notifications = notificationService.getAllForUser(appUser);
        model.addAttribute("notificationList", notifications);
        return "notifications/list";
    }

    @PreAuthorize("hasRole('COORDINATOR') or hasRole('SUBJECT_COORDINATOR') or hasRole('ERASMUS_STUDENT')")
    @GetMapping(path = "notifications/details/{id}")
    public String getOne(HttpServletRequest request, Model model, @PathVariable(value = "id") Long id) {
        Notification notification = notificationService.getOne(id);
        appUser = getLoggedInUser();

        if (notification != null && notification.getReceivers().contains(appUser)) {
            model.addAttribute("notification", notification);
            if (NotificationType.INTERVIEW.equals(notification.getNotificationType().getId())) {
                return "notifications/interview";
            } else if (NotificationType.REVIEW.equals(notification.getNotificationType().getId())) {
//                model.addAttribute("notificationId", notificationId);
                return "notifications/review";
            }

            model.addAttribute("isSuccessful", Boolean.FALSE);
            return "notifications/details";
        }

        LOGGER.warn("Korisnik " + appUser.getEmail() + " nije ovlasten za pregled notifikacije " + notification);
        return request.getRequestURL().toString();
    }

    @PostMapping(path = "notifications/resolve/{id}")
    public String resolve(HttpServletRequest request,
                          @PathVariable(value = "id") Long id,
                          @RequestParam(name = "reason") String reason,
                          @RequestParam(name = "state") String state){

        Notification notification = notificationService.getOne(id);
        if (notification == null || !notification.getReceivers().contains(appUser)) {
            LOGGER.warn("Korisnik " + appUser.getEmail() + " nije ovlasten za pregled notifikacije: " + notification);
            return request.getRequestURL().toString();
        }


        if (APPROVED.equals(state)) {
            notificationService.approveNotification(notification, appUser, reason);
        } else if (REJECTED.equals(state)) {
            notificationService.rejectNotification(notification, appUser, reason);
        } else {
            LOGGER.warn("Nepoznato stanje notifikacije: " + state);
        }
        return "redirect:/";
    }

    @PostMapping(path = "notifications/read/{id}")
    public String read(HttpServletRequest request, @PathVariable(value = "id") Long id) {
        Notification notification = notificationService.getOne(id);
        if (notification == null || !notification.getReceivers().contains(appUser)) {
            LOGGER.warn("Korisnik " + appUser.getEmail() + " nije ovlasten za pregled notifikacije: " + notification);
            return request.getRequestURL().toString();
        }

        if (notification.getSeen() == null) {
            notificationService.readNotification(notification);
        }

        return "redirect:/";
    }

    @PreAuthorize("hasRole('COORDINATOR')")
    @GetMapping("/notifications/interview")
    public String openCllForInterviewPage(Model model) {
        model.addAttribute("mobilityList", mobilityRepository.findAllByMobilityStatus_Id(MobilityStatus.CREATED));
        return "notifications/interview";
    }

    @PreAuthorize("hasRole('ERASMUS_STUDENT')")
    @GetMapping("/notifications/review/{id}")
    public String review(@PathVariable(value = "id") Long id) {
        Notification notification = notificationService.getOne(id);
//        notification.setSeen(LocalDateTime.now());
//        notification.setSeenBy(getLoggedInUser());
        //model.addAttribute("institution", notification.getMobility().getInstitution());
        //TODO označi notifikaciju seen
        return "redirect:/institutions/review/" + notification.getMobility().getInstitution().getId();
    }

    @PreAuthorize("hasRole('COORDINATOR')")
    @PostMapping("/notifications/interview")
    public String callForInterview(@RequestParam(name = "mobilities") List<Mobility> mobilities,
                                       @RequestParam(name = "message") String message) {
        notificationService.sendInterviewCalls(mobilities, message);
        return "redirect:/";
    }
}
