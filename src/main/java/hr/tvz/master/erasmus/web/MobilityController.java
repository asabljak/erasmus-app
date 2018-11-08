package hr.tvz.master.erasmus.web;

import hr.tvz.master.erasmus.entity.Mobility;
import hr.tvz.master.erasmus.repository.InstitutionRepository;
import hr.tvz.master.erasmus.repository.MobilityRepository;
import hr.tvz.master.erasmus.repository.MobilityStatusRepository;
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
public class MobilityController {

    @Autowired
    MobilityRepository mobilityRepository;
    
    @Autowired
    MobilityStatusRepository mobilityStatusRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    InstitutionRepository institutionRepository;

    @GetMapping("/mobilities")
    public String getAllActive(Model model) {
        List<Mobility> list = mobilityRepository.findAll(); //TODO find po statusu
        model.addAttribute("mobilityList", list);
        return "mobilities/list";
    }

    @GetMapping(path = "mobilities/details/{id}")
    public String getOne(Model model, @PathVariable(value = "id") Long id) {
        model.addAttribute("mobility", mobilityRepository.getOne(id));
        return "mobilities/details";
    }

    @GetMapping("/mobilities/create")
    public String getEmpty(Model model){
        model.addAttribute("mobility", new Mobility());
        model.addAttribute("mobilityStatusList", mobilityStatusRepository.findAll());
        model.addAttribute("studentList", studentRepository.findAll());
        model.addAttribute("institutionList", institutionRepository.findAll());
        
        return "mobilities/create";
    }

    @PostMapping("/mobilities/create")
    public String create(@ModelAttribute Mobility mobility) {
        Mobility createdMobility = mobilityRepository.save(mobility);
        return "redirect:/mobilities/details/" + createdMobility.getId();
    }

    @GetMapping("/mobilities/edit/{id}")
    public String getExisting(Model model, @PathVariable Long id) throws NotFoundException {

        Optional<Mobility> mobility = mobilityRepository.findById(id);

        if (!mobility.isPresent()) {
            throw new NotFoundException("Mobility not found");
        }

        model.addAttribute("mobility", mobility.get());
        model.addAttribute("mobilityStatusList", mobilityStatusRepository.findAll());

        return "mobilities/edit";
    }

    @PostMapping("/mobilities/edit")
    public String edit(@ModelAttribute Mobility newMobility) {
        Mobility oldMobility = mobilityRepository.getOne(newMobility.getId());
        oldMobility.setInstitution(newMobility.getInstitution());
        oldMobility.setMobilityStart(newMobility.getMobilityStart());
        oldMobility.setMobilityStatus(newMobility.getMobilityStatus());
        mobilityRepository.save(oldMobility);

        return "redirect:/mobilities/details/" + oldMobility.getId();
    }

    @GetMapping(path = "/mobilities/delete/{id}")
    public String deleteProduct(@PathVariable(name = "id") Long id) {
        mobilityRepository.deleteById(id);
        return "redirect:/mobilities";
    }
}
