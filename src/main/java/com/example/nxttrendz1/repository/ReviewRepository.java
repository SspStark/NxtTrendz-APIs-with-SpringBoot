package com.example.nxttrendz1.repository;

import java.util.*;

import com.example.nxttrendz1.model.*;

public interface ReviewRepository {

    ArrayList<Review> getReviews();

    Review getReviewById(int reviewId);

    Review addReview(Review review);

    Review updateReview(int reviewId, Review review);

    void deleteReview(int reviewId);

    Product getProductByReviewId(int reviewId);
}