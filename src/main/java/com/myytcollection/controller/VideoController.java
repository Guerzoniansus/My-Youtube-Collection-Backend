package com.myytcollection.controller;

import com.myytcollection.dto.TagDTO;
import com.myytcollection.dto.VideoDTO;
import com.myytcollection.model.User;
import com.myytcollection.model.Video;
import com.myytcollection.repository.UserRepository;
import com.myytcollection.service.VideoService;
import com.myytcollection.util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
public class VideoController extends Controller {

    private final JwtUtil jwtUtil;
    private final VideoService videoService;
    private final UserRepository userRepository;

    public VideoController(JwtUtil jwtUtil, VideoService videoService, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.videoService = videoService;
        this.userRepository = userRepository;
    }

    /**
     * Handles the creation of a new video in the database.
     * @param authorizationHeader The authorization header in the HTTP request.
     * @param video The video to save in the database.
     * @return Returns either nothing, or an error if something went wrong.
     */
    @RequestMapping(path = "/videos", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<?> createVideo(@RequestHeader("Authorization") String authorizationHeader, @RequestBody VideoDTO video) {
        try {
            final String email = getEmail(authorizationHeader, jwtUtil);
            User user = userRepository.findById(email).get();

            videoService.createVideo(user, video);

            return ResponseEntity.ok().build();
        }

        catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Something was wrong with the authorization");
        }
    }

    /**
     * Gets all videos from the user.
     * @param authorizationHeader The authorization header in the HTTP request.
     * @return All videos from the user.
     */
    @RequestMapping(path = "/videos", method = RequestMethod.GET)
    public ResponseEntity<?> getVideos(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            final String email = getEmail(authorizationHeader, jwtUtil);
            User user = userRepository.findById(email).get();

            Set<VideoDTO> videos = videoService.getAllVideosAsDTOs(user);

            return ResponseEntity.ok(videos);
        }

        catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Something was wrong with the authorization");
        }
    }

}
