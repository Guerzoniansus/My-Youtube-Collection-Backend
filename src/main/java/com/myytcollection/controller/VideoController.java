package com.myytcollection.controller;

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

    public VideoController(JwtUtil jwtUtil, VideoService videoService) {
        this.jwtUtil = jwtUtil;
        this.videoService = videoService;
    }

    @RequestMapping(path = "/videos", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<?> saveVideo(@RequestHeader("Authorization") String authorizationHeader, @RequestBody Video video) {
        System.out.println("Incoming save video");

        try {
            final String email = getEmail(authorizationHeader, jwtUtil);

            System.out.println(email);
            System.out.println("Video from VideoController: " + video);

            videoService.saveVideo(email, video);

            return ResponseEntity.ok().build();
        }

        catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Something was wrong with the authorization");
        }
    }

}
