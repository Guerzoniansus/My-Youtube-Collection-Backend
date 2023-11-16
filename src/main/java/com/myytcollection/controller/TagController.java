package com.myytcollection.controller;

import com.myytcollection.dto.TagDTO;
import com.myytcollection.model.Tag;
import com.myytcollection.model.User;
import com.myytcollection.model.Video;
import com.myytcollection.repository.UserRepository;
import com.myytcollection.service.TagService;
import com.myytcollection.util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class TagController extends Controller {

    private final JwtUtil jwtUtil;
    private final TagService tagService;

    private final UserRepository userRepository;

    public TagController(JwtUtil jwtUtil, TagService tagService, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.tagService = tagService;
        this.userRepository = userRepository;
    }

    @RequestMapping(path = "/tags", method = RequestMethod.POST)
    public ResponseEntity<?> saveTags(@RequestHeader("Authorization") String authorizationHeader, @RequestBody Set<TagDTO> tags) {
        try {
            String email = getEmail(authorizationHeader, jwtUtil);
            User user = userRepository.findById(email).get();

            System.out.println("Incoming save tags");
            tags.forEach(System.out::println);

            Set<Tag> createdTags = tagService.createTags(user, tags);

            return ResponseEntity.ok(createdTags);
        }

        catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Something was wrong with the authorization");
        }
    }

    @RequestMapping(path = "/tags", method = RequestMethod.GET)
    public ResponseEntity<?> getTags(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            final String email = getEmail(authorizationHeader, jwtUtil);
            User user = userRepository.findById(email).get();

            System.out.println("Getting all tags!");
            Set<TagDTO> tags = tagService.getAllTagsAsDTOs(user);

            System.out.println("Returning " + tags);
            return ResponseEntity.ok(tags);
        }

        catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Something was wrong with the authorization");
        }
    }

}
