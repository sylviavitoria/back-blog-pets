package com.sylviavitoria.blogpets.interfaces;

import org.springframework.data.domain.Page;

import com.sylviavitoria.blogpets.dto.PostRequest;
import com.sylviavitoria.blogpets.dto.PostResponse;

public interface IPost {
    PostResponse criar(PostRequest postRequest);
    PostResponse bucarPorId(Long id);
    Page<PostResponse> listarTodos(int page, int size, String sort);
    PostResponse atualizar(Long id, PostRequest postRequest);
    void excluir(Long id);
}
