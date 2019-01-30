package com.issuetracker.issuetracker.model;

public class ParticipantInfo {

    private Integer id;
    private String text;

    public ParticipantInfo(Integer id, String text) {
        this.id = id;
        this.text = text;
    }

    public ParticipantInfo() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
