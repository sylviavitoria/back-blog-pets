package com.sylviavitoria.blogpets.service;

import com.sylviavitoria.blogpets.dto.PostRequest;
import com.sylviavitoria.blogpets.dto.PostResponse;
import com.sylviavitoria.blogpets.exception.EntityNotFoundException;
import com.sylviavitoria.blogpets.interfaces.IPost;
import com.sylviavitoria.blogpets.mapper.PostMapper;
import com.sylviavitoria.blogpets.model.Post;
import com.sylviavitoria.blogpets.repository.PostRepository;

import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
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
        log.info("Iniciando criação de Post: {}, {}, {}", postRequest.getTitulo(), postRequest.getDescricao(),
                postRequest.getAutor());

        Post post = Post.criarNovo(
                postRequest.getTitulo(),
                postRequest.getDescricao(),
                postRequest.getAutor());

        Post savedPost = postRepository.save(post);

        return postMapper.toResponse(savedPost);
    }

    @Override
    public PostResponse bucarPorId(Long id) {
    log.info("Buscando Post por ID: {}", id);
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Post não encontrado com ID: " + id));

        return postMapper.toResponse(post);
    }

    @Override
    public Page<PostResponse> listarTodos(int page, int size, String sort) {
        log.info("Listando posts com paginação: página {}, tamanho {}, ordenação {}", page, size, sort);

        Pageable pageable;
        if (sort != null && !sort.isEmpty()) {
            pageable = PageRequest.of(page, size, Sort.by(sort));
        } else {
            pageable = PageRequest.of(page, size, Sort.by("titulo")); 
        }

        return postRepository.findAll(pageable)
                .map(postMapper::toResponse);
    }

    @Override
    @Transactional
    public PostResponse atualizar(Long id, PostRequest postRequest) {
        log.info("Atualido os dados de {}, {}, {}", postRequest.getTitulo(), postRequest.getDescricao(),
                postRequest.getAutor());
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Post não encontrado com ID: " + id));

        post.setTitulo(postRequest.getTitulo());
        post.setDescricao(postRequest.getDescricao());
        post.setAutor(postRequest.getAutor());

        Post updatedPost = postRepository.save(post);

        return postMapper.toResponse(updatedPost);

    }

    @Override
    public void excluir(Long id) {
        log.info("Excluindo post com ID: {}", id);
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Post não encontrado com ID: " + id));
        postRepository.delete(post);
    }

}