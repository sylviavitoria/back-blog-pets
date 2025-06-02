package com.sylviavitoria.blogpets.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostRequest {

    @Schema(description = "Titulo de um Post", example = "Como preparar sua casa para um novo pet")
    @NotBlank(message = "O título é obrigatório")
    @Size(min = 5, max = 100, message = "O título deve ter entre 5 e 100 caracteres")
    private String titulo;

    @Schema(description = "Descrição de um Post", example = "Vai receber um novo amigo peludo? Veja dicas fáceis para deixar sua casa segura e acolhedora para o seu pet desde o primeiro dia!")
    @NotBlank(message = "A descrição é obrigatória")
    @Size(min = 10, message = "A descrição deve ter pelo menos 10 caracteres")
    private String descricao;

    @Schema(description = "Autor de um Post", example = "Julia Silva")
    @NotBlank(message = "O autor é obrigatório")
    @Size(min = 3, message = "O nome do autor deve ter pelo menos 3 caracteres")
    private String autor;
}