package com.myytcollection.dto;

public class TagDTO {
    private int tagID;
    private String text;

    public TagDTO(int tagID, String text) {
        this.tagID = tagID;
        this.text = text;
    }

    public TagDTO() {
    }

    @Override
    public String toString() {
        return "TagDTO{" +
                "tagID=" + tagID +
                ", text='" + text + '\'' +
                '}';
    }

    public int getTagID() {
        return tagID;
    }

    public void setTagID(int tagID) {
        this.tagID = tagID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
