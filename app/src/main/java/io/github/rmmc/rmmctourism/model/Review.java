package io.github.rmmc.rmmctourism.model;

public class Review {

    public static final String collectionName = "userReview";
    public static final String userUIDField = "userUID";
    public static final String destinationUIDField = "destinationUID";
    public static final String rateField = "rate";

    private String reviewUID;
    private String userUID;
    private String destinationUID;
    private int Rate;

    public Review() {
    }

    public Review(String userUID, String destinationUID, int rate) {
        this.userUID = userUID;
        this.destinationUID = destinationUID;
        Rate = rate;
    }

    public String getReviewUID() {
        return reviewUID;
    }

    public void setReviewUID(String reviewUID) {
        this.reviewUID = reviewUID;
    }

    public String getUserUID() {
        return userUID;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }

    public String getDestinationUID() {
        return destinationUID;
    }

    public void setDestinationUID(String destinationUID) {
        this.destinationUID = destinationUID;
    }

    public int getRate() {
        return Rate;
    }

    public void setRate(int rate) {
        Rate = rate;
    }
}
