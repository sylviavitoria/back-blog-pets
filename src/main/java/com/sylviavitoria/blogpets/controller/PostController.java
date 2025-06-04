package com.sylviavitoria.blogpets.controller;

import com.sylviavitoria.blogpets.dto.PostRequest;
import com.sylviavitoria.blogpets.dto.PostResponse;
import com.sylviavitoria.blogpets.interfaces.IPost;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

import java.util.Map;

import org.springframework.data.domain.Page;
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
    @Operation(summary = "Criar um novo Post", description = "Cria um novo Post com os dados fornecidos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Post criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos", content = @Content(schema = @Schema(implementation = Map.class))),
    })
    public ResponseEntity<PostResponse> criar(@Valid @RequestBody PostRequest postRequest) {
        PostResponse postResponse = postService.criar(postRequest);
        return new ResponseEntity<>(postResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar Post por ID", description = "Retorna um Post com base no ID fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Post não encontrado", content = @Content(schema = @Schema(implementation = Map.class)))
    })
    public ResponseEntity<PostResponse> buscarPorId(@PathVariable Long id) {
        PostResponse postResponse = postService.bucarPorId(id);
        return ResponseEntity.ok(postResponse);
    }

    @GetMapping
    @Operation(summary = "Listar todos os Posts", description = "Retorna uma lista paginada de todos os Posts cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Page<PostResponse>> listarTodos(
            @Parameter(description = "Número da página (começa em 0)", example = "0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamanho da página", example = "10") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Campo para ordenação (ex: titulo, autor, dataCriacao)", example = "titulo") @RequestParam(required = false) String sort) {

        return ResponseEntity.ok(postService.listarTodos(page, size, sort));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar Post", description = "Atualiza os dados de um Post existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos", content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "404", description = "Post não encontrado", content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "409", description = "Post já existe com este CPF", content = @Content(schema = @Schema(implementation = Map.class)))
    })
    public ResponseEntity<PostResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody PostRequest postRequest) {
        PostResponse postResponse = postService.atualizar(id, postRequest);
        return ResponseEntity.ok(postResponse);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir Post", description = "Remove um Post com base no ID fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Post removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Post não encontrado", content = @Content(schema = @Schema(implementation = Map.class)))
    })
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        postService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}