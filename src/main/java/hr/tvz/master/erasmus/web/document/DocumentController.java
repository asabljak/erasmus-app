package hr.tvz.master.erasmus.web.document;

import hr.tvz.master.erasmus.entity.document.Document;
import hr.tvz.master.erasmus.entity.document.DocumentType;
import hr.tvz.master.erasmus.entity.user.AppUser;
import hr.tvz.master.erasmus.entity.user.Role;
import hr.tvz.master.erasmus.repository.DocumentRepository;
import hr.tvz.master.erasmus.repository.DocumentTypeRepository;
import hr.tvz.master.erasmus.repository.MobilityRepository;
import hr.tvz.master.erasmus.service.NotificationService;
import hr.tvz.master.erasmus.web.AbstractErasmusController;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class DocumentController extends AbstractErasmusController {

    @Autowired
    DocumentRepository documentRepository;

    @Autowired
    DocumentTypeRepository documentTypeRepository;

    @Autowired
    NotificationService notificationService;

    @Autowired
    MobilityRepository mobilityRepository;

    @PreAuthorize("hasRole('ADMIN') or hasRole('COORDINATOR') or hasRole('SUBJECT_COORDINATOR') or hasRole('ERASMUS_STUDENT')")
    @GetMapping("/documents")
    public String getAll(Model model) {
        List<Document> list = null;
        List loggedUseerRoles = getLoggedInUser().getIdRoles();
        if (loggedUseerRoles.contains(Role.ROLE_ADMIN) || loggedUseerRoles.contains(Role.ROLE_COORDINATOR)) {
            list = documentRepository.findAll();
        } else if (loggedUseerRoles.contains(Role.ROLE_ERASMUS_STUDENT)) {
            list = documentRepository.findByOwner(getLoggedInUser());
        } else if (loggedUseerRoles.contains(Role.ROLE_SUBJECT_COORDINATOR)) {
            list = documentRepository.findByDocumentType_Id(DocumentType.LA);
        }
        model.addAttribute("documents", list);
        return "documents/list";
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('COORDINATOR') or hasRole('SUBJECT_COORDINATOR') or hasRole('ERASMUS_STUDENT')")
    @GetMapping(path = "documents/details/{id}")
    public String getOne(Model model, @PathVariable(value = "id") Long id) {
        model.addAttribute("document", documentRepository.getOne(id));

        return "documents/details";
    }

    @ResponseBody
    @GetMapping("/documents/download/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COORDINATOR') or hasRole('SUBJECT_COORDINATOR') or hasRole('ERASMUS_STUDENT')")
    public ResponseEntity<InputStreamResource> downloadPDFFile(@PathVariable(value="id") Long id) throws IOException {
        Document document = documentRepository.getOne(id);
        byte[] content = document.getFileContent();

        return ResponseEntity
                .ok()
                .contentLength(content.length)
                .contentType(MediaType.parseMediaType(document.getFileContentType()))
                .header("Content-Disposition", "attachment; filename=\"" + document.getFileName() + "\"")
                .body(new InputStreamResource(new ByteArrayInputStream(content)));

    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('ERASMUS_STUDENT')")
    @GetMapping("/documents/create")
    public String getEmpty(Model model){
        model.addAttribute("mobilityList", mobilityRepository.findAllByStudent(getLoggedInUser()));
        model.addAttribute("document", new Document());
        model.addAttribute("documentTypeList", documentTypeRepository.findAll());
        return "documents/create";
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('ERASMUS_STUDENT')")
    @PostMapping("/documents/create")
    public String create(@ModelAttribute Document document, @RequestParam("file") MultipartFile file) throws IOException {
        AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        document.setFileContent(file.getBytes());
        document.setFileName(file.getOriginalFilename());
        document.setFileContentType(file.getContentType());
        document.setOwner(appUser);
        Document createdDocument = documentRepository.save(document);
        if (DocumentType.LA.equals(createdDocument.getDocumentType().getId())) {
            //TODO dodati link na dokumente
            notificationService.sendLaApprovalRequest(appUser, createdDocument);
        }
        return "redirect:/documents/details/" + createdDocument.getId();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/documents/edit/{id}")
    public String getExisting(Model model, @PathVariable Long id) throws NotFoundException {

        Optional<Document> document = documentRepository.findById(id);

        if (!document.isPresent()) {
            throw new NotFoundException("Document not found");
        }

        model.addAttribute("document", document.get());
        model.addAttribute("documentTypeList", documentTypeRepository.findAll());

        return "documents/edit";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/documents/edit")
    public String edit(@ModelAttribute Document newDocument) {
        if(!newDocument.isValid()) {
            throw new IllegalStateException("Sva polja nisu pravilno postavljena");
        }

        Document oldDocument = documentRepository.getOne(newDocument.getId());
        oldDocument.setName(newDocument.getName());
        oldDocument.setDescription(newDocument.getDescription());
        documentRepository.save(oldDocument);

        return "redirect:/documents/details/" + oldDocument.getId();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "/documents/delete/{id}")
    public String deleteProduct(@PathVariable(name = "id") Long id) {
        documentRepository.deleteById(id);
        return "redirect:/documents";
    }
}
