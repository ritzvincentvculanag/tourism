package io.github.rmmc.rmmctourism.model;

import com.google.firebase.Timestamp;

/**
 * Model class representing a review associated with a destination.
 */
public class Review {

    // Constants for Firestore collection and field names
    public static final String collectionName = "Review";
    public static final String userIdField = "userId";
    public static final String destinationIdField = "destinationId";
    public static final String contentField = "content";
    public static final String datePublishedField = "datePublished";

    private String reviewId;  // Unique identifier for the review
    private String userId;  // ID of the user who created the review
    private String destinationId;  // ID of the destination the review is associated with
    private String content;  // Content of the review
    private Timestamp datePublished;  // Timestamp indicating when the review was published

    // Empty constructor required for Firestore
    public Review() {

    }

    /**
     * Constructor for creating a new review.
     *
     * @param userId        ID of the user who created the review.
     * @param destinationId ID of the destination the review is associated with.
     * @param content       Content of the review.
     * @param datePublished Timestamp indicating when the review was published.
     */
    public Review(String userId, String destinationId, String content, Timestamp datePublished) {
        this.userId = userId;
        this.destinationId = destinationId;
        this.content = content;
        this.datePublished = datePublished;
    }

    /**
     * Constructor for updating an existing review.
     *
     * @param reviewId      Unique identifier for the review.
     * @param userId        ID of the user who created the review.
     * @param destinationId ID of the destination the review is associated with.
     * @param content       Content of the review.
     * @param datePublished Timestamp indicating when the review was published.
     */
    public Review(String reviewId, String userId, String destinationId, String content, Timestamp datePublished) {
        this.reviewId = reviewId;
        this.userId = userId;
        this.destinationId = destinationId;
        this.content = content;
        this.datePublished = datePublished;
    }

    // Getter and setter methods for each field

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
