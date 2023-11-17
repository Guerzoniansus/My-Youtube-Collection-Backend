package com.myytcollection.service;

import com.myytcollection.dto.TagDTO;
import com.myytcollection.mapper.TagMapper;
import com.myytcollection.model.Tag;
import com.myytcollection.model.User;
import com.myytcollection.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This class is used for managing tags.
 */
@Service
public class TagService {
    private final TagRepository tagRepository;
    private final TagMapper mapper;

    public TagService(TagRepository tagRepository, TagMapper mapper) {
        this.tagRepository = tagRepository;
        this.mapper = mapper;
    }

    /**
     * Save a tag to the database.
     * @param tag The tag to save.
     * @return The newly created tag.
     */
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    /**
     * Creates new tags by setting the user and saves them to database. Generates an ID too if it didn't have one yet.
     * @param user The user associated with each tag.
     * @param tagDTOs A collection of tags to create.
     * @return The newly created tags.
     */
    public Set<Tag> createTags(User user, Set<TagDTO> tagDTOs) {
        Set<Tag> tags = new HashSet<>();

        tagDTOs.forEach(dto -> {
           Tag tag = mapper.toModel(dto, user);
           tags.add(saveTag(tag));
        });

        return tags;
    }

    /**
     * Returns all tags of a user, or an empty list if the user has no tags.
     * @param user The user whose tags to get.
     * @return All tags of a user, or an empty list if the user has no tags, converted to TagDTOs.
     */
    public Set<TagDTO> getAllTagsAsDTOs(User user) {
        return getAllTags(user).stream().map(mapper::toDTO).collect(Collectors.toSet());
    }

    /**
     * Returns all tags of a user, or an empty list if the user has no tags.
     * @param user The user whose tags to get.
     * @return All tags of a user, or an empty list if the user has no tags.
     */
    public Set<Tag> getAllTags(User user) {
        return tagRepository.findByUserEmail(user.getEmail());
    }
}
