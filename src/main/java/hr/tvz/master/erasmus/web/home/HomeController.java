package hr.tvz.master.erasmus.web.home;

import hr.tvz.master.erasmus.entity.notification.Notification;
import hr.tvz.master.erasmus.service.NotificationService;
import hr.tvz.master.erasmus.web.AbstractErasmusController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController extends AbstractErasmusController {

    @Autowired
    NotificationService notificationService;

    @GetMapping("/")
    public String getHomepage(Model model) {
        List<Notification> notifications = notificationService.getUnreadNotificationsForUser(getLoggedInUser());
        model.addAttribute("notifications", notifications);
        return "template";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "/login/login";
    }
}
