package com.myytcollection.mapper;

import com.myytcollection.dto.TagDTO;
import com.myytcollection.dto.VideoDTO;
import com.myytcollection.model.Tag;
import com.myytcollection.model.User;
import com.myytcollection.model.Video;
import com.myytcollection.repository.VideoRepository;
import org.springframework.stereotype.Component;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class VideoMapper {
    private final VideoRepository videoRepository;
    private final TagMapper tagMapper;

    public VideoMapper(VideoRepository videoRepository, TagMapper tagMapper) {
        this.videoRepository = videoRepository;
        this.tagMapper = tagMapper;
    }

    /**
     * Retrieves a video from the database.
     * @param dto The video whose ID will be used for database retrieval.
     * @return The found video,
     */
    public Video fromDatabase(VideoDTO dto) {
        return videoRepository.getReferenceById(dto.getVideoID());
    }

    /**
     * Creates a new Video model. Sets the dateCreated to the current time.
     * @param dto The DTO object to convert.
     * @param user The user this video belongs to.
     * @return A new Video model.
     */
    public Video toModel(VideoDTO dto, User user) {
        Set<Tag> tags = dto.getTags().stream().map(tagDTO -> tagMapper.toModel(tagDTO, user)).collect(Collectors.toSet());

        return new Video(dto.getVideoID(), dto.getVideoCode(), dto.getTitle(),
                dto.getChannel(), dto.getAlternativeTitle(), Calendar.getInstance().getTime(), user, tags);
    }

    /**
     * Converts a video model to a video DTO. Includes converting all tags.
     * @param video The video to convert.
     * @return The converted video DTO.
     */
    public VideoDTO toDTO(Video video) {
        Set<TagDTO> tags = video.getTags() == null
                ? new HashSet<>()
                : video.getTags().stream().map(tagMapper::toDTO).collect(Collectors.toSet());

        return new VideoDTO(video.getVideoID(), video.getVideoCode(),
                video.getTitle(), video.getChannel(), video.getAlternativeTitle(), tags);
    }
}
