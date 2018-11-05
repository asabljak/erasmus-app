package hr.tvz.master.erasmus.repository;

import hr.tvz.master.erasmus.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {
}
