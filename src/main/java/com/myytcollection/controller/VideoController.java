package com.myytcollection.controller;

import com.myytcollection.dto.SearchFilterDTO;
import com.myytcollection.dto.VideoDTO;
import com.myytcollection.dto.VideoResponseDTO;
import com.myytcollection.model.User;
import com.myytcollection.service.UserService;
import com.myytcollection.service.VideoService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
public class VideoController extends Controller {

    private final UserService userService;
    private final VideoService videoService;

    public VideoController(UserService userService, VideoService videoService) {
        this.userService = userService;
        this.videoService = videoService;
    }

    /**
     * Handles the creation of a new video in the database.
     * @param authorizationHeader The authorization header in the HTTP request.
     * @param video The video to save in the database.
     * @return Returns either nothing, or an error if something went wrong.
     */
    @RequestMapping(path = "/videos/create", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<?> createVideo(@RequestHeader("Authorization") String authorizationHeader, @RequestBody VideoDTO video) {
        try {
            String jwt = getJWT(authorizationHeader);
            User user = userService.getUser(jwt);

            videoService.createVideo(user, video);

            return ResponseEntity.ok().build();
        }

        catch (Exception e) {
            System.out.println("Error in POST getVideos()");
            System.out.println("Auth header: " + authorizationHeader);
            System.out.println("VideoDTO: " + video);
            System.out.println("Error:");
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
            return ResponseEntity.badRequest().body("Something went wrong");
        }
    }

    /**
     * Gets videos from the user.
     * @param authorizationHeader The authorization header in the HTTP request.
     * @param searchFilter The filter used for searching.
     * @return Videos from the user, or an error if something went wrong.
     */
    @RequestMapping(path = "/videos", method = RequestMethod.POST)
    public ResponseEntity<?> getVideos(@RequestHeader("Authorization") String authorizationHeader, @RequestBody SearchFilterDTO searchFilter) {
        try {
            String jwt = getJWT(authorizationHeader);
            User user = userService.getUser(jwt);

            Page<VideoDTO> page = videoService.getVideos(user, searchFilter);
            List<VideoDTO> videos = page.getContent();
            int totalVideos = (int) page.getTotalElements();

            VideoResponseDTO response = new VideoResponseDTO(videos, totalVideos);

            return ResponseEntity.ok(response);
        }

        catch (Exception e) {
            System.out.println("Error in VideoController POST getVideos().");
            System.out.println("Auth header: " + authorizationHeader);
            System.out.println("Search filter DTO: " + searchFilter);
            System.out.println("Error:");
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
            return ResponseEntity.badRequest().body("Something went wrong");
        }
    }

}
