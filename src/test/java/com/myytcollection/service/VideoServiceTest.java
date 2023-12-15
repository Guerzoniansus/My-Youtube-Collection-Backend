package com.myytcollection.service;

import com.myytcollection.dto.SearchFilterDTO;
import com.myytcollection.dto.TagDTO;
import com.myytcollection.dto.VideoDTO;
import com.myytcollection.mapper.SearchFilterMapper;
import com.myytcollection.mapper.TagMapper;
import com.myytcollection.mapper.VideoMapper;
import com.myytcollection.model.SearchFilter;
import com.myytcollection.model.Tag;
import com.myytcollection.model.User;
import com.myytcollection.model.Video;
import com.myytcollection.repository.TagRepository;
import com.myytcollection.repository.VideoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class VideoServiceTest {

    @Mock
    private VideoRepository videoRepository;
    @Mock
    private TagRepository tagRepository;
    private VideoMapper videoMapper;
    private SearchFilterMapper searchFilterMapper;
    private VideoService videoService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        TagMapper tagMapper = new TagMapper(tagRepository);
        videoMapper = new VideoMapper(videoRepository, new TagMapper(tagRepository));
        searchFilterMapper = new SearchFilterMapper(tagMapper);
        videoService = new VideoService(videoRepository, videoMapper, searchFilterMapper);
    }

    @Test
    public void testCreateVideo_Success() {
        User user = new User("test@example.com");
        VideoDTO videoDTO = new VideoDTO(1, "videoCode", "title", "channel", "alternativeTitle", new HashSet<>());

        videoService.createVideo(user, videoDTO);

        // Verify that saveVideo was called with the correct argument
        verify(videoRepository).save(any(Video.class));
    }

    @Test
    public void testSaveVideo_Success() {
        User user = new User("test@example.com");
        Video video = new Video(1, "videoCode", "title", "channel", "alternativeTitle", null, user, new HashSet<>());

        when(videoRepository.save(video)).thenReturn(video);

        videoService.saveVideo(video);

        // Verify that save was called with the correct argument
        verify(videoRepository).save(video);
    }

    @Test
    public void testGetVideos_Success() {
        User user = new User("test@example.com");
        Video video = new Video(1, "videoCode", "title", "channel", "alternativeTitle", null, user, new HashSet<>());

        Set<Video> videos = new HashSet<>();
        videos.add(video);

        SearchFilterDTO searchFilterDTO = new SearchFilterDTO("query", null, 0, 10);
        SearchFilter searchFilter = searchFilterMapper.toModel(searchFilterDTO);
        Pageable pageable = PageRequest.of(searchFilter.getPage(), searchFilter.getPageSize());

        when(videoRepository.getVideos(user, searchFilter.getQuery(), null, pageable)).thenReturn(new PageImpl<>(videos.stream().toList()));

        Page<VideoDTO> videoDTOs = videoService.getVideos(user, searchFilterDTO);

        assertEquals(1, videoDTOs.getContent().size());
        assertEquals(video.getVideoID(), videoDTOs.getContent().iterator().next().getVideoID());
    }

    @Test
    public void testGetVideos_WithNullSearchFilterDTO() {
        User user = new User("test@example.com");
        assertThrows(IllegalArgumentException.class, () -> {
            videoService.getVideos(user, null);
        });
    }

    @Test
    public void testDeleteVideo_Success() throws IllegalAccessException {
        User user = new User("test@example.com");
        Video video = new Video(1, "videoCode", "title", "channel", "alternativeTitle", null, user, new HashSet<>());

        when(videoRepository.findById(1)).thenReturn(Optional.of(video));

        videoService.deleteVideo(user, 1);

        // Verify that delete was called with the correct argument
        verify(videoRepository).delete(video);
    }

    @Test
    public void testDeleteVideo_VideoDoesNotExist_ThrowsException() {
        User user = new User("test@example.com");

        when(videoRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            videoService.deleteVideo(user, 1);
        });
    }

    @Test
    public void testDeleteVideo_VideoDoesNotBelongToUser_ThrowsException() {
        User user = new User("test@example.com");
        User otherUser = new User("other@example.com");
        Video video = new Video(1, "videoCode", "title", "channel", "alternativeTitle", null, otherUser, new HashSet<>());

        when(videoRepository.findById(1)).thenReturn(Optional.of(video));

        assertThrows(IllegalAccessException.class, () -> {
            videoService.deleteVideo(user, 1);
        });
    }

    @Test
    public void testUpdateVideo_Success() throws IllegalAccessException {
        // Arrange
        User user = new User("test@example.com");

        VideoDTO updatedVideoDTO = new VideoDTO(1, "videoCode", "title", "channel", "newAlternativeTitle", new HashSet<>());
        updatedVideoDTO.getTags().add(new TagDTO(1, "text"));

        Video oldVideo = new Video(1, "videoCode", "title", "channel", "alternativeTitle", null, user, new HashSet<>());

        Video updatedVideo = new Video(1, "videoCode", "title", "channel", "newAlternativeTitle", null, user, new HashSet<>());
        updatedVideo.getTags().add(new Tag(1, "text", user));

        when(videoRepository.findById(1)).thenReturn(Optional.of(oldVideo));

        // Act
        videoService.updateVideo(user, 1, updatedVideoDTO);

        // Assert that save was called with the correct argument
        ArgumentCaptor<Video> videoCaptor = ArgumentCaptor.forClass(Video.class);
        verify(videoRepository).save(videoCaptor.capture());

        Video savedVideo = videoCaptor.getValue();
        assertEquals(updatedVideo.getAlternativeTitle(), savedVideo.getAlternativeTitle());

        // Check if tags are correct
        assertEquals(updatedVideo.getTags().iterator().next().getTagID(),
                savedVideo.getTags().iterator().next().getTagID());

        assertEquals(updatedVideo.getTags().iterator().next().getText(),
                savedVideo.getTags().iterator().next().getText());

        assertEquals(updatedVideo.getTags().iterator().next().getUser().getEmail(),
                savedVideo.getTags().iterator().next().getUser().getEmail());
    }

    @Test
    public void testUpdateVideo_VideoDoesNotExist_ThrowsException() {
        User user = new User("test@example.com");
        VideoDTO updatedVideoDTO = new VideoDTO(1, "videoCode", "newTitle", "channel", "newAlternativeTitle", new HashSet<>());

        when(videoRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            videoService.updateVideo(user, 1, updatedVideoDTO);
        });
    }

    @Test
    public void testUpdateVideo_VideoDoesNotBelongToUser_ThrowsException() {
        User user = new User("test@example.com");
        User otherUser = new User("other@example.com");
        VideoDTO updatedVideoDTO = new VideoDTO(1, "videoCode", "newTitle", "channel", "newAlternativeTitle", new HashSet<>());
        Video oldVideo = new Video(1, "videoCode", "title", "channel", "alternativeTitle", null, otherUser, new HashSet<>());

        when(videoRepository.findById(1)).thenReturn(Optional.of(oldVideo));

        assertThrows(IllegalAccessException.class, () -> {
            videoService.updateVideo(user, 1, updatedVideoDTO);
        });
    }


}
