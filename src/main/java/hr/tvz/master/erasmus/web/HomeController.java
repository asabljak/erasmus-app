package hr.tvz.master.erasmus.web;

import hr.tvz.master.erasmus.entity.notification.Notification;
import hr.tvz.master.erasmus.entity.user.AppUser;
import hr.tvz.master.erasmus.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    NotificationService notificationService;

    @GetMapping("/")
    public String getHomepage(Model model) {
        AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Notification> notifications = notificationService.getUnreadNotificationsForUser(appUser);
        model.addAttribute("notifications", notifications);
//        model.addAttribute("reason", "");
//        model.addAttribute("isSucessfull", new Boolean(null));
        return "template";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "/login/login";
    }
}
