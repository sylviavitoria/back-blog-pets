package com.sylviavitoria.blogpets.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PostTest {

    @Test
    @DisplayName("Deve criar um post válido com sucesso")
    void deveCriarPostValido() {

        Post post = Post.criarNovo("Título válido", "Descrição válida para testar", "Autor");

        assertNotNull(post);
        assertEquals("Título válido", post.getTitulo());
        assertEquals("Descrição válida para testar", post.getDescricao());
        assertEquals("Autor", post.getAutor());
        assertNotNull(post.getDataCriacao());
    }

    @Test
    @DisplayName("Deve definir a data de criação ao criar um post")
    void deveDefinirDataCriacao() {

        LocalDateTime antes = LocalDateTime.now();
        
        Post post = Post.criarNovo("Título válido", "Descrição válida para testar", "Autor");
        
        assertNotNull(post.getDataCriacao());
        assertFalse(post.getDataCriacao().isBefore(antes));
    }

    @Test
    @DisplayName("Deve definir a data de criação no prePersist")
    void deveDefinirDataCriacaoNoPrePersist() {

        Post post = new Post();
        
        post.prePersist();
        
        assertNotNull(post.getDataCriacao());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   ", "\t", "\n"})
    @DisplayName("Deve lançar exceção quando título for vazio ou nulo")
    void deveLancarExcecaoQuandoTituloForVazioOuNulo(String titulo) {

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Post.criarNovo(titulo, "Descrição válida para testar", "Autor");
        });
        
        assertEquals("O título não pode ser vazio", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"a", "ab", "abc", "abcd"})
    @DisplayName("Deve lançar exceção quando título tiver menos de 5 caracteres")
    void deveLancarExcecaoQuandoTituloTiverMenosDeCincoCaracteres(String titulo) {

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Post.criarNovo(titulo, "Descrição válida para testar", "Autor");
        });
        
        assertEquals("O título deve ter pelo menos 5 caracteres", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando título tiver mais de 100 caracteres")
    void deveLancarExcecaoQuandoTituloTiverMaisDeCemCaracteres() {

        String tituloLongo = "a".repeat(101); 
        
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Post.criarNovo(tituloLongo, "Descrição válida para testar", "Autor");
        });
        
        assertEquals("O título não pode ter mais de 100 caracteres", exception.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   ", "\t", "\n"})
    @DisplayName("Deve lançar exceção quando descrição for vazia ou nula")
    void deveLancarExcecaoQuandoDescricaoForVaziaOuNula(String descricao) {

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Post.criarNovo("Título válido", descricao, "Autor");
        });
        
        assertEquals("A descrição não pode ser vazia", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"curta", "pequena", "123456789"})
    @DisplayName("Deve lançar exceção quando descrição tiver menos de 10 caracteres")
    void deveLancarExcecaoQuandoDescricaoTiverMenosDeDezCaracteres(String descricao) {

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Post.criarNovo("Título válido", descricao, "Autor");
        });
        
        assertEquals("A descrição deve ter pelo menos 10 caracteres", exception.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   ", "\t", "\n"})
    @DisplayName("Deve lançar exceção quando autor for vazio ou nulo")
    void deveLancarExcecaoQuandoAutorForVazioOuNulo(String autor) {

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Post.criarNovo("Título válido", "Descrição válida para testar", autor);
        });
        
        assertEquals("O autor não pode ser vazio", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"a", "ab"})
    @DisplayName("Deve lançar exceção quando autor tiver menos de 3 caracteres")
    void deveLancarExcecaoQuandoAutorTiverMenosDeTresCaracteres(String autor) {

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Post.criarNovo("Título válido", "Descrição válida para testar", autor);
        });
        
        assertEquals("O nome do autor deve ter pelo menos 3 caracteres", exception.getMessage());
    }

    @Test
    @DisplayName("Deve aceitar título com exatamente 5 caracteres")
    void deveAceitarTituloComCincoCaracteres() {

        Post post = Post.criarNovo("12345", "Descrição válida para testar", "Autor");
        
        assertEquals("12345", post.getTitulo());
    }

    @Test
    @DisplayName("Deve aceitar título com exatamente 100 caracteres")
    void deveAceitarTituloComCemCaracteres() {

        String tituloCem = "a".repeat(100);
        
        Post post = Post.criarNovo(tituloCem, "Descrição válida para testar", "Autor");
        
        assertEquals(tituloCem, post.getTitulo());
        assertEquals(100, post.getTitulo().length());
    }

    @Test
    @DisplayName("Deve aceitar descrição com exatamente 10 caracteres")
    void deveAceitarDescricaoComDezCaracteres() {

        Post post = Post.criarNovo("Título válido", "1234567890", "Autor");
        
        assertEquals("1234567890", post.getDescricao());
    }

    @Test
    @DisplayName("Deve aceitar autor com exatamente 3 caracteres")
    void deveAceitarAutorComTresCaracteres() {

        Post post = Post.criarNovo("Título válido", "Descrição válida para testar", "Ana");
        
        assertEquals("Ana", post.getAutor());
    }
}