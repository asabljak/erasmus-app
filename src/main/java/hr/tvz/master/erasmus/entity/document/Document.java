package hr.tvz.master.erasmus.entity.document;

import hr.tvz.master.erasmus.entity.AbstractErasmusEntity;
import hr.tvz.master.erasmus.entity.user.AppUser;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.stream.Stream;

@Entity
public class Document extends AbstractErasmusEntity {
    private String name;

    private String description;

    @OneToOne(targetEntity = AppUser.class)
    private AppUser owner;

    @OneToOne(targetEntity = DocumentType.class)
    private DocumentType documentType;

    @Basic(fetch = FetchType.LAZY)
    private byte[] fileContent;

    private String fileName;

    private String fileContentType;

    //TODO treba li ovo?
    private LocalDateTime seen;

    //Getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AppUser getOwner() {
        return owner;
    }

    public void setOwner(AppUser owner) {
        this.owner = owner;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public byte[] getFileContent() {
        return fileContent;
    }

    public void setFileContent(byte[] fileContent) {
        this.fileContent = fileContent;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    public LocalDateTime getSeen() {
        return seen;
    }

    public void setSeen(LocalDateTime seen) {
        this.seen = seen;
    }

    @Override
    public boolean isValid() {
        return Stream.of(name, fileContent, fileName, fileContentType)
                .noneMatch(Objects::isNull);
    }
}
