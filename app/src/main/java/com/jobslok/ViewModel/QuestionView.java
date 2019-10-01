package com.jobslok.ViewModel;

public class QuestionView {
    private String image;
    private String name;
    private String question;
    private String date;
    private String uid;



    public String getImage() {
        return image;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public QuestionView() {
    }

    public QuestionView(String image, String name, String description, String date,String uid) {
        this.image = image;
        this.name = name;
        this.question = description;
        this.date = date;
        this.uid=uid;
    }
}
