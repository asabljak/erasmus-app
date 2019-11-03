package hr.tvz.master.erasmus.web.institution;

import hr.tvz.master.erasmus.entity.institution.Course;
import hr.tvz.master.erasmus.entity.institution.Field;
import hr.tvz.master.erasmus.entity.institution.Institution;
import hr.tvz.master.erasmus.entity.institution.Review;
import hr.tvz.master.erasmus.repository.CourseRepository;
import hr.tvz.master.erasmus.repository.InstitutionRepository;
import hr.tvz.master.erasmus.service.ReviewService;
import hr.tvz.master.erasmus.web.AbstractErasmusController;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.*;

@Controller
public class InstitutionController extends AbstractErasmusController {

    @Autowired
    InstitutionRepository institutionRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    ReviewService reviewService;

    @GetMapping("/institutions")
    public String getAll(Model model) {
        List<Institution> list = institutionRepository.findAll();
        model.addAttribute("institutions", list);
        return "institutions/list";
    }

    @GetMapping(path = "institutions/details/{id}")
    public String getOne(Model model, @PathVariable(value = "id") Long id) {
        List<Course> courses = courseRepository.findByInstitution_Id(id);
        List<Field> fields = getFieldsDistinct(courses);

        model.addAttribute("institution", institutionRepository.getOne(id));
        model.addAttribute("courses", courses);
        model.addAttribute("fields", fields);
        model.addAttribute("avgRating", reviewService.getAvgRatingForInstitution(id));
        model.addAttribute("reviews", reviewService.getAllForInstitution(id));

        return "institutions/details";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/institutions/create")
    public String getEmpty(Model model){
        model.addAttribute("institution", new Institution());
        return "institutions/create";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/institutions/create")
    public String create(@ModelAttribute Institution institution) {
        Institution createdInstitution = institutionRepository.save(institution);
        return "redirect:/institutions/details/" + createdInstitution.getId();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/institutions/edit/{id}")
    public String getExisting(Model model, @PathVariable Long id) throws NotFoundException {

        Optional<Institution> institution = institutionRepository.findById(id);

        if (!institution.isPresent()) {
            throw new NotFoundException("Institution not found");
        }

        model.addAttribute("institution", institution.get());

        return "institutions/edit";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/institutions/edit")
    public String edit(@ModelAttribute Institution newInstitution) {
        if(!newInstitution.isValid()) {
            throw new IllegalStateException("Sva polja nisu pravilno postavljena");
        }

        Institution oldInstitution = institutionRepository.getOne(newInstitution.getId());
        oldInstitution.setName(newInstitution.getName());
        oldInstitution.setCode(newInstitution.getCode());
        oldInstitution.setCity(newInstitution.getCity());
        oldInstitution.setCountry(newInstitution.getCountry());
        oldInstitution.setNumberOfStudents(newInstitution.getNumberOfStudents());

        if(null != newInstitution.getInformation())
            oldInstitution.setInformation(newInstitution.getInformation());

        if(null != newInstitution.getWebUrl())
            oldInstitution.setWebUrl(newInstitution.getWebUrl());

        institutionRepository.save(oldInstitution);

        return "redirect:/institutions/details/" + oldInstitution.getId();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "/institutions/delete/{id}")
    public String deleteInstitution(@PathVariable(name = "id") Long id) {
        institutionRepository.deleteById(id);
        return "redirect:/institutions";
    }

    @PreAuthorize("hasRole('ERASMUS_STUDENT')")
    @GetMapping(path = "/institutions/review/{id}")
    public String reviewScreen(Model model, @PathVariable(name = "id") Long id) {
        model.addAttribute("institution", institutionRepository.getOne(id));
        model.addAttribute("review", new Review());
        return "institutions/review";
    }

    @PreAuthorize("hasRole('ERASMUS_STUDENT')")
    @PostMapping(path = "/institutions/postReview/{id}")
    public String postReview(@ModelAttribute Review review, @PathVariable(name = "id") Long id) {
        reviewService.saveReview(review, institutionRepository.getOne(id), getLoggedInUser());
        return "redirect:/";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "/institutions/{institutionId}/deleteReview/{reviewId}")
    public String deleteReview(@PathVariable(name = "institutionId") Long institutionId,
                @PathVariable(name = "reviewId") Long reviewId) {
        reviewService.delete(reviewId);
        return "redirect:/institutions/details/" + institutionId;
    }

    private List<Field> getFieldsDistinct(List<Course> courses) {
        Set<Field> fields = new HashSet<>();
        for(Course course : courses) {
            for (Field field : course.getFields()) {
                fields.add(field);
            }
        }

        return new ArrayList<>(fields);
    }
}
