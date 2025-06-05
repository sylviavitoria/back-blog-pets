package com.sylviavitoria.blogpets.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.sylviavitoria.blogpets.dto.PostRequest;
import com.sylviavitoria.blogpets.dto.PostResponse;
import com.sylviavitoria.blogpets.exception.EntityNotFoundException;
import com.sylviavitoria.blogpets.mapper.PostMapper;
import com.sylviavitoria.blogpets.model.Post;
import com.sylviavitoria.blogpets.repository.PostRepository;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private PostMapper postMapper;

    @InjectMocks
    private PostService postService;

    private Post post;
    private PostRequest postRequest;
    private PostResponse postResponse;

    @BeforeEach
    void setup() {

        postRequest = new PostRequest();
        postRequest.setTitulo("Como cuidar de gatos");
        postRequest.setDescricao("Dicas para cuidar adequadamente do seu felino");
        postRequest.setAutor("Maria Silva");

        post = new Post();
        post.setId(1L);
        post.setTitulo("Como cuidar de gatos");
        post.setDescricao("Dicas para cuidar adequadamente do seu felino");
        post.setAutor("Maria Silva");
        post.setDataCriacao(LocalDateTime.now());

        postResponse = PostResponse.builder()
                .titulo("Como cuidar de gatos")
                .descricao("Dicas para cuidar adequadamente do seu felino")
                .autor("Maria Silva")
                .build();
    }

    @Test
    @DisplayName("Deve criar um post com sucesso")
    void deveCriarPostComSucesso() {

        Post novoPost = Post.criarNovo(
                postRequest.getTitulo(),
                postRequest.getDescricao(),
                postRequest.getAutor());
        
        when(postRepository.save(argThat(p -> 
            p.getTitulo().equals(postRequest.getTitulo()) && 
            p.getDescricao().equals(postRequest.getDescricao()) &&
            p.getAutor().equals(postRequest.getAutor())))).thenReturn(post);
        
        when(postMapper.toResponse(post)).thenReturn(postResponse);

        PostResponse resultado = postService.criar(postRequest);

        assertNotNull(resultado);
        assertEquals("Como cuidar de gatos", resultado.getTitulo());
        assertEquals("Dicas para cuidar adequadamente do seu felino", resultado.getDescricao());
        assertEquals("Maria Silva", resultado.getAutor());
        
        verify(postRepository, times(1)).save(argThat(p -> 
            p.getTitulo().equals(postRequest.getTitulo()) && 
            p.getDescricao().equals(postRequest.getDescricao()) &&
            p.getAutor().equals(postRequest.getAutor())));
        verify(postMapper, times(1)).toResponse(post);
    }

    @Test
    @DisplayName("Deve buscar um post por ID com sucesso")
    void deveBuscarPostPorIdComSucesso() {

        Long postId = 1L;
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(postMapper.toResponse(post)).thenReturn(postResponse);

        PostResponse resultado = postService.bucarPorId(postId);

        assertNotNull(resultado);
        assertEquals("Como cuidar de gatos", resultado.getTitulo());
        assertEquals("Dicas para cuidar adequadamente do seu felino", resultado.getDescricao());
        assertEquals("Maria Silva", resultado.getAutor());
        
        verify(postRepository, times(1)).findById(postId);
        verify(postMapper, times(1)).toResponse(post);
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar post com ID inexistente")
    void deveLancarExcecaoAoBuscarPostInexistente() {

        Long postId = 99L;
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            postService.bucarPorId(postId);
        });

        assertEquals("Post não encontrado com ID: " + postId, exception.getMessage());
        verify(postRepository, times(1)).findById(postId);
        verifyNoInteractions(postMapper);
    }

    @Test
    @DisplayName("Deve listar todos os posts com paginação")
    void deveListarTodosPostsComPaginacao() {

        int page = 0;
        int size = 10;
        List<Post> posts = Arrays.asList(post);
        Page<Post> postPage = new PageImpl<>(posts);
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("titulo"));
        when(postRepository.findAll(pageable)).thenReturn(postPage);
        when(postMapper.toResponse(post)).thenReturn(postResponse);

        Page<PostResponse> resultado = postService.listarTodos(page, size, null);

        assertNotNull(resultado);
        assertEquals(1, resultado.getTotalElements());
        assertEquals(postResponse, resultado.getContent().get(0));
        
        verify(postRepository, times(1)).findAll(pageable);
        verify(postMapper, times(1)).toResponse(post);
    }

    @Test
    @DisplayName("Deve listar todos os posts com ordenação personalizada")
    void deveListarTodosPostsComOrdenacaoPersonalizada() {

        int page = 0;
        int size = 10;
        String sort = "autor";
        List<Post> posts = Arrays.asList(post);
        Page<Post> postPage = new PageImpl<>(posts);
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        when(postRepository.findAll(pageable)).thenReturn(postPage);
        when(postMapper.toResponse(post)).thenReturn(postResponse);

        Page<PostResponse> resultado = postService.listarTodos(page, size, sort);

        assertNotNull(resultado);
        assertEquals(1, resultado.getTotalElements());
        
        verify(postRepository, times(1)).findAll(pageable);
        verify(postMapper, times(1)).toResponse(post);
    }

    @Test
    @DisplayName("Deve atualizar um post com sucesso")
    void deveAtualizarPostComSucesso() {

        Long postId = 1L;
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        
        Post postAtualizado = new Post();
        postAtualizado.setId(postId);
        postAtualizado.setTitulo(postRequest.getTitulo());
        postAtualizado.setDescricao(postRequest.getDescricao());
        postAtualizado.setAutor(postRequest.getAutor());
        postAtualizado.setDataCriacao(post.getDataCriacao());
        
        when(postRepository.save(post)).thenReturn(postAtualizado);
        when(postMapper.toResponse(postAtualizado)).thenReturn(postResponse);

        PostResponse resultado = postService.atualizar(postId, postRequest);

        assertNotNull(resultado);
        assertEquals(postRequest.getTitulo(), resultado.getTitulo());
        assertEquals(postRequest.getDescricao(), resultado.getDescricao());
        assertEquals(postRequest.getAutor(), resultado.getAutor());
        
        verify(postRepository, times(1)).findById(postId);
        verify(postRepository, times(1)).save(post);
        verify(postMapper, times(1)).toResponse(postAtualizado);
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar post inexistente")
    void deveLancarExcecaoAoAtualizarPostInexistente() {

        Long postId = 99L;
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            postService.atualizar(postId, postRequest);
        });

        assertEquals("Post não encontrado com ID: " + postId, exception.getMessage());
        verify(postRepository, times(1)).findById(postId);
        verifyNoInteractions(postMapper);
        verify(postRepository, never()).save(any(Post.class));
    }

    @Test
    @DisplayName("Deve excluir um post com sucesso")
    void deveExcluirPostComSucesso() {

        Long postId = 1L;
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        doNothing().when(postRepository).delete(post);

        postService.excluir(postId);

        verify(postRepository, times(1)).findById(postId);
        verify(postRepository, times(1)).delete(post);
    }

    @Test
    @DisplayName("Deve lançar exceção ao excluir post inexistente")
    void deveLancarExcecaoAoExcluirPostInexistente() {

        Long postId = 99L;
        when(postRepository.findById(postId)).thenReturn(Optional.empty());


        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            postService.excluir(postId);
        });

        assertEquals("Post não encontrado com ID: " + postId, exception.getMessage());
        verify(postRepository, times(1)).findById(postId);
        verify(postRepository, never()).delete(any(Post.class));
    }
}