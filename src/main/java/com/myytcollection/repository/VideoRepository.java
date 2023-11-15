package com.myytcollection.repository;

import com.myytcollection.model.Tag;
import com.myytcollection.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VideoRepository extends JpaRepository<Video, String> {
    List<Video> findByUserEmail(String userEmail);
}
