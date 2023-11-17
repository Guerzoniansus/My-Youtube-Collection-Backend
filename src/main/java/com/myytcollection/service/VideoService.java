package com.myytcollection.service;

import com.myytcollection.dto.VideoDTO;
import com.myytcollection.mapper.VideoMapper;
import com.myytcollection.model.User;
import com.myytcollection.model.Video;
import com.myytcollection.repository.VideoRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This class is used for managing videos.
 */
@Service
public class VideoService {

    private final VideoRepository videoRepository;
    private final VideoMapper mapper;

    public VideoService(VideoRepository videoRepository, VideoMapper videoMapper) {
        this.videoRepository = videoRepository;
        this.mapper = videoMapper;
    }

    /**
     * Creates a new video and saves it to database. The date created will be set to the current time.
     * @param user The user this video belongs to.
     * @param videoDTO The video to save.
     */
    public void createVideo(User user, VideoDTO videoDTO) {
        Video video = mapper.toModel(videoDTO, user);
        saveVideo(video);
    }

    public Set<Video> getVideos(User user) {
        return videoRepository.findByUserOrderByDateCreatedDesc(user);
    }

    /**
     * Save a video to the database.
     * @param video The video to save.
     */
    public void saveVideo(Video video) {
        videoRepository.save(video);
    }

    /**
     * Returns all videos of a user, or an empty list if the user has no videos.
     * @param user The user whose videos to get.
     * @return All videos of a user, or an empty list if the user has no videos, converted to VideoDTOs.
     */
    public Set<VideoDTO> getAllVideosAsDTOs(User user) {
        // Using LinkedHashSet to keep the order sorted by date
        return getAllVideos(user).stream().map(mapper::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    /**
     * Returns all videos of a user, or an empty list if the user has no videos.
     * @param user The user whose videos to get.
     * @return All videos of a user, or an empty list if the user has no videos.
     */
    public Set<Video> getAllVideos(User user) {
        return videoRepository.findByUserOrderByDateCreatedDesc(user);
    }
}
