package com.jobslok.ViewModel;

public class Messages {
    private String from, message,category,time,date,image;

    public Messages() {
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return category;
    }

    public void setType(String type) {
        this.category = category;
    }

    public Messages(String from, String messsage, String type, String time, String date) {
        this.from = from;
        this.message = messsage;
        this.category = type;
        this.time=time;
        this.date=date;
    }
}
