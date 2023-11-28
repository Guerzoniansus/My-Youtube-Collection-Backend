package com.myytcollection.service;

import com.myytcollection.dto.TagDTO;
import com.myytcollection.mapper.TagMapper;
import com.myytcollection.model.Tag;
import com.myytcollection.model.User;
import com.myytcollection.repository.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class TagServiceTest {

    @Mock
    private TagRepository tagRepository;

    private TagMapper mapper;

    private TagService tagService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mapper = new TagMapper(tagRepository);
        tagService = new TagService(tagRepository, mapper);
    }

    @Test
    public void testSaveTag_Success() {
        Tag tag = new Tag();
        tag.setTagID(1);

        when(tagRepository.save(tag)).thenReturn(tag);

        Tag result = tagService.saveTag(tag);

        assertEquals(tag, result);
    }

    @Test
    public void testCreateTags_Success() {
        User user = new User("test@example.com");
        TagDTO tagDTO = new TagDTO(1, "test");
        Set<TagDTO> tagDTOs = new HashSet<>();
        tagDTOs.add(tagDTO);

        Tag expectedTag = new Tag(1, "test", user);

        when(tagRepository.save(any())).thenReturn(expectedTag);

        Set<Tag> createdTags = tagService.createTags(user, tagDTOs);

        assertEquals(1, createdTags.size());
        assertEquals(tagDTO.getTagID(), createdTags.iterator().next().getTagID());
    }

    @Test
    public void testGetAllTagsAsDTOs_Success() {
        User user = new User("test@example.com");
        Tag tag = new Tag(1, "test", user);

        Set<Tag> tags = new HashSet<>();
        tags.add(tag);

        when(tagRepository.findByUserEmail(user.getEmail())).thenReturn(tags);

        Set<TagDTO> tagDTOs = tagService.getAllTagsAsDTOs(user);

        assertEquals(1, tagDTOs.size());
        assertEquals(tag.getTagID(), tagDTOs.iterator().next().getTagID());
        assertEquals(tag.getText(), tagDTOs.iterator().next().getText());
    }

    @Test
    public void testGetAllTags_Success() {
        User user = new User("test@example.com");
        Tag tag = new Tag(1, "test", user);

        Set<Tag> tags = new HashSet<>();
        tags.add(tag);

        when(tagRepository.findByUserEmail(user.getEmail())).thenReturn(tags);

        Set<Tag> retrievedTags = tagService.getAllTags(user);

        assertEquals(1, retrievedTags.size());
        assertEquals(tag.getTagID(), retrievedTags.iterator().next().getTagID());
        assertEquals(tag.getText(), retrievedTags.iterator().next().getText());
        assertEquals(tag.getUser(), retrievedTags.iterator().next().getUser());
    }
}