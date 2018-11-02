package hr.tvz.master.erasmus.web;

import hr.tvz.master.erasmus.entity.ApprovalType;
import hr.tvz.master.erasmus.repository.ApprovalTypeRepository;
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
public class ApprovalTypeController {

    @Autowired
    ApprovalTypeRepository approvalTypeRepository;

    @GetMapping("/approvalTypes")
    public String getAll(Model model) {
        List<ApprovalType> list = approvalTypeRepository.findAll();
        model.addAttribute("approvalTypes", list);
        return "approvalTypes/list";
    }

    @GetMapping(path = "approvalTypes/details/{id}")
    public String getOne(Model model, @PathVariable(value = "id") Long id) {
        model.addAttribute("approvalType", approvalTypeRepository.getOne(id));
        return "approvalTypes/details";
    }

    @GetMapping("/approvalTypes/create")
    public String getEmpty(Model model){
        model.addAttribute("approvalType", new ApprovalType());
        return "approvalTypes/create";
    }

    @PostMapping("/approvalTypes/create")
    public String create(@ModelAttribute ApprovalType approvalType) {
        ApprovalType createdApprovalType = approvalTypeRepository.save(approvalType);
        return "redirect:/approvalTypes/details/" + createdApprovalType.getId();
    }

    @GetMapping("/approvalTypes/edit/{id}")
    public String getExisting(Model model, @PathVariable Long id) throws NotFoundException {

        Optional<ApprovalType> approvalType = approvalTypeRepository.findById(id);

        if (!approvalType.isPresent()) {
            throw new NotFoundException("ApprovalType not found");
        }

        model.addAttribute("approvalType", approvalType.get());

        return "approvalTypes/edit";
    }

    @PostMapping("/approvalTypes/edit")
    public String edit(@ModelAttribute ApprovalType newApprovalType) {
        if(!newApprovalType.isValid()) {
            throw new IllegalStateException("Sva polja nisu pravilno postavljena");
        }

        ApprovalType oldApprovalType = approvalTypeRepository.getOne(newApprovalType.getId());
        oldApprovalType.setName(newApprovalType.getName());
        oldApprovalType.setDescription(newApprovalType.getDescription());
        approvalTypeRepository.save(oldApprovalType);

        return "redirect:/approvalTypes/details/" + oldApprovalType.getId();
    }

    @GetMapping(path = "/approvalTypes/delete/{id}")
    public String deleteProduct(@PathVariable(name = "id") Long id) {
        approvalTypeRepository.deleteById(id);
        return "redirect:/approvalTypes";
    }
}
