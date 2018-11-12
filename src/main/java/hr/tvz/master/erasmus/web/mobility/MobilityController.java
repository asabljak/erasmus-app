package hr.tvz.master.erasmus.web.mobility;

import hr.tvz.master.erasmus.entity.mobility.Mobility;
import hr.tvz.master.erasmus.entity.user.Role;
import hr.tvz.master.erasmus.repository.*;
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
    AppUserRepository appUserRepository;

    @Autowired
    InstitutionRepository institutionRepository;

    @Autowired
    ApprovalRepository approvalRepository;

    @Autowired
    RoleRepository roleRepository;

    @GetMapping("/mobilities")
    public String getAllActive(Model model) {
        List<Mobility> list = mobilityRepository.findAll(); //TODO find po statusu
        model.addAttribute("mobilityList", list);
        return "mobilities/list";
    }

    @GetMapping(path = "mobilities/details/{id}")
    public String getOne(Model model, @PathVariable(value = "id") Long id) {
        Mobility mobility = mobilityRepository.getOne(id);
        model.addAttribute("mobility", mobility);
        model.addAttribute("approvalList", approvalRepository.findByMobility_Id(id));

        return "mobilities/details";
    }

    @GetMapping("/mobilities/create")
    public String getEmpty(Model model){
        Role erasmusStudent = roleRepository.getOne(Role.ROLE_ERASMUS_STUDENT);

        model.addAttribute("mobility", new Mobility());
        model.addAttribute("mobilityStatusList", mobilityStatusRepository.findAll());
        model.addAttribute("studentList", appUserRepository.findAllByRoles(erasmusStudent));
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
