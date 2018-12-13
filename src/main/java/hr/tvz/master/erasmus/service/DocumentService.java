package hr.tvz.master.erasmus.service;

import hr.tvz.master.erasmus.entity.document.Document;
import hr.tvz.master.erasmus.entity.document.DocumentType;
import hr.tvz.master.erasmus.entity.user.AppUser;
import hr.tvz.master.erasmus.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    public Document createDocument(AppUser appUser, MultipartFile multipartFile, DocumentType documentType) throws IOException {
        Document document = new Document();
        document.setName(multipartFile.getOriginalFilename());
        document.setDescription(documentType.getName() + " za studenta" + appUser.getName() + " " + appUser.getSurname());
        document.setFileName(appUser.getJmbag() + "_" + multipartFile.getOriginalFilename());
        document.setFileContent(multipartFile.getBytes());
        document.setFileContentType(multipartFile.getContentType());
        document.setOwner(appUser);
        document.setDocumentType(documentType);

        return document;
    }

    public boolean saveDocuments(List<Document> documents) {
        documents.stream().forEach(d -> documentRepository.save(d));
        return  Boolean.TRUE;
    }
}
