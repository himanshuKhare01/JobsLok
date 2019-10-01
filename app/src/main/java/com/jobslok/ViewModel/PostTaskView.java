package com.jobslok.ViewModel;

public class PostTaskView {
    private String title;
    private String description;
    private boolean remotely;
    private String location;
    private String date;
    private String budget;
    private String hour;
    private String pricehour;
    private Long numberof_tasker;
    private String image;
    private String uid;
    private String type;
    private String name;
    private String postDate;
    String status;
    String offers;


    public PostTaskView(String title, String description, boolean remotely, String location, String date, String budget, String hour, String pricehour, long numberof_tasker,String image,String uid,String type,String name,String postTime,String status,String offers) {
        this.title = title;
        this.description = description;
        this.remotely = remotely;
        this.location = location;
        this.date = date;
        this.budget = budget;
        this.hour = hour;
        this.pricehour = pricehour;
        this.numberof_tasker = numberof_tasker;
        this.image=image;
        this.uid=uid;
        this.type=type;
        this.name=name;
        this.postDate =postTime;
        this.status=status;
        this.offers=offers;
    }

    public String getOffers() {
        return offers;
    }

    public void setOffers(String offers) {
        this.offers = offers;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public PostTaskView() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getRemotely() {
        return remotely;
    }

    public void setRemotely(boolean remotely) {
        this.remotely = remotely;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getPricehour() {
        return pricehour;
    }

    public void setPricehour(String pricehour) {
        this.pricehour = pricehour;
    }

    public Long getNumberof_tasker() {
        return numberof_tasker;
    }

    public void setNumberof_tasker(long numberof_tasker) {
        this.numberof_tasker = numberof_tasker;
    }
}
