package hr.tvz.master.erasmus.repository;

import hr.tvz.master.erasmus.entity.user.AppUser;
import hr.tvz.master.erasmus.entity.document.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findByOwner(AppUser owner);
}
