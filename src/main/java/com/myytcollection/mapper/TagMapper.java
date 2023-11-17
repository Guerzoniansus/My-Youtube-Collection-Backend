package com.myytcollection.mapper;

import com.myytcollection.dto.TagDTO;
import com.myytcollection.model.Tag;
import com.myytcollection.model.User;
import com.myytcollection.repository.TagRepository;
import org.springframework.stereotype.Component;

@Component
public class TagMapper {

    private final TagRepository tagRepository;

    public TagMapper(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    /**
     * Retrieves a tag from the database.
     * @param dto The tag whose ID will be used for database retrieval.
     * @return The found tag,
     */
    public Tag fromDatabase(TagDTO dto) {
        return tagRepository.getReferenceById(dto.getTagID());
    }

    /**
     * Creates a new model based on the given DTO.
     * @param dto The DTO to convert to a model.
     * @param user The user this tag belogns to.
     * @return A new tag.
     */
    public Tag toModel(TagDTO dto, User user) {
        return new Tag(dto.getTagID(), dto.getText(), user);
    }

    /**
     * Converts a tag model to a tag DTO.
     * @param tag The tag to convert.
     * @return The converted tag DTO.
     */
    public TagDTO toDTO(Tag tag) {
        return new TagDTO(tag.getTagID(), tag.getText());
    }
}

