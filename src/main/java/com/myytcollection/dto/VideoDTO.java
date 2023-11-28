package com.myytcollection.dto;

import com.myytcollection.model.Tag;

import javax.annotation.Nullable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class VideoDTO {
    private int videoID;
    private String videoCode;
    private String title;
    private String channel;
    private String alternativeTitle;
    private Set<TagDTO> tags = new HashSet<>();

    public VideoDTO(int videoID, String videoCode, String title, String channel, String alternativeTitle, Set<TagDTO> tags) {
        this.videoID = videoID;
        this.videoCode = videoCode;
        this.title = title;
        this.channel = channel;
        this.alternativeTitle = alternativeTitle;
        this.tags = tags;
    }

    public VideoDTO() {
    }

    @Override
    public String toString() {
        return "VideoDTO{" +
                "videoID=" + videoID +
                ", videoCode='" + videoCode + '\'' +
                ", title='" + title + '\'' +
                ", channel='" + channel + '\'' +
                ", alternativeTitle='" + alternativeTitle + '\'' +
                ", tags=" + tags +
                '}';
    }

    public int getVideoID() {
        return videoID;
    }

    public void setVideoID(int videoID) {
        this.videoID = videoID;
    }

    public String getVideoCode() {
        return videoCode;
    }

    public void setVideoCode(String videoCode) {
        this.videoCode = videoCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getAlternativeTitle() {
        return alternativeTitle;
    }

    public void setAlternativeTitle(String alternativeTitle) {
        this.alternativeTitle = alternativeTitle;
    }

    public Set<TagDTO> getTags() {
        return tags;
    }

    public void setTags(Set<TagDTO> tags) {
        this.tags = tags;
    }
}
