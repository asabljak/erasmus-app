package hr.tvz.master.erasmus.web;

import hr.tvz.master.erasmus.entity.Document;
import hr.tvz.master.erasmus.repository.DocumentRepository;
import hr.tvz.master.erasmus.storage.StorageService;
import javassist.NotFoundException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

//TODO rijesitibug s odabirom faze
@Controller
public class DocumentController {

    @Autowired
    DocumentRepository documentRepository;

    private final StorageService storageService;

    @Autowired
    public DocumentController(StorageService storageService) {
        this.storageService = storageService;
    }


    @GetMapping("/documents")
    public String getAll(Model model) {
//        List<String> documents = storageService.loadAll().map(
//                path -> MvcUriComponentsBuilder.fromMethodName(DocumentController.class,
//                        "serveFile", path.getFileName().toString()).build().toString())
//                .collect(Collectors.toList());
//
//        model.addAttribute("files", documents);
        model.addAttribute("documents", documentRepository.findAll());
        return "documents/list";
    }

    @GetMapping(path = "documents/details/{id}")
    public String getOne(Model model, @PathVariable(value = "id") Long id) {
        Document document = documentRepository.getOne(id);
        String filePath = MvcUriComponentsBuilder.fromMethodName(DocumentController.class,"serveFile",
                document.getFileName()).build().toString();

        model.addAttribute("document", document);
        model.addAttribute("file", filePath);

        return "documents/details";
    }

    @GetMapping("/documents/create")
    public String getEmpty(Model model){
        model.addAttribute("document", new Document());
        return "documents/create";
    }

    @PostMapping("/documents/create")
    public String create(@ModelAttribute Document document, @RequestParam("file") MultipartFile file,
                         RedirectAttributes redirectAttributes) {

        file.getOriginalFilename().replace(file.getOriginalFilename(), FilenameUtils.getBaseName(file.getOriginalFilename()).concat("_" + String.valueOf(System.currentTimeMillis())
                + "." + FilenameUtils.getExtension(file.getOriginalFilename())).toLowerCase());

//        file.getOriginalFilename().replace(file.getOriginalFilename().concat(String.valueOf(System.currentTimeMillis())));


//        try {
//            File renamed = new File(file.getOriginalFilename().concat(String.valueOf(System.currentTimeMillis())));
//            file.transferTo(renamed);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        storageService.store(file);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        String fileName = storageService.load(file.getOriginalFilename()).getFileName().toString();

        document.setFileName(fileName);
        document.setPhysicalPath(this.getFilePhisycalPath(file).get());
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

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    private Optional<String> getFilePhisycalPath(MultipartFile multipartFile) {
        File tmpFile;
        Optional<String> path =  Optional.empty();

        try {
            tmpFile= new File(multipartFile.getOriginalFilename());
            multipartFile.transferTo(tmpFile);
            path = Optional.of(tmpFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return path;
    }
}
