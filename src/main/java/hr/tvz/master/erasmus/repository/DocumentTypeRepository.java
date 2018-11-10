package hr.tvz.master.erasmus.repository;

import hr.tvz.master.erasmus.entity.document.DocumentType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentTypeRepository extends JpaRepository<DocumentType, Long> {
}
