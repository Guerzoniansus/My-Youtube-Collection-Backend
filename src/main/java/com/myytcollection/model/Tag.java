package com.myytcollection.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames={"text", "email"})
})
public class Tag implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int tagID;

    private String text;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "email", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.MERGE
            },
            mappedBy = "tags")
    @JsonIgnore
    private Set<Video> videos = new HashSet<>();

    public Tag(int tagID, String text, User user, Set<Video> videos) {
        this.tagID = tagID;
        this.text = text;
        this.user = user;
        this.videos = videos;
    }

    public Tag(int tagID, String text, User user) {
        this.tagID = tagID;
        this.text = text;
        this.user = user;
        this.videos = new HashSet<>();
    }

    public Tag() {}

    @Override
    public String toString() {
        // Videos do not get printed because that would cause an infinite loop stackoverflow exception
        return "Tag{" +
                "tagID=" + tagID +
                ", text='" + text + '\'' +
                ", user=" + user +
                '}';
    }

    public Set<Video> getVideos() {
        return videos;
    }

    public void setVideos(Set<Video> videos) {
        this.videos = videos;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
