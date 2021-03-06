package hr.tvz.master.erasmus.web.user;

import hr.tvz.master.erasmus.entity.user.AppUser;
import hr.tvz.master.erasmus.repository.AppUserRepository;
import hr.tvz.master.erasmus.repository.FieldRepository;
import hr.tvz.master.erasmus.repository.RoleRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class AppUserController {

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    FieldRepository fieldRepository;
    
    @Autowired
    RoleRepository roleRepository;

    @GetMapping("/appUsers/byRole")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COORDINATOR') or hasRole('SUBJECT_COORDINATOR')")
    public String getAll(Model model) {
        model.addAttribute("appUsers", appUserRepository.findAll());
        model.addAttribute("roles", roleRepository.findAll());
        return "appUsers/list";
    }

    @GetMapping("/appUsers/byRole/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COORDINATOR') or hasRole('SUBJECT_COORDINATOR')")
    public String getAllByRole(Model model, @PathVariable(value = "id") Long id) {
        List<AppUser> list = appUserRepository.findAllByRoles_Id(id);
        model.addAttribute("roles", roleRepository.findAll());
        model.addAttribute("appUsers", list);
        model.addAttribute("roleId", id);
        return "appUsers/list";
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('COORDINATOR') or hasRole('SUBJECT_COORDINATOR')")
    @GetMapping(path = "appUsers/details/{id}")
    public String getOne(Model model, @PathVariable(value = "id") Long id) {
        model.addAttribute("appUser", appUserRepository.getOne(id));
        return "appUsers/details";
    }

    @GetMapping("/appUsers/create")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COORDINATOR') or hasRole('SUBJECT_COORDINATOR')")
    public String getEmpty(Model model){
        //TODO dohvat smjerova samo s TVZ-a
        model.addAttribute("appUser", new AppUser());
        model.addAttribute("roles", roleRepository.findAll());
        model.addAttribute("homeCourseList", fieldRepository.findAll());
        return "appUsers/create";
    }

    @PostMapping("/appUsers/create")
    @PreAuthorize("hasRole('ADMIN')")
    public String create(@ModelAttribute AppUser appUser) {
        appUser.setEnabled(true);
        AppUser createdStudent = appUserRepository.save(appUser);
        return "redirect:/appUsers/details/" + createdStudent.getId();
    }

    @GetMapping("/appUsers/edit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String getExisting(Model model, @PathVariable Long id) throws NotFoundException {

        Optional<AppUser> appUser = appUserRepository.findById(id);

        if (!appUser.isPresent()) {
            throw new NotFoundException("Student not found");
        }

        model.addAttribute("appUser", appUser.get());
        model.addAttribute("roles", roleRepository.findAll());
        model.addAttribute("homeCourseList", fieldRepository.findAll());

        return "appUsers/edit";
    }

    @PostMapping("/appUsers/edit")
    @PreAuthorize("hasRole('ADMIN')")
    public String edit(@ModelAttribute AppUser newStudent) {
        if(!newStudent.isValid()) {
            throw new IllegalStateException("Sva polja nisu pravilno postavljena");
        }

        AppUser oldStudent = appUserRepository.getOne(newStudent.getId());
        oldStudent.setRoles(newStudent.getRoles());
        oldStudent.setJmbag(newStudent.getJmbag());
        oldStudent.setEmail(newStudent.getEmail());
        oldStudent.setName(newStudent.getName());
        oldStudent.setSurname(newStudent.getSurname());
        oldStudent.setBirthday(newStudent.getBirthday());
        oldStudent.setHomeCourse(newStudent.getHomeCourse());
        oldStudent.setPhone(newStudent.getPhone());
        oldStudent.setYearOfStudy(newStudent.getYearOfStudy());
        oldStudent.setEnabled(newStudent.isEnabled());
        appUserRepository.save(oldStudent);

        return "redirect:/appUsers/details/" + oldStudent.getId();
    }

    @GetMapping(path = "/appUsers/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteProduct(@PathVariable(name = "id") Long id) {
        appUserRepository.deleteById(id);
        return "redirect:/appUsers";
    }
}
