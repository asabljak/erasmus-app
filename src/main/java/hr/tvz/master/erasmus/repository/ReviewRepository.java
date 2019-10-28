package hr.tvz.master.erasmus.repository;

import hr.tvz.master.erasmus.entity.institution.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {

    @Query("select avg(rating) from Review r where r.institution.id = :instutionId")
    Double getAvgRatingForInstitution(@Param("instutionId") Long instutionId);

    List<Review> findAllByInstitution_Id(Long institutionId);

    void deleteById(Long institutionId);
}

