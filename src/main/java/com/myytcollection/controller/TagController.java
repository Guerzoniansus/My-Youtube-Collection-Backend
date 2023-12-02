package com.myytcollection.controller;

import com.myytcollection.dto.TagDTO;
import com.myytcollection.model.Tag;
import com.myytcollection.model.User;
import com.myytcollection.repository.UserRepository;
import com.myytcollection.service.TagService;
import com.myytcollection.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    /**
     * Handles the creation of new tags in the database.
     * @param authorizationHeader The authorization header in the HTTP request.
     * @param tags The tags to save in the database.
     * @return The newly created tags, or an error if something went wrong.
     */
    @RequestMapping(path = "/tags", method = RequestMethod.POST)
    public ResponseEntity<?> createTags(@RequestHeader("Authorization") String authorizationHeader, @RequestBody Set<TagDTO> tags) {
        try {
            String email = getEmail(authorizationHeader, jwtUtil);
            User user = userRepository.findById(email).get();

            System.out.println("dtos: ");
            System.out.println(tags);

            Set<Tag> createdTags = tagService.createTags(user, tags);
            System.out.println("created tags: ");
            System.out.println(createdTags);

            return ResponseEntity.ok(createdTags);
        }

        catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Something was wrong with the authorization");
        }
    }

    /**
     * Gets all tags from the user.
     * @param authorizationHeader The authorization header in the HTTP request.
     * @return All tags from the user.
     */
    @RequestMapping(path = "/tags", method = RequestMethod.GET)
    public ResponseEntity<?> getTags(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            final String email = getEmail(authorizationHeader, jwtUtil);
            User user = userRepository.findById(email).get();

            Set<TagDTO> tags = tagService.getAllTagsAsDTOs(user);

            return ResponseEntity.ok(tags);
        }

        catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Something was wrong with the authorization");
        }
    }

}
