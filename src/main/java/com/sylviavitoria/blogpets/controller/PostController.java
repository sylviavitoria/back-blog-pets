package com.sylviavitoria.blogpets.controller;

import com.sylviavitoria.blogpets.dto.PostRequest;
import com.sylviavitoria.blogpets.dto.PostResponse;
import com.sylviavitoria.blogpets.interfaces.IPost;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts")
@CrossOrigin(origins = "*") 
public class PostController {
    
    private final IPost postService;
    
    public PostController(IPost postService) {
        this.postService = postService;
    }
    
    @PostMapping
    public ResponseEntity<PostResponse> criar(@Valid @RequestBody PostRequest postRequest) {
        PostResponse postResponse = postService.criar(postRequest);
        return new ResponseEntity<>(postResponse, HttpStatus.CREATED);
    }
}