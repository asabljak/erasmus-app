package hr.tvz.master.erasmus.web;

import hr.tvz.master.erasmus.entity.Institution;
import hr.tvz.master.erasmus.repository.InstitutionRepository;
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
public class InstitutioController {

    @Autowired
    InstitutionRepository institutionRepository;

    @GetMapping("/institutions")
    public String getAll(Model model) {
        List<Institution> list = institutionRepository.findAll();
        model.addAttribute("institutions", list);
        return "institutions/list";
    }

    @GetMapping(path = "institutions/details/{id}")
    public String getOne(Model model, @PathVariable(value = "id") Long id) {
        model.addAttribute("institution", institutionRepository.getOne(id));
        return "institutions/details";
    }

    @GetMapping("/institutions/create")
    public String getEmpty(Model model){
        model.addAttribute("institution", new Institution());
        return "institutions/create";
    }

    @PostMapping("/institutions/create")
    public String create(@ModelAttribute Institution institution) {
        Institution createdInstitution = institutionRepository.save(institution);
        return "redirect:/institutions/details/" + createdInstitution.getId();
    }

    @GetMapping("/institutions/edit/{id}")
    public String getExisting(Model model, @PathVariable Long id) throws NotFoundException {

        Optional<Institution> institution = institutionRepository.findById(id);

        if (!institution.isPresent()) {
            throw new NotFoundException("Institution not found");
        }

        model.addAttribute("institution", institution.get());

        return "institutions/edit";
    }

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

    @GetMapping(path = "/institutions/delete/{id}")
    public String deleteProduct(@PathVariable(name = "id") Long id) {
        institutionRepository.deleteById(id);
        return "redirect:/institutions";
    }
}
