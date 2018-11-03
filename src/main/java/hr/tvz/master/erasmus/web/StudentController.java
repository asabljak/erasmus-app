package hr.tvz.master.erasmus.web;

import hr.tvz.master.erasmus.entity.Student;
import hr.tvz.master.erasmus.repository.CourseRepository;
import hr.tvz.master.erasmus.repository.StudentRepository;
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
public class StudentController {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    CourseRepository courseRepository;

    @GetMapping("/students")
    public String getAll(Model model) {
        List<Student> list = studentRepository.findAll();
        model.addAttribute("students", list);
        return "students/list";
    }

    @GetMapping(path = "students/details/{id}")
    public String getOne(Model model, @PathVariable(value = "id") Long id) {
        model.addAttribute("student", studentRepository.getOne(id));
        return "students/details";
    }

    @GetMapping("/students/create")
    public String getEmpty(Model model){
        //TODO dohvat smjerova samo s TVZ-a
        model.addAttribute("student", new Student());
        model.addAttribute("homeCourseList", courseRepository.findAll());
        return "students/create";
    }

    @PostMapping("/students/create")
    public String create(@ModelAttribute Student student) {
        Student createdStudent = studentRepository.save(student);
        return "redirect:/students/details/" + createdStudent.getId();
    }

    @GetMapping("/students/edit/{id}")
    public String getExisting(Model model, @PathVariable Long id) throws NotFoundException {

        Optional<Student> student = studentRepository.findById(id);

        if (!student.isPresent()) {
            throw new NotFoundException("Student not found");
        }

        model.addAttribute("student", student.get());
        model.addAttribute("homeCourseList", courseRepository.findAll());

        return "students/edit";
    }

    @PostMapping("/students/edit")
    public String edit(@ModelAttribute Student newStudent) {
        if(!newStudent.isValid()) {
            throw new IllegalStateException("Sva polja nisu pravilno postavljena");
        }

        Student oldStudent = studentRepository.getOne(newStudent.getId());
        oldStudent.setJmbag(newStudent.getJmbag());
        oldStudent.setEmail(newStudent.getEmail());
        oldStudent.setName(newStudent.getName());
        oldStudent.setSurname(newStudent.getSurname());
        oldStudent.setBirthday(newStudent.getBirthday());
        oldStudent.setHomeCourse(newStudent.getHomeCourse());
        oldStudent.setPhone(newStudent.getPhone());
        oldStudent.setYearOfStudy(newStudent.getYearOfStudy());
        studentRepository.save(oldStudent);

        return "redirect:/students/details/" + oldStudent.getId();
    }

    @GetMapping(path = "/students/delete/{id}")
    public String deleteProduct(@PathVariable(name = "id") Long id) {
        studentRepository.deleteById(id);
        return "redirect:/students";
    }
}
