package com.sylviavitoria.blogpets.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@Schema(description = "Dados de resposta de um Post")
public class PostResponse {

    @Schema(description = "Titulo de um Post")
    String titulo;
    
    @Schema(description = "Descrição de um Post")
    String descricao;

    @Schema(description = "Autor de um Post")
    String autor;

}
