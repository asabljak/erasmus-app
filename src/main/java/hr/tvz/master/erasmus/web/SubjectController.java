package hr.tvz.master.erasmus.web;

import hr.tvz.master.erasmus.entity.Subject;
import hr.tvz.master.erasmus.repository.SubjectRepository;
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
public class SubjectController {

    @Autowired
    SubjectRepository subjectRepository;

    @GetMapping("/subjects")
    public String getAll(Model model) {
        List<Subject> list = subjectRepository.findAll();
        model.addAttribute("subjects", list);
        return "subjects/list";
    }

    @GetMapping(path = "subjects/details/{id}")
    public String getOne(Model model, @PathVariable(value = "id") Long id) {
        model.addAttribute("subject", subjectRepository.getOne(id));
        return "subjects/details";
    }

    @GetMapping("/subjects/create")
    public String getEmpty(Model model){
        model.addAttribute("subject", new Subject());
        return "subjects/create";
    }

    @PostMapping("/subjects/create")
    public String create(@ModelAttribute Subject subject) {
        Subject createdSubject = subjectRepository.save(subject);
        return "redirect:/subjects/details/" + createdSubject.getId();
    }

    @GetMapping("/subjects/edit/{id}")
    public String getExisting(Model model, @PathVariable Long id) throws NotFoundException {

        Optional<Subject> subject = subjectRepository.findById(id);

        if (!subject.isPresent()) {
            throw new NotFoundException("Subject not found");
        }

        model.addAttribute("subject", subject.get());

        return "subjects/edit";
    }

    @PostMapping("/subjects/edit")
    public String edit(@ModelAttribute Subject newSubject) {
        if(!newSubject.isValid()) {
            throw new IllegalStateException("Sva polja nisu pravilno postavljena");
        }

        Subject oldSubject = subjectRepository.getOne(newSubject.getId());
        oldSubject.setName(newSubject.getName());
        oldSubject.setDescription(newSubject.getDescription());
        oldSubject.setEctsValue(newSubject.getEctsValue());
        oldSubject.setLanguage(newSubject.getLanguage());
        subjectRepository.save(oldSubject);

        return "redirect:/subjects/details/" + oldSubject.getId();
    }

    @GetMapping(path = "/subjects/delete/{id}")
    public String deleteProduct(@PathVariable(name = "id") Long id) {
        subjectRepository.deleteById(id);
        return "redirect:/subjects";
    }
}
