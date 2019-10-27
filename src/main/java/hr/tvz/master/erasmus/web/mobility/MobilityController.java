package hr.tvz.master.erasmus.web.mobility;

import hr.tvz.master.erasmus.entity.document.Document;
import hr.tvz.master.erasmus.entity.document.DocumentType;
import hr.tvz.master.erasmus.entity.institution.Institution;
import hr.tvz.master.erasmus.entity.mobility.Mobility;
import hr.tvz.master.erasmus.entity.user.AppUser;
import hr.tvz.master.erasmus.entity.user.Role;
import hr.tvz.master.erasmus.repository.*;
import hr.tvz.master.erasmus.service.MobilityService;
import hr.tvz.master.erasmus.web.AbstractErasmusController;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
public class MobilityController extends AbstractErasmusController {
    Logger logger = LoggerFactory.getLogger(MobilityController.class);

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
    DocumentTypeRepository documentTypeRepository;

    @Autowired
    DocumentRepository documentRepository;

    @Autowired
    MobilityService mobilityService;

    @PreAuthorize("hasRole('ADMIN') or hasRole('COORDINATOR')")
    @GetMapping("/mobilities")
    public String getAll(Model model) {
        List<Mobility> list = mobilityRepository.findAll();
        model.addAttribute("mobilityList", list);
        return "mobilities/list";
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('COORDINATOR')")
    @GetMapping(path = "mobilities/details/{id}")
    public String getOne(Model model, @PathVariable(value = "id") Long id) {
        Mobility mobility = mobilityRepository.getOne(id);
        model.addAttribute("mobility", mobility);
       // model.addAttribute("approvalList", approvalRepository.findByMobility_Id(id));

        return "mobilities/details";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/mobilities/create")
    public String getEmpty(Model model){
//        Role erasmusStudent = roleRepository.getOne(Role.ROLE_ERASMUS_STUDENT);

        model.addAttribute("mobility", new Mobility());
        model.addAttribute("mobilityStatusList", mobilityStatusRepository.findAll());
//        model.addAttribute("studentList", appUserRepository.findAllByRoles(erasmusStudent));
        model.addAttribute("studentList", appUserRepository.findAllByRoles_Id(Role.ROLE_ERASMUS_STUDENT));
        model.addAttribute("institutionList", institutionRepository.findAll());
        
        return "mobilities/create";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/mobilities/create")
    public String create(@ModelAttribute Mobility mobility) {
        Mobility createdMobility = mobilityRepository.save(mobility);
        return "redirect:/mobilities/details/" + createdMobility.getId();
    }

    @PreAuthorize("hasRole('VISITOR')")
    @GetMapping("/mobilities/create/{institutionId}")
    public String prepareForVisitorRequest(Model model, @PathVariable(name = "institutionId") Long institutionId) {
        model.addAttribute("institution", institutionRepository.getOne(institutionId));
        return "mobilities/createForInstitution";
    }

    @PreAuthorize("hasRole('VISITOR')")
    @PostMapping("/mobilities/create/{institutionId}")
    public String handleVisitorRequest(@PathVariable(name = "institutionId") Long id,
                                       @RequestParam("prijavniObrazac") MultipartFile prijavniObrazac,
                                       @RequestParam("motivacijskoPismo") MultipartFile motivacijskoPismo,
                                       @RequestParam("cv") MultipartFile cv,
                                       @RequestParam("domovnica") MultipartFile domovnica,
                                       @RequestParam("statusStudenta") MultipartFile statusStudenta,
                                       @RequestParam("prijepisOcjena") MultipartFile prijepisOcjena) {

//        AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); zasto je id null?
        String email = ((AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmail();
        AppUser appUser = appUserRepository.findByEmail(email).get();

        Institution institution = institutionRepository.getOne(id);

        Document docPrijavniObrazac = null;
        Document docMotivacijskoPismo = null;
        Document docCv = null;
        Document docDomovnica = null;
        Document docStatusStudenta = null;
        Document docPrijepisOcjena = null;

        try {
            docPrijavniObrazac = this.createDocument(appUser, prijavniObrazac, documentTypeRepository.getOne(DocumentType.PRIJAVA));
            docMotivacijskoPismo = this.createDocument(appUser, motivacijskoPismo, documentTypeRepository.getOne(DocumentType.MOTIVACIJSKO_PISMO));
            docCv = this.createDocument(appUser, cv, documentTypeRepository.getOne(DocumentType.CV));
            docDomovnica = this.createDocument(appUser, domovnica, documentTypeRepository.getOne(DocumentType.DOMOVNICA));
            docStatusStudenta = this.createDocument(appUser, statusStudenta, documentTypeRepository.getOne(DocumentType.STATUS_STUDENTA));
            docPrijepisOcjena = this.createDocument(appUser, statusStudenta, documentTypeRepository.getOne(DocumentType.PRIJEPIS_OCJENA));
        } catch (IOException e) {
            logger.error("Document creation error", e);
        }
//        documentRepository.saveAll(Arrays.asList(docPrijavniObrazac, docMotivacijskoPismo, docCv, docDomovnica, docStatusStudenta, docPrijepisOcjena));

        Mobility mobility = new Mobility();
        mobility.setStudent(appUser);
        mobility.setInstitution(institution);
//        mobility.setMobilityStatus(mobilityStatusRepository.getOne(MobilityStatus.REQUESTED));
        mobilityService.applyMobility(mobility,
                Arrays.asList(docPrijavniObrazac, docMotivacijskoPismo, docCv, docDomovnica, docStatusStudenta, docPrijepisOcjena));

        return "redirect:/";
    }

    private Document createDocument(AppUser appUser, MultipartFile multipartFile, DocumentType documentType) throws IOException {
        Document document = new Document();
        document.setName(multipartFile.getOriginalFilename());
        document.setDescription(documentType.getName() + " za studenta " + appUser.getName() + " " + appUser.getSurname());
        document.setFileName(appUser.getJmbag() + "_" + multipartFile.getOriginalFilename());
        document.setFileContent(multipartFile.getBytes());
        document.setFileContentType(multipartFile.getContentType());
        document.setOwner(appUser);
        document.setDocumentType(documentType);

        return document;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/mobilities/edit/{id}")
    public String getExisting(Model model, @PathVariable Long id) throws NotFoundException {

        Optional<Mobility> mobility = mobilityRepository.findById(id);

        if (!mobility.isPresent()) {
            throw new NotFoundException("Mobility not found");
        }

        model.addAttribute("mobility", mobility.get());
        model.addAttribute("approvalList", approvalRepository.findByMobility_Id(id));
        model.addAttribute("mobilityStatusList", mobilityStatusRepository.findAll());

        return "mobilities/edit";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/mobilities/edit")
    public String edit(@ModelAttribute Mobility newMobility) {
        Mobility oldMobility = mobilityRepository.getOne(newMobility.getId());
        oldMobility.setInstitution(newMobility.getInstitution());
        oldMobility.setMobilityStart(newMobility.getMobilityStart());
        oldMobility.setMobilityEnd(newMobility.getMobilityEnd());
        oldMobility.setMobilityStatus(newMobility.getMobilityStatus());
        oldMobility.setPoints(newMobility.getPoints());
        mobilityRepository.save(oldMobility);

        return "redirect:/mobilities/details/" + oldMobility.getId();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "/mobilities/delete/{id}")
    public String delete(@PathVariable(name = "id") Long id) {
        mobilityRepository.deleteById(id);
        return "redirect:/mobilities";
    }

}
