package com.sylviavitoria.blogpets.controller;

import com.sylviavitoria.blogpets.dto.PostRequest;
import com.sylviavitoria.blogpets.dto.PostResponse;
import com.sylviavitoria.blogpets.exception.EntityNotFoundException;
import com.sylviavitoria.blogpets.interfaces.IPost;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostControllerTest {

    @Mock
    private IPost postService;

    @InjectMocks
    private PostController postController;

    private PostRequest postRequest;
    private PostResponse postResponse;
    private Long postId;

    @BeforeEach
    void setUp() {
        postId = 1L;
        
        postRequest = new PostRequest();
        postRequest.setTitulo("Como cuidar de gatos");
        postRequest.setDescricao("Dicas para cuidar adequadamente do seu felino");
        postRequest.setAutor("Maria Silva");

        postResponse = PostResponse.builder()
                .titulo("Como cuidar de gatos")
                .descricao("Dicas para cuidar adequadamente do seu felino")
                .autor("Maria Silva")
                .build();
    }

    @Test
    @DisplayName("Deve criar um post com sucesso")
    void deveCriarPostComSucesso() {

        when(postService.criar(postRequest)).thenReturn(postResponse);

        ResponseEntity<PostResponse> response = postController.criar(postRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(postResponse, response.getBody());
        
        verify(postService, times(1)).criar(postRequest);
        verifyNoMoreInteractions(postService);
    }

    @Test
    @DisplayName("Deve buscar post por ID com sucesso")
    void deveBuscarPostPorIdComSucesso() {

        when(postService.bucarPorId(postId)).thenReturn(postResponse);

        ResponseEntity<PostResponse> response = postController.buscarPorId(postId);
 
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(postResponse, response.getBody());
    
        verify(postService, times(1)).bucarPorId(postId);
        verifyNoMoreInteractions(postService);
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar post com ID inexistente")
    void deveLancarExcecaoAoBuscarPostInexistente() {

        when(postService.bucarPorId(postId)).thenThrow(new EntityNotFoundException("Post não encontrado com ID: " + postId));

        assertThrows(EntityNotFoundException.class, () -> {
            postController.buscarPorId(postId);
        });
        
        verify(postService, times(1)).bucarPorId(postId);
        verifyNoMoreInteractions(postService);
    }

    @Test
    @DisplayName("Deve listar todos os posts com paginação padrão")
    void deveListarTodosPostsComPaginacaoPadrao() {

        int page = 0;
        int size = 10;
        String sort = null;
        
        List<PostResponse> posts = Arrays.asList(postResponse);
        Page<PostResponse> pageResponse = new PageImpl<>(posts);
        
        when(postService.listarTodos(page, size, sort)).thenReturn(pageResponse);

        ResponseEntity<Page<PostResponse>> response = postController.listarTodos(page, size, sort);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pageResponse, response.getBody());
        assertEquals(1, response.getBody().getTotalElements());
        
        verify(postService, times(1)).listarTodos(page, size, sort);
        verifyNoMoreInteractions(postService);
    }

    @Test
    @DisplayName("Deve listar todos os posts com ordenação personalizada")
    void deveListarTodosPostsComOrdenacaoPersonalizada() {

        int page = 0;
        int size = 10;
        String sort = "autor";
        
        List<PostResponse> posts = Arrays.asList(postResponse);
        Page<PostResponse> pageResponse = new PageImpl<>(posts);
        
        when(postService.listarTodos(page, size, sort)).thenReturn(pageResponse);

        ResponseEntity<Page<PostResponse>> response = postController.listarTodos(page, size, sort);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pageResponse, response.getBody());
        
        verify(postService, times(1)).listarTodos(page, size, sort);
        verifyNoMoreInteractions(postService);
    }

    @Test
    @DisplayName("Deve atualizar post com sucesso")
    void deveAtualizarPostComSucesso() {

        when(postService.atualizar(postId, postRequest)).thenReturn(postResponse);

        ResponseEntity<PostResponse> response = postController.atualizar(postId, postRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(postResponse, response.getBody());
        
        verify(postService, times(1)).atualizar(postId, postRequest);
        verifyNoMoreInteractions(postService);
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar post inexistente")
    void deveLancarExcecaoAoAtualizarPostInexistente() {

        when(postService.atualizar(postId, postRequest)).thenThrow(new EntityNotFoundException("Post não encontrado com ID: " + postId));

        assertThrows(EntityNotFoundException.class, () -> {
            postController.atualizar(postId, postRequest);
        });
        
        verify(postService, times(1)).atualizar(postId, postRequest);
        verifyNoMoreInteractions(postService);
    }

    @Test
    @DisplayName("Deve excluir post com sucesso")
    void deveExcluirPostComSucesso() {

        doNothing().when(postService).excluir(postId);

        ResponseEntity<Void> response = postController.excluir(postId);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        
        verify(postService, times(1)).excluir(postId);
        verifyNoMoreInteractions(postService);
    }

    @Test
    @DisplayName("Deve lançar exceção ao excluir post inexistente")
    void deveLancarExcecaoAoExcluirPostInexistente() {

        doThrow(new EntityNotFoundException("Post não encontrado com ID: " + postId))
            .when(postService).excluir(postId);

        assertThrows(EntityNotFoundException.class, () -> {
            postController.excluir(postId);
        });
        
        verify(postService, times(1)).excluir(postId);
        verifyNoMoreInteractions(postService);
    }
}