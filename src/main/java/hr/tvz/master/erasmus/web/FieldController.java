package hr.tvz.master.erasmus.web;

import hr.tvz.master.erasmus.entity.Field;
import hr.tvz.master.erasmus.repository.FieldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

//TODO srediti mappinge

@Controller
public class FieldController {

    @Autowired
    FieldRepository fieldRepository;

    @GetMapping("/fields")
    public String greeting(Model model) {
        List<Field> list = fieldRepository.findAll();
        model.addAttribute("fields", list );
        return "field/list";
    }

//    @GetMapping("/createDummy")
//    public String createDummy(Model model) {
//        Field field = new Field();
//        field.setCode("code");
//        field.setName("dummyName");
//
//        fieldRepository.save(field);
//
//        return "/greeting";
//    }

    @GetMapping("/field")
    public String getEmpty(Model model){
        model.addAttribute("field", new Field());
        return "field/create";
    }

    @PostMapping("/field")
    public String create(@ModelAttribute Field field) {
        Field tmp = field;
        fieldRepository.save(field);
        return "redirect:/fields";
    }
}
