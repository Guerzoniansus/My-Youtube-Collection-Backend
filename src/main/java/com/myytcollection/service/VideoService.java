package com.myytcollection.service;

import com.myytcollection.model.User;
import com.myytcollection.model.Video;
import com.myytcollection.repository.UserRepository;
import com.myytcollection.repository.VideoRepository;
import org.springframework.stereotype.Service;

@Service
public class VideoService {

    private final UserRepository userRepository;
    private final VideoRepository videoRepository;

    public VideoService(UserRepository userRepository, VideoRepository videoRepository) {
        this.userRepository = userRepository;
        this.videoRepository = videoRepository;
    }

    public void saveVideo(String email, Video video) {
        User user = userRepository.findById(email).orElseThrow(() -> new RuntimeException("User not found for email: " + email));
        // That runtime exception does not get thrown

        video.setUser(user);
        System.out.println("User: " + video.getUser());
        System.out.println("Video from VideoService: " + video);

        videoRepository.save(video);
    }
}
