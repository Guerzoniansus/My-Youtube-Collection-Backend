package com.myytcollection.repository;

import com.myytcollection.model.Tag;
import com.myytcollection.model.User;
import com.myytcollection.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface VideoRepository extends JpaRepository<Video, Integer> {
    Set<Video> findByUserOrderByDateCreatedDesc(User user);
    Set<Video> findByUserAndTitleContainingAndTagsIn(User user, String title, Set<Tag> tags);
}
