package hr.tvz.master.erasmus.web.document;

import hr.tvz.master.erasmus.entity.document.DocumentType;
import hr.tvz.master.erasmus.repository.ApprovalTypeRepository;
import hr.tvz.master.erasmus.repository.DocumentTypeRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;
//TODO rijesitibug s odabirom faze
@Controller
public class DocumentTypeController {

    @Autowired
    DocumentTypeRepository documentTypeRepository;

    @Autowired
    ApprovalTypeRepository approvalTypeRepository;

    @PreAuthorize("hasRole('ADMIN') or hasRole('COORDINATOR') or hasRole('SUBJECT_COORDINATOR')")
    @GetMapping("/documentTypes")
    public String getAll(Model model) {
        List<DocumentType> list = documentTypeRepository.findAll();
        model.addAttribute("documentTypes", list);
        return "documentTypes/list";
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('COORDINATOR') or hasRole('SUBJECT_COORDINATOR')")
    @GetMapping(path = "documentTypes/details/{id}")
    public String getOne(Model model, @PathVariable(value = "id") Long id) {
        model.addAttribute("documentType", documentTypeRepository.getOne(id));
        return "documentTypes/details";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/documentTypes/create")
    public String getEmpty(Model model){
        model.addAttribute("documentType", new DocumentType());
        model.addAttribute("phaseList", approvalTypeRepository.findAll());
        return "documentTypes/create";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/documentTypes/create")
    public String create(@ModelAttribute DocumentType documentType) {
        DocumentType createdDocumentType = documentTypeRepository.save(documentType);
        return "redirect:/documentTypes/details/" + createdDocumentType.getId();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/documentTypes/edit/{id}")
    public String getExisting(Model model, @PathVariable Long id) throws NotFoundException {

        Optional<DocumentType> documentType = documentTypeRepository.findById(id);

        if (!documentType.isPresent()) {
            throw new NotFoundException("DocumentType not found");
        }

        model.addAttribute("documentType", documentType.get());
        model.addAttribute("phaseList", approvalTypeRepository.findAll());

        return "documentTypes/edit";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/documentTypes/edit")
    public String edit(@ModelAttribute DocumentType newDocumentType) {
        if(!newDocumentType.isValid()) {
            throw new IllegalStateException("Sva polja nisu pravilno postavljena");
        }

        DocumentType oldDocumentType = documentTypeRepository.getOne(newDocumentType.getId());
        oldDocumentType.setName(newDocumentType.getName());
        oldDocumentType.setDescription(newDocumentType.getDescription());
        oldDocumentType.setPhases(newDocumentType.getPhases());
        documentTypeRepository.save(oldDocumentType);

        return "redirect:/documentTypes/details/" + oldDocumentType.getId();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "/documentTypes/delete/{id}")
    public String deleteProduct(@PathVariable(name = "id") Long id) {
        documentTypeRepository.deleteById(id);
        return "redirect:/documentTypes";
    }
}
