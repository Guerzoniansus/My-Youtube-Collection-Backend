package com.myytcollection.service;

import com.myytcollection.dto.VideoDTO;
import com.myytcollection.mapper.TagMapper;
import com.myytcollection.mapper.VideoMapper;
import com.myytcollection.model.User;
import com.myytcollection.model.Video;
import com.myytcollection.repository.TagRepository;
import com.myytcollection.repository.VideoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class VideoServiceTest {

    @Mock
    private VideoRepository videoRepository;
    @Mock
    private TagRepository tagRepository;

    private VideoMapper mapper;

    private VideoService videoService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mapper = new VideoMapper(videoRepository, new TagMapper(tagRepository));
        videoService = new VideoService(videoRepository, mapper);
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
    public void testGetVideos_Success() {
        User user = new User("test@example.com");
        Video video = new Video(1, "videoCode", "title", "channel", "alternativeTitle", Calendar.getInstance().getTime(), user, new HashSet<>());

        Set<Video> videos = new HashSet<>();
        videos.add(video);

        when(videoRepository.findByUserOrderByDateCreatedDesc(user)).thenReturn(videos);

        Set<Video> retrievedVideos = videoService.getVideos(user);

        assertEquals(1, retrievedVideos.size());
        assertEquals(video.getVideoID(), retrievedVideos.iterator().next().getVideoID());
    }

    @Test
    public void testSaveVideo_Success() {
        Video video = new Video(1, "videoCode", "title", "channel", "alternativeTitle", Calendar.getInstance().getTime(), new User("test@example.com"), new HashSet<>());

        when(videoRepository.save(video)).thenReturn(video);

        videoService.saveVideo(video);

        // Verify that save was called with the correct argument
        verify(videoRepository).save(video);
    }

    @Test
    public void testGetAllVideosAsDTOs_Success() {
        User user = new User("test@example.com");
        Video video = new Video(1, "videoCode", "title", "channel", "alternativeTitle", Calendar.getInstance().getTime(), user, new HashSet<>());

        Set<Video> videos = new HashSet<>();
        videos.add(video);

        when(videoRepository.findByUserOrderByDateCreatedDesc(user)).thenReturn(videos);

        Set<VideoDTO> videoDTOs = videoService.getAllVideosAsDTOs(user);

        assertEquals(1, videoDTOs.size());
        assertEquals(video.getVideoID(), videoDTOs.iterator().next().getVideoID());
    }

    @Test
    public void testGetAllVideos_Success() {
        User user = new User("test@example.com");
        Video video = new Video(1, "videoCode", "title", "channel", "alternativeTitle", Calendar.getInstance().getTime(), user, new HashSet<>());

        Set<Video> videos = new HashSet<>();
        videos.add(video);

        when(videoRepository.findByUserOrderByDateCreatedDesc(user)).thenReturn(videos);

        Set<Video> retrievedVideos = videoService.getAllVideos(user);

        assertEquals(1, retrievedVideos.size());
        assertEquals(video.getVideoID(), retrievedVideos.iterator().next().getVideoID());
    }
}
