package com.myytcollection.controller;

import com.myytcollection.dto.SearchFilterDTO;
import com.myytcollection.dto.VideoDTO;
import com.myytcollection.dto.VideoResponseDTO;
import com.myytcollection.model.User;
import com.myytcollection.repository.UserRepository;
import com.myytcollection.service.UserService;
import com.myytcollection.service.VideoService;
import com.myytcollection.util.JwtUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class VideoControllerTest {

    @InjectMocks
    private VideoController videoController;
    @Mock
    private UserService userService;
    @Mock
    private VideoService videoService;

    private final User user = new User("test@example.com");

    @Test
    public void createVideo_shouldReturnOk() {
        String authorizationHeader = "Bearer jwt";
        VideoDTO videoDTO = new VideoDTO();  // Add relevant fields to simulate video data

        when(userService.getUser("jwt")).thenReturn(user);

        ResponseEntity<?> responseEntity = videoController.createVideo(authorizationHeader, videoDTO);

        assertEquals(200, responseEntity.getStatusCodeValue());
        verify(videoService, times(1)).createVideo(user, videoDTO);
    }

    @Test
    public void getVideos_shouldReturnOkWithResponse() {
        String authorizationHeader = "Bearer jwt";
        SearchFilterDTO searchFilter = new SearchFilterDTO();  // Add relevant fields to simulate search filter
        List<VideoDTO> videoDTOList = new ArrayList<>();  // Add relevant fields to simulate video data
        videoDTOList.add(new VideoDTO(1, "videoCode", "title", "channel", "altTitle", new HashSet<>()));
        Page<VideoDTO> videoPage = mock(Page.class);

        when(userService.getUser("jwt")).thenReturn(user);

        when(videoPage.getContent()).thenReturn(videoDTOList);
        when(videoPage.getTotalElements()).thenReturn((long) videoDTOList.size());
        when(videoService.getVideos(user, searchFilter)).thenReturn(videoPage);

        ResponseEntity<?> responseEntity = videoController.getVideos(authorizationHeader, searchFilter);

        assertEquals(200, responseEntity.getStatusCodeValue());
        VideoResponseDTO responseDTO = (VideoResponseDTO) responseEntity.getBody();
        assertEquals(videoDTOList, responseDTO.getVideos());
        assertEquals(videoPage.getTotalElements(), responseDTO.getTotalVideos());
    }

}
