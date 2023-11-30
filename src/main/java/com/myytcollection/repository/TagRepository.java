package com.myytcollection.repository;

import com.myytcollection.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Set;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Integer> {
    Set<Tag> findByUserEmail(String userEmail);
}
