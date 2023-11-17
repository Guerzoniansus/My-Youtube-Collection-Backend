package com.myytcollection.service;

import com.myytcollection.dto.VideoDTO;
import com.myytcollection.mapper.VideoMapper;
import com.myytcollection.model.User;
import com.myytcollection.model.Video;
import com.myytcollection.repository.UserRepository;
import com.myytcollection.repository.VideoRepository;
import org.springframework.stereotype.Service;

/**
 * This class is used for managing videos.
 */
@Service
public class VideoService {

    private final VideoRepository videoRepository;
    private final VideoMapper videoMapper;

    public VideoService(VideoRepository videoRepository, VideoMapper videoMapper) {
        this.videoRepository = videoRepository;
        this.videoMapper = videoMapper;
    }

    /**
     * Creates a new video and saves it to database. The date created will be set to the current time.
     * @param user The user this video belongs to.
     * @param videoDTO The video to save.
     */
    public void createVideo(User user, VideoDTO videoDTO) {
        Video video = videoMapper.toModel(videoDTO, user);
        saveVideo(video);
    }

    /**
     * Save a video to the database.
     * @param video The video to save.
     */
    public void saveVideo(Video video) {
        videoRepository.save(video);
    }
}
