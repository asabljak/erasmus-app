package hr.tvz.master.erasmus.web.institution;

import hr.tvz.master.erasmus.entity.institution.Field;
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
public class FieldController {

    @Autowired
    FieldRepository fieldRepository;

    @GetMapping("/fields")
    public String getAll(Model model) {
        List<Field> list = fieldRepository.findAll();
        model.addAttribute("fields", list);
        return "fields/list";
    }

    @GetMapping(path = "fields/details/{id}")
    public String getOne(Model model, @PathVariable(value = "id") Long id) {
        model.addAttribute("field", fieldRepository.getOne(id));
        return "fields/details";
    }

    @GetMapping("/fields/create")
    public String getEmpty(Model model){
        model.addAttribute("field", new Field());
        return "fields/create";
    }

    @PostMapping("/fields/create")
    public String create(@ModelAttribute Field field) {
        if(!field.isValid()) {
            throw new IllegalStateException("Sva polja nisu pravilno postavljena");
        }

        Field createdField = fieldRepository.save(field);
        return "redirect:/fields/details/" + createdField.getId();
    }

    @GetMapping("/fields/edit/{id}")
    public String getExisting(Model model, @PathVariable Long id) throws NotFoundException {

        Optional<Field> field = fieldRepository.findById(id);

        if (!field.isPresent()) {
            throw new NotFoundException("Field not found");
        }

        model.addAttribute("field", field.get());

        return "fields/edit";
    }

    @PostMapping("/fields/edit")
    public String edit(@ModelAttribute Field newField) {
        if(!newField.isValid()) {
            throw new IllegalStateException("Sva polja nisu pravilno postavljena");
        }

        Field oldField = fieldRepository.getOne(newField.getId());
        oldField.setCode(newField.getCode());
        oldField.setName(newField.getName());
        fieldRepository.save(oldField);
        return "redirect:/fields/details/" + oldField.getId();
    }

    @GetMapping(path = "/fields/delete/{id}")
    public String deleteProduct(@PathVariable(name = "id") Long id) {
        fieldRepository.deleteById(id);
        return "redirect:/fields";
    }
}
