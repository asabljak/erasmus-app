package hr.tvz.master.erasmus.web.mobility;

import hr.tvz.master.erasmus.entity.document.Document;
import hr.tvz.master.erasmus.entity.document.DocumentType;
import hr.tvz.master.erasmus.entity.institution.Institution;
import hr.tvz.master.erasmus.entity.mobility.Mobility;
import hr.tvz.master.erasmus.entity.mobility.MobilityStatus;
import hr.tvz.master.erasmus.entity.user.AppUser;
import hr.tvz.master.erasmus.entity.user.Role;
import hr.tvz.master.erasmus.repository.*;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    DocumentTypeRepository documentTypeRepository;

    @Autowired
    DocumentRepository documentRepository;

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

    @GetMapping("/mobilities/create/{institutionId}")
    public String prepareForVisitorRequest(Model model, @PathVariable(name = "institutionId") Long institutionId) {
        model.addAttribute("institution", institutionRepository.getOne(institutionId));
        return "mobilities/createForInstitution";
    }

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
            e.printStackTrace();
            //TODO ispis u log
        }
        documentRepository.saveAll(Arrays.asList(docPrijavniObrazac, docMotivacijskoPismo, docCv, docDomovnica, docStatusStudenta, docPrijepisOcjena));

        Mobility mobility = new Mobility();
        mobility.setStudent(appUser);
        mobility.setInstitution(institution);
        mobility.setMobilityStatus(mobilityStatusRepository.getOne(MobilityStatus.REQUESTED));
        mobilityRepository.save(mobility);

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
        oldMobility.setMobilityEnd(newMobility.getMobilityEnd());
        oldMobility.setMobilityStatus(newMobility.getMobilityStatus());
        oldMobility.setPoints(newMobility.getPoints());
        mobilityRepository.save(oldMobility);

        return "redirect:/mobilities/details/" + oldMobility.getId();
    }

    @GetMapping(path = "/mobilities/delete/{id}")
    public String delete(@PathVariable(name = "id") Long id) {
        mobilityRepository.deleteById(id);
        return "redirect:/mobilities";
    }

}
