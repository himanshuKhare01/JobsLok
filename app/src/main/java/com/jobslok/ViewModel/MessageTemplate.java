package com.jobslok.ViewModel;
public class MessageTemplate {
    private String image;
    private String name;
    private String category;
    private String lastmessage;
    private String count;
    private String is;
    private String taskerID;
    private String posterID;
    private String postID;

    public MessageTemplate() {
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

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLastmessage() {
        return lastmessage;
    }

    public void setLastmessage(String lastmessage) {
        this.lastmessage = lastmessage;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getIs() {
        return is;
    }

    public void setIs(String is) {
        this.is = is;
    }

    public String getTaskerID() {
        return taskerID;
    }

    public void setTaskerID(String taskerID) {
        this.taskerID = taskerID;
    }

    public String getPosterID() {
        return posterID;
    }

    public void setPosterID(String posterID) {
        this.posterID = posterID;
    }

    public MessageTemplate(String image, String name, String category, String lastmessage, String count, String is, String taskerID, String posterID,String postID) {
        this.image = image;
        this.name = name;
        this.category = category;
        this.lastmessage = lastmessage;
        this.count = count;
        this.is = is;
        this.taskerID = taskerID;
        this.posterID = posterID;
        this.postID=postID;
    }
}