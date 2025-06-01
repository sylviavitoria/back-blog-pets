package com.sylviavitoria.blogpets.service;

import com.sylviavitoria.blogpets.dto.PostRequest;
import com.sylviavitoria.blogpets.dto.PostResponse;
import com.sylviavitoria.blogpets.interfaces.IPost;
import com.sylviavitoria.blogpets.mapper.PostMapper;
import com.sylviavitoria.blogpets.model.Post;
import com.sylviavitoria.blogpets.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService implements IPost {
    
    private final PostRepository postRepository;
    private final PostMapper postMapper;
    
    public PostService(PostRepository postRepository, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
    }
    
    @Override
    @Transactional
    public PostResponse criar(PostRequest postRequest) {
        Post post = postMapper.toEntity(postRequest);
        
        Post savedPost = postRepository.save(post);
        
        return postMapper.toResponse(savedPost);
    }
}