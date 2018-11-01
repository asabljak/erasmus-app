package hr.tvz.master.erasmus.web;

import hr.tvz.master.erasmus.entity.Course;
import hr.tvz.master.erasmus.repository.CourseRepository;
import hr.tvz.master.erasmus.repository.FieldRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class CourseController {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    FieldRepository fieldRepository;

    @GetMapping("/courses")
    public String getAll(Model model) {
        List<Course> list = courseRepository.findAll();
        model.addAttribute("courses", list);
        return "courses/list";
    }

    @GetMapping(path = "courses/details/{id}")
    public String getOne(Model model, @PathVariable(value = "id") Long id) {
        model.addAttribute("course", courseRepository.getOne(id));
        return "courses/details";
    }

    @GetMapping("/courses/create")
    public String getEmpty(Model model){
        model.addAttribute("course", new Course());
        model.addAttribute("fieldList", fieldRepository.findAll());
        return "courses/create";
    }

    @PostMapping("/courses/create")
    public String create(@ModelAttribute Course course) {
        Course createdCourse = courseRepository.save(course);
        return "redirect:/courses/details/" + createdCourse.getId();
    }

    @GetMapping("/courses/edit/{id}")
    public String getExisting(Model model, @PathVariable Long id) throws NotFoundException {

        Optional<Course> course = courseRepository.findById(id);

        if (!course.isPresent()) {
            throw new NotFoundException("Course not found");
        }

        model.addAttribute("course", course.get());
        model.addAttribute("fieldList", fieldRepository.findAll());

        return "courses/edit";
    }

    @PostMapping("/courses/edit")
    public String edit(@ModelAttribute Course newCourse) {
        if(!newCourse.isValid()) {
            throw new IllegalStateException("Sva polja nisu pravilno postavljena");
        }

        Course oldCourse = courseRepository.getOne(newCourse.getId());
        oldCourse.setName(newCourse.getName());
        oldCourse.setDescription(newCourse.getDescription());
        oldCourse.setFields(newCourse.getFields());
        courseRepository.save(oldCourse);

        return "redirect:/courses/details/" + oldCourse.getId();
    }

    @GetMapping(path = "/courses/delete/{id}")
    public String deleteProduct(@PathVariable(name = "id") Long id) {
        courseRepository.deleteById(id);
        return "redirect:/courses";
    }
}
