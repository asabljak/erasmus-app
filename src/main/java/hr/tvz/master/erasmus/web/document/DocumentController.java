package hr.tvz.master.erasmus.web.document;

import hr.tvz.master.erasmus.entity.document.Document;
import hr.tvz.master.erasmus.repository.DocumentRepository;
import hr.tvz.master.erasmus.repository.DocumentTypeRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class DocumentController {

    @Autowired
    DocumentRepository documentRepository;

    @Autowired
    DocumentTypeRepository documentTypeRepository;

    @GetMapping("/documents")
    public String getAll(Model model) {
        List<Document> list = documentRepository.findAll();
        model.addAttribute("documents", list);
        return "documents/list";
    }

    @GetMapping(path = "documents/details/{id}")
    public String getOne(Model model, @PathVariable(value = "id") Long id) throws IOException{
        model.addAttribute("document", documentRepository.getOne(id));

        return "documents/details";
    }

    @ResponseBody
    @GetMapping("/documents/download/{id}")
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

    @GetMapping("/documents/create")
    public String getEmpty(Model model){
        model.addAttribute("document", new Document());
        model.addAttribute("documentTypeList", documentTypeRepository.findAll());
        return "documents/create";
    }

    @PostMapping("/documents/create")
    public String create(@ModelAttribute Document document, @RequestParam("file") MultipartFile file,
                         RedirectAttributes redirectAttributes) throws IOException {
        document.setFileContent(file.getBytes());
        document.setFileName(file.getOriginalFilename());
        document.setFileContentType(file.getContentType());
        Document createdDocument = documentRepository.save(document);
        return "redirect:/documents/details/" + createdDocument.getId();
    }

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

    @GetMapping(path = "/documents/delete/{id}")
    public String deleteProduct(@PathVariable(name = "id") Long id) {
        documentRepository.deleteById(id);
        return "redirect:/documents";
    }
}
