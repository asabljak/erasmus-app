package hr.tvz.master.erasmus.web.mobility;

import hr.tvz.master.erasmus.entity.mobility.Approval;
import hr.tvz.master.erasmus.entity.mobility.Mobility;
import hr.tvz.master.erasmus.repository.ApprovalRepository;
import hr.tvz.master.erasmus.repository.ApprovalTypeRepository;
import hr.tvz.master.erasmus.repository.DocumentRepository;
import hr.tvz.master.erasmus.service.AppUserService;
import hr.tvz.master.erasmus.service.MobilityService;
import hr.tvz.master.erasmus.web.AbstractErasmusController;
import hr.tvz.master.erasmus.web.notification.NotificationController;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class ApprovalController extends AbstractErasmusController {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationController.class);

    @Autowired
    ApprovalRepository approvalRepository;

    @Autowired
    ApprovalTypeRepository approvalTypeRepository;

    @Autowired
    DocumentRepository documentRepository;

    @Autowired
    MobilityService mobilityService;

//    @Autowired
//    AppUserRepository appUserRepository;

    @Autowired
    AppUserService appUserService;

    @PreAuthorize("hasRole('ADMIN') or hasRole('COORDINATOR') or hasRole('ERASMUS_STUDENT')")
    @GetMapping("/approvals")
    public String getAll(Model model) { //TODO ifologjija tko vidi što
        List<Approval> list = approvalRepository.findAll();
        model.addAttribute("approvalList", list);
        return "approvals/list";
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('COORDINATOR') or hasRole('ERASMUS_STUDENT')")
    @GetMapping(path = "approvals/details/{id}")
    public String getOne(Model model, @PathVariable(value = "id") Long id) {
        model.addAttribute("approval", approvalRepository.getOne(id));
        return "approvals/details";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/approvals/create")
    public String getEmpty(Model model){
        model.addAttribute("approval", new Approval());
        model.addAttribute("approvalTypeList", approvalTypeRepository.findAll());
        model.addAttribute("documentList", documentRepository.findAll());
        model.addAttribute("mobilityList", mobilityService.findAll());

        return "approvals/create";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/approvals/create")
    public String create(@ModelAttribute Approval approval) {
        Approval createdApproval = approvalRepository.save(approval);
        return "redirect:/approvals/details/" + createdApproval.getId();
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('COORDINATOR')")
    @GetMapping("/approvals/create/{mobilityId}")
    public String getEmptyForApproval(Model model, @PathVariable Long mobilityId){
        Mobility mobility = mobilityService.getOne(mobilityId);

        model.addAttribute("approval", new Approval());
        model.addAttribute("approvalList", approvalRepository.findByMobility_Id(mobilityId));
        model.addAttribute("approvalTypeList", approvalTypeRepository.findAll());
        model.addAttribute("documentList", documentRepository.findByOwner(mobility.getStudent()));
        model.addAttribute("mobility", mobilityService.getOne(mobilityId));

        return "approvals/createForMobility";
    }

    @Transactional
    @PreAuthorize("hasRole('COORDINATOR')")
    @PostMapping("/approvals/createForMobility/{mobilityId}")
    public String createForMobility(@ModelAttribute Approval approval, @PathVariable Long mobilityId,
                                    @RequestParam(name = "mobilityPoints") Integer mobilityPoints) {

        approval.setMobility(mobilityService.getOne(mobilityId));
        approval.setCoordinator(getLoggedInUser());
        Approval createdApproval = approvalRepository.save(approval);

        if (mobilityPoints != null ) {
            Mobility mobility = mobilityService.getOne(mobilityId);
            mobility.setPoints(mobilityPoints);
            mobilityService.save(mobility);
        }

        return "redirect:/approvals/details/" + createdApproval.getId();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/approvals/edit/{id}")
    public String getExisting(Model model, @PathVariable Long id) throws NotFoundException {

        Optional<Approval> approval = approvalRepository.findById(id);

        if (!approval.isPresent()) {
            throw new NotFoundException("Approval not found");
        }

        model.addAttribute("approval", approval.get());
        model.addAttribute("approvalTypeList", approvalTypeRepository.findAll());
        model.addAttribute("documentList", documentRepository.findAll());
        model.addAttribute("mobilityList", mobilityService.findAll());

        return "approvals/edit";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/approvals/edit")
    public String edit(@ModelAttribute Approval newApproval) {
        if(!newApproval.isValid()) {
            throw new IllegalStateException("Sva polja nisu pravilno postavljena");
        }

        Approval oldApproval = approvalRepository.getOne(newApproval.getId());
        oldApproval.setApprovalType(newApproval.getApprovalType());
        oldApproval.setDocuments(newApproval.getDocuments());
        approvalRepository.save(oldApproval);

        return "redirect:/approvals/details/" + oldApproval.getId();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "/approvals/delete/{id}")
    public String deleteProduct(@PathVariable(name = "id") Long id) {
        approvalRepository.deleteById(id);
        return "redirect:/approvals";
    }
}
