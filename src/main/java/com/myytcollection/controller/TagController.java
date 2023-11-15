package com.myytcollection.controller;

import com.myytcollection.model.Tag;
import com.myytcollection.model.Video;
import com.myytcollection.service.TagService;
import com.myytcollection.util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TagController extends Controller {

    private final JwtUtil jwtUtil;
    private final TagService tagService;

    public TagController(JwtUtil jwtUtil, TagService tagService) {
        this.jwtUtil = jwtUtil;
        this.tagService = tagService;
    }

    @RequestMapping(path = "/tags", method = RequestMethod.POST)
    public ResponseEntity<?> saveTags(@RequestHeader("Authorization") String authorizationHeader, @RequestBody List<Tag> tags) {
        try {
            final String email = getEmail(authorizationHeader, jwtUtil);

            System.out.println("Incoming save tags");
            tags.forEach(System.out::println);

            List<Tag> createdTags = new ArrayList<>();
            tags.forEach(tag -> createdTags.add(tagService.saveTag(email, tag)));

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
            System.out.println("Getting all tags!");
            List<Tag> tags = tagService.getAllTags(email);
            System.out.println("Returning " + tags);
            return ResponseEntity.ok(tags);
        }

        catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Something was wrong with the authorization");
        }


    }

}
