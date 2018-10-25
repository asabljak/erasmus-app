package hr.tvz.master.erasmus.web;

import hr.tvz.master.erasmus.entity.Field;
import hr.tvz.master.erasmus.repository.FieldRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

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
        fieldRepository.save(field);
        return "redirect:/fields";
    }

    //TODO edit, delete, validacija

    @GetMapping("/field/edit/{id}")
    public ModelAndView editFieldView(@PathVariable Long id) throws NotFoundException {

        Optional<Field> field = fieldRepository.findById(id);

        if (!field.isPresent()) {
            throw new NotFoundException("Field not found");
        }

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("field/edit");
        modelAndView.addObject("field", field.get());

        return modelAndView;
    }

    @PostMapping("/field/edit/{id}")
    public ModelAndView editFieldAction(HttpServletRequest request, @PathVariable Long id, Field fieldView,
                                       BindingResult bindingResult) throws Exception {

//        Field field = fieldRepository.getOne(id);

        if (fieldView == null) {
            throw new NotFoundException("Field not found");
        }

        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("field.edit");
            modelAndView.addObject("field", fieldView);

            return modelAndView;
        }

//        Normalizer.Form.bind(request, fieldView, field);
        this.fieldRepository.save(fieldView);

        modelAndView.setViewName("redirect:/fields");

        return modelAndView;
    }


}
