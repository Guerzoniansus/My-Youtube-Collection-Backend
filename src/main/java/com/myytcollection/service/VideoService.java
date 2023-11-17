package com.myytcollection.service;

import com.myytcollection.dto.VideoDTO;
import com.myytcollection.mapper.VideoMapper;
import com.myytcollection.model.User;
import com.myytcollection.model.Video;
import com.myytcollection.repository.UserRepository;
import com.myytcollection.repository.VideoRepository;
import org.springframework.stereotype.Service;

@Service
public class VideoService {

    private final VideoRepository videoRepository;
    private final VideoMapper videoMapper;

    public VideoService(VideoRepository videoRepository, VideoMapper videoMapper) {
        this.videoRepository = videoRepository;
        this.videoMapper = videoMapper;
    }

    public void createVideo(User user, VideoDTO videoDTO) {
        Video video = videoMapper.toModel(videoDTO, user);
        saveVideo(user, video);
    }

    public void saveVideo(User user, Video video) {
        videoRepository.save(video);
    }
}
