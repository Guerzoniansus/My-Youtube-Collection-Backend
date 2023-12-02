package com.myytcollection.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myytcollection.dto.SearchFilterDTO;
import com.myytcollection.dto.VideoDTO;
import com.myytcollection.dto.VideoResponseDTO;
import com.myytcollection.model.User;
import com.myytcollection.model.Video;
import com.myytcollection.repository.UserRepository;
import com.myytcollection.repository.VideoRepository;
import com.myytcollection.service.VideoService;
import com.myytcollection.util.JwtUtil;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VideoControllerTest {

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private VideoService videoService;

    @InjectMocks
    private VideoController videoController;

    @Mock
    private UserRepository userRepository;

    private final User user = new User("test@example.com");

    public VideoControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createVideo_shouldReturnOk() {
        String authorizationHeader = "Bearer mockToken";
        VideoDTO videoDTO = new VideoDTO();  // Add relevant fields to simulate video data

        when(jwtUtil.extractEmailFromJwt(anyString())).thenReturn(user.getEmail());
        when(userRepository.findById(anyString())).thenReturn(java.util.Optional.of(user));

        ResponseEntity<?> responseEntity = videoController.createVideo(authorizationHeader, videoDTO);

        assertEquals(200, responseEntity.getStatusCodeValue());
        verify(videoService, times(1)).createVideo(user, videoDTO);
    }

    @Test
    public void getVideos_shouldReturnOkWithResponse() {
        String authorizationHeader = "Bearer mockToken";
        SearchFilterDTO searchFilter = new SearchFilterDTO();  // Add relevant fields to simulate search filter
        List<VideoDTO> videoDTOList = new ArrayList<>();  // Add relevant fields to simulate video data
        videoDTOList.add(new VideoDTO(1, "videoCode", "title", "channel", "altTitle", new HashSet<>()));
        Page<VideoDTO> videoPage = mock(Page.class);

        when(jwtUtil.extractEmailFromJwt(anyString())).thenReturn(user.getEmail());
        when(userRepository.findById(user.getEmail())).thenReturn(java.util.Optional.of(user));
        when(videoPage.getContent()).thenReturn(videoDTOList);
        when(videoPage.getNumberOfElements()).thenReturn(videoDTOList.size());
        when(videoService.getVideos(user, searchFilter)).thenReturn(videoPage);

        ResponseEntity<?> responseEntity = videoController.getVideos(authorizationHeader, searchFilter);

        assertEquals(200, responseEntity.getStatusCodeValue());
        VideoResponseDTO responseDTO = (VideoResponseDTO) responseEntity.getBody();
        assertEquals(videoDTOList, responseDTO.getVideos());
        assertEquals(videoDTOList.size(), responseDTO.getTotalVideos());
    }

}
