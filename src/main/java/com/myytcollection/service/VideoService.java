package com.myytcollection.service;

import com.myytcollection.dto.SearchFilterDTO;
import com.myytcollection.dto.VideoDTO;
import com.myytcollection.mapper.SearchFilterMapper;
import com.myytcollection.mapper.VideoMapper;
import com.myytcollection.model.SearchFilter;
import com.myytcollection.model.User;
import com.myytcollection.model.Video;
import com.myytcollection.model.Tag;
import com.myytcollection.repository.VideoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This class is used for managing videos.
 */
@Service
public class VideoService {

    private final VideoRepository videoRepository;
    private final VideoMapper videoMapper;
    private final SearchFilterMapper searchFilterMapper;

    public VideoService(VideoRepository videoRepository, VideoMapper videoMapper, SearchFilterMapper searchFilterMapper) {
        this.videoRepository = videoRepository;
        this.videoMapper = videoMapper;
        this.searchFilterMapper = searchFilterMapper;
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

    /**
     * Gets videos from the user.
     * @param user The user to get videos from.
     * @param searchFilterDTO An object containing the search parameters.
     * @return A Page object that contains the videos (use page.getContent() and the total amount of videos.
     */
    public Page<VideoDTO> getVideos(User user, SearchFilterDTO searchFilterDTO) {
        if (searchFilterDTO == null) {
            throw new IllegalArgumentException("The search filter cannot be null");
        }

        SearchFilter searchFilter = searchFilterMapper.toModel(searchFilterDTO);
        Page<Video> videos = getVideos(user, searchFilter);

        if (videos == null) {
            return new PageImpl<>(new ArrayList<>());
        }

        else {
            Page<VideoDTO> videoDTOs = videos.map(videoMapper::toDTO);
            return videoDTOs;
        }
    }

    /**
     * Gets videos from the user.
     * @param user The user to get videos from.
     * @param searchFilter An object containing the search parameters.
     * @return A Page object that contains the videos (use page.getContent() and the total amount of videos.
     */
    private Page<Video> getVideos(User user, SearchFilter searchFilter) {
        Set<Integer> tagIds = null; // Database query cannot use raw tags

        if(searchFilter.getTags() != null) {
            if (searchFilter.getTags().isEmpty() == false) {
                tagIds = searchFilter.getTags().stream().map(Tag::getTagID).collect(Collectors.toSet());
            }
        }

        Pageable pageable = PageRequest.of(searchFilter.getPageNumber(), searchFilter.getPageSize());
        Page<Video> videos = videoRepository.getVideos(user, searchFilter.getQuery(), tagIds, pageable);
        return videos;
    }
}
