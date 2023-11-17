package com.myytcollection.controller;

import com.myytcollection.dto.VideoDTO;
import com.myytcollection.model.User;
import com.myytcollection.model.Video;
import com.myytcollection.repository.UserRepository;
import com.myytcollection.service.VideoService;
import com.myytcollection.util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

}
