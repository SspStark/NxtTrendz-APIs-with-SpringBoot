package com.example.nxttrendz1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;

import com.example.nxttrendz1.model.*;
import com.example.nxttrendz1.repository.*;

@Service
public class ReviewJpaService implements ReviewRepository {

    @Autowired
    private ReviewJpaRepository reviewJpaRepository;

    @Autowired
    private ProductJpaRepository productJpaRepository;

    @Override
    public ArrayList<Review> getReviews() {
        List<Review> reviewList = reviewJpaRepository.findAll();
        ArrayList<Review> reviews = new ArrayList<>(reviewList);
        return reviews;
    }

    @Override
    public Review getReviewById(int reviewId) {
        try {
            Review review = reviewJpaRepository.findById(reviewId).get();
            return review;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Review addReview(Review review) {
        Product product = review.getProduct();
        int productId = product.getProductId();
        try {
            product = productJpaRepository.findById(productId).get();
            review.setProduct(product);
            reviewJpaRepository.save(review);
            return review;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Wrong productId");
        }
    }

    @Override
    public Review updateReview(int reviewId, Review review) {
        try {
            Review newReview = reviewJpaRepository.findById(reviewId).get();
            if (review.getReviewContent() != null) {
                newReview.setReviewContent(review.getReviewContent());
            }
            if (review.getRating() != 0) {
                newReview.setRating(review.getRating());
            }
            if (review.getProduct() != null) {
                int productId = review.getProduct().getProductId();
                Product newProduct = productJpaRepository.findById(productId).get();
                newReview.setProduct(newProduct);
            }
            reviewJpaRepository.save(newReview);
            return newReview;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteReview(int reviewId) {
        try {
            reviewJpaRepository.deleteById(reviewId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    public Product getProductByReviewId(int reviewId) {
        try {
            Review review = reviewJpaRepository.findById(reviewId).get();
            return review.getProduct();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}