package hr.tvz.master.erasmus.service;

import hr.tvz.master.erasmus.entity.institution.Institution;
import hr.tvz.master.erasmus.entity.institution.Review;
import hr.tvz.master.erasmus.entity.user.AppUser;
import hr.tvz.master.erasmus.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public Review saveReview(Review review, Institution institution, AppUser appUser) {
        review.setAppUser(appUser);
        review.setInstitution(institution);
        reviewRepository.save(review);
        return review;
    }

    public Double getAvgRatingForInstitution(Long institutionId) {
        return reviewRepository.getAvgRatingForInstitution(institutionId);
    }

    public List<Review> getAllForInstitution(Long institutionId) {
        return  reviewRepository.findAllByInstitution_Id(institutionId);
    }

    public void delete(Long institutionId) {
        reviewRepository.deleteById(institutionId);
    }

}
