package hr.tvz.master.erasmus.entity;

import javax.persistence.Entity;
import java.util.Objects;
import java.util.stream.Stream;
//TODO dodati usera kojie je vlassnik dokumenta
@Entity
public class Document extends AbstractErasmusEntity {
    private String name;
    private String description;
    private String fileName;
    private String physicalPath;

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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPhysicalPath() {
        return physicalPath;
    }

    public void setPhysicalPath(String physicalPath) {
        this.physicalPath = physicalPath;
    }

    @Override
    public boolean isValid() {
        return Stream.of(name)
                .noneMatch(Objects::isNull);
    }
}
