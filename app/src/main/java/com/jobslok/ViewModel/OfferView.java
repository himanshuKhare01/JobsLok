package com.jobslok.ViewModel;

public class OfferView {
    private String description;
    private String image;
    private String name;
    private String postDate;
    private String price;
    private String ratedBy;
    private String rating;
    private String taskCompleted;
    private String accepted;

    public OfferView() {
    }

    public OfferView(String description, String image, String name, String postDate, String price, String ratedBy, String rating, String taskCompleted,String accepted) {
        this.description = description;
        this.image = image;
        this.name = name;
        this.postDate = postDate;
        this.price = price;
        this.ratedBy = ratedBy;
        this.rating = rating;
        this.taskCompleted = taskCompleted;
        this.accepted=accepted;
    }

    public String getAccepted() {
        return accepted;
    }

    public void setAccepted(String accepted) {
        this.accepted = accepted;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRatedBy() {
        return ratedBy;
    }

    public void setRatedBy(String ratedBy) {
        this.ratedBy = ratedBy;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getTaskCompleted() {
        return taskCompleted;
    }

    public void setTaskCompleted(String taskCompleted) {
        this.taskCompleted = taskCompleted;
    }
}
