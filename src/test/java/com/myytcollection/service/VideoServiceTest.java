package com.myytcollection.service;

import com.myytcollection.dto.SearchFilterDTO;
import com.myytcollection.dto.VideoDTO;
import com.myytcollection.mapper.SearchFilterMapper;
import com.myytcollection.mapper.TagMapper;
import com.myytcollection.mapper.VideoMapper;
import com.myytcollection.model.SearchFilter;
import com.myytcollection.model.User;
import com.myytcollection.model.Video;
import com.myytcollection.repository.TagRepository;
import com.myytcollection.repository.VideoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.HashSet;
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
}
