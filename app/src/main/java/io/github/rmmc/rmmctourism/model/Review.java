package io.github.rmmc.rmmctourism.model;

import com.google.firebase.Timestamp;

public class Review {

    public static final String collectionName = "Review";
    public static final String userIdField = "userId";
    public static final String destinationIdField = "destinationId";
    public static final String contentField = "content";
    public static final String datePublishedField = "datePublished";
    private String reviewId;
    private String userId;
    private String destinationId;
    private String content;
    private Timestamp datePublished;


    public Review() {
    }

    public Review(String userId, String destinationId, String content, Timestamp datePublished) {
        this.userId = userId;
        this.destinationId = destinationId;
        this.content = content;
        this.datePublished = datePublished;
    }

    public Review(String reviewId, String userId, String destinationId, String content, Timestamp datePublished) {
        this.reviewId = reviewId;
        this.userId = userId;
        this.destinationId = destinationId;
        this.content = content;
        this.datePublished = datePublished;
    }

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(String destinationId) {
        this.destinationId = destinationId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(Timestamp datePublished) {
        this.datePublished = datePublished;
    }
}
