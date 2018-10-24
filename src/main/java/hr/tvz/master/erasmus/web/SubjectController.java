package hr.tvz.master.erasmus.web;

import hr.tvz.master.erasmus.entity.Field;
import hr.tvz.master.erasmus.entity.Subject;
import hr.tvz.master.erasmus.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class SubjectController {

    @Autowired
    SubjectRepository subjectRepository;

    @GetMapping("/subjects")
    public String greeting(Model model) {
        List<Subject> list = subjectRepository.findAll();
        model.addAttribute("subjects", list );
        return "subject/list";
    }

    @GetMapping("/subject")
    public String getEmpty(Model model){
        model.addAttribute("subject", new Subject());
        return "subject/create";
    }

    @PostMapping("/subject")
    public String create(@ModelAttribute Subject subject) {
        //TODO validacija da li je ectsValue broj
        subjectRepository.save(subject);
        return "redirect:/subjects";
    }
}
