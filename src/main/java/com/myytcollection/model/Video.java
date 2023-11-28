package com.myytcollection.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.annotation.Nullable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table
public class Video {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int videoID;

    private String videoCode;
    private String title;
    private String channel;

    @Nullable
    private String alternativeTitle;
    private Date dateCreated;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "email", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.MERGE
            })
    @JoinTable(name = "video_tag",
            joinColumns = { @JoinColumn(name = "video_id") },
            inverseJoinColumns = { @JoinColumn(name = "tag_id") })
    private Set<Tag> tags = new HashSet<>();

    public Video(int videoID, String videoCode, String title, String channel, @Nullable String alternativeTitle, Date dateCreated, User user, Set<Tag> tags) {
        this.videoID = videoID;
        this.videoCode = videoCode;
        this.title = title;
        this.channel = channel;
        this.alternativeTitle = alternativeTitle;
        this.dateCreated = dateCreated;
        this.user = user;
        this.tags = tags;
    }

    public Video(int videoID, String videoCode, String title, String channel, @Nullable String alternativeTitle, Date dateCreated, User user) {
        this.videoID = videoID;
        this.videoCode = videoCode;
        this.title = title;
        this.channel = channel;
        this.alternativeTitle = alternativeTitle;
        this.dateCreated = dateCreated;
        this.user = user;
    }

    public Video() {}

    @Override
    public String toString() {
        return "Video{" +
                "videoID=" + videoID +
                ", videoCode='" + videoCode + '\'' +
                ", title='" + title + '\'' +
                ", channel='" + channel + '\'' +
                ", alternativeTitle='" + alternativeTitle + '\'' +
                ", dateCreated=" + dateCreated +
                ", user=" + user +
                ", tags=" + tags +
                '}';
    }

    public void addTag(Tag tag) {
        this.tags.add(tag);
        tag.getVideos().add(this);
    }

    public void removeTag(long tagId) {
        Tag tag = this.tags.stream().filter(t -> t.getTagID() == tagId).findFirst().orElse(null);
        if (tag != null) {
            this.tags.remove(tag);
            tag.getVideos().remove(this);
        }
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
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

    @Nullable
    public String getAlternativeTitle() {
        return alternativeTitle;
    }

    public void setAlternativeTitle(@Nullable String alternativeTitle) {
        this.alternativeTitle = alternativeTitle;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
