package hr.tvz.master.erasmus.web;

import hr.tvz.master.erasmus.entity.Field;
import hr.tvz.master.erasmus.repository.FieldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class FieldController {

    @Autowired
    FieldRepository fieldRepository;

    @GetMapping("/fields")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        List<Field> list = fieldRepository.findAll();
        model.addAttribute("name", list );
        return "greeting";
    }

    @GetMapping("/create")
    public String create(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        Field field = new Field();
        field.setCODE("code");
        field.setNAME("dummyName");

        fieldRepository.save(field);

        return "/greeting";
    }
}
