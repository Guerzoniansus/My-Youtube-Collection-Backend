package com.myytcollection.mapper;

import com.myytcollection.dto.TagDTO;
import com.myytcollection.model.Tag;
import com.myytcollection.model.User;
import com.myytcollection.repository.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class TagMapperTest {

    @Mock
    private TagRepository tagRepository;

    private TagMapper tagMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        tagMapper = new TagMapper(tagRepository);
    }

    @Test
    public void testFromDatabase_Success() {
        TagDTO tagDTO = new TagDTO();
        tagDTO.setTagID(1);

        Tag tag = new Tag();
        tag.setTagID(1);

        when(tagRepository.getReferenceById(tagDTO.getTagID())).thenReturn(tag);

        Tag result = tagMapper.fromDatabase(tagDTO);

        assertEquals(tag, result);
    }

    @Test
    public void testToModel_Success() {
        TagDTO tagDTO = new TagDTO();
        tagDTO.setTagID(1);

        User user = new User("test@example.com");
        Tag tag = tagMapper.toModel(tagDTO, user);

        assertEquals(tagDTO.getTagID(), tag.getTagID());
        assertEquals(user, tag.getUser());
    }

    @Test
    public void testToDTO_Success() {
        Tag tag = new Tag();
        tag.setTagID(1);
        tag.setText("test");

        TagDTO tagDTO = tagMapper.toDTO(tag);

        assertEquals(tag.getTagID(), tagDTO.getTagID());
        assertEquals(tag.getText(), tagDTO.getText());
    }
}
