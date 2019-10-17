package hr.tvz.master.erasmus.web.mobility;

import hr.tvz.master.erasmus.entity.notification.Notification;
import hr.tvz.master.erasmus.entity.user.AppUser;
import hr.tvz.master.erasmus.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NotificationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationController.class);
    private static final String APPROVED = "approved";
    private static final String REJECTED = "rejected";
    private AppUser appUser;

    @Autowired
    NotificationService notificationService;

    @GetMapping(path = "notifications/details/{id}")
    public String getOne(HttpServletRequest request, Model model, @PathVariable(value = "id") Long id) {
        Notification notification = notificationService.getOne(id);
        appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (notification != null && notification.getReceivers().contains(appUser)) {
            model.addAttribute("notification", notification);
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

        notificationService.readNotification(notification);
        return "redirect:/";
    }
}
