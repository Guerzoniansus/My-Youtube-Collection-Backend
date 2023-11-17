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

    public Tag fromDatabase(TagDTO dto) {
        return tagRepository.getReferenceById(dto.getTagID());
    }

    public Tag toModel(TagDTO dto, User user) {
        return new Tag(dto.getTagID(), dto.getText(), user);
    }

    public TagDTO toDTO(Tag tag) {
        return new TagDTO(tag.getTagID(), tag.getText());
    }
}

