package hr.tvz.master.erasmus.web;

import hr.tvz.master.erasmus.entity.Approval;
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
//TODO dovršiti edit i fixati prikaz liste
@Controller
public class ApprovalController {

    @Autowired
    ApprovalRepository approvalRepository;

    @Autowired
    ApprovalTypeRepository approvalTypeRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    DocumentRepository documentRepository;

    @GetMapping("/approvals")
    public String getAll(Model model) {
        List<Approval> list = approvalRepository.findAll();
        model.addAttribute("approvalList", list);
        return "approvals/list";
    }

    @GetMapping(path = "approvals/details/{id}")
    public String getOne(Model model, @PathVariable(value = "id") Long id) {
        model.addAttribute("approval", approvalRepository.getOne(id));
        return "approvals/details";
    }

    @GetMapping("/approvals/create")
    public String getEmpty(Model model){
        model.addAttribute("approval", new Approval());
        model.addAttribute("approvalTypeList", approvalTypeRepository.findAll());
        model.addAttribute("studentList", studentRepository.findAll()); //TODO izmjeniti u findByID
        model.addAttribute("documentList", documentRepository.findAll()); //TODO getDocumentsForUser

        return "approvals/create";
    }

    @PostMapping("/approvals/create")
    public String create(@ModelAttribute Approval approval) {
        Approval createdApproval = approvalRepository.save(approval);
        return "redirect:/approvals/details/" + createdApproval.getId();
    }

    @GetMapping("/approvals/edit/{id}")
    public String getExisting(Model model, @PathVariable Long id) throws NotFoundException {

        Optional<Approval> approval = approvalRepository.findById(id);

        if (!approval.isPresent()) {
            throw new NotFoundException("Approval not found");
        }

        model.addAttribute("approval", approval.get());
        model.addAttribute("approvalTypeList", approvalTypeRepository.findAll());
        model.addAttribute("documentList", documentRepository.findAll());

        return "approvals/edit";
    }

    @PostMapping("/approvals/edit")
    public String edit(@ModelAttribute Approval newApproval) {
        if(!newApproval.isValid()) {
            throw new IllegalStateException("Sva polja nisu pravilno postavljena");
        }

        Approval oldApproval = approvalRepository.getOne(newApproval.getId());
        oldApproval.setApprovalType(newApproval.getApprovalType());
        oldApproval.setDocuments(newApproval.getDocuments());
        oldApproval.setComment(newApproval.getComment());
        approvalRepository.save(oldApproval);

        return "redirect:/approvals/details/" + oldApproval.getId();
    }

    @GetMapping(path = "/approvals/delete/{id}")
    public String deleteProduct(@PathVariable(name = "id") Long id) {
        approvalRepository.deleteById(id);
        return "redirect:/approvals";
    }
}
