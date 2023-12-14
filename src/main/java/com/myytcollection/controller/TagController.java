package com.myytcollection.controller;

import com.myytcollection.dto.TagDTO;
import com.myytcollection.model.Tag;
import com.myytcollection.model.User;
import com.myytcollection.repository.UserRepository;
import com.myytcollection.service.TagService;
import com.myytcollection.service.UserService;
import com.myytcollection.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Set;

@RestController
public class TagController extends Controller {

    private final UserService userService;
    private final TagService tagService;

    public TagController(UserService userService, TagService tagService) {
        this.userService = userService;
        this.tagService = tagService;
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
            String email = getJWT(authorizationHeader);
            User user = userService.getUser(email);

            Set<Tag> createdTags = tagService.createTags(user, tags);

            return ResponseEntity.ok(createdTags);
        }

        catch (Exception e) {
            System.out.println("Error in TagController POST createTags()");
            System.out.println("Auth header: " + authorizationHeader);
            System.out.println("Tag DTOs: " + tags);
            System.out.println("Error:");
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
            return ResponseEntity.badRequest().body("Something went wrong");
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
            String email = getJWT(authorizationHeader);
            User user = userService.getUser(email);

            Set<TagDTO> tags = tagService.getAllTagsAsDTOs(user);

            return ResponseEntity.ok(tags);
        }

        catch (Exception e) {
            System.out.println("Error in TagController GET getTags()");
            System.out.println("Auth header: " + authorizationHeader);
            System.out.println("Error:");
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
            return ResponseEntity.badRequest().body("Something went wrong");
        }
    }

}
