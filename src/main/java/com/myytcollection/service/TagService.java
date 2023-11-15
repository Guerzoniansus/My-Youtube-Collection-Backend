package com.myytcollection.service;

import com.myytcollection.model.Tag;
import com.myytcollection.model.User;
import com.myytcollection.repository.TagRepository;
import com.myytcollection.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class TagService {
    private final UserRepository userRepository;
    private final TagRepository tagRepository;

    public TagService(UserRepository userRepository, TagRepository tagRepository) {
        this.userRepository = userRepository;
        this.tagRepository = tagRepository;
    }

    public Tag saveTag(String email, Tag tag) {
        User user = userRepository.findById(email).get();

        try {
            tag.setUser(user);
            return tagRepository.save(tag);
        } catch (Exception e) {
            // Should throw SQLException but the IDE disagrees
            // Do nothing, it means the frontend did something stupid and this tag already exists in the database
            return tag;
        }
    }

    /**
     * Returns all tags of a user, or an empty list if the user has no tags.
     * @param email The user's email
     * @return All tags of a user, or an empty list if the user has no tags.
     */
    public List<Tag> getAllTags(String email) {
        return tagRepository.findByUserEmail(email);
    }
}
