package com.sylviavitoria.blogpets.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String titulo;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descricao;

    @Column(nullable = false)
    private String autor;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @PrePersist
    public void prePersist() {
        dataCriacao = LocalDateTime.now();
    }

    private Post(String titulo, String descricao, String autor) {
        validarTitulo(titulo);
        validarDescricao(descricao);
        validarAutor(autor);

        this.titulo = titulo;
        this.descricao = descricao;
        this.autor = autor;
        this.dataCriacao = LocalDateTime.now();
    }

    public static Post criarNovo(String titulo, String descricao, String autor) {
        return new Post(titulo, descricao, autor);
    }

    private void validarTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("O título não pode ser vazio");
        }
        if (titulo.trim().length() < 5) {
            throw new IllegalArgumentException("O título deve ter pelo menos 5 caracteres");
        }
        if (titulo.trim().length() > 100) {
            throw new IllegalArgumentException("O título não pode ter mais de 100 caracteres");
        }
    }

    private void validarDescricao(String descricao) {
        if (descricao == null || descricao.trim().isEmpty()) {
            throw new IllegalArgumentException("A descrição não pode ser vazia");
        }
        if (descricao.trim().length() < 10) {
            throw new IllegalArgumentException("A descrição deve ter pelo menos 10 caracteres");
        }
    }

    private void validarAutor(String autor) {
        if (autor == null || autor.trim().isEmpty()) {
            throw new IllegalArgumentException("O autor não pode ser vazio");
        }
        if (autor.trim().length() < 3) {
            throw new IllegalArgumentException("O nome do autor deve ter pelo menos 3 caracteres");
        }
    }
}