package com.myytcollection.mapper;

import com.myytcollection.dto.TagDTO;
import com.myytcollection.dto.VideoDTO;
import com.myytcollection.model.Tag;
import com.myytcollection.model.User;
import com.myytcollection.model.Video;
import com.myytcollection.repository.TagRepository;
import com.myytcollection.repository.VideoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class VideoMapperTest {

    @Mock
    private VideoRepository videoRepository;
    @Mock
    private TagRepository tagRepository;

    private TagMapper tagMapper;
    private VideoMapper videoMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        tagMapper = new TagMapper(tagRepository);
        videoMapper = new VideoMapper(videoRepository, tagMapper);
    }

    @Test
    public void testFromDatabase_Success() {
        VideoDTO videoDTO = new VideoDTO();
        videoDTO.setVideoID(1);

        Video video = new Video();
        video.setVideoID(1);

        when(videoRepository.getReferenceById(videoDTO.getVideoID())).thenReturn(video);

        Video result = videoMapper.fromDatabase(videoDTO);

        assertEquals(video, result);
    }

    @Test
    public void testToModel_Success() {
        User user = new User("test@example.com");

        Set<TagDTO> tagDTOs = new LinkedHashSet<>();
        tagDTOs.add(new TagDTO(1, "text"));
        tagDTOs.add(new TagDTO(2, "text2"));

        VideoDTO videoDTO = new VideoDTO(1, "videoCode", "title", "channel",
                "alternativeTitle", tagDTOs);

        Video video = videoMapper.toModel(videoDTO, user);

        assertEquals(user, video.getUser());
        assertEquals(videoDTO.getVideoID(), video.getVideoID());
        assertEquals(videoDTO.getVideoCode(), video.getVideoCode());
        assertEquals(videoDTO.getTitle(), video.getTitle());
        assertEquals(videoDTO.getChannel(), video.getChannel());
        assertEquals(videoDTO.getAlternativeTitle(), video.getAlternativeTitle());

        // Using List and sorting to make sure order is the same
        List<Tag> sortedTags = video.getTags().stream()
                .sorted(Comparator.comparing(Tag::getTagID))
                .toList();

        List<TagDTO> sortedTagDTOs = tagDTOs.stream()
                .sorted(Comparator.comparing(TagDTO::getTagID))
                .toList();

        // Converting to list because Set does not use indexes
        List<Tag> tags = tagDTOs.stream().map((tagDTO -> new Tag(tagDTO.getTagID(), tagDTO.getText(), user))).toList();

        for (int i = 0; i < sortedTags.size(); i++) {
            assertEquals(sortedTagDTOs.get(i).getTagID(), sortedTags.get(i).getTagID());
            assertEquals(sortedTagDTOs.get(i).getText(), sortedTags.get(i).getText());
        }
    }

    @Test
    public void testToDTO_Success() {
        User user = new User("test@example.com");

        Set<Tag> tags = new LinkedHashSet<>();
        tags.add(new Tag(1, "text", user));
        tags.add(new Tag(2, "text2", user));

        Video video = new Video(1, "videoCode", "title", "channel",
                "alternativeTitle", Calendar.getInstance().getTime(), user, tags);

        VideoDTO videoDTO = videoMapper.toDTO(video);

        assertEquals(video.getVideoID(), videoDTO.getVideoID());
        assertEquals(video.getVideoCode(), videoDTO.getVideoCode());
        assertEquals(video.getTitle(), videoDTO.getTitle());
        assertEquals(video.getChannel(), videoDTO.getChannel());
        assertEquals(video.getAlternativeTitle(), videoDTO.getAlternativeTitle());

        // Using List and sorting to make sure order is the same
        List<TagDTO> sortedTagDTOs = videoDTO.getTags().stream()
                .sorted(Comparator.comparing(TagDTO::getTagID))
                .toList();

        List<Tag> sortedTags = tags.stream()
                .sorted(Comparator.comparing(Tag::getTagID))
                .toList();

        // Compare each element in the sorted list
        for (int i = 0; i < sortedTags.size(); i++) {
            assertEquals(sortedTagDTOs.get(i).getTagID(), sortedTags.get(i).getTagID());
            assertEquals(sortedTagDTOs.get(i).getText(), sortedTags.get(i).getText());
        }
    }
}
