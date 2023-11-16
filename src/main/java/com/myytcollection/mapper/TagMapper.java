package com.myytcollection.mapper;

import com.myytcollection.dto.TagDTO;
import com.myytcollection.model.Tag;
import com.myytcollection.model.User;
import org.springframework.stereotype.Component;

@Component
public class TagMapper implements DataMapper<Tag, TagDTO> {

    @Override
    public Tag toModel(TagDTO dto) {
        return new Tag(dto.getTagID(), dto.getText(), null);
    }

    @Override
    public TagDTO toDTO(Tag model) {
        return new TagDTO(model.getTagID(), model.getText());
    }
}

