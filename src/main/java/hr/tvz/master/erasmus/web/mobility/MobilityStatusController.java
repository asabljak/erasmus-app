package hr.tvz.master.erasmus.web.mobility;

import hr.tvz.master.erasmus.entity.mobility.MobilityStatus;
import hr.tvz.master.erasmus.repository.MobilityStatusRepository;
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
public class MobilityStatusController {

    @Autowired
    MobilityStatusRepository mobilityStatusRepository;

    @GetMapping("/mobilityStatus")
    public String getAll(Model model) {
        List<MobilityStatus> list = mobilityStatusRepository.findAll();
        model.addAttribute("mobilityStatusList", list);
        return "mobilityStatus/list";
    }

    @GetMapping(path = "mobilityStatus/details/{id}")
    public String getOne(Model model, @PathVariable(value = "id") Long id) {
        model.addAttribute("mobilityStatus", mobilityStatusRepository.getOne(id));
        return "mobilityStatus/details";
    }

//    @GetMapping("/mobilityStatus/create")
//    public String getEmpty(Model model){
//        model.addAttribute("mobilityStatus", new MobilityStatus());
//        return "mobilityStatus/create";
//    }
//
//    @PostMapping("/mobilityStatus/create")
//    public String create(@ModelAttribute MobilityStatus mobilityStatus) {
//        MobilityStatus createdMobilityStatus = mobilityStatusRepository.save(mobilityStatus);
//        return "redirect:/mobilityStatus/details/" + createdMobilityStatus.getId();
//    }

    @GetMapping("/mobilityStatus/edit/{id}")
    public String getExisting(Model model, @PathVariable Long id) throws NotFoundException {

        Optional<MobilityStatus> mobilityStatus = mobilityStatusRepository.findById(id);

        if (!mobilityStatus.isPresent()) {
            throw new NotFoundException("MobilityStatus not found");
        }

        model.addAttribute("mobilityStatus", mobilityStatus.get());

        return "mobilityStatus/edit";
    }

    @PostMapping("/mobilityStatus/edit")
    public String edit(@ModelAttribute MobilityStatus newMobilityStatus) {
        if(!newMobilityStatus.isValid()) {
            throw new IllegalStateException("Sva polja nisu pravilno postavljena");
        }

        MobilityStatus oldMobilityStatus = mobilityStatusRepository.getOne(newMobilityStatus.getId());
        oldMobilityStatus.setName(newMobilityStatus.getName());
        oldMobilityStatus.setDescription(newMobilityStatus.getDescription());
        mobilityStatusRepository.save(oldMobilityStatus);

        return "redirect:/mobilityStatus/details/" + oldMobilityStatus.getId();
    }

//    @GetMapping(path = "/mobilityStatus/delete/{id}")
//    public String deleteProduct(@PathVariable(name = "id") Long id) {
//        mobilityStatusRepository.deleteById(id);
//        return "redirect:/mobilityStatus";
//    }
}
