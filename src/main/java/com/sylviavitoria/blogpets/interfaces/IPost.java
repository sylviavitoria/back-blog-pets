package com.sylviavitoria.blogpets.interfaces;

import com.sylviavitoria.blogpets.dto.PostRequest;
import com.sylviavitoria.blogpets.dto.PostResponse;

public interface IPost {
    PostResponse criar(PostRequest postRequest);
}
