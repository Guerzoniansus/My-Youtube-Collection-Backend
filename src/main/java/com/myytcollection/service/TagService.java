package com.myytcollection.service;

import com.myytcollection.dto.TagDTO;
import com.myytcollection.mapper.DataMapper;
import com.myytcollection.mapper.TagMapper;
import com.myytcollection.model.Tag;
import com.myytcollection.model.User;
import com.myytcollection.repository.TagRepository;
import com.myytcollection.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TagService {
    private final TagRepository tagRepository;
    private final DataMapper<Tag, TagDTO> mapper;

    public TagService(TagRepository tagRepository, TagMapper mapper) {
        this.tagRepository = tagRepository;
        this.mapper = mapper;
    }

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
// If I put this code here then I would also have to duplicate this code in VideoService
//        Set<Tag> tags = new HashSet<>();
//
//        tagDTOs.forEach(dto -> {
//            new Tag(dto.getTagID(), dto.getText(), user);
//        });


        Set<Tag> tags = new HashSet<>();

        tagDTOs.forEach(dto -> {
           Tag tag = mapper.toModel(dto);
           tag.setUser(user);
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
